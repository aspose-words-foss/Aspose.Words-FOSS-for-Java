// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This class is used together with <see cref="IVmlShapeTypeReader"/> interface to break the direct dependency 
/// Aspose.Words.Model from RW.Vml.
/// </summary>
class VmlShapeTypeReader implements IVmlShapeTypeReader
{
    /// <summary>
    /// Reads a shapeType element and returns parsed shape properties.
    /// </summary>
    public ShapePr readShapeType(String shapeTypeXml) throws Exception
    {
        return VmlShapeReader.readShapeType(new VmlShapeTypeReaderContext(shapeTypeXml));
    }
}

