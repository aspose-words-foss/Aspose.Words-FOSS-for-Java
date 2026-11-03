// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/04/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Language;


/// <summary>
/// Defines the document properties.
/// </summary>
class DocPr implements Cloneable
{
    /// <summary>
    /// Creates a deep copy of the object. Don't forget to add any new sub objects to here.
    /// </summary>
    DocPr deepClone()
    {
        DocPr lhs = (DocPr)memberwiseClone();

        lhs.ActiveWritingStyle = ActiveWritingStyle.deepClone();
        lhs.MailMergeSettings = MailMergeSettings.deepClone();
        lhs.DocumentProtection = DocumentProtection.deepClone();
        lhs.WriteProtection = WriteProtection.deepClone();
        lhs.FootnotePr = AttrCollectionExtension.deepClone(FootnotePr);
        lhs.CompatibilityOptions = CompatibilityOptions.deepClone();
        lhs.ViewOptions = ViewOptions.deepClone();
        lhs.HyphenationOptions = HyphenationOptions.deepClone();
        lhs.StylePaneFormatFilterSettings = new StylePaneFormatFilterSettings(this.StylePaneFormatFilterSettings.getData());
        lhs.ThemeFontLanguages = ThemeFontLanguages.deepClone();
        lhs.MathProperties = MathProperties.deepClone();

        lhs.XmlNamespaces = XmlNamespaces.deepClone();
        lhs.XmlSchemaReferences = XmlSchemaReferences.deepClone();

        return lhs;
    }

    /// <summary>
    /// Sets all font embedding options equal to embedding options in specified <paramref name="fontInfos"/>.
    /// </summary>
    void updateEmbedFontOptions(FontInfoCollection fontInfos)
    {
        EmbedTrueTypeFonts = fontInfos.getEmbedTrueTypeFonts();
        EmbedSystemFonts = fontInfos.getEmbedSystemFonts();
        SaveSubsetFonts = fontInfos.getSaveSubsetFonts();
    }

    /// <summary>
    /// Specifies presence of footnotes in document. Used only in Word95.
    /// </summary>
    /*GrpfIhdtDop*/int GrpfIhdt;

    /// <summary>
    /// Helps avoid unintentionally distributing hidden information, such as the document's author 
    /// or the names associated with comments or tracked changes (Security option).
    /// //security
    /// </summary>
    boolean RemovePersonalInformation;

    /// <summary>
    /// Allows PostScript code in PRINT fields in a document to print on top of the document text 
    /// instead of underneath it (Print option). This elements setting has no effect if a document 
    /// does not contain PRINT fields.
    /// //print
    /// </summary>
    boolean PrintPostScriptOverText;

    /// <summary>
    /// Word for the Macintosh setting that has no effect in other versions of Word (Print option).
    /// //print
    /// </summary>
    boolean PrintFractionalCharacterWidth;

    /// <summary>
    /// Prints the data entered into an online form without printing the online form (Print option).
    /// //print
    /// </summary>
    boolean PrintFormsData;

    /// <summary>
    /// Stores the TrueType fonts used to create this document along with the document (Save option). 
    /// Others who open the document will be able to view and print it with the fonts used to create it, 
    /// even if those fonts aren't installed on their computer. (NOTE: TrueType fonts are not embedded in 
    /// XML files.)
    /// //save
    /// </summary>
    boolean EmbedTrueTypeFonts;

    /// <summary>
    /// For the TrueType fonts in your document, embed fonts that are likely to already be
    /// installed on a computer (Save option). This option takes effect only when Embed TrueType 
    /// Fonts option is on.
    /// //save
    /// </summary>
    boolean EmbedSystemFonts;

    /// <summary>
    /// For the TrueType fonts in your document, embeds only the font styles you actually used in the document, 
    /// which may decrease the file size of your document (Save option). If you used 32 or fewer 
    /// characters of a font, Word embeds only those characters. This option takes effect only when Embed 
    /// TrueType Fonts option is on.
    /// //save
    /// </summary>
    boolean SaveSubsetFonts;

    /// <summary>
    /// Saves the data entered in an online form as a single, tab-delimited record so you can use 
    /// it in a database. Word saves the file in Text Only file format.
    /// //save
    /// </summary>
    boolean SaveFormsData;

