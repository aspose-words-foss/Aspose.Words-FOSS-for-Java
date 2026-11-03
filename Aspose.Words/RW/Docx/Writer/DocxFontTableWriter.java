// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.OpcPackagePart;
import com.aspose.OpcContentType;
import com.aspose.words.ref.Ref;


/// <summary>
/// Provides static method for writing "Font Table" package part
/// </summary>
class DocxFontTableWriter
{
    /// <summary>
    /// Writes "Font Table" document part for the specified document.
    /// </summary>
    static void write(DocxDocumentWriterBase writer) throws Exception
    {
        new DocxFontTableWriter(writer).write();
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    private DocxFontTableWriter(DocxDocumentWriterBase writer) throws Exception
    {
        mWriter = writer;
        mBuilder = writer.createChildPartAndBuilder("fontTable.xml", DocxContentType.FONT_TABLE, writer.getRelTypes().getFontTable());

        DocumentBase doc = mWriter.getDocument();
        DocPr docPr = doc.getDocPr();
        boolean embedTrueTypeFonts = docPr.EmbedTrueTypeFonts;
        boolean embedSystemFonts = docPr.EmbedSystemFonts;
        boolean saveSubsetFonts = docPr.SaveSubsetFonts;
        // FOSS Removed subsetting of embedded fonts.
        mFontInfos = doc.getFontInfos().cloneWithoutEmbeddedFonts();

        if (!embedTrueTypeFonts && (embedSystemFonts || saveSubsetFonts))
            writer.warn(WarningType.FONT_EMBEDDING, WarningStrings.FONTS_NOT_EMBEDDED_DUE_TO_EMBEDDING_DISABLED);
    }

    /// <summary>
    /// Writes "Font Table" document part using <see cref="mWriter"/>, <see cref="mBuilder"/> and <see cref="mFontInfos"/>
    /// initialized in ctor.
    /// </summary>
    private void write() throws Exception
    {
        mBuilder.startFontTableDocumentPart();

        // Write w:font elements.
        for (FontInfo fontInfo : mFontInfos)
            writeFontInfo(fontInfo);

        mBuilder.endDocument(); //w:fonts
    }

    /// <summary>
    /// Writes information related to one font from <see cref="FontInfoCollection"/> of the document.
    /// </summary>
    private void writeFontInfo(FontInfo fontInfo) throws Exception
    {
        Debug.assertTrue(fontInfo != null);

        mBuilder.startElement("w:font");

        String fontName = fontInfo.getName();

        // WORDSNET-13825 Try to fix font name if it is invalid.
        if (mBuilder.hasInvalidChars(fontName) && (!mBuilder.hasInvalidChars(fontInfo.getAltName())))
            fontName = fontInfo.getAltName();

        // WORDSNET-19382 Truncate font name to maximum allowed length.
        if (fontName.length() > FontInfo.MAX_FONT_NAME_LENGTH)
            fontName = fontName.substring((0), (0) + (FontInfo.MAX_FONT_NAME_LENGTH));

        mBuilder.writeAttributeString("w:name", fontName);

        NrxFontWriter.writeAltName(fontInfo, mBuilder, SaveFormat.DOCX);

        if (fontInfo.getPanose() != null)
            mBuilder.writeVal("w:panose1", StringUtil.bytesToHex(fontInfo.getPanose()));

        if (mBuilder.getOoxmlCompliance() == OoxmlComplianceCore.ISO_STRICT)
            mBuilder.writeElementWithAttributes("w:charset", "w:characterSet", fontInfo.getIanaCharset());
        else
            mBuilder.writeVal("w:charset", FormatterPal.intToStrX2(fontInfo.getCharset()));
        mBuilder.writeVal("w:family", DocxEnum.fontFamilyToDocx(fontInfo.getFamily()));

        if (!fontInfo.isTrueType())
            mBuilder.writeEmptyElement("w:notTrueType");

        mBuilder.writeVal("w:pitch", StyleConvertUtil.fontPitchToXml(fontInfo.getPitch()));

        if (fontInfo.getSig() != null)
            writeSigElement(fontInfo.getSig());

        EmbeddedFont[] embeddedFonts = fontInfo.getEmbeddedFonts();
        if (embeddedFonts != null)
            writeEmbeddedFonts(embeddedFonts);

        mBuilder.endElement(); // font
    }

    /// <summary>
    /// Writes <see cref="FontInfo.Sig"/> of specific font to font table.
    /// </summary>
    /// <param name="sig"></param>
    private void writeSigElement(byte[] sig)
    {
        Debug.assertTrue(sig != null);

        mBuilder.startElement("w:sig");
        mBuilder.writeAttributeString("w:usb0", NrxXmlUtil.intToHex(sig, 0));
        mBuilder.writeAttributeString("w:usb1", NrxXmlUtil.intToHex(sig, 4));
        mBuilder.writeAttributeString("w:usb2", NrxXmlUtil.intToHex(sig, 8));
        mBuilder.writeAttributeString("w:usb3", NrxXmlUtil.intToHex(sig, 12));
        mBuilder.writeAttributeString("w:csb0", NrxXmlUtil.intToHex(sig, 16));
        mBuilder.writeAttributeString("w:csb1", NrxXmlUtil.intToHex(sig, 20));
        mBuilder.endElement();    // sig
    }

    /// <summary>
    /// Writes up to four embedded fonts of different <see cref="EmbeddedFontStyle"/>s for one font name.
    /// </summary>
    private void writeEmbeddedFonts(EmbeddedFont[] embeddedFonts) throws Exception
    {
        Debug.assertTrue(embeddedFonts != null);

        for (EmbeddedFont embeddedFont : embeddedFonts)
        {
            if (embeddedFont == null)
                continue;

            // DOCX supports 'OpenType' font embedding format.
            EmbeddedFont openTypeFont = embeddedFont.getAsOpenType();
            if (openTypeFont == null)
                continue;

            String embeddedFontName = "fonts/font" + FormatterPal.intToStr(mEmbeddedFontOrdinal++) + ".odttf";
            writeEmbeddedFont(openTypeFont, embeddedFontName);
        }
    }

    /// <summary>
    /// Writes embedded font file to DOCX package and record to font table of the package.
    /// </summary>
    /// <param name="embeddedFont"></param>
    /// <param name="embeddedFontName"></param>
    private void writeEmbeddedFont(EmbeddedFont embeddedFont, String embeddedFontName) throws Exception
    {
        Debug.assertTrue(embeddedFont.getFormat() == EmbeddedFontFormat.OPEN_TYPE);

        String relId = null;
        Ref<String> referenceToRelId = new Ref<String>(relId);
        OpcPackagePart embeddedFontPart = mWriter.getPackage().createChildPart(
            mBuilder.getPart(),
            embeddedFontName,
            OpcContentType.ODTTF,
            mWriter.getRelTypes().getFont(), /*out*/ referenceToRelId);
        relId = referenceToRelId.get();

        String fontKey = DocxFontObfuscator.writeObfuscatedToStream(embeddedFont, embeddedFontPart.getStream());

        mBuilder.startElement(getEmbeddedFontElementName(embeddedFont.getStyle()));
        mBuilder.writeAttributeString("r:id", relId);
        mBuilder.writeAttributeIfTrue("w:subsetted", embeddedFont.isSubsetted());
        mBuilder.writeAttributeString("w:fontKey", fontKey);
        mBuilder.endElement();
    }

    /// <summary>
    /// Converts <see cref="EmbeddedFontStyle"/> to name of element in font table.
    /// </summary>
    private static String getEmbeddedFontElementName(/*EmbeddedFontStyle*/int fontStyle)
    {
        switch (fontStyle)
        {
            case EmbeddedFontStyle.REGULAR:
                return "w:embedRegular";
            case EmbeddedFontStyle.BOLD:
                return "w:embedBold";
            case EmbeddedFontStyle.ITALIC:
                return "w:embedItalic";
            case EmbeddedFontStyle.BOLD_ITALIC:
                return "w:embedBoldItalic";
            default:
                throw new IllegalArgumentException("fontStyle");
        }
    }

    private /*final*/ DocxDocumentWriterBase mWriter;
    private /*final*/ DocxBuilder mBuilder;
    private /*final*/ FontInfoCollection mFontInfos;

    /// <summary>
    /// It's used to create unique embedded font names in package.
    /// </summary>
    private int mEmbeddedFontOrdinal = 1;
}

