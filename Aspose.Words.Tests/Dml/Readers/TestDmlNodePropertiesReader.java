// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.StringUtil;


/// <summary>
/// Tests for <see cref="DmlShapeReader"/>.
/// </summary>
@Test
public class TestDmlNodePropertiesReader
{
    @Test
    public void readVisualGroupShapeProperties_ExtentDefined_ExtentHasCorrectValues() throws Exception
    {
        // Arrange
        String xml =
            "<a:grpSpPr bwMode=\"invGray\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:xfrm>" +
                    "<a:ext cx=\"1676400\" cy=\"1095375\" />" +
                "</a:xfrm>" +
            "</a:grpSpPr>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        DmlGroupShape groupShape = new DmlGroupShape(DmlNodeType.GROUP_SHAPE);
        // Act
        DmlNodePropertiesReader.readVisualGroupShapeProperties(reader, groupShape);
        // Assert
        msAssert.areEqual(1676400f, msSizeF.getWidth(groupShape.getTransform().getExtents()));
        msAssert.areEqual(1095375f, msSizeF.getHeight(groupShape.getTransform().getExtents()));
        msAssert.areEqual(BWMode.INVERSE_GRAY, groupShape.getBWMode());
    }

    @Test
    public void readShapeProperties_CustomGeometry_GeometrySetted() throws Exception
    {
        String xml =
            "<a:spPr xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:custGeom>" +
                "</a:custGeom>" +
            "</a:spPr>";
        buildAndCheckGeometry(xml);
    }

    @Test
    public void readShapeProperties_OutlineSpecified_PropertyInitialized() throws Exception
    {
        // Arrange
        String xml =
            "<a:spPr xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:ln/>" +
            "</a:spPr>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        DmlShape shape = new DmlShape(DmlNodeType.SHAPE);
        // Act
        DmlNodePropertiesReader.readShapeProperties(reader, shape);
        // Assert
        msAssert.isNotNull(shape.getOutline());
    }

    @Test
    public void readShapeProperties_PresetGeometry_GeometrySetted() throws Exception
    {
        String xml =
            "<a:spPr xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:prstGeom>" +
                "</a:prstGeom>" +
            "</a:spPr>";
        buildAndCheckGeometry(xml);
    }

    @Test
    public void readShapeProperties_XfrmDefined_TrasformPropertiesSetted() throws Exception
    {
        // Arrange
        String xml =
            "<a:spPr xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:xfrm>" +
                    "<a:ext cx=\"12\" cy=\"32\"/>" +
                    "<a:off x=\"54\" y=\"72\"/>" +
                "</a:xfrm>" +
            "</a:spPr>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        DmlShape shape = new DmlShape(DmlNodeType.SHAPE);
        // Act
        DmlNodePropertiesReader.readShapeProperties(reader, shape);
        // Assert
        DmlTransform transform = shape.getTransform();
        msAssert.areEqual(12, transform.getWidth());
        msAssert.areEqual(32, transform.getHeight());
        msAssert.areEqual(54, transform.getX());
        msAssert.areEqual(72, transform.getY());
    }

    private void buildAndCheckGeometry(String xml) throws Exception
    {
        // Arrange
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        DmlShape shape = new DmlShape(DmlNodeType.SHAPE);
        // Act
        DmlNodePropertiesReader.readShapeProperties(reader, shape);
        // Assert
        msAssert.isNotNull(shape.getGeometry());
        msAssert.areEqual(0, shape.getGeometry().getGuides().getAdjustableValues().size());
        msAssert.areEqual(0, shape.getGeometry().getGuides().getGuides().size());
        msAssert.areEqual(0, shape.getGeometry().getPaths().size());
        msAssert.isFalse(StringUtil.hasChars(shape.getGeometry().getPresetName()));
    }

    /// <summary>
    /// WORDSNET-11899 DmlNodePropertiesReader.ReadHLinkClick hanged if there was an unknown 
    /// attribute in the hlinkClick element. 
    /// WarnUnexcepted should be used instead of WarnUnexceptedAndIgnoreElement in attribute cycle.
    /// </summary>
    @Test
    public void readNonVisualDrawingProperties_UnknownHLinkClickAttribute() throws Exception
    {
        // Checks that reader does not hang
        // Arrange
        String xml =
            "<pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/main\" " +
                "xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" " +
                "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" >" +
                "<pic:nvPicPr>" +
                    "<pic:cNvPr id=\"1026\" name=\"Picture 2\">" +
                        "<a:hlinkClick r:id=\"rId19\" action1=\"ppaction://hlinkfile\"/>" + // unknown attribute action1 
                    "</pic:cNvPr>" +
                "</pic:nvPicPr>" +
            "</pic:pic>";
        TestMaxWarningCountCallback warnCounter = new TestMaxWarningCountCallback(1);
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml, warnCounter);
        reader.getXmlReader().readChild("nvPicPr");
        reader.getXmlReader().readChild("cNvPr");
        // Act
        DmlNodePropertiesReader.readNonVisualDrawingProperties(reader);
        // Assert
        msAssert.areEqual(1, warnCounter.getCount(), "Number of unknown nodes");
    }

}

