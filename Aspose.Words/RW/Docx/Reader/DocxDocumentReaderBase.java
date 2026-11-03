// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.OpcPackageBase;
import com.aspose.OpcPackagePart;
import com.aspose.Debug;
import com.aspose.ms.System.msObject;
import com.aspose.StringUtil;
import com.aspose.FileSystem;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ImageUtil;
import com.aspose.FileFormatCore;
import com.aspose.ImageSizeCore;
import com.aspose.CompressedData;
import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.OpcContentType;
import com.aspose.ms.System.IO.Stream;
import com.aspose.BitmapUtilPal;
import com.aspose.CantCreateBitmapException;
import com.aspose.StreamUtil;
import com.aspose.OpcRelationship;
import java.util.Map;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.IntToObjDictionary;
import java.util.HashMap;
import com.aspose.words.ref.Ref;


abstract class DocxDocumentReaderBase extends NrxDocumentReaderBase implements INrxDmlReader
{
    protected DocxDocumentReaderBase(
        OpcPackageBase _package,
        OpcPackagePart documentPart,
        DocumentBase doc,
        LoadOptions loadOptions,
        OoxmlComplianceInfo complianceInfo,
        DocxStylesReader stylesReader,
        DocxStoryReader storyReader,
        DocxNumberingReader numberingReader,
        DocxSectPrReader sectPrReader) throws Exception
    {
    	super(doc, loadOptions, WarningSource.DOCX, complianceInfo, storyReader, sectPrReader);
	
        Debug.assertTrue(_package != null);
        Debug.assertTrue(documentPart != null);
        Debug.assertTrue(stylesReader != null);
        Debug.assertTrue(numberingReader != null);
        Debug.assertTrue(sectPrReader != null);

        mPackage = _package;
        mDocumentPart = documentPart;
        mStylesReader = stylesReader;
        mNumberingReader = numberingReader;
        mXmlReader = new DocxXmlReader(mDocumentPart, loadOptions.getWarningCallback(), WarningSource.DOCX, complianceInfo);
        mParaToCommentMapper = new ParagraphToCommentMapper();
    }

    void read() throws Exception
    {
        DocxFontTableReader.read(this);
        DocxSettingsReader.read(this);
        DocxWebSettingsReader.read(this);
        mStylesReader.read(this);
        mNumberingReader.read(this);
        DocxPeopleReader.read(this);
        DocxCommentsExReader.read(this);
        DocxCommentsIdsReader.read(this);
        DocxCommentsExtensibleReader.read(this);
        DocxCommentsReader.read(this);
        mFootnotesReader = new DocxFootnotesReader(this);

        doRead();

        for (Style style : getDocument().getStyles())
            FormatRevisionUtil.convertToPositiveDifference(style);

        updateCommentTree();
        updateZOrders();
        resolveDuplicateShapeIds();
        updateTextboxNextShapeIds();
        updateDmlNextShapeIds();
        resolveDocPrStyles();

        insertAltChunks();
        getDocument().getLists().fixUpListsWithCircularReferences();
        ensureStylePointsToExistingList();
        ensureUsedListsExist();
        updateTablesWithMissedTableGrid();
        updateConnectors();
        getDocument().getLists().clearListStyleReferencesLevels();

        resolveToggleIssues();
    }

    /// <summary>
    /// Maps the specified ParaId and DurableId.
    /// </summary>
    void mapParaIdAndDurableId(int paraId, int durableId)
    {
        getParaToCommentMapper().mapParaIdAndDurableId(paraId, durableId);
    }

    /// <summary>
    /// Maps the specified ParaId and paragraph. Resolves duplicate ParaIds used in comments if needed.
    /// </summary>
    void mapParaId(int paraId, Paragraph para)
    {
        Debug.assertTrue(para != null);

        boolean isInComment = (para.getAncestor(NodeType.COMMENT) != null);

        if (isInComment)
            paraId = getParaToCommentMapper().mapParaIdAndResolveDuplicateIfNeeded(paraId);

        para.setParaId(paraId);

        if (isInComment)
            getParaToCommentMapper().mapParaIdAndParagraph(paraId, para);
    }

    /// <summary>
    /// Maps the specified ParaId and ParaId of the parent paragraph.
    /// </summary>
    void mapParaIdAndParentParaId(int paraId, int paraIdParent, boolean done)
    {
        getParaToCommentMapper().mapParaIdAndParentParaId(paraId, paraIdParent, done);
    }

    /// <summary>
    /// Finds a <see cref="CommentExtensible"/> object stored on reading commentsIds document part.
    /// </summary>
    CommentExtensible findCommentExtensible(int durableId)
    {
        return getParaToCommentMapper().findCommentExtensible(durableId);
    }

