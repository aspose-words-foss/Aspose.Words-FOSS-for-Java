// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/12/2015 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.ms.System.Xml.XmlNodeType;
import com.aspose.ms.System.msMath;
import com.aspose.StringUtil;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests exporting to Strict Open XML Document format (ISO/IEC 29500:2012 Strict).
/// </summary>
@Test
public class TestExportIsoStrictDocx
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests that if a document is loaded from ISO Strict format, it is saved as ISO Strict by default.
    /// </summary>
    @Test
    public void testImplicitIsoStrict() throws Exception
    {
        Document doc = TestUtil.open("ImportIso29500S\\TestJira8049.docx");
        msAssert.areEqual(OoxmlComplianceCore.ISO_STRICT, doc.getComplianceInfo().getCompliance());

        // OoxmlComplianceInfo.GetCompliance() is used internally to get compliance value.
        msAssert.areEqual(OoxmlComplianceCore.ISO_STRICT, OoxmlComplianceInfo.getCompliance(doc.getComplianceInfo(), new OoxmlSaveOptions()));

        DocxExportContext context = new DocxExportContext(doc, DOCUMENT_PART, new OoxmlSaveOptions());

        checkForIsoStrict(context);
    }

    /// <summary>
    /// Checks that the document that is stored in the specified context is a Strict OOXML document.
    /// </summary>
    /// <param name="context"></param>
    private static void checkForIsoStrict(DocxExportContext context)
    {
        org.w3c.dom.Node node = context.getXmlNode("//w:document/@w:conformance");
        msAssert.isNotNull(node);
        msAssert.areEqual("strict", node.getNodeValue());
    }

    /// <summary>
    /// Tests that the 'conformance' attribute of the 'document' element (§17.2.3) has the 'strict' value on saving
    /// as ISO Strict.
    /// </summary>
    @Test
    public void testDocumentConformanceAttribute() throws Exception
    {
        DocxExportContext context = new DocxExportContext(new Document(), OoxmlComplianceCore.ISO_STRICT);

        checkForIsoStrict(context);

        OoxmlSaveOptions saveOptions = createStrictSaveOptions();
        context = new DocxExportContext(new Document(), DOCUMENT_PART, saveOptions);

        checkForIsoStrict(context);
    }

    /// <summary>
    /// Tests that the 'compatibilityMode' compatibility option has value 15.
    /// as ISO Strict.
    /// </summary>
    @Test
    public void testCompatibilityMode() throws Exception
    {
        DocxExportContext context = new DocxExportContext(new Document(), SETTINGS_PART,
            OoxmlComplianceCore.ISO_STRICT);

        org.w3c.dom.Node node = context.getXmlNode("//w:compat/w:compatSetting[@w:name = 'compatibilityMode']");
        msAssert.isNotNull(node);
        msAssert.areEqual("15", node.getAttributes().getNamedItem("w:val").getNodeValue());
    }

    /// <summary>
    /// Tests that new attributes of the tblLook element (17.4.54 and 17.4.55 of ISO/IEC 29500-1:2012) are used
    /// on saving as Strict Open XML Document.
    /// </summary>
    @Test
    public void testTableStyleOptions() throws Exception
    {
        DocxExportContext context =
            new DocxExportContext(TestUtil.open("ExportDocx\\TestTableBordersAndMargins.docx"),
                    OoxmlComplianceCore.ISO_STRICT);

        org.w3c.dom.Node node = context.getXmlNode("descendant::w:tblLook");
        msAssert.isNotNull(node);
        msAssert.isNull(node.getAttributes().getNamedItem("w:val"), "The val attribute is written in the tblLook element on saving as DOCX ISO Strict.");
    }

    /// <summary>
    /// Tests that the following new ISO/IEC 29500-1:2012 elements are supported on writing:
    /// start (17.4.33, 17.4.34, 17.4.35, 17.4.36) and end (17.4.10, 17.4.11, 17.4.12, 17.4.13).
    /// </summary>
    @Test
    public void testTableBordersAndMargins() throws Exception
    {
        DocxExportContext context =
            new DocxExportContext(TestUtil.open("ExportDocx\\TestTableBordersAndMargins.docx"),
                    OoxmlComplianceCore.ISO_STRICT);

        checkStartEndElements(context, "tblBorders");
        checkStartEndElements(context, "tcBorders");
        checkStartEndElements(context, "tblCellMar");
        checkStartEndElements(context, "tcMar");
    }

    /// <summary>
    /// Searches for the specified parent element and checks that it contains the start and end elements.
    /// </summary>
    private static void checkStartEndElements(DocxExportContext context, String parentElement)
    {
        final String ERR_MSG = "'{0}' element is not written in '{1}'.";

        org.w3c.dom.Node parentNode = context.getXmlNode("descendant::w:" + parentElement);
        msAssert.isNotNull(parentNode);
        org.w3c.dom.Node node = context.getXmlNode((org.w3c.dom.Element)parentNode, "descendant::w:start");
        msAssert.isNotNull(node, msString.format(ERR_MSG, "start", parentElement));
        node = context.getXmlNode((org.w3c.dom.Element)parentNode, "descendant::w:end");
        msAssert.isNotNull(node, msString.format(ERR_MSG, "end", parentElement));
    }

    /// <summary>
    /// Tests conversion of TextOrientation values to string and backward.
    /// </summary>
    @Test (dataProvider = "testTextOrientationConversionDataProvider")
    public void testTextOrientationConversion(/*TextOrientation*/int value, String asEcma, String asIsoStrict)
    {
        msAssert.areEqual(asEcma, StyleConvertUtil.textOrientationToXml(value, true, OoxmlComplianceCore.ECMA_376), "Conversion to string (ECMA-376) is wrong.");
        msAssert.areEqual(asIsoStrict, StyleConvertUtil.textOrientationToXml(value, true, OoxmlComplianceCore.ISO_STRICT), "Conversion to string (ISO Strict) is wrong.");

        OoxmlComplianceInfo complianceInfo = new OoxmlComplianceInfo();
        msAssert.areEqual(value, StyleConvertUtil.xmlToTextOrientation(asEcma, complianceInfo), "Conversion from string (ECMA-376) is wrong.");
        msAssert.areEqual(OoxmlComplianceCore.ECMA_376, complianceInfo.getCompliance(), "Wrong compliance value.");

        msAssert.areEqual(value, StyleConvertUtil.xmlToTextOrientation(asIsoStrict, complianceInfo), "Conversion from string (ISO Strict) is wrong.");
        msAssert.areEqual(OoxmlComplianceCore.ISO_TRANSITIONAL, complianceInfo.getCompliance(), "Wrong compliance value.");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTextOrientationConversionDataProvider")
	public static Object[][] testTextOrientationConversionDataProvider()
	{
		return new Object[][]
		{
			{TextOrientation.UPWARD,  "btLr",  "lr"},
			{TextOrientation.HORIZONTAL,  "lrTb",  "tb"},
			{TextOrientation.HORIZONTAL_ROTATED_FAR_EAST,  "lrTbV",  "tbV"},
			{TextOrientation.VERTICAL_FAR_EAST,  "tbRlV",  "rlV"},
			{TextOrientation.DOWNWARD,  "tbRl",  "rl"},
			{TextOrientation.VERTICAL_ROTATED_FAR_EAST,  "tbLrV",  "lrV"},
		};
	}

    /// <summary>
    /// Tests that new values are supported on writing attributes of the ST_TextDirection simple type (§17.18.93)
    /// when saving as ISO/IEC 29500-1:2012 format.
    /// </summary>
    @Test
    public void testTextOrientation() throws Exception
    {
        DocxExportContext context = new DocxExportContext(TestUtil.open("ExportDocx\\TestTextOrientation.docx"),
            OoxmlComplianceCore.ISO_STRICT);

        ArrayList<org.w3c.dom.Node> nodes = context.getXmlNodes("descendant::w:textDirection");
        boolean hasNodes = false;
        for (org.w3c.dom.Node node : nodes)
        {
            hasNodes = true;
            String textOrientation = node.getAttributes().getNamedItem("w:val").getNodeValue();
            if (!isIsoStrictTextOrientation(textOrientation))
                Assert.fail(msString.format("Wrong text orientation value is written to document: {0}.", textOrientation));
        }
        msAssert.isTrue(hasNodes);
    }

    /// <summary>
    /// Returns true if the specified text orientation is allowed value by ISO/IEC 29500-1:2012.
    /// </summary>
    private static boolean isIsoStrictTextOrientation(String value)
    {
        return
            "tb".equals(value) ||
            "rl".equals(value) ||
            "lr".equals(value) ||
            "tbV".equals(value) ||
            "rlV".equals(value) ||
            "lrV".equals(value);
    }

    /// <summary>
    /// Tests that new values are supported on writing ISO/IEC 29500-1:2012 elements:
    /// 17.3.1.13 jc (Paragraph Alignment) and 17.4.27 jc (Table Row Alignment).
    /// </summary>
    @Test
    public void testParagraphAlignment() throws Exception
    {
        DocxExportContext context =
            new DocxExportContext(TestUtil.open("ExportDocx\\TestParagraphAlignment.docx"),
                    OoxmlComplianceCore.ISO_STRICT);

        // Check of 17.3.1.13 jc (Paragraph Alignment)
        org.w3c.dom.Node node = context.getXmlNode("descendant::w:jc");
        msAssert.isNotNull(node);
        msAssert.areEqual("pPr", node.getParentNode().getLocalName());
        msAssert.areEqual("end", node.getAttributes().getNamedItem("w:val").getNodeValue(), "Wrong paragraph alignment value.");

        // Check of 17.4.27 jc (Table Row Alignment)
        node = context.getXmlNode("descendant::w:tblPr");
        msAssert.isNotNull(node);
        node = context.getXmlNode((org.w3c.dom.Element)node, "descendant::w:jc");
        msAssert.isNotNull(node);
        msAssert.areEqual("tblPr", node.getParentNode().getLocalName());
        msAssert.areEqual("end", node.getAttributes().getNamedItem("w:val").getNodeValue(), "Wrong table row alignment value.");
    }


    /// <summary>
    /// Tests that percentage type values are written with percent sign on saving as ISO/IEC 29500-1:2012 format.
    /// </summary>
    @Test
    public void testPercentageValues() throws Exception
    {
        DocxExportContext context = new DocxExportContext(TestUtil.open("ExportDocx\\TestPercentageValues.docx"),
            OoxmlComplianceCore.ISO_STRICT);

        checkAttributeValue(context, "//a:gs/@pos", "10%");
        checkAttributeValue(context, "//a:alpha/@val", "80%");
        checkAttributeValue(context, "//a:lumMod/@val", "10%");
        checkAttributeValue(context, "//a:lumOff/@val", "90%");
        checkAttributeValue(context, "//a:shade/@val", "50%");
        checkAttributeValue(context, "//a:outerShdw/@sx", "80%");
        checkAttributeValue(context, "//a:outerShdw/@sy", "80%");
        checkAttributeValue(context, "//a:reflection/@stA", "45%");
        checkAttributeValue(context, "//a:reflection/@endPos", "27%");
        checkAttributeValue(context, "//a:reflection/@sy", "-100%");
    }

    /// <summary>
    /// Tests that attributes of the 20.1.2.3.30 scrgbClr (RGB Color Model - Percentage Variant) element with
    /// zero values are written with percent sign on saving as ISO/IEC 29500-1:2012 format.
    /// </summary>
    @Test
    public void testZeroPercentageRgbColor() throws Exception
    {
        DocxExportContext context = new DocxExportContext(TestUtil.open("Model\\DrawingML\\TestBlip.docx"),
            OoxmlComplianceCore.ISO_STRICT);

        org.w3c.dom.Node node = context.getXmlNode("//a:style/a:lnRef/a:scrgbClr");
        checkAttributeValue(node, "r", "0%");
        checkAttributeValue(node, "g", "0%");
        checkAttributeValue(node, "b", "0%");
    }

    /// <summary>
    /// Tests that the 21.3.2.21 relSizeAnchor (Relative Anchor Shape Size) element is written with correct
    /// namespace on saving as ISO/IEC 29500-1:2012 format.
    /// </summary>
    @Test
    public void testNamespaceOfRelativeAnchorShapeSize() throws Exception
    {
        DocxExportContext context = new DocxExportContext(
            TestUtil.open("Model\\DrawingML\\TestChartDrawingBlipAndBlipFill.docx"),
            "word/drawings/drawing1.xml", OoxmlComplianceCore.ISO_STRICT);

        org.w3c.dom.Node node = context.getXmlDocument().getChildNodes().item(INDEX).getChildNodes().item(0);
        msAssert.areEqual("relSizeAnchor", node.getLocalName());
        msAssert.areEqual(DML_URL_PREFIX + "chartDrawing", node.getNamespaceURI());
    }

    /// <summary>
    /// Tests that percentage values of the elements: 2.18.1.1 pctPosHOffset, 2.18.1.2 pctPosVOffset,
    /// pctWidth of 2.18.3.1 CT_SizeRelH and pctHeight of 2.18.3.2 CT_SizeRelV [MS-ODRAWXML] are written
    /// in correct format on saving as ISO/IEC 29500-1:2012 format.
    /// </summary>
    @Test
    public void testWordprocessingDrawingElements() throws Exception
    {
        final String FILE_NAME = "ExportDocx\\TestJira10070.docx";
        Document doc = TestUtil.open(FILE_NAME);
        DocxExportContext context = new DocxExportContext(doc, OoxmlComplianceCore.ISO_STRICT);

        org.w3c.dom.Node node = context.getXmlNode("//wp14:pctPosHOffset");
        msAssert.areEqual("44%", node.getTextContent());
        node = context.getXmlNode("//wp14:pctPosVOffset");
        msAssert.areEqual("2.5%", node.getTextContent());
        node = context.getXmlNode("//wp14:pctWidth");
        msAssert.areEqual("40%", node.getTextContent());
        node = context.getXmlNode("//wp14:pctHeight");
        msAssert.areEqual("70%", node.getTextContent());

        doc = TestUtil.saveOpen(doc, FILE_NAME,
            OoxmlSaveOptions.docxWithCompliance(OoxmlComplianceCore.ISO_STRICT), false);
        ShapePr shapePr = doc.getFirstSection().getBody().getShapes().get(0).getShapePr();

        msAssert.areEqual(440, (/*int*/Integer)shapePr.get(ShapeAttr.LEFT_PERCENT));
        msAssert.areEqual(25, (/*int*/Integer)shapePr.get(ShapeAttr.TOP_PERCENT));
        msAssert.areEqual(400, (/*int*/Integer)shapePr.get(ShapeAttr.WIDTH_PERCENT));
        msAssert.areEqual(700, (/*int*/Integer)shapePr.get(ShapeAttr.HEIGHT_PERCENT));
    }

    /// <summary>
    /// Searches for attribute node that is specified by parent element and XPath. Checks that value of
    /// the found attribute equals to the expected value.
    /// </summary>
    private static void checkAttributeValue(DocxExportContext context, org.w3c.dom.Element parentNode, String xPath,
        String expectedValue)
    {
        org.w3c.dom.Node node = context.getXmlNode(parentNode, xPath);
        msAssert.isNotNull(node, msString.format("Cannot find the node '{0}'.", xPath));
        msAssert.isInstanceOf(org.w3c.dom.Attr.class, node, msString.format("Wrong type of the node '{0}'.", xPath));
        msAssert.areEqual(expectedValue, node.getNodeValue(), msString.format("Value of the node '{0}' is wrong.", xPath));
    }

    /// <summary>
    /// Searches for attribute node that is specified by XPath from the document root. Checks that value of
    /// the found attribute equals to the expected value.
    /// </summary>
    private static void checkAttributeValue(DocxExportContext context, String xPath, String expectedValue)
    {
        checkAttributeValue(context, context.getXmlDocument().getDocumentElement(), xPath, expectedValue);
    }

    /// <summary>
    /// Gets attribute of the node and checks that its value equals to <see cref="expectedValue"/>.
    /// </summary>
    private static void checkAttributeValue(org.w3c.dom.Node node, String attributeName, String expectedValue)
    {
        org.w3c.dom.Attr attribute = (org.w3c.dom.Attr)node.getAttributes().getNamedItem(attributeName);//casting for java
        msAssert.isNotNull(attribute);
        msAssert.areEqual(expectedValue, attribute.getNodeValue());
    }

    /// <summary>
    /// Tests that value of the 'percent' attribute on the 'zoom' element is written in format with percent sign
    /// on saving as ISO/IEC 29500-1:2012 format.
    /// </summary>
    @Test
    public void testZoomValue() throws Exception
    {
        Document doc = new Document();
        doc.getViewOptions().setZoomPercent(75);
        DocxExportContext context = new DocxExportContext(doc, SETTINGS_PART, OoxmlComplianceCore.ISO_STRICT);

        checkAttributeValue(context, "//w:zoom/@w:percent", "75%");
    }


    /// <summary>
    /// Tests that the minVer attribute of the 2.10.1.1 dataModelExt [MS-ODRAWXML] element has transitional
    /// diagram drawing namespace as value on saving as ISO/IEC 29500-1:2012 format.
    /// </summary>
    @Test
    public void testDataModelExtension() throws Exception
    {
        DocxExportContext context =
            new DocxExportContext(TestUtil.open("Model\\TextEffects\\TestTextEffectFillSmartArt.docx"),
                "word/diagrams/data1.xml", OoxmlComplianceCore.ISO_STRICT);

        checkAttributeValue(context, "//dsp:dataModelExt/@minVer",
            "http://schemas.openxmlformats.org/drawingml/2006/diagram");
    }

    /// <summary>
    /// Tests that buSzPct (Bullet Size Percentage) element is written correctly.
    /// </summary>
    @Test (dataProvider = "testWritingBulletSizeDataProvider")
    public void testWritingBulletSize(boolean isIsoStrict, String expectedPercentValue) throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TextBox\\TestDmlListsBulletSize.docx");
        DocxExportContext context = new DocxExportContext(doc, DOCUMENT_PART,
            (isIsoStrict ? OoxmlComplianceCore.ISO_STRICT : OoxmlComplianceCore.ECMA_376));
        checkAttributeValue(context, "//a:buSzPct/@val", expectedPercentValue);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testWritingBulletSizeDataProvider")
	public static Object[][] testWritingBulletSizeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{false,  "200000"},
			{true,  "200%"},
		};
	}

    /// <summary>
    /// Tests that buSzPct (Bullet Size Percentage) element is read correctly.
    /// </summary>
    @Test
    public void testReadingBulletSize() throws Exception
    {
        final String DOC_FILE_NAME = "Model\\DrawingML\\TextBox\\TestDmlListsBulletSize.docx";
        Document doc = TestUtil.open(DOC_FILE_NAME);

        checkBulletSize(getTextShapeParagraph(doc, 0, 2), DmlTextBulletSizeType.PERCENTAGE, 200000d);

        // Reading from ISO 29500 Strict
        OoxmlSaveOptions options = new OoxmlSaveOptions();
        options.setComplianceCore(OoxmlComplianceCore.ISO_STRICT);
        doc = TestUtil.saveOpen(doc, DOC_FILE_NAME, options, false);

        checkBulletSize(getTextShapeParagraph(doc, 0, 2), DmlTextBulletSizeType.PERCENTAGE, 200000d);
    }

    /// <summary>
    /// Checks bullet size of DML paragraph.
    /// </summary>
    private static void checkBulletSize(DmlParagraph paragraph, /*DmlTextBulletSizeType*/int expectedSizeType,
        double expectedSize)
    {
        DmlTextBulletSize bulletSize = paragraph.getProperties().getBulletSize();
        msAssert.areEqual(expectedSizeType, bulletSize.getSizeType());
        msAssert.areEqual(expectedSize, bulletSize.getValue());
    }

    /// <summary>
    /// Gets paragraph of DML text shape that are specified by their indexes.
    /// </summary>
    private static DmlParagraph getTextShapeParagraph(Document doc, int shapeIndex, int paraIndex)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, shapeIndex, true);
        DmlShape dmlShape = (DmlShape)shape.getDmlNode();
        return (DmlParagraph)dmlShape.getTextShape().getTextBody().getParagraphs().get(paraIndex);
    }

    /// <summary>
    /// Tests that ds (Dash Stop) element is written correctly.
    /// </summary>
    @Test (dataProvider = "testDashStopDataProvider")
    public void testDashStop(boolean isIsoStrict, String expectedDash, String expectedSpace) throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\TestDashStop.docx");

        DmlParagraph paragraph = getTextShapeParagraph(doc, 0, 0);
        DmlRun run = (DmlRun)paragraph.getElements().get(0);
        DmlDashStop dashStop = ((DmlCustomDash)run.getRunProperties().getOutline().getDash()).getDashStops().get(0);
        msAssert.areEqual(0.1d, dashStop.getDashLength());
        msAssert.areEqual(0.05d, dashStop.getSpaceLength());

        DocxExportContext context = new DocxExportContext(doc, DOCUMENT_PART,
            (isIsoStrict ? OoxmlComplianceCore.ISO_STRICT : OoxmlComplianceCore.ECMA_376));
        checkAttributeValue(context, "//a:ds/@d", expectedDash);
        checkAttributeValue(context, "//a:ds/@sp", expectedSpace);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDashStopDataProvider")
	public static Object[][] testDashStopDataProvider() throws Exception
	{
		return new Object[][]
		{
			{false,  "10000",  "5000"},
			{true,  "10%",  "5%"},
		};
	}

    /// <summary>
    /// Tests that elements of the CT_TblWidth simple type are read and written correctly.
    /// </summary>
    @Test (dataProvider = "testReadWriteOfTableWidthDataProvider")
    public void testReadWriteOfTableWidth(boolean isIsoStrict) throws Exception
    {
        Document testDoc = new Document();
        DocumentBuilder builder = new DocumentBuilder(testDoc);

        Table table1 = insertTable(builder);
        setPreferredWidth(table1, PreferredWidth.fromPointsSafe(720.0));
        Table table2 = insertTable(builder);
        setPreferredWidth(table2, PreferredWidth.fromPercentSafe(60.0));
        Table table3 = insertTable(builder);
        setPreferredWidth(table3, PreferredWidth.fromRawSafe(PreferredWidthType.AUTO, 3600));

        OoxmlSaveOptions saveOptions = new OoxmlSaveOptions();
        saveOptions.setComplianceCore(isIsoStrict ? OoxmlComplianceCore.ISO_STRICT : OoxmlComplianceCore.ECMA_376);
        Document tmpDoc =
            TestUtil.saveOpen(testDoc, "ExportDocx\\TestReadWriteOfTableWidth.docx", saveOptions, false);
        NodeCollection tables = tmpDoc.getChildNodes(NodeType.TABLE, true);
        msAssert.areEqual(3, tables.getCount());

        checkPreferredWidth(table1, (Table)tables.get(0), "table1");
        checkPreferredWidth(table2, (Table)tables.get(1), "table2");
        checkPreferredWidth(table3, (Table)tables.get(2), "table3");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testReadWriteOfTableWidthDataProvider")
	public static Object[][] testReadWriteOfTableWidthDataProvider() throws Exception
	{
		return new Object[][]
		{
			{false},
			{true},
		};
	}

    /// <summary>
    /// Sets preferred width to the table.
    /// </summary>
    private static void setPreferredWidth(Table table, PreferredWidth width)
    {
        table.getFirstRow().getTablePr().setPreferredWidth(width);
    }

    /// <summary>
    /// Checks that the first rows of the specified tables have equal preferred widths.
    /// </summary>
    private static void checkPreferredWidth(Table expectedTable, Table actualTable, String tableName)
    {
        PreferredWidth expected = expectedTable.getFirstRow().getTablePr().getPreferredWidth();
        PreferredWidth actual = actualTable.getFirstRow().getTablePr().getPreferredWidth();

        msAssert.areEqual(expected.getType(), actual.getType(), msString.format("Wrong value type ({0}).", tableName));
        msAssert.areEqual(expected.getValueRaw(), actual.getValueRaw(), msString.format("Wrong value ({0}).", tableName));
    }

    /// <summary>
    /// Tests that elements of the CT_TblWidth simple type are written correctly on saving as
    /// ISO/IEC 29500-1:2012 format.
    /// </summary>
    @Test
    public void testTableWidthValues() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table1 = insertTable(builder);
        setPreferredWidth(table1, PreferredWidth.fromPointsSafe(720.0));
        Table table2 = insertTable(builder);
        setPreferredWidth(table2, PreferredWidth.fromPercentSafe(60.0));
        Table table3 = insertTable(builder);
        setPreferredWidth(table3,
            PreferredWidth.fromRawSafe(PreferredWidthType.AUTO, 3600)); // 3600 twips = 180 points

        table1.setTopPadding(20.0);
        Cell cell = table1.getCell(0, 0);
        cell.getCellPr().setTopPadding(300); // 300 twips = 15 points

        DocxExportContext context = new DocxExportContext(doc, DOCUMENT_PART, OoxmlComplianceCore.ISO_STRICT);
        ArrayList<org.w3c.dom.Node> nodes = context.getXmlNodes("//w:tr/w:tblPrEx/w:tblW");
        msAssert.areEqual(3, nodes.size());

        checkAttributeValue(context, (org.w3c.dom.Element)nodes.get(0), "@w:w", "720pt");
        checkAttributeValue(context, (org.w3c.dom.Element)nodes.get(0), "@w:type", "dxa");
        checkAttributeValue(context, (org.w3c.dom.Element)nodes.get(1), "@w:w", "60%");
        checkAttributeValue(context, (org.w3c.dom.Element)nodes.get(1), "@w:type", "pct");
        checkAttributeValue(context, (org.w3c.dom.Element)nodes.get(2), "@w:w", "180pt");
        checkAttributeValue(context, (org.w3c.dom.Element)nodes.get(2), "@w:type", "auto");

        org.w3c.dom.Element node = (org.w3c.dom.Element)context.getXmlNode("//w:tblCellMar/w:top");
        msAssert.isNotNull(node);
        checkAttributeValue(context, node, "@w:w", "20pt");
        checkAttributeValue(context, node, "@w:type", "dxa");

        node = (org.w3c.dom.Element)context.getXmlNode("//w:tcMar/w:top");
        msAssert.isNotNull(node);
        checkAttributeValue(context, node, "@w:w", "15pt");
        checkAttributeValue(context, node, "@w:type", "dxa");
    }

    /// <summary>
    /// Inserts a table of one cell with using the <see cref="builder"/> object.
    /// </summary>
    private static Table insertTable(DocumentBuilder builder)
    {
        Table table = builder.startTable();
        builder.insertCell();
        builder.endRow();
        builder.endTable();
        builder.insertParagraph();
        return table;
    }

    /// <summary>
    /// Tests the <see cref="NrxXmlReader.IsUniversalMeasure"/> method.
    /// </summary>
    @Test (dataProvider = "testIsUniversalMeasureDataProvider") // casting for C++
    public void testIsUniversalMeasure(String value, boolean expectedIsUniversalMeasure)
    {
        msAssert.areEqual(expectedIsUniversalMeasure, NrxXmlReader.isUniversalMeasure(value), msString.format(                "Error on checking '{0}'.", value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIsUniversalMeasureDataProvider")
	public static Object[][] testIsUniversalMeasureDataProvider()
	{
		return new Object[][]
		{
			{(String)null,  false},
			{"",  false},
			{"0",  false},
			{"-25.75pt",  true},
			{"1cm",  true},
			{"1dm",  false},
			{"0.9pc",  true},
			{"-0.9pi",  true},
		};
	}

    /// <summary>
    /// Tests that all compatibility settings except adjustLineHeightInTable, applyBreakingRules,
    /// balanceSingleByteDoubleByteWidth, compatSetting, doNotExpandShiftReturn, doNotLeaveBackslashAlone,
    /// spaceForUL, ulTrailSpace are not written in ISO Strict format.
    /// </summary>
    @Test
    public void testRemovedCompatibilitySettings() throws Exception
    {
        DocxExportContext context =
            new DocxExportContext(TestUtil.open("ExportDocx\\TestRemovedCompatibilitySettings.docx"),
                    SETTINGS_PART, OoxmlComplianceCore.ISO_STRICT);

        org.w3c.dom.Node node = context.getXmlNode("descendant::w:compat");
        msAssert.isNotNull(node);
        for (int i = 0; i < node.getChildNodes().getLength(); i++)
        {
            org.w3c.dom.Node compatNode = node.getChildNodes().item(i);
            if (compatNode.getNodeType() == XmlNodeType.ELEMENT)
            {
                msAssert.isTrue(isIsoStrictCompatibilitySetting(compatNode.getLocalName()), msString.format(                        "The compatibility setting {0} is not supported in ISO 29500 Strict.", compatNode.getLocalName()));
            }
        }
    }

    /// <summary>
    /// Returns the 'true' value if the specified compatibility setting is supported in ISO 29500 Strict.
    /// </summary>
    private static boolean isIsoStrictCompatibilitySetting(String settingName)
    {
        return
            ("compatSetting".equals(settingName)) ||
            ("adjustLineHeightInTable".equals(settingName)) ||
            ("applyBreakingRules".equals(settingName)) ||
            ("balanceSingleByteDoubleByteWidth".equals(settingName)) ||
            ("doNotExpandShiftReturn".equals(settingName)) ||
            ("doNotLeaveBackslashAlone".equals(settingName)) ||
            ("spaceForUL".equals(settingName)) ||
            ("ulTrailSpace".equals(settingName));
    }


    /// <summary>
    /// Returns the 'true' value if the specified attribute is supported attribute of the protection elements
    /// in ISO 29500 Strict.
    /// </summary>
    private static boolean isIsoStrictProtectionAttribute(String attributeName)
    {
        return
            ("algorithmName".equals(attributeName)) ||
            ("edit".equals(attributeName)) ||
            ("enforcement".equals(attributeName)) ||
            ("formatting".equals(attributeName)) ||
            ("hashValue".equals(attributeName)) ||
            ("recommended".equals(attributeName)) ||
            ("saltValue".equals(attributeName)) ||
            ("spinCount".equals(attributeName));
    }

    /// <summary>
    /// Tests conversion of cryptographic algorithm name to/from algorithm SID.
    /// </summary>
    @Test (dataProvider = "testCryptAlgorithmNameFromSidDataProvider")
    public void testCryptAlgorithmNameFromSid(String algorithmName)
    {
        int sid = PasswordHash.cryptAlgorithmSidFromAlgorithmName(algorithmName);
        msAssert.areEqual(algorithmName, PasswordHash.cryptAlgorithmNameFromSid(sid));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testCryptAlgorithmNameFromSidDataProvider")
	public static Object[][] testCryptAlgorithmNameFromSidDataProvider()
	{
		return new Object[][]
		{
			{"MD2"},
			{"MD4"},
			{"MD5"},
			{"RIPEMD-128"},
			{"RIPEMD-160"},
			{"SHA-1"},
			{"SHA-256"},
			{"SHA-384"},
			{"SHA-512"},
			{"WHIRLPOOL"},
		};
	}

    /// <summary>
    /// Tests that the hdrShapeDefaults and shapeDefaults elements are not written in ISO Strict format.
    /// </summary>
    /// <summary>
    /// Tests that the val attribute of the stylePaneFormatFilter element (§17.15.1.85) is not written
    /// in ISO Strict format.
    /// </summary>
    @Test
    public void testStylePaneFormatFilter() throws Exception
    {
        Document doc = new Document();
        doc.getDocPr().StylePaneFormatFilterSettings.setData(doc.getDocPr().StylePaneFormatFilterSettings.getData() + 1);

        DocxExportContext context = new DocxExportContext(doc, SETTINGS_PART, OoxmlComplianceCore.ISO_STRICT);

        org.w3c.dom.Node node = context.getXmlNode("descendant::w:stylePaneFormatFilter");
        msAssert.isNotNull(node);
        node = node.getAttributes().getNamedItem("w:val");
        msAssert.isNull(node, "The val attribute of the stylePaneFormatFilter element is written in ISO Strict format.");
    }

    /// <summary>
    /// Tests the <see cref="NrxXmlUtil.ConvertMeasure"/> method.
    /// </summary>
    @Test (dataProvider = "testMeasureConversionDataProvider") // Casting for C++.
    public void testMeasureConversion(double value, int sourceUnit, int targetUnit, double expectedResult)
    {
        msAssert.areEqual(msMath.round(expectedResult, 3), msMath.round(NrxXmlUtil.convertMeasure(value, (/*NrxUnit*/int)sourceUnit, (/*NrxUnit*/int)targetUnit), 3), msString.format(                "Error on conversion of {0} from {1} to {2}.", value, sourceUnit, targetUnit));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testMeasureConversionDataProvider")
	public static Object[][] testMeasureConversionDataProvider()
	{
		return new Object[][]
		{
			{-1d,  (int)NrxUnit.INCH,  (int)NrxUnit.MILLIMETER,  -25.4d},
			{1d,  (int)NrxUnit.INCH,  (int)NrxUnit.CENTIMETER,  2.54d},
			{1d,  (int)NrxUnit.INCH,  (int)NrxUnit.INCH,  1d},
			{0d,  (int)NrxUnit.INCH,  (int)NrxUnit.MILLIMETER,  0d},
			{254d,  (int)NrxUnit.MILLIMETER,  (int)NrxUnit.INCH,  10d},
			{-2d,  (int)NrxUnit.CENTIMETER,  (int)NrxUnit.MILLIMETER,  -20d},
			{480d,  (int)NrxUnit.TWIPS,  (int)NrxUnit.PICA,  2d},
			{2d,  (int)NrxUnit.POINT,  (int)NrxUnit.POINT,  2d},
			{24d,  (int)NrxUnit.HALF_POINTS,  (int)NrxUnit.TWIPS,  240d},
			{24d,  (int)NrxUnit.POINT,  (int)NrxUnit.HALF_POINTS,  48d},
			{2d,  (int)NrxUnit.TWIPS,  (int)NrxUnit.HUNDREDTHS_OF_POINT,  10d},
			{1d,  (int)NrxUnit.HUNDREDTHS_OF_POINT,  (int)NrxUnit.EMUS,  127d},
			{12700d,  (int)NrxUnit.EMUS,  (int)NrxUnit.POINT,  1d},
			{24d,  (int)NrxUnit.PICA,  (int)NrxUnit.INCH,  4d},
		};
	}

    /// <summary>
    /// Tests the <see cref="NrxXmlBuilder.ToUniversalMeasure"/> method.
    /// </summary>
    /// <remarks>
    /// Conversion itself is tested in <see cref="TestMeasureConversion"/>.
    /// </remarks>
    @Test (dataProvider = "testFormingUniversalMeasureDataProvider") // Casting for C++.
    public void testFormingUniversalMeasure(double value, int unit, String expectedResult)
    {
        msAssert.areEqual(expectedResult, NrxXmlBuilder.toUniversalMeasure(value, (/*NrxUnit*/int)unit, (/*NrxUnit*/int)unit), msString.format(                "Error on {0} ({1}).", value, unit));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFormingUniversalMeasureDataProvider")
	public static Object[][] testFormingUniversalMeasureDataProvider()
	{
		return new Object[][]
		{
			{-1.05d,  (int)NrxUnit.MILLIMETER,  "-1.05mm"},
			{1d,  (int)NrxUnit.CENTIMETER,  "1cm"},
			{-999999.999999999d,  (int)NrxUnit.INCH,  "-999999.999999999in"},
			{2d,  (int)NrxUnit.POINT,  "2pt"},
			{1.11d,  (int)NrxUnit.PICA,  "1.11pc"},
		};
	}

    /// <summary>
    /// Tests the <see cref="NrxXmlBuilder.ToUniversalMeasure"/> method.
    /// </summary>
    /// <remarks>
    /// Conversion itself is tested in <see cref="TestMeasureConversion"/>.
    /// Test cases with Exception.
    /// </remarks>
    @Test (expectedExceptions = IllegalStateException.class, dataProvider = "testFormingUniversalMeasure1DataProvider") // Casting for C++.
    public void testFormingUniversalMeasure1(double value, int unit, String expectedResult)
    {
        msAssert.areEqual(expectedResult, NrxXmlBuilder.toUniversalMeasure(value, (/*NrxUnit*/int)unit, (/*NrxUnit*/int)unit), msString.format(                "Error on {0} ({1}).", value, unit));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFormingUniversalMeasure1DataProvider")
	public static Object[][] testFormingUniversalMeasure1DataProvider()
	{
		return new Object[][]
		{
			{0d,  (int)NrxUnit.EMUS,  ""},
			{0d,  (int)NrxUnit.HUNDREDTHS_OF_POINT,  ""},
			{0d,  (int)NrxUnit.TWIPS,  ""},
			{0d,  (int)NrxUnit.HALF_POINTS,  ""},
			{0d,  (int)NrxUnit.NONE,  ""},
		};
	}

    /// <summary>
    /// Tests that the charset (Character Set Supported By Font) element is read and written correctly from/to
    /// ECMA-376 and ISO Strict formats..
    /// </summary>
    @Test (dataProvider = "testElementNotWrittenDataProvider")
    public void testElementNotWritten(String documentFile, String element, int warningIndex) throws Exception
    {
        Document doc = TestUtil.open(documentFile);
        TestWarningCallback warningCallback = new TestWarningCallback();
        doc.setWarningCallback(warningCallback);

        DocxExportContext context = new DocxExportContext(doc, OoxmlComplianceCore.ISO_STRICT);

        org.w3c.dom.Node node = context.getXmlNode(msString.format("//w:{0}", element));
        msAssert.isNull(node);

        msAssert.isTrue(warningCallback.getCount() > warningIndex);
        msAssert.isTrue(warningCallback.get(warningIndex).getDescription().contains(element));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testElementNotWrittenDataProvider")
	public static Object[][] testElementNotWrittenDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Revision\\TestRevisionNumberRemoved ms.docx",  "numberingChange",  0},
		};
	}

    /// <summary>
    /// Tests that ISO Strict namespaces are written in ISO Strict format.
    /// </summary>
    @Test
    public void testWritingCommonNamespaces() throws Exception
    {
        DocxExportContext context = new DocxExportContext(new Document(), OoxmlComplianceCore.ISO_STRICT);

        org.w3c.dom.Node documentNode = context.getXmlDocument().getChildNodes().item(INDEX);
        checkAttributeValue(documentNode, "xmlns:w", URL_PREFIX + "wordprocessingml/main");
        checkAttributeValue(documentNode, "xmlns:m", DOC_URL_PREFIX + "math");
        checkAttributeValue(documentNode, "xmlns:r", DOC_URL_PREFIX + "relationships");
        checkAttributeValue(documentNode, "xmlns:wp", DML_URL_PREFIX + "wordprocessingDrawing");
    }

    /// <summary>
    /// Tests that ISO Strict form of namespace of the 'graphicData' element is written in ISO Strict format.
    /// </summary>
    @Test (dataProvider = "testWritingGraphicDataNamespaceDataProvider")
    public void testWritingGraphicDataNamespace(String docFileName, String expectedNamespace) throws Exception
    {
        DocxExportContext context = new DocxExportContext(TestUtil.open(docFileName),
            OoxmlComplianceCore.ISO_STRICT);
        checkAttributeValue(context, "//a:graphicData/@uri", expectedNamespace);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testWritingGraphicDataNamespaceDataProvider")
	public static Object[][] testWritingGraphicDataNamespaceDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Charts\\Test3DAreaCharts.docx",  DML_URL_PREFIX + "chart"},
			{"Model\\DrawingML\\TestDiagramBlip.docx",  DML_URL_PREFIX + "diagram"},
			{"Model\\DrawingML\\TestImageData.docx",  DML_URL_PREFIX + "picture"},
		};
	}

    /// <summary>
    /// Tests that correct namespaces of extended properties are written in ISO Strict format.
    /// </summary>
    @Test
    public void testWritingExtendedPropertiesNamespaces() throws Exception
    {
        DocxExportContext context = new DocxExportContext(new Document(), APP_DOC_PROPS_PART,
            OoxmlComplianceCore.ISO_STRICT);

        org.w3c.dom.Node rootNode = context.getXmlDocument().getChildNodes().item(INDEX);
        checkAttributeValue(rootNode, "xmlns", DOC_URL_PREFIX + "extendedProperties");
        checkAttributeValue(rootNode, "xmlns:vt", DOC_URL_PREFIX + "docPropsVTypes");
    }


    /// <summary>
    /// Tests reading/writing document background from a Strict OOXML document.
    /// </summary>
    @Test (dataProvider = "testReadingWritingBackgroundDataProvider")
    public void testReadingWritingBackground(String fileName, String expectedFill) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        msAssert.isNotNull(doc.getBackgroundShape());
        ShapeBase dml = doc.getBackgroundShape().getFallbackShape();
        msAssert.isNotNull(dml);
        msAssert.isNotNull(dml.getDmlNode().getDocPrExtensions());
        msAssert.areEqual(1, dml.getDmlNode().getDocPrExtensions().getCount());

        DocxExportContext context = new DocxExportContext(doc, OoxmlComplianceCore.ISO_STRICT);

        msAssert.isNotNull(context.getXmlNode("//w:background/mc:AlternateContent/mc:Choice/v:background"));
        org.w3c.dom.Element dmlNode = (org.w3c.dom.Element)context.getXmlNode("//mc:AlternateContent/mc:Fallback/w:drawing");
        msAssert.isNotNull(dmlNode);
        msAssert.isNotNull(context.getXmlNode(dmlNode, "//wp:docPr/a:extLst/a:ext"));
        msAssert.isNotNull(context.getXmlNode(dmlNode, "//a:" + expectedFill));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testReadingWritingBackgroundDataProvider")
	public static Object[][] testReadingWritingBackgroundDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Shape\\Background\\TestIsoStrictBackground.docx",  "gradFill"},
			{"Model\\Shape\\Background\\TestBackgroundSolid_Strict_EmptyChoice.docx",  "solidFill"},
		};
	}


    /// <summary>
    /// Tests relationship type of mail merge recipient data.
    /// </summary>
    @Test
    public void testMailMergeRecipientData()
    {
        msAssert.areEqual("http://purl.oclc.org/ooxml/officeDocument/relationships/mailMergeRecipientData", DocxRelationshipTypes.getType(DocxRelationshipType.MAIL_MERGE_RECIPIENT_DATA, true));
    }


    /// <summary>
    /// Tests that correct namespaces of the theme part are written in ISO Strict format.
    /// </summary>
    @Test
    public void testWritingThemeNamespaces() throws Exception
    {
        Document doc = new Document();
        doc.getTheme().createThemeOverride();
        DocxExportContext context = new DocxExportContext(doc, THEME_PART, OoxmlComplianceCore.ISO_STRICT);

        org.w3c.dom.Element rootNode = (org.w3c.dom.Element)context.getXmlDocument().getChildNodes().item(INDEX);
        checkAttributeValue(rootNode, "xmlns:a", DML_URL_PREFIX + "main");
        org.w3c.dom.Node fmtSchemeNode = context.getXmlNode(rootNode, "//a:themeElements/a:fmtScheme");
        msAssert.isNotNull(fmtSchemeNode);
        msAssert.areEqual(DML_URL_PREFIX + "main", fmtSchemeNode.getNamespaceURI());
    }





    /// <summary>
    /// Tests that all relationship types are defined for Strict OOXML format.
    /// </summary>
    @Test
    public void testRelationshipTypes()
    {
        /*DocxRelationshipType*/int[] relationshipTypes =
            DocxRelationshipType.getValues();
        for (/*DocxRelationshipType*/int relationshipType : relationshipTypes)
        {
            msAssert.isTrue(StringUtil.hasChars(DocxRelationshipTypes.getType(relationshipType, true)), msString.format(                    "No relationship type for {0}.", relationshipType));
        }
    }




    private OoxmlSaveOptions createStrictSaveOptions()
    {
        OoxmlSaveOptions returnInitializer = new OoxmlSaveOptions();
        {
            returnInitializer.setCompliance(OoxmlCompliance.ISO_29500_2008_STRICT);
            returnInitializer.setSaveFormat(SaveFormat.DOCX);
        }
        return returnInitializer;
    }

    // Remove preprocessor after fix WORDSJAVA-1370.
    private static final int INDEX =
        0;

    private static final String DOCUMENT_PART = "word/document.xml";
    private static final String SETTINGS_PART = "word/settings.xml";
    private static final String FONTS_PART = "word/fontTable.xml";
    private static final String DOCUMENT_RELS_PART = "word/_rels/document.xml.rels";
    private static final String APP_DOC_PROPS_PART = "docProps/app.xml";
    private static final String CUSTOM_DOC_PROPS_PART = "docProps/custom.xml";
    private static final String THEME_PART = "word/theme/theme1.xml";
    private static final String NUMBERING_PART = "word/numbering.xml";
    private static final String STYLES_PART = "word/styles.xml";
    private static final String CHART_COLOR_STYLE_PART = "word/charts/chart/colors1.xml";
    private static final String CHART_STYLE_PART = "word/charts/chart/style1.xml";
    private static final String THEME_OVERRIDE_PART = "word/theme/themeOverride1.xml";

    private static final String URL_PREFIX = "http://purl.oclc.org/ooxml/";
    private static final String DML_URL_PREFIX = "http://purl.oclc.org/ooxml/drawingml/";
    private static final String DOC_URL_PREFIX = "http://purl.oclc.org/ooxml/officeDocument/";
}

