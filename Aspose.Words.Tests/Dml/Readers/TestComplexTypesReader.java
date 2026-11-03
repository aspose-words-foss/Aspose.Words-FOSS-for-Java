// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests for <see cref="DmlChartComplexTypesReader"/>.
/// </summary>
@Test
public class TestComplexTypesReader
{
    @Test
    public void testReadChartNumFormat()
    {
        String xml = "<c:numFmt formatCode=\"#,##0.00[$$-C09]\" sourceLinked=\"0\" xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\" />";

        NrxXmlReader reader = new NrxXmlReader(xml, null);

        DmlChartNumFormat numFormat = DmlChartComplexTypesReader.readChartNumFormat(reader);

        msAssert.isNotNull(numFormat);
        msAssert.areEqual("#,##0.00[$$-C09]", numFormat.getFormatCode());
        msAssert.isFalse(numFormat.getSourceLinked());
    }

    @Test
    public void testReadChartScaling() throws Exception
    {
        String xml =
            "<c:scaling xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:logBase val=\"2\" />" +
                "<c:orientation val=\"maxMin\" />" +
                "<c:max val=\"6\" />" +
                "<c:min val=\"1\" />" +
                "<c:extLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                    "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                        "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                            "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                            "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                    "</a:ext> " +
                "</c:extLst> " +
            "</c:scaling>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        AxisScaling scaling = new AxisScaling();
        DmlChartComplexTypesReader.readChartScaling(reader, scaling);

        msAssert.isNotNull(scaling);
        msAssert.areEqual(AxisScaleType.LOGARITHMIC, scaling.getType());
        msAssert.areEqual(2.0d, scaling.getLogBase());
        msAssert.areEqual(new AxisBound(1.0d), scaling.getMinimum());
        msAssert.areEqual(new AxisBound(6.0d), scaling.getMaximum());
        msAssert.areEqual(AxisOrientation.MAX_MIN, scaling.getOrientation());
        msAssert.areEqual(1, ((IDmlExtensionListSource)scaling).getExtensions().getCount());
    }

    @Test
    public void testReadChartDisplayUnits() throws Exception
    {
        // I cannot make MS Word to write something to dispUnitsLbl so for now test with empty dispUnitsLbl.
        String xml =
            "<c:dispUnits xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:builtInUnit val=\"hundreds\" />" +
                "<c:dispUnitsLbl />" +
                "<c:extLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                    "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                        "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                            "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                            "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                    "</a:ext> " +
                "</c:extLst> " +
            "</c:dispUnits>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        AxisDisplayUnit units = DmlChartComplexTypesReader.readChartDisplayUnits(null, reader);

        msAssert.isNotNull(units);
        msAssert.areEqual(AxisBuiltInUnit.HUNDREDS, units.getUnit());
        msAssert.areEqual(1, ((IDmlExtensionListSource)units).getExtensions().getCount());
    }

    @Test
    public void testReadChartLayout() throws Exception
    {
        String xml =
            "<c:layout xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:manualLayout>" +
                    "<c:layoutTarget val=\"inner\" />" +
                    "<c:xMode val=\"edge\" />" +
                    "<c:yMode val=\"edge\" />" +
                    "<c:x val=\"3.6680154564012864E-2\" />" +
                    "<c:y val=\"0.18294650668666429\" />" +
                    "<c:w val=\"0.8000107538641007\" />" +
                    "<c:h val=\"0.73748343957005369\" />" +
                    "<c:extLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                    "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                        "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                            "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                            "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                    "</a:ext> " +
                "</c:extLst> " +
                "</c:manualLayout>" +
                "<c:extLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                    "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                        "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                            "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                            "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                    "</a:ext> " +
                "</c:extLst> " +
            "</c:layout>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        DmlChartManualLayout layout = DmlChartComplexTypesReader.readChartLayout(reader);

        msAssert.isNotNull(layout);

        msAssert.areEqual(LayoutMode.EDGE, layout.getLeftMode());
        msAssert.areEqual(LayoutMode.EDGE, layout.getTopMode());
        msAssert.areEqual(LayoutMode.FACTOR, layout.getWidthMode());
        msAssert.areEqual(LayoutMode.FACTOR, layout.getHeightMode());

        msAssert.areEqual(0.036680154564012864d, layout.getLeft());
        msAssert.areEqual(0.18294650668666429d, layout.getTop());
        msAssert.areEqual(0.8000107538641007d, layout.getWidth());
        msAssert.areEqual(0.73748343957005369d, layout.getHeight());
        
        msAssert.areEqual(LayoutTarget.INNER, layout.getLayoutTarget());

        msAssert.areEqual(1, layout.getExtensions().getCount());
    }

