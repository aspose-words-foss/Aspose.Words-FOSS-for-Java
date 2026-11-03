// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2020 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msDictionary;
import java.util.Map;
import java.util.HashMap;
import com.aspose.words.ref.Ref;


/// <summary>Adapts <see cref="System.Char"/> to <see cref="IChar"/>.</summary>
public class SystemCharAdapter implements IChar
{
    private SystemCharAdapter(char value)
    {
        mValue = value;
    }

    public static SystemCharAdapter create(char value)
    {
        synchronized (G_LOCK)
        {
            SystemCharAdapter result = null;
            Ref<SystemCharAdapter> referenceToResult = new Ref<SystemCharAdapter>(result);
            boolean outRefCondition0 = !msDictionary.tryGetValue(G_CACHE, value, /*out*/ referenceToResult);
            result = referenceToResult.get();
            if (outRefCondition0)
            {
                result = new SystemCharAdapter(value);
                G_CACHE.put(value, result);
            }

            return result;
        }
    }

    public char toSystemChar()
    {
        return getValue();
    }

    public IChar /*IChar.*/toUpper()
    {
        return toUpperInternal();
    }

    public SystemCharAdapter toUpperInternal()
    {
        return new SystemCharAdapter(Character.toUpperCase(getValue()));
    }

    public IChar /*IChar.*/toLower()
    {
        return toLowerInternal();
    }

    public SystemCharAdapter toLowerInternal()
    {
        return new SystemCharAdapter(Character.toLowerCase(getValue()));
    }

    public char getValue() { return mValue; };

    private  char mValue;

    private static /*final*/ Map<Character, SystemCharAdapter> G_CACHE = new HashMap<Character, SystemCharAdapter>();
    private static /*final*/ Object G_LOCK = new Object();
}

