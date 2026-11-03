// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/12/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.BidiLevelList;
import com.aspose.ms.System.msObject;


/// <summary>
/// Applies CSS formatting to nodes of the document model.
/// </summary>
class DocumentFormatter implements ITableFormatter
{
    /// <summary>
    /// Applies calculated CSS styles to a model table.
    /// </summary>
    /// <param name="table">A model table.</param>
    /// <param name="topCaptionCount">Number of caption rows at the top of the table.</param>
    /// <param name="bottomCaptionCount">Number of caption rows at the bottom of the table.</param>
    /// <param name="appliedLeftBorder">Left CSS border of the table.</param>
    /// <param name="appliedRightBorder">Right CSS border of the table.</param>
    /// <remarks>Table captions are imported as table rows. Top captions become leading table rows and bottom captions
    /// become trailing rows. To distinguish 'normal' table rows from caption rows we need to know how many top and bottom
    /// caption rows the table contains.</remarks>
    public void toTable(
        Table table,
        int topCaptionCount,
        int bottomCaptionCount,
        CssBorder appliedLeftBorder,
        CssBorder appliedRightBorder) throws Exception
    {
        if (mCssStyleTracker.isEmpty())
            return;

        Style style = mStyleFormatter.getModelStyle(StyleType.TABLE);
        if (style != null)
        {
            for (Row row : (Iterable<Row>) table.getRows())
                row.getTablePr().setAttr(TableAttr.ISTD, style.getIstd());
        }

        // WORDSNET-12135 Table Style does not import into output document while using InsertHTML.
        // In this case table uses an already existed document style, so apply this style to the table completely.
        if ((style != null) && !style.getBuiltIn())
        {
            table.setStyleOptions(TableStyleOptions.FIRST_ROW | TableStyleOptions.FIRST_COLUMN | TableStyleOptions.ROW_BANDS |
                TableStyleOptions.LAST_COLUMN | TableStyleOptions.LAST_ROW);
        }

        HtmlTableFormater.toTable(
            table,
            topCaptionCount,
            bottomCaptionCount,
            appliedLeftBorder,
            appliedRightBorder,
            mCssStyleTracker);
    }

    /// <summary>
    /// Creates an instance of this class that uses MS Word formatting rules.
    /// </summary>
    DocumentFormatter(
        Document document,
        CssStyleTracker styleTracker,
        boolean isLoadingHtmlAltChunk)
    {
        Debug.assertTrue(document != null);
        Debug.assertTrue(styleTracker != null);

        mDocument = document;
        mCssStyleTracker = styleTracker;
        mParagraphFormatter = new ParagraphFormatterWordRules();
        mFontFormatter = new FontFormatterWordRules(mDocument.getFontProvider(), isLoadingHtmlAltChunk);
        mStyleFormatter = new StyleFormatterWordRules(
            mDocument,
            mCssStyleTracker,
            mParagraphFormatter,
            mFontFormatter);
        mSectionFormatter = new SectionFormatterWordRules(
            mDocument,
            mCssStyleTracker);
        mRemoveDuplicateDirectFormatting = true;
    }

    /// <summary>
    /// Creates an instance of this class that uses more HTML and CSS-conformant formatting rules.
    /// These rules differ from what MS Word uses.
    /// </summary>
    DocumentFormatter(
        Document document,
        int defaultParagraphIstd,
        int defaultFontIstd,
        /*CssUserAgentFormatting*/int omittedUserAgentFormatting,
        CssStyleTracker styleTracker,
        boolean useHtmlBlocks,
        CssFontFaceProvider cssFontFaceProvider)
    {
        Debug.assertTrue(document != null);
        Debug.assertTrue(styleTracker != null);

        mDocument = document;
        mCssStyleTracker = styleTracker;
        boolean applyUserAgentStyles = (omittedUserAgentFormatting & CssUserAgentFormatting.FONT) == 0;
        mFontFormatter = new FontFormatterHtmlRules(applyUserAgentStyles, cssFontFaceProvider, document.getFontProvider());
        mParagraphFormatter = new ParagraphFormatterHtmlRules(useHtmlBlocks);
        mStyleFormatter = new StyleFormatterHtmlRules(
            mDocument,
            mCssStyleTracker,
            defaultParagraphIstd,
            defaultFontIstd,
            mFontFormatter,
            mParagraphFormatter);
        mSectionFormatter = new SectionFormatterHtmlRules(
            mDocument,
            mCssStyleTracker);
        mRemoveDuplicateDirectFormatting = false;
    }

