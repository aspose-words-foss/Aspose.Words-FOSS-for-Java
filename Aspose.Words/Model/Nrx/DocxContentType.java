// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcContentType;


/// <summary>
/// Helper class to map names of relation types to enum-like references.
/// </summary>
/*static*/ class DocxContentType
{
	/* Simulation of static class by using private constructor */
	private DocxContentType()
	{}

    /// <summary>
    /// Used for all other OLE objects (not Office binary, not OOXML).
    /// </summary>
    static final String OLE_OBJECT = "application/vnd.openxmlformats-officedocument.oleObject";

    /// <summary>
    /// Used for embedded OLE objects that are binary Excel documents.
    /// </summary>
    static final String XLS = "application/vnd.ms-excel";

    /// <summary>
    /// Used for embedded OLE objects that are binary PowerPoint documents.
    /// </summary>
    static final String PPT = "application/vnd.ms-powerpoint";

    /// <summary>
    /// Used for embedded OLE objects that are binary Word documents.
    /// </summary>
    static final String DOC = "application/msword";

    /// <summary>
    /// Used for embedded OLE objects that are binary Visio documents.
    /// </summary>
    static final String VSD = "application/vnd.visio";

    /// <summary>
    /// Used for the DOCX document as a whole or when embedded as a package.
    /// </summary>
    static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    static final String DOTX = "application/vnd.openxmlformats-officedocument.wordprocessingml.template";
    static final String DOCM = "application/vnd.ms-word.document.macroEnabled.12";
    static final String DOTM = "application/vnd.ms-word.template.macroEnabled.12";

    static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static final String XLTX = "application/vnd.openxmlformats-officedocument.spreadsheetml.template";
    static final String XLSM = "application/vnd.ms-excel.sheet.macroEnabled.12";
    static final String XLTM = "application/vnd.ms-excel.template.macroEnabled.12";
    static final String XLSB = "application/vnd.ms-excel.sheet.binary.macroEnabled.12";
    static final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    
    static final String PPTM = "application/vnd.ms-powerpoint.presentation.macroEnabled.12";
    static final String PPSX = "application/vnd.openxmlformats-officedocument.presentationml.slideshow";
    static final String PPSM = "application/vnd.ms-powerpoint.slideshow.macroEnabled.12";

    static final String SLDX = "application/vnd.openxmlformats-officedocument.presentationml.slide";
    static final String SLDM = "application/vnd.ms-powerpoint.slide.macroEnabled.12";

    static final String VSDX = "application/vnd.ms-visio.drawing";

    static final String CORE_PROPERTIES = "application/vnd.openxmlformats-package.core-properties+xml";
    static final String EXTENDED_PROPERTIES = "application/vnd.openxmlformats-officedocument.extended-properties+xml";
    static final String CUSTOM_PROPERTIES = "application/vnd.openxmlformats-officedocument.custom-properties+xml";

    static final String WEB_EXTENSION_TASK_PANES = "application/vnd.ms-office.webextensiontaskpanes+xml";
    static final String WEB_EXTENSION = "application/vnd.ms-office.webextension+xml";

    /// <summary>
    /// Used for macro-free documents.
    /// </summary>
    static final String DOCUMENT = "application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml";
    /// <summary>
    /// Used for macro-enabled documents.
    /// </summary>
    static final String DOCUMENT_MACRO_ENABLED = "application/vnd.ms-word.document.macroEnabled.main+xml";
    /// <summary>
    /// Used for macro-free templates.
    /// </summary>
    static final String TEMPLATE = "application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml";
    /// <summary>
    /// Used for macro-enabled templates.
    /// </summary>
    static final String TEMPLATE_MACRO_ENABLED = "application/vnd.ms-word.template.macroEnabledTemplate.main+xml";

    static final String GLOSSARY_DOCUMENT = "application/vnd.openxmlformats-officedocument.wordprocessingml.document.glossary+xml";

    static final String COMMENTS = "application/vnd.openxmlformats-officedocument.wordprocessingml.comments+xml";
    static final String COMMENTS_EXTENDED = "application/vnd.openxmlformats-officedocument.wordprocessingml.commentsExtended+xml";
    static final String COMMENTS_IDS = 
        "application/vnd.openxmlformats-officedocument.wordprocessingml.commentsIds+xml";
    static final String COMMENTS_EXTENSIBLE = 
        "application/vnd.openxmlformats-officedocument.wordprocessingml.commentsExtensible+xml";
    static final String FONT_TABLE = "application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml";
    static final String FOOTNOTES = "application/vnd.openxmlformats-officedocument.wordprocessingml.footnotes+xml";
    static final String ENDNOTES = "application/vnd.openxmlformats-officedocument.wordprocessingml.endnotes+xml";
    static final String NUMBERING = "application/vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml";
    static final String STYLES = "application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml";
    static final String SETTINGS = "application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml";
    static final String WEB_SETTINGS = "application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml";
    static final String HEADER = "application/vnd.openxmlformats-officedocument.wordprocessingml.header+xml";
    static final String FOOTER = "application/vnd.openxmlformats-officedocument.wordprocessingml.footer+xml";
    static final String PEOPLE = "application/vnd.openxmlformats-officedocument.wordprocessingml.people+xml";

    /// <summary>
    /// For /word/vbaProject.bin
    /// MS Word actually writes this as a default content type for bin.
    /// </summary>
    static final String VBA = "application/vnd.ms-office.vbaProject";
    /// <summary>
    /// For /word/vbaData.xml
    /// </summary>
    static final String VBA_DATA = "application/vnd.ms-word.vbaData+xml";
    /// <summary>
    /// For /word/vbaProjectSignature.bin
    /// </summary>
    static final String VBA_PROJECT_SIGNATURE = "application/vnd.ms-office.vbaProjectSignature";
    /// <summary>
    /// For /word/customizations.xml
    /// </summary>
    static final String CUSTOMIZATIONS = "application/vnd.ms-word.keyMapCustomizations+xml";
    /// <summary>
    /// For /word/attachedToolbars.bin
    /// </summary>
    static final String ATTACHED_TOOLBARS = "application/vnd.ms-word.attachedToolbars";

    /// <summary>
    /// For /word/theme/theme1.xml
    /// </summary>
    static final String THEME = "application/vnd.openxmlformats-officedocument.theme+xml";

    /// <summary>
    /// For /word/theme/themeOverride{0}.xml
    /// </summary>
    static final String THEME_OVERRIDE = "application/vnd.openxmlformats-officedocument.themeOverride+xml";

    /// <summary>
    /// For recipientData.xml
    /// </summary>
    static final String MAIL_MERGE_RECIPIENT_DATA = "application/vnd.openxmlformats-officedocument.wordprocessingml.mailMergeRecipientData+xml";

    /// <summary>
    /// Relationship from a Custom XML Data Storage part to its Custom XML Data Storage Properties.
    /// </summary>
    static final String CUSTOM_XML_PROPERTIES = "application/vnd.openxmlformats-officedocument.customXmlProperties+xml";

    static final String DIAGRAM_DATA = "application/vnd.openxmlformats-officedocument.drawingml.diagramData+xml";
    static final String DIAGRAM_LAYOUT = "application/vnd.openxmlformats-officedocument.drawingml.diagramLayout+xml";
    static final String DIAGRAM_STYLE = "application/vnd.openxmlformats-officedocument.drawingml.diagramStyle+xml";
    static final String DIAGRAM_COLORS = "application/vnd.openxmlformats-officedocument.drawingml.diagramColors+xml";
    static final String DIAGRAM_DRAWING = "application/vnd.ms-office.drawingml.diagramDrawing+xml";

    static final String CHART = "application/vnd.openxmlformats-officedocument.drawingml.chart+xml";
    static final String CHART_DRAWING = "application/vnd.openxmlformats-officedocument.drawingml.chartshapes+xml";
    static final String CHART_COLOR_STYLE = "application/vnd.ms-office.chartcolorstyle+xml";
    static final String CHART_STYLE = "application/vnd.ms-office.chartstyle+xml";

    static final String CHART_EX = "application/vnd.ms-office.chartex+xml";

    /// <summary>
    /// Content type of XML part of ActiveX control.
    /// </summary>
    static final String CONTROL = "application/vnd.ms-office.activeX+xml";
    /// <summary>
    /// Content type of binary part of ActiveX control.
    /// </summary>
    static final String CONTROL_BINARY = "application/vnd.ms-office.activeX";

    /// <summary>
    /// For /word/ink/ink.xml
    /// </summary>
    static final String INK_ML = "application/inkml+xml";

    static final String OCTET_STREAM = OpcContentType.OCTET_STREAM;

    static final String IMAGE_PICT_COMPRESSED = OpcContentType.IMAGE_PICT_COMPRESSED;
}

