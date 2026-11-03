// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a "line to" path part.
/// </summary>
/// <remarks>
/// 20.1.9.13 lnTo (Draw Line To)
/// This element specifies the drawing of a straight line from the current pen position to the
/// new point specified. This line becomes part of the shape geometry, representing a side of the shape.
/// The coordinate system used when specifying this line is the path coordinate system.
/// </remarks>
class DmlLineTo implements IDmlPathPart, Cloneable
{
    DmlLineTo()
    {
    }

    DmlLineTo(DmlAdjustablePoint point)
    {
        mPoint = point;
    }

    /// <summary>
    /// Clones this instance of <see cref="DmlLineTo"/>.
    /// </summary>
    public IDmlPathPart deepClone()
    {
        DmlLineTo lhs = (DmlLineTo)memberwiseClone();

        if (mPoint != null)
            lhs.mPoint = mPoint.deepClone();

        return lhs;
    }

    public void write(NrxXmlBuilder builder)
    {
        builder.startElement("a:lnTo");
        DmlWriterUtil.writeDmlAdjustablePoint(getPoint(), builder);
        builder.endElement("a:lnTo");
    }

    public /*DmlPathPartType*/int getPathPartType() { return DmlPathPartType.LINE_TO; }

    DmlAdjustablePoint getPoint() { return mPoint; }
    void setPoint(DmlAdjustablePoint value) { mPoint = value; }

    private DmlAdjustablePoint mPoint;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

