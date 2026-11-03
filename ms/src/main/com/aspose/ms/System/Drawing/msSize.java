// Parts of this file are ported to Java by Aspose from the .NET sources; the same code is part of
// https://github.com/dotnet/runtime : src/libraries/System.Drawing.Primitives/src/System/Drawing/Size.cs
//
// Licensed to the .NET Foundation under one or more agreements.
// The .NET Foundation licenses this file to you under the MIT license, see dotnet-runtime-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Drawing;

import java.awt.*;

/**
 * Static class with extensions methods to emulate .Net Size stuct in Java using long value-type.
 */
public final class msSize {

    public static final /*Size*/long Empty = 0;

    public static /*Size*/long ctor(int width, int height)
    {
        return (width & 0xFFFFFFFFL)  | ((long) height << 32);
    }

    public static /*Size*/long ctor()
    {
        return Empty;
    }

    public static int getWidth(/*Size*/long size)
    {
        return (int) size;
    }

    public static int getHeight(/*Size*/long size)
    {
        return (int) (size >>> 32);
    }

    public static /*Size*/long setWidth(/*Size*/long size, int width)
    {
        return ctor(width, getHeight(size));
    }

    public static /*Size*/long setHeight(/*Size*/long size, int height)
    {
        return ctor(getWidth(size), height);
    }

    /**
     * Converts a SizeF to a Size by performing a round operation on all the coordinates.
     */
    public static /*Size*/long round(/*SizeF*/long sizeF)
    {
        return ctor(Math.round(msSizeF.getWidth(sizeF)), Math.round(msSizeF.getHeight(sizeF)));
    }

    public static boolean isEmpty(/*Size*/long size)
    {
        return (size == 0);
    }

    // This is needed because java equals is overridden.
    // Implementation by Rotor.
    public static int hashCode(/*Size*/long size)
    {
        return (getWidth(size) ^ getHeight(size));
    }

    // This is called by the autoporter when it encounters == in C#. It is the actual implementation of equality.
    // Implementation by RK.
    public static boolean equals(/*Size*/long a, /*Size*/long b)
    {
        return (a == b);
    }

    //JAVA-added instead of implicit casting operator
    public static /*SizeF*/long toSizeF(/*Size*/long size)
    {
        return msSizeF.ctor(getWidth(size), getHeight(size));
    }

    //JAVA-added
    public static java.awt.Dimension toJava(/*Size*/long size)
    {
        return new Dimension(getWidth(size), getHeight(size));
    }

    //JAVA-added
    public static /*Size*/long fromJava(java.awt.Dimension dimension)
    {
        return ctor(dimension.width, dimension.height);
    }

    public static String toString(/*Size*/long size)
    {
        return "{Width = " + getWidth(size) + " Height = " + getHeight(size) + "}";
    }
}
