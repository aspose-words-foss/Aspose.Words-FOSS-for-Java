// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2010 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines the method to be used for inflation and deflation.
/// </summary>
public /*enum*/ final class ZipMethod
{
    private ZipMethod(){}
    
    public static final int DEFLATE = 0;
    public static final int ZLIB = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ZipMethod value.
    /// </summary>
    public static String getName(/*ZipMethod*/int zipMethod)
    {
    	switch (zipMethod)
    	{
    		case DEFLATE: return "DEFLATE";
    		case ZLIB: return "ZLIB";
    		default: return "Unknown ZipMethod value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ZipMethod value.
    /// </summary>
    public static String toString(/*ZipMethod*/int zipMethod)
    {
    	switch (zipMethod)
    	{
    		case DEFLATE: return "Deflate";
    		case ZLIB: return "Zlib";
    		default: return "Unknown ZipMethod value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ZipMethod name.
    /// </summary>
    public static /*ZipMethod*/int fromName(String zipMethodName)
    {
    	if ("DEFLATE".equals(zipMethodName)) return DEFLATE;
    	if ("ZLIB".equals(zipMethodName)) return ZLIB;
    	throw new IllegalArgumentException("Unknown ZipMethod name.");
    }
    
    /// <summary>
    /// Returns array of ZipMethod values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFLATE,
    		ZLIB,
    	};
    }

    public static final int length = 2;
}
