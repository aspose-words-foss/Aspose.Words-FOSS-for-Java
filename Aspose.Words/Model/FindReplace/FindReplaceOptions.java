// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies options for find/replace operations.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/find-and-replace/">Find and Replace</a> documentation article.</para>
/// </summary>
public class FindReplaceOptions
{
    /// <summary>
    /// Initializes a new instance of the FindReplaceOptions class with default settings.
    /// </summary>
    public FindReplaceOptions()
    {
        mApplyFont = new Font(mApplyRunPr, null);
        mApplyParagraphFormat = new ParagraphFormat(mApplyParaPr, null);
    }

    /// <summary>
    /// Initializes a new instance of the FindReplaceOptions class with the specified direction.
    /// </summary>
    /// <param name="direction">The direction of the find and replace operation.</param>
    public FindReplaceOptions(/*FindReplaceDirection*/int direction)
    {
    	this();
	
        mDirection = direction;
    }

    /// <summary>
    /// Initializes a new instance of the FindReplaceOptions class with the specified replacing callback.
    /// </summary>
    /// <param name="replacingCallback">The callback to use for replacing found text.</param>
    public FindReplaceOptions(IReplacingCallback replacingCallback)
    {
    	this();
	
        setReplacingCallback(replacingCallback);
    }

    /// <summary>
    /// Initializes a new instance of the FindReplaceOptions class with the specified direction and replacing callback.
    /// </summary>
    /// <param name="direction">The direction of the find and replace operation.</param>
    /// <param name="replacingCallback">The callback to use for replacing found text.</param>
    public FindReplaceOptions(/*FindReplaceDirection*/int direction, IReplacingCallback replacingCallback)
    {
    	this();
	
        mDirection = direction;
        setReplacingCallback(replacingCallback);
    }

    /// <summary>
    /// Text formatting applied to new content.
    /// </summary>
    public Font getApplyFont() { return mApplyFont; }

    /// <summary>
    /// Paragraph formatting applied to new content.
    /// </summary>
    public ParagraphFormat getApplyParagraphFormat() { return mApplyParagraphFormat; }

    /// <summary>
    /// Selects direction for replace. Default value is <see cref="FindReplaceDirection.Forward" />.
    /// </summary>
    public /*FindReplaceDirection*/int getDirection() { return mDirection; }
    public void setDirection(/*FindReplaceDirection*/int value) { mDirection = value; }

    /// <summary>
    /// True indicates case-sensitive comparison, false indicates case-insensitive comparison.
    /// </summary>
    public boolean getMatchCase() { return mMatchCase; }
    public void setMatchCase(boolean value) { mMatchCase = value; }

    /// <summary>
    /// True indicates the oldValue must be a standalone word.
    /// </summary>
    public boolean getFindWholeWordsOnly() { return mFindWholeWordsOnly; }
    public void setFindWholeWordsOnly(boolean value) { mFindWholeWordsOnly = value; }

    /// <summary>
    /// The user-defined method which is called before every replace occurrence.
    /// </summary>
    public IReplacingCallback getReplacingCallback() { return mReplacingCallback; }
    public void setReplacingCallback(IReplacingCallback value) { mReplacingCallback = value; }

    /// <summary>
    /// True indicates that a text search is performed sequentially from top to bottom considering the text boxes.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <dev>
    /// WORDSNET-19357 Added option.
    /// </dev>
    public boolean getUseLegacyOrder() { return mUseLegacyOrder; }
    public void setUseLegacyOrder(boolean value) { mUseLegacyOrder = value; }

    /// <summary>
    /// Gets or sets a boolean value indicating either to ignore text inside delete revisions.
    /// The default value is <c>false</c>.
    /// </summary>
    public boolean getIgnoreDeleted() { return mIgnoreDeleted; }
    public void setIgnoreDeleted(boolean value) { mIgnoreDeleted = value; }

    /// <summary>
    /// Gets or sets a boolean value indicating either to ignore text inside insert revisions.
    /// The default value is <c>false</c>.
    /// </summary>
    public boolean getIgnoreInserted() { return mIgnoreInserted; }
    public void setIgnoreInserted(boolean value) { mIgnoreInserted = value; }

    /// <summary>
    /// Gets or sets a boolean value indicating either to ignore text inside fields.
    /// The default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>This option affects whole field (all nodes between
    /// <see cref="NodeType.FieldStart"/> and <see cref="NodeType.FieldEnd"/>).</para>
    /// <para>To ignore only field codes, please use corresponding option <see cref="IgnoreFieldCodes"/>.</para>
    /// </remarks>
    public boolean getIgnoreFields() { return mIgnoreFields; }
    public void setIgnoreFields(boolean value) { mIgnoreFields = value; }

    /// <summary>
    /// Gets or sets a boolean value indicating either to ignore text inside field codes.
    /// The default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>This option affects only field codes (it does not ignore nodes between
    /// <see cref="NodeType.FieldSeparator"/> and <see cref="NodeType.FieldEnd"/>).</para>
    /// <para>To ignore whole field, please use corresponding option <see cref="IgnoreFields"/>.</para>
    /// </remarks>
    public boolean getIgnoreFieldCodes() { return mIgnoreFieldCodes; }
    public void setIgnoreFieldCodes(boolean value) { mIgnoreFieldCodes = value; }

