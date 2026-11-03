// Parts of this file are ported to Java by Aspose from the .NET sources; the same code is part of
// https://github.com/dotnet/runtime : src/libraries/System.Drawing.Primitives/src/System/Drawing/Rectangle.cs
//
// Licensed to the .NET Foundation under one or more agreements.
// The .NET Foundation licenses this file to you under the MIT license, see dotnet-runtime-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Drawing;

public final /*struct*/ class Rectangle
{
    //JAVA-added. Helps to emulate .Net automatic struct initialization.
    public Rectangle()
    {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public Rectangle(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(/*msPoint*/long location, /*Size*/long size)
    {
        this.x = msPoint.getX(location);
        this.y = msPoint.getY(location);
        this.width = msSize.getWidth(size);
        this.height = msSize.getHeight(size);
    }

    public static Rectangle fromLTRB(int left, int top, int right, int bottom)
    {
        return new Rectangle(left, top, right - left, bottom - top);
    }

    public /*msPoint*/long getLocation()
    {
        return msPoint.ctor(this.x, this.y);
    }

    public /*Size*/long getSize()
    {
        return msSize.ctor(this.width, this.height);
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public int getLeft()
    {
        return this.x;
    }

    public int getTop()
    {
        return this.y;
    }

    public int getRight()
    {
        return (this.x + this.width);
    }

    public int getBottom()
    {
        return (this.y + this.height);
    }

    public boolean isEmpty()
    {
        return ((((this.height == 0) && (this.width == 0)) && (this.x == 0)) && (this.y == 0));

    }

    //JAVA-added
    // This is needed because java equals is overridden.
    // Implementation by Rotor.
    public int hashCode()
    {
        return (int)((long)x ^
                     (((long)y << 13) | ((long)y >> 19)) ^
                     (((long)width << 26) | ((long)width >>  6)) ^
                     (((long)height <<  7) | ((long)height >> 25)));
    }

    // This overrides java equals.
    // Implementation by RK.
    public boolean equals(Object obj)
    {
        return (obj instanceof Rectangle) && equals(this, (Rectangle)obj);
    }

    // This is called by the autoporter when it encounters == in C#. It is the actual implementation of equality.
    // Implementation by RK.
    public static boolean equals(Rectangle a, Rectangle b)
    {
        if (a == b)
            return true;

        return ((a != null) && (b != null) &&
                (a.x == b.x) &&
                (a.y == b.y) &&
                (a.width == b.width) &&
                (a.height == b.height));
    }

    public boolean contains(int x, int y)
    {
        return this.x <= x &&
               x < this.x + this.width &&
               this.y <= y &&
               y < this.y + this.height;
    }

    public boolean contains(/*msPoint*/long pt)
    {
        return contains(msPoint.getX(pt), msPoint.getY(pt));
    }

    public boolean contains(Rectangle rect)
    {
        return (this.x <= rect.x) &&
               ((rect.x + rect.width) <= (this.x + this.width)) &&
               (this.y <= rect.y) &&
               ((rect.y + rect.height) <= (this.y + this.height));
    }

    public boolean intersectsWith(Rectangle rect)
    {
        return (rect.x < this.x + this.width) &&
               (this.x < (rect.x + rect.width)) &&
               (rect.y < this.y + this.height) &&
               (this.y < rect.y + rect.height);
    }

    public String toString()
    {
        return "{X=" + x + ",Y=" + y + ",Width=" + width + ",Height=" + height + "}";
    }

    public static Rectangle intersect(Rectangle a, Rectangle b)
    {
        int left = Math.max(a.getLeft(), b.getLeft());
        int top = Math.max(a.getTop(), b.getTop());
        int right = Math.min(a.getRight(), b.getRight());
        int bottom = Math.min(a.getBottom(), b.getBottom());
        if ((right >= left) && (bottom >= top))
            return fromLTRB(left, top, right, bottom);
        else
            return Empty;
    }

    public static Rectangle union(Rectangle a, Rectangle b)
    {
        int left = Math.min(a.getLeft(), b.getLeft());
        int top = Math.min(a.getTop(), b.getTop());
        int right = Math.max(a.getRight(), b.getRight());
        int bottom = Math.max(a.getBottom(), b.getBottom());
        return fromLTRB(left, top, right, bottom);
    }

    public static Rectangle round(RectangleF rectangleF)
    {
        return new Rectangle(
                Math.round(rectangleF.getX()),
                Math.round(rectangleF.getY()),
                Math.round(rectangleF.getWidth()),
                Math.round(rectangleF.getHeight()));
    }

// JAVA-deleted: because need it to be immutable. Do not provide setters.
//    public void setLocation(Point value)
//    public void setSize(Size value)
//    public void setX(int value)
//    public void setY(int value)
//    public void setWidth(int value)
//    public void setHeight(int value)
//    public void inflate(int x, int y)
//    public void inflate(Size size)
//    public static Rectangle inflate(Rectangle rect, int x, int y)
//    public void intersect(Rectangle rect)
//    public void offset(Point pos)
//    public void offset(int x, int y)

	//JAVA-added
	public java.awt.Rectangle toJava()
	{
		return new java.awt.Rectangle(x, y, width, height);
	}

	//JAVA-added
	/**
	 * This static method should be used instead of rectangle.toJava() to avoid NullPointerException
	 * if rectangle == null. For instance, the method used by ApiChangeMap.
	 */
	public static java.awt.Rectangle toJava(Rectangle rectangle)
	{
		return rectangle == null ? null : rectangle.toJava();
	}

	//JAVA-added
	public static Rectangle fromJava(java.awt.Rectangle javaRectangle)
	{
		return new Rectangle(javaRectangle.x, javaRectangle.y, javaRectangle.width, javaRectangle.height);
	}

    public static final Rectangle Empty = new Rectangle();

    private int x;
    private int y;
    private int width;
    private int height;
}

