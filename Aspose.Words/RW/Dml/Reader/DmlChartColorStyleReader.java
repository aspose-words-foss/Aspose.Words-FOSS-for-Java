// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// This class allows reading a color style part of a chart.
/// </summary>
class DmlChartColorStyleReader extends DmlReaderBase
{
    // Cannot create objects of this class.
    private DmlChartColorStyleReader()
    {
    }

    /// <summary>
    /// Reads a <see cref="DmlChartColorStyle"/> object from the element 2.8.1.2 colorStyle [MS-ODRAWXML] that is
    /// a root element of the word/charts/colorsX.xml part.
    /// </summary>
    static DmlChartColorStyle read(DocxDocumentReaderBase reader) throws Exception
    {
        if (!"colorStyle".equals(reader.getXmlReader().getLocalName()))
            return null;

        DmlChartColorStyle colorStyle = new DmlChartColorStyle();

        readAttributes(reader.getXmlReader(), colorStyle);
        readChildren(reader, colorStyle);

        return colorStyle;
    }

    /// <summary>
    /// Reads attributes of the root colorStyle element.
    /// </summary>
    private static void readAttributes(NrxXmlReader xmlReader, DmlChartColorStyle colorStyle)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"meth"*/0:
                    colorStyle.setMethod(xmlReader.getValue());
                    break;
                case /*"id"*/1:
                    colorStyle.setId(xmlReader.getValue());
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }
        xmlReader.moveToElement();
    }

    /// <summary>
    /// Reads child elements of the root colorStyle element.
    /// </summary>
    private static void readChildren(DocxDocumentReaderBase reader, DmlChartColorStyle colorStyle) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        ArrayList<DmlColor> colors = new ArrayList<DmlColor>();
        ArrayList<DmlChartColorStyleVariation> variations = new ArrayList<DmlChartColorStyleVariation>();

        while (xmlReader.readChild("colorStyle"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"variation"*/2:
                    DmlChartColorStyleVariation variation = new DmlChartColorStyleVariation();
                    variation.setColorModifiers(DmlColorReader.readModifiers(xmlReader, reader.getComplianceInfo()));
                    msArrayList.add(variations, variation);
                    break;
                case /*"extLst"*/3:
                    colorStyle.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    DmlColor color = DmlColorReader.read(xmlReader, reader.getComplianceInfo());
                    if (color != null)
                        msArrayList.add(colors, color);
                    break;
            }
        }

        if (colors.size() > 0)
        {
            colorStyle.setColors(new DmlColor[colors.size()]);
            for (int i = 0; i < colors.size(); i++)
                colorStyle.getColors()[i] = colors.get(i);
        }

        if (variations.size() > 0)
        {
            colorStyle.setVariations(new DmlChartColorStyleVariation[variations.size()]);
            for (int i = 0; i < variations.size(); i++)
                colorStyle.getVariations()[i] = variations.get(i);
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"meth",
		"id",
		"variation",
		"extLst"
	);

}

