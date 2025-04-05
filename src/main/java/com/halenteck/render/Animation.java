package com.halenteck.render;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Animation {

    static class AnimationNode {
        private final String name;
        private final int id;
        private final Vector3f[] positions;
        private final Quaternionf[] rotations;
        private final Vector3f[] scalings;
        private final double[] times;

        public AnimationNode(String name, int id, Vector3f[] positions, Quaternionf[] rotations, Vector3f[] scalings, double[] times) {
            this.name = name;
            this.id = id;

            this.positions = positions;
            this.rotations = rotations;
            this.scalings = scalings;

            this.times = times;
        }

        /**
         * Get the interpolated transformation for the given time
         */
        public Matrix4f getTransformation(double time) {
            int index;
            for (index = 0; index < times.length; index++) {
                if (times[index] > time) {
                    break;
                }
                if (times[index] == time) {
                    return calculateTransformation(positions[index], rotations[index], scalings[index]);
                }
                if (index == times.length - 1) {
                    return calculateTransformation(positions[index], rotations[index], scalings[index]);
                }
            }
            int nextIndex = index;
            int prevIndex = index == 0 ? 0 : index - 1;
            double deltaTime = times[nextIndex] - times[prevIndex];
            double factor = (time - times[prevIndex]) / deltaTime;

            Vector3f position = new Vector3f(positions[prevIndex]);
            Vector3f nextPosition = new Vector3f(positions[nextIndex]);

            Quaternionf rotation = new Quaternionf(rotations[prevIndex]);
            Quaternionf nextRotation = new Quaternionf(rotations[nextIndex]);

            Vector3f scaling = new Vector3f(scalings[prevIndex]);
            Vector3f nextScaling = new Vector3f(scalings[nextIndex]);

            position.lerp(nextPosition, (float) factor);
            rotation.slerp(nextRotation, (float) factor);
            scaling.lerp(nextScaling, (float) factor);

            return calculateTransformation(position, rotation, scaling);
        }

        @Override
        public String toString() {
            return name + " " + id + " " + Arrays.toString(times);
        }

        public static Matrix4f calculateTransformation(Vector3f position, Quaternionf rotation, Vector3f scaling) {
            Matrix4f matrix = new Matrix4f().identity();
            matrix.translate(position);
            matrix.rotate(rotation);
            matrix.scale(scaling);
            return matrix;
        }
    }

    private final String name;
    private final double duration;
    private final double ticksPerSecond;
    private final AnimationNode[] animationNodes;
    private final Map<String, Node> nodeMap;

    private Map<String, Renderable> renderables;
    private long time;

    private boolean started = false;
    private boolean continuing = false;

    Animation(String name, double duration, double ticksPerSecond, AnimationNode[] animationNodes, Map<String, Node> nodeMap) {
        this.name = name;
        this.duration = duration;
        this.ticksPerSecond = ticksPerSecond;
        this.animationNodes = animationNodes;
        this.nodeMap = nodeMap;
    }

    public Animation(Animation animation, Entity entity) {
        this.name = animation.name;
        this.duration = animation.duration;
        this.ticksPerSecond = animation.ticksPerSecond;
        this.animationNodes = animation.animationNodes;
        this.nodeMap = animation.nodeMap;

        List<Renderable> renderables = entity.getRenderables();
        this.renderables = new HashMap<>();
        for (Renderable renderable : renderables) {
            this.renderables.put(renderable.getName(), renderable);
        }
    }

    public void startOnce() {
        if (started) {
            continuing = true;
            return;
        }
        started = true;

        new Thread(() -> {
            do {
                time = System.currentTimeMillis();
                continuing = false;
                long deltaTime = 0;
                while (deltaTime <= duration) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    update(deltaTime);
                    deltaTime = System.currentTimeMillis() - time;
                }
            } while (continuing);
            //applyTransformations(animationNode -> null);
            started = false;
        }).start();
    }

    public void startOnceReverse() {
        if (started) {
            continuing = true;
            return;
        }
        started = true;

        new Thread(() -> {
            do {
                time = System.currentTimeMillis();
                continuing = false;
                long deltaTime = 0;
                while (deltaTime <= duration) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    update((long) (duration - deltaTime));
                    deltaTime = System.currentTimeMillis() - time;
                }
            } while (continuing);
            //applyTransformations(animationNode -> null);
            started = false;
        }).start();
    }

    public void start() {
        if (started) {
            return;
        }
        started = true;
        continuing = true;

        new Thread(() -> {
            time = System.currentTimeMillis();
            long deltaTime = 0;
            while (continuing) {
                long currentTime = System.currentTimeMillis();
                deltaTime = currentTime - time;

                deltaTime %= (long) duration;

                update(deltaTime);
            }
            final long STOP_DURATION = 200; //TODO make this a parameter
            Map<AnimationNode, Function<Float, Matrix4f>> transformationResults = new HashMap<>();
            for (AnimationNode animationNode : animationNodes) {
                Matrix4f transformation = animationNode.getTransformation(deltaTime);
                Vector3f position = new Vector3f();
                Quaternionf rotation = new Quaternionf();
                Vector3f scaling = new Vector3f();
                transformation.getTranslation(position);
                transformation.getUnnormalizedRotation(rotation);
                transformation.getScale(scaling);

                Matrix4f endTransformation = nodeMap.get(animationNode.name).getTransformation();
                endTransformation = animationNode.getTransformation(100000);
                Vector3f endPosition = new Vector3f();
                Quaternionf endRotation = new Quaternionf();
                Vector3f endScaling = new Vector3f();
                endTransformation.getTranslation(endPosition);
                endTransformation.getUnnormalizedRotation(endRotation);
                endTransformation.getScale(endScaling);

                transformationResults.put(animationNode, (time) -> {
                    Vector3f posCopy = new Vector3f(position);
                    Quaternionf rotCopy = new Quaternionf(rotation);
                    Vector3f scaleCopy = new Vector3f(scaling);
                    posCopy.lerp(endPosition, time);
                    rotCopy.slerp(endRotation, time);
                    scaleCopy.lerp(endScaling, time);
                    return AnimationNode.calculateTransformation(posCopy, rotCopy, scaleCopy);
                });
            }
            time = System.currentTimeMillis();
            deltaTime = 0;
            while (deltaTime < STOP_DURATION) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                deltaTime = System.currentTimeMillis() - time;
                long finalDeltaTime = deltaTime;
                applyTransformations(animationNode -> transformationResults.get(animationNode).apply((float) finalDeltaTime / STOP_DURATION));
            }
            //applyTransformations(animationNode -> null);

            started = false;
        }).start();
    }


    public void stop() {
        continuing = false;
    }

    private void update(long time) {
        applyTransformations(animationNode -> animationNode.getTransformation(time));
    }

    private void applyTransformations(Function<AnimationNode, Matrix4f> transformationFunction) {
        for (AnimationNode animationNode : animationNodes) {
            Matrix4f transformation = transformationFunction.apply(animationNode);
            Node node = nodeMap.get(animationNode.name);
            List<Node.MeshAndNode> meshes = node.getMeshes();
            for (Node.MeshAndNode meshAndNode : meshes) {
                Renderable renderable = renderables.get(meshAndNode.mesh());
                if (renderable != null) {
                    renderable.setNodeTransformation(node.getTransformation());
                    renderable.setAnimationTransformation(transformation);
                }
            }
        }
    }

    public void connect(String part, Entity entity) {
        entity.getRenderables().forEach(renderable -> {
            renderables.put(renderable.getName(), renderable);
        });
        nodeMap.get(part).addEntity(entity);
    }

}
