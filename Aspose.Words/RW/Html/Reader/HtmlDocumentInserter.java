// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.HashSetGeneric;
import com.aspose.Debug;
import com.aspose.ms.System.IO.Stream;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


class HtmlDocumentInserter implements IDocumentReader
{
    HtmlDocumentInserter(String html, /*HtmlInsertOptions*/int insertOptions,
        DocumentBuilder documentBuilder, HashSetGeneric<String> bookmarkNames)
    {
        Debug.assertTrue(html != null);
        Debug.assertTrue(documentBuilder != null);
        mInsertOptions = insertOptions;
        mHtml = html;
        mDocumentBuilder = documentBuilder;
        mBookmarkNames = bookmarkNames;
    }

    public void read() throws Exception
    {
        Document doc = mDocumentBuilder.getDocument();
        HtmlResourceLoader resourceLoader = new HtmlResourceLoader(doc.getResourceLoadingCallback(), doc.getWarningCallback());
        HtmlReaderSettings readerSettings = new HtmlReaderSettings();
        readerSettings.UseHtmlBlocks = (mInsertOptions & HtmlInsertOptions.PRESERVE_BLOCKS) != 0;
        HtmlReader reader = new HtmlReader(readerSettings, LoadFormat.HTML, resourceLoader, mBookmarkNames);

        // WORDSNET-25722 Section formatting of the last section imported from HTML must only be applied
        // if all of the following conditions are true:
        //  - HTML is inserted into the last section of the document;
        //  - the target section is empty;
        //  - the target section has no headers or footers.
        // Otherwise, the last section imported from HTML must have the same formatting as the target section.
        // This is how InsertDocument handles formatting of inserted sections and we mimic this behavior here
        // by saving and restoring the target section formatting appropriately.
        SectionFormatting sectionFormatting = saveTargetSectionFormatting();

        reader.read(mHtml, mDocumentBuilder, mInsertOptions, (mBookmarkNames == null));

        restoreTargetSectionFormatting(sectionFormatting);
    }

    public boolean isEncrypted() { return false; }

    public Stream decrypt()
    {
        Debug.assertTrue(false, "Not supported");
        return null;
    }

    private SectionFormatting saveTargetSectionFormatting()
    {
        // If we're inserting into the main story of the last empty section, the last section imported from HTML can
        // be formatted using section formatting specified in HTML. There is no need to preserve target section's
        // formatting. This case also covers the scenario where HTML is inserted into a newly created empty document.
        // In this case, all sections imported from HTML will be formatted as specified in the HTML document.
        if (targetSectionIsLastAndEmpty())
        {
            return null;
        }

        Section section = mDocumentBuilder.getCurrentSection();

        SectionFormatting sectionFormatting = new SectionFormatting();
        sectionFormatting.SectPr = AttrCollectionExtension.deepClone(section.getSectPr());

        ArrayList<HeaderFooter> headersFooters = new ArrayList<HeaderFooter>();
        for (HeaderFooter headerFooter : (Iterable<HeaderFooter>) section.getHeadersFooters())
        {
            if (!headerFooter.isLinkedToPrevious())
            {
                msArrayList.add(headersFooters, headerFooter);
            }
        }
        sectionFormatting.HeadersFooters = msArrayList.toArray(headersFooters, new HeaderFooter[0]);

        // WORDSNET-27848 We're removing the section's headers and footers temporarily and will put them back after
        // HTML is inserted. Since these are just thechnical details of how section's headers and footers are preserved,
        // we don't want this removal to be tracked.
        mDocumentBuilder.getDocument().suspendTrackRevisions(SuspendedRevisionTypes.ALL);
        try
        {
            // Remove current section's headers and footers so that headers and footers from HTML can be loaded instead.
            // Original headers and footers will be restored by the calling code after HTML is inserted.
            section.getHeadersFooters().clear();
        }
        finally
        {
            mDocumentBuilder.getDocument().resumeTrackRevisions(SuspendedRevisionTypes.ALL);
        }

        return sectionFormatting;
    }

    private boolean targetSectionIsLastAndEmpty()
    {
        // This also covers the scenario where HTML is being inserted into a header/footer story. In that scenario
        // we don't do anything to current section's properties. If there is no valid target section, we cannot preserve
        // its formatting.
        Body body = ms.as(mDocumentBuilder.getCurrentStory(), Body.class);
        if (body == null)
        {
            return true;
        }

        Section section = body.getParentSection();
        if (section == null)
        {
            return true;
        }

        if (!section.isLastSection())
        {
            return false;
        }

        for (HeaderFooter headerFooter : (Iterable<HeaderFooter>) section.getHeadersFooters())
        {
            if (!headerFooter.isLinkedToPrevious())
            {
                return false;
            }
        }

        return body.isSectionBreak();
    }

    private void restoreTargetSectionFormatting(SectionFormatting savedSectionFormatting)
    {
        if (savedSectionFormatting == null)
        {
            return;
        }

        Section section = mDocumentBuilder.getCurrentSection();
        if (section == null)
        {
            return;
        }

        // WORDSNET-27848 The section's headers and footers are removed to be replaced with headers and footers that
        // we had stored before inserting HTML. Since these are just thechnical details of how section's headers and footers
        // are preserved, we don't want these operations to be tracked.
        mDocumentBuilder.getDocument().suspendTrackRevisions(SuspendedRevisionTypes.ALL);
        try
        {
            section.setSectPr(savedSectionFormatting.SectPr);

            section.getHeadersFooters().clear();
            for (HeaderFooter headerFooter : savedSectionFormatting.HeadersFooters)
            {
                section.getHeadersFooters().add(headerFooter);
            }
        }
        finally
        {
            mDocumentBuilder.getDocument().resumeTrackRevisions(SuspendedRevisionTypes.ALL);
        }
    }

    private static class SectionFormatting
    {
        SectPr SectPr;
        HeaderFooter[] HeadersFooters;
    }

    private /*final*/ /*HtmlInsertOptions*/int mInsertOptions;
    private /*final*/ String mHtml;
    private /*final*/ DocumentBuilder mDocumentBuilder;
    private /*final*/ HashSetGeneric<String> mBookmarkNames;
}

