package com.aspose.ms.java.nativeutils;

import com.aspose.SingleThreadTestGroup;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.words.NativeLibSettings;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

// Works with process-wide state (system properties), so it does not run in parallel with other tests.
@Test(groups = SingleThreadTestGroup.SINGLE_THREAD)
public class TestNativeLibSettings
{
    @Test
    public void testDefaultTmpDirPath()
    {
        msAssert.areEqual(System.getProperty("java.io.tmpdir"), NativeLibSettings.getTmpDirectoryPath());
    }

    @Test
    public void testCustomTmpDirPath()
    {
        String defaultPath = System.getProperty("java.io.tmpdir");
        String customPath = defaultPath + "customPath";

        NativeLibSettings.setTmpDirectoryPath(customPath);
        msAssert.areEqual(customPath, NativeLibSettings.getTmpDirectoryPath());

        //Remove property with custom path
        System.clearProperty("com.aspose.tmpdir");

        msAssert.areEqual(defaultPath, NativeLibSettings.getTmpDirectoryPath());
    }

    @Test (dataProvider = "testInvalidCustomPathProvider", expectedExceptions = IllegalArgumentException.class)
    public void testInvalidCustomPath(String invalidPath)
    {
        NativeLibSettings.setTmpDirectoryPath(invalidPath);
    }

    @DataProvider(name = "testInvalidCustomPathProvider")
    public static Object[][] testInvalidCustomPathProvider()
    {
        return new Object[][]
        {
            {null},
            {""},
            {"qwerty{af/df"}
        };
    }

}