    /// <summary>
    /// Aligns paragraph borders and tables with the page border throughout the document (Page Border option).
    /// Setting this element to on eliminates any gaps between adjoining borders. However, Word aligns, or snaps, 
    /// text to the edge of a table only if the text is one character width (10.5 points) or less from the page border.
    /// //page setup
    /// </summary>
    boolean AlignBordersAndEdges;

    /// <summary>
    /// Causes the page border to exclude the header (Page Border option).
    /// //page setup
    /// </summary>
    boolean BordersDoNotSurroundHeader;

    /// <summary>
    /// Causes the page border to exclude the footer (Page Border option).
    /// //page setup
    /// </summary>
    boolean BordersDoNotSurroundFooter;

    /// <summary>
    /// Matches "gutterAtTop" in MS Word document settings (17.15.1.50 of ISO-29500).
    /// The actual gutter position may be different depending on multiple page setup, page orientation and other section or document properties.
    /// <seealso cref="IsGutterSide"/> comments.
    /// </summary>
    boolean GutterAtTop;

    /// <summary>
    /// It is supposed to report if there is a gutter on a page side, 
    /// but the actual gutter position depends on a number of properties and the result may not be correct.
    /// </summary>
    /// <remarks>
    /// dmatv: This property is not used by the layout anymore.
    /// The logic that places the gutter correctly is currently in <see cref="PageMarginCalculator"/>.
    /// The behavior depends on section orientation, first section orientation and some other conditions.
    /// Also there is a nasty twist when MS Word behaves inconsistently determining if the gutter is on the side for 2-page and book fold setups.
    /// See TestJira16284*. I've submitted WORDSNET-16349 to review the code depending on this property in the model (and probably get rid of it).
    /// </remarks>
    boolean isGutterSide()
    {
        return !GutterAtTop
        || (MultiplePages == MultiplePagesType.MIRROR_MARGINS)
        || (MultiplePages == MultiplePagesType.BOOK_FOLD_PRINTING)
        || (MultiplePages == MultiplePagesType.BOOK_FOLD_PRINTING_REVERSE)
        || (MultiplePages == MultiplePagesType.TWO_PAGES_PER_SHEET);
    }

    /// <summary>
    /// Specifies the writing style you want Word to use to when checking grammar in this document 
    /// (Spelling and Grammar option)
    /// //grammar
    /// </summary>
    WritingStylePr ActiveWritingStyle = new WritingStylePr();

    /// <summary>
    /// Gets or sets the state of the spelling checker in this document.
    /// //grammar
    /// </summary>
    /*ProofState*/int ProofStateSpelling = ProofState.NONE;

    /// <summary>
    /// Gets or sets the state of the grammar checker in this document.
    /// //grammar
    /// </summary>
    /*ProofState*/int ProofStateGrammar = ProofState.NONE;

    /// <summary>
    /// Specifies the template that's attached to this document (Templates and Add-Ins option).
    /// //templates
    /// </summary>
    String AttachedTemplate = "";

    /// <summary>
    /// Updates the styles in this document to match the styles in the attached template each time you open 
    /// the document (Templates and Add-Ins option). This ensures that your document contains up-to-date 
    /// style formatting.
    /// //templates
    /// </summary>
    boolean LinkStyles;

    /// <summary>
    /// This element specifies whether applications shall prevent the replacement of the complete set 
    /// of styles stored in the Styles part when editing this document.
    /// </summary>
    boolean StyleLockQuickFormatSet;

    /// <summary>
    /// This element specifies whether applications shall prevent the modification of the document's 
    /// theme information stored in the Theme part when editing this document.
    /// </summary>
    boolean StyleLockTheme;

    /// <summary>
    /// Stores filter pane format settings as boolean properties.
    /// </summary>
    StylePaneFormatFilterSettings StylePaneFormatFilterSettings = new StylePaneFormatFilterSettings();

    /// <summary>
    /// This element specifies a suggested sorting which should be applied to the list of document 
    /// styles in this application if the styles are displayed in a user interface.
    /// </summary>
    /*StylePaneSortMethod*/int _StylePaneSortMethod = StylePaneSortMethod.DEFAULT;

    /// <summary>
    /// Stores multiple pages type, which should be applied to the document upon printing or rendering. 
    /// </summary>
    /// <remarks>
    /// Note, changing this property affects all sections in the document.
    /// </remarks>
    /*MultiplePagesType*/int MultiplePages = MultiplePagesType.DEFAULT;

