// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/04/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ArrayUtil;


@Test
public class TestDmlBlip
{
    @Test
    public void deepClone()
    {
        DmlBlip blip = new DmlBlip();
        //Arrange
        byte[] imageBytes = new byte[] {(byte) 0, (byte) 1, (byte) 2};
        blip.setEmbedImage(imageBytes);
        blip.setImageLink("linked");
        blip.setCompressionState(DmlCompressionState.HIGH_QUALITY_PRINT);
        //Act
        DmlBlip result = blip.deepClone();
        //Assert
        msAssert.areEqual(imageBytes, result.getEmbedImage());
        msAssert.areEqual("linked", result.getImageLink());
        msAssert.areEqual(DmlCompressionState.HIGH_QUALITY_PRINT, result.getCompressionState());
    }

    @Test
    public void getImage_ImageNotDefined_NullReturned() throws Exception
    {
        DmlBlip blip = new DmlBlip();
        // Arrange
        // Act
        byte[] result = blip.getImageBytes();
        // Assert
        msAssert.isNull(result);
    }

    @Test
    public void getImage_EmbededImage_EmbededImageReturned() throws Exception
    {
        DmlBlip blip = new DmlBlip();
        // Arrange
        byte[] image = ArrayUtil.EMPTY_BYTE_ARRAY;
        // Act
        blip.setEmbedImage(image);
        byte[] result = blip.getImageBytes();
        // Assert
        msAssert.areEqual(image, result);
    }

    @Test
    public void getImage_LinkedImage_ImageReturned() throws Exception
    {
        DmlBlip blip = new DmlBlip();
        // Arrange
        Document doc = new Document();
        blip.setDocument(doc);
        blip.getDocument().setResourceLoadingCallback(new UserProvidedDataHandler());
        blip.setImageLink("rid1");
        byte[] image = new byte[1];
        image[0] = (byte) 192;
        // Act
        byte[] result = blip.getImageBytes();
        // Assert
        msAssert.areEqual(5, result.length); 
    }

    @Test
    public void getImage_EmbededAndLinkedImageDefined_EmbededImageReturned() throws Exception
    {
        DmlBlip blip = new DmlBlip();
        // Arrange
        byte[] image = new byte[1];
        image[0] = (byte) 192;
        // Act
        blip.setEmbedImage(image);
        byte[] result = blip.getImageBytes();
        // Assert
        msAssert.areEqual(192, result[0]);
    }


    @Test
    public void getImage_ExceptionOccuredDuringReading_NullReturned() throws Exception
    {
        DmlBlip blip = new DmlBlip();
        // Arrange
        // Act
        byte[] result = blip.getImageBytes();
        // Assert
        msAssert.isNull(result);
    }
}