    DocxRelationshipTypes getRelTypes()
    {
        if (mRelTypes == null)
            mRelTypes = new DocxRelationshipTypes(getComplianceInfo().isIsoStrict());
        return mRelTypes;
    }

    /// <summary>
    /// Ensure list style points to existing list.
    /// When definition of a list points to some style but this style points to unexisting list,
    /// we have to mimic MS Word behavior and make this style pointing to the list that uses this definition.
    /// </summary>
    private void ensureStylePointsToExistingList()
    {
        for (List list : getDocument().getLists())
        {
            fixListStyle(list);
        }
    }

    /// <summary>
    /// Determines TerminalList for the source List.
    /// </summary>
    private List getTerminalList(List list)
    {
        if (list == null)
            return null;

        if (list.getStyle() == null)
            return list;

        List styleList = getDocument().getLists().getListByListId(list.getStyle().getParaPr().getListId());
        if (styleList == null)
            return null;

        if (styleList.getListId() == list.getListId())
            return list;

        return getTerminalList(styleList);
    }

    /// <summary>
    /// Ensure list style points to an existing list, otherwise fixes the list.
    /// </summary>
    private void fixListStyle(List list)
    {
        Style style = getDocument().getStyles().getByIstd(list.getListDef().getListStyleIstd(), false);
        if ((style != null) && (style.getType() == StyleType.LIST) && (style.getIstd() != StyleIndex.NO_LIST))
        {
            List styleList = getDocument().getLists().getListByListId(style.getParaPr().getListId());

            List terminalStyleList = getTerminalList(styleList);
            if (terminalStyleList == null)
                style.getParaPr().setListId(list.getListId());

            // WORDSNET-11410 MS Word removes reference to style from the list definition if
            // list definition of this style hasn't reference to the style.
            if ((styleList != null) && (styleList.getListDef().getListStyleIstd() == StyleIndex.NO_LIST))
            {
                list.getListDef().setListStyleIstd(StyleIndex.NO_LIST);
            }
        }
    }

    /// <summary>
    /// Import and insert altChunk content to the model.
    /// </summary>
    private void insertAltChunks() throws Exception
    {
        if (mAltChunkCollection.size() > 0)
        {
            // WORDSNET-18541 Word creates missed in main document reserved styles from the scratch even if they are
            // importing with KeepSourceStyles mode.
            getDocument().getStyles().ensureMinimum();

            for (int i = 0; i < mAltChunkCollection.size(); i++)
            {
                DocxAltChunk altChunk = mAltChunkCollection.get(i);
                Document altChunkDocument = altChunk.getAltChunkDocument();
                CompositeNode referenceContainer = altChunk.getReferenceContainer();
                Node referenceNode = altChunk.getReferenceNode();
                /*ImportFormatMode*/int importFormatMode = altChunk.getImportFormatMode();
                boolean isInline = altChunk.isInline();

                boolean isFirstChildInBody = false;
                Node refParent = referenceContainer.getParentNode();
                if ((refParent != null) && (refParent.getNodeType() == NodeType.BODY))
                    isFirstChildInBody = msObject.referenceEquals(((Body)refParent).getFirstChild(), referenceContainer);

                // WORDSNET-16555 Word takes in attention headers/footers of "AltChunk" document, which placed
                // as first child in the section body. Related tests: TestJira7382, TestJira11451.
                // WORDSNET-20804 Word also always processes headers/footers of multi-section AltChunk documents.
                if (isFirstChildInBody || (altChunkDocument.getSections().getCount() > 1))
                    resolveHeadersFooters(altChunkDocument, referenceContainer, importFormatMode);

                if (isInline)
                    copyInlineAltChunkContent(altChunkDocument, referenceContainer, referenceNode);
                else
                    copyAltChunkContent(altChunkDocument, referenceContainer, importFormatMode);

                if (isFirstChildInBody && (altChunkDocument.getSections().getCount() > 1))
                    postProcessHeadersFooters(altChunkDocument, referenceContainer, importFormatMode);

                // WORDSNET-23496 Copy all HTML blocks from altChunk documents to the model.
                // Some HTML blocks may be duplicated in different altChunks. HTML block collection resolves duplicates.
                getDocument().getHtmlBlockCollection().add(altChunkDocument.getHtmlBlockCollection());

                // Remove unneeded container.
                referenceContainer.remove();

                warn(WarningType.DATA_LOSS, WarningSource.DOCX, "The altChunk is replaced with its content.");
            }

            mAltChunkCollection.clear();
        }
    }

