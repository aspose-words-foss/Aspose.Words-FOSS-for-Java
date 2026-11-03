// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents piece of Ruby.
/// </summary>
class RubyChunk
{
    public /*override*/ int hashCode()
    {
        int hashCode = (Text != null) ? Text.hashCode() : 0;
        hashCode = (hashCode * 397) ^ ((RunPr != null) ? RunPr.hashCode() : 0);
        return hashCode;
    }

    String Text;
    RunPr RunPr = new RunPr();
}

