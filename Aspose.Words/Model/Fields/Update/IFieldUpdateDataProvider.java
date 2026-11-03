// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/06/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// When implemented, provides a field value or other data for a field that is unable to update itself. For example,
/// merge fields need to take their value from a mail merge region.
/// </summary>
interface IFieldUpdateDataProvider
{
    /// <summary>
    /// Returns a field value (represented by a constant) for the specified field or null if impossible.
    /// </summary>
    public Constant getValue(Field field) throws Exception;

    /// <summary>
    /// Returns an arbitrary data for the specified field or null if impossible.
    /// </summary>
    public Object getData(Field field);
}


