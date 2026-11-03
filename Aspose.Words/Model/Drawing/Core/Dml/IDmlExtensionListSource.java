// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


/// <summary>
/// Allows getting/setting an extension list.
/// </summary>
interface IDmlExtensionListSource
{
    /// <summary>
    /// Represents collection of Dml extensions.
    /// </summary>
    /// <remarks>
    /// The key is <see cref="DmlExtensionUri"/> the value is <see cref="DmlExtension"/>.
    /// </remarks>
    public StringToObjDictionary<DmlExtension> getExtensions(); public void setExtensions(StringToObjDictionary<DmlExtension> value);
}