    @Test
    public void testReadChartTxPlain() throws Exception
    {
        String xml =
            "<c:tx xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:strRef>" +
                    "<c:f>Sheet1!$C$1</c:f>" +
                    "<c:strCache><c:ptCount val=\"1\" /><c:pt idx=\"0\"><c:v>Series 2</c:v></c:pt></c:strCache>" +
                "</c:strRef>" +
                "<c:extLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                    "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                        "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                            "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                            "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                    "</a:ext> " +
                "</c:extLst> " +
            "</c:tx>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        DmlChartTx tx = DmlChartComplexTypesReader.readChartTx(reader);

        msAssert.isNotNull(tx);
        msAssert.areEqual(DmlChartTxType.SERIES_TEXT, tx.getTxType());
        msAssert.isNull(tx.getRichText());
        msAssert.areEqual("Series 2", tx.getPlainText());
        msAssert.isNotNull(tx.getStrRef());
        msAssert.areEqual(1, tx.getExtensions().getCount());
    }

    @Test
    public void testReadChartTxReach() throws Exception
    {
        String xml =
            "<c:tx xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:rich>" +
                    "<a:bodyPr xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" />" +
                    "<a:lstStyle xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" />" +
                    "<a:p xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                        "<a:pPr><a:defRPr /></a:pPr>" +
                        "<a:r><a:rPr lang=\"en-US\" i=\"1\" strike=\"sngStrike\" /><a:t>Axis Title</a:t></a:r>" +
                    "</a:p>" +
                "</c:rich>" +
            "</c:tx>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        DmlChartTx tx = DmlChartComplexTypesReader.readChartTx(reader);

        msAssert.isNotNull(tx);
        msAssert.areEqual(DmlChartTxType.CHART_TEXT, tx.getTxType());
        msAssert.isNotNull(tx.getRichText());
        msAssert.isNull(tx.getPlainText());
        msAssert.isNull(tx.getStrRef());
    }

    @Test
    public void testReadChartStrRef() throws Exception
    {
        String xml =
            "<c:strRef xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:f>Sheet1!$A$2:$A$5</c:f>" +
                "<c:strCache>" +
                    "<c:ptCount val=\"4\" />" +
                    "<c:pt idx=\"0\"><c:v>Category 1</c:v></c:pt>" +
                    "<c:pt idx=\"1\"><c:v>Category 2</c:v></c:pt>" +
                    "<c:pt idx=\"2\"><c:v>Category 3</c:v></c:pt>" +
                    "<c:pt idx=\"3\"><c:v>Category 4</c:v></c:pt>" +
                "</c:strCache>" +
                "<c:extLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                    "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                        "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                            "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                            "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                    "</a:ext> " +
                "</c:extLst> " +
            "</c:strRef>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        DmlChartValueRef strRef = DmlChartComplexTypesReader.readChartValRef(reader);

        msAssert.isNotNull(strRef);
        msAssert.areEqual("Sheet1!$A$2:$A$5", strRef.getFormula().getValue());
        msAssert.isNotNull(strRef.getValues());
        msAssert.areEqual(DmlChartValueType.STRING, strRef.getValues().getValueType());
        msAssert.areEqual(1, strRef.getExtensions().getCount());
    }

    @Test
    public void testReadChartNumRef() throws Exception
    {
        String xml =
            "<c:numRef xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:f>Sheet1!$C$2:$C$5</c:f>" +
                "<c:numCache>" +
                    "<c:formatCode>General</c:formatCode>" +
                    "<c:ptCount val=\"4\" />" +
                    "<c:pt idx=\"0\"><c:v>2.4</c:v></c:pt>" +
                    "<c:pt idx=\"1\"><c:v>4.4000000000000004</c:v></c:pt>" +
                    "<c:pt idx=\"2\"><c:v>1.8</c:v></c:pt>" +
                    "<c:pt idx=\"3\"><c:v>2.8</c:v></c:pt>" +
                "</c:numCache>" +
                "<c:extLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                    "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                        "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                            "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                            "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                    "</a:ext> " +
                "</c:extLst> " +
            "</c:numRef>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        DmlChartValueRef strRef = DmlChartComplexTypesReader.readChartValRef(reader);

        msAssert.isNotNull(strRef);
        msAssert.areEqual("Sheet1!$C$2:$C$5", strRef.getFormula().getValue());
        msAssert.isNotNull(strRef.getValues());
        msAssert.areEqual(DmlChartValueType.NUMERIC, strRef.getValues().getValueType());

        msAssert.areEqual(2.4d, ((DmlChartNumValue)strRef.getValues().get(0)).getValue());
        msAssert.areEqual(4.4000000000000004d, ((DmlChartNumValue)strRef.getValues().get(1)).getValue());
        msAssert.areEqual(1.8d, ((DmlChartNumValue)strRef.getValues().get(2)).getValue());
        msAssert.areEqual(2.8d, ((DmlChartNumValue)strRef.getValues().get(3)).getValue());
        msAssert.areEqual(1, strRef.getExtensions().getCount());
    }

