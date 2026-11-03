// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/09/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.MathUtil;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// CSS box model. Translates positions of CSS boxes into indents and spacing of document text.
/// </summary>
/// <remarks>
/// For details, see http://www.w3.org/TR/CSS2/box.html and http://www.w3.org/TR/CSS2/visuren.html.
/// </remarks>
class CssBoxModel
{
    /// <summary>
    /// Creates and initializes a new instance of the class.
    /// </summary>
    /// <param name="documentMode">
    /// The mode of the HTML document being processed.
    /// </param>
    /// <param name="applyDefaultVerticalMargins">
    /// Instructs the box model to use vertical margins specified in the user agent style sheet for certain elements
    /// (paragraphs, lists, etc.)
    /// </param>
    /// <param name="applyFormattingAsMsWord">
    /// Indicates whether we should stick to MS Word's rules when applying CSS formatting to document model.
    /// </param>
    CssBoxModel(
        /*CssDocumentMode*/int documentMode,
        boolean applyDefaultVerticalMargins,
        boolean applyFormattingAsMsWord)
    {
        mDocumentMode = documentMode;
        mApplyDefaultVerticalMargins = applyDefaultVerticalMargins;
        mApplyFormattingAsMsWord = applyFormattingAsMsWord;
        // An imaginary root box containing the whole document.
        CssBox rootBox = new CssBox();
        mBoxes.push(rootBox);
        // Root formatting context.
        mContexts.push(new CssBoxFormattingContext(rootBox, true));
    }

    /// <summary>
    /// Pushes a child HTML element into the model.
    /// </summary>
    /// <param name="element">An HTML element.</param>
    /// <param name="cssDeclarations">Element's CSS declarations.</param>
    /// <param name="displayType">Element display type based on CSS "display" property.</param>
    /// <param name="parentDisplayType">Parent element display type.</param>
    /// <param name="createsHtmlBlock">
    /// Indicates whether the HTML element creates a <see cref="HtmlBlock"/> instance in the document model.
    /// </param>
    void push(IHtmlElementProvider element,
        CssDeclarationCollection cssDeclarations,
        /*CssDisplayType*/int displayType,
        /*CssDisplayType*/int parentDisplayType,
        boolean createsHtmlBlock)
    {
        Debug.assertTrue(!element.isImplicit());

        if (getCurrentBox().startsWithImplicitBox())
        {
            getCurrentBox().setChildTopMarginCollapsing(CssBoxMarginCollapsing.SEPARATE);
        }
        if ("body".equals(element.getElementName()))
        {
            getCurrentBox().setChildTopMarginCollapsing(CssBoxMarginCollapsing.COLLAPSE);
        }
        if (element.isLastChildBox() && (!getCurrentBox().endsWithImplicitBox()))
        {
            getCurrentBox().setChildBottomMarginCollapsing(getCurrentBox().getBottomMarginIsCollapsible()
                ? CssBoxMarginCollapsing.COLLAPSE
                : CssBoxMarginCollapsing.ADD);
        }

        CssBox childBox;

        String name = element.getElementName();

        if (("td".equals(name)) || ("th".equals(name)))
        {
            // Table cells start AW model analogs of HTML boxes.
            childBox = computeTableCellBox(element);
        }
        else if (displayType == CssDisplayType.TABLE_CELL)
        {
            childBox = computeDisplayTableCellBox(element, cssDeclarations);
        }
        else if ("table".equals(name))
        {
            // Tables in AW model are similar to HTML boxes.
            childBox = computeTableBox(element, cssDeclarations);
        }
        else if ("caption".equals(name))
        {
            // Captions are imported as table cells, so they start boxes too.
            childBox = computeCaptionBox(element, cssDeclarations);
        }
        // WORDSNET-22044 A box of the list depends on display type.
        else if (("ul".equals(name)) || ("ol".equals(name)))
        {
            // For the following values of display type browser generates box without bottom margin
            // and without top margin for `inline` display type.
            boolean isInlineBox =
                displayType == CssDisplayType.INLINE ||
                displayType == CssDisplayType.TABLE_HEADER_GROUP ||
                displayType == CssDisplayType.TABLE_CELL ||
                displayType == CssDisplayType.TABLE_ROW ||
                displayType == CssDisplayType.TABLE_ROW_GROUP ||
                displayType == CssDisplayType.TABLE_FOOTER_GROUP;

            childBox = isInlineBox
                ? computeInlineListBox(element, cssDeclarations, displayType)
                : computeUsualBox(element, cssDeclarations);
        }
        else if ("li".equals(name))
        {
            // WORDSNET-26553 If the list element is not the first then top margin is ignored.
            if (element.getPreviousSiblingElement() != null)
            {
                getCurrentBox().setChildTopMarginCollapsing(CssBoxMarginCollapsing.SEPARATE);
            }

            // WORDSNET-22044 A box of a list item depends on display type.
            childBox = isBlock(displayType)
                ? computeUsualBox(element, cssDeclarations)
                : computeInlineBox(element);
        }
        // WORDSNET-23606 MS Word ignores the margins, the paddings and the border widths of `figure` elements
        // when loading HTML.
        else if (("figure".equals(name)) && mApplyFormattingAsMsWord)
        {
            childBox = new CssBox();
        }
        else
        {
            // If the HTML element creates the HTML block then it shouldn't change the size of the box model.
            // All the margins of such HTML element should be stored in the corresponding HTML block.
            if (createsHtmlBlock)
            {
                childBox = new CssBox();
            }
            else
            {
                childBox = isBlock(displayType)
                    ? computeUsualBox(element, cssDeclarations)
                    : computeInlineBox(element);
            }
        }

        getCurrentContext().setEmptyBlockMarginBefore(null);
        childBox.isEmptyBlock(!element.isElementContainsText() && (displayType == CssDisplayType.BLOCK));

        boolean elementIsFloatingTable = isFloatingTable(name, displayType, cssDeclarations);

        // Floating tables are excluded from the formatting context.
        // Their top margin never collapses with parent's top margin.
        if (element.isElementContainsText() && !elementIsFloatingTable)
        {
            // WORDSNET-22044 If the first list item is inline
            // and it is inside inline list then top margin is ignored.
            if ((element.getPreviousSiblingElement() == null) &&
                ("li".equals(element.getElementName())) &&
                (displayType == CssDisplayType.INLINE))
            {
                if (parentDisplayType == CssDisplayType.INLINE)
                    getCurrentBox().setChildTopMarginCollapsing(CssBoxMarginCollapsing.SEPARATE);
            }
            else
            {
                getCurrentBox().setChildTopMarginCollapsing(CssBoxMarginCollapsing.SEPARATE);
            }
        }

        if (element.isLastChildBox())
        {
            if (("li".equals(element.getElementName())) && (parentDisplayType == CssDisplayType.INLINE))
            {
                getCurrentBox().setChildTopMarginCollapsing(CssBoxMarginCollapsing.SEPARATE);
            }
            else
            {
                getCurrentBox().setChildBottomMarginCollapsing(CssBoxMarginCollapsing.COLLAPSE);
            }
        }

        // Floating tables start their own box formatting contexts.
        if (elementIsFloatingTable)
        {
            mContexts.push(new CssBoxFormattingContext(childBox, isFullWidthBox(cssDeclarations)));
        }

        if (element.containsChildBoxes())
        {
            childBox.setChildBottomMarginCollapsing(CssBoxMarginCollapsing.SEPARATE);
        }

        mBoxes.push(childBox);

        mRecalculationNeeded = true;
    }