    /// <summary>
    /// AltChunk might occur on inline level, in this case all altChunk content should be inserted after reference node.
    /// </summary>
    private static void copyInlineAltChunkContent(
        Document altChunkDocument,
        CompositeNode referenceContainer,
        Node referenceNode) throws Exception
    {
        // Try to get paragraph which contains altChunk.
        Paragraph paragraphWithAltChunk = ms.as(referenceContainer.getPreviousNonAnnotationSibling(), Paragraph.class);

        // Try to get SDT which contains altChunk.
        StructuredDocumentTag sdtWithAltChunk = ms.as(referenceContainer.getPreviousNonAnnotationSibling(), StructuredDocumentTag.class);

        // Mimic MS Word behavior, use destination styles upon copying inline AltChunkContent.
        copyAltChunkContent(altChunkDocument, referenceContainer, ImportFormatMode.USE_DESTINATION_STYLES);

        if (paragraphWithAltChunk != null)
        {
            // Get first paragraph inserted from altChunk.
            Paragraph firstAltChunkParagraph = ms.as(paragraphWithAltChunk.getNextNonAnnotationSibling(), Paragraph.class);

            // WORDSNET-18893 Preserve frame attrs from paragraph with altChunk.
            if (firstAltChunkParagraph != null)
                mirrorFrameAttrs(paragraphWithAltChunk, firstAltChunkParagraph);

            // We have to clear this paragraph formatting, because it will be inherited from
            // the first AltChunk paragraph.
            paragraphWithAltChunk.getParagraphFormat().clearFormatting();

            if (firstAltChunkParagraph != null)
            {
                // WORDSNET-10134 Preserve ParaPr from the first AltChunk paragraph.
                paragraphWithAltChunk.setParaPr(AttrCollectionExtension.deepClone(firstAltChunkParagraph.getParaPr()));
                paragraphWithAltChunk.setParagraphBreakRunPr(AttrCollectionExtension.deepClone(firstAltChunkParagraph.getParagraphBreakRunPr()));

                while (firstAltChunkParagraph.hasChildNodes())
                    paragraphWithAltChunk.insertAfter(firstAltChunkParagraph.getLastChild(), referenceNode);

                // Move annotations between the paragraphs into the left one.
                paragraphWithAltChunk.insertAfter(paragraphWithAltChunk.getNextSibling(),
                    firstAltChunkParagraph, referenceNode);

                firstAltChunkParagraph.remove();
            }
        }

        if (sdtWithAltChunk != null)
        {
            // Get first paragraph inserted from altChunk.
            Paragraph sdtParagraph = ms.as(sdtWithAltChunk.getParentNode().getNextNonAnnotationSibling(), Paragraph.class);

            if (sdtParagraph != null)
            {
                while (sdtParagraph.hasChildNodes())
                    sdtWithAltChunk.insertAfter(sdtParagraph.getLastChild(), referenceNode);

                // Move annotations between the paragraphs into the left one.
                sdtWithAltChunk.insertAfter(sdtWithAltChunk.getParentNode().getNextSibling(),
                    sdtParagraph, referenceNode);

                // WORDSNET-19346 Copy the style of the first altChunk para to the parent sdt para.
                Paragraph sdtParentPara = ms.as(sdtWithAltChunk.getParentNode(), Paragraph.class);

                // WORDSNET-24805 Resiliency, do not assign wrong type style.
                if ((sdtParentPara != null) && (sdtParagraph.getParagraphStyle().getType() == StyleType.PARAGRAPH))
                    sdtParentPara.getParagraphFormat().setStyle(sdtParagraph.getParagraphStyle());

                sdtParagraph.remove();
            }
        }
    }

    private static void mirrorFrameAttrs(Paragraph srcPara, Paragraph dstPara)
    {
        srcPara.getParaPr().mirrorTo(dstPara.getParaPr(), ParaPr.FLOATING_ATTRS);
        if (srcPara.getParaPr().containsAnyKey(ParaPr.FLOATING_ATTRS))
            srcPara.getParaPr().mirrorTo(dstPara.getParaPr(), ParaPr.BORDER_ATTRS);
    }

    /// <summary>
    /// Copy content of AltChunk document to destination document.
    /// </summary>
    private static void copyAltChunkContent(
        Document altChunkDocument,
        CompositeNode referenceContainer,
        /*ImportFormatMode*/int importFormatMode) throws Exception
    {
        // WORDSNET-28630 Ensure minimum altChunk document.
        altChunkDocument.ensureMinimum();

        // WORDSNET-16262 Validate current content of the last section body before insertion to avoid exception.
        Body lastSectBody = altChunkDocument.getLastSection().getBody();
        lastSectBody.ensureMinimum();

        NodeRange sourceRange = new NodeRange(altChunkDocument.getFirstSection().getBody().getFirstChild(), lastSectBody.getLastChild());

        ExternalDocumentModifier modifier =
            new ExternalDocumentModifier(altChunkDocument, referenceContainer.getDocument(), importFormatMode);
        NodeCopier.copy(sourceRange, referenceContainer, modifier, null, NodeCopierOptions.NONE);
    }

