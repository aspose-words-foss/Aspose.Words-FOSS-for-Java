// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/06/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.OpcRelationship;
import com.aspose.OpcPackagePart;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;


/// <summary>
/// Reads 5.1.8.9 theme (Theme).
///
/// At the moment stores the theme unparsed in the document so it preserved and can be saved.
/// At the moment parses only some info from the scheme related to fonts.
/// When later I implement complete theme parsing, then storing unparsed code will be removed.
/// </summary>
class DocxThemeReader extends DmlReaderBase
{
    private DocxThemeReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    /// <summary>
    /// Reads 5.1.8.9 theme (Theme).
    /// </summary>
    static void read(DocxDocumentReader reader) throws Exception
    {
        if (reader.getLoadOptions().getSkipFormatting())
            return;

        // Find the theme part is present. The theme part seems to be only specified for the
        // main document (not for glossary document).

        DocxXmlReader themeXmlReader = reader.switchToPartReaderByRelType(reader.getRelTypes().getTheme());
        if (themeXmlReader == null)
            return;

        // Create a theme and store in the document.
        Theme theme = new Theme();
        reader.getDocument().setThemeInternal(theme);

        // The theme needs this information from the settings to work properly.
        theme.setThemeFontLanguages(reader.getDocument().getDocPr().ThemeFontLanguages);

        // Store the unparsed theme part in the model because we do not do full parsing yet.
        theme.setThemePart(themeXmlReader.getPart(), themeXmlReader.getNamespaceURI());

        // Also store unparsed in the model all internal child parts that are related to the theme.
        for (OpcRelationship rel : themeXmlReader.getPart().getRels())
            readThemeRel(rel, reader, theme);

        DocxThemeReader themeReader = new DocxThemeReader(reader);
        themeReader.readCore(theme);
        reader.restorePartReader();
    }

    /// <summary>
    /// Reads 5.1.8.9 theme (Theme).
    /// </summary>
    static Theme readThemeXml(DocxDocumentReader reader) throws Exception
    {
        // Create a theme and store in the document.
        Theme theme = new Theme();

        // The theme needs this information from the settings to work properly.
        theme.setThemeFontLanguages(reader.getDocument().getDocPr().ThemeFontLanguages);

        DocxThemeReader themeReader = new DocxThemeReader(reader);
        themeReader.readCore(theme);
        reader.restorePartReader();

        return theme;
    }

    /// <summary>
    /// Reads 5.1.8.9 theme (Theme).
    /// </summary>
    static Theme readThemeOverride(DocxDocumentReaderBase reader) throws Exception
    {
        if (!reader.isDocx())
            return null;

        String themeOverrideRelType = reader.getRelTypes().getThemeOverride();
        DocxXmlReader docxReader = (DocxXmlReader)reader.getXmlReader();
        OpcPackagePart themeOverridePart = reader.getPartByRelationshipType(docxReader.getPart(), themeOverrideRelType);
        if (themeOverridePart == null)
            return null;

        // WORDSNET-21265 Clone theme override part to avoid duplicated references to the same part (i.e. stream).
        DocxXmlReader themeXmlReader = new DocxXmlReader(themeOverridePart.deepClone(), reader.getLoadOptions().getWarningCallback(), WarningSource.DRAWING_ML, reader.getComplianceInfo());
        reader.pushPartReader(themeXmlReader);

        // Create a theme and store in the document.
        Theme theme = new Theme();

        // The theme needs this information from the settings to work properly.
        theme.setThemeFontLanguages(reader.getDocument().getDocPr().ThemeFontLanguages);

        // Store the unparsed theme part in the model because we do not do full parsing yet.
        theme.setThemePart(themeXmlReader.getPart(), themeXmlReader.getNamespaceURI());

        // Also store unparsed in the model all internal child parts that are related to the theme.
        for (OpcRelationship rel : themeXmlReader.getPart().getRels())
            readThemeRel(rel, reader, theme);

        DocxThemeReader themeReader = new DocxThemeReader(reader);
        // Theme override is actually themeElements.
        themeReader.readThemeElements(theme);

        // Restore reader.
        reader.restorePartReader();

        return theme;
    }

