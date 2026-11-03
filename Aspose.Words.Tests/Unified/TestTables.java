// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ConvertUtilCore;
import com.aspose.DrColor;
import java.awt.Color;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Convert;
import com.aspose.ms.System.msString;
import com.aspose.IntList;
import com.aspose.ArrayUtil;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for tables.
///
/// Most of the detailed table property reading and writing is done in TestTablePrFiler.
/// These tests check that the whole thing hangs together and the produced document can be read back again.
/// </summary>
@Test
public class TestTables extends UnifiedTestsBase
{
    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestSimple(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestSimple", lf, sf);
        msAssert.areEqual("1.1\u0007\u0007\u00072.1\u00072.2\u0007\u0007\f", doc.getText());
        msAssert.areEqual(1, doc.selectNodes("//Table").getCount());
        msAssert.areEqual(2, doc.selectNodes("//Row").getCount());
        msAssert.areEqual(4, doc.selectNodes("//Cell").getCount());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSimpleSourceDataProvider")
	public void unifiedTestSimpleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSimple(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSimpleSourceDataProvider")
	Object[][] unifiedTestSimpleSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Check that table properties are read properly.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTableMisc(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestTable", lf, sf);

        NodeList rows = doc.selectNodes("//Row");
        msAssert.areEqual(2, rows.getCount());

        Row row = (Row)rows.get(0);
        msAssert.areEqual(4, row.selectNodes("Cell").getCount());
        msAssert.areEqual("A1\rAnother paragraph in a cell.\u0007B1\u0007C1\rC2\u0007D1\u0007\u0007", row.getText());

        // Check first row properties
        TablePr tablePr = row.getTablePr();
        msAssert.areEqual(0, tablePr.getLeftIndent());
        msAssert.areEqual(true, tablePr.getAllowAutoFit());
        msAssert.areEqual(0, tablePr.getLeftPadding());
        msAssert.areEqual(0, tablePr.getRightPadding());
        msAssert.areEqual(4, row.getCells().getCount());
        msAssert.areEqual(ConvertUtilCore.inchToTwip(1.5), row.getCells().get(0).getCellPr().getWidth());
        msAssert.areEqual(ConvertUtilCore.inchToTwip(1.0), row.getCells().get(1).getCellPr().getWidth());
        msAssert.areEqual(ConvertUtilCore.inchToTwip(2.0), row.getCells().get(2).getCellPr().getWidth());
        msAssert.areEqual(ConvertUtilCore.inchToTwip(1.5), row.getCells().get(3).getCellPr().getWidth());

        // Test diagonal borders
        CellPr cellPr = row.getCells().get(0).getCellPr();
        msAssert.areEqual(LineStyle.SINGLE, cellPr.getBorderDiagonalDown().getLineStyle());

        // Check the first vertically merged cell.
        cellPr = row.getCells().get(2).getCellPr();
        msAssert.areEqual(CellVerticalAlignment.BOTTOM, cellPr.getVerticalAlignment());
        msAssert.areEqual(CellMerge.FIRST, cellPr.getVerticalMerge());

        // Check second row
        row = (Row)rows.get(1);
        msAssert.areEqual(5, row.getCells().getCount());

        // Check cell border
        cellPr = row.getCells().get(1).getCellPr();
        msAssert.areEqual(3d, cellPr.getBorderLeft().getLineWidth());

        // Check the vertically merged cell. It is essentially empty, only fVertMerge is set.
        cellPr = row.getCells().get(2).getCellPr();
        msAssert.areEqual(CellMerge.PREVIOUS, cellPr.getVerticalMerge());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTableMiscSourceDataProvider")
	public void unifiedTestTableMiscSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTableMisc(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTableMiscSourceDataProvider")
	Object[][] unifiedTestTableMiscSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    /// <summary>
    /// Check that we write huge papx properly.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTableHugePapxReadWrite(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestTableHugePapx", lf, sf);

        NodeList rows = doc.selectNodes("//Row");
        msAssert.areEqual(4, rows.getCount());
        Row row = (Row)rows.get(0);


        CellPr cellPr;
        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
            {
                cellPr = row.getCells().get(0).getCellPr();
                break;
            }
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOC_2_WML:
            {
                // In MS generated WordML this formatting is defined in 'w:tblStylePr' element of table style.
                TableStyle style = (TableStyle)doc.getStyles().get("Table Classic 3");
                ConditionalStyle firstRowPr = style.getConditionalStyles().getFirstRow();
                cellPr = firstRowPr.getCellPr();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected scenario.");
        }
        // Check shading because I had problems with it.
        Shading shd = cellPr.getShading();
        // This is dark blue, but Word dark blue is 0x80 and Windows dark blue is 0x8b.
        msAssert.areEqual(DrColor.fromArgb(0, 0, 0x80), shd.getForegroundPatternColorInternal());
        msAssert.areEqual(Color.WHITE.getRGB(), shd.getBackgroundPatternColor().getRGB());
        msAssert.areEqual(TextureIndex.TEXTURE_SOLID, shd.getTexture());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTableHugePapxReadWriteSourceDataProvider")
	public void unifiedTestTableHugePapxReadWriteSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTableHugePapxReadWrite(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTableHugePapxReadWriteSourceDataProvider")
	Object[][] unifiedTestTableHugePapxReadWriteSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTableNested(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestTableNested", lf, sf);

        msAssert.areEqual("Nested 1.1.1\u0007\u0007Nested 1.1\u0007Nested 1.2\u0007\u0007Outer 1\u0007" +
                "Outer 2\rNested 2.1\u0007Nested 2.2\u0007\u0007\u0007\u0007\f", doc.getText());

        Cell l1Cell = (Cell)doc.selectSingleNode("//Cell[1]");

        //Yes, there is a nested table available.
        Row l2Row = (Row)l1Cell.selectSingleNode(".//Row");
        msAssert.isNotNull(l2Row);

        //There are two cells in this row and one at deeper level.
        NodeList cells = l2Row.selectNodes(".//Cell");
        msAssert.areEqual(3, cells.getCount());
        //Just check the content of the most nested cell.
        Cell l3Cell = (Cell)cells.get(1);
        msAssert.areEqual("Nested 1.1.1\u0007", l3Cell.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTableNestedSourceDataProvider")
	public void unifiedTestTableNestedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTableNested(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTableNestedSourceDataProvider")
	Object[][] unifiedTestTableNestedSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTablePreferredWidthPercent(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestTablePreferredWidthPercent", lf, sf);

        Row row = (Row)doc.selectSingleNode("//Row");
        TablePr tablePr = row.getTablePr();
        msAssert.areEqual(PreferredWidthType.PERCENT, tablePr.getPreferredWidth().getType());
        msAssert.areEqual((int)(97.5 * 50.0), tablePr.getPreferredWidth().getValueRaw());    //Specified in 1/50 percent.
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTablePreferredWidthPercentSourceDataProvider")
	public void unifiedTestTablePreferredWidthPercentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTablePreferredWidthPercent(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTablePreferredWidthPercentSourceDataProvider")
	Object[][] unifiedTestTablePreferredWidthPercentSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCellPadding(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestCellPadding", lf, sf);

        Table table = (Table)doc.getChild(NodeType.TABLE, 2, true);

        //Table defaults.
        msAssert.areEqual(0d, table.getLeftPadding());
        msAssert.areEqual(1d, table.getTopPadding());
        msAssert.areEqual(2d, table.getBottomPadding());
        msAssert.areEqual(3d, table.getRightPadding());

        Row row = table.getFirstRow();

        //This cell overrides left and right padding.
        Cell cell = (Cell)row.getChildNodes(NodeType.ANY, false).get(0);
        msAssert.areEqual(10d, cell.getCellFormat().getLeftPadding());
        msAssert.areEqual(1d, cell.getCellFormat().getTopPadding());
        msAssert.areEqual(2d, cell.getCellFormat().getBottomPadding());
        msAssert.areEqual(10d, cell.getCellFormat().getRightPadding());

        //This cell inherits all padding from the table.
        cell = (Cell)row.getChildNodes(NodeType.ANY, false).get(2);
        msAssert.areEqual(0d, cell.getCellFormat().getLeftPadding());
        msAssert.areEqual(1d, cell.getCellFormat().getTopPadding());
        msAssert.areEqual(2d, cell.getCellFormat().getBottomPadding());
        msAssert.areEqual(3d, cell.getCellFormat().getRightPadding());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCellPaddingSourceDataProvider")
	public void unifiedTestCellPaddingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCellPadding(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCellPaddingSourceDataProvider")
	Object[][] unifiedTestCellPaddingSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// MS Word has different default for left and right cell padding in DOC/RTF vs DOCX/WML.
    /// This checks that we produce correct files that appear the same regardless of the saved format.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCellPaddingCreatedTable(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        Table table = new Table(doc);
        doc.getFirstSection().getBody().appendChild(table);
        Row row = new Row(doc);
        table.appendChild(row);
        Cell cell = new Cell(doc);
        row.appendChild(cell);
        Paragraph para = new Paragraph(doc);
        cell.appendChild(para);
        cell.getCellFormat().getBorders().setLineWidth(1.0);
        cell.getCellFormat().getBorders().setColor(Color.BLACK);
        cell.getCellFormat().getBorders().setLineStyle(LineStyle.SINGLE);
        Run run = new Run(doc, "Test");
        para.appendChild(run);

        row = (Row)row.deepClone(true);
        table.appendChild(row);
        row.getTablePr().setLeftPadding(200);

        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestCellPaddingCreatedTable", lf, sf);
        row = (Row)doc.getChild(NodeType.ROW, 0, true);
        TablePr tablePr = row.getTablePr();

        switch (sf)
        {
            case SaveFormat.DOCX:
            case SaveFormat.DOC:
            case SaveFormat.WORD_ML:
                msAssert.areEqual(108, tablePr.get(TableAttr.LEFT_PADDING));
                msAssert.areEqual(108, tablePr.get(TableAttr.RIGHT_PADDING));
                break;
            case SaveFormat.RTF:
                // WORDSNET-12347 Direct attributes are collapsed over style.
                msAssert.areEqual(null, tablePr.get(TableAttr.LEFT_PADDING));
                msAssert.areEqual(null, tablePr.get(TableAttr.RIGHT_PADDING));
                break;
            default:
                throw new IllegalStateException("Unknown file format");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCellPaddingCreatedTableSourceDataProvider")
	public void unifiedTestCellPaddingCreatedTableSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCellPaddingCreatedTable(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCellPaddingCreatedTableSourceDataProvider")
	Object[][] unifiedTestCellPaddingCreatedTableSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// See comments in RowFormat, cell shading is MS Word does not actually seem to be inherited.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestInheritCellShading(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestInheritCellShading", lf, sf);

        Row row = (Row)doc.selectSingleNode("//Row");

        //Default table shading
        Cell cell = (Cell)row.getChildNodes(NodeType.ANY, false).get(0);
        msAssert.areEqual(Color.RED.getRGB(), cell.getCellFormat().getShading().getBackgroundPatternColor().getRGB());

        //Explicit shading off
        cell = (Cell)row.getChildNodes(NodeType.ANY, false).get(1);
        msAssert.areEqual(0, cell.getCellFormat().getShading().getBackgroundPatternColor().getRGB());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInheritCellShadingSourceDataProvider")
	public void unifiedTestInheritCellShadingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInheritCellShading(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInheritCellShadingSourceDataProvider")
	Object[][] unifiedTestInheritCellShadingSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestInheritCellBorders(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestInheritCellBorders", lf, sf);

        Row row = (Row)doc.selectSingleNode("//Row[1]");
        Cell cell;

        // RK This info is stored in the table style in DOCX, but in other formats it is direct formatting.
        if (!(lf == LoadFormat.DOCX || lf == LoadFormat.DOC))
        {
            // Check default table borders
            msAssert.areEqual(LineStyle.SINGLE, row.getRowFormat().getBorders().getByBorderType(BorderType.TOP).getLineStyle());
            msAssert.areEqual(LineStyle.SINGLE, row.getRowFormat().getBorders().getByBorderType(BorderType.LEFT).getLineStyle());
            msAssert.areEqual(LineStyle.SINGLE, row.getRowFormat().getBorders().getByBorderType(BorderType.BOTTOM).getLineStyle());
            msAssert.areEqual(LineStyle.SINGLE, row.getRowFormat().getBorders().getByBorderType(BorderType.RIGHT).getLineStyle());
            msAssert.areEqual(LineStyle.SINGLE, row.getRowFormat().getBorders().getByBorderType(BorderType.HORIZONTAL).getLineStyle());
            msAssert.areEqual(LineStyle.SINGLE, row.getRowFormat().getBorders().getByBorderType(BorderType.VERTICAL).getLineStyle());

            // Check the cell inherits default table borders
            cell = (Cell)row.getChildNodes(NodeType.ANY, false).get(0);
            msAssert.areEqual(LineStyle.SINGLE, cell.getCellFormat().getBorders().getByBorderType(BorderType.TOP).getLineStyle());
            msAssert.areEqual(LineStyle.SINGLE, cell.getCellFormat().getBorders().getByBorderType(BorderType.LEFT).getLineStyle());
            msAssert.areEqual(LineStyle.SINGLE, cell.getCellFormat().getBorders().getByBorderType(BorderType.BOTTOM).getLineStyle());
            msAssert.areEqual(LineStyle.SINGLE, cell.getCellFormat().getBorders().getByBorderType(BorderType.RIGHT).getLineStyle());
            msAssert.areEqual(LineStyle.NONE, cell.getCellFormat().getBorders().getByBorderType(BorderType.DIAGONAL_DOWN).getLineStyle());
            msAssert.areEqual(LineStyle.NONE, cell.getCellFormat().getBorders().getByBorderType(BorderType.DIAGONAL_UP).getLineStyle());
        }

        // Borders are explicitly off for this cell
        cell = (Cell)row.getChildNodes(NodeType.ANY, false).get(2);
        msAssert.areEqual(LineStyle.NONE, cell.getCellFormat().getBorders().getByBorderType(BorderType.TOP).getLineStyle());
        msAssert.areEqual(LineStyle.NONE, cell.getCellFormat().getBorders().getByBorderType(BorderType.LEFT).getLineStyle());
        msAssert.areEqual(LineStyle.NONE, cell.getCellFormat().getBorders().getByBorderType(BorderType.BOTTOM).getLineStyle());
        msAssert.areEqual(LineStyle.NONE, cell.getCellFormat().getBorders().getByBorderType(BorderType.RIGHT).getLineStyle());

        // Borders are explicitly set
        row = (Row)doc.selectSingleNode("//Row[2]");
        cell = (Cell)row.getChildNodes(NodeType.ANY, false).get(1);
        msAssert.areEqual(0, cell.getCellFormat().getBorders().getByBorderType(BorderType.TOP).getColor().getRGB());    //Default
        msAssert.areEqual(Color.RED.getRGB(), cell.getCellFormat().getBorders().getByBorderType(BorderType.LEFT).getColor().getRGB());
        msAssert.areEqual(0, cell.getCellFormat().getBorders().getByBorderType(BorderType.BOTTOM).getColor().getRGB());//Default
        msAssert.areEqual(Color.BLUE.getRGB(), cell.getCellFormat().getBorders().getByBorderType(BorderType.RIGHT).getColor().getRGB());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInheritCellBordersSourceDataProvider")
	public void unifiedTestInheritCellBordersSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInheritCellBorders(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInheritCellBordersSourceDataProvider")
	Object[][] unifiedTestInheritCellBordersSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Test that borders for different cell sides are correctly resolved not only
    /// to left, top, right and bottom, but also for horizontal and vertical (intra cell) borders.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestInheritCellBordersInside(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestInheritCellBordersInside", lf, sf);

        //Check default table borders
        Row row = (Row)doc.selectSingleNode("//Row[1]");
        msAssert.areEqual(3d, row.getRowFormat().getBorders().getByBorderType(BorderType.TOP).getLineWidth());
        msAssert.areEqual(3d, row.getRowFormat().getBorders().getByBorderType(BorderType.LEFT).getLineWidth());
        msAssert.areEqual(3d, row.getRowFormat().getBorders().getByBorderType(BorderType.BOTTOM).getLineWidth());
        msAssert.areEqual(3d, row.getRowFormat().getBorders().getByBorderType(BorderType.RIGHT).getLineWidth());
        msAssert.areEqual(0.75, row.getRowFormat().getBorders().getByBorderType(BorderType.HORIZONTAL).getLineWidth());
        msAssert.areEqual(0.75, row.getRowFormat().getBorders().getByBorderType(BorderType.VERTICAL).getLineWidth());

        //Check top left cell
        Cell cell = (Cell)row.getChildNodes(NodeType.ANY, false).get(0);
        msAssert.areEqual(3d, cell.getCellFormat().getBorders().getByBorderType(BorderType.TOP).getLineWidth());
        msAssert.areEqual(3d, cell.getCellFormat().getBorders().getByBorderType(BorderType.LEFT).getLineWidth());
        msAssert.areEqual(0.75, cell.getCellFormat().getBorders().getByBorderType(BorderType.BOTTOM).getLineWidth());
        msAssert.areEqual(0.75, cell.getCellFormat().getBorders().getByBorderType(BorderType.RIGHT).getLineWidth());

        //Check bottom right cell
        row = (Row)doc.selectSingleNode("//Row[3]");
        cell = (Cell)row.getChildNodes(NodeType.ANY, false).get(2);
        msAssert.areEqual(0.75, cell.getCellFormat().getBorders().getByBorderType(BorderType.TOP).getLineWidth());
        msAssert.areEqual(0.75, cell.getCellFormat().getBorders().getByBorderType(BorderType.LEFT).getLineWidth());
        msAssert.areEqual(3d, cell.getCellFormat().getBorders().getByBorderType(BorderType.BOTTOM).getLineWidth());
        msAssert.areEqual(3d, cell.getCellFormat().getBorders().getByBorderType(BorderType.RIGHT).getLineWidth());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInheritCellBordersInsideSourceDataProvider")
	public void unifiedTestInheritCellBordersInsideSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInheritCellBordersInside(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInheritCellBordersInsideSourceDataProvider")
	Object[][] unifiedTestInheritCellBordersInsideSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Had a problem with writing textbox text anchored to a cell of a table.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestShapeAtRow(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestShapeAtRow", lf, sf);

        msAssert.areEqual("Textbox\r" +                        //end of para
                "Cell in Textbox\r" +                //end of para
                "Nested Cell in Textbox\u0007\u0007" +    //end of nested cell and end of nested row
                "\u0007\u0007" +                    //end of the outer cell and row
                "\r" +                                //last char in the textbox
                "Cell\r" +                            //end if para
                "Nested Cell\u0007\u0007" +            //end of nested cell and roe
                "\u0007\u0007" +                    //end of outer cell and row
                "\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestShapeAtRowSourceDataProvider")
	public void unifiedTestShapeAtRowSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestShapeAtRow(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestShapeAtRowSourceDataProvider")
	Object[][] unifiedTestShapeAtRowSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestManyColumns(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestManyColumns", lf, sf);

        // alexnosk: Originally XPath "//Row[1]/Cell" was used to select cells, chnged code for C++ porting.
        NodeCollection cells = ((Row)doc.getChild(NodeType.ROW, 0, true)).getCells();

        msAssert.areEqual(53, cells.getCount());
        msAssert.areEqual(DrColor.fromArgb(0xff, 0, 0), ((Cell)cells.get(0)).getCellPr().getShading().getBackgroundPatternColorInternal());
        msAssert.areEqual(DrColor.fromArgb(0, 0xff, 0), ((Cell)cells.get(25)).getCellPr().getShading().getBackgroundPatternColorInternal());
        msAssert.areEqual(DrColor.fromArgb(0, 0, 0xff), ((Cell)cells.get(50)).getCellPr().getShading().getBackgroundPatternColorInternal());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestManyColumnsSourceDataProvider")
	public void unifiedTestManyColumnsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestManyColumns(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestManyColumnsSourceDataProvider")
	Object[][] unifiedTestManyColumnsSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}



    /// <summary>
    /// WORDSNET-837 RowFormat.AllowBreakAcrossPages is not saved when document is saved by Aspose.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect837(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestDefect837", lf, sf);

        Row row = (Row)doc.getChild(NodeType.ROW, 0, true);
        row.getRowFormat().setAllowBreakAcrossPages(false);

        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestDefect837 Modified", lf, sf);

        row = (Row)doc.getChild(NodeType.ROW, 0, true);
        msAssert.areEqual(false, row.getRowFormat().getAllowBreakAcrossPages());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect837SourceDataProvider")
	public void unifiedTestDefect837Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect837(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect837SourceDataProvider")
	Object[][] unifiedTestDefect837SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCloneRowsInvalidatesEnumerator(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestCloneRowsInvalidatesEnumerator", lf, sf);

        Table srcTable = doc.getSections().get(0).getBody().getTables().get(0);
        Table dstTable = doc.getSections().get(0).getBody().getTables().get(1);

        for (Row srcRow : (Iterable<Row>) srcTable.getRows())
            dstTable.getRows().add(srcRow.deepClone(true));

        msAssert.areEqual("Row2\u0007\u0007Row1\u0007\u0007", dstTable.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCloneRowsInvalidatesEnumeratorSourceDataProvider")
	public void unifiedTestCloneRowsInvalidatesEnumeratorSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCloneRowsInvalidatesEnumerator(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCloneRowsInvalidatesEnumeratorSourceDataProvider")
	Object[][] unifiedTestCloneRowsInvalidatesEnumeratorSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestEmptyCell(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestEmptyCell", lf, sf);
        Cell cell = (Cell)doc.getChild(NodeType.CELL, 0, true);
        Paragraph para = cell.getFirstParagraph();
        msAssert.areEqual(true, para.getParagraphBreakFont().getBold());
        msAssert.areEqual(20d, para.getParagraphBreakFont().getSize());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEmptyCellSourceDataProvider")
	public void unifiedTestEmptyCellSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEmptyCell(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEmptyCellSourceDataProvider")
	Object[][] unifiedTestEmptyCellSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}



    /// <summary>
    /// Verifies padding in table rows. Relates to Defect833.
    /// </summary>
    private static void verifyTestDefect833(Document doc, /*LoadFormat*/int lf, /*SaveFormat*/int sf)
    {
        // first row properties
        Row row1 = (Row)doc.getNodeById("0.1.0.0");
        Object left1 = row1.getTablePr().get(TableAttr.LEFT_PADDING);
        Object right1 = row1.getTablePr().get(TableAttr.RIGHT_PADDING);

        // second row properties
        Row row2 = (Row)doc.getNodeById("1.1.0.0");
        Object left2 = row2.getTablePr().get(TableAttr.LEFT_PADDING);
        Object right2 = row2.getTablePr().get(TableAttr.RIGHT_PADDING);

        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.WML_2_WML_NO_GOLD:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOC_2_WML:
                msAssert.areEqual(null, left1);
                msAssert.areEqual(0, right1);
                msAssert.areEqual(null, left2);
                msAssert.areEqual(108, right2);
                break;

            case UnifiedScenario.DOC_2_RTF:
                msAssert.areEqual(null, left1);
                msAssert.areEqual(0, right1);
                msAssert.areEqual(null, left2);
                // 'right2' has direct value '108' in the testing DOC.
                // When this document is exported to RTF and then opened again this value
                // will be equal to 'null' due to collapsed over the style. So, it has different values
                // for different formats and therefore, we are not checking it here.
                break;

            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                // WORDSNET-12347 Direct attributes are collapsed over table style.
                msAssert.areEqual(null, left1);
                msAssert.areEqual(null, right1);
                msAssert.areEqual(null, left2);
                msAssert.areEqual(null, right2);
                break;

            default:
                throw new IllegalStateException("Unknown unified scenario.");
        }
    }


    /// <summary>
    /// Code example how to replace text in a cell with new text, yet preserve formatting.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestReplaceCell(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestReplaceCell", lf, sf);
        DocumentBuilder b = new DocumentBuilder(doc);

        // Use the document builder to move to the cell and insert text.
        // When using document builder, the newly inserted text will take on
        // formatting at the current position in the document.
        b.moveToCell(0, 0, 0, 0);
        b.writeln("New text");

        //If the last call was Writeln, then the cursor is at the beginning
        //of the first paragraph with the original text.
        //The original text in the cell can be deleted in a loop like this.
        Node node = b.getCurrentParagraph();
        while (node != null)
        {
            Node nextNode = node.getNextSibling();
            node.remove();
            node = nextNode;
        }

        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestReplaceCell Modified", lf, sf);

        Cell cell = (Cell)doc.getChild(NodeType.CELL, 0, true);
        msAssert.areEqual("New text\u0007", cell.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestReplaceCellSourceDataProvider")
	public void unifiedTestReplaceCellSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestReplaceCell(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestReplaceCellSourceDataProvider")
	Object[][] unifiedTestReplaceCellSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestReplaceCell1(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestReplaceCell", lf, sf);

        Cell cell = doc.getSections().get(0).getBody().getTables().get(0).getRows().get(0).getCells().get(0);
        //"Insert after null" actually inserts before the first child.
        cell.getFirstParagraph().insertAfter(new BookmarkStart(doc, "bmk"), null);
        cell.getLastParagraph().appendChild(new BookmarkEnd(doc, "bmk"));

        Bookmark bmk = cell.getRange().getBookmarks().get("bmk");
        bmk.setText("New text");

        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestReplaceCell1 Modified", lf, sf);

        cell = (Cell)doc.getChild(NodeType.CELL, 0, true);
        msAssert.areEqual("New text\u0007", cell.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestReplaceCell1SourceDataProvider")
	public void unifiedTestReplaceCell1Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestReplaceCell1(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestReplaceCell1SourceDataProvider")
	Object[][] unifiedTestReplaceCell1SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// WORDSNET-854 Error while saving document in AsposePdf xml.
    /// Forum thread:
    ///   'save to stream as FormatAsposePdf throws exception'
    ///   https://www.aspose.com/Community/Forums/43433/ShowPost.aspx
    /// </summary>
    @Test
    public void testSaveEmptyTable() throws Exception
    {
        Document doc = new Document();
        doc.getFirstSection().getBody().appendChild(new Table(doc));

        doc.save(new MemoryStream(), SaveFormat.MARKDOWN);
    }

    /// <summary>
    /// WORDSNET-897 Imported table is treated by MS Word as 'corrupted' in the resulting document.
    /// The problem was caused by table being at the very end of a document. Document needs a paragraph at the end.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTableAtDocumentEnd(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestTableAtDocumentEnd", lf, sf);
        msAssert.areEqual("Test\u0007\u0007\f", doc.getText());

        Body body = doc.getFirstSection().getBody();

        // There is a table and a paragraph in the document.
        // Move the table to be after the paragraph and attempt to save.
        Node table = body.getFirstChild();
        table.remove();
        body.appendChild(table);

        // When saving in DOC format, a new empty paragraph is automatically appended at the end
        // because a table is not allowed to be the last element in a document.
        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestTableAtDocumentEnd Modified", lf, sf);

        msAssert.areEqual("\rTest\u0007\u0007\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTableAtDocumentEndSourceDataProvider")
	public void unifiedTestTableAtDocumentEndSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTableAtDocumentEnd(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTableAtDocumentEndSourceDataProvider")
	Object[][] unifiedTestTableAtDocumentEndSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Test getting/setting CellFormat.FitText property.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCellFitText(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCell();
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(72.0));
        builder.getCellFormat().setFitText(true);
        builder.write("This is a very very very very very long text.");

        builder.insertCell();
        builder.getCellFormat().setFitText(false);
        builder.write("This is a very very very very very long text.");

        Table table = builder.endTable();
        table.setPreferredWidth(PreferredWidth.AUTO);
        table.setAllowAutoFit(false);


        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestCellFitText", lf, sf);

        Row row = doc.getFirstSection().getBody().getTables().get(0).getRows().get(0);
        msAssert.isTrue(row.getCells().get(0).getCellFormat().getFitText());
        msAssert.isFalse(row.getCells().get(1).getCellFormat().getFitText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCellFitTextSourceDataProvider")
	public void unifiedTestCellFitTextSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCellFitText(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCellFitTextSourceDataProvider")
	Object[][] unifiedTestCellFitTextSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Test getting/setting CellFormat.WrapText property.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCellWrapText(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();

        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCell();
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(72.0));
        builder.getCellFormat().setWrapText(true);
        builder.write("This is a very very very very very long text.");

        builder.insertCell();
        builder.getCellFormat().setWrapText(false);
        builder.write("This is a very very very very very long text.");

        Table table = builder.endTable();
        table.setPreferredWidth(PreferredWidth.AUTO);
        table.setAllowAutoFit(false);

        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestCellWrapText", lf, sf);

        Row row = doc.getFirstSection().getBody().getTables().get(0).getRows().get(0);
        msAssert.isTrue(row.getCells().get(0).getCellFormat().getWrapText());
        msAssert.isFalse(row.getCells().get(1).getCellFormat().getWrapText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCellWrapTextSourceDataProvider")
	public void unifiedTestCellWrapTextSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCellWrapText(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCellWrapTextSourceDataProvider")
	Object[][] unifiedTestCellWrapTextSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    /// <summary>
    /// WORDSNET-1133 Setting Borders.LineWidth to value greater than 0 without explicit
    /// setting of Borders.LineStyle to value other than LineStyle.None crashes Word 2000 and 2002.
    /// </summary>
    @Test
    public void testDefect1133() throws Exception
    {
        DocumentBuilder b = new DocumentBuilder();
        b.insertCell();
        b.getRowFormat().getBorders().setLineWidth(0.0);
        b.getRowFormat().getBorders().setLineStyle(LineStyle.NONE);

        b.getRowFormat().getBorders().setLineWidth(1.0);
        // Line style is automatically changed to single when non zero border width is set.
        msAssert.areEqual(LineStyle.SINGLE, b.getRowFormat().getBorders().getLineStyle());

        //The other way around too. Setting line style none automatically sets zero line width.
        b.getRowFormat().getBorders().setLineStyle(LineStyle.NONE);
        msAssert.areEqual(0d, b.getRowFormat().getBorders().getLineWidth());
    }

    /// <summary>
    /// WORDSNET-1277 Inserting a HTML table using InsertHtml makes tables inserted after corrupted.
    /// </summary>
    @Test
    public void testDefect1277()
    {
        CellPr cellPr = new CellPr();
        CellFormat cellFormat = new CellFormat(cellPr);
        cellFormat.setLeftPadding(100.0);
        cellFormat.setWidth(110.0);
        cellFormat.setPreferredWidth(PreferredWidth.fromPoints(120.0));

        // It was that CellFormat.ClearFormatting reset cell width to zero and that value stayed in the builder.
        cellFormat.clearFormatting();

        // Most of the cell format values do indeed get reset.
        msAssert.areEqual(0.0, cellFormat.getLeftPadding());

        // But width of the cell does not get reset.
        msAssert.areEqual(110.0, cellFormat.getWidth());
        msAssert.isTrue((PreferredWidth.fromPoints(120.0).equals(cellFormat.getPreferredWidth())));
    }

    /// <summary>
    /// WORDSNET-1348 Setting borders properties for cell that is not appended to the row yet results in NullReferenceException.
    /// </summary>
    @Test
    public void testDefect1348() throws Exception
    {
        Document doc = new Document();

        Cell cell = new Cell(doc);
        cell.getCellFormat().getBorders().getLeft().setLineStyle(LineStyle.SINGLE);
        msAssert.areEqual(LineStyle.SINGLE, cell.getCellFormat().getBorders().getLeft().getLineStyle());

        Row row = new Row(doc);
        row.getRowFormat().getBorders().getLeft().setLineStyle(LineStyle.DOUBLE);
        msAssert.areEqual(LineStyle.DOUBLE, row.getRowFormat().getBorders().getLeft().getLineStyle());

        // This was not in the original bug, but lets try it anyway.
        Run run = new Run(doc);
        run.getFont().getBorder().setLineStyle(LineStyle.DOT);
        msAssert.areEqual(LineStyle.DOT, run.getFont().getBorder().getLineStyle());


        Paragraph para = new Paragraph(doc);
        para.getParagraphFormat().getBorders().getLeft().setLineStyle(LineStyle.DOT_DASH);
        msAssert.areEqual(LineStyle.DOT_DASH, para.getParagraphFormat().getBorders().getLeft().getLineStyle());
    }

    /// <summary>
    /// WORDSNET-1420 Sequential floating tables are put into one table in the model.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFloatingTables(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestFloatingTables", lf, sf);

        msAssert.areEqual(3, doc.getChildNodes(NodeType.TABLE, true).getCount());
        checkTable1420(doc, 0, "January 2006\u0007\u0007", 7);
        checkTable1420(doc, 1, "February 2006\u0007\u0007", 7);
        checkTable1420(doc, 2, "March 2006\u0007\u0007", 7);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFloatingTablesSourceDataProvider")
	public void unifiedTestFloatingTablesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFloatingTables(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFloatingTablesSourceDataProvider")
	Object[][] unifiedTestFloatingTablesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void checkTable1420(Document doc, int tableIndex, String firstRowText, int rowCount)
    {
        Table table = (Table)doc.getChild(NodeType.TABLE, tableIndex, true);
        msAssert.areEqual(firstRowText, table.getFirstRow().getText());
        msAssert.areEqual(rowCount, table.getRows().getCount());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDirectionInCell(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestDirectionInCell", lf, sf);
        checkCellDirection(doc, 0, TextOrientation.HORIZONTAL);
        checkCellDirection(doc, 1, TextOrientation.UPWARD);
        checkCellDirection(doc, 2, TextOrientation.VERTICAL_FAR_EAST);
        checkCellDirection(doc, 3, TextOrientation.DOWNWARD);
        checkCellDirection(doc, 4, TextOrientation.HORIZONTAL_ROTATED_FAR_EAST);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDirectionInCellSourceDataProvider")
	public void unifiedTestDirectionInCellSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDirectionInCell(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDirectionInCellSourceDataProvider")
	Object[][] unifiedTestDirectionInCellSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Checks text orientation for the cell with the specified index within the document.
    /// </summary>
    private static void checkCellDirection(Document doc, int cellIndex, /*TextOrientation*/int expectedOrientation)
    {
        Cell cell = (Cell)doc.getChild(NodeType.CELL, cellIndex, true);
        msAssert.areEqual(expectedOrientation, cell.getCellFormat().getOrientation());
    }

    /// <summary>
    /// WORDSNET-3381 Error with table containing in a cell a Paragraph and a Sub-table
    /// https://www.aspose.com/Community/Forums/thread/84251.aspx
    /// Nested table corrupts DOC document.
    /// Open the resulting document in Word 2003.
    /// Fixed. A cell should not end with a table, it should end with a paragraph.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect3381(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();

        doc.getFirstSection().getBody().removeAllChildren();

        Table table = new Table(doc);
        Row row = new Row(doc);
        table.getRows().add(row);
        table.setPreferredWidth(PreferredWidth.AUTO);
        table.setAllowAutoFit(false);

        Cell cell = new Cell(doc);
        cell.getCellFormat().setWidth(150.0);
        row.getCells().add(cell);

        Table subTable = new Table(doc);
        Row subRow = new Row(doc);
        subTable.getRows().add(subRow);
        table.setPreferredWidth(PreferredWidth.AUTO);
        table.setAllowAutoFit(false);

        Cell subCell0 = new Cell(doc);
        subCell0.getCellFormat().setWidth(30.0);
        subRow.getCells().add(subCell0);

        cell.appendChild(new Paragraph(doc));
        cell.appendChild(subTable);

        doc.getFirstSection().getBody().appendChild(table);

        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestDefect3381", lf, sf);

        // First empty paragraph, cell end, row end, cell end, row end, section end.
        msAssert.areEqual("\r\u0007\u0007\u0007\u0007\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect3381SourceDataProvider")
	public void unifiedTestDefect3381Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect3381(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect3381SourceDataProvider")
	Object[][] unifiedTestDefect3381SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// WORDSNET-4237 Table alignment does not work in created DOC files.
    /// The problem was related to writing FIB. I made writing FIB in Word 2000 format for now.
    /// There is no actual test here, just open the generated files in Word and see.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect4237(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        DocumentBuilder docBuilder = new DocumentBuilder();

        docBuilder.write("pre");

        docBuilder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(72.0));
        docBuilder.insertCell();
        docBuilder.write("A1");
        docBuilder.insertCell();
        docBuilder.write("B1");
        docBuilder.endRow();
        docBuilder.insertCell();
        docBuilder.write("A2");
        docBuilder.insertCell();
        docBuilder.write("B2");
        docBuilder.endRow();

        Table table = docBuilder.endTable();

        table.setPreferredWidth(PreferredWidth.AUTO);
        table.setAllowAutoFit(false);
        table.setAlignment(TableAlignment.CENTER);

        docBuilder.write("post");

        TestUtil.saveOpen(docBuilder.getDocument(), "Model\\Table\\TestDefect4237", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect4237SourceDataProvider")
	public void unifiedTestDefect4237Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect4237(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect4237SourceDataProvider")
	Object[][] unifiedTestDefect4237SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAll(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestAll", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAllSourceDataProvider")
	public void unifiedTestAllSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAll(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAllSourceDataProvider")
	Object[][] unifiedTestAllSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBorders(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestBorders", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBordersSourceDataProvider")
	public void unifiedTestBordersSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBorders(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBordersSourceDataProvider")
	Object[][] unifiedTestBordersSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCellAlignment(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestCellAlignment", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCellAlignmentSourceDataProvider")
	public void unifiedTestCellAlignmentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCellAlignment(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCellAlignmentSourceDataProvider")
	Object[][] unifiedTestCellAlignmentSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCellBorders(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestCellBorders", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCellBordersSourceDataProvider")
	public void unifiedTestCellBordersSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCellBorders(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCellBordersSourceDataProvider")
	Object[][] unifiedTestCellBordersSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCellProperties(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestCellProperties", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCellPropertiesSourceDataProvider")
	public void unifiedTestCellPropertiesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCellProperties(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCellPropertiesSourceDataProvider")
	Object[][] unifiedTestCellPropertiesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFloating(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestFloating", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFloatingSourceDataProvider")
	public void unifiedTestFloatingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFloating(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFloatingSourceDataProvider")
	Object[][] unifiedTestFloatingSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFormattingRevision(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        final String TEST_NAME = "Model\\Table\\TestFormattingRevision";
        Document doc;
        /*UnifiedScenario*/int us = TestUtil.getUnifiedScenario(lf, sf);

        // WORDSNET-5855 andrnosk: Do not perform comparing with gold because in some cases
        // we have difference between processing Width and PreferredWidth upon reading Doc and Docx/Wml.
        if ((us == UnifiedScenario.DOC_2_DOCX) || (us == UnifiedScenario.DOC_2_WML))
        {
            // Open/Save/Open without comparing with gold
            doc = TestUtil.open(TEST_NAME, lf);
            String outFileName = TestUtil.save(doc, TEST_NAME, SaveOptions.createSaveOptions(sf), false);
            doc = TestUtil.open(outFileName);
        }
        else
        {
            doc = TestUtil.openSaveOpen(TEST_NAME, lf, sf);
        }

        // Check that original indent is not set i.e null.
        Row row = (Row)doc.getNodeById("0.0.0.0");
        TablePr tablePr = row.getTablePr();
        CellPr cellPr = row.getFirstCell().getCellPr();


        switch (us)
        {
            case UnifiedScenario.DOC_2_DOC:
                // No explicit indent, formatting revision is ok.
                msAssert.areEqual(null, tablePr.get(TableAttr.LEFT_INDENT));
                msAssert.areEqual(200, tablePr.getFormatRevision().getRevPr().get(TableAttr.LEFT_INDENT));
                break;

            case UnifiedScenario.DOC_2_RTF:
                // WORDSNET-12347 Table styles are supported in RTF. Direct attributes should be collapsed with style.
                // Indent is resolved, formatting revision is lost.
                msAssert.areEqual(0, tablePr.get(TableAttr.LEFT_INDENT));
                msAssert.areEqual(null, tablePr.getFormatRevision());
                break;


            case UnifiedScenario.DOC_2_WML:
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
                // Indent is resolved, formatting revision is lost.
                msAssert.areEqual(200, tablePr.get(TableAttr.LEFT_INDENT));
                msAssert.areEqual(null, tablePr.getFormatRevision());
                msAssert.isNotNull(cellPr.getFormatRevision());
                msAssert.areEqual(3, cellPr.getCount());
                // AM. I tested this and there are actually two properties:
                // cell width and vertical alignment.
                CellPr revisedCellPr = (CellPr)cellPr.getFormatRevision().getRevPr();
                msAssert.areEqual(2, revisedCellPr.getCount());
                // Original width.
                msAssert.areEqual(9571, cellPr.get(CellAttr.WIDTH));
                // Final width.
                msAssert.areEqual(9371, revisedCellPr.get(CellAttr.WIDTH));
                break;

            case UnifiedScenario.WML_2_WML_NO_GOLD:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                msAssert.areEqual(200, tablePr.get(TableAttr.LEFT_INDENT));
                msAssert.areEqual(null, tablePr.getFormatRevision());
                break;

            default:
                throw new IllegalStateException("Unknown unified scenario.");

        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFormattingRevisionSourceDataProvider")
	public void unifiedTestFormattingRevisionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFormattingRevision(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFormattingRevisionSourceDataProvider")
	Object[][] unifiedTestFormattingRevisionSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    /// <summary>
    /// Inserting and deleting row logs insert or delete run revisions OK.
    ///
    /// Format a cell logged as a formatting revision in TablePr OK.
    ///
    /// Delete, split and merge cell operations bring up a dialog box that says this
    /// operation will not result in a tracked change.
    ///
    /// BUT Inserting a cell does not bring up a warning, but it DOES NOT FULLY WORK either.
    /// Sometimes it is possible to reject the change that inserted a cell, but sometimes
    /// it is not possible in MS Word. It is usually not possible when the cell is inserted
    /// before the first cell, but maybe there are other scenarios.
    ///
    /// When a cell was inserted, it seems to log TablePr with the new number of CellPr both
    /// in the original and in the revised TablePr. I guess it does not create any problems for me.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestInsertCellRevision(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestInsertCellRevision", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInsertCellRevisionSourceDataProvider")
	public void unifiedTestInsertCellRevisionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInsertCellRevision(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInsertCellRevisionSourceDataProvider")
	Object[][] unifiedTestInsertCellRevisionSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestNested(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestNested", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestNestedSourceDataProvider")
	public void unifiedTestNestedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestNested(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestNestedSourceDataProvider")
	Object[][] unifiedTestNestedSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestNestedEmptyPara(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestNestedEmptyPara", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestNestedEmptyParaSourceDataProvider")
	public void unifiedTestNestedEmptyParaSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestNestedEmptyPara(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestNestedEmptyParaSourceDataProvider")
	Object[][] unifiedTestNestedEmptyParaSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRightMargin(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestRightMargin", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRightMarginSourceDataProvider")
	public void unifiedTestRightMarginSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRightMargin(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRightMarginSourceDataProvider")
	Object[][] unifiedTestRightMarginSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRowHeight(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestRowHeight", lf, sf);

        Row row = (Row)doc.getChild(NodeType.ROW, 0, true);
        msAssert.areEqual(720, row.getTablePr().getHeight());
        msAssert.areEqual(HeightRule.AT_LEAST, row.getTablePr().getHeightRule());

        row = (Row)doc.getChild(NodeType.ROW, 1, true);
        msAssert.areEqual(0, row.getTablePr().getHeight());
        msAssert.areEqual(HeightRule.AUTO, row.getTablePr().getHeightRule());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRowHeightSourceDataProvider")
	public void unifiedTestRowHeightSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRowHeight(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRowHeightSourceDataProvider")
	Object[][] unifiedTestRowHeightSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRowProperties(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestRowProperties", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRowPropertiesSourceDataProvider")
	public void unifiedTestRowPropertiesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRowProperties(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRowPropertiesSourceDataProvider")
	Object[][] unifiedTestRowPropertiesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRtl(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestRtl", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRtlSourceDataProvider")
	public void unifiedTestRtlSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRtl(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRtlSourceDataProvider")
	Object[][] unifiedTestRtlSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestShading(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestShading", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestShadingSourceDataProvider")
	public void unifiedTestShadingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestShading(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestShadingSourceDataProvider")
	Object[][] unifiedTestShadingSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestShading1(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestShading1", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestShading1SourceDataProvider")
	public void unifiedTestShading1Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestShading1(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestShading1SourceDataProvider")
	Object[][] unifiedTestShading1SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestStyle", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestStyleSourceDataProvider")
	public void unifiedTestStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestStyleSourceDataProvider")
	Object[][] unifiedTestStyleSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTableAlignment(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestTableAlignment", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTableAlignmentSourceDataProvider")
	public void unifiedTestTableAlignmentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTableAlignment(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTableAlignmentSourceDataProvider")
	Object[][] unifiedTestTableAlignmentSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTableBorders(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestTableBorders", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTableBordersSourceDataProvider")
	public void unifiedTestTableBordersSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTableBorders(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTableBordersSourceDataProvider")
	Object[][] unifiedTestTableBordersSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTableProperties(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestTableProperties", lf, sf);

        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        Row row = table.getFirstRow();
        msAssert.areEqual(true, table.getPreferredWidth().isAuto());
        msAssert.areEqual(TableAlignment.LEFT, table.getAlignment());
        msAssert.areEqual(false, table.getAllowAutoFit());
        msAssert.areEqual(0.0, table.getCellSpacing());
        msAssert.areEqual(20.0, table.getLeftIndent());
        msAssert.areEqual(4785, row.getCells().get(0).getCellPr().getWidth());
        msAssert.areEqual(4786, row.getCells().get(1).getCellPr().getWidth());

        table = (Table)doc.getChild(NodeType.TABLE, 1, true);
        row = table.getFirstRow();
        msAssert.areEqual(80.5, table.getPreferredWidth().getValue());
        msAssert.areEqual(TableAlignment.CENTER, table.getAlignment());
        msAssert.areEqual(true, table.getAllowAutoFit());
        msAssert.areEqual(1.0, table.getCellSpacing()); // It is half of the spacing.
        msAssert.areEqual(0.0, table.getLeftIndent());
        msAssert.areEqual(4154, row.getCells().get(0).getCellPr().getWidth());
        msAssert.areEqual(3671, row.getCells().get(1).getCellPr().getWidth());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTablePropertiesSourceDataProvider")
	public void unifiedTestTablePropertiesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTableProperties(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTablePropertiesSourceDataProvider")
	Object[][] unifiedTestTablePropertiesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTableStyles(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestTableStyles", lf, sf);

        // RTF format misses "NoList" Style, same as in MSW.
        int stylesCount = (sf == SaveFormat.RTF) ? 49 : 50;

        msAssert.areEqual(stylesCount, doc.getStyles().getCount());

        // Test some details in one table style.
        TableStyle tableStyle = (TableStyle)doc.getStyles().getByName("Table 3D effects 1", false);
        msAssert.areEqual("Table 3D effects 1", tableStyle.getName());
        msAssert.areEqual(DrColor.fromArgb(0xc0, 0xc0, 0xc0), tableStyle.getCellPr().getShading().getForegroundPatternColorInternal());
        ConditionalStyle conditionalStyle = tableStyle.getConditionalStyles().getFirstRow();
        msAssert.isTrue(conditionalStyle.getRunPr().getColor().equals(new DrColor(0xff, 0x80, 0x00, 0x80)));

        // Test another table style.
        tableStyle = (TableStyle)doc.getStyles().getByName("Table Colorful 2", false);
        msAssert.isTrue(tableStyle.getTablePr().getBorderBottom().equals(new Border(LineStyle.SINGLE, 12,
                                                                  new DrColor(0xff, 0x00, 0x00, 0x00))));
        // There is difference between doc and docx reading. DOC reads Border.Empty, DOCX reads null.
        switch (lf)
        {
            case LoadFormat.DOCX:
            case LoadFormat.WORD_ML:
            case LoadFormat.RTF:
                msAssert.isTrue(tableStyle.getTablePr().getBorderTop() == null);
                break;

            default:
                msAssert.isTrue(tableStyle.getTablePr().getBorderTop().equals(Border.EMPTY));
                break;
        }

        // style has no conditional formatting for last row
        msAssert.isFalse(tableStyle.getConditionalStyles().containsTableStyleOverride(TableStyleOverrideType.LAST_ROW));
        conditionalStyle = tableStyle.getConditionalStyles().getFirstRow();
        RunPr firstRowPr = conditionalStyle.getRunPr();
        msAssert.areEqual(AttrBoolEx.TRUE, firstRowPr.getBold());
        msAssert.areEqual(AttrBoolEx.TRUE, firstRowPr.getItalic());

        // WML files has explicit shading written. Is it some kind of compatibility?
        // RTF files has explicit borders and shading written.
        if ((lf != LoadFormat.WORD_ML) && (sf != SaveFormat.RTF))
        {
            // Ensure none cell has explicit border or shading.
            NodeCollection cells = doc.getChildNodes(NodeType.CELL, true);
            for (Cell cell : (Iterable<Cell>) cells)
            {
                msAssert.isNull(cell.getCellPr().get(CellAttr.BORDER_LEFT));
                msAssert.isNull(cell.getCellPr().get(CellAttr.BORDER_RIGHT));
                msAssert.isNull(cell.getCellPr().get(CellAttr.BORDER_TOP));
                msAssert.isNull(cell.getCellPr().get(CellAttr.BORDER_BOTTOM));
                msAssert.isNull(cell.getCellPr().get(CellAttr.SHADING));
            }

            NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);
            for (Run run : (Iterable<Run>) runs)
            {
                msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getItalic());
                msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getBold());
                msAssert.areEqual(run.getRunPr().getColor(), DrColor.EMPTY);
            }

        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTableStylesSourceDataProvider")
	public void unifiedTestTableStylesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTableStyles(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTableStylesSourceDataProvider")
	Object[][] unifiedTestTableStylesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestZeroVisibleBorder(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestZeroVisibleBorder", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestZeroVisibleBorderSourceDataProvider")
	public void unifiedTestZeroVisibleBorderSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestZeroVisibleBorder(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestZeroVisibleBorderSourceDataProvider")
	Object[][] unifiedTestZeroVisibleBorderSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    /// <summary>
    /// WORDSNET-3623 Position of list item is incorrect after converting RTF to DOC/DOCX.
    /// </summary>
    /// <remarks>
    /// To avoid the condition where the tblpPr element is ignored, Word adds 1 twentieth of a point
    /// to the actual distance value when it saves a file and subtracts 1 twentieth of a point to the actual distance value when it opens a file.
    /// See "MS-DOC 2.9.354 YAS_plusOne, 2.9.348 XAS_plusOne".
    /// See "MS-OI29500 17.4.58. tblpPr (Floating Table Positioning)".
    /// </remarks>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira3623(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestJira3623", lf, sf);
        Row row = (Row)doc.getNodeById("0.1.0.0");
        msAssert.areEqual(0, row.getTablePr().getFrameTop());
        msAssert.areEqual(108, row.getTablePr().getFrameLeft());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira3623SourceDataProvider")
	public void unifiedTestJira3623Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira3623(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira3623SourceDataProvider")
	Object[][] unifiedTestJira3623SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// This test verifies that we automatically add one empty paragraph after a table
    /// if the table is the last node in a story (in any story).
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestEnsureStoryBreakAfterTable(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        doc.removeAllChildren();

        Section sect = new Section(doc);
        doc.appendChild(sect);

        // non empty header with table
        HeaderFooter headerWithTableAtEnd = new HeaderFooter(doc, HeaderFooterType.HEADER_PRIMARY);
        sect.appendChild(headerWithTableAtEnd);
        headerWithTableAtEnd.appendChild(create1x1Table(doc));

        // empty footer
        HeaderFooter emptyHeader = new HeaderFooter(doc, HeaderFooterType.FOOTER_PRIMARY);
        sect.appendChild(emptyHeader);

        // body
        Body body = new Body(doc);
        sect.appendChild(body);

        Paragraph paraInBody = new Paragraph(doc);
        body.appendChild(paraInBody);

        // empty textbox
        Shape emptyTextbox = new Shape(doc, ShapeType.TEXT_BOX);
        paraInBody.appendChild(emptyTextbox);
        emptyTextbox.setWidth(100.0);
        emptyTextbox.setHeight(100.0);
        emptyTextbox.setWrapType(WrapType.INLINE);

        // non empty textbox
        Shape textboxWithTableAtEnd = new Shape(doc, ShapeType.TEXT_BOX);
        paraInBody.appendChild(textboxWithTableAtEnd);
        textboxWithTableAtEnd.appendChild(create1x1Table(doc));
        textboxWithTableAtEnd.setWidth(100.0);
        textboxWithTableAtEnd.setHeight(100.0);
        textboxWithTableAtEnd.setWrapType(WrapType.INLINE);

        // empty comment
        Comment emptyComment = new Comment(doc);
        paraInBody.appendChild(emptyComment);

        // non empty comment
        Comment commentWithTableAtEnd = new Comment(doc);
        paraInBody.appendChild(commentWithTableAtEnd);
        commentWithTableAtEnd.appendChild(create1x1Table(doc));

        // empty footnote
        Footnote emptyFootnote = new Footnote(doc, FootnoteType.FOOTNOTE);
        paraInBody.appendChild(emptyFootnote);

        // non empty footnote
        Footnote footnoteWithTableAtEnd = new Footnote(doc, FootnoteType.FOOTNOTE);
        paraInBody.appendChild(footnoteWithTableAtEnd);
        footnoteWithTableAtEnd.appendChild(create1x1Table(doc));

        // talbe in body
        Table tableInBody = create1x1Table(doc);
        body.appendChild(tableInBody);

        // nested table
        Table tableInCell = create1x1Table(doc);
        tableInBody.getFirstRow().getFirstCell().appendChild(tableInCell);

        msAssert.areEqual("\u0007" +          // end of row, but no paragraph for empty cell
                "" +                // no paragraph break after a table

                "" +                // nothing for empty footer

                // body start here
                "" +                // nothing for empty textbox

                "\u0007" +          // end of row, but no paragraph for empty cell
                "" +                // no paragraph break after a table

                "" +                // nothing for empty comment

                "\u0007" +          // end of row, but no paragraph for empty cell
                "" +                // no paragraph break after a table

                "" +                // nothing for empty footnote

                "\u0007" +          // end of row, but no paragraph for empty cell
                "" +                // no paragraph break after a table

                "\r" +              // end of normal para

                "\u0007" +          // end of nested row

                "\u0007" +          // end of the outer row

                "", doc.getText());

        doc = TestUtil.saveOpen(doc, "Model\\Other\\TestEnsureStoreBreakAfterTable", lf, sf);

        msAssert.areEqual("\u0007\u0007" +    // table in the header
                "" +                // we do not auto generate an extra paragraph at end of header

                "" +                // nothing for the empty footer

                // body starts here
                "" +                // nothing for the empty textbox

                "\u0007\u0007" +    // table in the textbox
                "\r" +              // auto generated end of the textbox

                "\r" +              // auto generated for empty comment.

                "\u0007\u0007" +    // table in the comment (auto generated paragraph for empty cell and end of row mark)
                "\r" +              // auto generated end of the comment

                "\r" +              // auto generated for empty footnote

                "\u0007\u0007" +    // table in the comment (auto generated paragraph for empty cell and end of row mark)
                "\r" +              // auto generated end of the footnote

                "\r" +              // end of a normal para in the document.

                "\u0007\u0007" +    // table in cell (auto generated paragraph for empty cell and end of row mark)
                "\u0007\u0007" +    // table in body (auto generated paragraph at end of cell and end of row mark)

                "\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEnsureStoryBreakAfterTableSourceDataProvider")
	public void unifiedTestEnsureStoryBreakAfterTableSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEnsureStoryBreakAfterTable(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEnsureStoryBreakAfterTableSourceDataProvider")
	Object[][] unifiedTestEnsureStoryBreakAfterTableSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCellHideMark(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestHideMarkTable", lf, sf);

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        Cell cellWithHideMark = table.getFirstRow().getCells().get(1);
        ;
        Cell cellWithoutHideMark = table.getFirstRow().getCells().get(0);

        msAssert.isTrue(cellWithHideMark.getCellPr().getHideMark());
        msAssert.isFalse(cellWithoutHideMark.getCellPr().getHideMark());
        msAssert.areEqual(1980, cellWithHideMark.getCellPr().getWidth());
        msAssert.areEqual(1980, cellWithoutHideMark.getCellPr().getWidth());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCellHideMarkSourceDataProvider")
	public void unifiedTestCellHideMarkSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCellHideMark(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCellHideMarkSourceDataProvider")
	Object[][] unifiedTestCellHideMarkSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// WORDSNET-2523 Support Hidden attribute for row.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira2523(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        if (lf == LoadFormat.WORD_ML || sf == SaveFormat.WORD_ML)
            return;

        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestJira2523", lf, sf);

        // Verify both rows are hidden in model after export/import.
        for (Row row : (Iterable<Row>) doc.getChildNodes(NodeType.ROW, true))
            msAssert.areEqual(true, row.getTablePr().get(TableAttr.HIDDEN));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira2523SourceDataProvider")
	public void unifiedTestJira2523Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira2523(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira2523SourceDataProvider")
	Object[][] unifiedTestJira2523SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// WORDSNET-2523 Support Hidden attribute for row.
    /// AM. More complex file that I used to get logic how Word set TableAttr.Hidden when loads other than DOCX formats.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestHidden(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        if (lf == LoadFormat.WORD_ML || sf == SaveFormat.WORD_ML)
            return;

        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestHidden", lf, sf);

        // Verify that only two first rows are hidden but last one is not.
        for (Row row : (Iterable<Row>) doc.getChildNodes(NodeType.ROW, true))
        {
            Object hidden = row.getTablePr().get(TableAttr.HIDDEN);

            // Only first row is hidden except DOCX is source document.
            // See TestJira13772Hidden fro details.
            if ((row.getRowIndex() == 0) || ((row.getRowIndex() == 1) && (lf == LoadFormat.DOCX)))
                msAssert.isTrue((/*boolean*/Boolean)hidden);
            else
                msAssert.isNull(hidden);
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHiddenSourceDataProvider")
	public void unifiedTestHiddenSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHidden(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHiddenSourceDataProvider")
	Object[][] unifiedTestHiddenSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Setting the font hidden doesn't hide Row in DOCX.
    /// Fixed, now it does. Added updating TableAttr.Hidden into DocumentValidator before saving.
    /// </summary>
    @Test
    public void testJira6714() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.startTable();
        builder.insertCell();
        builder.write("Visible");
        builder.endRow();

        builder.getFont().setHidden(true);
        builder.insertCell();
        builder.write("Hidden");
        builder.endRow();
        builder.endTable();

        Document doc = TestUtil.saveOpen(builder.getDocument(), "Model\\Table\\TestJira6714.docx");

        Row row = (Row)doc.getChild(NodeType.ROW, 1, true);
        msAssert.isTrue(row.getTablePr().getHidden());
    }


    /// <summary>
    /// WORDSNET-7369 Table Row is expanding more than the Page width in Pdf.
    /// Problem happened because when AW clones TablePr, TableAttr.Sys_Cells was shallow cloned.
    /// TableAttr.Sys_Cells is cloned deeply now.
    /// </summary>
    @Test
    public void testJira7369()
    {
        TablePr sourceTablePr = new TablePr();
        CellPr cellPr = new CellPr();
        cellPr.setWidth(123);
        sourceTablePr.setAttr(TableAttr.SYS_CELLS, new CellPrCollection());
        sourceTablePr.getCellsPr().add(cellPr);

        TablePr cloneTablePr = AttrCollectionExtension.deepClone(sourceTablePr);
        sourceTablePr.getCellsPr().get(0).setWidth(0);
        msAssert.areEqual(123, cloneTablePr.getCellsPr().get(0).getWidth());
    }


    /// <summary>
    /// Tests that TextWrapping is get/set correctly.
    /// This is 'reverted' version of TestWrappingA to see how Word acts when TextWrapping is reverted back.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTextWrappingB(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\TestTextWrappingB", lf);

        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        table.setTextWrapping(TextWrapping.NONE);

        table = (Table)doc.getChild(NodeType.TABLE, 1, true);
        table.setTextWrapping(TextWrapping.AROUND);

        // Test modified document.
        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestTextWrappingB Modified", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);

        table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        msAssert.areEqual(TextWrapping.NONE, table.getTextWrapping());

        TablePr firstRowPr = table.getFirstRow().getTablePr();
        msAssert.isFalse(firstRowPr.contains(TableAttr.RELATIVE_VERTICAL_POSITION));
        msAssert.isFalse(firstRowPr.contains(TableAttr.FRAME_TOP));
        msAssert.isFalse(firstRowPr.contains(TableAttr.FRAME_LEFT));
        msAssert.isFalse(firstRowPr.contains(TableAttr.FRAME_DISTANCE_FROM_LEFT));
        msAssert.isFalse(firstRowPr.contains(TableAttr.FRAME_DISTANCE_FROM_RIGHT));
        msAssert.isFalse(firstRowPr.contains(TableAttr.ALLOW_OVERLAP));

        // FrameLeft does not go to LeftIndent.
        msAssert.areEqual(0, firstRowPr.fetchAttr(TableAttr.LEFT_INDENT));

        table = (Table)doc.getChild(NodeType.TABLE, 1, true);
        msAssert.areEqual(TextWrapping.AROUND, table.getTextWrapping());

        firstRowPr = table.getFirstRow().getTablePr();
        msAssert.areEqual(HorizontalAlignment.RIGHT, firstRowPr.getDirectAttr(TableAttr.HORIZONTAL_ALIGNMENT));
        msAssert.isFalse(firstRowPr.contains(TableAttr.ALIGNMENT));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTextWrappingBSourceDataProvider")
	public void unifiedTextWrappingBSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTextWrappingB(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTextWrappingBSourceDataProvider")
	Object[][] unifiedTextWrappingBSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Mimic MSW behavior: If there is a one-column table, where no height rule specified for v-merged cells,
    /// then we should add "AtLeast" height rule with a value, which determined according to a font size value.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestVMergeOneColumnTable(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Create one-column table, without any height rule attributes.
        Document doc = new Document();
        Table table = new Table(doc);
        doc.getFirstSection().getBody().appendChild(table);
        for (int i = 0; i < 3; i++)
        {
            Row row = new Row(doc);
            table.appendChild(row);
            Cell cell = new Cell(doc);
            row.appendChild(cell);
            Paragraph para = new Paragraph(doc);
            cell.appendChild(para);
            cell.getCellPr().setVerticalMerge((i == 0) ? CellMerge.FIRST : CellMerge.PREVIOUS);
            para.getParagraphBreakFont().setSize(12 + i);
        }

        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestVertMergeOneColumnTable", lf, sf);

        // Check, that "AtLeast" height rule with a proper value was added.
        RowCollection rows = ((Table)doc.getChild(NodeType.TABLE, 0, true)).getRows();
        int[] expectedHeights = new int[] { 273, 296, 319 };

        switch (sf)
        {
            case SaveFormat.DOCX:
            case SaveFormat.DOC:
            case SaveFormat.WORD_ML:
            case SaveFormat.RTF:
            {
                for (Row checkedRow : (Iterable<Row>) rows)
                {
                    msAssert.areEqual(checkedRow.getTablePr().getHeight(), expectedHeights[checkedRow.getRowIndex()]);
                    msAssert.areEqual(checkedRow.getTablePr().getHeightRule(), HeightRule.AT_LEAST);
                }
            }
            break;
            default:
                throw new IllegalStateException("Unknown file format");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestVMergeOneColumnTableSourceDataProvider")
	public void unifiedTestVMergeOneColumnTableSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestVMergeOneColumnTable(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestVMergeOneColumnTableSourceDataProvider")
	Object[][] unifiedTestVMergeOneColumnTableSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    /// <summary>
    /// WORDSNET-4838 Vertical alignment of table cell defined using table style, is lost during converting and rendering.
    /// AW always sets vertical alignment for cell to 'top' and this overrides an alignment, defined by table style.
    /// Removed setting default cell attributes. See TestTables.UnifiedTestJira6383 for details.
    /// </summary>
    @Test
    public void testJira4838() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\TestJira4838.docx");

        NodeCollection tables = doc.getChildNodes(NodeType.TABLE, true);

        Cell cell = ((Table)tables.get(0)).getCell(0, 0);
        // This cell has direct cell formatting attribute VerticalAlignment='center' and should be set in model.
        msAssert.areEqual(CellVerticalAlignment.CENTER, (/*CellVerticalAlignment*/Integer)cell.getCellPr().get(CellAttr.VERTICAL_ALIGNMENT));

        cell = ((Table)tables.get(1)).getCell(0, 0);
        // This cell has no direct cell formatting attribute VerticalAlignment and should not be set in model.
        msAssert.isNull(cell.getCellPr().get(CellAttr.VERTICAL_ALIGNMENT));

        cell = ((Table)tables.get(2)).getCell(0, 0);
        // This cell has direct cell formatting attribute VerticalAlignment='bottom' and should be set in model.
        msAssert.areEqual(CellVerticalAlignment.BOTTOM, (/*CellVerticalAlignment*/Integer)cell.getCellPr().get(CellAttr.VERTICAL_ALIGNMENT));
    }

    /// <summary>
    /// WORDSNET-6383 Default cell attributes is read into model.
    /// AW always treats vertical alignment 'top' for a cell as a default value and never writes it,
    /// even if it has overriding parent table style.
    /// See TestTables.UnifiedTestJira6383 for details.
    /// </summary>
    @Test
    public void testJira6383A() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestJira6383A.docx");
        Cell cell = (Cell)doc.getChildNodes(NodeType.CELL, true).get(3);
        /*CellVerticalAlignment*/int verticalAlignment = (/*CellVerticalAlignment*/Integer)cell.getCellPr().get(CellAttr.VERTICAL_ALIGNMENT);
        // Check the default cell value of vertical alignment 'top' was not lost on save.
        msAssert.areEqual(CellVerticalAlignment.TOP, verticalAlignment);
    }



    /// <summary>
    /// Relates to WORDSNET-9592
    /// Tests how borders specified in table style exposed in CellFormat.
    /// </summary>
    @Test
    public void testJira9592A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\TestJira9592A.docx");

        Table table = doc.getFirstSection().getBody().getTables().get(0);

        // 1st row.
        verifyCellFormatBorders(table.getCell(0, 0), LineStyle.DOUBLE, LineStyle.DOUBLE, LineStyle.DOUBLE, LineStyle.NONE);
        verifyCellFormatBorders(table.getCell(1, 0), LineStyle.DOUBLE, LineStyle.DOUBLE, LineStyle.NONE, LineStyle.NONE);
        verifyCellFormatBorders(table.getCell(2, 0), LineStyle.DOUBLE, LineStyle.DOUBLE, LineStyle.NONE, LineStyle.DOUBLE);

        // 2nd row.
        verifyCellFormatBorders(table.getCell(0, 1), LineStyle.DOT, LineStyle.DOT, LineStyle.DOT, LineStyle.NONE);
        verifyCellFormatBorders(table.getCell(1, 1), LineStyle.DOT, LineStyle.DOT, LineStyle.NONE, LineStyle.NONE);
        verifyCellFormatBorders(table.getCell(2, 1), LineStyle.DOT, LineStyle.DOT, LineStyle.NONE, LineStyle.DOT);

        // 3rd row.
        verifyCellFormatBorders(table.getCell(0, 2), LineStyle.SINGLE, LineStyle.SINGLE, LineStyle.SINGLE, LineStyle.NONE);
        verifyCellFormatBorders(table.getCell(1, 2), LineStyle.SINGLE, LineStyle.SINGLE, LineStyle.NONE, LineStyle.NONE);
        verifyCellFormatBorders(table.getCell(2, 2), LineStyle.SINGLE, LineStyle.SINGLE, LineStyle.NONE, LineStyle.SINGLE);

        // 4th row.
        verifyCellFormatBorders(table.getCell(0, 3), LineStyle.DOT, LineStyle.DOT, LineStyle.DOT, LineStyle.NONE);
        verifyCellFormatBorders(table.getCell(1, 3), LineStyle.DOT, LineStyle.DOT, LineStyle.NONE, LineStyle.NONE);
        verifyCellFormatBorders(table.getCell(2, 3), LineStyle.DOT, LineStyle.DOT, LineStyle.NONE, LineStyle.DOT);

        // 5th row.
        verifyCellFormatBorders(table.getCell(0, 4), LineStyle.TRIPLE, LineStyle.TRIPLE, LineStyle.TRIPLE, LineStyle.TRIPLE);
        verifyCellFormatBorders(table.getCell(1, 4), LineStyle.WAVE, LineStyle.WAVE, LineStyle.WAVE, LineStyle.WAVE);
        verifyCellFormatBorders(table.getCell(2, 4), LineStyle.WAVE, LineStyle.WAVE, LineStyle.WAVE, LineStyle.WAVE);
    }

    /// <summary>
    /// Relates to WORDSNET-9592
    /// Borders are defined in table style table formatting and should be inherited by cell.
    /// </summary>
    @Test
    public void testJira9592B() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\TestJira9592B.docx");

        for (Cell cell : (Iterable<Cell>) doc.getChildNodes(NodeType.CELL, true))
        {
            msAssert.areEqual(LineStyle.SINGLE, cell.getCellFormat().getBorders().getTop().getLineStyle());
            msAssert.areEqual(LineStyle.SINGLE, cell.getCellFormat().getBorders().getBottom().getLineStyle());
            msAssert.areEqual(LineStyle.SINGLE, cell.getCellFormat().getBorders().getLeft().getLineStyle());
            msAssert.areEqual(LineStyle.SINGLE, cell.getCellFormat().getBorders().getRight().getLineStyle());
        }
    }

    /// <summary>
    /// Relates to WORDSNET-9592
    /// Tests how shading defined in conditional formatting is exposed in CellFormat.
    /// </summary>
    @Test
    public void testJira9592C() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\TestJira9592C.docx");

        // Verify that there are no cells with shading applied directly.
        for (Cell cell : (Iterable<Cell>) doc.getChildNodes(NodeType.CELL, true))
            msAssert.isNull(cell.getCellPr().get(CellAttr.SHADING));

        DrColor c1 = DrColor.fromArgb(0xED, 0x7D, 0x31);
        DrColor c2 = DrColor.fromArgb(0xF7, 0xCA, 0xAC);
        DrColor c3 = DrColor.fromArgb(0xFB, 0xE4, 0xD5);

        Table table = doc.getFirstSection().getBody().getTables().get(0);

        // 1st row.
        msAssert.areEqual(c1, table.getCell(0, 0).getCellFormat().getShading().getBackgroundPatternColorInternal());
        msAssert.areEqual(c1, table.getCell(1, 0).getCellFormat().getShading().getBackgroundPatternColorInternal());

        // 2nd row.
        msAssert.areEqual(c1, table.getCell(0, 1).getCellFormat().getShading().getBackgroundPatternColorInternal());
        msAssert.areEqual(c2, table.getCell(1, 1).getCellFormat().getShading().getBackgroundPatternColorInternal());

        // 3rd row.
        msAssert.areEqual(c1, table.getCell(0, 2).getCellFormat().getShading().getBackgroundPatternColorInternal());
        msAssert.areEqual(c3, table.getCell(1, 2).getCellFormat().getShading().getBackgroundPatternColorInternal());
    }




    /// <summary>
    /// WORDSNET-11802 System.NullReferenceException during document saving to any format when table cell is merged
    /// with previous and has no child nodes. Fixed by improving <see cref="Table.SpecifyRowHeightRule"/>.
    /// </summary>
    @Test
    public void testJira11802() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        builder.startTable();

        Cell c1 = builder.insertCell();
        c1.getCellFormat().setVerticalMerge(CellMerge.FIRST);
        builder.endRow();

        Cell c2 = builder.insertCell();
        c2.getCellFormat().setVerticalMerge(CellMerge.PREVIOUS);
        builder.endRow();

        builder.endTable();

        c2.getChildNodes(NodeType.ANY, false).clear();

        // Check for no exception.
        builder.getDocument().save(new MemoryStream(), SaveFormat.DOCX);

        Table table = builder.getDocument().getSections().get(0).getBody().getTables().get(0);

        // Verify the assigned values.
        msAssert.areEqual(273, table.getFirstRow().getTablePr().getHeight());
        msAssert.areEqual(HeightRule.AT_LEAST, table.getFirstRow().getTablePr().getHeightRule());
    }


    /// <summary>
    /// WORDSNET-12114 LineStyle.None does not work for table's borders
    /// Made public Border.LineStyle create Nil border for LineStyle.None.
    /// </summary>
    /// <remarks>
    /// Word has no line style for Nil border and creates Nil border when we set wdLineStyleNone to border in VBA.
    /// Lets do the same while preserve actual line style during roundtrip.
    ///
    /// </remarks>
    @Test
    public void testJira12114() throws Exception
    {
        // Customer use case.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startTable();

        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        builder.getFont().setSize(9.0);
        builder.getFont().setBold(true);

        Cell cell = builder.insertCell();
        cell.getCellFormat().getBorders().getTop().setLineStyle(LineStyle.THICK);
        cell.getCellFormat().getBorders().getBottom().setLineStyle(LineStyle.NONE);

        cell.getCellFormat().getBorders().getLeft().setLineStyle(LineStyle.THICK);
        cell.getCellFormat().getBorders().getRight().setLineStyle(LineStyle.THICK);
        builder.write("a1");
        cell = builder.insertCell();
        cell.getCellFormat().getBorders().getLeft().setLineStyle(LineStyle.SINGLE);
        builder.write("b1");
        cell = builder.insertCell();
        cell.getCellFormat().getBorders().getRight().setLineStyle(LineStyle.THICK);
        builder.write("c1");
        builder.endRow();

        cell = builder.insertCell();
        cell.getCellFormat().getBorders().getTop().setLineStyle(LineStyle.NONE);
        cell.getCellFormat().getBorders().getBottom().setLineStyle(LineStyle.NONE);

        cell.getCellFormat().getBorders().getLeft().setLineStyle(LineStyle.THICK);
        cell.getCellFormat().getBorders().getRight().setLineStyle(LineStyle.THICK);
        builder.write("a2");
        cell = builder.insertCell();
        cell.getCellFormat().getBorders().getLeft().setLineStyle(LineStyle.SINGLE);
        builder.write("b2");
        cell = builder.insertCell();
        cell.getCellFormat().getBorders().getRight().setLineStyle(LineStyle.THICK);
        builder.write("c2");
        builder.endRow();

        cell = builder.insertCell();
        cell.getCellFormat().getBorders().getTop().setLineStyle(LineStyle.NONE);
        cell.getCellFormat().getBorders().getBottom().setLineStyle(LineStyle.SINGLE);

        cell.getCellFormat().getBorders().getLeft().setLineStyle(LineStyle.THICK);
        cell.getCellFormat().getBorders().getRight().setLineStyle(LineStyle.THICK);
        builder.write("a3");
        cell = builder.insertCell();
        cell.getCellFormat().getBorders().getLeft().setLineStyle(LineStyle.SINGLE);
        builder.write("b3");
        cell = builder.insertCell();
        cell.getCellFormat().getBorders().getRight().setLineStyle(LineStyle.THICK);
        builder.write("c3");
        builder.endRow();

        builder.endTable();
        // Compare gold to see that customer get desired output.
        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestJira12114.docx");

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        Border border = (Border)table.getFirstRow().getFirstCell().getCellPr().get(CellAttr.BORDER_BOTTOM);
        msAssert.isTrue(border.isNil());
    }

    /// <summary>
    /// Another case for TestJira12114.
    /// </summary>
    @Test
    public void testJira12114A() throws Exception
    {
        // Customer use case.
        Document doc = new Document();
        Table table = new Table(doc);

        for (int i = 0; i < 5; i++)
        {
            Row wordRow = new Row(doc);
            for (int j = 0; j < 5; j++)
            {
                Cell cell = new Cell(doc);

                //making sure LineStyle = None.
                cell.getCellFormat().getBorders().setLineStyle(LineStyle.NONE);

                Paragraph wordsParagraph = new Paragraph(doc);
                Run wordRun = new Run(doc);
                wordRun.setText(Convert.toString(j));
                wordsParagraph.appendChild(wordRun);
                cell.appendChild(wordsParagraph);
                cell.getCellFormat().getBorders().clearFormatting();
                wordRow.appendChild(cell);
            }

            table.getRows().add(wordRow);
        }

        doc.getFirstSection().getBody().getChildNodes(NodeType.ANY, false).add(table);

        // Compare gold to see that customer get desired output.
        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestJira12114A.docx");

        table = doc.getFirstSection().getBody().getTables().get(0);
        for (int key : new int[] { CellAttr.BORDER_TOP, CellAttr.BORDER_BOTTOM, CellAttr.BORDER_RIGHT, CellAttr.BORDER_LEFT })
        {
            Border border = (Border)table.getFirstRow().getFirstCell().getCellPr().get(key);
            msAssert.isTrue(border.isNil());
        }
    }




    private static void checkCellShading(Table table, int rowIndex, DrColor color)
    {
        msAssert.areEqual(color, table.getRows().get(rowIndex).getFirstCell().getCellPr().getShading().getBackgroundPatternColorInternal());
    }

    /// <summary>
    /// Checks that borders are exposed correctly in CellFormat.
    /// </summary>
    private static void verifyCellFormatBorders(Cell cell, /*LineStyle*/int topLineStyle, /*LineStyle*/int bottomLineStyle, /*LineStyle*/int leftLineStyle, /*LineStyle*/int rightLineStyle) throws Exception
    {
        msAssert.areEqual(topLineStyle, cell.getCellFormat().getBorders().getTop().getLineStyle());
        msAssert.areEqual(bottomLineStyle, cell.getCellFormat().getBorders().getBottom().getLineStyle());
        msAssert.areEqual(leftLineStyle, cell.getCellFormat().getBorders().getLeft().getLineStyle());
        msAssert.areEqual(rightLineStyle, cell.getCellFormat().getBorders().getRight().getLineStyle());
    }

    /// <summary>
    /// WORDSNET-12240 Model reports incorrect border width.
    /// We need to isolate adjustment of border width to DocumentBuilder only. Zero width border is valid border for MS Word.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira12240(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestJira12240", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);

        for (Table table : (Iterable<Table>) doc.getFirstSection().getBody().getTables())
        {
            Cell cell = table.getFirstRow().getFirstCell();
            double borderWidth = cell.getCellFormat().getBorders().getLeft().getLineWidth();
            msAssert.areEqual(0d, borderWidth, msString.format("Incorrect border width {0} for cell {1}.", borderWidth, cell));
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira12240SourceDataProvider")
	public void unifiedTestJira12240Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira12240(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira12240SourceDataProvider")
	Object[][] unifiedTestJira12240SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    /// <summary>
    /// WORDSNET-12235 Content cutting from left edge, Table disappears from top and unwanted rows appear in PDF
    /// </summary>
    @Test
    public void testJira12235A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\TestJira12235A.docx");
        msAssert.areEqual(2, doc.getFirstSection().getBody().getTables().getCount());

        // Verify that tables are not joined during validation.
        TestUtil.executeValidator(doc, SaveFormat.MARKDOWN);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getTables().getCount());
    }

    /// <summary>
    /// Relates to WORDSNET-12235
    /// Shows that we have to split tables for DOCX format the same way we did previously for DOC.
    /// </summary>
    @Test
    public void testJira12235B() throws Exception
    {
        final String TEST_NAME = "Model\\Table\\TestJira12235B";

        Document doc = TestUtil.open(TEST_NAME, LoadFormat.DOCX);
        msAssert.areEqual(4, doc.getFirstSection().getBody().getTables().getCount());

        doc = TestUtil.saveOpen(doc, TEST_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual(4, doc.getFirstSection().getBody().getTables().getCount());
    }

    /// <summary>
    /// Check that exception is thrown for DOCX format when cells count exceeds allowed limit.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testJira862B() throws Exception
    {
        makeDocWithCellLimit(SaveFormat.DOCX);
    }

    /// <summary>
    /// Allow table to have more than 63 cell per row for other formats.
    /// </summary>
    @Test
    public void testJira862D() throws Exception
    {
        makeDocWithCellLimit(SaveFormat.MARKDOWN);
    }


    /// <summary>
    /// Creates document with table which has more than 63 cells in a row.
    /// </summary>
    private static void makeDocWithCellLimit(/*SaveFormat*/int sf) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        builder.getCurrentSection().getPageSetup().setOrientation(Orientation.LANDSCAPE);
        builder.startTable();

        for (int i = 0; i < 64; i++)
        {
            builder.insertCell();
            builder.getFont().setSize(2.0);
            builder.write(msString.format("{0}", i));
        }

        builder.endTable();
        builder.getDocument().save(new MemoryStream(), sf);
    }



    /// <summary>
    /// WORDSNET-14411 Cell revisions is written incorrectly.
    /// Skip width validation for table with cell revisions.
    /// </summary>
    @Test
    public void testJira14411() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestJira14411", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        // Check original and final table grids are preserved.
        Table table = doc.getFirstSection().getBody().getTables().get(0);

        IntList tableGridOriginal = (IntList)table.getTablePr().get(TableAttr.SYS_TABLE_GRID);
        ArrayUtil.checkArraysEqual(tableGridOriginal.toArray(), new int[] { 4952, 1609, 3344 });

        IntList tableGridFinal = (IntList)table.getTablePr().getFormatRevision().getRevPr().get(TableAttr.SYS_TABLE_GRID);
        ArrayUtil.checkArraysEqual(tableGridFinal.toArray(), new int[] { 6561, 3344 });
    }

    /// <summary>
    /// WORDSNET-14501 Paragraph alignment in cells in a TFG data table is incorrect
    /// Alignment is defined in table style conditional formatting. Attribute fetcher is improved.
    /// </summary>
    @Test
    public void testJira14501() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\TestJira14501.docx");
        Paragraph p = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getFirstParagraph();
        msAssert.areEqual(ParagraphAlignment.LEFT, p.getParagraphFormat().getAlignment());
    }


    /// <summary>
    /// WORDSNET-5890 Tests reading/writing table title and description.
    /// </summary>
    @Test
    public void testReadingWritingTitle() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        TableBuilder tableBuilder = new TableBuilder(builder, false);

        Table table = tableBuilder.startTable();
        tableBuilder.startRow();
        tableBuilder.startCell();
        builder.writeln("Test");
        tableBuilder.endTable();

        table.setTitle("Test Title");
        table.setDescription("Test description");

        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestTitle.docx", null, false);
        table = doc.getFirstSection().getBody().getTables().get(0);

        msAssert.areEqual("Test Title", table.getTitle());
        msAssert.areEqual("Test description", table.getDescription());
    }


    private static void unifiedTestVerticalPosition(String path, /*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        /*UnifiedScenario*/int scenario = TestUtil.buildScenario(lf, sf, (sf != SaveFormat.ODT));
        Document doc = TestUtil.openSaveOpen(path, scenario);
        TablePr tblPr = doc.getFirstSection().getBody().getTables().get(2).getFirstRow().getTablePr();

        if (sf != SaveFormat.ODT)
        {
            msAssert.areEqual(RelativeVerticalPosition.PARAGRAPH, (/*RelativeVerticalPosition*/Integer)tblPr.get(TableAttr.RELATIVE_VERTICAL_POSITION));
            msAssert.areEqual(VerticalAlignment.OUTSIDE, tblPr.get(TableAttr.VERTICAL_ALIGNMENT));
            if (sf == SaveFormat.DOC)
            {
                // Reader reads "-1" in presence of alignment for some reason. See ReadFrameTop().
                // Leave it be for now.
                msAssert.areEqual(-1, tblPr.get(TableAttr.FRAME_TOP));
                // However, it will be removed by TableValidator.
            }
            else
            {
                // MS Word behavior: in presence of alignment, position attribute is removed. (WORDSNET-25085).
                msAssert.isFalse(tblPr.contains(TableAttr.FRAME_TOP));
            }
        }
        else
        {
            // "ODT" does not write "TableAttr.RelativeVerticalPosition" and "TableAttr.FrameTop" attributes.
            msAssert.isFalse(tblPr.contains(TableAttr.RELATIVE_VERTICAL_POSITION));
            msAssert.isFalse(tblPr.contains(TableAttr.FRAME_TOP));
        }
    }



    /// <summary>
    /// WORDSNET-18162 Incorrect table style (with conditional formatting) was applied
    /// for the second row. We must apply a conditional style with TableStyleOverrideType.FirstRow for the heading rows.
    /// </summary>
    /// <remarks>
    /// Check <see cref="Row.IsHeadingRow"/> for details.
    /// </remarks>
    @Test
    public void testJira18162() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\TestJira18162.docx");

        // Verify requested by customer import of Font.AllCaps.
        Run run = doc.getFirstSection().getBody().getTables().get(0).getRows().get(1).getFirstCell().getFirstParagraph().getFirstRun();
        msAssert.areEqual(true, run.getFont().getAllCaps());
    }


    /// <summary>
    /// WORDSNET-12204 Add feature to set/get positioning of floating table
    /// Added public setters for AbsoluteHorizontalDistance, AbsoluteVerticalDistance,
    /// RelativeHorizontalAlignment and RelativeVerticalAlignment.
    /// </summary>
    @Test
    public void test12204() throws Exception
    {
        final String TEST_FILE = "Model\\Table\\Test12204.docx";

        Document doc = TestUtil.open(TEST_FILE);

        // Move first table to the position of second table.
        Table table0 = doc.getFirstSection().getBody().getTables().get(0);

        msAssert.areEqual(209.5, Double.MIN_VALUE, table0.getAbsoluteHorizontalDistance());
        table0.setRelativeHorizontalAlignment(HorizontalAlignment.CENTER);
        msAssert.isNull(table0.getFirstRow().getTablePr().get(TableAttr.FRAME_LEFT));

        msAssert.areEqual(81.65, Double.MIN_VALUE, table0.getAbsoluteVerticalDistance());
        table0.setRelativeVerticalAlignment(VerticalAlignment.CENTER);
        msAssert.isNull(table0.getFirstRow().getTablePr().get(TableAttr.FRAME_TOP));

        // Move second table to the position of first table.
        Table table1 = doc.getFirstSection().getBody().getTables().get(1);
        msAssert.areEqual(HorizontalAlignment.CENTER, table1.getRelativeHorizontalAlignment());
        table1.setAbsoluteHorizontalDistance(209.5);
        msAssert.isNull(table1.getFirstRow().getTablePr().get(TableAttr.HORIZONTAL_ALIGNMENT));

        msAssert.areEqual(VerticalAlignment.CENTER, table1.getRelativeVerticalAlignment());
        table1.setAbsoluteVerticalDistance(81.65);
        msAssert.isNull(table1.getFirstRow().getTablePr().get(TableAttr.VERTICAL_ALIGNMENT));

        TestUtil.save(doc, TEST_FILE, SaveOptions.createSaveOptions(SaveFormat.DOCX), true);
    }

    /// <summary>
    /// WORDSNET-19873 Tests vertical anchor allowed values.
    /// </summary>
    @Test (dataProvider = "test19873VerticalArgumentDataProvider")
    public void test19873VerticalArgument(/*RelativeVerticalPosition*/int vAnchor, boolean allowed) throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\Test19873Inline.docx");
        Table table = doc.getFirstSection().getBody().getTables().get(0);

        boolean thrown = false;
        try
        {
            table.setVerticalAnchor(vAnchor);
        }
        catch (IllegalArgumentException e)
        {
            thrown = true;
        }

        msAssert.areEqual(allowed, !thrown);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19873VerticalArgumentDataProvider")
	public static Object[][] test19873VerticalArgumentDataProvider() throws Exception
	{
		return new Object[][]
		{
			{RelativeVerticalPosition.MARGIN,  true},
			{RelativeVerticalPosition.PAGE,  true},
			{RelativeVerticalPosition.PARAGRAPH,  true},
			{RelativeVerticalPosition.LINE,  false},
			{RelativeVerticalPosition.BOTTOM_MARGIN,  false},
			{RelativeVerticalPosition.INSIDE_MARGIN,  false},
			{RelativeVerticalPosition.OUTSIDE_MARGIN,  false},
			{RelativeVerticalPosition.TOP_MARGIN,  false},
		};
	}

    /// <summary>
    /// WORDSNET-19873 Tests horizontal anchor allowed values.
    /// </summary>
    @Test (dataProvider = "test19873HorizontalArgumentDataProvider")
    public void test19873HorizontalArgument(/*RelativeHorizontalPosition*/int hAnchor, boolean allowed) throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\Test19873Inline.docx");
        Table table = doc.getFirstSection().getBody().getTables().get(0);

        boolean thrown = false;
        try
        {
            table.setHorizontalAnchor(hAnchor);
        }
        catch (IllegalArgumentException e)
        {
            thrown = true;
        }

        msAssert.areEqual(allowed, !thrown);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19873HorizontalArgumentDataProvider")
	public static Object[][] test19873HorizontalArgumentDataProvider() throws Exception
	{
		return new Object[][]
		{
			{RelativeHorizontalPosition.MARGIN,  true},
			{RelativeHorizontalPosition.PAGE,  true},
			{RelativeHorizontalPosition.COLUMN,  true},
			{RelativeHorizontalPosition.CHARACTER,  false},
			{RelativeHorizontalPosition.LEFT_MARGIN,  false},
			{RelativeHorizontalPosition.INSIDE_MARGIN,  false},
			{RelativeHorizontalPosition.OUTSIDE_MARGIN,  false},
			{RelativeHorizontalPosition.RIGHT_MARGIN,  false},
		};
	}

    /// <summary>
    /// WORDSNET-19873 Tests vertical anchor applied to inline table.
    /// </summary>
    @Test (dataProvider = "test19873VerticalResultDataProvider")
    public void test19873VerticalResult(/*RelativeVerticalPosition*/int vAnchor, String outputFile) throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\Test19873Inline.docx");
        Table table = doc.getFirstSection().getBody().getTables().get(0);
        msAssert.isFalse(table.isFloating());

        table.setVerticalAnchor(vAnchor);

        msAssert.isTrue(table.isFloating());
        msAssert.areEqual(vAnchor, table.getVerticalAnchor());

        // Low level check that frame top is explicitly set.
        TablePr tablePr = table.getRows().get(0).getTablePr();
        msAssert.areEqual(0, tablePr.get(TableAttr.FRAME_TOP));
        msAssert.areEqual(180, tablePr.get(TableAttr.FRAME_DISTANCE_FROM_LEFT));
        msAssert.areEqual(180, tablePr.get(TableAttr.FRAME_DISTANCE_FROM_RIGHT));

        TestUtil.saveOpen(doc, outputFile);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19873VerticalResultDataProvider")
	public static Object[][] test19873VerticalResultDataProvider() throws Exception
	{
		return new Object[][]
		{
			{RelativeVerticalPosition.MARGIN,  "Model\\Table\\Test19873InlineVerticalTableDefault.docx"},
			{RelativeVerticalPosition.PAGE,  "Model\\Table\\Test19873InlineVerticalPage.docx"},
			{RelativeVerticalPosition.PARAGRAPH,  "Model\\Table\\Test19873InlineVerticalTextFrameDefault.docx"},
		};
	}

    /// <summary>
    /// WORDSNET-19873 Tests horizontal anchor applied to inline table.
    /// </summary>
    @Test (dataProvider = "test19873HorizontalResultDataProvider")
    public void test19873HorizontalResult(/*RelativeHorizontalPosition*/int hAnchor, String outputFile) throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\Test19873Inline.docx");
        Table table = doc.getFirstSection().getBody().getTables().get(0);
        msAssert.isFalse(table.isFloating());

        table.setHorizontalAnchor(hAnchor);

        msAssert.isTrue(table.isFloating());
        msAssert.areEqual(hAnchor, table.getHorizontalAnchor());

        // Low level check that frame top is explicitly set.
        // It is interesting that Word sets vertical position to force floating when horizontal anchor set.
        TablePr tablePr = table.getRows().get(0).getTablePr();
        msAssert.areEqual(0, tablePr.get(TableAttr.FRAME_TOP));
        msAssert.areEqual(RelativeVerticalPosition.PARAGRAPH, tablePr.get(TableAttr.RELATIVE_VERTICAL_POSITION));
        msAssert.areEqual(180, tablePr.get(TableAttr.FRAME_DISTANCE_FROM_LEFT));
        msAssert.areEqual(180, tablePr.get(TableAttr.FRAME_DISTANCE_FROM_RIGHT));

        TestUtil.saveOpen(doc, outputFile);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19873HorizontalResultDataProvider")
	public static Object[][] test19873HorizontalResultDataProvider() throws Exception
	{
		return new Object[][]
		{
			{RelativeHorizontalPosition.MARGIN,  "Model\\Table\\Test19873InlineHorizontalMargin.docx"},
			{RelativeHorizontalPosition.PAGE,  "Model\\Table\\Test19873InlineHorizontalPage.docx"},
			{RelativeHorizontalPosition.COLUMN,  "Model\\Table\\Test19873InlineHorizontalColumn.docx"},
		};
	}

    @Test (dataProvider = "test19873VerticalFloatingDataProvider")
    public void test19873VerticalFloating(/*RelativeVerticalPosition*/int vAnchor) throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\Test19873Floating.docx");

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        msAssert.isTrue(table.isFloating());
        msAssert.areEqual(50, table.getAbsoluteVerticalDistance());
        msAssert.areEqual(20, table.getAbsoluteHorizontalDistance());

        table.setVerticalAnchor(vAnchor);

        // All floating attributes except VerticalAnchor are preserved.
        msAssert.isTrue(table.isFloating());
        msAssert.areEqual(vAnchor, table.getVerticalAnchor());
        msAssert.areEqual(50, table.getAbsoluteVerticalDistance());
        msAssert.areEqual(20, table.getAbsoluteHorizontalDistance());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19873VerticalFloatingDataProvider")
	public static Object[][] test19873VerticalFloatingDataProvider() throws Exception
	{
		return new Object[][]
		{
			{RelativeVerticalPosition.MARGIN},
			{RelativeVerticalPosition.PAGE},
			{RelativeVerticalPosition.PARAGRAPH},
		};
	}

    /// <summary>
    /// WORDSNET-19873 Tests horizontal anchor applied to floating table.
    /// </summary>
    @Test (dataProvider = "test19873HorizontalFloatingDataProvider")
    public void test19873HorizontalFloating(/*RelativeHorizontalPosition*/int hAnchor) throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\Test19873Floating.docx");

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        msAssert.isTrue(table.isFloating());
        msAssert.areEqual(50, table.getAbsoluteVerticalDistance());
        msAssert.areEqual(20, table.getAbsoluteHorizontalDistance());

        table.setHorizontalAnchor(hAnchor);

        // All floating attributes except HorizontalAnchor are preserved.
        msAssert.isTrue(table.isFloating());
        msAssert.areEqual(hAnchor, table.getHorizontalAnchor());
        msAssert.areEqual(50, table.getAbsoluteVerticalDistance());
        msAssert.areEqual(20, table.getAbsoluteHorizontalDistance());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19873HorizontalFloatingDataProvider")
	public static Object[][] test19873HorizontalFloatingDataProvider() throws Exception
	{
		return new Object[][]
		{
			{RelativeHorizontalPosition.MARGIN},
			{RelativeHorizontalPosition.PAGE},
			{RelativeHorizontalPosition.COLUMN},
		};
	}





    /// <summary>
    /// WORDSNET-22097 <see cref="Table.ConvertToHorizontallyMergedCells"/> threw an
    /// <see cref="IndexOutOfRangeException"/> for a table created with <see cref="DocumentBuilder"/>.
    /// Now <see cref="Table.ConvertToHorizontallyMergedCells"/> does nothing for tables with zero width
    /// of cells.
    /// </summary>
    @Test
    public void test22097() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();

        builder.insertCell();
        builder.write("This is row 1 cell 1");

        builder.insertCell();
        builder.write("This is row 1 cell 2");

        builder.endRow();
        builder.endTable();

        // IndexOutOfRangeException was thrown on this line.
        table.convertToHorizontallyMergedCells();

        msAssert.areEqual(1, table.getRows().getCount());
        msAssert.areEqual(2, table.getFirstRow().getCells().getCount());
    }


    /// <summary>
    /// WORDSNET-17523 How to determine maximum value for table border
    /// Added descriptive exception message.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QMust be non-negative and less than or equal to 31.\r\nParameter name: lineWidth\\E")
    public void test17523() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        Table table = builder.startTable();
        builder.insertCell();
        builder.writeln("test");
        builder.endRow();
        builder.endTable();

        table.setBorder(BorderType.BOTTOM, LineStyle.SINGLE, 32.0, Color.RED, false);
    }


    /// <summary>
    /// WORDSNET-25164 Wk: Floating table margins setters
    /// Implemented an ability to set DistanceXXX properties of a <see cref="Table"/>. If a table is not floating,
    /// it becomes floating after setting the properties.
    /// </summary>
    @Test
    public void test25164() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Generate text.
        for (int i = 0; i < 20; i++)
            builder.write("Text1 ");

        builder.writeln();

        // Insert a table.
        Table table = builder.startTable();
        builder.insertCell();
        builder.write("Cell1");
        builder.insertCell();
        builder.write("Cell2");
        builder.endRow();
        builder.insertCell();
        builder.write("Cell3");
        builder.insertCell();
        builder.write("Cell4");
        builder.endRow();
        builder.endTable();

        // Generate text.
        for (int i = 0; i < 20; i++)
            builder.write("Text2 ");

        builder.writeln();

        table.autoFit(AutoFitBehavior.AUTO_FIT_TO_CONTENTS);
        table.setAlignment(TableAlignment.CENTER);

        msAssert.isFalse(table.isFloating());

        // Set table distance from text.
        table.setDistanceLeft(1.0);
        table.setDistanceRight(2.0);
        table.setDistanceTop(12.0);
        table.setDistanceBottom(14.0);

        msAssert.isTrue(table.isFloating());

        doc = TestUtil.saveOpen(doc, "Model\\Table\\Test25164", UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.EXPORT_ONLY);

        table = doc.getFirstSection().getBody().getTables().get(0);
        msAssert.isTrue(table.isFloating());
        msAssert.areEqual(1.0, 0.01, table.getDistanceLeft());
        msAssert.areEqual(2.0, 0.01, table.getDistanceRight());
        msAssert.areEqual(12.0, 0.01, table.getDistanceTop());
        msAssert.areEqual(14.0, 0.01, table.getDistanceBottom());
    }


    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testNegativeDistance() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a table.
        Table table = builder.startTable();
        builder.insertCell();
        builder.endRow();

        // Set wrong distance.
        table.setDistanceLeft(-0.5);
    }

    /// <summary>
    /// WORDSNET-25297 (fixed) - Provide public access to CellPr.HideMark property.
    /// Added new public property CellFormat.HideMark.
    /// </summary>
    @Test
    public void test25297() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\TestHideMarkTable", LoadFormat.DOCX);

        Cell cell = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell();
        msAssert.isFalse(cell.getCellFormat().getHideMark());

        cell.getCellFormat().setHideMark(true);

        doc = TestUtil.saveOpen(doc, "Model\\Table\\TestHideMarkTable", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        cell = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell();
        msAssert.isTrue(cell.getCellFormat().getHideMark());
    }


    private static void testHtmlBlock18599(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\Test18599", lf, sf);

        int htmlBlockId = doc.getFirstSection().getBody().getTables().get(0).getRows().get(0).getTablePr().getHtmlBlockId();
        msAssert.areEqual(sf != SaveFormat.RTF ? 1501000681 : 2, htmlBlockId);
        msAssert.areEqual(2, doc.getHtmlBlockCollection().getCount());
        msAssert.areEqual(-450, doc.getHtmlBlockCollection().getHtmlBlockById(htmlBlockId).getParaPr().get(ParaAttr.HTML_MARGIN_LEFT));
    }


    /// <summary>
    /// WORDSNET-23404 (draft) - Read and write table row widthAfter and widthBefore specified in percent units
    /// Added roundtrip for WidthBefore/WidthAfter specified in percents.
    /// </summary>
    @Test
    public void test23404() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\Test23404", UnifiedScenario.DOCX_2_DOCX);

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        PreferredWidth widthAfter = (PreferredWidth)table.getRows().get(1).getTablePr().get(TableAttr.WIDTH_AFTER);

        msAssert.isTrue(widthAfter.isPercent());
        msAssert.areEqual(55.56, 0.001, widthAfter.getValue());
    }



    /// <summary>
    /// WORDSNET-23670 Table column width is changed after open/save document.
    /// Added roundtrip for WidthBefore/WidthAfter specified in percents.
    /// </summary>
    @Test
    public void test23670() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\Test23670", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        PreferredWidth widthAfter = (PreferredWidth)table.getRows().get(0).getTablePr().get(TableAttr.WIDTH_AFTER);
        msAssert.isTrue(widthAfter.isPercent());
        msAssert.areEqual(0.94, 0.001, widthAfter.getValue());

        // FOSS The grid re-calculation that kept "width before" in percent units is removed.
        // The grid calculation from cell widths stores the value in twips.
        PreferredWidth widthBefore = (PreferredWidth)table.getRows().get(1).getTablePr().get(TableAttr.WIDTH_BEFORE);
        msAssert.isTrue(widthBefore.isFixed());
        msAssert.areEqual(13, widthBefore.getValueTwips());
    }



    /// <summary>
    /// WORDSNET-25899 Add support to hide or unhide row in the table.
    /// Tests hide row and check if the attribute are preserved after roundtrip.
    /// </summary>
    @Test (dataProvider = "test25899HideRowDataProvider")
    public void test25899HideRow(/*LoadFormat*/int loadFormat, /*SaveFormat*/int saveFormat) throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\Test25899", loadFormat);

        Row row = doc.getFirstSection().getBody().getTables().get(0).getRows().get(3);
        msAssert.isFalse(row.getHidden());
        row.setHidden(true);

        doc = TestUtil.saveOpen(doc, "Model\\Table\\Test25899HideRow", TestUtil.getUnifiedScenario(loadFormat, saveFormat));

        row = doc.getFirstSection().getBody().getTables().get(0).getRows().get(3);
        msAssert.isTrue(row.getHidden());

        for (Cell cell : (Iterable<Cell>) row.getCells())
        {
            for (Paragraph para : (Iterable<Paragraph>) cell.getParagraphs())
            {
                for (Run run : (Iterable<Run>) para.getRuns())
                    msAssert.isTrue(run.getFont().getHidden());
            }
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test25899HideRowDataProvider")
	public static Object[][] test25899HideRowDataProvider() throws Exception
	{
		return new Object[][]
		{
			{LoadFormat.DOCX,  SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-25899.
    /// Tests unhide row and check if the attribute are preserved after roundtrip.
    /// </summary>
    @Test (dataProvider = "test25899UnhideRowDataProvider")
    public void test25899UnhideRow(/*LoadFormat*/int loadFormat, /*SaveFormat*/int saveFormat) throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\Test25899HiddenRow", loadFormat);

        Row row = doc.getFirstSection().getBody().getTables().get(0).getRows().get(3);
        msAssert.isTrue(row.getHidden());
        row.setHidden(false);

        doc = TestUtil.saveOpen(doc, "Model\\Table\\Test25899UnhideRow", TestUtil.getUnifiedScenario(loadFormat, saveFormat));

        row = doc.getFirstSection().getBody().getTables().get(0).getRows().get(3);
        msAssert.isFalse(row.getHidden());

        for (Cell cell : (Iterable<Cell>) row.getCells())
        {
            for (Paragraph para : (Iterable<Paragraph>) cell.getParagraphs())
            {
                for (Run run : (Iterable<Run>) para.getRuns())
                    msAssert.isFalse(run.getFont().getHidden());
            }
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test25899UnhideRowDataProvider")
	public static Object[][] test25899UnhideRowDataProvider() throws Exception
	{
		return new Object[][]
		{
			{LoadFormat.DOCX,  SaveFormat.DOCX},
		};
	}



    private static Table create1x1Table(Document doc)
    {
        Table table = new Table(doc);

        Row row = new Row(doc);
        table.appendChild(row);

        Cell cell = new Cell(doc);
        row.appendChild(cell);

        return table;
    }
}

