// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/07/2021 by Mikhail Nepreteamov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Class for testing locale and language issues.
/// </summary>
@Test
public class TestLocale
{
    /// <summary>
    /// WORDSNET-22405 LocaleConverter doesn't have mapping for Language.Breton and Language.Alsatian locales.
    /// Fixed by adding the mapping for Language.Breton and Language.Alsatian locales.
    /// </summary>
    @Test
    public void test22405()
    {
        // For DOCX dictionary.
        msAssert.areEqual(Language.ALSATIAN, (/*Language*/int)LocaleConverter.docxTagToLocale("gsw-FR"));
        msAssert.areEqual(Language.BRETON, (/*Language*/int)LocaleConverter.docxTagToLocale("br-FR"));

        msAssert.areEqual("gsw-FR", LocaleConverter.localeToDocxTag((int)Language.ALSATIAN));
        msAssert.areEqual("br-FR", LocaleConverter.localeToDocxTag((int)Language.BRETON));

        // For other dictionaries.
        msAssert.areEqual(Language.ALSATIAN, (/*Language*/int)LocaleConverter.wmlTagToLocale("GSW-FR"));
        msAssert.areEqual(Language.BRETON, (/*Language*/int)LocaleConverter.wmlTagToLocale("BR-FR"));

        msAssert.areEqual("GSW-FR", LocaleConverter.localeToWmlTag((int)Language.ALSATIAN));
        msAssert.areEqual("BR-FR", LocaleConverter.localeToWmlTag((int)Language.BRETON));
    }

    /// <summary>
    /// Related with WORDSNET-22434
    /// Checks that we also support old tags for backward compatibility.
    /// </summary>
    @Test
    public void test22434()
    {
        // For DOCX dictionary.
        msAssert.areEqual(Language.RUSSIAN_MOLDOVA, (/*Language*/int)LocaleConverter.docxTagToLocale("ru-MO"));
        msAssert.areEqual(Language.RUSSIAN_MOLDOVA, (/*Language*/int)LocaleConverter.docxTagToLocale("ru-MD"));
        msAssert.areEqual(Language.ROMANIAN_MOLDOVA, (/*Language*/int)LocaleConverter.docxTagToLocale("ro-MO"));
        msAssert.areEqual(Language.ROMANIAN_MOLDOVA, (/*Language*/int)LocaleConverter.docxTagToLocale("ro-MD"));

        // For other dictionaries.
        msAssert.areEqual(Language.RUSSIAN_MOLDOVA, (/*Language*/int)LocaleConverter.wmlTagToLocale("ru-MO"));
        msAssert.areEqual(Language.RUSSIAN_MOLDOVA, (/*Language*/int)LocaleConverter.wmlTagToLocale("ru-MD"));
        msAssert.areEqual(Language.ROMANIAN_MOLDOVA, (/*Language*/int)LocaleConverter.wmlTagToLocale("ro-MO"));
        msAssert.areEqual(Language.ROMANIAN_MOLDOVA, (/*Language*/int)LocaleConverter.wmlTagToLocale("ro-MD"));
    }
}

