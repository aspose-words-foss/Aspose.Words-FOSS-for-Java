// Parts of this file are ported to Java by Aspose from the .NET sources; the same code is part of
// https://github.com/dotnet/runtime : src/libraries/System.Drawing.Primitives/src/System/Drawing/SizeF.cs
//
// Licensed to the .NET Foundation under one or more agreements.
// The .NET Foundation licenses this file to you under the MIT license, see dotnet-runtime-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Drawing;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Static class with extensions methods to emulate .Net SizeF stuct in Java using long value-type.
 */
public final class msSizeF {

    public static final /*SizeF*/long Empty = 0;

    //JAVA-added. Helps to emulate .Net automatic struct initialization.
    public static /*SizeF*/long ctor()
    {
        return Empty;
    }

    public static /*SizeF*/long ctor(/*SizeF*/long pt)
    {
        float width = getWidth(pt);
        float height = getHeight(pt);
        return ctor(width, height);
    }

    public static /*SizeF*/long ctor(float width, float height)
    {
        // floatToRawIntBits (note Raw) is used cause it faster (3-5 times) and we don't need check NaN in int.
        int wi = Float.floatToRawIntBits(width);
        int hi = Float.floatToRawIntBits(height);
        return  (wi & 0xFFFFFFFFL) | ((long) hi << 32);
    }

    // mf
    public static /*SizeF*/long ctor(Dimension value)
    {
        float width = (float)value.getWidth();
        float height = (float)value.getHeight();
        return ctor(width, height);
    }

    public static float getWidth(/*SizeF*/long size)
    {
        int x = (int) size;
        return Float.intBitsToFloat(x);
    }

    public static float getHeight(/*SizeF*/long size)
    {
        int y = (int) (size >>> 32);
        return Float.intBitsToFloat(y);
    }

    public static /*Size*/long setWidth(/*Size*/long size, float width)
    {
        return ctor(width, getHeight(size));
    }

    public static /*Size*/long setHeight(/*Size*/long size, float height)
    {
        return ctor(getWidth(size), height);
    }

    // JAVA-deleted: I want .NET structs to be immutable classes in Java.
    // public void setWidth(float width)
    // public void setHeight(float height)
    // public void setSize(double width, double height)

    // Also used by autoporter for "SizeF + SizeF" expressions in C#
    public static /*SizeF*/long add(/*SizeF*/long sz1, /*SizeF*/long sz2)
    {
        return ctor(getWidth(sz1) + getWidth(sz2), getHeight(sz1) + getHeight(sz2));
    }

    // Also used by autoporter for "SizeF - SizeF" expressions in C#
    public static /*SizeF*/long subtract(/*SizeF*/long sz1, /*SizeF*/long sz2)
    {
        return ctor(getWidth(sz1) - getWidth(sz2), getHeight(sz1) - getHeight(sz2));
    }

    public static boolean isEmpty(/*SizeF*/long size)
    {
        return (size == 0);
    }

    // This is needed because java equals is overridden.
    // Implementation by Rotor.
    public static int hashCode(/*SizeF*/long size)
    {
        return (int)((long)getWidth(size) ^ (long)getHeight(size));
    }

    // This is called by the autoporter when it encounters == in C#. It is the actual implementation of equality.
    // Implementation by RK.
    public static boolean equals(/*SizeF*/long a, /*SizeF*/long b)
    {
        return (a == b);
    }

    //JAVA was: implicit casting operator
    public static /*SizeF*/long fromSize(/*Size*/long size)
    {
        return ctor(msSize.getWidth(size), msSize.getHeight(size));
    }

    //JAVA-added
    public static /*Size*/long toSize(/*SizeF*/long size)
    {
        return msSize.ctor((int)getWidth(size), (int)getHeight(size));
    }

    //JAVA-added
    public static Point2D.Float toJava(/*SizeF*/long size)
    {
        return new Point2D.Float(getWidth(size), getHeight(size));
    }

    //JAVA-added
    public static /*SizeF*/long fromJava(Point2D.Float point)
    {
        return ctor(point.x, point.y);
    }

    public static String toString(/*SizeF*/long size)
    {
        return "{Width = " + getWidth(size) + " Height = " + getHeight(size) + "}";
    }
}
