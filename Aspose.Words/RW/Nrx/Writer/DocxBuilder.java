// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/05/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackagePart;
import com.aspose.ms.System.Text.UTF8Encoding;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.DateTime;
import com.aspose.FormatterPal;
import com.aspose.DrColor;
import java.util.ArrayList;


class DocxBuilder extends NrxXmlBuilder
{
    /// <summary>
    /// Creates DOCX builder, which writes in UTF8 encoding. MS Word writes in UTF8 too.
    /// </summary>
    /// <remarks>
    /// WORDSNET-12920 MS Word versions 2007-2019 never writes BOM to DOCX parts.
    /// AW writes BOM because we are using the static UTF8 property on the Encoding class.
    /// When the GetPreamble method is called on the instance of the Encoding class returned by the UTF8 property,
    /// it returns the byte order mark (the byte array of three characters) and is written to the stream before any other content
    /// is written to the stream (assuming a new stream).
    /// We can avoid this by creating the instance of the UTF8Encoding(false) class yourself, like below.
    /// </remarks>
    DocxBuilder(OpcPackagePart part, boolean isPrettyFormat, /*OoxmlComplianceCore*/int compliance,
        /*MsWordVersionCore*/int extensionVersion, IWarningCallback warningCallback) throws Exception
    {
    	super(part.getStream(), new UTF8Encoding(false), isPrettyFormat, (compliance == OoxmlComplianceCore.ECMA_376));
	
        mPart = part;
        mCompliance = compliance;
        mMsWordExtensionVersion = extensionVersion;
        mDocxNamespaces = new DocxNamespaces(mCompliance == OoxmlComplianceCore.ISO_STRICT);
        mWarningCallback = warningCallback;
    }

    /// <summary>
    /// Writes the start of a document with the 9 standard OOXML namespaces and prefixes.
    /// </summary>
    void startDocumentWithStandardNamespaces(String rootName)
    {
        startDocument(rootName);

        if (isIso29500Up())
        {
            writeAttributeString("xmlns:wpc", mDocxNamespaces.getWordprocessingCanvas());
            writeWord2016SpecificNs();
            writeMarkupCompatibilityNs();
        }
        else
        {
            writeAttributeString("xmlns:ve", mDocxNamespaces.getMarkupCompatibility());
        }

        writeAttributeString("xmlns:o", NrxNamespaces.OFFICE);
        writeAttributeString("xmlns:r", mDocxNamespaces.getRelationships());
        writeAttributeString("xmlns:m", mDocxNamespaces.getMath());
        writeAttributeString("xmlns:v", NrxNamespaces.VML);

        if (isIso29500Up())
            writeAttributeString("xmlns:wp14", mDocxNamespaces.getDrawingMLIso29500());

        writeAttributeString("xmlns:wp", mDocxNamespaces.getDrawingML());
        writeAttributeString("xmlns:w10", NrxNamespaces.WORD);
        writeAttributeString("xmlns:w", mDocxNamespaces.getMain());

        if (isIso29500Up())
        {
            writeWord2012SpecificNs();
        }

        writeAttributeString("xmlns:wne", mDocxNamespaces.getWmlBeta());

        if (isIso29500Up())
        {
            writeW16Ns();

            writeAttributeString("xmlns:wpg", mDocxNamespaces.getWordprocessingGroup());
            writeAttributeString("xmlns:wpi", mDocxNamespaces.getWordprocessingInk());
            writeAttributeString("xmlns:wps", mDocxNamespaces.getWordrocessingShape());
            writeIgnorableNs(true);
        }
    }

    /// <summary>
    /// Writes Word Markup extension namespaces introduced in Word 2016 and Word 2019.
    /// </summary>
    private void writeW16Ns()
    {
        if (mMsWordExtensionVersion >= MsWordVersionCore.WORD_2016)
        {
            writeAttributeString("xmlns:w16cid", mDocxNamespaces.getW16Cid());
            writeAttributeString("xmlns:w16se", mDocxNamespaces.getW16Symex());
        }

        if (mMsWordExtensionVersion >= MsWordVersionCore.WORD_2019)
        {
            writeAttributeString("xmlns:w16", mDocxNamespaces.getW16Markup());
            writeAttributeString("xmlns:w16cex", mDocxNamespaces.getW16Cex());
            writeAttributeString("xmlns:w16sdtdh", mDocxNamespaces.getW16Sdtdh());
        }
    }

