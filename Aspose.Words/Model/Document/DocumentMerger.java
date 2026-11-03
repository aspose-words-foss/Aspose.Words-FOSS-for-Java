// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2022 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.HashSetGeneric;
import com.aspose.ArgumentUtil;


/// <summary>
/// Class for merging documents.
/// </summary>
/// <dev>
/// This is very first version of document merge feature.
/// According to issues, there will be many tricks and hacks while trying to preserve original documents layout.
/// </dev>
class DocumentMerger
{
    /// <summary>
    /// Initializes a new instance of <see cref="DocumentMerger"/> class.
    /// </summary>
    private DocumentMerger(DocumentBase dstDoc, DocumentBase srcDoc)
    {
        Debug.assertTrue((srcDoc != null) && (dstDoc != null));
        mNodeImporter = new NodeImporter(srcDoc, dstDoc, IMPORT_FORMAT_MODE, G_IMPORT_FORMAT_OPTIONS);
    }

    /// <summary>
    /// Merges all specified documents into the <paramref name="dstDoc"/>.
    /// </summary>
    static void mergeDocuments(Document dstDoc, /*params*/ Document... srcDocs) throws Exception
    {
        for (Document srcDoc : srcDocs)
        {
            DocumentMerger merger = new DocumentMerger(dstDoc, srcDoc);

            updateLastParagraph(dstDoc);

            // WORDSNET-26891 Apply compatibility options from the source document
            // in case of destination document is blank.
            if (isBlank(dstDoc))
                dstDoc.getDocPr().CompatibilityOptions = srcDoc.getCompatibilityOptions().deepClone();

            // WORDSNET-24985 Ensure vertically anchored to paragraph TextBox,
            // will not be inside a section break paragraph.
            ensureNoTextBoxInLastPara(dstDoc);

            merger.execute();
        }
    }

