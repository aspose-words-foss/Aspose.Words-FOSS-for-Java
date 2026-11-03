// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msArray;


/// <summary>
/// Utility class for CTF coding.
/// </summary>
/// <remarks>
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#CTF for more info.
/// </remarks>
/*static*/ class CtfCoderUtil
{
	/* Simulation of static class by using private constructor */
	private CtfCoderUtil()
	{}

    /// <summary>
    /// Calculates prediction for the specified HDMX device record and horizontal metric.
    /// </summary>
    /// <remarks>The 'prediction' is 'rounded_TT_AW' in terms of spec.</remarks>
    static int calculatePrediction(HdmxDeviceRecord record, HorizontalMetric metric, /*ushort*/short unitsPerEm)
    {
        // See formula in spec MicroType® Express (MTX) Font Format - 5.4. The 'hdmx' Table Translation.
        return (((((64 * (record.PixelSize & 0xFF) * metric.getAdvanceWidth()) + ((unitsPerEm & 0xFFFF) / 2)) / (unitsPerEm & 0xFFFF)) + 32) / 64);
    }

    /// <summary>
    /// Returns cloned source array of data with first two bytes changed to specified 'newVersion'.
    /// </summary>
    static byte[] cloneWithVersion(byte[] srcData, /*ushort*/short newVersion)
    {
        byte[] clonedData = new byte[srcData.length];
        clonedData[0] = (byte)((newVersion & 0xFFFF) >>> 8);
        clonedData[1] = (byte)(newVersion & 0xFFFF);
        msArray.copy(srcData, 2, clonedData, 2, srcData.length - 2);

        return clonedData;
    }

    static final short CVT_POSITIVE_8 = 255;
    static final short CVT_POSITIVE_1 = 248;
    static final short CVT_NEGATIVE_8 = 247;
    static final short CVT_NEGATIVE_0 = 239;
    static final short CVT_LOWEST_CODE = 238;
}

