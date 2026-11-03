// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/06/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Language;
import com.aspose.StringUtil;
import com.aspose.DictionaryExtension;
import java.util.HashMap;
import com.aspose.OpcPackagePart;
import java.util.Map;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msString;
import com.aspose.ArgumentUtil;
import com.aspose.AnyXmlReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.AnyXmlBuilder;
import com.aspose.StringToObjDictionary;
import com.aspose.LanguageOnly;
import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Represents document Theme, and provides access to main theme parts including <see cref="MajorFonts"/>, <see cref="MinorFonts"/> and <see cref="Colors"/>
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-styles-and-themes/">Working with Styles and Themes</a> documentation article.</para>
/// </summary>
/// <dev>
/// At the moment this is just a crude hack to preserve themes during DOCX to DOCX roundtrip,
/// but later should be worked into a more appropriate object.
///
/// WORDSNET-10513 Limited support for theme handling was implemented.
/// If customer changes one of theme part then theme is rewritten with new values.
/// If theme is unchanged during roundtrip then it is written raw as before.
/// </dev>
public class Theme implements IThemeProvider, IDmlExtensionListSource, Cloneable
{
    /// <summary>
    /// Allows to specify the set of major fonts for different languages.
    /// </summary>
    public ThemeFonts getMajorFonts() { return mMajorFonts; }

    /// <summary>
    /// Allows to specify the set of minor fonts for different languages.
    /// </summary>
    public ThemeFonts getMinorFonts() { return mMinorFonts; }

    /// <summary>
    /// Allows to specify the set of theme colors for the document.
    /// </summary>
    public ThemeColors getColors() { return mColorScheme; }

    /// <summary>
    /// Returns the default built-in theme from embedded resource.
    /// </summary>
    static Theme getBuiltInTheme()
    {
        // Implementing Singleton in C# http://msdn.microsoft.com/en-us/library/ms998558.aspx
        if (gDefaultTheme == null)
        {
            synchronized (G_DEFAULT_THEME_SYNC_ROOT)
            {
                if (gDefaultTheme == null)
                {
                    gDefaultTheme = readBuiltInTheme("Aspose.Words.Resources.AllStyles2007.docx");
                }
            }
        }

        return gDefaultTheme;
    }

    //>>>>>>>> #region  IThemeProvider

    /// <summary>
    /// Safely gets a font name according to the algorithm described in 17.15.1.88 themeFontLang (Theme Font Languages).
    /// The returned value can be null or empty string.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public String /*IThemeProvider.*/getFontName(/*ThemeFontCore*/int themeFont)
    {
        // Select major or minor fonts.
        ThemeFonts themeFonts = ((themeFont & ThemeFontCore.GROUP_MASK) == ThemeFontCore.MAJOR) ? mMajorFonts : mMinorFonts;
        if (themeFonts == null)
            return null;

        // Select theme font language and the default font to use in case the font specified by the language will not be found.
        /*Language*/int themeFontLang;
        FontInfo defaultFont;
        switch (themeFont & ThemeFontCore.REGION_MASK)
        {
            case ThemeFontCore.ASCII:
            case ThemeFontCore.H_ANSI:
                themeFontLang = mThemeFontLanguages.Latin;
                defaultFont = themeFonts.LatinFontInfo;
                break;
            case ThemeFontCore.BIDI:
                themeFontLang = mThemeFontLanguages.Bidi;
                defaultFont = themeFonts.ComplexScriptFontInfo;
                break;
            case ThemeFontCore.EAST_ASIA:
                themeFontLang = mThemeFontLanguages.EastAsia;
                defaultFont = themeFonts.EastAsianFontInfo;
                break;
            default:
                themeFontLang = Language.NO_PROOF;
                defaultFont = null;
                break;
        }

        // Try to select the font specified by the theme font language.
        String script = languageToScript(themeFontLang);
        if (StringUtil.hasChars(script))
        {
            ThemeSupplementalFont suppFont = DictionaryExtension.getValueOrNull(themeFonts.getSupplementalFonts(), script);
            if ((suppFont != null) && StringUtil.hasChars(suppFont.getTypeface()))
                return suppFont.getTypeface();
        }

        // Ok, a font according to the theme font language is not specified, return the default font for the region.
        return (defaultFont != null) ? defaultFont.getName() : null;
    }

