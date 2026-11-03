package com.aspose;

import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.MemoryStream;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;

/**
 * The library ships no CMYK profile. Where the platform has none either (a typical Linux server), a CMYK JPEG without
 * a profile of its own still has to be read; the colors are then computed without color management.
 *
 * The platform profile is process-wide state, so the class does not run in parallel with other tests.
 */
@Test(groups = SingleThreadTestGroup.SINGLE_THREAD)
public class TestCmykWithoutProfile
{
    @Test(dataProvider = "testReadDataProvider")
    public void testRead(String fileName) throws Exception
    {
        byte[] imageBytes = File.readAllBytes(TestFxUtil.buildTestFileName(fileName));
        Assert.assertTrue(ImageUtil.isCmykOrYCCK(imageBytes));

        ICC_Profile platformProfile = CmykIccProfile.getInstance();
        CmykIccProfile.setInstance(null);
        try
        {
            BufferedImage image = new BitmapPal(new MemoryStream(imageBytes)).getNativeBitmap();

            Assert.assertNotNull(image);
            Assert.assertTrue(image.getWidth() > 10);
            Assert.assertTrue(image.getHeight() > 10);
            Assert.assertTrue(hasDifferentColors(image), "A picture is expected, not a filled rectangle.");
        }
        finally
        {
            CmykIccProfile.setInstance(platformProfile);
        }
    }

    @DataProvider(name = "testReadDataProvider")
    public static Object[][] testReadDataProvider()
    {
        return new Object[][]
        {
            {"Images\\CMYK\\TestCmykImage.jpeg"},
            {"Images\\CMYK\\TestCmykImagePhotoshop.jpeg"},
            {"Images\\CMYK\\TestCMYKImages_0.jpeg"},
            {"Images\\CMYK\\Test420CMYKImage_0.jpeg"},
        };
    }

    private static boolean hasDifferentColors(BufferedImage image)
    {
        int first = image.getRGB(0, 0);
        for (int y = 0; y < image.getHeight(); y += 4)
            for (int x = 0; x < image.getWidth(); x += 4)
                if (image.getRGB(x, y) != first)
                    return true;

        return false;
    }
}
