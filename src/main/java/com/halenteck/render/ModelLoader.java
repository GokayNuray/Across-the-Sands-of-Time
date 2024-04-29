package com.halenteck.render;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.*;

public final class ModelLoader {

    private static final int FLAGS = aiProcess_GenSmoothNormals | aiProcess_JoinIdenticalVertices |
            aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_CalcTangentSpace | aiProcess_LimitBoneWeights |
            aiProcess_PreTransformVertices;

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
            throw new RuntimeException("Error loading model: " + filePath);
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
            String texturePath = (filePath.substring(0, filePath.lastIndexOf("/") + 1) + texturePaths.get(aiMesh.mMaterialIndex())).substring("src/main/resources/".length() - 1);
            renderables.add(new Renderable(vertices, colors, texCoords, indices, texturePath));

        }

        return renderables;
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
