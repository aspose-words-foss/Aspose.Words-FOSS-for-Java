// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2018 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.msPoint;


/// <summary>
/// Implements page margin calculation for different multiple page setups.
/// </summary>
/// <remarks>
/// <para>
/// The approach is to save all properties affecting margins calculation to read-only fields.
/// So only the constructor needs to be different to handle Model or Layout objects.
/// The logic that handles the properties is the same.
/// </para>
/// <para>There is an inconsistent MS Word behavior for 2-page per sheet and book fold setups.
/// The gutter is present both on top of the page and on the side for some page orientations.
/// Also, side gutter is sometimes taken into account and some times it is not when this condition happen.
/// The behavior was implemented per WORDSNET-16284</para>
/// </remarks>
class PageMarginCalculator
{
    /// <summary>
    /// Initializes a new instance of this class by specifying properties with an object of
    /// the <see cref="PageProperties"/> class.
    /// </summary>
    PageMarginCalculator(PageProperties pageProperties)
    {
        LeftMargin = pageProperties.getLeftMargin();
        RightMargin = pageProperties.getRightMargin();

        // Negative margin is treated as positive for content positioning. Word behavior.
        // See PagePart.ContentTop for how negative margin is different from positive.
        TopMargin = Math.abs(pageProperties.getTopMargin());
        BottomMargin = Math.abs(pageProperties.getBottomMargin());

        Gutter = pageProperties.getGutter();

        MultiplePages = pageProperties.getMultiplePages();
        DifferentOddEvenPageHeader = pageProperties.getDifferentOddEvenPageHeader();

        _Orientation = pageProperties.getOrientation();
        OrientationMatchesFirstSection = pageProperties.getOrientationMatchesFirstSection();

        IsGutterAtTopSet = pageProperties.isGutterAtTopSet();
        IsRtlGutter = pageProperties.isRtlGutter();
    }

    /// <summary>
    /// Initializes a new instance of the class section and document properties in the Model rather than from layout.
    /// </summary>
    PageMarginCalculator(ISectionAttrSource section, DocPr docPr, /*Orientation*/int firstSectionOrientation)
    {
        // There is a unit difference between the two constructors.
        // This one actually gets margins in twips, and the other one in lis.
        // I think this is not a problem as long as the units match in the instance.
        LeftMargin = (/*int*/Integer)section.fetchSectionAttr(SectAttr.LEFT_MARGIN);
        RightMargin = (/*int*/Integer)section.fetchSectionAttr(SectAttr.RIGHT_MARGIN);

        // Negative margin is treated as positive for content positioning. Word behavior.
        // See PagePart.ContentTop for how negative margin is different from positive.
        TopMargin = Math.abs((/*int*/Integer)section.fetchSectionAttr(SectAttr.TOP_MARGIN));
        BottomMargin = Math.abs((/*int*/Integer)section.fetchSectionAttr(SectAttr.BOTTOM_MARGIN));

        Gutter = (/*int*/Integer)section.fetchSectionAttr(SectAttr.GUTTER);

        MultiplePages = docPr.MultiplePages;
        DifferentOddEvenPageHeader = docPr.EvenAndOddHeaders;

        _Orientation = (/*Orientation*/Integer)section.fetchSectionAttr(SectAttr.ORIENTATION);
        OrientationMatchesFirstSection = (_Orientation == firstSectionOrientation);

        IsGutterAtTopSet = docPr.GutterAtTop;
        IsRtlGutter = (/*boolean*/Boolean)section.fetchSectionAttr(SectAttr.RTL_GUTTER);
    }

    /// <summary>
    /// Gets the page margin and gutter on the given page side.
    /// </summary>
    int getMarginWithGutter(/*PageSide*/int side, boolean isOddPage)
    {
        int margin;

        switch (side)
        {
            case PageSide.TOP:
                margin = getTopMargin(isOddPage);
                break;
            case PageSide.BOTTOM:
                margin = getBottomMargin(isOddPage);
                break;
            case PageSide.LEFT:
                margin = getLeftMargin(isOddPage);
                break;
            case PageSide.RIGHT:
                margin = getRightMargin(isOddPage);
                break;
            default:
                throw new IllegalStateException("Unexpected page side value.");
        }

        int effectiveGutter = getEffectiveGutter(side, isOddPage, GutterHandling.SIDE_GUTTER_NORMAL);
        return margin + effectiveGutter;
    }

