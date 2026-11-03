// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2013 by Sergey Merkulov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for error diffusion dithering, where error is diffused to 
/// adjacent neighbor pixels.
/// </summary>
/// 
/// <remarks><para>The class does error diffusion to adjacent neighbor pixels
/// using specified set of coefficients. These coefficients are represented by
/// 2 dimensional jugged array, where first array of coefficients is for
/// right-standing pixels, but the rest of arrays are for bottom-standing pixels.
/// All arrays except the first one should have odd number of coefficients.</para>
/// 
/// <para>Suppose that error diffusion coefficients are represented by the next
/// jugged array:</para>
/// 
/// <code>
/// int[][] coefficients = new int[2][] {
///     new int[1] { 7 },
///     new int[3] { 3, 5, 1 }
/// };
/// </code>
/// 
/// <para>The above coefficients are used to diffuse error over the next neighbor
/// pixels (<b>*</b> marks current pixel, coefficients are placed to corresponding
/// neighbor pixels):</para>
/// <code lang="none">
///     | * | 7 |
/// | 3 | 5 | 1 |
/// 
/// / 16
/// </code>
/// 
/// <para>The filter accepts 8 bpp grayscale images for processing.</para>
/// </remarks>
class ColorErrorDiffusionToAdjacentNeighbors extends ErrorDiffusionDithering
{
    /// <summary>
    /// Initializes a new instance of the <see cref="ColorErrorDiffusionToAdjacentNeighbors"/> class.
    /// </summary>
    /// <param name="coefficients">Diffusion coefficients</param>
    public ColorErrorDiffusionToAdjacentNeighbors(int[][] coefficients)
    {
        this.mCoefficients = coefficients;
        calculateCoefficientsSum();
    }

    protected /*override*/ void diffuse(int error, byte[] bytes, int ptr)
    {
        // Error diffusion.
        int ed;

        // Do error diffusion to right-standing neighbors.
        int[] coefficientsRow = mCoefficients[0];

        for (int jI = 1, jC = 0, k = coefficientsRow.length; jC < k; jI++, jC++)
        {
            if (X + jI >= Width)
                break;

            ed = (bytes[ptr + jI] & 0xFF) + (error * coefficientsRow[jC]) / mCoefficientsSum;
            ed = (ed < 0) ? 0 : ((ed > 255) ? 255 : ed);
            bytes[ptr + jI] = (byte)ed;
        }

        // Do error diffusion to bottom neighbors.
        for (int i = 1, n = mCoefficients.length; i < n; i++)
        {
            if (Y + i >= Height)
                break;

            // Move pointer to next image line.
            ptr += Stride;

            // Get coefficients of the row.
            coefficientsRow = mCoefficients[i];

            // Process the row.
            for (int jC = 0, k = coefficientsRow.length, jI = -(k >> 1); jC < k; jI++, jC++)
            {
                if (X + jI >= Width)
                    break;

                if (X + jI < 0)
                    continue;

                ed = (bytes[ptr + jI] & 0xFF) + (error * coefficientsRow[jC]) / mCoefficientsSum;
                ed = (ed < 0) ? 0 : ((ed > 255) ? 255 : ed);
                bytes[ptr + jI] = (byte)ed;
            }
        }
    }

    private void calculateCoefficientsSum()
    {
        mCoefficientsSum = 0;

        for (int i = 0, n = mCoefficients.length; i < n; i++)
        {
            int[] coefficientsRow = mCoefficients[i];
            for (int j = 0, k = coefficientsRow.length; j < k; j++)
            {
                mCoefficientsSum += coefficientsRow[j];
            }
        }
    }

    // diffusion coefficients
    private /*final*/ int[][] mCoefficients;
    // sum of all coefficients
    private int mCoefficientsSum;
}

