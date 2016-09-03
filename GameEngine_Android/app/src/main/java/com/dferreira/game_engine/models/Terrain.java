package com.dferreira.game_engine.models;

import com.dferreira.commons.Vector3f;
import com.dferreira.game_engine.textures.TerrainTexturesPack;

/**
 * The model to the terrain entity
 */
public class Terrain {

    /**
     * Position of the terrain in the x-axle
     */
    private float x;

    /**
     * Position of the terrain in the y-axle
     */
    private float y;

    /**
     * Position of the terrain in the z-axle
     */
    private float z;

    /**
     * The RawModel of the terrain
     */
    private RawModel model;

    /**
     * The different textures of the terrain
     */
    private TerrainTexturesPack texturePack;

    /**
     * The constructor of the terrain entity
     *
     * @param texturePack The identifiers of the textures to the terrain
     * @param rawModel    The model of the terrain
     * @param position    Position where the terrain will be put in
     */
    public Terrain(TerrainTexturesPack texturePack, RawModel rawModel, Vector3f position) {

        this.texturePack = texturePack;
        this.model = rawModel;
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
     * @return the texturePack
     */
    public TerrainTexturesPack getTexturePack() {
        return texturePack;
    }


    /**
     * @return the model
     */
    public RawModel getModel() {
        return model;
    }
}
