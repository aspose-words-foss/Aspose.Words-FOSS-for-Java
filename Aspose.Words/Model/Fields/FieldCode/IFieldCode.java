// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides access to field code arguments and switches.
/// </summary>
interface IFieldCode
{
    /// <summary>
    /// Gets an argument as a plain text at the specified index.
    /// </summary>
    public String getArgumentAsString(int authorNameArgumentIndex) throws Exception;

    /// <summary>
    /// Returns <c>true</c> if the switch was found in the field code.
    /// </summary>
    public boolean hasSwitch(String switchName);
}

