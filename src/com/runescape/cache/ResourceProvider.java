package com.runescape.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import com.runescape.Configuration;
import com.runescape.MapViewer;
import com.runescape.collection.Deque;
import com.runescape.collection.Queue;
import com.runescape.io.Buffer;
import com.softgate.fs.binary.Archive;
import com.softgate.util.CompressionUtil;

public final class ResourceProvider implements Runnable {

	private final String crcNames[] = {"model_crc", "anim_crc", "midi_crc", "map_crc"};
    private final int[][] crcs = new int[crcNames.length][];
	private final byte[][] fileStatus;
	private final byte[] gzipInputBuffer;
	private boolean running;
	private int[] areas;
	private int[] mapFiles;
	private int[] landscapes;
	private int[] file_amounts = new int[4];
	private int[] musicPriorities;
	private MapViewer clientInstance;
	private int tick;
	private int maximumPriority;
	private boolean expectingData;
	private int uncompletedCount;
	private InputStream inputStream;
	private final Deque requested;
	private int idleTime;
	private Socket socket;
	private OutputStream outputStream;
	private int remainingData;
	private final Deque mandatoryRequests;
	private final Deque unrequested;
	private final Deque complete;
	private int completedCount;
	private int filesLoaded;
    private final Deque extras;
	private int totalFiles;
	private final byte[] payload;
	private Resource current;
	private int completedSize;
	private final Queue requests;
	private int[] cheapHaxValues = new int[]{
	            3627, 3628,
	            3655, 3656,
	            3625, 3626,
	            3629, 3630,
	            4071, 4072,
	            5253, 1816,
	            1817, 3653,
	            3654, 4067,
	            4068, 3639,
	            3640, 1976,
	            1977, 3571,
	            3572, 5129,
	            5130, 2066,
	            2067, 3545,
	            3546, 3559,
	            3560, 3569,
	            3570, 3551,
	            3552, 3579,
	            3580, 3575,
	            3576, 1766,
	            1767, 3547,
	            3548, 3682,
	            3683, 3696,
	            3697, 3692,
	            3693, 4013,
	            4079, 4080,
	            4082, 3996,
	            4083, 4084,
	            4075, 4076,
	            3664, 3993,
	            3994, 3995,
	            4077, 4078,
	            4073, 4074,
	            4011, 4012,
	            3998, 3999,
	            4081,
	    };
	
    public ResourceProvider() {
        requested = new Deque();
        payload = new byte[500];
        fileStatus = new byte[4][];
        extras = new Deque();
        running = true;
        expectingData = false;
        requests = new Queue();
        complete = new Deque();
        unrequested = new Deque();
        mandatoryRequests = new Deque();
        gzipInputBuffer = new byte[0x71868];
    }
	
	public void initialize(Archive archive, MapViewer client) throws IOException {
		for (int i = 0; i < crcNames.length; i++) {
            byte[] crc_file = archive.readFile(crcNames[i]);
            int length = 0;

            if (crc_file != null) {
                length = crc_file.length / 4;
                Buffer crcStream = new Buffer(crc_file);
                crcs[i] = new int[length];
                fileStatus[i] = new byte[length];
                for (int ptr = 0; ptr < length; ptr++) {
                    crcs[i][ptr] = crcStream.readInt();
                }
            }
        }


        byte[] data = archive.readFile("map_index");
        Buffer stream = new Buffer(data);
        int j1 = stream.readUShort();
        areas = new int[j1];
        mapFiles = new int[j1];
        landscapes = new int[j1];
        file_amounts[3] = j1;
        for (int i2 = 0; i2 < j1; i2++) {
            areas[i2] = stream.readUShort();
            mapFiles[i2] = stream.readUShort();
            landscapes[i2] = stream.readUShort();
        }
        
        data = archive.readFile("midi_index");
        stream = new Buffer(data);
        j1 = data.length;
        file_amounts[2] = j1;
        musicPriorities = new int[j1];
        for (int k2 = 0; k2 < j1; k2++)
            musicPriorities[k2] = stream.readUnsignedByte();
  
        data = archive.readFile("model_index");
        file_amounts[1] = data.length;

        data = archive.readFile("anim_index");
        file_amounts[0] = data.length;
       
        clientInstance = client;
        running = true;
        clientInstance.startThread(this, 2);
	}
	
