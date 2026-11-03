// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import java.util.ArrayList;
import com.aspose.Debug;


/*static*/ class DmlGeomertyWriter
{
	/* Simulation of static class by using private constructor */
	private DmlGeomertyWriter()
	{}

    static void write(DmlGeometry geometry, IDmlShapeWriterContext writer)
    {
        if (geometry == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        DmlGuides guides = geometry.getGuides();
        // If preset name of geometry is specified write it as prstGeom otherwise as custGeom.
        String presetName = geometry.getPresetName();
        if (StringUtil.hasChars(presetName))
        {
            // In MS Word, only the adjustable values that are not preset are updated and written.
            // If any preset adjustable values are changed, we need to create a custom adjustable values
            // with the same name but with the changed values in order to mimic this behavior.
            // Additionally, MS Word creates a copy of all preset values, even if only one value has been changed.
            if (!guides.hasCustomAdjustableValues() && guides.isEditedPresetAdjustableValues(presetName))
                guides.createCustomAdjustableValues();

            builder.startElement("a:prstGeom");
            builder.writeAttribute("prst", presetName);
            writeGuides(guides.getAdjustableValues(), builder, "a:avLst");
            builder.endElement("a:prstGeom");
        }
        else
        {
            builder.startElement("a:custGeom");
            writeGuides(guides.getAdjustableValues(), builder, "a:avLst");
            writeGuides(guides.getGuides(), builder, "a:gdLst");
            writeConnectionSites(geometry.getConnectionSites(), builder, "a:cxnLst");

            // andrnosk: WORDSNET-12188 Write a:rect element to preserve text box appearance.
            if (geometry.getDmlTextboxRect() != null)
            {
                writeTextboxRect(geometry.getDmlTextboxRect(), builder);
            }

            writePaths(geometry.getPaths(), builder);
            builder.endElement("a:custGeom");
        }
    }

    /// <summary>
    /// Writes Text Rectangle (L.4.9.4.4.2)
    /// The text rectangle defines where text resides within the shape.
    /// </summary>
    private static void writeTextboxRect(DmlTextBoxRect dmlTextboxRec, NrxXmlBuilder builder)
    {
        builder.startElement("a:rect");
        builder.writeAttribute("l", dmlTextboxRec.getLeft());
        builder.writeAttribute("t", dmlTextboxRec.getTop());
        builder.writeAttribute("r", dmlTextboxRec.getRight());
        builder.writeAttribute("b", dmlTextboxRec.getBottom());
        builder.endElement("a:rect");
    }

    static void writeGuides(ArrayList<DmlGuide> guides, NrxXmlBuilder builder, String rootName)
    {
        builder.startElement(rootName);

        for (DmlGuide guide : guides)
            writeGuide(guide, builder);

        builder.endElement(rootName);
    }

    /// <summary>
    /// Writes connection sites of the shape.
    /// </summary>
    /// <param name="connectionSites">Connection sites collection.</param>
    /// <param name="builder">Builder which writes to output file.</param>
    /// <param name="rootName">Root name for connection sites collection.</param>
    private static void writeConnectionSites(ArrayList<DmlConnectionSite> connectionSites, NrxXmlBuilder builder,
        String rootName)
    {
        Debug.assertTrue((connectionSites != null) && (builder != null));

        if (connectionSites.size() == 0)
            return;

        final String SITE_ROOT = "a:cxn";
        builder.startElement(rootName);

        for (DmlConnectionSite site : connectionSites)
        {
            builder.startElement(SITE_ROOT);
            builder.writeAttribute("ang", site.getAngle().getString());
            writeShapePosition(site.getCoordinates(), builder, "a:pos");
            builder.endElement(SITE_ROOT);
        }

        builder.endElement(rootName);
    }

    /// <summary>
    /// Writes position coordinate within the shape bounding box.
    /// </summary>
    /// <param name="coord">Shape position coordinate.</param>
    /// <param name="builder">Builder which writes to output file.</param>
    /// <param name="rootName">Root name for position coordinate.</param>
    private static void writeShapePosition(DmlAdjustablePoint coord, NrxXmlBuilder builder, String rootName)
    {
        Debug.assertTrue(coord != null);

        builder.startElement(rootName);
        builder.writeAttribute("x", coord.getX().getString());
        builder.writeAttribute("y", coord.getY().getString());
        builder.endElement(rootName);
    }

    private static void writeGuide(DmlGuide guide, NrxXmlBuilder builder)
    {
        if (!guide.isPreset())
            builder.writeElementWithAttributes("a:gd", "name", guide.getName(), "fmla", guide.getFormula().getSource());
    }

    private static void writePaths(ArrayList<DmlPath> paths, NrxXmlBuilder builder)
    {
        builder.startElement("a:pathLst");

        for (DmlPath path : paths)
            writePath(path, builder);

        builder.endElement("a:pathLst");
    }

    private static void writePath(DmlPath path, NrxXmlBuilder builder)
    {
        builder.startElement("a:path");
        builder.writeAttribute("fill", DmlEnum.pathFillModeToDml(path.getFillMode()));

        if (path.getHeightDefined())
            builder.writeAttribute("h", path.getHeight());
        if (path.getWidthDefined())
            builder.writeAttribute("w", path.getWidth());

        builder.writeAttribute("stroke", path.getStroke());

        // According to spec: if this attribute is omitted then a value of 0,
        // or false is assumed. So, write "extrusionOk" when value is true.
        builder.writeAttributeIfTrue("extrusionOk", path.getExtrusionOk());

        for (IDmlPathPart part : path.getPathParts())
            part.write(builder);

        builder.endElement("a:path");
    }
}

