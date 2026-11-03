// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/03/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IChar;
import com.aspose.Debug;


/// <summary>Represents a single character with RunPr formatting.</summary>
class RichChar implements IChar
{
    RichChar(char c, RunPr runPr)
    {
        Debug.assertTrue(runPr != null);
        mCharacter = c;
        mRunPr = runPr;
    }

    public char toSystemChar()
    {
        return getCharacter();
    }

    public IChar /*IChar.*/toUpper()
    {
        return toUpperInternal();
    }

    RichChar toUpperInternal()
    {
        return new RichChar(Character.toUpperCase(getCharacter()), getRunPr());
    }

    public IChar /*IChar.*/toLower()
    {
        return toLowerInternal();
    }

    RichChar toLowerInternal()
    {
        return new RichChar(Character.toLowerCase(getCharacter()), getRunPr());
    }

    char getCharacter() { return mCharacter; };

    private char mCharacter;

    RunPr getRunPr() { return mRunPr; };

    private RunPr mRunPr;

    RichString toRichString()
    {
        return new RichString(this);
    }

    static /*final*/ RichChar NULL_CHAR = new RichChar(Character.MIN_VALUE, new RunPr());
}

