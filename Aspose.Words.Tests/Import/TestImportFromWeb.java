// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2013 by Andrey Noskov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests importing documents from web by URL.
/// </summary>
@Test
public class TestImportFromWeb
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests load Document by URI, without specifying loading options.
    /// </summary>
    @Test (enabled = false, description = "TestImportFromWeb")
    public void testLoadDocumentByUrl() throws Exception
    {
        // I think for testing we can simply read robots.txt it is always there and it is a valid document.
        Document doc = new Document("https://www.aspose.com/robots.txt");
        msAssert.areEqual(LoadFormat.TEXT, doc.getOriginalLoadFormat());
    }
}

