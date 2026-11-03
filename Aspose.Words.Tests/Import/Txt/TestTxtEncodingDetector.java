package com.aspose.words;

import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;
import org.testng.annotations.Test;

import java.nio.charset.Charset;

@Test
public class TestTxtEncodingDetector extends TestGoldTxtBase
{

    @Test
    public void testEncodingDetectorContradictory() throws Exception
    {
        testEncodingDetector("ImportTxt\\EncodingDetector\\utf8.txt", Encoding.getWINDOWS_1252());

    }

    private void testEncodingDetector(String file, Encoding encoding) throws Exception
    {
        String fullPath = TestUtil.buildTestFileName(file);
        FileFormatDetector detector = new FileFormatDetector();

        FileStream fs = new FileStream(fullPath, FileMode.OPEN);
        FileFormatInfo fileFormatInfo = detector.detect(fs);

        fs.setPosition(0);
        msAssert.areEqual(encoding.getWebName(), fileFormatInfo.getEncodingInternal().getWebName());
        msAssert.areEqual(encoding.getCodePage(), fileFormatInfo.getEncodingInternal().getCodePage());
    }
}