    /// <summary>
    /// Concatenate AltChunk headers/footers with destination headers/footers.
    /// This is experimental code to mimic MS Word behavior.
    /// </summary>
    private static void resolveHeadersFooters(Document altChunkDocument, CompositeNode referenceContainer, /*ImportFormatMode*/int importFormatMode) throws Exception
    {
        NodeImporter srcToDstImporter = new NodeImporter(altChunkDocument, referenceContainer.getDocument(), importFormatMode);

        Section dstSection = (Section)referenceContainer.getAncestor(NodeType.SECTION);

        // Copy header/footer from the first section of source document to destination document if headers/footers exist.
        if (altChunkDocument.getFirstSection().getHeadersFooters().getCount() > 0)
            appendPrependHeadersFooters(altChunkDocument.getFirstSection().getHeadersFooters(), dstSection, srcToDstImporter, true);

        //If AltChunk document contains more then one section and there is section after the current section
        //in destination document, we need to copy headers/footers from the next section
        //of destination document to all (except first) sections of source document. (Mimic MS Word behavior)
        if ((altChunkDocument.getSections().getCount() > 1) && (dstSection.getNextSibling() != null))
        {
            NodeImporter dstToSrcImporter = new NodeImporter(referenceContainer.getDocument(), altChunkDocument, importFormatMode);

            // Section that follows the destination section.
            Section nextSection = (Section)dstSection.getNextSibling();

            for (Section section : (Iterable<Section>) altChunkDocument.getSections())
            {
                if (section.isFirstSection() || section.isLastChild())
                    continue;

                appendPrependHeadersFooters(nextSection.getHeadersFooters(), section, dstToSrcImporter, false);
            }
        }
    }

    /// <summary>
    /// Copy header/footer from the last section of AltChunk document to the last inserted section in the document.
    /// </summary>
    private static void postProcessHeadersFooters(Document altChunkDocument, CompositeNode referenceContainer, /*ImportFormatMode*/int importFormatMode) throws Exception
    {
        Section lastInsertedSection = (Section)referenceContainer.getAncestor(NodeType.SECTION);
        NodeImporter srcToDstImporter = new NodeImporter(altChunkDocument, referenceContainer.getDocument(), importFormatMode);
        appendPrependHeadersFooters(altChunkDocument.getLastSection().getHeadersFooters(), lastInsertedSection, srcToDstImporter, true);
    }

    /// <summary>
    /// Append or prepend the content of source header/footer to destination header/footer, or just add them.
    /// </summary>
    /// <param name="headersFooters">Header/footer collection of source section.</param>
    /// <param name="dstSection">Destination section.</param>
    /// <param name="nodeImporter">Node importer.</param>
    /// <param name="addToEnd">Indicate whether content of source header/footer must be added to the end of destination header/footer.</param>
    private static void appendPrependHeadersFooters(HeaderFooterCollection headersFooters, Section dstSection, NodeImporter nodeImporter, boolean addToEnd) throws Exception
    {
        for (HeaderFooter headerFooter : (Iterable<HeaderFooter>) headersFooters)
        {
            HeaderFooter sourceHeaderFooter = (HeaderFooter)nodeImporter.importNode(headerFooter, true);

            // Get specific HeaderFooter of current section.
            HeaderFooter destinationHeaderFooter = dstSection.getHeadersFooters().getByHeaderFooterType(headerFooter.getHeaderFooterType());

            // If header/footer of specific type exists in current section of destination document,
            // then just append/prepend the content of source header/footer to it. Else add whole source header/footer.
            if (destinationHeaderFooter != null)
            {
                if (addToEnd)
                {
                    // Append empty paragraph - mimic MS Word behavior.
                    destinationHeaderFooter.appendParagraph("");
                    nodeImporter.importAndAppendChildren(headerFooter, destinationHeaderFooter);
                }
                else
                {
                    // Prepend empty paragraph - mimic MS Word behavior.
                    destinationHeaderFooter.prependChild(new Paragraph(destinationHeaderFooter.getDocument()));

                    while (sourceHeaderFooter.hasChildNodes())
                        destinationHeaderFooter.prependChild(sourceHeaderFooter.getLastChild());
                }
            }
            else
            {
                dstSection.getHeadersFooters().add(sourceHeaderFooter);
            }
        }
    }

