// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2011 by Konstantin Sidorenko
// 2016/01/22 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msUri;


@Test
public class TestUri
{
    @Test
    public void testSlashes() throws Exception
    {
        // .Net normalizes forward slash '/' to backward one, java doesn't.
        String expected =
            "C:/Test.docx";
        msAssert.areEqual(new msUri("C:/Test.docx").getLocalPath(), expected);
        msAssert.areEqual(new msUri("C:\\Test.docx").getLocalPath(), expected);

        // WORDSNET-785
        msAssert.areEqual(new msUri("   file:C:/Test.docx").getLocalPath(), expected);
        msAssert.areEqual(new msUri("   file:/C:/Test.docx").getLocalPath(), expected);
        msAssert.areEqual(new msUri("   file://C:/Test.docx").getLocalPath(), expected);
        msAssert.areEqual(new msUri("   file:///C:/Test.docx").getLocalPath(), expected);
        msAssert.areEqual(new msUri("   file:////////C:/Test.docx").getLocalPath(), expected);

        // .Net accepts only two slashes after 'http:' protocol prefix, Java - any number:
        msAssert.areEqual(new msUri("   http:localhost").getAbsoluteUri(), "http://localhost");
        msAssert.areEqual(new msUri("   http:/localhost").getAbsoluteUri(), "http://localhost");
        msAssert.areEqual(new msUri("   http://localhost").getAbsoluteUri(), "http://localhost");
        msAssert.areEqual(new msUri("   http:///localhost").getAbsoluteUri(), "http://localhost");
        msAssert.areEqual(new msUri("   http:////////localhost").getAbsoluteUri(), "http://localhost");

        // Underscores in hostname
        // The same thing with arbitrary number of slashes in Java:
        msAssert.areEqual(new msUri("   http:local_host_").getAbsoluteUri(), "http://local_host_");
        msAssert.areEqual(new msUri("   http:/local_host_").getAbsoluteUri(), "http://local_host_");
        msAssert.areEqual(new msUri("   http://local_host_").getAbsoluteUri(), "http://local_host_");
        msAssert.areEqual(new msUri("   http:///local_host_").getAbsoluteUri(), "http://local_host_");
        msAssert.areEqual(new msUri("   http:////////local_host_").getAbsoluteUri(), "http://local_host_");
    }

    @Test
    public void testSpaces() throws Exception
    {
        msAssert.areEqual(new msUri("C:/Test with spaces.docx").getLocalPath(), "C:/Test with spaces.docx");
        msAssert.areEqual(new msUri("C:/Test%20with%20spaces.docx").getLocalPath(), "C:/Test with spaces.docx");
        //.Net tolerates spaces just before separator: "dir /file.txt".
        msUri uri = new msUri("file:///X:/awnet/TestData/Fields/LinksAndReferences /Hahaha.docx");
        msAssert.isTrue(uri.isAbsoluteUri()); //java used to throw here before fix.
    }

    @Test
    public void testScheme() throws Exception
    {
        String expected =
            "C:/Test.docx";
        msAssert.areEqual(new msUri("file:/C:/Test.docx").getLocalPath(), expected);
        msAssert.areEqual(new msUri("file:///C:/Test.docx").getLocalPath(), expected);
    }

    @Test
    public void testI18N() throws Exception
    {
        msAssert.areEqual(new msUri("C:/русский.txt").getLocalPath(), "C:/русский.txt");
    }

    /**
        * See http://en.wikipedia.org/wiki/File_URI_scheme about number of slashes in URI.
        *
        * Short resume about slashes in Uri:
        * 1. 2 slashes: Basic Uri like 'file://host/c:/test.pdf' or 'file://host/etc/test.pdf'.
        * 2. 3 slashes: Special case when host is localhost and it is omitted: 'file:///c:/test.pdf' or 'file:///etc/test.pdf'.
        * 3. 4 slashes - "legacy" URLs. Should be converted to 2 slashes.
        * 4. 0, 1, 4+ slashes - errors that can exist in real files. Should be converted to 2 slashes.
        *
        * So.
        * 1. 2 slashes are leaved as is.
        * 2. file scheme + (dos-like path or 3 slashes) => 3 slashes (implicit localhost).
        * 3. all other => 2 slashes.
        *
        * Note: .Net Uri throws on 0 or 1 slash - we don't (as some browsers). It works (i believe) for uri with scheme
        * but result is undefined for absolute Uri without scheme (like '/HOST/c:/test.pdf') - it will be treated as
        * relative local Uri i.e. something like 'X:/HOST/c:/test.pdf' - so I can't even include the case in tests.
        */

