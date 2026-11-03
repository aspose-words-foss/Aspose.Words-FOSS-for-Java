// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/11/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.ms;
import com.aspose.HashSetGeneric;
import com.aspose.Debug;
import com.aspose.IntToObjDictionary;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.StringUtil;
import com.aspose.StringToObjDictionary;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.DrColor;
import com.aspose.UriUtil;
import com.aspose.MathUtil;
import com.aspose.ms.System.DateTime;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Collections.msArrayList;
import java.awt.Color;
import com.aspose.ConvertUtilCore;
import com.aspose.StackExtension;
import com.aspose.CantCreateBitmapException;
import com.aspose.ImageUtil;
import com.aspose.ImageSizeCore;
import com.aspose.ImageConstants;
import com.aspose.FileFormat;
import com.aspose.FileFormatCore;
import com.aspose.Language;
import com.aspose.ms.System.msArray;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Imports HTML file into a Document object.
/// </summary>
class HtmlReader implements IHtmlNodeProcessor
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="settings">HTML reader settings.</param>
    /// <param name="loadFormat">Format of the document that this HTML belongs to.</param>
    /// <param name="resourceLoader">Resource loader.</param>
    HtmlReader(
        HtmlReaderSettings settings,
        /*LoadFormat*/int loadFormat,
        HtmlResourceLoader resourceLoader)
    {
    	this(settings, loadFormat, resourceLoader, null);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="settings">HTML reader settings.</param>
    /// <param name="loadFormat">Format of the document that this HTML belongs to.</param>
    /// <param name="resourceLoader">Resource loader.</param>
    /// <param name="bookmarkNames">Existing bookmark names to pass to the hyperlink resolver.
    /// Can be <b>null</b>. Bookmark names are expected to be in lower case.</param>
    HtmlReader(
        HtmlReaderSettings settings,
        /*LoadFormat*/int loadFormat,
        HtmlResourceLoader resourceLoader,
        HashSetGeneric<String> bookmarkNames)
    {
        Debug.assertTrue(settings != null);
        Debug.assertTrue(resourceLoader != null);
        Debug.assertTrue((loadFormat == LoadFormat.HTML) ||
            (loadFormat == LoadFormat.MHTML) ||
            (loadFormat == LoadFormat.CHM) ||
            (loadFormat == LoadFormat.EPUB));

        mLoadFormat = loadFormat;
        mResourceLoader = resourceLoader;

        mTableNestingLevel = 0;

        // Note that the following two modules implement alternative ways of preserving borders and margins of block-level
        // elements.
        mCommonBorderResolver = new HtmlCommonBorderResolver(settings.UseHtmlBlocks);
        mHtmlBlockReader = new HtmlBlockReader(!settings.UseHtmlBlocks, settings.ApplyFormattingAsMsWord);

        mComments = new IntToObjDictionary<Comment>();
        mCommentRangeEnds = new IntToObjDictionary<CommentRangeEnd>();
        mFullyProcessedComments = new ArrayList<Integer>();
        mFootnotes = new IntToObjDictionary<Footnote>();
        mEndnotes = new IntToObjDictionary<Footnote>();
        mFieldNodesStack = new msStack<FieldChar>();

        // Note that by default we don't support IE conditional comments (the features field is null), because they
        // are not supported by modern browsers. However, this feature must be supported if we want to correctly process
        // VML images.
        if (settings.SupportVml)
        {
            mSupportedFeatures = new Features();
            mSupportedFeatures.add(new Feature("vml", 1, 0));
            // VML shapes exported by MS Word can contain some parts (for example, tables) marked with <!if !mso>
            // and intended for aligning content in browsers only. MS Word itself omits these parts on import, and so do we.
            mSupportedFeatures.add(new Feature("mso", 1, 0));
        }
        mSettings = settings;

        mListReaderManager = new HtmlListReaderManager();
        mHtmlHyperlinkResolver = new HtmlHyperlinkResolver(settings.HyperlinkProcessor, bookmarkNames);
        mFixedPageFormatDetector = new HtmlFixedPageDetector();
    }

    /// <summary>
    /// Reads HTML document from stream into the model.
    /// </summary>
    /// <param name="stream"></param>
    /// <param name="encoding">Can be null, it will trigger autodetection.</param>
    /// <param name="doc">Document</param>
    /// <param name="reuseExistingBookmarks">
    /// Indicates whether to re-use bookmarks that already exist in the target document.
    /// </param>
    void read(
        Stream stream,
        Encoding encoding,
        Document doc,
        boolean reuseExistingBookmarks) throws Exception
    {
        read(stream, encoding, new DocumentBuilder(doc), reuseExistingBookmarks);
    }

    /// <summary>
    /// Reads HTML document from stream into the model associated with builder.
    /// Used to concatenate multiple documents in MHTML.
    /// </summary>
    /// <param name="stream"></param>
    /// <param name="encoding">Can be null, it will trigger autodetection.</param>
    /// <param name="builder">Document builder.</param>
    /// <param name="reuseExistingBookmarks">
    /// Indicates whether to re-use bookmarks that already exist in the target document.
    /// </param>
    void read(
        Stream stream,
        Encoding encoding,
        DocumentBuilder builder,
        boolean reuseExistingBookmarks) throws Exception
    {
        // Remember the current section. It is used later to limit the number of sections we need to process while removing
        // trailing empty paragraphs.
        Section startSection = builder.getCurrentSection();

        // WORDSNET-20364 Set MS Word version for document if it is not altChunk.
        // AltChunk document copies MS Word version from original document.
        // Note that here we rely on the fact that the "apply formatting as MS Word" flag is only set when we
        // load a HTML altChunk in another document and this flag is cleared in all other scenarios. If this behavior of
        // that flag changes at some point in the future, we'll need to use other method of checking whether we're loading
        // a HTML altChunk.
        if (!mSettings.ApplyFormattingAsMsWord)
            builder.getDocument().getCompatibilityOptions().optimizeFor(mSettings.MswVersion);

        // WORDSNET-13221 SPEED. Accessing the external font cache often implies enumerating all system fonts, which
        // is a lengthy operation (especially if font files are not in the in-memory file cache). However, we don't need
        // information from the font cache until we start processing the HTML tree and we can do two lengthy operations
        // in parallel: enumerate system fonts and prepare the HTML tree (parse HTML and collect CSS rules).
        builder.getDocument().getEffectiveFontSettings().preloadFontsInBackground();

        // In certain scenarios (for example, during import of MHTML document parts) this method is used to insert
        // an HTML part into a document. In these scenarios we must preserve font formatting after insertion of HTML part.
        builder.pushRunPr();

        // Detect and correct the encoding of the HTML document.
        encoding = HtmlEncodingDetector.detect(encoding, stream);

        // If the document encoding is ISO-8859-8 (Hebrew visually ordered text),
        // we assume that all text of this document is in visual order.
        boolean textIsVisuallyOrdered = StringUtil.equalsIgnoreCase(encoding.getWebName(), "iso-8859-8");

        // Note that when we import formatting as MS Word we disable certain default CSS (user-agent stylesheet) rules,
        // because in MS Word they are applied via styles.
        init(
            builder,
            true,
            mSettings.ApplyFormattingAsMsWord,
            textIsVisuallyOrdered);

        HtmlDocument srcDoc = buildHtmlTree(stream, encoding);

        processCssRules(srcDoc);

        mHtmlHyperlinkResolver.init(builder, mBidiTextArranger, reuseExistingBookmarks);

        mHtmlBlockReader.init(builder);

        processHtmlTree(srcDoc.getRoot(), true);

        mHtmlHyperlinkResolver.processBookmarksAndHyperlinks(mSettings.KeepUnreferencedIdBookmarks);

        // WORDSNET-19233 Remove empty paragraphs at the end of sections.
        // WORDSNET-27419 SPEED We used to iterate through all sections of the document in the process but it is faster to
        // check only the sections that were imported from HTML. This may increase performance in scenarios where
        // a relatively small HTML document is appended to a huge resulting document (which is the case when loading CHM or
        // EPUB with a lot of topics and concatenating them into a single document).
        removeEmptyParagraphs(startSection);

        // End of the document ends the last sequence of inline elements.
        mBidiTextArranger.rearrangeAndWriteText();

        // Restore saved font formatting. Also see the comment above where font formatting is saved.
        builder.popRunPr();

        mParagraphArranger.debugAssertHasNoRememberedParagraphs();

        Debug.assertTrue(mApplyDocumentWideFormatting);
        mStyleResolver.postUpdateDocumentWideFormatting();

        warnIfDocumentIsFixedPageHtml();
    }

    /// <summary>
    /// Reads HTML document from string and inserts at current position of builder.
    /// </summary>
    /// <remarks>
    /// This overloaded method is designed to insert an HTML fragment into an existing document, not to load a complete
    /// HTML document from a file. It implements certain features specific to HTML fragment processing
    /// (formatting preservation and merging, style isolation).
    /// </remarks>
    void read(
        String html,
        DocumentBuilder builder,
        /*HtmlInsertOptions*/int options,
        boolean reuseExistingBookmarks) throws Exception
    {
        // Remember if we are inserting HTML at the end of a paragraph. In other words, that there's no content between
        // the place where HTML is being inserted and the end of the corresponding paragraph. We use this information later
        // when we decide if we need to insert a paragraph break after the last block imported from HTML.
        // Note that in some mail merge scenarios there can be empty runs between the cursor and the end of the current
        // paragraph. These runs are removed upon saving the document so we ignore them here too.
        boolean htmlIsInsertedAtEndOfParagraph = isAtTheEndOfParagraphWithOptionalEmptyRuns(builder);

        // Paragraph formatting should be preserved after insertion of an HTML fragment.
        ParaPr paraPrBeforeInsertHtml = builder.getParaPrCopy();
        RunPr paragraphBreakRunPrBeforeInsertHtml = AttrCollectionExtension.deepClone(builder.getCurrentParagraph().getParagraphBreakRunPr());

        // WORDSNET-1653 Font formatting should be preserved after insertion of an HTML fragment.
        builder.pushRunPr();

        boolean useDocumentBuilderFormatting = (options & HtmlInsertOptions.USE_BUILDER_FORMATTING) != 0;
        init(builder, false, useDocumentBuilderFormatting, false);
        HtmlDocument srcDoc = HtmlDocument.load(
            html,
            mSupportedFeatures,
            mSettings.IgnoreNoscriptElements,
            mSettings.SupportSelfClosingNonHtmlTags);

        processCssRules(srcDoc);

        mHtmlHyperlinkResolver.init(builder, mBidiTextArranger, reuseExistingBookmarks);

        // WORDSNET-23654 Take into account the current paragraph and created HTML blocks
        // during inserting the HTML document.
        mHtmlBlockReader.init(builder);

        processHtmlTree(srcDoc.getRoot(), true);

        mHtmlHyperlinkResolver.processBookmarksAndHyperlinks(mSettings.KeepUnreferencedIdBookmarks);

        // End of the document ends the last sequence of inline elements.
        mBidiTextArranger.rearrangeAndWriteText();

        // WORDSNET-682 Inserting bulleted list with InsertHtml leaves list open-ended.
        // Might need to flush the last paragraph (insert a paragraph break), because we didn't explicitly start a paragraph
        // at the end of the last block element.
        // The old behavior is to always start a new paragraph after the last block imported from HTML. The newer behavior
        // is more inteligent and it doesn't start a new paragraph if HTML is inserted at the end of a paragraph, because
        // this results in an extra empty paragraph after inserted HTML. The old behavior is used by default for historical
        // reasons.
        // To preserve paragraph formatting after insertion of an HTML fragment the paragraph we add here
        // should be formatted like the target paragraph into which the HTML fragment is being inserted.
        if (!htmlIsInsertedAtEndOfParagraph || ((options & HtmlInsertOptions.REMOVE_LAST_EMPTY_PARAGRAPH) == 0))
        {
            mParagraphArranger.flushLastParagraphIfNeeded(paraPrBeforeInsertHtml, paragraphBreakRunPrBeforeInsertHtml);
        }

        // Restore the previously saved font formatting.
        builder.popRunPr();

        mParagraphArranger.debugAssertHasNoRememberedParagraphs();

        warnIfDocumentIsFixedPageHtml();
    }

    /// <summary>
    /// Returns ends of bookmarks created during import HTML document.
    /// </summary>
    StringToObjDictionary<BookmarkEnd> getBookmarkEnds() { return mHtmlHyperlinkResolver.getBookmarkEnds(); }

    private HtmlDocument buildHtmlTree(
        Stream stream,
        Encoding encoding) throws Exception
    {
        HtmlDocument htmlDocument = null;
        if (mSettings.ParseAsXhtml)
        {
            try
            {
                htmlDocument = HtmlDocument.loadXhtml(stream);
            }
            catch (IllegalStateException e)
            {
                // The input document is not XML. Try to parse it as HTML.
                mSettings.ParseAsXhtml = false;
                stream.setPosition(0);
            }
        }

        if (htmlDocument == null)
        {
            htmlDocument = HtmlDocument.load(
                stream,
                encoding,
                mSupportedFeatures,
                mSettings.IgnoreNoscriptElements,
                mSettings.SupportSelfClosingNonHtmlTags);
        }

        return htmlDocument;
    }

    /// <summary>
    /// Checks if the cursor of the document builder is at the end of a paragraph. Ignores empty runs between the cursor
    /// and the end of the paragraph.
    /// </summary>
    private static boolean isAtTheEndOfParagraphWithOptionalEmptyRuns(DocumentBuilder builder)
    {
        Node currentNode = builder.getCurrentNode();
        // Skip empty runs that have no text.
        while ((currentNode instanceof Run) && !StringUtil.hasChars(((Run)currentNode).getText()))
        {
            currentNode = currentNode.getNextSibling();
        }
        return currentNode == null;
    }

    private void init(
        DocumentBuilder builder,
        boolean applyDocumentWideFormatting,
        boolean useDocumentBuilderFormatting,
        boolean textIsVisuallyOrdered)
    {
        // HTML reader instances can be used only once to read a single HTML document. The following check prevents invalid
        // usage of the class.
        if (mBuilder != null)
        {
            throw new IllegalStateException("Attempt to re-use a HTML reader instance.");
        }

        mBuilder = builder;
        mBaseUri = builder.getDocument().getBaseUri();
        if (mBaseUri == null)
        {
            mBaseUri = "";
        }

        mApplyDocumentWideFormatting = applyDocumentWideFormatting;
        mUseDocumentBuilderFormatting = useDocumentBuilderFormatting;

        mBaseRunPr = useDocumentBuilderFormatting
            ? builder.getRunPrCopy()
            : new RunPr();

        mBaseParaPr = useDocumentBuilderFormatting
            ? builder.getParaPrCopy()
            : new ParaPr();

        mBaseParagraphLeftIndent = useDocumentBuilderFormatting
            ? builder.getParagraphFormat().getLeftIndent()
            : 0.0;

        mTextIsInVisualOrder = textIsVisuallyOrdered;
        mBidiTextArranger = new HtmlBidiTextArranger(mBuilder, textIsVisuallyOrdered, false);

        mVmlShapeReader = new HtmlVmlShapeReader(builder, mResourceLoader);
        mHtmlFontFallbackUtil = new HtmlFontFallbackUtil(builder.getDocument().getFontProvider());
    }

    /// <summary>
    /// See <see cref="ProcessHtmlTree"/>.
    /// </summary>
    public void /*IHtmlNodeProcessor.*/processCell(HtmlElementNode cellNode) throws Exception
    {
        processHtmlTree(cellNode, false);

        // WORDSNET-16399 Empty table cells collapsed after HTML round-trip if they contained an empty paragraph
        // with "-aw-import: ignore" and had "CellPr.HideMark=true".
        // WORDSNET-27490 MS Word clears the HideMark flag for cells with an empty paragraph at the end.
        // WORDSNET-27752 If a cell ends with a line break, the HideMark flag should also be cleared in order to make
        // the line after the line break always visible.
        Cell cell = mBuilder.getCurrentParagraph().getParentCell();
        Paragraph lastParagraph = cell.getLastParagraph();
        if (mParagraphArranger.isParagraphEmptyInHtml(lastParagraph))
        {
            cell.getCellPr().setHideMark(false);
        }
        else
        {
            Run lastRun = lastParagraph.getLastRun(true);
            if ((lastRun != null) &&
                msString.endsWith(lastRun.getText(), ControlChar.LINE_BREAK, StringComparison.ORDINAL))
            {
                cell.getCellPr().setHideMark(false);
            }
        }

        // WORDSNET-9028 Extra paragraph is added to table's cell so cell becomes higher.
        if ((lastParagraph.getChildNodes(NodeType.ANY, false).getCount() == 0) &&
            (lastParagraph.getPreviousNonAnnotationSibling() instanceof Paragraph) &&
            !mParagraphArranger.isParagraphEmptyInHtml(lastParagraph))
        {
            mBuilder.moveTo(lastParagraph.getPreviousNonAnnotationSibling());
            lastParagraph.remove();
        }
    }

    /// <summary>
    /// Recursive processes HTML nodes and load all CSS rules.
    /// </summary>
    private void processCssRules(HtmlDocument htmlDocument) throws Exception
    {
        /*CssUserAgentFormatting*/int omittedUserAgentFormatting = CssUserAgentFormatting.NONE;

        // If font formatting of imported text should be based on target font formatting, default font CSS styles
        // should not be applied to the text to preserve the target font formatting.
        // The same applies to paragraph formatting of the text and to default paragraph CSS styles.
        if (mUseDocumentBuilderFormatting)
        {
            omittedUserAgentFormatting |= CssUserAgentFormatting.FONT;
            omittedUserAgentFormatting |= CssUserAgentFormatting.PARAGRAPH;
        }

        // By default the color in quirks mode for body tag should be black.
        // In case a HTML fragment is inserted into a document builder using its formatting, this color must be undefined.
        DrColor defaultQuirksBodyColor = mUseDocumentBuilderFormatting ? null : DrColor.BLACK;

        CssClassFactory classFactory = new CssClassFactory(
           CssUtil.htmlDocumentModeToCssMode(htmlDocument.getMode()),
           omittedUserAgentFormatting,
           defaultQuirksBodyColor,
           mSettings.ApplyFormattingAsMsWord);

        CssReader cssReader = classFactory.createCssReader(mBaseUri, mResourceLoader);
        cssReader.readAllRules(htmlDocument.getRoot());
        mCssRules = cssReader.getStyleRules();

        // Paragraph style may affect character formatting, so we have to use builder paragraph style when we need
        // to preserve character formatting specified in the document builder.
        int defaultParagraphIstd = mUseDocumentBuilderFormatting
            ? mBuilder.getParagraphFormat().getStyle().getIstd()
            : StyleIndex.NORMAL;

        int defaultFontIstd = mUseDocumentBuilderFormatting
            ? mBuilder.getFont().getStyle().getIstd()
            : StyleIndex.DEFAULT_PARAGRAPH_FONT;

        mCssResolver = classFactory.createCssResolver(cssReader.getStyleRules());

        HtmlElementNode htmlElement = htmlDocument.getRoot().findSingleElementByName("html");
        String htmlElementInlineCssStyle = (htmlElement != null)
            ? htmlElement.getAttributes().getAttributeValue("style", "")
            : "";

        if (htmlElement != null)
        {
            HtmlElementNode bodyElement = htmlElement.findSingleElementByName("body");
            String bodyElementInlineCssStyle = (bodyElement != null)
                ? bodyElement.getAttributes().getAttributeValue("style", "")
                : "";

            mCssStyleTracker = classFactory.createStyleTracker(
                cssReader.getStyleRules(),
                cssReader.getPageRules(),
                htmlElementInlineCssStyle,
                bodyElementInlineCssStyle,
                mCssResolver,
                mSettings.ApplyFormattingAsMsWord,
                mSettings.UseHtmlBlocks);

            // CSS @font-face rules are imported into document's font info collection, which affects the whole document.
            // If we're not allowed to modify document-wise formatting, we cannot add or modify font infos.
            CssFontFaceProvider fontFaceProvider = (mApplyDocumentWideFormatting && mSettings.SupportFontFaceRules)
                ? new CssFontFaceProvider(
                    cssReader.getFontFaceRules(),
                    mBaseUri,
                    mResourceLoader,
                    mBuilder.getDocument())
                : null;

            mStyleResolver = mSettings.ApplyFormattingAsMsWord
                ? new DocumentFormatter(mBuilder.getDocument(), mCssStyleTracker, true)
                : new DocumentFormatter(
                    mBuilder.getDocument(),
                    defaultParagraphIstd,
                    defaultFontIstd,
                    omittedUserAgentFormatting,
                    mCssStyleTracker,
                    mSettings.UseHtmlBlocks,
                    fontFaceProvider);
        }

        mHtmlControlReader = (mSettings.PreferredControlType == HtmlControlType.STRUCTURED_DOCUMENT_TAG)
            ? (HtmlControlReader)new HtmlControlAsSdtReader(mBuilder, mStyleResolver)
            : new HtmlControlAsFormFieldReader(mBuilder, mStyleResolver);

        mElementCategorizer = classFactory.createHtmlElementCategorizer();

        // WORDSNET-6238 Changes to built-in document styles are not allowed when we insert an HTML fragment
        // into a document, which serves as a template.
        if (mApplyDocumentWideFormatting)
        {
            mStyleResolver.updateDocumentWideFormatting();
            mStyleResolver.updateDefaultSectionProperties(mBuilder.getCurrentSection());
        }

        // The paragraph arranger is allowed to reuse the current list item only if we're inserting a HTML fragment
        // into the document (as opposed to loading a whole HTML document into a blank model).
        boolean canReuseListItem = !mApplyDocumentWideFormatting;

        // WORDSNET-21193 HtmlParagraphArranger is created with HtmlListReaderManager to work with
        // CurrentListReader.
        mParagraphArranger = new HtmlParagraphArranger(
            this,
            mBidiTextArranger,
            mBuilder,
            mStyleResolver,
            mCommonBorderResolver,
            mBaseParaPr,
            mListReaderManager,
            mHtmlBlockReader,
            canReuseListItem);

        // WORDSNET-8594 Added support for fixed-width spans in MHTML documents.
        // WORDSNET-25538 Enhanced support for fixed-width spans in MHTML. Added support for spans with multiple children
        // and nested fixed-width spans.
        mFixedWidthSpanReader = new HtmlFixedWidthSpanReader(
            mLoadFormat,
            mStyleResolver,
            mBidiTextArranger,
            mBuilder.getDocument().getFontProvider());
    }

    /// <summary>
    /// Processes all nodes of the specified HTML tree in the depth-first manner.
    /// </summary>
    /// <param name="root">
    /// The root element of the HTML tree to process.
    /// </param>
    /// <param name="resolveRootStyle">
    /// Indicates whether CSS styles of the root element must be resolved. CSS styles of other nodes in the sub-tree are
    /// always resolved regardless of this parameter.
    /// </param>
    private void processHtmlTree(HtmlElementNode root, boolean resolveRootStyle) throws Exception
    {
        HtmlTreeEnumerator enumerator = new HtmlTreeEnumerator(root);
        while (enumerator.moveNext())
        {
            if (enumerator.getCurrent() instanceof HtmlElementNode)
            {
                HtmlElementNode elementNode = (HtmlElementNode)enumerator.getCurrent();
                boolean resolveStyle = (enumerator.getCurrent() != root) || resolveRootStyle;

                if (enumerator.isStart())
                {
                    if (resolveStyle)
                    {
                        mStyleResolver.pushElement(elementNode, true);
                    }

                    if (handleElement(elementNode, true) != HandleNodeAction.HANDLED_TRAVERSE_CHILDREN)
                    {
                        enumerator.dontEnumerateCurrentSubTree();
                        // The current element won't be visited for the second time.
                        if (resolveStyle)
                        {
                            mStyleResolver.popElement();
                        }
                    }
                    else
                    {
                        // A ::before pseudo-element, if any exists, becomes the first child of the current element.
                        processPseudoElement(HtmlElementPart.BEFORE);
                    }
                }
                else
                {
                    // An ::after pseudo-element, if any exists, becomes the last child of the current element.
                    processPseudoElement(HtmlElementPart.AFTER);

                    handleElement(elementNode, false);

                    if (resolveStyle)
                    {
                        mStyleResolver.popElement();
                    }
                }
            }
            else if (enumerator.getCurrent() instanceof HtmlTextNode)
            {
                Debug.assertTrue(enumerator.isStart());
                HtmlTextNode textNode = (HtmlTextNode)enumerator.getCurrent();

                // Ignore last text node containing only whitespaces.
                if ((textNode.getNextSibling() == null) &&
                    mStyleResolver.isBlockLevelElement() &&
                    !containsMeaningfulText(textNode))
                {
                    continue;
                }

                handleText(textNode.getText());
            }
        }
    }

    /// <summary>
    /// Processes a pseudo-element node.
    /// </summary>
    private void processPseudoElement(/*HtmlElementPart*/int part) throws Exception
    {
        Debug.assertTrue(part != HtmlElementPart.ELEMENT);

        // ::before pseudo-elements on list items are handled by the list reader, because they might be treated as
        // list item markers.
        boolean isListItemMarker = ("li".equals(mStyleResolver.getCurrentElement().getElementName())) && (part == HtmlElementPart.BEFORE);
        if (isListItemMarker)
        {
            return;
        }

        // We still must try to switch to the part, even if we won't process its content here, because switching to a part
        // updates CSS counters. If we skip switching, values of related counters may become incorrect.
        if (mStyleResolver.switchToPart(part, true))
        {

            handleText(mStyleResolver.getGeneratedContent());
            mStyleResolver.switchToPart(HtmlElementPart.ELEMENT, false);
        }
    }

    /// <summary>
    /// Called on start AND/OR end of all HTML elements.
    /// NOTE This method can be called twice for start AND end of the element, depending on what you return in the result.
    /// </summary>
    /// <param name="element"></param>
    /// <param name="isStart">
    /// This is true when called for processing of the element start.
    /// False when called for processing of the element end.
    /// </param>
    private /*HandleNodeAction*/int handleElement(
        HtmlElementNode element,
        boolean isStart) throws Exception
    {
        // WORDSNET-25035 Detect if we're loading a fixed-page HTML document and warn customers about formatting loss.
        if (mBuilder.getDocument().getWarningCallback() != null)
        {
            mFixedPageFormatDetector.processHtmlElementNode(
                element.getName(),
                element.getNamespace(),
                mStyleResolver.getElementDeclarations());
        }

        // Ruby elements are imported as inline-level nodes and they cannot contain block-level content. That's why we have
        // to close the current ruby element if we encounter a block-level element.
        if ((mRubyReader != null) && mStyleResolver.isBlockLevelElement())
        {
            mRubyReader.flush();
            mRubyReader = null;
        }

        // Inline contexts (sequences of adjacent inline elements) are bounded by blocks, breaks, and inline separators
        // (inline elements that are not transparent to bidi algorithm and separate inline contexts before and after them).
        if (mStyleResolver.isBlockLevelElement() || isBidiContextSeparator(element))
        {
            mBidiTextArranger.rearrangeAndWriteText();
        }

        if (isStart)
        {
            insertPostponedPageBreakAfterIfNeeded();
        }

        if ((NrxNamespaces.VML.equals(element.getNamespace())) &&
            (mStyleResolver.getElementDisplayState() == HtmlElementDisplayState.VISIBLE))
        {
            if (msString.endsWith(element.getName(), "textbox", StringComparison.ORDINAL))
            {
                // MS Word ignores textbox elements outside other VML elements but we accept documents containing nothing
                // but a textbox.
                return (("body".equals(element.getParent().getName())) && (element.getParent().getChildren().getCount() == 1))
                    ? HandleNodeAction.HANDLED_TRAVERSE_CHILDREN
                    : HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            mHtmlHyperlinkResolver.startInlineBookmarkIfNeeded(element);
            /*HandleNodeAction*/int vmlHandleNodeResult = mVmlShapeReader.handleVml(element);
            mHtmlHyperlinkResolver.endInlineBookmarkIfNeeded(element);
            return vmlHandleNodeResult;
        }

        /*HandleNodeAction*/int result = handleDisplayInvariantElement(element, isStart, mStyleResolver.getElementDisplayType());

        if (result != HandleNodeAction.NOT_HANDLED)
            return result;

        // Depending on 'display' property some elements may changes its properties.
        // It depends on its context too. For example tag 'li' loses its properties outside list and behaves as block-level.
        result = handleDisplayListItemElement(element, isStart);
        if (result != HandleNodeAction.NOT_HANDLED)
            return result;

        result = handleDisplayTableElement(element, isStart);
        if (result != HandleNodeAction.NOT_HANDLED)
            return result;

        result = handleDisplayBlockElement(element, isStart);
        if (result != HandleNodeAction.NOT_HANDLED)
            return result;

        result = handleDisplayInlineElement(element, isStart);
        if (result != HandleNodeAction.NOT_HANDLED)
            return result;

        // Using 'table-columns' or 'table-column-group' outside table context leads to element hiding.
        if (isTableColumnOrColumnGroupElement())
            return HandleNodeAction.HANDLED_SKIP_CHILDREN;

        // Do nothing special for unknown elements. Allow them to be processed.
        return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
    }

    /// <summary>
    /// Processing elements which do not change their properties depending on the 'display' property.
    /// </summary>
    private /*HandleNodeAction*/int handleDisplayInvariantElement(
        HtmlElementNode element,
        boolean isStart,
        /*CssDisplayType*/int displayType) throws Exception
    {
        switch (gStringSwitchMap.of(element.getName()))
        {
            case /*"html"*/0:
            {
                if (isStart)
                    mVmlShapeReader.handleHtml(element);
                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"head"*/1:
            {
                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"title"*/2:
            {
                if (isStart)
                    mBuilder.getDocument().getBuiltInDocumentProperties().get("Title").fromString(element.getInnerText());
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            case /*"meta"*/3:
            {
                if (isStart)
                    handleMeta(element);
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            case /*"base"*/4:
            {
                // WORDSNET-21682 Previously, URI from the 'href' attribute of the 'base' tag always overrode
                // the base URI specified in load options. Now we combine those URIs, because URI from 'href'
                // may be relative.
                if (isStart)
                {
                    String href = HtmlUtil.validateUri(element.getAttributes().getAttributeValue("href", ""));
                    mBaseUri = UriUtil.constructUnescapedAbsoluteUri(mBaseUri, href);
                }

                // WORDSNET-21522 Attribute "target" of the "base" element should be preserved in a custom document
                // property. This makes it possible to restore that attribute value upon saving to HTML. MS Word saves
                // the default "target" value in that custom property too.
                String target = element.getAttributes().getAttributeValue("target", "");
                if (!msString.isNullOrEmpty(target) &&
                    mApplyDocumentWideFormatting &&
                    !mBuilder.getDocument().getCustomDocumentProperties().contains(HtmlUtil.BASE_TARGET_DOCUMENT_PROPERTY))
                {
                    mBuilder.getDocument().getCustomDocumentProperties().add(HtmlUtil.BASE_TARGET_DOCUMENT_PROPERTY, target);
                }

                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            // Script element should be ignored.
            case /*"script"*/5:
            {
                // WORDSNET-19023 Warn the user about the presence of unsupported scripts.
                if (!mIgnoredAnyScriptNodes)
                {
                    warn(WarningType.UNEXPECTED_CONTENT,
                        "HTML client-side scripts are not supported. Scripts have been ignored.");
                    mIgnoredAnyScriptNodes = true;
                }
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            case /*"noscript"*/6:
            {
                // "<noscript>" elements are invisible in browsers when scripting is enabled.
                return mSettings.IgnoreNoscriptElements
                    ? HandleNodeAction.HANDLED_SKIP_CHILDREN
                    : HandleNodeAction.NOT_HANDLED;
            }
            // Elements that should be ignored.
            // 'style' and 'link' are already processed in the first pass.
            case /*"style"*/7:
            case /*"link"*/8:
            {
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            case /*"body"*/9:
            {
                // 'body' element is handled for any value of mStyleResolver.ElementDisplayState, because:
                // 1) Its nested elements can be visible.
                // 2) Text nodes with HtmlElementDisplayState.None (display:none) or HtmlElementDisplayState.Hidden ('visibility:hidden')
                // display states are imported to model as hidden runs.
                handleBody(isStart);
                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }

            // Form Elements
            case /*"input"*/10:
            {
                if (mStyleResolver.getElementDisplayState() != HtmlElementDisplayState.VISIBLE)
                    return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;

                mParagraphArranger.setIsParaStartNeededIfNodeIsBlockLevel();

                if (isStart)
                {
                    handleInput(element);
                }
                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"select"*/11:
            {
                if (mStyleResolver.getElementDisplayState() != HtmlElementDisplayState.VISIBLE)
                    return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;

                mParagraphArranger.setIsParaStartNeededIfNodeIsBlockLevel();

                handleSelect(element, isStart);

                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"option"*/12:
            {
                // Using 'table-columns' or 'table-column-group' outside table context leads to element hiding.
                if (!isTableColumnOrColumnGroupElement() &&
                    (mStyleResolver.getElementDisplayState() == HtmlElementDisplayState.VISIBLE))
                {
                    handleOption(element, isStart);
                }
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            // Special elements
            case /*"a"*/13:
            {
                // Using 'table-columns' or 'table-column-group' outside table context leads to element hiding.
                if (isTableColumnOrColumnGroupElement())
                    return HandleNodeAction.HANDLED_SKIP_CHILDREN;

                // WORDSNET-21612 We used to ignore hidden anchors here but now we always import them, because MS Word
                // does so. Actually, MS Word always imports other hidden elements, too, but we don't fully mimic its
                // behavior. The reason is that we already have some requests from customers (for example, WORDSNET-8381)
                // asking us to ignore certain hidden elements on import. As a result, this fix has been implemented only
                // for hidden anchors.
                handleAnchor(element, isStart);

                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"br"*/14:
            {
                if (isStart && (mStyleResolver.getElementDisplayState() == HtmlElementDisplayState.VISIBLE))
                    handleBreak(element);
                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"img"*/15:
            {
                if (mStyleResolver.getElementDisplayState() != HtmlElementDisplayState.VISIBLE)
                    return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;

                mParagraphArranger.setIsParaStartNeededIfNodeIsBlockLevel();

                if (isStart)
                {
                    handleImage(element);
                }

                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"svg"*/16:
                throw new UnsupportedOperationException("FOSS");
            case /*"math"*/17:
            {
                if (isTableColumnOrColumnGroupElement() ||
                    (displayType == CssDisplayType.TABLE_ROW) ||
                    (displayType == CssDisplayType.TABLE_ROW_GROUP) ||
                    (displayType == CssDisplayType.TABLE_FOOTER_GROUP))
                {
                    return HandleNodeAction.HANDLED_SKIP_CHILDREN;
                }

                if (mStyleResolver.getElementDisplayState() == HtmlElementDisplayState.VISIBLE)
                    handleMathMl(element);

                mParagraphArranger.setIsParaStartNeededIfNodeIsBlockLevel();

                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            case /*"embed"*/18:
            {
                if (mStyleResolver.getElementDisplayState() != HtmlElementDisplayState.VISIBLE)
                    return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;

                mParagraphArranger.setIsParaStartNeededIfNodeIsBlockLevel();

                if (isStart)
                {
                    handleEmbed(element);
                }

                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"object"*/19:
            {
                if (mStyleResolver.getElementDisplayState() != HtmlElementDisplayState.VISIBLE)
                    return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;

                mParagraphArranger.setIsParaStartNeededIfNodeIsBlockLevel();

                return isStart
                    ? handleObject(element)
                    : HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"iframe"*/20:
            {
                if (mStyleResolver.getElementDisplayState() != HtmlElementDisplayState.VISIBLE)
                    return HandleNodeAction.HANDLED_SKIP_CHILDREN;

                mParagraphArranger.setIsParaStartNeededIfNodeIsBlockLevel();

                if (isStart)
                {
                    handleIframe(element);
                }

                mParagraphArranger.setIsParaStartNeededIfNodeIsBlockLevel();

                // WORDSNET-10462 IFRAME contents should be ignored, since it is only visible in legacy browsers
                // that do not support IFRAME elements. We return false here to prevent processing of IFRAME's children.
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            case /*"plaintext"*/21:
            {
                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"textarea"*/22:
            {
                // Using 'table-columns' or 'table-column-group' outside table context leads to element hiding.
                if (mStyleResolver.getElementDisplayState() != HtmlElementDisplayState.VISIBLE)
                    return HandleNodeAction.HANDLED_SKIP_CHILDREN;

                mParagraphArranger.setIsParaStartNeededIfNodeIsBlockLevel();

                handleTextarea(isStart);

                mParagraphArranger.setIsParaStartNeededIfNodeIsBlockLevel();

                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"frameset"*/23:
            {
                if (mLoadFormat == LoadFormat.CHM)
                {
                    warn(WarningType.DATA_LOSS, "HTML frames are not supported when loading a CHM document.");
                }
                else
                {
                    // We delegate frameset reading to HtmlFramesetReader class and skip this element in HtmlReader.
                    HtmlFramesetReader framesetReader = new HtmlFramesetReader(mBuilder.getDocument(), mBaseUri, mResourceLoader);
                    framesetReader.read(element);
                }
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            // Table elements
            case /*"table"*/24:
            {
                if (mStyleResolver.getElementDisplayState() == HtmlElementDisplayState.VISIBLE)
                    handleTable(element);
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            case /*"thead"*/25:
            case /*"tbody"*/26:
            case /*"tfoot"*/27:
            case /*"colgroup"*/28:
            case /*"col"*/29:
            {
                // These nodes are processed in HtmlTable.
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            case /*"th"*/30:
            case /*"td"*/31:
            case /*"caption"*/32:
            {
                if (mStyleResolver.getElementDisplayState() == HtmlElementDisplayState.VISIBLE)
                    handleCell(isStart);
                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"ul"*/33:
            case /*"ol"*/34:
            {
                // Using 'table-columns' or 'table-column-group' outside table context leads to element hiding.
                if (isTableColumnOrColumnGroupElement())
                {
                    return HandleNodeAction.HANDLED_SKIP_CHILDREN;
                }

                // WORDSNET-22044 We used to ignore hidden lists here but now we always import them, because MS Word
                // does so.
                // Also lists with any display type will be handled.
                handleList(isStart);
                if (!isStart)
                {
                    // If the list is empty then we should insert a bookmark after the current paragraph.
                    // The bookmark will be inserted later when all hyperlinks will be collected.
                    if (!mBuilder.getCurrentParagraph().isListItem())
                    {
                        mHtmlHyperlinkResolver.addPendingBookmarkIfNeeded(element);
                    }
                    else
                    {
                        // The start of bookmark was created during handling the first list item.
                        mHtmlHyperlinkResolver.addPendingEndBookmarkIfNeeded(element);
                    }
                }
                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"ruby"*/35:
            {
                // In AW document model, ruby nodes cannot nest inside other ruby nodes. That's why we have to flattern
                // ruby elements and end the current ruby node before starting another one.
                if (mRubyReader != null)
                {
                    mRubyReader.flush();
                    mRubyReader = null;
                }

                if (isStart)
                {
                    mRubyReader = new HtmlRubyReader(mBuilder, mStyleResolver.getElementDeclarations(), mTextIsInVisualOrder);
                }

                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"rp"*/36:
            {
                // <rp> elements inside <ruby> are not visible in browsers that support <ruby> elements.
                // However, <rp> outside <ruby> are treated as normal inline elements.
                return (mRubyReader == null)
                    ? HandleNodeAction.HANDLED_TRAVERSE_CHILDREN
                    : HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
            case /*"rt"*/37:
            {
                if (mRubyReader != null)
                {
                    if (mStyleResolver.getElementDisplayState() == HtmlElementDisplayState.NONE)
                    {
                        // Like MS Word, we simply ignore <rt> elements with 'display:none'. If the current <ruby> element
                        // contains no other <rt> elements that are visible, the <ruby> element will be treated as a usual
                        // <span> without a ruby annotation.
                        return HandleNodeAction.HANDLED_SKIP_CHILDREN;
                    }
                    mRubyReader.isWritingTopText(isStart);
                }
                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"dl"*/38:
            case /*"dt"*/39:
            case /*"dd"*/40:
            {
                // Using 'table-columns' or 'table-column-group' outside table context leads to element hiding.
                if (isTableColumnOrColumnGroupElement())
                    return HandleNodeAction.HANDLED_SKIP_CHILDREN;

                if (mStyleResolver.isBlockLevelElement())
                    handleDescriptionList(isStart);
                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"ins"*/41:
            {
                // Regardless of how the tag is processed(inline, block, etc) it is necessary to make preparations.
                if (isStart && (mStyleResolver.getElementDisplayState() == HtmlElementDisplayState.VISIBLE))
                    handleRevision(mStyleResolver.getElementInsertionRevision());
                return HandleNodeAction.NOT_HANDLED;
            }
            case /*"del"*/42:
            {
                // Regardless of how the tag is processed(inline, block, etc) it is necessary to make preparations.
                if (isStart && (mStyleResolver.getElementDisplayState() == HtmlElementDisplayState.VISIBLE))
                    handleRevision(mStyleResolver.getElementDeletionRevision());
                return HandleNodeAction.NOT_HANDLED;
            }
            case /*"mbp:pagebreak"*/43:
            {
                // mbp:pagebreak, mbp:nu & mbp:section are custom tags used in MOBI and AZW3 documents.
                if (isStart)
                {
                    mBidiTextArranger.rearrangeAndWriteText();

                    // The new break is inserted into a separate run and we need to apply its formatting appropriately.
                    applyFontFormatting(true);

                    mBuilder.insertBreakCore(BreakType.PAGE_BREAK, false);
                }

                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }

            default:
                return HandleNodeAction.NOT_HANDLED;
        }
    }

    private /*HandleNodeAction*/int handleDisplayListItemElement(HtmlElementNode node, boolean isStart) throws Exception
    {
        if (!isListItemElement())
            return HandleNodeAction.NOT_HANDLED;

        // WORDSNET-22044 We used to ignore hidden list items here but now we always import them, because MS Word
        // does so. But unlike MS Word we import such list items as hidden.
        handleListItem(node, isStart);
        return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
    }

    private /*HandleNodeAction*/int handleDisplayTableElement(HtmlElementNode node, boolean isStart) throws Exception
    {
        if (!isTableElement())
            return HandleNodeAction.NOT_HANDLED;

        if (mStyleResolver.getElementDisplayState() != HtmlElementDisplayState.VISIBLE)
            return HandleNodeAction.HANDLED_SKIP_CHILDREN;

        if (handleTable(node))
            return HandleNodeAction.HANDLED_SKIP_CHILDREN;

        // If the table after import does not have rows and columns,
        // we import it as a block-level or inline element depending on "display" property.
        if (mStyleResolver.getElementDisplayType() == CssDisplayType.TABLE)
            handleParagraph(isStart);
        else if (mStyleResolver.getElementDisplayType() == CssDisplayType.INLINE_TABLE)
            handleSpan(node, isStart);

        return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
    }

    private /*HandleNodeAction*/int handleDisplayBlockElement(HtmlElementNode node, boolean isStart) throws Exception
    {
        if (!isBlockElement() || isImageSpan())
            return HandleNodeAction.NOT_HANDLED;

        switch (gStringSwitchMap.of(node.getName()))
        {
            case /*"div"*/44:
            {
                // 'div' element is handled for any value of mStyleResolver.ElementDisplayState, because:
                // 1) Its nested elements can be visible.
                // 2) Text nodes with HtmlElementDisplayState.None (display:none)
                //    or HtmlElementDisplayState.Hidden ('visibility:hidden') display states are imported to model
                //    as hidden runs.
                if (isIgnoredHeaderFooterDiv(node, isStart))
                {
                    // Header (footer) divs are not imported from HTML in the following cases:
                    // 1. If the header (footer) is linked.
                    //    Linked headers and footers are duplicated during export to HTML.
                    //    During import we ignore the duplicates completely, because in the document model a missing header
                    //    (footer) means 'use the header (footer) of the previous section'.
                    // 2. If the current section already has a header (footer) of the same type as the type
                    //    of header (footer) being imported. Related issues are:
                    //    WORDSNET-10597 If the HTML document is malformed and does not contain section breaks,
                    //    we will end up inserting all headers and footers into the same section and an exception will
                    //    occur, because only one header and footer of each type is allowed per section.
                    //    WORDSNET-21803 Discard content of duplicate headers or footers.
                    return HandleNodeAction.HANDLED_SKIP_CHILDREN;
                }
                handleDiv(node, isStart);
                if (isStart)
                {
                    mHtmlHyperlinkResolver.addPendingStartBookmarkIfNeeded(node);
                }
                else
                {
                    mHtmlHyperlinkResolver.addPendingEndBookmarkIfNeeded(node);
                }
                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            case /*"hr"*/45:
            {
                if (isStart && (mStyleResolver.getElementDisplayState() == HtmlElementDisplayState.VISIBLE))
                {
                    handleHorizontalRule(node);
                    // Add a bookmark around the horizontal rule.
                    mHtmlHyperlinkResolver.addPendingStartBookmarkIfNeeded(node);
                    mHtmlHyperlinkResolver.addPendingEndBookmarkIfNeeded(node);

                }
                return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
            }
            default:
            {
                // Nothing to do.
                break;
            }
        }

        // These elements are handled for any value of mStyleResolver.ElementDisplayState, because:
        // 1) Its nested elements can be visible.
        // 2) Text nodes with HtmlElementDisplayState.None (display:none) or HtmlElementDisplayState.Hidden ('visibility:hidden')
        // display states are imported to model as hidden runs.
        handleParagraph(isStart);
        if (isStart)
        {
            mHtmlHyperlinkResolver.addPendingStartBookmarkIfNeeded(node);
        }
        else
        {
            mHtmlHyperlinkResolver.addPendingEndBookmarkIfNeeded(node);
        }

        return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
    }

    private boolean isImageSpan()
    {
        return "absolute".equals(mStyleResolver.getElementDeclarations().getIdentifier("position")) &&
            MathUtil.isZero(mStyleResolver.getElementDeclarations().getLength("height"));
    }

    private /*HandleNodeAction*/int handleDisplayInlineElement(HtmlElementNode node, boolean isStart) throws Exception
    {
        return isInlineElement()
            ? handleSpan(node, isStart)
            : HandleNodeAction.NOT_HANDLED;
    }

    /// <summary>
    /// Returns <c>true</c> if the current element has "table" or "inline-table" value in "display" property.
    /// </summary>
    /// <returns></returns>
    private boolean isTableElement()
    {
        switch (mStyleResolver.getElementDisplayType())
        {
            case CssDisplayType.TABLE:
            case CssDisplayType.INLINE_TABLE:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns <c>true</c> if the current element can be interpreted as a "block-level".
    /// </summary>
    private boolean isBlockElement()
    {
        String elementName = mStyleResolver.getCurrentElement().getElementName();

        switch (mStyleResolver.getElementDisplayType())
        {
            case CssDisplayType.NONE:
                return HtmlElementCategorizer.isBlockElementByDefault(elementName);
            case CssDisplayType.BLOCK:
            case CssDisplayType.LIST_ITEM:
            case CssDisplayType.RUN_IN:
            // Some tables 'display' values outside table context behave as block-level elements.
            case CssDisplayType.TABLE_CAPTION:
            case CssDisplayType.TABLE_HEADER_GROUP:
            case CssDisplayType.TABLE_FOOTER_GROUP:
            case CssDisplayType.TABLE_ROW_GROUP:
            case CssDisplayType.TABLE_ROW:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns <c>true</c> if the current element can be interpreted as a "inline".
    /// </summary>
    private boolean isInlineElement()
    {
        String elementName = mStyleResolver.getCurrentElement().getElementName();

        switch (mStyleResolver.getElementDisplayType())
        {
            case CssDisplayType.NONE:
                return !HtmlElementCategorizer.isBlockElementByDefault(elementName);
            case CssDisplayType.INLINE:
            case CssDisplayType.INLINE_BLOCK:
            // Element with 'table-cell' display value outside table context has the same behavior as 'inline'.
            case CssDisplayType.TABLE_CELL:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns <c>true</c> if the current element can be interpreted as a "list-item".
    /// </summary>
    private boolean isListItemElement()
    {
        String elementName = mStyleResolver.getCurrentElement().getElementName();

        if (HtmlUtil.isParagraphOrHeadingElement(elementName))
        {
            CssDeclaration importBehaviorDeclaration = mStyleResolver.getElementDeclarations().get(HtmlConstants.IMPORT);
            if ((importBehaviorDeclaration != null) && importBehaviorDeclaration.getValue().equals(CssValue.ListItem))
            {
                return true;
            }
        }

        if (mStyleResolver.getElementDisplayType() == CssDisplayType.LIST_ITEM)
        {
            IElementProvider parentNode = mStyleResolver.getCurrentElement().getParentElement();
            // This is list-item:
            // If it is inside list.
            boolean isInsideList = (getCurrentListReader() != null) && (getCurrentListReader().getCurrentLevelNumber() > -1);

            // If 'li' is inside 'body' and outside list (list will be created).
            boolean isLiOutsideList = ("li".equals(elementName)) &&
                ((getCurrentListReader() == null) || (getCurrentListReader().getCurrentLevelNumber() == -1)) &&
                isBodyElement(parentNode);

            // If it is "display: list-item" element in any block-level element exclude "body".
            boolean isListItemInsideBlock = (!"li".equals(elementName)) &&
                mStyleResolver.parentElementIsBlockLevel() &&
                !isBodyElement(parentNode);

            // WORDSNET-22044 If a list item is inside a list with any CSS display type.
            boolean isListItemInsideList = ("li".equals(elementName)) &&
                ("ol".equals(mStyleResolver.getCurrentElement().getParentElement().getElementName()) ||
                "ul".equals(mStyleResolver.getCurrentElement().getParentElement().getElementName()));

            return isInsideList || isLiOutsideList || isListItemInsideBlock || isListItemInsideList;
        }

        // If it is block-level <li> which has css counter.
        return ("li".equals(elementName)) && isBlockElement() && hasCssCounter();
    }

    /// <summary>
    /// Returns <c>true</c> if the current element has "table-column" or "table-column-group" value in "display" property.
    ///
    /// </summary>
    private boolean isTableColumnOrColumnGroupElement()
    {
        return (mStyleResolver.getElementDisplayType() == CssDisplayType.TABLE_COLUMN) ||
               (mStyleResolver.getElementDisplayType() == CssDisplayType.TABLE_COLUMN_GROUP);
    }

    private static boolean isBodyElement(IElementProvider elementProvider)
    {
        return (elementProvider != null) && ("body".equals(elementProvider.getElementName()));
    }

    /// <summary>
    /// Called for table nodes.
    /// </summary>
    /// <returns><c>false</c> if table is empty and <c>true</c> if table contains rows and columns.</returns>
    private boolean handleTable(HtmlElementNode node) throws Exception
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());

        HtmlTable htmlTable = HtmlTable.create(node, mCssStyleTracker, mSettings.ParseAsXhtml);
        if ((htmlTable.getRowCount() == 0) || (htmlTable.getColumnCount() == 0))
        {
            // HTML reader doesn't create a table if the corresponding HTML table contains only an empty row
            // or only an empty caption. At this case we create bookmarks for the table, empty rows and empty captions.
            mHtmlHyperlinkResolver.addPendingBookmarkIfNeeded(node);

            for (int i = 0; i < htmlTable.getRowCount(); ++i)
            {
                mHtmlHyperlinkResolver.addPendingBookmarkIfNeeded(htmlTable.get(i).getNode());
            }

            for (int i = 0; i < htmlTable.getCaptionCount(); ++i)
            {
                mHtmlHyperlinkResolver.addPendingBookmarkIfNeeded(htmlTable.getCaption(i).getNode());
            }
            return false;
        }

        // If there is an empty paragraph just before the table in the HTML document, we should make sure it remains
        // an empty paragraph in the resulting document and is not used to store the table.
        mParagraphArranger.closeCurrentParagraphIfEmptyInHtml();

        // We should insert empty paragraph before table in empty list item.
        // WORDSNET-27545 Insert an empty paragraph only if the table will actually be created.
        mParagraphArranger.insertEmptyParagraphToListItemIfNeeded();

        mTableNestingLevel++;

        // WORDSNET-19132 A table defines a new context for list numbering.
        mListReaderManager.openNewContext();

        HtmlTableReader tableReader = new HtmlTableReader(
            mBuilder,
            mCssStyleTracker,
            mStyleResolver,
            mCommonBorderResolver,
            this,
            mSettings.ApplyFormattingAsMsWord,
            mHtmlBlockReader,
            mHtmlHyperlinkResolver);
        Table table = tableReader.processTable(htmlTable, mTableNestingLevel == 1, node);

        // Close the list numbering context defined by this table.
        mListReaderManager.closeContext();

        // WORDSNET-7798 Here we have to insert an empty paragraph between adjacent tables
        // or MS Word will concatenate them and change layout.
        mHtmlBlockReader.insertSeparatorParagraphBeforeTable(table);

        mTableNestingLevel--;
        return true;
    }

    /// <summary>
    /// Returns <c>true</c> if the current element has counter in css "content" property.
    /// <remarks>This feature is described in WORDSNET-10850.</remarks>
    /// </summary>
    private boolean hasCssCounter()
    {
        CssDeclaration declaration = mStyleResolver.getBeforePseudoElementDeclarations().get("content");
        if (declaration == null)
            return false;

        CssValue value = declaration.getValue().getFirstValue();

        if (!(value instanceof CssFunctionValue))
            return false;

        CssFunctionValue funcValue = (CssFunctionValue)value;
        return ("counter".equals(funcValue.getName())) || ("counters".equals(funcValue.getName()));
    }

    private void handleBody(boolean isStart) throws Exception
    {
        if (isStart)
        {
            boolean htmlBlockStarted = mHtmlBlockReader.startHtmlBlock(
                mStyleResolver.getCurrentElement().getElementName(),
                mStyleResolver.getElementDeclarations(),
                mStyleResolver.getElementDisplayType());

            // CSS style of <body> affects formatting of the whole document. However, when we insert an HTML fragment
            // into an existing template document, we do not want the fragment to affect formatting of the whole document.
            // WORDSNET-9421 When an HTML fragment is inserted into an existing document, CSS style of <body>
            // affects formatting of the paragraph currently selected in the target document.
            if (mApplyDocumentWideFormatting)
            {
                mStyleResolver.toDocument(mBuilder.getDocument(), getBackgroundImageBytes());
                applyParagraphFormatting();
            }

            mHtmlBlockReader.updateCurrentParagraph(htmlBlockStarted);
        }
        else
        {
            mHtmlBlockReader.endHtmlBlock(
                mStyleResolver.getCurrentElement().getElementName(),
                mStyleResolver.getElementDeclarations(),
                mBidiTextArranger);
        }
    }

    private byte[] getBackgroundImageBytes() throws Exception
    {
        String imageUri = mStyleResolver.getElementDeclarations().getUri("background-image");
        return StringUtil.hasChars(imageUri)
            ? mResourceLoader.loadImage(mBaseUri, imageUri, false)
            : null;
    }

    private void handleRevision(EditRevision revision)
    {
        Debug.assertTrue(revision != null);

        String author = mStyleResolver.getElementDeclarations().getString(HtmlConstants.REVISION_AUTHOR);
        if (!msString.equals(author, ""))
            revision.setAuthor(author);
        String dateTimeStr = mStyleResolver.getElementDeclarations().getString(HtmlConstants.REVISION_DATE_TIME);
        DateTime dateTime = FormatterPal.xmlToDateTime(dateTimeStr);
        if (!DateTime.equals(dateTime, DateTime.MinValue))
            revision.setDateTime(dateTime);
    }

    private void handleInput(HtmlElementNode node) throws Exception
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());
        mParagraphArranger.startParaIfNeeded();
        String type = msString.toLowerInvariant(node.getAttributes().getAttributeValue("type", "text"));
        if ("image".equals(type))
        {
            handleImage(node);
        }
        else
        {
            mHtmlControlReader.handleInput(node, type);
        }
    }

    private void handleSelect(HtmlElementNode node, boolean isStart) throws Exception
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());

        if (isStart)
        {
            mParagraphArranger.startParaIfNeeded();
            Debug.assertTrue(mOptionElements.size() == 0);
        }
        else
        {
            mHtmlControlReader.handleSelect(node, mOptionElements);
            mOptionElements.clear();
        }
    }

    private void handleOption(HtmlElementNode node, boolean isStart)
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());

        if (!isStart)
            return;

        // We can't add item with "disable" option in Form Field "FORMDROPDOWN" and SDT,
        // because item has not same option.
        // In HTML this item is displayed but it could not be selected
        if (node.getAttributes().get("disabled") != null)
            return;

        // IE, Opera display text of "label" in dropdown item if attribute exists
        // Firefox, Chrome always display inner text.
        // http://www.w3.org/TR/1999/REC-html401-19991224/interact/forms.html#adef-label-OPTION
        String label = node.getAttributes().getAttributeValue("label", "");
        String innerText = StringUtil.hasChars(label)
            ? label
            : HtmlUtil.removeWhitespaces(node.getInnerText(), true);

        boolean isSelected = node.getAttributes().get("selected") != null;
        String value = node.getAttributes().getAttributeValue("value", "");

        // This collection will be used for import of 'select' element.
        msArrayList.add(mOptionElements, new HtmlOptionElementInfo(innerText, value, isSelected));
    }

    /// <summary>
    /// Called for text nodes.
    /// </summary>
    private void handleText(String text) throws Exception
    {
        if (mIsIgnoreHyperlinkContent)
            return;

        if (mIsFootnoteReferenceText)
        {
            setFootnoteInfo(text);
            return;
        }

        insertPostponedPageBreakAfterIfNeeded();

        if (mStyleResolver.isPreformatted() ||
            mStyleResolver.isPreformattedWithLine() ||
            mStyleResolver.isPreformattedWithWrap())
        {

            mParagraphArranger.startParaIfNeeded();
            applyFontFormatting(true);

            // Preformatted text can be multi-line, and we process it line-by-line.
            // The HTML parser guarantees that only '\n' characters will split lines of text.
            String[] lines = msString.split(text, '\n');
            for (int i = 0; i < lines.length; i++)
            {
                // Insert additional paragraphs for the second and all the next lines of text.
                if (i > 0)
                {
                    // Each additional paragraph starts a new inline context.
                    mBidiTextArranger.rearrangeAndWriteText();

                    mParagraphArranger.insertParagraphImmediately();
                    mBuilder.getParagraphFormat().clearFormatting();
                    applyParagraphFormatting();
                    // WORDSNET-24755 Set the HTML block for a new paragraph created by multi-lined text.
                    mHtmlBlockReader.updateCurrentParagraph(true);
                    mCommonBorderResolver.collectParagraphWithBorder(mBuilder.getCurrentParagraph(), mStyleResolver.getElementDeclarations());
                }

                // WORDSNET-9333 Lines of preformatted text have zero space between them.
                // We have to manually zero space between the paragraphs containing the lines,
                // but left space before the first paragraph and after the last paragraph unchanged.
                boolean isFirstLine = i == 0;
                if (!isFirstLine)
                {
                    mBuilder.getParagraphFormat().setSpaceBefore(0.0);
                }
                boolean isLastLine = i == (lines.length - 1);
                if (!isLastLine)
                {
                    mBuilder.getParagraphFormat().setSpaceAfter(0.0);
                }

                // Remove control characters, because they are invisible in browsers but may have special meaning
                // in the document model.
                // If the text has the 'white-space:pre-line' style, we should also collapse sequences of whitespace.
                String lineText = lines[i];
                lineText = mStyleResolver.isPreformattedWithLine()
                    ? HtmlUtil.removeControlCharsAndWhitespaces(lineText, isFullLeftTrim())
                    : HtmlUtil.removeControlCharacters(lineText);

                if (StringUtil.hasChars(lineText))
                {
                    writeText(lineText);
                }
            }
        }
        else
        {
            String originalText = text;
            text = HtmlUtil.removeControlCharsAndWhitespaces(text, isFullLeftTrim());
            if (StringUtil.hasChars(text))
            {
                mParagraphArranger.startParaIfNeeded();
                applyFontFormatting(true);
                writeText(text);

                // If we're processing a span that has width specified, we take into account width of text we've just
                // written.
                mFixedWidthSpanReader.processText(text, mBuilder.getFont());
            }
            else if (HtmlUtil.containsAnythingButWhitespaces(originalText, true))
            {
                // WORDSNET-27752 We get here if the text contains whitespace and control characters only. After filtering
                // the text becomes empty, but the paragraph must not be removed, because control characters make it
                // non-empty and visible in HTML.
                mParagraphArranger.markCurrentParagraphAsEmptyInHtml();
            }
        }
    }

    private void setFootnoteInfo(String referenceText) throws Exception
    {
        mStyleResolver.toFont(mBuilder.getFont(), mBuilder.getCurrentParagraph().getParagraphStyle());
        mCurrentFootnote.setRunPrInternal(mBuilder.getRunPrCopy());

        // Here we simply ignore hyperlink formatting for now.
        mCurrentFootnote.getFont().setUnderline(Underline.NONE);
        mCurrentFootnote.getFont().setColor(Color.BLACK);

        if (mCurrentFootnote.isAuto())
        {
            SpecialChar footnoteNumber = new SpecialChar(mBuilder.getDocument(), ControlChar.FOOTNOTE_REF_CHAR, AttrCollectionExtension.deepClone(mCurrentFootnote.getRunPr()));
            footnoteNumber.getFont().setStyleIdentifier(FootnoteUtil.getFootnoteReferenceStyleIdentifier(mCurrentFootnote));
            mCurrentFootnote.getFirstParagraph().prependChild(footnoteNumber);
        }
        else
        {
            referenceText = HtmlUtil.removeWhitespaces(referenceText, isFullLeftTrim());
            mCurrentFootnote.setReferenceMark(referenceText.substring((1), (1) + (referenceText.length() - 2)));

            Run run = new Run(mBuilder.getDocument(), mCurrentFootnote.getReferenceMark(), AttrCollectionExtension.deepClone(mCurrentFootnote.getRunPr()));
            run.getFont().setStyleIdentifier(FootnoteUtil.getFootnoteReferenceStyleIdentifier(mCurrentFootnote));
            mCurrentFootnote.getFirstParagraph().prependChild(run);
        }
    }

    /// <summary>
    /// Writes inline text. Adjacent inline text is rearranged according to the Unicode bidirectional algorithm.
    /// </summary>
    /// <param name="text">Text to write.</param>
    private void writeText(String text) throws Exception
    {
        Debug.assertTrue(StringUtil.hasChars(text));

        boolean dontCollapseLastSpace = false;
        CssDeclaration importBehaviorDeclaration = mStyleResolver.getElementDeclarations().get(HtmlConstants.IMPORT);
        if (importBehaviorDeclaration != null)
        {
            // Spans with "space-replacement" role are used to preserve a sequence of spaces in HTML so they are converted
            // back on import. These spans contain non-break spaces that should be replaced on regular space chars.
            if (importBehaviorDeclaration.getValue().equals(CssValue.Spaces) && isMatchedWithSpaceReplacementPattern(text))
            {
                text = msString.newString(' ', text.length());
                dontCollapseLastSpace = true;
            }
        }

        RunPr baseRunPr = mBuilder.getRunPrCopy();

        // WORDSNET-13782 Characters 'NON-BREAKING HYPHEN' (U+2011) and 'SOFT HYPHEN' (U+00AD) have special
        // internal representations in Word. If we don't replace these characters on import, they will be rendered
        // incorrectly (with wrong glyphs and/or fonts) because of font fallback.
        text = msString.replace(text, ControlChar.UNICODE_NON_BREAKING_HYPHEN_CHAR, ControlChar.NON_BREAKING_HYPHEN_CHAR);
        text = msString.replace(text, ControlChar.UNICODE_OPTIONAL_HYPHEN_CHAR, ControlChar.OPTIONAL_HYPHEN_CHAR);

        // WORDSNET-10430 MS Word 2010 and older fail to use a fallback font when the run font contains no glyphs
        // for some characters. In this case, we have to choose fallback fonts ourselves and use it instead of the run font.
        HtmlFontFallbackRange[] fontFallbackRanges = mHtmlFontFallbackUtil.getFontFallbackRanges(text, mBuilder.getFont());
        for (HtmlFontFallbackRange fontFallbackRange : fontFallbackRanges)
        {
            RunPr runPr = baseRunPr;

            // Replace the run font with a fallback font if needed.
            if (fontFallbackRange.getNeedsFontFallback())
            {
                runPr = AttrCollectionExtension.deepClone(runPr);
                Font font = Font.makeFont(runPr, mBuilder.getDocument());
                font.setName(fontFallbackRange.getFallbackFontName());
            }

            if (mRubyReader != null)
            {
                mRubyReader.writeText(fontFallbackRange.getText(), runPr, mStyleResolver.getActiveBidiLevels(),
                    dontCollapseLastSpace);
            }
            else
            {
                // WORDSNET-16568 If an inline HTML element (except for <a> elements) has an 'id' attribute,
                // we should create a bookmark before the text of that element.
                Bookmark bookmark = null;
                if ((mStyleResolver.getElementDisplayType() == CssDisplayType.INLINE) &&
                    (!"a".equals(mStyleResolver.getCurrentElement().getElementName())))
                {
                    // Since, the inline HTML elements will be written later we just pass a new bookmark to the bidi
                    // text arranger. The newly created bookmark is inserted into the current paragraph but it will be moved
                    // before the corresponding run when bookmarks and hyperlinks are processed.
                    BookmarkStart bookmarkStart = mHtmlHyperlinkResolver.startInlineBookmarkIfNeeded(mStyleResolver.getCurrentElement());
                    BookmarkEnd bookmarkEnd = mHtmlHyperlinkResolver.endInlineBookmarkIfNeeded(mStyleResolver.getCurrentElement());
                    if ((bookmarkStart != null) && (bookmarkEnd != null))
                        bookmark = new Bookmark(bookmarkStart, bookmarkEnd);
                }

                // The text is not written immediately. Instead, it is appended to the bidirectional text arranger.
                // Text from adjacent inline elements is processed as a whole at the end of the paragraph.
                mBidiTextArranger.append(fontFallbackRange.getText(), runPr, mStyleResolver.getActiveBidiLevels(),
                   dontCollapseLastSpace, bookmark);
            }
        }
    }

    private static boolean isMatchedWithSpaceReplacementPattern(String text)
    {
        if ((text.length() == 1) && (msString.equals(text, ControlChar.NON_BREAKING_SPACE)))
            return true;

        if (text.charAt(text.length() - 1) != ' ')
            return false;

        for (int i = 0; i < text.length() - 1; i++)
        {
            if (text.charAt(i) != ControlChar.NON_BREAKING_SPACE_CHAR)
                return false;
        }

        return true;
    }

    /// <summary>
    /// Writes a Unicode direction mark (LRM or RLM) according to the current inline direction.
    /// </summary>
    private void writeDirectionMark()
    {
        // The text is not written immediately. Instead, it is appended to the bidirectional arranger.
        // Text from adjacent inline elements is processed as a whole at the end of the paragraph.
        mBidiTextArranger.appendDirectionMark(mStyleResolver.getActiveBidiLevels());
    }

    private boolean isFullLeftTrim()
    {
        // We need to check preceding inline text. The text can still be in any of inline buffers or it can be written
        // to the document already.

        // Examine the ruby reader buffer if it contains any text.
        if ((mRubyReader != null) && (!mRubyReader.isEmpty()))
        {
            return mRubyReader.collapseWhitespaceAfterText();
        }

        // Examine the bidi buffer if it is not empty.
        if (!mBidiTextArranger.isEmpty())
        {
            return mBidiTextArranger.collapseWhitespaceAfterText();
        }

        // If all buffers are empty, preceding inline text (if any) has already been written to the document,
        // and we need to examine the document nodes.
        if (mBidiTextArranger.isEmpty() && ((mRubyReader == null) || mRubyReader.isEmpty()))
        {
            // We only remove leading whitespace completely if:
            return mParagraphArranger.isParaStartNeeded() || // this text is not in a block and will start a new paragraph
                mBuilder.isAtStartOfParagraph() || // the previous character is paragraph break
                HtmlUtil.isParagraphHasOnlyFloatingShapes(mBuilder.getCurrentParagraph()) ||
                isLastCharacterSpaceOrBR(); // the previous character is space or line break
        }

        // No inline text has been written yet so there is no preceding space and full left trim is not required
        // for the text being processed.
        return false;
    }

    private boolean isLastCharacterSpaceOrBR()
    {
        Node curNode = mBuilder.getCurrentNode();
        Paragraph curPara = mBuilder.getCurrentParagraph();

        Run lastRun = null;
        boolean usePrevLastNode = (mLastNodeInCurParagraph != null) && (curPara == mLastNodeInCurParagraph.getParentNode());
        Node startNode = usePrevLastNode
            ? mLastNodeInCurParagraph
            : curPara.getFirstChild();

        Node lastNode = null;
        for (Node node = startNode; node != curNode; node = node.nextPreOrder(curPara))
        {
            lastNode = node;
            if (node.getNodeType() == NodeType.RUN)
                lastRun = (Run)node;
        }

        // WORDSNET-2936 White space after IMG tag is missed upon inserting HTML.
        // In case an image is contained in the middle of runs
        // the full left trim must not be applied to the run after the image.
        if ((lastNode != null) && (lastNode.getNodeType() == NodeType.SHAPE))
            return false;

        mLastNodeInCurParagraph = lastRun;

        boolean result = false;
        if (lastRun != null)
        {
            String text = lastRun.getText();
            if (StringUtil.hasChars(text))
            {
                char lastChar = text.charAt(text.length() - 1);
                result = (lastChar == ControlChar.SPACE_CHAR) || (lastChar == ControlChar.LINE_BREAK_CHAR) ||
                    (lastChar == ControlChar.PAGE_BREAK_CHAR) || (lastChar == ControlChar.COLUMN_BREAK_CHAR);
            }
        }

        return result;
    }

    private void handleDiv(HtmlElementNode node, boolean isStart) throws Exception
    {
        // I don't remove spaces before after DIV because some tests,
        // for example TestNoCss.html do not look good.
        // It is a bit tricky to deal with DIV and spaces, will research later.

        if (isElementIgnoredOnRoundtrip())
            return;

        if (handleCommentDiv(node, isStart))
            return;

        if (handleFootnoteDiv(mFootnotes, HtmlConstants.FOOTNOTE_ID_PREFIX, node, isStart))
            return;

        if (handleFootnoteDiv(mEndnotes, HtmlConstants.ENDNOTE_ID_PREFIX, node, isStart))
            return;

        if (handleHeaderFooterDiv(node, isStart))
            return;

        if (handleStructuredDocumentTagNode(node, isStart))
            return;

        if (isStart)
        {
            // CurrentStory can be null, for example, when we read contents of a text box imported from VML.
            // WORDSNET-21618 CurrentSection can be null when the HTML document has more than one footer or header
            // of the same type. In that case, the HTML reader discards all duplicate headers and footers.
            // WORDSNET-25722 We shouldn't apply formatting to the current section if we're inserting HTML into a header
            // or footer node. We're applying it only if we're inserting into the body (main text) story.
            Body body = ms.as(mBuilder.getCurrentStory(), Body.class);
            if ((body != null) && (body.getParentSection() != null))
            {
                mStyleResolver.toSection(body.getParentSection());
            }

            mCommonBorderResolver.startContainer(mStyleResolver.getElementDeclarations());
            mParagraphArranger.startPara();
            mHtmlBlockReader.startHtmlBlock(
                mStyleResolver.getCurrentElement().getElementName(),
                mStyleResolver.getElementDeclarations(),
                mStyleResolver.getElementDisplayType());

            // WORDSNET-7919 Some DIV elements are used instead of paragraph (P) elements and contain plain text.
            // Apply CSS formatting to paragraphs created for such DIVs.
            applyParagraphFormatting();

            mHtmlBlockReader.updateCurrentParagraph(true);

            mCommonBorderResolver.collectParagraphWithoutBorder(mBuilder.getCurrentParagraph());
            insertPageBreakBefore();
        }
        else
        {
            mCommonBorderResolver.endContainer();

            // WORDSNET-13137 Div is not imported into Aspose.Words DOM.
            // The empty div with non-zero height can be imported as
            // an empty paragraph. MSW omits empty divs in this case.
            if (isDivEmpty(node))
            {
                double height = mStyleResolver.getElementDeclarations().getLength("height");
                double minHeight = mStyleResolver.getElementDeclarations().getLength("min-height");
                double maxHeight = Math.max(height, minHeight);
                if (maxHeight > 0)
                {
                    mBuilder.getFont().setSize(maxHeight);
                    mParagraphArranger.insertParagraphImmediately();
                }
            }

            mParagraphArranger.endPara();
            insertPageBreakAfter();

            mHtmlBlockReader.endHtmlBlock(
                mStyleResolver.getCurrentElement().getElementName(),
                mStyleResolver.getElementDeclarations(),
                mBidiTextArranger);
        }
    }

    private boolean isDivEmpty(HtmlElementNode node)
    {
        if (node.getChildren().getCount() == 0)
            return true;

        if ((node.getChildren().getCount() == 1) && (node.getChildren().get(0) instanceof HtmlTextNode))
        {
            String text = ((HtmlTextNode)node.getChildren().get(0)).getText();
            text = HtmlUtil.removeControlCharsAndWhitespaces(text, isFullLeftTrim());
            return msString.isNullOrEmpty(text);
        }
        return false;
    }

    private void handleDescriptionList(boolean isStart) throws Exception
    {
        if (isStart)
        {
            mCommonBorderResolver.startContainer(mStyleResolver.getElementDeclarations());
            mParagraphArranger.startPara();
            // WORDSNET-7919 Some DIV elements are used instead of paragraph (P) elements and contain plain text.
            // Apply CSS formatting to paragraphs created for such DIVs.
            applyParagraphFormatting();
            mCommonBorderResolver.collectParagraphWithoutBorder(mBuilder.getCurrentParagraph());
        }
        else
        {
            mCommonBorderResolver.endContainer();
            mParagraphArranger.endPara();
        }
    }

    /// <summary>
    /// Inserts page breaks according to CSS "page-break-before" property.
    /// </summary>
    private void insertPageBreakBefore()
    {
        CssDeclaration pageBreakBefore = mStyleResolver.getElementDeclarations().get("page-break-before");
        if (pageBreakBefore == null)
            return;

        // "page-break-before: always" is already handled in CssPageBreakBeforePropertyDef.ToParagraphFormat.
        if (pageBreakBefore.getValue().equals(CssValue.Auto))
        {
            mBuilder.insertBreakCore(BreakType.SECTION_BREAK_CONTINUOUS, false);
        }
        else if (pageBreakBefore.getValue().equals(CssValue.Left))
        {
            mBuilder.insertBreakCore(BreakType.SECTION_BREAK_EVEN_PAGE, false);
        }
        else if (pageBreakBefore.getValue().equals(CssValue.Right))
        {
            mBuilder.insertBreakCore(BreakType.SECTION_BREAK_ODD_PAGE, false);
        }
        else if (pageBreakBefore.getValue().equals(CssValue.Avoid))
        {
            Node prevNode = mBuilder.getCurrentParagraph().getPreviousNonAnnotationSibling();
            if (prevNode instanceof Paragraph)
                ((Paragraph)prevNode).getParagraphFormat().setKeepWithNext(true);
        }
    }

    /// <summary>
    /// Inserts page breaks according to CSS "page-break-after" property.
    /// </summary>
    private void insertPageBreakAfter()
    {
        CssDeclaration pageBreakAfter = mStyleResolver.getElementDeclarations().get("page-break-after");
        if (pageBreakAfter == null)
            return;

        // "page-break-after: avoid" is already handled in CssPageBreakAfterPropertyDef.ToParagraphFormat.
        if (pageBreakAfter.getValue().equals(CssValue.Auto))
            mBuilder.insertBreakCore(BreakType.SECTION_BREAK_CONTINUOUS, false);
        if (pageBreakAfter.getValue().equals(CssValue.Always))
        {
            // WORDSNET-12102 Extra blank page at the end of a document imported from HTML.
            // Most browsers don't create a new page after the last block of the document event
            // if it has the 'page-break-after:always' style. Page break is inserted only in case
            // there is an opening tag or text after the break.
            mPostponedPageBreakAfterAlways = true;
        }
        else if (pageBreakAfter.getValue().equals(CssValue.Left))
        {
            mBuilder.insertBreakCore(BreakType.SECTION_BREAK_EVEN_PAGE, false);
        }
        else if (pageBreakAfter.getValue().equals(CssValue.Right))
        {
            mBuilder.insertBreakCore(BreakType.SECTION_BREAK_ODD_PAGE, false);
        }
    }

    private void insertPostponedPageBreakAfterIfNeeded()
    {
        if (mPostponedPageBreakAfterAlways)
        {
            mBuilder.insertBreakCore(BreakType.SECTION_BREAK_NEW_PAGE, false);
            mPostponedPageBreakAfterAlways = false;
        }
    }

    private boolean handleCommentDiv(HtmlElementNode node, boolean isStart)
    {
        String divId = node.getAttributes().getAttributeValue("id", "");
        if (msString.startsWith(divId, HtmlConstants.COMMENT_ID_PREFIX, StringComparison.ORDINAL))
        {
            int commentId = tryParseCommentIdNumber(divId);
            if (commentId <= 0)
            {
                if (isStart)
                {
                    warn(WarningType.MAJOR_FORMATTING_LOSS,
                        msString.format(
                            "Can't parse comment id from '{0}'. Positive integer is expected after prefix '{1}'. " +
                            "Comment's content has been treated as regular document's subtree.",
                            divId, HtmlConstants.COMMENT_ID_PREFIX));
                }

                return true;
            }

            if (mComments.get(commentId) == null)
            {
                if (isStart)
                {
                    warn(WarningType.MAJOR_FORMATTING_LOSS,
                        msString.format("There is no comment with comment id '{0}'. Comment's content has been treated as regular document's subtree.",
                        commentId));
                }

                return true;
            }

            if (isStart)
            {
                Comment currentComment = mComments.get(commentId);
                String author = mStyleResolver.getElementDeclarations().getString(HtmlConstants.COMMENT_AUTHOR);
                String initial = mStyleResolver.getElementDeclarations().getString(HtmlConstants.COMMENT_INITIAL);
                String dateTimeStr = mStyleResolver.getElementDeclarations().getString(HtmlConstants.COMMENT_DATE_TIME);
                if ((msString.equals(author, "")) && (msString.equals(initial, "")) && (msString.equals(dateTimeStr, "")))
                {
                    // Try to read roundtrip information saved in old data-* format.
                    author = node.getAttributes().getAttributeValue(HtmlConstants.COMMENT_AUTHOR_DATA_ATTRIBUTE, "");
                    initial = node.getAttributes().getAttributeValue(HtmlConstants.COMMENT_INITIAL_DATA_ATTRIBUTE, "");
                    dateTimeStr = node.getAttributes().getAttributeValue(HtmlConstants.COMMENT_DATE_TIME_DATA_ATTRIBUTE, "");
                }

                currentComment.setAuthor(author);
                currentComment.setInitial(initial);
                DateTime dateTime = FormatterPal.xmlToDateTime(dateTimeStr);
                if (!DateTime.equals(dateTime, DateTime.MinValue))
                    currentComment.setLocalDateTime(dateTime);

                int parentCommentId = tryParseCommentIdNumber(mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.COMMENT_PARENT_ID));
                if (parentCommentId > 0)
                {
                    Comment parent = mComments.get(parentCommentId);
                    // Protect against references to unknown comments and references to self.
                    if ((parent != null) && (parent.getId() != currentComment.getId()))
                    {
                        currentComment.setParentId(parent.getId());
                    }
                }

                boolean isDone = HtmlConstants.COMMENT_DONE_YES.equals(mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.COMMENT_DONE));
                currentComment.setDone(isDone);

                mParagraphArranger.rememberCurrentParagraphAndMoveTo(currentComment.getFirstParagraph());
            }
            else
            {
                mParagraphArranger.restoreCurrentParagraphIfNeeded();
            }

            return true;
        }

        return false;
    }

    /// <summary>
    /// Tries to parse a positive integer number from a comment id like '_cmnt123'.
    /// In case of an error, returns <see cref="int.MinValue"/>.
    /// </summary>
    private static int tryParseCommentIdNumber(String commentId)
    {
        if (StringUtil.hasChars(commentId) && (commentId.length() > HtmlConstants.COMMENT_ID_PREFIX.length()))
        {
            String numberSubstring = commentId.substring(HtmlConstants.COMMENT_ID_PREFIX.length());
            int number = FormatterPal.tryParseInt(numberSubstring);
            if (number > 0)
            {
                return number;
            }
        }
        return Integer.MIN_VALUE;
    }

    private boolean handleFootnoteDiv(IntToObjDictionary<Footnote> footnotes, String footnoteIdPrefix, HtmlElementNode node, boolean isStart)
    {
        String divId = node.getAttributes().getAttributeValue("id", "");
        if (msString.startsWith(divId, footnoteIdPrefix, StringComparison.ORDINAL))
        {
            int footnoteId = FormatterPal.tryParseInt(divId.substring(footnoteIdPrefix.length()));
            if ((footnoteId == Integer.MIN_VALUE) || (footnoteId <= 0))
            {
                if (isStart)
                {
                    warn(WarningType.MAJOR_FORMATTING_LOSS,
                        msString.format(
                            "Can't parse footnote id from '{0}'. Positive integer is expected after prefix '{1}'. " +
                            "Footnotes's content has been treated as regular document's subtree.",
                            divId, footnoteIdPrefix));
                }

                return true;
            }

            Footnote footnote = footnotes.get(footnoteId);
            if (footnote == null)
            {
                if (isStart)
                {
                    warn(WarningType.MAJOR_FORMATTING_LOSS,
                        msString.format(
                            "There is no footnote with footnote id '{0}'. Footnote's content has been treated as regular document's subtree.",
                            footnoteId));
                }

                return true;
            }

            if (isStart)
            {
                mCurrentFootnote = footnote;

                double footnoteIsAutoValue = mStyleResolver.getElementDeclarations().getNumber(HtmlConstants.FOOTNOTE_IS_AUTO);
                int footnoteIsAuto = MathUtil.isMinValue(footnoteIsAutoValue)
                    ? node.getAttributes().getAttributeValue(HtmlConstants.FOOTNOTE_IS_AUTO_ATTRIBUTE, -1)
                    : (int)footnoteIsAutoValue;

                if ((footnoteIsAuto != 0) && (footnoteIsAuto != 1))
                {
                    warn(WarningType.MINOR_FORMATTING_LOSS,
                        msString.format(
                            "Can't determine if footnote with footnote id '{0}' is auto. Footnote has been treated as having custom mark.",
                            footnoteId));
                    mCurrentFootnote.isAuto(false);
                }
                else
                {
                    mCurrentFootnote.isAuto(footnoteIsAuto != 0);
                }

                mParagraphArranger.rememberCurrentParagraphAndMoveTo(mCurrentFootnote.getFirstParagraph());
            }
            else
            {
                mCurrentFootnote = null;
                mParagraphArranger.restoreCurrentParagraphIfNeeded();
            }

            return true;
        }

        return false;
    }

    /// <summary>
    /// Returns a value indicating whether the HTML element being processed represents a header/footer that
    /// cannot be imported into the document model.
    /// </summary>
    private boolean isIgnoredHeaderFooterDiv(HtmlElementNode node, boolean isStart)
    {
        // This method processes only opening div tags.
        if (!isStart)
        {
            return false;
        }

        // Divs that represent linked headers/footers must be ignored. In the document model, if a header/footer is null,
        // it is inherited from the previous section by default (becomes linked).
        String headerFooterTypeValue = getHeaderFooterTypeValue(node);
        if (HtmlConstants.HEADER_FOOTER_TYPE_LINKED.equals(headerFooterTypeValue))
        {
            return true;
        }

        // If we cannot parse header/footer type, it is not a header/footer div.
        /*HeaderFooterType*/int headerFooterType = 0;
        RefInt referenceToHeaderFooterType = new RefInt(headerFooterType);
        boolean outRefCondition0 = !tryParseHeaderFooterType(headerFooterTypeValue, /*out*/ referenceToHeaderFooterType);
        headerFooterType = referenceToHeaderFooterType.get();
        if (outRefCondition0)
        {
            return false;
        }

        // WORDSNET-25722 We must process headers and footers only if we're inserting into the document's body (main text)
        // story. If we're inserting into any other story (for example, into a existing header or a footer), we must ignore
        // header/footer divs declared in HTML.
        // Note that CurrentStory may also be null here.
        Body body = ms.as(mBuilder.getCurrentStory(), Body.class);
        if (body == null)
        {
            return true;
        }

        Section currentSection = body.getParentSection();
        if (currentSection == null)
        {
            return true;
        }

        // If there is already another header/footer of this type in the current section, the header/footer div
        // must be discarded.
        boolean isDuplicate = currentSection.getHeadersFooters().getByHeaderFooterType(headerFooterType) != null;
        if (isDuplicate)
        {
            String warning = msString.format(
                "A duplicate header/footer of type '{0}' has been ignored.",
                headerFooterTypeValue);
            warn(WarningType.DATA_LOSS, warning);
        }
        return isDuplicate;
    }

    /// <summary>
    /// Parses &lt;div&gt; elements containing document headers and footers.
    /// </summary>
    /// <returns>
    /// <c>true</c> if the element was parsed as a header or a footer.
    /// <c>false</c> if the element is a regular &lt;div&gt; (contains neither a header nor a footer) and should be parsed
    /// elsewhere.
    /// </returns>
    /// <remarks>
    /// This method does not handle linked headers and footers, which are duplicated during export to HTML.
    /// </remarks>
    private boolean handleHeaderFooterDiv(HtmlElementNode node, boolean isStart)
    {
        String headerFooterTypeValue = getHeaderFooterTypeValue(node);
        /*HeaderFooterType*/int headerFooterType = 0;
        RefInt referenceToHeaderFooterType = new RefInt(headerFooterType);
        boolean outRefCondition1 = !tryParseHeaderFooterType(headerFooterTypeValue, /*out*/ referenceToHeaderFooterType);
        headerFooterType = referenceToHeaderFooterType.get();
        if (outRefCondition1)
            return false;

        if (isStart)
        {
            // A header (footer) instance is created here, but it will be inserted into the document only after
            // the whole header (footer) is parsed. This allows to simply discard linked headers (footers).
            HeaderFooter headerFooter = new HeaderFooter(mBuilder.getDocument(), headerFooterType);
            headerFooter.appendChild(new Paragraph(mBuilder.getDocument()));
            mBuilder.getCurrentSection().getHeadersFooters().add(headerFooter);

            // During export to HTML, the user decided that first-page header and footer were more important than primary
            // ones. We reflect this decision in the imported document.
            String differentFirstPageValue = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.HEADER_FOOTER_DIFFERENT_FIRST_PAGE);
            if ((HtmlConstants.HEADER_FOOTER_DIFFERENT_FIRST_PAGE_TRUE.equals(differentFirstPageValue)) ||
                (headerFooterType == HeaderFooterType.HEADER_FIRST) || (headerFooterType == HeaderFooterType.FOOTER_FIRST))
            {
                mBuilder.getCurrentSection().getSectPr().setDifferentFirstPageHeaderFooter(true);
            }

            mParagraphArranger.rememberCurrentParagraphAndMoveTo(headerFooter.getFirstParagraph());
        }
        else
        {
            mParagraphArranger.restoreCurrentParagraphIfNeeded();
        }

        return true;
    }

    /// <summary>
    /// Called for simple block level elements. Starts new paragraph when needed and applies formatting.
    /// </summary>
    private void handleParagraph(boolean isStart) throws Exception
    {
        if (isStart)
        {
            mParagraphArranger.startPara();

            boolean htmlBlockStarted = mHtmlBlockReader.startHtmlBlock(
                mStyleResolver.getCurrentElement().getElementName(),
                mStyleResolver.getElementDeclarations(),
                mStyleResolver.getElementDisplayType());

            applyParagraphFormatting();

            mCommonBorderResolver.collectParagraphWithBorder(
                        mBuilder.getCurrentParagraph(),
                        mStyleResolver.getElementDeclarations());

            mHtmlBlockReader.updateCurrentParagraph(htmlBlockStarted);
        }
        else
        {
            mParagraphArranger.endPara();
            mHtmlBlockReader.endHtmlBlock(
                mStyleResolver.getCurrentElement().getElementName(),
                mStyleResolver.getElementDeclarations(),
                mBidiTextArranger);
        }
    }

    /// <summary>
    /// Applies font formatting from CSS styles to the document builder font.
    /// </summary>
    void applyFontFormatting(boolean allowTextDecoration) throws Exception
    {
        // When we are at the end of the current paragraph or it is empty, setting its font also affects the paragraph break font.
        // We do not want to change the paragraph break font, because it was formatted correctly when the paragraph was
        // inserted, so we save the paragraph break font and restore it later.
        RunPr savedParagraphBreakRunPr = (mBuilder.isAtEndOfParagraph() || mBuilder.getCurrentParagraph().isEmptyOrContainsOnlyCrossAnnotation())
                                                        ? AttrCollectionExtension.deepClone(mBuilder.getCurrentParagraph().getParagraphBreakRunPr())
                                                        : null;

        // Start building new font formatting upon the base font formatting.
        mBuilder.setFont(mBaseRunPr, true);

        // WORDSNET-12199 If style without CSS declarations was applied we should expand style's RunPr properties to current RunPr.
        if (mStyleResolver.getStyleWithoutDeclarations() != null)
        {
            RunPr runPr = mBuilder.getRunPrCopy();
            mStyleResolver.getStyleWithoutDeclarations().getRunPr().expandTo(runPr);
            mBuilder.setFont(runPr, false);
        }

        // Apply CSS formatting.
        mStyleResolver.toFont(mBuilder.getFont(), mBuilder.getCurrentParagraph().getParagraphStyle());

        if (mStyleResolver.getElementDisplayState() != HtmlElementDisplayState.VISIBLE)
        {
            mBuilder.getFont().setHidden(true);
        }

        // Revisions.
        EditRevision insertionRevision = mStyleResolver.getElementInsertionRevision();
        if (insertionRevision != null)
        {
            ((IRunAttrSource)mBuilder).setRunAttr(RevisionAttr.INSERT_REVISION, insertionRevision);
        }
        EditRevision deletionRevision = mStyleResolver.getElementDeletionRevision();
        if (deletionRevision != null)
        {
            ((IRunAttrSource)mBuilder).setRunAttr(RevisionAttr.DELETE_REVISION, deletionRevision);
        }

        // CSS rules disallow text decoration on inline elements that are not text or whitespace (for example, images).
        // However, we apply text decoration to that elements if the user explicitly asks us to do so.
        if (!allowTextDecoration)
        {
            mBuilder.getFont().setUnderline(Underline.NONE);
            mBuilder.getFont().setStrikeThrough(false);
        }

        // WORDSNET-12924 Like MS Word, we enable kerning for text imported with the 'Heading 1' style. Because
        // there is no CSS counterpart for kerning, we have to apply it directly.
        if (!mUseDocumentBuilderFormatting &&
            !mSettings.ApplyFormattingAsMsWord &&
            (mBuilder.getParagraphFormat().getStyleIdentifier() == StyleIdentifier.HEADING_1))
        {
            mBuilder.getFont().setKerning(18.0);
        }

        // If font formatting of the target document should not affect formatting of text imported from HTML,
        // we have to override target font formatting, and we do so by collapsing direct font formatting
        // with default paragraph formatting and with formatting specified by paragraph and font styles.
        // After collapse:
        // 1. All formatting specified by styles that is duplicated in direct formatting will be removed from
        //    direct formatting.
        // 2. All formatting specified by styles that is not present in direct formatting will be explicitly reset
        //    to default in direct formatting.
        if (!mUseDocumentBuilderFormatting && !mSettings.ApplyFormattingAsMsWord)
        {
            RunPr styleRunPr = mBuilder.getParagraphFormat().getStyle().getExpandedRunPr(RunPrExpandFlags.DOCUMENT_DEFAULTS);
            mBuilder.getFont().getStyle().expandRunPr(styleRunPr, RunPrExpandFlags.NORMAL);
            RunPr uncollapsedDirectRunPr = mBuilder.getRunPrCopy();

            RunPr collapsedDirectRunPr = mBuilder.getRunPrCopy();
            collapsedDirectRunPr.themeColorInheritanceHack(styleRunPr);
            collapsedDirectRunPr.collapse(styleRunPr, FontAttr.ISTD);

            // WORDSNET-21435 Toggle attributes didn't resolve correctly if they were defined both in
            // Character style and Paragraph (or base) style, because values from different style levels are processed
            // in a special way for toggle attributes: instead of overriding each other, they are XORed together.
            // For details, see ECMA-376-1:2016, section 17.7.3 "Toggle properties".
            // Since the direct value of an attribute always overrides the value inherited from styles or document default,
            // we preserve values of toggle attributes in direct formatting (don't collapse them) in case styles or document
            // defaults also define a value for that attribute.
            // The fix is applied if the text has non-default character style, because we don't support table styles yet
            // and toggle properties can only be inherited from paragraph style or character style. The problematic case
            // is when both paragraph style and character style set the toggle property value to "true". If the character
            // style is default, it sets toggle property values to "false" and that causes no problem.
            if (collapsedDirectRunPr.getIstd() != StyleIndex.DEFAULT_PARAGRAPH_FONT)
            {
                for (int toggleAttribute : RunPr.TOGGLE_ATTRIBUTES)
                {
                    Object attributeValue = uncollapsedDirectRunPr.getDirectAttr(toggleAttribute);
                    if ((attributeValue != null) && !collapsedDirectRunPr.containsKey(toggleAttribute))
                    {
                        collapsedDirectRunPr.setAttr(toggleAttribute, attributeValue);
                    }
                }
            }

            mBuilder.setFont(collapsedDirectRunPr, false);
        }

        // Preserve the paragraph break font if needed. See the comment above, where the paragraph break font is saved.
        if (savedParagraphBreakRunPr != null)
        {
            mBuilder.getCurrentParagraph().setParagraphBreakRunPr(savedParagraphBreakRunPr);
        }
    }

    /// <summary>
    /// Applies paragraph formatting from CSS styles to the current paragraph.
    /// </summary>
    void applyParagraphFormatting() throws Exception
    {
        // Here we indent paragraphs created from <li> elements and also reindent the paragraphs if an <li> element contains
        // other block-level elements (for example, <p> - see WORDSNET-11567).
        if ((getCurrentListReader() != null) && (!mSettings.ApplyFormattingAsMsWord))
        {
            getCurrentListReader().indentCurrentParagraphAsListItemIfNeeded();
        }

        // Apply CSS formatting.
        mStyleResolver.toParagraphFormat(mBuilder.getCurrentParagraph());

        // WORDSNET-7347 visually ordered RTL paragraphs are declared as LTR paragraphs in HTML.
        if (mTextIsInVisualOrder)
        {
            switch (mBuilder.getParagraphFormat().getAlignment())
            {
                case ParagraphAlignment.LEFT:
                    mBuilder.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
                    break;
                case ParagraphAlignment.RIGHT:
                    mBuilder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
                    break;
                default:
                    // Ignore.
                    break;
            }
            mBuilder.getParagraphFormat().setBidi(true);
        }

        // If paragraph formatting of the target document should not affect formatting of text imported from HTML,
        // we have to override target paragraph formatting, and we do so by collapsing direct paragraph formatting
        // with formatting specified by paragraph style and with default paragraph formatting.
        // After this:
        // 1. All formatting specified by styles and duplicated in direct formatting will be removed from
        //    direct formatting.
        // 2. All formatting specified by styles and not present in direct formatting will be explicitly reset
        //    to default in direct formatting.
        if (!mUseDocumentBuilderFormatting &&
            !mSettings.ApplyFormattingAsMsWord)
        {
            ParaPr styleParaPr = mBuilder.getParagraphFormat().getStyle().getExpandedParaPr(ParaPrExpandFlags.DOCUMENT_DEFAULTS);
            ParaPr directParaPr = mBuilder.getParaPrCopy();
            directParaPr.collapse(styleParaPr, ParaAttr.ISTD);
            mBuilder.getCurrentParagraph().setParaPr(directParaPr);
        }
    }

    /// <summary>
    /// This is called by the table reader.
    /// </summary>
    /// <remarks>
    /// It's not very nice that part of table processing happens inside table reader and part here,
    /// but I don't see what I can move to make it all happen in one place.
    /// </remarks>
    private void handleCell(boolean isStart) throws Exception
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());

        // Ignore cell tags outside table.
        if (mTableNestingLevel == 0)
            return;

        if (isStart)
        {
            //In reality, whole StartPara and EndPara are not needed here, only need to raise
            //the flag that paragraph start is required.
            mParagraphArranger.startPara();
            applyParagraphFormatting();
        }
        else
        {
            mParagraphArranger.endPara();
        }
    }

    /// <summary>
    /// Handles A element to create a bookmark and/or hyperlink.
    /// </summary>
    private void handleAnchor(HtmlElementNode node, boolean isStart) throws Exception
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());

        // WORDSNET-27579 Inline-level <a> elements may map to the "Hyperlink" style so their style needs to be processed
        // and remembered for later use by child elements.
        if (isInlineElement())
        {
            if (isStart)
            {
                mStyleResolver.handleSpanStart();
            }
            else
            {
                mStyleResolver.handleSpanEnd();
            }
        }

        //It could be a bookmark or a hyperlink.
        String href = HtmlUtil.validateUri(node.getAttributes().getAttributeValue("href", ""));
        if (isStart)
            mHtmlHyperlinkResolver.addHyperlink(href);

        if ((!msString.equals(href, "")) && !UriUtil.isSubAddressOnly(href))
            href = UriUtil.constructAbsoluteUri(mBaseUri, href);
        String bookmarkName = node.getAttributes().getAttributeValue("name", "");

        // If no "name" specified try "id". "name" is marked as obsolete but used by MS Word.
        if (!StringUtil.hasChars(bookmarkName))
        {
            bookmarkName = node.getAttributes().getAttributeValue("id", "");
        }

        if (StringUtil.hasChars(bookmarkName) && (mSettings.HyperlinkProcessor != null))
        {
            bookmarkName = mSettings.HyperlinkProcessor.mapBookmarkName(bookmarkName);
        }

        if (handleCommentAnchor(bookmarkName, href, isStart))
            return;

        if (handleFootnoteAnchor(
            FootnoteType.FOOTNOTE,
            mFootnotes,
            HtmlConstants.FOOTNOTE_REFERENCE_ID_PREFIX,
            HtmlConstants.FOOTNOTE_HYPERLINK_HREF_PREFIX,
            HtmlConstants.FOOTNOTE_REFERENCE_HYPERLINK_HREF_PREFIX,
            bookmarkName,
            href,
            isStart))
        {
            return;
        }

        if (handleFootnoteAnchor(
            FootnoteType.ENDNOTE,
            mEndnotes,
            HtmlConstants.ENDNOTE_REFERENCE_ID_PREFIX,
            HtmlConstants.ENDNOTE_HYPERLINK_HREF_PREFIX,
            HtmlConstants.ENDNOTE_REFERENCE_HYPERLINK_HREF_PREFIX,
            bookmarkName,
            href,
            isStart))
        {
            return;
        }

        if (isElementIgnoredOnRoundtrip())
            return;

        boolean parentNodeIsParagraph = "p".equals(node.getParent().getName());
        if (isStart)
        {
            if (mStyleResolver.isBlockLevelElement())
            {
                mParagraphArranger.startPara();
                applyParagraphFormatting();
                mHtmlBlockReader.updateCurrentParagraph(false);
            }

            if (StringUtil.hasChars(href))
            {
                if (isValidHyperlinkHref(href))
                {
                    if (mSettings.HyperlinkProcessor != null)
                    {
                        href = mSettings.HyperlinkProcessor.mapHyperlinkHref(href);
                    }

                    String target = node.getAttributes().getAttributeValue("target", "");
                    String screenTip = node.getAttributes().getAttributeValue("title", "");
                    // The screen tip's length limit has been chosen arbitrarily. MS Word shows about 2048 characters
                    // of screen tip text at most, Chrome shows 1024 characters, IE even less. Firefox and Opera do not limit
                    // the screen tip's length.
                    final int MAX_SCREEN_TIP_LENGTH = 2048;
                    if (screenTip.length() > MAX_SCREEN_TIP_LENGTH)
                        screenTip = screenTip.substring((0), (0) + (MAX_SCREEN_TIP_LENGTH - 1)) + "…";

                    mBuilder.startHyperlink(href, target, screenTip);
                }
                else
                {
                    warn(WarningType.DATA_LOSS, "The hyperlink '" + href + "' is not supported and it has been ignored.");
                }
            }

            mHtmlHyperlinkResolver.startAnchorBookmarkIfNeeded(bookmarkName, parentNodeIsParagraph, false);
        }
        else
        {
            if (mStyleResolver.isBlockLevelElement())
            {
                mParagraphArranger.endPara();
            }

            // End the hyperlink field in the model if it has been created for this anchor.
            if (StringUtil.hasChars(href) && isValidHyperlinkHref(href))
            {
                mBuilder.endHyperlink();
            }

            mHtmlHyperlinkResolver.endAnchorBookmarkIfNeeded(bookmarkName, parentNodeIsParagraph, false);
        }
    }

    private boolean isElementIgnoredOnRoundtrip()
    {
        String ignore = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.IGNORE_ON_ROUNDTRIP);
        return HtmlConstants.IGNORE_ON_ROUNDTRIP_TRUE.equals(ignore);
    }

    private boolean handleCommentAnchor(String bookmarkName, String href, boolean isStart)
    {
        if (msString.startsWith(href, HtmlConstants.COMMENT_HYPERLINK_HREF_PREFIX, StringComparison.ORDINAL) ||
            msString.startsWith(href, HtmlConstants.COMMENT_REFERENCE_HYPERLINK_HREF_PREFIX, StringComparison.ORDINAL))
        {
            mIsIgnoreHyperlinkContent = isStart;
            return true;
        }

        if (msString.startsWith(bookmarkName, HtmlConstants.COMMENT_REFERENCE_ID_PREFIX, StringComparison.ORDINAL))
        {
            int commentId = FormatterPal.tryParseInt(bookmarkName.substring(HtmlConstants.COMMENT_REFERENCE_ID_PREFIX.length()));
            if (commentId == Integer.MIN_VALUE)
            {
                if (isStart)
                {
                    warn(WarningType.DATA_LOSS,
                         msString.format(
                             "Can't parse comment id from '{0}' in anchor element. " +
                             "Positive integer is expected after prefix '{1}'. " +
                             "Comment possibly has been lost.",
                             bookmarkName, HtmlConstants.COMMENT_REFERENCE_ID_PREFIX));
                }
                return true;
            }

            if (isStart)
            {
                if (!mComments.containsKey(commentId))
                    startComment(commentId);
            }
            else
            {
                if (!mFullyProcessedComments.contains(commentId))
                    endComment(commentId);
            }

            return true;
        }

        return false;
    }

    private boolean handleFootnoteAnchor(
        /*FootnoteType*/int footnoteType,
        IntToObjDictionary<Footnote> footnotes,
        String footnoteReferenceIdPrefix,
        String footnoteHyperlinkHrefPrefix,
        String footnoteReferenceHyperlinkHrefPrefix,
        String bookmarkName,
        String href,
        boolean isStart)
    {
        // WORDSNET-10535 Some footnote should be currently in processing in order to deal with footnote's reference text.
        if (msString.startsWith(href, footnoteReferenceHyperlinkHrefPrefix, StringComparison.ORDINAL) && (mCurrentFootnote != null))
        {
            int footnoteId = FormatterPal.tryParseInt(href.substring(footnoteReferenceHyperlinkHrefPrefix.length()));
            if ((footnoteId == Integer.MIN_VALUE) || (footnotes.get(footnoteId) == null))
                return false;

            mIsFootnoteReferenceText = isStart;
            return true;
        }

        if (msString.startsWith(href, footnoteHyperlinkHrefPrefix, StringComparison.ORDINAL))
        {
            int footnoteId = FormatterPal.tryParseInt(href.substring(footnoteHyperlinkHrefPrefix.length()));
            if ((footnoteId == Integer.MIN_VALUE) || (footnotes.get(footnoteId) == null))
                return false;

            mIsIgnoreHyperlinkContent = isStart;
            return true;
        }

        if (msString.startsWith(bookmarkName, footnoteReferenceIdPrefix, StringComparison.ORDINAL))
        {
            int footnoteId = FormatterPal.tryParseInt(bookmarkName.substring(footnoteReferenceIdPrefix.length()));
            if (footnoteId == Integer.MIN_VALUE)
            {
                if (isStart)
                {
                    warn(WarningType.DATA_LOSS,
                        msString.format(
                            "Can't parse footnote id from '{0}'. Positive integer is expected after prefix '{1}'. Footnote has been lost.",
                            bookmarkName, footnoteReferenceIdPrefix));
                }

                return true;
            }

            // WORDSNET-11705 We ignore duplicate footnotes.
            if (footnotes.containsKey(footnoteId))
            {
                warn(WarningType.DATA_LOSS,
                    msString.format(
                        "Document already contains footnote with footnote id '{0}'. Footnote has been lost.", footnoteId));
                return false;
            }

            if (!isStart)
            {
                // WORDSNET-21724 Previously, HTML reader inserted a footnote at the end of current paragraph.
                // Currently, it inserts the footnote before the document builder's cursor.
                Footnote footnote = mBuilder.insertFootnote(footnoteType, null, "");
                footnote.getFirstParagraph().removeAllChildren();
                footnotes.add(footnoteId, footnote);
            }

            return true;
        }

        return false;
    }

    /// <summary>
    /// Starts comment in model.
    /// </summary>
    private void startComment(int commentId)
    {
        Comment comment = new Comment(mBuilder.getDocument());
        mComments.add(commentId, comment);
        CommentRangeStart commentRangeStart = new CommentRangeStart(mBuilder.getDocument(), comment.getId());
        mBuilder.insertNode(commentRangeStart);
        comment.appendChild(new Paragraph(mBuilder.getDocument()));
    }

    /// <summary>
    /// Ends comment in model.
    /// </summary>
    private void endComment(int commentId)
    {
        Comment comment = mComments.get(commentId);
        CommentRangeEnd commentRangeEnd;
        if (mCommentRangeEnds.containsKey(commentId))
        {
            commentRangeEnd = mCommentRangeEnds.get(commentId);
            commentRangeEnd.remove();
            comment.remove();
        }
        else
        {
            commentRangeEnd = new CommentRangeEnd(mBuilder.getDocument(), comment.getId());
            mCommentRangeEnds.add(commentId, commentRangeEnd);
        }

        mBuilder.insertNode(commentRangeEnd);
        mBuilder.insertNode(comment);
    }

    /// <summary>
    /// Handles a META node.
    /// </summary>
    private void handleMeta(HtmlElementNode node)
    {
        String name = node.getAttributes().getAttributeValue("name", "");
        String content = node.getAttributes().getAttributeValue("content", "");

        if (StringUtil.equalsIgnoreCase(name, "description"))
            mBuilder.getDocument().getBuiltInDocumentProperties().get("Subject").fromString(content);
        else if (StringUtil.equalsIgnoreCase(name, "keywords"))
            mBuilder.getDocument().getBuiltInDocumentProperties().get("Keywords").fromString(content);
    }

    /// <summary>
    /// Handles BR node. It can represent a section, a page or a line break.
    /// </summary>
    private void handleBreak(HtmlElementNode node) throws Exception
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());

        // It is a fast fix for processing of single and double <br> elements at the end of blocks.
        // In MS Word, the first break is ignored and the second break increases paragraph spacing.
        if (mSettings.ApplyFormattingAsMsWord && handleBreaksAtBlockEnd(node))
            return;

        String pageBreakBefore = msString.toLowerInvariant(mStyleResolver.getElementDeclarations().getIdentifier("page-break-before"));
        String msoBreakType = msString.toLowerInvariant(mStyleResolver.getElementDeclarations().getIdentifier("mso-break-type"));
        String msoColumnBreakBefore = msString.toLowerInvariant(mStyleResolver.getElementDeclarations().getIdentifier("mso-column-break-before"));

        /*BreakType*/int breakType;
        if ("section-break".equals(msoBreakType))
        {
            // It's a section break. Currently only new page and continuous are supported.
            switch (gStringSwitchMap.of(pageBreakBefore))
            {
                case /*"always"*/46:
                    breakType = BreakType.SECTION_BREAK_NEW_PAGE;
                    break;
                case /*"left"*/47:
                    breakType = BreakType.SECTION_BREAK_EVEN_PAGE;
                    break;
                case /*"right"*/48:
                    breakType = BreakType.SECTION_BREAK_ODD_PAGE;
                    break;
                default:
                    // It's either auto or avoid, let's see mso-column-break-before.
                    breakType = ("always".equals(msoColumnBreakBefore))
                        ? BreakType.SECTION_BREAK_NEW_COLUMN
                        : BreakType.SECTION_BREAK_CONTINUOUS;
                    break;
            }
        }
        else
        {
            // It's either page break or line break or column break.
            switch (gStringSwitchMap.of(pageBreakBefore))
            {
                case /*"always"*/46:
                    breakType = BreakType.PAGE_BREAK;
                    break;
                default:
                    breakType = ("always".equals(msoColumnBreakBefore))
                        ? BreakType.COLUMN_BREAK
                        : BreakType.LINE_BREAK;
                    break;
            }
        }

        switch (breakType)
        {
            case BreakType.LINE_BREAK:
            {
                // WORDSNET-2562 In HTML, <br> elements not always insert a line break. For example, the last <br>
                // in a block is ignored by browsers.
                if (isLineBreakAppropriate(node))
                {
                    // Line break is an inline entity, it belongs to a paragraph. So we need to start it.
                    mParagraphArranger.startParaIfNeeded();

                    // WORDSNET-15597 Enable CompatibilityOptions.DoNotExpandShiftReturn option
                    // allows the justified text that contains 'br' after import in MSW to look like in the browser.
                    // If html is inserted with help DocumentBuilder.InsertHtml() we shouldn't change this option.
                    if (mApplyDocumentWideFormatting && (mBuilder.getParagraphFormat().getAlignment() == ParagraphAlignment.JUSTIFY))
                    {
                        mBuilder.getDocument().getCompatibilityOptions().setDoNotExpandShiftReturn(true);
                    }

                    // The new break is inserted into a separate run and we need to apply its formatting appropriately.
                    applyFontFormatting(true);

                    // We're manually creating the line break run, because it will get additional formatting that should not
                    // propagate onto next runs or onto the paragraph break character.
                    Run lineBreakRun = new Run(mBuilder.getDocument(), ControlChar.LINE_BREAK, mBuilder.getRunPrCopy());

                    // WORDSNET-10824 Provide break's clear behavior.
                    applyLineBreakClear(lineBreakRun);

                    mBuilder.insertNode(lineBreakRun);
                }
                else if (isOnlyBlockNeighbours(node))
                {
                    // Line break is an inline entity, it belongs to a paragraph. So we need to start it.
                    mParagraphArranger.startParaIfNeeded();
                    mHtmlBlockReader.markParagraphAsEmptyInHtml(mBuilder.getCurrentParagraph());
                    if (mStyleResolver.getLineBreakClear() != LineBreakClear.NONE)
                    {
                        // The new break is inserted into a separate run and we need to apply its formatting appropriately.
                        applyFontFormatting(true);

                        // We're manually creating the line break run, because it will get additional formatting that
                        // should not propagate onto next runs or onto the paragraph break character.
                        Run lineBreakRun = new Run(mBuilder.getDocument(), ControlChar.LINE_BREAK, mBuilder.getRunPrCopy());

                        // WORDSNET-10824 Provide break's clear behavior.
                        applyLineBreakClear(lineBreakRun);

                        // WORDSNET-10825 We must provide break's clear behavior but do not add additional vertical space.
                        // Here is a simple workaround. We add break with minimum allowed font size.
                        // Visually this is almost unnoticeable.
                        // FOSS: WordUtil.MinFontSize (= 0.5pt, MS Word minimum allowed font size) was removed.
                        int minFontSizeHalfPoints = ConvertUtilCore.pointToHalfPoint(0.5);
                        lineBreakRun.getRunPr().setSize(minFontSizeHalfPoints);
                        lineBreakRun.getRunPr().setSizeBi(minFontSizeHalfPoints);

                        mBuilder.insertNode(lineBreakRun);
                    }
                    else
                    {
                        mParagraphArranger.insertParagraphImmediately();
                    }
                }
                else if (isNullOrInsignificantWhitespace(node.getNextSibling()) &&
                    isNullOrInsignificantWhitespace(node.getPreviousSibling()))
                {
                    // If a block-level element (paragraph) contains nothing but one <br>, browsers handle the <br> element
                    // not as a line break but rather as a placeholder that prevents the block from collapsing. For example,
                    // "<p><br></p>" creates a uncollapsed empty paragraph in browsers.
                    mParagraphArranger.markCurrentParagraphAsEmptyInHtml();
                    mHtmlBlockReader.markParagraphAsEmptyInHtml(mBuilder.getCurrentParagraph());
                }
                break;
            }
            case BreakType.PAGE_BREAK:
            case BreakType.COLUMN_BREAK:
            {
                // WORDSNET-9492 If a break is outside any paragraph, it will be inserted to
                // the paragraph which follows it.
                mParagraphArranger.startParaIfNeeded();

                // The new break is inserted into a separate run and we need to apply its formatting appropriately.
                applyFontFormatting(true);

                mBuilder.insertBreakCore(breakType, false);
                break;
            }
            case BreakType.SECTION_BREAK_NEW_PAGE:
            {
                // WORDSNET-17504 Keeps settings for the previous paragraph and the paragraph itself to restore it
                // after break inserting.
                ParaPr paraPr = mBuilder.getParaPrCopy();
                Paragraph paragraph = mBuilder.getCurrentParagraph();

                // If a break is outside any paragraph, it will be inserted to
                // the paragraph which follows it.
                mParagraphArranger.startParaIfNeeded();
                mBuilder.insertBreakCore(breakType, false);

                paragraph.setParaPr(paraPr);
                break;
            }
            default:
            {
                // Any node can follow the break. There is no need to start another paragraph. Just reset.
                mParagraphArranger.isParaStartNeeded(false);
                mBuilder.insertBreakCore(breakType, false);
                break;
            }
        }
        mHtmlBlockReader.updateCurrentParagraph(false);
    }

    private void applyLineBreakClear(Run lineBreakRun)
    {
        /*LineBreakClear*/int lineBreakClear = mStyleResolver.getLineBreakClear();
        if (lineBreakClear != LineBreakClear.NONE)
        {
            lineBreakRun.getRunPr().setLineBreakClear(lineBreakClear);
        }
    }

    /// <summary>
    /// Determines whether current node is between two block nodes.
    /// </summary>
    /// <remarks>
    /// Needed to properly create empty paragraphs in places of lonely line breaks. Probably we can do this better
    /// but there is no evident way.
    /// This should be also recursive since we can have a sequence like this: [span][br][/span] (rare case).
    /// See WORDSNET-3386.
    /// </remarks>
    private boolean isOnlyBlockNeighbours(HtmlNode node)
    {
        // Are there any non-block nodes under the same parent near the current one?
        HtmlNode next = node;
        while ((next = next.getNextSibling()) != null)
        {
            if (isAnythingInline(next))
                return false;
            if (next instanceof HtmlElementNode)
                break;
        }

        HtmlNode prev = node;
        while ((prev = prev.getPreviousSibling()) != null)
        {
            if (isAnythingInline(prev))
                return false;
            if (prev instanceof HtmlElementNode)
                break;
        }

        // <br> elements are processed as breaks only if there is some content to "break". In other words, there must be
        // anything before and/or after the <br>.
        return (next != null) || (prev != null);
    }

    private boolean isAnythingInline(HtmlNode node)
    {
        if (node instanceof HtmlTextNode)
        {
            return containsMeaningfulText((HtmlTextNode)node);
        }
        else if (node instanceof HtmlElementNode)
        {
            HtmlElementNode elementNode = (HtmlElementNode)node;
            // WORDSNET-22838 All children of the inline element should be inline too.
            return isInlineElement(elementNode) && allChildrenAreInline(elementNode);
        }
        else
        {
            return false;
        }
    }

    /// <summary>
    /// Determines whether line break is appropriate at place of inserting this node.
    /// </summary>
    private boolean isLineBreakAppropriate(HtmlElementNode node)
    {
        // Are there any more sibling elements under the same parent?
        HtmlNode next = node;
        while ((next = next.getNextSibling()) != null)
        {
            if (next instanceof HtmlElementNode)
            {
                HtmlElementNode nextElementNode = (HtmlElementNode)next;
                // <style> and <title> are not visible by default ("display:none") and have no effect on line breaks.
                // Note that this is a quick fix. We'd better analyze actual "display" values to handle other cases where
                // elements get invisible.
                String name = nextElementNode.getName();
                if (("style".equals(name)) || ("title".equals(name)))
                    continue;

                // Any inline element at the same level should be separated.
                // WORDSNET-22838 All children of the inline element should be inline too.
                if (isInlineElement(nextElementNode) && allChildrenAreInline(nextElementNode))
                {
                    return true;
                }

                // Non-inline element occurred first. Should see in the parent.
                break;
            }

            // Any non-empty text should be also separated.
            if ((next instanceof HtmlTextNode) && containsMeaningfulText((HtmlTextNode)next))
            {
                return true;
            }
        }

        // The last break at the the end of a block (including "inline-block") is ignored in browsers.
        HtmlElementNode parentNode = node.getParent();
        if ((parentNode == null) ||
            !isInlineElement(parentNode) ||
            (mStyleResolver.parentElementDisplayType() == CssDisplayType.INLINE_BLOCK))
        {
            return false;
        }

        // For inline elements recurse to the parent.
        return isLineBreakAppropriate(parentNode);
    }

    /// <summary>
    /// Determines whether the node is <c>null</c> or is an insignificant whitespace node (is not a meaningful text node).
    /// </summary>
    private boolean isNullOrInsignificantWhitespace(HtmlNode node)
    {
        return (node == null) ||
            ((node instanceof HtmlTextNode) && !containsMeaningfulText((HtmlTextNode)node));
    }

    /// <summary>
    /// Checks whether it's an inline element.
    /// Queries from the HtmlCssReader and defaults to static list. Maybe we'll simplify this after deploying default style sheets.
    /// </summary>
    private boolean isInlineElement(HtmlElementNode node)
    {
        return !mElementCategorizer.isBlockLevelElement(node, mCssRules);
    }

    /// <summary>
    /// Checks whether all children of the specified node are inline.
    /// </summary>
    private boolean allChildrenAreInline(HtmlElementNode node)
    {
        for (HtmlNode child : node.getChildren())
        {
            HtmlElementNode childAsElement = ms.as(child, HtmlElementNode.class);
            if (childAsElement == null)
            {
                continue;
            }

            if (!isInlineElement(childAsElement) || !allChildrenAreInline(childAsElement))
            {
                return false;
            }
        }
        return true;
    }

    /// <summary>
    /// Special processing for single and double BR elements at end of block-level elements. Mimics MS Word's behavior.
    /// </summary>
    private boolean handleBreaksAtBlockEnd(HtmlElementNode currentBreakNode)
    {
        if (mStyleResolver.parentElementDisplayType() == CssDisplayType.INLINE)
            return false;

        // The following code recognizes both <br><br> pairs and single <br> elements. In the latter case leading and
        // trailing nodes will reference the same HTML node.
        HtmlNode leadingBreak = isBreak(currentBreakNode.getPreviousSibling())
            ? currentBreakNode.getPreviousSibling()
            : currentBreakNode;
        HtmlNode trailingBreak = isBreak(leadingBreak.getNextSibling())
            ? leadingBreak.getNextSibling()
            : currentBreakNode;

        // This method doesn't support the case where a block contains nothing but breaks.
        if (leadingBreak.getPreviousSibling() == null)
        {
            return false;
        }

        // Breaks must occur at the end of the block.
        return trailingBreak.getNextSibling() == null;
    }

    private static boolean isBreak(HtmlNode node)
    {
        if (node == null)
        {
            return false;
        }
        HtmlElementNode nodeAsElement = ms.as(node, HtmlElementNode.class);
        return (nodeAsElement != null) && ("br".equals(nodeAsElement.getName()));
    }

    /// <summary>
    /// Handles ordered or unordered native list
    /// </summary>
    private void handleList(boolean isStart)
    {
        if (isStart)
        {
            createListReaderIfNeeded();

            getCurrentListReader().beginLevel(mStyleResolver.getCurrentElement().getElementName());

            if (isImplicitListDisplayType(mStyleResolver.getElementDisplayType()))
                return;

            // WORDSNET-12225 Html to Docx conversion issue with display : inline style.
            // In case a list contains inline elements they must fit a separate paragraph.
            mParagraphArranger.endPara();
        }
        else
        {
            // Its presence should be guaranteed by parser
            Debug.assertTrue(getCurrentListReader() != null);
            getCurrentListReader().endLevel();
        }
    }

    private void handleListItem(HtmlElementNode node, boolean isStart) throws Exception
    {
        if (isStart)
        {
            // Usually, ::before pseudo-elements are processed (and corresponding CSS counters are updated) after
            // the main HTML element. However, ::before pseudo-elements on <li> elements are treated as list item
            // markers and in order to import them correctly we need to know values of CSS counters earlier,
            // when processing the main <li> element. We switch to ::before and immediately back to the main element
            // so that the counters will be updated.
            mStyleResolver.switchToPart(HtmlElementPart.BEFORE, true);
            mStyleResolver.switchToPart(HtmlElementPart.ELEMENT, false);

            createListReaderIfNeeded();

            mParagraphArranger.startPara();

            // Start of bookmark for the list. The end of bookmark will be added during handling the end of the list.
            if (!getCurrentListReader().getCurrentListHasListItems())
                mHtmlHyperlinkResolver.addPendingStartBookmarkIfNeeded(node.getParent());

            mHtmlBlockReader.startHtmlBlock(
                mStyleResolver.getCurrentElement().getElementName(),
                mStyleResolver.getElementDeclarations(),
                mStyleResolver.getElementDisplayType());

            getCurrentListReader().beginItem(node);

            applyParagraphFormatting();
            mCommonBorderResolver.collectParagraphWithBorder(mBuilder.getCurrentParagraph(), mStyleResolver.getElementDeclarations());

            // The list item may be a HTML block if 'div' or 'blockquote' element has `display:list-item` CSS property
            // or the list item may be inside already created the HTML block.
            mHtmlBlockReader.updateCurrentParagraph(true);

            mHtmlHyperlinkResolver.startInlineBookmarkIfNeeded(node);
        }
        else
        {
            mHtmlHyperlinkResolver.endInlineBookmarkIfNeeded(node);

            mParagraphArranger.endPara();
            Debug.assertTrue(getCurrentListReader() != null);

            getCurrentListReader().endItem();

            mHtmlBlockReader.endHtmlBlock(
                mStyleResolver.getCurrentElement().getElementName(),
                mStyleResolver.getElementDeclarations(),
                mBidiTextArranger);
        }
    }

    private void createListReaderIfNeeded()
    {
        if (getCurrentListReader() == null)
        {
            // WORDSNET-12935 In UseBuilderFormatting mode we don't want to override default positions of list item
            // markers.
            boolean adjustMarkerPositions = !mUseDocumentBuilderFormatting;

            mListReaderManager.createListReader(
                mBuilder,
                mStyleResolver,
                mResourceLoader,
                mBaseUri,
                mBaseParagraphLeftIndent,
                adjustMarkerPositions);
        }
    }

    /// <summary>
    /// Handles inline-level HTML elements like SPAN, B, I, U and others.
    /// </summary>
    private /*HandleNodeAction*/int handleSpan(HtmlElementNode node, boolean isStart) throws Exception
    {
        // '-aw-import: ignore' spans shouldn't be imported back.
        CssDeclaration importBehaviorDeclaration = mStyleResolver.getElementDeclarations().get(HtmlConstants.IMPORT);

        if ((importBehaviorDeclaration != null) && importBehaviorDeclaration.getValue().equals(CssValue.Ignore))
        {
            // We also use '-aw-import: ignore' spans containing just "&nbsp;" to prevent empty paragraphs
            // from collapsing in HTML browsers.
            if (msString.equals(node.getInnerText(), ControlChar.NON_BREAKING_SPACE))
            {
                mParagraphArranger.markCurrentParagraphAsEmptyInHtml();

                // WORDSNET-21377 MS Word applies element's font style to an empty paragraph. Mimic this behavior.
                // The font style should be applied if the span contains any other CSS besides "-aw-import:ignore".
                if (mStyleResolver.getElementDeclarations().getCount() > 1)
                {
                    mStyleResolver.handleSpanStart();
                    mStyleResolver.toFont(mBuilder.getFont(), mBuilder.getCurrentParagraph().getParagraphStyle());
                    mStyleResolver.handleSpanEnd();
                }
            }

            // All other such spans are simply ignored.
            return HandleNodeAction.HANDLED_SKIP_CHILDREN;
        }

        if (handleStructuredDocumentTagNode(node, isStart))
            return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;

        if (isStart)
        {
            // Sometimes a 'div' element has other display type.
            boolean htmlBlockStarted = mHtmlBlockReader.startHtmlBlock(
                mStyleResolver.getCurrentElement().getElementName(),
                mStyleResolver.getElementDeclarations(),
                mStyleResolver.getElementDisplayType());

            // WORDSNET-21063 Update only an empty paragraph to prevent setting the HTML block
            // for a preceding paragraph.
            if (htmlBlockStarted && (mBuilder.getCurrentParagraph().getCount() == 0))
                mHtmlBlockReader.updateCurrentParagraph(true);

            // Insert a bookmark if the span doesn't contain meaningful text (empty).
            if (!node.containsText())
            {
                mHtmlHyperlinkResolver.startInlineBookmarkIfNeeded(node);
                mHtmlHyperlinkResolver.endInlineBookmarkIfNeeded(node);
            }

            if (handleBookmarkSpan(node))
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            if (handleCommentSpan())
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            if (handleTabEmulationSpan(node))
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            if (handleFieldSpan())
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;

            mStyleResolver.handleSpanStart();

            mFixedWidthSpanReader.handleSpanStart(node);
        }
        else
        {
            // If needed, pad the current fixed-width span with space characters up to its desired width.
            applyFontFormatting(true);
            String padding = mFixedWidthSpanReader.handleSpanEnd(node, mBuilder.getFont());
            if (StringUtil.hasChars(padding))
            {
                writeText(padding);
                // There may be enclosing fixed-width spans that are still opened, so we take padding width into account.
                mFixedWidthSpanReader.processText(padding, mBuilder.getFont());
            }

            handleDirectionMarkSpan(node);
            mStyleResolver.handleSpanEnd();

            mHtmlBlockReader.endHtmlBlock(
                mStyleResolver.getCurrentElement().getElementName(),
                mStyleResolver.getElementDeclarations(),
                mBidiTextArranger);
        }
        return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
    }

    private boolean handleStructuredDocumentTagNode(HtmlElementNode node, boolean isStart) throws Exception
    {
        CssDeclaration tagDeclaration = mStyleResolver.getElementDeclarations().get(HtmlConstants.SDT_TAG);
        if (tagDeclaration == null)
            return false;

        mBidiTextArranger.rearrangeAndWriteText();

        if (isStart)
        {
            StructuredDocumentTag sdt = ("span".equals(node.getName()))
                ? new StructuredDocumentTag(mBuilder.getDocument(), SdtType.PLAIN_TEXT, MarkupLevel.INLINE)
                : new StructuredDocumentTag(mBuilder.getDocument(), SdtType.RICH_TEXT, MarkupLevel.BLOCK);
            sdt.setTag(mStyleResolver.getElementDeclarations().getString(HtmlConstants.SDT_TAG));
            sdt.setTitle(mStyleResolver.getElementDeclarations().getString(HtmlConstants.SDT_TITLE));
            String sdtContent = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.SDT_CONTENT);
            sdt.isShowingPlaceholderText(HtmlConstants.SDT_CONTENT_PLACEHOLDER.equals(sdtContent));
            sdt.getChildNodes(NodeType.ANY, false).clear();

            // WORDSNET-20553 Move all bookmarks before inline SDT to parent paragraph.
            // If current paragraph contains nodes with another types then bookmarks are not moved.
            if (mBuilder.getCurrentParagraph().hasChildNodes())
            {
                boolean possibleToMove = true;
                for (Node childNode : (Iterable<Node>) mBuilder.getCurrentParagraph().getChildNodes(NodeType.ANY, false))
                {
                    if ((childNode.getNodeType() != NodeType.BOOKMARK_START) &&
                       (childNode.getNodeType() != NodeType.BOOKMARK_END))
                    {
                        possibleToMove = false;
                        break;
                    }
                }
                if (possibleToMove)
                {
                    while (mBuilder.getCurrentParagraph().hasChildNodes())
                    {
                        Node firstChild = mBuilder.getCurrentParagraph().getFirstChild();
                        firstChild.remove();
                        mBuilder.getCurrentParagraph().insertPrevious(firstChild);
                    }
                }
            }

            if (sdt.getLevel() == MarkupLevel.INLINE)
            {
                Node firstNode = sdt.appendChild(new Run(mBuilder.getDocument()));
                mBuilder.insertNode(sdt);
                mParagraphArranger.rememberCurrentParagraphAndMoveTo(firstNode);
            }
            else
            {
                mParagraphArranger.startParaIfNeeded();
                sdt.appendChild(new Paragraph(mBuilder.getDocument()));
                if (!mBuilder.isAtStartOfParagraph())
                {
                    Debug.assertTrue(mBidiTextArranger.isEmpty());
                    mParagraphArranger.insertParagraphImmediately();
                }
                mBuilder.getCurrentParagraph().insertPrevious(sdt);
                mParagraphArranger.rememberCurrentParagraphAndMoveTo(sdt);
            }
        }
        else
        {
            // WORDSNET-20553 Move all bookmarks inside inline SDT to parent paragraph.
            StructuredDocumentTag sdt = ms.as(mBuilder.getCurrentParagraph().getFirstChild(), StructuredDocumentTag.class);
            if (sdt != null)
            {
                while (sdt.hasChildNodes())
                {
                    Node firstChild = sdt.getFirstChild();
                    if ((firstChild.getNodeType() != NodeType.BOOKMARK_START) &&
                           (firstChild.getNodeType() != NodeType.BOOKMARK_END))
                    {
                        break;
                    }
                    firstChild.remove();
                    mBuilder.getCurrentParagraph().insertPrevious(firstChild);
                }
            }

            // WORDSNET-20553 Move all bookmarks from the first SDT's paragraph to SDT's parent node.
            sdt = ms.as(mBuilder.getCurrentParagraph().getParentNode(), StructuredDocumentTag.class);
            if ((sdt != null) &&
                (sdt.getFirstChild() != null) &&
                (sdt.getFirstChild().getNodeType() == NodeType.PARAGRAPH))
            {
                Paragraph firstParagraph = (Paragraph)sdt.getFirstChild();
                while (firstParagraph.hasChildNodes())
                {
                    Node firstChild = firstParagraph.getFirstChild();
                    if ((firstChild.getNodeType() != NodeType.BOOKMARK_START) &&
                           (firstChild.getNodeType() != NodeType.BOOKMARK_END))
                    {
                        break;
                    }
                    firstChild.remove();
                    sdt.insertPrevious(firstChild);
                }
            }

            // WORDSNET-20108 Set the language of a whole structured document tag to 'HebrewIsrael' if its first run
            // is in 'HebrewIsrael'.
            updateSdtNodeBidiLocale();

            mParagraphArranger.restoreCurrentParagraphIfNeeded();
        }

        return true;
    }

    private void handleDirectionMarkSpan(HtmlElementNode node)
    {
        // WORDSNET-12119 A quirk exists in all modern browsers (Chrome, Firefox, IE) related to processing of
        // explicit embedding levels in the Unicode bidirectional algorithm (UBA). According to the HTML specification,
        // an empty span with 'dir' attribute should be treated as a LRE or RLE character that is immediately followed
        // by a PDF character. For example the following span
        //    <span dir=rtl></span>
        // is treated as a sequence RLE+PDF, which has no effect on surrounding text, according to UBA. However, this span
        // does affect surrounding text in modern browsers.
        // The exact way in which browsers process such spans is unclear and no official documentation exists regarding
        // that quirk (at least, it is hard to find). However, our tests show that browsers' behavior can be reproduced
        // if such spans are converted to Unicode direction marks (LRM or RLM) before passing them to UBA.
        // In other words, we treat
        //   <span dir=ltr></span>  as LRE+LRM+PDF
        //   <span dir=rtl></span>  as RLE+RLM+PDF
        // Note that this applies not only to spans but to any inline elements too.
        if (("embed".equals(mStyleResolver.getElementDeclarations().getIdentifier("unicode-bidi"))) && (!node.containsText()))
        {
            writeDirectionMark();
        }
    }

    /// <summary>
    /// Handles span that is a bookmark's start or bookmark's end mark written by HTML Writer.
    /// </summary>
    /// <returns><c>true</c> if the span node has been processed successfully; <c>false</c> otherwise.</returns>
    private boolean handleBookmarkSpan(HtmlElementNode node) throws Exception
    {
        boolean parentNodeIsParagraph = (node.getParent() != null) && ("p".equals(node.getParent().getName()));

        String bookmarkName = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.BOOKMARK_START);
        if (!msString.equals(bookmarkName, ""))
        {
            mHtmlHyperlinkResolver.startAnchorBookmarkIfNeeded(bookmarkName, parentNodeIsParagraph, true);
            return true;
        }

        bookmarkName = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.BOOKMARK_END);
        if (!msString.equals(bookmarkName, ""))
        {
            mHtmlHyperlinkResolver.endAnchorBookmarkIfNeeded(bookmarkName, parentNodeIsParagraph, true);
            return true;
        }

        return false;
    }

    /// <summary>
    /// Handles span that is a comment's start or comment's end mark written by HTML Writer.
    /// </summary>
    /// <returns><c>true</c> if the span node has been processed successfully; <c>false</c> otherwise.</returns>
    private boolean handleCommentSpan() throws Exception
    {
        return handleCommentStartSpan() || handleCommentEndSpan();
    }

    /// <summary>
    /// Handles span that is a comment's start mark written by HTML Writer.
    /// </summary>
    /// <returns><c>true</c> if the span node has been processed successfully; <c>false</c> otherwise.</returns>
    private boolean handleCommentStartSpan() throws Exception
    {
        String commentStartName = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.COMMENT_START);
        if (msString.equals(commentStartName, ""))
        {
            return false;
        }

        int commentId = FormatterPal.tryParseInt(commentStartName.substring(HtmlConstants.COMMENT_REFERENCE_ID_PREFIX.length()));
        if (commentId == Integer.MIN_VALUE)
        {
            warn(WarningType.DATA_LOSS,
                msString.format(
                    "Can't parse comment id from '{0}' in span element. " +
                    "Positive integer is expected after prefix '{1}'. " +
                    "Original comment's start position possibly has been lost.",
                    commentStartName, HtmlConstants.COMMENT_REFERENCE_ID_PREFIX));
        }
        else
        {
            if (!mComments.containsKey(commentId))
            {
                // Comment boundaries are hard to preserve during reordering by the Unicode bidirectional algorithm (UBA),
                // so at the moment comment contents are isolated from the UBA to make sure it is reordered as a whole.
                mBidiTextArranger.rearrangeAndWriteText();
                startComment(commentId);
            }
        }

        return true;
    }

    /// <summary>
    /// Handles span that is a comment's end mark written by HTML Writer.
    /// </summary>
    /// <returns><c>true</c> if the span node has been processed successfully; <c>false</c> otherwise.</returns>
    private boolean handleCommentEndSpan() throws Exception
    {
        String commentEndName = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.COMMENT_END);
        if (msString.equals(commentEndName, ""))
        {
            return false;
        }

        int commentId = FormatterPal.tryParseInt(commentEndName.substring(HtmlConstants.COMMENT_REFERENCE_ID_PREFIX.length()));
        if (commentId == Integer.MIN_VALUE)
        {
            warn(WarningType.DATA_LOSS,
                msString.format
                ("Can't parse comment id from '{0}' in span element. " +
                "Positive integer is expected after prefix '{1}'. " +
                "Original comment's end position possibly has been lost.",
                commentEndName, HtmlConstants.COMMENT_REFERENCE_ID_PREFIX));
        }
        else
        {
            if (mComments.containsKey(commentId))
            {
                // Comment boundaries are hard to preserve during reordering by the Unicode bidirectional algorithm (UBA),
                // so at the moment comment contents are isolated from the UBA to make sure it is reordered as a whole.
                mBidiTextArranger.rearrangeAndWriteText();

                endComment(commentId);
                msArrayList.add(mFullyProcessedComments, commentId);
            }
        }

        return true;
    }

    /// <summary>
    /// Handles span that is a tab stop emulation written by HTML Writer.
    /// </summary>
    /// <returns><c>true</c> if the span node has been processed successfully; <c>false</c> otherwise.</returns>
    /// <remarks>
    /// Examine HtmlSpanWriter.WriteTabStop(Font) (removed from FOSS build) to see how we represent tab stops in HTML.
    /// </remarks>
    private boolean handleTabEmulationSpan(HtmlElementNode element) throws Exception
    {
        String firstChildText = ((element.getChildren().getCount() == 1) && (element.getChildren().get(0) instanceof HtmlTextNode))
            ? ((HtmlTextNode)element.getChildren().get(0)).getText()
            : "";

        // WORDSNET-17074 Previously, we used a fixed whitespace string to represent tabstops.
        if (HtmlConstants.TAB_STOP_REPLACEMENT.equals(firstChildText))
        {
            writeText("\t");
            return true;
        }

        CssDeclaration displayDeclaration = mStyleResolver.getElementDeclarations().get("display");
        if ((displayDeclaration == null) || !displayDeclaration.getValue().equals(CssValue.InlineBlock))
            return false;

        double width = mStyleResolver.getElementDeclarations().getLength("width");
        if (width < 0)
            return false;

        // WORDSNET-22645 Sometimes customers edit HTML generated by AW and write arbitrary text right into
        // tab stop emulation spans. Because that text is then gets visible in browsers, we decided to add the following
        // check. If the fill text of a tab stop emulation span contains anything but tab stop fill characters used by our
        // HTML writer, the text must have been edited manually and we should process the span as normal text instead of
        // a tab stop replacement.
        // Note that leading and trailing new line characters are also ignored, so if text of a tab emulation span is
        // formatted, it is still recognized as tab stop.
        char[] tabStopFillCharactersUsedByHtmlWriter = new char[] { ' ', '\u00A0', '.', '-', '_', '·', '\n' };
        String trimmedFirstChildText = msString.trim(firstChildText, tabStopFillCharactersUsedByHtmlWriter);
        if (StringUtil.hasChars(trimmedFirstChildText))
        {
            return false;
        }

        String tabStopAlignValue = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.TAB_STOP_ALIGNMENT);
        double tabStopPosition = mStyleResolver.getElementDeclarations().getLength(HtmlConstants.TAB_STOP_POSITION);
        if ((!msString.equals(tabStopAlignValue, "")) && (tabStopPosition > 0))
        {
            // This span is a tab stop emulation produced by AW HTML Export and it contains a tab stop roundtrip info.
            /*TabAlignment*/int tabStopAlignment = 0;
            RefInt referenceToTabStopAlignment = new RefInt(tabStopAlignment);
            boolean outRefCondition2 = HtmlUtil.parseTapStopAlignment(tabStopAlignValue, /*out*/ referenceToTabStopAlignment);
            tabStopAlignment = referenceToTabStopAlignment.get();
            if (outRefCondition2)
            {
                /*TabLeader*/int tabStopLeader = TabLeader.NONE;
                String tabStopLeaderValue = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.TAB_STOP_LEADER);
                RefInt referenceToTabStopLeader = new RefInt(tabStopLeader);
                boolean outRefCondition3 = (msString.equals(tabStopLeaderValue, "")) ||
                    HtmlUtil.parseTapStopLeader(tabStopLeaderValue, /*out*/ referenceToTabStopLeader);
                tabStopLeader = referenceToTabStopLeader.get();
                if (outRefCondition3)
                {
                    ParaPr paraPr = mBuilder.getCurrentParagraph().getParaPr();
                    if (paraPr.getTabStops() == null)
                        paraPr.setTabStops(new TabStopCollection());
                    paraPr.getTabStops().add(tabStopPosition, tabStopAlignment, tabStopLeader);
                }
            }
        }
        else if ((!msString.equals(firstChildText, ControlChar.NON_BREAKING_SPACE)) && (element.getChildren().getCount() != 0))
        {
            // This span isn't an AW HTML Export tab stop emulation and should be processed as a normal span.
            // Before WORDSNET-14080 we emulated tabs with empty spans. We also support this emulation for backward
            // compatibility.
            return false;
        }

        // WORDSNET-10025 Support HTML round-trip of underline tab characters.
        mStyleResolver.toFont(mBuilder.getFont(), mBuilder.getCurrentParagraph().getParagraphStyle());
        writeText("\t");
        return true;
    }

    /// <summary>
    /// Handles span that is a field representation written by HTML Writer.
    /// </summary>
    /// <returns><c>true</c> if the span node has been processed successfully; <c>false</c> otherwise.</returns>
    private boolean handleFieldSpan() throws Exception
    {
        // We don't decide here, which fields are correct and which aren't, we load fields as is.
        // Fields' validation is done during document's post loading actions.

        String fieldStartValue = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.FIELD_START);
        if (HtmlConstants.FIELD_START_TRUE.equals(fieldStartValue))
        {
            mBidiTextArranger.rearrangeAndWriteText();

            FieldStart fieldStart = mBuilder.insertFieldStart(FieldType.FIELD_PAGE);
            mFieldNodesStack.push(fieldStart);

            return true;
        }

        String fieldCodeValue = mStyleResolver.getElementDeclarations().getString(HtmlConstants.FIELD_CODE);
        if (StringUtil.hasChars(fieldCodeValue))
        {
            // WORDSNET-12277 If the text buffer contains some text, it is time to write it to the resulting document.
            // Otherwise, text that comes before the field in the source HTML document will be written after the field
            // in the resulting document.
            mBidiTextArranger.rearrangeAndWriteText();

            mBuilder.insertFieldCode(fieldCodeValue);

            return true;
        }

        String fieldSeparatorValue = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.FIELD_SEPARATOR);
        if (HtmlConstants.FIELD_SEPARATOR_TRUE.equals(fieldSeparatorValue))
        {
            mBidiTextArranger.rearrangeAndWriteText();

            FieldSeparator fieldSeparator = mBuilder.insertFieldSeparator(FieldType.FIELD_NONE);
            mFieldNodesStack.push(fieldSeparator);

            return true;
        }

        String fieldEndValue = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.FIELD_END);
        if (HtmlConstants.FIELD_END_TRUE.equals(fieldEndValue))
        {
            mBidiTextArranger.rearrangeAndWriteText();

            FieldBundle field = new FieldBundle();

            field.setSeparator((FieldSeparator)StackExtension.popIfInstanceOf(mFieldNodesStack, FieldSeparator.class));
            field.setStart((FieldStart)StackExtension.popIfInstanceOf(mFieldNodesStack, FieldStart.class));
            field.setEnd(mBuilder.insertFieldEnd(FieldType.FIELD_NONE, field.getSeparator() != null));

            // WORDSNET-15311 MS Word hides field result for fields with FieldType.None, when displaying DOC documents,
            // so must we set actual FieldType for the field.
            if ((field.getStart() != null) && ((field.getSeparator() != null) || (field.getEnd() != null)))
                field.determineFieldType();

            return true;
        }

        return false;
    }

    /// <summary>
    /// Handles IMG element.
    /// </summary>
    private void handleImage(HtmlElementNode node) throws Exception
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());
        String imageUri = HtmlUtil.validateUri(node.getAttributes().getAttributeValue("src", ""));

        // In the mobi format we don't have src but we have recindex instead.
        if (msString.isNullOrEmpty(imageUri))
            imageUri = node.getAttributes().getAttributeValue("recindex", "");

        processImage(imageUri, node);
    }

    /// <summary>
    /// Handles MathML.
    /// </summary>
    private void handleMathMl(HtmlElementNode element) throws Exception
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());

        if (mStyleResolver.isDisplayedAsBlock() || (mStyleResolver.getElementDisplayType() == CssDisplayType.RUN_IN))
        {
            mParagraphArranger.isParaStartNeeded(true);
        }

        mParagraphArranger.startParaIfNeeded();

        // FOSS
    }

    /// <summary>
    /// Handles EMBED element.
    /// </summary>
    private void handleEmbed(HtmlElementNode node) throws Exception
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());
        String resourceUri = HtmlUtil.validateUri(node.getAttributes().getAttributeValue("src", ""));
        if (isImageInSupportedFormat(resourceUri))
            processImage(resourceUri, node);
    }

    /// <summary>
    /// Handles OBJECT element.
    /// </summary>
    private /*HandleNodeAction*/int handleObject(HtmlElementNode node) throws Exception
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());
        String resourceUri = HtmlUtil.validateUri(node.getAttributes().getAttributeValue("data", ""));
        if (isImageInSupportedFormat(resourceUri))
        {
            // WORDSNET-16978 Fallback content of an image <object> element is now processed only if the image
            // is unavailable and cannot be loaded.
            byte[] imageBytes = mResourceLoader.loadImage(mBaseUri, resourceUri, false);
            if (imageBytes != null)
            {
                processImage(imageBytes, resourceUri, node);
                return HandleNodeAction.HANDLED_SKIP_CHILDREN;
            }
        }

        // The data can't be imported. Import the fallback content instead.
        return HandleNodeAction.HANDLED_TRAVERSE_CHILDREN;
    }

    /// <summary>
    /// Handles IFRAME element.
    /// </summary>
    private void handleIframe(HtmlElementNode node) throws Exception
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());
        String resourceUri = HtmlUtil.validateUri(node.getAttributes().getAttributeValue("src", ""));
        if (isImageInSupportedFormat(resourceUri))
            processImage(resourceUri, node);
    }

    private void handleTextarea(boolean isStart) throws Exception
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());

        mParagraphArranger.startParaIfNeeded();

        if (isStart)
        {
            Shape shape = new Shape(mBuilder.getDocument(), ShapeType.TEXT_BOX);
            shape.setWrapType(WrapType.INLINE);

            // Approximately taken default padding value.
            final double DEFAULT_TEXTAREA_PADDING = 2.0;
            double paddingTop = mStyleResolver.getElementDeclarations().getLength("padding-top");
            shape.getTextBox().setInternalMarginTop(!MathUtil.isMinValue(paddingTop)
                                                    ? paddingTop
                                                    : DEFAULT_TEXTAREA_PADDING);
            double paddingRight = mStyleResolver.getElementDeclarations().getLength("padding-right");
            shape.getTextBox().setInternalMarginRight(!MathUtil.isMinValue(paddingRight)
                                                        ? paddingRight
                                                        : DEFAULT_TEXTAREA_PADDING);
            double paddingBottom = mStyleResolver.getElementDeclarations().getLength("padding-bottom");
            shape.getTextBox().setInternalMarginBottom(!MathUtil.isMinValue(paddingBottom)
                                                        ? paddingBottom
                                                        : DEFAULT_TEXTAREA_PADDING);
            double paddingLeft = mStyleResolver.getElementDeclarations().getLength("padding-left");
            shape.getTextBox().setInternalMarginLeft(!MathUtil.isMinValue(paddingLeft)
                                                    ? paddingLeft
                                                    : DEFAULT_TEXTAREA_PADDING);

            // Different browsers have different default size for textarea window.
            // So these are approximately taken values.
            final double DEFAULT_TEXTAREA_WIDTH = 144.0;
            final double DEFAULT_TEXTAREA_HEIGHT = 28.0;
            double width = mStyleResolver.getElementDeclarations().getLength("width");
            shape.setWidth(!MathUtil.areEqual(width, -Double.MAX_VALUE) ? width : DEFAULT_TEXTAREA_WIDTH);
            double height = mStyleResolver.getElementDeclarations().getLength("height");
            shape.setHeight(!MathUtil.areEqual(height, -Double.MAX_VALUE) ? height : DEFAULT_TEXTAREA_HEIGHT);

            // WORDSNET-14876 Remove border from textarea if "border:none" or "border:hidden" style is specified.
            if (isBorderExplicitlyHidden("border-top-style") && isBorderExplicitlyHidden("border-right-style") &&
                isBorderExplicitlyHidden("border-bottom-style") && isBorderExplicitlyHidden("border-left-style"))
            {
                shape.setStroked(false);
            }

            Paragraph para = new Paragraph(mBuilder.getDocument());
            shape.appendChild(para);

            mBuilder.insertNode(shape);
            mParagraphArranger.rememberCurrentParagraphAndMoveTo(para);
        }
        else
        {
            mParagraphArranger.restoreCurrentParagraphIfNeeded();
        }
    }

    private boolean isBorderExplicitlyHidden(String borderStylePropertyName)
    {
        CssDeclaration borderStyleDeclaration = mStyleResolver.getElementDeclarations().get(borderStylePropertyName);
        return (borderStyleDeclaration != null) &&
               (borderStyleDeclaration.getValue().equals(CssValue.None) ||
                borderStyleDeclaration.getValue().equals(CssValue.Hidden));
    }

    private void processImage(String imageUri, HtmlElementNode node) throws Exception
    {
        byte[] imageBytes = mResourceLoader.loadImage(mBaseUri, imageUri);
        processImage(imageBytes, imageUri, node);
    }

    private void processImage(byte[] imageBytes, String imageUri, HtmlElementNode element) throws Exception
    {
        SizeD svgImageSize = null;
        // WORDSNET-7502 If an image is not inline, it must be put into a new paragraph.
        mParagraphArranger.startParaIfNeeded();

        // Apply font formatting. According to CSS rules, text decoration is not applied to images. However, we apply
        // text decoration to images that are inserted into a target document that has text decoration, if the
        // user explicitly asks us to keep it.
        applyFontFormatting(mUseDocumentBuilderFormatting);

        Shape shape;
        if (imageBytes != null)
        {
            // WORDSNET-18329 The exception CantCreateBitmapException occurs when we try to load a malformed image.
            // Replace such image by the red-cross image.
            try
            {
                // WORDSNET-27384 EXIF attributes related to image orientation should not be considered for MHTML.
                shape = mBuilder.insertImage(
                    imageBytes,
                    RelativeHorizontalPosition.COLUMN,
                    0.0,
                    RelativeVerticalPosition.PARAGRAPH,
                    0.0,
                    -1,
                    -1,
                    WrapType.INLINE,
                    mLoadFormat != LoadFormat.MHTML);
            }
            catch (CantCreateBitmapException e)
            {
                imageBytes = ImageUtil.getNoImageBytes();
                shape = mBuilder.insertImage(imageBytes);
            }
            Debug.assertTrue(shape != null);

            // FOSS: ImageUtil.IsNotImageBytes(byte[]) was removed; inline equivalent check.
            byte[] noImageBytesRef = ImageUtil.getNoImageBytes();
            if (imageBytes.length == noImageBytesRef.length &&
                com.aspose.ArrayUtil.compareBytes(imageBytes, noImageBytesRef, imageBytes.length))
                warn(WarningType.DATA_LOSS, WarningStrings.IMAGE_REPLACED_WITH_PLACEHOLDER);

        }
        else
        {
            shape = new Shape(mBuilder.getDocument(), ShapeType.IMAGE);
            shape.setWrapType(WrapType.INLINE);
            shape.getImageData().setSourceFullName(UriUtil.constructUnescapedAbsoluteUri(mBaseUri, imageUri));
            mBuilder.insertNode(shape);
        }

        setShapeSize(shape, imageBytes, svgImageSize);

        // Here we should have one of the possibilities:
        // 1. Image created from HtmlResourceLoader
        // 2. Image created from uri directly(for corrupt images)
        // 3. Image with "no-image" picture
        // 4. Image created as linked image

        Debug.assertTrue(shape != null);

        // WORDSNET-8258 Handle 'alt' attribute.
        String altText = element.getAttributes().getAttributeValue("alt", "");
        shape.setShapeAttrInternal(ShapeAttr.SHAPE_DESCRIPTION, altText);
        // WORDSNET-5710 While converting html to doc, image properties not available.
        if (element.getAttributes().get("title") != null)
        {
            String titleText = element.getAttributes().getAttributeValue("title", "");
            shape.setShapeAttrInternal(ShapeAttr.IMAGE_TITLE, titleText);
        }

        // WORDSNET-21511 Remove border to prevent double border around an image.
        // Image borders are applied to image borders on VML shapes or to stroke on DML shapes. However, when the shape
        // is inserted, from font properties of the document builder are copied to RunPr of the shape, including borders.
        // As a result, the shape ends up with duplicate border: one from shape properties and another from font properties.
        // Since it is uneasy to not apply font borders or to not copy them to shape's RunPr, we decided to remove already
        // applied font borders from shapes afterwards. This fix doesn't look pretty and we might want to improve it later
        // but the results it produces look close to what is rendered in HTML browsers.
        if (shape.getRunPr().getBorder().isVisible())
            shape.getRunPr().remove(FontAttr.BORDER);

        mStyleResolver.toShape(shape);

        // WORDSNET-10097 Shape becomes shifted down after DOCX-HTMl-DOCX round-trip.
        String wrapTypeValue = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.IMAGE_WRAP_TYPE);
        /*WrapType*/int wrapType = 0;
        RefInt referenceToWrapType = new RefInt(wrapType);
        boolean outRefCondition4 = (!msString.equals(wrapTypeValue, "")) && HtmlUtil.parseWrapType(wrapTypeValue, /*out*/ referenceToWrapType);
        wrapType = referenceToWrapType.get();
        if (outRefCondition4)
        {
            shape.setWrapType(wrapType);
            String relHPosValue = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.IMAGE_RELATIVE_H_POS);
            /*RelativeHorizontalPosition*/int relHPos = 0;
            RefInt referenceToRelHPos = new RefInt(relHPos);
            boolean outRefCondition5 = (!msString.equals(relHPosValue, "")) && HtmlUtil.parseRelativeHorizontalPosition(relHPosValue, /*out*/ referenceToRelHPos);
            relHPos = referenceToRelHPos.get();
            if (outRefCondition5)
            {
                shape.setRelativeHorizontalPosition(relHPos);
                // WORDSNET-28986 Since shape alignment has higher priority than its relative position, we need to reset
                // its value to default.
                shape.setHorizontalAlignment(HorizontalAlignment.DEFAULT);
            }
            String relVPosValue = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.IMAGE_RELATIVE_V_POS);
            /*RelativeVerticalPosition*/int relVPos = 0;
            RefInt referenceToRelVPos = new RefInt(relVPos);
            boolean outRefCondition6 = (!msString.equals(relVPosValue, "")) && HtmlUtil.parseRelativeVerticalPosition(relVPosValue, /*out*/ referenceToRelVPos);
            relVPos = referenceToRelVPos.get();
            if (outRefCondition6)
            {
                shape.setRelativeVerticalPosition(relVPos);
                // WORDSNET-28986 Since shape alignment has higher priority than its relative position, we need to reset
                // its value to default.
                shape.setVerticalAlignment(VerticalAlignment.DEFAULT);
            }
            double topValue = mStyleResolver.getElementDeclarations().getLength(HtmlConstants.IMAGE_TOP_POS);
            if (!MathUtil.isMinValue(topValue))
                shape.setTop(topValue);
            double leftValue = mStyleResolver.getElementDeclarations().getLength(HtmlConstants.IMAGE_LEFT_POS);
            if (!MathUtil.isMinValue(leftValue))
                shape.setLeft(leftValue);
        }

        // WORDSNET-10651 Floating images are not affected by hyperlink fields. To make floating images hyperlinked
        // we must set the href of the shape. To find the hyperlink address, here we check only the parent element
        // of the image. Although it is enough for WORDSNET-10651, a more general solution would check all ancestors.
        if (!shape.isInline())
        {
            HtmlElementNode parent = element.getParent();
            if ((parent != null) && ("a".equals(parent.getName())))
            {
                String href = HtmlUtil.validateUri(parent.getAttributes().getAttributeValue("href", ""));
                if (StringUtil.hasChars(href))
                {
                    shape.setHRef(href);
                }
            }
        }

        // WORDSNET-16021 Image inside a table cell is cut if para line spacing equals to zero
        // and spacing rule is Multiple.
        if (mBuilder.getCurrentParagraph().isInCell() &&
            MathUtil.isZero(mBuilder.getCurrentParagraph().getParagraphFormat().getLineSpacing()) &&
            (mBuilder.getCurrentParagraph().getParagraphFormat().getLineSpacingRule() == LineSpacingRule.MULTIPLE))
        {
            // Change line spacing to 'Single'.
            mBuilder.getCurrentParagraph().getParagraphFormat().setLineSpacing(12.0);
        }
    }

    private void setShapeSize(
        Shape shape,
        byte[] imageBytes,
        SizeD svgImageSize) throws Exception
    {
        //Case in which there is no picture but there is its size and placeholder.
        if (imageBytes == null)
        {
            HtmlImageSizeCalculator size = new HtmlImageSizeCalculator(
                null,
                null,
                mStyleResolver.getElementDeclarations());
            if ((size.getCssWidth() > 0) || (size.getCssHeight() > 0))
            {
                shape.setSizeSafe(size.getCssWidth(), size.getCssHeight());
            }
            return;
        }

        ImageSizeCore originalSizeInfo = ImageUtil.getImageSize(imageBytes);
        SizeD originalSize = svgImageSize != null ? svgImageSize : imageSizeToSizeD(originalSizeInfo);

        SizeD containerSize = getContainerSize(shape);
        if (containerSize == null)
        {
            containerSize = ShapeSizeValidationHelper.getContainerSize(
                shape,
                (Paragraph)shape.getAncestor(NodeType.PARAGRAPH));
        }

        HtmlImageSizeCalculator imageSizeCalculator = new HtmlImageSizeCalculator(
            originalSize,
            containerSize,
            mStyleResolver.getElementDeclarations());
        /*CssDisplayType*/int parentDisplayType = mStyleResolver.parentElementDisplayType();
        SizeD imageSize = imageSizeCalculator.calculate(parentDisplayType);

        if ((imageSize != null) && imageSizeCalculator.hasAnyCssSizeProperties())
        {
            shape.setSizeSafe(imageSize.getWidth(), imageSize.getHeight());
        }
        // WORDSNET-23606 MS Word always use the original size of the image during import the alt-chunk document.
        else if (mSettings.ApplyFormattingAsMsWord)
        {
            if ((originalSize != null) &&
                (originalSizeInfo != null) &&
                (!MathUtil.areEqual(originalSize.getWidth(), shape.getWidth(), 1.0) ||
                !MathUtil.areEqual(originalSize.getHeight(), shape.getHeight(), 1.0)))
            {
                shape.setSizeSafe(originalSizeInfo.getWidthPoints(), originalSizeInfo.getHeightPoints());
            }
        }
        // WORDSNET-9671 Images with non-standard resolution (other than 96 dpi) have incorrect size after
        // they are imported from HTML. This happens because DocumentBuilder and HTML browsers use different resolutions
        // for such images. DocumentBuilder takes the actual resolution from image files, and HTML browsers always
        // use the standard (96 dpi) resolution. Here we resize images with non-standard resolution to make them
        // the same size as they are in HTML browsers.
        else if ((originalSizeInfo != null) &&
            (!MathUtil.areEqual(originalSizeInfo.getHorizontalResolution(), ImageConstants.STANDARD_RESOLUTION, 1.0) ||
            !MathUtil.areEqual(originalSizeInfo.getVerticalResolution(), ImageConstants.STANDARD_RESOLUTION, 1.0)))
        {
            shape.setSizeSafe(ConvertUtilCore.pixelToPoint(originalSizeInfo.getWidth(), ImageConstants.STANDARD_RESOLUTION),
                ConvertUtilCore.pixelToPoint(originalSizeInfo.getHeight(), ImageConstants.STANDARD_RESOLUTION));
        }
    }

    private static SizeD getContainerSize(Shape shape)
    {
        Paragraph parentParagraph = ms.as(shape.getParentNode(), Paragraph.class);
        if (parentParagraph != null)
        {
            Cell parentCell = parentParagraph.getParentCell();
            if (parentCell != null)
            {
                ParagraphFormat paragraphFormat = parentParagraph.getParagraphFormat();
                double paragraphIndents = paragraphFormat.getLeftIndent() + paragraphFormat.getRightIndent();

                CellFormat cellFormat = parentCell.getCellFormat();
                PreferredWidth cellWidth = cellFormat.getPreferredWidth();
                if (cellWidth.isFixed())
                {
                    return new SizeD(cellWidth.getValue() - paragraphIndents, ConvertUtilCore.MAX_SIZE_POINT);
                }
            }
        }

        return null;
    }

    private static SizeD imageSizeToSizeD(ImageSizeCore originalSize)
    {
        if (originalSize == null)
            return null;

        return new SizeD(
            ConvertUtil.pixelToPoint(originalSize.getWidth(), ImageConstants.STANDARD_RESOLUTION),
            ConvertUtil.pixelToPoint(originalSize.getHeight(), ImageConstants.STANDARD_RESOLUTION));
    }

    private boolean isImageInSupportedFormat(String resourceUri)
    {
        /*FileFormat*/int fileFormat = FileFormatCore.fromExt(UriUtil.getExtension(mBaseUri, resourceUri));
        if (fileFormat == FileFormat.UNKNOWN)
            return false;

        String contentType = FileFormatCore.toContentType(fileFormat);
        return msString.startsWith(contentType, "image/", StringComparison.ORDINAL);
    }

    /// <summary>
    /// Reads a horizontal rule. Accepts both HTML and CSS attributes.
    /// </summary>
    private void handleHorizontalRule(HtmlElementNode node) throws Exception
    {
        if (handleFootnoteEndnoteHorizontalRule(node))
            return;

        // WORDSNET-18289 Temporarily removes an HR element from CssStyleTracker to prevent element style applying
        // to an inserted paragraph.
        mStyleResolver.popElement();

        // HR always starts a new paragraph and requires another new paragraph to be started after it.
        mParagraphArranger.startParaIfNeeded();

        // WORDSNET-18289 Adds an HR element to CssStyleTracker after new paragraph adding.
        // We re-process the element and have already updated CSS counters on it earlier.
        mStyleResolver.pushElement(node, false);

        Shape shape = Shape.createHorizontalRule(mBuilder.getDocument());
        mBuilder.insertNode(shape);
        mStyleResolver.toHorizontalRule(shape);
        mParagraphArranger.isParaStartNeeded(true);
    }

    /// <summary>
    /// Handles horizontal rule span that is a footnote or endnote delimiter rule written by HTML Writer.
    /// </summary>
    /// <param name="node"></param>
    /// <returns><c>true</c> if the horizontal rule node has been processed successfully; <c>false</c> otherwise.</returns>
    private boolean handleFootnoteEndnoteHorizontalRule(HtmlElementNode node)
    {
        CssDeclarationCollection nodeDeclarations = mStyleResolver.getElementDeclarations();
        // This style is used for any footnote or endnote horizontal rule.
        if (!(MathUtil.areEqual(nodeDeclarations.getPercentage("width"), 33.0) &&
            MathUtil.areEqual(nodeDeclarations.getLength("height"), 0.75) &&
            ("left".equals(nodeDeclarations.getIdentifier("text-align")))))
        {
            return false;
        }

        int footnoteType = -1;
        int numberStyle = -1;
        int startNumber = -1;

        // Try to read footnote\endnote roundtrip information saved in new -aw-* format.
        double footnoteTypeValue = nodeDeclarations.getNumber(HtmlConstants.FOOTNOTE_TYPE);
        if (!MathUtil.isMinValue(footnoteTypeValue))
        {
            footnoteType = (int)footnoteTypeValue;
            double numberStyleValue = nodeDeclarations.getNumber(HtmlConstants.FOOTNOTE_NUMBER_STYLE);
            if (!MathUtil.isMinValue(numberStyleValue))
            {
                numberStyle = (int)numberStyleValue;
                double startNumberValue = nodeDeclarations.getNumber(HtmlConstants.FOOTNOTE_START_NUMBER);
                if (!MathUtil.isMinValue(startNumberValue))
                    startNumber = (int)startNumberValue;
            }
        }

        // Try to read footnote\endnote roundtrip information saved in old data-* format.
        if ((footnoteType == -1) && (numberStyle == -1) && (startNumber == -1))
        {
            footnoteType = node.getAttributes().getAttributeValue(HtmlConstants.FOOTNOTE_TYPE_ATTRIBUTE, -1);
            if (footnoteType != -1)
            {
                numberStyle = node.getAttributes().getAttributeValue(HtmlConstants.FOOTNOTE_NUMBER_STYLE_ATTRIBUTE, -1);
                if (numberStyle != -1)
                    startNumber = node.getAttributes().getAttributeValue(HtmlConstants.FOOTNOTE_START_NUMBER_ATTRIBUTE, -1);
            }
        }

        if ((footnoteType != -1) && (numberStyle != -1) && (startNumber != -1))
        {
            switch (footnoteType)
            {
                case (int)FootnoteType.FOOTNOTE:
                {
                    mBuilder.getDocument().getFootnoteOptions().setNumberStyle((/*NumberStyle*/int)numberStyle);
                    mBuilder.getDocument().getFootnoteOptions().setStartNumber(startNumber);
                    break;
                }
                case (int)FootnoteType.ENDNOTE:
                {
                    mBuilder.getDocument().getEndnoteOptions().setNumberStyle((/*NumberStyle*/int)numberStyle);
                    mBuilder.getDocument().getEndnoteOptions().setStartNumber(startNumber);
                    break;
                }
                default:
                {
                    // Ignore unknown values.
                    break;
                }
            }
        }
        return true;
    }

    private void warnIfDocumentIsFixedPageHtml()
    {
        // WORDSNET-25035 Detect if we're loading a fixed-page HTML document and warn customers about formatting loss.
        if (mFixedPageFormatDetector.isFixedPageHtml())
        {
            warn(WarningType.MAJOR_FORMATTING_LOSS,
                "The document is fixed-page HTML. Its structure may not be loaded correctly.");
        }
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(/*WarningType*/int warningType, String description)
    {
        if (mBuilder.getDocument().getWarningCallback() != null)
            mBuilder.getDocument().getWarningCallback().warning(new WarningInfo(warningType, WarningSource.HTML, description));
    }

    /// <summary>
    /// Determines whether the text node contains anything but ignored whitespaces.
    /// </summary>
    private boolean containsMeaningfulText(HtmlTextNode textNode)
    {
        if (mStyleResolver.isPreformatted() || mStyleResolver.isPreformattedWithWrap())
        {
            Debug.assertTrue(StringUtil.hasChars(textNode.getText()));
            return true;
        }
        boolean newlinesAreWhitespaces = !mStyleResolver.isPreformattedWithLine();
        return HtmlUtil.containsAnythingButWhitespaces(textNode.getText(), newlinesAreWhitespaces);
    }

    /// <summary>
    /// Remove empty paragraphs at the end of sections, starting from the specified section.
    /// </summary>
    /// <remarks>
    /// The paragraph should be removed if:
    /// - it's the last paragraph in a section;
    /// - it's not the first paragraph after a table;
    /// - it's not explicitly marked empty in HTML using AW-specific CSS properties;
    /// - it's not a section break paragraph;
    /// - it's not a list item;
    /// - it's not the only paragraph in the section.
    /// </remarks>
    private void removeEmptyParagraphs(Section firstSection)
    {
        Debug.assertTrue(firstSection != null);

        Paragraph builderParagraph = mBuilder.getCurrentParagraph();
        boolean builderParagraphRemoved = false;
        Body lastSectionBody = null;
        Section section = firstSection;
        while (section != null)
        {
            lastSectionBody = section.getBody();

            while (true)
            {
                Paragraph lastParagraph = ms.as(lastSectionBody.getLastChild(), Paragraph.class);
                if ((lastParagraph == null) || lastParagraph.isFirstChild())
                {
                    break;
                }

                Node previousNode = lastParagraph.getPreviousNonAnnotationSibling();
                boolean isRightAfterTable = (previousNode != null) && (previousNode.getNodeType() == NodeType.TABLE);

                if (lastParagraph.hasChildNodes() ||
                    mParagraphArranger.isParagraphEmptyInHtml(lastParagraph) ||
                    isRightAfterTable ||
                    lastParagraph.isSectionBreakParagraph() ||
                    lastParagraph.isListItem())
                {
                    break;
                }

                if (lastParagraph == builderParagraph)
                {
                    builderParagraphRemoved = true;
                }
                lastParagraph.remove();
            }

            section = ms.as(section.getNextSibling(), Section.class);
        }
        // WORDSNET-22736 Removing the last paragraph resets the cursor position of the document builder, so we must move
        // the cursor to the end of the document. This is to make sure that for multi-part documents (MHTML) the next part
        // is imported after the end of the current part.
        if (builderParagraphRemoved)
        {
            // Append a temporary paragraph to the last section and use it as a starting point to find the actual last
            // paragraph of the section. This looks silly but there doesn't seem to exist other efficient and obvious way
            // of doing it.
            Paragraph dummyParagraph = lastSectionBody.appendParagraph("");
            Paragraph lastParagraphOfLastSectionBody = (Paragraph)dummyParagraph.previousPreOrderOfType(
                lastSectionBody,
                NodeType.PARAGRAPH);
            dummyParagraph.remove();

            // WORDSNET-27419 SPEED We used to always call DocumentBuilder.MoveToDocumentEnd() here to move the cursor
            // to the end of the document. However, this method proved to be increasingly slow in the scenario where we
            // import a large number of HTML documents appending them one after another in a single document. This is what
            // we do when we load CHM or EPUB. Now we use another technique to quickly find the last paragraph of
            // the document and move the builder's cursor there.
            if (lastParagraphOfLastSectionBody != null)
            {
                mBuilder.moveTo(lastParagraphOfLastSectionBody);
            }
            else
            {
                // If something strange happens and we cannot find a paragraph to move the cursor to, we resort to invoking
                // DocumentBuilder.MoveToDocumentEnd(). It will be slow but it is able to correctly handle most situations
                // that may occur.
                mBuilder.moveToDocumentEnd();
            }
        }
    }

    private void updateSdtNodeBidiLocale()
    {
        // Check if there is a block-level SDT.
        StructuredDocumentTag sdt = ms.as(mBuilder.getCurrentParagraph().getParentNode(), StructuredDocumentTag.class);
        if (sdt == null)
        {
            // Check if there is an inline-level SDT.
            sdt = ms.as(mBuilder.getCurrentParagraph().getFirstChild(), StructuredDocumentTag.class);
        }
        if (sdt == null)
        {
            // No SDT node found. Nothing to process.
            return;
        }

        updateSdtNodeBidiLocale(sdt);
    }

    private static void updateSdtNodeBidiLocale(StructuredDocumentTag sdt)
    {
        if (sdt.getContentsRunPr().getDirectAttr(FontAttr.LOCALE_ID_BI) != null)
        {
            return;
        }

        Node firstChildOfSdt = sdt.getFirstChild();
        if (firstChildOfSdt == null)
        {
            return;
        }

        if (firstChildOfSdt.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
        {
            // Process a nested structured document tag.
            StructuredDocumentTag nestedSdt = (StructuredDocumentTag)firstChildOfSdt;
            updateSdtNodeBidiLocale(nestedSdt);
            updateSdtRunPrBidiLocale(sdt.getContentsRunPr(), nestedSdt.getContentsRunPr());
        }
        else if (firstChildOfSdt.getNodeType() == NodeType.PARAGRAPH)
        {
            // Process a block-level structured document tag.
            // The first run may be in the paragraph or in the nested structured document tag.
            Run firstRun = ((Paragraph)firstChildOfSdt).getFirstRun();
            if (firstRun != null)
            {
                updateSdtRunPrBidiLocale(sdt.getContentsRunPr(), firstRun.getRunPr());
            }
        }
        else if (firstChildOfSdt.getNodeType() == NodeType.RUN)
        {
            // Process an inline-level structured document tag.
            updateSdtRunPrBidiLocale(sdt.getContentsRunPr(), ((Run)firstChildOfSdt).getRunPr());
        }
    }

    private String getHeaderFooterTypeValue(HtmlElementNode node)
    {
        String headerFooterType = mStyleResolver.getElementDeclarations().getIdentifier(HtmlConstants.HEADER_FOOTER_TYPE);
        if (msString.equals(headerFooterType, ""))
            headerFooterType = node.getAttributes().getAttributeValue(HtmlConstants.HEADER_FOOTER_TYPE_ATTRIBUTE, "");

        return headerFooterType;
    }

    /// <summary>
    /// Sets the bidi locale of a SDT to match the bidi locale of the first run inside it.
    /// </summary>
    private static void updateSdtRunPrBidiLocale(RunPr sdtRunPr, RunPr referenceRunPr)
    {
        if (!referenceRunPr.getBidi().toBool())
            return;

        // Always set bidi if the reference run is bidi.
        sdtRunPr.setBidi(AttrBoolEx.TRUE);

        if (referenceRunPr.getLocaleIdBi() == (int)Language.HEBREW_ISRAEL)
        {
            sdtRunPr.setLocaleIdBi(referenceRunPr.getLocaleIdBi());
        }
    }

    /// <summary>
    /// Returns a value indicating whether an HTML element always separates bidi contexts before and after them.
    /// </summary>
    /// <remarks>
    /// Most inline elements are transparent to bidirectional text reordering. However, some elements always act
    /// as bidi context separators.
    /// </remarks>
    private static boolean isBidiContextSeparator(HtmlElementNode element)
    {
        return (NrxNamespaces.VML.equals(element.getNamespace())) ||
               (msArray.indexOf((G_BIDI_CONTEXT_SEPARATOR_ELEMENTS), (element.getName()), 0, (G_BIDI_CONTEXT_SEPARATOR_ELEMENTS).length) >= 0);
    }

    private static boolean tryParseHeaderFooterType(String headerFooterTypeValue, /*out*/RefInt headerFooterType)
    {
        switch (gStringSwitchMap.of(headerFooterTypeValue))
        {
            case /*HtmlConstants.HEADER_FOOTER_TYPE_HEADER_PRIMARY*/49:
                headerFooterType.set(HeaderFooterType.HEADER_PRIMARY);
                break;
            case/*HtmlConstants.HEADER_FOOTER_TYPE_HEADER_FIRST*/50:
                headerFooterType.set(HeaderFooterType.HEADER_FIRST);
                break;
            case/*HtmlConstants.HEADER_FOOTER_TYPE_FOOTER_PRIMARY*/51:
                headerFooterType.set(HeaderFooterType.FOOTER_PRIMARY);
                break;
            case/*HtmlConstants.HEADER_FOOTER_TYPE_FOOTER_FIRST*/52:
                headerFooterType.set(HeaderFooterType.FOOTER_FIRST);
                break;
            default:
                // Set default value.
                headerFooterType.set(HeaderFooterType.HEADER_EVEN);
                return false;
        }
        return true;
    }

    private HtmlListReader getCurrentListReader() { return mListReaderManager.getCurrentListReader(); }

    /// <summary>
    /// Features that are considered supported when loading HTML documents. These features affect parsing of IE conditional
    /// comments.
    /// </summary>
    private /*final*/ Features mSupportedFeatures;

    /// <summary>
    /// Caches all loaded resources and provides access to them which is independent on whether a resource already loaded or not yet.
    /// </summary>
    private /*final*/ HtmlResourceLoader mResourceLoader;

    /// <summary>
    /// Always have value of concrete load format unlike LoadOptions.LoadFormat which can have LoadFormat.Auto value.
    /// </summary>
    private /*final*/ /*LoadFormat*/int mLoadFormat;

    /// <summary>
    /// The last checked node in method IsLastCharacterSpaceOrBR() for current paragraph.
    /// Used for quick search of last node in paragraph. See WORDSNET-18910.
    /// In the future we can consider checking last added character when creating a run or adding a break.
    /// </summary>
    private Node mLastNodeInCurParagraph;

    /// <summary>
    /// We use this builder to build the document.
    /// </summary>
    private DocumentBuilder mBuilder;

    /// <summary>
    /// Can be given in the constructor or read from HTML. Used to construct full path for images that specify relative path.
    /// </summary>
    private String mBaseUri;

    private int mTableNestingLevel;

    private /*final*/ HtmlListReaderManager mListReaderManager;

    private HtmlFixedWidthSpanReader mFixedWidthSpanReader;

    /// <summary>
    /// Indicates that text of the HTML document flows in visual order
    /// (RTL text is reordered and reversed manually by an author of the document).
    /// </summary>
    private boolean mTextIsInVisualOrder;
    private CssResolver mCssResolver;
    private CssStyleTracker mCssStyleTracker;
    private DocumentFormatter mStyleResolver;
    private HtmlElementCategorizer mElementCategorizer;
    private ArrayList<CssStyleRule> mCssRules;
    private /*final*/ HtmlCommonBorderResolver mCommonBorderResolver;
    private boolean mIsIgnoreHyperlinkContent;
    private boolean mIsFootnoteReferenceText;
    private Footnote mCurrentFootnote;
    private /*final*/ IntToObjDictionary<Comment> mComments;
    private /*final*/ IntToObjDictionary<CommentRangeEnd> mCommentRangeEnds;
    private /*final*/ ArrayList<Integer> mFullyProcessedComments;
    private /*final*/ IntToObjDictionary<Footnote> mFootnotes;
    private /*final*/ IntToObjDictionary<Footnote> mEndnotes;

    /// <summary>
    /// Indicates whether HTML reader is allowed to modify document-wide formatting, such as page setup or existing document
    /// styles.
    /// </summary>
    private boolean mApplyDocumentWideFormatting;

    /// <summary>
    /// Indicates that font and paragraph formatting specified in the target document builder should be used as base
    /// formatting for content inserted from HTML.
    /// </summary>
    private boolean mUseDocumentBuilderFormatting;

    /// <summary>
    /// Base paragraph formatting to which all paragraph formatting imported from CSS is applied.
    /// If formatting merging is enabled, base formatting is the formatting in the point of a document where an HTML
    /// fragment is being inserted.
    /// If formatting merging is disabled, base formatting is empty.
    /// </summary>
    private ParaPr mBaseParaPr;

    /// <summary>
    /// Left indent of a paragraph where an HTML fragment is being inserted into (in case the reader is being executed
    /// as a result of calling <see cref="DocumentBuilder.InsertHtml(string)"/>. This value is used to correctly indent
    /// imported list items. If we convert a whole HTML document, this indent is zero and doesn't affect the result.
    /// </summary>
    private double mBaseParagraphLeftIndent;

    /// <summary>
    /// Base font formatting to which all font formatting imported from CSS is applied.
    /// If formatting merging is enabled, base formatting is the font formatting in the point of a document where an HTML
    /// fragment is being inserted.
    /// If formatting merging is disabled, base formatting is empty.
    /// </summary>
    private RunPr mBaseRunPr;

    /// <summary>
    /// Buffer for bidi text reordering.
    /// </summary>
    /// <remarks>
    /// Accumulates text of adjacent inline elements, reorders it using the Unicode bidirectional algorithm and writes
    /// reordered text to document builder.
    /// </remarks>
    private HtmlBidiTextArranger mBidiTextArranger;

    /// <summary>
    /// Elements that are not transparent to bidirectional text reordering and separate inline bidi contexts before
    /// and after them.
    /// </summary>
    private static /*final*/ String[] G_BIDI_CONTEXT_SEPARATOR_ELEMENTS = new String[]
    {
        // Breaks introduce new paragraphs with respect to the Unicode bidirectional algorithm.
        "br",
        // The following elements should be transparent to bidi reordering when they are inline,
        // but we do not support this yet.
        "input",
        "img",
        "svg",
        "math",
        "object",
        "embed",
        "iframe",
        "select",
        "option",
        "textarea",
        // Anchor boundaries are hard to preserve during reordering by the Unicode bidirectional algorithm (UBA),
        // so at the moment anchor contents are isolated from the UBA to make sure it is reordered as a whole.
        "a",
        // Ruby is bidi separator in IE and MS Word, but seems not in Firefox and Chrome.
        "ruby",
        // There is no counterpart for inline tables in our document model. Tables are always imported as blocks.
        "table",
        // In HTML, 'display:inline' seems to have no effect on table parts. They are always rendered as blocks.
        "caption",
        "th",
        "td"
    };

    /// <summary>
    /// Indicates whether the specified "href" is a valid address for a hyperlink in the source document.
    /// </summary>
    private boolean isValidHyperlinkHref(String href) throws Exception
    {
        return (mSettings.HyperlinkProcessor == null) ||
            mSettings.HyperlinkProcessor.isValidHyperlinkHref(href);
    }

    private static boolean isImplicitListDisplayType(/*CssDisplayType*/int displayType)
    {
        return (displayType == CssDisplayType.INLINE) ||
            (displayType == CssDisplayType.TABLE_HEADER_GROUP) ||
            (displayType == CssDisplayType.TABLE_FOOTER_GROUP) ||
            (displayType == CssDisplayType.TABLE_ROW) ||
            (displayType == CssDisplayType.TABLE_ROW_GROUP) ||
            (displayType == CssDisplayType.INLINE_BLOCK);
    }

    /// <summary>
    /// Indicates whether we have ignored any script nodes yet and warned the user about it.
    /// </summary>
    private boolean mIgnoredAnyScriptNodes;

    private boolean mPostponedPageBreakAfterAlways;
    private HtmlVmlShapeReader mVmlShapeReader;
    private HtmlRubyReader mRubyReader;
    private HtmlFontFallbackUtil mHtmlFontFallbackUtil;

    /// <summary>
    /// Settings that change certain aspects of the HTML loading process.
    /// </summary>
    private /*final*/ HtmlReaderSettings mSettings;

    /// <summary>
    /// Imports 'input' and 'select' elements as Form Fild/SDT nodes.
    /// </summary>
    private HtmlControlReader mHtmlControlReader;

    /// <summary>
    /// Contains information about 'option' elements required for 'select' element import.
    /// </summary>
    private /*final*/ ArrayList<HtmlOptionElementInfo> mOptionElements = new ArrayList<HtmlOptionElementInfo>();

    private HtmlParagraphArranger mParagraphArranger;

    /// <summary>
    /// Remembers created field start and separator nodes. Pops on field end.
    /// </summary>
    private /*final*/ msStack<FieldChar> mFieldNodesStack;

    private /*final*/ HtmlBlockReader mHtmlBlockReader;

    private /*final*/ HtmlHyperlinkResolver mHtmlHyperlinkResolver;

    private /*final*/ HtmlFixedPageDetector mFixedPageFormatDetector;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"html",
		"head",
		"title",
		"meta",
		"base",
		"script",
		"noscript",
		"style",
		"link",
		"body",
		"input",
		"select",
		"option",
		"a",
		"br",
		"img",
		"svg",
		"math",
		"embed",
		"object",
		"iframe",
		"plaintext",
		"textarea",
		"frameset",
		"table",
		"thead",
		"tbody",
		"tfoot",
		"colgroup",
		"col",
		"th",
		"td",
		"caption",
		"ul",
		"ol",
		"ruby",
		"rp",
		"rt",
		"dl",
		"dt",
		"dd",
		"ins",
		"del",
		"mbp:pagebreak",
		"div",
		"hr",
		"always",
		"left",
		"right",
		"header-primary",
		"header-first",
		"footer-primary",
		"footer-first"
	);

}

