// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.MathUtil;
import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ConvertUtilCore;
import com.aspose.DrFont;
import com.aspose.StackExtension;


/// <summary>
/// Responsible for importing HTML lists.
/// </summary>
class HtmlListReader
{
    HtmlListReader(
        DocumentBuilder builder,
        DocumentFormatter documentFormatter,
        HtmlResourceLoader htmlResourceLoader,
        String baseUri,
        double defaultWholeListLeftIndent,
        boolean adjustMarkerPositions)
    {
        Debug.assertTrue(builder != null);
        Debug.assertTrue(documentFormatter != null);
        Debug.assertTrue(htmlResourceLoader != null);

        mBuilder = builder;
        mDocumentFormatter = documentFormatter;
        mHtmlResourceLoader = htmlResourceLoader;
        mBaseUri = baseUri;

        mPictureBulletLoader = new HtmlPictureBulletLoader(mBuilder.getDocument(), mHtmlResourceLoader, mBaseUri);
        mListLevelInfos = new msStack<HtmlListLevelInfo>();
        mAdjustMarkerPositions = adjustMarkerPositions;
        mDefaultWholeListLeftIndent = defaultWholeListLeftIndent;
        mOpenedListItemCount = 0;

        mModelListManager = new HtmlModelListManager(mBuilder.getDocument());
        mIsLastListItemParagraphListItem = false;
    }

    void beginLevel(String tag)
    {
        beginLevel(tag, false);
    }

    void endLevel()
    {
        if (mListLevelInfos.getCount() > 0)
        {
            mListLevelInfos.pop();
        }

        if (mListLevelInfos.getCount() == 0)
        {
            // Reset the value so it cannot accidentally be used from outside the list.
            mWholeListLeftIndent = 0.0;

            if (!mIsLastListItemParagraphListItem)
            {
                mModelListManager.deleteAllLists();
            }
        }
    }

    void beginItem(HtmlElementNode node) throws Exception
    {
        Debug.assertTrue(mOpenedListItemCount >= 0);

        // WORDSNET-23569, WORDSNET-10611 Start a new list for an orphan list item.
        if (mListLevelInfos.getCount() == 0)
        {
            beginLevel("ul", true);
        }

        handleListItemElement(node);
        mOpenedListItemCount++;
    }

    void endItem()
    {
        Debug.assertTrue(mOpenedListItemCount > 0);

        // Note that the item may already be closed here. For example, if it belongs to a nested level.
        mItem = null;
        mOpenedListItemCount--;

        // WORDSNET-14529 List items can be nested if they are set via nested elements
        // (for example, &lt;span&gt;) with 'display: list-item' style.
        // So we should close implicit lists only after last opened list item is closed.
        if ((mOpenedListItemCount == 0) && getCurrentListLevelInfo().isImplicit())
        {
            endLevel();
        }
    }

    void indentCurrentParagraphAsListItemIfNeeded()
    {
        if ((mItem == null) || (mBuilder.getCurrentParagraph() != mItem.getParagraph()))
        {
            // The current paragraph is not a list item.
            return;
        }

        HtmlListLevelInfo currentListLevelInfo = getCurrentListLevelInfo();
        ListLevel listLevel = currentListLevelInfo.getCurrentListLevel();

        CssLength leftIndent = getCurrentElementLeftIndent();
        CssLength textIndent = mDocumentFormatter.getElementDeclarations().getCssLength("text-indent");

        CssLength firstLineIndent = mItem.getFirstLineIndent(leftIndent, textIndent);

        if (mAdjustMarkerPositions ||
            leftIndent.isCustom() ||
            firstLineIndent.isCustom() ||
            (!MathUtil.isZero(mWholeListLeftIndent)))
        {
            mBuilder.getParagraphFormat().setLeftIndent(mItem.getLeftIndent(leftIndent, textIndent).getValue());
            mBuilder.getParagraphFormat().setFirstLineIndent(firstLineIndent.getValue());

            CssLength tabStop = mItem.getTabStop(leftIndent, textIndent, listLevel);
            mBuilder.getParagraphFormat().getTabStops().clear();
            if (tabStop != null)
            {
                /*TabAlignment*/int tabAlignment = mDocumentFormatter.isBlockRtl()
                    ? TabAlignment.RIGHT
                    : TabAlignment.LEFT;
                mBuilder.getParagraphFormat().getTabStops().add(tabStop.getValue(), tabAlignment, TabLeader.NONE);
            }

            if (listLevel != null)
            {
                listLevel.getParaPr().getTabStops().clear();
            }
        }
    }

