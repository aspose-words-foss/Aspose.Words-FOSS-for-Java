// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/07/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.StringUtil;
import com.aspose.CssEscape;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.Debug;
import com.aspose.ms.System.Text.UTF8Encoding;


/// <summary>
/// Can be used to specify additional options when saving a document into the
/// <see cref="SaveFormat.Html"/>, <see cref="SaveFormat.Mhtml"/>, <see cref="SaveFormat.Epub"/>,
/// <see cref="SaveFormat.Azw3"/> or <see cref="SaveFormat.Mobi"/> format.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/specify-save-options/">Specify Save Options</a> documentation article.</para>
/// </summary>
public class HtmlSaveOptions extends SaveOptions implements Cloneable
{
    /// <summary>
    /// Initializes a new instance of this class that can be used to save a document
    /// in the <see cref="SaveFormat.Html"/> format.
    /// </summary>
    public HtmlSaveOptions()
    {
    	this(SaveFormat.HTML);
	
    }

    /// <summary>
    /// Initializes a new instance of this class that can be used to save a document
    /// in the <see cref="SaveFormat.Html"/>, <see cref="SaveFormat.Mhtml"/>, <see cref="SaveFormat.Epub"/>,
    /// <see cref="SaveFormat.Azw3"/> or <see cref="SaveFormat.Mobi"/> format.
    /// </summary>
    /// <param name="saveFormat">
    /// Can be <see cref="SaveFormat.Html"/>, <see cref="SaveFormat.Mhtml"/>, <see cref="SaveFormat.Epub"/>,
    /// <see cref="SaveFormat.Azw3"/> or <see cref="SaveFormat.Mobi"/>.
    /// </param>
    public HtmlSaveOptions(/*SaveFormat*/int saveFormat)
    {
        mSaveImageOptions.ScaleImageToShapeSize = true;
        mExportRoundtripInformation = true;
        setUpdateAmbiguousTextFont(true);

        setSaveFormatCore(saveFormat);

        // For some formats there are different defaults. They are set only once, when the object is constructed.
        // If you change save format subsequently, all these setting will remain unchanged.
        switch (saveFormat)
        {
            case SaveFormat.EPUB:
            case SaveFormat.AZW_3:
            {
                setExportHeadersFootersMode(ExportHeadersFootersMode.NONE);
                setCssStyleSheetType(CssStyleSheetType.EXTERNAL);
                setDocumentSplitCriteria(DocumentSplitCriteria.HEADING_PARAGRAPH);
                mExportRoundtripInformation = false;
                break;
            }
            case SaveFormat.MHTML:
            {
                mExportRoundtripInformation = false;
                break;
            }
            default:
                break;
        }
    }

    /// <summary>
    /// Initializes a new instance of default options that used to save a document fragment
    /// in the <see cref="SaveFormat.Html"/> format in the <see cref="Node.ToString(SaveFormat)"/> function.
    /// </summary>
    static HtmlSaveOptions createSaveFragmentOptions()
    {
        HtmlSaveOptions htmlOptions = new HtmlSaveOptions();
        // External resources are disabled by default.
        htmlOptions.setExportImagesAsBase64(true);
        htmlOptions.setCssStyleSheetType(CssStyleSheetType.INLINE);
        htmlOptions.setExportFontResources(false);
        htmlOptions.setExportRoundtripInformation(false);
        return htmlOptions;
    }

    /// <summary>
    /// Specifies the format in which the document will be saved if this save options object is used.
    /// Can be <see cref="SaveFormat.Html"/>, <see cref="SaveFormat.Mhtml"/>, <see cref="SaveFormat.Epub"/>,
    /// <see cref="SaveFormat.Azw3"/> or <see cref="SaveFormat.Mobi"/>.
    /// </summary>
    public /*override*/ /*SaveFormat*/int getSaveFormat() { return mSaveFormat; }
    public /*override*/ void setSaveFormat(/*SaveFormat*/int value) { setSaveFormatCore(value); }

    /// <summary>
    /// Multiple main parts can appear if only saving HTML with document splitting.
    /// In EPUB all parts will be in the same container. So we still have one output file.
    /// </summary>
    /*override*/ boolean isMultipleMainPartsAllowed() { return (getSaveFormat() == SaveFormat.HTML) && (getDocumentSplitCriteria() != DocumentSplitCriteria.NONE); }

    HtmlSaveOptions deepClone()
    {
        return (HtmlSaveOptions)memberwiseClone();
    }

    /// <summary>
    /// Sets options we use for unit testing.
    /// </summary>
    /*override*/ void setTestMode()
    {
        super.setTestMode();

        // Don't output any volatile data when testing.
        setExportTimeStamps(false);
        // I think when HTML document has this, it gets validated on load (by XmlReader? I'm not sure).
        mHtmlVersion = HtmlVersion.XHTML;
        mExportXhtmlTransitional = true;
    }

    /// <summary>
    /// Specifies whether negative left and right indents of paragraphs are normalized
    /// when saving to HTML, MHTML or EPUB. Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>When negative indent is not allowed, it is exported as zero margin to HTML.
    /// When negative indent is allowed, a paragraph might appear partially outside of the
    /// browser window.</para>
    /// </remarks>
    public boolean getAllowNegativeIndent() { return mAllowNegativeIndent; }
    public void setAllowNegativeIndent(boolean value) { mAllowNegativeIndent = value; }

    /// <summary>
    /// Specifies the path and the name of the Cascading Style Sheet (CSS) file written when a document
    /// is exported to HTML.
    /// Default is an empty string.
    /// </summary>
    /// <remarks>
    /// <para>This property has effect only when saving a document to HTML format
    /// and external CSS style sheet is requested using <see cref="CssStyleSheetType"/>.</para>
    ///
    /// <para>If this property is empty, the CSS file will be saved into the same folder and with the same name as the HTML
    /// document but with the ".css" extension.</para>
    ///
    /// <para>If only path but no file name is specified in this property, the CSS file will be saved into the specified
    /// folder and will have the same name as the HTML document but with the ".css" extension.</para>
    ///
    /// <para>If the folder specified by this property doesn't exist, it will be created automatically before the CSS file
    /// is saved.</para>
    ///
    /// <para>Another way to specify a folder where external CSS file is saved is to use <see cref="ResourceFolder"/>.
    /// </para>
    ///
    /// <seealso cref="ResourceFolder"/>
    /// <seealso cref="ResourceFolderAlias"/>
    /// <seealso cref="CssStyleSheetType"/>
    /// </remarks>
    public String getCssStyleSheetFileName() { return mCssStyleSheetFileName; }
    public void setCssStyleSheetFileName(String value)
    {
        ArgumentUtil.checkNotNull(value, "CssStyleSheetFileName");
        mCssStyleSheetFileName = value;
    }

