// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/12/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// When implemented, provides necessary functionality related to field result formatting.
/// </summary>
interface IFieldResultFormatProvider
{
    /// <summary>
    /// Returns the inline node that is taken as a formatting pattern for the field result.
    /// </summary>
    /// <returns></returns>
    public Inline getSourceNode();

    /// <summary>
    /// Returns an object that applies result format to the field result.
    /// Returns null if no result format is specified.
    /// </summary>
    /// <returns></returns>
    public IFieldResultFormatApplier getFormatApplier() throws Exception;
}

