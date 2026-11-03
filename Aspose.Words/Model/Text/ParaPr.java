// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2005 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.SortedList;


/// <summary>
/// Provides typed access to paragraph attributes.
/// </summary>
class ParaPr extends WordAttrCollection implements IParaAttrSource
{
    /*override*/ void acceptFormatRevision()
    {
        if (hasListRevision())
        {
            // WORDSNET-14997 It seems that Word removes left indent when accepts list revision.
            // I believe that actually Word removes all list related attributes
            // (LeftIndent, FirstLineIndent and TabStop) but lets limit solution for a while.
            remove(ParaAttr.LEFT_INDENT);
            // WORDSNET-19608 Word removes "hanging" attribute when numbering changed within revision.
            remove(ParaAttr.FIRST_LINE_INDENT);
        }

        acceptFormatRevisionCore(ParaAttr.ISTD, G_REVISION_PRESERVED_ATTRIBUTES);
    }

    /// <summary>
    /// Style index.
    /// </summary>
    int getIstd() { return (/*int*/Integer)fetchAttr(ParaAttr.ISTD); }
    void setIstd(int value) { setAttr(ParaAttr.ISTD, value); }

    /*ParagraphAlignment*/int getAlignment() { return (/*ParagraphAlignment*/Integer)fetchAttr(ParaAttr.ALIGNMENT); }
    void setAlignment(/*ParagraphAlignment*/int value) { setAttr(ParaAttr.ALIGNMENT, value); }

