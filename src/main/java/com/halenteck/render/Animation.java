package com.halenteck.render;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animation {

    static class AnimationNode {
        private final String name;
        private final int id;
        private final float[][] transformation;

        public AnimationNode(String name, int id, float[][] positions, float[][] rotations, float[][] scalings) {
            this.name = name;
            this.id = id;

            this.transformation = new float[positions.length][16];
            for (int i = 0; i < positions.length; i++) {
                float[] position = positions[i];
                float[] rotation = rotations[i];
                float[] scaling = scalings[i];

                Matrix4f matrix = new Matrix4f().identity();
                System.out.println(position[0] + ", " + position[1] + ", " + position[2]);
                matrix.translate(position[0], position[1], position[2]);
                Quaternionf quaternion = new Quaternionf(rotation[0], rotation[1], rotation[2], rotation[3]);
                matrix.rotate(quaternion);
                matrix.scale(scaling[0], scaling[1], scaling[2]);

                matrix.get(transformation[i]);
            }
        }
    }

    private final String name;
    private final double duration;
    private final double ticksPerSecond;
    private final AnimationNode[] animationNodes;
    private final Map<String, Node> nodeMap;

    private Map<String, Renderable> renderables;
    private double time;

    public Animation(String name, double duration, double ticksPerSecond, AnimationNode[] animationNodes, Map<String, Node> nodeMap) {
        this.name = name;
        this.duration = duration;
        this.ticksPerSecond = ticksPerSecond;
        this.animationNodes = animationNodes;
        this.nodeMap = nodeMap;
    }

    public Animation(Animation animation, Entity entity) {
        System.out.println("Cloning animation " + animation.name);
        this.name = animation.name;
        this.duration = animation.duration;
        this.ticksPerSecond = animation.ticksPerSecond;
        this.animationNodes = animation.animationNodes;
        this.nodeMap = animation.nodeMap;

        List<Renderable> renderables = new ArrayList<>();
        entity.getRenderables(renderables);
        this.renderables = new HashMap<>();
        for (Renderable renderable : renderables) {
            this.renderables.put(renderable.getName(), renderable);
        }
        System.out.println("Cloned animation " + animation.name);
    }

    public void start() {
        System.out.println("Starting animation " + name);
        time = System.currentTimeMillis();

        update(0);

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 / 24);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                update();
            }
        }).start();
    }

    public void update() {
        double currentTime = System.currentTimeMillis();
        double deltaTime = currentTime - time;

        deltaTime %= duration;
        double ratio = deltaTime / duration;

        update(ratio);
    }

    public void update(double indexRatio) {
        Map<String, Matrix4f> transformations = new HashMap<>();
        for (AnimationNode animationNode : animationNodes) {
            int index = (int) (indexRatio * animationNode.transformation.length);
            float[] transformationArr = animationNode.transformation[index];
            Matrix4f transformation = new Matrix4f(
                    transformationArr[0], transformationArr[1], transformationArr[2], transformationArr[3],
                    transformationArr[4], transformationArr[5], transformationArr[6], transformationArr[7],
                    transformationArr[8], transformationArr[9], transformationArr[10], transformationArr[11],
                    transformationArr[12], transformationArr[13], transformationArr[14], transformationArr[15]);
            //System.out.println("Transformation: " + transformation);
            if (transformations.containsKey(animationNode.name)) {
                transformations.get(animationNode.name).mul(transformation);
            } else {
                transformations.put(animationNode.name, transformation);
            }

            Node node = nodeMap.get(animationNode.name);

            List<Node.MeshAndNode> meshes = node.getMeshes();
            for (Node.MeshAndNode meshAndNode : meshes) {
                Renderable renderable = renderables.get(meshAndNode.mesh());
                if (renderable != null) {
                    Matrix4f currentTransformation = new Matrix4f(transformations.get(animationNode.name));
                    renderable.setNodeTransformation(node.getTransformation());
                    renderable.setAnimationTransformation(currentTransformation);
                }
            }
        }
    }

}