    private static void readThemeRel(OpcRelationship rel, DocxDocumentReaderBase reader, Theme theme)
    {
        DocxXmlReader themeXmlReader = ms.as(reader.getXmlReader(), DocxXmlReader.class);
        if (rel.isExternal() || (themeXmlReader == null))
            return;

        String partName = themeXmlReader.getPart().getRelatedPartName(rel);
        OpcPackagePart part = reader.getPartByName(partName);

        if (part != null)
        {
            theme.getRelatedParts().put(rel.getId(), part);
        }
        else
        {
            // WORDSNET-24536 Skip parts which has "NULL" target in relations.
            reader.warn(WarningType.DATA_LOSS,
                WarningSource.DOCX,
                msString.format(WarningStrings.THEME_PART_MISSING, partName));
        }

    }

    private void readCore(Theme theme) throws Exception
    {
        // Now parse some of the theme XML that we understand.
        theme.setName(getXmlReader().readAttribute("name", ""));

        while (getXmlReader().readChild("theme"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"themeElements"*/0:
                    readThemeElements(theme);
                    break;
                case /*"extLst"*/1:
                    // Extensions are not parsed now. A theme extension can only contain the element 2.4.1.1
                    // themeFamily [MS-ODRAWXML]. See also 2.2.8 Themes [MS-ODRAWXML].
                    ((IDmlExtensionListSource)theme).setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                case /*"objectDefaults"*/2:
                    theme.setObjectDefaults(readObjectDefaults());
                    break;
                case /*"custClrLst"*/3:
                case /*"extraClrSchemeLst"*/4:
                    // A warning is not generated since we write this XML to output document
                    // in ThemeWriter.WriteUnparsedElements.
                    getXmlReader().ignoreElement();
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    /// <summary>
    /// Reads the element 20.1.6.7 objectDefaults (Object Defaults, CT_ObjectStyleDefaults).
    /// </summary>
    private ThemeObjectDefaults readObjectDefaults() throws Exception
    {
        if (getXmlReader().isEmptyElement())
            return null;

        ThemeObjectDefaults result = new ThemeObjectDefaults();

        String tagName = getXmlReader().getLocalName();
        while (getXmlReader().readChild(tagName))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"spDef"*/5:
                    result.setShapeDefaults(readShapeDefaults());
                    break;
                case /*"lnDef"*/6:
                    result.setLineDefaults(readShapeDefaults());
                    break;
                case /*"txDef"*/7:
                    result.setTextDefaults(readShapeDefaults());
                    break;
                case /*"extLst"*/1:
                    if (!getXmlReader().isEmptyElement())
                        warnNotSupportedAndIgnoreElement(getXmlReader());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return result;
    }

    /// <summary>
    /// Reads shape definition element (CT_DefaultShapeDefinition).
    /// </summary>
    private DefaultShapeDefinition readShapeDefaults() throws Exception
    {
        DefaultShapeDefinition result = new DefaultShapeDefinition();

        String tagName = getXmlReader().getLocalName();
        while (getXmlReader().readChild(tagName))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"spPr"*/8:
                    result.setShapePr(new DefaultShapeProperties());
                    DmlNodePropertiesReader.readShapeProperties(mDocumentReader, result.getShapePr());
                    break;
                case /*"bodyPr"*/9:
                    result.setTextBodyPr(new DmlTextBodyProperties());
                    DmlTextShapeReader.readTextBodyProperties(mDocumentReader, result.getTextBodyPr(),
                        mDocumentReader.getComplianceInfo());
                    break;
                case /*"lstStyle"*/10:
                    result.setListStyles(new DmlTextListStyles());
                    DmlTextShapeReader.readListStyles(mDocumentReader, result.getListStyles());
                    break;
                case /*"style"*/11:
                    result.setStyle(DmlShapeStyleReader.read(getXmlReader(), mDocumentReader.getComplianceInfo()));
                    break;
                case /*"extLst"*/1:
                    if (!getXmlReader().isEmptyElement())
                        warnNotSupportedAndIgnoreElement(getXmlReader());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return result;
    }

    /// <summary>
    /// Reads 5.1.8.10 themeElements (Theme Elements).
    /// </summary>
    private void readThemeElements(Theme theme) throws Exception
    {
        String tagName = getXmlReader().getLocalName();
        while (getXmlReader().readChild(tagName))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"fontScheme"*/12:
                    readFontScheme(theme);
                    break;
                case /*"clrScheme"*/13:
                    readColorScheme(theme);
                    break;
                case /*"fmtScheme"*/14:
                    readFormatScheme(theme);
                    break;
                case /*"extLst"*/1:
                    if (!getXmlReader().isEmptyElement())
                        warnNotSupportedAndIgnoreElement(getXmlReader());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    private void readFormatScheme(Theme theme) throws Exception
    {
        theme.getFormatScheme().setName(getXmlReader().readAttribute("name", ""));
        while (getXmlReader().readChild("fmtScheme"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"bgFillStyleLst"*/15:
                    readBackgroundFillStyleList(theme.getFormatScheme());
                    break;
                case /*"fillStyleLst"*/16:
                    readFillStyleList(theme.getFormatScheme());
                    break;
                case /*"lnStyleLst"*/17:
                    readLineStyleList(theme.getFormatScheme());
                    break;
                case /*"effectStyleLst"*/18:
                    readEffectStyleList(theme.getFormatScheme());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    private void readBackgroundFillStyleList(FormatScheme formatScheme) throws Exception
    {
        while (getXmlReader().readChild("bgFillStyleLst"))
        {
            DmlFill fill = DmlFillReader.read(mDocumentReader);
            if (fill != null)
                formatScheme.addBackgroundFillStyle(fill);
        }
    }

    private void readFillStyleList(FormatScheme formatScheme) throws Exception
    {
        while (getXmlReader().readChild("fillStyleLst"))
        {
            DmlFill fill = DmlFillReader.read(mDocumentReader);
            if (fill != null)
                formatScheme.addFillStyle(fill);
        }
    }

    private void readLineStyleList(FormatScheme formatScheme) throws Exception
    {
        while (getXmlReader().readChild("lnStyleLst"))
        {
            DmlOutline outline = DmlOutlineReader.read(mDocumentReader);
            if (outline != null)
                formatScheme.addLineStyle(outline);
        }
    }

    private void readEffectStyleList(FormatScheme formatScheme) throws Exception
    {
        while (getXmlReader().readChild("effectStyleLst"))
        {
            EffectStyle effectStyle = new EffectStyle();
            readEffectStyle(effectStyle);
            formatScheme.addEffectStyle(effectStyle);
        }
    }

    private void readEffectStyle(EffectStyle effectStyle) throws Exception
    {
        while (getXmlReader().readChild("effectStyle"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"effectLst"*/19:
                    effectStyle.setEffects(DmlShapeEffectReader.readEffects(mDocumentReader, true, false));
                    break;
                case /*"scene3d"*/20:
                    effectStyle.setScene3DProperties(DmlScene3DReader.readScene3DProperties(mDocumentReader, true, getComplianceInfo()));
                    break;
                case /*"sp3d"*/21:
                    effectStyle.setShape3DProperties(DmlScene3DReader.readShape3DProperties(mDocumentReader, true, getComplianceInfo()));
                    break;
                case /*"effectDag"*/22:
                    effectStyle.setEffects(DmlShapeEffectReader.readEffects(mDocumentReader, true, true));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    private void readColorScheme(Theme theme)
    {
        theme.getColorScheme().setName(getXmlReader().readAttribute("name", ""));

        while (getXmlReader().readChild("clrScheme"))
        {
            DmlColor color = null;
            String colorName = getXmlReader().getLocalName();
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"accent1"*/23:
                case /*"accent2"*/24:
                case /*"accent3"*/25:
                case /*"accent4"*/26:
                case /*"accent5"*/27:
                case /*"accent6"*/28:
                case /*"dk1"*/29:
                case /*"dk2"*/30:
                case /*"folHlink"*/31:
                case /*"hlink"*/32:
                case /*"lt1"*/33:
                case /*"lt2"*/34:
                    color = readChildColor(getXmlReader(), getComplianceInfo());
                    break;
                case /*"extLst"*/1:
                    if (!getXmlReader().isEmptyElement())
                        warnNotSupportedAndIgnoreElement(getXmlReader());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
            if (color != null)
            {
                theme.getColorScheme().addColor(color, ThemeColorConverter.fromString(colorName));
            }
        }
    }

    private static DmlColor readChildColor(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        if (!reader.readChild(reader.getLocalName()))
            return null;

        return DmlColorReader.read(reader, complianceInfo);
    }

    /// <summary>
    /// Reads 5.1.4.1.18 fontScheme (Font Scheme).
    /// </summary>
    private void readFontScheme(Theme theme)
    {
        theme.setFontSchemeName(getXmlReader().readAttribute("name", ""));

        while (getXmlReader().readChild("fontScheme"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"majorFont"*/35:
                    theme.setMajorFonts(readFonts());
                    break;
                case /*"minorFont"*/36:
                    theme.setMinorFonts(readFonts());
                    break;
                case /*"extLst"*/1:
                    if (!getXmlReader().isEmptyElement())
                        warnNotSupportedAndIgnoreElement(getXmlReader());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 5.1.4.1.24 majorFont (Major Font) or 5.1.4.1.25 minorFont (Minor fonts).
    /// </summary>
    private ThemeFonts readFonts()
    {
        ThemeFonts fonts = new ThemeFonts();

        String elemName = getXmlReader().getLocalName();
        while (getXmlReader().readChild(elemName))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"cs"*/37:
                    fonts.ComplexScriptFontInfo = readFont();
                    break;
                case /*"ea"*/38:
                    fonts.EastAsianFontInfo = readFont();
                    break;
                case /*"latin"*/39:
                    fonts.LatinFontInfo = readFont();
                    break;
                case /*"font"*/40:
                    readSupplementalFont(fonts);
                    break;
                case /*"extLst"*/1:
                    if (!getXmlReader().isEmptyElement())
                        warnNotSupportedAndIgnoreElement(getXmlReader());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return fonts;
    }

    /// <summary>
    /// Reads a
    /// 5.1.5.3.1 cs (Complex Script Font) or
    /// 5.1.5.3.3 ea (East Asian Font) or
    /// 5.1.5.3.7 latin (Latin Font)
    /// </summary>
    private FontInfo readFont()
    {
        FontInfo font = new FontInfo();

        while (getXmlReader().moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"charset"*/41:
                    font.setCharset(getXmlReader().getValueAsInt());
                    break;
                case /*"panose"*/42:
                    font.setPanose(NrxXmlUtil.hexToBytes(getXmlReader().getValue(), FontInfo.PANOSE_LENGTH));
                    break;
                case /*"pitchFamily"*/43:
                    font.setPitch((/*FontPitch*/int)getXmlReader().getValueAsInt());
                    break;
                case /*"typeface"*/44:
                    font.setName(getXmlReader().getValue());
                    break;
                default:
                    warnUnexpected(getXmlReader());
                    break;
            }
        }

        return font;
    }

    private void readSupplementalFont(ThemeFonts themeFonts)
    {
        String script = null;
        String typeface = null;

        while (getXmlReader().moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"script"*/45:
                    script = getXmlReader().getValue();
                    break;
                case /*"typeface"*/44:
                    typeface = getXmlReader().getValue();
                    break;
                default:
                    warnUnexpected(getXmlReader());
                    break;
            }
        }

        if (StringUtil.hasChars(script))
        {
            ThemeSupplementalFont font = new ThemeSupplementalFont(script, typeface);
            themeFonts.getSupplementalFonts().put(font.getScript(), font);
        }
    }

    private NrxXmlReader getXmlReader() { return mDocumentReader.getXmlReader(); }

    private OoxmlComplianceInfo getComplianceInfo() { return mDocumentReader.getComplianceInfo(); }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"themeElements",
		"extLst",
		"objectDefaults",
		"custClrLst",
		"extraClrSchemeLst",
		"spDef",
		"lnDef",
		"txDef",
		"spPr",
		"bodyPr",
		"lstStyle",
		"style",
		"fontScheme",
		"clrScheme",
		"fmtScheme",
		"bgFillStyleLst",
		"fillStyleLst",
		"lnStyleLst",
		"effectStyleLst",
		"effectLst",
		"scene3d",
		"sp3d",
		"effectDag",
		"accent1",
		"accent2",
		"accent3",
		"accent4",
		"accent5",
		"accent6",
		"dk1",
		"dk2",
		"folHlink",
		"hlink",
		"lt1",
		"lt2",
		"majorFont",
		"minorFont",
		"cs",
		"ea",
		"latin",
		"font",
		"charset",
		"panose",
		"pitchFamily",
		"typeface",
		"script"
	);

}

