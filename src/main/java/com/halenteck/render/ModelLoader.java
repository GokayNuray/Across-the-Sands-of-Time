package com.halenteck.render;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.lwjgl.assimp.Assimp.*;

public final class ModelLoader {

    private static final int FLAGS = aiProcess_GenSmoothNormals | aiProcess_JoinIdenticalVertices |
            aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_CalcTangentSpace | aiProcess_LimitBoneWeights;

    private static final boolean isJar;

    static {
        String me = ModelLoader.class.getName().replace(".", "/") + ".class";
        URL dirURL = ModelLoader.class.getClassLoader().getResource(me);
        isJar = dirURL.getPath().endsWith(".jar!/" + me);
    }

    private ModelLoader() {
    }

    public static List<Renderable> loadModel(String filePath) {
        return loadModel(filePath, FLAGS, 1);
    }

    public static List<Renderable> loadModel(String filePath, float scale) {
        return loadModel(filePath, FLAGS, scale);
    }

    public static List<Renderable> loadModel(String filePath, int flags, float scale) {

        List<Renderable> renderables = new ArrayList<>();

        String parentFolder = isJar ? exportParentFolderToDiskAndReturnResourcesPath(filePath) : "src/main/resources/";
        String target = parentFolder + filePath;
        AIScene aiScene = aiImportFile(target, flags);
        if (aiScene == null) {
            throw new RuntimeException("Error loading model: " + target + " " + aiGetErrorString());
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
            for (int j = 0; j < numVertices; j++) {
                AIVector3D aiVertex = aiVertices.get();
                vertices[j * 3] = aiVertex.x() * scale;
                vertices[j * 3 + 1] = aiVertex.y() * scale;
                vertices[j * 3 + 2] = aiVertex.z() * scale;
            }

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
            String textureName = texturePaths.get(aiMesh.mMaterialIndex());
            if (textureName.isEmpty()) {
                System.out.println("No texture found for mesh: " + aiMesh.mName().dataString());
                textureName = "/whiteSquare.png";
            }
            String texturePath = parentFolder + filePath.substring(0, filePath.lastIndexOf("/") + 1) + textureName;

            renderables.add(new Renderable(aiMesh.mName().dataString(), vertices, colors, texCoords, indices, texturePath));
        }

        return renderables;
    }

    public static Map<String, Animation> loadAnimations(String filePath, float scale) {
        String target = (isJar ? exportParentFolderToDiskAndReturnResourcesPath(filePath) : "src/main/resources/") + filePath;
        AIScene aiScene = aiImportFile(target, 0);
        if (aiScene == null) {
            throw new RuntimeException("Error loading model: " + target + " " + aiGetErrorString());
        }

        AINode aiRootNode = aiScene.mRootNode();
        if (aiRootNode == null) {
            throw new RuntimeException("Error loading root node: " + target + " " + aiGetErrorString());
        }
        Map<String, Node> nodeMap = new HashMap<>();
        Node rootNode = new Node(aiRootNode, null, nodeMap, aiScene, scale);

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

                Vector3f[] positions = new Vector3f[numPositionKeys];
                double[] times = new double[numPositionKeys];
                for (int k = 0; k < numPositionKeys; k++) {
                    AIVectorKey aiVectorKey = aiNodeAnim.mPositionKeys().get(k);
                    positions[k] = new Vector3f(aiVectorKey.mValue().x(), aiVectorKey.mValue().y(), aiVectorKey.mValue().z());
                    positions[k].mul(scale);
                    times[k] = aiVectorKey.mTime() * 1000;
                }

                Quaternionf rotations[] = new Quaternionf[numRotationKeys];
                for (int k = 0; k < numRotationKeys; k++) {
                    AIQuatKey aiQuatKey = aiNodeAnim.mRotationKeys().get(k);
                    rotations[k] = new Quaternionf(aiQuatKey.mValue().x(), aiQuatKey.mValue().y(), aiQuatKey.mValue().z(), aiQuatKey.mValue().w());
                }

                Vector3f[] scalings = new Vector3f[numScalingKeys];
                for (int k = 0; k < numScalingKeys; k++) {
                    AIVectorKey aiVectorKey = aiNodeAnim.mScalingKeys().get(k);
                    scalings[k] = new Vector3f(aiVectorKey.mValue().x(), aiVectorKey.mValue().y(), aiVectorKey.mValue().z());
                }

                assert numPositionKeys == numRotationKeys && numRotationKeys == numScalingKeys : "Number of keys must be the same else idk what to do";

                animationNodes[j] = new Animation.AnimationNode(nodeName, j, positions, rotations, scalings, times);
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

    public static String exportParentFolderToDiskAndReturnResourcesPath(String filePath) {
        //copy everything inside the folder of the model to a temporary directory
        String me = ModelLoader.class.getName().replace(".", "/") + ".class";
        URL dirURL = ModelLoader.class.getClassLoader().getResource(me);
        String dirPath = URLDecoder.decode(dirURL.getPath(), StandardCharsets.UTF_8);
        String jarPath = dirPath.substring(5, dirPath.lastIndexOf("!"));
        JarFile jar = null;
        try {
            jar = new JarFile(jarPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String jarParent = jarPath.substring(0, jarPath.lastIndexOf("/") + 1);
        String resourcePath = jarParent + "resources/";
        Enumeration<JarEntry> entries = jar.entries();
        String filePath2 = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (name.startsWith(filePath2)) {
                InputStream is = ModelLoader.class.getClassLoader().getResourceAsStream(name);
                if (is == null) {
                    System.out.println(name + " is null");
                    continue;
                }
                File file = new File(resourcePath + name);
                if (entry.isDirectory()) {
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                } else {
                    if (!file.exists()) {
                        try {
                            file.getParentFile().mkdirs();
                            file.createNewFile();
                            FileOutputStream fos = new FileOutputStream(file);
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, bytesRead);
                            }
                            fos.close();
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
        return resourcePath;//TODO test this on windows
    }

    public static String getResourcePathOnDisk(String filePath) {
        if (!isJar) return "src/main/resources/" + filePath;
        return exportFileToDiskAndReturnPath(filePath);
    }

    private static String exportFileToDiskAndReturnPath(String filePath) {
        //copy everything inside the folder of the model to a temporary directory
        String me = ModelLoader.class.getName().replace(".", "/") + ".class";
        URL dirURL = ModelLoader.class.getClassLoader().getResource(me);
        String dirPath = URLDecoder.decode(dirURL.getPath(), StandardCharsets.UTF_8);
        String jarPath = dirPath.substring(5, dirPath.lastIndexOf("!"));
        String jarParent = jarPath.substring(0, jarPath.lastIndexOf("/") + 1);
        String resourcePath = jarParent + "resources/";
        InputStream is = ModelLoader.class.getClassLoader().getResourceAsStream(filePath);
        if (is == null) {
            System.out.println(filePath + " is null");
        }
        File file = new File(resourcePath + filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resourcePath.substring(1) + filePath;
    }
}