    /// <summary>
    /// Gets a boolean value indicating if <see>MultiplePages</see> property specifies book fold layout.
    /// </summary>
    boolean isBookFold()
    {
        return (MultiplePages == MultiplePagesType.BOOK_FOLD_PRINTING)
            || (MultiplePages == MultiplePagesType.BOOK_FOLD_PRINTING_REVERSE);
    }

    /// <summary>
    /// Do Not Track Formatting Revisions When Tracking Revisions.
    /// </summary>
    boolean DoNotTrackFormatting;

    /// <summary>
    /// This element specifies that applications shall not track revisions made to this WordprocessingML document
    /// as moves when the trackRevisions element (§2.15.1.90) is turned on, even when that syntax is appropriate.
    /// Instead, applications should use a standard insertion and deletion annotation syntax.
    /// Existing moves shall not be modified.
    /// </summary>
    boolean DoNotTrackMoves = false;

    /// <summary>
    /// Specifies the document type used by the AutoFormat feature.
    /// //general
    /// </summary>
    /*AutoFormatDocumentType*/int DocumentType = AutoFormatDocumentType.NORMAL;

    /// <summary>
    /// Contains the elements that hold mail merge information for this document. 
    /// </summary>
    MailMergeSettings MailMergeSettings = new MailMergeSettings();

    /// <summary>
    /// Marks changes in the current document and keeps track of each change by reviewer name.
    /// //revisions
    /// </summary>
    boolean TrackRevisions;

    /// <summary>
    /// Track changes: show markup
    /// // revisions
    /// </summary>
    boolean ShowMarkup = true;

    /// <summary>
    /// Track changes: show annotations
    /// // revisions
    /// </summary>
    boolean ShowAnnotations = true;

    /// <summary>
    /// Track changes: show insertions and deletions
    /// // revisions
    /// </summary>
    boolean ShowInsertionsDeletions = true;

    /// <summary>
    /// Track changes: show formatting
    /// // revisions
    /// </summary>
    boolean ShowFormatting = true;

    /// <summary>
    /// Show ink annotations.
    /// // revisions.
    /// </summary>
    boolean ShowInkAnnotations = true;

    /// <summary>
    /// A shortcut for all show revision properties used for older DOC files.
    /// </summary>
    boolean getShowRevisions()
    {
        return
            ShowMarkup ||
            ShowAnnotations ||
            ShowInsertionsDeletions ||
            ShowFormatting ||
            ShowInkAnnotations;
    }
    void setShowRevisions(boolean value)
    {
        ShowMarkup = value;
        ShowAnnotations = value;
        ShowInsertionsDeletions = value;
        ShowFormatting = value;
        ShowInkAnnotations = value;
    }

    /// <summary>
    /// Helps prevent unintentional changes to all or part of an online form or document, as specified 
    /// (Protect Document option).
    /// </summary>
    DocumentProtection DocumentProtection = new DocumentProtection();

    /// <summary>
    /// Write protection settings.
    /// </summary>
    WriteProtection WriteProtection = new WriteProtection();

    /// <summary>
    /// Specifies the default spacing between tab stops in twips (Format Tabs option).
    /// </summary>
    int DefaultTabStop = DEFAULT_TAB_STOP_DEFAULT;

    /// <summary>
    /// Provides document hyphenation options.
    /// </summary>
    HyphenationOptions HyphenationOptions = new HyphenationOptions();

    /// <summary>
    /// Displays the Microsoft Office Outlook e-mail header in a document.
    /// </summary>
    boolean ShowEnvelope;

    /// <summary>
    /// Freeze Document Layout.
    /// </summary>
    boolean ReadingModeInkLockDown;

    /// <summary>
    /// Specifies that the date and time information shall be removed from all annotations, which are present
    /// in a document when it is saved in MS Word.
    /// </summary>
    boolean RemoveDateAndTime;

    /// <summary>
    /// Default paragraph style for click and type.
    /// </summary>
    int ClickTypeParaStyleIstd = CLICK_TYPE_PARA_STYLE_ISTD_DEFAULT;

    /// <summary>
    /// Default table style. 
    /// </summary>
    int DefaultTableStyleIstd = DEFAULT_TABLE_STYLE_ISTD_DEFAULT;

    /// <summary>
    /// Creates one header or footer for even-numbered pages and a different header or footer 
    /// for odd-numbered pages (Page Setup Layout option).
    /// </summary>
    boolean EvenAndOddHeaders;

