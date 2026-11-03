// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/06/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.HashUtil;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ImageUtil;
import com.aspose.StringUtil;
import com.aspose.words.ref.RefBoolean;


/// <summary>
/// Represents a picture.
/// </summary>
/// <remarks>
/// 20.1.2.2.30 pic (Picture)
/// This element specifies the existence of a picture object within the document.
/// </remarks>
class DmlPicture extends DmlShapeBase
{
    /*override*/ DmlNode deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        DmlPicture lhs = (DmlPicture)super.deepClone(isCloneChildren, cloningListener);
        if (mBlipFill != null)
            lhs.mBlipFill = (DmlBlipFill)mBlipFill.deepClone();
        return lhs;
    }

    public /*override*/ boolean hasTransparency() throws Exception
    {
        return super.hasTransparency() || pictureHasAlphaChannel();
    }

    private boolean pictureHasAlphaChannel() throws Exception
    {
        // Check whether image has alpha channel.
        if (getBlipFill().getImageBytes() != null)
        {
            // Since picture might be changed upon document processing 
            // it is required to get transparency for image that is in blip now. 
            // It is expensive to get HasAlphaChannel every time the property is requested, 
            // so calculate the current image hash and cache HasAlphaChannel value for it in the collection.
            int pictureHash = HashUtil.getSHA512Hash(getBlipFill().getImageBytes()).hashCode();

            HashMap<Integer, Boolean> transparencyFlagCache = (getDml().getDocument() != null)
                ? getDml().getDocument().getImageTransparencyFlagCache()
                : null;

            boolean hasAlphaChannel = false;
            RefBoolean referenceToHasAlphaChannel = new RefBoolean(hasAlphaChannel);
            boolean outRefCondition0 = (transparencyFlagCache != null) && msDictionary.tryGetValue(transparencyFlagCache, pictureHash, /*out*/ referenceToHasAlphaChannel);
            hasAlphaChannel = referenceToHasAlphaChannel.get();
            if (outRefCondition0)
                return hasAlphaChannel;
            try
            {
                hasAlphaChannel = ImageUtil.hasAlphaChannel(getBlipFill().getImageBytes());
            }
            catch(Exception e)
            {
                // There might be invalid image data, consider such images as not transparent.
                hasAlphaChannel = false;
            }

            if (transparencyFlagCache != null)
                msDictionary.add(transparencyFlagCache, pictureHash, hasAlphaChannel);

            return hasAlphaChannel;
        }

        return false;
    }

    /*override*/ /*DmlNodeType*/int getDmlNodeType() { return DmlNodeType.PICTURE; }

    DmlBlipFill getBlipFill() { return mBlipFill; }
    void setBlipFill(DmlBlipFill value) { mBlipFill = value; }

    /*override*/ void setHRef(String href)
    {
        super.setHRef(href);

        if (!StringUtil.hasChars(href) &&
            ((getNonVisualPr() == null) ||
            (getNonVisualPr().getNvDrawingProperties() == null) ||
            (getNonVisualPr().getNvDrawingProperties().getHlinkClick() == null)))
        {
            return;
        }

        setNonVisualPr((getNonVisualPr() == null) ? new DmlNvPrPicture() : getNonVisualPr());
        DmlNvDrawingProperties nvDrawingPr = getNonVisualPr().getNvDrawingProperties();

        nvDrawingPr = (nvDrawingPr == null) ? new DmlNvDrawingProperties(mShape.getId(), "") : nvDrawingPr;
        nvDrawingPr.setHlinkClick((nvDrawingPr.getHlinkClick() == null) ? new DmlHlink() : nvDrawingPr.getHlinkClick());

        // WORDSNET-21498 Update non-visual drawing properties while changing picture hyperlink.
        nvDrawingPr.getHlinkClick().setId(href);
        getNonVisualPr().setNvDrawingProperties(nvDrawingPr);
    }

    private DmlBlipFill mBlipFill;
}

