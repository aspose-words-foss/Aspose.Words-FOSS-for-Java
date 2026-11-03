// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/05/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.IO.Stream;
import com.aspose.Debug;
import com.aspose.ms.System.IO.StreamReader;
import java.util.HashMap;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.DrFont;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.Collections.msDictionary;


/// <summary>
/// Imports plain text file into a Document object.
/// </summary>
class TxtReader implements IDocumentReader
{
    /// <summary>
    /// Ctor.
    /// </summary>
    TxtReader(Stream stream, LoadOptions loadOptions, FileFormatInfo fileFormatInfo, Document document) throws Exception
    {
        Debug.assertTrue(stream != null);
        Debug.assertTrue(document != null);
        mStream = stream;
        mDocument = document;

        mLoadOptions = ms.as(loadOptions, TxtLoadOptions.class);
        if (mLoadOptions == null)
            mLoadOptions = new TxtLoadOptions(loadOptions);

        FileFormatInfo info;
        if (fileFormatInfo != null)
        {
            info = fileFormatInfo;
        }
        else
        {
            FileFormatDetector detector = new FileFormatDetector();
            info = detector.detect(mStream);
        }

        mEncoding = getEncoding(mLoadOptions, info);
        mHasRtlScript = info.hasRtlScript();
        mSpaceWidth = -1;
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    TxtReader(Stream stream, Document document, FileFormatInfo fileFormatInfo) throws Exception
    {
    	this(stream, null, fileFormatInfo, document);
	
    }

    //>>>>>>>> #region  Methods

    /// <summary>
    /// Reads plain text file into the model.
    /// </summary>
    public void read() throws Exception
    {
        // For safety reasons. Obtaining a non-empty document is unlikely.
        boolean wasEmptyDocument = mDocument.getFirstSection() == null;
        mDocumentBuilder = new DocumentBuilder(mDocument);

        // WORDSNET-19067 We have introduced a new DocumentDirection load option and
        // need to set paragraph BiDi attribute in accordance with it.
        if ((mLoadOptions.getDocumentDirection() == DocumentDirection.RIGHT_TO_LEFT) ||
            ((mLoadOptions.getDocumentDirection() == DocumentDirection.AUTO) && mHasRtlScript))
            mDocumentBuilder.getParagraphFormat().setBidi(true);

        setFontProperties(mDocumentBuilder);
        TxtParagraphReader paragraphReader = new TxtParagraphReader(new StreamReader(mStream, mEncoding), mLoadOptions);
        mParagraphLists = new HashMap<TxtParagraph, List>();
        mSpaceWidth = -1;

        // WORDSNET-22891 Implemented TXT loading progress notification.
        LoadingProgressProcessor progressProcessor = new LoadingProgressProcessor(mDocument, mLoadOptions);
        TxtParagraph[] paragraphs = paragraphReader.read(progressProcessor);
        for (TxtParagraph paragraph : paragraphs)
            write(paragraph);
        removeNumbersInNeed();

        if (wasEmptyDocument && !mDocument.getFirstSection().getBody().getLastParagraph().hasChildNodes())
            mDocument.getFirstSection().getBody().getLastParagraph().remove();

        // After correct bidi runs are generated, we set fonts to them depending on text language like MS Word does.
        // If necessary, runs are split to several to assign font.
        mDocumentBuilder.getDocument().accept(new TxtFontSetter());

        // WORDSNET-17877 Set locale-dependent default page size.
        mDocument.getFirstSection().getSectPr().setDefaultPageSize();
    }

    public Stream decrypt()
    {
        Debug.assertTrue(false, "Not supported");
        return null;
    }

    /// <summary>
    /// Converts indent in chars to indent in points.
    /// </summary>
    double getIndentInPoints(int indentInChars) throws Exception
    {
        if (mSpaceWidth < 0)
            mSpaceWidth = getTextWidth("w");
        return mSpaceWidth * indentInChars;
    }

    /// <summary>
    /// Returns encoding obtained either from a specified LoadOptions or FileFormatInfo.
    /// By default returns UTF8.
    /// </summary>
    static Encoding getEncoding(LoadOptions loadOptions, FileFormatInfo fileFormatInfo)
    {
        if ((loadOptions != null) && (loadOptions.getEncodingInternal() != null))
            return loadOptions.getEncodingInternal();

        if ((fileFormatInfo != null) && (fileFormatInfo.getEncodingInternal() != null))
            return fileFormatInfo.getEncodingInternal();

        return Encoding.getUTF8();
    }

    /// <summary>
    /// Writes the text paragraph into the document.
    /// </summary>
    private void write(TxtParagraph paragraph) throws Exception
    {
        if (paragraph.getNumbering() != null)
        {
            writeList(paragraph);
        }
        else
        {
            removeNumbersInNeed();
            writeParagraph(paragraph);
        }

        mPrevParagraph = paragraph;
    }

    /// <summary>
    /// Writes the text paragraph to document's paragraph.
    /// </summary>
    private void writeParagraph(TxtParagraph paragraph) throws Exception
    {
        mDocumentBuilder.getParagraphFormat().setLeftIndent(getIndentInPoints(paragraph.getLeftIndent()));
        mDocumentBuilder.getParagraphFormat().setFirstLineIndent(getIndentInPoints(paragraph.getFirstLineIndent()));

        paragraph.write(mDocumentBuilder, mLoadOptions);

        if (paragraph.isNewSectionRequested())
            mDocumentBuilder.insertSection(SectionStart.NEW_PAGE);
    }

    private void removeNumbersInNeed()
    {
        if ((mPrevParagraph != null) && (mPrevParagraph.getNumbering() != null))
            mDocumentBuilder.getListFormat().removeNumbers();
    }

    private double getTextWidth(String text) throws Exception
    {
        DocumentBuilder docBuilder;

        if(mDocumentBuilder == null)
        {
            docBuilder = new DocumentBuilder();
            setFontProperties(docBuilder);
        }
        else
            docBuilder = mDocumentBuilder;

        Font font = docBuilder.getFont();
        DrFont drawingFont = docBuilder.getDocument().getFontProvider().fetchDrFont(
            font.getName(), (float)font.getSize(), font.getFontStyle());
        return drawingFont.getTextWidthPoints(text);
    }

    /// <summary>
    /// Writes the text paragraph as document's list.
    /// </summary>
    private void writeList(TxtParagraph paragraph) throws Exception
    {
        setListToDocument(paragraph);
        setListFormating(paragraph.getNumbering());

        double numberPos = getIndentInPoints(paragraph.getNumbering().getNumberPosition());
        double numberWidth = getTextWidth(paragraph.getNumbering().getText() + "  ");
        mDocumentBuilder.getParagraphFormat().setLeftIndent(numberPos + numberWidth);
        mDocumentBuilder.getParagraphFormat().setFirstLineIndent(-numberWidth);

        paragraph.write(mDocumentBuilder, mLoadOptions);

        // WORDSNET-10612 List paragraph could be built from the string, which is ended with page break symbol (0x12).
        if (paragraph.isNewSectionRequested())
            mDocumentBuilder.insertSection(SectionStart.NEW_PAGE);
    }

    /// <summary>
    /// Sets document's list for the text paragraph.
    /// </summary>
    private void setListToDocument(TxtParagraph paragraph)
    {
        Debug.assertTrue(paragraph.getNumbering() != null);

        List list;
        if (paragraph.getNumbering().getPrevNumberingParagraph() != null)
            list = DictionaryExtension.getValueOrNull(mParagraphLists, paragraph.getNumbering().getPrevNumberingParagraph());
        else if (paragraph.getNumbering().getParentNumberingParagraph() != null)
            list = DictionaryExtension.getValueOrNull(mParagraphLists, paragraph.getNumbering().getParentNumberingParagraph());
        else
            list = mDocumentBuilder.getDocument().getLists().addEmpty(
                paragraph.getNumbering().getNumberingStyle().isLevelsSupported()
                    ? ListType.MULTI_LEVEL
                    : ListType.SINGLE_LEVEL);

        msDictionary.add(mParagraphLists, paragraph, list);
        mDocumentBuilder.getListFormat().setList(list);
    }

    /// <summary>
    /// Sets document's list formatting properties.
    /// </summary>
    /// <param name="numbering">Source numbering information</param>
    private void setListFormating(TxtNumbering numbering)
    {
        mDocumentBuilder.getListFormat().setListLevelNumber(numbering.getListLevelNumber());
        mDocumentBuilder.getListFormat().getListLevel().setNumberStyle(numbering.getNumberingStyle().getNumberStyle());

        // WORDSNET-9968 We should specify font properties for a ListLevel, in case the document starts from a list paragraph.
        setFontProperties(mDocumentBuilder.getListFormat().getListLevel().getFont());

        mDocumentBuilder.getListFormat().getListLevel().setNumberFormat(numbering.getNumberingStyle().getNumberFormat(numbering.getListLevelNumber()));
    }

    /// <summary>
    /// Sets document's builder font properties.
    /// </summary>
    /// <param name="documentBuilder">An instance of DocumentBuilder.</param>
    private static void setFontProperties(DocumentBuilder documentBuilder)
    {
        if (documentBuilder != null)
            setFontProperties(documentBuilder.getFont());
    }

    /// <summary>
    /// Sets font properties which used by MS Word while importing a text files.
    /// </summary>
    private static void setFontProperties(Font font)
    {
        font.setName(TxtFontSetter.DEFAULT_FONT);
        font.setSize(10.5);
        font.setSizeBi(10.5);
    }
    //<<<<<<<< #endregion 

    public boolean isEncrypted() { return false; }

    private DocumentBuilder mDocumentBuilder;
    private TxtParagraph mPrevParagraph;
    private HashMap<TxtParagraph, List> mParagraphLists;
    private double mSpaceWidth;
    private /*final*/ Stream mStream;
    private /*final*/ Document mDocument;
    private /*final*/ Encoding mEncoding;
    private /*final*/ boolean mHasRtlScript;
    private /*final*/ TxtLoadOptions mLoadOptions;
}

