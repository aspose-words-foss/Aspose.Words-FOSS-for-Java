// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/12/2003 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.IO.StreamWriter;
import com.aspose.FontUtil;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// The base class for saving document in plain text formats.
/// </summary>
abstract class TxtWriterBase extends DocumentVisitor implements IDocumentWriter
{
    /// <summary>
    /// Saves a document or a fragment of a document in a plain text format.
    /// </summary>
    public SaveOutputParameters /*IDocumentWriter.*/saveToStream(SaveInfo saveInfo) throws Exception
    {
        Debug.assertTrue(saveInfo != null);
        setSaveInfo(saveInfo);

        init(saveInfo.Document, (TxtSaveOptionsBase)saveInfo.SaveOptions);

        for (Node child = mDocument.getFirstChild(); child != null; child = child.getNextSibling())
            writeSection((Section)child);
        getDocument().acceptEnd(this);

        // Save the text to the stream.
        StreamWriter writer = new StreamWriter(saveInfo.Stream, mSaveOptionsBase.getEncodingInternal());
        writer.write(mBuilder.toString());
        writer.write(mHeadersFootersBuilder.toString());

        // Just flush, but do not close the stream because closing is the client's responsibility.
        writer.flush();

        return new SaveOutputParameters("text/plain");
    }

    /// <summary>
    /// Saves a specified node to a string.
    /// </summary>
    String saveToString(Node node, TxtSaveOptionsBase saveOptions) throws Exception
    {
        // FOSS

        init(node.getDocument(), saveOptions);

        beforeNodeAccepted(node);
        node.accept(this);
        afterNodeAccepted(node);

        return getBuilder().toString();
    }

    protected /*virtual*/ void beforeNodeAccepted(Node node) throws Exception
    {
        if (node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START)
            ((StructuredDocumentTagRangeStart)node).acceptAsCompositeNode(this);
    }

    protected /*virtual*/ void afterNodeAccepted(Node node)
    {
    }

    /// <summary>
    /// Gets text of a specified run with applied formatting.
    /// </summary>
    static String getText(Run run)
    {
        String text = run.getText();

        if (run.getRunPr().contains(FontAttr.RUBY))
        {
            Ruby ruby = (Ruby)run.getRunPr().get(FontAttr.RUBY);
            text = ruby.getText();
        }

        text = run.getFont().getAllCaps() || isParagraphAllCaps(run)
            ? text.toUpperCase()
            : text;

        // WORDSNET-25757 Convert text with single character (that is most probably symbol entity (<w:sym> element in DOCX))
        // with Wingding font from PUA to analogue character from some other Unicode area. This allows to avoid converting
        // it later to Symbol character with FontUtil.UnicodeToSymbol(), as in some cases this is not correct.
        if ((text.length() == 1) && ("Wingdings".equals(run.getRunPr().getComplexNameAscii().resolve(run.getDocument().getThemeInternal()))))
            return Character.toString(FontUtil.wingdingToUnicode(text.charAt(0)));

        return text;
    }

    /// <summary>
    /// Switches current builder to a specified one.
    /// </summary>
    private void switchToBuilder(StringBuilder builder)
    {
        mBuilder = builder;
    }

    /// <summary>
    /// Writes all headers and footers of a specified section.
    /// </summary>
    protected /*virtual*/ void writeAllHeadersFooters(Section section) throws Exception
    {
        // Switch to specified string builder.
        switchToBuilder(getHeadersFootersBuilder());

        for (/*HeaderFooterType*/int type : G_HEADERS_FOOTERS_SORTED_TYPES)
        {
            writeHeaderFooter(section, type);
            // Insert paragraph break after each HeaderFooter to mimic Word behavior.
            if (section.getHeadersFooters().getByHeaderFooterType(type) != null)
                msStringBuilder.append(mBuilder, getSaveOptionsBase().getParagraphBreak());
        }

        switchToBuilder(getMainBuilder());
    }

    /// <summary>
    /// Writes HeaderFooter to a current string builder.
    /// </summary>
    protected void writeHeaderFooter(Section section, /*HeaderFooterType*/int headerFooterType) throws Exception
    {
        HeaderFooter header = section.getHeadersFooters().getByHeaderFooterType(headerFooterType);
        if (header != null)
            writeStory(header);
    }