    @Test
    public void testReadChartStrCache() throws Exception
    {
        String xml =
            "<c:strCache xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:ptCount val=\"4\" />" +
                "<c:pt idx=\"0\"><c:v>Category 1</c:v></c:pt>" +
                "<c:pt idx=\"1\"><c:v>Category 2</c:v></c:pt>" +
                "<c:pt idx=\"2\"><c:v>Category 3</c:v></c:pt>" +
                "<c:pt idx=\"3\"><c:v>Category 4</c:v></c:pt>" +
                "<c:extLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                    "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                        "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                            "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                            "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                    "</a:ext> " +
                "</c:extLst> " +
            "</c:strCache>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        DmlChartValueCollection cache = new DmlChartValueCollection(DmlChartValueType.STRING);
        DmlChartComplexTypesReader.readChartValuesCache(reader, cache);

        msAssert.isNotNull(cache);
        msAssert.areEqual(4, cache.getValueCount());
        msAssert.areEqual(1, cache.getExtensions().getCount());
    }

    @Test
    public void testReadStringPoint()
    {
        String xml =
            "<c:pt idx=\"2\" xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:v>Category 3</c:v>" +
            "</c:pt>";

        NrxXmlReader reader = new NrxXmlReader(xml, null);

        DmlChartValue pt = DmlChartComplexTypesReader.readChartValPoint(reader, DmlChartValueType.STRING);

        msAssert.isNotNull(pt);
        msAssert.areEqual(2, pt.getIndex());
        msAssert.areEqual("Category 3", pt.getStringValue());
    }

    @Test
    public void testReadMarker() throws Exception
    {
        String xml =
            "<c:marker xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:symbol val=\"square\" />" +
                "<c:size val=\"42\" />" +
                "<c:spPr>" +
                    "<a:blipFill dpi=\"0\" rotWithShape=\"1\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                        "<a:blip xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" r:embed=\"rId1\" />" +
                        "<a:srcRect />" +
                        "<a:tile tx=\"100\" ty=\"200\" sx=\"10000\" sy=\"10000\" flip=\"none\" algn=\"tl\" />" +
                    "</a:blipFill>" +
                "</c:spPr>" +
            "</c:marker>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        ChartMarker marker = new ChartMarker(reader.getDocument().getThemeInternal());
        DmlChartComplexTypesReader.readChartMarker(reader, marker);

        msAssert.isNotNull(marker);
        msAssert.areEqual(42, marker.getSize());
        msAssert.areEqual(MarkerSymbol.SQUARE, marker.getSymbol());
        msAssert.isNotNull(marker.getSpPr());
        msAssert.isNotNull(marker.getSpPr().getFill());
        DmlBlipFillTile tile = (DmlBlipFillTile)((DmlBlipFill)marker.getSpPr().getFill()).getBlipFillMode();
        msAssert.areEqual(100d, tile.getHorizontalOffset());
        msAssert.areEqual(200d, tile.getVerticalOffset());
    }

