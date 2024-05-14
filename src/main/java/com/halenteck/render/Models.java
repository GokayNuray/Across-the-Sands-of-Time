package com.halenteck.render;

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

    private Models() {
    }

    private static Map<Integer, List<Renderable>> models;
    private static Map<Integer, Map<String, Animation>> animations;

    public static void loadModels() {
        models = new HashMap<>();
        animations = new HashMap<>();
        models.put(SQUARE_PRISM, List.of(ModelLoader.createRectangularPrism(new float[]{-0.5f, 0, -0.5f}, new float[]{0.5f, 2, 0.5f})));
        models.put(TEST2, ModelLoader.loadModel("src/main/resources/test/test2/test2.obj"));
        models.put(WORLD_MAP1, ModelLoader.loadModel("src/main/resources/test/testworld2/testworld3.obj"));
        models.put(CHARACTER1, ModelLoader.loadModel("src/main/resources/test/animTest/model.fbx"));
        animations.put(CHARACTER1, ModelLoader.loadAnimations("src/main/resources/test/animTest/model.fbx"));
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
