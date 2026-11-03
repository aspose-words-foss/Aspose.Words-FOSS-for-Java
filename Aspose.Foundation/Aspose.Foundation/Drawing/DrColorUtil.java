// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2017 by Sergey Merkulov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides utility methods to work with <see cref="DrColor"/> class.
/// </summary>
public /*static*/ class DrColorUtil
{
	/* Simulation of static class by using private constructor */
	private DrColorUtil()
	{}

    /// <summary>
    /// Converts RGB color to CMYK using simple formula.
    /// Returns components in order: Cyan, Magenta, Yellow, Black.
    /// </summary>
    public static byte[] convertToCmyk(byte redComponent, byte greenComponent, byte blueComponent)
    {
        byte[] cmykBytes = new byte[4];
        final float MAX_COLOR_VALUE = 255f;

        float red = (redComponent & 0xFF) / MAX_COLOR_VALUE;
        float green = (greenComponent & 0xFF) / MAX_COLOR_VALUE;
        float blue = (blueComponent & 0xFF) / MAX_COLOR_VALUE;

        float black = MathUtil.min(1f - red, 1f - green, 1f - blue);
        float cyan, magenta, yellow;
        if (black == 1)
        {
            cyan = magenta = yellow = 0f;
        }
        else
        {
            cyan = (1f - red - black) / (1f - black);
            magenta = (1f - green - black) / (1f - black);
            yellow = (1f - blue - black) / (1f - black);
        }

        cmykBytes[0] = (byte)(cyan * MAX_COLOR_VALUE);
        cmykBytes[1] = (byte)(magenta * MAX_COLOR_VALUE);
        cmykBytes[2] = (byte)(yellow * MAX_COLOR_VALUE);
        cmykBytes[3] = (byte)(black * MAX_COLOR_VALUE);

        return cmykBytes;
    }

    /// <summary>
    /// Returns palette index of specified for indexed image.
    /// </summary>
    public static int getPaletteIndex(byte pixelByte, int colorIndex, int bitsPerComponent)
    {
        int startBitInByte = (colorIndex * bitsPerComponent) % 8;
        int mask = (1 << bitsPerComponent) - 1;
        int paletteIndex = ((pixelByte & 0xFF) & (mask << startBitInByte)) >> startBitInByte;

        return paletteIndex;
    }



    /// <summary>
    /// Checks whether the specified color is a shade of gray.
    /// </summary>
    public static boolean isShadeOfGray(int r, int g, int b)
    {
        return
            (Math.abs(r - g) < COLOR_DIFFERENCE_THRESHOLD) &&
            (Math.abs(g - b) < COLOR_DIFFERENCE_THRESHOLD);
    }

    static final int COLOR_DIFFERENCE_THRESHOLD = 10;
}

