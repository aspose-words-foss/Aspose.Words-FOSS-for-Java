// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.ObjToIntDictionary;


/// <summary>
/// Class is used to read a chart part.
/// This is root class for reading charts.
/// </summary>
class DmlChartReader extends DmlReaderBase
{
    private DmlChartReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    /// <summary>
    /// Builds <see cref="DmlChart"/>, xmlReader current node must be 5.7.2.26 chart (Reference to Chart Part).
    /// </summary>
    static Shape read(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        if (!"chart".equals(xmlReader.getLocalName()))
            return null;

        String chartId = null;

        // Read id of the chart part and get it.
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"id"*/0:
                    chartId = xmlReader.getValue();
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        if (!StringUtil.hasChars(chartId))
            return null;

        DocxXmlReader chartSpaceReader = reader.switchToPartReaderByRelId(chartId);

        Shape chartDml = new Shape(reader.getDocument(), ShapeMarkupLanguage.DML);
        reader.addAndPushContainer(chartDml);

        boolean isChartEx =
            msString.equals(chartSpaceReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false));
        DmlChartSpace chartSpace = new DmlChartSpace(isChartEx);
        chartDml.setDmlNode(chartSpace);
        read(reader, chartSpace);

        // Read theme override if exists.
        chartSpace.setThemeOverride(DocxThemeReader.readThemeOverride(reader));

        boolean isIsoStrict = reader.getComplianceInfo().isIsoStrict();

