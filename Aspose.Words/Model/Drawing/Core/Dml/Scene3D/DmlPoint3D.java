// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.5.1 anchor (Anchor Point)
/// This element specifies a point in 3D space.
/// </summary>
/*struct*/ final class DmlPoint3D
{
	public DmlPoint3D(){}
	
    /// <summary>
    /// X-Coordinate in 3D space.
    /// </summary>
    double getX() { return mX; }
    void setX(double value) { mX = value; }

    /// <summary>
    /// Y-Coordinate in 3D space.
    /// </summary>
    double getY() { return mY; }
    void setY(double value) { mY = value; }

    /// <summary>
    /// Z-Coordinate in 3D space.
    /// </summary>
    double getZ() { return mZ; }
    void setZ(double value) { mZ = value; }

    private double mX;
    private double mY;
    private double mZ;
}

