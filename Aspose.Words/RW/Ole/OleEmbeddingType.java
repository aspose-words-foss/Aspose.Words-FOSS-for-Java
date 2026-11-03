// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/03/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the embedding types of OLE objects.
/// </summary>
/*enum*/ final class OleEmbeddingType
{
    private OleEmbeddingType(){}
    
    /// <summary>
    /// Data embedded as OLE package object.
    /// </summary>
    public static final int OLE_1_PACKAGE = 0;

    /// <summary>
    /// Data embedded as "Ole1Native" stream.
    /// </summary>
    public static final int OLE_1_NATIVE_STREAM = 1;

    /// <summary>
    /// Data embedded as structured storage.
    /// </summary>
    public static final int OLE_2_STORAGE = 2;

    /// <summary>
    /// Data embedded as "Contents" stream.
    /// </summary>
    public static final int OLE_2_STREAM_CONTENTS = 3;

    /// <summary>
    /// Data embedded as "Package" stream.
    /// </summary>
    public static final int OLE_2_STREAM_PACKAGE = 4;

    /// <summary>
    /// Data embedded as "EmbeddedOdf" stream.
    /// </summary>
    public static final int OLE_2_STREAM_EMBEDDED_ODF = 5;

    /// <summary>
    /// Data embedded as "Equation0x0020Native" stream.
    /// </summary>
    public static final int OLE_2_STREAM_EQUATION_NATIVE = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OleEmbeddingType value.
    /// </summary>
    public static String getName(/*OleEmbeddingType*/int oleEmbeddingType)
    {
    	switch (oleEmbeddingType)
    	{
    		case OLE_1_PACKAGE: return "OLE_1_PACKAGE";
    		case OLE_1_NATIVE_STREAM: return "OLE_1_NATIVE_STREAM";
    		case OLE_2_STORAGE: return "OLE_2_STORAGE";
    		case OLE_2_STREAM_CONTENTS: return "OLE_2_STREAM_CONTENTS";
    		case OLE_2_STREAM_PACKAGE: return "OLE_2_STREAM_PACKAGE";
    		case OLE_2_STREAM_EMBEDDED_ODF: return "OLE_2_STREAM_EMBEDDED_ODF";
    		case OLE_2_STREAM_EQUATION_NATIVE: return "OLE_2_STREAM_EQUATION_NATIVE";
    		default: return "Unknown OleEmbeddingType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OleEmbeddingType value.
    /// </summary>
    public static String toString(/*OleEmbeddingType*/int oleEmbeddingType)
    {
    	switch (oleEmbeddingType)
    	{
    		case OLE_1_PACKAGE: return "Ole1Package";
    		case OLE_1_NATIVE_STREAM: return "Ole1NativeStream";
    		case OLE_2_STORAGE: return "Ole2Storage";
    		case OLE_2_STREAM_CONTENTS: return "Ole2StreamContents";
    		case OLE_2_STREAM_PACKAGE: return "Ole2StreamPackage";
    		case OLE_2_STREAM_EMBEDDED_ODF: return "Ole2StreamEmbeddedOdf";
    		case OLE_2_STREAM_EQUATION_NATIVE: return "Ole2StreamEquationNative";
    		default: return "Unknown OleEmbeddingType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OleEmbeddingType name.
    /// </summary>
    public static /*OleEmbeddingType*/int fromName(String oleEmbeddingTypeName)
    {
    	if ("OLE_1_PACKAGE".equals(oleEmbeddingTypeName)) return OLE_1_PACKAGE;
    	if ("OLE_1_NATIVE_STREAM".equals(oleEmbeddingTypeName)) return OLE_1_NATIVE_STREAM;
    	if ("OLE_2_STORAGE".equals(oleEmbeddingTypeName)) return OLE_2_STORAGE;
    	if ("OLE_2_STREAM_CONTENTS".equals(oleEmbeddingTypeName)) return OLE_2_STREAM_CONTENTS;
    	if ("OLE_2_STREAM_PACKAGE".equals(oleEmbeddingTypeName)) return OLE_2_STREAM_PACKAGE;
    	if ("OLE_2_STREAM_EMBEDDED_ODF".equals(oleEmbeddingTypeName)) return OLE_2_STREAM_EMBEDDED_ODF;
    	if ("OLE_2_STREAM_EQUATION_NATIVE".equals(oleEmbeddingTypeName)) return OLE_2_STREAM_EQUATION_NATIVE;
    	throw new IllegalArgumentException("Unknown OleEmbeddingType name.");
    }
    
    /// <summary>
    /// Returns array of OleEmbeddingType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		OLE_1_PACKAGE,
    		OLE_1_NATIVE_STREAM,
    		OLE_2_STORAGE,
    		OLE_2_STREAM_CONTENTS,
    		OLE_2_STREAM_PACKAGE,
    		OLE_2_STREAM_EMBEDDED_ODF,
    		OLE_2_STREAM_EQUATION_NATIVE,
    	};
    }

    public static final int length = 7;
}

