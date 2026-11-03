// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.

package com.aspose;

import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.words.Document;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Locale;

/**
 * Test Java-specific CultureInfo features.
 */
@Test(groups = SingleThreadTestGroup.SINGLE_THREAD)
public class TestCultureInfoJavaOnly
{

    @BeforeClass
    public void setUp()
    {
        SystemPal.saveCulture();
        CurrentThread.clearAll();
    }

    @AfterClass
    public void tearDown()
    {
        SystemPal.restoreCulture();
    }

    /**
     * WORDSJAVA-1554 (fixed) - AW for Java throws while saving to pdf when default locale
     * is initialized with language only.
     */
    @Test
    public void testAbsentCountry()
    {
        Locale.setDefault(new Locale("de"));
        msCultureInfo cultureInfo = msCultureInfo.getCurrentCulture();

        msAssert.assertTrue(cultureInfo != null);
        msAssert.areEqual("de", cultureInfo.getLocale().getLanguage());
        msAssert.areEqual("", cultureInfo.getLocale().getCountry());
    }

    /**
     * WORDSJAVA-1571 (fixed) - AW for Java throws while saving to pdf when default locale
     * is initialized with language only.
     */
    @Test
    public void testJiraJ1571() throws Exception
    {
        Locale.setDefault(new Locale("en"));
        Document doc = new Document();
    }

    /**
     * WORDSJAVA-2830 (fixed) - CultureNotFoundException is thrown when try to set Indonesian culture.
     */
    @Test
    public void testJava2830()
    {
        String userLocale = "in_ID"; // Same with id_ID
        String[] locValue = userLocale.split("_");
        Locale loc = new Locale(locValue[0], locValue[1]);
        com.aspose.words.CurrentThreadSettings.setLocale(loc);
    }
}
