// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/09/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This interface breaks a dependency from the Nrx document reader to the object that will actually read DrawingML.
/// </summary>
interface INrxDmlReader
{
    /// <summary>
    /// The implementation needs to read a DrawingML node from the current position (beginning of a w:drawing element)
    /// and add it to the model appropriately. The implementation needs to assign the provided run properties to the new node.
    /// </summary>
    public ShapeBase readDrawing(RunPr runPr) throws Exception;
}

