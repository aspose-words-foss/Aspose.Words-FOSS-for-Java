// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This interface is used to break the direct dependency Aspose.Words.Model from RW.Vml.
/// We use a stub implementation of IVmlShapeTypeReader in C++ branches until RW.Vml isn't ported to C++. 
/// </summary>
interface IVmlShapeTypeReader
{
    /// <summary>
    /// Reads a shapeType element and returns parsed shape properties.
    /// </summary>
    public ShapePr readShapeType(String shapeTypeXml) throws Exception;
}

