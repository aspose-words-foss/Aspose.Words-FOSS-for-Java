// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/08/2008 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.BytesHash;
import com.aspose.HashUtil;
import com.aspose.HashSetGeneric;
import com.aspose.BytesHashToObjDictionary;


/// <summary>
/// Removes any image bytes duplication among shapes of the document.
/// </summary>
class ShapeImageOptimizer
{
    /// <summary>
    /// Remove image duplication if necessary from all shape attributes that can store images.
    /// </summary>
    void removeImageDuplication(ShapeBase shape)
    {
        removeImageDuplicationCore(shape, ShapeAttr.IMAGE_BYTES);
        removeImageDuplicationCore(shape, ShapeAttr.FILL_IMAGE_BYTES);
        removeImageDuplicationCore(shape, ShapeAttr.LINE_IMAGE_BYTES);
    }

    /// <summary>
    /// Remove image duplication if necessary.
    /// </summary>
    /// <param name="shape">Shape object.</param>
    /// <param name="imageBytesAttr">Shape attribute for image data.
    /// Can be <see cref="ShapeAttr.ImageBytes"/>, <see cref="ShapeAttr.FillImageBytes"/> or <see cref="ShapeAttr.LineImageBytes"/>.</param>
    private void removeImageDuplicationCore(ShapeBase shape, int imageBytesAttr)
    {
        byte[] imageBytes = (byte[])shape.getShapePr().getDirectAttr(imageBytesAttr);
        if (imageBytes == null)
            return;

        // If new image bytes encountered.
        if (!mImageBytesSet.contains(imageBytes))
        {
            BytesHash imageGuid = HashUtil.getSHA512Hash(imageBytes);
            byte[] cachedBytes = mImageGuidTable.get(imageGuid);

            if (cachedBytes != null)
            {
                // It is a duplicate image, change it to the first encounered image bytes with this GUID.
                shape.getShapePr().setAttr(imageBytesAttr, cachedBytes);
            }
            else
            {
                mImageBytesSet.add(imageBytes);
                mImageGuidTable.set(imageGuid, imageBytes);
            }
        }
    }

    /// <summary>
    /// Contains byte[] objects. Allows to quickly discard the same image bytes without calculating hash.
    /// </summary>
    private /*final*/ HashSetGeneric<byte[]> mImageBytesSet = new HashSetGeneric<byte[]>();
    /// <summary>
    /// Table to store relationship between shape GUIDs and image data .
    /// </summary>
    private /*final*/ BytesHashToObjDictionary<byte[]> mImageGuidTable = new BytesHashToObjDictionary<byte[]>();
}

