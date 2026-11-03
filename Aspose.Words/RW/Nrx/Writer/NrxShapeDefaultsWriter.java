// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;


/// <summary>
/// Implements writing 'shapeDefaults' and 'hdrShapeDefaults' elements.
/// </summary>
/*static*/ class NrxShapeDefaultsWriter
{
	/* Simulation of static class by using private constructor */
	private NrxShapeDefaultsWriter()
	{}

    static void write(NrxXmlBuilder builder, SaveInfo saveInfo, boolean isHeaderFooterShapes)
    {
        ArrayList<ShapeBase> connectorShapes = isHeaderFooterShapes
            ? saveInfo.ConnectorsHdr
            : saveInfo.ConnectorsBody;
        String tagName = isHeaderFooterShapes ? "w:hdrShapeDefaults" : "w:shapeDefaults";

        if (connectorShapes.size() == 0)
            return;

        builder.startElement(tagName);
        builder.startElement("o:shapelayout");
        builder.startElement("o:rules");

        int ruleIndex = 1;
        for(ShapeBase connectorShape : connectorShapes)
        {
            ConnectorRule rule = (ConnectorRule)connectorShape.getShapePr().get(ShapeAttr.CONNECTOR_RULE);

            builder.startElement("o:r");
            builder.writeAttribute("id", msString.format("V:Rule{0}", ruleIndex));
            builder.writeAttribute("type", "connector");
            builder.writeAttribute("idref", formatIdRef(connectorShape));

            // proxy element for start.
            builder.startElement("o:proxy");
            builder.writeAttributeString("start", "");
            builder.writeAttribute("idref", formatIdRef(saveInfo.Shapes.get(rule.ShapeAId)));
            builder.writeAttribute("connectloc", rule.ShapeASite);
            builder.endElement("o:proxy");

            // proxy element for end.
            builder.startElement("o:proxy");
            builder.writeAttributeString("end", "");
            builder.writeAttribute("idref", formatIdRef(saveInfo.Shapes.get(rule.ShapeBId)));
            builder.writeAttribute("connectloc", rule.ShapeBSite);
            builder.endElement("o:proxy");

            builder.endElement("o:r");

            ruleIndex++;
        }

        builder.endElement("o:rules");
        builder.endElement("o:shapelayout");
        builder.endElement(tagName);
    }

    private static String formatIdRef(ShapeBase shape)
    {
        String shapeName = (String)shape.getShapePr().get(ShapeAttr.SHAPE_NAME);
        return msString.format("#{0}", StringUtil.hasChars(shapeName) ? shapeName : NrxXmlUtil.getShapeId(shape));
    }
}

