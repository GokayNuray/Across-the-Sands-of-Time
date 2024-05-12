package com.halenteck.render;

import org.joml.Matrix4f;
import org.lwjgl.assimp.AIMatrix4x4;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AINode;
import org.lwjgl.assimp.AIScene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Node {

    private String name;
    private Node parent;
    private Node[] children;
    private Matrix4f transformation;
    private List<String> meshes = new ArrayList<>();

    public Node(AINode aiNode, Node parent, Map<String, Node> nodeMap, AIScene aiScene) {
        this.name = aiNode.mName().dataString();
        this.parent = parent;
        this.children = new Node[aiNode.mNumChildren()];

        this.transformation = assimpToJoml(aiNode.mTransformation());

        for (int i = 0; i < children.length; i++) {
            children[i] = new Node(AINode.create(aiNode.mChildren().get(i)), this, nodeMap, aiScene);
        }


        int numMeshes = aiNode.mNumMeshes();
        for (int i = 0; i < numMeshes; i++) {
            AIMesh aiMesh = AIMesh.create(aiScene.mMeshes().get(aiNode.mMeshes().get(i)));
            meshes.add(aiMesh.mName().dataString());
        }

        nodeMap.put(name, this);
    }

    public Matrix4f assimpToJoml(AIMatrix4x4 aiMatrix4x4) {
        return new Matrix4f(
                aiMatrix4x4.a1(), aiMatrix4x4.b1(), aiMatrix4x4.c1(), aiMatrix4x4.d1(),
                aiMatrix4x4.a2(), aiMatrix4x4.b2(), aiMatrix4x4.c2(), aiMatrix4x4.d2(),
                aiMatrix4x4.a3(), aiMatrix4x4.b3(), aiMatrix4x4.c3(), aiMatrix4x4.d3(),
                aiMatrix4x4.a4(), aiMatrix4x4.b4(), aiMatrix4x4.c4(), aiMatrix4x4.d4()
        );
    }

    public Matrix4f getTransformation() {
        return transformation;
    }

    public record MeshAndNode(String mesh, Node node) {
    }

    public List<MeshAndNode> getMeshes() {
        List<MeshAndNode> meshAndNodes = new ArrayList<>();
        for (String mesh : meshes) {
            meshAndNodes.add(new MeshAndNode(mesh, this));
        }
        for (Node child : children) {
            meshAndNodes.addAll(child.getMeshes());
        }
        return meshAndNodes;
    }
}
