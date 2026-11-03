// This file is ported to Java by Aspose from the .NET sources; the same code is part of
// https://github.com/dotnet/runtime : src/libraries/System.Drawing.Primitives/src/System/Drawing/RectangleF.cs
//
// Licensed to the .NET Foundation under one or more agreements.
// The .NET Foundation licenses this file to you under the MIT license, see dotnet-runtime-LICENSE.txt in com/aspose/ms/System.

// ported 18.03.2009 by Konstantin Sidorenko
package com.aspose.ms.System.Drawing;

import java.awt.geom.Rectangle2D;

/**
 * JAVA: stolen from Rotor.
 * Use toJava() and fromJava() methods to convert to/from java analog java.awt.geom.Rectangle2D.Float.
 * Note: .Net struct initialized automatically. In java developer should add intitialization when needed.
 *
 *  Stores the location and size of a rectangular region. For
 *  more advanced region functions use a <see cref='System.Drawing.Region'/> object.
 */
public final /*struct*/ class RectangleF
{
	//JAVA-added. Helps to emulate .Net automatic struct initialization.
	public RectangleF()
	{
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}

	public RectangleF(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public RectangleF(/*msPointF*/long location, /*SizeF*/long size)
	{
		this.x = msPointF.getX(location);
		this.y = msPointF.getY(location);
		this.width = msSizeF.getWidth(size);
		this.height = msSizeF.getHeight(size);
	}

	public static RectangleF fromLTRB(float left, float top, float right, float bottom)
	{
		return new RectangleF(left,
		                      top,
		                      right - left,
		                      bottom - top);
	}

	public /*msPointF*/long getLocation()
	{
		return msPointF.ctor(x, y);
	}

	public /*SizeF*/long getSize()
	{
		return msSizeF.ctor(width, height);
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

	public float getLeft()
	{
		return x;
	}

	public float getTop()
	{
		return y;
	}

	public float getRight()
	{
		return x + width;
	}

	public float getBottom()
	{
		return y + height;
	}

	public boolean isEmpty()
	{
		return (width <= 0 )|| (height <= 0);
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
        return (obj instanceof RectangleF) && equals(this, (RectangleF)obj);
    }

    // This is called by the autoporter when it encounters == in C#. It is the actual implementation of equality.
    // Implementation by RK.
    public static boolean equals(RectangleF a, RectangleF b)
    {
        if (a == b)
            return true;

        return ((a != null) && (b != null) &&
                (a.x == b.x) &&
                (a.y == b.y) &&
                (a.width == b.width) &&
                (a.height == b.height));
    }

	//JAVA-deleted '==', '!=' operators: use equals(RectangleF) instead.

	public boolean contains(float x, float y)
	{
		return this.x <= x &&
		       x < this.x + this.width &&
		       this.y <= y &&
		       y < this.y + this.height;
	}

	public boolean contains(/*msPointF*/long pt)
	{
		return contains(msPointF.getX(pt), msPointF.getY(pt));
	}

	public boolean contains(RectangleF rect)
	{
		return (this.x <= rect.x) &&
		       ((rect.x + rect.width) <= (this.x + this.width)) &&
		       (this.y <= rect.y) &&
		       ((rect.y + rect.height) <= (this.y + this.height));
	}

	public boolean intersectsWith(RectangleF rect)
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

    public static RectangleF intersect(RectangleF a, RectangleF b)
    {
        float left = Math.max(a.getLeft(), b.getLeft());
        float top = Math.max(a.getTop(), b.getTop());
        float right = Math.min(a.getRight(), b.getRight());
        float bottom = Math.min(a.getBottom(), b.getBottom());
        if ((right >= left) && (bottom >= top))
            return fromLTRB(left, top, right, bottom);
        else
            return Empty;
    }

    public static RectangleF union(RectangleF a, RectangleF b)
    {
        float left = Math.min(a.getLeft(), b.getLeft());
        float top = Math.min(a.getTop(), b.getTop());
        float right = Math.max(a.getRight(), b.getRight());
        float bottom = Math.max(a.getBottom(), b.getBottom());
        return fromLTRB(left, top, right, bottom);
    }

	public void offset(/*msPointF*/long pos)
	{
		offset(msPointF.getX(pos), msPointF.getY(pos));
	}

	public void offset(float x, float y)
	{
		this.x += x;
		this.y += y;
	}

    public void inflate(float x, float y)
    {
        this.x -= x;
        this.y -= y;
        this.width += x * 2;
        this.height += y * 2;
    }

	//JAVA was: implicit casting operator
	public static RectangleF fromRectangle(Rectangle r)
	{
	    return new RectangleF(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}

	//JAVA-added
    public Rectangle toRectangle()
    {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

	//JAVA-added
    public Rectangle2D.Float toJava()
    {
        return new Rectangle2D.Float(x, y, width, height);
    }

	//JAVA-added
	/**
	 * This static method should be used instead of rectangleF.toJava() to avoid NullPointerException
	 * if rectangleF == null. For instance, the method used by ApiChangeMap.
	 */
	public static Rectangle2D.Float toJava(RectangleF rectangleF)
	{
		return rectangleF == null ? null : rectangleF.toJava();
	}

	//JAVA-added
    public static RectangleF fromJava(Rectangle2D.Float value)
    {
        return new RectangleF(value.x, value.y, value.width, value.height);
    }

    public static RectangleF fromJava(Rectangle2D value)
    {
        if (value instanceof Rectangle2D.Float)
            return fromJava((Rectangle2D.Float) value);

        return new RectangleF((float) value.getX(), (float) value.getY(), (float) value.getWidth(), (float) value.getHeight());
    }

    public static RectangleF inflate(RectangleF rect, float x, float y)
    {
        return new RectangleF(rect.x - x, rect.y - y, rect.width + x * 2, rect.height + y * 2);
    }

// JAVA-deleted: This class has to be immutable. Do not provide setters.
//    public void setLocation(PointF value)
//    public void setSize(SizeF value)
//    public void setX(float value)
//    public void setY(float value)
//    public void setWidth(float value)
//    public void setHeight(float value)
//    public void inflate(float x, float y)
//    public void inflate(SizeF size)
//    public void intersect(RectangleF rect)
//    public void offset(PointF pos)
//    public void offset(float x, float y)

    public static final RectangleF Empty = new RectangleF();

    private float x;
    private float y;
    private float width;
    private float height;
}
