// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/10/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the module that produces a warning during document loading or saving.
/// </summary>
public /*enum*/ final class WarningSource
{
    private WarningSource(){}
    
    /// <summary>
    /// The warning source is not specified.
    /// </summary>
    public static final int UNKNOWN = 0;

    /// <summary>
    /// Module that builds a document layout.
    /// </summary>
    public static final int LAYOUT = 1;

    /// <summary>
    /// Module that renders DrawingML shapes.
    /// </summary>
    public static final int DRAWING_ML = 2;

    /// <summary>
    /// Module that renders OfficeMath.
    /// </summary>
    public static final int OFFICE_MATH = 3;

    /// <summary>
    /// Module that renders ordinary shapes.
    /// </summary>
    public static final int SHAPES = 4;

    /// <summary>
    /// Module that renders metafiles.
    /// </summary>
    public static final int METAFILE = 5;

    /// <summary>
    /// Module that renders XPS.
    /// </summary>
    public static final int XPS = 6;

    /// <summary>
    /// Module that renders PDF.
    /// </summary>
    public static final int PDF = 7;

    /// <summary>
    /// Module that renders images.
    /// </summary>
    public static final int IMAGE = 8;

    /// <summary>
    /// Module that reads/writes DOCX files.
    /// </summary>
    public static final int DOCX = 9;

    /// <summary>
    /// Module that reads/writes binary DOC files.
    /// </summary>
    public static final int DOC = 10;

    /// <summary>
    /// Module that reads/writes plaintext files.
    /// </summary>
    public static final int TEXT = 11;

    /// <summary>
    /// Module that reads/writes RTF files.
    /// </summary>
    public static final int RTF = 12;

    /// <summary>
    /// Module that reads/writes WML files.
    /// </summary>
    public static final int WORD_ML = 13;

    /// <summary>
    /// Common modules that are shared between DOCX/WML reader/writer modules.
    /// </summary>
    public static final int NRX = 14;

    /// <summary>
    /// Module that reads/writes ODT files.
    /// </summary>
    public static final int ODT = 15;

    /// <summary>
    /// Module that reads/writes HTML/MHTML files.
    /// </summary>
    public static final int HTML = 16;

    /// <summary>
    /// Module that verifies model consistency and validity.
    /// </summary>
    public static final int VALIDATOR = 17;

    /// <summary>
    /// Module that reads/writes Xaml files.
    /// </summary>
    public static final int XAML = 18;

    /// <summary>
    /// Module that reads Svm files.
    /// </summary>
    public static final int SVM = 19;

    /// <summary>
    /// Module that reads W3C MathML files.
    /// </summary>
    public static final int MATH_ML = 20;

    /// <summary>
    /// Module that reads font files.
    /// </summary>
    public static final int FONT = 21;

    /// <summary>
    /// Module that reads SVG files.
    /// </summary>
    public static final int SVG = 22;

    /// <summary>
    /// Module that reads/writes Markdown files.
    /// </summary>
    public static final int MARKDOWN = 23;

    /// <summary>
    /// Module that reads CHM files.
    /// </summary>
    public static final int CHM = 24;

    /// <summary>
    /// Module that reads/writes EPUB files.
    /// </summary>
    public static final int EPUB = 25;

    /// <summary>
    /// Module that reads XML files.
    /// </summary>
    public static final int XML = 26;

    /// <summary>
    /// Module that writes XLSX files.
    /// </summary>
    public static final int XLSX = 27;

    /// <summary>
    /// Module that writes Docling JSON files.
    /// </summary>
    public static final int DOCLING = 28;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer WarningSource value.
    /// </summary>
    public static String getName(/*WarningSource*/int warningSource)
    {
    	switch (warningSource)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case LAYOUT: return "LAYOUT";
    		case DRAWING_ML: return "DRAWING_ML";
    		case OFFICE_MATH: return "OFFICE_MATH";
    		case SHAPES: return "SHAPES";
    		case METAFILE: return "METAFILE";
    		case XPS: return "XPS";
    		case PDF: return "PDF";
    		case IMAGE: return "IMAGE";
    		case DOCX: return "DOCX";
    		case DOC: return "DOC";
    		case TEXT: return "TEXT";
    		case RTF: return "RTF";
    		case WORD_ML: return "WORD_ML";
    		case NRX: return "NRX";
    		case ODT: return "ODT";
    		case HTML: return "HTML";
    		case VALIDATOR: return "VALIDATOR";
    		case XAML: return "XAML";
    		case SVM: return "SVM";
    		case MATH_ML: return "MATH_ML";
    		case FONT: return "FONT";
    		case SVG: return "SVG";
    		case MARKDOWN: return "MARKDOWN";
    		case CHM: return "CHM";
    		case EPUB: return "EPUB";
    		case XML: return "XML";
    		case XLSX: return "XLSX";
    		case DOCLING: return "DOCLING";
    		default: return "Unknown WarningSource value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WarningSource value.
    /// </summary>
    public static String toString(/*WarningSource*/int warningSource)
    {
    	switch (warningSource)
    	{
    		case UNKNOWN: return "Unknown";
    		case LAYOUT: return "Layout";
    		case DRAWING_ML: return "DrawingML";
    		case OFFICE_MATH: return "OfficeMath";
    		case SHAPES: return "Shapes";
    		case METAFILE: return "Metafile";
    		case XPS: return "Xps";
    		case PDF: return "Pdf";
    		case IMAGE: return "Image";
    		case DOCX: return "Docx";
    		case DOC: return "Doc";
    		case TEXT: return "Text";
    		case RTF: return "Rtf";
    		case WORD_ML: return "WordML";
    		case NRX: return "Nrx";
    		case ODT: return "Odt";
    		case HTML: return "Html";
    		case VALIDATOR: return "Validator";
    		case XAML: return "Xaml";
    		case SVM: return "Svm";
    		case MATH_ML: return "MathML";
    		case FONT: return "Font";
    		case SVG: return "Svg";
    		case MARKDOWN: return "Markdown";
    		case CHM: return "Chm";
    		case EPUB: return "Epub";
    		case XML: return "Xml";
    		case XLSX: return "Xlsx";
    		case DOCLING: return "Docling";
    		default: return "Unknown WarningSource value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by WarningSource name.
    /// </summary>
    public static /*WarningSource*/int fromName(String warningSourceName)
    {
    	if ("UNKNOWN".equals(warningSourceName)) return UNKNOWN;
    	if ("LAYOUT".equals(warningSourceName)) return LAYOUT;
    	if ("DRAWING_ML".equals(warningSourceName)) return DRAWING_ML;
    	if ("OFFICE_MATH".equals(warningSourceName)) return OFFICE_MATH;
    	if ("SHAPES".equals(warningSourceName)) return SHAPES;
    	if ("METAFILE".equals(warningSourceName)) return METAFILE;
    	if ("XPS".equals(warningSourceName)) return XPS;
    	if ("PDF".equals(warningSourceName)) return PDF;
    	if ("IMAGE".equals(warningSourceName)) return IMAGE;
    	if ("DOCX".equals(warningSourceName)) return DOCX;
    	if ("DOC".equals(warningSourceName)) return DOC;
    	if ("TEXT".equals(warningSourceName)) return TEXT;
    	if ("RTF".equals(warningSourceName)) return RTF;
    	if ("WORD_ML".equals(warningSourceName)) return WORD_ML;
    	if ("NRX".equals(warningSourceName)) return NRX;
    	if ("ODT".equals(warningSourceName)) return ODT;
    	if ("HTML".equals(warningSourceName)) return HTML;
    	if ("VALIDATOR".equals(warningSourceName)) return VALIDATOR;
    	if ("XAML".equals(warningSourceName)) return XAML;
    	if ("SVM".equals(warningSourceName)) return SVM;
    	if ("MATH_ML".equals(warningSourceName)) return MATH_ML;
    	if ("FONT".equals(warningSourceName)) return FONT;
    	if ("SVG".equals(warningSourceName)) return SVG;
    	if ("MARKDOWN".equals(warningSourceName)) return MARKDOWN;
    	if ("CHM".equals(warningSourceName)) return CHM;
    	if ("EPUB".equals(warningSourceName)) return EPUB;
    	if ("XML".equals(warningSourceName)) return XML;
    	if ("XLSX".equals(warningSourceName)) return XLSX;
    	if ("DOCLING".equals(warningSourceName)) return DOCLING;
    	throw new IllegalArgumentException("Unknown WarningSource name.");
    }
    
    /// <summary>
    /// Returns array of WarningSource values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		LAYOUT,
    		DRAWING_ML,
    		OFFICE_MATH,
    		SHAPES,
    		METAFILE,
    		XPS,
    		PDF,
    		IMAGE,
    		DOCX,
    		DOC,
    		TEXT,
    		RTF,
    		WORD_ML,
    		NRX,
    		ODT,
    		HTML,
    		VALIDATOR,
    		XAML,
    		SVM,
    		MATH_ML,
    		FONT,
    		SVG,
    		MARKDOWN,
    		CHM,
    		EPUB,
    		XML,
    		XLSX,
    		DOCLING,
    	};
    }

    public static final int length = 29;
}

