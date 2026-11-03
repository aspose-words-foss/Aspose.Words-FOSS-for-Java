// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import com.aspose.SystemPal;
import org.testng.annotations.AfterMethod;
import java.util.ArrayList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Represents a base class for all classes that test how fields work.
/// </summary>
@Test
public abstract class TestFieldsBase
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void testSetUp()
    {
        RandomUtil.reset();

        // Parsing of numbers and dates is performed using the current culture and this is by design.
        // So we have to select the standard culture before we run a test in order to pass on all
        // development machines that can have different cultures selected.
        SystemPal.saveCulture();
        SystemPal.setStandardCulture();
    }

    @AfterMethod (alwaysRun = true)
    public void testShutDown()
    {
        SystemPal.restoreCulture();
    }

    protected static Document openSaveOpenUpdateFields(String fileName) throws Exception
    {
        return openSaveOpenUpdateFields(fileName, true);
    }

    /// <summary>
    /// Works exactly like Open but also clears then updates field results throughout the document.
    /// Used by the field evaluation engine tests.
    /// </summary>
    /// <param name="fileName">Can be relative to the TestData folder or an explicit path.</param>
    /// <param name="setGarbage"></param>
    protected static Document openSaveOpenUpdateFields(String fileName, boolean setGarbage) throws Exception
    {
        Document doc = TestUtil.openSaveOpen(fileName);

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        if (setGarbage)
        {
            for (Field field : fields)
            {
                if (field.hasSeparator())
                    field.setResult("34twrsdgrw546565twetwet");   // Some garbage text that should never appear during update.
            }
        }

        doc.updateFields();

        return doc;
    }

    /// <summary>
    /// Opens a file with the given relative path in the TestData folder, loads the document, updates its fields
    /// without BIDI text support, saves and opens it performing a gold compare test.
    /// </summary>
    protected static void openUpdateFieldsSaveCheckGold(String fileName) throws Exception
    {
        openUpdateFieldsSaveCheckGold(fileName, false);
    }

    /// <summary>
    /// Opens a file with the given relative path in the TestData folder, loads the document, updates its fields
    /// with optionally BIDI text support, saves and opens it performing a gold compare test.
    /// </summary>
    protected static void openUpdateFieldsSaveCheckGold(String fileName, boolean isBidiSupported) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        doc.getFieldOptions().isBidiTextSupportedOnUpdate(isBidiSupported);
        doc.updateFields();

        TestUtil.saveCheckGold(doc, fileName);
    }

    protected static void assertAreEqual(Field field1, Field field2)
    {
        msAssert.areEqual(field1.getStart(), field2.getStart());
        msAssert.areEqual(field1.getSeparator(), field2.getSeparator());
        msAssert.areEqual(field1.getEnd(), field2.getEnd());
        msAssert.areEqual(field1.getType(), field2.getType());
        msAssert.areEqual(field1.getFieldCode(), field2.getFieldCode());
        msAssert.areEqual(field1.getResult(), field2.getResult());
    }

    protected static String[] getFieldResults(Iterable<Field> fields)
    {
        ArrayList<String> results = new ArrayList<String>();

        for (Field field : fields)
            msArrayList.add(results, field.getResult());

        return msArrayList.toArray(results, new String[0]);
    }
}