    /// <summary>
    /// For multiple-page documents with book fold and reverse book fold printing, 
    /// sets the number of sheets per booklet (Page Setup Margin option).
    /// </summary>
    int BookFoldPrintingSheets;

    /// <summary>
    /// Specifies the amount of horizontal space between vertical gridlines (Drawing Grid option).
    /// </summary>
    int DrawingGridHorizontalSpacing = DRAWING_GRID_HORIZONTAL_SPACING_DEFAULT;

    /// <summary>
    /// Specifies the amount of vertical space between horizontal gridlines (Drawing Grid option).
    /// </summary>
    int DrawingGridVerticalSpacing = DRAWING_GRID_VERTICAL_SPACING_DEFAULT;

    /// <summary>
    /// Specifies the amount of space between horizontal gridlines drawn on the screen (Drawing Grid option).
    /// </summary>
    int DisplayHorizontalDrawingGridEvery = DISPLAY_HORIZONTAL_DRAWING_GRID_EVERY_DEFAULT;

    /// <summary>
    /// Specifies the amount of space between vertical gridlines drawn on the screen (Drawing Grid option).
    /// </summary>
    int DisplayVerticalDrawingGridEvery = DISPLAY_VERTICAL_DRAWING_GRID_EVERY_DEFAULT;

    /// <summary>
    /// If set to on overrides the settings for drawingGridHorizontalOrigin and drawingGridVerticalOrigin 
    /// and sets the upper-left corner of the document area within the margins as the grid origin 
    /// (Drawing Grid option).
    /// </summary>
    boolean UseMarginsForDrawingGridOrigin = true;

    /// <summary>
    /// Specifies the point at the left edge of the page where you want the invisible grid to begin 
    /// (Drawing Grid option). This setting is ignored when useMarginsForDrawingGridOrigin is set to on.
    /// </summary>
    int DrawingGridHorizontalOrigin = DRAWING_GRID_HORIZONTAL_ORIGIN_DEFAULT;

    /// <summary>
    /// Specifies the point at the top edge of the page where you want the invisible grid to begin 
    /// (Drawing Grid option). This setting is ignored when useMarginsForDrawingGridOrigin is set to on.
    /// </summary>
    int DrawingGridVerticalOrigin = DRAWING_GRID_VERTICAL_ORIGIN_DEFAULT;

    /// <summary>
    /// Specifies whether to turn off the gray shading on form fields.
    /// </summary>
    boolean DoNotShadeFormData;

    /// <summary>
    /// When kerning for Latin text is turned on, also kern punctuation text (Asian Typography option).
    /// </summary>
    boolean PunctuationKerning;

    /// <summary>
    /// Sets the blank-space compression option you want for Asian characters (Asian Typography option). 
    /// The equivalent in HTML is setting text-justify-trim on the BODY element.
    /// </summary>
    /*JustificationMode*/int CharacterSpacingType = JustificationMode.EXPAND;

    /// <summary>
    /// Specifies whether to use standard characters to start and end lines of text (Asian Typography option).
    /// </summary>
    boolean StrictFirstAndLastChars;

    /// <summary>
    /// Specified for what Language it's used NoLineBreaksAfter, NoLineBreaksBefore.
    /// </summary>
    /*Language*/int NoLineBreaksLanguage;

    /// <summary>
    /// Specifies which characters are restricted from ending a line (Asian Typography option).
    /// </summary>
    String NoLineBreaksAfter;

    /// <summary>
    /// Specifies which characters are restricted from starting a line (Asian Typography option).
    /// </summary>
    String NoLineBreaksBefore;

    /// <summary>
    /// Specifies the encoding you want to use when you save as a Web page (Web option).
    /// // web
    /// </summary>
    String WebPageEncoding;

    /// <summary>
    /// Specifies whether to disable features not supported by Web browsers (Web option).
    /// // web
    /// </summary>
    boolean OptimizeForBrowser;

    /// <summary>
    /// Specifies that the web page produced by this document should be optimized for specific output target
    /// (web option).
    /// </summary>
    /// <remarks>It might be a good idea to set <see cref="HtmlSaveOptions.ExportXhtmlTransitional"/> to
    /// true somewhere after document is loaded if the WebTarget is set to 
    /// <see cref="Settings.WebTarget.XhtmlPlusCss1"/>, or <see cref="Settings.WebTarget.XhtmlPlusCss2"/>
    /// </remarks>
    /*WebTarget*/int _WebTarget = WebTarget.NONE;

