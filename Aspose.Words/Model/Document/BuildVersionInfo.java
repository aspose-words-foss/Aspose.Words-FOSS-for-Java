// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Provides information about the current product name and version.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/generator-or-producer-name-included-in-output-documents/">Generator or Producer Name Included in Output Documents</a> documentation article.</para>
/// </summary>
public /*static*/ class BuildVersionInfo
{
	/* Simulation of static class by using private constructor */
	private BuildVersionInfo()
	{}

    /// <summary>
    /// Gets the full name of the product.
    /// </summary>
    public static String getProduct()
    {
        return AssemblyConstants.PRODUCT;
    }

    /// <summary>
    /// Gets the product version.
    /// </summary>
    /// <remarks>
    /// <para>The product version is in the "Major.Minor.Hotfix.0" format.</para>
    /// </remarks>
    public static String getVersion() { return AssemblyConstants.VERSION; }
}

