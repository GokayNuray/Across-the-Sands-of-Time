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

    public boolean isFull(float x, float y, float z) {
        int iX = (int) (Math.floor(x));
        int iY = (int) (Math.floor(y));
        int iZ = (int) (Math.floor(z));
        return iY <= -1; //method not implemented yet
        //return collisionData[x + y * 100 + z * 10000];
    }

}
