package com.halenteck.render;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.assimp.Assimp.*;

public final class ModelLoader {

    private static final int FLAGS = aiProcess_GenSmoothNormals | aiProcess_JoinIdenticalVertices |
            aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_CalcTangentSpace | aiProcess_LimitBoneWeights;

    private ModelLoader() {
    }

    public static List<Renderable> loadModel(String filePath) {
        return loadModel(filePath, FLAGS);
    }

    public static List<Renderable> loadModel(String filePath, int flags) {

        List<Renderable> renderables = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }

        AIScene aiScene = aiImportFile(filePath, flags);
        if (aiScene == null) {
            throw new RuntimeException("Error loading model: " + filePath + " " + aiGetErrorString());
        }

        List<String> texturePaths = new ArrayList<>();
        int numMaterials = aiScene.mNumMaterials();
        for (int i = 0; i < numMaterials; i++) {
            AIMaterial aiMaterial = AIMaterial.create(aiScene.mMaterials().get(i));

            AIString path = AIString.calloc();
            aiGetMaterialTexture(aiMaterial, aiTextureType_DIFFUSE, 0, path, (IntBuffer) null, null, null, null, null, null);
            String texturePath = path.dataString();
            texturePaths.add(texturePath);
        }

        int numMeshes = aiScene.mNumMeshes();
        PointerBuffer aiMeshes = aiScene.mMeshes();
        for (int i = 0; i < numMeshes; i++) {
            AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));
            int numVertices = aiMesh.mNumVertices();

            AIVector3D.Buffer aiVertices = aiMesh.mVertices();
            float[] vertices = new float[numVertices * 3];
            if (filePath.contains("dae")) {
                System.out.println("mesh: " + aiMesh.mName().dataString());
            }
            for (int j = 0; j < numVertices; j++) {
                AIVector3D aiVertex = aiVertices.get();
                vertices[j * 3] = aiVertex.x();
                vertices[j * 3 + 1] = aiVertex.y();
                vertices[j * 3 + 2] = aiVertex.z();
                if (filePath.contains("dae")) {
                    System.out.println("vertex: " + vertices[j * 3] + ", " + vertices[j * 3 + 1] + ", " + vertices[j * 3 + 2]);
                }
            }
            System.out.println();

            AIVector3D.Buffer aiTexCoords = aiMesh.mTextureCoords(0);
            float[] texCoords = new float[numVertices * 2];
            if (aiTexCoords != null) {
                for (int j = 0; j < numVertices; j++) {
                    AIVector3D aiTexCoord = aiTexCoords.get();
                    texCoords[j * 2] = aiTexCoord.x();
                    texCoords[j * 2 + 1] = 1 - aiTexCoord.y();
                }
            }

            List<Integer> indicesList = new ArrayList<>();
            int numFaces = aiMesh.mNumFaces();
            AIFace.Buffer aiFaces = aiMesh.mFaces();
            for (int j = 0; j < numFaces; j++) {
                AIFace aiFace = aiFaces.get();
                IntBuffer buffer = aiFace.mIndices();
                while (buffer.hasRemaining()) {
                    indicesList.add(buffer.get());
                }
            }
            int[] indices = indicesList.stream().mapToInt(Integer::intValue).toArray();

            float[] colors = new float[vertices.length / 3 * 4];
            for (int j = 0; j < colors.length; j += 4) {
                colors[j] = 1.0f;
                colors[j + 1] = 1.0f;
                colors[j + 2] = 1.0f;
                colors[j + 3] = 1.0f;
            }
            String texturePath = (filePath.substring(0, filePath.lastIndexOf("/") + 1) + texturePaths.get(aiMesh.mMaterialIndex())).substring("src/main/resources/".length() - 1);
            renderables.add(new Renderable(aiMesh.mName().dataString(), vertices, colors, texCoords, indices, texturePath));
        }

        return renderables;
    }

    public static Map<String, Animation> loadAnimations(String filePath) {
        AIScene aiScene = aiImportFile(filePath, 0);

        AINode aiRootNode = aiScene.mRootNode();
        Map<String, Node> nodeMap = new HashMap<>();
        Node rootNode = new Node(aiRootNode, null, nodeMap, aiScene);

        for (int i = 0; i < aiRootNode.mNumChildren(); i++) {
            AINode aiNode = AINode.create(aiRootNode.mChildren().get(i));
            for (int j = 0; j < aiNode.mNumChildren(); j++) {
                AINode aiNode2 = AINode.create(aiNode.mChildren().get(j));
                IntBuffer aiMeshes2 = aiNode2.mMeshes();
                int mesh = aiMeshes2.get();
                AIMesh aiMesh = AIMesh.create(aiScene.mMeshes().get(mesh));
            }
        }

        Map<String, Animation> animationMap = new HashMap<>();

        for (int i = 0; i < aiScene.mNumAnimations(); i++) {

            AIAnimation aiAnimation = AIAnimation.create(aiScene.mAnimations().get(i));
            String name = aiAnimation.mName().dataString();

            Animation.AnimationNode[] animationNodes = new Animation.AnimationNode[aiAnimation.mNumChannels()];

            for (int j = 0; j < aiAnimation.mNumChannels(); j++) {
                AINodeAnim aiNodeAnim = AINodeAnim.create(aiAnimation.mChannels().get(j));
                String nodeName = aiNodeAnim.mNodeName().dataString();
                int numPositionKeys = aiNodeAnim.mNumPositionKeys();
                int numRotationKeys = aiNodeAnim.mNumRotationKeys();
                int numScalingKeys = aiNodeAnim.mNumScalingKeys();

                float[][] positions = new float[numPositionKeys][];
                for (int k = 0; k < numPositionKeys; k++) {
                    AIVectorKey aiVectorKey = aiNodeAnim.mPositionKeys().get(k);
                    positions[k] = new float[]{aiVectorKey.mValue().x(), aiVectorKey.mValue().y(), aiVectorKey.mValue().z()};
                }

                float[][] rotations = new float[numRotationKeys][];
                for (int k = 0; k < numRotationKeys; k++) {
                    AIQuatKey aiQuatKey = aiNodeAnim.mRotationKeys().get(k);
                    rotations[k] = new float[]{aiQuatKey.mValue().x(), aiQuatKey.mValue().y(), aiQuatKey.mValue().z(), aiQuatKey.mValue().w()};
                }

                float[][] scalings = new float[numScalingKeys][];
                for (int k = 0; k < numScalingKeys; k++) {
                    AIVectorKey aiVectorKey = aiNodeAnim.mScalingKeys().get(k);
                    scalings[k] = new float[]{aiVectorKey.mValue().x(), aiVectorKey.mValue().y(), aiVectorKey.mValue().z()};
                }

                animationNodes[j] = new Animation.AnimationNode(nodeName, j, positions, rotations, scalings);
            }

            animationMap.put(name, new Animation(aiAnimation.mName().dataString(), aiAnimation.mDuration() * 1000, aiAnimation.mTicksPerSecond(), animationNodes, nodeMap));
        }
        return animationMap;
    }

    public static Renderable createRectangularPrism(float[] start, float[] end) {
        float[] vertices = {
                start[0], start[1], start[2],
                end[0], start[1], start[2],
                end[0], end[1], start[2],
                start[0], end[1], start[2],
                start[0], start[1], end[2],
                end[0], start[1], end[2],
                end[0], end[1], end[2],
                start[0], end[1], end[2]
        };

        float[] colors = {
                1, 0, 0, 1,
                0, 1, 0, 1,
                0, 0, 1, 1,
                1, 1, 0, 1,
                1, 0, 1, 1,
                0, 1, 1, 1,
                1, 1, 1, 1,
                0, 0, 0, 1
        };

        int[] indices = {
                0, 1, 2,
                0, 2, 3,
                0, 1, 5,
                0, 5, 4,
                1, 2, 6,
                1, 6, 5,
                2, 3, 7,
                2, 7, 6,
                3, 0, 4,
                3, 4, 7,
                4, 5, 6,
                4, 6, 7
        };

        return new Renderable(vertices, colors, indices);
    }
}
