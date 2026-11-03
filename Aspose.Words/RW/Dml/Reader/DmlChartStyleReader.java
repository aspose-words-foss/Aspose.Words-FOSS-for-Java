// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.StringToIntDictionary;


/// <summary>
/// This class allows reading a style part of a chart.
/// </summary>
class DmlChartStyleReader extends DmlReaderBase
{
    // Cannot create objects of this class.
    private DmlChartStyleReader()
    {
    }

    /// <summary>
    /// Reads a <see cref="DmlChartStyle"/> object from the element 2.8.1.1 chartStyle [MS-ODRAWXML] that is
    /// a root element of the word/charts/styleX.xml part.
    /// </summary>
    static DmlChartStyle read(DocxDocumentReaderBase reader) throws Exception
    {
        if (!"chartStyle".equals(reader.getXmlReader().getLocalName()))
            return null;

        DmlChartStyle style = new DmlChartStyle();

        readAttributes(reader.getXmlReader(), style);
        readChildren(reader, style);

        return style;
    }

    /// <summary>
    /// Reads attributes of the root chartStyle element.
    /// </summary>
    private static void readAttributes(NrxXmlReader xmlReader, DmlChartStyle style)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"id"*/0:
                    style.setId(xmlReader.getValue());
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }
        xmlReader.moveToElement();
    }

    /// <summary>
    /// Reads child elements of the root chartStyle element.
    /// </summary>
    private static void readChildren(DocxDocumentReaderBase reader, DmlChartStyle style) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("chartStyle"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"dataPointMarkerLayout"*/1:
                    style.setDataPointMarkerLayout(readMarkerLayout(xmlReader));
                    break;
                case /*"extLst"*/2:
                    style.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    {
                        if (G_ELEMENT_TO_STYLE_ITEM_MAP.containsKey(xmlReader.getLocalName()))
                        {
                            style.setByDmlChartStyleItem((/*DmlChartStyleItem*/int)G_ELEMENT_TO_STYLE_ITEM_MAP.get(xmlReader.getLocalName()), readStyleEntry(reader));
                        }
                        else
                        {
                            warnUnexpectedAndIgnoreElement(xmlReader);
                        }
                        break;
                    }
            }
        }
    }

    /// <summary>
    /// Reads an element of the 2.8.3.7 CT_StyleEntry complex type.
    /// </summary>
    private static DmlChartStyleEntry readStyleEntry(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlChartStyleEntry styleEntry = new DmlChartStyleEntry();
        String tagName = xmlReader.getLocalName();

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"mods"*/3:
                    styleEntry.setModifiers(msString.split(xmlReader.getValue(), ' '));
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        xmlReader.moveToElement();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"lnRef"*/4:
                    styleEntry.setLineReference(readStyleReference(reader));
                    break;
                case /*"lineWidthScale"*/5:
                    double value = FormatterPal.tryParseDoubleInvariant(xmlReader.readString());
                    if (!Double.isNaN(value))
                        styleEntry.setLineWidthScale(value);
                    break;
                case /*"fillRef"*/6:
                    styleEntry.setFillReference(readStyleReference(reader));
                    break;
                case /*"effectRef"*/7:
                    styleEntry.setEffectReference(readStyleReference(reader));
                    break;
                case /*"fontRef"*/8:
                    styleEntry.setFontReference(readFontReference(reader));
                    break;
                case /*"spPr"*/9:
                    {
                        styleEntry.setShapePr(new DefaultShapeProperties());
                        DmlNodePropertiesReader.readShapeProperties(reader, styleEntry.getShapePr());
                        break;
                    }
                case /*"defRPr"*/10:
                    {
                        styleEntry.setDefaultRunPr(new DmlRunProperties());
                        DmlTextShapeReader.readRunProperties(styleEntry.getDefaultRunPr(), reader);
                        break;
                    }
                case /*"bodyPr"*/11:
                    {
                        styleEntry.setTextBodyPr(new DmlTextBodyProperties());
                        DmlTextShapeReader.readTextBodyProperties(reader, styleEntry.getTextBodyPr(), reader.getComplianceInfo());
                        break;
                    }
                case /*"extLst"*/2:
                    styleEntry.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return styleEntry;
    }

    /// <summary>
    /// Reads an element of the 2.8.3.8 CT_StyleReference complex type.
    /// </summary>
    private static DmlChartStyleReference readStyleReference(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlChartStyleReference styleReference = new DmlChartStyleReference();

        styleReference.setStyleMatrixIndex(xmlReader.readIntAttribute("idx", 0));
        styleReference.setModifiers(readReferenceModifiers(xmlReader));
        readReferenceColor(reader, styleReference);

        return styleReference;
    }

    /// <summary>
    /// Reads the mods attribute of the 2.8.4.12 ST_StyleReferenceModifierList type.
    /// </summary>
    private static String[] readReferenceModifiers(NrxXmlReader xmlReader)
    {
        String modifiers = xmlReader.readAttribute("mods", "");
        return StringUtil.hasChars(modifiers) ? msString.split(modifiers, ' ') : null;
    }

    /// <summary>
    /// Reads color of the CT_StyleReference and CT_FontReference elements [MS-ODRAWXML].
    /// </summary>
    private static void readReferenceColor(DocxDocumentReaderBase reader, DmlStyleReferenceBase styleReference)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String currentTagName = xmlReader.getLocalName();

        while (xmlReader.readChild(currentTagName))
        {
            if ("styleClr".equals(xmlReader.getLocalName()))
            {
                styleReference.setColor(readStyleColor(reader));
            }
            else
            {
                DmlColor color = DmlColorReader.read(xmlReader, reader.getComplianceInfo());
                // Reader returns null if it founds unknown tag. We filter null value to disable overwriting of
                // already initialized Color property by null values.
                if (color != null)
                    styleReference.setColor(color);
            }
        }
    }

    /// <summary>
    /// Reads an element of the 2.8.3.6 CT_StyleColor complex type.
    /// </summary>
    private static DmlChartStyleColor readStyleColor(DocxDocumentReaderBase reader)
    {
        DmlChartStyleColor color = new DmlChartStyleColor();
        color.setValue(reader.getXmlReader().readAttribute(""));
        color.setColorModifiers(DmlColorReader.readModifiers(reader.getXmlReader(), reader.getComplianceInfo()));
        return color;
    }

    /// <summary>
    /// Reads an element of the 2.8.3.4 CT_FontReference complex type.
    /// </summary>
    private static DmlChartFontReference readFontReference(DocxDocumentReaderBase reader)
    {
        DmlChartFontReference fontReference = new DmlChartFontReference();

        String idxValue = reader.getXmlReader().readAttribute("idx", "");
        fontReference.setFontCollectionIndex(DmlEnum.dmlToFontCollectionIndex(idxValue));

        fontReference.setModifiers(readReferenceModifiers(reader.getXmlReader()));
        readReferenceColor(reader, fontReference);

        return fontReference;
    }

    /// <summary>
    /// Reads an element of the 2.8.3.5 CT_MarkerLayout complex type.
    /// </summary>
    private static DmlChartMarkerLayout readMarkerLayout(NrxXmlReader xmlReader)
    {
        DmlChartMarkerLayout markerLayout = new DmlChartMarkerLayout();

        markerLayout.setSymbol(xmlReader.readAttribute("symbol", ""));
        markerLayout.setSize(xmlReader.readIntAttribute("size", 0));

        return markerLayout;
    }

    private static /*final*/ StringToIntDictionary G_ELEMENT_TO_STYLE_ITEM_MAP;

    /// <summary>
    /// Static ctor.
    /// </summary>
    static/* DmlChartStyleReader()*/
    {
        G_ELEMENT_TO_STYLE_ITEM_MAP = new StringToIntDictionary();

        G_ELEMENT_TO_STYLE_ITEM_MAP.add("axisTitle", (int)DmlChartStyleItem.AXIS_TITLE);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("categoryAxis", (int)DmlChartStyleItem.CATEGORY_AXIS);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("chartArea", (int)DmlChartStyleItem.CHART_AREA);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("dataLabel", (int)DmlChartStyleItem.DATA_LABEL);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("dataLabelCallout", (int)DmlChartStyleItem.DATA_LABEL_CALLOUT);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("dataPoint", (int)DmlChartStyleItem.DATA_POINT);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("dataPoint3D", (int)DmlChartStyleItem.DATA_POINT_3_D);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("dataPointLine", (int)DmlChartStyleItem.DATA_POINT_LINE);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("dataPointMarker", (int)DmlChartStyleItem.DATA_POINT_MARKER);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("dataPointWireframe", (int)DmlChartStyleItem.DATA_POINT_WIREFRAME);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("dataTable", (int)DmlChartStyleItem.DATA_TABLE);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("downBar", (int)DmlChartStyleItem.DOWN_BAR);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("dropLine", (int)DmlChartStyleItem.DROP_LINE);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("errorBar", (int)DmlChartStyleItem.ERROR_BAR);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("floor", (int)DmlChartStyleItem.FLOOR);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("gridlineMajor", (int)DmlChartStyleItem.GRIDLINE_MAJOR);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("gridlineMinor", (int)DmlChartStyleItem.GRIDLINE_MINOR);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("hiLoLine", (int)DmlChartStyleItem.HIGH_LOW_LINE);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("leaderLine", (int)DmlChartStyleItem.LEADER_LINE);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("legend", (int)DmlChartStyleItem.LEGEND);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("plotArea", (int)DmlChartStyleItem.PLOT_AREA);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("plotArea3D", (int)DmlChartStyleItem.PLOT_AREA_3_D);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("seriesAxis", (int)DmlChartStyleItem.SERIES_AXIS);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("seriesLine", (int)DmlChartStyleItem.SERIES_LINE);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("title", (int)DmlChartStyleItem.TITLE);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("trendline", (int)DmlChartStyleItem.TREND_LINE);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("trendlineLabel", (int)DmlChartStyleItem.TRENDLINE_LABEL);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("upBar", (int)DmlChartStyleItem.UP_BAR);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("valueAxis", (int)DmlChartStyleItem.VALUE_AXIS);
        G_ELEMENT_TO_STYLE_ITEM_MAP.add("wall", (int)DmlChartStyleItem.WALL);

    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"id",
		"dataPointMarkerLayout",
		"extLst",
		"mods",
		"lnRef",
		"lineWidthScale",
		"fillRef",
		"effectRef",
		"fontRef",
		"spPr",
		"defRPr",
		"bodyPr"
	);

}

