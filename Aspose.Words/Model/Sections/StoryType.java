// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Text of a Word document is stored in stories. <see cref="StoryType"/> identifies a story.
/// </summary>
public /*enum*/ final class StoryType
{
    private StoryType(){}
    
    /// <summary>
    /// Default value. There is no such story in the document.
    /// </summary>
    public static final int NONE     = 0;
    /// <summary>
    /// Contains the main text of the document, represented by <see cref="Aspose.Words.Body"/>.
    /// </summary>
    public static final int MAIN_TEXT = 1;
    /// <summary>
    /// Contains footnote text, represented by <see cref="Aspose.Words.Notes.Footnote"/>.
    /// </summary>
    public static final int FOOTNOTES = 2;
    /// <summary>
    /// Contains endnotes text, represented by <see cref="Aspose.Words.Notes.Footnote"/>.
    /// </summary>
    public static final int ENDNOTES = 3;
    /// <summary>
    /// Contains document comments (annotations), represented by <see cref="Aspose.Words.Comment"/>.
    /// </summary>
    public static final int COMMENTS = 4;
    /// <summary>
    /// Contains shape or textbox text, represented by <see cref="Aspose.Words.Drawing.Shape"/>.
    /// </summary>
    public static final int TEXTBOX = 5;
    /// <summary>
    /// Contains text of the even pages header, represented by <see cref="Aspose.Words.HeaderFooter"/>.
    /// </summary>
    public static final int EVEN_PAGES_HEADER = 6;
    /// <summary>
    /// Contains text of the primary header. When header is different for odd and even pages,
    /// contains text of the odd pages header. Represented by <see cref="Aspose.Words.HeaderFooter"/>.
    /// </summary>
    public static final int PRIMARY_HEADER = 7;
    /// <summary>
    /// Contains text of the even pages footer, represented by <see cref="Aspose.Words.HeaderFooter"/>.
    /// </summary>
    public static final int EVEN_PAGES_FOOTER = 8;
    /// <summary>
    /// Contains text of the primary footer. When footer is different for odd and even pages,
    /// contains text of the odd pages footer. Represented by <see cref="Aspose.Words.HeaderFooter"/>.
    /// </summary>
    public static final int PRIMARY_FOOTER = 9;
    /// <summary>
    /// Contains text of the first page header, represented by <see cref="Aspose.Words.HeaderFooter"/>.
    /// </summary>
    public static final int FIRST_PAGE_HEADER = 10;
    /// <summary>
    /// Contains text of the first page footer, represented by <see cref="Aspose.Words.HeaderFooter"/>.
    /// </summary>
    public static final int FIRST_PAGE_FOOTER = 11;
    /// <summary>
    /// Contains the text of the footnote separator. <dev>Represented by <see cref="Aspose.Words.Notes.FootnoteSeparator"/>.</dev>
    /// </summary>
    public static final int FOOTNOTE_SEPARATOR = 12;
    /// <summary>
    /// Contains the text of the footnote continuation separator. <dev>Represented by <see cref="Aspose.Words.Notes.FootnoteSeparator"/>.</dev>
    /// </summary>
    public static final int FOOTNOTE_CONTINUATION_SEPARATOR = 13;
    /// <summary>
    /// Contains the text of the footnote continuation notice separator. <dev>Represented by <see cref="Aspose.Words.Notes.FootnoteSeparator"/>.</dev>
    /// </summary>
    public static final int FOOTNOTE_CONTINUATION_NOTICE = 14;
    /// <summary>
    /// Contains the text of the endnote separator. <dev>Represented by <see cref="Aspose.Words.Notes.FootnoteSeparator"/>.</dev>
    /// </summary>
    public static final int ENDNOTE_SEPARATOR = 15;
    /// <summary>
    /// Contains the text of the endnote continuation separator. <dev>Represented by <see cref="Aspose.Words.Notes.FootnoteSeparator"/>.</dev>
    /// </summary>
    public static final int ENDNOTE_CONTINUATION_SEPARATOR = 16;
    /// <summary>
    /// Contains the text of the endnote continuation notice separator. <dev>Represented by <see cref="Aspose.Words.Notes.FootnoteSeparator"/>.</dev>
    /// </summary>
    public static final int ENDNOTE_CONTINUATION_NOTICE = 17;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer StoryType value.
    /// </summary>
    public static String getName(/*StoryType*/int storyType)
    {
    	switch (storyType)
    	{
    		case NONE: return "NONE";
    		case MAIN_TEXT: return "MAIN_TEXT";
    		case FOOTNOTES: return "FOOTNOTES";
    		case ENDNOTES: return "ENDNOTES";
    		case COMMENTS: return "COMMENTS";
    		case TEXTBOX: return "TEXTBOX";
    		case EVEN_PAGES_HEADER: return "EVEN_PAGES_HEADER";
    		case PRIMARY_HEADER: return "PRIMARY_HEADER";
    		case EVEN_PAGES_FOOTER: return "EVEN_PAGES_FOOTER";
    		case PRIMARY_FOOTER: return "PRIMARY_FOOTER";
    		case FIRST_PAGE_HEADER: return "FIRST_PAGE_HEADER";
    		case FIRST_PAGE_FOOTER: return "FIRST_PAGE_FOOTER";
    		case FOOTNOTE_SEPARATOR: return "FOOTNOTE_SEPARATOR";
    		case FOOTNOTE_CONTINUATION_SEPARATOR: return "FOOTNOTE_CONTINUATION_SEPARATOR";
    		case FOOTNOTE_CONTINUATION_NOTICE: return "FOOTNOTE_CONTINUATION_NOTICE";
    		case ENDNOTE_SEPARATOR: return "ENDNOTE_SEPARATOR";
    		case ENDNOTE_CONTINUATION_SEPARATOR: return "ENDNOTE_CONTINUATION_SEPARATOR";
    		case ENDNOTE_CONTINUATION_NOTICE: return "ENDNOTE_CONTINUATION_NOTICE";
    		default: return "Unknown StoryType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer StoryType value.
    /// </summary>
    public static String toString(/*StoryType*/int storyType)
    {
    	switch (storyType)
    	{
    		case NONE: return "None";
    		case MAIN_TEXT: return "MainText";
    		case FOOTNOTES: return "Footnotes";
    		case ENDNOTES: return "Endnotes";
    		case COMMENTS: return "Comments";
    		case TEXTBOX: return "Textbox";
    		case EVEN_PAGES_HEADER: return "EvenPagesHeader";
    		case PRIMARY_HEADER: return "PrimaryHeader";
    		case EVEN_PAGES_FOOTER: return "EvenPagesFooter";
    		case PRIMARY_FOOTER: return "PrimaryFooter";
    		case FIRST_PAGE_HEADER: return "FirstPageHeader";
    		case FIRST_PAGE_FOOTER: return "FirstPageFooter";
    		case FOOTNOTE_SEPARATOR: return "FootnoteSeparator";
    		case FOOTNOTE_CONTINUATION_SEPARATOR: return "FootnoteContinuationSeparator";
    		case FOOTNOTE_CONTINUATION_NOTICE: return "FootnoteContinuationNotice";
    		case ENDNOTE_SEPARATOR: return "EndnoteSeparator";
    		case ENDNOTE_CONTINUATION_SEPARATOR: return "EndnoteContinuationSeparator";
    		case ENDNOTE_CONTINUATION_NOTICE: return "EndnoteContinuationNotice";
    		default: return "Unknown StoryType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by StoryType name.
    /// </summary>
    public static /*StoryType*/int fromName(String storyTypeName)
    {
    	if ("NONE".equals(storyTypeName)) return NONE;
    	if ("MAIN_TEXT".equals(storyTypeName)) return MAIN_TEXT;
    	if ("FOOTNOTES".equals(storyTypeName)) return FOOTNOTES;
    	if ("ENDNOTES".equals(storyTypeName)) return ENDNOTES;
    	if ("COMMENTS".equals(storyTypeName)) return COMMENTS;
    	if ("TEXTBOX".equals(storyTypeName)) return TEXTBOX;
    	if ("EVEN_PAGES_HEADER".equals(storyTypeName)) return EVEN_PAGES_HEADER;
    	if ("PRIMARY_HEADER".equals(storyTypeName)) return PRIMARY_HEADER;
    	if ("EVEN_PAGES_FOOTER".equals(storyTypeName)) return EVEN_PAGES_FOOTER;
    	if ("PRIMARY_FOOTER".equals(storyTypeName)) return PRIMARY_FOOTER;
    	if ("FIRST_PAGE_HEADER".equals(storyTypeName)) return FIRST_PAGE_HEADER;
    	if ("FIRST_PAGE_FOOTER".equals(storyTypeName)) return FIRST_PAGE_FOOTER;
    	if ("FOOTNOTE_SEPARATOR".equals(storyTypeName)) return FOOTNOTE_SEPARATOR;
    	if ("FOOTNOTE_CONTINUATION_SEPARATOR".equals(storyTypeName)) return FOOTNOTE_CONTINUATION_SEPARATOR;
    	if ("FOOTNOTE_CONTINUATION_NOTICE".equals(storyTypeName)) return FOOTNOTE_CONTINUATION_NOTICE;
    	if ("ENDNOTE_SEPARATOR".equals(storyTypeName)) return ENDNOTE_SEPARATOR;
    	if ("ENDNOTE_CONTINUATION_SEPARATOR".equals(storyTypeName)) return ENDNOTE_CONTINUATION_SEPARATOR;
    	if ("ENDNOTE_CONTINUATION_NOTICE".equals(storyTypeName)) return ENDNOTE_CONTINUATION_NOTICE;
    	throw new IllegalArgumentException("Unknown StoryType name.");
    }
    
    /// <summary>
    /// Returns array of StoryType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		MAIN_TEXT,
    		FOOTNOTES,
    		ENDNOTES,
    		COMMENTS,
    		TEXTBOX,
    		EVEN_PAGES_HEADER,
    		PRIMARY_HEADER,
    		EVEN_PAGES_FOOTER,
    		PRIMARY_FOOTER,
    		FIRST_PAGE_HEADER,
    		FIRST_PAGE_FOOTER,
    		FOOTNOTE_SEPARATOR,
    		FOOTNOTE_CONTINUATION_SEPARATOR,
    		FOOTNOTE_CONTINUATION_NOTICE,
    		ENDNOTE_SEPARATOR,
    		ENDNOTE_CONTINUATION_SEPARATOR,
    		ENDNOTE_CONTINUATION_NOTICE,
    	};
    }

    public static final int length = 18;
}

class StoryTypeHelper
{
    public static boolean isHeader(/*StoryType*/int storyType)
    {
        return (
            (storyType == StoryType.EVEN_PAGES_HEADER) ||
            (storyType == StoryType.FIRST_PAGE_HEADER) ||
            (storyType == StoryType.PRIMARY_HEADER));
    }

    public static boolean isFooter(/*StoryType*/int storyType)
    {
        return (
            (storyType == StoryType.EVEN_PAGES_FOOTER) ||
            (storyType == StoryType.FIRST_PAGE_FOOTER) ||
            (storyType == StoryType.PRIMARY_FOOTER));
    }
}

/// <summary>
/// The StoryType stack.
/// </summary>
class StoryTypeStack
{
    /// <summary>
    /// Pushes the specified StoryType.
    /// </summary>
    void push(/*StoryType*/int storyType)
    {
        mStack.push(storyType);
        handleInHeaderOrInFooterState(storyType, true);
    }

    /// <summary>
    /// Pops the specified StoryType.
    /// </summary>
    void pop(/*StoryType*/int storyType)
    {
        Debug.assertTrue(getCurrent() == storyType);
        mStack.pop();
        handleInHeaderOrInFooterState(storyType, false);
    }

    private void handleInHeaderOrInFooterState(/*StoryType*/int storyType, boolean isPush)
    {
        if (((isPush) ? !isInHeader() : isInHeader()) && StoryTypeHelper.isHeader(storyType))
            isInHeader(isPush);
        else if (((isPush) ? !isInFooter() : isInFooter()) && StoryTypeHelper.isFooter(storyType))
            isInFooter(isPush);
    }

    /// <summary>
    /// Gets the current StoryType.
    /// </summary>
    /*StoryType*/int getCurrent() { return mStack.peek(); }

    /// <summary>
    /// Returns true if the current StoryType is included in the header or footer.
    /// </summary>
    boolean isInHeaderOrFooter() { return (isInHeader() || isInFooter()); }

    /// <summary>
    /// Returns true if the current StoryType is included in the header.
    /// </summary>
    boolean isInHeader() { return mIsInHeader; }; private void isInHeader(boolean value) { mIsInHeader = value; };

    private boolean mIsInHeader;

    /// <summary>
    /// Returns true if the current StoryType is included in the footer.
    /// </summary>
    boolean isInFooter() { return mIsInFooter; }; private void isInFooter(boolean value) { mIsInFooter = value; };

    private boolean mIsInFooter;

    private /*final*/ msStack<Integer> mStack = new msStack<Integer>();
}

