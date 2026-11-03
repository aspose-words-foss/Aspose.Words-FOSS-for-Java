// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2013 by Sergey Merkulov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Dithering using Floyd-Steinberg error diffusion.
/// </summary>
/// <remarks><para>The filter represents binarization filter, which is based on
/// error diffusion dithering with <a href="http://en.wikipedia.org/wiki/Floyd%E2%80%93Steinberg_dithering">Floyd-Steinberg</a>
/// coefficients. Error is diffused on 4 neighbor pixels with next coefficients:</para>
/// <code lang="none">
///     | * | 7 |
/// | 3 | 5 | 1 |
/// 
/// / 16
/// </code>
/// <para>The filter accepts 8 bpp grayscale images for processing.</para>
/// </remarks>
final class FloydSteinbergColorDithering extends ColorErrorDiffusionToAdjacentNeighbors
{
    public FloydSteinbergColorDithering()
    {
    	super(new int[][] {
            new int[] { 7 },
            new int[] { 3, 5, 1 } });
	
    }
}

