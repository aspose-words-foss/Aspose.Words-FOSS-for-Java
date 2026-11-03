// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/02/2018 by Anatoly Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.SortedList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.words.ref.Ref;


@Test
public class TestOutRefGenerics
{
    @Test
    public void testGenericOutRefCtor()
    {
        ArrayList<String> list = null;
        SortedList<String, String> dict = null;
        Ref<ArrayList<String>> referenceToList = new Ref<ArrayList<String>>(list);
        Ref<SortedList<String, String>> referenceToDict = new Ref<SortedList<String, String>>(dict);
        methodRef(/*out*/ referenceToList, /*out*/ referenceToDict);
        list = referenceToList.get();
        dict = referenceToDict.get();

        msAssert.areEqual(list.size(), 3);
        msAssert.areEqual(dict.getCount(), 3);

        msAssert.areEqual(list.get(0), "val_1");
        msAssert.areEqual(list.get(1), "val_2");
        msAssert.areEqual(list.get(2), "val_3");

        msAssert.areEqual(dict.get("key_1"), "val_1");
        msAssert.areEqual(dict.get("key_2"), "val_2");
        msAssert.areEqual(dict.get("key_3"), "val_3");
    }

    private void methodRef(/*out*/Ref<ArrayList<String>> list, /*out*/Ref<SortedList<String, String>> dict)
    {
        list.set(new ArrayList<String>());
        addStringListValue(/*ref*/ list);
        dict.set(new SortedList<String, String>());
        addStringSortedListValue(/*ref*/ dict);
    }

    private void addStringListValue(/*ref*/Ref<ArrayList<String>> list)
    {
        msArrayList.add(list.get(), "val_1");
        msArrayList.add(list.get(), "val_2");
        msArrayList.add(list.get(), "val_3");
    }

    private void addStringSortedListValue(/*ref*/Ref<SortedList<String, String>> dict)
    {
        dict.get().add("key_1", "val_1");
        dict.get().add("key_2", "val_2");
        dict.get().add("key_3", "val_3");
    }
}

