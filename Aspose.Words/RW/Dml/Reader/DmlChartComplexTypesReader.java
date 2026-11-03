// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.IntList;
import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.ms.System.Guid;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.XmlUtilPal;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Class is used to read complex types upon reading charts.
/// </summary>
class DmlChartComplexTypesReader extends DmlReaderBase
{
    private DmlChartComplexTypesReader()
    {
    }

    /// <summary>
    /// Reads CT_TextBody complex type that defines specifies text formatting.
    /// </summary>
    static void readChartTxPr(DocxDocumentReaderBase reader, DmlChartTxPr txPr) throws Exception
    {
        while (reader.getXmlReader().readChild("txPr"))
        {
            switch (gStringSwitchMap.of(reader.getXmlReader().getLocalName()))
            {
                case /*"bodyPr"*/0:
                {
                    txPr.setBodyPr(new DmlTextBodyProperties());
                    DmlTextShapeReader.readTextBodyProperties(reader, txPr.getBodyPr(),
                        reader.getComplianceInfo());
                    break;
                }
                case /*"lstStyle"*/1:
                    // According to the specs this element is not supported within chart, but anyway lets read it.
                    DmlTextShapeReader.readListStyles(reader, txPr.getLstStyle());
                    break;
                case /*"p"*/2:
                    DmlParagraph paragraph = txPr.addParagraph();
                    DmlTextShapeReader.readParagraph(paragraph, reader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader.getXmlReader());
                    break;
            }
        }
    }

    /// <summary>
    /// Reads CT_ShapeProperties complex type that contains Shape properties for charts.
    /// 5.7.2.198 spPr (Shape Properties)
    /// </summary>
    static void readChartSpPr(DocxDocumentReaderBase reader, DmlChartSpPr spPr) throws Exception
    {
        OoxmlComplianceInfo compliance = reader.getComplianceInfo();

        while (reader.getXmlReader().readChild("spPr"))
        {
            switch (gStringSwitchMap.of(reader.getXmlReader().getLocalName()))
            {
                case /*"blipFill"*/3:
                case /*"gradFill"*/4:
                case /*"grpFill"*/5:
                case /*"noFill"*/6:
                case /*"pattFill"*/7:
                case /*"solidFill"*/8:
                    spPr.setFill(DmlFillReader.read(reader));
                    break;
                case /*"ln"*/9:
                    spPr.setOutline(DmlOutlineReader.read(reader));
                    break;
                case /*"effectLst"*/10:
                    spPr.setEffects(DmlShapeEffectReader.readEffects(reader, false, false));
                    break;
                case /*"effectDag"*/11:
                    spPr.setEffects(DmlShapeEffectReader.readEffects(reader, false, true));
                    break;
                case /*"scene3d"*/12:
                    spPr.setScene3DProp(DmlScene3DReader.readScene3DProperties(reader, compliance));
                    break;
                case /*"sp3d"*/13:
                    spPr.setShape3DProp(DmlScene3DReader.readShape3DProperties(reader, compliance));
                    break;
                case /*"extLst"*/14:
                    spPr.setExtensions(DmlExtensionListReader.read(reader));
                    break;

                // According to the spec custGeom, prstGeom, scene3d, and xfrm are not supported in charts.
                case /*"custGeom"*/15:
                case /*"xfrm"*/16:
                case /*"prstGeom"*/17:
                // Other are not supported by our code, so ignore.
                default:
                    warnUnexpectedAndIgnoreElement(reader.getXmlReader());
                    break;
            }
        }
    }

