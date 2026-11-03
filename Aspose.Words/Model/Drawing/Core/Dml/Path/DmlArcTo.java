// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents an arcTo element.
/// </summary>
/// <remarks>
/// 20.1.9.4 arcTo (Draw Arc To)
/// This element specifies the existence of an arc within a shape path.
/// It draws an arc with the specified parameters from the current pen position to the
/// new point specified. An arc is a line that is bent based on the shape of a supposed circle.
/// The length of this arc is determined by specifying both a start angle and an ending angle
/// that act together to effectively specify an end point for the arc.
/// </remarks>
class DmlArcTo implements IDmlPathPart, Cloneable
{
    public /*DmlPathPartType*/int getPathPartType() { return DmlPathPartType.ARC_TO; }

    /// <summary>
    /// Clones this instance of <see cref="DmlArcTo"/>.
    /// </summary>
    public IDmlPathPart deepClone()
    {
        DmlArcTo lhs = (DmlArcTo)memberwiseClone();

        if (mHeightRadius != null)
            lhs.mHeightRadius = mHeightRadius.deepClone();

        if (mWidthRadius != null)
            lhs.mWidthRadius = mWidthRadius.deepClone();

        // Cloning the DmlAdjustableAngle fields is skipped since they are immutable.

        return lhs;
    }

    private static double getParametricAngle(double noScaledWidthRadius, double noScaledHeightRadius,  double angle)
    {
        return Math.atan2(
            ((noScaledHeightRadius < 1) ? 1.0 : 1.0d / noScaledHeightRadius)  * Math.sin(angle),
            ((noScaledWidthRadius < 1) ? 1.0 : 1.0d / noScaledWidthRadius) * Math.cos(angle));
    }

    public void write(NrxXmlBuilder builder)
    {
        builder.writeElementWithAttributes("a:arcTo",
            "hR", getHeightRadius().getString(),
            "wR", getWidthRadius().getString(),
            "stAng", getStartAngle().getString(),
            "swAng", getSwingAngle().getString());
    }

    /// <summary>
    /// Gets or sets the height radius.
    /// </summary>
    /// <remarks>
    /// This attribute specifies the height radius of the supposed circle being used to
    ///  draw the arc. This gives the circle a total height of (2 * hR). This total height
    /// could also be called it's vertical diameter as it is the diameter for the y axis only.
    /// </remarks>
    /// <value>The height radius.</value>
    DmlAdjustableCoordinate getHeightRadius() { return mHeightRadius; }
    void setHeightRadius(DmlAdjustableCoordinate value) { mHeightRadius = value; }

    /// <summary>
    /// Gets or sets the width radius.
    /// </summary>
    /// <remarks>
    /// This attribute specifies the width radius of the supposed circle being used to draw
    /// the arc. This gives the circle a total width of (2 * wR). This total width could also
    /// be called it's horizontal diameter as it is the diameter for the x axis only.
    /// </remarks>
    /// <value>The width radius.</value>
    DmlAdjustableCoordinate getWidthRadius() { return mWidthRadius; }
    void setWidthRadius(DmlAdjustableCoordinate value) { mWidthRadius = value; }

    /// <summary>
    /// Gets or sets the start angle.
    /// </summary>
    /// <remarks>
    /// Specifies the start angle for an arc. This angle specifies what angle along the supposed
    /// circle path is used as the start position for drawing the arc. This start angle is locked
    /// to the last known pen position in the shape path. Thus guaranteeing a continuos shape path.
    /// </remarks>
    /// <value>The start angle.</value>
    DmlAdjustableAngle getStartAngle() { return mStartAngle; }
    void setStartAngle(DmlAdjustableAngle value) { mStartAngle = value; }

    /// <summary>
    /// Gets or sets the swing angle.
    /// </summary>
    /// <remarks>
    /// Specifies the swing angle for an arc. This angle specifies how far angle-wise along the
    /// supposed circle path the arc is extended. The extension from the start angle is always in
    /// the clockwise direction around the supposed circle.
    /// </remarks>
    /// <value>The swing angle.</value>
    DmlAdjustableAngle getSwingAngle() { return mSwingAngle; }
    void setSwingAngle(DmlAdjustableAngle value) { mSwingAngle = value; }

    private DmlAdjustableCoordinate mHeightRadius;
    private DmlAdjustableAngle mStartAngle;
    private DmlAdjustableAngle mSwingAngle;
    private DmlAdjustableCoordinate mWidthRadius;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