    /// <summary>
    /// Updates comments tree.
    /// </summary>
    private void updateCommentTree()
    {
        getParaToCommentMapper().applyCommentExData();
        getParaToCommentMapper().applyCommentExtensibleData();
    }

    protected abstract void doRead() throws Exception;

    protected void readBody(DocumentBase doc) throws Exception
    {
        LoadingProgressProcessor progressProcessor = new LoadingProgressProcessor(doc, getLoadOptions());

        startSection(doc);

        String tagName = getXmlReader().getLocalName();
        while (getXmlReader().readChild(tagName))
        {
            // WORDSNET-14472 Adding a Callback event that notifies the progress of loading document.
            progressProcessor.execute(getXmlReader().getUnderlyingStream());

            if ("sectPr".equals(getXmlReader().getLocalName()))
            {
                // When sectPr occurs in body it contains properties for the last section of the document.
                // We just apply those properties to the current section and assume it is last.

                // WORDSNET-8383 Final sectPr is not last element of body but Word reads them.
                // Preserve and apply final section properties at document reading ends.
                readGlobalSectPr(true);
            }
            else
            {
                DocxReaderFactory.getStoryReader().readChild(this);
            }
        }

        // WORDSNET-10436 <w:br> element might be read at the end of the body, so need to add new paragraph to write it.
        if (getXmlReader().getRunTextBuilder().length() > 0)
            flushRun(getDocument());
    }

    /// <summary>
    /// Starts section if it is not started already. Section can be already started if there is content before body.
    /// <remarks>andrnosk: WORDSNET-5252</remarks>
    /// </summary>
    protected void startSection(DocumentBase doc)
    {
        if (getCurContainer().getNodeType() != NodeType.BODY)
        {
            addAndPushContainer(new Section(doc));
            addAndPushContainer(new Body(doc));
        }
    }

    /// <summary>
    /// Closes the last section of the document. Call this method when reading whole document is finished,
    /// because there might be some content outside body.
    /// <remarks>andrnosk: WORDSNET-5252</remarks>
    /// </summary>
    protected void endSection()
    {
        if (getCurContainer().getNodeType() == NodeType.BODY)
        {
            popContainer(NodeType.BODY);
            // If section properties were accumulated, but were not flushed into LastSectPr yet,
            // then we just use them as properties for last section.
            ((Section)getCurContainer()).setSectPr((GlobalSectPr.getCount() > 0) ? GlobalSectPr : getLastSectPr());

            popContainer(NodeType.SECTION);
        }
    }

    public /*override*/ NrxXmlReader getXmlReader() { return mXmlReader; }

    private OpcPackagePart getRelationshipTargetPart(String relId)
    {
        String partName = getRelationshipTarget(relId);
        return StringUtil.hasChars(partName)
            ? mPackage.getPartByName(partName)
            : null;
    }

    public /*override*/ IEmbeddedObject getEmbeddedObject(String id) throws Exception
    {
        OpcPackagePart olePart = getRelationshipTargetPart(id);
        // WORDSNET-Defect 1581. We should check the presence of OLE object data before preceding.
        if (olePart == null)
            return null;

        // WORDSNET-15758 System.InvalidCastException is thrown while loading DOC file.
        // One OLE package may be referred more than once in document. Need to reset position.
        olePart.getStream().setPosition(0);

        if (FileSystem.isStructuredStorage(olePart.getStream()))
        {
            // This is an embedded OLE object.
            FileSystem fs = new FileSystem(olePart.getStream());
            return new OleObject(fs.getRoot());
        }
        else
        {
            // This must be an embedded OOXML package.
            return new OoxmlObject(olePart.getStream(), olePart.getContentType(), olePart.getName());
        }
    }

    /// <summary>
    /// <para>Returns image data by relationship id.</para>
    /// <para>Memory optimization is used so equal data is not stored twice.
    /// In return, it's readonly data, do not modify it.</para>
    /// </summary>
    public /*override*/ byte[] getBinData(String relId) throws Exception
    {
        OpcPackagePart imagePart = getRelationshipTargetPart(relId);
        String partName = (imagePart == null) ? "" : imagePart.getName();

        byte[] cachedBinData = DictionaryExtension.getValueOrNull(mImageBytesByPartName, partName);
        if (cachedBinData == null)
        {
            String contentType = null;
            Ref<String> referenceToContentType = new Ref<String>(contentType);
            cachedBinData = getBinData(imagePart, /*out*/ referenceToContentType);
            contentType = referenceToContentType.get();
            msDictionary.add(mImageBytesByPartName, partName, cachedBinData);
        }

        return cachedBinData;
    }

