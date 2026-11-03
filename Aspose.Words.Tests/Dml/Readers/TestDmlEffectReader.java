// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/09/2011 by Alexey Kachalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests for <see cref="DmlEffectReader"/>.
/// </summary>
@Test
public class TestDmlEffectReader
{
    @Test
    public void build_XmlWithBiLevelEffect_EffectIsBuiltCorrectly() throws Exception
    {
        // Arrange.
        final String XML = "<biLevel thresh=\"50000\" />";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(XML);

        // Act.
        DmlBiLevelEffect effect = (DmlBiLevelEffect)DmlEffectReader.read(reader);

        // Assert.
        msAssert.areEqual(50000, DmlPercentageUtil.toDmlPercent(effect.getThreshold()));
    }

    @Test
    public void build_XmlWithColorChangeEffectWithConsiderAlphaValuesByDefault_EffectIsBuiltCorrectly() throws Exception
    {
        // Arrange.
        final String XML = "<clrChange>" +
                           "<clrFrom>" +
                           "<srgbClr />" +
                           "</clrFrom>" +
                           "<clrTo>" +
                           "<scrgbClr />" +
                           "</clrTo>" +
                           "</clrChange>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(XML);

        // Act.
        DmlColorChangeEffect effect = (DmlColorChangeEffect)DmlEffectReader.read(reader);

        // Assert.
        msAssert.areEqual(DmlColorType.HEX_RGB_COLOR, effect.getSourceColor().getColorType());
        msAssert.areEqual(DmlColorType.PERCENTAGE_RGB_COLOR, effect.getDestinationColor().getColorType());
        msAssert.areEqual(true, effect.getConsiderAlphaValues());
    }

    @Test
    public void build_XmlWithColorChangeEffectWithConsiderAlphaValuesFalse_EffectIsBuiltCorrectly() throws Exception
    {
        // Arrange.
        final String XML = "<clrChange useA=\"false\">" +
                           "<clrFrom>" +
                           "</clrFrom>" +
                           "<clrTo>" +
                           "</clrTo>" +
                           "</clrChange>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(XML);

        // Act.
        DmlColorChangeEffect effect = (DmlColorChangeEffect)DmlEffectReader.read(reader);

        // Assert.
        msAssert.areEqual(false, effect.getConsiderAlphaValues());
    }

    @Test
    public void build_XmlWithGrayScaleEffect_EffectIsBuiltCorrectly() throws Exception
    {
        // Arrange.
        final String XML = "<grayscl />";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(XML);

        // Act.
        DmlEffect effect = DmlEffectReader.read(reader);

        // Assert.
        msAssert.isInstanceOf(DmlGrayScaleEffect.class, effect);
    }

    @Test
    public void build_XmlWithLuminanceEffect_EffectIsBuiltCorrectly() throws Exception
    {
        // Arrange.
        final String XML = "<lum bright=\"30000\" contrast=\"-30000\">";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(XML);

        // Act.
        DmlLuminanceEffect effect = (DmlLuminanceEffect)DmlEffectReader.read(reader);

        // Assert.
        msAssert.areEqual(30000D, 0.01, DmlPercentageUtil.toDmlPercent((effect.getBrightness()*2.0) - 1.0));
        msAssert.areEqual(-30000D, 0.01, DmlPercentageUtil.toDmlPercent((effect.getContrast()*2.0) - 1.0));
    }

    @Test
    public void build_XmlWithDuotoneEffect_EffectIsBuiltCorrectly() throws Exception
    {
        // Arrange.
        final String XML = "<duotone>" +
                           "<srgbClr/>" +
                           "<srgbClr/>" +
                           "</duotone>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(XML);

        // Act.
        DmlDuotoneEffect effect = (DmlDuotoneEffect)DmlEffectReader.read(reader);

        // Assert.
        // Assert.
        msAssert.areEqual(DmlColorType.HEX_RGB_COLOR, effect.getColor1().getColorType());
        msAssert.areEqual(DmlColorType.HEX_RGB_COLOR, effect.getColor2().getColorType());
    }
}