    void updateDocumentWideFormatting() throws Exception
    {
        mStyleFormatter.updatePredefinedStyles();
    }

    void updateDefaultSectionProperties(Section section)
    {
        mSectionFormatter.updateDefaultSectionProperties(section);
    }

    void postUpdateDocumentWideFormatting()
    {
        mStyleFormatter.postUpdateStyles();
    }

    void pushElement(IHtmlElementProvider element, boolean updateCounters)
    {
        mCssStyleTracker.pushElement(element, updateCounters);
    }

    void popElement()
    {
        mCssStyleTracker.popElement();
    }

    boolean switchToPart(/*HtmlElementPart*/int part, boolean updateCounters)
    {
        return mCssStyleTracker.switchToPart(part, updateCounters);
    }

    String getGeneratedContent()
    {
        return mCssStyleTracker.getGeneratedContent();
    }

    int getListLevelCount()
    {
        return mCssStyleTracker.getListLevelCount();
    }

    GeneratedContentListLabelInfo getListLabelInfo(int skipListLevelsCount, int currentListLevelNumber)
    {
        return mCssStyleTracker.getListLabelInfo(skipListLevelsCount, currentListLevelNumber);
    }

    boolean isBlockLevelElement()
    {
        return mCssStyleTracker.isBlockLevelElement();
    }

    CssDeclarationCollection getElementDeclarations() { return mCssStyleTracker.getElementDeclarations(); }

    IHtmlElementProvider getCurrentElement() { return mCssStyleTracker.getCurrentElement(); }

    BidiLevelList getActiveBidiLevels()
    {
        return mCssStyleTracker.getActiveBidiLevels();
    }

    boolean getCurrentElementHasChildren() { return mCssStyleTracker.getCurrentElementHasChildren(); }

    void handleSpanStart() throws Exception
    {
        mStyleFormatter.handleSpanStart();
    }

    void handleSpanEnd()
    {
        mStyleFormatter.handleSpanEnd();
    }

    void handleParagraphStart() throws Exception
    {
        mStyleFormatter.handleParagraphStart();
    }

    boolean parentElementIsBlockLevel()
    {
        return mCssStyleTracker.parentElementIsBlockLevel();
    }

    /*CssDisplayType*/int parentElementDisplayType()
    {
        return mCssStyleTracker.parentElementDisplayType();
    }

    boolean isPreformatted()
    {
        return mCssStyleTracker.isPreformatted();
    }

    boolean isPreformattedWithLine()
    {
        return mCssStyleTracker.isPreformattedWithLine();
    }

    boolean isPreformattedWithWrap()
    {
        return mCssStyleTracker.isPreformattedWithWrap();
    }

    boolean isBlockRtl()
    {
        return mCssStyleTracker.isBlockRtl();
    }

    int getCounterValue(String counterName)
    {
        return mCssStyleTracker.getCounterValue(counterName);
    }

    /// <summary>
    /// Applies calculated CSS styles to a model paragraph style.
    /// </summary>
    /// <param name="paragraph">Paragraph to format.</param>
    void toParagraphFormat(Paragraph paragraph) throws Exception
    {
        Debug.assertTrue(paragraph != null);
        if (mCssStyleTracker.isEmpty())
            return;

        mCssStyleTracker.pushImplicitDiv();

        applyParagraphStyleAndFormatting(paragraph.getParagraphFormat());

        if (mRemoveDuplicateDirectFormatting)
        {
            paragraph.removeDuplicateDirectFormatting(RevisionsView.ORIGINAL);
        }

        mCssStyleTracker.popImplicitElement();
    }

