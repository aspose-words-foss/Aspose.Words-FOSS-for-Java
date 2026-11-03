// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests for <see cref="DmlCompositeNodeReader"/>.
/// </summary>
@Test
public class TestDmlCompositeNodeReader
{
    @Test
    public void build_BadXml_NullReturned() throws Exception
    {
        // Arrange
        String xml =
            "<lc:badLockedCanvas xmlns:lc=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">" +
            "</lc:badLockedCanvas>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        ShapeBase result = DmlCompositeNodeReader.readLockedCanvas(reader);
        // Assert
        msAssert.isNull(result);
    }



    @Test
    public void build_ShapeDefined_ShapeCreated() throws Exception
    {
        String xml =
            "<lc:lockedCanvas xmlns:lc=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">" +
            "<a:sp xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>" +
            "</lc:lockedCanvas>";
        buildAndCheckThatShapeCreated(xml);
    }

    @Test
    public void build_GroupShapeDefined_ShapeCreated() throws Exception
    {
        String xml =
            "<lc:lockedCanvas xmlns:lc=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">" +
                "<a:grpSp xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>" +
            "</lc:lockedCanvas>";
        buildAndCheckThatShapeCreated(xml);
    }

    @Test
    public void build_GroupTreeDefined_NestedShapesCreated() throws Exception
    {
        // Arrange
        String xml =
            "<lc:lockedCanvas xmlns:lc=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">" +
                "<a:grpSp xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                    "<a:grpSp >" +
                        "<a:grpSp />" +
                    "</a:grpSp>" +
                "</a:grpSp>" +
            "</lc:lockedCanvas>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        ShapeBase drawingML;
        // Act
        drawingML = DmlCompositeNodeReader.readLockedCanvas(reader);
        // Assert
        msAssert.areEqual(1, drawingML.getChildNodes(NodeType.ANY, false).getCount());
        GroupShape groupDml = (GroupShape)drawingML.getChildNodes(NodeType.ANY, false).get(0);
        msAssert.areEqual(1, groupDml.getChildNodes(NodeType.ANY, false).getCount());
        GroupShape nestedGroupDml = (GroupShape)groupDml.getChildNodes(NodeType.ANY, false).get(0);
        msAssert.areEqual(1, nestedGroupDml.getChildNodes(NodeType.ANY, false).getCount());
    }


    @Test
    public void build_CxnShapeDefined_ShapeCreated() throws Exception
    {
        String xml =
            "<lc:lockedCanvas xmlns:lc=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">" +
            "<a:cxnSp xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>" +
            "</lc:lockedCanvas>";
        buildAndCheckThatShapeCreated(xml);
    }

    @Test
    public void build_PictureDefined_ShapeCreated() throws Exception
    {
        String xml =
            "<lc:lockedCanvas xmlns:lc=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">" +
                "<a:pic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>" +
            "</lc:lockedCanvas>";
        DmlNode node = buildAndCheckThatShapeCreated(xml);
        msAssert.isTrue(node instanceof DmlLockedCanvas);
    }

    private DmlNode buildAndCheckThatShapeCreated(String xml) throws Exception
    {
        Shape drawingML = new Shape(new Document(), ShapeMarkupLanguage.DML);
        // Arrange
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        reader.addAndPushContainer(drawingML);
        // Act
        DmlCompositeNodeReader.readLockedCanvas(reader);
        // Assert
        msAssert.areEqual(1, drawingML.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.isNotNull(drawingML.getChildNodes(NodeType.ANY, false).get(0));
        return ((GroupShape)drawingML.getChildNodes(NodeType.ANY, false).get(0)).getDmlNode();
    }

    @Test
    public void build_TagIsEmpty_ResultIsInitializedWithDefaultValues() throws Exception
    {
        // Arrange
        String xml =
            "<lc:lockedCanvas xmlns:lc=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">" +
            "</lc:lockedCanvas>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        ShapeBase result = DmlCompositeNodeReader.readLockedCanvas(reader);
        // Assert
        msAssert.isNotNull(result.getDmlNode());
        msAssert.isNotNull(result.getDmlNode().getTransform());
        msAssert.areEqual(0, result.getChildNodes(NodeType.ANY, false).getCount());
    }
}