    /// <summary>
    /// Writes main element w:fonts of fontTable.xml, takes OOXML compliance into the account to properly write namespaces.
    /// </summary>
    /// <remarks>Keeps Namespace order the same way MS Word does.</remarks>
    void startFontTableDocumentPart()
    {
        Debug.assertCallingClass("DocxFontTableWriter");

        startDocument("w:fonts");
        writeStandardNamespaces();
    }

    void startExtendedPropertiesDocumentPart()
    {
        Debug.assertCallingClass("DocxExtentedPropertiesWriter");
        startDocument("Properties");

        if (isIso29500Strict())
        {
            writeAttributeString("xmlns",
                "http://purl.oclc.org/ooxml/officeDocument/extendedProperties");
            writeAttributeString("xmlns:vt",
                "http://purl.oclc.org/ooxml/officeDocument/docPropsVTypes");
        }
        else
        {
            writeAttributeString("xmlns",
                "http://schemas.openxmlformats.org/officeDocument/2006/extended-properties");
            writeAttributeString("xmlns:vt",
                "http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes");
        }
    }

    void startCustomPropertiesDocumentPart()
    {
        Debug.assertCallingClass("DocxCustomPropertiesWriter");
        startDocument("Properties");

        if (isIso29500Strict())
        {
            writeAttributeString("xmlns",
                "http://purl.oclc.org/ooxml/officeDocument/customProperties");
            writeAttributeString("xmlns:vt",
                "http://purl.oclc.org/ooxml/officeDocument/docPropsVTypes");
        }
        else
        {
            writeAttributeString("xmlns",
                "http://schemas.openxmlformats.org/officeDocument/2006/custom-properties");
            writeAttributeString("xmlns:vt",
                "http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes");
        }
    }

    /// <summary>
    /// Writes main element w:settings of settings.xml, takes OOXML compliance into the account to properly write namespaces.
    /// </summary>
    /// <remarks>Keeps Namespace order the same way MS Word does.</remarks>
    void startSettingsDocumentPart()
    {
        Debug.assertCallingClass("DocxSettingsWriter");

        startDocument("w:settings");

        if (isIso29500Up())
            writeMarkupCompatibilityNs();

        writeAttributeString("xmlns:o", NrxNamespaces.OFFICE);
        writeAttributeString("xmlns:r", mDocxNamespaces.getRelationships());
        writeAttributeString("xmlns:m", mDocxNamespaces.getMath());
        writeAttributeString("xmlns:v", NrxNamespaces.VML);
        writeAttributeString("xmlns:w10", NrxNamespaces.WORD);
        writeAttributeString("xmlns:w", mDocxNamespaces.getMain());

        if (isIso29500Up())
        {
            writeWord2012SpecificNs();
            writeW16Ns();
        }

        writeAttributeString("xmlns:sl", mDocxNamespaces.getSchemaLibrary());

        if (isIso29500Up())
            writeIgnorableNs(false);
    }

    /// <summary>
    /// Writes main element w:webSettings of webSettings.xml, takes OOXML compliance into the account to properly write namespaces.
    /// </summary>
    /// <remarks>Keeps Namespace order the same way MS Word does.</remarks>
    void startWebSettingsDocumentPart()
    {
        Debug.assertCallingClass("DocxWebSettingsWriter");
        startDocument("w:webSettings");
        writeStandardNamespaces();
    }

    /// <summary>
    /// Writes main element w:styles of styles.xml, takes OOXML compliance into the account to properly write namespaces.
    /// </summary>
    /// <remarks>Keeps Namespace order the same way MS Word does.</remarks>
    void startStylesDocumentPart()
    {
        Debug.assertCallingClass("DocxStylesWriter");
        startDocument("w:styles");
        writeStandardNamespaces();
    }

    //>>>>>>>> #region  Namespace Writing Routines

    private void writeStandardNamespaces()
    {
        if (isIso29500Up())
            writeMarkupCompatibilityNs();

        writeAttributeString("xmlns:r", mDocxNamespaces.getRelationships());
        writeAttributeString("xmlns:w", mDocxNamespaces.getMain());

        if (isIso29500Up())
        {
            writeWord2012SpecificNs();
            writeW16Ns();
            writeIgnorableNs(false);
        }
    }

    private void writeMarkupCompatibilityNs()
    {
        writeAttributeString("xmlns:mc", mDocxNamespaces.getMarkupCompatibility());
    }

    private void writeWord2012SpecificNs()
    {
        writeAttributeString("xmlns:w14", mDocxNamespaces.getW14Markup());
        writeAttributeString("xmlns:w15", mDocxNamespaces.getW15Markup());
    }

