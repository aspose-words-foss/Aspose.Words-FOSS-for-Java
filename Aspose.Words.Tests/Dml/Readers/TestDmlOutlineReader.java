// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests for <see cref="DmlOutlineReader"/>.
/// </summary>
@Test
public class TestDmlOutlineReader
{
    @Test
    public void build_EmptyTag_DefaultValuesAreCorrect() throws Exception
    {
        // Arrange
        String xml = "<ln/>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlOutline result = DmlOutlineReader.read(reader);
        // Assert
        msAssert.isFalse(result.getStrokeAlignment());
        msAssert.areEqual(ShapeLineStyle.SINGLE, result.getCompoundLineType());
        msAssert.areEqual(EndCap.FLAT, result.getLineEndingCapType());
        msAssert.areEqual(0.0, result.getWidthInEmus());
        msAssert.areEqual(DashStyle.SOLID, ((DmlPresetDash)result.getDash()).getPreset());
        msAssert.areEqual(DmlFillType.NO_FILL, result.getFill().getDmlFillType());
        msAssert.areEqual(new DmlHeadLineEndStyle(), result.getHeadLineEndStyle());
        msAssert.areEqual(new DmlTailLineEndStyle(), result.getTailLineEndStyle());
    }

    @Test
    public void build_Attributes_PropertiesAreInitialized() throws Exception
    {
        // Arrange
        String xml = "<ln algn=\"in\" cap=\"flat\" cmpd=\"tri\" w=\"12345\">" +
                     "</ln>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlOutline result = DmlOutlineReader.read(reader);
        // Assert
        msAssert.isTrue(result.getStrokeAlignment());
        msAssert.areEqual(ShapeLineStyle.TRIPLE, result.getCompoundLineType());
        msAssert.areEqual(EndCap.FLAT, result.getLineEndingCapType());
        msAssert.areEqual(12345.0, result.getWidthInEmus());
    }

    @Test
    public void build_SolidFillSpecified_PropertyInitialized() throws Exception
    {
        // Arrange
        String xml = "<ln>" +
                        "<solidFill><schemeClr val=\"accent1\" /></solidFill>" +
                     "</ln>";
        checkFillProperty(xml, DmlFillType.SOLID_FILL);
    }

    @Test
    public void build_NoFillSpecified_PropertyInitialized() throws Exception
    {
        // Arrange
        String xml = "<ln>" +
                        "<noFill/>" +
                     "</ln>";
        checkFillProperty(xml, DmlFillType.NO_FILL);
    }

    @Test
    public void build_PatternFillSpecified_PropertyInitialized() throws Exception
    {
        // Arrange
        String xml = "<ln>" +
                        "<pattFill/>" +
                     "</ln>";
        checkFillProperty(xml, DmlFillType.PATTERN_FILL);
    }

    @Test
    public void build_GradFillSpecified_PropertyInitialized() throws Exception
    {
        // Arrange
        String xml = "<ln>" +
                        "<gradFill/>" +
                     "</ln>";
        checkFillProperty(xml, DmlFillType.GRADIENT_FILL);
    }


    @Test
    public void build_PresetDashSpecified_PropertyInitialized() throws Exception
    {
        // Arrange
        String xml = "<ln>" +
                        "<prstDash val=\"dot\"/>" +
                     "</ln>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlOutline result = DmlOutlineReader.read(reader);
        // Assert
        msAssert.areEqual(DashStyle.DOT, ((DmlPresetDash)result.getDash()).getPreset());
    }

    @Test
    public void build_CustomDashSpecified_PropertyInitialized() throws Exception
    {
        // Arrange
        String xml = "<ln>" +
                        "<custDash>" +
                            "<ds/>" +
                            "<ds d=\"100\" sp=\"10000\"/>" +
                        "</custDash>" +
                     "</ln>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlOutline result = DmlOutlineReader.read(reader);
        DmlCustomDash dash = (DmlCustomDash)result.getDash();
        // Assert
        msAssert.areEqual(2, dash.getDashStops().size());
        msAssert.areEqual(100.0, DmlPercentageUtil.toDmlPercent(dash.getDashStops().get(1).getDashLength()));
        msAssert.areEqual(10000.0, DmlPercentageUtil.toDmlPercent(dash.getDashStops().get(1).getSpaceLength()));
    }

    @Test
    public void build_TailEndSpecified_PropertyInitialized() throws Exception
    {
        // Arrange
        String xml = "<ln>" +
                        "<tailEnd len=\"med\" type=\"diamond\" w=\"lg\"/>" +
                     "</ln>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlOutline result = DmlOutlineReader.read(reader);
        // Assert
        msAssert.areEqual(ArrowLength.MEDIUM, result.getTailLineEndStyle().getLength());
        msAssert.areEqual(ArrowWidth.WIDE, result.getTailLineEndStyle().getWidth());
        msAssert.areEqual(ArrowType.DIAMOND, result.getTailLineEndStyle().getType());
    }

    @Test
    public void build_HeadEndSpecified_PropertyInitialized() throws Exception
    {
        // Arrange
        String xml = "<ln>" +
                        "<headEnd  len=\"sm\" type=\"arrow\" w=\"med\"/>" +
                     "</ln>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlOutline result = DmlOutlineReader.read(reader);
        // Assert
        msAssert.areEqual(ArrowLength.SHORT, result.getHeadLineEndStyle().getLength());
        msAssert.areEqual(ArrowWidth.MEDIUM, result.getHeadLineEndStyle().getWidth());
        msAssert.areEqual(ArrowType.OPEN, result.getHeadLineEndStyle().getType());
    }

    private void checkFillProperty(String xml, /*DmlFillType*/int expectedFillType) throws Exception
    {
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        DmlOutline result = DmlOutlineReader.read(reader);
        // Assert
        msAssert.isNotNull(result.getFill());
        msAssert.areEqual(expectedFillType, result.getFill().getDmlFillType());
    }
}
