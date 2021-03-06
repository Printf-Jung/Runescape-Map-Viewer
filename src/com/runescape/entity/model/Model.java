package com.runescape.entity.model;

import com.runescape.cache.defintion.MapDefinition;
import com.runescape.draw.Rasterizer2D;
import com.runescape.draw.Rasterizer3D;
import com.runescape.entity.Renderable;
import com.runescape.io.Buffer;
import com.runescape.scene.SceneGraph;

public class Model extends Renderable {
	
	public static int sine[];
	public static int cosine[];
	private static ModelHeader modelHeaderCache[];
	private static MapDefinition resourceProvider;
	public static boolean aBoolean1684;
	public static int mouseX;
	public static int mouseY;
	public static int anInt1687;
	public int maxVertexDistanceXZPlane;
	public int[] vertexX;
	public int maximumYVertex;
	public int[] vertexY;
	public int minimumXVertex;
	public int minimumZVertex;
	public int verticeCount;
	public VertexNormal[] alsoVertexNormals;
	public int maximumXVertex;
	public int[] vertexZ;
	public int maximumZVertex;
	public int triangleCount;
	public int[] facePointA;
	public int[] facePointB;
	public int[] facePointC;
	public int[] faceDrawType;
	public int faceGroups[][];
	public int vertexGroups[][];
	public int itemDropHeight;	
	private int diagonal3DAboveOrigin;
	private boolean fitsOnSingleTile;
	private int textureTriangleCount;
	private int faceHslA[];
	private int faceHslB[];
	private int faceHslC[];
	private int[] verticesParticle;
	private short triangleColours[];
	private short[] texture;
	private byte[] textureCoordinates;
	private int faceAlpha[];
	private byte faceRenderPriorities[];
	private byte facePriority;
	private int maxRenderDepth;
	private short texturesFaceA[];
	private short texturesFaceB[];
	private short texturesFaceC[];
	private int vertexVSkin[];
	private int triangleTSkin[];
	private byte[] textureType;
	private static int modelIntArray3[];
	private static int anIntArray1688[] = new int[1000];
	private static int projected_vertex_x[] = new int[8000];
	private static int projected_vertex_y[] = new int[8000];
	private static int projected_vertex_z[] = new int[8000];
	private static int camera_vertex_y[] = new int[8000];
	private static int camera_vertex_x[] = new int[8000];
	private static int camera_vertex_z[] = new int[8000];
	private static int anIntArray1668[] = new int[8000];
	private static int depthListIndices[] = new int[3000];
	private static int faceLists[][] = new int[1600][512];
	private static int anIntArray1673[] = new int[12];
	private static int anIntArrayArray1674[][] = new int[12][2000];
	private static int anIntArray1675[] = new int[2000];
	private static int anIntArray1676[] = new int[2000];
	private static int anIntArray1677[] = new int[12];
	private static boolean hasAnEdgeToRestrict[] = new boolean[8000];
	private static boolean outOfReach[] = new boolean[8000];
	private static int anIntArray1678[] = new int[10];
	private static int anIntArray1679[] = new int[10];
	private static int anIntArray1680[] = new int[10];
	private static int modelIntArray4[];
	
	static {
		sine = Rasterizer3D.sine;
		cosine = Rasterizer3D.cosine;
		modelIntArray3 = Rasterizer3D.hslToRgb;
		modelIntArray4 = Rasterizer3D.DEPTH;
	}
	
	public Model(boolean contouredGround, boolean delayShading, Model model) {
		fitsOnSingleTile = false;
		verticeCount = model.verticeCount;
		triangleCount = model.triangleCount;
		textureTriangleCount = model.textureTriangleCount;
		if (contouredGround) {
			vertexY = new int[verticeCount];
			for (int index = 0; index < verticeCount; index++) {
				vertexY[index] = model.vertexY[index];
			}
		} else {
			vertexY = model.vertexY;
		}
		
		if (delayShading) {
			faceHslA = new int[triangleCount];
			faceHslB = new int[triangleCount];
			faceHslC = new int[triangleCount];
			for (int index = 0; index < triangleCount; index++) {
				faceHslA[index] = model.faceHslA[index];
				faceHslB[index] = model.faceHslB[index];
				faceHslC[index] = model.faceHslC[index];
			}

			faceDrawType = new int[triangleCount];
			if (model.faceDrawType == null) {
				for (int index = 0; index < triangleCount; index++) {
					faceDrawType[index] = 0;
				}
			} else {
				for (int index = 0; index < triangleCount; index++) {
					faceDrawType[index] = model.faceDrawType[index];
				}
			}
			super.vertexNormals = new VertexNormal[verticeCount];
			for (int index = 0; index < verticeCount; index++) {
				VertexNormal vertexNormalPrimary = super.vertexNormals[index] = new VertexNormal();
				VertexNormal vertexNormalSecondary = model.vertexNormals[index];
				vertexNormalPrimary.normalX = vertexNormalSecondary.normalX;
				vertexNormalPrimary.normalY = vertexNormalSecondary.normalY;
				vertexNormalPrimary.normalZ = vertexNormalSecondary.normalZ;
				vertexNormalPrimary.magnitude = vertexNormalSecondary.magnitude;
			}
			alsoVertexNormals = model.alsoVertexNormals;
		} else {
			faceHslA = model.faceHslA;
			faceHslB = model.faceHslB;
			faceHslC = model.faceHslC;
			faceDrawType = model.faceDrawType;
		}
		verticesParticle = model.verticesParticle;
		vertexX = model.vertexX;
		vertexZ = model.vertexZ;
		triangleColours = model.triangleColours;
		faceAlpha = model.faceAlpha;
		faceRenderPriorities = model.faceRenderPriorities;
		facePriority = model.facePriority;
		facePointA = model.facePointA;
		facePointB = model.facePointB;
		facePointC = model.facePointC;
		texturesFaceA = model.texturesFaceA;
		texturesFaceB = model.texturesFaceB;
		texturesFaceC = model.texturesFaceC;
		super.modelBaseY = model.modelBaseY;
		textureCoordinates = model.textureCoordinates;
		texture = model.texture;
		maxVertexDistanceXZPlane = model.maxVertexDistanceXZPlane;
		diagonal3DAboveOrigin = model.diagonal3DAboveOrigin;
		maxRenderDepth = model.maxRenderDepth;
		minimumXVertex = model.minimumXVertex;
		maximumZVertex = model.maximumZVertex;
		minimumZVertex = model.minimumZVertex;
		maximumXVertex = model.maximumXVertex;
	}  
	