    /// <summary>
    /// Add new paragraph and add run element with text <see cref="Run"/>.
    /// </summary>
    /// <param name="doc">Document</param>
    void flushRun(DocumentBase doc)
    {
        Paragraph par = new Paragraph(doc);
        addAndPushContainer(par);
        NrxRunReaderBase.flushRun(this, new RunPr());
        popContainer(NodeType.PARAGRAPH);
    }

    /// <summary>
    /// <para>Returns image data by relationship id.</para>
    /// <para>Memory optimization is used so equal data is not stored twice.
    /// In return, it's readonly data, do not modify it.</para>
    /// <para>Returns ContentType of OpcPackagePart as output parameter.</para>
    /// </summary>
    byte[] getBinData(OpcPackagePart imagePart, /*out*/Ref<String> contentType) throws Exception
    {
        contentType.set("");

        byte[] binData;
        if (isValidImagePart(imagePart))
        {
            if (getLoadOptions().getConvertMetafilesToPng() && ImageUtil.isMetafile(FileFormatCore.fromExt(imagePart.getExtension())))
            {
                ImageSizeCore imageSizeCore = ImageUtil.getMetafileSize(CompressedData.getStream(imagePart.getStream()));
                /*SizeF*/long resolution = msSizeF.ctor((float)imageSizeCore.getHorizontalResolution(), (float)imageSizeCore.getVerticalResolution());

                contentType.set(OpcContentType.IMAGE_PNG);
                imagePart.getStream().setPosition(0);
                try
                {
                    Stream imagePartStream = CompressedData.getStream(imagePart.getStream());
                    try /*JAVA: was using*/
                	{
                        binData = BitmapUtilPal.convertMetafileToBitmapUsingGdiPlus(imagePartStream, resolution, false, null);
                	}
                    finally { if (imagePartStream != null) imagePartStream.close(); }
                }
                catch (CantCreateBitmapException e)
                {
                    // Only the .NET Framework build can rasterize a metafile (through GDI+). Everywhere else the caller
                    // asked for something that cannot be done, and has to be told so: the picture readers swallow
                    // ordinary failures, which would silently lose the image.
                    throw new UnsupportedOperationException(
                        "LoadOptions.ConvertMetafilesToPng is not supported on this platform: there is no metafile renderer to convert WMF and EMF images with.");
                }
            }
            else
            {
                contentType.set(imagePart.getContentType());
                imagePart.getStream().setPosition(0);

                if (DocxContentType.IMAGE_PICT_COMPRESSED.equals(contentType.get()))
                {
                    binData = VmlUtil.unpackPictImage(StreamUtil.copyStreamToByteArray(imagePart.getStream()));
                }
                else if (ImageUtil.isStandardMetafile(imagePart.getStream()))
                {
                    // andrnosk: WORDSNET-6777 Add a placeable header to metafiles.
                    // For some reason, some wmf pictures do not render correctly without that header.
                    imagePart.getStream().setPosition(0);
                    ImageSizeCore metafileSize = ImageUtil.getMetafileSize(imagePart.getStream());

                    imagePart.getStream().setPosition(0);
                    binData = ImageUtil.prependWmfPlaceableHeader(imagePart.getStream(), metafileSize);
                }
                else
                {
                    binData = StreamUtil.copyStreamToByteArray(imagePart.getStream());
                }
            }
        }
        else
        {
            // WORDSNET-8253 The document contains rels with Target="NULL"
            // I found nothing about such target in DOCX specification,
            // but I suppose MS Word consider such target as noimage, or just ignore it
            // let's load NoImage if we encounter such Target of an image
            // imagePartName is empty string if Target="NULL"
            binData = ImageUtil.getNoImageBytes();
        }

        return getNonDuplicatedBytes(binData);
    }

    /// <summary>
    /// <para>Returns bin data by relationship id.</para>
    /// <para>Guarantees that there are no other references to returned byte array
    /// so data may be modified without side effects.</para>
    /// <para>It's common case to attach data after modification back to reader by
    /// <see cref="NrxDocumentReaderBase.GetNonDuplicatedBytes"/>.</para>
    /// </summary>
    byte[] getDetachedBinData(String relId)
    {
        OpcPackagePart binPart = getRelationshipTargetPart(relId);

        // check binPart == null covers case, when document contains rels with Target="NULL". I'm not sure
        // if it's possible in common case and added the check as analogue for image data in GetBinData.

        return (binPart == null) ? null : StreamUtil.copyStreamToByteArray(binPart.getStream());
    }

    public /*override*/ String getRelationshipTarget(String relId)
    {
        return mXmlReader.getPart().getRelationshipTarget(relId);
    }

