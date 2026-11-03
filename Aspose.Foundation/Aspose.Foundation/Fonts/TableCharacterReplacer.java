// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Performs character replacement according to provided table.
/// </summary>
abstract class TableCharacterReplacer extends CharacterReplacerBase
{
    protected TableCharacterReplacer(IntToIntDictionary table)
    {
        mTable = table;
    }

    /*override*/ void replace(int charCode, IntList replacements)
    {
        int c = mTable.get(charCode);
        if (!IntToIntDictionary.isNullSubstitute(c))
            replacements.add(c);
    }

    private /*final*/ IntToIntDictionary mTable;
}

