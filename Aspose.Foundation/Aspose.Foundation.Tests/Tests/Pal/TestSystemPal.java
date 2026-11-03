// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/11/2018 by Roman Pankin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.net.HttpURLConnection;
import com.aspose.ms.System.msConsole;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestSystemPal
{
    /// <summary>
    /// WORDSJAVA-1936 - Image not visible in exported HTML
    /// The document in the jira task contains an image and broken URL (unavailable now) from which this image was loaded.
    /// Aspose.Words is trying to download an image from the Internet before and only if it's not available,
    /// it uses the image from the document's resources. To complete all steps properly, an exception should be raised
    /// if a resource can't be read from the Internet. The URL from the document is "http://img2.7wenta.com/upload/wti/20140528/14012099014997642.png"
    /// 
    /// The link below is unavailable. Both c# and Java have to raise an exception in this case.
    /// </summary>
    @Test
    public void testHttpRequest() throws Exception
    {
        try
        {
            SystemPal.openStreamFromHref("http://aaa.bbb/ccc.png");
        }
        catch(Exception e)
        {
            return;
        }

        throw new Exception("Any exception is expected");
    }

    /// <summary>
    /// WORDSJAVA-2811 Images is lost after rendering the document.
    /// In Java Server returned HTTP response code: 403 for URL:
    /// https://www.sec.gov/Archives/edgar/data/1630970/000114420416130880/pg2img1_ex10-13.jpg
    /// It means that server discard request because of forbidden UserAgent string or problem with request.
    ///
    /// Both c# and Java have to use right request and get images.
    /// </summary>
    @Test
    public void testJavaRequest() throws Exception
    {
        String url = "https://www.example.com/example.jpg";
        HttpURLConnection request = (HttpURLConnection)WebRequestHelper.createRequestFromUri(url);
        String requestString = WebRequestHelper.getRequestAsString(request);

        String expectedRequest = "GET https://www.example.com/example.jpg\r\n" +
            "User-Agent:Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)Accept:*/*Accept-Language:*Accept-Encoding:GZip, Deflate";

        System.out.println(requestString);
        msAssert.areEqual(expectedRequest, requestString);
    }
}

