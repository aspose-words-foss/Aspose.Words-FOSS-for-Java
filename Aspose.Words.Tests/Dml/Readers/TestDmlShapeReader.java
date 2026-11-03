// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests for <see cref="DmlShapeReader"/>.
/// </summary>
@Test
public class TestDmlShapeReader
{
    @Test
    public void build_BadXml_NullReturned() throws Exception
    {
        // Arrange
        String xml =
            "<a:Badsp xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "</a:Badsp>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        Shape result = DmlShapeReader.read(reader);
        // Assert
        msAssert.isNull(result);
    }
    
    @Test
    public void build_StyleSpecified_PropertyInitialized() throws Exception
    {
        // Arrange
        String xml =
            "<a:sp xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:style>" +
                "</a:style>" +
            "</a:sp>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        Shape result = DmlShapeReader.read(reader);
        // Assert
        msAssert.isNotNull(result);
        msAssert.isNotNull(result.getDmlNode());
        msAssert.isNotNull(ms.as(result.getDmlNode(), DmlShape.class));
        msAssert.isNotNull((ms.as(result.getDmlNode(), DmlShape.class)).getStyle());
    }
}