    /// <summary>
    /// Tests reading the CT_Marker complex type from ISO Strict format.
    /// Also checks reading values of the ST_UniversalMeasure simple type.
    /// </summary>
    @Test
    public void testReadMarkerAsIsoStrict() throws Exception
    {
        String xml =
            "<c:marker xmlns:c=\"http://purl.oclc.org/ooxml/drawingml/chart\">" +
                "<c:symbol val=\"square\" />" +
                "<c:size val=\"42\" />" +
                "<c:spPr>" +
                    "<a:blipFill dpi=\"0\" rotWithShape=\"1\" xmlns:a=\"http://purl.oclc.org/ooxml/drawingml/main\">" +
                        "<a:blip xmlns:r=\"http://purl.oclc.org/ooxml/officeDocument/relationships\" r:embed=\"rId1\" />" +
                        "<a:srcRect />" +
                        "<a:tile tx=\"1pt\" ty=\"0.5pc\" sx=\"10000\" sy=\"10000\" flip=\"none\" algn=\"tl\" />" +
                    "</a:blipFill>" +
                "</c:spPr>" +
            "</c:marker>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        ChartMarker marker = new ChartMarker(reader.getDocument().getThemeInternal());
        DmlChartComplexTypesReader.readChartMarker(reader, marker);

        msAssert.isNotNull(marker);
        msAssert.areEqual(42, marker.getSize());
        msAssert.areEqual(MarkerSymbol.SQUARE, marker.getSymbol());
        msAssert.isNotNull(marker.getSpPr());
        msAssert.isNotNull(marker.getSpPr().getFill());
        DmlBlipFillTile tile = (DmlBlipFillTile)((DmlBlipFill)marker.getSpPr().getFill()).getBlipFillMode();
        msAssert.areEqual(12700d, tile.getHorizontalOffset());
        msAssert.areEqual(76200d, tile.getVerticalOffset());
    }

    @Test
    public void testReadDataPoint() throws Exception
    {
        String xml =
            "<c:dPt xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:idx val=\"1\" />" +
                "<c:marker>" +
                    "<c:symbol val=\"diamond\" />" +
                    "<c:size val=\"15\" />" +
                    "<c:spPr>" +
                        "<a:solidFill xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                            "<a:srgbClr val=\"1F497D\"><a:alpha val=\"50000\" /></a:srgbClr>" +
                        "</a:solidFill>" +
                        "<a:scene3d xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                            "<a:camera prst=\"orthographicFront\" />" +
                            "<a:lightRig rig=\"threePt\" dir=\"t\" />" +
                        "</a:scene3d>" +
                        "<a:sp3d xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                            "<a:bevelT />" +
                        "</a:sp3d>" +
                    "</c:spPr>" +
                "</c:marker>" +
                "<c:spPr>" +
                    "<a:ln cmpd=\"dbl\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                        "<a:solidFill><a:srgbClr val=\"FF0000\" /></a:solidFill>" +
                        "<a:prstDash val=\"sysDash\" />" +
                    "</a:ln>" +
                "</c:spPr>" +
            "</c:dPt>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        DmlChart chart = new DmlAreaChart();
        chart.setDocument(reader.getDocument());
        ChartDataPoint pt = DmlChartComplexTypesReader.readChartDataPoint(reader, chart);

        msAssert.isNotNull(pt);
        msAssert.areEqual(1, pt.getIndex());
        msAssert.isNotNull(pt.getMarker());
        msAssert.isNotNull(pt.getSpPr());
    }

