// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2010 by Alexey Titov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests for <see cref="DmlFillReader"/>.
/// </summary>
@Test
public class TestDmlFillReader
{
    @Test
    public void build_BlipFillWithAttributes_CorrespondingPropertiesInitialized() throws Exception
    {
        // Arrange
        String xml = "<blipFill dpi=\"120\" rotWithShape=\"true\"/>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlBlipFill result = (DmlBlipFill)DmlFillReader.read(reader);
        // Assert
        msAssert.areEqual(120, result.getDotsPerInch());
        msAssert.areEqual(true, result.getRotateWithShape());
    }

    @Test
    public void build_BlipFillWithStretch_StretchInitialized() throws Exception
    {
        // Arrange
        String xml = "<blipFill>" +
                        "<stretch>" +
                            "<fillRect l=\"50000\" t=\"60000\" r=\"70000\" b=\"80000\" />" +
                        "</stretch>" +
                     "</blipFill>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlBlipFill result = (DmlBlipFill)DmlFillReader.read(reader);
        // Assert
        DmlBlipFillStretch dmlBlipFillStretch = ((DmlBlipFillStretch)result.getBlipFillMode());
        msAssert.areEqual(new DmlPercentageOffsetRectangle(0.5, 0.6, 0.7, 0.8), dmlBlipFillStretch.getFillRectangle());
    }

    @Test
    public void build_BlipFillWithSourceRectangle_SourceRectangleInitialized() throws Exception
    {
        // Arrange
        String xml = "<blipFill>" +
                         "<srcRect l=\"50000\" t=\"60000\" r=\"70000\" b=\"80000\" />" +
                     "</blipFill>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlBlipFill result = (DmlBlipFill)DmlFillReader.read(reader);
        // Assert
        msAssert.areEqual(new DmlPercentageOffsetRectangle(0.5, 0.6, 0.7, 0.8), result.getSourceRectangle());
    }

    @Test
    public void build_BlipFillWithTile_TileInitialized() throws Exception
    {
        // Arrange
        String xml = "<blipFill>" +
                         "<tile algn=\"ctr\" flip=\"y\" sx=\"50000\" sy=\"100000\" tx=\"100\" ty=\"200\"/>" +
                     "</blipFill>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlBlipFill result = (DmlBlipFill)DmlFillReader.read(reader);
        // Assert
        DmlBlipFillTile tile = (DmlBlipFillTile)result.getBlipFillMode();
        msAssert.areEqual(DmlRectangleAlignment.CENTER, tile.getAlignment());
        msAssert.areEqual(DmlTileFlipMode.VERTICAL, tile.getTileFlipMode());
        msAssert.areEqual(0.5, tile.getHorizontalRatio());
        msAssert.areEqual(1.0, tile.getVerticalRatio());
        msAssert.areEqual(100.0, tile.getHorizontalOffset());
        msAssert.areEqual(200.0, tile.getVerticalOffset());
    }

    @Test
    public void build_BlipFillWithBilp_BlipInitialized() throws Exception
    {
        // Arrange
        String xml = "<blipFill>" +
                        "<blip embed=\"rId2\" link=\"http://url\" cstate=\"hqprint\"/>" +
                     "</blipFill>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlBlipFill result = (DmlBlipFill)DmlFillReader.read(reader);
        // Assert
        msAssert.areEqual(new byte[0], result.getBlip().getEmbedImage());
        msAssert.areEqual(msString.format("reltaget/http://url"), result.getBlip().getImageLink());
        msAssert.areEqual(DmlCompressionState.HIGH_QUALITY_PRINT, result.getBlip().getCompressionState());
        msAssert.areEqual(0, result.getBlip().getEffects().size());
    }

    @Test
    public void build_GradFill_DmlGradientFillBuilded() throws Exception
    {
        // Arrange
        String xml = "<gradFill rotWithShape=\"1\" flip=\"xy\">" +
                        "<tileRect l=\"50000\" t=\"60000\" r=\"70000\" b=\"80000\"/>" +
                     "</gradFill>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlGradientFill result = (DmlGradientFill)DmlFillReader.read(reader);
        // Assert
        msAssert.isTrue(result.getRotateWithShape());
        msAssert.areEqual(DmlTileFlipMode.HORIZONTAL_AND_VERTICAL, result.getTileFlipMode());
        msAssert.areEqual(new DmlPercentageOffsetRectangle(0.5, 0.6, 0.7, 0.8), result.getTileRectangle());
    }

