// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msObject;


/// <summary>
/// 20.1.8.56 stretch (Stretch)
/// This element specifies that a BLIP should be stretched to fill
/// the target rectangle. The other option is a tile where a BLIP
/// is tiled to fill the available area.
/// </summary>
class DmlBlipFillStretch implements IDmlBlipFillMode
{
    public IDmlBlipFillMode deepClone()
    {
        DmlBlipFillStretch blipFillStretch = new DmlBlipFillStretch();
        if (mFillRectangle != null)
            blipFillStretch.mFillRectangle = mFillRectangle.deepClone();
        return blipFillStretch;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlBlipFillStretch value = (DmlBlipFillStretch)obj;

        return msObject.equals(value.getFillRectangle(), getFillRectangle());
    }

    public /*override*/ int hashCode()
    {
        return getFillRectangle().hashCode();
    }

    public void write(IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isIsoStrict = (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        builder.startElement("a:stretch");
        DmlWriterUtil.writePercentageOffsetRectangle(getFillRectangle(), "a", "fillRect", true, builder, isIsoStrict);
        builder.endElement("a:stretch");
    }

    public /*FillTypeCore*/int getFillType() { return FillTypeCore.PICTURE; }

    DmlPercentageOffsetRectangle getFillRectangle()
    {
        if (mFillRectangle == null)
            mFillRectangle = new DmlPercentageOffsetRectangle();

        return mFillRectangle;
    }
    void setFillRectangle(DmlPercentageOffsetRectangle value) { mFillRectangle = value; }

    public 
    boolean /*IDmlBlipFillMode.*/hasOffsets() { return !mFillRectangle.isEmpty(); }

    private DmlPercentageOffsetRectangle mFillRectangle;
}