    @Test
    public void testReadChartDataLabelCollection() throws Exception
    {
        String xml =
            "<c:dLbls xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:txPr>" +
                    "<a:bodyPr rot=\"0\" vert=\"eaVert\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" />" +
                    "<a:lstStyle xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" />" +
                    "<a:p xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"><a:pPr><a:defRPr /></a:pPr><a:endParaRPr lang=\"en-US\" /></a:p>" +
                "</c:txPr>" +
                "<c:dLblPos val=\"outEnd\" />" +
                "<c:showLegendKey val=\"1\" />" +
                "<c:showSerName val=\"1\" />" +
                "<c:separator>. </c:separator>" +
            "</c:dLbls>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        DmlBarChart chart = new DmlBarChart();
        chart.setDocument(reader.getDocument());
        ChartDataLabelCollection lbls = DmlChartComplexTypesReader.readChartDataLabelCollection(reader, chart, null);

        msAssert.isNotNull(lbls);
        msAssert.areEqual(ChartDataLabelPosition.OUTSIDE_END, lbls.getLabelPr().getProperty(DmlChartDataLabelAttrs.D_LBL_POS));
        msAssert.areEqual(true, lbls.getLabelPr().getProperty(DmlChartDataLabelAttrs.SHOW_LEGEND_KEY));
        msAssert.areEqual(true, lbls.getLabelPr().getProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME));
        msAssert.areEqual(". ", lbls.getLabelPr().getProperty(DmlChartDataLabelAttrs.SEPARATOR));
    }

    @Test
    public void testReadChartErrorBars() throws Exception
    {
        String xml =
            "<c:errBars xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:errBarType val=\"both\" />" +
                "<c:errValType val=\"fixedVal\" />" +
                "<c:val val=\"0.1\" />" +
                "<c:spPr>" +
                    "<a:ln xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                        "<a:solidFill><a:schemeClr val=\"tx1\" /></a:solidFill>" +
                    "</a:ln>" +
                "</c:spPr>" +
                "<c:extLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                    "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                        "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                            "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                            "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                    "</a:ext> " +
                "</c:extLst> " +
            "</c:errBars>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        DmlChartErrorBars errorBars = DmlChartComplexTypesReader.readChartErrorBars(reader);

        msAssert.isNotNull(errorBars);
        msAssert.areEqual(ErrorValueType.FIXED_VALUE, errorBars.getErrValType());
        msAssert.areEqual(ErrorBarType.BOTH, errorBars.getErrBarType());
        msAssert.areEqual(0.1d, errorBars.getVal());
        msAssert.isNotNull(errorBars.getSpPr());
        msAssert.isNotNull(errorBars.getPlus());
        msAssert.isNotNull(errorBars.getMinus());
        msAssert.areEqual(1, errorBars.getExtensions().getCount());
    }

    @Test
    public void testReadChartTrendline() throws Exception
    {
        String xml =
            "<c:trendline xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:name>test</c:name>" +
                "<c:trendlineType val=\"linear\" />" +
                "<c:forward val=\"2\" />" +
                "<c:backward val=\"3\" />" +
                "<c:intercept val=\"4\" />" +
                "<c:dispRSqr val=\"1\" />" +
                "<c:dispEq val=\"1\" />" +
                "<c:trendlineLbl><c:numFmt formatCode=\"General\" sourceLinked=\"0\" /></c:trendlineLbl>" +
                "<c:extLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                    "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                        "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                            "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                            "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                    "</a:ext> " +
                "</c:extLst> " +
            "</c:trendline>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        DmlChartTrendline trendline = DmlChartComplexTypesReader.readChartTrendline(reader);

        msAssert.isNotNull(trendline);
        msAssert.isNotNull(trendline.getTrendlineLbl());
        msAssert.areEqual(TrendlineType.LINEAR, trendline.getTrendlineType());
        msAssert.areEqual(2, trendline.getForward());
        msAssert.areEqual(3, trendline.getBackward());
        msAssert.areEqual(4, trendline.getIntercept());
        msAssert.isTrue(trendline.getDispEq());
        msAssert.isTrue(trendline.getDispRSqr());
        msAssert.areEqual(1, trendline.getExtensions().getCount());
    }

    /// <summary>
    /// Checks the reading of the "scene3d" and "sp3d".
    /// </summary>
    @Test
    public void testRead3DChartSpPr() throws Exception
    {
        String xml = "<c:spPr xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\"" +
               " xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" >" +
            "<a:scene3d>" +
              "<a:camera prst=\"orthographicFront\"/>" +
              "<a:lightRig rig=\"threePt\" dir=\"t\"/>" +
            "</a:scene3d>" +
            "<a:sp3d contourW=\"25400\">" +
              "<a:contourClr>" +
                "<a:schemeClr val=\"lt1\"/>" +
              "</a:contourClr>" +
            "</a:sp3d>" +
            "<c:extLst xmlns:c16r2=\"http://schemas.microsoft.com/office/drawing/2015/06/chart\">" +
                "<c:ext uri=\"{C3380CC4-5D6E-409C-BE32-E72D297353CC}\""+
                        " xmlns:c16=\"http://schemas.microsoft.com/office/drawing/2014/chart\">" +
                    "<c16:uniqueId val=\"{00000001-84B0-46BC-8D9F-C0CDE4AB9DEC}\"/>" +
                "</c:ext>" +
            "</c:extLst>" +
          "</c:spPr>";

        DmlChartSpPr spPr = new DmlChartSpPr();
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        DmlChartComplexTypesReader.readChartSpPr(reader, spPr);

        // Just check that reading was made.
        msAssert.areEqual(1, spPr.getExtensions().getCount());
        msAssert.areEqual(25400, spPr.getShape3DProp().getContourWidth());            
        msAssert.areEqual(DmlPresetCameraType.ORTHOGRAPHIC_FRONT, spPr.getScene3DProp().getCamera().getPresetCameraType());
    }

 
}

