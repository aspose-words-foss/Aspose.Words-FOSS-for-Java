// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.StringUtil;


/// <summary>
/// Tests for <see cref="DmlShapeReader"/>.
/// </summary>
@Test
public class TestDmlPictureReader
{
    @Test
    public void build_BadXml_NullReturned() throws Exception
    {
        // Arrange
        String xml =
            "<a:Badpic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "</a:Badpic>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        Shape result = DmlPictureReader.read(reader);
        // Assert
        msAssert.isNull(result);
    }
    
    @Test
    public void build_StyleSpecified_PropertyInitialized() throws Exception
    {
        // Arrange
        String xml =
            "<a:pic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:style>" +
                "</a:style>" +
            "</a:pic>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        Shape result = DmlPictureReader.read(reader);
        // Assert
        msAssert.isNotNull(result);
        msAssert.isNotNull(result.getDmlNode());
        msAssert.isNotNull(ms.as(result.getDmlNode(), DmlPicture.class));
        msAssert.isNotNull((ms.as(result.getDmlNode(), DmlPicture.class)).getStyle());
    }

    @Test
    public void build_BlipFillSpecified_PropertyInitialized() throws Exception
    {
        // Arrange
        String xml =
            "<pic >" +
                "<blipFill>" +
                "</blipFill>" +
            "</pic>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        // Act
        Shape result = DmlPictureReader.read(reader);
        // Assert
        msAssert.isNotNull(result);
        msAssert.isNotNull(result.getDmlNode());

        DmlPicture dmlPicture = ms.as(result.getDmlNode(), DmlPicture.class);
        msAssert.isNotNull(dmlPicture);
        msAssert.isNotNull(dmlPicture.getBlipFill());
        msAssert.areEqual(DmlFillType.BLIP_FILL, dmlPicture.getBlipFill().getDmlFillType());
        msAssert.isNotNull(dmlPicture.getBlipFill().getBlip());
        msAssert.isNull(dmlPicture.getBlipFill().getBlip().getEmbedImage());
        msAssert.isFalse(StringUtil.hasChars(dmlPicture.getBlipFill().getBlip().getImageLink()));
    }
}