    /// <summary>
    /// Pops the most inner child HTML element from the model.
    /// </summary>
    void pop()
    {
        Debug.assertTrue(mBoxes.getCount() > 0);

        // The current formatting context closes when its root box is removed from the model.
        if (getCurrentContext().getRootBox() == getCurrentBox())
        {
            boolean closedContextIsFullWidthBox = getCurrentContext().isFullWidthBox();
            mContexts.pop();

            // There always exists a formatting context. At least the root context is never removed from the stack.
            Debug.assertTrue(mContexts.getCount() > 0);

            // WORDSNET-11202 A full-width floating box cannot have other boxes on its sides. A box after a full-width
            // floating box is pushed below the floating box, and the floating box occupies its top margin.
            // Here we do not know the exact height of the floating box but we assume it is big enough to occupy the whole
            // top margin of the next box and to effectively reduce its top margin value to zero.
            getCurrentContext().setCurrentBoxTopMarginManualCollapsing(closedContextIsFullWidthBox
                ? CssBoxMarginManualCollapsing.ZERO
                : CssBoxMarginManualCollapsing.COLLAPSE);
        }
        else
        {
            getCurrentContext().setCurrentBoxTopMarginManualCollapsing(CssBoxMarginManualCollapsing.SEPARATE);
        }

        getCurrentContext().setPreviousBoxBottomMargin(getCurrentContext().getCurrentBoxBottomMargin());

        // WORDSNET-18824 Margins of an empty block are now collapsed with the top margin of the block right after
        // the empty one.
        if (getCurrentBox().isEmptyBlock())
        {
            // The calling code doesn't always recalculate margins of empty blocks, because they are normally ignored.
            // Let's make sure we know the margins of this empty block.
            recalculateIfNeeded();

            CssLength emptyBlockMargin = new CssLength(
                Math.max(mTop.getValue(), mBottom.getValue()),
                mTop.isCustom() || mBottom.isCustom());

            // Margins of consequent empty blocks are collapsed too.
            if (getCurrentContext().getEmptyBlockMarginBefore() == null)
            {
                getCurrentContext().setEmptyBlockMarginBefore(emptyBlockMargin);
            }
            else
            {
                getCurrentContext().setEmptyBlockMarginBefore(new CssLength(
                    Math.max(getCurrentContext().getEmptyBlockMarginBefore().getValue(), emptyBlockMargin.getValue()),
                    getCurrentContext().getEmptyBlockMarginBefore().isCustom() || emptyBlockMargin.isCustom()));
            }
        }

        mBoxes.pop();
        mRecalculationNeeded = true;
    }