    /// <summary>
    /// Specifies whether to rely on Vector Markup Language (VML) for displaying graphics in browsers (Web option).
    /// // web
    /// </summary>
    boolean RelyOnVml;

    /// <summary>
    /// Specifies whether to allow Portable Network Graphics (PNG ) as a graphic format (Web option).
    /// // web
    /// </summary>
    boolean AllowPng;

    /// <summary>
    /// Turns off cascading style sheets (CSS) for font formatting of Web pages (Web option).
    /// // web
    /// </summary>
    boolean DoNotRelyOnCss;

    /// <summary>
    /// When saving this file as a Web page, does not save as a single-file Web page (MHTML) (Web option).
    /// // web
    /// </summary>
    boolean DoNotSaveWebPagesAsSingleFile;

    /// <summary>
    /// When saving as a Web page, causes all supporting files such as bullets, background textures, 
    /// and graphics to be stored in the same folder as the Web page.
    /// // web
    /// </summary>
    boolean DoNotOrganizeInFolder;

    /// <summary>
    /// Disables long file names for Web pages, which forces a file name of no more than eight characters 
    /// (Web option).
    /// // web
    /// </summary>
    boolean DoNotUseLongFileNames;

    /// <summary>
    /// The number of pixels per inch that you want for the display of pictures in Web pages (Web option). 
    /// The size that you select affects the size of graphics relative to the size of text on the screen.
    /// // web
    /// </summary>
    int PixelsPerInch = PIXELS_PER_INCH_DEFAULT;

    /// <summary>
    /// Specifies the monitor resolution (screen size) that you are optimizing your Web pages for (Web option). 
    /// The screen size that you specify can affect the size and layout of images on Web pages.
    /// // web
    /// </summary>
    /*ScreenSize*/int TargetScreenSize = TARGET_SCREEN_SIZE_DEFAULT;

    /// <summary>
    /// Validates document against attached schemas (Templates and Add-Ins XML Schema option).
    /// // xml
    /// </summary>
    boolean ValidateAgainstSchema = true;

    /// <summary>
    /// Specifies whether to allow saving as XML even if the XML is not valid 
    /// // xml
    /// </summary>
    boolean SaveInvalidXml;

    /// <summary>
    /// Only Save Custom XML Markup.
    /// // xml
    /// </summary>
    /// <remarks>
    /// This element specifies that the contents of this document shall be saved as an XML file containing only the
    /// custom XML markup in this document in its regular form. The resulting document does not conform to ISO/IEC
    /// 29500 (i.e. this is an export-only save option for a WordprocessingML document).</remarks>
    boolean SaveXmlDataOnly;

    /// <summary>
    /// Show Visual Indicators for Custom XML Markup Start/End Locations.
    /// // xml
    /// </summary>
    /// <remarks>
    /// This element specifies that some visual indicator shall be provided for the start and end locations of 
    /// custom XML markup present in this document, if any. If this element is omitted, then applications
    ///  should not provide any visual indicator of the locations of custom XML markup start/end tags.
    /// </remarks>
    boolean ShowXmlTags;

    /// <summary>
    /// Specifies whether save and validate ignores all text not in leaf nodes 
    /// // xml
    /// </summary>
    boolean IgnoreMixedContent;

    /// <summary>
    /// Turns on display of placeholder text for all empty leaf elements.
    /// // xml
    /// </summary>
    boolean AlwaysShowPlaceholderText;

    /// <summary>
    /// Turns off wavy underline of schema violations in document.
    /// // xml
    /// </summary>
    boolean DoNotUnderlineInvalidXml;

    /// <summary>
    /// True if need to use XSLT specified in <see cref="SaveThroughXslt"/>.
    /// // xml
    /// </summary>
    boolean UseXsltWhenSaving;

    /// <summary>
    /// Specifies the custom transform to apply when saving document as XML (XML Save option).
    /// // xml
    /// </summary>
    String SaveThroughXslt;

    /// <summary>
    /// Controls how empty namespace elements that do not belong to a schema are handled. 
    /// If set to on, these elements will not be removed. If set to off, they will be removed.
    /// // xml
    /// </summary>
    boolean AlwaysMergeEmptyNamespace;