    /// <summary>
    /// Gets a boolean value indicating if side gutter should taken into account for column width calculation.
    /// </summary>
    /// <remarks>
    /// There is a rare case for some of the multiple page setups when side gutter is present,
    /// but column text is allowed to overlap it. The method handles the condition (ignores side gutter if needed).
    /// </remarks>
    static boolean getPageHasSideGutter(SectPr sectionPr, DocPr docPr, /*Orientation*/int firstSectionOrientation)
    {
        PageMarginCalculator calculator = new PageMarginCalculator(sectionPr, docPr, firstSectionOrientation);

        final boolean ANY_PAGE = true;
        int sideGutter = calculator.getEffectiveGutter(PageSide.LEFT, ANY_PAGE, GutterHandling.DISREGARD_SIDE_GUTTER_FOR_DOUBLE_GUTTER_CASE) +
            calculator.getEffectiveGutter(PageSide.RIGHT, ANY_PAGE, GutterHandling.DISREGARD_SIDE_GUTTER_FOR_DOUBLE_GUTTER_CASE);

        return (sideGutter > 0);
    }

    /// <summary>
    /// Implements width metrics calculation taking caller context specified via <paramref name="gutterBehaviorContext"/> into account.
    /// </summary>
    /*Point*/long getWidthMetrics(/*GutterBehavior*/int gutterBehaviorContext, int pageWidth, boolean isOddPage,
        boolean isWord2013OrLaterCompatible)
    {
        boolean disregardSideGutterForDoubleGutterCase;
        switch (gutterBehaviorContext)
        {
            case GutterBehavior.NORMAL:
                disregardSideGutterForDoubleGutterCase = false;
                break;
            case GutterBehavior.COLUMN:
                disregardSideGutterForDoubleGutterCase = true;
                break;
            case GutterBehavior.HEADER_FOOTER:
                // Side gutter is disregarded in compatibility mode when there is a gutter on top as well.
                disregardSideGutterForDoubleGutterCase = !isWord2013OrLaterCompatible;
                break;
            case GutterBehavior.FLOATING_SHAPE_POSITIONING:
                // Side gutter is disregarded in 2013 mode when there is a gutter on top as well.
                disregardSideGutterForDoubleGutterCase = isWord2013OrLaterCompatible;
                break;
            default:
                throw new IllegalStateException("Unknown gutter behavior");
        }

        return disregardSideGutterForDoubleGutterCase
            ? getWidthMetricsWithSpecialGutterCondition(pageWidth, isOddPage)
            : getWidthMetrics(pageWidth, isOddPage);
    }

    /// <summary>
    /// Implements page width metrics calculation for normal gutter behavior.
    /// </summary>
    private /*Point*/long getWidthMetrics(int pageWidth, boolean isOddPage)
    {
        int leftMargin = getMarginWithGutter(PageSide.LEFT, isOddPage);
        int rightMargin = getMarginWithGutter(PageSide.RIGHT, isOddPage);

        return msPoint.ctor(leftMargin, pageWidth - rightMargin);
    }

    /// <summary>
    /// Gets page width metrics similar to <see cref="GetWidthMetrics(int, bool)"/>,
    /// but takes a special condition to ignore side gutter into account.
    /// </summary>
    /// <remarks>
    /// There is a rare case for some of the multiple page setups when side gutter is present,
    /// but column text is allowed to overlap it. The method handles the condition (ignores side gutter if needed).
    /// </remarks>
    private /*Point*/long getWidthMetricsWithSpecialGutterCondition(int pageWidth, boolean isOddPage)
    {
        int leftMargin = getLeftMargin(isOddPage);
        int rightMargin = getRightMargin(isOddPage);

        int leftGutter = getEffectiveGutter(PageSide.LEFT, isOddPage, GutterHandling.DISREGARD_SIDE_GUTTER_FOR_DOUBLE_GUTTER_CASE);
        int rightGutter = getEffectiveGutter(PageSide.RIGHT, isOddPage, GutterHandling.DISREGARD_SIDE_GUTTER_FOR_DOUBLE_GUTTER_CASE);

        int leftGutterNormal = getEffectiveGutter(PageSide.LEFT, isOddPage, GutterHandling.ADD_SIDE_GUTTER_FOR_DOUBLE_GUTTER_CASE);

        int contentLeft = leftMargin + leftGutterNormal;
        int actualContentWidth = pageWidth - (leftMargin + leftGutter + rightMargin + rightGutter);
        int contentRight = contentLeft + actualContentWidth;

        return msPoint.ctor(contentLeft, contentRight);
    }