    boolean hasCustomParentLeft()
    {
        CssLength parentLeft = calculateParentMarginValue(CssBoxMarginSide.LEFT, true);
        return parentLeft.isCustom();
    }

    boolean hasCustomParentRight()
    {
        CssLength parentRight = calculateParentMarginValue(CssBoxMarginSide.RIGHT, true);
        return parentRight.isCustom();
    }

    /// <summary>
    /// Gets the current left indent value.
    /// </summary>
    CssLength getLeft()
    {
        recalculateIfNeeded();
        return mLeft;
    }

    /// <summary>
    /// Gets the current right indent value.
    /// </summary>
    CssLength getRight()
    {
        recalculateIfNeeded();
        return mRight;
    }

    /// <summary>
    /// Gets the current top spacing value.
    /// </summary>
    CssLength getTop()
    {
        recalculateIfNeeded();
        return mTop;
    }

    /// <summary>
    /// Gets the current bottom spacing value.
    /// </summary>
    CssLength getBottom()
    {
        recalculateIfNeeded();
        return mBottom;
    }

    /// <summary>
    /// Gets all current indent and spacing values.
    /// </summary>
    CssMargins getMargins()
    {
        CssMargins margins = new CssMargins(getTop().getValue(), getRight().getValue(), getBottom().getValue(), getLeft().getValue());
        margins.setUseTop(getTop().isCustom());
        margins.setUseRight(getRight().isCustom());
        margins.setUseBottom(getBottom().isCustom());
        margins.setUseLeft(getLeft().isCustom());
        return margins;
    }

    private static boolean isBlock(/*CssDisplayType*/int displayType)
    {
        switch (displayType)
        {
            case CssDisplayType.INLINE:
            case CssDisplayType.TABLE_HEADER_GROUP:
            case CssDisplayType.TABLE_ROW_GROUP:
            case CssDisplayType.TABLE_ROW:
            case CssDisplayType.TABLE_CELL:
            case CssDisplayType.TABLE_FOOTER_GROUP:
                return false;
            case CssDisplayType.BLOCK:
            case CssDisplayType.INLINE_BLOCK:
            case CssDisplayType.LIST_ITEM:
            case CssDisplayType.RUN_IN:
            case CssDisplayType.TABLE:
            case CssDisplayType.INLINE_TABLE:
            case CssDisplayType.TABLE_CAPTION:
            case CssDisplayType.TABLE_COLUMN_GROUP:
            case CssDisplayType.TABLE_COLUMN:
            case CssDisplayType.NONE:
                return true;
            default:
                Debug.assertTrue(false, "Unknown display type.");
                return false;
        }
    }

    private void recalculateIfNeeded()
    {
        if (mRecalculationNeeded)
        {
            recalculateLeft();
            recalculateRight();
            recalculateTop();
            recalculateBottom();

            mRecalculationNeeded = false;
        }
    }

    private void recalculateLeft()
    {
        mLeft = calculateCurrentMarginValue(CssBoxMarginSide.LEFT, true);
    }

    private void recalculateRight()
    {
        mRight = calculateCurrentMarginValue(CssBoxMarginSide.RIGHT, true);
    }

    private void recalculateTop()
    {
        boolean firstTextBoxOrElementItself = (!getCurrentBox().canContainImplicitTextBoxes()) ||
            (getCurrentBox().getChildTopMarginCollapsing() != CssBoxMarginCollapsing.SEPARATE);

        if (firstTextBoxOrElementItself)
        {
            // AW document model do not accept negative vertical margins (paragraph spacing).
            mTop = calculateCurrentMarginValue(CssBoxMarginSide.TOP, false);

            // We manually collapse adjacent vertical margins if they should collapse according to CSS rules
            // but MS Word refuses to do it.
            if (getCurrentContext().getCurrentBoxTopMarginManualCollapsing() == CssBoxMarginManualCollapsing.COLLAPSE)
            {
                double correctedValue = Math.max(mTop.getValue() - getCurrentContext().getPreviousBoxBottomMargin(), 0.0);
                mTop = new CssLength(correctedValue, mTop.isCustom());
            }
            else if (getCurrentContext().getCurrentBoxTopMarginManualCollapsing() == CssBoxMarginManualCollapsing.ZERO)
            {
                mTop = new CssLength(0.0, mTop.isCustom());
            }
        }
        else
        {
            // Margin value of an implicit text box.
            mTop = CssLength.ZeroDefault;
        }
    }