    /// <summary>
    /// Applies calculated CSS styles to a model font style.
    /// </summary>
    void toFont(Font font, Style paragraphStyle) throws Exception
    {
        // Also think about font size adjustment here.
        // We should correct size at the very end of collecting style information or even after particular node is parsed.
        // This is unlikely that we get such a style in a style sheet.

        Debug.assertTrue(font != null);
        if (mCssStyleTracker.isEmpty())
            return;

        mCssStyleTracker.pushImplicitSpan();

        if (mRemoveDuplicateDirectFormatting)
        {
            RunPr newRunPr = new RunPr();
            Font newFont = Font.makeFont(newRunPr, mDocument);

            // Needed to make Font.Style work on underlying levels.
            Style existingFontStyle = font.getStyle();
            if (existingFontStyle.getStyleIdentifier() != StyleIdentifier.DEFAULT_PARAGRAPH_FONT)
            {
                newFont.setIstd(existingFontStyle.getIstd());
            }

            applyFontStyleAndFormatting(newFont);

            // Apply only formatting that don't duplicate formatting from other levels (styles, document defaults, etc.)

            RunPr existingRunPr = paragraphStyle.getExpandedRunPr(RunPrExpandFlags.DOCUMENT_DEFAULTS);
            newFont.getStyle().expandRunPr(existingRunPr, RunPrExpandFlags.NORMAL);

            for (int i = 0; i < newRunPr.getCount(); i++)
            {
                int key = newRunPr.getKey(i);
                Object existingValue = existingRunPr.fetchAttr(key);
                Object newValue = newRunPr.get(key);
                // Style istd is always copied here. It's checked for duplicates elsewhere.
                if ((key == FontAttr.ISTD) ||
                    (existingValue == null) ||
                    (!newValue.equals(existingValue)))
                {
                    font.getParent().setRunAttr(key, newValue);
                }
            }
        }
        else
        {
            applyFontStyleAndFormatting(font);
        }

        mCssStyleTracker.popImplicitElement();
    }

    /// <summary>
    /// Applies CSS to the specified section.
    /// </summary>
    /// <param name="section">A model section.</param>
    void toSection(Section section)
    {
        mSectionFormatter.format(section);
    }

    CssDeclarationCollection getAllPageDeclarations()
    {
        return mCssStyleTracker.getAllPageDeclarations();
    }

    /// <summary>
    /// Applies calculated CSS styles to a horizontal rule shape.
    /// </summary>
    void toHorizontalRule(Shape horizontalRuleShape) throws Exception
    {
        Debug.assertTrue(horizontalRuleShape != null);
        if (mCssStyleTracker.isEmpty())
            return;

        CssHRuleNoShadeStyleConverter.toHorizontalRule(mCssStyleTracker.getElementDeclarations(), horizontalRuleShape);
        CssHRuleAlignmentStyleConverter.toHorizontalRule(mCssStyleTracker.getElementDeclarations(), horizontalRuleShape);
        CssHRuleHeightStyleConverter.toHorizontalRule(mCssStyleTracker.getElementDeclarations(), horizontalRuleShape);
        for (CssDeclaration declaration : mCssStyleTracker.getElementDeclarations())
            ((CssComputedDeclaration)declaration).toHorizontalRule(horizontalRuleShape);
    }

    /// <summary>
    /// Applies calculated CSS styles to a shape.
    /// </summary>
    void toShape(Shape shape) throws Exception
    {
        CssBorderStyleConverter.toShape(mCssStyleTracker.getElementDeclarations(), shape);
        for (CssDeclaration declaration : mCssStyleTracker.getElementDeclarations())
            ((CssComputedDeclaration)declaration).toShape(shape);
        CssPositionStyleConverter.toShape(mCssStyleTracker.getElementDeclarations(), shape, mCssStyleTracker.getBoxModel());
    }

    /// <summary>
    /// Applies calculated CSS styles to a document.
    /// </summary>
    void toDocument(Document document, byte[] backgroundImageBytes) throws Exception
    {
        for (CssDeclaration declaration : mCssStyleTracker.getElementDeclarations())
            ((CssComputedDeclaration)declaration).toDocument(document);

        applyBackgroundImage(document, backgroundImageBytes, mCssStyleTracker.getElementDeclarations().get("background-image"));
    }

    /// <summary>
    /// Determines whether the following elements are wrapped in a paragraph:
    /// svg, img, object, embed, textarea, iframe, input, select.
    /// </summary>
    boolean isDisplayedAsBlock()
    {
        switch (getElementDisplayType())
        {
            case CssDisplayType.BLOCK:
            case CssDisplayType.LIST_ITEM:
            case CssDisplayType.TABLE:
            case CssDisplayType.TABLE_CAPTION:
                return true;
            default:
                return false;
        }
    }