    boolean isAtStartOfListItem()
    {
        return (mItem != null) &&
            (mBuilder.getCurrentParagraph() == mItem.getParagraph()) &&
            mBuilder.isAtStartOfParagraph();
    }

    boolean isListItemNoneListStyle() { return (mItem != null) && mItem.isListItemNoneListStyle(); }

    int getCurrentLevelNumber()
    {
        HtmlListLevelInfo listLevelInfo = getCurrentListLevelInfo();
        return (listLevelInfo != null) ? listLevelInfo.getListLevelNumber() : -1;
    }

    ListLevel getCurrentListLevel()
    {
        HtmlListLevelInfo listLevelInfo = getCurrentListLevelInfo();
        return (listLevelInfo != null) ? listLevelInfo.getCurrentListLevel() : null;
    }

    boolean getCurrentListHasListItems()
    {
        if (getCurrentListLevelInfo() == null)
            return false;

        return getCurrentListLevelInfo().getListItemCount() > 0;
    }

    private CssLength getCurrentElementLeftIndent()
    {
        return mDocumentFormatter.isBlockRtl()
            ? mDocumentFormatter.getBoxModel().getRight()
            : mDocumentFormatter.getBoxModel().getLeft();
    }

    private void beginLevel(String tag, boolean isImplicit)
    {
        // A new level ends the current list item.
        mItem = null;

        int levelNumber = getCurrentLevelNumber() + 1;
        HtmlListLevelInfo newListLevelInfo = new HtmlListLevelInfo(levelNumber, isImplicit);

        newListLevelInfo.setListTemplate(getListTemplate(tag, levelNumber));
        newListLevelInfo.setDefaultListItemMarker(HtmlListItemMarker.getFromListTag(tag, levelNumber));

        newListLevelInfo.isNoneListStyle(mDocumentFormatter.getElementDeclarations().containsIdentifier("list-style-type", "none"));

        int startValue = 1;
        String startAttribute = mDocumentFormatter.getCurrentElement().getAttributeValue("start");
        if (startAttribute != null)
        {
            int parsedStartValue = FormatterPal.tryParseInt(startAttribute);
            if (parsedStartValue >= 0)
            {
                startValue = parsedStartValue;
            }
        }
        newListLevelInfo.setStartValue(startValue);

        // A left indent value from the document builder is applied at the top-level of a list so all its sublevels
        // get indented as well.
        if (mListLevelInfos.getCount() == 0)
        {
            calculateWholeListLeftIndent();
        }

        mListLevelInfos.push(newListLevelInfo);
    }

    private void calculateWholeListLeftIndent()
    {
        // We only apply left indent value from the document builder to list elements only if elements don't have
        // custom left position.

        // We cannot just use the current 'left' value returned by our CSS box model, because it also includes the padding
        // of the list element. But in HTML left padding on list elements is used to offset list items from other
        // paragraphs and is more like a characteristic of the list itself, other than a means of positioning list items
        // relative to the left side of the document. As a result, left padding on list elements shouldn't be taken
        // into account when deciding whether a list has custom (non-default) left indent.

        // The following code actually subtracts left padding of the list element from the 'left' value returned
        // by the CSS box model.

        boolean isRtl = mDocumentFormatter.isBlockRtl();

        boolean parentBoxHasCustomLeft = isRtl
            ? mDocumentFormatter.getBoxModel().hasCustomParentRight()
            : mDocumentFormatter.getBoxModel().hasCustomParentLeft();

        String leftMarginProperty = isRtl
            ? "margin-right"
            : "margin-left";
        boolean listHasCustomLeftMargin = mDocumentFormatter.getElementDeclarations().get(leftMarginProperty) != null;

        mWholeListLeftIndent = (parentBoxHasCustomLeft || listHasCustomLeftMargin)
            ? 0.0
            : mDefaultWholeListLeftIndent;
    }