    private void recalculateBottom()
    {
        boolean lastTextBoxOrElementItself = (!getCurrentBox().canContainImplicitTextBoxes()) ||
            (getCurrentBox().getChildBottomMarginCollapsing() != CssBoxMarginCollapsing.SEPARATE);

        if (lastTextBoxOrElementItself)
        {
            // AW document model do not accept negative vertical margins (paragraph spacing).
            mBottom = calculateCurrentMarginValue(CssBoxMarginSide.BOTTOM, false);
        }
        else
        {
            // Margin value of an implicit text box.
            mBottom = CssLength.ZeroDefault;
        }

        // Remember the bottom margin of the current box in case we have to manually collapse it
        // with the top margin of the next box.
        getCurrentContext().setCurrentBoxBottomMargin(mBottom.getValue());
    }

    private CssLength calculateCurrentMarginValue(/*CssBoxMarginSide*/int side, boolean isNegativeResultAllowed)
    {
        return calculateMarginValue(0, side, isNegativeResultAllowed);
    }

    private CssLength calculateParentMarginValue(/*CssBoxMarginSide*/int side, boolean isNegativeResultAllowed)
    {
        // Note that only horizontal margins of an ancestor element can be recalculated at an arbitrary moment. This method
        // will return invalid results for vertical margins of ancestor boxes, because vertical margins may collapse.
        return calculateMarginValue(1, side, isNegativeResultAllowed);
    }

    private CssLength calculateMarginValue(
        int depth,
        /*CssBoxMarginSide*/int side,
        boolean isNegativeResultAllowed)
    {
        double sumMargin = 0.0;
        double maxPositiveCollapsedMargin = 0.0;
        double minNegativeCollapsedMargin = 0.0;
        boolean onlyDefaultMarginsCollapsed = true;
        boolean isCustom = false;

        // At the top level, adjacent bottom margins of elements are collapsed to zero if they all have default values.
        boolean zeroDefaultCollapsedMargins = side == CssBoxMarginSide.TOP;

        // Process boxes from innermost to outermost.
        int currentDepth = -1;
        for (CssBox box : mBoxes)
        {
            ++currentDepth;
            if (currentDepth < depth)
            {
                // Skip inner elements we are not interested in.
                continue;
            }

            CssBoxMargin margin = box.getMargin(side);

            if (margin.getLength().isCustom())
            {
                if (!MathUtil.isZero(margin.getLength().getValue()))
                {
                    onlyDefaultMarginsCollapsed = false;
                }
                isCustom = true;
            }

            if ((margin.getLength().getValue() > 0) && (margin.getLength().getValue() > maxPositiveCollapsedMargin))
            {
                maxPositiveCollapsedMargin = margin.getLength().getValue();
            }
            else if ((margin.getLength().getValue() < 0) && (margin.getLength().getValue() < minNegativeCollapsedMargin))
            {
                minNegativeCollapsedMargin = margin.getLength().getValue();
            }

            if (margin.getCollapsingWithParentMargin() == CssBoxMarginCollapsing.SEPARATE)
            {
                // Stop margin calculation.
                zeroDefaultCollapsedMargins = margin.getZeroDefaultCollapsedMargins();
                break;
            }

            if (margin.getCollapsingWithParentMargin() != CssBoxMarginCollapsing.COLLAPSE)
            {
                // Restart collapsed margin calculation if the current margin does not collapse with the margin of its child.
                sumMargin += maxPositiveCollapsedMargin + minNegativeCollapsedMargin;
                maxPositiveCollapsedMargin = 0.0;
                minNegativeCollapsedMargin = 0.0;
                onlyDefaultMarginsCollapsed = true;
            }
        }

        // Some quirks require default vertical margins to collapse to zero at root element level.
        if (zeroDefaultCollapsedMargins && onlyDefaultMarginsCollapsed)
        {
            maxPositiveCollapsedMargin = 0.0;
            minNegativeCollapsedMargin = 0.0;
        }

        double value = sumMargin + maxPositiveCollapsedMargin + minNegativeCollapsedMargin;
        if ((!isNegativeResultAllowed) && (value < 0))
        {
            value = 0.0;
        }
        return new CssLength(value, isCustom);
    }

