// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Indicates the format in which the document is saved.
/// </summary>
/// <seealso cref="Document.Save(Stream, SaveFormat)"/>
/// <seealso cref="SaveOptions"/>
/// <dev>
/// We should only use values 0..255 here because these values are used as bit flags in the UnifiedScenario enum.
/// </dev>
public /*enum*/ final class SaveFormat
{
    private SaveFormat(){}
    
    /// <summary>
    /// Default, invalid value for file format.
    /// </summary>
    public static final int UNKNOWN = 0;

    /// <summary>
    /// Saves the document in the Microsoft Word 97 - 2007 Document format.
    /// </summary>
    public static final int DOC = 10;
    /// <summary>
    /// Saves the document in the Microsoft Word 97 - 2007 Template format.
    /// </summary>
    public static final int DOT = 11;

    /// <summary>
    /// Saves the document as an Office Open XML WordprocessingML Document (macro-free).
    /// </summary>
    public static final int DOCX = 20;
    /// <summary>
    /// Saves the document as an Office Open XML WordprocessingML Macro-Enabled Document.
    /// </summary>
    public static final int DOCM = 21;
    /// <summary>
    /// Saves the document as an Office Open XML WordprocessingML Template (macro-free).
    /// </summary>
    public static final int DOTX = 22;
    /// <summary>
    /// Saves the document as an Office Open XML WordprocessingML Macro-Enabled Template.
    /// </summary>
    public static final int DOTM = 23;
    /// <summary>
    /// Saves the document as an Office Open XML WordprocessingML stored in a flat XML file instead of a ZIP package.
    /// </summary>
    public static final int FLAT_OPC = 24;
    /// <summary>
    /// Saves the document as an Office Open XML WordprocessingML Macro-Enabled Document stored in a flat XML file instead of a ZIP package.
    /// </summary>
    public static final int FLAT_OPC_MACRO_ENABLED = 25;
    /// <summary>
    /// Saves the document as an Office Open XML WordprocessingML Template (macro-free) stored in a flat XML file instead of a ZIP package.
    /// </summary>
    public static final int FLAT_OPC_TEMPLATE = 26;
    /// <summary>
    /// Saves the document as an Office Open XML WordprocessingML Macro-Enabled Template stored in a flat XML file instead of a ZIP package.
    /// </summary>
    public static final int FLAT_OPC_TEMPLATE_MACRO_ENABLED = 27;
    /// <summary>
    /// Saves the document in the RTF format. 
    /// All characters above 7-bits are escaped as hexadecimal or Unicode characters.
    /// </summary>
    public static final int RTF = 30;
    /// <summary>
    /// Saves the document in the Microsoft Word 2003 WordprocessingML format.
    /// </summary>
    public static final int WORD_ML = 31;

    /// <summary>
    /// Saves the document as PDF (Adobe Portable Document) format.
    /// </summary>
    public static final int PDF = 40;
    /// <summary>
    /// Saves the document in the XPS (XML Paper Specification) format.
    /// </summary>
    public static final int XPS = 41;
    /// <summary>
    /// Saves the document in the Extensible Application Markup Language (XAML) format as a fixed document.
    /// </summary>
    public static final int XAML_FIXED = 42;
    /// <summary>
    /// Saves the document in the Svg (Scalable Vector Graphics) format.
    /// </summary>
    public static final int SVG = 44;
    /// <summary>
    /// Saves the document in the HTML format using absolutely positioned elements
    /// </summary>
    public static final int HTML_FIXED = 45;
    /// <summary>
    /// Saves the document in the OpenXPS (Ecma-388) format.
    /// </summary>
    public static final int OPEN_XPS = 46;
    /// <summary>
    /// Saves the document in the PS (PostScript) format.
    /// </summary>
    public static final int PS = 47;
    /// <summary>
    /// Saves the document in the PCL (Printer Control Language) format.
    /// </summary>
    public static final int PCL = 48;
    /// <summary>
    /// Saves the document in the HTML format.
    /// </summary>
    public static final int HTML = 50;
    /// <summary>
    /// Saves the document in the MHTML (Web archive) format.
    /// </summary>
    public static final int MHTML = 51;
    /// <summary>
    /// Saves the document in the EPUB format.
    /// </summary>
    public static final int EPUB = 52;
    /// <summary>
    /// Saves the document in the AZW3 format.
    /// </summary>
    public static final int AZW_3 = 53;
    /// <summary>
    /// Saves the document in the MOBI format.
    /// </summary>
    public static final int MOBI = 54;

    /// <summary>
    /// Saves the document as an ODF Text Document.
    /// </summary>
    public static final int ODT = 60;
    /// <summary>
    /// Saves the document as an ODF Text Document Template.
    /// </summary>
    public static final int OTT = 61;

    /// <summary>
    /// Saves the document in the plain text format.
    /// </summary>
    public static final int TEXT = 70;
    /// <summary>
    /// <b>Beta.</b> Saves the document in the Extensible Application Markup Language (XAML) format as a flow document.
    /// </summary>
    public static final int XAML_FLOW = 71;
    /// <summary>
    /// <b>Beta.</b> Saves the document in the Extensible Application Markup Language (XAML) package format as a flow document.
    /// </summary>
    public static final int XAML_FLOW_PACK = 72;
    /// <summary>
    /// Saves the document in the Markdown format.
    /// </summary>
    public static final int MARKDOWN = 73;

    /// <summary>
    /// Saves the document as an Office Open XML SpreadsheetML Document (macro-free).
    /// </summary>
    public static final int XLSX = 80;

    /// <summary>
    /// Saves the document in Docling JSON format.
    /// </summary>
    public static final int DOCLING = 81;

    /// <summary>
    /// Renders a page or pages of the document and saves them into a single or multipage TIFF file.
    /// </summary>
    public static final int TIFF = 100;
    /// <summary>
    /// Renders a page of the document and saves it as a PNG file.
    /// </summary>
    public static final int PNG = 101;
    /// <summary>
    /// Renders a page of the document and saves it as a BMP file.
    /// </summary>
    public static final int BMP = 102;
    /// <summary>
    /// Renders a page of the document and saves it as a vector EMF (Enhanced Meta File) file.
    /// </summary>
    public static final int EMF = 103;
    /// <summary>
    /// Renders a page of the document and saves it as a JPEG file.
    /// </summary>
    public static final int JPEG = 104;
    /// <summary>
    /// Renders a page of the document and saves it as a GIF file.
    /// </summary>
    public static final int GIF = 105;
    /// <summary>
    /// Renders a page of the document and saves it as an EPS file.
    /// </summary>
    public static final int EPS = 106;
    /// <summary>
    /// Renders a page of the document and saves it as a WebP file.
    /// </summary>
    public static final int WEB_P = 107;

    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SaveFormat value.
    /// </summary>
    public static String getName(/*SaveFormat*/int saveFormat)
    {
    	switch (saveFormat)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case DOC: return "DOC";
    		case DOT: return "DOT";
    		case DOCX: return "DOCX";
    		case DOCM: return "DOCM";
    		case DOTX: return "DOTX";
    		case DOTM: return "DOTM";
    		case FLAT_OPC: return "FLAT_OPC";
    		case FLAT_OPC_MACRO_ENABLED: return "FLAT_OPC_MACRO_ENABLED";
    		case FLAT_OPC_TEMPLATE: return "FLAT_OPC_TEMPLATE";
    		case FLAT_OPC_TEMPLATE_MACRO_ENABLED: return "FLAT_OPC_TEMPLATE_MACRO_ENABLED";
    		case RTF: return "RTF";
    		case WORD_ML: return "WORD_ML";
    		case PDF: return "PDF";
    		case XPS: return "XPS";
    		case XAML_FIXED: return "XAML_FIXED";
    		case SVG: return "SVG";
    		case HTML_FIXED: return "HTML_FIXED";
    		case OPEN_XPS: return "OPEN_XPS";
    		case PS: return "PS";
    		case PCL: return "PCL";
    		case HTML: return "HTML";
    		case MHTML: return "MHTML";
    		case EPUB: return "EPUB";
    		case AZW_3: return "AZW_3";
    		case MOBI: return "MOBI";
    		case ODT: return "ODT";
    		case OTT: return "OTT";
    		case TEXT: return "TEXT";
    		case XAML_FLOW: return "XAML_FLOW";
    		case XAML_FLOW_PACK: return "XAML_FLOW_PACK";
    		case MARKDOWN: return "MARKDOWN";
    		case XLSX: return "XLSX";
    		case DOCLING: return "DOCLING";
    		case TIFF: return "TIFF";
    		case PNG: return "PNG";
    		case BMP: return "BMP";
    		case EMF: return "EMF";
    		case JPEG: return "JPEG";
    		case GIF: return "GIF";
    		case EPS: return "EPS";
    		case WEB_P: return "WEB_P";
    		default: return "Unknown SaveFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SaveFormat value.
    /// </summary>
    public static String toString(/*SaveFormat*/int saveFormat)
    {
    	switch (saveFormat)
    	{
    		case UNKNOWN: return "Unknown";
    		case DOC: return "Doc";
    		case DOT: return "Dot";
    		case DOCX: return "Docx";
    		case DOCM: return "Docm";
    		case DOTX: return "Dotx";
    		case DOTM: return "Dotm";
    		case FLAT_OPC: return "FlatOpc";
    		case FLAT_OPC_MACRO_ENABLED: return "FlatOpcMacroEnabled";
    		case FLAT_OPC_TEMPLATE: return "FlatOpcTemplate";
    		case FLAT_OPC_TEMPLATE_MACRO_ENABLED: return "FlatOpcTemplateMacroEnabled";
    		case RTF: return "Rtf";
    		case WORD_ML: return "WordML";
    		case PDF: return "Pdf";
    		case XPS: return "Xps";
    		case XAML_FIXED: return "XamlFixed";
    		case SVG: return "Svg";
    		case HTML_FIXED: return "HtmlFixed";
    		case OPEN_XPS: return "OpenXps";
    		case PS: return "Ps";
    		case PCL: return "Pcl";
    		case HTML: return "Html";
    		case MHTML: return "Mhtml";
    		case EPUB: return "Epub";
    		case AZW_3: return "Azw3";
    		case MOBI: return "Mobi";
    		case ODT: return "Odt";
    		case OTT: return "Ott";
    		case TEXT: return "Text";
    		case XAML_FLOW: return "XamlFlow";
    		case XAML_FLOW_PACK: return "XamlFlowPack";
    		case MARKDOWN: return "Markdown";
    		case XLSX: return "Xlsx";
    		case DOCLING: return "Docling";
    		case TIFF: return "Tiff";
    		case PNG: return "Png";
    		case BMP: return "Bmp";
    		case EMF: return "Emf";
    		case JPEG: return "Jpeg";
    		case GIF: return "Gif";
    		case EPS: return "Eps";
    		case WEB_P: return "WebP";
    		default: return "Unknown SaveFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SaveFormat name.
    /// </summary>
    public static /*SaveFormat*/int fromName(String saveFormatName)
    {
    	if ("UNKNOWN".equals(saveFormatName)) return UNKNOWN;
    	if ("DOC".equals(saveFormatName)) return DOC;
    	if ("DOT".equals(saveFormatName)) return DOT;
    	if ("DOCX".equals(saveFormatName)) return DOCX;
    	if ("DOCM".equals(saveFormatName)) return DOCM;
    	if ("DOTX".equals(saveFormatName)) return DOTX;
    	if ("DOTM".equals(saveFormatName)) return DOTM;
    	if ("FLAT_OPC".equals(saveFormatName)) return FLAT_OPC;
    	if ("FLAT_OPC_MACRO_ENABLED".equals(saveFormatName)) return FLAT_OPC_MACRO_ENABLED;
    	if ("FLAT_OPC_TEMPLATE".equals(saveFormatName)) return FLAT_OPC_TEMPLATE;
    	if ("FLAT_OPC_TEMPLATE_MACRO_ENABLED".equals(saveFormatName)) return FLAT_OPC_TEMPLATE_MACRO_ENABLED;
    	if ("RTF".equals(saveFormatName)) return RTF;
    	if ("WORD_ML".equals(saveFormatName)) return WORD_ML;
    	if ("PDF".equals(saveFormatName)) return PDF;
    	if ("XPS".equals(saveFormatName)) return XPS;
    	if ("XAML_FIXED".equals(saveFormatName)) return XAML_FIXED;
    	if ("SVG".equals(saveFormatName)) return SVG;
    	if ("HTML_FIXED".equals(saveFormatName)) return HTML_FIXED;
    	if ("OPEN_XPS".equals(saveFormatName)) return OPEN_XPS;
    	if ("PS".equals(saveFormatName)) return PS;
    	if ("PCL".equals(saveFormatName)) return PCL;
    	if ("HTML".equals(saveFormatName)) return HTML;
    	if ("MHTML".equals(saveFormatName)) return MHTML;
    	if ("EPUB".equals(saveFormatName)) return EPUB;
    	if ("AZW_3".equals(saveFormatName)) return AZW_3;
    	if ("MOBI".equals(saveFormatName)) return MOBI;
    	if ("ODT".equals(saveFormatName)) return ODT;
    	if ("OTT".equals(saveFormatName)) return OTT;
    	if ("TEXT".equals(saveFormatName)) return TEXT;
    	if ("XAML_FLOW".equals(saveFormatName)) return XAML_FLOW;
    	if ("XAML_FLOW_PACK".equals(saveFormatName)) return XAML_FLOW_PACK;
    	if ("MARKDOWN".equals(saveFormatName)) return MARKDOWN;
    	if ("XLSX".equals(saveFormatName)) return XLSX;
    	if ("DOCLING".equals(saveFormatName)) return DOCLING;
    	if ("TIFF".equals(saveFormatName)) return TIFF;
    	if ("PNG".equals(saveFormatName)) return PNG;
    	if ("BMP".equals(saveFormatName)) return BMP;
    	if ("EMF".equals(saveFormatName)) return EMF;
    	if ("JPEG".equals(saveFormatName)) return JPEG;
    	if ("GIF".equals(saveFormatName)) return GIF;
    	if ("EPS".equals(saveFormatName)) return EPS;
    	if ("WEB_P".equals(saveFormatName)) return WEB_P;
    	throw new IllegalArgumentException("Unknown SaveFormat name.");
    }
    
    /// <summary>
    /// Returns array of SaveFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		DOC,
    		DOT,
    		DOCX,
    		DOCM,
    		DOTX,
    		DOTM,
    		FLAT_OPC,
    		FLAT_OPC_MACRO_ENABLED,
    		FLAT_OPC_TEMPLATE,
    		FLAT_OPC_TEMPLATE_MACRO_ENABLED,
    		RTF,
    		WORD_ML,
    		PDF,
    		XPS,
    		XAML_FIXED,
    		SVG,
    		HTML_FIXED,
    		OPEN_XPS,
    		PS,
    		PCL,
    		HTML,
    		MHTML,
    		EPUB,
    		AZW_3,
    		MOBI,
    		ODT,
    		OTT,
    		TEXT,
    		XAML_FLOW,
    		XAML_FLOW_PACK,
    		MARKDOWN,
    		XLSX,
    		DOCLING,
    		TIFF,
    		PNG,
    		BMP,
    		EMF,
    		JPEG,
    		GIF,
    		EPS,
    		WEB_P,
    	};
    }

    public static final int length = 42;
}

