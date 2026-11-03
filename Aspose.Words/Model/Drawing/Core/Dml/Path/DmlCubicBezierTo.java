// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a cubic bezier part.
/// </summary>
/// <remarks>
/// 20.1.9.7 cubicBezTo (Draw Cubic Bezier Curve To)
/// This element specifies to draw a cubic bezier curve along the specified points.
/// To specify a cubic bezier curve there needs to be 3 points specified. The first
/// two are control points used in the cubic bezier calculation and the last is the
/// ending point for the curve. The coordinate system used for this kind of curve is the
/// path coordinate system as this element is path specific.
/// </remarks>
class DmlCubicBezierTo implements IDmlPathPart, Cloneable
{
    /// <summary>
    /// Constructor
    /// </summary>
    /// <param name="pathPoints">The path points.</param>
    DmlCubicBezierTo(DmlAdjustablePoint[] pathPoints)
    {
        if (pathPoints.length > 0)
            setControlPoint1((pathPoints[0] != null) ? pathPoints[0] : new DmlAdjustablePoint());
        if (pathPoints.length > 1)
            setControlPoint2((pathPoints[1] != null) ? pathPoints[1] : new DmlAdjustablePoint());
        if (pathPoints.length > 2)
            setEndPoint((pathPoints[2] != null) ? pathPoints[2] : new DmlAdjustablePoint());
    }

    /// <summary>
    /// Clones this instance of <see cref="DmlCubicBezierTo"/>.
    /// </summary>
    public IDmlPathPart deepClone()
    {
        DmlCubicBezierTo lhs = (DmlCubicBezierTo)memberwiseClone();

        if (mControlPoint1 != null)
            lhs.mControlPoint1 = mControlPoint1.deepClone();

        if (mControlPoint2 != null)
            lhs.mControlPoint2 = mControlPoint2.deepClone();

        if (mEndPoint != null)
            lhs.mEndPoint = mEndPoint.deepClone();

        return lhs;
    }

    public void write(NrxXmlBuilder builder)
    {
        builder.startElement("a:cubicBezTo");
        DmlWriterUtil.writeDmlAdjustablePoint(getControlPoint1(), builder);
        DmlWriterUtil.writeDmlAdjustablePoint(getControlPoint2(), builder);
        DmlWriterUtil.writeDmlAdjustablePoint(getEndPoint(), builder);
        builder.endElement("a:cubicBezTo");
    }

    public /*DmlPathPartType*/int getPathPartType() { return DmlPathPartType.CUBIC_BEZIER_TO; }

    /// <summary>
    /// Gets or sets the control point1.
    /// </summary>
    DmlAdjustablePoint getControlPoint1() { return mControlPoint1; }
    void setControlPoint1(DmlAdjustablePoint value) { mControlPoint1 = value; }

    /// <summary>
    /// Gets or sets the control point2.
    /// </summary>
    DmlAdjustablePoint getControlPoint2() { return mControlPoint2; }
    void setControlPoint2(DmlAdjustablePoint value) { mControlPoint2 = value; }

    /// <summary>
    /// Gets or sets the end point.
    /// </summary>
    DmlAdjustablePoint getEndPoint() { return mEndPoint; }
    void setEndPoint(DmlAdjustablePoint value) { mEndPoint = value; }

    private DmlAdjustablePoint mControlPoint1;
    private DmlAdjustablePoint mControlPoint2;
    private DmlAdjustablePoint mEndPoint;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

