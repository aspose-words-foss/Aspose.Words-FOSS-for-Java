package com.aspose;

import com.aspose.ms.java.windows.WindowsRegistry;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * The registry is read with reg.exe, so these tests have something to check on Windows only.
 */
@Test
public class TestWindowsRegistry
{
    @BeforeMethod
    public void windowsOnly()
    {
        if (!PlatformUtilPal.isWindows())
            throw new SkipException("Windows only.");
    }

    @Test
    public void testReadWindowsRegistry()
    {
        WindowsRegistry instance = new WindowsRegistry();
        Map<String, String> values = instance.readRegistryStringValues(WindowsRegistry.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Fonts");
        Assert.assertNotNull(values);
        Assert.assertFalse(values.isEmpty());
        Assert.assertNotNull(values.get("Arial (TrueType)"));
    }

    @Test
    public void testKeyThatDoesNotExist()
    {
        Map<String, String> values = new WindowsRegistry().readRegistryStringValues(WindowsRegistry.HKEY_CURRENT_USER, "SOFTWARE\\Aspose\\No such key");
        Assert.assertNotNull(values);
        Assert.assertTrue(values.isEmpty());
    }

    /**
     * The defaults of a new section depend on it, see SectPr.updateDefaultsByLcid.
     */
    @Test
    public void testSystemDefaultLcid()
    {
        Assert.assertTrue(SystemPal.getSystemDefaultLcid() > 0);
    }
}
