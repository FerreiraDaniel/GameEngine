package com.dferreira.commons;

/**
 *	Represents one shader program in that can be running in the Graphic Boards
 */
public class ShaderProgram {
    /**
     * The id of the program
     */
    private int programId;

    /**
     * The id of the vertex shader
     */
    private int vertexShaderId;

    /**
     * The id of the fragment shader
     */
    private int fragmentShaderId;

    /**
     * @return the programId
     */
    public int getProgramId() {
        return programId;
    }

    /**
     * @param programId the programId to set
     */
    public void setProgramId(int programId) {
        this.programId = programId;
    }

    /**
     * @return the vertexShaderId
     */
    public int getVertexShaderId() {
        return vertexShaderId;
    }

    /**
     * @param vertexShaderId the vertexShaderId to set
     */
    public void setVertexShaderId(int vertexShaderId) {
        this.vertexShaderId = vertexShaderId;
    }

    /**
     * @return the fragmentShaderId
     */
    public int getFragmentShaderId() {
        return fragmentShaderId;
    }

    /**
     * @param fragmentShaderId the fragmentShaderId to set
     */
    public void setFragmentShaderId(int fragmentShaderId) {
        this.fragmentShaderId = fragmentShaderId;
    }


}
