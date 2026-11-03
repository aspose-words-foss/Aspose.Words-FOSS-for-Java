// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Writes footnotes and endnotes.
/// </summary>
class DocxFootnotesWriter extends NrxFootnotesWriter
{
    DocxFootnotesWriter(/*FootnoteType*/int footnoteType, DocxDocumentWriterBase writer) throws Exception
    {
    	super(footnoteType,
            (footnoteType == FootnoteType.ENDNOTE)
                ? writer.getSaveInfo().HasEndnotes || !writer.getDocument().getFootnoteSeparators().isDefault()
                : writer.getSaveInfo().HasFootnotes || !writer.getDocument().getFootnoteSeparators().isDefault(),
            writer);
	
        mWriter = writer;

        if (hasFootnotes())
        {
            String partName;
            String contentType;
            String relType;
            String rootName;
            switch (footnoteType)
            {
                case FootnoteType.FOOTNOTE:
                    partName = "footnotes.xml";
                    contentType = DocxContentType.FOOTNOTES;
                    relType = writer.getRelTypes().getFootnotes();
                    rootName = "w:footnotes";
                    break;
                case FootnoteType.ENDNOTE:
                    partName = "endnotes.xml";
                    contentType = DocxContentType.ENDNOTES;
                    relType = writer.getRelTypes().getEndnotes();
                    rootName = "w:endnotes";
                    break;
                default:
                    throw new IllegalStateException("Unknown footnote type.");
            }

            mBuilder = mWriter.createChildPartAndBuilder(partName, contentType, relType);
            mBuilder.startDocumentWithStandardNamespaces(rootName);
        }
    }

    void close()
    {
        if (mBuilder != null)
            mBuilder.endDocument();
    }

    /// <summary>
    /// Writes a footnote start into "Footnotes"/"Endnotes" package part.
    /// </summary>
    void writeFootnoteStart()
    {
        mBuilder.startElement(isEndnote() ? "w:endnote" : "w:footnote");
        mBuilder.writeAttribute("w:id", mId);

        // We need to write all the following story nodes to "Footnotes"/"Endnotes" part, so save the current builder in the main writer.
        mWriter.pushBuilder(mBuilder);
    }

    /// <summary>
    /// Writes footnote end into "Footnotes"/"Endnotes" package part.
    /// </summary>
    /// <returns>Returns footnote reference id.</returns>
    int writeFootnoteEnd()
    {
        mBuilder.endElement();

        // Restore the writer's builder to the previous setting.
        mWriter.popBuilder();

        return mId++;
    }

    /// <summary>
    /// Writes footnote separator definitions to "Footnote"/"Endnote" builder.
    /// and corresponding footnote references to the current builder.
    /// </summary>
    protected /*override*/ void writeSeparator(FootnoteSeparator separator) throws Exception
    {
        if (separator == null)
            return;

        writeFootnoteStart(separator.getSeparatorType());
        separator.accept(mWriter);
        int id = writeFootnoteEnd();

        mWriter.writeElementWithId(isEndnote() ? "w:endnote" : "w:footnote", id);
    }

    /// <summary>
    /// Converts the specified number style to a string value to write it to document XML.
    /// </summary>
    protected /*override*/ String numberStyleToXml(/*NumberStyle*/int value)
    {
        return DocxEnum.numberStyleToDocx(value);
    }

    /// <summary>
    /// Writes a footnote start into "Footnotes"/"Endnotes" package part for footnote/endnote separators.
    /// </summary>
    private void writeFootnoteStart(/*FootnoteSeparatorType*/int separatorType)
    {
        mBuilder.startElement(isEndnote() ? "w:endnote" : "w:footnote");
        mBuilder.writeAttributeIfNotDefault("w:type", DocxEnum.footnoteSeparatorTypeToDocx(separatorType, isEndnote()), "normal");
        mBuilder.writeAttribute("w:id", mId);

        // We need to write all the following story nodes to "Footnotes"/"Endnotes" part, so save the current builder in the main writer.
        mWriter.pushBuilder(mBuilder);
    }

    private /*final*/ DocxDocumentWriterBase mWriter;
    /// <summary>
    /// This is the builder for the footnotes or endnotes package part.
    /// </summary>
    private /*final*/ DocxBuilder mBuilder;
    private int mId;
}