    /// <summary>
    /// Writes specific namespaces that are written since 2016 version of MS Word.
    /// </summary>
    private void writeWord2016SpecificNs()
    {
        writeAttributeString("xmlns:cx", mDocxNamespaces.getChartEx());
        writeAttributeString("xmlns:cx1", mDocxNamespaces.getChartEx1());
        writeAttributeString("xmlns:cx2", mDocxNamespaces.getChartEx2());
        writeAttributeString("xmlns:cx3", mDocxNamespaces.getChartEx3());
        writeAttributeString("xmlns:cx4", mDocxNamespaces.getChartEx4());
        writeAttributeString("xmlns:cx5", mDocxNamespaces.getChartEx5());
    }

    private void writeIgnorableNs(boolean writeDrawingNs)
    {
        String prefixes =
            "w14 w15" +
            ((writeDrawingNs) ? " wp14" : "") +
            ((mMsWordExtensionVersion >= MsWordVersionCore.WORD_2016) ? " w16se w16cid" : "") +
            ((mMsWordExtensionVersion >= MsWordVersionCore.WORD_2019) ? " w16 w16cex w16sdtdh" : "");

        writeAttributeString("mc:Ignorable", prefixes);
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Writes border element.
    /// </summary>
    /// <param name="elementName">Name of the border element.</param>
    /// <param name="border">Border value.</param>
    protected /*override*/ void writeBorderCore(String elementName, Border border)
    {
        writeElementWithAttributes(
            elementName,
            "w:val", DocxEnum.lineStyleToDocx(border.getLineStyle()),
            "w:sz", border.getRawLineWidth(),
            "w:space", border.getRawDistanceFromText(),
            "w:color", border.getColorInternal(),
            "w:themeColor", border.getThemeColorInternal(),
            "w:themeShade", border.getThemeShade(),
            "w:themeTint", border.getThemeTint(),
            "w:shadow", dontWriteOff(border.getShadow()),
            "w:frame", dontWriteOff(border.getFrame()));
    }

    /// <summary>
    /// Writes w:shd element.
    /// </summary>
    /// <param name="shading">Shading value.</param>
    /*override*/ void writeShd(Shading shading)
    {
        // Example:
        //     <w:shd w:val="pct-12" w:color="auto" w:fill="0B1621" wx:bgcolor="0B1520" />

        if (shading == null || shading.isInherited())
            return;

        writeElementWithAttributes(
            "w:shd",
            "w:val", StyleConvertUtil.textureIndexToXml(shading.getTexture(), true),
            "w:color", shading.getForegroundPatternColorInternal(),
            "w:fill", shading.getBackgroundPatternColorInternal(),
            "w:themeColor", shading.getThemeColor(),
            "w:themeShade", shading.getThemeShade(),
            "w:themeTint", shading.getThemeTint(),
            "w:themeFill", shading.getThemeFill(),
            "w:themeFillShade", shading.getThemeFillShade(),
            "w:themeFillTint", shading.getThemeFillTint());
    }

    /// <summary>
    /// Writes element with the specified name and "w:val" attribute with "0" value if specified boolean value is false
    /// and "1" value if specified boolean value is true.
    /// </summary>
    /*override*/ void writeBoolValExplicit(String elementName, boolean value)
    {
        writeVal(elementName, value ? "1" : "0");
    }

    /// <summary>
    /// Writes element with the specified name and "w:val" attribute with "0" value if specified boolean value is false.
    /// If true, the empty element with the specified name is written.
    /// </summary>
    /*override*/ void writeVal(String elementName, boolean value)
    {
        if (value)
            writeEmptyElement(elementName); // do not write "1", "1" is default
        else
            writeVal(elementName, "0");
    }

    /// <summary>
    /// RK Override to write "1/0" instead of "on/off".
    /// </summary>
    /*override*/ void writeAttribute(String attributeName, boolean value)
    {
        writeAttributeString(attributeName, value ? "1" : "0");
    }

    /*override*/ void writeAttributeIfTrue(String attributeName, boolean value)
    {
        if (value)
            writeAttributeString(attributeName, "1");
    }

    /// <summary>
    /// Writes format revision element start.
    /// Does nothing. Concrete classes to implement if they support revisions.
    /// </summary>
    /*override*/ void writeRevisionStart(FormatRevision revision, String elementName, int id)
    {
        writeRevisionStartCore(elementName, id, revision.getAuthor(), revision.getDateTime());
    }

    /// <summary>
    /// Writes edit revision element start.
    /// Does nothing. Concrete classes to implement if they support revisions.
    /// </summary>
    /*override*/ void writeRevisionStart(EditRevision revision, int id)
    {
        String elemName = (revision.getType() == EditRevisionType.INSERTION) ? "w:ins" : "w:del";
        writeRevisionStartCore(elemName, id, revision.getAuthor(), revision.getDateTime());
    }

    /// <summary>
    /// Writes move revision element start.
    /// Does nothing. Concrete classes to implement if they support revisions.
    /// </summary>
    /*override*/ void writeRevisionStart(MoveRevision revision, int id)
    {
        String elemName = (revision.getType() == MoveRevisionType.MOVE_TO) ? "w:moveTo" : "w:moveFrom";
        writeRevisionStartCore(elemName, id, revision.getAuthor(), revision.getDateTime());
    }

    /// <summary>
    /// Writes revision element end. Provided for symmetry with StartRevision.
    /// Does nothing. Concrete classes to implement if they support revisions.
    /// </summary>
    /*override*/ void writeRevisionEnd()
    {
        endElement();
    }

    /// <summary>
    /// Writes a numbering revision.
    /// Does nothing. Concrete classes to implement if they support revisions.
    /// </summary>
    /*override*/ void writeRevision(ParagraphNumberRevision revision, int id)
    {
        String elemName = revision.isInsertion() ? "w:ins" : "w:numberingChange";
        // The numberingChange element was removed in ISO Strict.
        if (("w:numberingChange".equals(elemName)) && (getOoxmlCompliance() == OoxmlComplianceCore.ISO_STRICT))
        {
            warn(WarningType.MINOR_FORMATTING_LOSS,
                msString.format(WarningStrings.NOT_SUPPORTED_BY_ISO_STRICT, "numberingChange"));
            return;
        }

        writeRevisionStartCore(elemName, id, revision.getAuthor(), revision.getDateTime());

        // WORDSNET-9507 In the DOCX specification mentioned that w:original is optional.
        // But for some reason MS Word cannot open DOCX/WML document with omited w:original.
        // So write it in all cases, even when it is empty string.
        if (revision.isNumbering())
            writeAttributeString("w:original", NrxXmlUtil.getOriginal(revision));

        endElement(elemName);
    }

    /*override*/ void writeRevision(FieldNumberRevision revision, int id)
    {
        if (getOoxmlCompliance() == OoxmlComplianceCore.ISO_STRICT)
        {
            warn(
                WarningType.MINOR_FORMATTING_LOSS,
                msString.format(WarningStrings.NOT_SUPPORTED_BY_ISO_STRICT, "numberingChange"));
            return;
        }

        writeRevisionStartCore("w:numberingChange", id, revision.getAuthor(), revision.getDateTime());
        writeAttributeString("w:original", revision.getOriginal());
        endElement("w:numberingChange");
    }

    /*override*/ void writeRevision(EditRevision revision, int id)
    {
        String elemName = (revision.getType() == EditRevisionType.INSERTION) ? "w:ins" : "w:del";
        writeRevisionStartElement(elemName, revision, id);
    }

    /// <summary>
    /// Writes move revision element.
    /// </summary>
    /*override*/ void writeRevision(MoveRevision revision, int id)
    {
        if (revision.getType() != MoveRevisionType.NONE)
        {
            String elemName = (revision.getType() == MoveRevisionType.MOVE_FROM) ? "w:moveFrom" : "w:moveTo";
            writeRevisionStartElement(elemName, revision, id);
        }
    }

    /// <summary>
    /// Writes cell revision.
    /// </summary>
    /// <remarks>Cell revisions can be only inside OOXML documents.</remarks>
    /*override*/ void writeCellRevision(EditRevision revision, int id)
    {
        String elemName = (revision.getType() == EditRevisionType.INSERTION) ? "w:cellIns" : "w:cellDel";
        writeRevisionStartElement(elemName, revision, id);
    }

    /// <summary>
    /// Writes SDT edit revision start.
    /// </summary>
    /*override*/ void writeSdtRevisionStart(EditRevision revision, int id)
    {
        String elemName = (revision.getType() == EditRevisionType.INSERTION) ? "w:customXmlInsRangeStart" : "w:customXmlDelRangeStart";
        writeRevisionStartElement(elemName, revision, id);
    }

    /// <summary>
    /// Writes SDT move revision start.
    /// </summary>
    /*override*/ void writeSdtRevisionStart(MoveRevision revision, int id)
    {
        if (revision.getType() == MoveRevisionType.NONE)
            return;

        String elemName = (revision.getType() == MoveRevisionType.MOVE_FROM)
            ? "w:customXmlMoveFromRangeStart"
            : "w:customXmlMoveToRangeStart";
        writeRevisionStartElement(elemName, revision, id);
    }

    /// <summary>
    /// Writes SDT edit revision end.
    /// </summary>
    /*override*/ void writeSdtRevisionEnd(EditRevision revision, int id)
    {
        String elemName = (revision.getType() == EditRevisionType.INSERTION) ? "w:customXmlInsRangeEnd" : "w:customXmlDelRangeEnd";
        writeRevisionStartCore(elemName, id, "", DateTime.MinValue);
        endElement(elemName);
    }

    /// <summary>
    /// Writes SDT move revision end.
    /// </summary>
    /*override*/ void writeSdtRevisionEnd(MoveRevision revision, int id)
    {
        if (revision.getType() == MoveRevisionType.NONE)
            return;

        String elemName = (revision.getType() == MoveRevisionType.MOVE_FROM)
            ? "w:customXmlMoveFromRangeEnd"
            : "w:customXmlMoveToRangeEnd";
        writeRevisionStartCore(elemName, id, "", DateTime.MinValue);
        endElement(elemName);
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    void warn(/*WarningType*/int type, String description)
    {
        if (mWarningCallback != null)
            mWarningCallback.warning(new WarningInfo(type, WarningSource.DOCX, description));
    }

    /// <summary>
    /// Writes revision start element.
    /// </summary>
    private void writeRevisionStartElement(String elemName, RevisionBase revision, int id)
    {
        writeRevisionStartCore(elemName, id, revision.getAuthor(), revision.getDateTime());
        endElement(elemName);
    }

    private void writeRevisionStartCore(String elementName, int id, String author, DateTime dateTime)
    {
        startElement(elementName);
        writeAttribute("w:id", id);
        writeAttribute("w:author", author);
        if (!DateTime.equals(dateTime, DateTime.MinValue))
            writeAttribute("w:date", FormatterPal.dateTimeToXmlUtc(dateTime));
    }

    /*override*/ boolean isStylesBuilder() { return (DocxContentType.STYLES.equals(mPart.getContentType())); }

    /// <summary>
    /// Writes an element that contains only one attribute "r:id".
    /// </summary>
    void writeRelationshipId(String elemName, String relId)
    {
        startElement(elemName);
        writeAttributeString("r:id", relId);
        endElement();
    }

    /// <summary>
    /// WORDSNET-6471 See "part 1 reference c059575_ISO_IEC_29500-1_2011 §17.18.38".
    /// This simple type specifies that its contents shall contain a color value in RRGGBB hexadecimal format, specified
    /// using six hexadecimal digits. Each of the red, green, and blue color values, from 0-255, is encoded as two
    /// hexadecimal digits.
    /// </summary>
    static String getColorString(DrColor color)
    {
        return msString.format("{0}{1}{2}", FormatterPal.intToStrX2Lower(color.getR()),
            FormatterPal.intToStrX2Lower(color.getG()),
            FormatterPal.intToStrX2Lower(color.getB()));
    }

    OpcPackagePart getPart() { return mPart; }

    /// <summary>
    /// Keeps record of shapetype definitions already written to the document part where this builder writes to.
    /// </summary>
    ArrayList<String> getShapeTypesWritten()
    {
        if (mShapeTypesWritten == null)
            mShapeTypesWritten = new ArrayList<String>();

        return mShapeTypesWritten;
    }

    /// <summary>
    /// RsidR string for the current paragraph.
    /// Should be refactored to stack to work correctly as paragraphs can be nested.
    /// But since RSIDs implementation in the model is incomplete anyway - I leave it as is for now.
    /// </summary>
    String getRsidR() { return mRsidR; }
    void setRsidR(String value) { mRsidR = value; }

    /// <summary>
    /// Requires builder to adhere to one of <see cref="Saving.OoxmlCompliance"/> standards.
    /// </summary>
    /*OoxmlComplianceCore*/int getOoxmlCompliance() { return mCompliance; }

    private boolean isIso29500Up() { return mCompliance != OoxmlComplianceCore.ECMA_376; }

    /// <summary>
    /// Gets a flag indicating that the output document should conforms to ISO/IEC 29500 Strict specification.
    /// </summary>
    private boolean isIso29500Strict() { return mCompliance == OoxmlComplianceCore.ISO_STRICT; }

    private ArrayList<String> mShapeTypesWritten;
    private String mRsidR;
    private /*final*/ OpcPackagePart mPart;
    private /*final*/ DocxNamespaces mDocxNamespaces;
    private /*final*/ IWarningCallback mWarningCallback;
    private /*final*/ /*OoxmlComplianceCore*/int mCompliance;
    private /*final*/ /*MsWordVersionCore*/int mMsWordExtensionVersion;
}