	@Override
	public void run() {
		try {
            while (running) {
                tick++;
                int sleepTime = 20;
                if (maximumPriority == 0 && Configuration.CACHE.getStore(0) != null)
                    sleepTime = 50;
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                expectingData = true;
                for (int index = 0; index < 100; index++) {
                    if (!expectingData)
                        break;
                    expectingData = false;
                    loadMandatory();
                    requestMandatory();
                    if (uncompletedCount == 0 && index >= 5)
                        break;
                    loadExtra();
                    if (inputStream != null)
                        respond();
                }

                boolean idle = false;
                for (Resource resource = (Resource) requested.reverseGetFirst(); resource != null; resource = (Resource) requested.reverseGetNext())
                    if (resource.incomplete) {
                        idle = true;
                        resource.loopCycle++;
                        if (resource.loopCycle > 50) {
                            resource.loopCycle = 0;
                        }
                    }

                if (!idle) {
                    for (Resource resource = (Resource) requested.reverseGetFirst(); resource != null; resource = (Resource) requested.reverseGetNext()) {
                        idle = true;
                        resource.loopCycle++;
                        if (resource.loopCycle > 50) {
                            resource.loopCycle = 0;
                        }
                    }

                }
                if (idle) {
                    idleTime++;
                    if (idleTime > 750) {
                        try {
                            socket.close();
                        } catch (Exception _ex) {
                        }
                        socket = null;
                        inputStream = null;
                        outputStream = null;
                        remainingData = 0;
                    }
                } else {
                    idleTime = 0;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("od_ex " + exception.getMessage());
        }
	}

	private void loadMandatory() {
        Resource resource;
        synchronized (mandatoryRequests) {
            resource = (Resource) mandatoryRequests.popHead();
        }
        while (resource != null) {
            expectingData = true;
            byte data[] = null;

            if (Configuration.CACHE.getStore(0) != null)
            	data = Configuration.CACHE.getStore(resource.dataType + 1).readFile(resource.ID);

            synchronized (mandatoryRequests) {
                if (data == null) {
                    unrequested.insertHead(resource);
                } else {
                    resource.buffer = data;
                    synchronized (complete) {
                        complete.insertHead(resource);
                    }
                }
                resource = (Resource) mandatoryRequests.popHead();
            }
        }
    }
	
	private void requestMandatory() {
        uncompletedCount = 0;
        completedCount = 0;
        for (Resource resource = (Resource) requested.reverseGetFirst(); resource != null; resource = (Resource) requested.reverseGetNext())
            if (resource.incomplete) {
                uncompletedCount++;
                System.out.println("Error: model is incomplete or missing  [ type = " + resource.dataType + "]  [id = " + resource.ID + "]");
            } else
                completedCount++;

        while (uncompletedCount < 10) {
            Resource request = (Resource) unrequested.popHead();
            if (request == null) {
                break;
            }
            try {
                if (fileStatus[request.dataType][request.ID] != 0) {
                    filesLoaded++;
                }
                fileStatus[request.dataType][request.ID] = 0;
                requested.insertHead(request);
                uncompletedCount++;
                expectingData = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
	
	private void loadExtra() {
        while (uncompletedCount == 0 && completedCount < 10) {
            if (maximumPriority == 0)
                break;
            Resource resource;
            synchronized (extras) {
                resource = (Resource) extras.popHead();
            }
            while (resource != null) {
                if (fileStatus[resource.dataType][resource.ID] != 0) {
                    fileStatus[resource.dataType][resource.ID] = 0;
                    requested.insertHead(resource);
                    expectingData = true;
                    if (filesLoaded < totalFiles)
                        filesLoaded++;
                    completedCount++;
                    if (completedCount == 10)
                        return;
                }
                synchronized (extras) {
                    resource = (Resource) extras.popHead();
                }
            }
            for (int type = 0; type < 4; type++) {
                byte data[] = fileStatus[type];
                int size = data.length;
                for (int file = 0; file < size; file++)
                    if (data[file] == maximumPriority) {
                        data[file] = 0;
                        Resource newResource = new Resource();
                        newResource.dataType = type;
                        newResource.ID = file;
                        newResource.incomplete = false;
                        requested.insertHead(newResource);
                        expectingData = true;
                        if (filesLoaded < totalFiles)
                            filesLoaded++;
                        completedCount++;
                        if (completedCount == 10)
                            return;
                    }
            }
            maximumPriority--;
        }
    }
	
	private void respond() {
        try {
            int available = inputStream.available();
            if (remainingData == 0 && available >= 10) {
                expectingData = true;
                for (int skip = 0; skip < 10; skip += inputStream.read(payload, skip, 10 - skip))
                    ;
                int type = payload[0] & 0xff;
                int file = ((payload[1] & 0xff) << 16) + ((payload[2] & 0xff) << 8) + (payload[3] & 0xff);
                int length = ((payload[4] & 0xff) << 32) + ((payload[5] & 0xff) << 16) + ((payload[6] & 0xff) << 8) + (payload[7] & 0xff);
                int sector = ((payload[8] & 0xff) << 8) + (payload[9] & 0xff);
                current = null;
                for (Resource resource = (Resource) requested.reverseGetFirst(); resource != null; resource = (Resource) requested.reverseGetNext()) {
                    if (resource.dataType == type && resource.ID == file)
                        current = resource;
                    if (current != null)
                        resource.loopCycle = 0;
                }

                if (current != null) {
                    idleTime = 0;
                    if (length == 0) {
                        System.out.println("Rej: " + type + "," + file);
                        current.buffer = null;
                        if (current.incomplete)
                            synchronized (complete) {
                                complete.insertHead(current);
                            }
                        else {
                            current.unlink();
                        }
                        current = null;
                    } else {
                        if (current.buffer == null && sector == 0)
                            current.buffer = new byte[length];
                        if (current.buffer == null && sector != 0)
                            throw new IOException("missing start of file");
                    }
                }
                completedSize = sector * 500;
                remainingData = 500;
                if (remainingData > length - sector * 500)
                    remainingData = length - sector * 500;
            }
            if (remainingData > 0 && available >= remainingData) {
                expectingData = true;
                byte data[] = payload;
                int read = 0;
                if (current != null) {
                    data = current.buffer;
                    read = completedSize;
                }
                for (int skip = 0; skip < remainingData; skip += inputStream.read(data, skip + read, remainingData - skip))
                    ;
                if (remainingData + completedSize >= data.length && current != null) {
                    if (Configuration.CACHE.getStore(0) != null)
                    	Configuration.CACHE.getStore(current.dataType + 1).writeFile(data.length, data, current.ID);
                    if (!current.incomplete && current.dataType == 3) {
                        current.incomplete = true;
                        current.dataType = 93;
                    }
                    if (current.incomplete)
                        synchronized (complete) {
                            complete.insertHead(current);
                        }
                    else {
                        current.unlink();
                    }
                }
                remainingData = 0;
            }
        } catch (IOException ex) {
            try {
                socket.close();
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }
            socket = null;
            inputStream = null;
            outputStream = null;
            remainingData = 0;
        }
    }
	
	public int getMapIndex(int regionX, int regionY, int type) {
		int code = (type << 8) + regionY;
		for (int area = 0; area < areas.length; area++) {			
			if (areas[area] == code) {
				if (regionX == 0) {
					return mapFiles[area] > 3535 ? -1 : mapFiles[area];
				} else {
					return landscapes[area] > 3535 ? -1 : landscapes[area];
				}
			}
		}
		return -1;
	}

	public void clearExtras() {
        synchronized (extras) {
            extras.clear();
        }
    }
	
	public byte[] getModel(int id) {
    	try {
    		return CompressionUtil.degzip(ByteBuffer.wrap(Configuration.CACHE.getStore(1).readFile(id)));
    	} catch (Exception exception) {
    		exception.printStackTrace();
    	}

    	return null;
	}
}
