package com.halenteck.render;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class World {

    private Entity model;
    private Set<BlockCoordinates> blocks;

    public World(int model, String collisionDataPath) {
        this.model = new Entity(model, 0.5f, 0.5f, 0.5f, 0, 0, 1);
        this.blocks = World.loadCollisionData(collisionDataPath);
    }

    private static Set<BlockCoordinates> loadCollisionData(String collisionDataPath) {
        try {
            InputStream file = World.class.getClassLoader().getResourceAsStream(collisionDataPath);
            if (file == null) {
                throw new FileNotFoundException("Resource not found: " + collisionDataPath);
            }
            DataInputStream data = new DataInputStream(file);
            Set<BlockCoordinates> blocks = new HashSet<>();
            int size = data.readInt();
            for (int i = 0; i < size; i++) {
                int x = data.readInt();
                int y = data.readInt();
                int z = data.readInt();
                BlockCoordinates block = new BlockCoordinates(x, y, z);
                blocks.add(block);
            }
            return blocks;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Entity getModel() {
        return model;
    }

    public boolean isFull(float x, float y, float z) {
        int iX = (int) (Math.floor(x));
        int iY = (int) (Math.floor(y));
        int iZ = (int) (Math.floor(z));

        return blocks.contains(new BlockCoordinates(iX, iY, iZ));
    }

}

class BlockCoordinates {
    private int x;
    private int y;
    private int z;

    public BlockCoordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return "BlockCoordinates{" +
                "x= " + x +
                ", y= " + y +
                ", z= " + z +
                '}';
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BlockCoordinates other)) {
            return false;
        }
        return x == other.x && y == other.y && z == other.z;
    }

    public int hashCode() {
        return 31 * (31 * x + y) + z;
    }
}
