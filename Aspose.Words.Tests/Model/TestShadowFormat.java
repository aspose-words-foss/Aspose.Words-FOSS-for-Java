// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/05/2022 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests shadow formatting.
/// </summary>
@Test
public class TestShadowFormat
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-23877 Provide API to remove the shape shadows.
    /// DML shape case.
    /// </summary>
    @Test
    public void test23877Dml() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\ShadowFormat\\TestShadowDML.docx");

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);

        msAssert.isTrue(shape.getShadowFormat().getVisible());
        msAssert.isNotNull(shape.getDmlShape().getEffects().getByDmlShapeEffectType(DmlShapeEffectType.OUTER_SHADOW));

        shape.getShadowFormat().clear();

        msAssert.isFalse(shape.getShadowFormat().getVisible());
        msAssert.isNull(shape.getDmlShape().getEffects());
    }

    // FOSS: Test23877Vml removed — loads a VML .doc and asserts VML-specific shadow
    // representation (ShapeAttr.ShadowOn/ShadowType/ShadowOpacity). The Doc reader is
    // removed and VML shapes have no equivalent in the DML path. DML shadow removal
    // stays covered by Test23877Dml.





    /// <summary>
    /// WORDSNET-26555 Add support to get DML effects of a Shape.
    /// Implemented <see cref="ShadowFormat.Color"/>.
    /// </summary>
    @Test (dataProvider = "test26555DataProvider") // FOSS: VML .doc case removed (Doc reader gone; VML shadow is DOC-specific).
    public void test26555(String fileExt) throws Exception
    {
        String fileName = msString.format("Model\\Shape\\ShadowFormat\\Test26555{0}", fileExt);
        Document doc = TestUtil.open(fileName);

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        TestUtil.checkColor(new Color((0x66FF0000), true), shape.getShadowFormat().getColor());

        doc = TestUtil.saveOpen(doc, fileName);

        shape = doc.getFirstSection().getBody().getShapes().get(0);
        TestUtil.checkColor(new Color((0x66FF0000), true), shape.getShadowFormat().getColor());

        // Check also color in cleared shadow.
        shape.getShadowFormat().clear();
        TestUtil.checkColor(Color.BLACK, shape.getShadowFormat().getColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test26555DataProvider")
	public static Object[][] test26555DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"DML.docx"},
		};
	}

    /// <summary>
    /// WORDSNET-28384 Provide API to set Shadow Color on Shape
    /// Tests the new public Color property.
    /// </summary>
    @Test (dataProvider = "test28384ColorDataProvider") // FOSS: VML .doc case removed (Doc reader gone; VML shadow is DOC-specific).
    public void test28384Color(String fileExt) throws Exception
    {
        String fileName = msString.format("Model\\Shape\\ShadowFormat\\Test28384{0}", fileExt);
        Document doc = TestUtil.open(fileName);
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        TestUtil.checkColor(Color.BLACK, shape.getShadowFormat().getColor());

        shape.getShadowFormat().setColor(Color.RED);
        TestUtil.checkColor(Color.RED, shape.getShadowFormat().getColor());

        shape.getShadowFormat().setColor(msColor.Empty);
        TestUtil.checkColor(msColor.Empty, shape.getShadowFormat().getColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28384ColorDataProvider")
	public static Object[][] test28384ColorDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"DML.docx"},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-28384.
    /// Tests the new public Transparency property.
    /// </summary>
    @Test (dataProvider = "test28384TransparencyDataProvider") // FOSS: VML .doc case removed (Doc reader gone; VML shadow is DOC-specific).
    public void test28384Transparency(String fileExt) throws Exception
    {
        String fileName = msString.format("Model\\Shape\\ShadowFormat\\Test28384{0}", fileExt);
        Document doc = TestUtil.open(fileName);
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        msAssert.areEqual(0.0, 0.01, shape.getShadowFormat().getTransparency());
        shape.getShadowFormat().setTransparency(0.5);
        msAssert.areEqual(0.5, 0.01, shape.getShadowFormat().getTransparency());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28384TransparencyDataProvider")
	public static Object[][] test28384TransparencyDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"DML.docx"},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-28384.
    /// Tests that exception is thrown when applying value to Transparency out of [0, 1] range.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QExpected a value between 0 and 1.\r\nParameter name: Shadow.Transparency\\E", dataProvider = "test28384TransparencyInvalidDataProvider")
    // FOSS: VML .doc cases removed (Doc reader gone).
    public void test28384TransparencyInvalid(String fileExt, double transparency) throws Exception
    {
        String fileName = msString.format("Model\\Shape\\ShadowFormat\\Test28384{0}", fileExt);
        Document doc = TestUtil.open(fileName);
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        shape.getShadowFormat().setTransparency(transparency);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28384TransparencyInvalidDataProvider")
	public static Object[][] test28384TransparencyInvalidDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"DML.docx",  -0.3},
			{"DML.docx",  1.3},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-28384.
    /// Tests stability of changing the shadow type to the same value.
    /// </summary>
    @Test (dataProvider = "test28384ResetTypeResilienceDataProvider") // FOSS: VML .doc case removed (Doc reader gone; VML shadow is DOC-specific).
    public void test28384ResetTypeResilience(String fileExt) throws Exception
    {
        String fileName = msString.format("Model\\Shape\\ShadowFormat\\Test28384{0}", fileExt);
        Document doc = TestUtil.open(fileName);
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        shape.getShadowFormat().setType(ShadowType.SHADOW_21);
        shape.getShadowFormat().setColor(Color.RED);
        shape.getShadowFormat().setTransparency(0.5);

        // Checks that setting the same shadow type does not reset the other properties.
        shape.getShadowFormat().setType(ShadowType.SHADOW_21);
        TestUtil.checkColor(new Color((255), (0), (0), (128)), shape.getShadowFormat().getColor());
        msAssert.areEqual(0.5, 0.01, shape.getShadowFormat().getTransparency());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28384ResetTypeResilienceDataProvider")
	public static Object[][] test28384ResetTypeResilienceDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"DML.docx"},
		};
	}

}