    public /*override*/ boolean isExternalImage(String src)
    {
        OpcRelationship rel = mXmlReader.getPart().getRels().getById(src);

        // WORDSNET-23938 Resilience for missing relationship part.
        return (rel == null) || rel.isExternal();
    }

    boolean isExternalPackage(String src)
    {
        OpcRelationship rel = mXmlReader.getPart().getRels().getById(src);
        return rel.isExternal();
    }

    public /*override*/ void readBinData()
    {
        // Do nothing. This is applicable for WordML implementation only.
    }

    public /*override*/ boolean readTextboxContent(ShapeBase shape) throws Exception
    {
        while (mXmlReader.readChild("textbox"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"txbxContent"*/0: // w:txbxContent
                {
                    StoryRevisionStack.pushStory();
                    DocxReaderFactory.getStoryReader().read(this, shape);
                    StoryRevisionStack.popStory();
                    break;
                }
                default:
                {
                    mXmlReader.ignoreElement();
                    break;
                }
            }
        }

        return false;
    }

    /// <summary>
    /// Gets next not-used footnote or endnote by an ID. Returns null if the node is not found. Raises an exception
    /// if all footnotes with the specified ID are already inserted into a document. Allows correctly process
    /// documents that has several footnotes with same ID: next not-used footnote/endnote is returned.
    /// </summary>
    /*override*/ Footnote getFootnoteById(/*FootnoteType*/int footnoteType, int id)
    {
        return mFootnotesReader.getFootnoteById(footnoteType, id);
    }

    /*override*/ void addComment(Comment comment)
    {
        // WORDSNET-12268 Mimic MS Word behavior and ignore next comment with the same ID.
        if (!mIdToComment.containsKey(comment.getId()))

            // We add comments to a dictionary, not to the document tree straight away.
            mIdToComment.add(comment.getId(), comment);
    }

    /*override*/ Comment getCommentById(int id)
    {
        return mIdToComment.get(id);
    }

    /*override*/ boolean isSmartTag() { throw new IllegalStateException("Not expected to call this."); }

    /*override*/ boolean isCustomXmlTag() { throw new IllegalStateException("Not expected to call this."); }

    /// <summary>
    /// Needed for DOCX only.
    /// Switches the XML reader to read the part specified by the relationship id.
    /// Saves the current XML reader so it can be restored.
    /// </summary>
    DocxXmlReader switchToPartReaderByRelId(String relId) throws Exception
    {
        OpcPackagePart part = getPartByName(getRelationshipTarget(relId));

        if (part == null)
            return null;

        mXmlReaderStack.push(mXmlReader);
        mXmlReader = new DocxXmlReader(part, mXmlReader.getWarningCallback(), WarningSource.DOCX, getComplianceInfo());
        return mXmlReader;
    }

    /// <summary>
    /// Creates a DOCX XML reader for a part that is a child of the document part and
    /// at the end of the specified relationship. Returns null if there is no such part.
    /// </summary>
    /*virtual*/ DocxXmlReader switchToPartReaderByRelType(String relType) throws Exception
    {
        return switchToPartReaderByRelType(mDocumentPart, relType);
    }

    /// <summary>
    /// Creates a DOCX XML reader for a part that is a child of the specified part and
    /// at the end of the specified relationship. Returns null if there is no such part.
    /// </summary>
    DocxXmlReader switchToPartReaderByRelType(OpcPackagePart parentPart, String relType) throws Exception
    {
        OpcPackagePart part = getPartByRelationshipType(parentPart, relType);
        if (part == null)
            return null;

        return pushPartReader(new DocxXmlReader(part, getLoadOptions().getWarningCallback(), WarningSource.DOCX, getComplianceInfo()));
    }

    /// <summary>
    /// Restores the previously saved XML reader.
    /// </summary>
    /*virtual*/ DocxXmlReader restorePartReader()
    {
        mXmlReader = (mXmlReaderStack.getCount() > 0) ? mXmlReaderStack.pop() : null;
        return mXmlReader;
    }

    DocxXmlReader pushPartReader(DocxXmlReader reader)
    {
        mXmlReaderStack.push(mXmlReader);
        mXmlReader = reader;
        return mXmlReader;
    }

    /// <summary>
    /// Creates <see cref="DocxXmlReader"/> using specified XML string and collection of
    /// namespaces mapping and pushes it into part reader stack.
    /// </summary>
    DocxXmlReader createAndPushPartReader(String xml, Map<String, String> namespaceMap)
    {
        // Expected that current reader can not be "null".
        Debug.assertTrue((mXmlReader != null) && (namespaceMap != null));

        DocxXmlReader reader = new DocxXmlReader(mXmlReader.getPart(), getComplianceInfo(), xml, namespaceMap);
        return pushPartReader(reader);
    }

