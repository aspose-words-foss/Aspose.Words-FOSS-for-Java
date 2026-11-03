// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Contains list of attributes of the 2.24.3.7 CT_Binning type [MS-ODRAWXML].
/// </summary>
/*enum*/ final class DmlChartBinningAttr
{
    private DmlChartBinningAttr(){}
    
    /// <summary>
    /// Specifies the binning by bin size.
    /// Property data type is <b>double</b>.
    /// </summary>
    public static final int BIN_SIZE = 0;

    /// <summary>
    /// Specifies the binning by bin count.
    /// Property data type is <b>int</b>.
    /// </summary>
    public static final int BIN_COUNT = 1;

    /// <summary>
    /// Specifies the interval closed side.
    /// Property data type is <see cref="IntervalClosedSide"/>.
    /// </summary>
    public static final int INTERVAL_CLOSED = 2;

    /// <summary>
    /// Specifies the custom value for underflow bin, or an automatic value.
    /// Property data type is <see cref="DoubleOrAutomatic"/>.
    /// </summary>
    public static final int UNDERFLOW = 3;

    /// <summary>
    /// Specifies the custom value for overflow bin, or an automatic value.
    /// Property data type is <see cref="DoubleOrAutomatic"/>.
    /// </summary>
    public static final int OVERFLOW = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartBinningAttr value.
    /// </summary>
    public static String getName(/*DmlChartBinningAttr*/int dmlChartBinningAttr)
    {
    	switch (dmlChartBinningAttr)
    	{
    		case BIN_SIZE: return "BIN_SIZE";
    		case BIN_COUNT: return "BIN_COUNT";
    		case INTERVAL_CLOSED: return "INTERVAL_CLOSED";
    		case UNDERFLOW: return "UNDERFLOW";
    		case OVERFLOW: return "OVERFLOW";
    		default: return "Unknown DmlChartBinningAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartBinningAttr value.
    /// </summary>
    public static String toString(/*DmlChartBinningAttr*/int dmlChartBinningAttr)
    {
    	switch (dmlChartBinningAttr)
    	{
    		case BIN_SIZE: return "BinSize";
    		case BIN_COUNT: return "BinCount";
    		case INTERVAL_CLOSED: return "IntervalClosed";
    		case UNDERFLOW: return "Underflow";
    		case OVERFLOW: return "Overflow";
    		default: return "Unknown DmlChartBinningAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartBinningAttr name.
    /// </summary>
    public static /*DmlChartBinningAttr*/int fromName(String dmlChartBinningAttrName)
    {
    	if ("BIN_SIZE".equals(dmlChartBinningAttrName)) return BIN_SIZE;
    	if ("BIN_COUNT".equals(dmlChartBinningAttrName)) return BIN_COUNT;
    	if ("INTERVAL_CLOSED".equals(dmlChartBinningAttrName)) return INTERVAL_CLOSED;
    	if ("UNDERFLOW".equals(dmlChartBinningAttrName)) return UNDERFLOW;
    	if ("OVERFLOW".equals(dmlChartBinningAttrName)) return OVERFLOW;
    	throw new IllegalArgumentException("Unknown DmlChartBinningAttr name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartBinningAttr values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BIN_SIZE,
    		BIN_COUNT,
    		INTERVAL_CLOSED,
    		UNDERFLOW,
    		OVERFLOW,
    	};
    }

    public static final int length = 5;
}