    /// <summary>
    /// Writes section.
    /// </summary>
    private void writeSection(Section section) throws Exception
    {

        if (getSaveOptionsBase().getExportHeadersFootersMode() == TxtExportHeadersFootersMode.ALL_AT_END)
        {
            // When headers and footers are exporting using this mode, we have to collect their text and
            // write it at the very end of a document instead of writing it just here at the current section.
            // So, we write them into dedicated string builder.
            writeAllHeadersFooters(section);
        }

        if (getSaveOptionsBase().getExportHeadersFootersMode() == TxtExportHeadersFootersMode.PRIMARY_ONLY)
            writeHeaderFooter(section, HeaderFooterType.HEADER_PRIMARY);

        if (section.getBody() != null)
            writeStory(section.getBody());

        if (getSaveOptionsBase().getExportHeadersFootersMode() == TxtExportHeadersFootersMode.PRIMARY_ONLY)
            writeHeaderFooter(section, HeaderFooterType.FOOTER_PRIMARY);

    }

    /// <summary>
    /// Writes story.
    /// </summary>
    private void writeStory(Story story) throws Exception
    {
        story.accept(this);
    }

    /// <summary>
    /// Returns true, if a text of a specified run should be converted to upper case.
    /// </summary>
    private static boolean isParagraphAllCaps(Run run)
    {
        Paragraph parentParagraph = run.getParentParagraph();

        return ((parentParagraph != null) &&
                (parentParagraph.getParagraphStyle() != null) &&
                (parentParagraph.getParagraphStyle().getFont() != null) &&
                parentParagraph.getParagraphStyle().getFont().getAllCaps());
    }

    /// <summary>
    /// Initializes writer.
    /// </summary>
    private void init(DocumentBase doc, TxtSaveOptionsBase saveOptions)
    {
        // Handling of char array TxtWriter.gSimplifiedBulletsCharacters is based on these constants.
        // Fix this handling inside ListNumberGenerator class if assertions are violated.
        Debug.assertTrue((ListLevel.MIN_LEVEL == 0) && (ListLevel.MAX_LEVELS == 9));
        Debug.assertTrue(doc != null);
        Debug.assertTrue(saveOptions != null);

        mDocument = doc;
        mSaveOptionsBase = saveOptions;

        mMainBuilder = new StringBuilder();
        mHeadersFootersBuilder = new StringBuilder();

        switchToBuilder(mMainBuilder);
    }

    /// <summary>
    /// Gets a document being written.
    /// </summary>
    DocumentBase getDocument() { return mDocument; }

    /// <summary>
    /// Gets current builder.
    /// </summary>
    StringBuilder getBuilder() { return mBuilder; }

    /// <summary>
    /// Gets a builder that will be eventually written into a text file.
    /// </summary>
    protected StringBuilder getMainBuilder() { return mMainBuilder; }

    /// <summary>
    /// Gets builder for writing headers and footers.
    /// </summary>
    protected StringBuilder getHeadersFootersBuilder() { return mHeadersFootersBuilder; }

    /// <summary>
    /// Gets save options.
    /// </summary>
    protected TxtSaveOptionsBase getSaveOptionsBase() { return mSaveOptionsBase; }

    /// <summary>
    /// Encapsulates parameters passed into a document writer.
    /// </summary>
    protected SaveInfo getSaveInfo() { return mSaveInfo; }; private void setSaveInfo(SaveInfo value) { mSaveInfo = value; };

    private SaveInfo mSaveInfo;

    /// <summary>
    /// The array of headers and footers types sorted in the same order as Word writes them in plain text format.
    /// </summary>
    private static /*final*/ /*HeaderFooterType*/int[] G_HEADERS_FOOTERS_SORTED_TYPES =
    {
        HeaderFooterType.HEADER_EVEN,
        HeaderFooterType.HEADER_PRIMARY,
        HeaderFooterType.FOOTER_EVEN,
        HeaderFooterType.FOOTER_PRIMARY,
        HeaderFooterType.HEADER_FIRST,
        HeaderFooterType.FOOTER_FIRST
    };

    private TxtSaveOptionsBase mSaveOptionsBase;

    private DocumentBase mDocument;

    private StringBuilder mBuilder;
    private StringBuilder mMainBuilder;
    private StringBuilder mHeadersFootersBuilder;
}