    /*override*/ boolean isDocx() { return true; }

    OpcPackagePart getPartByRelationshipType(OpcPackagePart parentPart, String relType)
    {
        return mPackage.getPartByRelationshipType(parentPart, relType);
    }

    OpcPackagePart fetchPartByName(String name)
    {
        return mPackage.fetchPartByName(name);
    }

    /// <summary>
    /// Gets part by given name.
    /// </summary>
    /// <returns>Returns null if part is not found.</returns>
    OpcPackagePart getPartByName(String name)
    {
        return mPackage.getPartByName(name);
    }

    /// <summary>
    /// This is called when NrxRunReaderBase encounters a w:drawing element.
    /// </summary>
    public ShapeBase /*INrxDmlReader.*/readDrawing(RunPr runPr) throws Exception
    {
        DmlDrawingReader dmlReader = new DmlDrawingReader();
        return dmlReader.readDrawing(this);
    }

    /// <summary>
    /// Add AltChunk object to collection.
    /// </summary>
    /// <param name="altChunk">AltChunk object.</param>
    void addAltChunk(DocxAltChunk altChunk)
    {
        msArrayList.add(mAltChunkCollection, altChunk);
    }

    protected OpcPackageBase getPackage() { return mPackage; }

    OpcPackagePart getDocumentPart() { return mDocumentPart; }

    /// <summary>
    /// Holds list of picture bullet ids to translate it to zero based values.
    /// </summary>
    ArrayList<Integer> getPictureBulletTranslation() { return mPictureBulletTranslation; }

    ArrayList<DocxAltChunk> getAltChunks() { return mAltChunkCollection; }

    boolean isValidImagePart(String relId) throws Exception
    {
        return isValidImagePart(getRelationshipTargetPart(relId));
    }

    /// <summary>
    /// Checks that given OpcPackagePart is valid image part.
    /// </summary>
    private static boolean isValidImagePart(OpcPackagePart imagePart) throws Exception
    {
        if (imagePart == null)
            return false;

        // andrnosk: WORDSNET-10229 Consider XML OpcPackagePart as invalid image part.
        if ((OpcContentType.XML.equals(imagePart.getContentType())) ||
            ("xml".equals(imagePart.getExtension())))
        {
            return false;
        }

        // Word preserves octet streams even if they are zero sized for unknown reason.
        if (DocxContentType.OCTET_STREAM.equals(imagePart.getContentType()))
            return true;

        // For image content types zero size seems to be invalid.
        if (imagePart.getStream().getLength() == 0)
            return false;

        return true;
    }

    /// <summary>
    /// Updates layout of tables with missed TableGrid.
    ///  </summary>
    private void updateTablesWithMissedTableGrid()
    {
        for (Table table : TablesWithMissedTableGrid)
        {
            table.updateLayout();
        }
    }

    /// <summary>
    /// Helper for mapping paragraphs to comments.
    /// </summary>
    private ParagraphToCommentMapper getParaToCommentMapper() { return mParaToCommentMapper; };

    private ParagraphToCommentMapper mParaToCommentMapper;

    private DocxRelationshipTypes mRelTypes;

    private /*final*/ OpcPackageBase mPackage;
    /// <summary>
    /// The document part.
    /// </summary>
    private /*final*/ OpcPackagePart mDocumentPart;

    private /*final*/ DocxStylesReader mStylesReader;
    private /*final*/ DocxNumberingReader mNumberingReader;

    /// <summary>
    /// Current document part reader.
    /// </summary>
    private DocxXmlReader mXmlReader;
    /// <summary>
    /// During read we can switch from part to part, such switches are stored in this stack.
    /// </summary>
    private /*final*/ msStack<DocxXmlReader> mXmlReaderStack = new msStack<DocxXmlReader>();
    /// <summary>
    /// All comments are read here before the main document is read.
    /// Key is integer comment id, value is a Comment object.
    /// </summary>
    private /*final*/ IntToObjDictionary<Comment> mIdToComment = new IntToObjDictionary<Comment>();
    private DocxFootnotesReader mFootnotesReader;

    /// <summary>
    /// Collection of AltChunk.
    /// </summary>
    private /*final*/ ArrayList<DocxAltChunk> mAltChunkCollection = new ArrayList<DocxAltChunk>();

    private /*final*/ ArrayList<Integer> mPictureBulletTranslation = new ArrayList<Integer>();

    private /*final*/ HashMap<String, byte[]> mImageBytesByPartName = new HashMap<String, byte[]>();

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"txbxContent"
	);

}

