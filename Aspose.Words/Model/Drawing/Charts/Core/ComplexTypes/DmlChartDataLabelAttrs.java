// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Contains list of attributes of 5.7.2.47 dLbl (Data Label).
/// </summary>
/*enum*/ final class DmlChartDataLabelAttrs
{
    private DmlChartDataLabelAttrs(){}
    
    /// <summary>
    /// This is zero value, according to "CA1008 Enums should have zero value" guideline.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Specifies that the chart element specified by its containing element shall be deleted from the chart.
    /// Boolean.
    /// </summary>
    public static final int DELETE = 1;

    /// <summary>
    /// Specifies the position of the data label.
    /// <see cref="ChartDataLabelPosition"/>.
    /// </summary>
    public static final int D_LBL_POS = 2;

    /// <summary>
    /// Specifies the index of the containing element. 
    /// This index shall determine which of the parent's 14 children collection this element applies to.
    /// Integer.
    /// </summary>
    public static final int IDX = 3;

    /// <summary>
    /// Specifies how the chart element is placed on the chart.
    /// <see cref="DmlChartManualLayout"/>.
    /// </summary>
    public static final int LAYOUT = 4;

    /// <summary>
    /// Specifies number formatting for the parent element.
    /// <see cref="DmlChartNumFormat"/>.
    /// </summary>
    public static final int NUM_FMT = 5;

    /// <summary>
    /// specifies text that shall be used to separate the parts of a data label. 
    /// The default is a comma, except for pie charts showing only category name and percentage, when a 
    /// line break shall be used instead.
    /// String.
    /// </summary>
    public static final int SEPARATOR = 6;

    /// <summary>
    /// Specifies the bubble size shall be shown in a data label.
    /// Boolean.
    /// </summary>
    public static final int SHOW_BUBBLE_SIZE = 7;

    /// <summary>
    /// Specifies that the category name shall be shown in the data label.
    /// Boolean.
    /// </summary>
    public static final int SHOW_CAT_NAME = 8;

    /// <summary>
    /// Specifies legend keys shall be shown in data labels.
    /// Boolean.
    /// </summary>
    public static final int SHOW_LEGEND_KEY = 9;

    /// <summary>
    /// Specifies that the percentage shall be shown in a data label.
    /// Boolean.
    /// </summary>
    public static final int SHOW_PERCENT = 10;

    /// <summary>
    /// Specifies that the series name shall be shown in a data label.
    /// Boolean.
    /// </summary>
    public static final int SHOW_SER_NAME = 11;

    /// <summary>
    /// Specifies that the value shall be shown in a data label.
    /// Boolean.
    /// </summary>
    public static final int SHOW_VAL = 12;

    /// <summary>
    /// Specifies that leader line should be drawn to the data label. Applies only to Pie Chart.
    /// Boolean.
    /// </summary>
    public static final int SHOW_LEADER_LINES = 13;

    /// <summary>
    /// Specifies that value from data labels range must be displayed.
    /// Boolean.
    /// </summary>
    public static final int SHOW_DATA_LABELS_RANGE = 14;

    /// <summary>
    /// Specifies the formatting for the parent chart element.
    /// <see cref="DmlChartSpPr"/>.
    /// </summary>
    public static final int SP_PR = 15;

    /// <summary>
    /// Specifies text to use on a chart, including rich text formatting.
    /// <see cref="DmlChartTx"/>.
    /// </summary>
    public static final int TX = 16;

    /// <summary>
    /// Specifies text formatting.
    /// <see cref="DmlChartTxPr"/>.
    /// </summary>
    public static final int TX_PR = 17;

    /// <summary>
    /// Specifies line formatting for leader lines.
    /// <see cref="DmlChartSpPr"/>.
    /// </summary>
    public static final int LEADER_LINES = 18;

    /// <summary>
    /// extLst (Chart Extensibility) This element contains tags used for future extensibility of the file format
    /// </summary>
    public static final int EXTENSIONS = 19;

    /// <summary>
    /// Specifies whether a Datalabel was created as an exception entry only for saving, but is treated the same as
    /// the prototype Datalabel in the collection.
    /// Boolean.
    /// </summary>
    public static final int X_FOR_SAVE = 20;

    /// <summary>
    /// Represents spPr extension of a data label that is used to specify a shape of a label.
    /// </summary>
    public static final int SHAPE_PR = 21;

    /// <summary>
    /// Represents a data label field table, each entry of which corresponds to a text field in a data label whose
    /// value is obtained from a formula reference. Currently, entries are represented as plain XML.
    /// </summary>
    public static final int FIELD_TABLE = 22;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartDataLabelAttrs value.
    /// </summary>
    public static String getName(/*DmlChartDataLabelAttrs*/int dmlChartDataLabelAttrs)
    {
    	switch (dmlChartDataLabelAttrs)
    	{
    		case NONE: return "NONE";
    		case DELETE: return "DELETE";
    		case D_LBL_POS: return "D_LBL_POS";
    		case IDX: return "IDX";
    		case LAYOUT: return "LAYOUT";
    		case NUM_FMT: return "NUM_FMT";
    		case SEPARATOR: return "SEPARATOR";
    		case SHOW_BUBBLE_SIZE: return "SHOW_BUBBLE_SIZE";
    		case SHOW_CAT_NAME: return "SHOW_CAT_NAME";
    		case SHOW_LEGEND_KEY: return "SHOW_LEGEND_KEY";
    		case SHOW_PERCENT: return "SHOW_PERCENT";
    		case SHOW_SER_NAME: return "SHOW_SER_NAME";
    		case SHOW_VAL: return "SHOW_VAL";
    		case SHOW_LEADER_LINES: return "SHOW_LEADER_LINES";
    		case SHOW_DATA_LABELS_RANGE: return "SHOW_DATA_LABELS_RANGE";
    		case SP_PR: return "SP_PR";
    		case TX: return "TX";
    		case TX_PR: return "TX_PR";
    		case LEADER_LINES: return "LEADER_LINES";
    		case EXTENSIONS: return "EXTENSIONS";
    		case X_FOR_SAVE: return "X_FOR_SAVE";
    		case SHAPE_PR: return "SHAPE_PR";
    		case FIELD_TABLE: return "FIELD_TABLE";
    		default: return "Unknown DmlChartDataLabelAttrs value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartDataLabelAttrs value.
    /// </summary>
    public static String toString(/*DmlChartDataLabelAttrs*/int dmlChartDataLabelAttrs)
    {
    	switch (dmlChartDataLabelAttrs)
    	{
    		case NONE: return "None";
    		case DELETE: return "Delete";
    		case D_LBL_POS: return "DLblPos";
    		case IDX: return "Idx";
    		case LAYOUT: return "Layout";
    		case NUM_FMT: return "NumFmt";
    		case SEPARATOR: return "Separator";
    		case SHOW_BUBBLE_SIZE: return "ShowBubbleSize";
    		case SHOW_CAT_NAME: return "ShowCatName";
    		case SHOW_LEGEND_KEY: return "ShowLegendKey";
    		case SHOW_PERCENT: return "ShowPercent";
    		case SHOW_SER_NAME: return "ShowSerName";
    		case SHOW_VAL: return "ShowVal";
    		case SHOW_LEADER_LINES: return "ShowLeaderLines";
    		case SHOW_DATA_LABELS_RANGE: return "ShowDataLabelsRange";
    		case SP_PR: return "SpPr";
    		case TX: return "Tx";
    		case TX_PR: return "TxPr";
    		case LEADER_LINES: return "LeaderLines";
    		case EXTENSIONS: return "Extensions";
    		case X_FOR_SAVE: return "XForSave";
    		case SHAPE_PR: return "ShapePr";
    		case FIELD_TABLE: return "FieldTable";
    		default: return "Unknown DmlChartDataLabelAttrs value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartDataLabelAttrs name.
    /// </summary>
    public static /*DmlChartDataLabelAttrs*/int fromName(String dmlChartDataLabelAttrsName)
    {
    	if ("NONE".equals(dmlChartDataLabelAttrsName)) return NONE;
    	if ("DELETE".equals(dmlChartDataLabelAttrsName)) return DELETE;
    	if ("D_LBL_POS".equals(dmlChartDataLabelAttrsName)) return D_LBL_POS;
    	if ("IDX".equals(dmlChartDataLabelAttrsName)) return IDX;
    	if ("LAYOUT".equals(dmlChartDataLabelAttrsName)) return LAYOUT;
    	if ("NUM_FMT".equals(dmlChartDataLabelAttrsName)) return NUM_FMT;
    	if ("SEPARATOR".equals(dmlChartDataLabelAttrsName)) return SEPARATOR;
    	if ("SHOW_BUBBLE_SIZE".equals(dmlChartDataLabelAttrsName)) return SHOW_BUBBLE_SIZE;
    	if ("SHOW_CAT_NAME".equals(dmlChartDataLabelAttrsName)) return SHOW_CAT_NAME;
    	if ("SHOW_LEGEND_KEY".equals(dmlChartDataLabelAttrsName)) return SHOW_LEGEND_KEY;
    	if ("SHOW_PERCENT".equals(dmlChartDataLabelAttrsName)) return SHOW_PERCENT;
    	if ("SHOW_SER_NAME".equals(dmlChartDataLabelAttrsName)) return SHOW_SER_NAME;
    	if ("SHOW_VAL".equals(dmlChartDataLabelAttrsName)) return SHOW_VAL;
    	if ("SHOW_LEADER_LINES".equals(dmlChartDataLabelAttrsName)) return SHOW_LEADER_LINES;
    	if ("SHOW_DATA_LABELS_RANGE".equals(dmlChartDataLabelAttrsName)) return SHOW_DATA_LABELS_RANGE;
    	if ("SP_PR".equals(dmlChartDataLabelAttrsName)) return SP_PR;
    	if ("TX".equals(dmlChartDataLabelAttrsName)) return TX;
    	if ("TX_PR".equals(dmlChartDataLabelAttrsName)) return TX_PR;
    	if ("LEADER_LINES".equals(dmlChartDataLabelAttrsName)) return LEADER_LINES;
    	if ("EXTENSIONS".equals(dmlChartDataLabelAttrsName)) return EXTENSIONS;
    	if ("X_FOR_SAVE".equals(dmlChartDataLabelAttrsName)) return X_FOR_SAVE;
    	if ("SHAPE_PR".equals(dmlChartDataLabelAttrsName)) return SHAPE_PR;
    	if ("FIELD_TABLE".equals(dmlChartDataLabelAttrsName)) return FIELD_TABLE;
    	throw new IllegalArgumentException("Unknown DmlChartDataLabelAttrs name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartDataLabelAttrs values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		DELETE,
    		D_LBL_POS,
    		IDX,
    		LAYOUT,
    		NUM_FMT,
    		SEPARATOR,
    		SHOW_BUBBLE_SIZE,
    		SHOW_CAT_NAME,
    		SHOW_LEGEND_KEY,
    		SHOW_PERCENT,
    		SHOW_SER_NAME,
    		SHOW_VAL,
    		SHOW_LEADER_LINES,
    		SHOW_DATA_LABELS_RANGE,
    		SP_PR,
    		TX,
    		TX_PR,
    		LEADER_LINES,
    		EXTENSIONS,
    		X_FOR_SAVE,
    		SHAPE_PR,
    		FIELD_TABLE,
    	};
    }

    public static final int length = 23;
}