    /// <summary>
    /// Specifies how CSS (Cascading Style Sheet) styles are exported to HTML, MHTML or EPUB.
    /// Default value is <see cref="CssStyleSheetType.Inline"/> for HTML/MHTML and
    /// <see cref="CssStyleSheetType.External"/> for EPUB.
    /// </summary>
    /// <remarks>
    /// <para>Saving CSS style sheet into an external file is only supported when saving to HTML.
    /// When you are exporting to one of the container formats (EPUB or MHTML) and specifying
    /// <see cref="CssStyleSheetType.External"/>, CSS file will be encapsulated
    /// into the output package.</para>
    /// </remarks>
    /// <seealso cref="CssStyleSheetFileName"/>
    public /*CssStyleSheetType*/int getCssStyleSheetType() { return mCssStyleSheetType; }
    public void setCssStyleSheetType(/*CssStyleSheetType*/int value) { mCssStyleSheetType = value; }

    /// <summary>
    /// Specifies a prefix which is added to all CSS class names.
    /// Default value is an empty string and generated CSS class names have no common prefix.
    /// </summary>
    /// <remarks>
    /// <para>If this value is not empty, all CSS classes generated by Aspose.Words will start with the specified prefix.
    /// This might be useful, for example, if you add custom CSS to generated documents and want to prevent class
    /// name conflicts.</para>
    /// <para>If the value is not <c>null</c> or empty, it must be a valid CSS identifier.</para>
    /// </remarks>
    /// <exception cref="ArgumentException">The value is not empty and is not a valid CSS identifier.</exception>
    public String getCssClassNamePrefix() { return mCssClassNamesPrefix; }
    public void setCssClassNamePrefix(String value)
    {
        if (StringUtil.hasChars(value) && !CssEscape.isValidIdentifier(value))
            throw new IllegalArgumentException("The class name prefix must be a valid CSS identifier.");
        mCssClassNamesPrefix = value;
    }

    /// <summary>
    /// Allows to control how document parts are saved when a document is saved to HTML or EPUB.
    /// </summary>
    public IDocumentPartSavingCallback getDocumentPartSavingCallback() { return mDocumentPartSavingCallback; }
    public void setDocumentPartSavingCallback(IDocumentPartSavingCallback value) { mDocumentPartSavingCallback = value; }

    /// <summary>
    /// Allows to control how CSS styles are saved when a document is saved to HTML, MHTML or EPUB.
    /// </summary>
    public ICssSavingCallback getCssSavingCallback() { return mCssSavingCallback; }
    public void setCssSavingCallback(ICssSavingCallback value) { mCssSavingCallback = value; }

    /// <summary>
    /// Specifies how the document should be split when saving to <see cref="SaveFormat.Html"/>,
    /// <see cref="SaveFormat.Epub"/> or <see cref="SaveFormat.Azw3"/> format.
    /// Default is <see cref="DocumentSplitCriteria.None"/> for HTML and
    /// <see cref="DocumentSplitCriteria.HeadingParagraph"/> for EPUB and AZW3.
    /// </summary>
    /// <remarks>
    /// <para>Normally you would want a document saved to HTML as a single file.
    /// But in some cases it is preferable to split the output into several smaller HTML pages.
    /// When saving to HTML format these pages will be output to individual files or streams.
    /// When saving to EPUB format they will be incorporated into corresponding packages.</para>
    /// <para>A document cannot be split when saving in the MHTML format.</para>
    /// <seealso cref="DocumentSplitHeadingLevel"/>
    /// <seealso cref="DocumentPartSavingCallback"/>
    /// </remarks>
    public /*DocumentSplitCriteria*/int getDocumentSplitCriteria() { return mDocumentSplitCriteria; }
    public void setDocumentSplitCriteria(/*DocumentSplitCriteria*/int value) { mDocumentSplitCriteria = value; }

