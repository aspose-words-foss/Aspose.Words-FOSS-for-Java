// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/27/2022 by Tengiz Sharafiev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the mode of internal Document ctor.
/// </summary>
/*enum*/ final class DocumentCtorMode
{
    private DocumentCtorMode(){}
    
    /// <summary>
    /// Creates a completely empty document node.
    /// </summary>
    public static final int EMPTY_DOCUMENT_NODE = 0;
    /// <summary>
    /// Loads a blank document.
    /// </summary>
    public static final int BLANK_DOCUMENT_NODE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DocumentCtorMode value.
    /// </summary>
    public static String getName(/*DocumentCtorMode*/int documentCtorMode)
    {
    	switch (documentCtorMode)
    	{
    		case EMPTY_DOCUMENT_NODE: return "EMPTY_DOCUMENT_NODE";
    		case BLANK_DOCUMENT_NODE: return "BLANK_DOCUMENT_NODE";
    		default: return "Unknown DocumentCtorMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DocumentCtorMode value.
    /// </summary>
    public static String toString(/*DocumentCtorMode*/int documentCtorMode)
    {
    	switch (documentCtorMode)
    	{
    		case EMPTY_DOCUMENT_NODE: return "EmptyDocumentNode";
    		case BLANK_DOCUMENT_NODE: return "BlankDocumentNode";
    		default: return "Unknown DocumentCtorMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DocumentCtorMode name.
    /// </summary>
    public static /*DocumentCtorMode*/int fromName(String documentCtorModeName)
    {
    	if ("EMPTY_DOCUMENT_NODE".equals(documentCtorModeName)) return EMPTY_DOCUMENT_NODE;
    	if ("BLANK_DOCUMENT_NODE".equals(documentCtorModeName)) return BLANK_DOCUMENT_NODE;
    	throw new IllegalArgumentException("Unknown DocumentCtorMode name.");
    }
    
    /// <summary>
    /// Returns array of DocumentCtorMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EMPTY_DOCUMENT_NODE,
    		BLANK_DOCUMENT_NODE,
    	};
    }

    public static final int length = 2;
}

