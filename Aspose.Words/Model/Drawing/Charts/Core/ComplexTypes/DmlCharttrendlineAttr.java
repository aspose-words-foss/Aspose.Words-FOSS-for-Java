// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enum of <see cref="DmlChartTrendline"/> properties.
/// </summary>
/*enum*/ final class DmlChartTrendlineAttr
{
    private DmlChartTrendlineAttr(){}
    
    public static final int BACKWARD = 0;
    public static final int DISP_EQ = 1;
    public static final int DISP_R_SQR = 2;
    public static final int FORWARD = 3;
    public static final int INTERCEPT = 4;
    public static final int NAME = 5;
    public static final int ORDER = 6;
    public static final int PERIOD = 7;
    public static final int SP_PR = 8;
    public static final int TRENDLINE_LBL = 9;
    public static final int TRENDLINE_TYPE = 10;
    public static final int EXTENSIONS = 11;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartTrendlineAttr value.
    /// </summary>
    public static String getName(/*DmlChartTrendlineAttr*/int dmlChartTrendlineAttr)
    {
    	switch (dmlChartTrendlineAttr)
    	{
    		case BACKWARD: return "BACKWARD";
    		case DISP_EQ: return "DISP_EQ";
    		case DISP_R_SQR: return "DISP_R_SQR";
    		case FORWARD: return "FORWARD";
    		case INTERCEPT: return "INTERCEPT";
    		case NAME: return "NAME";
    		case ORDER: return "ORDER";
    		case PERIOD: return "PERIOD";
    		case SP_PR: return "SP_PR";
    		case TRENDLINE_LBL: return "TRENDLINE_LBL";
    		case TRENDLINE_TYPE: return "TRENDLINE_TYPE";
    		case EXTENSIONS: return "EXTENSIONS";
    		default: return "Unknown DmlChartTrendlineAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartTrendlineAttr value.
    /// </summary>
    public static String toString(/*DmlChartTrendlineAttr*/int dmlChartTrendlineAttr)
    {
    	switch (dmlChartTrendlineAttr)
    	{
    		case BACKWARD: return "Backward";
    		case DISP_EQ: return "DispEq";
    		case DISP_R_SQR: return "DispRSqr";
    		case FORWARD: return "Forward";
    		case INTERCEPT: return "Intercept";
    		case NAME: return "Name";
    		case ORDER: return "Order";
    		case PERIOD: return "Period";
    		case SP_PR: return "SpPr";
    		case TRENDLINE_LBL: return "TrendlineLbl";
    		case TRENDLINE_TYPE: return "TrendlineType";
    		case EXTENSIONS: return "Extensions";
    		default: return "Unknown DmlChartTrendlineAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartTrendlineAttr name.
    /// </summary>
    public static /*DmlChartTrendlineAttr*/int fromName(String dmlChartTrendlineAttrName)
    {
    	if ("BACKWARD".equals(dmlChartTrendlineAttrName)) return BACKWARD;
    	if ("DISP_EQ".equals(dmlChartTrendlineAttrName)) return DISP_EQ;
    	if ("DISP_R_SQR".equals(dmlChartTrendlineAttrName)) return DISP_R_SQR;
    	if ("FORWARD".equals(dmlChartTrendlineAttrName)) return FORWARD;
    	if ("INTERCEPT".equals(dmlChartTrendlineAttrName)) return INTERCEPT;
    	if ("NAME".equals(dmlChartTrendlineAttrName)) return NAME;
    	if ("ORDER".equals(dmlChartTrendlineAttrName)) return ORDER;
    	if ("PERIOD".equals(dmlChartTrendlineAttrName)) return PERIOD;
    	if ("SP_PR".equals(dmlChartTrendlineAttrName)) return SP_PR;
    	if ("TRENDLINE_LBL".equals(dmlChartTrendlineAttrName)) return TRENDLINE_LBL;
    	if ("TRENDLINE_TYPE".equals(dmlChartTrendlineAttrName)) return TRENDLINE_TYPE;
    	if ("EXTENSIONS".equals(dmlChartTrendlineAttrName)) return EXTENSIONS;
    	throw new IllegalArgumentException("Unknown DmlChartTrendlineAttr name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartTrendlineAttr values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BACKWARD,
    		DISP_EQ,
    		DISP_R_SQR,
    		FORWARD,
    		INTERCEPT,
    		NAME,
    		ORDER,
    		PERIOD,
    		SP_PR,
    		TRENDLINE_LBL,
    		TRENDLINE_TYPE,
    		EXTENSIONS,
    	};
    }

    public static final int length = 12;
}

