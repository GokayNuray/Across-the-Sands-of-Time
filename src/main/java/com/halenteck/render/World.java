package com.halenteck.render;

public class World {

    private Renderable model;
    private boolean[] collisionData;

    public World(int model, String collisionDataPath) {
        this.model = Models.getModel(model).get(0);
        this.collisionData = World.loadCollisionData(collisionDataPath);
    }

    private static boolean[] loadCollisionData(String collisionDataPath) {
        return null; //method not implemented yet
    }

    public Renderable getModel() {
        return model;
    }

    public boolean isFull(int x, int y, int z) {
        return true; //method not implemented yet
        //return collisionData[x + y * 100 + z * 10000];
    }

}
