// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
package com.aspose;

import com.aspose.ms.System.msString;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;

public class HttpClientPal
{
    // ################################# HTTP ################################# //

    public static int doPost(String url, StringToStringDictionary params) throws Exception
    {
        if (params == null || params.getCount() == 0)
            throw new IllegalArgumentException("Params cannot be empty");

        StringBuilder postPayload = new StringBuilder();
        StringToStringDictionary.Enumerator it = params.iterator();
        boolean first = true;

        while (it.moveNext())
        {
            String key = it.getCurrentKey();
            String value = it.getCurrentValue();

            if (value == null)
                continue;

            if (msString.isNullOrEmpty(value))
                continue;

            if (!first)
                postPayload.append(AND);

            postPayload.append(makePair(key, value));
            first = false;
        }

        return doPost(url, postPayload.toString());

    }

    private static int doPost(String url, String postEncodedParams) throws Exception
    {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(gRequestMethod);
        con.setRequestProperty(gUserAgentKey, gUserAgentValue);
        con.setRequestProperty(gAcceptLanguageKey, gAcceptLanguageValue);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postEncodedParams);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        if (responseCode != RESPONSE_OK)
        {
            InputStream inputStream = null;
            try
            {
                inputStream = con.getErrorStream();
                String response = readResponse(inputStream);
                throw new Exception(response);
            }
            finally
            {
                if (inputStream != null)
                    inputStream.close();
            }
        }

        return 0;
    }

    private static String readResponse(InputStream inputStream) throws IOException
    {
        if (inputStream == null)
            return null;

        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(isr);

        int c;
        StringBuilder data = new StringBuilder();
        while (-1 != (c = reader.read()))
            data.append((char) c);

        return data.toString();
    }

    // ################################# QUERY ################################# //

    private static final String makePair(String key, String value)
    {
        if (msString.isNullOrEmpty(key) || value == null)
            return msString.Empty;

        try
        {
            return gKeyValFormat.format(new Object[]{URLEncoder.encode(key, gEncoding),
                    URLEncoder.encode(value.toString(), gEncoding),});
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return msString.Empty;
    }

    private static final String AND = "&";
    private static final String gEncoding = "UTF-8";
    private static final MessageFormat gKeyValFormat = new MessageFormat("{0}={1}");
    private static final String gUserAgentValue =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
    public static final String gRequestMethod = "POST";
    public static final String gUserAgentKey = "User-Agent";
    public static final String gAcceptLanguageKey = "Accept-Language";
    public static final String gAcceptLanguageValue = "en-NZ,en;q=0.5";
    public static final int RESPONSE_OK = 200;
}