    private void handleListItemElement(HtmlElementNode node) throws Exception
    {
        HtmlListLevelInfo listLevelInfo = getCurrentListLevelInfo();

        HtmlListItemMarker listItemMarker = getListItemMarker();
        if (listItemMarker == null)
            listItemMarker = listLevelInfo.getDefaultListItemMarker();

        String imageUrl = mDocumentFormatter.getElementDeclarations().getUri("list-style-image");
        boolean isListItemNoneListStyle = mDocumentFormatter.getElementDeclarations().containsIdentifier(
            "list-style-type", "none");

        String listItemValueAttribute = mDocumentFormatter.getCurrentElement().getAttributeValue("value", "-1");
        int listItemValue = FormatterPal.tryParseInt(listItemValueAttribute);
        // WORDSNET-20283 Ignore "start at" values that are not accepted by MS Word. This is what MS Word does when
        // it loads HTML.
        if (ListLevel.isStartAtValid(listItemValue))
        {
            listLevelInfo.setStartValue(listItemValue);
            listLevelInfo.setListItemCount(0);
        }

        boolean isParagraphListItem = HtmlUtil.isParagraphOrHeadingElement(mDocumentFormatter.getCurrentElement().getElementName());
        mIsLastListItemParagraphListItem = isParagraphListItem;

        mDocumentFormatter.switchToPart(HtmlElementPart.BEFORE, false);
        String generatedContent = mDocumentFormatter.getGeneratedContent();
        mDocumentFormatter.switchToPart(HtmlElementPart.ELEMENT, false);

        // WORDSNET-11999 Import list item with 'list-style-type:none' style as simple paragraph.
        // WORDSNET-17520 Import list item with empty generated content as simple paragraph.
        boolean isImportListItemAsParagraph =
            isListItemNoneListStyle &&
            !isParagraphListItem &&
            !StringUtil.hasChars(imageUrl) &&
            !StringUtil.hasChars(generatedContent);

        boolean isPseudoElementListItem = false;

        HtmlListItemBase htmlListItem = null;
        if (!isImportListItemAsParagraph)
        {
            htmlListItem = getHtmlListItem(
                listLevelInfo,
                listItemMarker,
                imageUrl,
                generatedContent,
                isListItemNoneListStyle,
                isParagraphListItem);
            HtmlModelList htmlModelList = mModelListManager.addListItem(listLevelInfo.getListTemplate(), htmlListItem);

            isPseudoElementListItem = htmlListItem.isPseudoElement();

            int listLevelNumber = htmlListItem.getListLevelId().getListLevelNumber();

            mBuilder.getListFormat().setList(htmlModelList.getList());
            mBuilder.getListFormat().setListLevelNumber(listLevelNumber);

            ListLevel listLevel = htmlModelList.getListLevel(listLevelNumber).getListLevel();
            listLevelInfo.setCurrentListLevel(listLevel);

            // Implicit lists that contain orphan list items (<li> elements that have no parent <ol>/<ul> elements) have
            // zero default indentation.
            if (listLevelInfo.isImplicit())
            {
                listLevel.setTextPosition(0.0);
                listLevel.setNumberPosition(0.0);
            }

            if (isParagraphListItem)
                applyListLabelFormatting();
        }

        listLevelInfo.setListItemMarker(listItemMarker);
        listLevelInfo.setListItemCount(listLevelInfo.getListItemCount() + 1)/*Property++*/;

        if (!isImportListItemAsParagraph && mAdjustMarkerPositions)
        {
            // WORDSNET-12958 List numbers are not aligned in output Html/Doc/Docx.
            listLevelInfo.getCurrentListLevel().setAlignment(ListLevelAlignment.LEFT);
        }

        double markerTextWidth = !isImportListItemAsParagraph
            ? getMarkerTextWidth(htmlListItem, imageUrl)
            : 0.0;

        // WORDSNET-12668 List padding simulation spans writed by HTML Export aren't recognized during importing.
        // We write the padding simulation in HtmlListWriter.WritePaddingSimulation() method.
        double paddingSimulationWidth = mDocumentFormatter.getElementDeclarations().getLength(
            HtmlConstants.LIST_PADDING_SIMULATION);
        if (MathUtil.isMinValue(paddingSimulationWidth))
        {
            paddingSimulationWidth = 0.0;
        }

        if (!isParagraphListItem && isPseudoElementListItem)
        {
            String text = getTextOfFirstTextNode(node);
            text = HtmlUtil.removeControlCharsAndWhitespaces(text, false);
            if (msString.startsWith(text, " ", StringComparison.ORDINAL))
            {
                paddingSimulationWidth += HtmlConstants.FIRST_SPACE_WIDTH;
            }
        }

        String displayStr = mDocumentFormatter.getElementDeclarations().getIdentifier("display");
        boolean isDisplayListItem = (msString.equals(displayStr, "")) || StringUtil.equalsIgnoreCase(displayStr, "list-item");

        CssLength textIndent = mDocumentFormatter.getElementDeclarations().getCssLength("text-indent");

        // Markers are inside if 'li' elements aren't located inside 'ul' or 'ol' elements. Chrome browser uses
        // this behavior and we do the same. Also markers are inside if no native list marker is shown.
        // For bulleted items imported from pseudo-elements marker is located inside if text-indent is greater than
        // or equals 0.
        boolean isInsideListStylePosition =
            !isPseudoElementListItem &&
            mDocumentFormatter.getElementDeclarations().containsIdentifier("list-style-position", "inside");

        boolean isMarkerPositionInside =
            isParagraphListItem ||
            isImportListItemAsParagraph ||
            listLevelInfo.isImplicit() ||
            (listLevelInfo.isNoneListStyle() && !isPseudoElementListItem) ||
            !isDisplayListItem ||
            isInsideListStylePosition ||
            (isPseudoElementListItem && ((textIndent.getValue() >= 0) || (Math.abs(textIndent.getValue()) <= markerTextWidth)));

        if (!isImportListItemAsParagraph && isMarkerPositionInside)
        {
            listLevelInfo.getCurrentListLevel().setAlignment(ListLevelAlignment.LEFT);
            if (!isPseudoElementListItem &&
                !isParagraphListItem &&
                (listLevelInfo.isNoneListStyle() || !isDisplayListItem))
            {
                listLevelInfo.getCurrentListLevel().setTrailingCharacter(ListTrailingCharacter.NOTHING);
            }
        }

        double markerToTextDistance = 0.0;
        if (mAdjustMarkerPositions || isMarkerPositionInside)
        {
            markerToTextDistance = markerTextWidth + paddingSimulationWidth;

            if (!isParagraphListItem && !isPseudoElementListItem)
            {
                markerToTextDistance += HtmlConstants.LIST_LABEL_TO_TEXT_WIDTH;
            }
        }
        else if (!isImportListItemAsParagraph)
        {
            markerToTextDistance =
                listLevelInfo.getCurrentListLevel().getTextPosition() - listLevelInfo.getCurrentListLevel().getNumberPosition();
        }

        if (isPseudoElementListItem && !isParagraphListItem)
        {
            // Depending on the direction we should import marker info not only from ::before but also
            // ::after pseudo-element, but markers do not work properly in browser for it.
            // For ::after pseudo-element we also must read 'padding-left' and we may implement it in the future.
            CssLength bulletPaddingFromPseudoElement =
                mDocumentFormatter.getBeforePseudoElementDeclarations().getCssLength("padding-right");

            // WORDSNET-24702 List items with markers specified by ::before or ::after pseudo-elements
            // and without padding shouldn't have trailing character.
            if ((bulletPaddingFromPseudoElement.getValue() == 0) && (paddingSimulationWidth == 0))
            {
                listLevelInfo.getCurrentListLevel().setTrailingCharacter(ListTrailingCharacter.NOTHING);
            }

            markerToTextDistance = markerTextWidth + paddingSimulationWidth;

            mItem = new HtmlPseudoElementListItemInfo(
                mBuilder.getCurrentParagraph(),
                new CssLength(mWholeListLeftIndent, true),
                getCurrentElementLeftIndent(),
                bulletPaddingFromPseudoElement,
                new CssLength(markerToTextDistance, true),
                isMarkerPositionInside,
                isListItemNoneListStyle);
        }
        else
        {
            String paddingProperty = mDocumentFormatter.isBlockRtl()
                ? "padding-right"
                : "padding-left";
            CssLength padding = mDocumentFormatter.getElementDeclarations().getCssLength(paddingProperty);

            mItem = new HtmlRealListItemInfo(
                mBuilder.getCurrentParagraph(),
                new CssLength(mWholeListLeftIndent, true),
                getCurrentElementLeftIndent(),
                padding,
                new CssLength(markerToTextDistance, true),
                isMarkerPositionInside,
                isListItemNoneListStyle);
        }
    }

