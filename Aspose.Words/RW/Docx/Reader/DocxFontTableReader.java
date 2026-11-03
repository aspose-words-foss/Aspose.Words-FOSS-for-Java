// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.Debug;


/// <summary>
/// Provides static method for reading "Font Table" package part.
/// </summary>
/*static*/ class DocxFontTableReader
{
	/* Simulation of static class by using private constructor */
	private DocxFontTableReader()
	{}

    /// <summary>
    /// Reads "Font Table" package part.
    /// </summary>
    static void read(DocxDocumentReaderBase reader) throws Exception
    {
        if (reader.getLoadOptions().getSkipFormatting())
            return;

        NrxXmlReader xmlReader = reader.switchToPartReaderByRelType(reader.getRelTypes().getFontTable());
        if (xmlReader == null)
            return;

        FontInfoCollection fontInfos = reader.getDocument().getFontInfos();

        while (xmlReader.readChild("fonts"))    // w:fonts
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"font"*/0:    // w:font
                    fontInfos.merge(readFont(reader));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.restorePartReader();
    }

    /// <summary>
    /// Reads 'w:font' element.
    /// </summary>
    private static FontInfo readFont(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        FontInfo fontInfo = new FontInfo(xmlReader.readAttribute("name", ""));

        while (xmlReader.readChild("font"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"altName"*/1:
                {
                    // WORDSNET-27952 AltName specified as inner text rather than as val attribute.
                    String altName = xmlReader.readVal();
                    if (StringUtil.hasChars(altName))
                        fontInfo.setAltName(altName);
                    break;
                }
                case /*"panose1"*/2:
                {
                    // WORDSNET-20762 Panose can be null, need to check it.
                    String panose = xmlReader.readVal();
                    if (StringUtil.hasChars(panose))
                        fontInfo.setPanose(NrxXmlUtil.hexToBytes(panose, FontInfo.PANOSE_LENGTH));
                    break;
                }
                case /*"charset"*/3:
                {
                    String charset = xmlReader.readVal();
                    if (StringUtil.hasChars(charset))
                    {
                        // WORDSNET-19353 0x prefix is not allowed. Make format parsable.
                        fontInfo.setCharset(NrxXmlUtil.hexToInt(charset.replace("x", "")));
                    }
                    else
                    {
                        // characterSet - IANA Name of Character Set. Name of the character set associated with the font.
                        // The values allowed by this attribute are defined by the names and aliases listed in the IANA registration table.
                        // If this value is specified, the value of the val attribute is ignored.
                        charset = xmlReader.readAttribute("characterSet", "");
                        if (StringUtil.hasChars(charset))
                        {
                            fontInfo.setIanaCharset(charset);
                            reader.getComplianceInfo().markAsIsoTransitional();
                        }
                    }
                    break;
                }
                case /*"family"*/4:
                    fontInfo.setFamily(DocxEnum.docxToFontFamily(xmlReader.readVal()));
                    break;
                case /*"notTrueType"*/5:
                    fontInfo.isTrueType(!xmlReader.readBoolVal());
                    break;
                case /*"pitch"*/6:
                    fontInfo.setPitch(StyleConvertUtil.xmlToFontPitch(xmlReader.readVal()));
                    break;
                case /*"sig"*/7:
                {
                    fontInfo.setSig(new byte[FontInfo.SIG_LENGTH]);
                    while (xmlReader.moveToNextAttribute())
                    {
                        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                        {
                            case /*"usb0"*/12:
                                NrxXmlUtil.hexToBytesReversed(xmlReader.getValue(), fontInfo.getSig(), 0);
                                break;
                            case /*"usb1"*/13:
                                NrxXmlUtil.hexToBytesReversed(xmlReader.getValue(), fontInfo.getSig(), 4);
                                break;
                            case /*"usb2"*/14:
                                NrxXmlUtil.hexToBytesReversed(xmlReader.getValue(), fontInfo.getSig(), 8);
                                break;
                            case /*"usb3"*/15:
                                NrxXmlUtil.hexToBytesReversed(xmlReader.getValue(), fontInfo.getSig(), 12);
                                break;
                            case /*"csb0"*/16:
                                NrxXmlUtil.hexToBytesReversed(xmlReader.getValue(), fontInfo.getSig(), 16);
                                break;
                            case /*"csb1"*/17:
                                NrxXmlUtil.hexToBytesReversed(xmlReader.getValue(), fontInfo.getSig(), 20);
                                break;
                            default:
                                Debug.fail(xmlReader.getLocalName());
                                break;
                        }
                    }
                    break;
                }
                case /*"embedRegular"*/8:
                    addEmbeddedDataToFontInfo(reader, xmlReader, fontInfo, EmbeddedFontStyle.REGULAR);
                    break;
                case /*"embedBold"*/9:
                    addEmbeddedDataToFontInfo(reader, xmlReader, fontInfo, EmbeddedFontStyle.BOLD);
                    break;
                case /*"embedItalic"*/10:
                    addEmbeddedDataToFontInfo(reader, xmlReader, fontInfo, EmbeddedFontStyle.ITALIC);
                    break;
                case /*"embedBoldItalic"*/11:
                    addEmbeddedDataToFontInfo(reader, xmlReader, fontInfo, EmbeddedFontStyle.BOLD_ITALIC);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        return fontInfo;
    }

    private static void addEmbeddedDataToFontInfo(DocxDocumentReaderBase reader, NrxXmlReader xmlReader,
        FontInfo fontInfo, /*EmbeddedFontStyle*/int embeddedFontStyle)
    {
        String relId = xmlReader.readId();
        String fontKey = xmlReader.readAttribute("fontKey", null);
        boolean isSubsetted = xmlReader.readBoolAttribute("subsetted", false);

        byte[] data = reader.getDetachedBinData(relId);
        if (fontKey != null)
            DocxFontObfuscator.deObfuscate(data, fontKey);

        // DOCX can contain up to 4 identical font files of different styles (regular, bold, italic, bold-italic)
        // used for a font that has no special file for particular style. GetNonDuplicatedBytes function
        // saves space in the model for this case.
        data = reader.getNonDuplicatedBytes(data);

        fontInfo.addEmbeddedFont(data, EmbeddedFontFormat.OPEN_TYPE, embeddedFontStyle, isSubsetted);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"font",
		"altName",
		"panose1",
		"charset",
		"family",
		"notTrueType",
		"pitch",
		"sig",
		"embedRegular",
		"embedBold",
		"embedItalic",
		"embedBoldItalic",
		"usb0",
		"usb1",
		"usb2",
		"usb3",
		"csb0",
		"csb1"
	);

}

