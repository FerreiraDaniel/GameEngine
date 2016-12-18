package com.dferreira.commons.wavefront;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.dferreira.commons.ColorRGB;
import com.dferreira.commons.shapes.IExternalMaterial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Parses files with wavefront format That describes a set of material
 */
class MtlLoader extends GenericLoader {

    private final static String TAG = "MtlLoader";

    /**
     * @param line line to get parsed
     * @return Parse one element with only one component
     */
    private static float parseComponent(String[] line) {
        return Float.parseFloat(line[ComponentPositions.r]);
    }

    /**
     * Parses one RGB color
     *
     * @param line line to get parsed
     * @return The color that got parsed
     */
    private static ColorRGB parseRgbColor(String[] line) {
        float r = Float.parseFloat(line[ComponentPositions.r]);
        float g = Float.parseFloat(line[ComponentPositions.g]);
        float b = Float.parseFloat(line[ComponentPositions.b]);

        return new ColorRGB(r, g, b);
    }

    /**
     * Parses one waveFront file contain a list of material
     *
     * @param context  Context where the method is called
     * @param fileName Name of the file without extension
     * @return A list with information about material read
     */
    private static List<IExternalMaterial> lLoadObjModel(Context context, String fileName) {
        InputStream inputStream;
        BufferedReader reader = null;
        String line;
        List<IExternalMaterial> materials = null;

        try {
            if (TextUtils.isEmpty(fileName)) {
                return null;
            } else {
                String resourceName = fileName.split("\\.mtl")[0];
                int resourceId = context.getResources().getIdentifier(resourceName, "raw", context.getPackageName());
                inputStream = context.getResources().openRawResource(resourceId);
            }
        } catch (Exception e) {
            System.err.println("Could not load file!");
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            return null;
        }

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream), 1024);
            materials = new ArrayList<>();
            WfMaterial currentMaterial = null;

            while ((line = reader.readLine()) != null) {
                if ("".equals(line.trim())) {
                    continue;
                }

                String[] currentLine = line.split(SPLIT_TOKEN);
                String prefix = currentLine[0];
                switch (prefix) {
                    // Comment in the MTL file gets ignored
                    case MtlPrefix.COMMENT:
                        break;
                    // Gets the information that should parse a new material
                    case MtlPrefix.NEW_MATERIAL:
                        currentMaterial = new WfMaterial();
                        currentMaterial.setName(currentLine[1]);
                        materials.add(currentMaterial);
                        break;
                    case MtlPrefix.WEIGHT_SPECULAR_COLOR:
                        float weightSpecularColor = parseComponent(currentLine);
                        if (currentMaterial != null) {
                            currentMaterial.setWeightSpecularColor(weightSpecularColor);
                        }
                        break;
                    case MtlPrefix.SPECULAR_COLOR:
                        ColorRGB specularColor = parseRgbColor(currentLine);
                        if (currentMaterial != null)
                            currentMaterial.setSpecularColor(specularColor);
                        break;
                    case MtlPrefix.AMBIENT_COLOR:
                        ColorRGB ambientColor = parseRgbColor(currentLine);
                        if (currentMaterial != null) {
                            currentMaterial.setAmbientColor(ambientColor);
                        }
                        break;
                    case MtlPrefix.DIFFUSE_COLOR:
                        ColorRGB diffuseColor = parseRgbColor(currentLine);
                        if (currentMaterial != null) {
                            currentMaterial.setDiffuseColor(diffuseColor);
                        }
                        break;
                    case MtlPrefix.EMISSIVE_COLOR:
                        ColorRGB emissiveColor = parseRgbColor(currentLine);
                        if (currentMaterial != null) {
                            currentMaterial.setEmissiveColor(emissiveColor);
                        }
                        break;
                    case MtlPrefix.OPTICAL_DENSITY:
                        float opticalDensity = parseComponent(currentLine);
                        if (currentMaterial != null) {
                            currentMaterial.setOpticalDensity(opticalDensity);
                        }
                        break;
                    case MtlPrefix.DISSOLVE_FACTOR:
                        float dissolveFactor = parseComponent(currentLine);
                        if (currentMaterial != null) {
                            currentMaterial.setDissolveFactor(dissolveFactor);
                        }
                        break;
                    case MtlPrefix.DISSOLVE_FACTOR_INVERTED:
                        float dissolveFactorInverted = parseComponent(currentLine);
                        if (currentMaterial != null) {
                            currentMaterial.setDissolveFactor(1.0f - dissolveFactorInverted);
                        }
                        break;
                    case MtlPrefix.ILLUMINATION_MODEL:
                        float illuminationModel = parseComponent(currentLine);
                        if (currentMaterial != null) {
                            currentMaterial.setIlluminationModel(Float.valueOf(illuminationModel).intValue());
                        }
                        break;
                    case MtlPrefix.TRANSMISSION_FILTER:
                        ColorRGB transmissionFilter = parseRgbColor(currentLine);
                        if (currentMaterial != null) {
                            currentMaterial.setTransmissionFactor(transmissionFilter);
                        }
                        break;
                    case MtlPrefix.COLOR_TEXTURE_MAP:
                        String textureFileName = parseStringComponent(line, currentLine);
                        if (currentMaterial != null) {
                            currentMaterial.setDiffuseTextureFileName(textureFileName);
                        }
                        break;
                    case MtlPrefix.SPECULAR_COLOR_TEXTURE_MAP:
                        String specularFileName = parseStringComponent(line, currentLine);
                        if (currentMaterial != null)
                            currentMaterial.setSpecularTextureFileName(specularFileName);
                        break;
                    case MtlPrefix.AMBIENT_COLOR_TEXTURE_MAP:
                        String ambientFileName = parseStringComponent(line, currentLine);
                        if (currentMaterial != null)
                            currentMaterial.setAmbientTextureFileName(ambientFileName);
                        break;
                    case MtlPrefix.BUMP_TEXTURE_MAP:
                    case MtlPrefix.BUMP_TEXTURE_MAP_V2:
                    case MtlPrefix.BUMP_TEXTURE_MAP_V3:
                        String bumpFileName = parseStringComponent(line, currentLine);
                        if (currentMaterial != null) {
                            currentMaterial.setBumpTextureFileName(bumpFileName);
                        }
                        break;
                    case MtlPrefix.OPACITY_MAP:
                        String opacityFileName = parseStringComponent(line, currentLine);
                        if (currentMaterial != null) {
                            currentMaterial.setOpacityTextureFileName(opacityFileName);
                        }
                        break;

                    // The material defines something that is not supported by the
                    // parser
                    default:
                        System.err.println("line:" + line + " Not supported");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(reader != null)
                {
                    reader.close();
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return materials;
    }

    /**
     * Creates an hashMap of the materials that were parsed in order to make
     * faster associations
     *
     * @param materials List of materials
     * @return the HashMap with materials
     */
    private static HashMap<String, IExternalMaterial> buildMapOfMaterials(List<IExternalMaterial> materials) {
        if ((materials == null) || (materials.isEmpty())) {
            return null;
        } else {
            HashMap<String, IExternalMaterial> mapOfMaterials = new HashMap<>();
            for (int i = 0; i < materials.size(); i++) {
                IExternalMaterial material = materials.get(i);
                mapOfMaterials.put(material.getName(), material);
            }
            return mapOfMaterials;
        }
    }

    /**
     * Parses one waveFront file contain a list of material
     *
     * @param context  Context where the method is called
     * @param fileName Name of the file without extension
     * @return An hash with information about materials read
     */
    public static HashMap<String, IExternalMaterial> loadObjModel(Context context, String fileName) {
        return buildMapOfMaterials(MtlLoader.lLoadObjModel(context, fileName));
    }
}
