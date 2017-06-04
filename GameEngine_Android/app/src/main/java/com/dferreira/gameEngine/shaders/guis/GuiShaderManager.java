package com.dferreira.gameEngine.shaders.guis;

import android.content.Context;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.IEnum;
import com.dferreira.commons.generic_render.IShaderManagerAPI;
import com.dferreira.gameEngine.R;
import com.dferreira.gameEngine.shaders.ShaderManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manager of the shader files that are going to be load to render the 2D GUIs
 */
public class GuiShaderManager extends ShaderManager {


    /**
     * All the uniform locations in the shader programs
     */
    private int[] uniforms;

    /**
     * Constructor of the game shader where the vertex and fragment shader of
     * the game engine are loaded
     *
     * @param context   Context where the game engine will be created
     * @param renderAPI Reference to the API that is going to manage the program shader
     */
    public GuiShaderManager(Context context, IShaderManagerAPI renderAPI) {
        super(context, R.raw.gui_vertex_shader, R.raw.gui_fragment_shader, renderAPI);
    }

    /**
     * Bind the attributes of the program shader
     */
    @Override
    protected List<IEnum> getAttributes() {
        return new ArrayList<IEnum>(Arrays.asList(TGuiAttribute.values()));
    }

    /**
     * Get all the uniform location in the shader script
     */
    @Override
    protected void getAllUniformLocations() {
        int size = TGuiUniform.numOfGUILocations.getValue();
        uniforms = new int[size];

        for (int i = 0; i < size; i++) {
            TGuiUniform location = TGuiUniform.values()[i];
            uniforms[i] = super.getUniformLocation(location);
        }
    }

    /**
     * Load the transformation matrix
     *
     * @param matrix the matrix to be loaded
     */
    public void loadTransformationMatrix(GLTransformation matrix) {
        super.loadMatrix(uniforms[TGuiUniform.transformationMatrix.getValue()], matrix);
    }

}