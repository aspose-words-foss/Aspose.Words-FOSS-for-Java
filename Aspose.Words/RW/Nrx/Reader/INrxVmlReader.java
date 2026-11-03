// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/02/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


interface INrxVmlReader
{
    /// <summary>
    /// Reads a shape container element such as w:pict or w:object.
    /// Reader should be positioned to element start.
    /// </summary>
    public ArrayList<ShapeBase> read(IVmlShapeReaderContext context) throws Exception;
}

