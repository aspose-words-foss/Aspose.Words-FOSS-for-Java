// ported 18.03.2009 by Konstantin Sidorenko
// 08.03.2014 Nail Islamov

package com.aspose.ms.System.Drawing;

import java.awt.geom.Point2D;


/**
 * Static class with extensions methods to emulate .Net PointF stuct in Java using long value-type.
 */
public final class msPointF
{
	private msPointF()
	{
	}

	/**
	 * Create a new Point object at the given location
	 */
	public static long ctor(float x, float y)
	{
        // floatToRawIntBits (note Raw) is used cause it faster (3-5 times) and we don't need check NaN in int.
        int xi = Float.floatToRawIntBits(x);
        int yi = Float.floatToRawIntBits(y);
        return  (xi & 0xFFFFFFFFL) | ((long) yi << 32);
	}

	public static long ctor()
	{
		return Empty;
	}

	public static boolean isEmpty(long point)
	{
		return point == 0;
	}

	public static float getX(long point)
	{
        int x = (int) point;
        return Float.intBitsToFloat(x);
	}

    public static long setX(long point, float x)
    {
        return ctor(x, getY(point));
    }

	public static float getY(long point)
	{
        int y = (int) (point >>> 32);
        return Float.intBitsToFloat(y);
    }

    public static long setY(long point, float y)
    {
        return ctor(getX(point), y);
    }

    // JAVA-deleted: This is struct in .NET and we should use it as immutable in Java, do not add setters.
    // public void setX(float value)
    // public void setY(float value)

    public static int hashCode(long value)
    {
        //exactly as hashCode() in java.lang.Long
        return (int)(value ^ (value >>> 32));
    }

   // This is called by the autoporter when it encounters == in C#.
    public static boolean equals(long a, long b)
    {
        return  (a == b);
    }

    // Also used by autoporter for "PointF + SizeF" expressions in C#
    public static long addSizeF(long a, /*SizeF*/long b)
    {
        return ctor(getX(a) + msSizeF.getWidth(b), getY(a) + msSizeF.getHeight(b));
    }

    // Also used by autoporter for "PointF + Size" expressions in C#
    public static long addSize(/*PointF*/long a, /*Size*/long b)
    {
        return ctor(getX(a) + msSize.getWidth(b), getY(a) + msSize.getHeight(b));
    }

	//JAVA-added
	public static Point2D.Float toJava(long point)
	{
		return new Point2D.Float(getX(point), getY(point));
	}

	//JAVA-added
	public static long fromJava(Point2D.Float point)
	{
        return ctor(point.x, point.y);
    }

    public static final long Empty = 0;
}