    /// <summary>
    /// Gets or sets a boolean value indicating either to ignore footnotes.
    /// The default value is <c>false</c>.
    /// </summary>
    public boolean getIgnoreFootnotes() { return mIgnoreFootnotes; }; public void setIgnoreFootnotes(boolean value) { mIgnoreFootnotes = value; };

    private boolean mIgnoreFootnotes;

    /// <summary>
    /// Gets or sets a boolean value indicating whether to recognize and use substitutions within replacement patterns.
    /// The default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// For the details on substitution elements please refer to:
    /// https://docs.microsoft.com/en-us/dotnet/standard/base-types/substitutions-in-regular-expressions.
    /// </remarks>
    public boolean getUseSubstitutions() { return mUseSubstitutions; }
    public void setUseSubstitutions(boolean value) { mUseSubstitutions = value; }

    /// <summary>
    /// Gets or sets a boolean value indicating that old find/replace algorithm is used.
    /// </summary>
    /// <remarks>
    /// Use this flag if you need exactly the same behavior as before advanced find/replace feature was introduced.
    /// Note that old algorithm does not support advanced features such as replace with breaks, apply formatting and so on.
    /// </remarks>
    public boolean getLegacyMode() { return mLegacyMode; }
    public void setLegacyMode(boolean value) { mLegacyMode = value; }

    /// <summary>
    /// Gets or sets a boolean value indicating either to ignore content of <see cref="StructuredDocumentTag"/>.
    /// The default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>
    /// When this option is set to <c>true</c>, the content of <see cref="StructuredDocumentTag"/>
    /// will be treated as a simple text.
    /// </para>
    /// <para>
    /// Otherwise, <see cref="StructuredDocumentTag"/> will be processed as standalone Story
    /// and replacing pattern will be searched separately for each <see cref="StructuredDocumentTag"/>,
    /// so that if pattern crosses a <see cref="StructuredDocumentTag"/>, then replacement will not
    /// be performed for such pattern.
    /// </para>
    /// </remarks>
    public boolean getIgnoreStructuredDocumentTags() { return mIgnoreStructuredDocumentTags; }; public void setIgnoreStructuredDocumentTags(boolean value) { mIgnoreStructuredDocumentTags = value; };

    private boolean mIgnoreStructuredDocumentTags;

    /// <summary>
    /// <para>Gets or sets a boolean value indicating either it is allowed to replace paragraph break
    /// when there is no next sibling paragraph.</para>
    /// <para>The default value is <c>false</c>.</para>
    /// </summary>
    /// <remarks>
    /// This option allows to replace paragraph break when there is no next sibling paragraph to which all child
    /// nodes can be moved, by finding any (not necessarily sibling) next paragraph after the paragraph being replaced.
    /// </remarks>
    public boolean getSmartParagraphBreakReplacement() { return mSmartParagraphBreakReplacement; }; public void setSmartParagraphBreakReplacement(boolean value) { mSmartParagraphBreakReplacement = value; };

    private boolean mSmartParagraphBreakReplacement;

    /// <summary>
    /// <para>Gets or sets a boolean value indicating either to ignore shapes within a text.</para>
    /// <para>The default value is <c>false</c>.</para>
    /// </summary>
    public boolean getIgnoreShapes() { return mIgnoreShapes; }; public void setIgnoreShapes(boolean value) { mIgnoreShapes = value; };

    private boolean mIgnoreShapes;

    /// <summary>
    /// Gets or sets a boolean value indicating either to ignore text inside OfficeMath/>.
    /// The default value is <c>true</c>.
    /// </summary>
    public boolean getIgnoreOfficeMath() { return mIgnoreOfficeMath; }
    public void setIgnoreOfficeMath(boolean value) { mIgnoreOfficeMath = value; }

    RunPr getApplyRunPr() { return mApplyRunPr; }

    ParaPr getApplyParaPr() { return mApplyParaPr; }

    private /*FindReplaceDirection*/int mDirection = FindReplaceDirection.FORWARD;

    private /*final*/ Font mApplyFont;
    private /*final*/ ParagraphFormat mApplyParagraphFormat;

    private /*final*/ RunPr mApplyRunPr = new RunPr();
    private /*final*/ ParaPr mApplyParaPr = new ParaPr();

    private boolean mMatchCase;
    private boolean mFindWholeWordsOnly;
    private IReplacingCallback mReplacingCallback;
    private boolean mUseLegacyOrder;

    private boolean mIgnoreDeleted;
    private boolean mIgnoreInserted;
    private boolean mIgnoreFields;
    private boolean mIgnoreFieldCodes;

    private boolean mIgnoreOfficeMath = true;

    private boolean mUseSubstitutions;
    private boolean mLegacyMode;
}