    @ReservedForInternalUse @Deprecated
    public DmlColor /*IThemeProvider.*/getThemeColor(/*ThemeColor*/int color)
    {
        // We return a clone to protect original color from modification
        DmlColor dmlColor = getColorScheme().getColor(color);
        return (dmlColor == null) ? null : dmlColor.deepClone();
    }

    @ReservedForInternalUse @Deprecated
    public DmlFill /*IThemeProvider.*/getBackgroundFillStyle(int index)
    {
        // We return a clone to protect original fill from modification
        return getFormatScheme().getBackgroundFillStyle(index).deepClone();
    }

    @ReservedForInternalUse @Deprecated
    public DmlFill /*IThemeProvider.*/getFillStyle(int index)
    {
        // We return a clone to protect original fill from modification
        return getFormatScheme().getFillStyle(index).deepClone();
    }

    @ReservedForInternalUse @Deprecated
    public DmlOutline /*IThemeProvider.*/getLineStyle(int index)
    {
        // We return a clone to protect original line from modification
        return getFormatScheme().getLineStyle(index).deepClone();
    }

    @ReservedForInternalUse @Deprecated
    public EffectStyle /*IThemeProvider.*/getEffectStyle(int index)
    {
        // We return a clone to protect original line from modification
        return getFormatScheme().getEffectStyle(index).deepClone();
    }

