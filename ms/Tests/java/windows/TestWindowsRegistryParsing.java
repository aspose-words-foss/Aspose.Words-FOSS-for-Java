// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.

package com.aspose.ms.java.windows;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * WindowsRegistry gets its values from the text that "reg query" prints. Runs everywhere: no registry is needed to parse text.
 */
@Test
public class TestWindowsRegistryParsing
{
    @Test
    public void testStringValuesOfTheKey()
    {
        String output = "\r\n" +
                "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Fonts\r\n" +
                "    Arial (TrueType)    REG_SZ    arial.ttf\r\n" +
                "    Myanmar Text Bold (TrueType)    REG_SZ    mmrtextb.ttf\r\n" +
                "    Installed elsewhere (TrueType)    REG_SZ    C:\\Users\\Some User\\Fonts\\My Font.ttf\r\n" +
                "    Expandable    REG_EXPAND_SZ    %SystemRoot%\\Fonts\\x.ttf\r\n" +
                "    Empty    REG_SZ    \r\n" +
                "    EmptyWithoutSeparator    REG_SZ\r\n" +
                "    A number    REG_DWORD    0x1\r\n" +
                "    Bytes    REG_BINARY    0102\r\n" +
                "\r\n" +
                "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Fonts\\Subkey\r\n";

        Map<String, String> values = WindowsRegistry.parse(output);

        Assert.assertEquals(values.size(), 6);
        Assert.assertEquals(values.get("Arial (TrueType)"), "arial.ttf");
        Assert.assertEquals(values.get("Myanmar Text Bold (TrueType)"), "mmrtextb.ttf");
        Assert.assertEquals(values.get("Installed elsewhere (TrueType)"), "C:\\Users\\Some User\\Fonts\\My Font.ttf");
        Assert.assertEquals(values.get("Expandable"), "%SystemRoot%\\Fonts\\x.ttf");
        Assert.assertEquals(values.get("Empty"), "");
        Assert.assertEquals(values.get("EmptyWithoutSeparator"), "");
    }

    @Test
    public void testNothingToRead()
    {
        Assert.assertTrue(WindowsRegistry.parse("").isEmpty());
        Assert.assertTrue(WindowsRegistry.parse("ERROR: The system was unable to find the specified registry key or value.\r\n").isEmpty());
    }

    /**
     * Whatever goes wrong is logged and gives no values; on other systems the root is not even looked at.
     */
    @Test
    public void testUnknownRoot()
    {
        Assert.assertTrue(new WindowsRegistry().readRegistryStringValues(0x12345, "SOFTWARE").isEmpty());
    }
}
