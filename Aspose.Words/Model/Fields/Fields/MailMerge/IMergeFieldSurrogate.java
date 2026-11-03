// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implemented by the fields that may act as merge fields, such as IF 0 = 0 and MACROBUTTON.
/// </summary>
interface IMergeFieldSurrogate
{
    /// <summary>
    /// Returns field start.
    /// </summary>
    public FieldStart getStart();

    /// <summary>
    /// Returns field separator.
    /// </summary>
    public FieldSeparator getSeparator();

    /// <summary>
    /// Returns field end.
    /// </summary>
    public FieldEnd getEnd();

    /// <summary>
    /// Gets whether the field requires value to act as a merge field.
    /// </summary>
    public boolean isMergeValueRequired();

    /// <summary>
    /// Gets the name of the merge field used to retrieve a value from the data source.
    /// </summary>
    public String getMergeFieldName() throws Exception;

    /// <summary>
    /// Gets whether the field may act as a merge field.
    /// </summary>
    public boolean canWorkAsMergeField() throws Exception;
}