    @ReservedForInternalUse @Deprecated
    public void /*IThemeProvider.*/onChange()
    {
        updateDocument();
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Internal setter. Used during document loading.
    /// </summary>
    void setMajorFonts(ThemeFonts majorFonts)
    {
        mMajorFonts = majorFonts;
    }

    /// <summary>
    /// Internal setter. Used during document loading.
    /// </summary>
    void setMinorFonts(ThemeFonts minorFonts)
    {
        mMinorFonts = minorFonts;
    }

    /// <summary>
    /// At the moment returns just a shallow copy because we do not fully parse a theme.
    /// </summary>
    Theme deepClone()
    {
        Theme lhs = (Theme)memberwiseClone();
        lhs.setThemePart(getThemePart().deepClone(), mThemeNamespace);

        if (mColorScheme != null)
        {
            lhs.mColorScheme = mColorScheme.deepClone();
            lhs.mColorScheme.setTheme(lhs);
        }

        if (mFormatScheme != null)
            lhs.mFormatScheme = mFormatScheme.deepClone();

        if (mMajorFonts != null)
            lhs.mMajorFonts = mMajorFonts.deepClone();

        if (mMinorFonts != null)
            lhs.mMinorFonts = mMinorFonts.deepClone();

        if (mRelatedParts != null)
        {
            lhs.mRelatedParts = new HashMap<String, OpcPackagePart>();
            for (Map.Entry<String, OpcPackagePart> entry : mRelatedParts.entrySet())
                msDictionary.add(lhs.mRelatedParts, entry.getKey(), entry.getValue().deepClone());
        }

        if (mThemeFontLanguages != null)
            lhs.mThemeFontLanguages = mThemeFontLanguages.deepClone();

        lhs.mExtensions = DmlExtensionListSource.cloneExtensions(mExtensions);

        return lhs;
    }

    /// <summary>
    /// Checks that both themes defines that same font set.
    /// </summary>
    /// <remarks>
    /// AM. Although it looks like bad realization of Theme.Equals we need to compare themes this way.
    /// The method is used in importing to correctly update fonts in imported content in case of KeepSourceFormat mode so
    /// we take care about fonts only.
    /// </remarks>
    static boolean themeFontsEquals(Theme a, Theme b)
    {
        if (msObject.referenceEquals(a, b))
            return true;

        if (msObject.referenceEquals(null, a))
            return false;

        if (msObject.referenceEquals(null, b))
            return false;

        for (/*ThemeFontCore*/int themeFont : RunPr.ThemeFontAttrs)
            if (!msString.equals(((IThemeProvider)a).getFontName(themeFont), ((IThemeProvider)b).getFontName(themeFont)))
                return false;

        return true;
    }

    static boolean themeColorsEquals(Theme a, Theme b)
    {
        if (msObject.referenceEquals(a, b))
            return true;

        if (ArgumentUtil.oneIsNull(a, b))
            return false;

        if (!a.getColorScheme().getAccent3().equals(b.getColorScheme().getAccent3()))
            return false;

        return true;
    }

    /// <summary>
    /// Resolves theme attributes in given RunPr.
    /// </summary>
    /// <remarks>
    /// AM. Made static because given theme can be null.
    /// </remarks>
    static void apply(Theme theme, RunPr runPr)
    {
        // Theme can be null and it's OK. In this case all theme-related fonts should be resolved to Times.

        RunPr appliedPr = new RunPr();

        boolean applyThemeColor = false;
        for (int i = 0; i < runPr.getCount(); i++)
        {
            int key = runPr.getKey(i);
            Object value = runPr.getByIndex(i);

            switch (key)
            {
                case FontAttr.NAME_ASCII:
                case FontAttr.NAME_FAR_EAST:
                case FontAttr.NAME_OTHER:
                case FontAttr.NAME_BI:
                    appliedPr.setAttr(key, ComplexFontName.fromName(ComplexFontName.resolve(value, theme)));
                    break;
                case FontAttr.THEME_COLOR:
                    applyThemeColor = true;
                    // AM Apply theme color. The problem here is that we need to delete ThemeColor.
                    // It has to be gone when ThemeColor and Color is combined into one attribute.
                    break;
                default:
                    break;
            }
        }

        appliedPr.expandTo(runPr);
        if (applyThemeColor)
        {
            // AM. In common both Color and ThemeColor are present, so we need to just delete ThemeColor.
            // But it will be incorrect if only ThemeColor specified. We need to correctly resolve this color.
            runPr.remove(FontAttr.THEME_COLOR);
        }
    }

    /// <summary>
    /// Creates Theme with theme override XML.
    /// </summary>
    Theme createThemeOverride()
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	Theme themeOverride = this.deepClone();

        	// Build theme override part which is theme itself but has another root element.
        	getThemePart().getStream().setPosition(0);
        	AnyXmlReader reader = new AnyXmlReader(getThemePart().getStream());

        	MemoryStream overrideXmlStream = new MemoryStream();
        	AnyXmlBuilder builder = new AnyXmlBuilder(overrideXmlStream, false);

        	builder.startDocument("a:themeOverride");
        	builder.writeAttributeString("xmlns:a", themeOverride.getThemeNamespace());

        	reader.readChild("theme");
        	reader.readChild("themeElements");

        	while (!reader.isEndElement("themeElements"))
            	builder.writeRaw(reader.readOuterXml());

        	builder.endDocument();

        	overrideXmlStream.setPosition(0);

        	themeOverride.getThemePart().setStream(overrideXmlStream);

        	return themeOverride;
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Sets the theme's main XML part. The specified namespace indicates OOXML format,
    /// in which the theme is written.
    /// </summary>
    void setThemePart(OpcPackagePart value, String themeNamespace)
    {
        mThemePart = value;
        mThemeNamespace = themeNamespace;
    }

    /// <summary>
    /// The theme's main XML part. At the moment we preserve it unparsed.
    /// </summary>
    OpcPackagePart getThemePart() { return mThemePart; }

    /// <summary>
    /// Returns namespace indicating OOXML format, in which the theme is stored in <see cref="ThemePart"/>.
    /// </summary>
    String getThemeNamespace() { return mThemeNamespace; }

    /// <summary>
    /// Collection of parts that the theme refers to.
    ///
    /// Key is a string rId (relationship id) that was specified in the original DOCX.
    /// We preserve it because we do not parse the theme XML.
    ///
    /// The value is an <see cref="OpcPackagePart"/> object taken from the original DOCX.
    /// </summary>
    HashMap<String, OpcPackagePart> getRelatedParts() { return mRelatedParts; }
    void setRelatedParts(HashMap<String, OpcPackagePart> value) { mRelatedParts = value; }

    /// <summary>
    /// Theme name. Can be null.
    /// </summary>
    String getName() { return mName; }
    void setName(String value) { mName = value; }

    /// <summary>
    /// Font scheme name. Can be null.
    /// </summary>
    String getFontSchemeName() { return mFontSchemeName; }
    void setFontSchemeName(String value) { mFontSchemeName = value; }

    /// <summary>
    /// Corresponds to 17.15.1.88 themeFontLang (Theme Font Languages).
    /// In DOCX this is stored in settings.xml but we need this data here to properly resolve font names.
    /// This data is actually stored in <see cref="DocPr.ThemeFontLanguages"/>. This property is just a reference to it.
    /// </summary>
    ThemeFontLanguages getThemeFontLanguages() { return mThemeFontLanguages; }
    void setThemeFontLanguages(ThemeFontLanguages value) { mThemeFontLanguages = value; }

    /// <summary>
    /// Corresponds to 20.1.6.2 clrScheme (Color Scheme) and defines
    /// a set of colors which are referred to as a color scheme.
    /// The color scheme is responsible for defining a list of twelve colors.
    /// The twelve colors consist of six accent colors, two dark colors, two light
    /// colors and a color for each of a hyperlink and followed hyperlink.
    /// </summary>
    ThemeColors getColorScheme()
    {
        if (mColorScheme == null)
            mColorScheme = new ThemeColors(this);
        return mColorScheme;
    }

    /// <summary>
    /// Corresponds to 20.1.4.1.14 fmtScheme (Format Scheme)
    /// and contains the background fill styles,
    /// effect styles, fill styles, and line styles which
    /// define the style matrix for a theme.
    /// </summary>
    FormatScheme getFormatScheme()
    {
        if (mFormatScheme == null)
            mFormatScheme = new FormatScheme();
        return mFormatScheme;
    }
    void setFormatScheme(FormatScheme value) { mFormatScheme = value; }

    /// <summary>
    /// Gets or sets an object that defines default shape, line, and textbox formatting properties.
    /// Corresponds to the element 20.1.6.7 objectDefaults (Object Defaults).
    /// The value can be <c>null</c> if no defaults is set.
    /// </summary>
    ThemeObjectDefaults getObjectDefaults() { return mObjectDefaults; }
    void setObjectDefaults(ThemeObjectDefaults value) { mObjectDefaults = value; }

    /// <summary>
    /// Corresponds to 20.1.2.2.15 extLst (Extension List, CT_OfficeArtExtensionList),
    /// see also 2.2.8 Themes [MS-ODRAWXML].
    /// An extension can only contain the element 2.4.1.1 themeFamily [MS-ODRAWXML].
    /// </summary>
    /// <remarks>
    /// The returned array list contains items of the <see cref="DmlExtension"/> type.
    /// Each item stores extension's XML as is.
    /// The <c>null</c> value may be returned.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public StringToObjDictionary<DmlExtension> /*IDmlExtensionListSource.*/getExtensions() { return mExtensions; }
    @ReservedForInternalUse @Deprecated
    public void /*IDmlExtensionListSource.*/setExtensions(StringToObjDictionary<DmlExtension> value) { mExtensions = value; }

    /// <summary>
    /// Returns flag indicating that the theme is modified by customer.
    /// </summary>
    boolean isModified()
    {
        return (mMajorFonts != null && mMajorFonts.isModified()) ||
            (mMinorFonts != null && mMinorFonts.isModified()) ||
            (mColorScheme != null && mColorScheme.isModified());
    }

    /// <summary>
    /// Attaches document to the theme.
    /// </summary>
    void attach(Document document)
    {
        mDocument = document;
        if (mColorScheme != null && mColorScheme.isModified())
            updateDocument();
    }

    /// <summary>
    /// Converts a <see cref="Language"/> value into a script name.
    /// Script names are according to http://unicode.org/iso15924/iso15924-codes.html
    /// </summary>
    private static String languageToScript(/*Language*/int lang)
    {
        switch ((int)lang & 0x00ff)
        {
            case LanguageOnly.JAPANESE:
                return "Jpan";
            case LanguageOnly.KOREAN:
                return "Hang";
            case LanguageOnly.CHINESE:
                // WORDSNET-15818 Resolve either to 'Traditional' or to 'Simplified' Chinese script
                // depending on the country code.
                return usesTraditionalChineseScript(lang) ? "Hant" : "Hans";
            case LanguageOnly.ARABIC:
                return "Arab";
            case LanguageOnly.HEBREW:
                return "Hebr";
            case LanguageOnly.THAI:
                return "Thai";
            case LanguageOnly.ETHIOPIAN:
                return "Ethi";
            case LanguageOnly.BENGALI:
                return "Beng";
            case LanguageOnly.GUJARATI:
                return "Gujr";
            case LanguageOnly.KHMER:
                return "Khmr";
            case LanguageOnly.KANNADA:
                return "Knda";
            case LanguageOnly.PUNJABI:
                return "Guru"; // http://en.wikipedia.org/wiki/Gurmukh%C4%AB_script
            case LanguageOnly.INUKTITUT:
                return "Cans"; // http://en.wikipedia.org/wiki/Canadian_Aboriginal_syllabics
            case LanguageOnly.CHEROKEE:
                return "Cher";
            case LanguageOnly.YI:
                return "Yiii";
            case LanguageOnly.TIBETAN:
                return "Tibt";
            case LanguageOnly.DIVEHI:
                return "Thaa"; // http://en.wikipedia.org/wiki/T%C4%81na
            case LanguageOnly.DEVANGARI:
                return "Deva";
            case LanguageOnly.TELUGU:
                return "Telu";
            case LanguageOnly.TAMIL:
                return "Taml";
            case LanguageOnly.SYRIAC:
                return "Syrc";
            case LanguageOnly.ORIYA:
                return "Orya";
            case LanguageOnly.MALAYALAM:
                return "Mlym";
            case LanguageOnly.LAO:
                return "Laoo";
            case LanguageOnly.SINHALESE:
                return "Sing";
            case LanguageOnly.MONGOLIAN:
                return "Mong";
            case LanguageOnly.VIETNAMESE:
                return "Viet";
            // WORDSNET-17652 Determine script for "Hindi" language to get correct theme font name.
            case LanguageOnly.HINDI:
                return "Deva";
            default:
                // No idea how to handle the "Uigh" value. It is for the Uyghur script.
                // But I don't have a code for it http://en.wikipedia.org/wiki/Uyghur_people
                return null;
        }
    }

    private static boolean usesTraditionalChineseScript(/*Language*/int lang)
    {
        return (lang == Language.CHINESE_HONG_KONG) ||
            (lang == Language.CHINESE_TAIWAN) ||
            (lang == Language.CHINESE_MACAO);
    }

    /// <summary>
    /// Updates document content colors from the theme.
    /// </summary>
    private void updateDocument()
    {
        if (mDocument != null)
            ThemeColorUpdater.update(mDocument);
    }

    private static Theme readBuiltInTheme(String resourceName)
    {
        try
        {
            // INSP DD: Do themes differ from word to word version? If yes we should probably consider Document.CompatibilityOptions.OptimizeFor
            Stream stream = ResourceUtil.fetchResourceStream(resourceName);
            try /*JAVA: was using*/
            {
                Document doc = new Document(stream, null, false);
                return doc.getThemeInternal();
            }
            finally { if (stream != null) stream.close(); }
        }
        catch (Exception e)
        {
            // JAVA: Catch and rethrow RuntimeException for java.
            // This needed to not propagate throws exception clause to attribute fetching methods.
            throw new IllegalStateException("Cannot load built in theme from an embedded resource.", e);
        }
    }

    private ThemeColors mColorScheme;

    private String mFontSchemeName;
    private FormatScheme mFormatScheme;
    private ThemeFonts mMajorFonts;
    private ThemeFonts mMinorFonts;
    private String mName;
    private HashMap<String, OpcPackagePart> mRelatedParts = new HashMap<String, OpcPackagePart>();
    private ThemeObjectDefaults mObjectDefaults;
    private StringToObjDictionary<DmlExtension> mExtensions;

    /// <summary>
    /// This must not be null when DOCX is read.
    /// </summary>
    private ThemeFontLanguages mThemeFontLanguages;

    private OpcPackagePart mThemePart;

    /// Attached document to update content if theme is changed.
    private Document mDocument;

    private String mThemeNamespace;

    private static Theme gDefaultTheme;
    private static /*final*/ Object G_DEFAULT_THEME_SYNC_ROOT = new Object();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