    private HtmlListItemBase getHtmlListItem(
        HtmlListLevelInfo listLevelInfo,
        HtmlListItemMarker listItemMarker,
        String imageUrl,
        String generatedContent,
        boolean isListItemNoneListStyle,
        boolean isParagraphListItem) throws Exception
    {
        double listLevelNumber = mDocumentFormatter.getElementDeclarations().getNumber(HtmlConstants.LIST_LEVEL_NUMBER);
        int htmlListLevelNumber = !MathUtil.isMinValue(listLevelNumber)
            ? (int)listLevelNumber
            : listLevelInfo.getListLevelNumber();

        int pictureBulletId = mPictureBulletLoader.getPictureBulletId(imageUrl);
        if (pictureBulletId >= 0)
        {
            Shape pictureBullet = mBuilder.getDocument().getLists().getPictureBullet(pictureBulletId);
            double correctedHeightInPoints = getCorrectedPictureBulletHeightInPoints(pictureBullet);
            int pictureBulletHeight = ConvertUtilCore.pointToHalfPoint(correctedHeightInPoints);

            return new HtmlPictureBulletListItem(
                new HtmlListLevelId(htmlListLevelNumber),
                pictureBulletId,
                pictureBulletHeight);
        }

        if (isParagraphListItem)
        {
            String listNumberFormatValue =
                mDocumentFormatter.getElementDeclarations().getString(HtmlConstants.LIST_NUMBER_FORMAT);
            String listNumberValuesValue =
                mDocumentFormatter.getElementDeclarations().getString(HtmlConstants.LIST_NUMBER_VALUES);
            String listNumberStylesValue =
                mDocumentFormatter.getElementDeclarations().getString(HtmlConstants.LIST_NUMBER_STYLES);

            HtmlNumberListItem paragraphListItem = HtmlNumberListItem.create(
                listNumberFormatValue,
                listNumberValuesValue,
                listNumberStylesValue,
                htmlListLevelNumber);

            if (paragraphListItem != null)
            {
                return paragraphListItem;
            }
        }
        else if (StringUtil.hasChars(generatedContent))
        {
            HtmlNumberListItem generatedContentListItem = HtmlNumberListItem.create(
                mDocumentFormatter,
                generatedContent,
                htmlListLevelNumber);

            if (generatedContentListItem != null)
            {
                return generatedContentListItem;
            }
        }

        if (listItemMarker.isBullet())
        {
            String numberFormat = mDocumentFormatter.getElementDeclarations().getString(HtmlConstants.LIST_LABEL_NUMBER_FORMAT);
            String fontFamily = mDocumentFormatter.getElementDeclarations().getString(HtmlConstants.ORIGINAL_FONT_FAMILY);
            if (isValidNumberFormatForBullet(numberFormat) && StringUtil.hasChars(fontFamily))
            {
                return new HtmlBulletListItem(
                    new HtmlListLevelId(htmlListLevelNumber),
                    HtmlMarkerType.AW,
                    mBuilder,
                    mDocumentFormatter,
                    numberFormat,
                    listItemMarker.getNumberStyle(),
                    fontFamily);
            }

            String contentAsString = mDocumentFormatter.getBeforePseudoElementDeclarations().getString("content");

            // WORDSNET-14766 Import custom list markers from pseudo-elements.
            // If li element has no roundtrip information about marker, we can import it from
            // pseudo-element. Depending on the direction we should import marker info
            // not only from ::before but also ::after pseudo-element, but markers do not work properly in browser for it.
            if (!mDocumentFormatter.isBlockRtl() &&
                isListItemNoneListStyle &&
                isValidNumberFormatForBullet(contentAsString))
            {
                return new HtmlBulletListItem(
                    new HtmlListLevelId(listLevelInfo.getListLevelNumber()),
                    HtmlMarkerType.PSEUDO_ELEMENT,
                    mBuilder,
                    mDocumentFormatter,
                    contentAsString,
                    listItemMarker.getNumberStyle(),
                    "");
            }

            return new HtmlBulletListItem(
                new HtmlListLevelId(htmlListLevelNumber),
                HtmlMarkerType.HTML,
                mBuilder,
                mDocumentFormatter,
                listItemMarker.getBulletFormat(),
                listItemMarker.getNumberStyle(),
                listItemMarker.getBulletFontName());
        }

        return HtmlNumberListItem.create(
            htmlListLevelNumber,
            listLevelInfo.getListLevelNumberValue() + 1,
            listItemMarker.getNumberStyle());
    }

