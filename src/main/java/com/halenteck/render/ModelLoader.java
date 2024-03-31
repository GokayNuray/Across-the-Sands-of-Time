package com.halenteck.render;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.*;

public final class ModelLoader {

    private static int FLAGS = aiProcess_GenSmoothNormals | aiProcess_JoinIdenticalVertices |
            aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_CalcTangentSpace | aiProcess_LimitBoneWeights |
            aiProcess_PreTransformVertices;

    private ModelLoader() {
    }

    public static Renderable loadModel(String filePath) {
        return loadModel(filePath, FLAGS);
    }

    public static Renderable loadModel(String filePath, int flags) {

        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }

        AIScene aiScene = aiImportFile(filePath, flags);
        if (aiScene == null) {
            throw new RuntimeException("Error loading model: " + filePath);
        }

        int numMaterials = aiScene.mNumMaterials();
        for (int i = 0; i < numMaterials; i++) {
            AIMaterial aiMaterial = AIMaterial.create(aiScene.mMaterials().get(i));

            AIString path = AIString.calloc();
            aiGetMaterialTexture(aiMaterial, aiTextureType_DIFFUSE, 0, path, (IntBuffer) null, null, null, null, null, null);
            System.out.println(path.dataString());
        }

        int numMeshes = aiScene.mNumMeshes();
        PointerBuffer aiMeshes = aiScene.mMeshes();
        System.out.println("Number of meshes: " + numMeshes);
        for (int i = 0; i < numMeshes; i++) {
            AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));
            int numVertices = aiMesh.mNumVertices();
            System.out.println("Number of vertices: " + numVertices);

            AIVector3D.Buffer aiVertices = aiMesh.mVertices();
            float[] vertices = new float[numVertices * 3];
            for (int j = 0; j < numVertices; j++) {
                AIVector3D aiVertex = aiVertices.get();
                vertices[j * 3] = aiVertex.x() * 0.1f;
                vertices[j * 3 + 1] = aiVertex.y() * 0.1f;
                vertices[j * 3 + 2] = aiVertex.z() * 0.1f;
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
            return new Renderable(vertices, colors, texCoords, indices, "/test/elgato/Cat_diffuse.jpg");

        }

        return null;
    }

}