    private CssBox computeUsualBox(IHtmlElementProvider element, CssDeclarationCollection cssDeclarations)
    {
        CssLength leftValue = new CssLength(
            computeHorizontalMargin("margin-left", "padding-left", "border-left", cssDeclarations),
            isCustomHorizontalMargin("margin-left", "padding-left", cssDeclarations));

        CssLength rightValue = new CssLength(
            computeHorizontalMargin("margin-right", "padding-right", "border-right", cssDeclarations),
            isCustomHorizontalMargin("margin-right", "padding-right", cssDeclarations));

        CssLength topValue = new CssLength(
            computeVerticalMargin("margin-top", element, cssDeclarations),
            isCustomVerticalMargin("margin-top", cssDeclarations));

        CssLength bottomValue = new CssLength(
            computeVerticalMargin("margin-bottom", element, cssDeclarations),
            isCustomVerticalMargin("margin-bottom", cssDeclarations));

        // WORDSNET-18824 Collapse margins of an empty block with the top margin of the block right after the empty
        // one.
        if ((getCurrentContext().getEmptyBlockMarginBefore() != null) && (getCurrentContext().getEmptyBlockMarginBefore().getValue() > topValue.getValue()))
        {
            topValue = getCurrentContext().getEmptyBlockMarginBefore();
        }

        // Margin collapsing quirk. In quirks mode, the last paragraph element in a table cell has zero bottom margin.
        // We cannot apply this quirk to <p> element (our CSS box model does not allow this), so we modify default bottom
        // margin of all elements in a cell except the last <p>.
        // See http://www.w3.org/TR/html5/rendering.html#margin-collapsing-quirks
        // Testing in browsers shows that this quirk also applies to preformatted elements (<pre>, <xmp>, and <listing>).
        if (getCurrentBox().isInsideTableCell() &&
            !bottomValue.isCustom() &&
            !MathUtil.isZero(bottomValue.getValue()) &&
            element.isElementContainsText())
        {
            if ((!hasBottomMarginQuirkWhenLastInCell(element)) ||
                (mDocumentMode != CssDocumentMode.QUIRKS) ||
                (getCurrentBox().getChildBottomMarginCollapsing() == CssBoxMarginCollapsing.SEPARATE))
            {
                bottomValue = new CssLength(bottomValue.getValue(), true);
            }
        }

        CssDeclaration borderTopStyleDeclaration = cssDeclarations.get("border-top-style");
        boolean hasTopBorder = (borderTopStyleDeclaration != null) &&
            (!borderTopStyleDeclaration.getValue().equals(CssValue.None)) &&
            (!borderTopStyleDeclaration.getValue().equals(CssValue.Hidden));

        CssDeclaration borderBottomStyleDeclaration = cssDeclarations.get("border-bottom-style");
        boolean hasBottomBorder = (borderBottomStyleDeclaration != null) &&
            (!borderBottomStyleDeclaration.getValue().equals(CssValue.None)) &&
            (!borderBottomStyleDeclaration.getValue().equals(CssValue.Hidden));

        CssBoxMargin left = CssBoxMargin.create(leftValue, CssBoxMarginCollapsing.ADD, false);
        CssBoxMargin right = CssBoxMargin.create(rightValue, CssBoxMarginCollapsing.ADD, false);

        CssBoxMargin top = CssBoxMargin.create(topValue, getCurrentBox().getChildTopMarginCollapsing(), false);
        CssBoxMargin bottom = CssBoxMargin.create(bottomValue, getCurrentBox().getChildBottomMarginCollapsing(), false);

        CssBox box = new CssBox(left, right, top, bottom, element.startsWithImplicitBox(), element.endsWithImplicitBox(),
            !hasBottomBorder, getCurrentBox().isInsideTableCell(), true);
        if (hasTopBorder)
        {
            box.setChildTopMarginCollapsing(CssBoxMarginCollapsing.ADD);
        }
        if (hasBottomBorder)
        {
            box.setChildBottomMarginCollapsing(CssBoxMarginCollapsing.ADD);
        }

        return box;
    }

    private CssBox computeInlineBox(IHtmlElementProvider element)
    {
        CssBoxMargin left = CssBoxMargin.create(CssLength.ZeroDefault, CssBoxMarginCollapsing.ADD, false);
        CssBoxMargin right = CssBoxMargin.create(CssLength.ZeroDefault, CssBoxMarginCollapsing.ADD, false);
        CssBoxMargin top = CssBoxMargin.create(CssLength.ZeroDefault, getCurrentBox().getChildTopMarginCollapsing(), false);
        CssBoxMargin bottom = CssBoxMargin.create(CssLength.ZeroDefault, getCurrentBox().getChildBottomMarginCollapsing(), false);

        return new CssBox(left, right, top, bottom, element.startsWithImplicitBox(), element.endsWithImplicitBox(), true,
            getCurrentBox().isInsideTableCell(), true);
    }