	public Model(int length, Model model_segments[]) {
		try {
			fitsOnSingleTile = false;
			boolean renderTypeFlag = false;
			boolean priorityFlag = false;
			boolean alphaFlag = false;
			boolean tSkinFlag = false;
			boolean colorFlag = false;
			boolean textureFlag = false;
			boolean coordinateFlag = false;
			verticeCount = 0;
			triangleCount = 0;
			textureTriangleCount = 0;
			facePriority = -1;
			Model build;
			for (int segment_index = 0; segment_index < length; segment_index++) {
				build = model_segments[segment_index];
				if (build != null) {
					verticeCount += build.verticeCount;
					triangleCount += build.triangleCount;
					textureTriangleCount += build.textureTriangleCount;
					renderTypeFlag |= build.faceDrawType != null;
					alphaFlag |= build.faceAlpha != null;
					if (build.faceRenderPriorities != null) {
						priorityFlag = true;
					} else {
						if (facePriority == -1) {
							facePriority = build.facePriority;
						}
							
						if (facePriority != build.facePriority) {
							priorityFlag = true;
						}
					}
					tSkinFlag |= build.triangleTSkin != null;
					colorFlag |= build.triangleColours != null;
					textureFlag |= build.texture != null;
					coordinateFlag |= build.textureCoordinates != null;
				}
			}
			verticesParticle = new int[verticeCount];
			vertexX = new int[verticeCount];
			vertexY = new int[verticeCount];
			vertexZ = new int[verticeCount];
			vertexVSkin = new int[verticeCount];
			facePointA = new int[triangleCount];
			facePointB = new int[triangleCount];
			facePointC = new int[triangleCount];
			if(colorFlag) {
				triangleColours = new short[triangleCount];
			}
			
			if (renderTypeFlag) {
				faceDrawType = new int[triangleCount];
			}
			
			if (priorityFlag) {
				faceRenderPriorities = new byte[triangleCount];
			}
			
			if (alphaFlag) {
				faceAlpha = new int[triangleCount];
			}
			
			if (tSkinFlag) {
				triangleTSkin = new int[triangleCount];
			}
			
			if(textureFlag) {
				texture = new short[triangleCount];
			}
			
			if (coordinateFlag) {
				textureCoordinates = new byte[triangleCount];
			}
			
			if(textureTriangleCount > 0) {
				textureType = new byte[textureTriangleCount];
				texturesFaceA = new short[textureTriangleCount];
				texturesFaceB = new short[textureTriangleCount];
				texturesFaceC = new short[textureTriangleCount];
			}
			verticeCount = 0;
			triangleCount = 0;
			textureTriangleCount = 0;
			int texture_face = 0;
			for (int segmentIndex = 0; segmentIndex < length; segmentIndex++) {
				build = model_segments[segmentIndex];
				if (build != null) {
					for (int face = 0; face < build.triangleCount; face++) {
						if(renderTypeFlag && build.faceDrawType != null) {
							faceDrawType[triangleCount] = build.faceDrawType[face];
						}
						
						if (priorityFlag) {
							if (build.faceRenderPriorities == null) {
								faceRenderPriorities[triangleCount] = build.facePriority;
							} else {
								faceRenderPriorities[triangleCount] = build.faceRenderPriorities[face];
							}
						}
						
						if (alphaFlag && build.faceAlpha != null) {
							faceAlpha[triangleCount] = build.faceAlpha[face];
						}
						
						if (tSkinFlag && build.triangleTSkin != null) {
							triangleTSkin[triangleCount] = build.triangleTSkin[face];
						}
						
						if(textureFlag) {
							if(build.texture != null) {
								texture[triangleCount] = build.texture[face];
							} else {
								texture[triangleCount] = -1;
							}
						}
						
						if(coordinateFlag) {
							if(build.textureCoordinates != null && build.textureCoordinates[face] != -1) {
								textureCoordinates[triangleCount] = (byte) (build.textureCoordinates[face] + texture_face);
							} else {
								textureCoordinates[triangleCount] = -1;
							}
						}
						triangleColours[triangleCount] = build.triangleColours[face];
						facePointA[triangleCount] = getSharedVertices(build, build.facePointA[face]);
						facePointB[triangleCount] = getSharedVertices(build, build.facePointB[face]);
						facePointC[triangleCount] = getSharedVertices(build, build.facePointC[face]);
						triangleCount++;
					}
					
					for (int textureEdge = 0; textureEdge < build.textureTriangleCount; textureEdge++) {
						texturesFaceA[textureTriangleCount] = (short) getSharedVertices(build, build.texturesFaceA[textureEdge]);
						texturesFaceB[textureTriangleCount] = (short) getSharedVertices(build, build.texturesFaceB[textureEdge]);
						texturesFaceC[textureTriangleCount] = (short) getSharedVertices(build, build.texturesFaceC[textureEdge]);
						textureTriangleCount++;
					}
					texture_face += build.textureTriangleCount;
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public Model(boolean colorFlag, boolean alphaFlag, boolean animated, boolean textureFlag, Model model) {
		fitsOnSingleTile = false;
		verticeCount = model.verticeCount;
		triangleCount = model.triangleCount;
		textureTriangleCount = model.textureTriangleCount;
		if (animated) {
			verticesParticle = model.verticesParticle;
			vertexX = model.vertexX;
			vertexY = model.vertexY;
			vertexZ = model.vertexZ;
		} else {
			verticesParticle = new int[verticeCount];
			vertexX = new int[verticeCount];
			vertexY = new int[verticeCount];
			vertexZ = new int[verticeCount];
			for (int index = 0; index < verticeCount; index++) {
				verticesParticle[index] = model.verticesParticle[index];
				vertexX[index] = model.vertexX[index];
				vertexY[index] = model.vertexY[index];
				vertexZ[index] = model.vertexZ[index];
			}

		}
		
		if (colorFlag) {
			triangleColours = model.triangleColours;
		} else {
			triangleColours = new short[triangleCount];
			for (int index = 0; index < triangleCount; index++) {
				triangleColours[index] = model.triangleColours[index];
			}
		}

		if(!textureFlag && model.texture != null) {
			texture = new short[triangleCount];
			for(int face = 0; face < triangleCount; face++) {
				texture[face] = model.texture[face];
			}
		} else {
			texture = model.texture;
		}

		if (alphaFlag) {
			faceAlpha = model.faceAlpha;
		} else {
			faceAlpha = new int[triangleCount];
			if (model.faceAlpha == null) {
				for (int index = 0; index < triangleCount; index++) {
					faceAlpha[index] = 0;
				}
			} else {
				for (int index = 0; index < triangleCount; index++) {
					faceAlpha[index] = model.faceAlpha[index];
				}
			}
		}
		vertexVSkin = model.vertexVSkin;
		triangleTSkin = model.triangleTSkin;
		faceDrawType = model.faceDrawType;
		facePointA = model.facePointA;
		facePointB = model.facePointB;
		facePointC = model.facePointC;
		faceRenderPriorities = model.faceRenderPriorities;
		facePriority = model.facePriority;
		texturesFaceA = model.texturesFaceA;
		texturesFaceB = model.texturesFaceB;
		texturesFaceC = model.texturesFaceC;
		textureCoordinates = model.textureCoordinates;
		textureType = model.textureType;
	}

	public Model(int modelId) {
		byte[] modelData = modelHeaderCache[modelId].modelData;
		
		if (modelData[modelData.length - 1] == -1 && modelData[modelData.length - 2] == -1) {
			decodeNew(modelData, modelId);
		} else {
			decodeOld(modelData, modelId);
		}
	}
	
	private final int getSharedVertices(Model model, int point) {
		int sharedVertex = -1;
		int particlePoint = model.verticesParticle[point];
		int x = model.vertexX[point];
		int y = model.vertexY[point];
		int z = model.vertexZ[point];
		for (int index = 0; index < verticeCount; index++) {
			if (x != vertexX[index] || y != vertexY[index] || z != vertexZ[index]) {
				continue;
			}
			sharedVertex = index;
			break;
		}

		if (sharedVertex == -1) {
			verticesParticle[verticeCount] = particlePoint;
			vertexX[verticeCount] = x;
			vertexY[verticeCount] = y;
			vertexZ[verticeCount] = z;
			if (model.vertexVSkin != null) {
				vertexVSkin[verticeCount] = model.vertexVSkin[point];
			}
			sharedVertex = verticeCount++;
		}
		return sharedVertex;
	}

	public void decodeOld(byte[] data, int modelId) {
		boolean hasFaceType = false;
		boolean hasTexture_Type = false;
		Buffer first = new Buffer(data);
		Buffer second = new Buffer(data);
		Buffer third = new Buffer(data);
		Buffer fourth = new Buffer(data);
		Buffer fifth = new Buffer(data);
		first.currentPosition = data.length - 18;
		verticeCount = first.readUShort();
		triangleCount = first.readUShort();
		textureTriangleCount = first.readUnsignedByte();
		int renderTypeOpcode = first.readUnsignedByte();
		int priorityOpcode = first.readUnsignedByte();
		int alphaOpcode = first.readUnsignedByte();
		int tSkinOpcode = first.readUnsignedByte();
		int vSkinOpcode = first.readUnsignedByte();
		int vertexX = first.readUShort();
		int vertexY = first.readUShort();
		int vertexZ = first.readUShort();
		int vertexPoints = first.readUShort();
		int position = 0;

		int vertexFlagOffset = position;
		position += verticeCount;

		int faceCompressTypeOffset = position;
		position += triangleCount;

		int facePriorityOffset = position;
		if (priorityOpcode == 255) {
			position += triangleCount;
		}

		int tSkinOffset = position;
		if (tSkinOpcode == 1) {
			position += triangleCount;
		}
		
		int renderTypeOffset = position;
		if (renderTypeOpcode == 1) {
			position += triangleCount;
		}
		
		int vSkinOffset = position;
		if (vSkinOpcode == 1) {
			position += verticeCount;
		}
		
		int alphaOffset = position;
		if (alphaOpcode == 1) {
			position += triangleCount;
		}
		
		int pointsOffset = position;
		position += vertexPoints;

		int colorOffset = position;
		position += triangleCount * 2;

		int textureOffset = position;
		position += textureTriangleCount * 6;

		int vertexXOffset = position;
		position += vertexX;

		int vertexYOffset = position;
		position += vertexY;
		
		int vertexZOffset = position;
		position += vertexZ;
		verticesParticle = new int[verticeCount];
		this.vertexX = new int[verticeCount];
		this.vertexY = new int[verticeCount];
		this.vertexZ = new int[verticeCount];
		facePointA = new int[triangleCount];
		facePointB = new int[triangleCount];
		facePointC = new int[triangleCount];
		if (textureTriangleCount > 0) {
			textureType = new byte[textureTriangleCount];
			texturesFaceA = new short[textureTriangleCount];
			texturesFaceB = new short[textureTriangleCount];
			texturesFaceC = new short[textureTriangleCount];
		}

		if (vSkinOpcode == 1)
			vertexVSkin = new int[verticeCount];

		if (renderTypeOpcode == 1) {
			faceDrawType = new int[triangleCount];
			textureCoordinates = new byte[triangleCount];
			texture = new short[triangleCount];
		}

		if (priorityOpcode == 255) {
			faceRenderPriorities = new byte[triangleCount];
		} else {
			facePriority = (byte) priorityOpcode;
		}
		
		if (alphaOpcode == 1) {
			faceAlpha = new int[triangleCount];
		}
		
		if (tSkinOpcode == 1) {
			triangleTSkin = new int[triangleCount];
		}	
		triangleColours = new short[triangleCount];
		first.currentPosition = vertexFlagOffset;
		second.currentPosition = vertexXOffset;
		third.currentPosition = vertexYOffset;
		fourth.currentPosition = vertexZOffset;
		fifth.currentPosition = vSkinOffset;
		int startX = 0;
		int startY = 0;
		int startZ = 0;
		for (int point = 0; point < verticeCount; point++) {
			int positionMask = first.readUnsignedByte();
			int x = 0;
			if ((positionMask & 0x1) != 0) {
				x = second.readSmart();
			}		
			int y = 0;
			if ((positionMask & 0x2) != 0) {
				y = third.readSmart();
			}			
			int z = 0;
			if ((positionMask & 0x4) != 0) {
				z = fourth.readSmart();
			}
			this.vertexX[point] = startX + x;
			this.vertexY[point] = startY + y;
			this.vertexZ[point] = startZ + z;
			startX = this.vertexX[point];
			startY = this.vertexY[point];
			startZ = this.vertexZ[point];
			if (vSkinOpcode == 1) {
				vertexVSkin[point] = fifth.readUnsignedByte();
			}
		}
		first.currentPosition = colorOffset;
		second.currentPosition = renderTypeOffset;
		third.currentPosition = facePriorityOffset;
		fourth.currentPosition = alphaOffset;
		fifth.currentPosition = tSkinOffset;
		for (int face = 0; face < triangleCount; face++) {
			triangleColours[face] = (short) first.readUShort();
			if (renderTypeOpcode == 1) {
				int flag = second.readUnsignedByte();
				if ((flag & 0x1) == 1) {
					faceDrawType[face] = 1;
					hasFaceType = true;
				} else {
					faceDrawType[face] = 0;
				}

				if ((flag & 0x2) != 0) {
					textureCoordinates[face] = (byte) (flag >> 2);
					texture[face] = triangleColours[face];
					triangleColours[face] = 127;
					if (texture[face] != -1) {
						hasTexture_Type = true;
					}
				} else {
					textureCoordinates[face] = -1;
					texture[face] =  -1;
				}
			}
			if (priorityOpcode == 255) {
				faceRenderPriorities[face] = third.readSignedByte();
			}
			
			if (alphaOpcode == 1) {
				faceAlpha[face] = fourth.readSignedByte();
				if (faceAlpha[face] < 0) {
					faceAlpha[face] = (256 + faceAlpha[face]);
				}

			}
			
			if (tSkinOpcode == 1) {
				triangleTSkin[face] = fifth.readUnsignedByte();
			}
		}
		first.currentPosition = pointsOffset;
		second.currentPosition = faceCompressTypeOffset;
		int coordinateA = 0;
		int coordinateB = 0;
		int coordinateC = 0;
		int offset = 0;
		int coordinate;
		for (int face = 0; face < triangleCount; face++) {
			int opcode = second.readUnsignedByte();
			if (opcode == 1) {
				coordinateA = (first.readSmart() + offset);
				offset = coordinateA;
				coordinateB = (first.readSmart() + offset);
				offset = coordinateB;
				coordinateC = (first.readSmart() + offset);
				offset = coordinateC;
				facePointA[face] = coordinateA;
				facePointB[face] = coordinateB;
				facePointC[face] = coordinateC;
			}
			if (opcode == 2) {
				coordinateB = coordinateC;
				coordinateC = (first.readSmart() + offset);
				offset = coordinateC;
				facePointA[face] = coordinateA;
				facePointB[face] = coordinateB;
				facePointC[face] = coordinateC;
			}
			if (opcode == 3) {
				coordinateA = coordinateC;
				coordinateC = (first.readSmart() + offset);
				offset = coordinateC;
				facePointA[face] = coordinateA;
				facePointB[face] = coordinateB;
				facePointC[face] = coordinateC;
			}
			if (opcode == 4) {
				coordinate = coordinateA;
				coordinateA = coordinateB;
				coordinateB = coordinate;
				coordinateC = (first.readSmart() + offset);
				offset = coordinateC;
				facePointA[face] = coordinateA;
				facePointB[face] = coordinateB;
				facePointC[face] = coordinateC;
			}
		}
		first.currentPosition = textureOffset;
		for (int face = 0; face < textureTriangleCount; face++) {
			textureType[face] = 0;
			texturesFaceA[face] = (short) first.readUShort();
			texturesFaceB[face] = (short) first.readUShort();
			texturesFaceC[face] = (short) first.readUShort();
		}
		if (textureCoordinates != null) {
			boolean textured = false;
			for (int face = 0; face < triangleCount; face++) {
				coordinate = textureCoordinates[face] & 0xff;
				if (coordinate != 255) {
					if (((texturesFaceA[coordinate] & 0xffff) == facePointA[face]) && ((texturesFaceB[coordinate] & 0xffff)  == facePointB[face]) && ((texturesFaceC[coordinate] & 0xffff) == facePointC[face])) {
						textureCoordinates[face] = -1;
					} else {
						textured = true;
					}
				}
			}
			
			if (!textured) {
				textureCoordinates = null;
			}
		}
		
		if (!hasTexture_Type) {
			texture = null;
		}

		if (!hasFaceType) {
			faceDrawType = null;
		}
	}
	
	private void decodeNew(byte data[], int modelId) {
		Buffer first = new Buffer(data);
		Buffer second = new Buffer(data);
		Buffer third = new Buffer(data);
		Buffer fourth = new Buffer(data);
		Buffer fifth = new Buffer(data);
		Buffer sixth = new Buffer(data);
		Buffer seventh = new Buffer(data);
		first.currentPosition = data.length - 23;
		verticeCount = first.readUShort();
		triangleCount = first.readUShort();
		textureTriangleCount = first.readUnsignedByte();
		int renderTypeOpcode = first.readUnsignedByte();
		int priorityOpcode = first.readUnsignedByte();
		int alphaOpcode = first.readUnsignedByte();
		int tSkinOpcode = first.readUnsignedByte();
		int textureOpcode = first.readUnsignedByte();
		int vSkinOpcode = first.readUnsignedByte();
		int vertexX = first.readUShort();
		int vertexY = first.readUShort();
		int vertexZ = first.readUShort();
		int vertexPoints = first.readUShort();
		int textureIndices = first.readUShort();
		int textureIdSimple = 0;
		int textureIdComplex = 0;
		int textureIdCube = 0;
		int face;
		triangleColours = new short[triangleCount];
		if (textureTriangleCount > 0) {
			textureType = new byte[textureTriangleCount];
			first.currentPosition = 0;
			for (face = 0; face < textureTriangleCount; face++) {
				byte opcode = textureType[face] = first.readSignedByte();
				if (opcode == 0) {
					textureIdSimple++;
				}

				if (opcode >= 1 && opcode <= 3) {
					textureIdComplex++;
				}
				
				if (opcode == 2) {
					textureIdCube++;
				}
			}
		}
		int position;
		position = textureTriangleCount;
		int vertexOffset = position;
		position += verticeCount;

		int renderTypeOffset = position;
		if (renderTypeOpcode == 1) {
			position += triangleCount;
		}

		int faceOffset = position;
		position += triangleCount;

		int facePriorityOffset = position;
		if (priorityOpcode == 255) {
			position += triangleCount;
		}
		
		int tSkinOffset = position;
		if (tSkinOpcode == 1)
			position += triangleCount;

		int vSkinOffset = position;
		if (vSkinOpcode == 1)
			position += verticeCount;

		int alphaOffset = position;
		if (alphaOpcode == 1) {
			position += triangleCount;
		}
		
		int pointsOffset = position;
		position += vertexPoints;

		int textureId = position;
		if (textureOpcode == 1) {
			position += triangleCount * 2;
		}
		
		int textureCoordinateOffset = position;
		position += textureIndices;

		int colorOffset = position;
		position += triangleCount * 2;

		int vertexXOffset = position;
		position += vertexX;

		int vertexYOffset = position;
		position += vertexY;

		int vertexZOffset = position;
		position += vertexZ;

		int simpleTextureoffset = position;
		position += textureIdSimple * 6;

		int complexTextureOffset = position;
		position += textureIdComplex * 6;

		int textureScalOffset = position;
		position += textureIdComplex * 6;

		int textureRotationOffset = position;
		position += textureIdComplex * 2;

		int textureDirectionOffset = position;
		position += textureIdComplex;

		int textureTranslateOffset = position;
		position += textureIdComplex * 2 + textureIdCube * 2;
		verticesParticle = new int[verticeCount];
		this.vertexX = new int[verticeCount];
		this.vertexY = new int[verticeCount];
		this.vertexZ = new int[verticeCount];
		facePointA = new int[triangleCount];
		facePointB = new int[triangleCount];
		facePointC = new int[triangleCount];
		if (vSkinOpcode == 1) {
			vertexVSkin = new int[verticeCount];
		}
		
		if (renderTypeOpcode == 1) {
			faceDrawType = new int[triangleCount];
		}
		
		if (priorityOpcode == 255) {
			faceRenderPriorities = new byte[triangleCount];
		} else {
			facePriority = (byte) priorityOpcode;
		}
		
		if (alphaOpcode == 1) {
			faceAlpha = new int[triangleCount];
		}
		
		if (tSkinOpcode == 1) {
			triangleTSkin = new int[triangleCount];
		}
			
		if (textureOpcode == 1) {
			texture = new short[triangleCount];
		}
		
		if (textureOpcode == 1 && textureTriangleCount > 0) {
			textureCoordinates = new byte[triangleCount];
		}

		if (textureTriangleCount > 0) {
			texturesFaceA = new short[textureTriangleCount];
			texturesFaceB = new short[textureTriangleCount];
			texturesFaceC = new short[textureTriangleCount];
		}
		first.currentPosition = vertexOffset;
		second.currentPosition = vertexXOffset;
		third.currentPosition = vertexYOffset;
		fourth.currentPosition = vertexZOffset;
		fifth.currentPosition = vSkinOffset;
		int startX = 0;
		int startY = 0;
		int startZ = 0;
		for (int point = 0; point < verticeCount; point++) {
			int positionMask = first.readUnsignedByte();
			int x = 0;
			if ((positionMask & 1) != 0) {
				x = second.readSmart();
			}
			int y = 0;
			if ((positionMask & 2) != 0) {
				y = third.readSmart();

			}
			int z = 0;
			if ((positionMask & 4) != 0) {
				z = fourth.readSmart();
			}
			this.vertexX[point] = startX + x;
			this.vertexY[point] = startY + y;
			this.vertexZ[point] = startZ + z;
			startX = this.vertexX[point];
			startY = this.vertexY[point];
			startZ = this.vertexZ[point];
			if (vertexVSkin != null) {
				vertexVSkin[point] = fifth.readUnsignedByte();
			}
		}
		first.currentPosition = colorOffset;
		second.currentPosition = renderTypeOffset;
		third.currentPosition = facePriorityOffset;
		fourth.currentPosition = alphaOffset;
		fifth.currentPosition = tSkinOffset;
		sixth.currentPosition = textureId;
		seventh.currentPosition = textureCoordinateOffset;
		for (face = 0; face < triangleCount; face++) {
			triangleColours[face] = (short) first.readUShort();
			if (renderTypeOpcode == 1) {
				faceDrawType[face] = second.readSignedByte();
			}
			
			if (priorityOpcode == 255) {
				faceRenderPriorities[face] = third.readSignedByte();
			}
			
			if (alphaOpcode == 1) {
				faceAlpha[face] = fourth.readSignedByte();
				if (faceAlpha[face] < 0) {
					faceAlpha[face] = (256 + faceAlpha[face]);
				}
			}
			
			if (tSkinOpcode == 1) {
				triangleTSkin[face] = fifth.readUnsignedByte();
			}
			
			if (textureOpcode == 1) {
				texture[face] = (short) (sixth.readUShort() - 1);
				if(texture[face] >= 0) {
					if(faceDrawType != null) {
						if(faceDrawType[face] < 2 && triangleColours[face] != 127 && triangleColours[face] != -27075) {
							texture[face] = -1;
						}
					}
				}
				
				if(texture[face] != -1) {
					triangleColours[face] = 127;
				}
			}
			
			if (textureCoordinates != null && texture[face] != -1) {
				textureCoordinates[face] = (byte) (seventh.readUnsignedByte() - 1);
			}
		}
		first.currentPosition = pointsOffset;
		second.currentPosition = faceOffset;
		int coordinateA = 0;
		int coordinateB = 0;
		int coordinateC = 0;
		int offset = 0;
		for (face = 0; face < triangleCount; face++) {
			int opcode = second.readUnsignedByte();
			if (opcode == 1) {
				coordinateA = first.readSmart() + offset;
				offset = coordinateA;
				coordinateB = first.readSmart() + offset;
				offset = coordinateB;
				coordinateC = first.readSmart() + offset;
				offset = coordinateC;
				facePointA[face] = coordinateA;
				facePointB[face] = coordinateB;
				facePointC[face] = coordinateC;
			}
			if (opcode == 2) {
				coordinateB = coordinateC;
				coordinateC = first.readSmart() + offset;
				offset = coordinateC;
				facePointA[face] = coordinateA;
				facePointB[face] = coordinateB;
				facePointC[face] = coordinateC;
			}
			if (opcode == 3) {
				coordinateA = coordinateC;
				coordinateC = first.readSmart() + offset;
				offset = coordinateC;
				facePointA[face] = coordinateA;
				facePointB[face] = coordinateB;
				facePointC[face] = coordinateC;
			}
			if (opcode == 4) {
				int tempCoordinateA = coordinateA;
				coordinateA = coordinateB;
				coordinateB = tempCoordinateA;
				coordinateC = first.readSmart() + offset;
				offset = coordinateC;
				facePointA[face] = coordinateA;
				facePointB[face] = coordinateB;
				facePointC[face] = coordinateC;
			}
		}
		first.currentPosition = simpleTextureoffset;
		second.currentPosition = complexTextureOffset;
		third.currentPosition = textureScalOffset;
		fourth.currentPosition = textureRotationOffset;
		fifth.currentPosition = textureDirectionOffset;
		sixth.currentPosition = textureTranslateOffset;
		for (face = 0; face < textureTriangleCount; face++) {
			int opcode = textureType[face] & 0xff;
			if (opcode == 0) {
				texturesFaceA[face] = (short) first.readUShort();
				texturesFaceB[face] = (short) first.readUShort();
				texturesFaceC[face] = (short) first.readUShort();
			}
			
			if (opcode == 1) {
				texturesFaceA[face] = (short) second.readUShort();
				texturesFaceB[face] = (short) second.readUShort();
				texturesFaceC[face] = (short) second.readUShort();
			}
			
			if (opcode == 2) {
				texturesFaceA[face] = (short) second.readUShort();
				texturesFaceB[face] = (short) second.readUShort();
				texturesFaceC[face] = (short) second.readUShort();
			}
			
			if (opcode == 3) {
				texturesFaceA[face] = (short) second.readUShort();
				texturesFaceB[face] = (short) second.readUShort();
				texturesFaceC[face] = (short) second.readUShort();
			}
		}
		first.currentPosition = vertexOffset;
		face = first.readUnsignedByte();
	}

	public static void initialize(int modelAmount, MapDefinition resourceProviderInstance) {
		modelHeaderCache = new ModelHeader[modelAmount];
		resourceProvider = resourceProviderInstance;
	}

	public final void flatLighting(int intensity, int distributionFactor, int lightX, int lightY, int lightZ) {
		for (int triangle = 0; triangle < triangleCount; triangle++) {
			int a = facePointA[triangle];
			int b = facePointB[triangle];
			int c = facePointC[triangle];
			short textureId;
			if(texture == null) {
				textureId = -1;
			} else {
				textureId = texture[triangle];
			}
			if (faceDrawType == null) {
				int type;
				if(textureId != -1) {
					type = 2;
				} else {
					type = 1;
				}
				int hsl = triangleColours[triangle];
				VertexNormal vertexNormal = super.vertexNormals[a];
				int lightItensity = intensity + (lightX * vertexNormal.normalX + lightY * vertexNormal.normalY + lightZ * vertexNormal.normalZ) / (distributionFactor * vertexNormal.magnitude);
				faceHslA[triangle] = light(hsl, lightItensity, type);
				vertexNormal = super.vertexNormals[b];
				lightItensity = intensity + (lightX * vertexNormal.normalX + lightY * vertexNormal.normalY + lightZ * vertexNormal.normalZ) / (distributionFactor * vertexNormal.magnitude);
				faceHslB[triangle] = light(hsl, lightItensity, type);
				vertexNormal = super.vertexNormals[c];
				lightItensity = intensity + (lightX * vertexNormal.normalX + lightY * vertexNormal.normalY + lightZ * vertexNormal.normalZ) / (distributionFactor * vertexNormal.magnitude);
				faceHslC[triangle] = light(hsl, lightItensity, type);
			} else if ((faceDrawType[triangle] & 1) == 0) {
				int hsl = triangleColours[triangle];
				int type = faceDrawType[triangle];
				if(textureId != -1) {
					type = 2;
				}
				VertexNormal vertexNormal = super.vertexNormals[a];
				int lightItensity = intensity + (lightX * vertexNormal.normalX + lightY * vertexNormal.normalY + lightZ * vertexNormal.normalZ) / (distributionFactor * vertexNormal.magnitude);
				faceHslA[triangle] = light(hsl, lightItensity, type);
				vertexNormal = super.vertexNormals[b];
				lightItensity = intensity + (lightX * vertexNormal.normalX + lightY * vertexNormal.normalY + lightZ * vertexNormal.normalZ) / (distributionFactor * vertexNormal.magnitude);
				faceHslB[triangle] = light(hsl, lightItensity, type);
				vertexNormal = super.vertexNormals[c];
				lightItensity = intensity + (lightX * vertexNormal.normalX + lightY * vertexNormal.normalY + lightZ * vertexNormal.normalZ) / (distributionFactor * vertexNormal.magnitude);
				faceHslC[triangle] = light(hsl, lightItensity, type);
			}
		}

		super.vertexNormals = null;
		alsoVertexNormals = null;
		vertexVSkin = null;
		triangleTSkin = null;
		triangleColours = null;
	}
	
	public static final int light(int hsl, int light, int type) {
		if (hsl == 65535) {
			return 0;
		}
		
		if ((type & 2) == 2)  {
			return light(light);
		}
		
		return light(hsl, light);
	}
	
	public static final int light(int light) {
		if (light < 0) {
			light = 0;
		} else if (light > 127) {
			light = 127;
		}
		light = 127 - light;
		return light;
	}
	
	public static final int light(int hsl, int light) {
		light = light * (hsl & 0x7f) >> 7;
		if(light < 2) {
			light = 2;
		} else if(light > 126) {
			light = 126;
		}
		return (hsl & 0xff80) + light;
	}

	public void computeSphericalBounds() {
		super.modelBaseY = 0;
		maximumYVertex = 0;
		for (int index = 0; index < verticeCount; index++) {
			int y = vertexY[index];
			if (-y > super.modelBaseY) {
				super.modelBaseY = -y;
			}
			if (y > maximumYVertex) {
				maximumYVertex = y;
			}
		}
		diagonal3DAboveOrigin = (int) (Math.sqrt(maxVertexDistanceXZPlane * maxVertexDistanceXZPlane + super.modelBaseY * super.modelBaseY) + 0.98999999999999999D);
		maxRenderDepth = diagonal3DAboveOrigin + (int) (Math.sqrt(maxVertexDistanceXZPlane * maxVertexDistanceXZPlane + maximumYVertex * maximumYVertex) + 0.98999999999999999D);
	}

	public void invert() {
		for (int index = 0; index < verticeCount; index++) {
			vertexZ[index] = -vertexZ[index];
		}
		for (int face = 0; face < triangleCount; face++) {
			int triA = facePointA[face];
			facePointA[face] = facePointC[face];
			facePointC[face] = triA;
		}
	}

	public static Model get(int file) {
		if (modelHeaderCache == null) {
			return null;
		}
		ModelHeader modelHeader = modelHeaderCache[file];
		if (modelHeader == null) {
			decodeHeader(resourceProvider.getModel(file), file);
			return new Model(file);
		} else {
			return new Model(file);
		}
	}

	public void rotate90Degrees() {
		for (int index = 0; index < verticeCount; index++) {
			int x = vertexX[index];
			vertexX[index] = vertexZ[index];
			vertexZ[index] = -x;
		}
	}

	public void recolor(int found, int replace) {
		if(triangleColours != null) {
			for (int face = 0; face < triangleCount; face++) {
				if (triangleColours[face] == (short) found) {
					triangleColours[face] = (short) replace;
				}
			}
		}
	}

	public void retexture(short found, short replace) {
		if(texture != null) {
			for (int face = 0; face < triangleCount; face++) {
				if (texture[face] == found) {
					texture[face] = replace;
				}
			}
		}
	}

	public void scale(int x, int z, int y) {
		for (int index = 0; index < verticeCount; index++) {
			vertexX[index] = (vertexX[index] * x) / 128;
			vertexY[index] = (vertexY[index] * y) / 128;
			vertexZ[index] = (vertexZ[index] * z) / 128;
		}

	}

	public void translate(int x, int y, int z) {
		for (int index = 0; index < verticeCount; index++) {
			vertexX[index] += x;
			vertexY[index] += y;
			vertexZ[index] += z;
		}
	}
//TODO:
	public final void light(int i, int j, int k, int l, int i1, boolean lightModelNotSure) {
		int j1 = (int) Math.sqrt(k * k + l * l + i1 * i1);
		int k1 = j * j1 >> 8;
		if (faceHslA == null) {
			faceHslA = new int[triangleCount];
			faceHslB = new int[triangleCount];
			faceHslC = new int[triangleCount];
		}
		
		if (super.vertexNormals == null) {
			super.vertexNormals = new VertexNormal[verticeCount];
			for (int l1 = 0; l1 < verticeCount; l1++) {
				super.vertexNormals[l1] = new VertexNormal();
			}
		}
		
		for (int i2 = 0; i2 < triangleCount; i2++) {
			int j2 = facePointA[i2];
			int l2 = facePointB[i2];
			int i3 = facePointC[i2];
			int j3 = vertexX[l2] - vertexX[j2];
			int k3 = vertexY[l2] - vertexY[j2];
			int l3 = vertexZ[l2] - vertexZ[j2];
			int i4 = vertexX[i3] - vertexX[j2];
			int j4 = vertexY[i3] - vertexY[j2];
			int k4 = vertexZ[i3] - vertexZ[j2];
			int l4 = k3 * k4 - j4 * l3;
			int i5 = l3 * i4 - k4 * j3;
			int j5;
			for (j5 = j3 * j4 - i4 * k3; l4 > 8192 || i5 > 8192 || j5 > 8192 || l4 < -8192 || i5 < -8192
					|| j5 < -8192; j5 >>= 1) {
				l4 >>= 1;
				i5 >>= 1;
			}

			int k5 = (int) Math.sqrt(l4 * l4 + i5 * i5 + j5 * j5);
			if (k5 <= 0) {
				k5 = 1;
			}
			l4 = (l4 * 256) / k5;
			i5 = (i5 * 256) / k5;
			j5 = (j5 * 256) / k5;

			short texture_id;
			int type;
			if(faceDrawType != null)
				type = faceDrawType[i2];
			else
				type = 0;

			if(texture == null) {
				texture_id = -1;
			} else {
				texture_id = texture[i2];
			}

			if (faceDrawType == null || (faceDrawType[i2] & 1) == 0) {

				VertexNormal vertexNormal = super.vertexNormals[j2];
				vertexNormal.normalX += l4;
				vertexNormal.normalY += i5;
				vertexNormal.normalZ += j5;
				vertexNormal.magnitude++;
				vertexNormal = super.vertexNormals[l2];
				vertexNormal.normalX += l4;
				vertexNormal.normalY += i5;
				vertexNormal.normalZ += j5;
				vertexNormal.magnitude++;
				vertexNormal = super.vertexNormals[i3];
				vertexNormal.normalX += l4;
				vertexNormal.normalY += i5;
				vertexNormal.normalZ += j5;
				vertexNormal.magnitude++;
			} else {
				if(texture_id != -1) {
					type = 2;
				}
				int l5 = i + (k * l4 + l * i5 + i1 * j5) / (k1 + k1 / 2);
				faceHslA[i2] = light(triangleColours[i2], l5, type);

			}
		}

		if (lightModelNotSure) {
			flatLighting(i, k1, k, l, i1);
		} else {
			alsoVertexNormals = new VertexNormal[verticeCount];
			for (int k2 = 0; k2 < verticeCount; k2++) {
				VertexNormal vertexNormal = super.vertexNormals[k2];
				VertexNormal vertexMerge = alsoVertexNormals[k2] = new VertexNormal();
				vertexMerge.normalX = vertexNormal.normalX;
				vertexMerge.normalY = vertexNormal.normalY;
				vertexMerge.normalZ = vertexNormal.normalZ;
				vertexMerge.magnitude = vertexNormal.magnitude;
			}
		}
		
		if (lightModelNotSure) {
			calculateDistances();
		} else {
			calculateVertexData();
		}
	}
	
	private void calculateDistances() {
		super.modelBaseY = 0;
		maxVertexDistanceXZPlane = 0;
		maximumYVertex = 0;
		for (int i = 0; i < verticeCount; i++) {
			int x = vertexX[i];
			int y = vertexY[i];
			int z = vertexZ[i];
			if (-y > super.modelBaseY) {
				super.modelBaseY = -y;
			}
			if (y > maximumYVertex) {
				maximumYVertex = y;
			}
			int sqDistance = x * x + z * z;
			if (sqDistance > maxVertexDistanceXZPlane) {
				maxVertexDistanceXZPlane = sqDistance;
			}
		}
		maxVertexDistanceXZPlane = (int) (Math.sqrt(maxVertexDistanceXZPlane) + 0.98999999999999999D);
		diagonal3DAboveOrigin = (int) (Math.sqrt(maxVertexDistanceXZPlane * maxVertexDistanceXZPlane + super.modelBaseY * super.modelBaseY) + 0.98999999999999999D);
		maxRenderDepth = diagonal3DAboveOrigin + (int) (Math.sqrt(maxVertexDistanceXZPlane * maxVertexDistanceXZPlane + maximumYVertex * maximumYVertex) + 0.98999999999999999D);
	}
	
	private void calculateVertexData() {
		super.modelBaseY = 0;
		maxVertexDistanceXZPlane = 0;
		maximumYVertex = 0;
		minimumXVertex = 999999;
		maximumXVertex = -999999;
		maximumZVertex = -99999;
		minimumZVertex = 99999;
		for (int idx = 0; idx < verticeCount; idx++) {
			int xVertex = vertexX[idx];
			int yVertex = vertexY[idx];
			int zVertex = vertexZ[idx];
			if (xVertex < minimumXVertex) {
				minimumXVertex = xVertex;
			}
			
			if (xVertex > maximumXVertex) {
				maximumXVertex = xVertex;
			}
			
			if (zVertex < minimumZVertex) {
				minimumZVertex = zVertex;
			}
			
			if (zVertex > maximumZVertex) {
				maximumZVertex = zVertex;
			}
			
			if (-yVertex > super.modelBaseY) {
				super.modelBaseY = -yVertex;
			}
			
			if (yVertex > maximumYVertex) {
				maximumYVertex = yVertex;
			}
			int vertexDistanceXZPlane = xVertex * xVertex + zVertex * zVertex;
			if (vertexDistanceXZPlane > maxVertexDistanceXZPlane) {
				maxVertexDistanceXZPlane = vertexDistanceXZPlane;
			}
		}
		maxVertexDistanceXZPlane = (int) Math.sqrt(maxVertexDistanceXZPlane);
		diagonal3DAboveOrigin = (int) Math.sqrt(maxVertexDistanceXZPlane * maxVertexDistanceXZPlane + super.modelBaseY * super.modelBaseY);
		maxRenderDepth = diagonal3DAboveOrigin + (int) Math.sqrt(maxVertexDistanceXZPlane * maxVertexDistanceXZPlane + maximumYVertex * maximumYVertex);
	}

	/*
	 int renderTypeOpcode = first.readUnsignedByte();
		int priorityOpcode = first.readUnsignedByte();
		int alphaOpcode = first.readUnsignedByte();
		int tSkinOpcode = first.readUnsignedByte();
		int vSkinOpcode = first.readUnsignedByte();
		int vertexX = first.readUShort();
		int vertexY = first.readUShort();
		int vertexZ = first.readUShort();
		int vertexPoints = first.readUShort();
		int position = 0;

		int vertexFlagOffset = position;
		position += verticeCount;

		int faceCompressTypeOffset = position;
		position += triangleCount;

		int facePriorityOffset = position;
		if (priorityOpcode == 255) {
			position += triangleCount;
		}

		int tSkinOffset = position;
		if (tSkinOpcode == 1) {
			position += triangleCount;
		}
		
		int renderTypeOffset = position;
		if (renderTypeOpcode == 1) {
			position += triangleCount;
		}
		
		int vSkinOffset = position;
		if (vSkinOpcode == 1) {
			position += verticeCount;
		}
		
		int alphaOffset = position;
		if (alphaOpcode == 1) {
			position += triangleCount;
		}
		
		int pointsOffset = position;
		position += vertexPoints;

		int colorOffset = position;
		position += triangleCount * 2;

		int textureOffset = position;
		position += textureTriangleCount * 6;

		int vertexXOffset = position;
		position += vertexX;

		int vertexYOffset = position;
		position += vertexY;
		
		int vertexZOffset = position;
		position += vertexZ;
	 */
	public static void decodeHeader(byte data[], int j) {
		try {
			if (data == null) {
				ModelHeader modelHeader = modelHeaderCache[j] = new ModelHeader();
				modelHeader.modelVerticeCount = 0;
				modelHeader.modelTriangleCount = 0;
				modelHeader.modelTextureTriangleCount = 0;
				return;
			}
			Buffer stream = new Buffer(data);
			stream.currentPosition = data.length - 18;
			ModelHeader modelHeader = modelHeaderCache[j] = new ModelHeader();
			modelHeader.modelData = data;
			modelHeader.modelVerticeCount = stream.readUShort();
			modelHeader.modelTriangleCount = stream.readUShort();
			modelHeader.modelTextureTriangleCount = stream.readUnsignedByte();
			int k = stream.readUnsignedByte();
			int l = stream.readUnsignedByte();
			int i1 = stream.readUnsignedByte();
			int j1 = stream.readUnsignedByte();
			int k1 = stream.readUnsignedByte();
			int l1 = stream.readUShort();
			int i2 = stream.readUShort();
			int j2 = stream.readUShort();
			int k2 = stream.readUShort();
			int l2 = 0;
			modelHeader.vertexModOffset = l2;
			l2 += modelHeader.modelVerticeCount;
			modelHeader.triMeshLinkOffset = l2;
			l2 += modelHeader.modelTriangleCount;
			modelHeader.facePriorityBasePos = l2;
			if (l == 255) {
				l2 += modelHeader.modelTriangleCount;
			} else {
				modelHeader.facePriorityBasePos = -l - 1;
			}
			modelHeader.tskinBasepos = l2;
			if (j1 == 1) {
				l2 += modelHeader.modelTriangleCount;
			} else {
				modelHeader.tskinBasepos = -1;
			}
			modelHeader.drawTypeBasePos = l2;
			if (k == 1) {
				l2 += modelHeader.modelTriangleCount;
			} else {
				modelHeader.drawTypeBasePos = -1;
			}
			modelHeader.vskinBasePos = l2;
			if (k1 == 1) {
				l2 += modelHeader.modelVerticeCount;
			} else {
				modelHeader.vskinBasePos = -1;
			}
			modelHeader.alphaBasepos = l2;
			if (i1 == 1) {
				l2 += modelHeader.modelTriangleCount;
			} else {
				modelHeader.alphaBasepos = -1;
			}
			modelHeader.triVPointOffset = l2;
			l2 += k2;
			modelHeader.triColourOffset = l2;
			l2 += modelHeader.modelTriangleCount * 2;
			modelHeader.textureInfoBasePos = l2;
			l2 += modelHeader.modelTextureTriangleCount * 6;
			modelHeader.vertexXOffset = l2;
			l2 += l1;
			modelHeader.vertexYOffset = l2;
			l2 += i2;
			modelHeader.vertexZOffset = l2;
			l2 += j2;
		} catch (Exception _ex) {
			_ex.printStackTrace();
		}
	}
	
	@Override
	public final void renderAtPoint(int i, int j, int k, int l, int i1, int j1, int k1, int l1,
									int i2) {
		int j2 = l1 * i1 - j1 * l >> 16;
		int k2 = k1 * j + j2 * k >> 16;
		int l2 = maxVertexDistanceXZPlane * k >> 16;
		int i3 = k2 + l2;
		if (i3 <= 50 || k2 >= 3500) {
			return;
		}
		int j3 = l1 * l + j1 * i1 >> 16;
		int k3 = j3 - maxVertexDistanceXZPlane << SceneGraph.viewDistance;
		if (k3 / i3 >= Rasterizer2D.viewportCenterX) {
			return;
		}
		int l3 = j3 + maxVertexDistanceXZPlane << SceneGraph.viewDistance;
		if (l3 / i3 <= -Rasterizer2D.viewportCenterX) {
			return;
		}
		int i4 = k1 * k - j2 * j >> 16;
		int j4 = maxVertexDistanceXZPlane * j >> 16;
		int k4 = i4 + j4 << SceneGraph.viewDistance;
		if (k4 / i3 <= -Rasterizer2D.viewportCenterY) {
			return;
		}
		int l4 = j4 + (super.modelBaseY * k >> 16);
		int i5 = i4 - l4 << SceneGraph.viewDistance;
		if (i5 / i3 >= Rasterizer2D.viewportCenterY) {
			return;
		}
		int j5 = l2 + (super.modelBaseY * j >> 16);
		boolean flag = false;
		if (k2 - j5 <= 50) {
			flag = true;
		}
		boolean flag1 = false;
		if (i2 > 0 && aBoolean1684) {
			int k5 = k2 - l2;
			if (k5 <= 50) {
				k5 = 50;
			}
			if (j3 > 0) {
				k3 /= i3;
				l3 /= k5;
			} else {
				l3 /= i3;
				k3 /= k5;
			}
			if (i4 > 0) {
				i5 /= i3;
				k4 /= k5;
			} else {
				k4 /= i3;
				i5 /= k5;
			}
			int i6 = mouseX - Rasterizer3D.originViewX;
			int k6 = mouseY - Rasterizer3D.originViewY;
			if (i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4) {
				if (fitsOnSingleTile) {
					anIntArray1688[anInt1687++] = i2;
				} else {
					flag1 = true;
				}
			}
		}
		int l5 = Rasterizer3D.originViewX;
		int j6 = Rasterizer3D.originViewY;
		int l6 = 0;
		int i7 = 0;
		if (i != 0) {
			l6 = sine[i];
			i7 = cosine[i];
		}
		for (int j7 = 0; j7 < verticeCount; j7++) {
			int k7 = vertexX[j7];
			int l7 = vertexY[j7];
			int i8 = vertexZ[j7];
			if (i != 0) {
				int j8 = i8 * l6 + k7 * i7 >> 16;
				i8 = i8 * i7 - k7 * l6 >> 16;
				k7 = j8;
			}
			k7 += j1;
			l7 += k1;
			i8 += l1;
			int k8 = i8 * l + k7 * i1 >> 16;
			i8 = i8 * i1 - k7 * l >> 16;
			k7 = k8;
			k8 = l7 * k - i8 * j >> 16;
			i8 = l7 * j + i8 * k >> 16;
			l7 = k8;
			projected_vertex_z[j7] = i8 - k2;
			camera_vertex_z[j7] = i8;
			if (i8 >= 50) {
				projected_vertex_x[j7] = l5 + (k7 << SceneGraph.viewDistance) / i8;
				projected_vertex_y[j7] = j6 + (l7 << SceneGraph.viewDistance) / i8;
			} else {
				projected_vertex_x[j7] = -5000;
				flag = true;
			}
			if (flag || textureTriangleCount > 0) {
				anIntArray1668[j7] = k7;
				camera_vertex_y[j7] = l7;
				camera_vertex_x[j7] = i8;
			}
		}

		try {
			method483(flag, flag1, i2);
			return;
		} catch (Exception _ex) {
			return;
		}
	}
	
	private final void method483(boolean flag, boolean flag1, int i) {
		for (int j = 0; j < maxRenderDepth; j++) {
			depthListIndices[j] = 0;
		}

		for (int k = 0; k < triangleCount; k++) {
			if (faceDrawType == null || faceDrawType[k] != -1) {
				int l = facePointA[k];
				int k1 = facePointB[k];
				int j2 = facePointC[k];
				int i3 = projected_vertex_x[l];
				int l3 = projected_vertex_x[k1];
				int k4 = projected_vertex_x[j2];
				if (flag && (i3 == -5000 || l3 == -5000 || k4 == -5000)) {
					outOfReach[k] = true;
					int j5 = (projected_vertex_z[l] + projected_vertex_z[k1] + projected_vertex_z[j2]) / 3
							+ diagonal3DAboveOrigin;
					faceLists[j5][depthListIndices[j5]++] = k;
				} else {
					if (flag1 && method486(mouseX, mouseY, projected_vertex_y[l],
							projected_vertex_y[k1], projected_vertex_y[j2], i3, l3, k4)) {
						anIntArray1688[anInt1687++] = i;
						flag1 = false;
					}
					if ((i3 - l3) * (projected_vertex_y[j2] - projected_vertex_y[k1])
							- (projected_vertex_y[l] - projected_vertex_y[k1]) * (k4 - l3) > 0) {
						outOfReach[k] = false;
						if (i3 < 0 || l3 < 0 || k4 < 0 || i3 > Rasterizer2D.lastX || l3 > Rasterizer2D.lastX
								|| k4 > Rasterizer2D.lastX) {
							hasAnEdgeToRestrict[k] = true;
						} else {
							hasAnEdgeToRestrict[k] = false;
						}
						int k5 = (projected_vertex_z[l] + projected_vertex_z[k1] + projected_vertex_z[j2]) / 3
								+ diagonal3DAboveOrigin;
						faceLists[k5][depthListIndices[k5]++] = k;
					}
				}
			}
		}

		if (faceRenderPriorities == null) {
			for (int i1 = maxRenderDepth - 1; i1 >= 0; i1--) {
				int l1 = depthListIndices[i1];
				if (l1 > 0) {
					int ai[] = faceLists[i1];
					for (int j3 = 0; j3 < l1; j3++) {
						method484(ai[j3]);
					}

				}
			}

			return;
		}
		for (int j1 = 0; j1 < 12; j1++) {
			anIntArray1673[j1] = 0;
			anIntArray1677[j1] = 0;
		}

		for (int i2 = maxRenderDepth - 1; i2 >= 0; i2--) {
			int k2 = depthListIndices[i2];
			if (k2 > 0) {
				int ai1[] = faceLists[i2];
				for (int i4 = 0; i4 < k2; i4++) {
					int l4 = ai1[i4];
					int l5 = faceRenderPriorities[l4];
					int j6 = anIntArray1673[l5]++;
					anIntArrayArray1674[l5][j6] = l4;
					if (l5 < 10) {
						anIntArray1677[l5] += i2;
					} else if (l5 == 10) {
						anIntArray1675[j6] = i2;
					} else {
						anIntArray1676[j6] = i2;
					}
				}

			}
		}

		int l2 = 0;
		if (anIntArray1673[1] > 0 || anIntArray1673[2] > 0) {
			l2 = (anIntArray1677[1] + anIntArray1677[2]) / (anIntArray1673[1] + anIntArray1673[2]);
		}
		int k3 = 0;
		if (anIntArray1673[3] > 0 || anIntArray1673[4] > 0) {
			k3 = (anIntArray1677[3] + anIntArray1677[4]) / (anIntArray1673[3] + anIntArray1673[4]);
		}
		int j4 = 0;
		if (anIntArray1673[6] > 0 || anIntArray1673[8] > 0) {
			j4 = (anIntArray1677[6] + anIntArray1677[8]) / (anIntArray1673[6] + anIntArray1673[8]);
		}
		int i6 = 0;
		int k6 = anIntArray1673[10];
		int ai2[] = anIntArrayArray1674[10];
		int ai3[] = anIntArray1675;
		if (i6 == k6) {
			i6 = 0;
			k6 = anIntArray1673[11];
			ai2 = anIntArrayArray1674[11];
			ai3 = anIntArray1676;
		}
		int i5;
		if (i6 < k6) {
			i5 = ai3[i6];
		} else {
			i5 = -1000;
		}
		for (int l6 = 0; l6 < 10; l6++) {
			while (l6 == 0 && i5 > l2) {
				method484(ai2[i6++]);
				if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
					i6 = 0;
					k6 = anIntArray1673[11];
					ai2 = anIntArrayArray1674[11];
					ai3 = anIntArray1676;
				}
				if (i6 < k6) {
					i5 = ai3[i6];
				} else {
					i5 = -1000;
				}
			}
			while (l6 == 3 && i5 > k3) {
				method484(ai2[i6++]);
				if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
					i6 = 0;
					k6 = anIntArray1673[11];
					ai2 = anIntArrayArray1674[11];
					ai3 = anIntArray1676;
				}
				if (i6 < k6) {
					i5 = ai3[i6];
				} else {
					i5 = -1000;
				}
			}
			while (l6 == 5 && i5 > j4) {
				method484(ai2[i6++]);
				if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
					i6 = 0;
					k6 = anIntArray1673[11];
					ai2 = anIntArrayArray1674[11];
					ai3 = anIntArray1676;
				}
				if (i6 < k6) {
					i5 = ai3[i6];
				} else {
					i5 = -1000;
				}
			}
			int i7 = anIntArray1673[l6];
			int ai4[] = anIntArrayArray1674[l6];
			for (int j7 = 0; j7 < i7; j7++) {
				method484(ai4[j7]);
			}

		}

		while (i5 != -1000) {
			method484(ai2[i6++]);
			if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
				i6 = 0;
				ai2 = anIntArrayArray1674[11];
				k6 = anIntArray1673[11];
				ai3 = anIntArray1676;
			}
			if (i6 < k6) {
				i5 = ai3[i6];
			} else {
				i5 = -1000;
			}
		}

		
	}
	
