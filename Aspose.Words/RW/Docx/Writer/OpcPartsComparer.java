// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/11/2019 by Artem Ptitsin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Comparator;
import com.aspose.StringOrdinalComparer;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.msInt;
import java.util.HashMap;


/// <summary>
/// Comparer for sorting opc types in the OOXML formats.
/// </summary>
/// <remarks>The main goal is to sort the first three opc parts. Read more information on WORDSNET-19478</remarks>
class OpcPartsComparer implements Comparator<String>
{
    public int compare(String x, String y)
    {
        if (x == null || y == null)
            return StringOrdinalComparer.DEFAULT.compare(x, y);

        int xOrdinal = DictionaryExtension.getValueOrDefault(G_OVERRIDE_TYPES, x, 3);
        int yOrdinal = DictionaryExtension.getValueOrDefault(G_OVERRIDE_TYPES, y, 3);

        return xOrdinal == yOrdinal
            ? StringOrdinalComparer.DEFAULT.compare(x, y)
            : msInt.compareTo(xOrdinal, yOrdinal);
    }

    private static /*final*/ HashMap<String, Integer> G_OVERRIDE_TYPES = G_OVERRIDE_TYPESInitializer();
    /*JAVA-added private static Initializer*/
    private static HashMap<String, Integer> G_OVERRIDE_TYPESInitializer()
    {
        HashMap<String, Integer> G_OVERRIDE_TYPES = new HashMap<String, Integer>();
    	{
        	G_OVERRIDE_TYPES.put("/[Content_Types].xml", 0);
        	G_OVERRIDE_TYPES.put("/_rels/.rels", 1);
        	G_OVERRIDE_TYPES.put("/word/document.xml", 2);
    	}
        return G_OVERRIDE_TYPES;
    }
}

