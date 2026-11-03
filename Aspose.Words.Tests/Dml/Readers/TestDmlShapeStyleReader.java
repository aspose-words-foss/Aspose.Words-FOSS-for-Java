// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests for <see cref="DmlShapeStyleReader"/>.
/// </summary>
@Test
public class TestDmlShapeStyleReader
{
    @Test
    public void build_LineReference_Builded()
    {
        // Arrange
        String xml = "<style>" + 
                        "<lnRef idx=\"45\">" +
                            "<prstClr />" +
                        "</lnRef>" +
                     "</style>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlShapeStyle result = DmlShapeStyleReader.read(reader, null);
        // Assert
        msAssert.areEqual(45, result.getLineReference().getStyleMatrixIndex());
        msAssert.areEqual(DmlColorType.PRESET_COLOR, result.getLineReference().getColor().getColorType());
    }

    @Test
    public void build_FillReference_Builded()
    {
        // Arrange
        String xml = "<style>" +
                        "<fillRef idx=\"45\">" +
                            "<prstClr />" +
                        "</fillRef>" +
                     "</style>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlShapeStyle result = DmlShapeStyleReader.read(reader, null);
        // Assert
        msAssert.areEqual(45, result.getFillReference().getStyleMatrixIndex());
        msAssert.areEqual(DmlColorType.PRESET_COLOR, result.getFillReference().getColor().getColorType());
    }

    @Test
    public void build_EffectReference_Builded()
    {
        // Arrange
        String xml = "<style>" +
                        "<effectRef idx=\"45\">" +
                            "<prstClr />" +
                        "</effectRef>" +
                     "</style>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlShapeStyle result = DmlShapeStyleReader.read(reader, null);
        // Assert
        msAssert.areEqual(45, result.getEffectReference().getStyleMatrixIndex());
        msAssert.areEqual(DmlColorType.PRESET_COLOR, result.getEffectReference().getColor().getColorType());
    }

    @Test
    public void build_FontReference_Builded()
    {
        // Arrange
        String xml = "<style>" +
                        "<fontRef idx=\"minor\">" +
                            "<prstClr />" +
                        "</fontRef>" +
                     "</style>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlShapeStyle result = DmlShapeStyleReader.read(reader, null);
        // Assert
        msAssert.areEqual(DmlFontCollectionIndex.MINOR, result.getFontReference().getFontCollectionIndex());
        msAssert.areEqual(DmlColorType.PRESET_COLOR, result.getFontReference().getColor().getColorType());
    }
}
