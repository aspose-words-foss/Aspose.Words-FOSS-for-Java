// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/09/2016 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Shape connection site.
/// </summary>
/// <remarks>
/// 20.1.9.9 cxn (Shape Connection Site).
/// This element specifies the existence of a connection site on a custom shape. A connection site allows a cxnSp
/// to be attached to this shape. This connection is maintained when the shape is repositioned within the document.
/// It should be noted that this connection is placed within the shape bounding box using the transform coordinate
/// system which is also called the shape coordinate system, as it encompasses the entire shape. The width and
/// height for this coordinate system are specified within the ext transform element. 
/// </remarks>
class DmlConnectionSite implements Cloneable
{
    /// <summary>
    /// Ctr.
    /// </summary>
    /// <param name="angle">Angle for incoming connector.</param>
    DmlConnectionSite(String angle)
    {
        mAngle = new DmlAdjustableAngle(angle);
    }

    /// <summary>
    /// Clones this instance of <see cref="DmlConnectionSite"/>.
    /// </summary>
    public DmlConnectionSite deepClone()
    {
        DmlConnectionSite lhs = (DmlConnectionSite)memberwiseClone();

        if (mCoordinates != null)
            lhs.mCoordinates = mCoordinates.deepClone();

        // Cloning of the mAngle field is skipped since it is immutable.

        return lhs;
    }

    /// <summary>
    /// Specifies the incoming connector angle. 
    /// </summary>
    DmlAdjustableAngle getAngle() { return mAngle; }
    void setAngle(DmlAdjustableAngle value) { mAngle = value; }

    /// <summary>
    /// Specifies a position coordinate within the shape bounding box.
    /// </summary>
    DmlAdjustablePoint getCoordinates() { return mCoordinates; }
    void setCoordinates(DmlAdjustablePoint value) { mCoordinates = value; }

    private DmlAdjustableAngle mAngle;
    private DmlAdjustablePoint mCoordinates;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

