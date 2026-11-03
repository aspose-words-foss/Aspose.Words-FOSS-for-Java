// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Docx relationship type names enum.
/// </summary>
/*enum*/ final class DocxRelationshipType
{
    private DocxRelationshipType(){}
    
    public static final int OFFICE_DOCUMENT = 0;
    public static final int CORE_PROPERTIES = 1;
    public static final int EXTENDED_PROPERTIES = 2;
    public static final int CUSTOM_PROPERTIES = 3;
    public static final int THUMBNAIL = 4;
    public static final int FONT_TABLE = 5;
    public static final int STYLES = 6;
    public static final int NUMBERING = 7;
    public static final int SETTINGS = 8;
    public static final int WEB_SETTINGS = 9;
    public static final int THEME = 10;
    public static final int THEME_OVERRIDE = 11;
    public static final int HEADER = 12;
    public static final int FOOTER = 13;
    public static final int COMMENTS = 14;
    public static final int COMMENTS_EX = 15;
    public static final int COMMENTS_IDS = 16;
    public static final int COMMENTS_EXTENSIBLE = 17;
    public static final int FOOTNOTES = 18;
    public static final int ENDNOTES = 19;
    public static final int GLOSSARY_DOCUMENT = 20;
    public static final int CUSTOM_XML = 21;
    public static final int ALTERNATIVE_FORMAT_CHUNK = 22;
    public static final int CHART = 23;
    public static final int DIAGRAM_COLORS = 24;
    public static final int DIAGRAM_DATA = 25;
    public static final int DIAGRAM_LAYOUT = 26;
    public static final int DIAGRAM_STYLE = 27;
    public static final int PRINTER_SETTINGS = 28;
    public static final int SUB_DOCUMENT = 29;
    public static final int IMAGE = 30;
    public static final int VIDEO = 31;
    public static final int OLE_OBJECT = 32;
    public static final int CONTROL = 33;
    public static final int PACKAGE = 34;
    public static final int HYPERLINK = 35;
    public static final int ATTACHED_TEMPLATE = 36;
    public static final int VBA = 37;
    public static final int CUSTOMIZATIONS = 38;
    public static final int CHART_DRAWING = 39;
    public static final int DIAGRAM_DRAWING = 40;
    public static final int CUSTOM_XML_PROPERTIES = 41;
    public static final int VBA_DATA = 42;
    public static final int VBA_PROJECT_SIGNATURE = 43;
    public static final int ATTACHED_TOOLBARS = 44;
    public static final int MAIL_MERGE_DATA_SOURCE = 45;
    public static final int MAIL_MERGE_HEADER_SOURCE = 46;
    public static final int MAIL_MERGE_RECIPIENT_DATA = 47;
    public static final int FONT = 48;
    public static final int FRAMESET = 49;
    public static final int CONTROL_BINARY = 50;
    public static final int SAVE_THROUGH_XSLT = 51;
    public static final int CHART_STYLE = 52;
    public static final int CHART_COLOR_STYLE = 53;
    public static final int PEOPLE = 54;
    public static final int CHART_EX = 55;
    public static final int WEB_EXTENSION_TASK_PANES = 56;
    public static final int WEB_EXTENSION = 57;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DocxRelationshipType value.
    /// </summary>
    public static String getName(/*DocxRelationshipType*/int docxRelationshipType)
    {
    	switch (docxRelationshipType)
    	{
    		case OFFICE_DOCUMENT: return "OFFICE_DOCUMENT";
    		case CORE_PROPERTIES: return "CORE_PROPERTIES";
    		case EXTENDED_PROPERTIES: return "EXTENDED_PROPERTIES";
    		case CUSTOM_PROPERTIES: return "CUSTOM_PROPERTIES";
    		case THUMBNAIL: return "THUMBNAIL";
    		case FONT_TABLE: return "FONT_TABLE";
    		case STYLES: return "STYLES";
    		case NUMBERING: return "NUMBERING";
    		case SETTINGS: return "SETTINGS";
    		case WEB_SETTINGS: return "WEB_SETTINGS";
    		case THEME: return "THEME";
    		case THEME_OVERRIDE: return "THEME_OVERRIDE";
    		case HEADER: return "HEADER";
    		case FOOTER: return "FOOTER";
    		case COMMENTS: return "COMMENTS";
    		case COMMENTS_EX: return "COMMENTS_EX";
    		case COMMENTS_IDS: return "COMMENTS_IDS";
    		case COMMENTS_EXTENSIBLE: return "COMMENTS_EXTENSIBLE";
    		case FOOTNOTES: return "FOOTNOTES";
    		case ENDNOTES: return "ENDNOTES";
    		case GLOSSARY_DOCUMENT: return "GLOSSARY_DOCUMENT";
    		case CUSTOM_XML: return "CUSTOM_XML";
    		case ALTERNATIVE_FORMAT_CHUNK: return "ALTERNATIVE_FORMAT_CHUNK";
    		case CHART: return "CHART";
    		case DIAGRAM_COLORS: return "DIAGRAM_COLORS";
    		case DIAGRAM_DATA: return "DIAGRAM_DATA";
    		case DIAGRAM_LAYOUT: return "DIAGRAM_LAYOUT";
    		case DIAGRAM_STYLE: return "DIAGRAM_STYLE";
    		case PRINTER_SETTINGS: return "PRINTER_SETTINGS";
    		case SUB_DOCUMENT: return "SUB_DOCUMENT";
    		case IMAGE: return "IMAGE";
    		case VIDEO: return "VIDEO";
    		case OLE_OBJECT: return "OLE_OBJECT";
    		case CONTROL: return "CONTROL";
    		case PACKAGE: return "PACKAGE";
    		case HYPERLINK: return "HYPERLINK";
    		case ATTACHED_TEMPLATE: return "ATTACHED_TEMPLATE";
    		case VBA: return "VBA";
    		case CUSTOMIZATIONS: return "CUSTOMIZATIONS";
    		case CHART_DRAWING: return "CHART_DRAWING";
    		case DIAGRAM_DRAWING: return "DIAGRAM_DRAWING";
    		case CUSTOM_XML_PROPERTIES: return "CUSTOM_XML_PROPERTIES";
    		case VBA_DATA: return "VBA_DATA";
    		case VBA_PROJECT_SIGNATURE: return "VBA_PROJECT_SIGNATURE";
    		case ATTACHED_TOOLBARS: return "ATTACHED_TOOLBARS";
    		case MAIL_MERGE_DATA_SOURCE: return "MAIL_MERGE_DATA_SOURCE";
    		case MAIL_MERGE_HEADER_SOURCE: return "MAIL_MERGE_HEADER_SOURCE";
    		case MAIL_MERGE_RECIPIENT_DATA: return "MAIL_MERGE_RECIPIENT_DATA";
    		case FONT: return "FONT";
    		case FRAMESET: return "FRAMESET";
    		case CONTROL_BINARY: return "CONTROL_BINARY";
    		case SAVE_THROUGH_XSLT: return "SAVE_THROUGH_XSLT";
    		case CHART_STYLE: return "CHART_STYLE";
    		case CHART_COLOR_STYLE: return "CHART_COLOR_STYLE";
    		case PEOPLE: return "PEOPLE";
    		case CHART_EX: return "CHART_EX";
    		case WEB_EXTENSION_TASK_PANES: return "WEB_EXTENSION_TASK_PANES";
    		case WEB_EXTENSION: return "WEB_EXTENSION";
    		default: return "Unknown DocxRelationshipType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DocxRelationshipType value.
    /// </summary>
    public static String toString(/*DocxRelationshipType*/int docxRelationshipType)
    {
    	switch (docxRelationshipType)
    	{
    		case OFFICE_DOCUMENT: return "OfficeDocument";
    		case CORE_PROPERTIES: return "CoreProperties";
    		case EXTENDED_PROPERTIES: return "ExtendedProperties";
    		case CUSTOM_PROPERTIES: return "CustomProperties";
    		case THUMBNAIL: return "Thumbnail";
    		case FONT_TABLE: return "FontTable";
    		case STYLES: return "Styles";
    		case NUMBERING: return "Numbering";
    		case SETTINGS: return "Settings";
    		case WEB_SETTINGS: return "WebSettings";
    		case THEME: return "Theme";
    		case THEME_OVERRIDE: return "ThemeOverride";
    		case HEADER: return "Header";
    		case FOOTER: return "Footer";
    		case COMMENTS: return "Comments";
    		case COMMENTS_EX: return "CommentsEx";
    		case COMMENTS_IDS: return "CommentsIds";
    		case COMMENTS_EXTENSIBLE: return "CommentsExtensible";
    		case FOOTNOTES: return "Footnotes";
    		case ENDNOTES: return "Endnotes";
    		case GLOSSARY_DOCUMENT: return "GlossaryDocument";
    		case CUSTOM_XML: return "CustomXml";
    		case ALTERNATIVE_FORMAT_CHUNK: return "AlternativeFormatChunk";
    		case CHART: return "Chart";
    		case DIAGRAM_COLORS: return "DiagramColors";
    		case DIAGRAM_DATA: return "DiagramData";
    		case DIAGRAM_LAYOUT: return "DiagramLayout";
    		case DIAGRAM_STYLE: return "DiagramStyle";
    		case PRINTER_SETTINGS: return "PrinterSettings";
    		case SUB_DOCUMENT: return "SubDocument";
    		case IMAGE: return "Image";
    		case VIDEO: return "Video";
    		case OLE_OBJECT: return "OleObject";
    		case CONTROL: return "Control";
    		case PACKAGE: return "Package";
    		case HYPERLINK: return "Hyperlink";
    		case ATTACHED_TEMPLATE: return "AttachedTemplate";
    		case VBA: return "Vba";
    		case CUSTOMIZATIONS: return "Customizations";
    		case CHART_DRAWING: return "ChartDrawing";
    		case DIAGRAM_DRAWING: return "DiagramDrawing";
    		case CUSTOM_XML_PROPERTIES: return "CustomXmlProperties";
    		case VBA_DATA: return "VbaData";
    		case VBA_PROJECT_SIGNATURE: return "VbaProjectSignature";
    		case ATTACHED_TOOLBARS: return "AttachedToolbars";
    		case MAIL_MERGE_DATA_SOURCE: return "MailMergeDataSource";
    		case MAIL_MERGE_HEADER_SOURCE: return "MailMergeHeaderSource";
    		case MAIL_MERGE_RECIPIENT_DATA: return "MailMergeRecipientData";
    		case FONT: return "Font";
    		case FRAMESET: return "Frameset";
    		case CONTROL_BINARY: return "ControlBinary";
    		case SAVE_THROUGH_XSLT: return "SaveThroughXslt";
    		case CHART_STYLE: return "ChartStyle";
    		case CHART_COLOR_STYLE: return "ChartColorStyle";
    		case PEOPLE: return "People";
    		case CHART_EX: return "ChartEx";
    		case WEB_EXTENSION_TASK_PANES: return "WebExtensionTaskPanes";
    		case WEB_EXTENSION: return "WebExtension";
    		default: return "Unknown DocxRelationshipType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DocxRelationshipType name.
    /// </summary>
    public static /*DocxRelationshipType*/int fromName(String docxRelationshipTypeName)
    {
    	if ("OFFICE_DOCUMENT".equals(docxRelationshipTypeName)) return OFFICE_DOCUMENT;
    	if ("CORE_PROPERTIES".equals(docxRelationshipTypeName)) return CORE_PROPERTIES;
    	if ("EXTENDED_PROPERTIES".equals(docxRelationshipTypeName)) return EXTENDED_PROPERTIES;
    	if ("CUSTOM_PROPERTIES".equals(docxRelationshipTypeName)) return CUSTOM_PROPERTIES;
    	if ("THUMBNAIL".equals(docxRelationshipTypeName)) return THUMBNAIL;
    	if ("FONT_TABLE".equals(docxRelationshipTypeName)) return FONT_TABLE;
    	if ("STYLES".equals(docxRelationshipTypeName)) return STYLES;
    	if ("NUMBERING".equals(docxRelationshipTypeName)) return NUMBERING;
    	if ("SETTINGS".equals(docxRelationshipTypeName)) return SETTINGS;
    	if ("WEB_SETTINGS".equals(docxRelationshipTypeName)) return WEB_SETTINGS;
    	if ("THEME".equals(docxRelationshipTypeName)) return THEME;
    	if ("THEME_OVERRIDE".equals(docxRelationshipTypeName)) return THEME_OVERRIDE;
    	if ("HEADER".equals(docxRelationshipTypeName)) return HEADER;
    	if ("FOOTER".equals(docxRelationshipTypeName)) return FOOTER;
    	if ("COMMENTS".equals(docxRelationshipTypeName)) return COMMENTS;
    	if ("COMMENTS_EX".equals(docxRelationshipTypeName)) return COMMENTS_EX;
    	if ("COMMENTS_IDS".equals(docxRelationshipTypeName)) return COMMENTS_IDS;
    	if ("COMMENTS_EXTENSIBLE".equals(docxRelationshipTypeName)) return COMMENTS_EXTENSIBLE;
    	if ("FOOTNOTES".equals(docxRelationshipTypeName)) return FOOTNOTES;
    	if ("ENDNOTES".equals(docxRelationshipTypeName)) return ENDNOTES;
    	if ("GLOSSARY_DOCUMENT".equals(docxRelationshipTypeName)) return GLOSSARY_DOCUMENT;
    	if ("CUSTOM_XML".equals(docxRelationshipTypeName)) return CUSTOM_XML;
    	if ("ALTERNATIVE_FORMAT_CHUNK".equals(docxRelationshipTypeName)) return ALTERNATIVE_FORMAT_CHUNK;
    	if ("CHART".equals(docxRelationshipTypeName)) return CHART;
    	if ("DIAGRAM_COLORS".equals(docxRelationshipTypeName)) return DIAGRAM_COLORS;
    	if ("DIAGRAM_DATA".equals(docxRelationshipTypeName)) return DIAGRAM_DATA;
    	if ("DIAGRAM_LAYOUT".equals(docxRelationshipTypeName)) return DIAGRAM_LAYOUT;
    	if ("DIAGRAM_STYLE".equals(docxRelationshipTypeName)) return DIAGRAM_STYLE;
    	if ("PRINTER_SETTINGS".equals(docxRelationshipTypeName)) return PRINTER_SETTINGS;
    	if ("SUB_DOCUMENT".equals(docxRelationshipTypeName)) return SUB_DOCUMENT;
    	if ("IMAGE".equals(docxRelationshipTypeName)) return IMAGE;
    	if ("VIDEO".equals(docxRelationshipTypeName)) return VIDEO;
    	if ("OLE_OBJECT".equals(docxRelationshipTypeName)) return OLE_OBJECT;
    	if ("CONTROL".equals(docxRelationshipTypeName)) return CONTROL;
    	if ("PACKAGE".equals(docxRelationshipTypeName)) return PACKAGE;
    	if ("HYPERLINK".equals(docxRelationshipTypeName)) return HYPERLINK;
    	if ("ATTACHED_TEMPLATE".equals(docxRelationshipTypeName)) return ATTACHED_TEMPLATE;
    	if ("VBA".equals(docxRelationshipTypeName)) return VBA;
    	if ("CUSTOMIZATIONS".equals(docxRelationshipTypeName)) return CUSTOMIZATIONS;
    	if ("CHART_DRAWING".equals(docxRelationshipTypeName)) return CHART_DRAWING;
    	if ("DIAGRAM_DRAWING".equals(docxRelationshipTypeName)) return DIAGRAM_DRAWING;
    	if ("CUSTOM_XML_PROPERTIES".equals(docxRelationshipTypeName)) return CUSTOM_XML_PROPERTIES;
    	if ("VBA_DATA".equals(docxRelationshipTypeName)) return VBA_DATA;
    	if ("VBA_PROJECT_SIGNATURE".equals(docxRelationshipTypeName)) return VBA_PROJECT_SIGNATURE;
    	if ("ATTACHED_TOOLBARS".equals(docxRelationshipTypeName)) return ATTACHED_TOOLBARS;
    	if ("MAIL_MERGE_DATA_SOURCE".equals(docxRelationshipTypeName)) return MAIL_MERGE_DATA_SOURCE;
    	if ("MAIL_MERGE_HEADER_SOURCE".equals(docxRelationshipTypeName)) return MAIL_MERGE_HEADER_SOURCE;
    	if ("MAIL_MERGE_RECIPIENT_DATA".equals(docxRelationshipTypeName)) return MAIL_MERGE_RECIPIENT_DATA;
    	if ("FONT".equals(docxRelationshipTypeName)) return FONT;
    	if ("FRAMESET".equals(docxRelationshipTypeName)) return FRAMESET;
    	if ("CONTROL_BINARY".equals(docxRelationshipTypeName)) return CONTROL_BINARY;
    	if ("SAVE_THROUGH_XSLT".equals(docxRelationshipTypeName)) return SAVE_THROUGH_XSLT;
    	if ("CHART_STYLE".equals(docxRelationshipTypeName)) return CHART_STYLE;
    	if ("CHART_COLOR_STYLE".equals(docxRelationshipTypeName)) return CHART_COLOR_STYLE;
    	if ("PEOPLE".equals(docxRelationshipTypeName)) return PEOPLE;
    	if ("CHART_EX".equals(docxRelationshipTypeName)) return CHART_EX;
    	if ("WEB_EXTENSION_TASK_PANES".equals(docxRelationshipTypeName)) return WEB_EXTENSION_TASK_PANES;
    	if ("WEB_EXTENSION".equals(docxRelationshipTypeName)) return WEB_EXTENSION;
    	throw new IllegalArgumentException("Unknown DocxRelationshipType name.");
    }
    
    /// <summary>
    /// Returns array of DocxRelationshipType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		OFFICE_DOCUMENT,
    		CORE_PROPERTIES,
    		EXTENDED_PROPERTIES,
    		CUSTOM_PROPERTIES,
    		THUMBNAIL,
    		FONT_TABLE,
    		STYLES,
    		NUMBERING,
    		SETTINGS,
    		WEB_SETTINGS,
    		THEME,
    		THEME_OVERRIDE,
    		HEADER,
    		FOOTER,
    		COMMENTS,
    		COMMENTS_EX,
    		COMMENTS_IDS,
    		COMMENTS_EXTENSIBLE,
    		FOOTNOTES,
    		ENDNOTES,
    		GLOSSARY_DOCUMENT,
    		CUSTOM_XML,
    		ALTERNATIVE_FORMAT_CHUNK,
    		CHART,
    		DIAGRAM_COLORS,
    		DIAGRAM_DATA,
    		DIAGRAM_LAYOUT,
    		DIAGRAM_STYLE,
    		PRINTER_SETTINGS,
    		SUB_DOCUMENT,
    		IMAGE,
    		VIDEO,
    		OLE_OBJECT,
    		CONTROL,
    		PACKAGE,
    		HYPERLINK,
    		ATTACHED_TEMPLATE,
    		VBA,
    		CUSTOMIZATIONS,
    		CHART_DRAWING,
    		DIAGRAM_DRAWING,
    		CUSTOM_XML_PROPERTIES,
    		VBA_DATA,
    		VBA_PROJECT_SIGNATURE,
    		ATTACHED_TOOLBARS,
    		MAIL_MERGE_DATA_SOURCE,
    		MAIL_MERGE_HEADER_SOURCE,
    		MAIL_MERGE_RECIPIENT_DATA,
    		FONT,
    		FRAMESET,
    		CONTROL_BINARY,
    		SAVE_THROUGH_XSLT,
    		CHART_STYLE,
    		CHART_COLOR_STYLE,
    		PEOPLE,
    		CHART_EX,
    		WEB_EXTENSION_TASK_PANES,
    		WEB_EXTENSION,
    	};
    }

    public static final int length = 58;
}

