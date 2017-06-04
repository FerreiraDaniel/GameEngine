package com.dferreira.gameEngine.models;

import com.dferreira.commons.Maths;
import com.dferreira.commons.Vector2f;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.gameEngine.textures.TerrainTexturesPack;

/**
 * The model to the terrain entity
 */
public class Terrain {

    /**
     * Position of the terrain in the x-axle
     */
    private final float x;

    /**
     * Position of the terrain in the y-axle
     */
    private final float y;

    /**
     * Position of the terrain in the z-axle
     */
    private final float z;

    /* Heights of the components of the terrain */
    private final float[][] heights;

    /**
     * The GLRawModel of the terrain
     */
    private final IRawModel model;

    /**
     * The different textures of the terrain
     */
    private TerrainTexturesPack texturePack;

    /**
     * The constructor of the terrain entity
     *
     * @param rawModel The model of the terrain
     * @param heights  The heights of the terrain
     * @param position Position where the terrain will be put in
     */
    public Terrain(IRawModel rawModel, float[][] heights, Vector3f position) {
        this.model = rawModel;
        this.heights = heights;
        this.x = position.x * TerrainShape.SIZE;
        this.y = position.y * TerrainShape.SIZE;
        this.z = position.z * TerrainShape.SIZE;
    }


    /**
     * @return the position of the terrain in the x-axle
     */
    public float getX() {
        return x;
    }

    /**
     * @return the position of the terrain in the y-axle
     */
    public float getY() {
        return y;
    }


    /**
     * @return the position of the terrain in the z-axle
     */
    public float getZ() {
        return z;
    }

    /**
     * Set the textures package of the terrain
     *
     * @param texturePack The identifiers of the textures to the terrain
     */
    public void setTexturePack(TerrainTexturesPack texturePack) {
        this.texturePack = texturePack;
    }

    /**
     * @return the texturePack
     */
    public TerrainTexturesPack getTexturePack() {
        return texturePack;
    }


    /**
     * @return the model
     */
    public IRawModel getModel() {
        return model;
    }

    /**
     * The height of the terrain in a certain position of the world
     *
     * @param worldX The x-component of the location to compute the height of the
     *               terrain
     * @param worldZ The y-component of the location to compute the height of the
     *               terrain
     * @return The height of the terrain in the specified position of the world
     */
    public float getHeightOfTerrain(float worldX, float worldZ) {
        float terrainX = worldX - this.x;
        float terrainZ = worldZ - this.z;
        float gridSquareSize = TerrainShape.SIZE / ((float) heights.length - 1);
        int gridX = (int) Math.floor(terrainX / gridSquareSize);
        int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
        if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
            return 0;
        }
        float xCoord = (terrainX % gridSquareSize);
        float zCoord = (terrainZ % gridSquareSize);

        float height;
        if (xCoord <= (gridSquareSize - zCoord)) {
            float v1Height = heights[gridX][gridZ];
            float v3Height = heights[gridX][gridZ + 1];
            height = Maths.barryCentric(new Vector3f(0, v1Height, 0),
                    new Vector3f(gridSquareSize, heights[gridX + 1][gridZ], 0),
                    new Vector3f(0, v3Height, gridSquareSize), new Vector2f(xCoord, zCoord));
        } else {
            float v1Height = heights[gridX + 1][gridZ];
            float v2Height = heights[gridX + 1][gridZ + 1];
            float v3Height = heights[gridX][gridZ + 1];
            height = Maths.barryCentric(new Vector3f(gridSquareSize, v1Height, 0),
                    new Vector3f(gridSquareSize, v2Height, gridSquareSize), new Vector3f(0, v3Height, gridSquareSize),
                    new Vector2f(xCoord, zCoord));
        }

        return height;
    }
}
