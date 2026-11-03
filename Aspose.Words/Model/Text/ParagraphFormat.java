// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/05/2005 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.Collections.SortedList;


/// <summary>
/// Represents all the formatting for a paragraph.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-paragraphs/">Working with Paragraphs</a> documentation article.</para>
/// </summary>
public class ParagraphFormat implements IBorderAttrSource, IShadingAttrSource
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="parent">The object that provides paragraph attributes.</param>
    /// <param name="styles">The styles of the document, needed for resolution of some properties.
    /// It is possible to pass <c>null</c> here, but some of the properties will throw.</param>
    ParagraphFormat(IParaAttrSource parent, StyleCollection styles)
    {
        mParent = parent;
        mStyles = styles;
    }

    /// <summary>
    /// Resets to default paragraph formatting.
    /// </summary>
    /// <remarks>
    /// Default paragraph formatting is Normal style, left aligned, no indentation,
    /// no spacing, no borders and no shading.
    /// </remarks>
    public void clearFormatting()
    {
        mParent.clearParaAttrs();
    }

    /// <summary>
    /// Gets or sets text alignment for the paragraph.
    /// </summary>
    public /*ParagraphAlignment*/int getAlignment() { return (/*ParagraphAlignment*/Integer)mParent.fetchParaAttr(ParaAttr.ALIGNMENT); }
    public void setAlignment(/*ParagraphAlignment*/int value) { mParent.setParaAttr(ParaAttr.ALIGNMENT, value); }

    /// <summary>
    /// Gets or sets fonts vertical position on a line.
    /// </summary>
    public /*BaselineAlignment*/int getBaselineAlignment() { return (/*BaselineAlignment*/Integer)mParent.fetchParaAttr(ParaAttr.BASELINE_ALIGNMENT); }
    public void setBaselineAlignment(/*BaselineAlignment*/int value) { mParent.setParaAttr(ParaAttr.BASELINE_ALIGNMENT, value); }

    boolean isJustified()
    {
        return
            ((getAlignment() == ParagraphAlignment.JUSTIFY) ||
            (getAlignment() == ParagraphAlignment.DISTRIBUTED));
    }

    /// <summary>
    /// When <c>true</c>, <see cref="SpaceBefore"/> and <see cref="SpaceAfter"/> will be ignored
    /// between the paragraphs of the same style.
    /// </summary>
    /// <remarks>
    /// <p>This setting only takes affect when applied to a paragraph style. If applied to
    /// a paragraph directly, it has no effect.</p>
    /// </remarks>
    public boolean getNoSpaceBetweenParagraphsOfSameStyle() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.NO_SPACE_BETWEEN_SAME_STYLE); }
    public void setNoSpaceBetweenParagraphsOfSameStyle(boolean value) { mParent.setParaAttr(ParaAttr.NO_SPACE_BETWEEN_SAME_STYLE, value); }

    boolean getSideBySide() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.SIDE_BY_SIDE); }
    void setSideBySide(boolean value) { mParent.setParaAttr(ParaAttr.SIDE_BY_SIDE, value); }

    /// <summary>
    /// True if all lines in the paragraph are to remain on the same page.
    /// </summary>
    public boolean getKeepTogether() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.KEEP_TOGETHER); }
    public void setKeepTogether(boolean value) { mParent.setParaAttr(ParaAttr.KEEP_TOGETHER, value); }

    /// <summary>
    /// True if the paragraph is to remains on the same page as the paragraph that follows it.
    /// </summary>
    public boolean getKeepWithNext() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.KEEP_WITH_NEXT); }
    public void setKeepWithNext(boolean value) { mParent.setParaAttr(ParaAttr.KEEP_WITH_NEXT, value); }

    /// <summary>
    /// True if a page break is forced before the paragraph.
    /// </summary>
    public boolean getPageBreakBefore() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.PAGE_BREAK_BEFORE); }
    public void setPageBreakBefore(boolean value) { mParent.setParaAttr(ParaAttr.PAGE_BREAK_BEFORE, value); }

    /// <summary>
    /// Specifies whether the current paragraph's lines should be exempted from line numbering
    /// which is applied in the parent section.
    /// </summary>
    public boolean getSuppressLineNumbers() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.SUPPRESS_LINE_NUMBERS); }
    public void setSuppressLineNumbers(boolean value) { mParent.setParaAttr(ParaAttr.SUPPRESS_LINE_NUMBERS, value); }

    /// <summary>
    /// Specifies whether the current paragraph should be exempted from any hyphenation which
    /// is applied in the document settings.
    /// </summary>
    public boolean getSuppressAutoHyphens() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.SUPPRESS_AUTO_HYPHENS); }
    public void setSuppressAutoHyphens(boolean value) { mParent.setParaAttr(ParaAttr.SUPPRESS_AUTO_HYPHENS, value); }

    /// <summary>
    /// True if the first and last lines in the paragraph are to remain on the same page as the rest of the paragraph.
    /// </summary>
    public boolean getWidowControl() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.WIDOW_CONTROL); }
    public void setWidowControl(boolean value) { mParent.setParaAttr(ParaAttr.WIDOW_CONTROL, value); }

    /// <summary>
    /// Gets or sets a flag indicating whether inter-character spacing is automatically adjusted between regions
    /// of Latin text and regions of East Asian text in the current paragraph.
    /// </summary>
    public boolean getAddSpaceBetweenFarEastAndAlpha() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_ALPHA); }
    public void setAddSpaceBetweenFarEastAndAlpha(boolean value) { mParent.setParaAttr(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_ALPHA, value); }

    /// <summary>
    /// Gets or sets a flag indicating whether inter-character spacing is automatically adjusted between regions
    /// of numbers and regions of East Asian text in the current paragraph.
    /// </summary>
    public boolean getAddSpaceBetweenFarEastAndDigit() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_DIGIT); }
    public void setAddSpaceBetweenFarEastAndDigit(boolean value) { mParent.setParaAttr(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_DIGIT, value); }

    /// <summary>
    /// Gets or sets a flag indicating whether East Asian line-breaking rules are applied to the current paragraph.
    /// </summary>
    public boolean getFarEastLineBreakControl() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.FAR_EAST_LINE_BREAK_CONTROL); }
    public void setFarEastLineBreakControl(boolean value) { mParent.setParaAttr(ParaAttr.FAR_EAST_LINE_BREAK_CONTROL, value); }

    /// <summary>
    /// If this property is <c>false</c>, Latin text in the middle of a word can be wrapped for
    /// the current paragraph. Otherwise Latin text is wrapped by whole words.
    /// </summary>
    public boolean getWordWrap() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.WORD_WRAP); }
    public void setWordWrap(boolean value) { mParent.setParaAttr(ParaAttr.WORD_WRAP, value); }

    /// <summary>
    /// Gets or sets a flag indicating whether hanging punctuation is enabled for the current paragraph.
    /// </summary>
    public boolean getHangingPunctuation() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.HANGING_PUNCTUATION); }
    public void setHangingPunctuation(boolean value) { mParent.setParaAttr(ParaAttr.HANGING_PUNCTUATION, value); }

    boolean getTopLinePunctuation() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.TOP_LINE_PUNCTUATION); }
    void setTopLinePunctuation(boolean value) { mParent.setParaAttr(ParaAttr.TOP_LINE_PUNCTUATION, value); }

    boolean getAutoAdjustRightIndent() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.AUTO_ADJUST_RIGHT_INDENT); }
    void setAutoAdjustRightIndent(boolean value) { mParent.setParaAttr(ParaAttr.AUTO_ADJUST_RIGHT_INDENT, value); }

    /// <summary>
    /// Gets or sets whether this is a right-to-left paragraph.
    /// </summary>
    /// <remarks>
    /// <p>When <c>true</c>, the runs and other inline objects in this paragraph
    /// are laid out right to left.</p>
    /// </remarks>
    public boolean getBidi() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.BIDI); }
    public void setBidi(boolean value) { mParent.setParaAttr(ParaAttr.BIDI, value); }

    /// <summary>
    /// Gets or sets the value (in points) that represents the left indent for paragraph.
    /// </summary>
    public double getLeftIndent() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.LEFT_INDENT)); }
    public void setLeftIndent(double value)
    {
        mParent.removeParaAttr(ParaAttr.LEFT_INDENT_UNITS);
        mParent.setParaAttr(ParaAttr.LEFT_INDENT, ConvertUtilCore.pointToTwip(value));
    }

    /// <summary>
    /// Gets or sets the left indent value (in characters) for the specified paragraphs.
    /// </summary>
    public double getCharacterUnitLeftIndent() { return ((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.LEFT_INDENT_UNITS)) / 100.0; }
    public void setCharacterUnitLeftIndent(double value) { mParent.setParaAttr(ParaAttr.LEFT_INDENT_UNITS, (int)(value * 100.0)); }

    /// <summary>
    /// Gets or sets the value (in points) that represents the right indent for paragraph.
    /// </summary>
    public double getRightIndent() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.RIGHT_INDENT)); }
    public void setRightIndent(double value)
    {
        mParent.removeParaAttr(ParaAttr.RIGHT_INDENT_UNITS);
        mParent.setParaAttr(ParaAttr.RIGHT_INDENT, ConvertUtilCore.pointToTwip(value));
    }

    /// <summary>
    /// Gets or sets the right indent value (in characters) for the specified paragraphs.
    /// </summary>
    public double getCharacterUnitRightIndent() { return ((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.RIGHT_INDENT_UNITS)) / 100.0; }
    public void setCharacterUnitRightIndent(double value) { mParent.setParaAttr(ParaAttr.RIGHT_INDENT_UNITS, (int)(value * 100.0)); }

    /// <summary>
    /// Gets or sets the value (in points) for a first line or hanging indent.
    /// <p>Use positive values to set the first-line indent, and negative values to set the hanging indent.</p>
    /// </summary>
    public double getFirstLineIndent() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.FIRST_LINE_INDENT)); }
    public void setFirstLineIndent(double value)
    {
        mParent.removeParaAttr(ParaAttr.FIRST_LINE_INDENT_UNITS);
        mParent.setParaAttr(ParaAttr.FIRST_LINE_INDENT, ConvertUtilCore.pointToTwip(value));
    }

    /// <summary>
    /// Gets or sets the value (in characters) for the first-line or hanging indent.
    /// <p>Use positive values to set the first-line indent, and negative values to set the hanging indent.</p>
    /// </summary>
    public double getCharacterUnitFirstLineIndent() { return ((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.FIRST_LINE_INDENT_UNITS)) / 100.0; }
    public void setCharacterUnitFirstLineIndent(double value) { mParent.setParaAttr(ParaAttr.FIRST_LINE_INDENT_UNITS, (int)(value * 100.0)); }

    /// <summary>
    /// True if the amount of spacing before the paragraph is set automatically.
    /// </summary>
    /// <remarks>
    /// <p>When set to <c>true</c>, overrides the effect of <see cref="SpaceBefore"/>.</p>
    /// </remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="ParagraphFormat.AutoSpacing"]/*'/>
    public boolean getSpaceBeforeAuto() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.SPACE_BEFORE_AUTO); }
    public void setSpaceBeforeAuto(boolean value) { mParent.setParaAttr(ParaAttr.SPACE_BEFORE_AUTO, value); }

    /// <summary>
    /// True if the amount of spacing after the paragraph is set automatically.
    /// </summary>
    /// <remarks>
    /// <p>When set to <c>true</c>, overrides the effect of <see cref="SpaceAfter"/>.</p>
    /// </remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="ParagraphFormat.AutoSpacing"]/*'/>
    public boolean getSpaceAfterAuto() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.SPACE_AFTER_AUTO); }
    public void setSpaceAfterAuto(boolean value) { mParent.setParaAttr(ParaAttr.SPACE_AFTER_AUTO, value); }

    /// <summary>
    /// Gets or sets the amount of spacing (in points) before the paragraph.
    /// </summary>
    /// <remarks>
    /// <p>Has no effect when <see cref="SpaceBeforeAuto"/> is <c>true</c>.</p>
    /// <p>Valid values range from 0 to 1584 inclusive.</p>
    /// </remarks>
    /// <exception cref="ArgumentOutOfRangeException">
    /// Throws when argument was out of the range of valid values.
    /// </exception>
    public double getSpaceBefore() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.SPACE_BEFORE)); }
    public void setSpaceBefore(double value)
    {
        ArgumentUtil.validateRange(value, 0.0, 0.0, 1584.0, 1584.0, true, "SpaceBefore");

        // AM. Actually Word does not remove units when normal spacing/indent set but
        // lets postpone till customer requests.
        mParent.removeParaAttr(ParaAttr.SPACE_BEFORE_UNITS);
        mParent.setParaAttr(ParaAttr.SPACE_BEFORE, ConvertUtilCore.pointToTwip(value));
    }

    /// <summary>
    /// Gets or sets the amount of spacing (in gridlines) before the paragraphs.
    /// </summary>
    public double getLineUnitBefore() { return ((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.SPACE_BEFORE_UNITS)) / 100.0; }
    public void setLineUnitBefore(double value) { mParent.setParaAttr(ParaAttr.SPACE_BEFORE_UNITS, (int)(value * 100.0)); }

    /// <summary>
    /// Gets or sets the amount of spacing (in points) after the paragraph.
    /// </summary>
    /// <remarks>
    /// <p>Has no effect when <see cref="SpaceAfterAuto"/> is <c>true</c>.</p>
    /// <p>Valid values ​​range from 0 to 1584 inclusive.</p>
    /// </remarks>
    /// <exception cref="ArgumentOutOfRangeException">
    /// Throws when argument was out of the range of valid values.
    /// </exception>
    public double getSpaceAfter() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.SPACE_AFTER)); }
    public void setSpaceAfter(double value)
    {
        // WORDSNET-19480 Added validation.
        ArgumentUtil.validateRange(value, 0.0, 0.0, 1584.0, 1584.0, true, "SpaceAfter");

        mParent.removeParaAttr(ParaAttr.SPACE_AFTER_UNITS);
        mParent.setParaAttr(ParaAttr.SPACE_AFTER, ConvertUtilCore.pointToTwip(value));
    }

    /// <summary>
    /// Gets or sets the amount of spacing (in gridlines) after the paragraphs.
    /// </summary>
    public double getLineUnitAfter() { return ((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.SPACE_AFTER_UNITS)) / 100.0; }
    public void setLineUnitAfter(double value) { mParent.setParaAttr(ParaAttr.SPACE_AFTER_UNITS, (int)(value * 100.0)); }

    /// <summary>
    /// Gets or sets the line spacing for the paragraph.
    /// </summary>
    public /*LineSpacingRule*/int getLineSpacingRule() { return ((LineSpacing)mParent.fetchParaAttr(ParaAttr.LINE_SPACING)).getRule(); }
    public void setLineSpacingRule(/*LineSpacingRule*/int value)
    {
        LineSpacing lineSpacing = (LineSpacing)fetchOrCreateComplexParaAttr(ParaAttr.LINE_SPACING);
        lineSpacing.setRule(value);
    }

    /// <summary>
    /// Gets or sets the line spacing (in points) for the paragraph.
    /// </summary>
    /// <remarks>
    /// <p>When <see cref="LineSpacingRule"/> property is set to <see cref="LineSpacingRule.AtLeast"/>, the line spacing can be greater than or equal to,
    /// but never less than the specified <see cref="LineSpacing"/> value.</p>
    /// <p>When <see cref="LineSpacingRule"/> property is set to <see cref="LineSpacingRule.Exactly"/>, the line spacing never changes from
    /// the specified <see cref="LineSpacing"/> value, even if a larger font is used within the paragraph.</p>
    /// </remarks>
    public double getLineSpacing() { return ConvertUtilCore.twipToPoint(((LineSpacing)mParent.fetchParaAttr(ParaAttr.LINE_SPACING)).getValue()); }
    public void setLineSpacing(double value)
    {
        LineSpacing lineSpacing = (LineSpacing)fetchOrCreateComplexParaAttr(ParaAttr.LINE_SPACING);
        lineSpacing.setValue(ConvertUtilCore.pointToTwip(value));
    }

    /// <summary>
    /// Gets or sets a flag indicating whether the left and right indents are of the same width.
    /// </summary>
    public boolean getMirrorIndents() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.MIRROR_INDENTS); }
    public void setMirrorIndents(boolean value) { mParent.setParaAttr(ParaAttr.MIRROR_INDENTS, value); }

    /// <summary>
    /// Gets the LineSpacing / 12 value.
    /// </summary>
    double getLineSpacingInLines() { return ConvertUtilCore.pointToLine(getLineSpacing()); }

    /// <summary>
    /// True when the paragraph style is one of the built-in Heading styles.
    /// </summary>
    public boolean isHeading() { return getStyle().isHeading(); }

    /// <summary>
    /// True when the paragraph is an item in a bulleted or numbered list.
    /// </summary>
    public boolean isListItem() { return (getListId() != 0); }

    /// <summary>
    /// In Word documents, lists may consist of up to nine levels, numbered 0 to 8.
    /// </summary>
    int getListLevel() { return (/*int*/Integer)mParent.fetchParaAttr(ParaAttr.LIST_LEVEL); }
    void setListLevel(int value) { mParent.setParaAttr(ParaAttr.LIST_LEVEL, value); }

    /// <summary>
    /// This is actually ilfo. I should probably rename it into ilfo.
    /// </summary>
    int getListId() { return (/*int*/Integer)mParent.fetchParaAttr(ParaAttr.LIST_ID); }
    void setListId(int value) { mParent.setParaAttr(ParaAttr.LIST_ID, value); }

    /// <summary>
    /// Specifies the outline level of the paragraph in the document.
    /// </summary>
    public /*OutlineLevel*/int getOutlineLevel() { return (/*OutlineLevel*/Integer)mParent.fetchParaAttr(ParaAttr.OUTLINE_LEVEL); }
    public void setOutlineLevel(/*OutlineLevel*/int value)
    {
        if ((value < OutlineLevel.LEVEL_1) || (value > OutlineLevel.BODY_TEXT))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        mParent.setParaAttr(ParaAttr.OUTLINE_LEVEL, value);
    }

    /// <summary>
    /// Text frame.
    /// </summary>
    double getFrameWidth() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_WIDTH)); }
    void setFrameWidth(double value) { mParent.setParaAttr(ParaAttr.FRAME_WIDTH, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*HeightRule*/int getFrameHeightRule()
    {
        Height height = (Height)mParent.fetchParaAttr(ParaAttr.FRAME_HEIGHT);
        return height.getRule();
    }
    void setFrameHeightRule(/*HeightRule*/int value)
    {
        Height height = (Height)fetchOrCreateComplexParaAttr(ParaAttr.FRAME_HEIGHT);
        height.setRule(value);
    }

    /// <summary>
    /// Text frame.
    /// </summary>
    double getFrameHeight()
    {
        Height height = (Height)mParent.fetchParaAttr(ParaAttr.FRAME_HEIGHT);
        return ConvertUtilCore.twipToPoint(height.getValue());
    }
    void setFrameHeight(double value)
    {
        Height height = (Height)fetchOrCreateComplexParaAttr(ParaAttr.FRAME_HEIGHT);
        height.setValue(ConvertUtilCore.pointToTwip(value));
    }

    /// <summary>
    /// Text frame.
    /// </summary>
    double getFrameLeft() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_LEFT)); }
    void setFrameLeft(double value) { mParent.setParaAttr(ParaAttr.FRAME_LEFT, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Text frame.
    /// </summary>
    double getFrameTop() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_TOP)); }
    void setFrameTop(double value) { mParent.setParaAttr(ParaAttr.FRAME_TOP, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*HorizontalAlignment*/int getHorizontalAlignment() { return (/*HorizontalAlignment*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_HORIZONTAL_ALIGNMENT); }
    void setHorizontalAlignment(/*HorizontalAlignment*/int value) { mParent.setParaAttr(ParaAttr.FRAME_HORIZONTAL_ALIGNMENT, value); }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*VerticalAlignment*/int getVerticalAlignment() { return (/*VerticalAlignment*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_VERTICAL_ALIGNMENT); }
    void setVerticalAlignment(/*VerticalAlignment*/int value) { mParent.setParaAttr(ParaAttr.FRAME_VERTICAL_ALIGNMENT, value); }

    /// <summary>
    /// Text frame.
    /// </summary>
    boolean getFrameLockAnchor() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.FRAME_LOCK_ANCHOR); }
    void setFrameLockAnchor(boolean value) { mParent.setParaAttr(ParaAttr.FRAME_LOCK_ANCHOR, value); }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*RelativeHorizontalPosition*/int getRelativeHorizontalPosition() { return (/*RelativeHorizontalPosition*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION); }
    void setRelativeHorizontalPosition(/*RelativeHorizontalPosition*/int value) { mParent.setParaAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION, value); }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*RelativeVerticalPosition*/int getRelativeVerticalPosition() { return (/*RelativeVerticalPosition*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION); }
    void setRelativeVerticalPosition(/*RelativeVerticalPosition*/int value) { mParent.setParaAttr(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION, value); }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*WrapType*/int getWrapType() { return (/*WrapType*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_WRAP_TYPE); }
    void setWrapType(/*WrapType*/int value) { mParent.setParaAttr(ParaAttr.FRAME_WRAP_TYPE, value); }

    /// <summary>
    /// Text frame.
    /// </summary>
    double getFrameHorizontalDistanceFromText() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT)); }
    void setFrameHorizontalDistanceFromText(double value) { mParent.setParaAttr(ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Text frame.
    /// </summary>
    double getFrameVerticalDistanceFromText() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT)); }
    void setFrameVerticalDistanceFromText(double value) { mParent.setParaAttr(ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*TextOrientation*/int getFrameTextOrientation() { return (/*TextOrientation*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_TEXT_ORIENTATION); }
    void setFrameTextOrientation(/*TextOrientation*/int value) { mParent.setParaAttr(ParaAttr.FRAME_TEXT_ORIENTATION, value); }

    /// <summary>
    /// Text frame.
    /// </summary>
    boolean getFrameSuppressOverlap() { return (/*boolean*/Boolean) mParent.fetchParaAttr(ParaAttr.FRAME_SUPPRESS_OVERLAP); }
    void setFrameSuppressOverlap(boolean value) { mParent.setParaAttr(ParaAttr.FRAME_SUPPRESS_OVERLAP, value); }

    /// <summary>
    /// Gets or sets the number of lines of the paragraph text used to calculate the drop cap height.
    /// </summary>
    public int getLinesToDrop() { return (/*int*/Integer)mParent.fetchParaAttr(ParaAttr.DROP_CAP_LINES_TO_DROP); }
    public void setLinesToDrop(int value)
    {
        if (value < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        mParent.setParaAttr(ParaAttr.DROP_CAP_LINES_TO_DROP, value);
    }

    /// <summary>
    /// Gets or sets the position for a drop cap text.
    /// </summary>
    public /*DropCapPosition*/int getDropCapPosition() { return (/*DropCapPosition*/Integer)mParent.fetchParaAttr(ParaAttr.DROP_CAP_POSITION); }
    public void setDropCapPosition(/*DropCapPosition*/int value)
    {
        if ((mParent.getDirectParaAttr(ParaAttr.DROP_CAP_POSITION) == null) && (value == DropCapPosition.NONE))
            return;

        mParent.setParaAttr(ParaAttr.DROP_CAP_POSITION, value);

        // andrnosk: WORDSNET-6606 We need to set specific FrameRelativeHorizontalPosition and
        // FrameRelativeVerticalPosition upon specifying DropCapPosition.
        setDropCapFrameRelativePosition(value);
    }

    /// <summary>
    /// Set specific DropCap Frame Relative position.
    /// </summary>
    private void setDropCapFrameRelativePosition(/*DropCapPosition*/int value)
    {
        switch (value)
        {
            case DropCapPosition.MARGIN:
                {
                    mParent.setParaAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION, RelativeHorizontalPosition.PAGE);
                    mParent.setParaAttr(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION, RelativeHorizontalPosition.DEFAULT);
                    break;
                }
            case DropCapPosition.NORMAL:
                {
                    mParent.setParaAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION, RelativeHorizontalPosition.DEFAULT);
                    mParent.setParaAttr(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION, RelativeHorizontalPosition.DEFAULT);
                    break;
                }
            case DropCapPosition.NONE:
            default:
                break;
        }
    }

    /// <summary>
    /// Returns a <see cref="Aspose.Words.Shading"/> object that refers to the shading formatting for the paragraph.
    /// </summary>
    public Shading getShading()
    {
        //<<GetOrCreateComplexAttr>> pattern
        Shading shading = (Shading)mParent.getDirectParaAttr(ParaAttr.SHADING);
        if (shading == null)
        {
            ParagraphFormat parent = this;
            shading = new Shading(parent, ParaAttr.SHADING);
            shading = shading;
            mParent.setParaAttr(ParaAttr.SHADING, shading);
        }
        return shading;
    }

    /// <summary>
    /// Gets collection of borders of the paragraph.
    /// </summary>
    public BorderCollection getBorders()
    {
        if (mBorders == null)
            mBorders = new BorderCollection(this);
        return mBorders;
    }

    /// <summary>
    /// Gets or sets the paragraph style applied to this formatting.
    /// </summary>
    public Style getStyle()
    {
        if (mStyles == null)
            return null;

        /*RevisionsView*/int revisionsView = (mStyles.getDocument().getNodeType() == NodeType.DOCUMENT)
            ? ((Document)mStyles.getDocument()).getRevisionsView()
            : RevisionsView.ORIGINAL;

        // Try to get RevisionViews aware Istd.
        int istd = (mParent.getDirectParaAttr(ParaAttr.ISTD, revisionsView) != null)
            ? (/*int*/Integer)mParent.getDirectParaAttr(ParaAttr.ISTD, revisionsView)
            : getIstd();

        return mStyles.fetchByIstd(istd, StyleIndex.NORMAL);
    }
    public void setStyle(Style value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");

        if (value.getDocument() != mStyles.getDocument())
            throw new IllegalArgumentException("This style belongs to a different document.");

        if (value.getType() != StyleType.PARAGRAPH)
            throw new IllegalArgumentException("This style is not a paragraph style.");

        setIstd(value.getIstd());
    }

    /// <summary>
    /// Gets or sets the name of the paragraph style applied to this formatting.
    /// </summary>
    public String getStyleName() { return getStyle().getName(); }
    public void setStyleName(String value) { setStyle(mStyles.fetchByName(value)); }

    /// <summary>
    /// Gets or sets the locale independent style identifier of the paragraph style applied to this formatting.
    /// </summary>
    public /*StyleIdentifier*/int getStyleIdentifier() { return getStyle().getStyleIdentifier(); }
    public void setStyleIdentifier(/*StyleIdentifier*/int value) { setStyle(mStyles.fetchBySti(value)); }

    /// <summary>
    /// Specifies whether the current paragraph should use the document grid lines per page settings
    /// when laying out the contents in the paragraph.
    /// </summary>
    public boolean getSnapToGrid() { return (/*boolean*/Boolean)mParent.fetchParaAttr(ParaAttr.SNAP_TO_GRID); }
    public void setSnapToGrid(boolean value) { mParent.setParaAttr(ParaAttr.SNAP_TO_GRID, value); }

    int getIstd()
    {
        Object istd = mParent.getDirectParaAttr(ParaAttr.ISTD);
        // When getting the style of the paragraph, don't try to retrieve the value from the parent
        // since the parent is the style and it will result in an endless loop.
        return (istd != null) ? (/*int*/Integer)istd : (/*int*/Integer)ParaPr.fetchDefaultAttr(ParaAttr.ISTD);
    }
    void setIstd(int value)
    {
        mParent.setParaAttr(ParaAttr.ISTD, value);
    }

    /// <summary>
    /// Gets the collection of custom tab stops defined for this object.
    /// </summary>
    public TabStopCollection getTabStops()
    {
        //<<GetOrCreateComplexAttr>> pattern
        TabStopCollection tabStops = (TabStopCollection)mParent.getDirectParaAttr(ParaAttr.TAB_STOPS);
        if (tabStops == null)
        {
            tabStops = new TabStopCollection();
            mParent.setParaAttr(ParaAttr.TAB_STOPS, tabStops);
        }
        return tabStops;
    }

    /// <summary>
    /// Gets the attribute from the attribute collection or from one of the parents.
    /// </summary>
    Object fetchAttr(int key)
    {
        return mParent.fetchParaAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/getDirectBorderAttr(int key)
    {
        return mParent.getDirectParaAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/fetchInheritedBorderAttr(int key)
    {
        return mParent.fetchInheritedParaAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IBorderAttrSource.*/setBorderAttr(int key, Object value)
    {
        mParent.setParaAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public SortedList<Integer, Integer> /*IBorderAttrSource.*/getPossibleBorderKeys() { return ParaPr.POSSIBLE_BORDER_KEYS; }

    @ReservedForInternalUse @Deprecated
    public Object /*IShadingAttrSource.*/fetchInheritedShadingAttr(int key)
    {
        return mParent.fetchInheritedParaAttr(key);
    }

    private Object fetchOrCreateComplexParaAttr(int key)
    {
        Object directValue = mParent.getDirectParaAttr(key);

        if (directValue != null)
            return directValue;

        Object defaultValue = ParaPr.fetchDefaultAttr(key);

        Object clonedValue = ((IComplexAttr)defaultValue).deepCloneComplexAttr();
        mParent.setParaAttr(key, clonedValue);

        return clonedValue;
    }

    private /*final*/ IParaAttrSource mParent;
    private /*final*/ StyleCollection mStyles;
    private BorderCollection mBorders;

    /// <summary>
    /// Microsoft Word adds 14 points when auto space after or before is turned on.
    /// </summary>
    static final float AUTO_SPACE_SIZE = 14f;

}

