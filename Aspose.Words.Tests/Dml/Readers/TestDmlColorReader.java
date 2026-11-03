// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests for <see cref="DmlColorReader"/>. 
/// </summary>
@Test
public class TestDmlColorReader
{
    @Test
    public void build_HslColor_ColorBuilt()
    {
        // Arrange
        String xml = "<hslClr hue=\"14400000\" sat=\"100.000%\" lum=\"50.000%\"/>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlHslColor result = (DmlHslColor)DmlColorReader.read(reader, null);
        // Assert
        msAssert.areEqual(new DmlAngle(14400000.0), result.getHue());
        msAssert.areEqual(DmlPercentageUtil.fromPercent("100.000%"), result.getSaturation());
        msAssert.areEqual(DmlPercentageUtil.fromPercent("50.000%"), result.getLuminance());
    }

    @Test
    public void build_PresetColor_ColorBuilt()
    {
        // Arrange
        String xml = "<prstClr val=\"black\"/>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPresetColor result = (DmlPresetColor)DmlColorReader.read(reader, null);
        // Assert
        msAssert.areEqual("black", result.getValue());
    }

    @Test
    public void build_PercentageRgbColor_ColorBuilt()
    {
        // Arrange
        String xml = "<scrgbClr r=\"50%\" g=\"-51%\" b=\"1%\"/>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPercentageRgbColor result = (DmlPercentageRgbColor)DmlColorReader.read(reader, null);
        // Assert
        msAssert.areEqual(DmlPercentageUtil.fromPercent("50%"), result.getR());
        msAssert.areEqual(DmlPercentageUtil.fromPercent("-51%"), result.getG());
        msAssert.areEqual(DmlPercentageUtil.fromPercent("1%"), result.getB());
    }

    @Test
    public void build_HexRgbColor_ColorBuilded()
    {
        // Arrange
        String xml = "<srgbClr val=\"10ABCD\"/>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlHexRgbColor result = (DmlHexRgbColor)DmlColorReader.read(reader, null);
        // Assert
        msAssert.areEqual("10ABCD", result.getValue());
    }

    @Test
    public void build_SchemeColor_ColorBuilt()
    {
        // Arrange
        String xml = "<schemeClr val=\"lt1\"/>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlSchemeColor result = (DmlSchemeColor)DmlColorReader.read(reader, null);
        // Assert
        msAssert.areEqual(ThemeColor.LIGHT_1, result.getValue());
    }

    @Test
    public void build_SchemeColorWithPlaceholderValue_ColorBuilt()
    {
        // Arrange
        String xml = "<schemeClr val=\"phClr\"/>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlColor result = DmlColorReader.read(reader, null);
        // Assert
        msAssert.isTrue(result instanceof DmlPlaceholderColor);
    }

    @Test
    public void build_SystemColor_ColorBuilt()
    {
        // Arrange
        String xml = "<sysClr val=\"windowText\" lastClr=\"FF0011\"/>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlSystemColor result = (DmlSystemColor)DmlColorReader.read(reader, null);
        // Assert
        msAssert.areEqual("windowText", result.getValue());
        msAssert.areEqual("FF0011", result.getLastColor());
    }

    @Test
    public void readColor_XmlContainsSchemeColorAndSystemColor_TheLastColorIsRead()
    {
        // Arrange.
        String xml =
            "<clrFrom>" +
                "<schemeClr />" +
                "<sysClr />" +
            "</clrFrom>";

        NrxXmlReader reader = new NrxXmlReader(xml, null);

        // Act.
        DmlColor color = DmlColorReader.readColor(reader, null);

        // Assert.
        msAssert.isInstanceOf(DmlSystemColor.class, color);
    }
}