    private CssBox computeCaptionBox(IHtmlElementProvider element, CssDeclarationCollection cssDeclarations)
    {
        CssLength leftLength = new CssLength(computeTableCellMargin("margin-left", cssDeclarations), true);
        CssBoxMargin left = CssBoxMargin.create(leftLength, CssBoxMarginCollapsing.SEPARATE, false);

        CssLength rightLength = new CssLength(computeTableCellMargin("margin-right", cssDeclarations), true);
        CssBoxMargin right = CssBoxMargin.create(rightLength, CssBoxMarginCollapsing.SEPARATE, false);

        double topValue = computeTableCellMargin("margin-top", cssDeclarations);
        CssLength topLength = new CssLength(topValue, !MathUtil.isZero(topValue));
        CssBoxMargin top = CssBoxMargin.create(topLength, CssBoxMarginCollapsing.SEPARATE, true);

        double bottomValue = computeTableCellMargin("margin-bottom", cssDeclarations);
        CssLength bottomLength = new CssLength(bottomValue, !MathUtil.isZero(bottomValue));
        CssBoxMargin bottom = CssBoxMargin.create(bottomLength, CssBoxMarginCollapsing.SEPARATE, true);

        return new CssBox(left, right, top, bottom, element.startsWithImplicitBox(), element.endsWithImplicitBox(), true,
            getCurrentBox().isInsideTableCell(), true);
    }

    private CssBox computeTableBox(IHtmlElementProvider element, CssDeclarationCollection cssDeclarations)
    {
        CssLength leftLength = new CssLength(computeTableCellMargin("margin-left", cssDeclarations), true);
        CssBoxMargin left = CssBoxMargin.create(leftLength, CssBoxMarginCollapsing.ADD, false);

        CssLength rightLength = new CssLength(computeTableCellMargin("margin-right", cssDeclarations), true);
        CssBoxMargin right = CssBoxMargin.create(rightLength, CssBoxMarginCollapsing.ADD, false);

        double topValue = computeTableCellMargin("margin-top", cssDeclarations);
        CssLength topLength = new CssLength(topValue, !MathUtil.isZero(topValue));
        CssBoxMargin top = CssBoxMargin.create(topLength, getCurrentBox().getChildTopMarginCollapsing(), true);

        double bottomValue = computeTableCellMargin("margin-bottom", cssDeclarations);
        CssLength bottomLength = new CssLength(bottomValue, !MathUtil.isZero(bottomValue));
        CssBoxMargin bottom = CssBoxMargin.create(bottomLength, getCurrentBox().getChildBottomMarginCollapsing(), true);

        return new CssBox(left, right, top, bottom, element.startsWithImplicitBox(), element.endsWithImplicitBox(), true,
            getCurrentBox().isInsideTableCell(), false);
    }

    private CssBox computeTableCellBox(IHtmlElementProvider element)
    {
        CssBoxMargin zeroCollapseDefault = CssBoxMargin.create(CssLength.ZeroDefault, CssBoxMarginCollapsing.SEPARATE, true);
        CssBoxMargin zeroKeepDefault = CssBoxMargin.create(CssLength.ZeroDefault, CssBoxMarginCollapsing.SEPARATE, false);

        CssBoxMargin left = zeroKeepDefault;
        CssBoxMargin right = zeroKeepDefault;
        CssBoxMargin bottom = zeroCollapseDefault;

        // Default vertical margins of an element inside a table cell collapse to zero when the element's box is the first
        // or the last box of the cell. See http://www.w3.org/TR/html5/rendering.html#margin-collapsing-quirks
        CssBoxMargin top = ((mDocumentMode == CssDocumentMode.QUIRKS) || !element.isElementContainsText())
            ? zeroCollapseDefault
            : zeroKeepDefault;

        return new CssBox(left, right, top, bottom, element.startsWithImplicitBox(), element.endsWithImplicitBox(), true, true, true);
    }

    private CssBox computeInlineListBox(IHtmlElementProvider element, CssDeclarationCollection cssDeclarations, /*CssDisplayType*/int displayType)
    {
        CssBoxMargin left = CssBoxMargin.create(CssLength.ZeroDefault, CssBoxMarginCollapsing.ADD, false);
        CssBoxMargin right = CssBoxMargin.create(CssLength.ZeroDefault, CssBoxMarginCollapsing.ADD, false);

        CssLength topValue = (displayType == CssDisplayType.INLINE)
            ? new CssLength(computeVerticalMargin("margin-top", element, cssDeclarations),
                isCustomVerticalMargin("margin-top", cssDeclarations))
            : CssLength.ZeroDefault;

        CssBoxMargin top = CssBoxMargin.create(topValue, getCurrentBox().getChildTopMarginCollapsing(), false);
        CssBoxMargin bottom = CssBoxMargin.create(CssLength.ZeroDefault, getCurrentBox().getChildBottomMarginCollapsing(), false);

        return new CssBox(left, right, top, bottom, element.startsWithImplicitBox(), element.endsWithImplicitBox(), true,
            getCurrentBox().isInsideTableCell(), true);
    }

