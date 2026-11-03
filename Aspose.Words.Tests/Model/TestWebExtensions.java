// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;


/// <summary>
/// Checks manipulations with the task pane add-ins through the model.
/// </summary>
@Test
public class TestWebExtensions
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks that client can not add empty item to task pane collection.
    /// </summary>
    @Test (expectedExceptions = NullPointerException.class)
    public void test18681AddNullItemToTaskPaneCollection()
    {
        TaskPaneCollection collection = new TaskPaneCollection();
        collection.add(null);
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks that client can not add empty item to bindings collection.
    /// </summary>
    @Test (expectedExceptions = NullPointerException.class)
    public void test18681AddNullItemToBindingsCollection()
    {
        WebExtensionBindingCollection collection = new WebExtensionBindingCollection();
        collection.add(null);
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks that client can not add empty item to properties collection.
    /// </summary>
    @Test (expectedExceptions = NullPointerException.class)
    public void test18681AddNullItemToPropertiesCollection()
    {
        WebExtensionPropertyCollection collection = new WebExtensionPropertyCollection();
        collection.add(null);
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks that client can not add empty item to references collection.
    /// </summary>
    @Test (expectedExceptions = NullPointerException.class)
    public void test18681AddNullItemToReferencesCollection()
    {
        WebExtensionReferenceCollection collection = new WebExtensionReferenceCollection();
        collection.add(null);
    }
}

