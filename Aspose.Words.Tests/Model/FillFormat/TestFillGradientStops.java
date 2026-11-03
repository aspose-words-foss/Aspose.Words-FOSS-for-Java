// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/09/2021 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.SystemPal;
import org.testng.annotations.AfterMethod;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing GradientStops of Fill object.
/// </summary>
@Test
public class TestFillGradientStops extends TestFillFormatBase
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
        SystemPal.saveCulture();
        // JAVA-deleted SaveUICulture(): No analog in Java.
        SystemPal.setStandardCulture();
        // JAVA-deleted SetStandardUICulture(): No analog in Java.
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown()
    {
        SystemPal.restoreCulture();
        // JAVA-deleted RestoreUICulture(): No analog in Java.
    }

    /// <summary>
    /// Checks indexer of gradient stop collection.
    /// </summary>
    @Test (dataProvider = "testIndexerDataProvider")
    public void testIndexer(String fileName) throws Exception
    {
        final /*ShapeMarkupLanguage*/byte MARKUP_LANGUAGE = ShapeMarkupLanguage.DML;
        Document doc = open(fileName, MARKUP_LANGUAGE);

        Fill fill = getFill(doc);

        GradientStop gradientStopA = new GradientStop(msColor.getChartreuse(), 0.0);
        //Setter.
        fill.getGradientStops().set(0, gradientStopA);
        // Getter.
        msAssert.areEqual(gradientStopA, fill.getGradientStops().get(0));

        GradientStop gradientStopB = new GradientStop(msColor.getGold(), 0.8);
        // Setter.
        fill.getGradientStops().set(1, gradientStopB);
        // Getter.
        msAssert.areEqual(gradientStopB, fill.getGradientStops().get(1));

        // Roundtrip and check.
        String outFileName = msString.format("Model\\FillFormat\\{0}_GradientStop_Indexer.docx", fileName);
        doc = TestUtil.saveOpen(doc, outFileName, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        fill = getFill(doc);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIndexerDataProvider")
	public static Object[][] testIndexerDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"TextTwoColorGradient"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
		};
	}

    /// <summary>
    /// Checks insertion of gradient stops to a specified index.
    /// </summary>
    @Test (dataProvider = "testInsertDataProvider")
    public void testInsert(String fileName) throws Exception
    {
        final /*ShapeMarkupLanguage*/byte MARKUP_LANGUAGE = ShapeMarkupLanguage.DML;
        Document doc = open(fileName, MARKUP_LANGUAGE);

        Fill fill = getFill(doc);

        int originalCount = fill.getGradientStops().getCount();
        GradientStop existingGradientStopA = fill.getGradientStops().get(0);
        GradientStop existingGradientStopB = fill.getGradientStops().get(1);

        // Insert at index 1.
        GradientStop gradientStopA = new GradientStop(msColor.getChartreuse(), 0.3);
        fill.getGradientStops().insert(1, gradientStopA);
        msAssert.areEqual(originalCount + 1, fill.getGradientStops().getCount());
        msAssert.areEqual(gradientStopA, fill.getGradientStops().get(1));

        // Insert at index 0.
        GradientStop gradientStopB = new GradientStop(Color.YELLOW, 0.7);
        fill.getGradientStops().insert(0, gradientStopB);
        msAssert.areEqual(originalCount + 2, fill.getGradientStops().getCount());
        msAssert.areEqual(gradientStopB, fill.getGradientStops().get(0));

        // Roundtrip and check.
        String outFileName = msString.format("Model\\FillFormat\\{0}_GradientStop_Insert.docx", fileName);
        doc = TestUtil.saveOpen(doc, outFileName, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        fill = getFill(doc);
        msAssert.areEqual(originalCount + 2, fill.getGradientStops().getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testInsertDataProvider")
	public static Object[][] testInsertDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"TextTwoColorGradient"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
		};
	}

    /// <summary>
    /// Checks adding gradient stop to the end of the collection and removing it at a specified index.
    /// </summary>
    @Test (dataProvider = "testAddAndRemoveAtDataProvider")
    public void testAddAndRemoveAt(String fileName) throws Exception
    {
        final /*ShapeMarkupLanguage*/byte MARKUP_LANGUAGE = ShapeMarkupLanguage.DML;
        Document doc = open(fileName, MARKUP_LANGUAGE);

        Fill fill = getFill(doc);

        int originalCount = fill.getGradientStops().getCount();
        GradientStop existingGradientStopA = fill.getGradientStops().get(0);
        GradientStop existingGradientStopB = fill.getGradientStops().get(1);

        // Check adding a new gradient stop.
        GradientStop addedGradientStop = fill.getGradientStops().add(new GradientStop(msColor.getGreen(), 0.5));
        msAssert.areEqual(addedGradientStop, fill.getGradientStops().get(originalCount));
        msAssert.areEqual(originalCount + 1, fill.getGradientStops().getCount());

        // Roundtrip.
        String outFileName = msString.format("Model\\FillFormat\\{0}_GradientStop_AddRemoveAt.docx", fileName);
        doc = TestUtil.saveOpen(doc, outFileName, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        fill = getFill(doc);
        msAssert.areEqual(originalCount + 1, fill.getGradientStops().getCount());

        // Check removing gradient stop at a specified index.
        GradientStop removedGradientStop = fill.getGradientStops().removeAt(0);

        // Roundtrip.
        outFileName = msString.format("Model\\FillFormat\\{0}_GradientStop_RemoveAt.docx", fileName);
        doc = TestUtil.saveOpen(doc, outFileName, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        fill = getFill(doc);
        msAssert.areEqual(originalCount, fill.getGradientStops().getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testAddAndRemoveAtDataProvider")
	public static Object[][] testAddAndRemoveAtDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"TextTwoColorGradient"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
		};
	}

    /// <summary>
    /// Checks adding gradient stop to the end of the collection and
    /// removing some <see cref="GradientStop"/> from the collection.
    /// </summary>
    @Test (dataProvider = "testAddAndRemoveDataProvider")
    public void testAddAndRemove(String fileName) throws Exception
    {
        final /*ShapeMarkupLanguage*/byte MARKUP_LANGUAGE = ShapeMarkupLanguage.DML;
        Document doc = open(fileName, MARKUP_LANGUAGE);

        Fill fill = getFill(doc);

        int originalCount = fill.getGradientStops().getCount();
        GradientStop existingGradientStopA = fill.getGradientStops().get(0);
        GradientStop existingGradientStopB = fill.getGradientStops().get(1);

        // Check adding a new gradient stop.
        GradientStop addedGradientStop = fill.getGradientStops().add(new GradientStop(msColor.getSkyBlue(), 0.6));
        msAssert.areEqual(addedGradientStop, fill.getGradientStops().get(originalCount));
        msAssert.areEqual(originalCount + 1, fill.getGradientStops().getCount());

        // Check removing gradient stop.
        msAssert.isTrue(fill.getGradientStops().remove(existingGradientStopB));
        msAssert.areEqual(originalCount, fill.getGradientStops().getCount());

        // Roundtrip.
        String outFileName = msString.format("Model\\FillFormat\\{0}_GradientStop_Remove.docx", fileName);
        doc = TestUtil.saveOpen(doc, outFileName, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        fill = getFill(doc);
        msAssert.areEqual(originalCount, fill.getGradientStops().getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testAddAndRemoveDataProvider")
	public static Object[][] testAddAndRemoveDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"TextTwoColorGradient"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
		};
	}

    /// <summary>
    /// Checks <see cref="GradientStop.Color"/> property of gradient stop.
    /// </summary>
    @Test (dataProvider = "testColorDataProvider")
    public void testColor(String fileName) throws Exception
    {
        final /*ShapeMarkupLanguage*/byte MARKUP_LANGUAGE = ShapeMarkupLanguage.DML;
        Document doc = open(fileName, MARKUP_LANGUAGE);

        Fill fill = getFill(doc);

        GradientStop gradientStop = fill.getGradientStops().get(0);

        //Check getter.
        msAssert.areEqual(fill.getForeColor(), fill.getGradientStops().get(0).getColor());
        msAssert.areEqual(fill.getForeColor(), gradientStop.getColor());

        // Check setter.
        gradientStop.setColor(msColor.getChartreuse());
        msAssert.areEqual(new Color((msColor.getChartreuse().getRGB()), true), fill.getGradientStops().get(0).getColor());
        msAssert.areEqual(new Color((msColor.getChartreuse().getRGB()), true), gradientStop.getColor());

        // Roundtrip.
        String outFileName = msString.format("Model\\FillFormat\\{0}_GradientStop_Color.docx", fileName);
        doc = TestUtil.saveOpen(doc, outFileName, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        fill = getFill(doc);
        msAssert.areEqual(new Color((msColor.getChartreuse().getRGB()), true), fill.getGradientStops().get(0).getColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testColorDataProvider")
	public static Object[][] testColorDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"TextTwoColorGradient"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
		};
	}

    /// <summary>
    /// Checks <see cref="GradientStop.Position"/> property of gradient stop.
    /// </summary>
    @Test (dataProvider = "testPositionDataProvider")
    public void testPosition(String fileName) throws Exception
    {
        final /*ShapeMarkupLanguage*/byte MARKUP_LANGUAGE = ShapeMarkupLanguage.DML;
        Document doc = open(fileName, MARKUP_LANGUAGE);

        Fill fill = getFill(doc);

        //Check getter.
        msAssert.areEqual(0.0, 0.001, fill.getGradientStops().get(0).getPosition());
        GradientStop gradientStop = fill.getGradientStops().get(0);
        msAssert.areEqual(0.0, 0.001, gradientStop.getPosition());

        // Check setter.
        gradientStop.setPosition(0.7);
        msAssert.areEqual(0.7, 0.001, fill.getGradientStops().get(0).getPosition());
        msAssert.areEqual(0.7, 0.001, gradientStop.getPosition());

        // Roundtrip.
        String outFileName = msString.format("Model\\FillFormat\\{0}_GradientStop_Position.docx", fileName);
        doc = TestUtil.saveOpen(doc, outFileName, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        fill = getFill(doc);
        msAssert.areEqual(0.7, 0.001, fill.getGradientStops().get(0).getPosition());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testPositionDataProvider")
	public static Object[][] testPositionDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"TextTwoColorGradient"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
		};
	}

    /// <summary>
    /// Checks <see cref="GradientStop.Transparency"/> property of gradient stop.
    /// </summary>
    @Test (dataProvider = "testTransparencyDataProvider")
    public void testTransparency(String fileName) throws Exception
    {
        final /*ShapeMarkupLanguage*/byte MARKUP_LANGUAGE = ShapeMarkupLanguage.DML;
        Document doc = open(fileName, MARKUP_LANGUAGE);

        Fill fill = getFill(doc);

        GradientStop gradientStop = fill.getGradientStops().get(0);

        //Check getter.
        msAssert.areEqual(fill.getTransparency(), 0.001, fill.getGradientStops().get(0).getTransparency());
        msAssert.areEqual(fill.getTransparency(), 0.001, gradientStop.getTransparency());

        // Check setter.
        gradientStop.setTransparency(0.15);
        msAssert.areEqual(0.15, 0.001, fill.getGradientStops().get(0).getTransparency());
        msAssert.areEqual(0.15, 0.001, gradientStop.getTransparency());

        // Check also ctor with transparency.
        gradientStop = new GradientStop(msColor.getCrimson(), 0.65, 0.25);
        fill.getGradientStops().insert(1, gradientStop);
        msAssert.areEqual(0.25, 0.001, fill.getGradientStops().get(1).getTransparency());

        // Roundtrip.
        String outFileName = msString.format("Model\\FillFormat\\{0}_GradientStop_Transparency.docx", fileName);
        doc = TestUtil.saveOpen(doc, outFileName, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        fill = getFill(doc);
        msAssert.areEqual(0.15, 0.001, fill.getGradientStops().get(0).getTransparency());
        msAssert.areEqual(0.25, 0.001, fill.getGradientStops().get(1).getTransparency());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTransparencyDataProvider")
	public static Object[][] testTransparencyDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"TextTwoColorGradient"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
		};
	}


    /// <summary>
    /// Checks <see cref="GradientStop.Remove"/> method of gradient stop.
    /// </summary>
    @Test (dataProvider = "testRemoveDataProvider")
    public void testRemove(String fileName) throws Exception
    {
        final /*ShapeMarkupLanguage*/byte MARKUP_LANGUAGE = ShapeMarkupLanguage.DML;
        Document doc = open(fileName, MARKUP_LANGUAGE);

        Fill fill = getFill(doc);

        int originalCount = fill.getGradientStops().getCount();
        GradientStop existingGradientStopA = fill.getGradientStops().get(0);
        GradientStop existingGradientStopB = fill.getGradientStops().get(1);

        GradientStop addedGradientStop = fill.getGradientStops().add(new GradientStop(msColor.getBrown(), 0.85));

        existingGradientStopA.remove();

        msAssert.areEqual(originalCount, fill.getGradientStops().getCount());
        msAssert.areEqual(existingGradientStopB, fill.getGradientStops().get(0));
        msAssert.areEqual(addedGradientStop, fill.getGradientStops().get(originalCount - 1));

        // Roundtrip.
        String outFileName = msString.format("Model\\FillFormat\\{0}_GradientStop_Remove.docx", fileName);
        doc = TestUtil.saveOpen(doc, outFileName, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        fill = getFill(doc);
        msAssert.areEqual(originalCount, fill.getGradientStops().getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRemoveDataProvider")
	public static Object[][] testRemoveDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"TextTwoColorGradient"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
		};
	}

    /// <summary>
    /// Checks that exception is raised when accessing gradient stops of a non-gradient fill.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QObject doesn't support this action.\\E", dataProvider = "testNonGradientFillsDataProvider")
    public void testNonGradientFills(String fileName) throws Exception
    {
        final /*ShapeMarkupLanguage*/byte MARKUP_LANGUAGE = ShapeMarkupLanguage.DML;
        Document doc = open(fileName, MARKUP_LANGUAGE);

        Fill fill = getFill(doc);
        msAssert.isNull(fill.getGradientStops());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNonGradientFillsDataProvider")
	public static Object[][] testNonGradientFillsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill"},
			{"TextNoFill"},
			{"Patterned"},
			{"PresetTextured"},
			{"Solid"},
			{"TextSolid"},
			{"UserPicture"},
			{"UserTextured"},
		};
	}

    /// <summary>
    /// Checks that exception is raised when accessing gradient stops of fill in VML object.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QObject doesn't support this action.\\E", dataProvider = "testVmlFillsDataProvider")
    public void testVmlFills(String fileName) throws Exception
    {
        final /*ShapeMarkupLanguage*/byte MARKUP_LANGUAGE = ShapeMarkupLanguage.VML;
        Document doc = open(fileName, MARKUP_LANGUAGE);

        Fill fill = getFill(doc);
        msAssert.isNull(fill.getGradientStops());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testVmlFillsDataProvider")
	public static Object[][] testVmlFillsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill"},
			{"TextNoFill"},
			{"Patterned"},
			{"PresetTextured"},
			{"Solid"},
			{"TextSolid"},
			{"UserPicture"},
			{"UserTextured"},
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"TextTwoColorGradient"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
		};
	}

    /// <summary>
    /// Checks that exception is raised when accessing gradient stops collection at the out of range index.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe specified value is out of range.\r\nParameter name: index\\E", dataProvider = "testIndexerGetterOutOfRangeDataProvider")
    public void testIndexerGetterOutOfRange(String fileName) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);
        msAssert.isNull(fill.getGradientStops().get(fill.getGradientStops().getCount()));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIndexerGetterOutOfRangeDataProvider")
	public static Object[][] testIndexerGetterOutOfRangeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"TextTwoColorGradient"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
		};
	}

    /// <summary>
    /// Checks that exception is raised when accessing gradient stops collection at the out of range index.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe specified value is out of range.\r\nParameter name: index\\E", dataProvider = "testIndexerSetterOutOfRangeDataProvider")
    public void testIndexerSetterOutOfRange(String fileName) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);
        fill.getGradientStops().set(fill.getGradientStops().getCount(), new GradientStop(msColor.getAqua(), 0.2));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIndexerSetterOutOfRangeDataProvider")
	public static Object[][] testIndexerSetterOutOfRangeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"TextTwoColorGradient"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
		};
	}

    /// <summary>
    /// Checks how facade and internal gradient stops are synchronizing.
    /// </summary>
    @Test (dataProvider = "testSyncDataProvider")
    public void testSync(String fileName) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);

        // Check original first gradient stop color.
        GradientStop gradientStop = fill.getGradientStops().get(0);
        msAssert.areEqual(fill.getForeColor(), gradientStop.getColor());

        // Change first gradient stop color and check foreground color of fill is altered as well.
        gradientStop.setColor(msColor.getChartreuse());
        msAssert.areEqual(new Color((msColor.getChartreuse().getRGB()), true), fill.getForeColor());

        // Change foreground color of fill and check first gradient stop color is altered as well.
        fill.setForeColor(msColor.getChocolate());
        msAssert.areEqual(new Color((msColor.getChocolate().getRGB()), true), gradientStop.getColor());
        msAssert.areEqual(new Color((msColor.getChocolate().getRGB()), true), fill.getGradientStops().get(0).getColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSyncDataProvider")
	public static Object[][] testSyncDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"TextTwoColorGradient"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
		};
	}

    /// <summary>
    /// Checks that exception is raised when applying gradient stop that belongs to another collection already.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe gradient stop belongs to another collection already.\\E")
    public void testInsertIntoOtherCollection() throws Exception
    {
        Document docA = open("OneColorGradient", ShapeMarkupLanguage.DML);
        Fill fillA = getFill(docA);

        Document docB = open("TwoColorGradient", ShapeMarkupLanguage.DML);
        Fill fillB = getFill(docB);

        // Applying gradient stop from foreign collection.
        fillA.getGradientStops().set(0, fillB.getGradientStops().get(0));
    }

    /// <summary>
    /// Checks that exception is raised when applying gradient stop
    /// that already set to another gradient index of the same collection.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe gradient stop is set to another index already.\\E")
    public void testInsertIntoSameCollection() throws Exception
    {
        Document doc = open("OneColorGradient", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);

        // Applying gradient stop from the same collection.
        fill.getGradientStops().set(0, fill.getGradientStops().get(1));
    }

    /// <summary>
    /// Checks that gradient stop can be applied to the same index of the same collection
    /// (this is meaningless, but is possible).
    /// </summary>
    @Test
    public void testInsertAtSameIndex() throws Exception
    {
        Document doc = open("OneColorGradient", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);

        // Applying gradient stop to itself.
        fill.getGradientStops().set(0, fill.getGradientStops().get(0));
    }

    /// <summary>
    /// Checks that removed gradient stop can be set to another index or even another collection.
    /// </summary>
    @Test
    public void testInsertRemoved() throws Exception
    {
        Document doc = open("PresetGradient", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);

        msAssert.areEqual(6, fill.getGradientStops().getCount());
        GradientStop gradientStop = fill.getGradientStops().get(4);
        // Remove gradient stop from the collection.
        gradientStop.remove();
        msAssert.areEqual(5, fill.getGradientStops().getCount());

        msAssert.areNotEqual(gradientStop, fill.getGradientStops().get(0));
        // Set removed gradient stop to another index of the same collection.
        fill.getGradientStops().set(0, gradientStop);
        msAssert.areEqual(gradientStop, fill.getGradientStops().get(0));

        // Remove it again to allow it to be set into another collection.
        fill.getGradientStops().remove(gradientStop);
        msAssert.areEqual(4, fill.getGradientStops().getCount());

        Document docOther = open("OneColorGradient", ShapeMarkupLanguage.DML);
        Fill fillOther = getFill(docOther);

        msAssert.areNotEqual(gradientStop, fillOther.getGradientStops().get(1));
        // Set removed gradient stop to another collection.
        fillOther.getGradientStops().set(1, gradientStop);
        msAssert.areEqual(gradientStop, fillOther.getGradientStops().get(1));
    }

    /// <summary>
    /// Checks that exception is raised when trying to remove gradient stop
    /// that doesn't belong to any collection yet.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe gradient stop doesn't belong to any collection.\\E")
    public void testRemoveNonAttached()
    {
        GradientStop gradientStop = new GradientStop(msColor.getAqua(), 0.4);
        gradientStop.remove();
    }

    /// <summary>
    /// Checks that exception is raised when trying to remove gradient stop from the collection
    /// with less or equal than two gradient stops.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThere can not be less than two gradient stops in gradient fill.\\E")
    public void testRemoveInsufficientNumberOfStops() throws Exception
    {
        Document doc = open("TwoColorGradient", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);

        fill.getGradientStops().removeAt(0);
    }

    /// <summary>
    /// Checks <see cref="GradientStop.BaseColor"/> property of gradient stop.
    /// </summary>
    @Test
    public void testBaseColor() throws Exception
    {
        Chart chart = createChart(new DocumentBuilder());

        Fill fill = chart.getSeries().get(0).getDataPoints().get(0).getFormat().getFill();
        fill.oneColorGradient(Color.RED, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.1);

        // In OneColorGradient the second color is set automatically by adding to the first color either tint or shade.
        // So, we can use this to check unmodified color is actually those we set to the first color.
        GradientStop gradientStop = fill.getGradientStops().get(1);
        msAssert.areEqual(new Color((0xFF), (0x00), (0x00)), gradientStop.getBaseColor());
        msAssert.areEqual(new Color((0x7C), (0x00), (0x00)), gradientStop.getColor());
    }
}