    private void applyListLabelFormatting() throws Exception
    {
        int pushedElementsCount = 0;

        IElementProvider firstChildElement = mDocumentFormatter.getCurrentElement();
        while ((firstChildElement.getFirstChildElement() != null) &&
            ("span".equals(firstChildElement.getFirstChildElement().getElementName())))
        {
            firstChildElement = firstChildElement.getFirstChildElement();

            mDocumentFormatter.pushElement((IHtmlElementProvider)firstChildElement, false);

            pushedElementsCount++;
        }

        // WORDSNET-26508 Apply formatting of list label to paragraph break font instead of list label font.
        mDocumentFormatter.toFont(mBuilder.getCurrentParagraph().getParagraphBreakFont(), mBuilder.getCurrentParagraph().getParagraphStyle());

        for (int i = 0; i < pushedElementsCount; i++)
        {
            mDocumentFormatter.popElement();
        }

        IHtmlElementProvider currentElement = mDocumentFormatter.getCurrentElement();

        // We should re-push current element. See Text18318Margins.
        mDocumentFormatter.popElement();
        mDocumentFormatter.pushElement(currentElement, false);
    }

    private static String getTextOfFirstTextNode(HtmlElementNode node)
    {
        for (HtmlNode childNode : node.getChildren())
        {
            // WORDSNET-21142 Check if it's a text node that doesn't contain only new line and space characters.
            // We ignore such whitespace text nodes, because they are there only to indent elements of HTML code and
            // contain no meaningful text.
            HtmlTextNode textNode = ms.as(childNode, HtmlTextNode.class);
            if ((textNode != null) && HtmlUtil.containsAnythingButWhitespaces(textNode.getText(), true))
            {
                return textNode.getText();
            }

            HtmlElementNode htmlElementNode = ms.as(childNode, HtmlElementNode.class);
            if (htmlElementNode != null)
            {
                String text = getTextOfFirstTextNode(htmlElementNode);
                if (!msString.equals(text, ""))
                    return text;
            }
        }

        return "";
    }