    /// <summary>
    /// Executes merger.
    /// </summary>
    private void execute() throws Exception
    {
        // WORDSNET-28311 Word always updates `NUMPAGES` field when loads a document. This means we need
        // to replace `NUMPAGES` field with its result from the original document to preserve number of pages
        // as it was in original document.
        // Also note, to simplify the solution, we assume that `NUMPAGES` field is located inside HeadersFooters.
        // If there will be another places with `NUMPAGES`, then we will need to extend the solution.
        FieldRemoverFilter filter = new FieldRemoverFilter(FieldType.FIELD_NUM_PAGES);
        replaceFieldsWithResults(getDstDoc(), filter);

        // Remember last section of the original destination document.
        Section lastSection = getDstDoc().getLastSection();

        // The collection of forcibly unlinked from previous
        // Header and Footer types that were empty in a source document.
        HashSetGeneric<Integer> unlinkedHeaderFooters = null;

        for (Section srcSection : (Iterable<Section>) getSrcDoc().getSections())
        {
            Section importedSection = (Section)mNodeImporter.importNode(srcSection, true, mInfoCollector);
            getDstDoc().appendChild(importedSection);

            // For the very first section, we should unlink it from a previous section
            // and reset section break to a `NewPage`, as we want to preserve source
            // sections "as is" by design.
            if (srcSection.isFirstSection())
            {
                unlinkedHeaderFooters = unlinkFromPrevious(importedSection.getHeadersFooters());
                importedSection.getPageSetup().setSectionStart(SectionStart.NEW_PAGE);

                // WORDSNET-25831 Preserve original page numbering.
                importedSection.getPageSetup().setRestartPageNumbering(true);
            }
            else
            {
                Debug.assertTrue(unlinkedHeaderFooters != null);

                // WORDSNET-25832 For all other (non-first) imported sections we also
                // should consider either they were linked or not before import and
                // either we have unlinked something forcibly for the very first section.
                // If there is no Header (or Footer respectively) in imported section and
                // there was forcibly unlinked first section with empty Header (Footer),
                // then we should compensate Header (Footer) distance for such section.
                // Otherwise, we remove Headers (Footers) from the empty unlinked collection
                // to not consider them anymore (as they become non-empty).
                for (int i = importedSection.getHeadersFooters().getCount() - 1; i >= 0; i--)
                {
                    HeaderFooter hf = importedSection.getHeadersFooters().get(i);
                    if (hf.isLinkedToPrevious() && unlinkedHeaderFooters.contains(hf.getHeaderFooterType()))
                    {
                        unlinkedHeaderFooters.remove(hf.getHeaderFooterType());
                        importedSection.getHeadersFooters().removeAt(i);
                    }
                }

                // WORDSNET-28445 We can compensate Distance only when there are
                // no content in all Headers or Footers of the section.
                if (!hasContentInHeaders(importedSection))
                    importedSection.getPageSetup().setHeaderDistance(0.0);

                if (!hasContentInFooters(importedSection))
                    importedSection.getPageSetup().setFooterDistance(0.0);
            }

            // WORDSNET-28311 Replace `NUMPAGES` fields with their results. See details above.
            replaceFieldsWithResults(importedSection, filter);
        }

        // WORDSNET-26046 If footnote separators in source and destination documents have
        // different formatting and source document has footnotes, but destination document
        // has no it, then we can replace destination separator formatting with the source
        // one to preserve formatting of the source document in this case.
        FootnoteSeparator dstSeparator = getDstDoc().getFootnoteSeparators().getByFootnoteSeparatorType(FootnoteSeparatorType.FOOTNOTE_SEPARATOR);
        FootnoteSeparator srcSeparator = getSrcDoc().getFootnoteSeparators().getByFootnoteSeparatorType(FootnoteSeparatorType.FOOTNOTE_SEPARATOR);
        // WORDSNET-28957 First check whether the source document contains FootnoteSeparator.
        boolean tryResolveFootnotes = (srcSeparator != null) &&
                                   mInfoCollector.hasFootnotes() &&
                                   !areEqual(dstSeparator, srcSeparator);

        // WORDSNET-25885 If `DoNotShadeFormData` setting is different in source and destination documents,
        // then we can try the trick the same, as above for footnote separators in WORDSNET-26046.
        boolean tryResolveFormFields = mInfoCollector.hasFormFields() &&
                                 (getDstDoc().getDocPr().DoNotShadeFormData != getSrcDoc().getDocPr().DoNotShadeFormData);
        if (tryResolveFootnotes || tryResolveFormFields)
        {
            // Run visitor to collect data up to original
            // last section of the destination document.
            DocMergerVisitor mergerVisitor = new DocMergerVisitor(lastSection);
            getDstDoc().accept(mergerVisitor);

            if (tryResolveFootnotes && !mergerVisitor.hasFootnotes())
            {
                // There are no footnotes in destination,
                // so we can replace it to preserve source formatting.
                // WORDSNET-26604 We should import, instead of just clone a node.
                FootnoteSeparator clonedSeparator = (FootnoteSeparator)mNodeImporter.importNode(srcSeparator, true);
                clonedSeparator.setDocument(getDstDoc());
                getDstDoc().getFootnoteSeparators().add(clonedSeparator);
            }

            if (tryResolveFormFields && !mergerVisitor.hasFormFields())
            {
                // There are no form fields in destination, so we can override
                // `DoNotShadeFormData` setting from the source document.
                getDstDoc().getDocPr().DoNotShadeFormData = getSrcDoc().getDocPr().DoNotShadeFormData;
            }
        }

        // WORDSNET-25408 Update cloned SDTs mapped to document core
        // properties with the data from the source document.
        updateCorePropertiesSdts();

        // WORDSNET-25587 Added importing citations.
        mNodeImporter.importCitationSources();
    }

