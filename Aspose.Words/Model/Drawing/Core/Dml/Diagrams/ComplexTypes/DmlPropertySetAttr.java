// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/01/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Attributes of <see cref="DmlPropertySet"/>.
/// </summary>
/*enum*/ final class DmlPropertySetAttr
{
    private DmlPropertySetAttr(){}
    
    public static final int PRES_ASSOC_ID = 0;
    public static final int PRES_NAME = 1;
    public static final int PRES_STYLE_LBL = 2;
    public static final int PRES_STYLE_IDX = 3;
    public static final int PRES_STYLE_CNT = 4;
    public static final int LO_TYPE_ID = 5;
    public static final int LO_CAT_ID = 6;
    public static final int QS_TYPE_ID = 7;
    public static final int QS_CAT_ID = 8;
    public static final int CS_TYPE_ID = 9;
    public static final int CS_CAT_ID = 10;
    public static final int COHERENT_3_D_OFF = 11;
    public static final int PHLDR_T = 12;
    public static final int PHLDR = 13;
    public static final int CUST_ANG = 14;
    public static final int CUST_FLIP_VERT = 15;
    public static final int CUST_FLIP_HOR = 16;
    public static final int CUST_SZ_X = 17;
    public static final int CUST_SZ_Y = 18;
    public static final int CUST_SCALE_X = 19;
    public static final int CUST_SCALE_Y = 20;
    public static final int CUST_T = 21;
    public static final int CUST_LIN_FACT_X = 22;
    public static final int CUST_LIN_FACT_Y = 23;
    public static final int CUST_LIN_FACT_NEIGHBOR_X = 24;
    public static final int CUST_LIN_FACT_NEIGHBOR_Y = 25;
    public static final int CUST_RAD_SCALE_RAD = 26;
    public static final int CUST_RAD_SCALE_INC = 27;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlPropertySetAttr value.
    /// </summary>
    public static String getName(/*DmlPropertySetAttr*/int dmlPropertySetAttr)
    {
    	switch (dmlPropertySetAttr)
    	{
    		case PRES_ASSOC_ID: return "PRES_ASSOC_ID";
    		case PRES_NAME: return "PRES_NAME";
    		case PRES_STYLE_LBL: return "PRES_STYLE_LBL";
    		case PRES_STYLE_IDX: return "PRES_STYLE_IDX";
    		case PRES_STYLE_CNT: return "PRES_STYLE_CNT";
    		case LO_TYPE_ID: return "LO_TYPE_ID";
    		case LO_CAT_ID: return "LO_CAT_ID";
    		case QS_TYPE_ID: return "QS_TYPE_ID";
    		case QS_CAT_ID: return "QS_CAT_ID";
    		case CS_TYPE_ID: return "CS_TYPE_ID";
    		case CS_CAT_ID: return "CS_CAT_ID";
    		case COHERENT_3_D_OFF: return "COHERENT_3_D_OFF";
    		case PHLDR_T: return "PHLDR_T";
    		case PHLDR: return "PHLDR";
    		case CUST_ANG: return "CUST_ANG";
    		case CUST_FLIP_VERT: return "CUST_FLIP_VERT";
    		case CUST_FLIP_HOR: return "CUST_FLIP_HOR";
    		case CUST_SZ_X: return "CUST_SZ_X";
    		case CUST_SZ_Y: return "CUST_SZ_Y";
    		case CUST_SCALE_X: return "CUST_SCALE_X";
    		case CUST_SCALE_Y: return "CUST_SCALE_Y";
    		case CUST_T: return "CUST_T";
    		case CUST_LIN_FACT_X: return "CUST_LIN_FACT_X";
    		case CUST_LIN_FACT_Y: return "CUST_LIN_FACT_Y";
    		case CUST_LIN_FACT_NEIGHBOR_X: return "CUST_LIN_FACT_NEIGHBOR_X";
    		case CUST_LIN_FACT_NEIGHBOR_Y: return "CUST_LIN_FACT_NEIGHBOR_Y";
    		case CUST_RAD_SCALE_RAD: return "CUST_RAD_SCALE_RAD";
    		case CUST_RAD_SCALE_INC: return "CUST_RAD_SCALE_INC";
    		default: return "Unknown DmlPropertySetAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlPropertySetAttr value.
    /// </summary>
    public static String toString(/*DmlPropertySetAttr*/int dmlPropertySetAttr)
    {
    	switch (dmlPropertySetAttr)
    	{
    		case PRES_ASSOC_ID: return "PresAssocId";
    		case PRES_NAME: return "PresName";
    		case PRES_STYLE_LBL: return "PresStyleLbl";
    		case PRES_STYLE_IDX: return "PresStyleIdx";
    		case PRES_STYLE_CNT: return "PresStyleCnt";
    		case LO_TYPE_ID: return "LoTypeId";
    		case LO_CAT_ID: return "LoCatId";
    		case QS_TYPE_ID: return "QsTypeId";
    		case QS_CAT_ID: return "QsCatId";
    		case CS_TYPE_ID: return "CsTypeId";
    		case CS_CAT_ID: return "CsCatId";
    		case COHERENT_3_D_OFF: return "Coherent3DOff";
    		case PHLDR_T: return "PhldrT";
    		case PHLDR: return "Phldr";
    		case CUST_ANG: return "CustAng";
    		case CUST_FLIP_VERT: return "CustFlipVert";
    		case CUST_FLIP_HOR: return "CustFlipHor";
    		case CUST_SZ_X: return "CustSzX";
    		case CUST_SZ_Y: return "CustSzY";
    		case CUST_SCALE_X: return "CustScaleX";
    		case CUST_SCALE_Y: return "CustScaleY";
    		case CUST_T: return "CustT";
    		case CUST_LIN_FACT_X: return "CustLinFactX";
    		case CUST_LIN_FACT_Y: return "CustLinFactY";
    		case CUST_LIN_FACT_NEIGHBOR_X: return "CustLinFactNeighborX";
    		case CUST_LIN_FACT_NEIGHBOR_Y: return "CustLinFactNeighborY";
    		case CUST_RAD_SCALE_RAD: return "CustRadScaleRad";
    		case CUST_RAD_SCALE_INC: return "CustRadScaleInc";
    		default: return "Unknown DmlPropertySetAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlPropertySetAttr name.
    /// </summary>
    public static /*DmlPropertySetAttr*/int fromName(String dmlPropertySetAttrName)
    {
    	if ("PRES_ASSOC_ID".equals(dmlPropertySetAttrName)) return PRES_ASSOC_ID;
    	if ("PRES_NAME".equals(dmlPropertySetAttrName)) return PRES_NAME;
    	if ("PRES_STYLE_LBL".equals(dmlPropertySetAttrName)) return PRES_STYLE_LBL;
    	if ("PRES_STYLE_IDX".equals(dmlPropertySetAttrName)) return PRES_STYLE_IDX;
    	if ("PRES_STYLE_CNT".equals(dmlPropertySetAttrName)) return PRES_STYLE_CNT;
    	if ("LO_TYPE_ID".equals(dmlPropertySetAttrName)) return LO_TYPE_ID;
    	if ("LO_CAT_ID".equals(dmlPropertySetAttrName)) return LO_CAT_ID;
    	if ("QS_TYPE_ID".equals(dmlPropertySetAttrName)) return QS_TYPE_ID;
    	if ("QS_CAT_ID".equals(dmlPropertySetAttrName)) return QS_CAT_ID;
    	if ("CS_TYPE_ID".equals(dmlPropertySetAttrName)) return CS_TYPE_ID;
    	if ("CS_CAT_ID".equals(dmlPropertySetAttrName)) return CS_CAT_ID;
    	if ("COHERENT_3_D_OFF".equals(dmlPropertySetAttrName)) return COHERENT_3_D_OFF;
    	if ("PHLDR_T".equals(dmlPropertySetAttrName)) return PHLDR_T;
    	if ("PHLDR".equals(dmlPropertySetAttrName)) return PHLDR;
    	if ("CUST_ANG".equals(dmlPropertySetAttrName)) return CUST_ANG;
    	if ("CUST_FLIP_VERT".equals(dmlPropertySetAttrName)) return CUST_FLIP_VERT;
    	if ("CUST_FLIP_HOR".equals(dmlPropertySetAttrName)) return CUST_FLIP_HOR;
    	if ("CUST_SZ_X".equals(dmlPropertySetAttrName)) return CUST_SZ_X;
    	if ("CUST_SZ_Y".equals(dmlPropertySetAttrName)) return CUST_SZ_Y;
    	if ("CUST_SCALE_X".equals(dmlPropertySetAttrName)) return CUST_SCALE_X;
    	if ("CUST_SCALE_Y".equals(dmlPropertySetAttrName)) return CUST_SCALE_Y;
    	if ("CUST_T".equals(dmlPropertySetAttrName)) return CUST_T;
    	if ("CUST_LIN_FACT_X".equals(dmlPropertySetAttrName)) return CUST_LIN_FACT_X;
    	if ("CUST_LIN_FACT_Y".equals(dmlPropertySetAttrName)) return CUST_LIN_FACT_Y;
    	if ("CUST_LIN_FACT_NEIGHBOR_X".equals(dmlPropertySetAttrName)) return CUST_LIN_FACT_NEIGHBOR_X;
    	if ("CUST_LIN_FACT_NEIGHBOR_Y".equals(dmlPropertySetAttrName)) return CUST_LIN_FACT_NEIGHBOR_Y;
    	if ("CUST_RAD_SCALE_RAD".equals(dmlPropertySetAttrName)) return CUST_RAD_SCALE_RAD;
    	if ("CUST_RAD_SCALE_INC".equals(dmlPropertySetAttrName)) return CUST_RAD_SCALE_INC;
    	throw new IllegalArgumentException("Unknown DmlPropertySetAttr name.");
    }
    
    /// <summary>
    /// Returns array of DmlPropertySetAttr values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PRES_ASSOC_ID,
    		PRES_NAME,
    		PRES_STYLE_LBL,
    		PRES_STYLE_IDX,
    		PRES_STYLE_CNT,
    		LO_TYPE_ID,
    		LO_CAT_ID,
    		QS_TYPE_ID,
    		QS_CAT_ID,
    		CS_TYPE_ID,
    		CS_CAT_ID,
    		COHERENT_3_D_OFF,
    		PHLDR_T,
    		PHLDR,
    		CUST_ANG,
    		CUST_FLIP_VERT,
    		CUST_FLIP_HOR,
    		CUST_SZ_X,
    		CUST_SZ_Y,
    		CUST_SCALE_X,
    		CUST_SCALE_Y,
    		CUST_T,
    		CUST_LIN_FACT_X,
    		CUST_LIN_FACT_Y,
    		CUST_LIN_FACT_NEIGHBOR_X,
    		CUST_LIN_FACT_NEIGHBOR_Y,
    		CUST_RAD_SCALE_RAD,
    		CUST_RAD_SCALE_INC,
    	};
    }

    public static final int length = 28;
}

