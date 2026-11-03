// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a value used to set field result. The value may be represented by a constant or a
/// formatted content. Field result may be either calculated (thus being a constant) or taken from
/// a field argument (thus being a plain text or formatted nodes, depending on what we currently need).
/// </summary>
interface IFieldResult
{
    /// <summary>
    /// Returns a constant that represents a result of field evaluation.
    /// </summary>
    public Constant getFieldResultValue() throws Exception;

    /// <summary>
    /// Returns a node range representation of field result.
    /// </summary>
    public NodeRange getFieldResultRange();
}

