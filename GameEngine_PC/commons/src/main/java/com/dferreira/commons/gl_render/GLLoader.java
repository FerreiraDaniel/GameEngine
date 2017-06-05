package com.dferreira.commons.gl_render;

import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.shapes.IShape;

/**
 * Loader for parts that are specific to openGL
 */
public class GLLoader implements ILoaderRenderAPI {

	@Override
	public int getTextureId(String fileName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer loadTexture(int resourceId, boolean repeat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer loadTexture(String filename, boolean repeat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer loadTCubeMap(int[] resourcesId, boolean repeat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRawModel loadToRawModel(IShape shape) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRawModel load2DPositionsToRawModel(float[] positions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRawModel load3DPositionsToRawModel(float[] positions) {
		// TODO Auto-generated method stub
		return null;
	}

}
