// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestPresetGeometryXmlRepository
{
    @Test
    public void getPresetGeometryXml_NotExistingPreset_NullReturned()
    {
        // Act
        String result = G_REPOSITORY.getPresetGeometryXml("abra-kadabra");
        // Assert
        msAssert.isNull(result);
    }

    @Test
    public void getPresetGeometryXml_FirstPreset_XmlReturned()
    {
        // Act
        String result = G_REPOSITORY.getPresetGeometryXml("accentBorderCallout1");
        // Assert
        msAssert.isTrue(result.startsWith("<accentBorderCallout1>"));
        msAssert.isTrue(result.endsWith("</accentBorderCallout1>"));
    }

    @Test
    public void getPresetGeometryXml_LastPreset_XmlReturned()
    {
        // Act
        String result = G_REPOSITORY.getPresetGeometryXml("wedgeRoundRectCallout");
        // Assert
        msAssert.isTrue(result.startsWith("<wedgeRoundRectCallout>"));
        msAssert.isTrue(result.endsWith("</wedgeRoundRectCallout>"));
    }

    private static /*final*/ PresetGeometryXmlRepository G_REPOSITORY = new PresetGeometryXmlRepository();
}
