package com.halenteck.render;

import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Models {

    static {
        loadModels();
    }

    public static final int SQUARE_PRISM = 0;
    public static final int TEST2 = 1;
    public static final int WORLD_MAP1 = 2;
    public static final int CHARACTER1 = -1;
    public static final int CHARACTER2 = -2;
    public static final int CHARACTER3 = -3;
    public static final int CHARACTER4 = -4;
    public static final int CHARACTER5 = -5;
    public static final int ANIM_TEST = 11;
    public static final int WEAPON1 = 21;
    public static final int WEAPON2 = 22;

    private Models() {
    }

    private static Map<Integer, List<Renderable>> models;
    private static Map<Integer, Map<String, Animation>> animations;

    public static void loadModels() {
        models = new HashMap<>();
        animations = new HashMap<>();
        models.put(SQUARE_PRISM, List.of(ModelLoader.createRectangularPrism(new float[]{-0.5f, 0, -0.5f}, new float[]{0.5f, 2, 0.5f})));
        models.put(TEST2, ModelLoader.loadModel("src/main/resources/test/test2/test2.obj"));
        models.put(WORLD_MAP1, ModelLoader.loadModel("src/main/resources/test/testworld4/testworld4.obj"));
        models.put(CHARACTER1, ModelLoader.loadModel("src/main/resources/characters/caveman/model/model.fbx"));
        models.put(CHARACTER2, ModelLoader.loadModel("src/main/resources/characters/antidote/model/model.fbx"));
        models.put(CHARACTER3, ModelLoader.loadModel("src/main/resources/characters/nazi/model/model.fbx"));
        models.put(CHARACTER4, ModelLoader.loadModel("src/main/resources/characters/globalwarming/model/model.fbx"));
        models.put(CHARACTER5, ModelLoader.loadModel("src/main/resources/characters/boss/model/model.fbx"));
        models.put(WEAPON1, ModelLoader.loadModel("src/main/resources/weapons/m16a1/scene.gltf"));
        models.put(WEAPON2, ModelLoader.loadModel("src/main/resources/weapons/combat_knife/scene.gltf"));
        animations.put(ANIM_TEST, ModelLoader.loadAnimations("src/main/resources/test/animTest/model.fbx"));

        models.get(CHARACTER1).forEach(r -> r.setModelScale(new Vector3f(0.01f, 0.01f, 0.01f)));
        models.get(CHARACTER1).forEach(r -> r.setModelYaw((float) Math.toRadians(180)));
        models.get(CHARACTER2).forEach(r -> r.setModelScale(new Vector3f(0.01f, 0.01f, 0.01f)));
        models.get(CHARACTER2).forEach(r -> r.setModelYaw((float) Math.toRadians(180)));
        models.get(CHARACTER3).forEach(r -> r.setModelScale(new Vector3f(0.01f, 0.01f, 0.01f)));
        models.get(CHARACTER3).forEach(r -> r.setModelYaw((float) Math.toRadians(180)));
        models.get(CHARACTER4).forEach(r -> r.setModelScale(new Vector3f(0.01f, 0.01f, 0.01f)));
        models.get(CHARACTER4).forEach(r -> r.setModelYaw((float) Math.toRadians(180)));
        models.get(CHARACTER5).forEach(r -> r.setModelScale(new Vector3f(0.01f, 0.01f, 0.01f)));
        models.get(CHARACTER5).forEach(r -> r.setModelYaw((float) Math.toRadians(180)));

        models.get(WEAPON1).forEach(r -> r.setModelYaw((float) Math.toRadians(270)));
        models.get(WEAPON1).forEach(r -> r.setModelPitch((float) Math.toRadians(90)));
        models.get(WEAPON2).forEach(r -> r.setModelScale(new Vector3f(0.2f, 0.2f, 0.2f)));
        models.get(WEAPON2).forEach(r -> r.setModelYaw((float) Math.toRadians(90)));
    }

    public static List<Renderable> getModel(int modelId) {
        List<Renderable> model = models.get(modelId);
        if (model == null) {
            throw new IllegalArgumentException("Model not found. : " + modelId);
        }
        return model.stream().map(Renderable::clone).collect(Collectors.toList());
    }

    public static Map<String, Animation> getAnimations(int modelId) {
        return animations.get(modelId);
    }

}
