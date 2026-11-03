// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/02/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This interface is used to break the direct dependency Aspose.Words from RW.Docx.
/// We use a stub implementation of IDefaultThemeProvider in C++ branches until RW.Docx isn't ported to C++. 
/// </summary>
interface IDefaultThemeProvider
{
    /// <summary>
    /// Reads default theme.
    /// </summary>
    public Theme getDefaultTheme() throws Exception;
}

