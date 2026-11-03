// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/06/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.CompressedData;
import com.aspose.ArrayUtil;


/// <summary>
/// Represents a data source for image of a VML-based shape.
/// </summary>
class ImageDataSource implements IImageDataSource
{
    ImageDataSource(IShapeAttrSource parent)
    {
        mParent = parent;
    }

    public String /*IImageDataSource.*/getSourceFullName() { return (String)mParent.fetchShapeAttr(ShapeAttr.IMAGE_SOURCE_FULL_NAME); }
    public void /*IImageDataSource.*/setSourceFullName(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mParent.setShapeAttr(ShapeAttr.IMAGE_SOURCE_FULL_NAME, value);
    }

    public byte[] /*IImageDataSource.*/getImageBytes() 
    {
        byte[] imageBytes = (byte[])mParent.fetchShapeAttr(ShapeAttr.IMAGE_BYTES);
        return CompressedData.getData(imageBytes);
    }
    public void /*IImageDataSource.*/setImageBytes(byte[] value)
    {
        if (value == null || value.length == 0)
            mParent.removeShapeAttr(ShapeAttr.IMAGE_BYTES);
        else
            mParent.setShapeAttr(ShapeAttr.IMAGE_BYTES, value);
    }

    public boolean /*IImageDataSource.*/hasImageBytes() { return ArrayUtil.hasData((byte[])mParent.fetchShapeAttr(ShapeAttr.IMAGE_BYTES)); }

    private /*final*/ IShapeAttrSource mParent;
}

