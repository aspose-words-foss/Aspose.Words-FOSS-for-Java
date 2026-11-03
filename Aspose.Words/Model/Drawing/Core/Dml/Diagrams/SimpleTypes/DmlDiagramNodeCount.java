// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 21.4.7.44 ST_NodeCount (Number of Nodes Definition)
/// </summary>
class DmlDiagramNodeCount
{
    DmlDiagramNodeCount(int value)
    {
        if(value < -1)
            throw new IllegalArgumentException("Unexpected value.");

        mValue = value;
    }

    int getValue() { return mValue; }

    private /*final*/ int mValue;
}