    private double getMarkerTextWidth(HtmlListItemBase htmlListItem, String imageUrl) throws Exception
    {
        int pictureBulletId = mPictureBulletLoader.getPictureBulletId(imageUrl);
        if (pictureBulletId >= 0)
        {
            Shape pictureBullet = mBuilder.getDocument().getLists().getPictureBullet(pictureBulletId);
            double originalHeightInPoints = pictureBullet.getImageData().getImageSize().getHeightPoints();
            double correctedHeightInPoints = getCorrectedPictureBulletHeightInPoints(pictureBullet);

            return pictureBullet.getImageData().getImageSize().getWidthPoints() * correctedHeightInPoints / originalHeightInPoints;
        }

        Font listLabelFont = mBuilder.getCurrentParagraph().getListLabel().getFont();
        DrFont drawingFont = mBuilder.getDocument().getFontProvider().fetchDrFont(
            listLabelFont.getName(),
            (float)listLabelFont.getSize(),
            listLabelFont.getFontStyle());

        return drawingFont.getTextWidthPoints(htmlListItem.getListLabelString());
    }

    private static boolean isValidNumberFormatForBullet(String numberFormat)
    {
        return StringUtil.hasChars(numberFormat) &&
            (numberFormat.length() == 1) &&
            !ListLevel.isPlaceholder(numberFormat.charAt(0));
    }

