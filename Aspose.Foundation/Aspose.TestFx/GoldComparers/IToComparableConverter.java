// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/14/2013 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



public interface IToComparableConverter
{
    /// <summary>
    /// Converts input file to comparable file type, like image, text or zip.
    /// </summary>
    public String[] convert(String inFileName) throws Exception;

    /// <summary>
    /// Returns type of output comparable file.
    /// </summary>
    public /*ComparableFileType*/int getFileType();
}