	//rasterize
	private final void method484(int i) {
		if (outOfReach[i]) {
			method485(i);
			return;
		}
		int j = facePointA[i];
		int k = facePointB[i];
		int l = facePointC[i];
		Rasterizer3D.textureOutOfDrawingBounds = hasAnEdgeToRestrict[i];
		if (faceAlpha == null) {
			Rasterizer3D.alpha = 0;
		} else {
			Rasterizer3D.alpha = faceAlpha[i];
		}
		int type;
		if (faceDrawType == null) {
			type = 0;
		} else {
			type = faceDrawType[i] & 3;
		}

		if(texture != null && texture[i] != -1) {
			int texture_a = j;
			int texture_b = k;
			int texture_c = l;
			if(textureCoordinates != null && textureCoordinates[i] != -1) {
				int coordinate = textureCoordinates[i] & 0xff;
				texture_a = texturesFaceA[coordinate];
				texture_b = texturesFaceB[coordinate];
				texture_c = texturesFaceC[coordinate];
			}
			if(faceHslC[i] == -1 || type == 3) {
				Rasterizer3D.drawTexturedTriangle(
						projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
						projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l],
						faceHslA[i], faceHslA[i], faceHslA[i],
						anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
						camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
						camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
						texture[i],
						camera_vertex_z[j], camera_vertex_z[k], camera_vertex_z[l]);
			} else {
				Rasterizer3D.drawTexturedTriangle(
						projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
						projected_vertex_x[j], projected_vertex_x[k],projected_vertex_x[l],
						faceHslA[i], faceHslB[i], faceHslC[i],
						anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
						camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
						camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
						texture[i],
						camera_vertex_z[j], camera_vertex_z[k], camera_vertex_z[l]);
			}
		} else {
			if (type == 0) {
				Rasterizer3D.drawShadedTriangle(projected_vertex_y[j], projected_vertex_y[k],
						projected_vertex_y[l], projected_vertex_x[j], projected_vertex_x[k],
						projected_vertex_x[l], faceHslA[i], faceHslB[i], faceHslC[i], camera_vertex_z[j],
						camera_vertex_z[k], camera_vertex_z[l]);
				return;
			}
			if (type == 1) {
				Rasterizer3D.drawFlatTriangle(projected_vertex_y[j], projected_vertex_y[k],
						projected_vertex_y[l], projected_vertex_x[j], projected_vertex_x[k],
						projected_vertex_x[l], modelIntArray3[faceHslA[i]], camera_vertex_z[j],
						camera_vertex_z[k], camera_vertex_z[l]);
				;
				return;
			}
		}
	}
	
	//rasterize_rotation
	private final void method485(int i) {
		int j = Rasterizer3D.originViewX;
		int k = Rasterizer3D.originViewY;
		int l = 0;
		int i1 = facePointA[i];
		int j1 = facePointB[i];
		int k1 = facePointC[i];
		int l1 = camera_vertex_x[i1];
		int i2 = camera_vertex_x[j1];
		int j2 = camera_vertex_x[k1];
		if (l1 >= 50) {
			anIntArray1678[l] = projected_vertex_x[i1];
			anIntArray1679[l] = projected_vertex_y[i1];
			anIntArray1680[l++] = faceHslA[i];
		} else {
			int k2 = anIntArray1668[i1];
			int k3 = camera_vertex_y[i1];
			int k4 = faceHslA[i];
			if (j2 >= 50) {
				int k5 = (50 - l1) * modelIntArray4[j2 - l1];
				anIntArray1678[l] = j + (k2 + ((anIntArray1668[k1] - k2) * k5 >> 16) << SceneGraph.viewDistance) / 50;
				anIntArray1679[l] = k + (k3 + ((camera_vertex_y[k1] - k3) * k5 >> 16) << SceneGraph.viewDistance) / 50;
				anIntArray1680[l++] = k4 + ((faceHslC[i] - k4) * k5 >> 16);
			}
			if (i2 >= 50) {
				int l5 = (50 - l1) * modelIntArray4[i2 - l1];
				anIntArray1678[l] = j + (k2 + ((anIntArray1668[j1] - k2) * l5 >> 16) << SceneGraph.viewDistance) / 50;
				anIntArray1679[l] = k + (k3 + ((camera_vertex_y[j1] - k3) * l5 >> 16) << SceneGraph.viewDistance) / 50;
				anIntArray1680[l++] = k4 + ((faceHslB[i] - k4) * l5 >> 16);
			}
		}
		if (i2 >= 50) {
			anIntArray1678[l] = projected_vertex_x[j1];
			anIntArray1679[l] = projected_vertex_y[j1];
			anIntArray1680[l++] = faceHslB[i];
		} else {
			int l2 = anIntArray1668[j1];
			int l3 = camera_vertex_y[j1];
			int l4 = faceHslB[i];
			if (l1 >= 50) {
				int i6 = (50 - i2) * modelIntArray4[l1 - i2];
				anIntArray1678[l] = j + (l2 + ((anIntArray1668[i1] - l2) * i6 >> 16) << SceneGraph.viewDistance) / 50;
				anIntArray1679[l] = k + (l3 + ((camera_vertex_y[i1] - l3) * i6 >> 16) << SceneGraph.viewDistance) / 50;
				anIntArray1680[l++] = l4 + ((faceHslA[i] - l4) * i6 >> 16);
			}
			if (j2 >= 50) {
				int j6 = (50 - i2) * modelIntArray4[j2 - i2];
				anIntArray1678[l] = j + (l2 + ((anIntArray1668[k1] - l2) * j6 >> 16) << SceneGraph.viewDistance) / 50;
				anIntArray1679[l] = k + (l3 + ((camera_vertex_y[k1] - l3) * j6 >> 16) << SceneGraph.viewDistance) / 50;
				anIntArray1680[l++] = l4 + ((faceHslC[i] - l4) * j6 >> 16);
			}
		}
		if (j2 >= 50) {
			anIntArray1678[l] = projected_vertex_x[k1];
			anIntArray1679[l] = projected_vertex_y[k1];
			anIntArray1680[l++] = faceHslC[i];
		} else {
			int i3 = anIntArray1668[k1];
			int i4 = camera_vertex_y[k1];
			int i5 = faceHslC[i];
			if (i2 >= 50) {
				int k6 = (50 - j2) * modelIntArray4[i2 - j2];
				anIntArray1678[l] = j + (i3 + ((anIntArray1668[j1] - i3) * k6 >> 16) << SceneGraph.viewDistance) / 50;
				anIntArray1679[l] = k + (i4 + ((camera_vertex_y[j1] - i4) * k6 >> 16) << SceneGraph.viewDistance) / 50;
				anIntArray1680[l++] = i5 + ((faceHslB[i] - i5) * k6 >> 16);
			}
			if (l1 >= 50) {
				int l6 = (50 - j2) * modelIntArray4[l1 - j2];
				anIntArray1678[l] = j + (i3 + ((anIntArray1668[i1] - i3) * l6 >> 16) << SceneGraph.viewDistance) / 50;
				anIntArray1679[l] = k + (i4 + ((camera_vertex_y[i1] - i4) * l6 >> 16) << SceneGraph.viewDistance) / 50;
				anIntArray1680[l++] = i5 + ((faceHslA[i] - i5) * l6 >> 16);
			}
		}
		int j3 = anIntArray1678[0];
		int j4 = anIntArray1678[1];
		int j5 = anIntArray1678[2];
		int i7 = anIntArray1679[0];
		int j7 = anIntArray1679[1];
		int k7 = anIntArray1679[2];
		if ((j3 - j4) * (k7 - j7) - (i7 - j7) * (j5 - j4) > 0) {
			Rasterizer3D.textureOutOfDrawingBounds = false;
			int texture_a = i1;
			int texture_b = j1;
			int texture_c = k1;
			if (l == 3) {
				if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Rasterizer2D.lastX || j4 > Rasterizer2D.lastX || j5 > Rasterizer2D.lastX)
					Rasterizer3D.textureOutOfDrawingBounds = true;

				int l7;
				if (faceDrawType == null)
					l7 = 0;
				else
					l7 = faceDrawType[i] & 3;

				if(texture != null && texture[i] != -1) {
					if(textureCoordinates != null && textureCoordinates[i] != -1) {
						int coordinate = textureCoordinates[i] & 0xff;
						texture_a = texturesFaceA[coordinate];
						texture_b = texturesFaceB[coordinate];
						texture_c = texturesFaceC[coordinate];
					}
					if(faceHslC[i] == -1) {
						Rasterizer3D.drawTexturedTriangle(
								i7, j7, k7,
								j3, j4, j5,
								faceHslA[i], faceHslA[i], faceHslA[i],
								anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
								camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
								camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
								texture[i],
								camera_vertex_z[i1], camera_vertex_z[j1], camera_vertex_z[k1]);
					} else {
						Rasterizer3D.drawTexturedTriangle(
								i7, j7, k7,
								j3, j4, j5,
								anIntArray1680[0], anIntArray1680[1], anIntArray1680[2],
								anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
								camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
								camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
								texture[i],
								camera_vertex_z[i1], camera_vertex_z[j1], camera_vertex_z[k1]);
					}
				} else {
					if (l7 == 0)
						Rasterizer3D.drawShadedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2], -1f, -1f, -1f);

					else if (l7 == 1)
						Rasterizer3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, modelIntArray3[faceHslA[i]], -1f, -1f, -1f);
				}
			}
			if (l == 4) {
				if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Rasterizer2D.lastX || j4 > Rasterizer2D.lastX || j5 > Rasterizer2D.lastX || anIntArray1678[3] < 0 || anIntArray1678[3] > Rasterizer2D.lastX)
					Rasterizer3D.textureOutOfDrawingBounds = true;
				int type;
				if (faceDrawType == null)
					type = 0;
				else
					type = faceDrawType[i] & 3;

				if(texture != null && texture[i] != -1) {
					if(textureCoordinates != null && textureCoordinates[i] != -1) {
						int coordinate = textureCoordinates[i] & 0xff;
						texture_a = texturesFaceA[coordinate];
						texture_b = texturesFaceB[coordinate];
						texture_c = texturesFaceC[coordinate];
					}
					if(faceHslC[i] == -1) {
						Rasterizer3D.drawTexturedTriangle(
								i7, j7, k7,
								j3, j4, j5,
								faceHslA[i], faceHslA[i], faceHslA[i],
								anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
								camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
								camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
								texture[i],
								camera_vertex_z[i1], camera_vertex_z[j1], camera_vertex_z[k1]);
						Rasterizer3D.drawTexturedTriangle(
								i7, k7, anIntArray1679[3],
								j3, j5, anIntArray1678[3],
								faceHslA[i], faceHslA[i], faceHslA[i],
								anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
								camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
								camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
								texture[i],
								camera_vertex_z[i1], camera_vertex_z[j1], camera_vertex_z[k1]);
					} else {
						Rasterizer3D.drawTexturedTriangle(
								i7, j7, k7,
								j3, j4, j5,
								anIntArray1680[0], anIntArray1680[1], anIntArray1680[2],
								anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
								camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
								camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
								texture[i],
								camera_vertex_z[i1], camera_vertex_z[j1], camera_vertex_z[k1]);
						Rasterizer3D.drawTexturedTriangle(
								i7, k7, anIntArray1679[3],
								j3, j5, anIntArray1678[3],
								anIntArray1680[0], anIntArray1680[2], anIntArray1680[3],
								anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
								camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
								camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
								texture[i],
								camera_vertex_z[i1], camera_vertex_z[j1], camera_vertex_z[k1]);
						return;
					}
				} else {
					if (type == 0) {
						Rasterizer3D.drawShadedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2], -1f, -1f, -1f);
						Rasterizer3D.drawShadedTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], anIntArray1680[0], anIntArray1680[2], anIntArray1680[3], camera_vertex_z[i1], camera_vertex_z[j1], camera_vertex_z[k1]);
						return;
					}
					if (type == 1) {
						int l8 = modelIntArray3[faceHslA[i]];
						Rasterizer3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, l8, -1f, -1f, -1f);
						Rasterizer3D.drawFlatTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], l8, camera_vertex_z[i1], camera_vertex_z[j1], camera_vertex_z[k1]);
						return;
					}
				}
			}
		}
	}

	//entered_clickbox
	private final boolean method486(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
		if (j < k && j < l && j < i1) {
			return false;
		}
		if (j > k && j > l && j > i1) {
			return false;
		}
		if (i < j1 && i < k1 && i < l1) {
			return false;
		}
		return i <= j1 || i <= k1 || i <= l1;
	}
}
