// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2025 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.OpcPackagePart;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.StreamUtil;


/// <summary>
/// Reads preset chart types and chart styles from the embedded resources.
/// </summary>
/*static*/ class PresetChartReader
{
	/* Simulation of static class by using private constructor */
	private PresetChartReader()
	{}

    /// <summary>
    /// Reads chart of specific type from the Aspose.Words.Resources.Charts.ChartTypes.xml
    /// </summary>
    public static void readPresetChart(DmlChartSpace chartSpace, /*ChartType*/int chartType, int documentVersion) throws Exception
    {
        final String RESOURCE_NAME = "Aspose.Words.Resources.Charts.ChartTypes.xml";

        Stream stream = ResourceUtil.fetchResourceStream(RESOURCE_NAME);
        try /*JAVA: was using*/
        {
            stream.setPosition(0);

            DocxXmlReader xmlReader = new DocxXmlReader(stream, new OoxmlComplianceInfo());

            DocxDocumentReaderBase reader = DocxReaderFactory.createDocumentReader(getDocxDocumentReaderStub());

            // Some chart property defaults depend on a document version: need to set the version so that the defaults
            // are generated using the correct document version.
            if (documentVersion >= 0)
                ((Document)reader.getDocument()).getBuiltInDocumentProperties().setVersion(documentVersion);

            reader.pushPartReader(xmlReader);

            String tagName = getResourceTagName(chartType);

            while (reader.getXmlReader().readChild("chartTypes"))
            {
                if (msString.equals(reader.getXmlReader().getLocalName(), tagName))
                {
                    while (reader.getXmlReader().readChild(tagName))
                        DmlChartReader.read(reader, chartSpace);
                }
                else
                {
                    reader.getXmlReader().ignoreElement();
                }
            }
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Reads data of a styleX.xml part of a chart from the ChartStylesXXX.xml resource file as a
    /// <see cref="DmlChartStyle"/> instance.
    /// </summary>
    static DmlChartStyle readChartStyle(int styleId, Document document) throws Exception
    {
        String resourceName = getChartStyleResourceName(styleId);

        // This initializes a theme if it doesn't exist.
        if (document.getTheme() == null)
            Debug.fail(null);

        Stream stream = ResourceUtil.fetchResourceStream(resourceName);
        try /*JAVA: was using*/
        {
            stream.setPosition(0);

            DocxXmlReader xmlReader = new DocxXmlReader(stream, new OoxmlComplianceInfo());

            DocxDocumentReaderBase reader = DocxReaderFactory.createDocumentReader(getDocxDocumentReaderStub());

            reader.pushPartReader(xmlReader);

            String stringId = Integer.toString(styleId);

            while (reader.getXmlReader().readChild("chartStyles"))
            {
                Debug.assertTrue("chartStyle".equals(reader.getXmlReader().getLocalName()));
                if (msString.equals(reader.getXmlReader().readAttribute("id", ""), stringId))
                    return DmlChartStyleReader.read(reader);

                reader.getXmlReader().ignoreElement();
            }
        }
        finally { if (stream != null) stream.close(); }

        return null;
    }

    private static String getChartStyleResourceName(int styleId)
    {
        Debug.assertTrue(styleId >= 1);
        int i = 1;
        while ((i < G_CHART_STYLE_RESOURCE_FILE_START_ID.length) && (styleId >= G_CHART_STYLE_RESOURCE_FILE_START_ID[i]))
            i++;

        final String RESOURCE_NAME_TEMPLATE = "Aspose.Words.Resources.Charts.ChartStyles{0}.xml";
        return msString.format(RESOURCE_NAME_TEMPLATE, G_CHART_STYLE_RESOURCE_FILE_START_ID[i - 1]);
    }

    /// <summary>
    /// Gets a tag name that is used to store a chart definition of the specified type in the ChartTypes.xml
    /// resource file.
    /// </summary>
    private static String getResourceTagName(/*ChartType*/int chartType)
    {
        switch (chartType)
        {
            case ChartType.AREA:
            case ChartType.AREA_STACKED:
            case ChartType.AREA_PERCENT_STACKED:
                return "areaChart";
            case ChartType.AREA_3_D:
            case ChartType.AREA_3_D_STACKED:
            case ChartType.AREA_3_D_PERCENT_STACKED:
                return "area3DChart";
            case ChartType.BAR:
            case ChartType.BAR_STACKED:
            case ChartType.BAR_PERCENT_STACKED:
                return "barChart";
            case ChartType.BAR_3_D:
            case ChartType.BAR_3_D_STACKED:
            case ChartType.BAR_3_D_PERCENT_STACKED:
                return "bar3DChart";
            case ChartType.BUBBLE:
            case ChartType.BUBBLE_3_D:
                return "bubbleChart";
            case ChartType.COLUMN:
            case ChartType.COLUMN_STACKED:
            case ChartType.COLUMN_PERCENT_STACKED:
                return "colChart";
            case ChartType.COLUMN_3_D:
                return "col3DChart";
            case ChartType.COLUMN_3_D_CLUSTERED:
            case ChartType.COLUMN_3_D_STACKED:
            case ChartType.COLUMN_3_D_PERCENT_STACKED:
                return "col3DClusteredChart";
            case ChartType.DOUGHNUT:
                return "doughnutChart";
            case ChartType.LINE:
            case ChartType.LINE_STACKED:
            case ChartType.LINE_PERCENT_STACKED:
                return "lineChart";
            case ChartType.LINE_3_D:
                return "line3DChart";
            case ChartType.PIE:
                return "pieChart";
            case ChartType.PIE_OF_BAR:
            case ChartType.PIE_OF_PIE:
                return "ofPieChart";
            case ChartType.PIE_3_D:
                return "pie3DChart";
            case ChartType.RADAR:
                return "radarChart";
            case ChartType.SCATTER:
                return "scatterChart";
            case ChartType.STOCK:
                return "stockChart";
            case ChartType.SURFACE:
                return "surfaceChart";
            case ChartType.SURFACE_3_D:
                return "surface3DChart";
            case ChartType.TREEMAP:
                return "treemap";
            case ChartType.SUNBURST:
                return "sunburst";
            case ChartType.HISTOGRAM:
                return "histogram";
            case ChartType.PARETO:
                return "pareto";
            case ChartType.BOX_AND_WHISKER:
                return "boxWhisker";
            case ChartType.WATERFALL:
                return "waterfall";
            case ChartType.FUNNEL:
                return "funnel";
            default:
                return "";
        }
    }

    /// <summary>
    /// Gets a temporary document.xml part to use in a reader.
    /// </summary>
    private static OpcPackagePart getDocxDocumentReaderStub() throws Exception
    {
        OpcPackagePart documentPartStub = new OpcPackagePart("document.xml", "test");
        documentPartStub.setStream(new MemoryStream());
        StreamUtil.writeAnsiStringToStream("<document />", documentPartStub.getStream());
        documentPartStub.getStream().setPosition(0);
        return documentPartStub;
    }

    /// <summary>
    /// The predefined chart styles are stored in resources as several XML files to reduce reading time. Each file
    /// contains a certain range of styles identified by ID. The files are named ChartStyleXXX.xml, where XXX is
    /// the first style ID that is stored in the file. This array contains the IDs used in file names.
    /// </summary>
    private static /*final*/ int[] G_CHART_STYLE_RESOURCE_FILE_START_ID =
    {
        1, 222, 245, 264, 284, 305, 326, 346, 385, 415
    };
}

