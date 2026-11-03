// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a quadratic bezier part.
/// </summary>
/// <remarks>
/// 20.1.9.21 quadBezTo (Draw Quadratic Bezier Curve To)
/// This element specifies to draw a quadratic bezier curve along the specified points.
/// To specify a quadratic bezier curve there needs to be 2 points specified.
/// The first is a control point used in the quadratic bezier calculation and
/// the last is the ending point for the curve. The coordinate system used for
/// this type of curve is the path coordinate system as this element is path specific.
/// </remarks>
class DmlQuadraticBezierTo implements IDmlPathPart, Cloneable
{
    /// <summary>
    /// Constructor.
    /// </summary>
    /// <param name="pathPoints">The path points.</param>
    DmlQuadraticBezierTo(DmlAdjustablePoint[] pathPoints)
    {
        if (pathPoints.length > 0)
            setControlPoint((pathPoints[0] != null) ? pathPoints[0] : new DmlAdjustablePoint());
        if (pathPoints.length > 1)
            setEndPoint((pathPoints[1] != null) ? pathPoints[1] : new DmlAdjustablePoint());
    }

    /// <summary>
    /// Clones this instance of <see cref="DmlQuadraticBezierTo"/>.
    /// </summary>
    public IDmlPathPart deepClone()
    {
        DmlQuadraticBezierTo lhs = (DmlQuadraticBezierTo)memberwiseClone();

        if (mControlPoint != null)
            lhs.mControlPoint = mControlPoint.deepClone();

        if (mEndPoint != null)
            lhs.mEndPoint = mEndPoint.deepClone();

        return lhs;
    }

    public void write(NrxXmlBuilder builder)
    {
        builder.startElement("a:quadBezTo");
        DmlWriterUtil.writeDmlAdjustablePoint(getControlPoint(), builder);
        DmlWriterUtil.writeDmlAdjustablePoint(getEndPoint(), builder);
        builder.endElement("a:quadBezTo");
    }

    public /*DmlPathPartType*/int getPathPartType() { return DmlPathPartType.QUADRATIC_BEZIER_TO; }

    /// <summary>
    /// Gets or sets the control point.
    /// </summary>
    DmlAdjustablePoint getControlPoint() { return mControlPoint; }
    void setControlPoint(DmlAdjustablePoint value) { mControlPoint = value; }

    /// <summary>
    /// Gets or sets the end point.
    /// </summary>
    DmlAdjustablePoint getEndPoint() { return mEndPoint; }
    void setEndPoint(DmlAdjustablePoint value) { mEndPoint = value; }

    private DmlAdjustablePoint mControlPoint;
    private DmlAdjustablePoint mEndPoint;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