    /// <summary>
    /// There are several elements in charts that have only SpPr as child nodes and no attributes.
    /// Use this method to read such elements.
    /// </summary>
    static void readChartSpPrContainer(DocxDocumentReaderBase reader, DmlChartSpPr spPr) throws Exception
    {
        String tagName = reader.getXmlReader().getLocalName();

        while (reader.getXmlReader().readChild(tagName))
        {
            switch (gStringSwitchMap.of(reader.getXmlReader().getLocalName()))
            {
                case /*"spPr"*/18:
                    readChartSpPr(reader, spPr);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader.getXmlReader());
                    break;
            }
        }
    }

    /// <summary>
    /// Reads CT_Legend complex type that specifies the legend.
    /// </summary>
    static ChartLegend readChartLegend(DocxDocumentReaderBase reader, DmlChartFormat chartFormat) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        ChartLegend legend = new ChartLegend(chartFormat);
        legend.isVisible(true);
        if (msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false)))
        {
            // Default position is different in Word 2016 charts.
            legend.setSidePosition(ChartLegend.CHART_EX_DEFAULT_SIDE_POSITION);
        }

        // Read attributes of the 2.24.3.54 CT_Legend complex type [MS-ODRAWXML].
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"pos"*/19:
                    legend.setSidePosition(DmlChartsEnum.dmlToSidePosition(xmlReader.getValue(), ChartLegend.CHART_EX_DEFAULT_SIDE_POSITION));
                    break;
                case /*"align"*/20:
                    legend.setPositionAlignment(DmlChartsEnum.dmlToPositionAlignment(xmlReader.getValue()));
                    break;
                case /*"overlay"*/21:
                    legend.setOverlay(xmlReader.getValueAsBool());
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        xmlReader.moveToElement();
        while (xmlReader.readChild("legend"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"layout"*/22:
                    legend.setLayout(readChartLayout(reader));
                    break;
                case /*"legendEntry"*/23:
                    legend.addEntry(readChartLegendEntry(reader));
                    break;
                case /*"legendPos"*/24:
                    legend.setPositionInternal(DmlChartsEnum.dmlToLegendPosition(xmlReader.readAttribute("")));
                    break;
                case /*"overlay"*/21:
                    legend.setOverlay(xmlReader.readBoolAttribute(true));
                    break;
                case /*"spPr"*/18:
                    readChartSpPr(reader, legend.getSpPr());
                    break;
                case /*"txPr"*/25:
                    readChartTxPr(reader, legend.getTxPr());
                    break;
                case /*"extLst"*/14:
                    ((IDmlExtensionListSource)legend).setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return legend;
    }

    /// <summary>
    /// Reads CT_LegendEntry complex type that specifies a legend entry.
    /// 5.7.2.95 legendEntry (Legend Entry)
    /// </summary>
    private static ChartLegendEntry readChartLegendEntry(DocxDocumentReaderBase reader) throws Exception
    {
        ChartLegendEntry entry = new ChartLegendEntry();
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("legendEntry"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"delete"*/26:
                    entry.isHidden(xmlReader.readBoolAttribute(true));
                    break;
                case /*"idx"*/27:
                    entry.setIndex(xmlReader.readIntAttribute(0));
                    break;
                case /*"txPr"*/25:
                    readChartTxPr(reader, entry.getTxPr());
                    break;
                case /*"extLst"*/14:
                    ((IDmlExtensionListSource)entry).setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return entry;
    }

    /// <summary>
    /// Reads CT_PivotFmts complex type that contains a collection of formatting bands for a surface
    /// chart indexed from low to high.
    /// 5.7.2.144 pivotFmts (Pivot Formats)
    /// </summary>
    static DmlChartPivotFormats readChartPivotFormats(DocxDocumentReaderBase reader) throws Exception
    {
        DmlChartPivotFormats pivotFormats = new DmlChartPivotFormats();

        while (reader.getXmlReader().readChild("pivotFmts"))
        {
            switch (gStringSwitchMap.of(reader.getXmlReader().getLocalName()))
            {
                case /*"pivotFmt"*/28:
                    pivotFormats.addFmt(readChartPivotFormat(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader.getXmlReader());
                    break;
            }
        }

        return pivotFormats;
    }

    /// <summary>
    /// Reads CT_PivotFmt complex type that contains a set of formatting to be applied to the chart
    /// that is based on a pivotTable.
    /// 5.7.2.143 pivotFmt (Pivot Format)
    /// </summary>
    private static DmlChartPivotFormat readChartPivotFormat(DocxDocumentReaderBase reader) throws Exception
    {
        DmlChartPivotFormat pivotFormat = new DmlChartPivotFormat(reader.getDocument().getThemeInternal());
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("pivotFmt"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"dLbl"*/29:
                    pivotFormat.setDLbl(readChartDataLabel(reader, null, null));
                    break;
                case /*"idx"*/27:
                    pivotFormat.setIndex(xmlReader.readIntAttribute(0));
                    break;
                case /*"marker"*/30:
                    readChartMarker(reader, pivotFormat.getMarker());
                    break;
                case /*"spPr"*/18:
                    readChartSpPr(reader, pivotFormat.getSpPr());
                    break;
                case /*"txPr"*/25:
                    readChartTxPr(reader, pivotFormat.getTxPr());
                    break;
                case /*"extLst"*/14:
                    pivotFormat.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return pivotFormat;
    }

    /// <summary>
    /// Reads CT_View3D complex type that specifies the 3-D view of the chart.
    /// </summary>
    static DmlChartView3D readChartView3D(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        DmlChartView3D view3D = new DmlChartView3D();

        while (xmlReader.readChild("view3D"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"depthPercent"*/31:
                    view3D.setDepthPercent(xmlReader.readIntAttribute(100));
                    break;
                case /*"hPercent"*/32:
                    view3D.setHPercent(xmlReader.readIntAttribute(100));
                    break;
                case /*"perspective"*/33:
                    view3D.setPerspective(xmlReader.readIntAttribute(30));
                    break;
                case /*"rAngAx"*/34:
                    view3D.setRAngAx(xmlReader.readBoolAttribute(true));
                    break;
                case /*"rotX"*/35:
                    view3D.setRotX(xmlReader.readIntAttribute(0));
                    break;
                case /*"rotY"*/36:
                    view3D.setRotY(xmlReader.readIntAttribute(0));
                    break;
                case /*"extLst"*/14:
                    view3D.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return view3D;
    }

    /// <summary>
    /// Reads CT_Protection complex type that specifies protection for the chart.
    /// 5.7.2.150 protection (Protection)
    /// </summary>
    static DmlChartProtection readChartProtection(NrxXmlReader reader)
    {
        DmlChartProtection protection = new DmlChartProtection();

        while (reader.readChild("protection"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"chartObject"*/37:
                    protection.setChartObject(reader.readBoolAttribute(true));
                    break;
                case /*"data"*/38:
                    protection.setData(reader.readBoolAttribute(true));
                    break;
                case /*"formatting"*/39:
                    protection.setFormatting(reader.readBoolAttribute(true));
                    break;
                case /*"selection"*/40:
                    protection.setSelection(reader.readBoolAttribute(true));
                    break;
                case /*"userInterface"*/41:
                    protection.setUserInterface(reader.readBoolAttribute(true));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }

        return protection;
    }

    /// <summary>
    /// Reads CT_PivotSource complex type that specifies the source pivot table for a pivot chart.
    /// 5.7.2.145 pivotSource (Pivot Source)
    /// </summary>
    static DmlChartPivotSource readChartPivotSource(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        DmlChartPivotSource pivotSource = new DmlChartPivotSource();

        while (xmlReader.readChild("pivotSource"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"fmtId"*/42:
                    pivotSource.setFmtId(xmlReader.readIntAttribute(0));
                    break;
                case /*"name"*/43:
                    pivotSource.setName(xmlReader.readString());
                    break;
                case /*"extLst"*/14:
                    pivotSource.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return pivotSource;
    }

    /// <summary>
    /// Reads CT_UpDownBars complex type that specifies the up and down bars..
    /// 5.7.2.219 upDownBars (Up/Down Bars)
    /// </summary>
    static void readChartUpDownBars(DocxDocumentReaderBase reader, DmlChartUpDownBars upDownBars) throws Exception
    {
        upDownBars.isVisible(true);
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("upDownBars"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"downBars"*/44:
                {
                    upDownBars.hasDownBars(true);
                    readChartSpPrContainer(reader, upDownBars.getDownBars());
                    break;
                }
                case /*"gapWidth"*/45:
                    upDownBars.setGapWidth(xmlReader.readIntAttribute(150));
                    break;
                case /*"upBars"*/46:
                {
                    upDownBars.hasUpBars(true);
                    readChartSpPrContainer(reader, upDownBars.getUpBars());
                    break;
                }
                case /*"extLst"*/14:
                    upDownBars.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads CT_CustSplit complex type that contains the custom split information for a pie-of-pie or
    /// bar-of-pie chart with a custom split type.
    /// 5.7.2.35 custSplit (Custom Split)
    /// </summary>
    static int[] readChartCustomSplit(NrxXmlReader reader)
    {
        IntList splitsList = IntList.createAllocated();

        while (reader.readChild("custSplit"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"secondPiePt"*/47:
                    splitsList.add(reader.readIntAttribute(0));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }

        return splitsList.toArray();
    }

    /// <summary>
    /// Reads CT_BandFmts complex type that contains a collection of formatting bands for a surface chart
    /// indexed from low to high.
    /// 5.7.2.14 bandFmts (Band Formats)
    /// </summary>
    static void readChartBandFmts(DocxDocumentReaderBase reader, DmlChartBandFormats bandFmts) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("bandFmts"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"bandFmt"*/48:
                    readChartBandFmt(reader, bandFmts);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads CT_BandFmt complex type that specifies the formatting band of a surface chart.
    /// 5.7.2.13 bandFmt (Band Format)
    /// </summary>
    private static void readChartBandFmt(DocxDocumentReaderBase reader, DmlChartBandFormats bandFmts) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        int index = 0;
        DmlChartSpPr spPr = new DmlChartSpPr();

        while (xmlReader.readChild("bandFmt"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"idx"*/27:
                    index = xmlReader.readIntAttribute(0);
                    break;
                case /*"spPr"*/18:
                    readChartSpPr(reader, spPr);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        bandFmts.addFmt(index, spPr);
    }

    /// <summary>
    /// Reads CT_DTable complex type that specifies a data table.
    /// 5.7.2.54 dTable (Data Table)
    /// </summary>
    static ChartDataTable readChartDataTable(DocxDocumentReaderBase reader, DmlChartPlotArea plotArea) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        ChartDataTable dt = new ChartDataTable(plotArea);
        // If a data table element is present, the data table is shown.
        dt.setShowInternal(true);

        while (xmlReader.readChild("dTable"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"showHorzBorder"*/49:
                    dt.hasHorizontalBorder(xmlReader.readBoolAttribute(true));
                    break;
                case /*"showKeys"*/50:
                    dt.hasLegendKeys(xmlReader.readBoolAttribute(true));
                    break;
                case /*"showOutline"*/51:
                    dt.hasOutlineBorder(xmlReader.readBoolAttribute(true));
                    break;
                case /*"showVertBorder"*/52:
                    dt.hasVerticalBorder(xmlReader.readBoolAttribute(true));
                    break;
                case /*"spPr"*/18:
                    readChartSpPr(reader, dt.getSpPr());
                    break;
                case /*"txPr"*/25:
                    readChartTxPr(reader, dt.getTxPr());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return dt;
    }

    /// <summary>
    /// Reads CT_Surface complex type that specifies the back wall, side wall or floor of the chart.
    /// 5.7.2.11 backWall (Back Wall)
    /// 5.7.2.192 sideWall (Side Wall)
    /// 5.7.2.69 floor (Floor)
    /// </summary>
    static DmlChartSurface readChartSurface(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        String tagName = xmlReader.getLocalName();
        DmlChartSurface surface = new DmlChartSurface();

        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"thickness"*/53:
                    surface.setThickness(xmlReader.readIntAttribute(0));
                    break;
                case /*"pictureOptions"*/54:
                    surface.setPictureOptions(readChartPictureOptions(xmlReader));
                    break;
                case /*"spPr"*/18:
                    readChartSpPr(reader, surface.getSpPr());
                    break;
                case /*"extLst"*/14:
                    surface.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return surface;
    }

    /// <summary>
    /// Reads CT_Title complex type that specifies a title.
    /// 5.7.2.211 title (Title) ECMA-376 and 2.24.3.12 CT_ChartTitle [MS-ODRAWXML]
    /// </summary>
    static DmlChartTitle readChartTitle(IDmlChartTitleHolder chartTitleHolder, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        DmlChartTitle title = new DmlChartTitle(chartTitleHolder);

        // Read attributes of the 2.24.3.12 CT_ChartTitle complex type [MS-ODRAWXML].
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"pos"*/19:
                    title.setSidePosition(DmlChartsEnum.dmlToSidePosition(xmlReader.getValue(), SidePosition.TOP));
                    break;
                case /*"align"*/20:
                    title.setPositionAlignment(DmlChartsEnum.dmlToPositionAlignment(xmlReader.getValue()));
                    break;
                case /*"overlay"*/21:
                    title.setOverlay(xmlReader.getValueAsBool());
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        xmlReader.moveToElement();
        while (xmlReader.readChild("title"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"layout"*/22:
                    title.setLayout(readChartLayout(reader));
                    break;
                case /*"overlay"*/21:
                    title.setOverlay(xmlReader.readBoolAttribute(true));
                    break;
                case /*"spPr"*/18:
                    title.setSpPr(new DmlChartSpPr());
                    readChartSpPr(reader, title.getSpPr());
                    break;
                case /*"tx"*/55:
                    title.setTx(readChartTx(reader));
                    break;
                case /*"txPr"*/25:
                    readChartTxPr(reader, title.getTxPr());
                    break;
                case /*"extLst"*/14:
                    ((IDmlExtensionListSource)title).setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return title;
    }

    /// <summary>
    /// Reads CT_NumFmt complex type that specifies number formatting for the parent element.
    /// 5.7.2.122 numFmt (Number Format).
    /// </summary>
    static DmlChartNumFormat readChartNumFormat(NrxXmlReader reader)
    {
        DmlChartNumFormat numFormat = new DmlChartNumFormat();

        // Read attributes.
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"formatCode"*/56:
                    numFormat.setFormatCode(reader.getValue());
                    break;
                case /*"sourceLinked"*/57:
                    numFormat.setSourceLinked(reader.getValueAsBool());
                    break;
                default:
                    warnUnexpected(reader);
                    break;
            }
        }

        return numFormat;
    }

    /// <summary>
    /// Reads CT_Scaling complex type that contains additional axis settings.
    /// 5.7.2.161 scaling (Scaling)
    /// </summary>
    static void readChartScaling(DocxDocumentReaderBase reader, AxisScaling scaling) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        // Read children
        while (xmlReader.readChild("scaling"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"logBase"*/58:
                {
                    double value = xmlReader.readDoubleAttribute(Double.NaN);
                    if (!Double.isNaN(value))
                        scaling.setLogBaseWithoutCheck(value);
                    break;
                }
                case /*"max"*/59:
                    scaling.setMaximum(new AxisBound(xmlReader.readDoubleAttribute(0.0)));
                    break;
                case /*"min"*/60:
                    scaling.setMinimum(new AxisBound(xmlReader.readDoubleAttribute(0.0)));
                    break;
                case /*"orientation"*/61:
                    scaling.setOrientation(DmlChartsEnum.dmlToAxisOrientation(xmlReader.readAttribute("minMax")));
                    break;
                case /*"extLst"*/14:
                    ((IDmlExtensionListSource)scaling).setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads the catScaling element of the 2.24.3.8 CT_CategoryAxisScaling complex type [MS-ODRAWXML].
    /// It contains additional axis settings.
    /// </summary>
    static void readCategoryScaling(NrxXmlReader reader, AxisScaling scaling)
    {
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"gapWidth"*/45:
                    readDoubleOrAutomatic(reader, scaling.getGapWidth());
                    break;
                default:
                    warnUnexpected(reader);
                    break;
            }
        }

        reader.moveToElement();
    }

    /// <summary>
    /// Reads the valScaling element of the 2.24.3.82 CT_ValueAxisScaling complex type [MS-ODRAWXML].
    /// It contains additional axis settings.
    /// </summary>
    static void readValueScaling(NrxXmlReader reader, ChartAxis axis)
    {
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"max"*/59:
                    axis.getScaling().setMaximum(readAxisBound(reader));
                    break;
                case /*"min"*/60:
                    axis.getScaling().setMinimum(readAxisBound(reader));
                    break;
                case /*"majorUnit"*/62:
                    readDoubleOrAutomatic(reader, axis.getMajorUnitPr());
                    break;
                case /*"minorUnit"*/63:
                    readDoubleOrAutomatic(reader, axis.getMinorUnitPr());
                    break;
                default:
                    warnUnexpected(reader);
                    break;
            }
        }

        reader.moveToElement();
    }

    /// <summary>
    /// Reads value of the <see cref="DoubleOrAutomatic"/> type from elements of the ST_DoubleOrAutomatic,
    /// ST_ValueAxisUnit, ST_GapWidthRatio simple types [MS-ODRAWXML].
    /// </summary>
    private static void readDoubleOrAutomatic(NrxXmlReader reader, DoubleOrAutomatic instance)
    {
        if ("auto".equals(reader.getValue()))
            instance.isAuto(true);
        else
            instance.setValue(FormatterPal.tryParseDoubleInvariant(reader.getValue()));
    }

    /// <summary>
    /// Reads value of the <see cref="AxisBound"/> type.
    /// </summary>
    private static AxisBound readAxisBound(NrxXmlReader reader)
    {
        if ("auto".equals(reader.getValue()))
            return AxisBound.AUTO;
        else
            return new AxisBound(FormatterPal.tryParseDoubleInvariant(reader.getValue()));
    }

    /// <summary>
    /// Reads CT_DispUnits complex type that specifies the scaling value of the display units for the value axis.
    /// 5.7.2.45 dispUnits (Display Units).
    /// </summary>
    static AxisDisplayUnit readChartDisplayUnits(IDmlChartTitleHolder chartTitleHolder,
        DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        AxisDisplayUnit displayUnits = new AxisDisplayUnit();
        String tagName = xmlReader.getLocalName();
        boolean isEmpty = true;
        boolean isChartEx =
            msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false));

        if (isChartEx)
        {
            String units = xmlReader.readAttribute("unit", "");
            if (StringUtil.hasChars(units))
            {
                displayUnits.setUnit(DmlChartsEnum.dmlToBuiltInUnit(units));
                isEmpty = false;
            }
        }

        // Read children
        while (xmlReader.readChild(tagName))
        {
            isEmpty = false;

            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"builtInUnit"*/64:
                    displayUnits.setUnit(DmlChartsEnum.dmlToBuiltInUnit(xmlReader.readAttribute("thousands")));
                    break;
                case /*"custUnit"*/65:
                    displayUnits.setCustomUnit(xmlReader.readDoubleAttribute(0.0));
                    break;
                case /*"dispUnitsLbl"*/66:
                case /*"unitsLabel"*/67: // 2.24.3.6 CT_AxisUnitsLabel [MS-ODRAWXML]
                    displayUnits.setLabel(readChartDisplayUnitsLabel(chartTitleHolder, reader));
                    break;
                case /*"extLst"*/14:
                    ((IDmlExtensionListSource)displayUnits).setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        // In case of empty 'dispUnits' element we return null since no processing of display units is required in this case.
        return isEmpty ? null : displayUnits;
    }


    /// <summary>
    /// Reads CT_DispUnitsLbl complex type that specifies the display unit label for the value axis in the specified chart.
    /// 5.7.2.46 dispUnitsLbl (Display Units Label).
    /// </summary>
    private static DmlChartDisplayUnitsLabel readChartDisplayUnitsLabel(IDmlChartTitleHolder chartTitleHolder,
        DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlChartDisplayUnitsLabel label = new DmlChartDisplayUnitsLabel(chartTitleHolder);
        String tagName = xmlReader.getLocalName();

        // Read children
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"layout"*/22:
                    label.setLayout(readChartLayout(reader));
                    break;
                case /*"spPr"*/18:
                    readChartSpPr(reader, label.getSpPr());
                    break;
                case /*"tx"*/55:
                    label.setTx(readChartTx(reader));
                    break;
                case /*"txPr"*/25:
                    readChartTxPr(reader, label.getTxPr());
                    break;
                case /*"extLst"*/14: // in 2.24.3.6 CT_AxisUnitsLabel complex type [MS-ODRAWXML]
                    ((IDmlExtensionListSource)label).setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return label;
    }

    /// <summary>
    /// Reads CT_Layout complex type that specifies how the chart element is placed on the chart.
    /// 5.7.2.88 layout (Layout).
    /// </summary>
    static DmlChartManualLayout readChartLayout(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        // There can be empty 'layout', in this case behavior should be the same as if 'layout' is not set at all.
        // So do not create an instance of DmlChartManualLayout if 'manualLayout' does not exist.
        DmlChartManualLayout layout = null;

        // Read children
        while (xmlReader.readChild("layout"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"manualLayout"*/68:
                    layout = new DmlChartManualLayout();
                    readChartManualLayout(reader, layout);
                    break;
                case /*"extLst"*/14:
                    warnNotSupportedAndIgnoreElement(xmlReader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return layout;
    }

    /// <summary>
    /// Reads CT_ManualLayout complex type properties that specifies the exact position of a chart element.
    /// </summary>
    private static void readChartManualLayout(DocxDocumentReaderBase reader, DmlChartManualLayout layout) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        // Read children
        while (xmlReader.readChild("manualLayout"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"h"*/69:
                    layout.setHeight(xmlReader.readDoubleAttribute(0.0));
                    break;
                case /*"w"*/70:
                    layout.setWidth(xmlReader.readDoubleAttribute(0.0));
                    break;
                case /*"x"*/71:
                    layout.setLeft(xmlReader.readDoubleAttribute(0.0));
                    break;
                case /*"y"*/72:
                    layout.setTop(xmlReader.readDoubleAttribute(0.0));
                    break;
                case /*"hMode"*/73:
                    layout.setHeightMode(DmlChartsEnum.dmlToLayoutMode(xmlReader.readAttribute("factor")));
                    break;
                case /*"wMode"*/74:
                    layout.setWidthMode(DmlChartsEnum.dmlToLayoutMode(xmlReader.readAttribute("factor")));
                    break;
                case /*"xMode"*/75:
                    layout.setLeftMode(DmlChartsEnum.dmlToLayoutMode(xmlReader.readAttribute("factor")));
                    break;
                case /*"yMode"*/76:
                    layout.setTopMode(DmlChartsEnum.dmlToLayoutMode(xmlReader.readAttribute("factor")));
                    break;
                case /*"layoutTarget"*/77:
                    layout.setLayoutTarget(DmlChartsEnum.dmlToLayoutTarget(xmlReader.readAttribute("outer")));
                    break;
                case /*"extLst"*/14:
                    layout.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads CT_SerTx or CT_Tx complex type that specifies text to use on a chart, including rich text formatting
    /// or text for a series name, without rich text formatting.
    /// 5.7.2.215 tx (Chart Text)
    /// 5.7.2.216 tx (Series Text)
    /// </summary>
    static DmlChartTx readChartTx(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlChartTx tx = new DmlChartTx();

        // Read children
        while (xmlReader.readChild("tx"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"rich"*/78:
                    tx.setRichText(new DmlTextBody());
                    DmlTextShapeReader.readTextBody(reader, tx.getRichText(), "rich");
                    break;
                case /*"v"*/79:
                    tx.setPlainText(xmlReader.readString());
                    break;
                case /*"strRef"*/80:
                    tx.setStrRef(readChartValRef(reader));
                    break;
                case /*"txData"*/81: // 2.24.3.79 CT_TextData [MS-ODRAWXML]
                    readTxData(reader, tx);
                    break;
                case /*"extLst"*/14:
                    tx.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return tx;
    }

    /// <summary>
    /// Reads the txData element of the 2.24.3.79 CT_TextData complex type [MS-ODRAWXML].
    /// </summary>
    private static void readTxData(DocxDocumentReaderBase reader, DmlChartTx tx)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        // Read children
        while (xmlReader.readChild("txData"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"f"*/82:
                {
                    tx.setFormula(new DmlChartFormula());
                    readFormula(xmlReader, tx.getFormula());
                    break;
                }
                case /*"v"*/79:
                    tx.setPlainText(xmlReader.readString());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads extension 2.6.1.3 datalabelsRange
    /// </summary>
    static DmlChartValueRef readChartDataLabelsRange(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        DmlChartValueRef valueRef = null;
        while (xmlReader.readChild("ext"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"datalabelsRange"*/83:
                    valueRef = readChartValRef(reader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return valueRef;
    }

    /// <summary>
    /// Reads uniqueId extension (http://schemas.microsoft.com/office/drawing/2014/chart).
    /// </summary>
    static Guid readUniqueIdExtension(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        Guid uniqueId = Guid.Empty;

        while (xmlReader.readChild("ext"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"uniqueId"*/84:
                {
                    String id = xmlReader.readAttribute("val", "");
                    if (!"".equals(id))
                        uniqueId = new Guid(id);
                    break;
                }
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return uniqueId;
    }

    /// <summary>
    /// Reads CT_StrRef or CT_NumRef complex type that specifies a reference to data for a single data
    /// label or title with a cache of the last values used.
    /// 5.7.2.202 strRef (String Reference)
    /// 5.7.2.124 1 numRef (Number Reference)
    /// The method also reads elements of the CT_NumericDimension and CT_StringDimension complex types [MS-ODRAWXML].
    /// </summary>
    static DmlChartValueRef readChartValRef(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        String tagName = xmlReader.getLocalName();

        /*DmlChartValueType*/int valueType = getRefValueType(tagName);
        DmlChartValueRef dataRef = new DmlChartValueRef(valueType);
        int levelIndex = 0;

        dataRef.setDimensionType(DmlChartsEnum.dmlToDimensionType(xmlReader.readAttribute("type", "")));

        // Read children
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"f"*/82:
                {
                    // The dir attribute of the 2.24.3.28 CT_Formula complex type [MS-ODRAWXML].
                    readFormula(xmlReader, dataRef.getFormula());
                    break;
                }
                case /*"nf"*/85:
                {
                    // nf element of the CT_NumericDimension and CT_StringDimension complex types [MS-ODRAWXML]
                    readFormula(xmlReader, dataRef.getNameFormula());
                    break;
                }
                case /*"strCache"*/86:
                case /*"dlblRangeCache"*/87:
                    readChartValuesCache(reader, dataRef.getValues());
                    break;
                case /*"numCache"*/88:
                    readChartValuesCache(reader, dataRef.getValues());
                    break;
                case /*"multiLvlStrCache"*/89:
                    readChartMultiLvlValuesCache(reader, dataRef.getValues());
                    break;
                case /*"lvl"*/90:
                {
                    // lvl element of the CT_NumericDimension and CT_StringDimension complex types [MS-ODRAWXML]
                    readLevel(xmlReader, dataRef.getValues(), "numDim".equals(tagName), levelIndex);
                    levelIndex++;
                    break;
                }
                case /*"extLst"*/14:
                    dataRef.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return dataRef;
    }

    /// <summary>
    /// Reads the formula element (21.2.2.65 f [ISO/IEC 29500-1] or 2.24.3.28 CT_Formula [MS-ODRAWXML]).
    /// </summary>
    private static void readFormula(NrxXmlReader xmlReader, DmlChartFormula formula)
    {
        formula.setDirection(DmlChartsEnum.dmlToFormulaDirection(xmlReader.readAttribute("dir", "")));
        formula.setValue(xmlReader.readString());
    }

    private static /*DmlChartValueType*/int getRefValueType(String tagName)
    {
        switch (gStringSwitchMap.of(tagName))
        {
            case /*"multiLvlStrRef"*/91:
            case /*"strDim"*/92: // chartEx schema
                return DmlChartValueType.MULTI_LVL_STRING;
            case /*"numDim"*/93: // chartEx schema
                return DmlChartValueType.MULTI_LVL_NUMERIC;
            case /*"numRef"*/94:
                return DmlChartValueType.NUMERIC;
            case /*"strRef"*/80:
            case /*"datalabelsRange"*/83:
                return DmlChartValueType.STRING;
            default:
                return DmlChartValueType.NONE;
        }
    }

    /// <summary>
    /// Reads CT_StrData complex type that specifies the last string data used for a chart.
    /// 5.7.2.200 strCache (String Cache)
    /// </summary>
    static void readChartMultiLvlValuesCache(DocxDocumentReaderBase reader, DmlChartValueCollection data) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String tagName = xmlReader.getLocalName();
        int levelIndex = 0;

        // Read children
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"ptCount"*/95:
                    data.setValueCount(xmlReader.readIntAttribute(0));
                    break;
                case /*"lvl"*/90:
                    readLevel(xmlReader, data, false, levelIndex);
                    levelIndex++;
                    break;
                case /*"extLst"*/14:
                    data.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Read level of multi-level value cache.
    /// </summary>
    private static void readLevel(NrxXmlReader reader, DmlChartValueCollection data, boolean isNumeric, int levelIndex)
    {
        if (msString.equals(reader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false)))
        {
            // Read attributes of the CT_NumericLevel and CT_StringLevel complex types [MS-ODRAWXML].
            DmlChartDataLevelProperties levelProperties = new DmlChartDataLevelProperties();
            while (reader.moveToNextAttribute())
            {
                switch (gStringSwitchMap.of(reader.getLocalName()))
                {
                    case /*"ptCount"*/95:
                        levelProperties.setValueCount(reader.getValueAsInt());
                        break;
                    case /*"formatCode"*/56:
                        levelProperties.setFormatCode(reader.getValue());
                        break;
                    case /*"name"*/43:
                        levelProperties.setName(reader.getValue());
                        break;
                    default:
                        warnUnexpected(reader);
                        break;
                }
            }

            while (data.getLevelProperties().size() < levelIndex)
                data.addLevelProperties(null);

            data.addLevelProperties(levelProperties);
        }

        reader.moveToElement();
        while (reader.readChild("lvl"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"pt"*/96:
                {
                    DmlChartValue val = readChartValPoint(reader,
                        isNumeric ? DmlChartValueType.NUMERIC : DmlChartValueType.STRING);
                    DmlChartMultiLvlValue multiLvlValue = (DmlChartMultiLvlValue)data.get(val.getIndex());

                    if (isNumeric)
                    {
                        if (multiLvlValue == null)
                        {
                            multiLvlValue = new DmlChartMultiLvlNumValue(val.getIndex(), data.getLevelProperties());
                            data.add(multiLvlValue);
                        }

                        multiLvlValue.addLevelValue(val.getValue(), levelIndex);
                    }
                    else
                    {
                        if (multiLvlValue == null)
                        {
                            multiLvlValue = new DmlChartMultiLvlStrValue(val.getIndex());
                            data.add(multiLvlValue);
                        }

                        multiLvlValue.addLevelValue(val.getStringValue(), levelIndex);
                    }
                    break;
                }
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads CT_StrData complex type that specifies the last string data used for a chart.
    /// 5.7.2.200 strCache (String Cache)
    /// </summary>
    static void readChartValuesCache(DocxDocumentReaderBase reader, DmlChartValueCollection data) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        String tagName = xmlReader.getLocalName();

        // Read children
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"formatCode"*/56:
                    data.setFormatCode(xmlReader.readString());
                    break;
                case /*"ptCount"*/95:
                {
                    data.setValueCount(xmlReader.readIntAttribute(0));
                    break;
                }
                case /*"pt"*/96:
                    data.add(readChartValPoint(xmlReader, data.getValueType()));
                    break;
                case /*"extLst"*/14:
                    data.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        // Inherit format code for numeric values from data collection.
        if (data.getValueType() == DmlChartValueType.NUMERIC)
        {
            for (DmlChartValue value : data)
            {
                DmlChartNumValue numValue = (DmlChartNumValue)value;
                // WORDSNET-15228 Reset format code only if it is not specified explicitly.
                // See TestJira14993.
                if (!StringUtil.hasChars(numValue.getFormatCode()))
                    numValue.setFormatCode(data.getFormatCode());
            }
        }
    }

    /// <summary>
    /// Reads CT_StrVal complex type that specifies string data for a specific data point.
    /// 5.7.2.152 pt (String Point)
    /// </summary>
    static DmlChartValue readChartValPoint(NrxXmlReader reader, /*DmlChartValueType*/int pointType)
    {
        int idx = 0;
        String strValue = null;
        String formatCode = null;
        boolean isChartEx = msString.equals(reader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false));

        // Read attributes.
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"idx"*/27:
                    idx = reader.getValueAsInt();
                    break;
                case /*"formatCode"*/56:
                    formatCode = reader.getValue();
                    break;
                default:
                    warnUnexpected(reader);
                    break;
            }
        }

        reader.moveToElement();
        if (isChartEx)
        {
            strValue = reader.readString();
        }
        else
        {
            // Read children
            while (reader.readChild("pt"))
            {
                switch (gStringSwitchMap.of(reader.getLocalName()))
                {
                    case /*"v"*/79:
                        strValue = reader.readString();
                        break;
                    default:
                        warnUnexpectedAndIgnoreElement(reader);
                        break;
                }
            }
        }

        if (pointType == DmlChartValueType.STRING)
        {
            return new DmlChartStrValue(idx, strValue);
        }
        else
        {
            // If NaN value encountered, it will be removed later.
            double val = FormatterPal.tryParseDoubleInvariant(strValue);
            return new DmlChartNumValue(idx, val, formatCode);
        }
    }

    /// <summary>
    /// Reads CT_DPt complex type that specifies a single data point.
    /// 5.7.2.52 dPt (Data Point)
    /// </summary>
    static ChartDataPoint readChartDataPoint(DocxDocumentReaderBase reader, DmlChart chart) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        ChartDataPoint dataPoint = new ChartDataPoint(chart);
        String tagName = xmlReader.getLocalName();

        if (msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false)))
            dataPoint.getPointPr().setProperty(DmlChartDataPointAttr.INDEX, xmlReader.readIntAttribute("idx", 0));

        // Read children
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"bubble3D"*/97:
                    dataPoint.getPointPr().setProperty(DmlChartDataPointAttr.BUBBLE_3_D, xmlReader.readBoolAttribute(true));
                    break;
                case /*"explosion"*/98:
                    dataPoint.getPointPr().setProperty(DmlChartDataPointAttr.EXPLOSION, xmlReader.readIntAttribute(0));
                    break;
                case /*"idx"*/27:
                    dataPoint.getPointPr().setProperty(DmlChartDataPointAttr.INDEX, xmlReader.readIntAttribute(0));
                    break;
                case /*"invertIfNegative"*/99:
                    dataPoint.getPointPr().setProperty(DmlChartDataPointAttr.INVERT_IF_NEGATIVE, xmlReader.readBoolAttribute(true));
                    break;
                case /*"marker"*/30:
                    ChartMarker marker = new ChartMarker(chart);
                    readChartMarker(reader, marker);
                    dataPoint.getPointPr().setProperty(DmlChartDataPointAttr.MARKER, marker);
                    break;
                case /*"pictureOptions"*/54:
                    dataPoint.getPointPr().setProperty(DmlChartDataPointAttr.PICTURE_OPTIONS, readChartPictureOptions(xmlReader));
                    break;
                case /*"spPr"*/18:
                    DmlChartSpPr spPr = new DmlChartSpPr();
                    readChartSpPr(reader, spPr);
                    dataPoint.getPointPr().setProperty(DmlChartDataPointAttr.SP_PR, spPr);
                    break;
                case /*"extLst"*/14:
                    dataPoint.getPointPr().setProperty(DmlChartDataPointAttr.EXTENSIONS, DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return dataPoint;
    }

    /// <summary>
    /// Reads CT_PictureOptions complex type that specifies the picture to be used on the data point,
    /// series, wall, or floor.
    /// 5.7.2.139 pictureOptions (Picture Options)
    /// </summary>
    static DmlChartPictureOptions readChartPictureOptions(NrxXmlReader reader)
    {
        DmlChartPictureOptions pictureOptions = new DmlChartPictureOptions();

        // Read children
        while (reader.readChild("pictureOptions"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"applyToEnd"*/100:
                    pictureOptions.setApplyToEnd(reader.readBoolAttribute(true));
                    break;
                case /*"applyToFront"*/101:
                    pictureOptions.setApplyToFront(reader.readBoolAttribute(true));
                    break;
                case /*"applyToSides"*/102:
                    pictureOptions.setApplyToSides(reader.readBoolAttribute(true));
                    break;
                case /*"pictureFormat"*/103:
                    pictureOptions.setPictureFormat(DmlChartsEnum.dmlToPictureFormat(reader.readAttribute("stack")));
                    break;
                case /*"pictureStackUnit"*/104:
                    pictureOptions.setPictureStackUnit(reader.readDoubleAttribute(0.0d));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }

        return pictureOptions;
    }

    /// <summary>
    /// Reads CT_Marker complex type that specifies a data marker.
    /// 5.7.2.106 marker (Marker)
    /// </summary>
    static void readChartMarker(DocxDocumentReaderBase reader, ChartMarker marker) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        // Read children
        while (xmlReader.readChild("marker"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"size"*/105:
                    marker.getMarkerPr().setProperty(DmlChartMarkerAttr.SIZE, xmlReader.readIntAttribute(7));
                    break;
                case /*"spPr"*/18:
                    DmlChartSpPr spPr = new DmlChartSpPr();
                    readChartSpPr(reader, spPr);
                    marker.getMarkerPr().setProperty(DmlChartMarkerAttr.SP_PR, spPr);
                    break;
                case /*"symbol"*/106:
                    marker.getMarkerPr().setProperty(DmlChartMarkerAttr.SYMBOL, DmlChartsEnum.dmlToMarkerStyle(xmlReader.readAttribute("none")));
                    break;
                case /*"extLst"*/14:
                    marker.getMarkerPr().setProperty(DmlChartMarkerAttr.EXTENSIONS, DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads CT_DLbls complex type that serves as a root element
    /// that specifies the settings for the data labels for an entire series or the entire chart.
    /// 5.7.2.106 marker (Marker)
    /// </summary>
    static ChartDataLabelCollection readChartDataLabelCollection(DocxDocumentReaderBase reader,
        DmlChart chart, ChartSeries series) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        ChartDataLabelCollection labelCollection = new ChartDataLabelCollection(chart, series);

        if (series != null)
            series.setHasDataLabels(true);

        DmlChartDataLabelPr labelPr = labelCollection.getLabelPr();
        String tagName = xmlReader.getLocalName();

        if (msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false)))
        {
            String position = xmlReader.readAttribute("pos", "");
            if (StringUtil.hasChars(position))
                labelPr.setProperty(DmlChartDataLabelAttrs.D_LBL_POS, DmlChartsEnum.dmlToDataLabelPosition(position));
        }

        // Read children
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"delete"*/26:
                case /*"dLblPos"*/107:
                case /*"extLst"*/14:
                case /*"numFmt"*/108:
                case /*"separator"*/109:
                case /*"showBubbleSize"*/110:
                case /*"showCatName"*/111:
                case /*"showLeaderLines"*/112:
                case /*"showLegendKey"*/113:
                case /*"showPercent"*/114:
                case /*"showSerName"*/115:
                case /*"showVal"*/116:
                case /*"spPr"*/18:
                case /*"txPr"*/25:
                case /*"leaderLines"*/117:
                case /*"visibility"*/118: // in the 2.24.3.19 CT_DataLabels complex type [MS-ODRAWXML]
                    readChartDataLabelOption(reader, labelPr, false);
                    break;
                case /*"dLbl"*/29:
                case /*"dataLabel"*/119: // in the 2.24.3.19 CT_DataLabels complex type [MS-ODRAWXML]
                    labelCollection.addLabel(readChartDataLabel(reader, labelPr, series));
                    break;
                case /*"dataLabelHidden"*/120: // in the 2.24.3.19 CT_DataLabels complex type [MS-ODRAWXML]
                    int index = xmlReader.readIntAttribute("idx", -1);
                    if (index >= 0)
                        labelCollection.get(index).isHidden(true);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        labelCollection.normalizeAfterLoad();

        return labelCollection;
    }

    private static ChartDataLabel readChartDataLabel(DocxDocumentReaderBase reader, DmlChartDataLabelPr parentLabelPr,
        ChartSeries series) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        ChartDataLabel label = new ChartDataLabel(parentLabelPr, series);
        String tagName = xmlReader.getLocalName();

        if (msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false)))
        {
            while (xmlReader.moveToNextAttribute())
            {
                switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                {
                    case /*"idx"*/27:
                        label.setIndex(xmlReader.getValueAsInt());
                        break;
                    case /*"pos"*/19:
                        label.getLabelPr().setProperty(DmlChartDataLabelAttrs.D_LBL_POS,
                            DmlChartsEnum.dmlToDataLabelPosition(xmlReader.getValue()));
                        break;
                    default:
                        warnUnexpected(xmlReader);
                        break;
                }
            }

            xmlReader.moveToElement();
        }

        while (xmlReader.readChild(tagName))
            readChartDataLabelOption(reader, label.getLabelPr(), false);

        return label;
    }

    /// <summary>
    /// Reads the extension of the dLbls/dLbl elements (2.2.1.2 and 2.2.1.3 of [MS-ODRAWXML]).
    /// </summary>
    static DmlChartDataLabelPr readChartExtDataLabelPr(DocxDocumentReaderBase reader) throws Exception
    {
        DmlChartDataLabelPr pr = new DmlChartDataLabelPr(null, reader.getDocument(), false);

        while (reader.getXmlReader().readChild("ext"))
            readChartDataLabelOption(reader, pr, true);

        return pr;
    }

    private static void readChartDataLabelOption(DocxDocumentReaderBase reader, DmlChartDataLabelPr labelPr,
        boolean isExtension) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"delete"*/26:
                labelPr.setProperty(DmlChartDataLabelAttrs.DELETE, xmlReader.readBoolAttribute(true));
                break;
            case /*"dLblPos"*/107:
                labelPr.setProperty(DmlChartDataLabelAttrs.D_LBL_POS,
                    DmlChartsEnum.dmlToDataLabelPosition(xmlReader.readAttribute("center")));
                break;
            case /*"idx"*/27:
                labelPr.setProperty(DmlChartDataLabelAttrs.IDX, xmlReader.readIntAttribute(0));
                break;
            case /*"layout"*/22:
                labelPr.setProperty(DmlChartDataLabelAttrs.LAYOUT, readChartLayout(reader));
                break;
            case /*"numFmt"*/108:
                labelPr.setProperty(DmlChartDataLabelAttrs.NUM_FMT, readChartNumFormat(xmlReader));
                break;
            case /*"separator"*/109:
                labelPr.setProperty(DmlChartDataLabelAttrs.SEPARATOR, xmlReader.readString());
                break;
            case /*"showBubbleSize"*/110:
                labelPr.setProperty(DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE, xmlReader.readBoolAttribute(true));
                break;
            case /*"showCatName"*/111:
                labelPr.setProperty(DmlChartDataLabelAttrs.SHOW_CAT_NAME, xmlReader.readBoolAttribute(true));
                break;
            case /*"showLegendKey"*/113:
                labelPr.setProperty(DmlChartDataLabelAttrs.SHOW_LEGEND_KEY, xmlReader.readBoolAttribute(true));
                break;
            case /*"showPercent"*/114:
                labelPr.setProperty(DmlChartDataLabelAttrs.SHOW_PERCENT, xmlReader.readBoolAttribute(true));
                break;
            case /*"showSerName"*/115:
                labelPr.setProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME, xmlReader.readBoolAttribute(true));
                break;
            case /*"showVal"*/116:
                labelPr.setProperty(DmlChartDataLabelAttrs.SHOW_VAL, xmlReader.readBoolAttribute(true));
                break;
            case /*"showLeaderLines"*/112:
                labelPr.setProperty(DmlChartDataLabelAttrs.SHOW_LEADER_LINES, xmlReader.readBoolAttribute(true));
                break;
            case /*"showDataLabelsRange"*/121:
                labelPr.setProperty(DmlChartDataLabelAttrs.SHOW_DATA_LABELS_RANGE, xmlReader.readBoolAttribute(true));
                break;
            case /*"spPr"*/18:
            {
                if (isExtension)
                {
                    DmlChartShapeProperties shapeProperties = new DmlChartShapeProperties();
                    DmlNodePropertiesReader.readShapeProperties(reader, shapeProperties);
                    labelPr.setProperty(DmlChartDataLabelAttrs.SHAPE_PR, shapeProperties);
                }
                else
                {
                    DmlChartSpPr spPr = new DmlChartSpPr();
                    readChartSpPr(reader, spPr);
                    labelPr.setProperty(DmlChartDataLabelAttrs.SP_PR, spPr);
                }
                break;
            }
            case /*"tx"*/55:
                labelPr.setProperty(DmlChartDataLabelAttrs.TX, readChartTx(reader));
                break;
            case /*"txPr"*/25:
                DmlChartTxPr txPr = new DmlChartTxPr();
                readChartTxPr(reader, txPr);
                labelPr.setProperty(DmlChartDataLabelAttrs.TX_PR, txPr);
                break;
            case /*"leaderLines"*/117:
                DmlChartSpPr leaderLines = new DmlChartSpPr();
                readChartSpPrContainer(reader, leaderLines);
                labelPr.setProperty(DmlChartDataLabelAttrs.LEADER_LINES, leaderLines);
                break;
            case /*"visibility"*/118: // in the 2.24.3.19 CT_DataLabels complex type [MS-ODRAWXML]
            {
                labelPr.setProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME,
                    xmlReader.readBoolAttribute("seriesName", false));
                labelPr.setProperty(DmlChartDataLabelAttrs.SHOW_CAT_NAME,
                    xmlReader.readBoolAttribute("categoryName", false));
                labelPr.setProperty(DmlChartDataLabelAttrs.SHOW_VAL,
                    xmlReader.readBoolAttribute("value", true));
                break;
            }
            case /*"xForSave"*/122:
                labelPr.setProperty(DmlChartDataLabelAttrs.X_FOR_SAVE, xmlReader.readBoolAttribute(true));
                break;
            case /*"dlblFieldTable"*/123:
                readDataLabelFieldTable(xmlReader, labelPr);
                break;
            case /*"extLst"*/14:
                labelPr.setProperty(DmlChartDataLabelAttrs.EXTENSIONS, DmlExtensionListReader.read(reader));
                break;
            default:
                warnUnexpectedAndIgnoreElement(xmlReader);
                break;
        }
    }

    /// <summary>
    /// Reads the [MS-ODRAWXML] 2.6.1.4 dlblFieldTable element.
    /// </summary>
    private static void readDataLabelFieldTable(NrxXmlReader xmlReader, DmlChartDataLabelPr labelPr)
    {
        // The property contains a list of field table entries. Each entry corresponds to a text field
        // in a data label whose value is obtained from a formula reference.
        // MS Word has a bug that if there is a space between '</c15:dlblFieldTableCache>' and '</c15:dlblFTEntry>'
        // element close tags, it raises an error while opening the document. Thus, let's read and store entries as
        // plain XML for now.
        // Cannot use XmlUtilPal.ReadOuterXml for the entire dlblFieldTable element because it moves the reader to
        // the next element.

        final String TAG_NAME = "dlblFieldTable";
        Debug.assertTrue(TAG_NAME.equals(xmlReader.getLocalName()));

        ArrayList<String> fieldTable = new ArrayList<String>();
        xmlReader.readChild(TAG_NAME);

        while (!TAG_NAME.equals(xmlReader.getLocalName()))
        {
            String xml = XmlUtilPal.readOuterXml(xmlReader.getUnderlyingReader());
            msArrayList.add(fieldTable, xml);
        }

        if (fieldTable.size() > 0)
            labelPr.setProperty(DmlChartDataLabelAttrs.FIELD_TABLE, fieldTable);
    }

    /// <summary>
    /// Reads CT_ErrBars complex type that specifies error bars.
    /// 5.7.2.55 errBars (Error Bars)
    /// </summary>
    static DmlChartErrorBars readChartErrorBars(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlChartErrorBars errorBars = new DmlChartErrorBars();

        // Read children
        while (xmlReader.readChild("errBars"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"errBarType"*/124:
                    errorBars.setErrBarType(DmlChartsEnum.dmlToErrorBarType(xmlReader.readAttribute("")));
                    break;
                case /*"errDir"*/125:
                    errorBars.setErrDir(DmlChartsEnum.dmlToErrorBarDirection(xmlReader.readAttribute("")));
                    break;
                case /*"errValType"*/126:
                    errorBars.setErrValType(DmlChartsEnum.dmlToErrorValueType(xmlReader.readAttribute("")));
                    break;
                case /*"minus"*/127:
                    readChartDataSource(reader, errorBars.getMinus().getDataSource());
                    break;
                case /*"plus"*/128:
                    readChartDataSource(reader, errorBars.getPlus().getDataSource());
                    break;
                case /*"noEndCap"*/129:
                    errorBars.setNoEndCap(xmlReader.readBoolAttribute(true));
                    break;
                case /*"spPr"*/18:
                    readChartSpPr(reader, errorBars.getSpPr());
                    break;
                case /*"val"*/130:
                    errorBars.setVal(xmlReader.readDoubleAttribute(0.0d));
                    break;
                case /*"extLst"*/14:
                    errorBars.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return errorBars;
    }

    static void readChartDataSource(DocxDocumentReaderBase reader, DmlChartDataSource dataSource) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        String tagName = xmlReader.getLocalName();

        // Read children
        while (xmlReader.readChild(tagName))
        {
            if ("AlternateContent".equals(xmlReader.getLocalName()))
            {
                readDataSourceAlternateContent(reader, dataSource);
            }
            else
            {
                DmlChartValueRef valueRef = readChartDataSourceValueRef(reader);
                if (valueRef != null)
                    dataSource.setValueRef(valueRef);
            }
        }
    }

    /// <summary>
    /// Reads an AlternateContent element that is used to represent multi-level string literal data in
    /// pre-Word 2016 charts.
    /// </summary>
    private static void readDataSourceAlternateContent(DocxDocumentReaderBase reader, DmlChartDataSource dataSource) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String tagName = xmlReader.getLocalName();

        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"Choice"*/131:
                    readDataSourceAlternateContentChoice(reader, dataSource);
                    break;
                case /*"Fallback"*/132:
                    readDataSourceAlternateContentFallback(reader, dataSource);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads a Choice part of an AlternateContent element that is used to represent multi-level string literal data
    /// in pre-Word 2016 charts.
    /// </summary>
    private static void readDataSourceAlternateContentChoice(DocxDocumentReaderBase reader,
        DmlChartDataSource dataSource) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("Choice"))
        {
            DmlChartValueRef valueRef = readChartDataSourceValueRef(reader);
            if (valueRef != null)
                dataSource.setValueRef(valueRef);
        }
    }

    /// <summary>
    /// Reads a Fallback part of an AlternateContent element that is used to represent multi-level string literal data
    /// in pre-Word 2016 charts.
    /// </summary>
    private static void readDataSourceAlternateContentFallback(DocxDocumentReaderBase reader,
        DmlChartDataSource dataSource) throws Exception
    {
        Debug.assertTrue(dataSource.getValues() != null);

        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("Fallback"))
        {
            DmlChartValueRef valueRef = readChartDataSourceValueRef(reader);
            if (valueRef != null)
                dataSource.setFallbackValueRef(valueRef);
        }
    }

    /// <summary>
    /// Reads reference or literal data of a chart.
    /// </summary>
    private static DmlChartValueRef readChartDataSourceValueRef(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"strLit"*/133:
            {
                DmlChartValueCollection values = new DmlChartValueCollection(DmlChartValueType.STRING);
                readChartValuesCache(reader, values);
                return new DmlChartValueRef(values);
            }
            case /*"multiLvlStrLit"*/134:
            {
                DmlChartValueCollection values = new DmlChartValueCollection(DmlChartValueType.MULTI_LVL_STRING);
                readChartMultiLvlValuesCache(reader, values);
                return new DmlChartValueRef(values);
            }
            case /*"numLit"*/135:
            {
                DmlChartValueCollection values = new DmlChartValueCollection(DmlChartValueType.NUMERIC);
                readChartValuesCache(reader, values);
                return new DmlChartValueRef(values);
            }
            case /*"strRef"*/80:
            case /*"numRef"*/94:
            case /*"multiLvlStrRef"*/91:
            case /*"datalabelsRange"*/83:
                return readChartValRef(reader);
            default:
                warnUnexpectedAndIgnoreElement(xmlReader);
                return null;
        }
    }

    /// <summary>
    /// Reads CT_Trendline complex type that specifies a trendline.
    /// 5.7.2.212 trendline (Trendlines)
    /// </summary>
    static DmlChartTrendline readChartTrendline(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlChartTrendline trendline = new DmlChartTrendline();

        // Read children
        while (xmlReader.readChild("trendline"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"backward"*/136:
                    trendline.setBackward(xmlReader.readDoubleAttribute(0.0));
                    break;
                case /*"dispEq"*/137:
                    trendline.setDispEq(xmlReader.readBoolAttribute(true));
                    break;
                case /*"dispRSqr"*/138:
                    trendline.setDispRSqr(xmlReader.readBoolAttribute(true));
                    break;
                case /*"forward"*/139:
                    trendline.setForward(xmlReader.readDoubleAttribute(0.0));
                    break;
                case /*"intercept"*/140:
                    trendline.setIntercept(xmlReader.readDoubleAttribute(0.0));
                    break;
                case /*"name"*/43:
                    trendline.setName(xmlReader.readString());
                    break;
                case /*"order"*/141:
                    trendline.setOrder(xmlReader.readIntAttribute(0));
                    break;
                case /*"period"*/142:
                    trendline.setPeriod(xmlReader.readIntAttribute(0));
                    break;
                case /*"spPr"*/18:
                    readChartSpPr(reader, trendline.getSpPr());
                    break;
                case /*"trendlineLbl"*/143:
                    trendline.setTrendlineLbl(readChartTrendlineLbl(reader));
                    break;
                case /*"trendlineType"*/144:
                    trendline.setTrendlineType(DmlChartsEnum.dmlToTrendlineType(xmlReader.readAttribute("")));
                    break;
                case /*"extLst"*/14:
                    trendline.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return trendline;
    }

    /// <summary>
    /// Reads CT_TrendlineLbl complex type that specifies the label for the trendline.
    /// 5.7.2.213 trendlineLbl (Trendline Label).
    /// </summary>
    private static DmlChartTrendlineLabel readChartTrendlineLbl(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlChartTrendlineLabel label = new DmlChartTrendlineLabel();

        // Read children
        while (xmlReader.readChild("trendlineLbl"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"layout"*/22:
                    label.setLayout(readChartLayout(reader));
                    break;
                case /*"numFmt"*/108:
                    label.setNumFmt(readChartNumFormat(xmlReader));
                    break;
                case /*"spPr"*/18:
                    readChartSpPr(reader, label.getSpPr());
                    break;
                case /*"tx"*/55:
                    label.setTx(readChartTx(reader));
                    break;
                case /*"txPr"*/25:
                    readChartTxPr(reader, label.getTxPr());
                    break;
                case /*"extLst"*/14:
                    label.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return label;
    }

    /// <summary>
    /// Reads elements of the 2.24.3.26 CT_FormatOverride complex type [MS-ODRAWXML].
    /// </summary>
    static DmlChartFormatOverride readFormatOverride(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        DmlChartFormatOverride formatOverrid = new DmlChartFormatOverride();
        formatOverrid.setIndex(xmlReader.readIntAttribute("idx", 0));

        // Read children
        while (xmlReader.readChild("fmtOvr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"spPr"*/18:
                {
                    formatOverrid.setSpPr(new DmlChartSpPr());
                    readChartSpPr(reader, formatOverrid.getSpPr());
                    break;
                }
                case /*"extLst"*/14:
                    formatOverrid.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return formatOverrid;
    }

    /// <summary>
    /// Reads an element of the 2.24.3.81 CT_TickMarks complex type [MS-ODRAWXML].
    /// </summary>
    static void readTickMarks(DocxDocumentReaderBase reader, DmlChartAxisPr axisPr,
        /*DmlChartAxisAttrs*/int typeAttribute, /*DmlChartAxisAttrs*/int extensionAttribute) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String tagName = xmlReader.getLocalName();

        axisPr.setProperty(typeAttribute, DmlChartsEnum.dmlToTickMark(xmlReader.readAttribute("type", "")));

        // Read children
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"extLst"*/14:
                    axisPr.setProperty(extensionAttribute, DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads the valueColors element of the 2.24.3.86 CT_ValueColors complex type [MS-ODRAWXML].
    /// </summary>
    static DmlChartValueColors readValueColors(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlChartValueColors colors = new DmlChartValueColors();

        // Read children
        while (xmlReader.readChild("valueColors"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"minColor"*/145:
                    colors.setMinimumColor(DmlColorReader.readColor(xmlReader, reader.getComplianceInfo()));
                    break;
                case /*"midColor"*/146:
                    colors.setMiddleColor(DmlColorReader.readColor(xmlReader, reader.getComplianceInfo()));
                    break;
                case /*"maxColor"*/147:
                    colors.setMaximumColor(DmlColorReader.readColor(xmlReader, reader.getComplianceInfo()));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return colors;
    }

    /// <summary>
    /// Reads the layoutPr element of the 2.24.3.72 CT_SeriesLayoutProperties complex type [MS-ODRAWXML].
    /// </summary>
    static void readLayoutProperties(DocxDocumentReaderBase reader, DmlChartSeriesLayoutPr layoutPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        // Read children
        while (xmlReader.readChild("layoutPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"parentLabelLayout"*/148:
                {
                    layoutPr.setProperty(DmlChartSeriesLayoutAttr.PARENT_LABEL_LAYOUT,
                        DmlChartsEnum.dmlToParentLabelLayout(xmlReader.readAttribute("")));
                    break;
                }
                case /*"regionLabelLayout"*/149:
                {
                    layoutPr.setProperty(DmlChartSeriesLayoutAttr.REGION_LABEL_LAYOUT,
                        DmlChartsEnum.dmlToRegionLabelLayout(xmlReader.readAttribute("")));
                    break;
                }
                case /*"visibility"*/118:
                    readSeriesElementVisibilities(xmlReader, layoutPr);
                    break;
                case /*"aggregation"*/150:
                {
                    layoutPr.setProperty(DmlChartSeriesLayoutAttr.IS_AGGREGATION,
                        true);
                    break;
                }
                case /*"binning"*/151:
                {
                    layoutPr.setProperty(DmlChartSeriesLayoutAttr.BINNING,
                        readBinning(xmlReader));
                    break;
                }
                case /*"statistics"*/152:
                {
                    layoutPr.setProperty(DmlChartSeriesLayoutAttr.QUARTILE_METHOD,
                        DmlChartsEnum.dmlToQuartileMethod(xmlReader.readAttribute("quartileMethod", "")));
                    break;
                }
                case /*"subtotals"*/153:
                {
                    layoutPr.setProperty(DmlChartSeriesLayoutAttr.SUBTOTALS,
                        readSubtotals(xmlReader));
                    break;
                }
                case /*"extLst"*/14:
                {
                    layoutPr.setProperty(DmlChartSeriesLayoutAttr.EXTENSIONS,
                        DmlExtensionListReader.read(reader));
                    break;
                }
                case /*"geography"*/154:
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads an element of the 2.24.3.71 CT_SeriesElementVisibilities complex type [MS-ODRAWXML].
    /// </summary>
    private static void readSeriesElementVisibilities(NrxXmlReader xmlReader, DmlChartSeriesLayoutPr layoutPr)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"connectorLines"*/155:
                    layoutPr.setProperty(DmlChartSeriesLayoutAttr.IS_CONNECTOR_LINES_VISIBLE, xmlReader.getValueAsBool());
                    break;
                case /*"meanLine"*/156:
                    layoutPr.setProperty(DmlChartSeriesLayoutAttr.IS_MEAN_LINE_VISIBLE, xmlReader.getValueAsBool());
                    break;
                case /*"meanMarker"*/157:
                    layoutPr.setProperty(DmlChartSeriesLayoutAttr.IS_MEAN_MARKER_VISIBLE, xmlReader.getValueAsBool());
                    break;
                case /*"nonoutliers"*/158:
                    layoutPr.setProperty(DmlChartSeriesLayoutAttr.IS_NON_OUTLIERS_VISIBLE, xmlReader.getValueAsBool());
                    break;
                case /*"outliers"*/159:
                    layoutPr.setProperty(DmlChartSeriesLayoutAttr.IS_OUTLIERS_VISIBLE, xmlReader.getValueAsBool());
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        xmlReader.moveToElement();
    }

    /// <summary>
    /// Reads the binning element of the 2.24.3.7 CT_Binning complex type [MS-ODRAWXML].
    /// </summary>
    private static DmlChartBinningPr readBinning(NrxXmlReader xmlReader)
    {
        DmlChartBinningPr binningPr = new DmlChartBinningPr();

        // Read attributes
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"intervalClosed"*/160:
                {
                    binningPr.setProperty(DmlChartBinningAttr.INTERVAL_CLOSED,
                        DmlChartsEnum.dmlToIntervalClosedSide(xmlReader.getValue()));
                    break;
                }
                case /*"underflow"*/161:
                    readDoubleOrAutomatic(xmlReader,
                        (DoubleOrAutomatic)binningPr.getProperty(DmlChartBinningAttr.UNDERFLOW));
                    break;
                case /*"overflow"*/162:
                    readDoubleOrAutomatic(xmlReader,
                        (DoubleOrAutomatic)binningPr.getProperty(DmlChartBinningAttr.OVERFLOW));
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }
        xmlReader.moveToElement();

        // Read children
        while (xmlReader.readChild("binning"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"binSize"*/163:
                    binningPr.setProperty(DmlChartBinningAttr.BIN_SIZE, xmlReader.readDoubleAttribute(1.0));
                    break;
                case /*"binCount"*/164:
                    binningPr.setProperty(DmlChartBinningAttr.BIN_COUNT, xmlReader.readIntAttribute(6));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return binningPr;
    }

    /// <summary>
    /// Reads the subtotals element of the 2.24.3.77 CT_Subtotals complex type [MS-ODRAWXML].
    /// </summary>
    private static ArrayList<Integer> readSubtotals(NrxXmlReader xmlReader)
    {
        ArrayList<Integer> subtotals = new ArrayList<Integer>();

        // Read children
        while (xmlReader.readChild("subtotals"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"idx"*/27:
                    msArrayList.add(subtotals, xmlReader.readIntAttribute(0));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return subtotals;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"bodyPr",
		"lstStyle",
		"p",
		"blipFill",
		"gradFill",
		"grpFill",
		"noFill",
		"pattFill",
		"solidFill",
		"ln",
		"effectLst",
		"effectDag",
		"scene3d",
		"sp3d",
		"extLst",
		"custGeom",
		"xfrm",
		"prstGeom",
		"spPr",
		"pos",
		"align",
		"overlay",
		"layout",
		"legendEntry",
		"legendPos",
		"txPr",
		"delete",
		"idx",
		"pivotFmt",
		"dLbl",
		"marker",
		"depthPercent",
		"hPercent",
		"perspective",
		"rAngAx",
		"rotX",
		"rotY",
		"chartObject",
		"data",
		"formatting",
		"selection",
		"userInterface",
		"fmtId",
		"name",
		"downBars",
		"gapWidth",
		"upBars",
		"secondPiePt",
		"bandFmt",
		"showHorzBorder",
		"showKeys",
		"showOutline",
		"showVertBorder",
		"thickness",
		"pictureOptions",
		"tx",
		"formatCode",
		"sourceLinked",
		"logBase",
		"max",
		"min",
		"orientation",
		"majorUnit",
		"minorUnit",
		"builtInUnit",
		"custUnit",
		"dispUnitsLbl",
		"unitsLabel",
		"manualLayout",
		"h",
		"w",
		"x",
		"y",
		"hMode",
		"wMode",
		"xMode",
		"yMode",
		"layoutTarget",
		"rich",
		"v",
		"strRef",
		"txData",
		"f",
		"datalabelsRange",
		"uniqueId",
		"nf",
		"strCache",
		"dlblRangeCache",
		"numCache",
		"multiLvlStrCache",
		"lvl",
		"multiLvlStrRef",
		"strDim",
		"numDim",
		"numRef",
		"ptCount",
		"pt",
		"bubble3D",
		"explosion",
		"invertIfNegative",
		"applyToEnd",
		"applyToFront",
		"applyToSides",
		"pictureFormat",
		"pictureStackUnit",
		"size",
		"symbol",
		"dLblPos",
		"numFmt",
		"separator",
		"showBubbleSize",
		"showCatName",
		"showLeaderLines",
		"showLegendKey",
		"showPercent",
		"showSerName",
		"showVal",
		"leaderLines",
		"visibility",
		"dataLabel",
		"dataLabelHidden",
		"showDataLabelsRange",
		"xForSave",
		"dlblFieldTable",
		"errBarType",
		"errDir",
		"errValType",
		"minus",
		"plus",
		"noEndCap",
		"val",
		"Choice",
		"Fallback",
		"strLit",
		"multiLvlStrLit",
		"numLit",
		"backward",
		"dispEq",
		"dispRSqr",
		"forward",
		"intercept",
		"order",
		"period",
		"trendlineLbl",
		"trendlineType",
		"minColor",
		"midColor",
		"maxColor",
		"parentLabelLayout",
		"regionLabelLayout",
		"aggregation",
		"binning",
		"statistics",
		"subtotals",
		"geography",
		"connectorLines",
		"meanLine",
		"meanMarker",
		"nonoutliers",
		"outliers",
		"intervalClosed",
		"underflow",
		"overflow",
		"binSize",
		"binCount"
	);

}