    static double getCorrectedPictureBulletHeightInPoints(Shape pictureBullet) throws Exception
    {
        // Seems like we should always add this value to image's height to make it have same size as in browsers.
        final double PICTURE_BULLET_HEIGHT_CORRECTION_VALUE_IN_POINTS = 2.0;
        double originalHeightInPoints = pictureBullet.getImageData().getImageSize().getHeightPoints();
        return originalHeightInPoints + PICTURE_BULLET_HEIGHT_CORRECTION_VALUE_IN_POINTS;
    }

    /// <summary>
    /// Returns a list-item marker of the current HTML element.
    /// </summary>
    /// <returns>List-item marker corresponding to the current HTML element.</returns>
    private HtmlListItemMarker getListItemMarker()
    {
        String listStyleType = mDocumentFormatter.getElementDeclarations().getIdentifier("list-style-type");
        return HtmlListItemMarker.getFromListStyleType(listStyleType);
    }

    /// <summary>
    /// Gets a model list template for current list level.
    /// </summary>
    private /*ListTemplate*/int getListTemplate(String listTag, int listLevel)
    {
        HtmlListItemMarker listItemMarker = getListItemMarker();
        if (listItemMarker == null)
            listItemMarker = HtmlListItemMarker.getFromListTag(listTag, listLevel);
        return listItemMarker.getListTemplate();
    }

    private HtmlListLevelInfo getCurrentListLevelInfo() { return StackExtension.top(mListLevelInfos); }

    private /*final*/ DocumentBuilder mBuilder;
    private /*final*/ DocumentFormatter mDocumentFormatter;
    private /*final*/ HtmlResourceLoader mHtmlResourceLoader;
    private /*final*/ String mBaseUri;

    /// <summary>
    /// Currently opened list levels.
    /// </summary>
    private /*final*/ msStack<HtmlListLevelInfo> mListLevelInfos;

    private /*final*/ HtmlPictureBulletLoader mPictureBulletLoader;

    /// <summary>
    /// If this option is on, positions of imported list item markers are additionally adjusted so that the resulting list
    /// looks closer to a list rendered in browsers.
    /// </summary>
    private /*final*/ boolean mAdjustMarkerPositions;

    /// <summary>
    /// Indentation used for all lists that have no custom indentation (no custom margins or padding).
    /// </summary>
    private /*final*/ double mDefaultWholeListLeftIndent;

    /// <summary>
    /// Indentation used for the current list as a whole. Items at all levels of this list are additionally indented by this
    /// value.
    /// </summary>
    /// <remarks>
    /// WORDSNET-13239 When inserting an HTML fragment with builder formatting, lists that have no custom
    /// indentation should use target paragraph's indentation.
    /// </remarks>
    private double mWholeListLeftIndent;

    /// <summary>
    /// The list item currently being processed. Can be <c>null</c>.
    /// </summary>
    private HtmlListItemInfo mItem;

    /// <summary>
    /// This field counts opened list items across all list levels.
    /// </summary>
    private int mOpenedListItemCount;

    private /*final*/ HtmlModelListManager mModelListManager;

    /// <summary>
    /// This property is used to prevent HtmlModelListManager from deleting all lists,
    /// when we're reading list items which are specified as "p" elements.
    /// </summary>
    private boolean mIsLastListItemParagraphListItem;
}

