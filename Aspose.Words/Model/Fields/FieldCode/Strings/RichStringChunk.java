// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/04/2020 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>Represents a collection of characters with same formatting.</summary>
class RichStringChunk
{
    public RichStringChunk(String value, RunPr runPr)
    {
        mValue = value;
        mRunPr = runPr;
    }

    String getValue() { return mValue; };

    private String mValue;
    RunPr getRunPr() { return mRunPr; };

    private RunPr mRunPr;
}

