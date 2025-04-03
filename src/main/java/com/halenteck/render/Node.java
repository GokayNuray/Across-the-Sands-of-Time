package com.halenteck.render;

import org.joml.Matrix4f;
import org.lwjgl.assimp.AIMatrix4x4;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AINode;
import org.lwjgl.assimp.AIScene;

import java.util.*;

public class Node {

    private final String name;
    private final Node parent;
    private final Node[] children;
    private final Matrix4f transformation;
    private final Set<String> meshes = new HashSet<>();

    public Node(AINode aiNode, Node parent, Map<String, Node> nodeMap, AIScene aiScene, float scale) {
        this.name = aiNode.mName().dataString();
        this.parent = parent;
        this.children = new Node[aiNode.mNumChildren()];

        this.transformation = assimpToJoml(aiNode.mTransformation(), scale);

        for (int i = 0; i < children.length; i++) {
            children[i] = new Node(AINode.create(Objects.requireNonNull(aiNode.mChildren()).get(i)), this, nodeMap, aiScene, scale);
        }


        int numMeshes = aiNode.mNumMeshes();
        for (int i = 0; i < numMeshes; i++) {
            AIMesh aiMesh = AIMesh.create(aiScene.mMeshes().get(aiNode.mMeshes().get(i)));
            meshes.add(aiMesh.mName().dataString());
        }

        nodeMap.put(name, this);
    }

    @Override
    public String toString() {
        return "Node{" +
                "transformation=\n" + transformation +
                ", parent=" + (parent == null ? "null" : parent.name) +
                ", meshes=" + meshes +
                "}\n";
    }

    public Matrix4f assimpToJoml(AIMatrix4x4 aiMatrix4x4, float scale) {
        return new Matrix4f(
                aiMatrix4x4.a1(), aiMatrix4x4.b1(), aiMatrix4x4.c1(), aiMatrix4x4.d1(),
                aiMatrix4x4.a2(), aiMatrix4x4.b2(), aiMatrix4x4.c2(), aiMatrix4x4.d2(),
                aiMatrix4x4.a3(), aiMatrix4x4.b3(), aiMatrix4x4.c3(), aiMatrix4x4.d3(),
                aiMatrix4x4.a4() * scale, aiMatrix4x4.b4() * scale, aiMatrix4x4.c4() * scale, aiMatrix4x4.d4()
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

    public void addEntity(Entity entity) {
        entity.getRenderables().forEach(renderable -> {
            meshes.add(renderable.getName());
        });
    }
}
