// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Miscelaneous import FlatOpc tests. Usually for strange files received from customers.
/// </summary>
@Test
public class TestImportFlatOpc
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }



    /// <summary>
    /// WORDSNET-19626 Bookmark which is a direct child of Body is missed upon loading.
    /// The problem occurred because we lost inline content at the end of the document 
    /// if inline content is direct child of body. In this particular case it is bookmark at the end of the document.
    /// Fixed by adding this content to the last paragraph of Body once we finished reading Body.
    /// </summary>
    @Test
    public void testDefect19626() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportFlatOpc\\TestDefect19626.fopc");
        msAssert.areEqual(9, doc.getRange().getBookmarks().getCount());
        msAssert.isNotNull(doc.getRange().getBookmarks().get("EndRangeHere"));
    }



    /// <summary>
    /// WORDSNET-17034 The source WordML is improperly uploaded.
    /// Missing mso-application tag.
    /// </summary>
    @Test
    public void testJira17034() throws Exception
    {
        Document doc = TestUtil.openFromZip("ImportFlatOpc\\TestJira17034.xml.zip");
        msAssert.areEqual(11, doc.getFirstSection().getBody().getTables().getCount());
    }
}