    @Test
    public void testLocalHostSlashes() throws Exception
    {
        testLocalHostSlashesInternal("host", "/Users/Public/aspose exchange.txt");
        testLocalHostSlashesInternal("host_1", "/Users/Public/aspose exchange.txt");
        // WORDSJAVA-840 AWJ under linux doesn't read localhost image file with 3 slashes: "file:///tmp/documentum/09.png"
        msAssert.areEqual(new msUri("file:///tmp/documentum/09.png").getLocalPath(), "/tmp/documentum/09.png");
        // Detailed results for different platforms were captured during the original investigation.
    }

    private void testLocalHostSlashesInternal(String host, String path) throws Exception
    {
        String path1 = "//" + host + path;
        String path2 = "/" + host + path;


        msAssert.areEqual(new msUri("//" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("///" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("////" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);

        msAssert.areEqual(new msUri("\\\\" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("\\\\\\" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("\\\\\\\\" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("\\\\\\\\\\" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);

        msAssert.areEqual(new msUri("file:" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("file:\\" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("file:\\\\" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("file:\\\\\\" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path2);
        msAssert.areEqual(new msUri("file:\\\\\\\\" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("file:\\\\\\\\\\" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);

        msAssert.areEqual(new msUri("file:" + host + path).getLocalPath(), path1);
        msAssert.areEqual(new msUri("file:\\" + host + path).getLocalPath(), path1);
        msAssert.areEqual(new msUri("file:\\\\" + host + path).getLocalPath(), path1);
        msAssert.areEqual(new msUri("file:\\\\\\" + host + path).getLocalPath(), path2);
        msAssert.areEqual(new msUri("file:\\\\\\\\" + host + path).getLocalPath(), path1);
        msAssert.areEqual(new msUri("file:\\\\\\\\\\" + host + path).getLocalPath(), path1);

        msAssert.areEqual(new msUri("file:" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("file:/" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("file://" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("file:///" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path2);
        msAssert.areEqual(new msUri("file:////" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);
        msAssert.areEqual(new msUri("file://///" + host + "\\Users\\Public\\aspose exchange.txt").getLocalPath(), path1);

        msAssert.areEqual(new msUri("file:" + host + path).getLocalPath(), path1);
        msAssert.areEqual(new msUri("file:/" + host + path).getLocalPath(), path1);
        msAssert.areEqual(new msUri("file://" + host + path).getLocalPath(), path1);
        msAssert.areEqual(new msUri("file:///" + host + path).getLocalPath(), path2);
        msAssert.areEqual(new msUri("file:////" + host + path).getLocalPath(), path1);
        msAssert.areEqual(new msUri("file://///" + host + path).getLocalPath(), path1);
    }

    @Test
    public void testNet() throws Exception
    {
        msAssert.areEqual(new msUri("ftp://ftp.example.com/Test.docx").getAbsoluteUri(), "ftp://ftp.example.com/Test.docx");
    }

    @Test
    public void testMailTo() throws Exception
    {
        msUri uri = new msUri("mailto:info@jsvest.net");
        msAssert.isTrue(uri.isAbsoluteUri());
        msAssert.areEqual(uri.getAbsoluteUri(), "mailto:info@jsvest.net");
    }

    @Test
    public void testJiraJ1802() throws Exception
    {
        String s1 = "X:\\awnet\\TestData\\AUT 70015.jpg";
        String s2 = "X:\\awnet\\TestData\\AUT% 70015.jpg";
        String s3 = "X:\\awnet\\TestData\\AUT%2070015.jpg";

        msAssert.areEqual("file:///X:/awnet/TestData/AUT 70015.jpg", new msUri(s1).toString());
        msAssert.areEqual("file:///X:/awnet/TestData/AUT%2070015.jpg", new msUri(s1).getAbsoluteUri());

        msAssert.areEqual("file:///X:/awnet/TestData/AUT%25 70015.jpg", new msUri(s2).toString());
        msAssert.areEqual("file:///X:/awnet/TestData/AUT%25%2070015.jpg", new msUri(s2).getAbsoluteUri());

        msAssert.areEqual("file:///X:/awnet/TestData/AUT%252070015.jpg", new msUri(s3).toString());
        msAssert.areEqual("file:///X:/awnet/TestData/AUT%252070015.jpg", new msUri(s3).getAbsoluteUri());


        String http1 = "http://test.com/?a=a b";
        String http2 = "http://test.com/?a=a%20b";

        msAssert.areEqual("http://test.com/?a=a b", new msUri(http1).toString());
        msAssert.areEqual("http://test.com/?a=a%20b", new msUri(http1).getAbsoluteUri());

        msAssert.areEqual("http://test.com/?a=a b", new msUri(http2).toString());
        msAssert.areEqual("http://test.com/?a=a%20b", new msUri(http2).getAbsoluteUri());
    }
}

