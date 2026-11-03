// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/11/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.FormatterPal;


/// <summary>
/// Utility methods for writing common DrawingML elements.
/// </summary>
/*static*/ class DmlBasicsWriter
{
	/* Simulation of static class by using private constructor */
	private DmlBasicsWriter()
	{}

    static void writeColor(String elemName, DrColor color, NrxXmlBuilder builder, 
        /*OoxmlComplianceCore*/int compliance)
    {
        boolean isIsoStrict = compliance == OoxmlComplianceCore.ISO_STRICT;

        builder.startElement(elemName);

        builder.startElement("a:srgbClr");

        builder.writeAttribute("val", FormatterPal.intToStrX8(color.toArgb()).substring(2));

        final int ALPHA_MAX_VALUE = 0xff;
        if (color.getA() != ALPHA_MAX_VALUE)
        {
            builder.startElement("a:alpha");
            builder.writeAttribute("val", 
                DmlPercentageUtil.toPercentOrDmlPercent((double)color.getA() / ALPHA_MAX_VALUE, isIsoStrict));
            builder.endElement();
        }

        builder.endElement();   //srgbClr

        builder.endElement();   // elemName
    }

    static void writeXY(String elemName, int x, int y, NrxXmlBuilder builder)
    {
        builder.startElement(elemName);
        builder.writeAttribute("x", x);
        builder.writeAttribute("y", y);
        builder.endElement();
    }

    static void writeCxCy(String elemName, int cx, int cy, NrxXmlBuilder builder)
    {
        builder.startElement(elemName);
        builder.writeAttribute("cx", cx);
        builder.writeAttribute("cy", cy);
        builder.endElement();
    }

    static void writeXYZ(String elemName, double x, double y, double z, NrxXmlBuilder builder)
    {
        builder.startElement(elemName);
        builder.writeAttribute("x", x);
        builder.writeAttribute("y", y);
        builder.writeAttribute("z", z);
        builder.endElement();
    }

    static void writeDxDyDz(String elemName, double dx, double dy, double dz, NrxXmlBuilder builder)
    {
        builder.startElement(elemName);
        builder.writeAttribute("dx", dx);
        builder.writeAttribute("dy", dy);
        builder.writeAttribute("dz", dz);
        builder.endElement();
    }
}

