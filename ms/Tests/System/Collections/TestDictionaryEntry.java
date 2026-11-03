// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/03/2009 by Konstantin Sidorenko
// 2016/07/14 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Collections.DictionaryEntry;
import java.util.Map;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests adapted from Rotor.
/// </summary>
@Test
public class TestDictionaryEntry
{
    @Test
    public void testGetKey()
    {
        entry = new DictionaryEntry("Hello", "World");
        msAssert.areEqual(entry.getKey(), "Hello");

        entry = new DictionaryEntry();
        entry = new DictionaryEntry(null, null);
        msAssert.areEqual(entry.getKey(), null);

        entry = new DictionaryEntry(5, 6);
        msAssert.areEqual(entry.getKey(), 5);
    }

    @Test
    public void testGetValue()
    {
        entry = new DictionaryEntry("Hello", "World");
        msAssert.areEqual(entry.getValue(), "World");

        entry = new DictionaryEntry(5, 6);
        msAssert.areEqual(entry.getValue(), 6);
    }

    @Test
    public void testSetValue()
    {
        entry = new DictionaryEntry("What", "ever");
        strValue = "Hello";
        entry.setValue(strValue);
        msAssert.areEqual(entry.getValue(), strValue);

        entry = new DictionaryEntry(5, 6);
        strValue = "Hello";
        entry.setValue(strValue);
        msAssert.areEqual(entry.getValue(), strValue);

        entry = new DictionaryEntry("string", 5);
        entry.setValue(6);
        msAssert.areEqual(entry.getValue(), 6);
    }

    //JAVA-specific Generics:

    @Test
    public void testGetKeyGenerics()
    {
        entry = new DictionaryEntry<String,String>("Hello", "World");
	    Assert.assertEquals(entry.getKey(), "Hello");

	    entry = new DictionaryEntry<Integer,Integer>(5, 6);
	    Assert.assertEquals(entry.getKey(), 5);

	    DictionaryEntry<String,String> entryStr = new DictionaryEntry<String,String>("Hello", "World");
	    Assert.assertEquals(entryStr.getKey(), "Hello");

	    DictionaryEntry<Integer,Integer> entryInt = new DictionaryEntry<Integer,Integer>(5, 6);
	    Assert.assertEquals((int) entryInt.getKey(), 5);
    }

    @Test
    public void testGetValueGenerics()
    {
        entry = new DictionaryEntry<String,String>("Hello", "World");
	    Assert.assertEquals(entry.getValue(), "World");

	    entry = new DictionaryEntry<Integer,Integer>(5, 6);
	    Assert.assertEquals(entry.getValue(), 6);

	    DictionaryEntry<String,String> entryStr = new DictionaryEntry<String,String>("Hello", "World");
	    Assert.assertEquals(entryStr.getValue(), "World");

	    DictionaryEntry<Integer,Integer> entryInt = new DictionaryEntry<Integer,Integer>(5, 6);
	    Assert.assertEquals((int) entryInt.getValue(), 6);
    }

    @Test
    public void testSetValueGenerics()
    {
        DictionaryEntry<String,String> entryStr = new DictionaryEntry<String,String>();
	    strValue = "Hello";
        entryStr.setValue(strValue);
	    Assert.assertEquals(entryStr.getValue(), strValue);

	    entryStr = new DictionaryEntry<String,String>("What", "ever");
	    strValue = "Hello";
        entryStr.setValue(strValue);
	    Assert.assertEquals(entryStr.getValue(), strValue);

	    DictionaryEntry<String,Integer> entryStrInt = new DictionaryEntry<String,Integer>("STring", 5);
	    entryStrInt.setValue(6);
	    Assert.assertEquals((int) entryStrInt.getValue(), 6);
    }

    private Map.Entry entry = new DictionaryEntry();
    private String strValue;
}

