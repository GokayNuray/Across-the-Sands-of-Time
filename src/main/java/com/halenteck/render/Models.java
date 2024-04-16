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

    private Models() {
    }

    private static Map<Integer, List<Renderable>> models;

    public static void loadModels() {
        models = new HashMap<>();
        models.put(SQUARE_PRISM, List.of(ModelLoader.createRectangularPrism(new float[]{-0.5f, 0, -0.5f}, new float[]{0.5f, 2, 0.5f})));
        models.put(TEST2, ModelLoader.loadModel("src/main/resources/test/test2/test2.obj"));
    }

    public static List<Renderable> getModel(int modelId) {
        return models.get(modelId).stream().map(Renderable::clone).collect(Collectors.toList());
    }

}
