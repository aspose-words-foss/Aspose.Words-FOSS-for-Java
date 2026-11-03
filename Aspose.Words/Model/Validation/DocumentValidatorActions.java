// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/11/2009 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies various actions that must be performed on a document when saving in a particular format.
/// </summary>
/*enum*/ final class DocumentValidatorActions
{
    private DocumentValidatorActions(){}
    
    public static final int NONE = 0x0000;

    /// <summary>
    /// The table styles need to be expanded into direct formatting when writing.
    /// </summary>
    public static final int EXPAND_TABLE_STYLES = 0x0001;

    /// <summary>
    /// The save format requires that the font info collection in a document is updated before saving.
    /// </summary>
    public static final int UPDATE_FONT_INFO = 0x0002;

    /// <summary>
    /// Causes legacy list level formatting to be expanded into normal list formatting.
    /// </summary>
    public static final int CONVERT_LEGACY_LIST_FORMATTING = 0x008;

    /// <summary>
    /// Causes horizontally merged cells to be converted into normal cells.
    /// </summary>
    public static final int NORMALIZE_HORIZONTAL_MERGE = 0x0020;

    /// <summary>
    /// The save format does not support DrawingML, but when this option is turned on,
    /// we can at least convert DrawingML pictures to model shapes.
    /// </summary>
    public static final int CONVERT_DML_PICTURES_TO_VML = 0x0040;

    /// <summary>
    /// The save format requires to update list labels.
    /// </summary>
    public static final int UPDATE_LIST_LABELS = 0x0080;

    /// <summary>
    /// The save format requires PreferredWidth.Auto value explicitly set.
    /// </summary>
    public static final int EXPLICIT_AUTO_PREFERRED_WIDTH = 0x0100;

    /// <summary>
    /// Fixes some issues with shapes that is needed for layout and rendering formats.
    /// </summary>
    public static final int PREPARE_SHAPES_FOR_RENDERING = 0x0400;

    /// <summary>
    /// Causes validator to check table width to be valid value for MS Word.
    /// </summary>
    /// <remarks>
    /// AM. Seems that we need to add this flag later to other conditions, for example,
    /// row height is checked as well.
    /// </remarks>
    public static final int WORD_TABLE_LIMITS = 0x0800;

    /// <summary>
    /// Allows long (more than 40 characters length) bookmark name.
    /// </summary>
    public static final int LONG_BOOKMARK_NAMES = 0x1000;

    public static final int NON_MS_WORD_FORMAT = EXPAND_TABLE_STYLES | CONVERT_LEGACY_LIST_FORMATTING |
                        CONVERT_DML_PICTURES_TO_VML | UPDATE_LIST_LABELS;

    public static final int RENDERING = NON_MS_WORD_FORMAT | NORMALIZE_HORIZONTAL_MERGE | PREPARE_SHAPES_FOR_RENDERING;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DocumentValidatorActions value.
    /// </summary>
    public static String getName(/*DocumentValidatorActions*/int documentValidatorActions)
    {
    	switch (documentValidatorActions)
    	{
    		case NONE: return "NONE";
    		case EXPAND_TABLE_STYLES: return "EXPAND_TABLE_STYLES";
    		case UPDATE_FONT_INFO: return "UPDATE_FONT_INFO";
    		case CONVERT_LEGACY_LIST_FORMATTING: return "CONVERT_LEGACY_LIST_FORMATTING";
    		case NORMALIZE_HORIZONTAL_MERGE: return "NORMALIZE_HORIZONTAL_MERGE";
    		case CONVERT_DML_PICTURES_TO_VML: return "CONVERT_DML_PICTURES_TO_VML";
    		case UPDATE_LIST_LABELS: return "UPDATE_LIST_LABELS";
    		case EXPLICIT_AUTO_PREFERRED_WIDTH: return "EXPLICIT_AUTO_PREFERRED_WIDTH";
    		case PREPARE_SHAPES_FOR_RENDERING: return "PREPARE_SHAPES_FOR_RENDERING";
    		case WORD_TABLE_LIMITS: return "WORD_TABLE_LIMITS";
    		case LONG_BOOKMARK_NAMES: return "LONG_BOOKMARK_NAMES";
    		case NON_MS_WORD_FORMAT: return "NON_MS_WORD_FORMAT";
    		case RENDERING: return "RENDERING";
    		default: return "Unknown DocumentValidatorActions value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer DocumentValidatorActions value.
    /// </summary>
    public static Set<String> getNames(/*DocumentValidatorActions*/int documentValidatorActions)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & documentValidatorActions) == NONE) names.add("NONE");
    	if ((EXPAND_TABLE_STYLES & documentValidatorActions) == EXPAND_TABLE_STYLES) names.add("EXPAND_TABLE_STYLES");
    	if ((UPDATE_FONT_INFO & documentValidatorActions) == UPDATE_FONT_INFO) names.add("UPDATE_FONT_INFO");
    	if ((CONVERT_LEGACY_LIST_FORMATTING & documentValidatorActions) == CONVERT_LEGACY_LIST_FORMATTING) names.add("CONVERT_LEGACY_LIST_FORMATTING");
    	if ((NORMALIZE_HORIZONTAL_MERGE & documentValidatorActions) == NORMALIZE_HORIZONTAL_MERGE) names.add("NORMALIZE_HORIZONTAL_MERGE");
    	if ((CONVERT_DML_PICTURES_TO_VML & documentValidatorActions) == CONVERT_DML_PICTURES_TO_VML) names.add("CONVERT_DML_PICTURES_TO_VML");
    	if ((UPDATE_LIST_LABELS & documentValidatorActions) == UPDATE_LIST_LABELS) names.add("UPDATE_LIST_LABELS");
    	if ((EXPLICIT_AUTO_PREFERRED_WIDTH & documentValidatorActions) == EXPLICIT_AUTO_PREFERRED_WIDTH) names.add("EXPLICIT_AUTO_PREFERRED_WIDTH");
    	if ((PREPARE_SHAPES_FOR_RENDERING & documentValidatorActions) == PREPARE_SHAPES_FOR_RENDERING) names.add("PREPARE_SHAPES_FOR_RENDERING");
    	if ((WORD_TABLE_LIMITS & documentValidatorActions) == WORD_TABLE_LIMITS) names.add("WORD_TABLE_LIMITS");
    	if ((LONG_BOOKMARK_NAMES & documentValidatorActions) == LONG_BOOKMARK_NAMES) names.add("LONG_BOOKMARK_NAMES");
    	if ((NON_MS_WORD_FORMAT & documentValidatorActions) == NON_MS_WORD_FORMAT) names.add("NON_MS_WORD_FORMAT");
    	if ((RENDERING & documentValidatorActions) == RENDERING) names.add("RENDERING");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DocumentValidatorActions value.
    /// </summary>
    public static String toString(/*DocumentValidatorActions*/int documentValidatorActions)
    {
    	switch (documentValidatorActions)
    	{
    		case NONE: return "None";
    		case EXPAND_TABLE_STYLES: return "ExpandTableStyles";
    		case UPDATE_FONT_INFO: return "UpdateFontInfo";
    		case CONVERT_LEGACY_LIST_FORMATTING: return "ConvertLegacyListFormatting";
    		case NORMALIZE_HORIZONTAL_MERGE: return "NormalizeHorizontalMerge";
    		case CONVERT_DML_PICTURES_TO_VML: return "ConvertDmlPicturesToVml";
    		case UPDATE_LIST_LABELS: return "UpdateListLabels";
    		case EXPLICIT_AUTO_PREFERRED_WIDTH: return "ExplicitAutoPreferredWidth";
    		case PREPARE_SHAPES_FOR_RENDERING: return "PrepareShapesForRendering";
    		case WORD_TABLE_LIMITS: return "WordTableLimits";
    		case LONG_BOOKMARK_NAMES: return "LongBookmarkNames";
    		case NON_MS_WORD_FORMAT: return "NonMSWordFormat";
    		case RENDERING: return "Rendering";
    		default: return toStringSet(documentValidatorActions);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DocumentValidatorActions value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by DocumentValidatorActions name.
    /// </summary>
    public static /*DocumentValidatorActions*/int fromName(String documentValidatorActionsName)
    {
    	if ("NONE".equals(documentValidatorActionsName)) return NONE;
    	if ("EXPAND_TABLE_STYLES".equals(documentValidatorActionsName)) return EXPAND_TABLE_STYLES;
    	if ("UPDATE_FONT_INFO".equals(documentValidatorActionsName)) return UPDATE_FONT_INFO;
    	if ("CONVERT_LEGACY_LIST_FORMATTING".equals(documentValidatorActionsName)) return CONVERT_LEGACY_LIST_FORMATTING;
    	if ("NORMALIZE_HORIZONTAL_MERGE".equals(documentValidatorActionsName)) return NORMALIZE_HORIZONTAL_MERGE;
    	if ("CONVERT_DML_PICTURES_TO_VML".equals(documentValidatorActionsName)) return CONVERT_DML_PICTURES_TO_VML;
    	if ("UPDATE_LIST_LABELS".equals(documentValidatorActionsName)) return UPDATE_LIST_LABELS;
    	if ("EXPLICIT_AUTO_PREFERRED_WIDTH".equals(documentValidatorActionsName)) return EXPLICIT_AUTO_PREFERRED_WIDTH;
    	if ("PREPARE_SHAPES_FOR_RENDERING".equals(documentValidatorActionsName)) return PREPARE_SHAPES_FOR_RENDERING;
    	if ("WORD_TABLE_LIMITS".equals(documentValidatorActionsName)) return WORD_TABLE_LIMITS;
    	if ("LONG_BOOKMARK_NAMES".equals(documentValidatorActionsName)) return LONG_BOOKMARK_NAMES;
    	if ("NON_MS_WORD_FORMAT".equals(documentValidatorActionsName)) return NON_MS_WORD_FORMAT;
    	if ("RENDERING".equals(documentValidatorActionsName)) return RENDERING;
    	throw new IllegalArgumentException("Unknown DocumentValidatorActions name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by DocumentValidatorActions names.
    /// </summary>
    public static /*DocumentValidatorActions*/int fromNames(Set<String> documentValidatorActionsNames)
    {
    	/*DocumentValidatorActions*/int result = 0;
    	for (String name : documentValidatorActionsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of DocumentValidatorActions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		EXPAND_TABLE_STYLES,
    		UPDATE_FONT_INFO,
    		CONVERT_LEGACY_LIST_FORMATTING,
    		NORMALIZE_HORIZONTAL_MERGE,
    		CONVERT_DML_PICTURES_TO_VML,
    		UPDATE_LIST_LABELS,
    		EXPLICIT_AUTO_PREFERRED_WIDTH,
    		PREPARE_SHAPES_FOR_RENDERING,
    		WORD_TABLE_LIMITS,
    		LONG_BOOKMARK_NAMES,
    		NON_MS_WORD_FORMAT,
    		RENDERING,
    	};
    }

    public static final int length = 13;
}