    private static CssBox computeDisplayTableCellBox(IHtmlElementProvider element, CssDeclarationCollection cssDeclarations)
    {
        CssLength leftValue = new CssLength(computeHorizontalMargin("padding-left", "border-left-width", cssDeclarations),
            hasCustomValue("padding-left", cssDeclarations));

        CssLength rightValue = new CssLength(computeHorizontalMargin("padding-right", "border-right-width", cssDeclarations),
            hasCustomValue("padding-right", cssDeclarations));

        CssBoxMargin left = CssBoxMargin.create(leftValue, CssBoxMarginCollapsing.ADD, false);
        CssBoxMargin right = CssBoxMargin.create(rightValue, CssBoxMarginCollapsing.ADD, false);
        CssBoxMargin top = CssBoxMargin.create(CssLength.ZeroDefault, CssBoxMarginCollapsing.SEPARATE, false);
        CssBoxMargin bottom = CssBoxMargin.create(CssLength.ZeroDefault, CssBoxMarginCollapsing.SEPARATE, true);

        return new CssBox(left, right, top, bottom, element.startsWithImplicitBox(), element.endsWithImplicitBox(),
            true, true, true);
    }

    private static boolean isCustomHorizontalMargin(
        String marginProperty,
        String paddingProperty,
        CssDeclarationCollection cssDeclarations)
    {
        return hasCustomValue(marginProperty, cssDeclarations) ||
            hasCustomValue(paddingProperty, cssDeclarations);
    }

    private static boolean hasCustomValue(String property, CssDeclarationCollection declarations)
    {
        Debug.assertTrue(StringUtil.hasChars(property));
        CssDeclaration declaration = declarations.get(property);
        // Values that come from the User Agent stylesheet are also considered default.
        return (declaration != null) && (!declarations.isUserAgent(property));
    }

    private static boolean isCustomVerticalMargin(String marginPropertyName, CssDeclarationCollection cssDeclarations)
    {
        return cssDeclarations.get(marginPropertyName) != null;
    }

    private static double computeHorizontalMargin(String marginPropertyName, String paddingPropertyName,
        String borderPropertyPrefix, CssDeclarationCollection cssDeclarations)
    {
        return computeMargin(cssDeclarations, marginPropertyName) +
               computePadding(cssDeclarations, paddingPropertyName) +
               computeBorderLineWidth(cssDeclarations, borderPropertyPrefix);
    }

    private static double computeHorizontalMargin(String paddingPropertyName,
        String borderWidthPropertyName, CssDeclarationCollection cssDeclarations)
    {
        return computePadding(cssDeclarations, paddingPropertyName) +
               computeBorderLineWidth(cssDeclarations, borderWidthPropertyName);
    }

    private static double computeBorderLineWidth(CssDeclarationCollection cssDeclarations, String borderPropertyPrefix)
    {
        Debug.assertTrue(!msString.isNullOrEmpty(borderPropertyPrefix));
        Debug.assertTrue(cssDeclarations != null);

        String borderStylePropertyName = borderPropertyPrefix + "-style";
        CssDeclaration borderStyleDeclaration = cssDeclarations.get(borderStylePropertyName);
        if ((borderStyleDeclaration != null) && (!borderStyleDeclaration.getValue().equals(CssValue.None)))
        {
            CssDeclaration borderWidthDeclaration = cssDeclarations.get(borderPropertyPrefix + "-width");
            if (borderWidthDeclaration != null)
            {
                return CssUtil.getBorderLineWidth(borderWidthDeclaration.getValue());
            }
        }
        return 0.0;
    }

    private static double computePadding(CssDeclarationCollection cssDeclarations, String paddingPropertyName)
    {
        Debug.assertTrue(!msString.isNullOrEmpty(paddingPropertyName));
        Debug.assertTrue(cssDeclarations != null);

        CssDeclaration paddingDeclaration = cssDeclarations.get(paddingPropertyName);
        if (paddingDeclaration != null)
            return CssPaddingIndividualPropertyDefBase.getPadding(paddingDeclaration.getValue());
        return 0.0;
    }

    private static double computeMargin(CssDeclarationCollection cssDeclarations, String marginPropertyName)
    {
        Debug.assertTrue(!msString.isNullOrEmpty(marginPropertyName));
        Debug.assertTrue(cssDeclarations != null);

        CssDeclaration marginDeclaration = cssDeclarations.get(marginPropertyName);
        if (marginDeclaration != null)
            return CssMarginIndividualPropertyDefBase.getIndent(marginDeclaration.getValue());
        return 0.0;
    }

