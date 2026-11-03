// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



interface IDmlBlipFillMode
{
    public IDmlBlipFillMode deepClone();

    /// <summary>
    /// The corresponded VML fill type.
    /// </summary>
    public /*FillTypeCore*/int getFillType();

    /// <summary>
    /// Indicates whether the blip has offsets from the bounding box.
    /// </summary>
    public boolean hasOffsets();

    public void write(IDmlShapeWriterContext writer);
}

