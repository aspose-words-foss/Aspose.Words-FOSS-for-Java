// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;


/// <summary>
/// 20.1.8.58 tile (Tile)
/// This element specifies that a BLIP should be tiled to fill the available space.
/// This element defines a "tile" rectangle within the bounding box. The image is
/// encompassed within the tile rectangle, and the tile rectangle is tiled across
/// the bounding box to fill the entire area.
/// </summary>
class DmlBlipFillTile implements IDmlBlipFillMode
{
    public IDmlBlipFillMode deepClone()
    {
        DmlBlipFillTile tile = new DmlBlipFillTile();
        tile.setAlignment(getAlignment());
        tile.setHorizontalOffset(getHorizontalOffset());
        tile.setHorizontalRatio(getHorizontalRatio());
        tile.setVerticalOffset(getVerticalOffset());
        tile.setVerticalRatio(getVerticalRatio());
        tile.setTileFlipMode(getTileFlipMode());
        return tile;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlBlipFillTile value = (DmlBlipFillTile)obj;

        return (value.getAlignment() == getAlignment()) &&
               (value.getTileFlipMode() == getTileFlipMode()) &&
               MathUtil.areEqual(value.getHorizontalOffset(), getHorizontalOffset()) &&
               MathUtil.areEqual(value.getHorizontalRatio(), getHorizontalRatio()) &&
               MathUtil.areEqual(value.getVerticalOffset(), getVerticalOffset()) &&
               MathUtil.areEqual(value.getVerticalRatio(), getVerticalRatio());

    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        hash ^= /*enum.hashCode()=(int)value*/getAlignment();
        hash ^= msNumber.doubleHashCode(getHorizontalOffset());
        hash ^= msNumber.doubleHashCode(getHorizontalRatio());
        hash ^= msNumber.doubleHashCode(getVerticalOffset());
        hash ^= msNumber.doubleHashCode(getVerticalRatio());
        hash ^= /*enum.hashCode()=(int)value*/getTileFlipMode();
        return hash;
    }

    public void write(IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isIsoStrict = (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);

        builder.startElement("a:tile");
        builder.writeAttribute("tx", getHorizontalOffset());
        builder.writeAttribute("ty", getVerticalOffset());
        builder.writeAttribute("sx", DmlPercentageUtil.toPercentOrDmlPercent(getHorizontalRatio(), isIsoStrict));
        builder.writeAttribute("sy", DmlPercentageUtil.toPercentOrDmlPercent(getVerticalRatio(), isIsoStrict));
        builder.writeAttribute("flip", DmlEnum.tileFlipModeToDml(getTileFlipMode()));
        builder.writeAttribute("algn", DmlEnum.rectangleAlignmentToDml(getAlignment()));
        builder.endElement("a:tile");
    }

    public /*FillTypeCore*/int getFillType() { return FillTypeCore.TEXTURE; }

    /// <summary>
    /// Specifies where to align the first tile with respect to the shape.
    /// Alignment happens after the scaling, but before the additional offset.
    /// </summary>
    /*DmlRectangleAlignment*/int getAlignment() { return mAlignment; }
    void setAlignment(/*DmlRectangleAlignment*/int value) { mAlignment = value; }

    /// <summary>
    /// Specifies the direction(s) in which to flip the source image while tiling.
    /// Images can be flipped horizontally, vertically, or in both directions
    /// to fill the entire region.
    /// </summary>
    /*DmlTileFlipMode*/int getTileFlipMode() { return mTileFlipMode; }
    void setTileFlipMode(/*DmlTileFlipMode*/int value) { mTileFlipMode = value; }

    /// <summary>
    /// Specifies the amount to horizontally scale the source rectangle.
    /// </summary>
    double getHorizontalRatio() { return mHorizontalRatio; }
    void setHorizontalRatio(double value) { mHorizontalRatio = value; }

    /// <summary>
    /// Specifies the amount to vertically  scale the source rectangle.
    /// </summary>
    double getVerticalRatio() { return mVerticalRatio; }
    void setVerticalRatio(double value) { mVerticalRatio = value; }

    /// <summary>
    /// Specifies additional horizontal offset after alignment.
    /// </summary>
    double getHorizontalOffset() { return mHorizontalOffset; }
    void setHorizontalOffset(double value) { mHorizontalOffset = value; }

    /// <summary>
    /// Specifies additional vertical offset after alignment.
    /// </summary>
    double getVerticalOffset() { return mVerticalOffset; }
    void setVerticalOffset(double value) { mVerticalOffset = value; }

    public boolean /*IDmlBlipFillMode.*/hasOffsets() { return false; }

    private /*DmlRectangleAlignment*/int mAlignment;
    private double mHorizontalOffset;
    private double mHorizontalRatio = 1.0;
    private /*DmlTileFlipMode*/int mTileFlipMode;
    private double mVerticalOffset;
    private double mVerticalRatio = 1.0;
}

