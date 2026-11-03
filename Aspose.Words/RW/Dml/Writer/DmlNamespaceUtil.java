// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/16/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.IntToObjDictionary;


/*static*/ class DmlNamespaceUtil
{
	/* Simulation of static class by using private constructor */
	private DmlNamespaceUtil()
	{}

    /// <summary>
    /// Returns namespace of the specified Dml node type.
    /// </summary>
    static String getNamespace(/*DmlNodeType*/int dmlType, boolean isIsoStrict)
    {
        switch (dmlType)
        {
            case DmlNodeType.LOCKED_CANVAS:
            case DmlNodeType.PICTURE:
            case DmlNodeType.CHART:
            case DmlNodeType.CHART_EX:
            case DmlNodeType.DIAGRAM:
            case DmlNodeType.WORDPROCESSING_SHAPE:
            case DmlNodeType.WORDPROCESSING_GROUP_SHAPE:
            case DmlNodeType.WORDPROCESSING_CANVAS:
            case DmlNodeType.CONTENT_PART:
                return isIsoStrict
                    ? G_GRAPHIC_DATA_NAMESPACES_STRICT.get((int)dmlType)
                    : G_GRAPHIC_DATA_NAMESPACES_TRANSITIONAL.get((int)dmlType);
            default:
                throw new IllegalArgumentException("Unexpected DrawingML type.");
        }
    }

    /// <summary>
    /// Adds prefix to attribute if prefix is 'w14'.
    /// This is required for writing text effects.
    /// </summary>
    static String getAttrName(String prefix, String attr)
    {
        if (!"w14".equals(prefix))
            return attr;

        return msString.format("{0}:{1}", prefix, attr);
    }

    private static void initNamespacesTransitional()
    {
        G_GRAPHIC_DATA_NAMESPACES_TRANSITIONAL.set((int)DmlNodeType.CHART, ECMA_DML_PREFIX + "chart");
        G_GRAPHIC_DATA_NAMESPACES_TRANSITIONAL.set((int)DmlNodeType.CHART_EX, CHART_EX);
        G_GRAPHIC_DATA_NAMESPACES_TRANSITIONAL.set((int)DmlNodeType.DIAGRAM, ECMA_DML_PREFIX + "diagram");
        G_GRAPHIC_DATA_NAMESPACES_TRANSITIONAL.set((int)DmlNodeType.LOCKED_CANVAS, ECMA_DML_PREFIX + "lockedCanvas");
        G_GRAPHIC_DATA_NAMESPACES_TRANSITIONAL.set((int)DmlNodeType.PICTURE, ECMA_DML_PREFIX + "picture");
        G_GRAPHIC_DATA_NAMESPACES_TRANSITIONAL.set((int)DmlNodeType.WORDPROCESSING_CANVAS, WORD_PREFIX + "wordprocessingCanvas");
        G_GRAPHIC_DATA_NAMESPACES_TRANSITIONAL.set((int)DmlNodeType.WORDPROCESSING_GROUP_SHAPE, WORD_PREFIX + "wordprocessingGroup");
        G_GRAPHIC_DATA_NAMESPACES_TRANSITIONAL.set((int)DmlNodeType.WORDPROCESSING_SHAPE, WORD_PREFIX + "wordprocessingShape");
        // Theoretically here might be other than Ink types, but for now I did not see such documents.
        // So write wordprocessingInk for ContentPart.
        G_GRAPHIC_DATA_NAMESPACES_TRANSITIONAL.set((int)DmlNodeType.CONTENT_PART, WORD_PREFIX + "wordprocessingInk");

        // There is no namespaces for DmlNodeType.GroupShape and DmlNodeType.Shape, 
        // because they seem cannot be direct children 
        // of graphicData, they are always children of lockedCanvas.
        // For the same reason GraphicFrame DML node type is missing here (Frame can be child element
        // of the group or canvas according to XML scheme described into attachment 
        // “A.4.4 DrawingML - WordprocessingML Drawing” of the spec).
    }

    private static void initNamespacesStrict()
    {
        G_GRAPHIC_DATA_NAMESPACES_STRICT.set((int)DmlNodeType.CHART, ISO_DML_PREFIX + "chart");
        G_GRAPHIC_DATA_NAMESPACES_STRICT.set((int)DmlNodeType.CHART_EX, CHART_EX);
        G_GRAPHIC_DATA_NAMESPACES_STRICT.set((int)DmlNodeType.DIAGRAM, ISO_DML_PREFIX + "diagram");
        G_GRAPHIC_DATA_NAMESPACES_STRICT.set((int)DmlNodeType.LOCKED_CANVAS, ISO_DML_PREFIX + "lockedCanvas");
        G_GRAPHIC_DATA_NAMESPACES_STRICT.set((int)DmlNodeType.PICTURE, ISO_DML_PREFIX + "picture");
        G_GRAPHIC_DATA_NAMESPACES_STRICT.set((int)DmlNodeType.WORDPROCESSING_CANVAS, WORD_PREFIX + "wordprocessingCanvas");
        G_GRAPHIC_DATA_NAMESPACES_STRICT.set((int)DmlNodeType.WORDPROCESSING_GROUP_SHAPE, WORD_PREFIX + "wordprocessingGroup");
        G_GRAPHIC_DATA_NAMESPACES_STRICT.set((int)DmlNodeType.WORDPROCESSING_SHAPE, WORD_PREFIX + "wordprocessingShape");
        G_GRAPHIC_DATA_NAMESPACES_STRICT.set((int)DmlNodeType.CONTENT_PART, WORD_PREFIX + "wordprocessingInk");
    }

    private static /*final*/ IntToObjDictionary<String> G_GRAPHIC_DATA_NAMESPACES_TRANSITIONAL;
    private static /*final*/ IntToObjDictionary<String> G_GRAPHIC_DATA_NAMESPACES_STRICT;

    private static final String ISO_DML_PREFIX = "http://purl.oclc.org/ooxml/drawingml/";
    private static final String ECMA_DML_PREFIX = "http://schemas.openxmlformats.org/drawingml/2006/";
    private static final String WORD_PREFIX = "http://schemas.microsoft.com/office/word/2010/";
    private static final String CHART_EX = "http://schemas.microsoft.com/office/drawing/2014/chartex";

    static/* DmlNamespaceUtil()*/
    {
        // Init graphicData namespaces dictionary.
        G_GRAPHIC_DATA_NAMESPACES_TRANSITIONAL = new IntToObjDictionary<String>();
        initNamespacesTransitional();
        G_GRAPHIC_DATA_NAMESPACES_STRICT = new IntToObjDictionary<String>();
        initNamespacesStrict();
    }
}

