// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/27/2015 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;


@Test
public class TestDmlLists
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testDmlListsBulletTypes() throws Exception
    {
        TestUtil.openSaveOpen("Model\\DrawingML\\TextBox\\TestDmlListsBulletTypes.docx");
    }

    @Test
    public void testDmlListsLevels() throws Exception
    {
        TestUtil.openSaveOpen("Model\\DrawingML\\TextBox\\TestDmlListsLevels.docx");
    }

    @Test
    public void testDmlListsBulletColor() throws Exception
    {
        TestUtil.openSaveOpen("Model\\DrawingML\\TextBox\\TestDmlListsBulletColor.docx");
    }

    @Test
    public void testDmlListsBulletFont() throws Exception
    {
        TestUtil.openSaveOpen("Model\\DrawingML\\TextBox\\TestDmlListsBulletFont.docx");
    }

    @Test
    public void testDmlListsBulletSize() throws Exception
    {
        TestUtil.openSaveOpen("Model\\DrawingML\\TextBox\\TestDmlListsBulletSize.docx");
    }
}