    /// <summary>
    /// When true, causes MS Word to Remove Smart Tags When Saving.
    /// </summary>
    boolean DoNotEmbedSmartTags;

    /// <summary>
    /// Specifies whether to save smart tag data as an XML-based property bag at the head of 
    /// the HTML page when saving as HTML.
    /// </summary>
    boolean SaveSmartTagsAsXml;

    int RsidRoot;

    /// <summary>
    /// Revision state identifiers table.
    /// </summary>
    RsidTable Rsids = new RsidTable();

    /// <summary>
    /// Specifies the code page to use when saving as encoded text.
    /// </summary>
    int CodePageText;

    /// <summary>
    /// Contains document-wide footnote and endnote properties.
    /// These are actually for Word2000 and earlier files (except endnote location).
    /// For newer formats there are per-section attributes.
    /// </summary>
    SectPr FootnotePr = new SectPr();

    /// <summary>
    /// Contains compatibility options (that is, the user preferences entered on the Compatibility tab of the 
    /// Options dialog in Word).
    /// </summary>
    CompatibilityOptions CompatibilityOptions = new CompatibilityOptions();

    ViewOptions ViewOptions = new ViewOptions();

    /// <summary>
    /// This is only found in DOCX.
    /// </summary>
    ThemeFontLanguages ThemeFontLanguages = new ThemeFontLanguages();

    /// <summary>
    /// Stored document-wide math properties (DOCX only)
    /// </summary>
    MathProperties MathProperties = new MathProperties();

    XmlNamespaceCollection XmlNamespaces = new XmlNamespaceCollection();

    XmlSchemaReferenceCollection XmlSchemaReferences = new XmlSchemaReferenceCollection();

    /// <summary>
    /// Specifies whether footnotes, endnotes and textboxes should be included in word counting.
    /// </summary>
    boolean DoNotIncludeSubDocsInStats;

    /// <summary>
    /// Specifies whether visual cues are not displayed around content contained in a document which is flagged as a possible spelling error.
    /// </summary>
    boolean HideSpellingErrors;

    /// <summary>
    /// Specifies whether visual cues are not displayed around content contained in a document which is flagged as a possible grammatical error.
    /// </summary>
    boolean HideGrammaticalErrors;

    /// <summary>
    /// Specifies that pictures in a document shall not automatically be compressed on saving in MS Word in order
    /// to reduce the overall size of the resulting document.
    /// </summary>
    boolean DoNotAutoCompressPictures;

    /// <summary>
    /// Specifies whether fields contained in a document should be automatically updated when the document is
    /// opened by MS Word.
    /// </summary>
    boolean UpdateFields;

    /// <summary>
    /// Specifies that contents of a document can be upgraded and that the resulting document shall not have
    /// its functionality limited to only those functions compatible with earlier word processing applications.
    /// </summary>
    /// <dev>
    /// ISO 29500 says that if an application does not know how to upgrade a document, this element should be
    /// ignored and persisted.
    /// </dev>
    boolean ForceUpgrade;

    /// <summary>
    /// Specifies how the datapoint properties ([MS-ODRAWXML] section 2.8.3.1) and datalabels ([MS-ODRAWXML]
    /// section 2.2.1.2) in all charts in a document behave.
    /// </summary>
    boolean ChartTrackingRefBased;

    /// <summary>
    /// Specifies a unique identifier for a set of documents derived from a common source.
    /// </summary>
    String DocumentSetId;

    /// <summary>
    /// Specifies that when true, the cropped-out areas of the images are not to be saved in MS Word.
    /// </summary>
    boolean DiscardImageEditingData;

    /// <summary>
    /// Specifies the resolution in dots per inch (DPI), at which images in a document will be saved in MS Word.
    /// </summary>
    int DefaultImageDpi;

    /// <summary>
    /// Specifies an arbitrary identifier for the context of the paragraph identifiers in the document.
    /// </summary>
    String DocId;

    /// <summary>
    /// Preferred editing language specified via <see cref="Loading.LanguagePreferences"/>.
    /// </summary>
    /// <remarks>
    /// This is actually an MS Word application setting, not a document property.
    /// However it is needed in the document model in order to imitate MS Word layout for Asian languages.
    /// </remarks>
    /*Language*/int PreferredEditingLanguage;

