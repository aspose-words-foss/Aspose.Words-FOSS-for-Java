// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/06/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;


/// <summary>
/// Represents a data source of DrawingML pictures.
/// </summary>
class DrawingMLImageDataSource implements IImageDataSource
{
    public DrawingMLImageDataSource(ShapeBase drawingML)
    {
        mDrawingML = drawingML;
    }

    public String getSourceFullName()
    {
        DmlBlipFill blip = getBlipFill();
        if (blip == null)
            return null;

        return blip.getBlip().getImageLink();
    }
    public void setSourceFullName(String value)
    {
        DmlBlipFill blip = getBlipFill();
        if (blip == null)
            return;

        blip.getBlip().setImageLink(value);
    }

    public byte[] getImageBytes()
    {
        DmlBlipFill blip = getBlipFill();
        if (blip == null)
            return null;

        return blip.getBlip().getEmbedImage();
    }
    public void setImageBytes(byte[] value) throws Exception
    {
        // Create blip fill if possible.
        DmlBlipFill blip = fetchBlip();
        if (blip != null)
            blip.getBlip().setEmbedImage(value);

        // Set fallback image too.
        if (mDrawingML.getFallbackShape() != null && mDrawingML.getFallbackShape().canHaveImage())
            mDrawingML.getFallbackShape().setShapeAttrInternal(ShapeAttr.IMAGE_BYTES, ImageDataCore.getImageBytes(value));
    }

    public boolean /*IImageDataSource.*/hasImageBytes()
    {
        DmlBlipFill blip = getBlipFill();
        if ((blip == null) || (blip.getBlip() == null))
            return false;

        return blip.getBlip().hasEmbedImage();
    }

    private DmlBlipFill getBlipFill()
    {
        DmlPicture pict = ms.as(mDrawingML.getDmlNode(), DmlPicture.class);
        if (pict != null)
            return pict.getBlipFill();

        DmlFillableNode fillableNode = ms.as(mDrawingML.getDmlNode(), DmlFillableNode.class);
        if (fillableNode != null)
            return ms.as(fillableNode.getFill(), DmlBlipFill.class);

        return null;
    }

    /// <summary>
    /// Returns either existing blip fill, or create new.
    /// </summary>
    private DmlBlipFill fetchBlip()
    {
        DmlBlipFill blip = getBlipFill();
        if (blip != null)
            return blip;

        DmlPicture pict = ms.as(mDrawingML.getDmlNode(), DmlPicture.class);
        if (pict != null)
            pict.setBlipFill(new DmlBlipFill());

        DmlFillableNode fillableNode = ms.as(mDrawingML.getDmlNode(), DmlFillableNode.class);
        if (fillableNode != null)
            fillableNode.setFill(new DmlBlipFill());

        return getBlipFill();
    }

    private /*final*/ ShapeBase mDrawingML;
}

