// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/02/2020 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Regular encodings which can be handled by system class. 
/// </summary>
class TTEncodingRegular extends TTEncoding
{
    public TTEncodingRegular(Encoding encoding)
    {
        mEncoding = encoding;
    }

    public /*override*/ String getString(byte[] bytes)
    {
        return mEncoding.getString(bytes);
    }

    private /*final*/ Encoding mEncoding;
}

