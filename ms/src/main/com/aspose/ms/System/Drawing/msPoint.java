package com.aspose.ms.System.Drawing;

/**
 * Static class with extensions methods to emulate .Net Point stuct in Java using long value-type.
 */
public final class  msPoint
{
    private msPoint()
    {
    }

    public static long ctor()
    {
        return Empty;
    }

    public static long ctor(int x, int y)
    {
        return (x & 0xFFFFFFFFL)  | ((long) y << 32);
    }

    public static long ctor(/*Size*/long sz)
    {
        int x = msSize.getWidth(sz);
        int y = msSize.getHeight(sz);
        return ctor(x, y);
    }

    public static long ctor(int dw)
    {
        int x = (short)LOWORD(dw);
        int y = (short)HIWORD(dw);
        return ctor(x, y);
    }

    public static boolean isEmpty(long point)
    {
        return point == 0;
    }

    public static int getX(long point)
    {
        return (int) point;
    }

    public static long setX(long point, int x)
    {
        return ctor(x, getY(point));
    }

    public static int getY(long point)
    {
        return (int) (point >> 32);
    }

    public static long setY(long point, int y)
    {
        return ctor(getX(point), y);
    }

    public static long offset(long point, long offset)
    {
        int x = getX(point) + getX(offset);
        int y = getY(point) + getY(offset);
        return ctor(x, y);
    }

    public static long offset(long point, int dx, int dy)
    {
	    int x = getX(point) + dx;
        int y = getY(point) + dy;
        return ctor(x, y);
    }

    public static int getHashCode(long point)
    {
        return getX(point) ^ getY(point);
    }

    // JAVA-deleted: This is struct in .NET and we should use it as immutable in Java, do not add setters.
    // public void setX(int value)
    // public void setY(int value)

// JAVA-deleted: until required
//    public static explicit operator Size(Point p)
//    {
//        return new Size(p.X, p.Y);
//    }
//
//    public static Point operator +(Point pt, Size sz)
//    {
//        return new Point(pt.X + sz.Width, pt.Y + sz.Height);
//    }
//
//    public static Point operator -(Point pt, Size sz)
//    {
//        return new Point(pt.X - sz.Width, pt.Y - sz.Height);
//    }

// JAVA-deleted: until required
//    public static bool operator !=(Point left, Point right)
//    {
//        return !(left == right);
//    }
//
//    public static Point Ceiling(PointF value)
//    {
//        return new Point((int) Math.Ceiling((double) value.X), (int) Math.Ceiling((double) value.Y));
//    }
//
//    public static Point Truncate(PointF value)
//    {
//        return new Point((int) value.X, (int) value.Y);
//    }
//
//    public static Point Round(PointF value)
//    {
//        return new Point((int) Math.Round((double) value.X), (int) Math.Round((double) value.Y));
//    }

    // This is called by the autoporter when it encounters == in C#. 
    public static boolean equals(long a, long b)
    {
        return a == b;
    }

    //JAVA-added instead of implicit casting operator
    public static /*PointF*/long toPointF(/*Point*/long point)
    {
        return msPointF.ctor(getX(point), getY(point));
    }

	//JAVA-added
	public static java.awt.Point toJava(long point)
	{
		return new java.awt.Point(getX(point), getY(point));
	}

	//JAVA-added
	public static long fromJava(java.awt.Point point)
	{
        return ctor(point.x, point.y);
    }

    private static int HIWORD(int n)
    {
        return ((n >> 0x10) & 0xffff);
    }

    private static int LOWORD(int n)
    {
        return (n & 0xffff);
    }

    public static final long Empty = 0;
}

