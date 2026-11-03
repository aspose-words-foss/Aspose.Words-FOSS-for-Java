// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a "move to" path part.
/// </summary>
/// <remarks>
/// 20.1.9.14 moveTo (Move Path To)
/// This element specifies a set of new coordinates to move the shape cursor to. This element is only
/// used for drawing a custom geometry. When this element is utilized the pt element is used to specify
/// a new set of shape coordinates that the shape cursor should be moved to. This does not draw a line
/// or curve to this new position from the old position but simply move the cursor to a new starting
/// position. It is only when a path drawing element such as lnTo is used that a portion of the path is drawn.
/// </remarks>
class DmlMoveTo implements IDmlPathPart, Cloneable
{
    DmlMoveTo()
    {
    }

    DmlMoveTo(DmlAdjustablePoint point)
    {
        mPoint = point;
    }

    /// <summary>
    /// Clones this instance of <see cref="DmlMoveTo"/>.
    /// </summary>
    public IDmlPathPart deepClone()
    {
        DmlMoveTo lhs = (DmlMoveTo)memberwiseClone();

        if (mPoint != null)
            lhs.mPoint = mPoint.deepClone();

        return lhs;
    }

    public void write(NrxXmlBuilder builder)
    {
        builder.startElement("a:moveTo");
        DmlWriterUtil.writeDmlAdjustablePoint(getPoint(), builder);
        builder.endElement("a:moveTo");
    }

    public /*DmlPathPartType*/int getPathPartType() { return DmlPathPartType.MOVE_TO; }

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

