// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Determines when automatic footnote or endnote numbering restarts.
/// </summary>
/// <seealso cref="FootnoteOptions"/>
/// <seealso cref="EndnoteOptions"/>
public /*enum*/ final class FootnoteNumberingRule
{
    private FootnoteNumberingRule(){}
    
    /// <summary>
    /// Numbering continuous throughout the document.
    /// </summary>
    public static final int CONTINUOUS = 0;
    /// <summary>
    /// Numbering restarts at each section.
    /// </summary>
    public static final int RESTART_SECTION = 1;
    /// <summary>
    /// Numbering restarts at each page. Valid for footnotes only.
    /// </summary>
    public static final int RESTART_PAGE = 2;

    /// <summary>
    /// Equals <see cref="Continuous"/>.
    /// </summary>
    public static final int DEFAULT = CONTINUOUS;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FootnoteNumberingRule value.
    /// </summary>
    public static String getName(/*FootnoteNumberingRule*/int footnoteNumberingRule)
    {
    	switch (footnoteNumberingRule)
    	{
    		case CONTINUOUS:
    		/*case DEFAULT:*/
    			return "CONTINUOUS" + " | " + "DEFAULT";
    		case RESTART_SECTION: return "RESTART_SECTION";
    		case RESTART_PAGE: return "RESTART_PAGE";
    		default: return "Unknown FootnoteNumberingRule value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FootnoteNumberingRule value.
    /// </summary>
    public static String toString(/*FootnoteNumberingRule*/int footnoteNumberingRule)
    {
    	switch (footnoteNumberingRule)
    	{
    		case CONTINUOUS:
    		/*case DEFAULT:*/
    			return "Continuous" + " | " + "Default";
    		case RESTART_SECTION: return "RestartSection";
    		case RESTART_PAGE: return "RestartPage";
    		default: return "Unknown FootnoteNumberingRule value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FootnoteNumberingRule name.
    /// </summary>
    public static /*FootnoteNumberingRule*/int fromName(String footnoteNumberingRuleName)
    {
    	if ("CONTINUOUS".equals(footnoteNumberingRuleName)) return CONTINUOUS;
    	if ("RESTART_SECTION".equals(footnoteNumberingRuleName)) return RESTART_SECTION;
    	if ("RESTART_PAGE".equals(footnoteNumberingRuleName)) return RESTART_PAGE;
    	if ("DEFAULT".equals(footnoteNumberingRuleName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown FootnoteNumberingRule name.");
    }
    
    /// <summary>
    /// Returns array of FootnoteNumberingRule values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CONTINUOUS,
    		RESTART_SECTION,
    		RESTART_PAGE,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