    // *** Below are DOP Only Properties. I don't know where they are in WordML.
    //>>>>>>>> #region  Properties available in DOC only.

    /// <summary>
    /// What outline levels to show. Not in WordML, but present and used in DOP.
    /// </summary>
    int ShowOutlineLevels = (int)OutlineLevel.BODY_TEXT;

    /// <summary>
    /// Display hidden document contents. Seems to be available in DOC only. 
    /// </summary>
    boolean ShowHidden;

    /// <summary>
    /// Display field results when set, otherwise display field codes. 
    /// Seems to be available in DOC only.
    /// </summary>
    boolean ShowFieldResults;

    /// <summary>
    /// Determines if revisions are printed. Not a WordML property. Seems to be obsolete DOC only.
    /// </summary>
    boolean PrintRevisions;

    /// <summary>
    /// Specifies that this file is based upon an HTML file. Not a WordML property.
    /// </summary>
    boolean HtmlDoc;

    /// <summary>
    /// True when widow control is enabled. Not a WordML property.
    /// </summary>
    boolean WidowControl = true;

    /// <summary>
    /// Show Asian grid. Not in WordML.
    /// </summary>
    boolean ShowGrid;

    /// <summary>
    /// Hide the version created for autoversion. Not in WordML.
    /// </summary>
    boolean HideLastVersion;

    /// <summary>
    /// Versioning is turned on. Not in WordML.
    /// </summary>
    boolean HasVersions;

    /// <summary>
    /// Autoversioning is enabled. Not in WordML.
    /// </summary>
    boolean AutoVersion;

    /// <summary>
    /// This is not really a DOP property. It is just set when reading DOC so the user
    /// can query how many versions were in the file. "Were" because we don't support
    /// versions and on save there will be no versions saved.
    /// </summary>
    int VersionsCount;

    /// <summary>
    /// Fit text should be ignored. Can be true only for DOC format.
    /// </summary>
    boolean NoFitText;

    //<<<<<<<< #endregion  Properties available in DOC only.


    // RK: *** Default values constants.
    // Note: In Microsoft Office Word 2007 the default sorting order is specified by the \spriorityN control word. 
    // RK OOXML spec is not correct. RTF 1.9.1 is correct.

    /// <summary>
    /// 0    Specifies that visible styles should be sorted by their names.
    /// </summary>
    static final int STYLE_PANE_SORT_METHOD_NAMES = 0;

    /// <summary>
    /// 1 - Specifies that visible styles should be sorted by the default sorting of the host application.
    /// </summary>
    static final int STYLE_PANE_SORT_METHOD_DEFAULT = 1;

    /// <summary>
    /// 2    Specifies that visible styles should be sorted by the font for which they apply.
    /// </summary>
    static final int STYLE_PANE_SORT_METHOD_FONT = 2;

    /// <summary>
    /// 3    Specifies that visible styles should be sorted by the style on which they are based.
    /// </summary>
    static final int STYLE_PANE_SORT_METHOD_STYLE = 3;

    /// <summary>
    /// 4    Specifies that visible styles should be sorted by their style types (e.g., character, linked, paragraph).
    /// Any other value    Undefined. Shall not be used.
    /// </summary>
    static final int STYLE_PANE_SORT_METHOD_TYPE = 4;

    static final int DEFAULT_TAB_STOP_DEFAULT = 720;
    static final int PIXELS_PER_INCH_DEFAULT = 96;
    static final /*ScreenSize*/int TARGET_SCREEN_SIZE_DEFAULT = ScreenSize.SIZE_800_X_600;
    static final int DRAWING_GRID_HORIZONTAL_SPACING_DEFAULT = 180;
    static final int DRAWING_GRID_VERTICAL_SPACING_DEFAULT = 180;
    static final int DISPLAY_HORIZONTAL_DRAWING_GRID_EVERY_DEFAULT = 1;
    static final int DISPLAY_VERTICAL_DRAWING_GRID_EVERY_DEFAULT = 1;
    static final int DRAWING_GRID_HORIZONTAL_ORIGIN_DEFAULT = 1800;
    static final int DRAWING_GRID_VERTICAL_ORIGIN_DEFAULT = 1440;
    static final int CLICK_TYPE_PARA_STYLE_ISTD_DEFAULT = StyleIndex.NORMAL;
    static final int DEFAULT_TABLE_STYLE_ISTD_DEFAULT = StyleIndex.NIL;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