    /// <summary>
    /// Replaces fields with field results in the all headers and footers of a given document.
    /// </summary>
    private static void replaceFieldsWithResults(Document doc, FieldRemoverFilter filter) throws Exception
    {
        for (Section section : (Iterable<Section>) doc.getSections())
            replaceFieldsWithResults(section, filter);
    }

    /// <summary>
    /// Replaces fields with field results in the all headers and footers of a given section.
    /// </summary>
    private static void replaceFieldsWithResults(Section section, FieldRemoverFilter filter) throws Exception
    {
        for (HeaderFooter headerFooter : (Iterable<HeaderFooter>) section.getHeadersFooters())
        {
            NodeRange range = new NodeRange(headerFooter, headerFooter);
            FieldRemover.replaceFieldsWithResults(range, filter);
        }
    }

    /// <summary>
    /// Returns true, if specified <see cref="FootnoteSeparator"/> objects are equal.
    /// </summary>
    private static boolean areEqual(FootnoteSeparator a, FootnoteSeparator b)
    {
        if (a != null)
            return a.equals(b);

        return (b == null);
    }

    /// <summary>
    /// Returns true, if a specified collection has any of the Headers from the <see cref="HeaderFooterType"/>.
    /// </summary>
    private static boolean hasHeaders(HashSetGeneric<Integer> headerFooterTypes)
    {
        for (/*HeaderFooterType*/int headerFooterType : (Iterable</*HeaderFooterType*/Integer>) headerFooterTypes)
        {
            if ((headerFooterType == HeaderFooterType.HEADER_EVEN) ||
                (headerFooterType == HeaderFooterType.HEADER_FIRST) ||
                (headerFooterType == HeaderFooterType.HEADER_PRIMARY))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns true, if a specified collection has any of the Footers from the <see cref="HeaderFooterType"/>.
    /// </summary>
    private static boolean hasFooters(HashSetGeneric<Integer> headerFooterTypes)
    {
        for (/*HeaderFooterType*/int headerFooterType : (Iterable</*HeaderFooterType*/Integer>) headerFooterTypes)
        {
            if ((headerFooterType == HeaderFooterType.FOOTER_EVEN) ||
                (headerFooterType == HeaderFooterType.FOOTER_FIRST) ||
                (headerFooterType == HeaderFooterType.FOOTER_PRIMARY))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns collection of <see cref="HeaderFooterType"/> objects that are used in a specified <see cref="Document"/>.
    /// </summary>
    private static HashSetGeneric<Integer> getUsedHeaderFooterTypes(Document doc)
    {
        HashSetGeneric<Integer> headerFooterTypes = new HashSetGeneric<Integer>();
        for (Section section : (Iterable<Section>) doc.getSections())
        {
            HashSetGeneric<Integer> curSectionheaderFooterTypes = getUsedHeaderFooterTypes(section);
            for (/*HeaderFooterType*/int curSectionheaderFooterType : (Iterable</*HeaderFooterType*/Integer>) curSectionheaderFooterTypes)
                headerFooterTypes.add(curSectionheaderFooterType);
        }

        return headerFooterTypes;
    }

    /// <summary>
    /// Returns collection of <see cref="HeaderFooterType"/> objects that are used in a specified <see cref="Document"/>.
    /// </summary>
    private static HashSetGeneric<Integer> getUsedHeaderFooterTypes(Section section)
    {
        HashSetGeneric<Integer> headerFooterTypes = new HashSetGeneric<Integer>();
        for (HeaderFooter hf : (Iterable<HeaderFooter>) section.getHeadersFooters())
            headerFooterTypes.add(hf.getHeaderFooterType());

        return headerFooterTypes;
    }

    /// <summary>
    /// Unlinks specified HeadersFooters from previous section.
    /// </summary>
    /// <remarks>
    /// Unlinks only existing HeadersFooters,
    /// preventing creation an extra empty HeadersFooters.
    /// </remarks>
    /// <returns> The collection of <see cref="HeaderFooterType"/> objects for which
    /// <see cref="HeaderFooter"/> were originally empty and unlinked.</returns>
    private HashSetGeneric<Integer> unlinkFromPrevious(HeaderFooterCollection headersFooters)
    {
        HashSetGeneric<Integer> unlinkedHeaderFooters = new HashSetGeneric<Integer>();

        // Collect existing types of HeadersFooters in destination document.
        HashSetGeneric<Integer> dstHeaderFooterTypes = getUsedHeaderFooterTypes(getDstDoc());

        // If there was no HeaderFooter originally, or it was empty, then try to
        // compensate it by eliminating Header/Footer distance.
        // WORDSNET-25886 Compensate distance only when destination has header/footer and
        // merging section does not have it.
        if (!headersFooters.hasHeaders() && hasHeaders(dstHeaderFooterTypes))
            headersFooters.getParentSection().getPageSetup().setHeaderDistance(0.0);

        if (!headersFooters.hasFooters() && hasFooters(dstHeaderFooterTypes))
            headersFooters.getParentSection().getPageSetup().setFooterDistance(0.0);

        for (/*HeaderFooterType*/int type : (Iterable</*HeaderFooterType*/Integer>) dstHeaderFooterTypes)
        {
            HeaderFooter headerFooter = headersFooters.getByHeaderFooterType(type);
            boolean isLinkedToPrevious = (headerFooter == null) || headerFooter.isLinkedToPrevious();

            // There is such HeaderFooter type in destination document,
            // so unlink this type from previous forcibly.
            if (isLinkedToPrevious)
            {
                headersFooters.linkToPrevious(type, false);
                unlinkedHeaderFooters.add(type);

                headerFooter = headersFooters.getByHeaderFooterType(type);
                // As we unlinked HeaderFooter, there must be at least one paragraph.
                Debug.assertTrue(headerFooter.getFirstParagraph() != null);

                // If there was no HeaderFooter originally, or it was empty,
                // then try to compensate it by hiding paragraph.
                headerFooter.getFirstParagraph().getParagraphBreakRunPr().set(FontAttr.SIZE, 0);
                headerFooter.getFirstParagraph().getParagraphBreakRunPr().set(FontAttr.HIDDEN, AttrBoolEx.TRUE);
            }
        }

        return unlinkedHeaderFooters;
    }

    /// <summary>
    /// Returns true, if a specified section has any content in the HeaderFooter of a specified type.
    /// </summary>
    /// <remarks>
    /// Considers also content of the previous HeaderFooter if this one is linked to it.
    /// </remarks>
    private static boolean hasContentInHeaderFooter(Section section, /*HeaderFooterType*/int type)
    {
        while (section != null)
        {
            HeaderFooter hf = section.getHeadersFooters().getByHeaderFooterType(type);
            section = (Section)section.getPreviousSibling();

            if (hf == null || !hf.hasChildNodes())
                continue;

            if (hf.getCount() > 1)
                return true;

            return (hf.getFirstParagraph() != null) && hf.getFirstParagraph().hasChildNodes();
        }

        return false;
    }

    /// <summary>
    /// Returns true, if a specified section has any content in any of its Headers.
    /// </summary>
    /// <remarks>
    /// Considers also content of the previous Headers, if this one is linked to it.
    /// </remarks>
    private static boolean hasContentInHeaders(Section section)
    {
        return hasContentInHeaderFooter(section, HeaderFooterType.HEADER_FIRST) ||
               hasContentInHeaderFooter(section, HeaderFooterType.HEADER_PRIMARY) ||
               hasContentInHeaderFooter(section, HeaderFooterType.HEADER_EVEN);
    }

    /// <summary>
    /// Returns true, if a specified section has any content in any of its Footers.
    /// </summary>
    /// <remarks>
    /// Considers also content of the previous Footers, if this one is linked to it.
    /// </remarks>
    private static boolean hasContentInFooters(Section section)
    {
        return hasContentInHeaderFooter(section, HeaderFooterType.FOOTER_FIRST) ||
               hasContentInHeaderFooter(section, HeaderFooterType.FOOTER_PRIMARY) ||
               hasContentInHeaderFooter(section, HeaderFooterType.FOOTER_EVEN);
    }

    /// <summary>
    /// Fixes the issue with incorrect alignment of a paragraph when it appears not at the end of the document during the merge process.
    /// </summary>
    private static void updateLastParagraph(Document doc)
    {
        if ((doc.getLastSection() == null) || (doc.getLastSection().getBody() == null))
            return;

        Paragraph lastPara = doc.getLastSection().getBody().getLastParagraph();
        if (lastPara == null || lastPara.isEmptyOrContainsOnlyCrossAnnotation())
            return;

        if (lastPara.getParaPr().getAlignment() == ParagraphAlignment.JUSTIFY)
        {
            // A zero-size paragraph is added, so that the page layout does not have to be changed.
            // In fact, the actual height of such paragraph is 2 pixels, or 30 twips.
            Paragraph hiddenPara = new Paragraph(doc);
            hiddenPara.getParaPr().setSpaceAfter(0);
            hiddenPara.getParaPr().setSpaceBefore(0);
            hiddenPara.getParagraphBreakRunPr().setSize(0);
            hiddenPara.getParagraphBreakRunPr().setSizeBi(0);
            doc.getLastSection().getBody().appendChild(hiddenPara);
        }
    }

    /// <summary>
    /// Updates cloned SDTs mapped to document core properties
    /// with the data from the source document.
    /// </summary>
    private void updateCorePropertiesSdts() throws Exception
    {
        for (StructuredDocumentTag sdt : mInfoCollector.getCorePropSdts())
        {
            // For the moment we process only Title sdts.
            updateTitleSdt(sdt);
        }
    }

    /// <summary>
    /// Updates specified SDT if it maps to document core property Title
    /// with a Title from the source document.
    /// </summary>
    private void updateTitleSdt(StructuredDocumentTag sdt) throws Exception
    {
        if (!PropertyName.TITLE.equals(sdt.getXmlMapping().getPropertyName()))
            return;

        // Save current Title.
        String dstTitle = getDstDoc().getBuiltInDocumentProperties().getTitle();

        // Update with source document Title.
        getDstDoc().getBuiltInDocumentProperties().setTitle(getSrcDoc().getBuiltInDocumentProperties().getTitle());
        sdt.getXmlMapping().updateContent();

        // Remove mapping to preserve updated content from any future changes.
        sdt.getXmlMapping().delete();

        // Restore original Title.
        getDstDoc().getBuiltInDocumentProperties().setTitle(dstTitle);
    }

    /// <summary>
    /// Ensures that there is no TextBox inside a very last paragraph of a specified document
    /// vertically anchored to this paragraph.
    /// </summary>
    private static void ensureNoTextBoxInLastPara(Document doc)
    {
        // WORDSNET-26815 Resilience against a completely empty document.
        if (doc.getLastSection() == null)
            return;

        Paragraph lastPara = doc.getLastSection().getBody().getLastParagraph();
        if (lastPara == null)
            return;

        Shape shape = (Shape)lastPara.getChild(NodeType.SHAPE, 0, true);
        // If there is no TextBox, or it is not vertically anchored to paragraph,
        // then nothing to do and just exit.
        if ((shape == null) || !shape.isTextBox() || (shape.getRelativeVerticalPosition() != RelativeVerticalPosition.PARAGRAPH))
            return;

        // To ensure TextBox is not inside a very last paragraph
        // just add extra paragraph after it.
        lastPara.getParentNode().appendChild(new Paragraph(doc));
    }

    /// <summary>
    /// Returns true, if a specified document is blank.
    /// </summary>
    private static boolean isBlank(Document doc)
    {
        if (doc.getFirstSection() == null)
            return true;

        // For the moment consider blank only single-section documents.
        if (doc.getCount() > 1)
            return false;

        // For the moment consider as blank, if there are no any headers and footers.
        if (doc.getFirstSection().getHeadersFooters().hasHeaders() || doc.getFirstSection().getHeadersFooters().hasFooters())
            return false;

        if (doc.getFirstSection().getBody() == null)
            return true;

        for (Node node : (Iterable<Node>) doc.getFirstSection().getBody())
        {
            switch (node.getNodeType())
            {
                case NodeType.BOOKMARK_START:
                case NodeType.BOOKMARK_END:
                    continue;

                case NodeType.PARAGRAPH:
                    if (((Paragraph)node).isEmptyOrContainsOnlyCrossAnnotation())
                        continue;
                    break;
                default:
                    return false;
            }

            return false;
        }

        return true;
    }

    /// <summary>
    /// The source document.
    /// </summary>
    private Document getSrcDoc() { return (Document)mNodeImporter.getContext().getSrcDoc(); }

    /// <summary>
    /// The destination document.
    /// </summary>
    private Document getDstDoc() { return (Document)mNodeImporter.getContext().getDstDoc(); }

    /// <summary>
    /// The instance of NodeImporter based on merging documents.
    /// </summary>
    private /*final*/ NodeImporter mNodeImporter;

    /// <summary>
    /// The import format options being used upon merging documents.
    /// </summary>
    private static /*final*/ ImportFormatOptions G_IMPORT_FORMAT_OPTIONS = G_IMPORT_FORMAT_OPTIONSInitializer();
    /*JAVA-added private static Initializer*/
    private static ImportFormatOptions G_IMPORT_FORMAT_OPTIONSInitializer()
    {
        ImportFormatOptions G_IMPORT_FORMAT_OPTIONS = new ImportFormatOptions();
    	{
        	G_IMPORT_FORMAT_OPTIONS.isMerger(true); G_IMPORT_FORMAT_OPTIONS.setIgnoreHeaderFooter(false); G_IMPORT_FORMAT_OPTIONS.setIgnoreTextBoxes(false); G_IMPORT_FORMAT_OPTIONS.setKeepSourceNumbering(true);
    	}
        return G_IMPORT_FORMAT_OPTIONS;
    }

    /// <summary>
    /// The import format mode.
    /// </summary>
    /// <remarks>
    /// To preserve source document 'as is' we should always use `KeepSourceFormatting` mode.
    /// </remarks>
    private static final /*ImportFormatMode*/int IMPORT_FORMAT_MODE = com.aspose.words.ImportFormatMode.KEEP_SOURCE_FORMATTING;

    /// <summary>
    /// Clone listener that collects all necessary information about inserted content upon importing its particular nodes.
    /// </summary>
    private /*final*/ DocMergerInfoCollector mInfoCollector = new DocMergerInfoCollector();

    /// <summary>
    /// Implements <see cref="IFieldRemoverFilter"/> for certain field types.
    /// </summary>
    private static class FieldRemoverFilter implements IFieldRemoverFilter
    {
        /// <summary>
        /// Initializes a new instance of <see cref="FieldRemoverFilter"/> class.
        /// </summary>
        public FieldRemoverFilter(/*params*/ /*FieldType*/int... fieldTypes)
        {
            ArgumentUtil.checkNotNull(fieldTypes, "fieldTypes");
            mFieldTypes = fieldTypes;
        }

        public boolean /*IFieldRemoverFilter.*/needReplaceFieldWithResult(FieldChar fieldChar)
        {
            for (/*FieldType*/int fieldType : mFieldTypes)
            {
                if (fieldType == fieldChar.getFieldType())
                    return true;
            }

            return false;
        }

        private /*final*/ /*FieldType*/int[] mFieldTypes;
    }
}

