// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/04/2012 by Andrey Soldatov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// <para>File format enum. It can contain file formats used in any context.</para>
/// <para>The <c>enum</c> is used as intermediate format in <see cref="FileFormatCore"/> and <c>FileFormatUtil</c>.</para>
/// </summary>
/// <remarks>
/// This enum is center point to conversions between:
/// - File Extension : string,
/// - Content Type : string,
/// - LoadFormat,
/// - SaveFormat,
/// - ImageType.
///
/// Although LoadFormat, SaveFormat and ImageType are parts of AW, this enum is completely standalone.
/// By design, it can contain any file formats used in any context. That's why it's in Aspose.Foundation area.
///
/// In Aspose.Foundation it can be used for conversions between File Extension and Content Type.
/// </remarks>
public /*enum*/ final class FileFormat
{
    private FileFormat(){}
    
    /// <summary>
    /// An unknown file type.
    /// </summary>
    public static final int UNKNOWN = 0;

    public static final int XML = 1;

    /// <summary>
    /// Windows Enhanced Metafile.
    /// </summary>
    public static final int EMF = 2;

    /// <summary>
    /// Windows Metafile.
    /// </summary>
    public static final int WMF = 3;

    /// <summary>
    /// Macintosh PICT.
    /// </summary>
    public static final int PICT = 4;

    /// <summary>
    /// JPEG JFIF.
    /// </summary>
    public static final int JPEG = 5;

    /// <summary>
    /// Portable Network Graphics.
    /// </summary>
    public static final int PNG = 6;

    /// <summary>
    /// Windows Bitmap.
    /// </summary>
    public static final int BMP = 7;

    /// <summary>
    /// Tiff.
    /// </summary>
    public static final int TIFF = 8;

    /// <summary>
    /// Gif.
    /// </summary>
    public static final int GIF = 9;

    /// <summary>
    /// Encapsulated PostScript.
    /// </summary>
    public static final int EPS = 10;

    /// <summary>
    /// WebP.
    /// </summary>
    public static final int WEB_P = 11;

    public static final int DOC_PRE_WORD_97 = 12;
    public static final int DOC = 13;
    public static final int DOT = 14;
    public static final int DOCX = 15;
    public static final int DOCM = 16;
    public static final int DOTX = 17;
    public static final int DOTM = 18;
    public static final int WORD_ML = 19;

    public static final int FLAT_OPC = 20;
    public static final int FLAT_OPC_MACRO_ENABLED = 21;
    public static final int FLAT_OPC_TEMPLATE = 22;
    public static final int FLAT_OPC_TEMPLATE_MACRO_ENABLED = 23;

    public static final int ODT = 24;
    public static final int OTT = 25;

    public static final int RTF = 26;
    public static final int HTML = 27;
    public static final int HTML_FIXED = 28;
    public static final int MHTML = 29;
    public static final int MOBI = 30;
    public static final int CHM = 31;
    public static final int EPUB = 32;
    public static final int AZW_3 = 33;

    public static final int TXT = 34;
    public static final int MARKDOWN = 35;

    public static final int PDF = 36;
    public static final int XPS = 37;
    public static final int OPEN_XPS = 38;
    public static final int SVG = 39;
    public static final int XAML_FIXED = 40;
    public static final int XAML_FLOW = 41;
    public static final int XAML_FLOW_PACK = 42;
    public static final int PS = 43;
    public static final int PCL = 44;

    public static final int MOV = 45;
    public static final int ICO = 46;

    /// <summary>
    /// Embedded font.
    /// </summary>
    public static final int ODTTF = 47;

    /// <summary>
    /// Spreadsheet Open Office XML format.
    /// </summary>
    public static final int XLSX = 48;

    /// <summary>
    /// Microsoft Works format.
    /// </summary>
    public static final int MS_WORKS = 49;

    /// <summary>
    /// Docling JSON format.
    /// </summary>
    public static final int DOCLING = 50;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FileFormat value.
    /// </summary>
    public static String getName(/*FileFormat*/int fileFormat)
    {
    	switch (fileFormat)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case XML: return "XML";
    		case EMF: return "EMF";
    		case WMF: return "WMF";
    		case PICT: return "PICT";
    		case JPEG: return "JPEG";
    		case PNG: return "PNG";
    		case BMP: return "BMP";
    		case TIFF: return "TIFF";
    		case GIF: return "GIF";
    		case EPS: return "EPS";
    		case WEB_P: return "WEB_P";
    		case DOC_PRE_WORD_97: return "DOC_PRE_WORD_97";
    		case DOC: return "DOC";
    		case DOT: return "DOT";
    		case DOCX: return "DOCX";
    		case DOCM: return "DOCM";
    		case DOTX: return "DOTX";
    		case DOTM: return "DOTM";
    		case WORD_ML: return "WORD_ML";
    		case FLAT_OPC: return "FLAT_OPC";
    		case FLAT_OPC_MACRO_ENABLED: return "FLAT_OPC_MACRO_ENABLED";
    		case FLAT_OPC_TEMPLATE: return "FLAT_OPC_TEMPLATE";
    		case FLAT_OPC_TEMPLATE_MACRO_ENABLED: return "FLAT_OPC_TEMPLATE_MACRO_ENABLED";
    		case ODT: return "ODT";
    		case OTT: return "OTT";
    		case RTF: return "RTF";
    		case HTML: return "HTML";
    		case HTML_FIXED: return "HTML_FIXED";
    		case MHTML: return "MHTML";
    		case MOBI: return "MOBI";
    		case CHM: return "CHM";
    		case EPUB: return "EPUB";
    		case AZW_3: return "AZW_3";
    		case TXT: return "TXT";
    		case MARKDOWN: return "MARKDOWN";
    		case PDF: return "PDF";
    		case XPS: return "XPS";
    		case OPEN_XPS: return "OPEN_XPS";
    		case SVG: return "SVG";
    		case XAML_FIXED: return "XAML_FIXED";
    		case XAML_FLOW: return "XAML_FLOW";
    		case XAML_FLOW_PACK: return "XAML_FLOW_PACK";
    		case PS: return "PS";
    		case PCL: return "PCL";
    		case MOV: return "MOV";
    		case ICO: return "ICO";
    		case ODTTF: return "ODTTF";
    		case XLSX: return "XLSX";
    		case MS_WORKS: return "MS_WORKS";
    		case DOCLING: return "DOCLING";
    		default: return "Unknown FileFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FileFormat value.
    /// </summary>
    public static String toString(/*FileFormat*/int fileFormat)
    {
    	switch (fileFormat)
    	{
    		case UNKNOWN: return "Unknown";
    		case XML: return "Xml";
    		case EMF: return "Emf";
    		case WMF: return "Wmf";
    		case PICT: return "Pict";
    		case JPEG: return "Jpeg";
    		case PNG: return "Png";
    		case BMP: return "Bmp";
    		case TIFF: return "Tiff";
    		case GIF: return "Gif";
    		case EPS: return "Eps";
    		case WEB_P: return "WebP";
    		case DOC_PRE_WORD_97: return "DocPreWord97";
    		case DOC: return "Doc";
    		case DOT: return "Dot";
    		case DOCX: return "Docx";
    		case DOCM: return "Docm";
    		case DOTX: return "Dotx";
    		case DOTM: return "Dotm";
    		case WORD_ML: return "WordML";
    		case FLAT_OPC: return "FlatOpc";
    		case FLAT_OPC_MACRO_ENABLED: return "FlatOpcMacroEnabled";
    		case FLAT_OPC_TEMPLATE: return "FlatOpcTemplate";
    		case FLAT_OPC_TEMPLATE_MACRO_ENABLED: return "FlatOpcTemplateMacroEnabled";
    		case ODT: return "Odt";
    		case OTT: return "Ott";
    		case RTF: return "Rtf";
    		case HTML: return "Html";
    		case HTML_FIXED: return "HtmlFixed";
    		case MHTML: return "Mhtml";
    		case MOBI: return "Mobi";
    		case CHM: return "Chm";
    		case EPUB: return "Epub";
    		case AZW_3: return "Azw3";
    		case TXT: return "Txt";
    		case MARKDOWN: return "Markdown";
    		case PDF: return "Pdf";
    		case XPS: return "Xps";
    		case OPEN_XPS: return "OpenXps";
    		case SVG: return "Svg";
    		case XAML_FIXED: return "XamlFixed";
    		case XAML_FLOW: return "XamlFlow";
    		case XAML_FLOW_PACK: return "XamlFlowPack";
    		case PS: return "Ps";
    		case PCL: return "Pcl";
    		case MOV: return "Mov";
    		case ICO: return "Ico";
    		case ODTTF: return "Odttf";
    		case XLSX: return "Xlsx";
    		case MS_WORKS: return "MsWorks";
    		case DOCLING: return "Docling";
    		default: return "Unknown FileFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FileFormat name.
    /// </summary>
    public static /*FileFormat*/int fromName(String fileFormatName)
    {
    	if ("UNKNOWN".equals(fileFormatName)) return UNKNOWN;
    	if ("XML".equals(fileFormatName)) return XML;
    	if ("EMF".equals(fileFormatName)) return EMF;
    	if ("WMF".equals(fileFormatName)) return WMF;
    	if ("PICT".equals(fileFormatName)) return PICT;
    	if ("JPEG".equals(fileFormatName)) return JPEG;
    	if ("PNG".equals(fileFormatName)) return PNG;
    	if ("BMP".equals(fileFormatName)) return BMP;
    	if ("TIFF".equals(fileFormatName)) return TIFF;
    	if ("GIF".equals(fileFormatName)) return GIF;
    	if ("EPS".equals(fileFormatName)) return EPS;
    	if ("WEB_P".equals(fileFormatName)) return WEB_P;
    	if ("DOC_PRE_WORD_97".equals(fileFormatName)) return DOC_PRE_WORD_97;
    	if ("DOC".equals(fileFormatName)) return DOC;
    	if ("DOT".equals(fileFormatName)) return DOT;
    	if ("DOCX".equals(fileFormatName)) return DOCX;
    	if ("DOCM".equals(fileFormatName)) return DOCM;
    	if ("DOTX".equals(fileFormatName)) return DOTX;
    	if ("DOTM".equals(fileFormatName)) return DOTM;
    	if ("WORD_ML".equals(fileFormatName)) return WORD_ML;
    	if ("FLAT_OPC".equals(fileFormatName)) return FLAT_OPC;
    	if ("FLAT_OPC_MACRO_ENABLED".equals(fileFormatName)) return FLAT_OPC_MACRO_ENABLED;
    	if ("FLAT_OPC_TEMPLATE".equals(fileFormatName)) return FLAT_OPC_TEMPLATE;
    	if ("FLAT_OPC_TEMPLATE_MACRO_ENABLED".equals(fileFormatName)) return FLAT_OPC_TEMPLATE_MACRO_ENABLED;
    	if ("ODT".equals(fileFormatName)) return ODT;
    	if ("OTT".equals(fileFormatName)) return OTT;
    	if ("RTF".equals(fileFormatName)) return RTF;
    	if ("HTML".equals(fileFormatName)) return HTML;
    	if ("HTML_FIXED".equals(fileFormatName)) return HTML_FIXED;
    	if ("MHTML".equals(fileFormatName)) return MHTML;
    	if ("MOBI".equals(fileFormatName)) return MOBI;
    	if ("CHM".equals(fileFormatName)) return CHM;
    	if ("EPUB".equals(fileFormatName)) return EPUB;
    	if ("AZW_3".equals(fileFormatName)) return AZW_3;
    	if ("TXT".equals(fileFormatName)) return TXT;
    	if ("MARKDOWN".equals(fileFormatName)) return MARKDOWN;
    	if ("PDF".equals(fileFormatName)) return PDF;
    	if ("XPS".equals(fileFormatName)) return XPS;
    	if ("OPEN_XPS".equals(fileFormatName)) return OPEN_XPS;
    	if ("SVG".equals(fileFormatName)) return SVG;
    	if ("XAML_FIXED".equals(fileFormatName)) return XAML_FIXED;
    	if ("XAML_FLOW".equals(fileFormatName)) return XAML_FLOW;
    	if ("XAML_FLOW_PACK".equals(fileFormatName)) return XAML_FLOW_PACK;
    	if ("PS".equals(fileFormatName)) return PS;
    	if ("PCL".equals(fileFormatName)) return PCL;
    	if ("MOV".equals(fileFormatName)) return MOV;
    	if ("ICO".equals(fileFormatName)) return ICO;
    	if ("ODTTF".equals(fileFormatName)) return ODTTF;
    	if ("XLSX".equals(fileFormatName)) return XLSX;
    	if ("MS_WORKS".equals(fileFormatName)) return MS_WORKS;
    	if ("DOCLING".equals(fileFormatName)) return DOCLING;
    	throw new IllegalArgumentException("Unknown FileFormat name.");
    }
    
    /// <summary>
    /// Returns array of FileFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		XML,
    		EMF,
    		WMF,
    		PICT,
    		JPEG,
    		PNG,
    		BMP,
    		TIFF,
    		GIF,
    		EPS,
    		WEB_P,
    		DOC_PRE_WORD_97,
    		DOC,
    		DOT,
    		DOCX,
    		DOCM,
    		DOTX,
    		DOTM,
    		WORD_ML,
    		FLAT_OPC,
    		FLAT_OPC_MACRO_ENABLED,
    		FLAT_OPC_TEMPLATE,
    		FLAT_OPC_TEMPLATE_MACRO_ENABLED,
    		ODT,
    		OTT,
    		RTF,
    		HTML,
    		HTML_FIXED,
    		MHTML,
    		MOBI,
    		CHM,
    		EPUB,
    		AZW_3,
    		TXT,
    		MARKDOWN,
    		PDF,
    		XPS,
    		OPEN_XPS,
    		SVG,
    		XAML_FIXED,
    		XAML_FLOW,
    		XAML_FLOW_PACK,
    		PS,
    		PCL,
    		MOV,
    		ICO,
    		ODTTF,
    		XLSX,
    		MS_WORKS,
    		DOCLING,
    	};
    }

    public static final int length = 51;
}

