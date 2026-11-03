// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.MathUtil;


/*static*/ class DmlXfrmWriter
{
	/* Simulation of static class by using private constructor */
	private DmlXfrmWriter()
	{}

    /// <summary>
    /// Writes 2D Transform for Grouped Objects.
    /// </summary>
    static void write(DmlTransform dmlTransform, IDmlShapeWriterContext writer)
    {
        write("a:xfrm", dmlTransform, writer);
    }

    static void write(String tagName, DmlTransform dmlTransform, IDmlShapeWriterContext writer)
    {
        if (dmlTransform == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(tagName);

        // Write only non-zero rotation.
        // Value stores without fractional part in output XML.
        int rotVal = MathUtil.doubleToInt(dmlTransform.getRotation().getValue());
        builder.writeAttributeIfNotZero("rot", rotVal);

        /*FlipOrientation*/int orientation = dmlTransform.getFlipOrientation();
        boolean flipH = (orientation == FlipOrientation.HORIZONTAL) || (orientation == FlipOrientation.BOTH);
        boolean flipV = (orientation == FlipOrientation.VERTICAL) || (orientation == FlipOrientation.BOTH);

        builder.writeAttributeIfTrue("flipH", flipH);
        builder.writeAttributeIfTrue("flipV", flipV);

        DmlBasicsWriter.writeXY("a:off", MathUtil.doubleToInt(dmlTransform.getX()),
            MathUtil.doubleToInt(dmlTransform.getY()), builder);
        DmlBasicsWriter.writeCxCy("a:ext", MathUtil.doubleToInt(dmlTransform.getWidth()),
            MathUtil.doubleToInt(dmlTransform.getHeight()), builder);

        DmlGroupTransform dmlGrpTransform = ms.as(dmlTransform, DmlGroupTransform.class);
        if (dmlGrpTransform != null)
        {
            DmlBasicsWriter.writeXY("a:chOff", MathUtil.doubleToInt(dmlGrpTransform.getChildX()),
                MathUtil.doubleToInt(dmlGrpTransform.getChildY()), builder);
            DmlBasicsWriter.writeCxCy("a:chExt", MathUtil.doubleToInt(dmlGrpTransform.getChildWidth()),
                MathUtil.doubleToInt(dmlGrpTransform.getChildHeight()), builder);
        }

        builder.endElement(tagName);
    }
}

