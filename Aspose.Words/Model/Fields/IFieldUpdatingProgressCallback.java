// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/10/2022 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implement this interface if you want to track field updating progress.
/// </summary>
public interface IFieldUpdatingProgressCallback
{
    /// <summary>
    /// A user defined method that is called when updating progress is changed.
    /// </summary>
    public void notify(FieldUpdatingProgressArgs args);
}

