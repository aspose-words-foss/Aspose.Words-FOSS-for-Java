// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/05/2017 by Konstantin Sidorenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.Language;
import org.testng.Assert;
import com.aspose.LocaleClassifier;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


@Test
public class TestLanguage
{
    @Test
    public void testEnumIsDefined()
    {
        /*Language*/int[] values = (/*Language*/int[])Language.getValues();
        for (/*Language*/int language : values)
        {
            msAssert.isTrue(LocaleClassifier.isDefined(language), msString.format("Language enum {0} is not defined. Please rerun TestLanguage.PrintSwitchBody() " +
                                                                    "and insert the new body into LocaleClassifier.IsDefined()", language));
        }
    }
}