    private double computeVerticalMargin(String marginPropertyName, IHtmlElementProvider element,
        CssDeclarationCollection cssDeclarations)
    {
        CssDeclaration marginDeclaration = cssDeclarations.get(marginPropertyName);
        if (marginDeclaration != null)
        {
            return CssMarginIndividualPropertyDefBase.getIndent(marginDeclaration.getValue());
        }

        double defaultMarginValue = getDefaultVerticalMargins(element);
        // According to the CSS specification, relative values refer to the 'font-size' value of the corresponding HTML element.
        // In MS Word, however, such values refer to a fixed 'font-size' value that doesn't change from element to element.
        double fontSize = mApplyFormattingAsMsWord
            ? CssUtil.DEFAULT_FONT_SIZE
            : CssUtil.computeFontSize(cssDeclarations);
        return defaultMarginValue * fontSize;
    }

    private static double computeTableCellMargin(String marginPropertyName, CssDeclarationCollection cssDeclarations)
    {
        CssDeclaration marginDeclaration = cssDeclarations.get(marginPropertyName);
        return (marginDeclaration != null)
            ? CssMarginIndividualPropertyDefBase.getIndent(marginDeclaration.getValue())
            : 0.0;
    }

    /// <summary>
    /// Returns default (user agent) vertical margins of HTML elements, in em.
    /// </summary>
    /// <param name="element">An HTML element.</param>
    /// <returns>
    /// Vertical margins of the HTML element as declared in the default (user agent) style sheet.
    /// </returns>
    /// <remarks>
    /// These margins cannot be declared directly in the user agent style sheet, because in this case we will not be able
    /// to tell where a CSS margin property came from: the author style sheet or the user agent style sheet.
    /// </remarks>
    private double getDefaultVerticalMargins(IHtmlElementProvider element)
    {
        if (!mApplyDefaultVerticalMargins)
        {
            return 0.0;
        }

        return HtmlDefaultVerticalMargins.getMargins(element);
    }

    private static boolean hasBottomMarginQuirkWhenLastInCell(IHtmlElementProvider element)
    {
        return ("p".equals(element.getElementName())) ||
            ("pre".equals(element.getElementName())) ||
            ("listing".equals(element.getElementName())) ||
            ("xmp".equals(element.getElementName())) ||
            ("plaintext".equals(element.getElementName()));
    }

    private static boolean isFloatingTable(String elementName, /*CssDisplayType*/int displayType, CssDeclarationCollection cssDeclarations)
    {
        if ((!"table".equals(elementName)) || (displayType != CssDisplayType.TABLE))
        {
            return false;
        }

        CssDeclaration floatDeclaration = cssDeclarations.get("float");
        if (floatDeclaration == null)
        {
            return false;
        }

        CssPropertyValue floatValue = floatDeclaration.getValue();
        return floatValue.equals(CssValue.Left) || floatValue.equals(CssValue.Right);
    }

    /// <summary>
    /// Indicates whether a box is full-width (takes the whole width of an enclosing box).
    /// </summary>
    /// <remarks>
    /// This method is a quick and simple check for situations where a box is certainly full-width.
    /// We cannot do a more general check here, because it requires performing layout of boxes.
    /// </remarks>
    private static boolean isFullWidthBox(CssDeclarationCollection cssDeclarations)
    {
        CssDeclaration widthDeclaration = cssDeclarations.get("width");
        return (widthDeclaration != null) && widthDeclaration.getValue().equals(new CssPercentageValue(100.0));
    }

    private CssBox getCurrentBox()
    {
        Debug.assertTrue(mBoxes.getCount() > 0);
        return mBoxes.peek();
    }

    private CssBoxFormattingContext getCurrentContext()
    {
        Debug.assertTrue(mContexts.getCount() > 0);
        return mContexts.peek();
    }

    private /*final*/ /*CssDocumentMode*/int mDocumentMode;

    private /*final*/ boolean mApplyDefaultVerticalMargins;

    /// <summary>
    /// Indicates whether we should stick to MS Word's rules when applying CSS formatting to document model.
    /// </summary>
    private /*final*/ boolean mApplyFormattingAsMsWord;

    private /*final*/ msStack<CssBox> mBoxes = new msStack<CssBox>();

    private /*final*/ msStack<CssBoxFormattingContext> mContexts = new msStack<CssBoxFormattingContext>();

    /// <summary>
    /// Indicates whether any boxes were added or removed from the model since last recalculation.
    /// </summary>
    /// <remarks>
    /// Model must be recalculated no more than once on each modification (addition or removal of an element).
    /// This flag is used to prevent additional recalculations.
    /// </remarks>
    private boolean mRecalculationNeeded;

    private CssLength mLeft = CssLength.ZeroDefault;
    private CssLength mRight = CssLength.ZeroDefault;
    private CssLength mTop = CssLength.ZeroDefault;
    private CssLength mBottom = CssLength.ZeroDefault;
}