    /// <summary>
    /// Gets the effective gutter for the given page side and gutter behavior.
    /// </summary>
    private int getEffectiveGutter(/*PageSide*/int side, boolean isOddPage, /*GutterHandling*/int gutterBehavior)
    {
        boolean addGutter;
        boolean isSideGutterMirrored;
        /*PageSide*/int gutterPosition;
        switch (MultiplePages)
        {
            case MultiplePagesType.NORMAL:
            case MultiplePagesType.MIRROR_MARGINS:
                boolean pageHasTopBottomGutter = OrientationMatchesFirstSection
                    ? IsGutterAtTopSet
                    : !IsGutterAtTopSet;

                if (pageHasTopBottomGutter)
                {
                    boolean gutterOnTop = OrientationMatchesFirstSection || (_Orientation == Orientation.LANDSCAPE);

                    gutterPosition = gutterOnTop
                        ? PageSide.TOP
                        : PageSide.BOTTOM;

                    addGutter = (side == gutterPosition);
                }
                else
                {
                    isSideGutterMirrored = DifferentOddEvenPageHeader || (MultiplePages == MultiplePagesType.MIRROR_MARGINS);

                    // This is for left-to-right documents (left gutter is the default).
                    boolean isLeftGutter = isOddPage || !isSideGutterMirrored;

                    if (IsRtlGutter)
                    {
                        // Everything is vice versa for Right gutter, which is normally set for RTL sections.
                        isLeftGutter = !isLeftGutter;
                    }

                    gutterPosition = isLeftGutter
                        ? PageSide.LEFT
                        : PageSide.RIGHT;

                    addGutter = (side == gutterPosition);

                }
                break;
            case MultiplePagesType.TWO_PAGES_PER_SHEET:
            case MultiplePagesType.BOOK_FOLD_PRINTING:
            case MultiplePagesType.BOOK_FOLD_PRINTING_REVERSE:
                if (_Orientation == Orientation.PORTRAIT)
                {
                    // Gutter is on the top and it is mirrored on even pages.
                    gutterPosition = isOddPage
                        ? PageSide.TOP
                        : PageSide.BOTTOM;

                    addGutter = (side == gutterPosition);
                }
                else
                {
                    // Side gutter mirrors the same way for two pages per sheet and book fold.
                    // It is the other way around for reverse book fold.
                    isSideGutterMirrored = (MultiplePages != MultiplePagesType.BOOK_FOLD_PRINTING_REVERSE)
                        ? isOddPage
                        : !isOddPage;

                    gutterPosition = isSideGutterMirrored
                        ? PageSide.LEFT
                        : PageSide.RIGHT;

                    // Below is an implementation of a special MS Word behavior which looks like a glitch,
                    // The gutter is placed both on top and on the side.
                    boolean secondGutterAtTop = (MultiplePages == MultiplePagesType.TWO_PAGES_PER_SHEET)
                        ? OrientationMatchesFirstSection && IsGutterAtTopSet
                        : !OrientationMatchesFirstSection;
                    // It is strange that gutterAtTop only affects 2 pages per sheet setup, but it seems MS Word works this way.

                    // For column width calculation in presence of two gutters side gutter is disregarded.
                    boolean isSideGutterPresent = !(secondGutterAtTop && (gutterBehavior == GutterHandling.DISREGARD_SIDE_GUTTER_FOR_DOUBLE_GUTTER_CASE));
                    addGutter = secondGutterAtTop && (side == PageSide.TOP) ||
                        isSideGutterPresent && (side == gutterPosition);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected multiple page setup value.");
        }

        return addGutter ? Gutter : 0;
    }

    private int getTopMargin(boolean isOddPage)
    {
        // When top-bottom is mirrored, "outside" (bottom) margin is used for odd pages.
        boolean mirrorTop = isTopBottomMarginMirrored() && isOddPage;
        int topMargin = mirrorTop
            ? BottomMargin
            : TopMargin;

        return topMargin;
    }

    private int getBottomMargin(boolean isOddPage)
    {
        // When top-bottom is mirrored, "outside", that is, bottom margin is used for odd pages.
        boolean mirrorTop = isTopBottomMarginMirrored() && isOddPage;
        int bottomMargin = mirrorTop
            ? TopMargin
            : BottomMargin;

        return bottomMargin;
    }

    private int getLeftMargin(boolean isOddPage)
    {
        boolean mirrorSide = isSideMarginMirrored(isOddPage);
        int leftMargin = mirrorSide
            ? RightMargin
            : LeftMargin;

        return leftMargin;
    }

    private int getRightMargin(boolean isOddPage)
    {
        // When left-right is mirrored, left margin goes to the right for even pages.
        boolean mirrorSide = isSideMarginMirrored(isOddPage);
        int rightMargin = mirrorSide
            ? LeftMargin
            : RightMargin;

        return rightMargin;
    }

    private boolean isTopBottomMarginMirrored()
    {
        boolean isMirrored;
        switch (MultiplePages)
        {
            case MultiplePagesType.NORMAL:
            case MultiplePagesType.MIRROR_MARGINS:
                isMirrored = false;
                break;
            case MultiplePagesType.TWO_PAGES_PER_SHEET:
            case MultiplePagesType.BOOK_FOLD_PRINTING:
            case MultiplePagesType.BOOK_FOLD_PRINTING_REVERSE:
                // Top-bottom margins are mirrored in portrait.
                // It means, the page that holds two sheets is portrait, but each sheet may be a landscape.
                isMirrored = (_Orientation == Orientation.PORTRAIT);
                break;
            default:
                throw new IllegalStateException("Unexpected multiple page setup value.");
        }

        return isMirrored;
    }

    private boolean isSideMarginMirrored(boolean isOddPage)
    {
        boolean isMirrored;
        switch (MultiplePages)
        {
            case MultiplePagesType.NORMAL:
                isMirrored = false;
                break;
            case MultiplePagesType.MIRROR_MARGINS:
                // Left margin goes to the right for even pages.
                isMirrored = OrientationMatchesFirstSection && !isOddPage;
                break;
            case MultiplePagesType.TWO_PAGES_PER_SHEET:
            case MultiplePagesType.BOOK_FOLD_PRINTING:
                // Side margins are mirrored for landscape orientation on odd pages.
                isMirrored = (_Orientation == Orientation.LANDSCAPE) && isOddPage;
                break;
            case MultiplePagesType.BOOK_FOLD_PRINTING_REVERSE:
                // Side margins are mirrored for landscape orientation on even pages.
                isMirrored = (_Orientation == Orientation.LANDSCAPE) && !isOddPage;
                break;
            default:
                throw new IllegalStateException("Unexpected multiple page setup value.");
        }

        return isMirrored;
    }

    /// <summary>
    /// Describes different gutter handling methods.
    /// </summary>
    private /*enum*/ final class GutterHandling
    {
        private GutterHandling(){}
        
        public static final int SIDE_GUTTER_NORMAL = 0;
        public static final int ADD_SIDE_GUTTER_FOR_DOUBLE_GUTTER_CASE = SIDE_GUTTER_NORMAL;
        public static final int DISREGARD_SIDE_GUTTER_FOR_DOUBLE_GUTTER_CASE = 1;

        public static final int length = 3;
    }

    private /*final*/ int LeftMargin;
    private /*final*/ int RightMargin;
    private /*final*/ int TopMargin;
    private /*final*/ int BottomMargin;

    private /*final*/ int Gutter;
    private /*final*/ boolean IsGutterAtTopSet;
    private /*final*/ boolean IsRtlGutter;

    private /*final*/ /*MultiplePagesType*/int MultiplePages;
    private /*final*/ /*Orientation*/int _Orientation;
    private /*final*/ boolean OrientationMatchesFirstSection;

    private /*final*/ boolean DifferentOddEvenPageHeader;
}

