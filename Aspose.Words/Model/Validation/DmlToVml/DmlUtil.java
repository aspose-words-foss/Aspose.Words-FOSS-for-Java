// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/10/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// FOSS reduction: only the pure-model helpers survive. The DML-to-VML conversion
/// helpers (ConvertDmlToShape, ConvertDmlToShapeNoReplace, ReplaceShape) and their
/// geometry/fill helpers are gone with the rendering subsystem.
/// </summary>
/*static*/ class DmlUtil
{
	/* Simulation of static class by using private constructor */
	private DmlUtil()
	{}

    /// <summary>
    /// Returns true if the specified drawingML needs fallback shape.
    /// MS Word always writes fallback shape for Wordprocessing Shape, Wordprocessing Group, Wordprocessing Canvas and
    /// ContentPart - so we do the same.
    /// </summary>
    static boolean needFallback(ShapeBase dml, /*OoxmlComplianceCore*/int compliance)
    {
        if (!dml.isTopLevel())
            return false;

        /*DmlNodeType*/int dmlType = dml.getDmlNode().getDmlNodeType();

        if (dmlType == DmlNodeType.CHART_EX) // A chartEx shape needs DML picture fallback.
            return true;

        if (compliance == OoxmlComplianceCore.ISO_STRICT)  // ISO Strict does not support VML shapes.
            return false;

        // Graphic frame can not be top level, so missed here.
        return (dmlType == DmlNodeType.WORDPROCESSING_SHAPE)
            || (dmlType == DmlNodeType.WORDPROCESSING_GROUP_SHAPE)
            || (dmlType == DmlNodeType.WORDPROCESSING_CANVAS)
            || (dmlType == DmlNodeType.CONTENT_PART);
    }

    /// <summary>
    /// Returns true if the specified DrawingML shape needs DrawingML fallback shape.
    /// (A chartEx shape needs DML picture fallback.)
    /// </summary>
    static boolean needDmlFallback(ShapeBase dml)
    {
        return dml.isTopLevel() && (dml.getDmlNode().getDmlNodeType() == DmlNodeType.CHART_EX);
    }

    /// <summary>
    /// Replaces DrawingML node with alternate (FallBack) nodes.
    /// We expect shape inside the fallback but according to spec there can be any node.
    /// Currently this method works just in case when FallBack contains inline nodes.
    /// </summary>
    static boolean replaceDmlWithFallBack(ShapeBase dml, DocumentVisitor visitor) throws Exception
    {
        ShapeBase fallbackShape = dml.getFallbackShape();

        if (fallbackShape == null)
            return false;

        // WORDSNET-6729 We need to preserve run properties that were on the original DrawingML node, assign them onto the fallback VML shape.
        fallbackShape.setRunPr(dml.getRunPr());

        // andrnosk: WORDSNET-9723 Mimic MS Word behavior and set drawingML z-order to fallback shape.
        fallbackShape.setZOrder(dml.getZOrder());

        // WORDSNET-12083 Copy hyperlink from DML when it is replaced by Fallback.
        if (dml.hasHyperlink() && !fallbackShape.hasHyperlink())
            fallbackShape.setHRef(dml.getHRef());

        NodeUtil.insertShapeAtCompatibleTreeLevel(fallbackShape, dml);

        fallbackShape.accept(visitor);
        dml.remove();

        return true;
    }
}

