package com.aspose.ms.System.Globalization;

import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.Test;

public class TestCultureDataLoader
{

    @Test
    public void testLoadCultureFromXml()
    {
        CultureData culture = new CultureData("ru-RU");
        CultureDataLoader.loadCultureData(culture);
        msAssert.areEqual(1049, culture.getLanguage());
        msAssert.areEqual(1, culture.getDefaultCalendar().getID());
        msAssert.areEqual("ru-RU", culture.getCultureName());
        msAssert.areEqual("Russian (Russia)", culture.getEngDisplayName());
        msAssert.areEqual("RUS", culture.getAbbrevLangName());
        msAssert.areEqual("ru", culture.getISO639LangName());
        msAssert.areEqual("rus", culture.getISO639LangName2());
        msAssert.areEqual("Russian (Russia)", culture.getLocalizedDisplayName());
        msAssert.areEqual("русский (Россия)", culture.getNativeDisplayName());
        msAssert.areEqual("ru-RU", culture.getName());
        msAssert.areEqual("ru-RU", culture.getNonSortName());

    }

    @Test
    public void testCultureDataCached()
    {
        CultureData data = CultureData.getInvariant();
        msAssert.assertTrue(data == CultureData.getInvariant());
        msAssert.assertTrue(data == CultureData.getInvariant());
        msAssert.assertTrue(data == CultureData.getInvariant());

        data = CultureData.getCultureData("ru-RU", false);
        msAssert.assertTrue(data == CultureData.getCultureData("ru-RU", false));
        msAssert.assertTrue(data == CultureData.getCultureData("ru-RU", false));
        msAssert.assertTrue(data == CultureData.getCultureData("ru-RU", false));


        data = CultureData.getCultureData(1033, false);
        msAssert.assertTrue(data == CultureData.getCultureData(1033, false));
        msAssert.assertTrue(data == CultureData.getCultureData(1033, false));
        msAssert.assertTrue(data == CultureData.getCultureData(1033, false));
    }

}
