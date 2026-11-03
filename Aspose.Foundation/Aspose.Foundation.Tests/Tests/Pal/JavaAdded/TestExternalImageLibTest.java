package com.aspose;

import com.aspose.ms.System.IO.File;
import com.aspose.words.SaveFormat;
import com.aspose.words.utils.ExternalImageLibTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/***
 * This class describes how to check whether the optional image libraries are installed or not.
 * Also it shows how to check if Aspose.Words for Java can read/write the given image.
 */
@Test
public class TestExternalImageLibTest
{
    /**
     * In order to check if Aspose.Words for Java can find the optional image libraries on its classpath users could use this method.
     * The warning will be printed out in case if a library is missed.
     */
    @Test
    public void testIfInstalled()
    {
        ExternalImageLibTest.checkLibraries();
        // expect the detailed instruction if a library is not installed
    }

    /**
     * In order to check if some image can be read by Aspose.Words for Java users can use read() method.
     * Users will see any errors or warnings if they occur.
     *
     * @throws Exception
     */
    @Test
    public void testCanRead() throws Exception
    {
        byte[] imageBytes = File.readAllBytes(TestFxUtil.buildTestFileName(TestFxUtil.buildTestFileName("Rendering\\Aps\\TestImages\\TestImage Format16bppArgb1555.tif")));
        BufferedImage result = ExternalImageLibTest.read(imageBytes);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getWidth() > 0);
        Assert.assertTrue(result.getHeight() > 0);
    }

    /**
     * In order to check if some image can be written by Aspose.Words for Java users can use write() method.
     * Users will see any errors or warnings if they occur.
     *
     * @throws Exception
     */
    @Test
    public void testCanWrite() throws Exception
    {
        BufferedImage image = ImageIO.read(new java.io.File(TestFxUtil.buildTestFileName(TestFxUtil.buildTestFileName("Rendering\\Aps\\TestImages\\TestImage Format16bppArgb1555.tif"))));
        byte[] result = ExternalImageLibTest.write(image, SaveFormat.TIFF);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.length > 0);
    }
}