    boolean getNoSpaceBetweenSameStyle() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.NO_SPACE_BETWEEN_SAME_STYLE); }
    void setNoSpaceBetweenSameStyle(boolean value) { setAttr(ParaAttr.NO_SPACE_BETWEEN_SAME_STYLE, value); }

    boolean getSideBySide() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.SIDE_BY_SIDE); }
    void setSideBySide(boolean value) { setAttr(ParaAttr.SIDE_BY_SIDE, value); }

    boolean getKeepTogether() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.KEEP_TOGETHER); }
    void setKeepTogether(boolean value) { setAttr(ParaAttr.KEEP_TOGETHER, value); }

    boolean getKeepWithNext() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.KEEP_WITH_NEXT); }
    void setKeepWithNext(boolean value) { setAttr(ParaAttr.KEEP_WITH_NEXT, value); }

    boolean getPageBreakBefore() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.PAGE_BREAK_BEFORE); }
    void setPageBreakBefore(boolean value) { setAttr(ParaAttr.PAGE_BREAK_BEFORE, value); }

    boolean getKinsoku() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.FAR_EAST_LINE_BREAK_CONTROL); }
    void setKinsoku(boolean value) { setAttr(ParaAttr.FAR_EAST_LINE_BREAK_CONTROL, value); }

    boolean getWordWrap() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.WORD_WRAP); }
    void setWordWrap(boolean value) { setAttr(ParaAttr.WORD_WRAP, value); }

    boolean getOverflowPunctuation() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.HANGING_PUNCTUATION); }
    void setOverflowPunctuation(boolean value) { setAttr(ParaAttr.HANGING_PUNCTUATION, value); }

    boolean getTopLinePunctuation() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.TOP_LINE_PUNCTUATION); }
    void setTopLinePunctuation(boolean value) { setAttr(ParaAttr.TOP_LINE_PUNCTUATION, value); }

    /// <summary>
    /// In Word documents, lists may consist of up to nine levels, numbered 0 to 8.
    /// </summary>
    int getListLevel() { return (/*int*/Integer)fetchAttr(ParaAttr.LIST_LEVEL); }
    void setListLevel(int value)
    {
        // WORDSNET-807 list level 10 is specified.
        // Ideally, all setting value in the model should go through one place to make validation in one place only.
        value = com.aspose.words.ListLevel.makeLevelNumberValid(value);

        setAttr(ParaAttr.LIST_LEVEL, value);
    }

    /// <summary>
    /// Zero when the paragraph is not a list item.
    /// </summary>
    int getListId() { return (/*int*/Integer)fetchAttr(ParaAttr.LIST_ID); }
    void setListId(int value) { setAttr(ParaAttr.LIST_ID, value); }

    /// <summary>
    /// Returns true when ListId is present and it is zero.
    /// </summary>
    boolean isExplicitlyNotListItem() { return (containsKey(ParaAttr.LIST_ID) && (getListId() == 0)); }

    /// <summary>
    /// Gets the object that represents info about the revision to the list formatting of the paragraph.
    /// After the model passed the document post loader, presence of this object indicates the paragraph has a numbering revision.
    /// </summary>
    ParagraphNumberRevision getNumberRevision() { return (ParagraphNumberRevision)getDirectAttr(RevisionAttr.NUMBER_REVISION); }
    void setNumberRevision(ParagraphNumberRevision value) { setAttr(RevisionAttr.NUMBER_REVISION, value); }

    boolean hasNumberRevision() { return (getNumberRevision() != null); }

    boolean getSuppressLineNumbers() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.SUPPRESS_LINE_NUMBERS); }
    void setSuppressLineNumbers(boolean value) { setAttr(ParaAttr.SUPPRESS_LINE_NUMBERS, value); }

    /// <summary>
    /// Gets or sets the collection of tab stops directly.
    /// </summary>
    TabStopCollection getTabStops() { return (TabStopCollection)getDirectAttr(ParaAttr.TAB_STOPS); }
    void setTabStops(TabStopCollection value) { setAttr(ParaAttr.TAB_STOPS, value); }

    int getLeftIndent() { return (/*int*/Integer)fetchAttr(ParaAttr.LEFT_INDENT); }
    void setLeftIndent(int value) { setAttr(ParaAttr.LEFT_INDENT, value); }

    int getFirstLineIndent() { return (/*int*/Integer)fetchAttr(ParaAttr.FIRST_LINE_INDENT); }
    void setFirstLineIndent(int value) { setAttr(ParaAttr.FIRST_LINE_INDENT, value); }

    int getRightIndent() { return (/*int*/Integer)fetchAttr(ParaAttr.RIGHT_INDENT); }
    void setRightIndent(int value) { setAttr(ParaAttr.RIGHT_INDENT, value); }

    int getLineSpacing() { return ((LineSpacing)fetchAttr(ParaAttr.LINE_SPACING)).getValue(); }
    void setLineSpacing(int value) { ((LineSpacing)getOrCreateComplexAttr(ParaAttr.LINE_SPACING)).setValue(value); }

    /*LineSpacingRule*/int getLineSpacingRule() { return ((LineSpacing)fetchAttr(ParaAttr.LINE_SPACING)).getRule(); }
    void setLineSpacingRule(/*LineSpacingRule*/int value) { ((LineSpacing)getOrCreateComplexAttr(ParaAttr.LINE_SPACING)).setRule(value); }

    int getSpaceBefore() { return (/*int*/Integer)fetchAttr(ParaAttr.SPACE_BEFORE); }
    void setSpaceBefore(int value) { setAttr(ParaAttr.SPACE_BEFORE, value); }

    boolean getSpaceBeforeAuto() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.SPACE_BEFORE_AUTO); }
    void setSpaceBeforeAuto(boolean value) { setAttr(ParaAttr.SPACE_BEFORE_AUTO, value); }

    int getSpaceAfter() { return (/*int*/Integer)fetchAttr(ParaAttr.SPACE_AFTER); }
    void setSpaceAfter(int value) { setAttr(ParaAttr.SPACE_AFTER, value); }

    boolean getSpaceAfterAuto() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.SPACE_AFTER_AUTO); }
    void setSpaceAfterAuto(boolean value) { setAttr(ParaAttr.SPACE_AFTER_AUTO, value); }

    /*OutlineLevel*/int getOutlineLevel() { return (/*OutlineLevel*/Integer)fetchAttr(ParaAttr.OUTLINE_LEVEL); }
    void setOutlineLevel(/*OutlineLevel*/int value) { setAttr(ParaAttr.OUTLINE_LEVEL, value); }

    boolean hasFrameHorizontalAlignment() { return containsKey(ParaAttr.FRAME_HORIZONTAL_ALIGNMENT); }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*HorizontalAlignment*/int getFrameHorizontalAlignment() { return (/*HorizontalAlignment*/Integer)fetchAttr(ParaAttr.FRAME_HORIZONTAL_ALIGNMENT); }
    void setFrameHorizontalAlignment(/*HorizontalAlignment*/int value) { setAttr(ParaAttr.FRAME_HORIZONTAL_ALIGNMENT, value); }


    boolean hasFrameVerticalAlignment() { return containsKey(ParaAttr.FRAME_VERTICAL_ALIGNMENT);}

    boolean hasFrameAttributes()
    {
        return hasFrameHeight() ||
            hasFrameWidth() ||
            hasFrameLeft() ||
            hasFrameTop() ||
            hasFrameHorizontalAlignment() ||
            hasFrameVerticalAlignment() ||
            hasFrameRelativeHorizontalPosition() ||
            hasFrameRelativeVerticalPosition();

    }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*VerticalAlignment*/int getFrameVerticalAlignment() { return (/*VerticalAlignment*/Integer)fetchAttr(ParaAttr.FRAME_VERTICAL_ALIGNMENT); }
    void setFrameVerticalAlignment(/*VerticalAlignment*/int value) { setAttr(ParaAttr.FRAME_VERTICAL_ALIGNMENT, value); }

    /// <summary>
    /// Text frame.
    /// </summary>
    int getFrameLeft() { return (/*int*/Integer)fetchAttr(ParaAttr.FRAME_LEFT); }
    void setFrameLeft(int value) { setAttr(ParaAttr.FRAME_LEFT, value); }

    boolean hasFrameLeft() { return containsKey(ParaAttr.FRAME_LEFT); }

    /// <summary>
    /// Text frame.
    /// </summary>
    int getFrameTop() { return (/*int*/Integer)fetchAttr(ParaAttr.FRAME_TOP); }
    void setFrameTop(int value) { setAttr(ParaAttr.FRAME_TOP, value); }

    boolean hasFrameTop() { return containsKey(ParaAttr.FRAME_TOP); }

    /// <summary>
    /// Text frame.
    /// </summary>
    int getFrameWidth() { return (/*int*/Integer)fetchAttr(ParaAttr.FRAME_WIDTH); }
    void setFrameWidth(int value) { setAttr(ParaAttr.FRAME_WIDTH, value); }

    boolean hasFrameWidth() { return containsKey(ParaAttr.FRAME_WIDTH); }

    /// <summary>
    /// Text frame.
    /// </summary>
    int getFrameHeight() { return ((Height)fetchAttr(ParaAttr.FRAME_HEIGHT)).getValue(); }
    void setFrameHeight(int value) { ((Height)getOrCreateComplexAttr(ParaAttr.FRAME_HEIGHT)).setValue(value); }
    /// <summary>
    /// Text frame.
    /// </summary>
    /*HeightRule*/int getFrameHeightRule() { return ((Height)fetchAttr(ParaAttr.FRAME_HEIGHT)).getRule(); }
    void setFrameHeightRule(/*HeightRule*/int value) { ((Height)getOrCreateComplexAttr(ParaAttr.FRAME_HEIGHT)).setRule(value); }

    boolean hasFrameHeight() { return containsKey(ParaAttr.FRAME_HEIGHT); }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*RelativeHorizontalPosition*/int getFrameRelativeHorizontalPosition() { return (/*RelativeHorizontalPosition*/Integer)fetchAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION); }

    boolean hasFrameRelativeHorizontalPosition() { return containsKey(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION); }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*RelativeVerticalPosition*/int getFrameRelativeVerticalPosition() { return (/*RelativeVerticalPosition*/Integer)fetchAttr(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION); }

    boolean hasFrameRelativeVerticalPosition() { return containsKey(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION); }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*WrapType*/int getFrameWrapType() { return (/*WrapType*/Integer)fetchAttr(ParaAttr.FRAME_WRAP_TYPE); }

    /// <summary>
    /// Text frame.
    /// </summary>
    boolean getFrameSuppressOverlap() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.FRAME_SUPPRESS_OVERLAP); }
    void setFrameSuppressOverlap(boolean value) { setAttr(ParaAttr.FRAME_SUPPRESS_OVERLAP, value); }

    Border getBorderTop() { return (Border)fetchAttr(ParaAttr.BORDER_TOP); }
    void setBorderTop(Border value) { setAttr(ParaAttr.BORDER_TOP, value); }

    Border getBorderLeft() { return (Border)fetchAttr(ParaAttr.BORDER_LEFT); }
    void setBorderLeft(Border value) { setAttr(ParaAttr.BORDER_LEFT, value); }

    Border getBorderBottom() { return (Border)fetchAttr(ParaAttr.BORDER_BOTTOM); }
    void setBorderBottom(Border value) { setAttr(ParaAttr.BORDER_BOTTOM, value); }

    Border getBorderRight() { return (Border)fetchAttr(ParaAttr.BORDER_RIGHT); }
    void setBorderRight(Border value) { setAttr(ParaAttr.BORDER_RIGHT, value); }

    Border getBorderBetween() { return (Border)fetchAttr(ParaAttr.BORDER_BETWEEN); }
    void setBorderBetween(Border value) { setAttr(ParaAttr.BORDER_BETWEEN, value); }

    Border getBorderBar() { return (Border)fetchAttr(ParaAttr.BORDER_BAR); }
    void setBorderBar(Border value) { setAttr(ParaAttr.BORDER_BAR, value); }

    boolean getSuppressAutoHyphens() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.SUPPRESS_AUTO_HYPHENS); }
    void setSuppressAutoHyphens(boolean value) { setAttr(ParaAttr.SUPPRESS_AUTO_HYPHENS, value); }

    /*DropCapPosition*/int getDropCapPosition() { return (/*DropCapPosition*/Integer)fetchAttr(ParaAttr.DROP_CAP_POSITION); }
    void setDropCapPosition(/*DropCapPosition*/int value) { setAttr(ParaAttr.DROP_CAP_POSITION, value); }

    int getDropCapLinesToDrop() { return (/*int*/Integer)fetchAttr(ParaAttr.DROP_CAP_LINES_TO_DROP); }
    void setDropCapLinesToDrop(int value) { setAttr(ParaAttr.DROP_CAP_LINES_TO_DROP, value); }

    Shading getShading() { return (Shading)fetchAttr(ParaAttr.SHADING); }
    void setShading(Shading value) { setAttr(ParaAttr.SHADING, value); }

    boolean getWidowControl() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.WIDOW_CONTROL); }
    void setWidowControl(boolean value) { setAttr(ParaAttr.WIDOW_CONTROL, value); }

    /// <summary>
    /// HTML related information <see cref="HtmlBlock" /> is applied to this paragraph.
    /// </summary>
    int getHtmlBlockId() { return (/*int*/Integer)fetchAttr(ParaAttr.HTML_BLOCK_ID); }
    void setHtmlBlockId(int value) { setAttr(ParaAttr.HTML_BLOCK_ID, value); }

    /// <summary>
    /// Text frame.
    /// </summary>
    /*TextOrientation*/int getFrameTextOrientation() { return (/*TextOrientation*/Integer)fetchAttr(ParaAttr.FRAME_TEXT_ORIENTATION); }

    /// <summary>
    /// Text frame.
    /// </summary>
    int getFrameHorizontalDistanceFromText() { return (/*int*/Integer)fetchAttr(ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT); }

    /// <summary>
    /// Text frame.
    /// </summary>
    int getFrameVerticalDistanceFromText() { return (/*int*/Integer)fetchAttr(ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT); }

    /// <summary>
    /// Text frame.
    /// </summary>
    boolean getFrameLockAnchor() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.FRAME_LOCK_ANCHOR); }

    /*BaselineAlignment*/int getBaselineAlignment() { return (/*BaselineAlignment*/Integer)fetchAttr(ParaAttr.BASELINE_ALIGNMENT); }
    void setBaselineAlignment(/*BaselineAlignment*/int value) { setAttr(ParaAttr.BASELINE_ALIGNMENT, value); }

    boolean getBidi() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.BIDI); }
    void setBidi(boolean value) { setAttr(ParaAttr.BIDI, value); }

    /// <summary>
    /// Gets or sets the collapsed state of a paragraph.
    /// </summary>
    boolean getCollapsed() { return (/*boolean*/Boolean)fetchAttr(ParaAttr.COLLAPSED); }
    void setCollapsed(boolean value) { setAttr(ParaAttr.COLLAPSED, value); }

    /// <summary>
    /// Top margin of <see cref="HtmlBlock"/>
    /// </summary>
    int getHtmlMarginTop() { return (/*int*/Integer)fetchAttr(ParaAttr.HTML_MARGIN_TOP); }

    /// <summary>
    /// Bottom margin of <see cref="HtmlBlock"/>
    /// </summary>
    int getHtmlMarginBottom() { return (/*int*/Integer)fetchAttr(ParaAttr.HTML_MARGIN_BOTTOM); }

    /// <summary>
    /// Left margin of <see cref="HtmlBlock"/>
    /// </summary>
    int getHtmlMarginLeft() { return (/*int*/Integer)fetchAttr(ParaAttr.HTML_MARGIN_LEFT); }

    /// <summary>
    /// Right margin of <see cref="HtmlBlock"/>
    /// </summary>
    int getHtmlMarginRight() { return (/*int*/Integer)fetchAttr(ParaAttr.HTML_MARGIN_RIGHT); }

    /// <summary>
    /// Returns true if this para should be positioned as floating textframe.
    /// </summary>
    /// <remarks>
    /// 1. None of MS Specs define precise conditions for text frame to be floating,
    /// so this is experimentally derived conditions that relate to the topic of
    /// removal tblpPr in docx
    /// 2. Todo DD: Investigate why is this function works only when so different from <see cref="TablePr.IsFloating"/>, although
    /// Floating properties are the same in iso29500 spec.
    /// </remarks>
    boolean isFloating() { return FrameFormat.isFloatingCore(this); }

    /// <summary>
    /// Returns true if the paragraph properties have same absolute positioning as the specified properties.
    /// There are 14 text frame positioning attributes. This efficiently compares all of them if needed.
    /// </summary>
    boolean isSameFloatingPositioning(ParaPr rhs)
    {
        boolean isApo1 = isFloating();
        boolean isApo2 = rhs.isFloating();

        // A quick way to get out if both are not absolutely positioned.
        if (!isApo1 && !isApo2)
            return true;

        // A quick way to get out if one is absolutely positioned and the other is not.
        if (isApo1 != isApo2)
            return false;

        // Now we have to compare all frame attributes thoroughly.
        if (getFrameRelativeHorizontalPosition() != rhs.getFrameRelativeHorizontalPosition())
            return false;

        if (getFrameRelativeVerticalPosition() != rhs.getFrameRelativeVerticalPosition())
            return false;

        if (getFrameLeft() != rhs.getFrameLeft())
            return false;

        if (getFrameTop() != rhs.getFrameTop())
            return false;

        if (getFrameHorizontalAlignment() != rhs.getFrameHorizontalAlignment())
            return false;

        if (getFrameVerticalAlignment() != rhs.getFrameVerticalAlignment())
            return false;

        if (getFrameWidth() != rhs.getFrameWidth())
            return false;

        if (getFrameHeight() != rhs.getFrameHeight())
            return false;

        if (getFrameHeightRule() != rhs.getFrameHeightRule())
            return false;

        if (getFrameHorizontalDistanceFromText() != rhs.getFrameHorizontalDistanceFromText())
            return false;

        if (getFrameVerticalDistanceFromText() != rhs.getFrameVerticalDistanceFromText())
            return false;

        if (getFrameWrapType() != rhs.getFrameWrapType())
            return false;

        if (getFrameLockAnchor() != rhs.getFrameLockAnchor())
            return false;

        if (getFrameTextOrientation() != rhs.getFrameTextOrientation())
            return false;

        return true;
    }

    void removeFloatingAttrs()
    {
        for (int attr : FLOATING_ATTRS)
            remove(attr);

        // The [MS-OI-29500].pdf also says that supressOverlap must not be written into styles,
        // but I am not sure where it is in the model. We would need to delete it here too.
    }

    static /*final*/ int[] FLOATING_ATTRS = new int[]
    {
        ParaAttr.DROP_CAP_POSITION,
        ParaAttr.DROP_CAP_LINES_TO_DROP,
        ParaAttr.FRAME_HORIZONTAL_ALIGNMENT,
        ParaAttr.FRAME_VERTICAL_ALIGNMENT,
        ParaAttr.FRAME_LEFT,
        ParaAttr.FRAME_TOP,
        ParaAttr.FRAME_WIDTH,
        ParaAttr.FRAME_HEIGHT,
        ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION,
        ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION,
        ParaAttr.FRAME_WRAP_TYPE,
        ParaAttr.FRAME_TEXT_ORIENTATION,
        ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT,
        ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT,
        ParaAttr.FRAME_LOCK_ANCHOR
    };

    static /*final*/ int[] BORDER_ATTRS = new int[]
    {
        ParaAttr.BORDER_TOP,
        ParaAttr.BORDER_LEFT,
        ParaAttr.BORDER_BOTTOM,
        ParaAttr.BORDER_RIGHT,
        ParaAttr.BORDER_BETWEEN,
        ParaAttr.BORDER_BAR
    };

    void removeClearTabStops()
    {
        TabStopCollection tabStops = this.getTabStops();
        if (tabStops != null)
            tabStops.removeClearTabStops();
    }

    /// <summary>
    /// Creates and sets the <see cref="NumberRevision"/> object if it does not exist yet.
    /// </summary>
    void ensureHasNumberRevision()
    {
        if (getNumberRevision() == null)
            setNumberRevision(new ParagraphNumberRevision());
    }

    /// <summary>
    /// Reverses a paragraph alignment value.
    /// </summary>
    static /*ParagraphAlignment*/int reverseAlignment(/*ParagraphAlignment*/int alignment)
    {
        switch (alignment)
        {
            case ParagraphAlignment.LEFT:
                return ParagraphAlignment.RIGHT;
            case ParagraphAlignment.RIGHT:
                return ParagraphAlignment.LEFT;
            default:
                return alignment;
        }
    }

    /// <summary>
    /// Returns the static collection of default attributes.
    /// </summary>
    protected /*override*/ AttrCollection getDefaults()
    {
        return G_DEFAULTS;
    }

    static Object fetchDefaultAttr(int key)
    {
        return G_DEFAULTS.fetchAttr(key);
    }

    /// <summary>
    /// Returns property collection depending on passed flags.
    /// </summary>
    ParaPr getSourceParaPr(/*ParaPrExpandFlags*/int flags)
    {
        ParaPr sourcePr = this;

        if (getFormatRevision() != null)
        {
            if ((flags & ParaPrExpandFlags.REVISED) != 0)
            {
                sourcePr = AttrCollectionExtension.deepClone(this);
                sourcePr.acceptFormatRevision();
            }
            else if ((flags & ParaPrExpandFlags.AFTER_CHANGES) != 0)
            {
                sourcePr = (ParaPr)this.getFormatRevision().getRevPr();
            }
        }

        return sourcePr;
    }

    /// <summary>
    /// Removes frame position inheritance issue.
    /// </summary>
    /// <remarks>
    /// AM. FrameTop + FrameVerticalAlignment (FrameLeft + FrameHorizontalAlignment)
    /// should be joined to one attribute the same way as it done for LineSpacing.
    /// This is temporary solution that mimics this.
    /// </remarks>
    void frameInheritanceHack(ParaPr dstParaPr)
    {
        if (getFrameVerticalAlignment() != VerticalAlignment.NONE)
            dstParaPr.remove(ParaAttr.FRAME_TOP);
        else if (getFrameTop() > 0)
            dstParaPr.remove(ParaAttr.FRAME_VERTICAL_ALIGNMENT);

        if (getFrameHorizontalAlignment() != HorizontalAlignment.NONE)
            dstParaPr.remove(ParaAttr.FRAME_LEFT);
        else if (getFrameLeft() > 0)
            dstParaPr.remove(ParaAttr.FRAME_HORIZONTAL_ALIGNMENT);
    }

    /// <summary>
    /// Returns true if a specified indent is valid for the given format.
    /// </summary>
    static boolean isValidIndent(int indent, /*LoadFormat*/int lf)
    {
        switch (lf)
        {
            case LoadFormat.DOC:
                return (indent <= DOC_MAX_INDENT);
            case LoadFormat.RTF:
                return (Math.abs(indent) <= RTF_MAX_INDENT);
            default:
                return true;
        }
    }

    /// <summary>
    /// Returns true, if attribute with a specified key should be processed
    /// in a special way in accordance with ISO29500-1, 17.9.18 numId.
    /// </summary>
    static boolean isNoListAttr(int key)
    {
        return (key == ParaAttr.LEFT_INDENT) ||
               (key == ParaAttr.FIRST_LINE_INDENT) ||
               (key == ParaAttr.LEFT_INDENT_UNITS) ||
               (key == ParaAttr.FIRST_LINE_INDENT_UNITS);
    }

    /// <summary>
    /// Indicates that Istd is changed in format revision.
    /// </summary>
    boolean hasIstdRevision()
    {
        if (getFormatRevision() == null)
            return false;

        return (/*int*/Integer)fetchAttr(ParaAttr.ISTD) != (/*int*/Integer)getFormatRevision().getRevPr().fetchAttr(ParaAttr.ISTD);
    }

    /// <summary>
    /// Indicates that the paragraph properties contains list revision.
    /// </summary>
    boolean hasListRevision()
    {
        if (getFormatRevision() == null)
            return false;

        // Get final list.
        Object val = getFormatRevision().getRevPr().get(ParaAttr.LIST_ID);
        if (val == null)
            return false;

        int listIdFinal = (/*int*/Integer)val;

        // Get original list.
        val = this.get(ParaAttr.LIST_ID);
        int listIdOriginal = (val != null) ? (/*int*/Integer)val : 0;

        return listIdOriginal != listIdFinal;
    }

    
    protected /*override*/ int getDefaultCapacity()
    {
        // WORDSNET-21593 Reduce default capacity to optimize memory consumption.
        return 2;
    }

    //>>>>>>>> #region  IParaAttrSource

    public Object /*IParaAttrSource.*/getDirectParaAttr(int key)
    {
        return getDirectAttr(key);
    }

    public Object /*IParaAttrSource.*/getDirectParaAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return getDirectAttr(key, revisionsView);
    }

    /// <summary>
    /// IParaAttrSource.
    /// </summary>
    public Object /*IParaAttrSource.*/fetchInheritedParaAttr(int key)
    {
        return fetchInheritedAttr(key);
    }

    /// <summary>
    /// IParaAttrSource.
    /// </summary>
    public Object /*IParaAttrSource.*/fetchParaAttr(int key)
    {
        Object value = getDirectAttr(key);
        return (value != null) ? value : ((IParaAttrSource)this).fetchInheritedParaAttr(key);
    }

    /// <summary>
    /// IParaAttrSource.
    /// </summary>
    public void /*IParaAttrSource.*/setParaAttr(int key, Object value)
    {
        setAttr(key, value);
    }

    /// <summary>
    /// IParaAttrSource.
    /// </summary>
    public void /*IParaAttrSource.*/removeParaAttr(int key)
    {
        remove(key);
    }

    /// <summary>
    /// IParaAttrSource.
    /// </summary>
    public void /*IParaAttrSource.*/clearParaAttrs()
    {
        clear();
    }

    /// <summary>
    /// The key is <see cref="BorderType"/> that represents a border that can occur in a <see cref="ParaPr"/> collection.
    /// The value is an <see cref="ParaAttr"/> integer that represents the attribute key of that border.
    /// </summary>
    static /*final*/ SortedList<Integer, Integer> POSSIBLE_BORDER_KEYS = new SortedList<Integer, Integer>();

    private static /*final*/ ParaPr G_DEFAULTS;

    /// <summary>
    /// Specifies what attributes to preserve during <see cref="AcceptFormatRevision"/> in presence of changed style.
    /// </summary>
    private static /*final*/ int[] G_REVISION_PRESERVED_ATTRIBUTES = new int[] { RevisionAttr.NUMBER_REVISION };

    /// <summary>
    /// Maximum allowed in Word indent for DOC format.
    /// AM. Although spec says that indent can be equal to MaxIndent value (31680)
    /// but in Jira4880 such value seems should be ignored.
    /// </summary>
    private static final int DOC_MAX_INDENT = 31679;

    /// <summary>
    /// Maximum allowed in Word indent for RTF format.
    /// </summary>
    private static final int RTF_MAX_INDENT = 31681;

    //<<<<<<<< #endregion 

    static/* ParaPr()*/
    {
        G_DEFAULTS = new ParaPr();

        G_DEFAULTS.add(ParaAttr.ISTD, StyleIndex.NORMAL);
        G_DEFAULTS.add(ParaAttr.ALIGNMENT, ParagraphAlignment.LEFT);

        G_DEFAULTS.add(ParaAttr.NO_SPACE_BETWEEN_SAME_STYLE, false);
        G_DEFAULTS.add(ParaAttr.SIDE_BY_SIDE, false);
        G_DEFAULTS.add(ParaAttr.KEEP_TOGETHER, false);
        G_DEFAULTS.add(ParaAttr.KEEP_WITH_NEXT, false);
        G_DEFAULTS.add(ParaAttr.PAGE_BREAK_BEFORE, false);
        G_DEFAULTS.add(ParaAttr.SUPPRESS_LINE_NUMBERS, false);
        G_DEFAULTS.add(ParaAttr.SUPPRESS_AUTO_HYPHENS, false);
        G_DEFAULTS.add(ParaAttr.WIDOW_CONTROL, true);
        G_DEFAULTS.add(ParaAttr.MIRROR_INDENTS, false);

        G_DEFAULTS.add(ParaAttr.FAR_EAST_LINE_BREAK_CONTROL, true);              // As per OOXML Part 4, 2.3.1.16
        G_DEFAULTS.add(ParaAttr.WORD_WRAP, true);             // As per OOXML Part 4, 2.3.1.45
        G_DEFAULTS.add(ParaAttr.HANGING_PUNCTUATION, true);  // As per OOXML Part 4, 2.3.1.21
        G_DEFAULTS.add(ParaAttr.TOP_LINE_PUNCTUATION, false);
        G_DEFAULTS.add(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_ALPHA, true); // Default value for all formats except RTF and ODT.
        G_DEFAULTS.add(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_DIGIT, true); // Default value for all formats except RTF and ODT.
        G_DEFAULTS.add(ParaAttr.AUTO_ADJUST_RIGHT_INDENT, true); // See "part 1 reference c051463_ISOIEC 29500-1_2008(E) 17.3.1.1 adjustRightInd"
        G_DEFAULTS.add(ParaAttr.BASELINE_ALIGNMENT, BaselineAlignment.AUTO);
        G_DEFAULTS.add(ParaAttr.SNAP_TO_GRID, true);
        G_DEFAULTS.add(ParaAttr.BIDI, false);
        G_DEFAULTS.add(ParaAttr.RSID_P, 0);

        G_DEFAULTS.add(ParaAttr.LIST_LEVEL, 0);
        G_DEFAULTS.add(ParaAttr.LIST_ID, 0);
        G_DEFAULTS.add(ParaAttr.OUTLINE_LEVEL, OutlineLevel.BODY_TEXT);

        G_DEFAULTS.add(ParaAttr.LEFT_INDENT, 0);
        G_DEFAULTS.add(ParaAttr.RIGHT_INDENT, 0);
        G_DEFAULTS.add(ParaAttr.FIRST_LINE_INDENT, 0);
        G_DEFAULTS.add(ParaAttr.SPACE_BEFORE_AUTO, false);
        G_DEFAULTS.add(ParaAttr.SPACE_AFTER_AUTO, false);
        G_DEFAULTS.add(ParaAttr.SPACE_BEFORE, 0);
        G_DEFAULTS.add(ParaAttr.SPACE_AFTER, 0);
        G_DEFAULTS.add(ParaAttr.SPACE_BEFORE_UNITS, 0);
        G_DEFAULTS.add(ParaAttr.SPACE_AFTER_UNITS, 0);
        G_DEFAULTS.add(ParaAttr.LEFT_INDENT_UNITS, 0);
        G_DEFAULTS.add(ParaAttr.RIGHT_INDENT_UNITS, 0);
        G_DEFAULTS.add(ParaAttr.FIRST_LINE_INDENT_UNITS, 0);

        // These are complex attributes. Be careful not to modify the actual values in them.
        G_DEFAULTS.add(ParaAttr.TAB_STOPS, new TabStopCollection());
        G_DEFAULTS.add(ParaAttr.BORDER_TOP, new Border());
        G_DEFAULTS.add(ParaAttr.BORDER_LEFT, new Border());
        G_DEFAULTS.add(ParaAttr.BORDER_BOTTOM, new Border());
        G_DEFAULTS.add(ParaAttr.BORDER_RIGHT, new Border());
        G_DEFAULTS.add(ParaAttr.BORDER_BETWEEN, new Border());
        G_DEFAULTS.add(ParaAttr.BORDER_BAR, new Border());
        G_DEFAULTS.add(ParaAttr.SHADING, new Shading());

        G_DEFAULTS.add(ParaAttr.FRAME_WIDTH, 0);
        G_DEFAULTS.add(ParaAttr.FRAME_HEIGHT, new Height(HeightRule.AUTO, 0));
        G_DEFAULTS.add(ParaAttr.FRAME_LEFT, 0);
        G_DEFAULTS.add(ParaAttr.FRAME_TOP, 0);
        G_DEFAULTS.add(ParaAttr.FRAME_HORIZONTAL_ALIGNMENT, HorizontalAlignment.NONE);
        G_DEFAULTS.add(ParaAttr.FRAME_VERTICAL_ALIGNMENT, VerticalAlignment.NONE);
        G_DEFAULTS.add(ParaAttr.FRAME_LOCK_ANCHOR, false);
        G_DEFAULTS.add(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION, RelativeHorizontalPosition.DEFAULT);
        G_DEFAULTS.add(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION, RelativeVerticalPosition.TABLE_DEFAULT);
        G_DEFAULTS.add(ParaAttr.FRAME_WRAP_TYPE, WrapType.INLINE);
        G_DEFAULTS.add(ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT, 0);
        G_DEFAULTS.add(ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT, 0);
        G_DEFAULTS.add(ParaAttr.DROP_CAP_LINES_TO_DROP, 0);
        G_DEFAULTS.add(ParaAttr.DROP_CAP_POSITION, DropCapPosition.NONE);
        G_DEFAULTS.add(ParaAttr.FRAME_TEXT_ORIENTATION, TextOrientation.HORIZONTAL);
        G_DEFAULTS.add(ParaAttr.FRAME_SUPPRESS_OVERLAP, false);
        G_DEFAULTS.add(ParaAttr.HTML_BLOCK_ID, 0);
        G_DEFAULTS.add(ParaAttr.TEXTBOX_TIGHT_WRAP, TextboxTightWrap.NONE);
        G_DEFAULTS.add(ParaAttr.HTML_MARGIN_LEFT, 0);
        G_DEFAULTS.add(ParaAttr.HTML_MARGIN_RIGHT, 0);
        G_DEFAULTS.add(ParaAttr.HTML_MARGIN_TOP, 0);
        G_DEFAULTS.add(ParaAttr.HTML_MARGIN_BOTTOM, 0);

        G_DEFAULTS.add(ParaAttr.LINE_SPACING, new LineSpacing(240, LineSpacingRule.MULTIPLE));

        // Need to have defaults for these so we can collapse during RTF import.
        G_DEFAULTS.add(ParaAttr.SYS_ALIGNMENT_97, ParagraphAlignment.LEFT);
        G_DEFAULTS.add(ParaAttr.SYS_FIRST_LINE_INDENT_97, 0);
        G_DEFAULTS.add(ParaAttr.SYS_LEFT_INDENT_97, 0);
        G_DEFAULTS.add(ParaAttr.SYS_RIGHT_INDENT_97, 0);

        G_DEFAULTS.add(ParaAttr.COLLAPSED, false);

        POSSIBLE_BORDER_KEYS.add(BorderType.TOP, ParaAttr.BORDER_TOP);
        POSSIBLE_BORDER_KEYS.add(BorderType.LEFT, ParaAttr.BORDER_LEFT);
        POSSIBLE_BORDER_KEYS.add(BorderType.BOTTOM, ParaAttr.BORDER_BOTTOM);
        POSSIBLE_BORDER_KEYS.add(BorderType.RIGHT, ParaAttr.BORDER_RIGHT);
        POSSIBLE_BORDER_KEYS.add(BorderType.HORIZONTAL, ParaAttr.BORDER_BETWEEN);
        POSSIBLE_BORDER_KEYS.add(BorderType.VERTICAL, ParaAttr.BORDER_BAR);
    }
}

