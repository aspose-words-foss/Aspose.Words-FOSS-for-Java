// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies the type of a warning that is issued by Aspose.Words during document loading or saving.
/// </summary>
public /*enum*/ final class WarningType
{
    private WarningType(){}
    
    /// <summary>
    /// Some text/char/image or other data will be missing from either the document tree following load, 
    /// or from the created document following save.
    /// </summary>
    public static final int DATA_LOSS_CATEGORY = 0x000000FF;
    /// <summary>
    /// Generic data loss, no specific code.
    /// </summary>
    public static final int DATA_LOSS = 0x00000001;
    /// <summary>
    /// The resulting document or a particular location in it might look substantially different 
    /// compared to the original document.
    /// </summary>
    public static final int MAJOR_FORMATTING_LOSS_CATEGORY = 0x0000FF00;
    /// <summary>
    /// Generic major formatting loss, no specific code.
    /// </summary>
    public static final int MAJOR_FORMATTING_LOSS = 0x00000100;
    /// <summary>
    /// The resulting document or a particular location in it might look somewhat different compared 
    /// to the original document.
    /// </summary>
    public static final int MINOR_FORMATTING_LOSS_CATEGORY = 0x00FF0000;
    /// <summary>
    /// Generic minor formatting loss, no specific code.
    /// </summary>
    public static final int MINOR_FORMATTING_LOSS = 0x00010000;
    /// <summary>
    /// Font has been substituted.
    /// </summary>
    public static final int FONT_SUBSTITUTION = 0x00020000;
    /// <summary>
    /// Loss of embedded font information during document saving.
    /// </summary>
    public static final int FONT_EMBEDDING = 0x00040000;
    /// <summary>
    /// Some content in the source document could not be recognized (i.e. is unsupported), this may or may not 
    /// cause issues or result in data/formatting loss. 
    /// </summary>
    public static final int UNEXPECTED_CONTENT_CATEGORY = 0x0F000000;
    /// <summary>
    /// Generic unexpected content, no specific code.
    /// </summary>
    public static final int UNEXPECTED_CONTENT = 0x01000000;
    /// <summary>
    /// Advises of a potential problem or suggests an improvement.
    /// </summary>
    public static final int HINT = 0x10000000;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer WarningType value.
    /// </summary>
    public static String getName(/*WarningType*/int warningType)
    {
    	switch (warningType)
    	{
    		case DATA_LOSS_CATEGORY: return "DATA_LOSS_CATEGORY";
    		case DATA_LOSS: return "DATA_LOSS";
    		case MAJOR_FORMATTING_LOSS_CATEGORY: return "MAJOR_FORMATTING_LOSS_CATEGORY";
    		case MAJOR_FORMATTING_LOSS: return "MAJOR_FORMATTING_LOSS";
    		case MINOR_FORMATTING_LOSS_CATEGORY: return "MINOR_FORMATTING_LOSS_CATEGORY";
    		case MINOR_FORMATTING_LOSS: return "MINOR_FORMATTING_LOSS";
    		case FONT_SUBSTITUTION: return "FONT_SUBSTITUTION";
    		case FONT_EMBEDDING: return "FONT_EMBEDDING";
    		case UNEXPECTED_CONTENT_CATEGORY: return "UNEXPECTED_CONTENT_CATEGORY";
    		case UNEXPECTED_CONTENT: return "UNEXPECTED_CONTENT";
    		case HINT: return "HINT";
    		default: return "Unknown WarningType value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer WarningType value.
    /// </summary>
    public static Set<String> getNames(/*WarningType*/int warningType)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((DATA_LOSS_CATEGORY & warningType) == DATA_LOSS_CATEGORY) names.add("DATA_LOSS_CATEGORY");
    	if ((DATA_LOSS & warningType) == DATA_LOSS) names.add("DATA_LOSS");
    	if ((MAJOR_FORMATTING_LOSS_CATEGORY & warningType) == MAJOR_FORMATTING_LOSS_CATEGORY) names.add("MAJOR_FORMATTING_LOSS_CATEGORY");
    	if ((MAJOR_FORMATTING_LOSS & warningType) == MAJOR_FORMATTING_LOSS) names.add("MAJOR_FORMATTING_LOSS");
    	if ((MINOR_FORMATTING_LOSS_CATEGORY & warningType) == MINOR_FORMATTING_LOSS_CATEGORY) names.add("MINOR_FORMATTING_LOSS_CATEGORY");
    	if ((MINOR_FORMATTING_LOSS & warningType) == MINOR_FORMATTING_LOSS) names.add("MINOR_FORMATTING_LOSS");
    	if ((FONT_SUBSTITUTION & warningType) == FONT_SUBSTITUTION) names.add("FONT_SUBSTITUTION");
    	if ((FONT_EMBEDDING & warningType) == FONT_EMBEDDING) names.add("FONT_EMBEDDING");
    	if ((UNEXPECTED_CONTENT_CATEGORY & warningType) == UNEXPECTED_CONTENT_CATEGORY) names.add("UNEXPECTED_CONTENT_CATEGORY");
    	if ((UNEXPECTED_CONTENT & warningType) == UNEXPECTED_CONTENT) names.add("UNEXPECTED_CONTENT");
    	if ((HINT & warningType) == HINT) names.add("HINT");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WarningType value.
    /// </summary>
    public static String toString(/*WarningType*/int warningType)
    {
    	switch (warningType)
    	{
    		case DATA_LOSS_CATEGORY: return "DataLossCategory";
    		case DATA_LOSS: return "DataLoss";
    		case MAJOR_FORMATTING_LOSS_CATEGORY: return "MajorFormattingLossCategory";
    		case MAJOR_FORMATTING_LOSS: return "MajorFormattingLoss";
    		case MINOR_FORMATTING_LOSS_CATEGORY: return "MinorFormattingLossCategory";
    		case MINOR_FORMATTING_LOSS: return "MinorFormattingLoss";
    		case FONT_SUBSTITUTION: return "FontSubstitution";
    		case FONT_EMBEDDING: return "FontEmbedding";
    		case UNEXPECTED_CONTENT_CATEGORY: return "UnexpectedContentCategory";
    		case UNEXPECTED_CONTENT: return "UnexpectedContent";
    		case HINT: return "Hint";
    		default: return toStringSet(warningType);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WarningType value.
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
    /// Returns integer representation by WarningType name.
    /// </summary>
    public static /*WarningType*/int fromName(String warningTypeName)
    {
    	if ("DATA_LOSS_CATEGORY".equals(warningTypeName)) return DATA_LOSS_CATEGORY;
    	if ("DATA_LOSS".equals(warningTypeName)) return DATA_LOSS;
    	if ("MAJOR_FORMATTING_LOSS_CATEGORY".equals(warningTypeName)) return MAJOR_FORMATTING_LOSS_CATEGORY;
    	if ("MAJOR_FORMATTING_LOSS".equals(warningTypeName)) return MAJOR_FORMATTING_LOSS;
    	if ("MINOR_FORMATTING_LOSS_CATEGORY".equals(warningTypeName)) return MINOR_FORMATTING_LOSS_CATEGORY;
    	if ("MINOR_FORMATTING_LOSS".equals(warningTypeName)) return MINOR_FORMATTING_LOSS;
    	if ("FONT_SUBSTITUTION".equals(warningTypeName)) return FONT_SUBSTITUTION;
    	if ("FONT_EMBEDDING".equals(warningTypeName)) return FONT_EMBEDDING;
    	if ("UNEXPECTED_CONTENT_CATEGORY".equals(warningTypeName)) return UNEXPECTED_CONTENT_CATEGORY;
    	if ("UNEXPECTED_CONTENT".equals(warningTypeName)) return UNEXPECTED_CONTENT;
    	if ("HINT".equals(warningTypeName)) return HINT;
    	throw new IllegalArgumentException("Unknown WarningType name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by WarningType names.
    /// </summary>
    public static /*WarningType*/int fromNames(Set<String> warningTypeNames)
    {
    	/*WarningType*/int result = 0;
    	for (String name : warningTypeNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of WarningType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DATA_LOSS_CATEGORY,
    		DATA_LOSS,
    		MAJOR_FORMATTING_LOSS_CATEGORY,
    		MAJOR_FORMATTING_LOSS,
    		MINOR_FORMATTING_LOSS_CATEGORY,
    		MINOR_FORMATTING_LOSS,
    		FONT_SUBSTITUTION,
    		FONT_EMBEDDING,
    		UNEXPECTED_CONTENT_CATEGORY,
    		UNEXPECTED_CONTENT,
    		HINT,
    	};
    }

    public static final int length = 11;
}

// RK By Aspose's design there are two more values that we currently don't use. I just don't have good uses for them yet.
//
// 0 - SourceFileCorruption  An issue has been detected in the source document which makes it very likely the document 
//     will be not be able to be opened if saved in it's original format (Load warning only).
//
// 4 - CompatibilityIssue  Known issue that will prevent the document being opened by certain user agents, or previous 
//     versions of user agents (To be explained in WarningInfo.Description). 

