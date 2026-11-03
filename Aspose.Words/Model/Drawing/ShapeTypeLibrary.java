// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/08/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.IntToObjDictionary;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.StreamReader;


/// <summary>
/// This is a library of standard shape type definitions.
/// Allows to get shape type as an XML string or as a set of parsed shape properties.
/// </summary>
/*static*/ class ShapeTypeLibrary
{
	/* Simulation of static class by using private constructor */
	private ShapeTypeLibrary()
	{}

    /// <summary>
    /// Returns a shape type definition as an XML string. Returns null if shape type is not found.
    /// </summary>
    static String getShapeTypeXml(/*ShapeType*/int shapeType) throws Exception
    {
        return getShapeTypesXml().get((int)shapeType);
    }

    /// <summary>
    /// Returns a shape type definition as an XML string. Returns null if shape type is not found.
    /// </summary>
    /// <param name="shapeTypeId">The string in format "#_x0000_t{0}</param>
    static String getShapeTypeXml(String shapeTypeId) throws Exception
    {
        // Search for the start index of typeId within input shapeTypeId string.
        int nonDigitIdx = shapeTypeId.length() - 1;
        while ((nonDigitIdx >= 0) && Character.isDigit(shapeTypeId.charAt(nonDigitIdx)))
            nonDigitIdx--;

        int typeIdIdx = nonDigitIdx + 1;
        if (typeIdIdx < shapeTypeId.length())
        {
            int typeId = FormatterPal.parseInt(shapeTypeId.substring(typeIdIdx));
            return getShapeTypeXml((/*ShapeType*/int)typeId);
        }

        return null;
    }

    /// <summary>
    /// Returns a shape type definition as a shape properties collection. Returns null if shape type is not found.
    /// Note, you should not modify the returned collection of atrributes!
    /// </summary>
    static ShapePr getShapeTypePr(/*ShapeType*/int shapeType)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	ShapePr shapeTypePr;
        	synchronized (G_SHAPE_TYPES_PR_SYNC_ROOT)
        	{
            	shapeTypePr = G_SHAPE_TYPES_PR.get((int)shapeType);

            	if (shapeTypePr == null)
            	{
                	// We must use ContainsKey here instead of getting by key directly because we need
                	// to really know whether the shape type is already in the collection.
                	// If the shape type is in the collection, but null it means it is being read.
                	if (G_SHAPE_TYPES_PR.containsKey((int)shapeType))
                	{
                    	shapeTypePr = G_SHAPE_TYPES_PR.get((int)shapeType);
                	}
                	else
                	{
                    	// This is to avoid endless recursion in VmlShapeReader. The code there might
                    	// access some properties while reading a shape type and that causes it to try
                    	// to resolve the attribute value and attempt to load the shape type again and so on.
                    	G_SHAPE_TYPES_PR.set((int)shapeType, null);

                    	String shapeTypeXml = getShapeTypeXml(shapeType);
                    	if (StringUtil.hasChars(shapeTypeXml))
                    	{
                        	IVmlShapeTypeReader vmlShapeTypeReader = WriterFactory.createVmlShapeTypeReader();
                        	shapeTypePr = vmlShapeTypeReader.readShapeType(shapeTypeXml);
                        	G_SHAPE_TYPES_PR.set((int)shapeType, shapeTypePr);
                    	}
                	}
            	}

            	return shapeTypePr;
        	}
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Loads VML of standard shape types from an embedded resource into a dictionary.
    /// </summary>
    private static IntToObjDictionary<String> loadShapeTypesXml() throws Exception
    {
        final String RESOURCE_NAME = "Aspose.Words.Resources.ShapeTypes.txt";
        Stream stream = ResourceUtil.fetchResourceStream(RESOURCE_NAME);
        try /*JAVA: was using*/
        {
            StreamReader sr = new StreamReader(stream);
            try /*JAVA: was using*/
            {
                IntToObjDictionary<String> result = new IntToObjDictionary<String>(220);  // There are about 202 standard shape types.

                int shapeTypeId = (int)ShapeType.MIN_VALUE;
                String shapeTypeXml = sr.readLine();
                while (shapeTypeXml != null)
                {
                    result.add(shapeTypeId, shapeTypeXml);
                    shapeTypeId++;
                    shapeTypeXml = sr.readLine();
                }

                return result;
            }
            finally { if (sr != null) sr.close(); }
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Provides access to the VML definitions of standard shape types.
    /// Initialized and loaded on first access.
    /// </summary>
    private static IntToObjDictionary<String> getShapeTypesXml() throws Exception
    {
        // double-checked locking pattern.
        if (gShapeTypesXmlCache == null)
        {
            synchronized (G_SHATE_TYPES_XML_SYNC_ROOT)
            {
                if (gShapeTypesXmlCache == null)
                    gShapeTypesXmlCache = loadShapeTypesXml();
            }
        }

        return gShapeTypesXmlCache;
    }

    /// <summary>
    /// Do not access this variable directly, access it via the property only.
    ///
    /// JAVA: volatile modifier is added to static field with purpose to double-check pattern work in java.
    ///
    /// Key is <see cref="ShapeType"/>. Value is a string that contains XML of a shape type definition.
    /// </summary>
    private static volatile IntToObjDictionary<String> gShapeTypesXmlCache;
    private static /*final*/ Object G_SHATE_TYPES_XML_SYNC_ROOT = new Object();

    /// <summary>
    /// Key is <see cref="ShapeType"/>. Value is <see cref="ShapePr"/> that contains the shape type definition.
    /// </summary>
    private static /*final*/ IntToObjDictionary<ShapePr> G_SHAPE_TYPES_PR = new IntToObjDictionary<ShapePr>();
    /// <summary>
    /// Added to allow autoporting to Java.
    /// </summary>
    private static /*final*/ Object G_SHAPE_TYPES_PR_SYNC_ROOT = new Object();
}

