// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/07/2010 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Comparator;
import com.aspose.ms.System.msInt;
import com.aspose.ms.System.msChar;


/// <summary>
/// Structured storage elements must be sorted. 
/// It is very important to sort "correctly" according to the structured storage 
/// rules, otherwise the produced file will not be valid.
/// </summary>
class MemoryStorageNameComparer implements Comparator<String>
{
    public int /*IComparer<String>.*/compare(String s1, String s2)
    {

        // First compare the length, the shorter string is considered smaller.
        int result = msInt.compareTo(s1.length(), s2.length());
        if (result == 0)
        {
            // Now compare the name itself, but take care.

            // First of all, the compare is case insensitive, but I cannot use 
            // string.Compare because it incorrectly compares some special
            // characters like 0x01, 0x03 and 0x05 that occur in stream names.

            // Also, the underscore character is considered greater than all alpha chars.

            // The simplest solution is to convert all to upper case (not lowercase)
            // and compare ordinal values of the characters.

            // Converting to upper case is important because it ensures that the underscore
            // character is treated as greater than all alpha chars.

            // Fixed WORDSJAVA-858: OLE object cannot be edited after re saving the document.
            // gpv: "ß".ToUpper() gives "ß" in .NET, whereas "ß".ToUpper() gives "SS" in Java.
            // gpv: I use a simple comparison algorithm described in http://msdn.microsoft.com/en-us/library/dd942470.aspx, p.3.
            result = compareOrdinalUpperCase(s1, s2);
        }
        return result;
    }

    /// <summary>
    /// Comparison algorithm of Directory Entry Names. Described in http://msdn.microsoft.com/en-us/library/dd942470.aspx, p.3.
    /// 1. For strings with the same name length, iterate through each UTF-16 code point, one at a time, from the beginning of the Unicode string.
    /// 2. For each UTF-16 code point, convert to upper-case with the Unicode Default Case Conversion Algorithm
    /// 3. Compare each upper-cased UTF-16 code point binary value.
    /// </summary>
    private static int compareOrdinalUpperCase(String s1, String s2)
    {
        int len1 = s1.length();
        int len2 = s2.length();
        int n = Math.min(len1, len2);

        int i = 0;
        while (n-- != 0)
        {
            char c1 = msChar.toUpperInvariant(s1.charAt(i));
            char c2 = msChar.toUpperInvariant(s2.charAt(i));

            if (c1 != c2)
                return c1 - c2;

            i++;
        }

        return len1 - len2;
    }
}

