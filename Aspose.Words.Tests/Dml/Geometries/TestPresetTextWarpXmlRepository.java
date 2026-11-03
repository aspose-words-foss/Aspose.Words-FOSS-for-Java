// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Dmitry Bormashov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestPresetTextWarpXmlRepository
{
    @Test
    public void getPresetTextWarpXml_NotExistingPreset_NullReturned()
    {
        // Act
        String result = G_REPOSITORY.getPresetTextWarpXml("abra-kadabra");
        // Assert
        msAssert.isNull(result);
    }

    @Test
    public void getPresetTextWarpXml_FirstPreset_XmlReturned()
    {
        // Act
        String result = G_REPOSITORY.getPresetTextWarpXml("textArchDown");
        // Assert
        msAssert.isTrue(result.startsWith("<textArchDown>"));
        msAssert.isTrue(result.endsWith("</textArchDown>"));
    }

    @Test
    public void getPresetTextWarpXml_LastPreset_XmlReturned()
    {
        // Act
        String result = G_REPOSITORY.getPresetTextWarpXml("textWave4");
        // Assert
        msAssert.isTrue(result.startsWith("<textWave4>"));
        msAssert.isTrue(result.endsWith("</textWave4>"));
    }

    private static /*final*/ PresetTextWarpXmlRepository G_REPOSITORY = new PresetTextWarpXmlRepository();
}