    @Test
    public void build_GradFillWithGradientStop_DmlGradientFillBuilded() throws Exception
    {
        // Arrange
        String xml = "<gradFill>" +
                          "<gsLst>" +
                                "<gs pos=\"0\">" +
                                    "<schemeClr val=\"phClr\"/>" +
                                "</gs>" +
                          "</gsLst>" +
                     "</gradFill>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlGradientFill result = (DmlGradientFill)DmlFillReader.read(reader);
        // Assert
        msAssert.areEqual(1, result.getGradientStops().size());
        DmlGradientStop gradientStop = result.getGradientStops().get(0);
        msAssert.areEqual(0.0, gradientStop.getPosition());
        msAssert.isTrue(gradientStop.getColor() instanceof DmlPlaceholderColor);
    }

    @Test
    public void build_GradFillWithLinearGradient_DmlGradientFillBuilded() throws Exception
    {
        // Arrange
        String xml = "<gradFill>" +
                          "<lin ang=\"16200000\" scaled=\"1\"/> " +
                     "</gradFill>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlGradientFill result = (DmlGradientFill)DmlFillReader.read(reader);
        // Assert
        DmlLinearGradient gradient = (DmlLinearGradient)result.getGradient();
        msAssert.areEqual(16200000.0, gradient.getAngle().getValue());
        msAssert.isTrue(gradient.isScaled());
    }

    @Test
    public void build_GradFillWithPathGradient_DmlGradientFillBuilded() throws Exception
    {
        // Arrange
        String xml = "<gradFill>" +
                          "<path path=\"circle\">" +
                                "<fillToRect l=\"50000\" t=\"60000\" r=\"70000\" b=\"80000\"/>" +
                          "</path>" +
                     "</gradFill>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlGradientFill result = (DmlGradientFill)DmlFillReader.read(reader);
        // Assert
        DmlPathGradient gradient = (DmlPathGradient)result.getGradient();
        msAssert.areEqual(new DmlPercentageOffsetRectangle(0.5,0.6,0.7,0.8), gradient.getFillToRectangle());
        msAssert.areEqual(DmlPathShadeType.CIRCLE, gradient.getPath());
    }

    @Test
    public void build_GrpFill_DmlGroupFillBuilded() throws Exception
    {
        // Arrange
        String xml = "<grpFill>" +
                     "</grpFill>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlGroupFill result = (DmlGroupFill)DmlFillReader.read(reader);
        // Assert
        msAssert.isNotNull(result);
    }

    @Test
    public void build_NoFill_DmlNoFillBuilded() throws Exception
    {
        // Arrange
        String xml = "<noFill>" +
                     "</noFill>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlNoFill result = (DmlNoFill)DmlFillReader.read(reader);
        // Assert
        msAssert.isNotNull(result);
    }

    @Test
    public void build_PattFill_DmlPatternFillBuilded() throws Exception
    {
        // Arrange
        String xml = "<pattFill prst=\"pct70\">" +
                        "<bgClr><sysClr val=\"windowText\"/></bgClr>" +
                        "<fgClr><schemeClr val=\"lt1\"/></fgClr>" +
                     "</pattFill>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlPatternFill result = (DmlPatternFill)DmlFillReader.read(reader);
        // Assert
        msAssert.areEqual(PatternType.PERCENT_70, result.getFillPresetPattern());
        msAssert.isTrue(result.getBackgroundColor() instanceof DmlSystemColor);
        msAssert.isTrue(result.getForegroundColor() instanceof DmlSchemeColor);
    }

    @Test
    public void build_SolidFill_DmlSolidFillBuilded() throws Exception
    {
        // Arrange
        String xml = "<solidFill>" +
                        "<srgbClr>" +
                        "</srgbClr>" + 
                     "</solidFill>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlSolidFill result = (DmlSolidFill)DmlFillReader.read(reader);
        // Assert
        msAssert.areEqual(DmlColorType.HEX_RGB_COLOR, result.getColor().getColorType());
    }
}

