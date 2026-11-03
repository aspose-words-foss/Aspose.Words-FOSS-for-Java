// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/09/2017 by Andrey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.Encoding;


public /*static*/ class EncodingUtil
{
	/* Simulation of static class by using private constructor */
	private EncodingUtil()
	{}

    public static void registerEncodings()
    {
        // Nothing to do.
    }

    /// <summary>Returns the encoding associated with the specified code page identifier.</summary>
    public static Encoding getEncoding(int codepage)
    {

        return Encoding.getEncoding(codepage);
    }

    /// <summary>Returns the encoding associated with the specified code page name.</summary>
    public static Encoding getEncoding(String name)
    {

        return Encoding.getEncoding(name);
    }

}

