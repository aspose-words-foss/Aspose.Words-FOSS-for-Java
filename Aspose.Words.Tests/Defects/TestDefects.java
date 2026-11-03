// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;


/// <summary>
/// This file contains tests made for problems reported on support forum.
/// Some of them will also correspond to issues logged on DTTS
/// </summary>
@Test
public class TestDefects
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }



    /// <summary>
    /// Tests cloning chart title.
    /// </summary>
    @Test (enabled = false, description = "TestDefects")
    public void testCloningChartTitle() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestJira16069.docx");
        Document clone = doc.deepClone();
        ChartTitle title = clone.getFirstSection().getBody().getShapes().get(0).getChart().getTitle();
        Assert.fail("The mDocument field of the 'title' object has wrong value, it should be equals to 'clone'.");
    }
}

