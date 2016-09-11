package com.dferreira.game_engine.renderEngine;

@SuppressWarnings("WeakerAccess")
public class RenderConstants {

    @SuppressWarnings("FieldCanBeLocal")
    public final static int VERTEX_SIZE = 3;

    //Specifies whether fixed-point data values should be normalized (GL_TRUE) or converted directly as fixed-point values (GL_FALSE) when they are accessed.
    @SuppressWarnings("FieldCanBeLocal")
    public final static boolean VERTEX_NORMALIZED = false;

    //Offset between following vertices
    @SuppressWarnings("FieldCanBeLocal")
    public final static int STRIDE = 0;

    //number of components per generic vertex attribute
    @SuppressWarnings("FieldCanBeLocal")
    public final static int NUMBER_COMPONENTS_PER_VERTEX_ATTR = 2;


    /**
     * Number of components that compose the normal vector
     */
    @SuppressWarnings("FieldCanBeLocal")
    public final static int NUMBER_COMPONENTS_PER_NORMAL_ATTR = 3;
}