    SizeD getEffectiveParentElementSize()
    {
        return mCssStyleTracker.getEffectiveParentElementSize();
    }

    /*LineBreakClear*/int getLineBreakClear()
    {
        return mCssStyleTracker.getLineBreakClear();
    }

    CssBoxModel getBoxModel() { return mCssStyleTracker.getBoxModel(); }

    Style getStyleWithoutDeclarations() { return mStyleFormatter.getStyleWithoutDeclarations(); }

    CssDeclarationCollection getBeforePseudoElementDeclarations() { return mCssStyleTracker.getBeforePseudoElementDeclarations(); }

    /*HtmlElementDisplayState*/int getElementDisplayState() { return mCssStyleTracker.getElementDisplayState(); }

    /*CssDisplayType*/int getElementDisplayType() { return mCssStyleTracker.getElementDisplayType(); }

    EditRevision getElementInsertionRevision() { return mCssStyleTracker.getElementInsertionRevision(); }

    EditRevision getElementDeletionRevision() { return mCssStyleTracker.getElementDeletionRevision(); }

    private void applyFontStyleAndFormatting(Font font) throws Exception
    {
        Style style = mStyleFormatter.getModelStyle(StyleType.CHARACTER);
        if ((style != null) && (!msObject.referenceEquals(font.getStyle(), style)))
        {
            font.setStyle(style);
        }

        mFontFormatter.format(font, mCssStyleTracker);
    }

    private void applyParagraphStyleAndFormatting(ParagraphFormat pf) throws Exception
    {
        Style style = mStyleFormatter.getModelStyle(StyleType.PARAGRAPH);
        if ((style != null) && (!msObject.referenceEquals(pf.getStyle(), style)))
        {
            pf.setStyle(style);
        }

        mParagraphFormatter.format(pf, mCssStyleTracker);
    }

    /// <summary>
    /// Applies 'background-image' CSS property to a document.
    /// </summary>
    private static void applyBackgroundImage(
        Document document,
        byte[] backgroundImageBytes,
        CssDeclaration backgroundImageDeclaration) throws Exception
    {
        if (backgroundImageDeclaration == null)
            return;

        Debug.assertTrue(backgroundImageDeclaration.getValue().getCount() == 1);

        CssValue backgroundImageValue = backgroundImageDeclaration.getValue().getFirstValue();
        if (backgroundImageValue.getValueType() == CssValueType.URI)
        {
            // WORDSNET-24088 Some image formats are not fully supported by the document model in all scenarios.
            // For example, there may be issues with GIF images upon rendering or saving to some document formats.
            // As a workaround, we currently convert such image formats to PNG upon loading. We plan, however, to get rid of
            // that conversion and store such images as is in the model, because MS Word supports that. We're going to
            // rework this code after support for additional formats improves (see WORDSNET-24146).
            backgroundImageBytes = HtmlImageUtil.getSupportedImageBytes(backgroundImageBytes);
            if (backgroundImageBytes != null)
            {
                if (document.getBackgroundShape() == null)
                    document.setBackgroundShape(new Shape(document, ShapeType.RECTANGLE));

                document.getBackgroundShape().getFillCore().setImageBytes(backgroundImageBytes);
                document.getBackgroundShape().getFillCore().setFillType(FillTypeCore.TEXTURE);
            }
        }
        else if (backgroundImageValue.equals(CssValue.None))
        {
            // "background-image" has a priority over "background-color" and these properties both use DocumentBase.BackgroundShape
            // so we should eliminate background image only if background color was not previously set.
            if ((document.getBackgroundShape() == null) || !document.getBackgroundShape().getFilled())
                document.setBackgroundShapeSafe(null);
        }
    }

    private /*final*/ Document mDocument;
    private /*final*/ CssStyleTracker mCssStyleTracker;
    private /*final*/ StyleFormatter mStyleFormatter;
    private /*final*/ SectionFormatter mSectionFormatter;
    private /*final*/ FontFormatter mFontFormatter;
    private /*final*/ ParagraphFormatter mParagraphFormatter;

    /// <summary>
    /// Indicates whether direct formatting should be removed if it duplicates inherited formatting.
    /// </summary>
    private /*final*/ boolean mRemoveDuplicateDirectFormatting;
}

