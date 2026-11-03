// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.MathUtil;


/// <summary>
/// Class is used to read axis of charts.
/// 5.7.2.25 catAx (Category Axis Data)
/// 5.7.2.39 dateAx (Date Axis)
/// 5.7.2.176 serAx (Series Axis)
/// 5.7.2.227 valAx (Value Axis)
/// </summary>
class DmlChartAxisReader extends DmlReaderBase
{
    private DmlChartAxisReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    public static ChartAxis read(DocxDocumentReaderBase reader, DmlChart dmlChart, boolean isChartEx) throws Exception
    {
        DmlChartAxisReader chartAxisReader = new DmlChartAxisReader(reader);
        NrxXmlReader xmlReader = reader.getXmlReader();
        String tagName = xmlReader.getLocalName();
        /*ChartAxisType*/int axisType = getAxisType(tagName);

        ChartAxis axis = new ChartAxis(axisType, dmlChart, reader.getDocument(), isChartEx);

        if (axis.isCategory())
            axis.getChartAxisPr().setProperty(DmlChartAxisAttrs.IS_DATE_CATEGORY_AXIS, "dateAx".equals(tagName));

        if (msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false)))
        {
            // Read attributes of the 2.24.3.3 CT_Axis complex type [MS-ODRAWXML].
            while (xmlReader.moveToNextAttribute())
            {
                switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                {
                    case /*"id"*/0:
                        axis.setAxId(xmlReader.getValueAsInt());
                        axis.setDirection(dmlChart.addAxId(axis.getAxId()));
                        break;
                    case /*"hidden"*/1:
                        axis.setHiddenInternal(xmlReader.getValueAsBool());
                        break;
                    default:
                        warnUnexpected(xmlReader);
                        break;
                }
            }

            // If the tickLabels element of the 2.24.3.3 CT_Axis type does not exist, labels are not shown.
            axis.setAreTickLabelsVisible(false);
        }

        xmlReader.moveToElement();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"axId"*/2:
                    axis.setAxId(xmlReader.readIntAttribute(0));
                    break;
                case /*"catScaling"*/3: // 2.24.3.8 CT_CategoryAxisScaling [MS-ODRAWXML]
                {
                    axis.setType(ChartAxisType.CATEGORY);
                    DmlChartComplexTypesReader.readCategoryScaling(xmlReader, axis.getScaling());
                    break;
                }
                case /*"valScaling"*/4: // 2.24.3.82 CT_ValueAxisScaling [MS-ODRAWXML]
                {
                    axis.setType(ChartAxisType.VALUE);
                    DmlChartComplexTypesReader.readValueScaling(xmlReader, axis);
                    break;
                }
                case /*"scaling"*/5:
                    DmlChartComplexTypesReader.readChartScaling(reader, axis.getScaling());
                    break;
                case /*"delete"*/6:
                    axis.setHiddenInternal(xmlReader.readBoolAttribute(true));
                    break;
                case /*"axPos"*/7:
                    axis.setAxPos(DmlChartsEnum.dmlToAxisPosition(xmlReader.readAttribute("")));
                    break;
                case /*"majorGridlines"*/8:
                    axis.setMajorGridlines(chartAxisReader.readGridLines("majorGridlines"));
                    break;
                case /*"minorGridlines"*/9:
                    axis.setMinorGridlines(chartAxisReader.readGridLines("minorGridlines"));
                    break;
                case /*"title"*/10:
                    axis.setTitleInternal(DmlChartComplexTypesReader.readChartTitle(axis, reader));
                    break;
                case /*"numFmt"*/11:
                    axis.setNumFmt(DmlChartComplexTypesReader.readChartNumFormat(xmlReader));
                    break;
                case /*"majorTickMark"*/12:
                    axis.setMajorTickMark(DmlChartsEnum.dmlToTickMark(xmlReader.readAttribute("")));
                    break;
                case /*"minorTickMark"*/13:
                    axis.setMinorTickMark(DmlChartsEnum.dmlToTickMark(xmlReader.readAttribute("")));
                    break;
                case /*"majorTickMarks"*/14:
                    DmlChartComplexTypesReader.readTickMarks(reader, axis.getChartAxisPr(),
                        DmlChartAxisAttrs.MAJOR_TICK_MARK, DmlChartAxisAttrs.MAJOR_TICK_MARK_EXTENSIONS);
                    break;
                case /*"minorTickMarks"*/15:
                    DmlChartComplexTypesReader.readTickMarks(reader, axis.getChartAxisPr(),
                        DmlChartAxisAttrs.MINOR_TICK_MARK, DmlChartAxisAttrs.MINOR_TICK_MARK_EXTENSIONS);
                    break;
                case /*"tickLabels"*/16: // of 2.24.3.80 CT_TickLabels type [MS-ODRAWXML]
                {
                    axis.setAreTickLabelsVisible(true);
                    readTickLabels(reader, axis);
                    break;
                }
                case /*"tickLblPos"*/17:
                    axis.getTickLabels().setPosition(DmlChartsEnum.dmlToTickLabelPosition(xmlReader.readAttribute("")));
                    break;
                case /*"spPr"*/18:
                    DmlChartComplexTypesReader.readChartSpPr(reader, axis.getSpPr());
                    break;
                case /*"txPr"*/19:
                    DmlChartComplexTypesReader.readChartTxPr(reader, axis.getTxPr());
                    break;
                case /*"crossAx"*/20:
                    axis.setCrossAx(xmlReader.readIntAttribute(0));
                    break;
                case /*"crosses"*/21:
                    axis.setCrosses(DmlChartsEnum.dmlToCrosses(xmlReader.readAttribute("")));
                    break;
                case /*"crossesAt"*/22:
                    axis.setCrossesAt(xmlReader.readDoubleAttribute(0.0));
                    axis.setCrosses(AxisCrosses.CUSTOM);
                    break;
                case /*"crossBetween"*/23:
                    axis.setCrossBetween(DmlChartsEnum.dmlToCrossBetween(xmlReader.readAttribute("")));
                    break;
                case /*"majorUnit"*/24:
                    axis.getMajorUnitPr().setValue(xmlReader.readDoubleAttribute(1.0));   // set without checking value
                    break;
                case /*"minorUnit"*/25:
                    axis.getMinorUnitPr().setValue(xmlReader.readDoubleAttribute(0.5)); // set without checking value
                    break;
                case /*"dispUnits"*/26:
                case /*"units"*/27: // 2.24.3.5 CT_AxisUnits [MS-ODRAWXML]
                    axis.setDisplayUnit(DmlChartComplexTypesReader.readChartDisplayUnits(axis, reader));
                    break;
                case /*"auto"*/28:
                    if (xmlReader.readBoolAttribute(true))
                        axis.setCategoryType(AxisCategoryType.AUTOMATIC);
                    break;
                case /*"lblAlgn"*/29:
                    axis.setLblAlgn(DmlChartsEnum.dmlToLabelAlignment(xmlReader.readAttribute("")));
                    break;
                case /*"lblOffset"*/30:
                    // Use SetProperty to not check input value.
                    axis.getChartAxisPr().setProperty(DmlChartAxisAttrs.TICK_LABEL_OFFSET, xmlReader.readIntAttribute(100));
                    break;
                case /*"tickLblSkip"*/31:
                    // Use SetProperty to not check input value.
                    axis.getChartAxisPr().setProperty(DmlChartAxisAttrs.TICK_LABEL_SPACING, xmlReader.readIntAttribute(1));
                    axis.getTickLabels().isAutoSpacing(false);
                    break;
                case /*"tickMarkSkip"*/32:
                    // Use SetProperty to not check input value.
                    axis.getChartAxisPr().setProperty(DmlChartAxisAttrs.TICK_MARK_SPACING, xmlReader.readIntAttribute(1));
                    break;
                case /*"noMultiLvlLbl"*/33:
                    axis.setNoMultiLvlLbl(xmlReader.readBoolAttribute(true));
                    break;
                case /*"baseTimeUnit"*/34:
                    axis.setBaseTimeUnit(DmlChartsEnum.dmlToTimeUnit(xmlReader.readAttribute("")));
                    break;
                case /*"majorTimeUnit"*/35:
                    axis.setMajorUnitScale(DmlChartsEnum.dmlToTimeUnit(xmlReader.readAttribute("")));
                    break;
                case /*"minorTimeUnit"*/36:
                    axis.setMinorUnitScale(DmlChartsEnum.dmlToTimeUnit(xmlReader.readAttribute("")));
                    break;
                case /*"extLst"*/37:
                    ((IDmlExtensionListSource)axis).setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        setDefaultTitleRotation(axis);
        validateLabelRotation(axis);

        return axis;
    }

    /// <summary>
    /// Reads an element of the 2.24.3.80 CT_TickLabels complex type [MS-ODRAWXML].
    /// </summary>
    static void readTickLabels(DocxDocumentReaderBase reader, ChartAxis axis) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        // Read children
        while (xmlReader.readChild("tickLabels"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"extLst"*/37:
                    axis.setTickLabelExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Sets default rotation for vertical axis' title (WORDSNET-8556).
    /// </summary>
    static void setDefaultTitleRotation(ChartAxis axis)
    {
        DmlTextBodyProperties parentBodyPr = new DmlTextBodyProperties();
        if (axis.isVertical())
            parentBodyPr.setRotation(DmlAngle.fromDegrees(-90));

        if (axis.getTitleInternal() != null)
            axis.getTitleInternal().getTxPr().getBodyPr().setParentProperties(parentBodyPr);

        if (axis.getDisplayUnit().getLabel() != null)
            axis.getDisplayUnit().getLabel().getTxPr().getBodyPr().setParentProperties(parentBodyPr);
    }

    /// <summary>
    /// For some reason MS Word 2013 sets axis labels rotation to -1000 degrease by default.
    /// But this value is treated as zero in MS Word. So to fix the problem simply reset value to zero.
    /// </summary>
    private static void validateLabelRotation(ChartAxis axis)
    {
        if (MathUtil.areEqual(axis.getTxPr().getBodyPr().getRotation().getValueInDegrees(), -1000.0d))
        {
            axis.getTxPr().getBodyPr().remove(DmlTextBodyPropertiesDefaultsIds.ROTATION);
            axis.getTxPr().getBodyPr().hasDefaultRotation(true);
        }
    }

    private DmlChartGridlines readGridLines(String tagName) throws Exception
    {
        DmlChartGridlines gridlines = new DmlChartGridlines();

        while (mDocumentReader.getXmlReader().readChild(tagName))
        {
            switch (gStringSwitchMap.of(mDocumentReader.getXmlReader().getLocalName()))
            {
                case /*"spPr"*/18:
                    DmlChartComplexTypesReader.readChartSpPr(mDocumentReader, gridlines.getSpPr());
                    break;
                case /*"extLst"*/37: // in 2.24.3.52 CT_Gridlines type [MS-ODRAWXML]
                    gridlines.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mDocumentReader.getXmlReader());
                    break;
            }
        }

        return gridlines;
    }

    private static /*ChartAxisType*/int getAxisType(String tagName)
    {
        switch (gStringSwitchMap.of(tagName))
        {
            case /*"catAx"*/38:
            case /*"dateAx"*/39:
                return ChartAxisType.CATEGORY;
            case /*"serAx"*/40:
                return ChartAxisType.SERIES;
            case /*"valAx"*/41:
                return ChartAxisType.VALUE;
            case /*"axis"*/42: // axis element of 2.24.3.64 CT_PlotArea [MS-ODRAWXML]
                // Type is defined by catScaling/valScaling element; return Value for now.
                return ChartAxisType.VALUE;
            default:
                throw new IllegalArgumentException("Unexpected axis type.");
        }
    }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"id",
		"hidden",
		"axId",
		"catScaling",
		"valScaling",
		"scaling",
		"delete",
		"axPos",
		"majorGridlines",
		"minorGridlines",
		"title",
		"numFmt",
		"majorTickMark",
		"minorTickMark",
		"majorTickMarks",
		"minorTickMarks",
		"tickLabels",
		"tickLblPos",
		"spPr",
		"txPr",
		"crossAx",
		"crosses",
		"crossesAt",
		"crossBetween",
		"majorUnit",
		"minorUnit",
		"dispUnits",
		"units",
		"auto",
		"lblAlgn",
		"lblOffset",
		"tickLblSkip",
		"tickMarkSkip",
		"noMultiLvlLbl",
		"baseTimeUnit",
		"majorTimeUnit",
		"minorTimeUnit",
		"extLst",
		"catAx",
		"dateAx",
		"serAx",
		"valAx",
		"axis"
	);

}