    /// <summary>
    /// Specifies the maximum level of headings at which to split the document.
    /// Default value is <c>2</c>.
    /// </summary>
    /// <remarks>
    /// <para>When <see cref="DocumentSplitCriteria"/> includes <see cref="DocumentSplitCriteria.HeadingParagraph"/>
    /// and this property is set to a value from 1 to 9, the document will be split at paragraphs formatted using
    /// <b>Heading 1</b>, <b>Heading 2</b> , <b>Heading 3</b> etc. styles up to the specified heading level.</para>
    /// <para>By default, only <b>Heading 1</b> and <b>Heading 2</b> paragraphs cause the document to be split.
    /// Setting this property to zero will cause the document not to be split at heading paragraphs at all.</para>
    /// <seealso cref="DocumentSplitCriteria"/>
    /// <seealso cref="DocumentPartSavingCallback"/>
    /// </remarks>
    public int getDocumentSplitHeadingLevel() { return mDocumentSplitHeadingLevel; }
    public void setDocumentSplitHeadingLevel(int value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0, 9, "DocumentSplitHeadingLevel");
        mDocumentSplitHeadingLevel = value;
    }

    /// <summary>
    /// Specifies the encoding to use when exporting to HTML, MHTML or EPUB.
    /// Default value is <ms><c>new UTF8Encoding(false)</c> (UTF-8 without BOM)</ms><java>'UTF-8' Charset
    /// with BOM.</java><cpp><c>new UTF8Encoding(false)</c> (UTF-8 without BOM)</cpp>.
    /// </summary>
    /// <remarks>
    /// <java>
    /// Please note that BOM is added in the case of every Unicode charset (UTF-16, UTF-16BE, UTF-32, etc).
    /// </java>
    /// </remarks>
    /// <javaName>Encoding(java.nio.charset.Charset)</javaName>
    //JAVA: property accessor is internalized
	Encoding getEncodingInternal() { return mEncoding; }
	//JAVA-added public wrapper for internalized property accessor
	public java.nio.charset.Charset getEncoding() { return Encoding.toJava(getEncodingInternal()); }
    //JAVA: property accessor is internalized
	void setEncodingInternal(Encoding value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");
        mEncoding = value;
    }
	//JAVA-added public wrapper for internalized property accessor
	public void setEncoding(java.nio.charset.Charset value) { setEncodingInternal(Encoding.fromJava(value)); }

    /// <summary>
    /// Specifies the maximum level of headings populated to the navigation map when exporting to EPUB, MOBI, or AZW3
    /// formats. Default value is <c>3</c>.
    /// </summary>
    /// <remarks>
    /// <para>The navigation map allows user agents to provide an easy way of navigation through the document structure.
    /// Usually navigation points correspond to headings in the document. In order to populate headings up to level <b>N</b>
    /// assign this value to <see cref="NavigationMapLevel" />.</para>
    ///
    /// <para>By default, three levels of headings are populated: paragraphs of styles <b>Heading 1</b>, <b>Heading 2</b>
    /// and <b>Heading 3</b>.
    /// You can set this property to a value from 1 to 9 in order to request the corresponding maximum level.
    /// Setting it to zero will reduce the navigation map to only the document root or roots of document parts.</para>
    /// </remarks>
    public int getNavigationMapLevel() { return mNavigationMapLevel; }
    public void setNavigationMapLevel(int value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0, 9, "NavigationMapLevel");
        mNavigationMapLevel = value;
    }

    /// <summary>
    /// Specifies whether to export built-in and custom document properties to HTML, MHTML or EPUB.
    /// Default value is <c>false</c>.
    /// </summary>
    public boolean getExportDocumentProperties() { return mExportDocumentProperties; }
    public void setExportDocumentProperties(boolean value) { mExportDocumentProperties = value; }

    /// <summary>
    /// Specifies whether font resources should be exported to HTML, MHTML or EPUB.
    /// Default is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>Exporting font resources allows for consistent document rendering independent of the fonts available
    /// in a given user's environment.</para>
    ///
    /// <para>If <see cref="ExportFontResources" /> is set to <c>true</c>, main HTML document will refer to every font via
    /// the CSS 3 <b>@font-face</b> at-rule and fonts will be output as separate files. When exporting to IDPF EPUB or MHTML
    /// formats, fonts will be embedded into the corresponding package along with other subsidiary files.</para>
    ///
    /// <para>If <see cref="ExportFontsAsBase64"/> is set to <c>true</c>, fonts will not be saved to separate files.
    /// Instead, they will be embedded into <b>@font-face</b> at-rules in Base64 encoding.</para>
    ///
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="SaveOptions.HtmlExportFontResourcesCommonLicensingWarning"]/*'/>
    ///
    /// <seealso cref="FontResourcesSubsettingSizeThreshold" />
    /// </remarks>
    public boolean getExportFontResources() { return mExportFontResources; }
    public void setExportFontResources(boolean value) { mExportFontResources = value; }

    /// <summary>
    /// Specifies whether fonts resources should be embedded to HTML in Base64 encoding.
    /// Default is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>By default, fonts are written to separate files. If this option is set to <c>true</c>, fonts will be embedded
    /// into the document's CSS in Base64 encoding.</para>
    /// </remarks>
    public boolean getExportFontsAsBase64() { return mExportFontsAsBase64; }
    public void setExportFontsAsBase64(boolean value) { mExportFontsAsBase64 = value; }

    /// <summary>
    /// Specifies how headers and footers are output to HTML, MHTML or EPUB.
    /// Default value is <see cref="ExportHeadersFootersMode.PerSection"/> for HTML/MHTML
    /// and <see cref="ExportHeadersFootersMode.None"/> for EPUB.
    /// </summary>
    /// <remarks>
    /// <para>It is hard to meaningfully output headers and footers to HTML because HTML is not paginated.</para>
    ///
    /// <para>When this property is <see cref="ExportHeadersFootersMode.PerSection"/>, Aspose.Words exports
    /// only primary headers and footers at the beginning and the end of each section.</para>
    ///
    /// <para>When it is <see cref="ExportHeadersFootersMode.FirstSectionHeaderLastSectionFooter"/>
    /// only first primary header and the last primary footer (including linked to previous) are exported.</para>
    ///
    /// <para>You can disable export of headers and footers altogether by setting this property
    /// to <see cref="ExportHeadersFootersMode.None"/>.</para>
    /// </remarks>
    public /*ExportHeadersFootersMode*/int getExportHeadersFootersMode() { return mExportHeadersFootersMode; }
    public void setExportHeadersFootersMode(/*ExportHeadersFootersMode*/int value) { mExportHeadersFootersMode = value; }

    /// <summary>
    /// Specifies whether images are saved in Base64 format to the output HTML, MHTML or EPUB.
    /// Default is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>When this property is set to <c>true</c> images data are exported
    /// directly into the <b>img</b> elements and separate files are not created.</para>
    /// </remarks>
    public boolean getExportImagesAsBase64() { return mSaveImageOptions.ToBase64; }
    public void setExportImagesAsBase64(boolean value) { mSaveImageOptions.ToBase64 = value; }

    /// <summary>
    /// Specifies whether language information is exported to HTML, MHTML or EPUB.
    /// Default is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>When this property is set to <c>true</c> Aspose.Words outputs <b>lang</b> HTML attribute on the document
    /// elements that specify language. This can be needed to preserve language related semantics.</para>
    /// </remarks>
    public boolean getExportLanguageInformation() { return mExportLanguageInformation; }
    public void setExportLanguageInformation(boolean value) { mExportLanguageInformation = value; }

    /// <summary>
    /// Controls how list labels are output to HTML, MHTML or EPUB.
    /// Default value is <see cref="ExportListLabels.Auto"/>.
    /// </summary>
    public /*ExportListLabels*/int getExportListLabels() { return mExportListLabels; }
    public void setExportListLabels(/*ExportListLabels*/int value) { mExportListLabels = value; }

    /// <summary>
    /// Specifies in what format metafiles are saved when exporting to HTML, MHTML, or EPUB.
    /// Default value is <see cref="HtmlMetafileFormat.Png" />, meaning that metafiles are rendered to raster PNG images.
    /// </summary>
    /// <remarks>
    /// <para>Metafiles are not natively displayed by HTML browsers. By default, Aspose.Words converts WMF and EMF
    /// images into PNG files when exporting to HTML. Other options are to convert metafiles to SVG images or to export
    /// them as is without conversion.</para>
    ///
    /// <para>Some image transforms, in particular image cropping, will not be applied to metafile images if they
    /// are exported to HTML without conversion.</para>
    /// </remarks>
    /// <seealso cref="ImageResolution"/>
    /// <seealso cref="ScaleImageToShapeSize"/>
    public /*HtmlMetafileFormat*/int getMetafileFormat() { return mSaveImageOptions.MetafileFormat; }
    public void setMetafileFormat(/*HtmlMetafileFormat*/int value) { mSaveImageOptions.MetafileFormat = value; }

    /// <summary>
    /// Specifies whether page setup is exported to HTML, MHTML or EPUB.
    /// Default is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>Each <see cref="Section"/> in Aspose.Words document model provides page setup information
    /// via <see cref="PageSetup"/> class. When you export a document to HTML format you might need to keep this information
    /// for further usage. In particular, page setup might be important for rendering to paged media (printing)
    /// or subsequent conversion to the native Microsoft Word file formats (DOCX, DOC, RTF, WML).</para>
    ///
    /// <para>In most cases HTML is intended for viewing in browsers where pagination is not performed. So this feature
    /// is inactive by default.</para>
    /// </remarks>
    public boolean getExportPageSetup() { return mExportPageSetup; }
    public void setExportPageSetup(boolean value) { mExportPageSetup = value; }

    /// <summary>
    /// Specifies whether page margins is exported to HTML, MHTML or EPUB.
    /// Default is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// Aspose.Words does not show area of page margins by default.
    /// If any elements are completely or partially clipped by the document edge the displayed area can be extended with
    /// this option.
    /// </remarks>
    public boolean getExportPageMargins() { return mExportPageMargins; }
    public void setExportPageMargins(boolean value) { mExportPageMargins = value; }

    /// <summary>
    /// Specifies whether font sizes should be output in relative units when saving to HTML, MHTML or EPUB.
    /// Default is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>In many existing documents (HTML, IDPF EPUB) font sizes are specified in relative units. This allows
    /// applications to adjust text size when viewing/processing documents. For instance, Microsoft Internet Explorer
    /// has "View->Text Size" submenu, Adobe Digital Editions has two buttons: Increase/Decrease Text Size.
    /// If you expect this functionality to work then set <see cref="ExportRelativeFontSize" /> property to <c>true</c>.
    /// </para>
    ///
    /// <para>Aspose Words document model contains and operates only with absolute font size units. Relative units need
    /// additional logic to be recalculated from some initial (standard) size. Font size of <b>Normal</b> document style
    /// is taken as standard. For instance, if <b>Normal</b> has 12pt font and some text is 18pt then it will be output
    /// as <b>1.5em.</b> to the HTML.</para>
    ///
    /// <para>When this option is enabled, document elements other than text will still have absolute sizes. Also some
    /// text-related attributes might be expressed absolutely. In particular, line spacing specified with "exactly" rule
    /// might produce unwanted results when scaling text. So the source documents should be properly designed and tested
    /// when exporting with <see cref="ExportRelativeFontSize" /> set to <c>true</c>.</para>
    /// </remarks>
    public boolean getExportRelativeFontSize() { return mExportRelativeFontSize; }
    public void setExportRelativeFontSize(boolean value) { mExportRelativeFontSize = value; }

    /// <summary>
    /// Controls how text input form fields are saved to HTML or MHTML.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>When set to <c>true</c>, exports text input form fields as normal text.
    /// When <c>false</c>, exports Word text input form fields as INPUT elements in HTML.</para>
    /// <para>When exporting to EPUB, text input form fields are always saved as text due
    /// to requirements of this format.</para>
    /// </remarks>
    public boolean getExportTextInputFormFieldAsText() { return mExportTextInputFormFieldAsText; }
    public void setExportTextInputFormFieldAsText(boolean value) { mExportTextInputFormFieldAsText = value; }

    /// <summary>
    /// Controls whether <see cref="Drawing.Shape"/> nodes are converted to SVG images when saving
    /// to HTML, MHTML, EPUB or AZW3.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>
    /// If this option is set to <c>true</c>, <see cref="Drawing.Shape"/> nodes are exported as &lt;svg&gt; elements.
    /// Otherwise, they are rendered to bitmaps and are exported as &lt;img&gt; elements.
    /// </para>
    /// </remarks>
    public boolean getExportShapesAsSvg() { return mSaveImageOptions.ShapesAsSvg; }
    public void setExportShapesAsSvg(boolean value) { mSaveImageOptions.ShapesAsSvg = value; }

    /// <summary>
    /// Controls how drop-down form fields are saved to HTML or MHTML.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>When set to <c>true</c>, exports drop-down form fields as normal text.
    /// When <c>false</c>, exports drop-down form fields as SELECT element in HTML.</para>
    /// <para>When exporting to EPUB, text drop-down form fields are always saved as text due
    /// to requirements of this format.</para>
    /// </remarks>
    public boolean getExportDropDownFormFieldAsText() { return mExportDropDownFormFieldAsText; }
    public void setExportDropDownFormFieldAsText(boolean value) { mExportDropDownFormFieldAsText = value; }

    /// <summary>
    /// Specifies whether to write page numbers to table of contents when saving HTML, MHTML and EPUB.
    /// Default value is <c>false</c>.
    /// </summary>
    public boolean getExportTocPageNumbers() { return mExportTocPageNumbers; }
    public void setExportTocPageNumbers(boolean value) { mExportTocPageNumbers = value; }

    /// <summary>
    /// Specifies whether to write the DOCTYPE declaration when saving to HTML or MHTML.
    /// When <c>true</c>, writes a DOCTYPE declaration in the document prior to the root element.
    /// Default value is <c>false</c>.
    /// When saving to EPUB or HTML5 (<see cref="HtmlVersion.Html5"/>) the DOCTYPE
    /// declaration is always written.
    /// </summary>
    /// <remarks>
    /// <para>Aspose.Words always writes well formed HTML regardless of this setting.</para>
    ///
    /// <para>When <c>true</c>, the beginning of the HTML output document will look like this:</para>
    /// <code>
    /// &lt;?xml version="1.0" encoding="utf-8" standalone="no" ?&gt;
    /// &lt;!DOCTYPE html
    ///       PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    /// "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;
    /// &lt;html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"&gt;
    /// </code>
    ///
    /// <para>Aspose.Words aims to output XHTML according to the XHTML 1.0 Transitional specification,
    /// but the output will not always validate against the DTD. Some structures inside a Microsoft Word
    /// document are hard or impossible to map to a document that will validate against the XHTML schema.
    /// For example, XHTML does not allow nested lists (UL cannot be nested inside another UL element),
    /// but in Microsoft Word document multilevel lists occur quite often.</para>
    /// </remarks>
    public boolean getExportXhtmlTransitional() { return mExportXhtmlTransitional; }
    public void setExportXhtmlTransitional(boolean value) { mExportXhtmlTransitional = value; }

    /// <summary>
    /// Specifies version of HTML standard that should be used when saving the document to HTML or MHTML.
    /// Default value is <see cref="HtmlVersion.Xhtml"/>.
    /// </summary>
    public /*HtmlVersion*/int getHtmlVersion() { return mHtmlVersion; }
    public void setHtmlVersion(/*HtmlVersion*/int value) { mHtmlVersion = value; }

    /// <summary>
    /// Specifies whether to write the roundtrip information when saving to HTML, MHTML or EPUB.
    /// Default value is <c>true</c> for HTML and <c>false</c> for MHTML and EPUB.
    /// </summary>
    /// <remarks>
    /// <para>Saving of the roundtrip information allows to restore document properties such as tab stops,
    /// comments, headers and footers during the HTML documents loading back into a <see cref="Document"/> object.</para>
    /// <para>When <c>true</c>, the roundtrip information is exported as -aw-* CSS properties
    /// of the corresponding HTML elements.</para>
    /// <para>When <c>false</c>, causes no roundtrip information to be output into produced files.</para>
    /// </remarks>
    public boolean getExportRoundtripInformation() { return mExportRoundtripInformation; }
    public void setExportRoundtripInformation(boolean value) { mExportRoundtripInformation = value; }

    /// <summary>
    /// Specifies a physical folder where all resources like images, fonts, and external CSS are saved when a document
    /// is exported to HTML. Default is an empty string.
    /// </summary>
    /// <remarks>
    /// <para><see cref="ResourceFolder"/> is the simplest way to specify a folder where all resources should be written.
    /// Another way is to use individual properties <see cref="FontsFolder"/>, <see cref="ImagesFolder"/>,
    /// and <see cref="CssStyleSheetFileName"/>.</para>
    ///
    /// <para><see cref="ResourceFolder"/> has a lower priority than folders specified via <see cref="FontsFolder"/>,
    /// <see cref="ImagesFolder"/>, and <see cref="CssStyleSheetFileName"/>. For example, if both
    /// <see cref="ResourceFolder"/> and <see cref="FontsFolder"/> are specified, fonts will be saved
    /// to <see cref="FontsFolder"/>, while images and CSS will be saved to <see cref="ResourceFolder"/>.</para>
    ///
    /// <para>If the folder specified by <see cref="ResourceFolder"/> doesn't exist, it will be created automatically.
    /// </para>
    ///
    /// <seealso cref="FontsFolder"/>
    /// <seealso cref="ImagesFolder"/>
    /// <seealso cref="CssStyleSheetFileName"/>
    /// </remarks>
    public String getResourceFolder() { return mResourceFolder; }
    public void setResourceFolder(String value)
    {
        ArgumentUtil.checkNotNull(value, "ResourceFolder");
        mResourceFolder = value;
    }

    /// <summary>
    /// Specifies the name of the folder used to construct URIs of all resources written into an HTML document.
    /// Default is an empty string.
    /// </summary>
    /// <remarks>
    /// <para><see cref="ResourceFolderAlias"/> is the simplest way to specify how URIs for all resource files should be
    /// constructed. Same information can be specified for images and fonts separately via <see cref="ImagesFolderAlias"/>
    /// and <see cref="FontsFolderAlias"/> properties, respectively. However, there is no individual property for CSS.
    /// </para>
    ///
    /// <para><see cref="ResourceFolderAlias"/> has lower priority than <see cref="FontsFolderAlias"/>
    /// and <see cref="ImagesFolderAlias"/>. For example, if both <see cref="ResourceFolderAlias"/>
    /// and <see cref="FontsFolderAlias"/> are specified, fonts' URIs will be constructed using
    /// <see cref="FontsFolderAlias"/>, while URIs of images and CSS will be constructed using
    /// <see cref="ResourceFolderAlias"/>.</para>
    ///
    /// <para>If <see cref="ResourceFolderAlias"/> is empty, the <see cref="ResourceFolder"/> property value will be used
    /// to construct resource URIs.</para>
    ///
    /// <para>If <see cref="ResourceFolderAlias"/> is set to '.' (dot), resource URIs will contain file names only, without
    /// any path.</para>
    ///
    /// <seealso cref="ResourceFolder"/>
    /// <seealso cref="FontsFolderAlias"/>
    /// <seealso cref="ImagesFolderAlias"/>
    /// </remarks>
    public String getResourceFolderAlias() { return mResourceFolderAlias; }
    public void setResourceFolderAlias(String value)
    {
        ArgumentUtil.checkNotNull(value, "ResourceFolderAlias");
        mResourceFolderAlias = value;
    }

    /// <summary>
    /// Specifies the physical folder where fonts are saved when exporting a document to HTML.
    /// Default is an empty string.
    /// </summary>
    /// <remarks>
    /// <para>When you save a <see cref="Document"/> in HTML format and <see cref="ExportFontResources"/>
    /// is set to <c>true</c>, Aspose.Words needs to save fonts used in the document as standalone files.
    /// <see cref="FontsFolder"/> allows you to specify where the fonts will be saved and
    /// <see cref="FontsFolderAlias"/> allows to specify how the font URIs will be constructed.</para>
    ///
    /// <para>If you save a document into a file and provide a file name, Aspose.Words, by default, saves the
    /// fonts in the same folder where the document file is saved. Use <see cref="FontsFolder"/>
    /// to override this behavior.</para>
    ///
    /// <para>If you save a document into a stream, Aspose.Words does not have a folder where to save the fonts,
    /// but still needs to save the fonts somewhere. In this case, you need to specify an accessible folder
    /// in the <see cref="FontsFolder"/> property.</para>
    ///
    /// <para>If the folder specified by <see cref="FontsFolder"/> doesn't exist, it will be created automatically.</para>
    ///
    /// <para><see cref="ResourceFolder"/> is another way to specify a folder where fonts should be saved.</para>
    ///
    /// <seealso cref="ResourceFolder"/>
    /// <seealso cref="ExportFontResources"/>
    /// <seealso cref="FontsFolderAlias"/>
    /// </remarks>
    public String getFontsFolder() { return mFontsFolder; }
    public void setFontsFolder(String value)
    {
        ArgumentUtil.checkNotNull(value, "FontsFolder");
        mFontsFolder = value;
    }

    /// <summary>
    /// Specifies the name of the folder used to construct font URIs written into an HTML document.
    /// Default is an empty string.
    /// </summary>
    /// <remarks>
    /// <para>When you save a <see cref="Document"/> in HTML format and <see cref="ExportFontResources"/>
    /// is set to <c>true</c>, Aspose.Words needs to save fonts used in the document as standalone files.
    /// <see cref="FontsFolder"/> allows you to specify where the fonts will be saved and
    /// <see cref="FontsFolderAlias"/> allows to specify how the font URIs will be constructed.</para>
    ///
    /// <para>If <see cref="FontsFolderAlias"/> is not an empty string, then the font URI written
    /// to HTML will be <i>FontsFolderAlias + &lt;font file name&gt;</i>.</para>
    ///
    /// <para>If <see cref="FontsFolderAlias"/> is an empty string, then the font URI written
    /// to HTML will be <i>FontsFolder + &lt;font file name&gt;</i>.</para>
    ///
    /// <para>If <see cref="FontsFolderAlias"/> is set to '.' (dot), then the font file name
    /// will be written to HTML without path regardless of other options.</para>
    ///
    /// <para>Alternative way to specify the name of the folder to construct font URIs
    /// is to use <see cref="ResourceFolderAlias"/>.</para>
    ///
    /// <seealso cref="ResourceFolderAlias"/>
    /// <seealso cref="ExportFontResources"/>
    /// <seealso cref="FontsFolder"/>
    /// </remarks>
    public String getFontsFolderAlias() { return mFontsFolderAlias; }
    public void setFontsFolderAlias(String value)
    {
        ArgumentUtil.checkNotNull(value, "FontsFolderAlias");
        mFontsFolderAlias = value;
    }

    /// <summary>
    /// Controls which font resources need subsetting when saving to HTML, MHTML or EPUB.
    /// Default is <c>0</c>.
    /// </summary>
    /// <remarks>
    /// <para><see cref="ExportFontResources"/> allows exporting fonts as subsidiary files or as parts of the output
    /// package. If the document uses many fonts, especially with large number of glyphs, then output size can grow
    /// significantly. Font subsetting reduces the size of the exported font resource by filtering out glyphs that
    /// are not used by the current document.</para>
    ///
    /// <para>Font subsetting works as follows:</para>
    /// <list type="bullet">
    /// <item>By default, all exported fonts are subsetted.</item>
    /// <item>Setting <see cref="FontResourcesSubsettingSizeThreshold" /> to a positive value
    /// instructs Aspose.Words to subset fonts which file size is larger than the specified value.</item>
    /// <item>Setting the property to <ms><see cref="int.MaxValue"/></ms><java><b>Integer.MAX_VALUE</b></java><cpp><see cref="int.MaxValue"/></cpp>
    /// suppresses font subsetting.</item>
    /// </list>
    ///
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="SaveOptions.HtmlExportFontResourcesCommonLicensingWarning"]/*'/>
    ///
    /// <seealso cref="ExportFontResources" />
    /// </remarks>
    public int getFontResourcesSubsettingSizeThreshold() { return mFontResourcesSubsettingSizeThreshold; }
    public void setFontResourcesSubsettingSizeThreshold(int value)
    {
        if (value < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        mFontResourcesSubsettingSizeThreshold = value;
    }

    /// <summary>
    /// Specifies the physical folder where images are saved when exporting a document to HTML format.
    /// Default is an empty string.
    /// </summary>
    /// <remarks>
    /// <para>When you save a <see cref="Document"/> in HTML format, Aspose.Words needs to save all
    /// images embedded in the document as standalone files. <see cref="ImagesFolder"/>
    /// allows you to specify where the images will be saved and <see cref="ImagesFolderAlias"/>
    /// allows to specify how the image URIs will be constructed.</para>
    ///
    /// <para>If you save a document into a file and provide a file name, Aspose.Words, by default, saves the
    /// images in the same folder where the document file is saved. Use <see cref="ImagesFolder"/>
    /// to override this behavior.</para>
    ///
    /// <para>If you save a document into a stream, Aspose.Words does not have a folder where to save the images,
    /// but still needs to save the images somewhere. In this case, you need to specify an accessible folder
    /// in the <see cref="ImagesFolder"/> property or provide custom streams via
    /// the <see cref="ImageSavingCallback"/> event handler.</para>
    ///
    /// <para>If the folder specified by <see cref="ImagesFolder"/> doesn't exist, it will be created automatically.</para>
    ///
    /// <para><see cref="ResourceFolder"/> is another way to specify a folder where images should be saved.</para>
    ///
    /// <seealso cref="ResourceFolder"/>
    /// <seealso cref="ImagesFolderAlias"/>
    /// <seealso cref="ImageSavingCallback"/>
    /// </remarks>
    public String getImagesFolder() { return mImagesFolder; }
    public void setImagesFolder(String value)
    {
        ArgumentUtil.checkNotNull(value, "ImagesFolder");
        mImagesFolder = value;
    }

    /// <summary>
    /// Specifies the name of the folder used to construct image URIs written into an HTML document.
    /// Default is an empty string.
    /// </summary>
    /// <remarks>
    /// <para>When you save a <see cref="Document"/> in HTML format, Aspose.Words needs to save all
    /// images embedded in the document as standalone files. <see cref="ImagesFolder"/>
    /// allows you to specify where the images will be saved and <see cref="ImagesFolderAlias"/>
    /// allows to specify how the image URIs will be constructed.</para>
    ///
    /// <para>If <see cref="ImagesFolderAlias"/> is not an empty string, then the image URI written
    /// to HTML will be <i>ImagesFolderAlias + &lt;image file name&gt;</i>.</para>
    ///
    /// <para>If <see cref="ImagesFolderAlias"/> is an empty string, then the image URI written
    /// to HTML will be <i>ImagesFolder + &lt;image file name&gt;</i>.</para>
    ///
    /// <para>If <see cref="ImagesFolderAlias"/> is set to '.' (dot), then the image file name
    /// will be written to HTML without path regardless of other options.</para>
    ///
    /// <para>Alternative way to specify the name of the folder to construct image URIs
    /// is to use <see cref="ResourceFolderAlias"/>.</para>
    ///
    /// <seealso cref="ResourceFolderAlias"/>
    /// <seealso cref="ImagesFolder"/>
    /// <seealso cref="ImageSavingCallback"/>
    /// </remarks>
    public String getImagesFolderAlias() { return mImagesFolderAlias; }
    public void setImagesFolderAlias(String value)
    {
        ArgumentUtil.checkNotNull(value, "ImagesFolderAlias");
        mImagesFolderAlias = value;
    }

    /// <summary>
    /// Specifies the output resolution for images when exporting to HTML, MHTML or EPUB.
    /// Default is <c>96 dpi</c>.
    /// </summary>
    /// <remarks>
    /// <para>This property effects raster images when <see cref="ScaleImageToShapeSize"/>
    /// is <c>true</c> and effects metafiles exported as raster images. Some image properties such as cropping
    /// or rotation require saving transformed images and in this case transformed images are created in the given
    /// resolution.</para>
    /// </remarks>
    /// <seealso cref="ScaleImageToShapeSize"/>
    public int getImageResolution() { return mSaveImageOptions.Resolution; }
    public void setImageResolution(int value)
    {
        ArgumentUtil.checkPositive(value, "ImageResolution");
        mSaveImageOptions.Resolution = value;
    }

    /// <summary>
    /// Allows to control how images are saved when a document is saved to HTML, MHTML or EPUB.
    /// </summary>
    public IImageSavingCallback getImageSavingCallback() { return mSaveImageOptions.ImageSavingCallback; }
    public void setImageSavingCallback(IImageSavingCallback value) { mSaveImageOptions.ImageSavingCallback = value; }

    /// <summary>
    /// Specifies whether images are scaled by Aspose.Words to the bounding shape size when exporting to HTML, MHTML
    /// or EPUB.
    /// Default value is <c>true</c>.
    /// </summary>
    /// <remarks>
    /// <para>An image in a Microsoft Word document is a shape. The shape has a size and the image
    /// has its own size. The sizes are not directly linked. For example, the image can be 1024x786 pixels,
    /// but shape that displays this image can be 400x300 points.</para>
    ///
    /// <para>In order to display an image in the browser, it must be scaled to the shape size.
    /// The <see cref="ScaleImageToShapeSize"/> property controls where the scaling of the image
    /// takes place: in Aspose.Words during export to HTML or in the browser when displaying the document.</para>
    ///
    /// <para>When <see cref="ScaleImageToShapeSize"/> is <c>true</c>, the image is scaled by Aspose.Words
    /// using high quality scaling during export to HTML. When <see cref="ScaleImageToShapeSize"/>
    /// is <c>false</c>, the image is output with its original size and the browser has to scale it.</para>
    ///
    /// <para>In general, browsers do quick and poor quality scaling. As a result, you will normally get better
    /// display quality in the browser and smaller file size when <see cref="ScaleImageToShapeSize"/> is <c>true</c>,
    /// but better printing quality and faster conversion when <see cref="ScaleImageToShapeSize"/> is <c>false</c>.</para>
    ///
    /// <para>In addition to shapes containing individual raster images, this option also affects group shapes consisting
    /// of raster images. If <see cref="ScaleImageToShapeSize"/> is <c>false</c> and a group shape contains raster images
    /// whose intrinsic resolution is higher than the value specified in <see cref="ImageResolution"/>, Aspose.Words will
    /// increase rendering resolution for that group. This allows to better preserve quality of grouped high resolution
    /// images when saving to HTML.</para>
    /// </remarks>
    /// <seealso cref="ImageResolution"/>
    public boolean getScaleImageToShapeSize() { return mSaveImageOptions.ScaleImageToShapeSize; }
    public void setScaleImageToShapeSize(boolean value) { mSaveImageOptions.ScaleImageToShapeSize = value; }

    /// <summary>
    /// Controls how table, row and cell widths are exported to HTML, MHTML or EPUB.
    /// Default value is <see cref="HtmlElementSizeOutputMode.All"/>.
    /// </summary>
    /// <remarks>
    /// <para>In the HTML format, table, row and cell elements
    /// (<b>&lt;table&gt;</b>, <b>&lt;tr&gt;</b>, <b>&lt;th&gt;</b>, <b>&lt;td&gt;</b>)
    /// can have their widths specified either in relative (percentage) or in absolute units.
    /// In a document in Aspose.Words, tables, rows and cells can have their widths specified
    /// using either relative or absolute units too.</para>
    ///
    /// <para>When you convert a document to HTML using Aspose.Words, you might want to control how
    /// table, row and cell widths are exported to affect how the resulting document is displayed
    /// in the visual agent (e.g. a browser or viewer).</para>
    ///
    /// <para>Use this property as a filter to specify what table widths values are exported into the destination document.
    /// For example, if you are converting a document to EPUB and intend to view the document on a mobile reading device,
    /// then you probably want to avoid exporting absolute width values. To do this you need to specify
    /// the output mode <see cref="HtmlElementSizeOutputMode.RelativeOnly"/> or <see cref="HtmlElementSizeOutputMode.None"/>
    /// so the viewer on the mobile device can layout the table to fit the width of the screen as best as it can.</para>
    /// </remarks>
    public /*HtmlElementSizeOutputMode*/int getTableWidthOutputMode() { return mTableWidthOutputMode; }
    public void setTableWidthOutputMode(/*HtmlElementSizeOutputMode*/int value) { mTableWidthOutputMode = value; }

    /// <summary>
    /// Controls how OfficeMath objects are exported to HTML, MHTML or EPUB.
    /// Default value is <see cref="HtmlOfficeMathOutputMode.Image"/>.
    /// </summary>
    public /*HtmlOfficeMathOutputMode*/int getOfficeMathOutputMode() { return mOfficeMathOutputMode; }
    public void setOfficeMathOutputMode(/*HtmlOfficeMathOutputMode*/int value) { mOfficeMathOutputMode = value; }

    /// <summary>
    /// Specifies whether original URL should be used as the URL of the linked images.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>If value is set to <c>true</c> <see cref="Drawing.ImageData.SourceFullName"/> value is used
    /// as the URL of linked images and linked images are not loaded into document's folder
    /// or <see cref="ImagesFolder"/>.</para>
    ///
    /// <para>If value is set to <c>false</c> linked images are loaded into document's folder
    /// or <see cref="ImagesFolder"/> and URL of each linked image is constructed depending
    /// on document's folder, <see cref="ImagesFolder"/>
    /// and <see cref="ImagesFolderAlias"/> properties.</para>
    /// </remarks>
    public boolean getExportOriginalUrlForLinkedImages() { return mExportOriginalUrlForLinkedImages; }
    public void setExportOriginalUrlForLinkedImages(boolean value) { mExportOriginalUrlForLinkedImages = value; }

    /// <summary>
    /// Specifies whether to use CID (Content-ID) URLs to reference resources (images, fonts, CSS) included in MHTML
    /// documents. Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>This option affects only documents being saved to MHTML.</para>
    ///
    /// <para>By default, resources in MHTML documents are referenced by file name (for example, "image.png"), which
    /// are matched against "Content-Location" headers of MIME parts.</para>
    ///
    /// <para>This option enables an alternative method, where references to resource files are written as CID (Content-ID)
    /// URLs (for example, "cid:image.png") and are matched against "Content-ID" headers.</para>
    ///
    /// <para>In theory, there should be no difference between the two referencing methods and either of them should work
    /// fine in any browser or mail agent. In practice, however, some agents fail to fetch resources by file name. If your
    /// browser or mail agent refuses to load resources included in an MTHML document (doesn't show images or doesn't load
    /// CSS styles), try exporting the document with CID URLs.</para>
    /// </remarks>
    public boolean getExportCidUrlsForMhtmlResources() { return mExportCidUrlsForMhtmlResources; }
    public void setExportCidUrlsForMhtmlResources(boolean value) { mExportCidUrlsForMhtmlResources = value; }

    /// <summary>
    /// Specifies whether font family names used in the document are resolved and substituted according to
    /// <see cref="Document.FontSettings"/> when being written into HTML-based formats.
    /// </summary>
    /// <remarks>
    /// <para>By default, this option is set to <c>false</c> and font family names are written to HTML as specified
    /// in source documents. That is, <see cref="Document.FontSettings"/> are ignored and no resolution or substitution
    /// of font family names is performed.</para>
    ///
    /// <para>If this option is set to <c>true</c>, Aspose.Words uses <see cref="Document.FontSettings"/> to resolve
    /// each font family name specified in a source document into the name of an available font family, performing
    /// font substitution as required.</para>
    /// </remarks>
    public boolean getResolveFontNames() { return mResolveFontNames; }; public void setResolveFontNames(boolean value) { mResolveFontNames = value; };

    private boolean mResolveFontNames;

    /// <summary>
    /// Specifies whether backslash characters should be replaced with yen signs.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// By default, Aspose.Words mimics MS Word's behavior and doesn't replace backslash characters with yen signs in
    /// generated HTML documents. However, previous versions of Aspose.Words performed such replacements in certain
    /// scenarios. This flag enables backward compatibility with previous versions of Aspose.Words.
    /// </remarks>
    public boolean getReplaceBackslashWithYenSign() { return mReplaceBackslashWithYenSign; }; public void setReplaceBackslashWithYenSign(boolean value) { mReplaceBackslashWithYenSign = value; };

    private boolean mReplaceBackslashWithYenSign;

    /// <summary>
    /// Specifies whether JavaScript will be removed from links.
    /// Default is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// If this option is enabled, all links containing JavaScript (e.g., links with "javascript:" in the href attribute)
    /// will be replaced with "javascript:void(0)". This can help prevent potential security risks, such as XSS attacks.
    /// </remarks>
    public boolean getRemoveJavaScriptFromLinks() { return mRemoveJavaScriptFromLinks; }; public void setRemoveJavaScriptFromLinks(boolean value) { mRemoveJavaScriptFromLinks = value; };

    private boolean mRemoveJavaScriptFromLinks;

    /// <summary>
    /// Property for internal purposes.
    /// Set to export HTML in compliance with EPUB standard. May affect many changes that I hope to minimize in the future.
    /// <see cref="ExportBookmarkIdInsteadOfName"/> should be separate because it corresponds to an isolated feature.
    /// Default is <c>false</c>.
    /// </summary>
    boolean getEnforceEpubCompliance() { return mEnforceEpubCompliance; }
    void setEnforceEpubCompliance(boolean value) { mEnforceEpubCompliance = value; }

    /// <summary>
    /// Property for internal purposes.
    /// Specifies whether we output <b>id</b> with same value as <b>name</b> in bookmarks.
    /// </summary>
    /// <remarks>
    /// On import we accept <b>id</b> if <b>name</b> is absent (<b>name</b> commonly represents MS Word bookmarks
    /// and it is imported.)
    /// </remarks>
    boolean getExportBookmarkIdInsteadOfName()
    {
        // WORDSNET-26108 Both MOBI and AZW3 writers post-process bookmarks (anchors) in generated HTML and they expect
        // all bookmarks to have "name" attributes (don't support "id" attributes).
        if ((getSaveFormat() == SaveFormat.MOBI) || (getSaveFormat() == SaveFormat.AZW_3))
        {
            return false;
        }

        // We save EPUB with "id" attributes for compatibily with EPUB readers.
        // For example, navigation in Adobe Digital Editions works with "id" attributes only.
        if (getSaveFormat() == SaveFormat.EPUB)
        {
            return true;
        }

        // In HTML 5, the "name" attribute is deprecated and "id" must be used instead.
        return isHtml5();
    }

    /// <summary>
    /// Property for internal purposes.
    /// Specifies whether we output generic font families in font-family CSS attribute.
    /// Needed for EPUB export. Otherwise ADE doesn't distinguish fonts while rendering. WORDSNET-9515.
    /// Default is <c>false</c>.
    /// </summary>
    boolean getExportGenericFontFamilies() { return mExportGenericFontFamilies; }
    void setExportGenericFontFamilies(boolean value) { mExportGenericFontFamilies = value; }

    /// <summary>
    /// When <c>false</c>, causes no timestamps to be output into produced MHTML or EPUB files.
    /// Used when testing against gold files so don't have different output.
    /// Default is <c>true</c>.
    /// </summary>
    boolean getExportTimeStamps() { return mExportTimeStamps; }
    void setExportTimeStamps(boolean value) { mExportTimeStamps = value; }

    /// <summary>
    /// Property for internal purposes. Used for MHTML only.
    /// Needed to output in form acceptable for Aspose.Network.
    /// I added this to avoid removing code that will be needed later.
    /// Default is <c>true</c>.
    /// </summary>
    boolean getMhtmlEmailCompatible() { return mMhtmlEmailCompatible; }
    void setMhtmlEmailCompatible(boolean value) { mMhtmlEmailCompatible = value; }

    /// <summary>
    /// Property for internal purposes.
    /// Specifies the media type to output for EPUB export.
    /// (Is there any reason to make it public?)
    /// Default is <c>text/html</c>.
    /// </summary>
    String getMediaType() { return mMediaType; }
    void setMediaType(String value) { mMediaType = value; }

    /// <summary>
    /// Indicates whether the output HTML should conform to HTML5 standard.
    /// See also <see cref="HtmlVersion"/>.
    /// </summary>
    boolean isHtml5() { return getWebStandard() == WebStandard.HTML_5; }

    private void setSaveFormatCore(/*SaveFormat*/int saveFormat)
    {
        switch (saveFormat)
        {
            case SaveFormat.HTML:
            case SaveFormat.MHTML:
            case SaveFormat.EPUB:
            case SaveFormat.AZW_3:
            case SaveFormat.MOBI:
                mSaveFormat = saveFormat;
                break;
            default:
                throw new IllegalArgumentException("An invalid SaveFormat for this options type was chosen.");
        }
    }

    SaveImageOptions getSaveImageOptions()
    {
        mSaveImageOptions.UseAntiAliasing = getUseAntiAliasing();
        return mSaveImageOptions;
    }

    /// <summary>
    /// Specifies whether OfficeMath objects should be converted to Shape objects during validation.
    /// </summary>
    boolean isConvertOfficeMathToShape() { return mOfficeMathOutputMode == HtmlOfficeMathOutputMode.IMAGE; }

    /// <summary>
    /// Gets the Web standard in which the document should be saved.
    /// Extends <see cref="SaveFormat"/> property for markup languages formats (HTML, EPUB and MHTML).
    /// </summary>
    /*WebStandard*/int getWebStandard()
    {
        /*WebStandard*/int webStandard = WebStandard.XHTML_TRANSITIONAL_QUIRKS;
        switch (getSaveFormat())
        {
            case SaveFormat.HTML:
            case SaveFormat.MHTML:
            case SaveFormat.AZW_3:
            case SaveFormat.MOBI:
            {
                switch (mHtmlVersion)
                {
                    case HtmlVersion.XHTML:
                    {
                        webStandard = mExportXhtmlTransitional
                            ? WebStandard.XHTML_TRANSITIONAL
                            : WebStandard.XHTML_TRANSITIONAL_QUIRKS;
                        break;
                    }
                    case HtmlVersion.HTML_5:
                    {
                        webStandard = WebStandard.HTML_5;
                        break;
                    }
                    default:
                    {
                        Debug.assertTrue(false);
                        break;
                    }
                }
                break;
            }
            case SaveFormat.EPUB:
            {
                webStandard = WebStandard.EPUB;
                break;
            }
            default:
            {
                Debug.assertTrue(false);
                break;
            }
        }

        return webStandard;
    }

    private /*final*/ SaveImageOptions mSaveImageOptions = new SaveImageOptions();

    private /*SaveFormat*/int mSaveFormat;
    private boolean mExportTimeStamps = true;

    // HTML export options
    private boolean mExportLanguageInformation = false;
    private String mResourceFolder = "";
    private String mResourceFolderAlias = "";
    private String mImagesFolder = "";
    private String mImagesFolderAlias = "";
    private String mCssStyleSheetFileName = "";
    private ICssSavingCallback mCssSavingCallback;
    private boolean mExportTextInputFormFieldAsText = false;
    private boolean mExportDropDownFormFieldAsText = false;
    private /*ExportHeadersFootersMode*/int mExportHeadersFootersMode = ExportHeadersFootersMode.PER_SECTION;
    private boolean mAllowNegativeIndent = false;
    private boolean mExportDocumentProperties = false;
    private boolean mExportRoundtripInformation;
    private boolean mExportOriginalUrlForLinkedImages = false;
    private boolean mExportCidUrlsForMhtmlResources = false;

    private /*CssStyleSheetType*/int mCssStyleSheetType = CssStyleSheetType.INLINE;
    private /*HtmlElementSizeOutputMode*/int mTableWidthOutputMode = HtmlElementSizeOutputMode.ALL;
    private /*HtmlOfficeMathOutputMode*/int mOfficeMathOutputMode = HtmlOfficeMathOutputMode.IMAGE;
    private boolean mExportPageSetup = false;

    /// <summary>
    /// Default value is UTF-8 without BOM.
    /// </summary>
    private Encoding mEncoding = new UTF8Encoding(false);

    private boolean mExportXhtmlTransitional;
    private /*HtmlVersion*/int mHtmlVersion = HtmlVersion.XHTML;
    private boolean mExportRelativeFontSize = false;
    private boolean mExportFontResources = false;
    private int mFontResourcesSubsettingSizeThreshold = 0;
    private String mFontsFolder = "";
    private String mFontsFolderAlias = "";
    private /*DocumentSplitCriteria*/int mDocumentSplitCriteria = DocumentSplitCriteria.NONE;
    private int mDocumentSplitHeadingLevel = 2;
    private /*ExportListLabels*/int mExportListLabels = ExportListLabels.AUTO;

    private IDocumentPartSavingCallback mDocumentPartSavingCallback;
    private boolean mExportTocPageNumbers;

    // Other possible options to add:
    // Quality of JPEG files exported.
    // Whether to export BMP, JPEG and PNG images in their original formats or convert them all to one format.

    // MHTML export options
    private boolean mMhtmlEmailCompatible = true;

    // EPUB export options
    private int mNavigationMapLevel = 3;

    private String mMediaType = "text/html";
    private boolean mExportGenericFontFamilies = false;
    private boolean mEnforceEpubCompliance = false;
    private boolean mExportPageMargins = false;
    private boolean mExportFontsAsBase64 = false;
    private String mCssClassNamesPrefix = "";

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

