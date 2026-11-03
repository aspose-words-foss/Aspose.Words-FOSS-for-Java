// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/10/2024 by Anatoly Sidorenko

package com.aspose;

import com.aspose.ms.System.Environment;
import com.aspose.ms.System.msUri;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/// <summary>
/// Contains helper methods for creating web requests.
/// </summary>
/// <remarks>
/// Limits rate of unsuccessful requests.
/// </remarks>
//[JavaManual("Platform abstraction for system utilities. Manual porting by design.")]
/*static*/ class WebRequestHelper
{
	/* Simulation of static class by using private constructor */
	private WebRequestHelper()
	{}

    public static URLConnection createRequestFromUri(String uri) throws Exception
    {
        // WORDSNET-12737 Newline characters inside URLs prevent HTML import from loading resources.
        // This handles http://, file:// and probably ftp://.
        uri = UriUtil.escapeHref(uri);
        URL url = new msUri(uri).toJavaUrl();
        return createRequestFromUrl(url);
    }

    public static URLConnection createRequestFromUrl(URL url) throws IOException
    {
        URLConnection conn = url.openConnection();

        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)");
        // WORDSNET-23888 WORDSJAVA-2811 We've seen a HTTP server that refused to respond unless the following HTTP headers
        // were specified.
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Accept-Language", "*");
        conn.setRequestProperty("Accept-Encoding", "GZip, Deflate");

        return conn;
    }

    public static String getRequestAsString(HttpURLConnection request)
    {
        String str = request.getRequestMethod() + " " + request.getURL() + Environment.getNewLine();

        str = str + "User-Agent:" + request.getRequestProperty("User-Agent");
        str = str + "Accept:" + request.getRequestProperty("Accept");
        str = str + "Accept-Language:" + request.getRequestProperty("Accept-Language");
        str = str + "Accept-Encoding:" + request.getRequestProperty("Accept-Encoding");

        return str;
    }
}

