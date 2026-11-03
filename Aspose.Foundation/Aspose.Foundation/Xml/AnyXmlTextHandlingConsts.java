// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2010 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


public /*static*/ class AnyXmlTextHandlingConsts
{	/* Simulation of static class by using private constructor */	private AnyXmlTextHandlingConsts()	{}

    /// <summary>
    /// Text and significant whitespace is returned. 
    /// This is the mode to read most of the "normal" XML documents.
    /// </summary>
    public static final /*AnyXmlTextHandling*/int TEXT_AND_SIGNIFICANT = 
        AnyXmlTextHandling.TEXT | AnyXmlTextHandling.SIGNIFICANT_WHITESPACE;
    /// <summary>
    /// Text, significant and ignorable whitespace is returned to the caller.
    /// Returning ignorable whitespace to the caller is used when reading ODT because ODT rules for whitespace are different from .NET.
    /// </summary>
    public static final /*AnyXmlTextHandling*/int TEXT_AND_SIGNIFICANT_AND_IGNORABLE =
        AnyXmlTextHandling.TEXT | AnyXmlTextHandling.SIGNIFICANT_WHITESPACE | AnyXmlTextHandling.WHITESPACE;
    /// <summary>
    /// All of defined here XML node types are returned to the caller.
    /// </summary>
    public static final /*AnyXmlTextHandling*/int ALL = AnyXmlTextHandling.TEXT |
        AnyXmlTextHandling.SIGNIFICANT_WHITESPACE | AnyXmlTextHandling.WHITESPACE |
        AnyXmlTextHandling.PROCESSING_INSTRUCTION | AnyXmlTextHandling.ENTITY_REFERENCE;
}

