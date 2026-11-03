// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/03/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Indicates the format of the document that is to be loaded.
/// </summary>
/// <dev>
/// We should only use values 0..255 here because these values are used as bit flags in the UnifiedScenario enum.
/// </dev>
public /*enum*/ final class LoadFormat
{
    private LoadFormat(){}
    
    /// <summary>
    /// Instructs Aspose.Words to recognize the format automatically.
    /// </summary>
    public static final int AUTO = 0;

    /// <summary>
    /// Microsoft Works 8 Document.
    /// </summary>
    public static final int MS_WORKS = 8;

    /// <summary>
    /// Microsoft Word 95 or Word 97 - 2003 Document.
    /// </summary>
    public static final int DOC = 10;
    /// <summary>
    /// Microsoft Word 95 or Word 97 - 2003 Template.
    /// </summary>
    public static final int DOT = 11;
    /// <summary>
    /// The document is in pre-Word 95 format.
    /// Aspose.Words does not currently support loading such documents.
    /// </summary>
    public static final int DOC_PRE_WORD_60 = 12;

    /// <summary>
    /// Office Open XML WordprocessingML Document (macro-free).
    /// </summary>
    public static final int DOCX = 20;
    /// <summary>
    /// Office Open XML WordprocessingML Macro-Enabled Document.
    /// </summary>
    public static final int DOCM = 21;
    /// <summary>
    /// Office Open XML WordprocessingML Template (macro-free).
    /// </summary>
    public static final int DOTX = 22;
    /// <summary>
    /// Office Open XML WordprocessingML Macro-Enabled Template.
    /// </summary>
    public static final int DOTM = 23;
    /// <summary>
    /// Office Open XML WordprocessingML stored in a flat XML file instead of a ZIP package.
    /// </summary>
    public static final int FLAT_OPC = 24;
    /// <summary>
    /// Office Open XML WordprocessingML Macro-Enabled Document stored in a flat XML file instead of a ZIP package.
    /// </summary>
    public static final int FLAT_OPC_MACRO_ENABLED = 25;
    /// <summary>
    /// Office Open XML WordprocessingML Template (macro-free) stored in a flat XML file instead of a ZIP package.
    /// </summary>
    public static final int FLAT_OPC_TEMPLATE = 26;
    /// <summary>
    /// Office Open XML WordprocessingML Macro-Enabled Template stored in a flat XML file instead of a ZIP package.
    /// </summary>
    public static final int FLAT_OPC_TEMPLATE_MACRO_ENABLED = 27;
    /// <summary>
    /// RTF format.
    /// </summary>
    public static final int RTF = 30;
    /// <summary>
    /// Microsoft Word 2003 WordprocessingML format.
    /// </summary>
    public static final int WORD_ML = 31;

    /// <summary>
    /// HTML format.
    /// </summary>
    public static final int HTML = 50;
    /// <summary>
    /// MHTML (Web archive) format.
    /// </summary>
    public static final int MHTML = 51;
    /// <summary>
    /// MOBI format. Used by MobiPocket reader and Amazon Kindle readers.
    /// </summary>
    public static final int MOBI = 52;
    /// <summary>
    /// CHM (Compiled HTML Help) format.
    /// </summary>
    public static final int CHM = 53;
    /// <summary>
    /// AZW3 format. Used by Amazon Kindle readers.
    /// </summary>
    public static final int AZW_3 = 54;
    /// <summary>
    /// EPUB format.
    /// </summary>
    public static final int EPUB = 55;

    /// <summary>
    /// ODF Text Document.
    /// </summary>
    public static final int ODT = 60;
    /// <summary>
    /// ODF Text Document Template.
    /// </summary>
    public static final int OTT = 61;

    /// <summary>
    /// Plain Text.
    /// </summary>
    public static final int TEXT = 62;

    /// <summary>
    /// Markdown text document.
    /// </summary>
    public static final int MARKDOWN = 63;

    /// <summary>
    /// Pdf document.
    /// </summary>
    public static final int PDF = 64;

    /// <summary>
    /// XML document.
    /// </summary>
    public static final int XML = 65;

    /// <summary>
    /// Unrecognized format, cannot be loaded by Aspose.Words.
    /// </summary>
    public static final int UNKNOWN = 255;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LoadFormat value.
    /// </summary>
    public static String getName(/*LoadFormat*/int loadFormat)
    {
    	switch (loadFormat)
    	{
    		case AUTO: return "AUTO";
    		case MS_WORKS: return "MS_WORKS";
    		case DOC: return "DOC";
    		case DOT: return "DOT";
    		case DOC_PRE_WORD_60: return "DOC_PRE_WORD_60";
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
    		case HTML: return "HTML";
    		case MHTML: return "MHTML";
    		case MOBI: return "MOBI";
    		case CHM: return "CHM";
    		case AZW_3: return "AZW_3";
    		case EPUB: return "EPUB";
    		case ODT: return "ODT";
    		case OTT: return "OTT";
    		case TEXT: return "TEXT";
    		case MARKDOWN: return "MARKDOWN";
    		case PDF: return "PDF";
    		case XML: return "XML";
    		case UNKNOWN: return "UNKNOWN";
    		default: return "Unknown LoadFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LoadFormat value.
    /// </summary>
    public static String toString(/*LoadFormat*/int loadFormat)
    {
    	switch (loadFormat)
    	{
    		case AUTO: return "Auto";
    		case MS_WORKS: return "MsWorks";
    		case DOC: return "Doc";
    		case DOT: return "Dot";
    		case DOC_PRE_WORD_60: return "DocPreWord60";
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
    		case HTML: return "Html";
    		case MHTML: return "Mhtml";
    		case MOBI: return "Mobi";
    		case CHM: return "Chm";
    		case AZW_3: return "Azw3";
    		case EPUB: return "Epub";
    		case ODT: return "Odt";
    		case OTT: return "Ott";
    		case TEXT: return "Text";
    		case MARKDOWN: return "Markdown";
    		case PDF: return "Pdf";
    		case XML: return "Xml";
    		case UNKNOWN: return "Unknown";
    		default: return "Unknown LoadFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LoadFormat name.
    /// </summary>
    public static /*LoadFormat*/int fromName(String loadFormatName)
    {
    	if ("AUTO".equals(loadFormatName)) return AUTO;
    	if ("MS_WORKS".equals(loadFormatName)) return MS_WORKS;
    	if ("DOC".equals(loadFormatName)) return DOC;
    	if ("DOT".equals(loadFormatName)) return DOT;
    	if ("DOC_PRE_WORD_60".equals(loadFormatName)) return DOC_PRE_WORD_60;
    	if ("DOCX".equals(loadFormatName)) return DOCX;
    	if ("DOCM".equals(loadFormatName)) return DOCM;
    	if ("DOTX".equals(loadFormatName)) return DOTX;
    	if ("DOTM".equals(loadFormatName)) return DOTM;
    	if ("FLAT_OPC".equals(loadFormatName)) return FLAT_OPC;
    	if ("FLAT_OPC_MACRO_ENABLED".equals(loadFormatName)) return FLAT_OPC_MACRO_ENABLED;
    	if ("FLAT_OPC_TEMPLATE".equals(loadFormatName)) return FLAT_OPC_TEMPLATE;
    	if ("FLAT_OPC_TEMPLATE_MACRO_ENABLED".equals(loadFormatName)) return FLAT_OPC_TEMPLATE_MACRO_ENABLED;
    	if ("RTF".equals(loadFormatName)) return RTF;
    	if ("WORD_ML".equals(loadFormatName)) return WORD_ML;
    	if ("HTML".equals(loadFormatName)) return HTML;
    	if ("MHTML".equals(loadFormatName)) return MHTML;
    	if ("MOBI".equals(loadFormatName)) return MOBI;
    	if ("CHM".equals(loadFormatName)) return CHM;
    	if ("AZW_3".equals(loadFormatName)) return AZW_3;
    	if ("EPUB".equals(loadFormatName)) return EPUB;
    	if ("ODT".equals(loadFormatName)) return ODT;
    	if ("OTT".equals(loadFormatName)) return OTT;
    	if ("TEXT".equals(loadFormatName)) return TEXT;
    	if ("MARKDOWN".equals(loadFormatName)) return MARKDOWN;
    	if ("PDF".equals(loadFormatName)) return PDF;
    	if ("XML".equals(loadFormatName)) return XML;
    	if ("UNKNOWN".equals(loadFormatName)) return UNKNOWN;
    	throw new IllegalArgumentException("Unknown LoadFormat name.");
    }
    
    /// <summary>
    /// Returns array of LoadFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTO,
    		MS_WORKS,
    		DOC,
    		DOT,
    		DOC_PRE_WORD_60,
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
    		HTML,
    		MHTML,
    		MOBI,
    		CHM,
    		AZW_3,
    		EPUB,
    		ODT,
    		OTT,
    		TEXT,
    		MARKDOWN,
    		PDF,
    		XML,
    		UNKNOWN,
    	};
    }

    public static final int length = 28;
}

/// <summary>
/// This is used during unit testing as an additional load format to the one specified in LoadFormat above.
/// </summary>
/*enum*/ final class LoadFormatTest
{
    private LoadFormatTest(){}
    
    /// <summary>
    /// Used in unified tests only to request loading of a DOCX file with graphics in DrawingML format.
    /// </summary>
    public static final int TEST_DOCX_DML = 254;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LoadFormatTest value.
    /// </summary>
    public static String getName(/*LoadFormatTest*/int loadFormatTest)
    {
    	switch (loadFormatTest)
    	{
    		case TEST_DOCX_DML: return "TEST_DOCX_DML";
    		default: return "Unknown LoadFormatTest value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LoadFormatTest value.
    /// </summary>
    public static String toString(/*LoadFormatTest*/int loadFormatTest)
    {
    	switch (loadFormatTest)
    	{
    		case TEST_DOCX_DML: return "TestDocxDml";
    		default: return "Unknown LoadFormatTest value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LoadFormatTest name.
    /// </summary>
    public static /*LoadFormatTest*/int fromName(String loadFormatTestName)
    {
    	if ("TEST_DOCX_DML".equals(loadFormatTestName)) return TEST_DOCX_DML;
    	throw new IllegalArgumentException("Unknown LoadFormatTest name.");
    }
    
    /// <summary>
    /// Returns array of LoadFormatTest values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEST_DOCX_DML,
    	};
    }

    public static final int length = 1;
}