        if (reader.switchToPartReaderByRelType(chartSpaceReader.getPart(),
                DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_COLOR_STYLE, isIsoStrict)) != null)
        {
            chartSpace.setColorStyle(DmlChartColorStyleReader.read(reader));
            reader.restorePartReader();
        }
        if (reader.switchToPartReaderByRelType(chartSpaceReader.getPart(),
                DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_STYLE, isIsoStrict)) != null)
        {
            chartSpace.setDmlChartStyle(DmlChartStyleReader.read(reader));
            reader.restorePartReader();
        }

        reader.popContainer(NodeType.SHAPE);
        reader.restorePartReader();

        return chartDml;
    }

    /// <summary>
    /// Builds <see cref="DmlChartSpace"/>. xmlReader must read chart part.
    /// </summary>
    static void read(DocxDocumentReaderBase reader, DmlChartSpace chartSpace) throws Exception
    {
        buildChartSpace(reader, chartSpace);
    }

    /// <summary>
    /// Method reads root element of chart part.
    /// </summary>
    private static void buildChartSpace(DocxDocumentReaderBase reader, DmlChartSpace chartSpace) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlChartReader chartReader = new DmlChartReader(reader);

        while (xmlReader.readChild("chartSpace"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"chart"*/1:
                    chartSpace.setChartFormat(chartReader.buildChartFormat());
                    break;
                case /*"date1904"*/2:
                    chartSpace.setDate1904(xmlReader.readBoolAttribute(false));
                    break;
                case /*"externalData"*/3:
                    readExternalData(chartSpace, reader);
                    break;
                case /*"lang"*/4:
                    chartSpace.setLang(xmlReader.readAttribute(""));
                    break;
                case /*"pivotSource"*/5:
                    chartSpace.setPivotSource(DmlChartComplexTypesReader.readChartPivotSource(reader));
                    break;
                case /*"protection"*/6:
                    chartSpace.setProtection(DmlChartComplexTypesReader.readChartProtection(xmlReader));
                    break;
                case /*"roundedCorners"*/7:
                    chartSpace.setRoundedCorners(xmlReader.readBoolAttribute(true));
                    break;
                case /*"spPr"*/8:
                    DmlChartComplexTypesReader.readChartSpPr(reader, chartSpace.getSpPr());
                    break;
                case /*"style"*/9:
                    chartSpace.setStyleIndex(xmlReader.readIntAttribute(0));
                    break;
                case /*"txPr"*/10:
                    DmlChartComplexTypesReader.readChartTxPr(reader, chartSpace.getTxPr());
                    break;
                case /*"userShapes"*/11:
                {
                    String userShapesId = xmlReader.readAttribute("id", "");
                    DmlChartShapesReader.read(reader, userShapesId);
                    break;
                }
                case /*"AlternateContent"*/12:
                    // WORDSNET-8006 Try to read style from fallback.
                    // It seems MS Word 2013 always use AlternateContent to set style of the charts.
                    chartReader.tryReadFallBackStyle(chartSpace);
                    break;
                case /*"clrMapOvr"*/13:
                    chartSpace.setColorMapOverride(readColorMapOverride(reader));
                    break;
                case /*"extLst"*/14:
                    chartSpace.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                case /*"printSettings"*/15:
                    warnNotSupportedAndIgnoreElement(xmlReader);
                    break;
                case /*"chartData"*/16: // 2.24.3.10 CT_ChartData [MS-ODRAWXML]
                    readChartData(chartSpace, reader);
                    break;
                case /*"fmtOvrs"*/17:   // 2.24.3.27 CT_FormatOverrides [MS-ODRAWXML]
                    readFormatOverrides(chartSpace.getFormatOverrides(), reader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads elements of the 2.24.3.10 CT_ChartData complex type [MS-ODRAWXML].
    /// </summary>
    private static void readChartData(DmlChartSpace chartSpace, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        xmlReader.moveToElement();
        while (xmlReader.readChild("chartData"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"externalData"*/3:
                    readExternalData(chartSpace, reader);
                    break;
                case /*"data"*/18:
                    readData(chartSpace, reader);
                    break;
                case /*"extLst"*/14:
                    chartSpace.getData().setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads elements of the 2.24.3.27 CT_FormatOverrides complex type [MS-ODRAWXML].
    /// </summary>
    private static void readFormatOverrides(ArrayList<DmlChartFormatOverride> list, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        xmlReader.moveToElement();
        while (xmlReader.readChild("fmtOvrs"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"fmtOvr"*/19:
                    list.add(DmlChartComplexTypesReader.readFormatOverride(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    private static DmlChartColorMapOverride readColorMapOverride(DocxDocumentReaderBase reader)
    {
        DmlChartColorMapOverride colorMapOverride = new DmlChartColorMapOverride();

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"bg1"*/20:
                    colorMapOverride.Bg1 = xmlReader.getValue();
                    break;
                case /*"tx1"*/21: colorMapOverride.Tx1 = xmlReader.getValue();
                    break;
                case /*"bg2"*/22: colorMapOverride.Bg2 = xmlReader.getValue();
                    break;
                case /*"tx2"*/23: colorMapOverride.Tx2 = xmlReader.getValue();
                    break;
                case /*"accent1"*/24: colorMapOverride.Accent1 = xmlReader.getValue();
                    break;
                case /*"accent2"*/25: colorMapOverride.Accent2 = xmlReader.getValue();
                    break;
                case /*"accent3"*/26: colorMapOverride.Accent3 = xmlReader.getValue();
                    break;
                case /*"accent4"*/27: colorMapOverride.Accent4 = xmlReader.getValue();
                    break;
                case /*"accent5"*/28: colorMapOverride.Accent5 = xmlReader.getValue();
                    break;
                case /*"accent6"*/29: colorMapOverride.Accent6 = xmlReader.getValue();
                    break;
                case /*"hlink"*/30: colorMapOverride.Hlink = xmlReader.getValue();
                    break;
                case /*"folHlink"*/31: colorMapOverride.FolHlink = xmlReader.getValue();
                    break;
                default:
                    break;
            }
        }

        xmlReader.moveToElement();

        while (xmlReader.readChild("clrMapOvr"))
            warnNotSupportedAndIgnoreElement(xmlReader);

        return colorMapOverride;
    }

    private static void readExternalData(DmlChartSpace chartSpace, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String externalDataId = xmlReader.readAttribute("id", "");
        if (!StringUtil.hasChars(externalDataId))
            return;

        // Try reading external data as embedded object first.
        chartSpace.setEmbeddedData(reader.getEmbeddedObject(externalDataId));

        // If embedded data is null, suppose data is linked.
        if (chartSpace.getEmbeddedData() == null)
            chartSpace.setLinkedData(reader.getRelationshipTarget(externalDataId));

        // autoUpdate is attribute in the chartEx scheme.
        chartSpace.setAutoUpdate(xmlReader.readBoolAttribute("autoUpdate", true));

        xmlReader.moveToElement();
        while (xmlReader.readChild("externalData"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"autoUpdate"*/32:
                    chartSpace.setAutoUpdate(xmlReader.readBoolAttribute(true));
                    break;
                default:
                       warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads elements of the 2.24.3.15 CT_Data complex type [MS-ODRAWXML].
    /// </summary>
    private static void readData(DmlChartSpace chartSpace, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        DmlChartData data = new DmlChartData();
        data.setId(xmlReader.readIntAttribute("id", -1));

        while (xmlReader.readChild("data"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"numDim"*/33:
                case /*"strDim"*/34:
                {
                    DmlChartDataSource dataSource = new DmlChartDataSource();
                    dataSource.setValueRef(DmlChartComplexTypesReader.readChartValRef(reader));
                    data.getDataSources().add(dataSource);
                    break;
                }
                case /*"extLst"*/14:
                    data.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        chartSpace.getData().add(data);
    }

    /// <summary>
    /// Method tries to read style from fallback.
    /// IF there is no style set in fallback, simply does nothing.
    /// </summary>
    private void tryReadFallBackStyle(DmlChartSpace chartSpace)
    {
        while (getXmlReader().readChild("AlternateContent"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"Fallback"*/35:
                {
                    while (getXmlReader().readChild("Fallback"))
                    {
                        switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
                        {
                            case /*"style"*/9:
                                chartSpace.setStyleIndex(getXmlReader().readIntAttribute(0));
                                break;
                            default:
                                warnNotSupportedAndIgnoreElement(getXmlReader());
                                break;
                        }
                    }
                    break;
                }
                case /*"Choice"*/36:
                    while (getXmlReader().readChild("Choice"))
                    {
                        switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
                        {
                            case /*"style"*/9:
                                chartSpace.setWord2010Style(getXmlReader().readIntAttribute(0));
                                break;
                            default:
                                warnNotSupportedAndIgnoreElement(getXmlReader());
                                break;
                        }
                    }
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 'chart' element.
    /// </summary>
    private DmlChartFormat buildChartFormat() throws Exception
    {
        DmlChartFormat chartFormat = new DmlChartFormat();
        boolean isChartEx =
            msString.equals(getXmlReader().getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false));

        while (getXmlReader().readChild("chart"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"autoTitleDeleted"*/37:
                    chartFormat.setAutoTitleDeleted(getXmlReader().readBoolAttribute(true));
                    break;
                case /*"backWall"*/38:
                    chartFormat.setBackWall(DmlChartComplexTypesReader.readChartSurface(mDocumentReader));
                    break;
                case /*"dispBlanksAs"*/39:
                    chartFormat.setDispBlanksAs(DmlChartsEnum.dmlToDisplayBlanksAs(getXmlReader().readAttribute("")));
                    break;
                case /*"floor"*/40:
                    chartFormat.setFloor(DmlChartComplexTypesReader.readChartSurface(mDocumentReader));
                    break;
                case /*"legend"*/41:
                    chartFormat.setLegend(DmlChartComplexTypesReader.readChartLegend(mDocumentReader, chartFormat));
                    break;
                case /*"pivotFmts"*/42:
                    chartFormat.setPivotFmts(DmlChartComplexTypesReader.readChartPivotFormats(mDocumentReader));
                    break;
                case /*"plotArea"*/43:
                    chartFormat.setPlotArea(buildPlotArea(chartFormat));
                    break;
                case /*"plotVisOnly"*/44:
                    chartFormat.setPlotVisOnly(getXmlReader().readBoolAttribute(true));
                    break;
                case /*"showDLblsOverMax"*/45:
                    chartFormat.setShowDLblsOverMax(getXmlReader().readBoolAttribute(true));
                    break;
                case /*"sideWall"*/46:
                    chartFormat.setSideWall(DmlChartComplexTypesReader.readChartSurface(mDocumentReader));
                    break;
                case /*"title"*/47:
                    chartFormat.setDCTitle(DmlChartComplexTypesReader.readChartTitle(chartFormat, mDocumentReader));
                    break;
                case /*"view3D"*/48:
                    chartFormat.setView3D(DmlChartComplexTypesReader.readChartView3D(mDocumentReader));
                    break;
                case /*"extLst"*/14:
                    chartFormat.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        if (isChartEx && (chartFormat.getDCTitle() == null))
            chartFormat.setAutoTitleDeleted(true);

        return chartFormat;
    }

    /// <summary>
    /// Reads 'plotArea' element.
    /// </summary>
    private DmlChartPlotArea buildPlotArea(DmlChartFormat chartFormat) throws Exception
    {
        DmlChartPlotArea plotArea = new DmlChartPlotArea(chartFormat);
        if (msString.equals(getXmlReader().getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false)))
        {
            DmlChartExChart chart = new DmlChartExChart();
            // Need to assign a document: the defaults depend on the document version.
            chart.setDocument(mDocumentReader.getDocument());
            plotArea.addChart(chart);
        }

        while (getXmlReader().readChild("plotArea"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"areaChart"*/49:
                case /*"area3DChart"*/50:
                case /*"lineChart"*/51:
                case /*"line3DChart"*/52:
                case /*"stockChart"*/53:
                case /*"radarChart"*/54:
                case /*"scatterChart"*/55:
                case /*"pieChart"*/56:
                case /*"pie3DChart"*/57:
                case /*"doughnutChart"*/58:
                case /*"barChart"*/59:
                case /*"bar3DChart"*/60:
                case /*"ofPieChart"*/61:
                case /*"surfaceChart"*/62:
                case /*"surface3DChart"*/63:
                case /*"bubbleChart"*/64:
                    plotArea.addChart(buildChart(mDocumentReader.getDocument()));
                    break;
                case /*"valAx"*/65:
                case /*"catAx"*/66:
                case /*"dateAx"*/67:
                case /*"serAx"*/68:
                    plotArea.addAxis(DmlChartAxisReader.read(mDocumentReader, plotArea.getFirstChart(), false));
                    break;
                case /*"layout"*/69:
                    plotArea.setLayout(DmlChartComplexTypesReader.readChartLayout(mDocumentReader));
                    break;
                case /*"dTable"*/70:
                    plotArea.setDataTable(DmlChartComplexTypesReader.readChartDataTable(mDocumentReader, plotArea));
                    break;
                case /*"spPr"*/8:
                    DmlChartComplexTypesReader.readChartSpPr(mDocumentReader, plotArea.getSpPr());
                    break;
                case /*"plotAreaRegion"*/71: // 2.24.3.65 CT_PlotAreaRegion [MS-ODRAWXML]
                    readPlotAreaRegion(plotArea);
                    break;
                case /*"axis"*/72: // 2.24.3.3 CT_Axis [MS-ODRAWXML]
                    plotArea.addAxis(DmlChartAxisReader.read(mDocumentReader, plotArea.getFirstChart(), true));
                    break;
                case /*"extLst"*/14:
                    plotArea.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        // WORDSNET-23232 Chart data for the axis may be null when an axis follows before a chart node in the markup.
        // Let's set first chart from the plot area collection as works the logic above.
        for (ChartAxis axis : plotArea.getAxes())
        {
            if (axis.getChart() == null)
                axis.setChart(plotArea.getFirstChart());
        }

        plotArea.initAxisDirection();

        return plotArea;
    }

    /// <summary>
    /// Reads the plotAreaRegion element of the 2.24.3.65 CT_PlotAreaRegion complex type [MS-ODRAWXML].
    /// </summary>
    private void readPlotAreaRegion(DmlChartPlotArea plotArea) throws Exception
    {
        DmlChart chart = plotArea.getFirstChart();
        ObjToIntDictionary<ChartSeries> ownerMap = new ObjToIntDictionary<ChartSeries>();

        while (getXmlReader().readChild("plotAreaRegion"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"plotSurface"*/73:
                    plotArea.setSurface(DmlChartComplexTypesReader.readChartSurface(mDocumentReader));
                    break;
                case /*"series"*/74:
                    chart.getSeries().addForLoad(DmlChartSeriesReader.build(mDocumentReader, chart, ownerMap));
                    break;
                case /*"extLst"*/14:
                    plotArea.setRegionExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        resolveSeriesOwners(chart.getSeries(), ownerMap);
    }

    /// <summary>
    /// Resolves series owners by their indexes.
    /// </summary>
    /// <param name="seriesCollection">A series collection.</param>
    /// <param name="ownerMap">A map of owned series to its owner index.</param>
    private static void resolveSeriesOwners(DmlChartSeriesCollection seriesCollection,
        ObjToIntDictionary<ChartSeries> ownerMap)
    {
        for (ChartSeries series : ownerMap.getKeys())
        {
            int ownerIndex = ownerMap.get(series);
            series.setOwner(seriesCollection.get(ownerIndex));
        }
    }

    private DmlChart buildChart(DocumentBase doc) throws Exception
    {
        String tagName = getXmlReader().getLocalName();
        /*DmlChartType*/int chartType = getChartType(tagName);
        DmlChart chart = DmlChartFactory.createChart(chartType);
        // WORDSNET-20873 The defaults depend on the document version.
        chart.setDocument(doc);

        while (getXmlReader().readChild(tagName))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"axId"*/75:
                    chart.addAxId(getXmlReader().readIntAttribute(0));
                    break;
                case /*"dLbls"*/76:
                    chart.getChartPr().setProperty(DmlChartAttrs.D_LBLS,
                        DmlChartComplexTypesReader.readChartDataLabelCollection(mDocumentReader, chart, null));
                    break;
                case /*"dropLines"*/77:
                {
                    chart.getChartPr().setProperty(DmlChartAttrs.IS_DROP_LINES_VISIBLE, true);
                    DmlChartComplexTypesReader.readChartSpPrContainer(mDocumentReader, (DmlChartSpPr)chart.getChartPr().getProperty(DmlChartAttrs.DROP_LINES));
                    break;
                }
                case /*"ser"*/78:
                {
                    // WORDSNET-7285 Add series to the collection only if it is not empty.
                    // It seems, MS Word ignores series with no data.
                    ChartSeries series = DmlChartSeriesReader.build(mDocumentReader, chart, null);
                    if (series.getValueCount() > 0)
                        chart.getSeries().addForLoad(series);
                    break;
                }
                case /*"varyColors"*/79:
                chart.getChartPr().setProperty(DmlChartAttrs.VARY_COLORS, getXmlReader().readBoolAttribute(true));
                    break;
                case /*"grouping"*/80:
                    chart.getChartPr().setGrouping(DmlChartsEnum.dmlToGrouping(getXmlReader().readAttribute("")));
                    break;
                case /*"gapDepth"*/81:
                    chart.getChartPr().setProperty(DmlChartAttrs.GAP_DEPTH, getXmlReader().readIntAttribute(150));
                    break;
                case /*"barDir"*/82:
                    chart.getChartPr().setProperty(DmlChartAttrs.BAR_DIR, DmlChartsEnum.dmlToBarDirection(getXmlReader().readAttribute("")));
                    break;
                case /*"gapWidth"*/83:
                    chart.getChartPr().setProperty(DmlChartAttrs.GAP_WIDTH, getXmlReader().readIntAttribute(150));
                    break;
                case /*"shape"*/84:
                    chart.getChartPr().setProperty(DmlChartAttrs.SHAPE, DmlChartsEnum.dmlToShape(getXmlReader().readAttribute("")));
                    break;
                case /*"overlap"*/85:
                    chart.getChartPr().setProperty(DmlChartAttrs.OVERLAP, getXmlReader().readIntAttribute(0));
                    break;
                case /*"serLines"*/86:
                    chart.getChartPr().setProperty(DmlChartAttrs.SHOW_SER_LINE, true);
                    DmlChartComplexTypesReader.readChartSpPrContainer(mDocumentReader, (DmlChartSpPr)chart.getChartPr().getProperty(DmlChartAttrs.SER_LINES));
                    break;
                case /*"bubbleScale"*/87:
                    chart.getChartPr().setProperty(DmlChartAttrs.BUBBLE_SCALE, getXmlReader().readIntAttribute(0));
                    break;
                case /*"showNegBubbles"*/88:
                    chart.getChartPr().setProperty(DmlChartAttrs.SHOW_NEG_BUBBLES, getXmlReader().readBoolAttribute(true));
                    break;
                case /*"sizeRepresents"*/89:
                    chart.getChartPr().setProperty(DmlChartAttrs.SIZE_REPRESENTS, DmlChartsEnum.dmlToSizeRepresents(getXmlReader().readAttribute("")));
                    break;
                case /*"firstSliceAng"*/90:
                    chart.getChartPr().setProperty(DmlChartAttrs.FIRST_SLICE_ANG, getXmlReader().readIntAttribute(0));
                    break;
                case /*"holeSize"*/91:
                    chart.getChartPr().setProperty(DmlChartAttrs.HOLE_SIZE, getXmlReader().readIntAttribute(10));
                    break;
                case /*"hiLowLines"*/92:
                {
                    chart.getChartPr().setProperty(DmlChartAttrs.IS_HI_LOW_LINES_VISIBLE, true);
                    DmlChartComplexTypesReader.readChartSpPrContainer(mDocumentReader, (DmlChartSpPr)chart.getChartPr().getProperty(DmlChartAttrs.HI_LOW_LINES));
                    break;
                }
                case /*"marker"*/93:
                chart.getChartPr().setProperty(DmlChartAttrs.SHOW_MARKER, getXmlReader().readBoolAttribute(true));
                    break;
                case /*"smooth"*/94:
                    chart.getChartPr().setProperty(DmlChartAttrs.SMOOTH, getXmlReader().readBoolAttribute(true));
                    break;
                case /*"upDownBars"*/95:
                {
                    DmlChartUpDownBars upDownBars = new DmlChartUpDownBars();
                    DmlChartComplexTypesReader.readChartUpDownBars(mDocumentReader, upDownBars);
                    chart.getChartPr().setProperty(DmlChartAttrs.UP_DOWN_BARS, upDownBars);
                    break;
                }
                case /*"custSplit"*/96:
                    chart.getChartPr().setProperty(DmlChartAttrs.CUST_SPLIT, DmlChartComplexTypesReader.readChartCustomSplit(getXmlReader()));
                    break;
                case /*"ofPieType"*/97:
                    chart.getChartPr().setOfPieType(DmlChartsEnum.dmlToOfPieType(getXmlReader().readAttribute("")));
                    break;
                case /*"secondPieSize"*/98:
                    chart.getChartPr().setProperty(DmlChartAttrs.SECOND_PIE_SIZE, getXmlReader().readIntAttribute(75));
                    break;
                case /*"splitPos"*/99:
                    chart.getChartPr().setProperty(DmlChartAttrs.SPLIT_POS, getXmlReader().readDoubleAttribute(0.0));
                    break;
                case /*"splitType"*/100:
                    chart.getChartPr().setProperty(DmlChartAttrs.SPLIT_TYPE, DmlChartsEnum.dmlToSplitType(getXmlReader().readAttribute("")));
                    break;
                case /*"radarStyle"*/101:
                    chart.getChartPr().setProperty(DmlChartAttrs.RADAR_STYLE, DmlChartsEnum.dmlToRadarStyle(getXmlReader().readAttribute("")));
                    break;
                case /*"scatterStyle"*/102:
                    chart.getChartPr().setProperty(DmlChartAttrs.SCATTER_STYLE, DmlChartsEnum.dmlToScatterStyle(getXmlReader().readAttribute("")));
                    break;
                case /*"bandFmts"*/103:
                    DmlChartComplexTypesReader.readChartBandFmts(mDocumentReader, (DmlChartBandFormats)chart.getChartPr().getProperty(DmlChartAttrs.BAND_FMTS));
                    break;
                case /*"wireframe"*/104:
                    chart.getChartPr().setProperty(DmlChartAttrs.WIREFRAME, getXmlReader().readBoolAttribute(true));
                    break;
                case /*"extLst"*/14:
                    chart.getChartPr().setProperty(DmlChartAttrs.EXTENSIONS, DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        if ((chart instanceof DmlBubbleChart) && (chart.getSeries().getCount() > 0) && chart.getSeries().get(0).getBubble3D())
            ((DmlBubbleChart)chart).setBubble3D(true);

        return chart;
    }

    private static /*DmlChartType*/int getChartType(String tagName)
    {
        switch (gStringSwitchMap.of(tagName))
        {
            case /*"area3DChart"*/50:
                return DmlChartType.AREA_3_D_CHART;
            case /*"areaChart"*/49:
                return DmlChartType.AREA_CHART;
            case /*"bar3DChart"*/60:
                return DmlChartType.BAR_3_D_CHART;
            case /*"barChart"*/59:
                return DmlChartType.BAR_CHART;
            case /*"bubbleChart"*/64:
                return DmlChartType.BUBBLE_CHART;
            case /*"doughnutChart"*/58:
                return DmlChartType.DOUGHNUT_CHART;
            case /*"line3DChart"*/52:
                return DmlChartType.LINE_3_D_CHART;
            case /*"lineChart"*/51:
                return DmlChartType.LINE_CHART;
            case /*"ofPieChart"*/61:
                return DmlChartType.OF_PIE_CHART;
            case /*"pie3DChart"*/57:
                return DmlChartType.PIE_3_D_CHART;
            case /*"pieChart"*/56:
                return DmlChartType.PIE_CHART;
            case /*"radarChart"*/54:
                return DmlChartType.RADAR_CHART;
            case /*"scatterChart"*/55:
                return DmlChartType.SCATTER_CHART;
            case /*"stockChart"*/53:
                return DmlChartType.STOCK_CHART;
            case /*"surface3DChart"*/63:
                return DmlChartType.SURFACE_3_D_CHART;
            case /*"surfaceChart"*/62:
                return DmlChartType.SURFACE_CHART;
            default:
                throw new IllegalArgumentException("Unexpected tag name");
        }
    }

    private NrxXmlReader getXmlReader() { return mDocumentReader.getXmlReader(); }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"id",
		"chart",
		"date1904",
		"externalData",
		"lang",
		"pivotSource",
		"protection",
		"roundedCorners",
		"spPr",
		"style",
		"txPr",
		"userShapes",
		"AlternateContent",
		"clrMapOvr",
		"extLst",
		"printSettings",
		"chartData",
		"fmtOvrs",
		"data",
		"fmtOvr",
		"bg1",
		"tx1",
		"bg2",
		"tx2",
		"accent1",
		"accent2",
		"accent3",
		"accent4",
		"accent5",
		"accent6",
		"hlink",
		"folHlink",
		"autoUpdate",
		"numDim",
		"strDim",
		"Fallback",
		"Choice",
		"autoTitleDeleted",
		"backWall",
		"dispBlanksAs",
		"floor",
		"legend",
		"pivotFmts",
		"plotArea",
		"plotVisOnly",
		"showDLblsOverMax",
		"sideWall",
		"title",
		"view3D",
		"areaChart",
		"area3DChart",
		"lineChart",
		"line3DChart",
		"stockChart",
		"radarChart",
		"scatterChart",
		"pieChart",
		"pie3DChart",
		"doughnutChart",
		"barChart",
		"bar3DChart",
		"ofPieChart",
		"surfaceChart",
		"surface3DChart",
		"bubbleChart",
		"valAx",
		"catAx",
		"dateAx",
		"serAx",
		"layout",
		"dTable",
		"plotAreaRegion",
		"axis",
		"plotSurface",
		"series",
		"axId",
		"dLbls",
		"dropLines",
		"ser",
		"varyColors",
		"grouping",
		"gapDepth",
		"barDir",
		"gapWidth",
		"shape",
		"overlap",
		"serLines",
		"bubbleScale",
		"showNegBubbles",
		"sizeRepresents",
		"firstSliceAng",
		"holeSize",
		"hiLowLines",
		"marker",
		"smooth",
		"upDownBars",
		"custSplit",
		"ofPieType",
		"secondPieSize",
		"splitPos",
		"splitType",
		"radarStyle",
		"scatterStyle",
		"bandFmts",
		"wireframe"
	);

}

