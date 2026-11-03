// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/01/2019 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.SystemPal;


/// <summary>
/// Helps to fetch resources in Aspose.Words.dll assembly.
/// </summary>
/*static*/ class ResourceUtil
{
	/* Simulation of static class by using private constructor */
	private ResourceUtil()
	{}

    /// <summary>
    /// Returns a resource stream in Aspose.Words.dll assembly or throws if the resource cannot be found.
    /// </summary>
    /// <param name="fullResourceName">Full resource name.</param>
    public static Stream fetchResourceStream(String fullResourceName)
    {
        return SystemPal.fetchResourceStream(fullResourceName, Document.class);
    }
}

