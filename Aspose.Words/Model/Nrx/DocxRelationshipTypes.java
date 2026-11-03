// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.EnumUtilPal;

/// <summary>
/// Factory for Docx relationship types.
/// </summary>
class DocxRelationshipTypes
{
    DocxRelationshipTypes(boolean isStrict)
    {
        mIsStrict = isStrict;
    }

    /// <summary>
    /// Returns Docx relationship type by name.
    /// </summary>
    private String getRelType(/*DocxRelationshipType*/int relationshipTypeName)
    {
        return getType(relationshipTypeName, mIsStrict);
    }

    /// <summary>
    /// Returns Docx relationship type by name and conformance type.
    /// </summary>
    static String getType(/*DocxRelationshipType*/int relationshipTypeName, boolean isStrict)
    {
        String[] docxRelationshipTypes = isStrict ? G_DOCX_RELATIONSHIP_TYPES_STRICT : G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL;
        return docxRelationshipTypes[(int)relationshipTypeName];
    }

    String getOfficeDocument() { return getRelType(DocxRelationshipType.OFFICE_DOCUMENT); }
    String getCoreProperties() { return getRelType(DocxRelationshipType.CORE_PROPERTIES); }
    String getExtendedProperties() { return getRelType(DocxRelationshipType.EXTENDED_PROPERTIES); }
    String getCustomProperties() { return getRelType(DocxRelationshipType.CUSTOM_PROPERTIES); }
    String getThumbnail() { return getRelType(DocxRelationshipType.THUMBNAIL); }
    String getFontTable() { return getRelType(DocxRelationshipType.FONT_TABLE); }
    String getStyles() { return getRelType(DocxRelationshipType.STYLES); }
    String getNumbering() { return getRelType(DocxRelationshipType.NUMBERING); }
    String getSettings() { return getRelType(DocxRelationshipType.SETTINGS); }
    String getWebSettings() { return getRelType(DocxRelationshipType.WEB_SETTINGS); }
    String getTheme() { return getRelType(DocxRelationshipType.THEME); }
    String getThemeOverride() { return getRelType(DocxRelationshipType.THEME_OVERRIDE); }
    String getHeader() { return getRelType(DocxRelationshipType.HEADER); }
    String getFooter() { return getRelType(DocxRelationshipType.FOOTER); }
    String getComments() { return getRelType(DocxRelationshipType.COMMENTS); }
    String getCommentsEx() { return getRelType(DocxRelationshipType.COMMENTS_EX); }
    String getCommentsIds() { return getRelType(DocxRelationshipType.COMMENTS_IDS); }
    String getCommentsExtensible() { return getRelType(DocxRelationshipType.COMMENTS_EXTENSIBLE); }
    String getFootnotes() { return getRelType(DocxRelationshipType.FOOTNOTES); }
    String getEndnotes() { return getRelType(DocxRelationshipType.ENDNOTES); }
    String getGlossaryDocument() { return getRelType(DocxRelationshipType.GLOSSARY_DOCUMENT); }
    String getCustomXml() { return getRelType(DocxRelationshipType.CUSTOM_XML); }
    String getAlternativeFormatChunk() { return getRelType(DocxRelationshipType.ALTERNATIVE_FORMAT_CHUNK); }
    String getChart() { return getRelType(DocxRelationshipType.CHART); }
    String getDiagramColors() { return getRelType(DocxRelationshipType.DIAGRAM_COLORS); }
    String getDiagramData() { return getRelType(DocxRelationshipType.DIAGRAM_DATA); }
    String getDiagramLayout() { return getRelType(DocxRelationshipType.DIAGRAM_LAYOUT); }
    String getDiagramStyle() { return getRelType(DocxRelationshipType.DIAGRAM_STYLE); }
    String getPrinterSettings() { return getRelType(DocxRelationshipType.PRINTER_SETTINGS); }
    String getSubDocument() { return getRelType(DocxRelationshipType.SUB_DOCUMENT); }
    String getImage() { return getRelType(DocxRelationshipType.IMAGE); }
    String getVideo() { return getRelType(DocxRelationshipType.VIDEO); }
    String getOleObject() { return getRelType(DocxRelationshipType.OLE_OBJECT); }
    String getControl() { return getRelType(DocxRelationshipType.CONTROL); }
    String getPackage() { return getRelType(DocxRelationshipType.PACKAGE); }
    String getHyperlink() { return getRelType(DocxRelationshipType.HYPERLINK); }
    String getAttachedTemplate() { return getRelType(DocxRelationshipType.ATTACHED_TEMPLATE); }
    String getVba() { return getRelType(DocxRelationshipType.VBA); }
    String getCustomizations() { return getRelType(DocxRelationshipType.CUSTOMIZATIONS); }
    String getPeople() { return getRelType(DocxRelationshipType.PEOPLE); }
    String getChartDrawing() { return getRelType(DocxRelationshipType.CHART_DRAWING); }
    String getDiagramDrawing() { return getRelType(DocxRelationshipType.DIAGRAM_DRAWING); }
    String getCustomXmlProperties() { return getRelType(DocxRelationshipType.CUSTOM_XML_PROPERTIES); }
    String getVbaData() { return getRelType(DocxRelationshipType.VBA_DATA); }
    String getVbaProjectSignature() { return getRelType(DocxRelationshipType.VBA_PROJECT_SIGNATURE); }
    String getAttachedToolbars() { return getRelType(DocxRelationshipType.ATTACHED_TOOLBARS); }
    String getMailMergeDataSource() { return getRelType(DocxRelationshipType.MAIL_MERGE_DATA_SOURCE); }
    String getMailMergeHeaderSource() { return getRelType(DocxRelationshipType.MAIL_MERGE_HEADER_SOURCE); }
    String getMailMergeRecipientData() { return getRelType(DocxRelationshipType.MAIL_MERGE_RECIPIENT_DATA); }
    String getFont() { return getRelType(DocxRelationshipType.FONT); }
    String getFrameset() { return getRelType(DocxRelationshipType.FRAMESET); }
    String getControlBinary() { return getRelType(DocxRelationshipType.CONTROL_BINARY); }
    String getSaveThroughXslt() { return getRelType(DocxRelationshipType.SAVE_THROUGH_XSLT); }
    String getWebExtensionTaskPanes() { return getRelType(DocxRelationshipType.WEB_EXTENSION_TASK_PANES); }
    String getWebExtension() { return getRelType(DocxRelationshipType.WEB_EXTENSION); }

    private /*final*/ boolean mIsStrict;

    private static void initDocxRelationshipTypeStrict()
    {
        //From package.
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.OFFICE_DOCUMENT] = "http://purl.oclc.org/ooxml/officeDocument/relationships/officeDocument";

        //From package.
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.CORE_PROPERTIES] = "http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties";

        //From package.
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.EXTENDED_PROPERTIES] = "http://purl.oclc.org/ooxml/officeDocument/relationships/extendedProperties";

        //From package.
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.CUSTOM_PROPERTIES] = "http://purl.oclc.org/ooxml/officeDocument/relationships/customProperties";

        //From package.
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.WEB_EXTENSION_TASK_PANES] = "http://schemas.microsoft.com/office/2011/relationships/webextensiontaskpanes";

        //From package, from document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.THUMBNAIL] = "http://schemas.openxmlformats.org/package/2006/relationships/metadata/thumbnail";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.FONT_TABLE] = "http://purl.oclc.org/ooxml/officeDocument/relationships/fontTable";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.STYLES] = "http://purl.oclc.org/ooxml/officeDocument/relationships/styles";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.NUMBERING] = "http://purl.oclc.org/ooxml/officeDocument/relationships/numbering";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.SETTINGS] = "http://purl.oclc.org/ooxml/officeDocument/relationships/settings";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.WEB_SETTINGS] = "http://purl.oclc.org/ooxml/officeDocument/relationships/webSettings";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.THEME] = "http://purl.oclc.org/ooxml/officeDocument/relationships/theme";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.THEME_OVERRIDE] = "http://purl.oclc.org/ooxml/officeDocument/relationships/themeOverride";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.HEADER] = "http://purl.oclc.org/ooxml/officeDocument/relationships/header";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.FOOTER] = "http://purl.oclc.org/ooxml/officeDocument/relationships/footer";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.COMMENTS] = "http://purl.oclc.org/ooxml/officeDocument/relationships/comments";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.COMMENTS_EX] = "http://schemas.microsoft.com/office/2011/relationships/commentsExtended";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.COMMENTS_IDS] =
            "http://schemas.microsoft.com/office/2016/09/relationships/commentsIds";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.COMMENTS_EXTENSIBLE] =
            "http://schemas.microsoft.com/office/2018/08/relationships/commentsExtensible";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.FOOTNOTES] = "http://purl.oclc.org/ooxml/officeDocument/relationships/footnotes";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.ENDNOTES] = "http://purl.oclc.org/ooxml/officeDocument/relationships/endnotes";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.GLOSSARY_DOCUMENT] = "http://purl.oclc.org/ooxml/officeDocument/relationships/glossaryDocument";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.CUSTOM_XML] = "http://purl.oclc.org/ooxml/officeDocument/relationships/customXml";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.ALTERNATIVE_FORMAT_CHUNK] = "http://purl.oclc.org/ooxml/officeDocument/relationships/aFChunk";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.CHART] = "http://purl.oclc.org/ooxml/officeDocument/relationships/chart";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.DIAGRAM_COLORS] = "http://purl.oclc.org/ooxml/officeDocument/relationships/diagramColors";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.DIAGRAM_DATA] = "http://purl.oclc.org/ooxml/officeDocument/relationships/diagramData";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.DIAGRAM_LAYOUT] = "http://purl.oclc.org/ooxml/officeDocument/relationships/diagramLayout";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.DIAGRAM_STYLE] = "http://purl.oclc.org/ooxml/officeDocument/relationships/diagramQuickStyle";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.PRINTER_SETTINGS] = "http://purl.oclc.org/ooxml/officeDocument/relationships/printerSettings";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.SUB_DOCUMENT] = "http://purl.oclc.org/ooxml/officeDocument/relationships/subDocument";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.IMAGE] = "http://purl.oclc.org/ooxml/officeDocument/relationships/image";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.VIDEO] = "http://purl.oclc.org/ooxml/officeDocument/relationships/video";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.OLE_OBJECT] = "http://purl.oclc.org/ooxml/officeDocument/relationships/oleObject";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.CONTROL] = "http://purl.oclc.org/ooxml/officeDocument/relationships/control";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.PACKAGE] = "http://purl.oclc.org/ooxml/officeDocument/relationships/package";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.HYPERLINK] = "http://purl.oclc.org/ooxml/officeDocument/relationships/hyperlink";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.ATTACHED_TEMPLATE] = "http://purl.oclc.org/ooxml/officeDocument/relationships/attachedTemplate";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.CHART_EX] = "http://schemas.microsoft.com/office/2014/relationships/chartEx";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.VBA] = "http://schemas.microsoft.com/office/2006/relationships/vbaProject";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.CUSTOMIZATIONS] = "http://schemas.microsoft.com/office/2006/relationships/keyMapCustomizations";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.PEOPLE] = "http://schemas.microsoft.com/office/2011/relationships/people";

        //From Chart.
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.CHART_DRAWING] = "http://purl.oclc.org/ooxml/officeDocument/relationships/chartUserShapes";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.CHART_STYLE] = "http://schemas.microsoft.com/office/2011/relationships/chartStyle";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.CHART_COLOR_STYLE] = "http://schemas.microsoft.com/office/2011/relationships/chartColorStyle";

        //From Diagram Data.
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.DIAGRAM_DRAWING] = "http://schemas.microsoft.com/office/2007/relationships/diagramDrawing";

        //From Custom XML Data Storage Part to its properties.
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.CUSTOM_XML_PROPERTIES] = "http://purl.oclc.org/ooxml/officeDocument/relationships/customXmlProps";

        //From vbaProject.bin
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.VBA_DATA] = "http://schemas.microsoft.com/office/2006/relationships/wordVbaData";

        //From vbaProject.bin
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.VBA_PROJECT_SIGNATURE] = "http://schemas.microsoft.com/office/2006/relationships/vbaProjectSignature";

        //From customizations.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.ATTACHED_TOOLBARS] = "http://schemas.microsoft.com/office/2006/relationships/attachedToolbars";

        //From settings.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.MAIL_MERGE_DATA_SOURCE] = "http://purl.oclc.org/ooxml/officeDocument/relationships/mailMergeSource";

        //From settings.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.MAIL_MERGE_HEADER_SOURCE] = "http://purl.oclc.org/ooxml/officeDocument/relationships/mailMergeHeaderSource";

        //From settings.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.MAIL_MERGE_RECIPIENT_DATA] = "http://purl.oclc.org/ooxml/officeDocument/relationships/mailMergeRecipientData";

        //From fontTable.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.FONT] = "http://purl.oclc.org/ooxml/officeDocument/relationships/font";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.FRAMESET] = "http://purl.oclc.org/ooxml/officeDocument/relationships/frame";
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.CONTROL_BINARY] = "http://schemas.microsoft.com/office/2006/relationships/activeXControlBinary";

        //From settings.xml
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.SAVE_THROUGH_XSLT] = "http://purl.oclc.org/ooxml/officeDocument/relationships/transform";

        //From taskpanes.xml.
        G_DOCX_RELATIONSHIP_TYPES_STRICT[(int)DocxRelationshipType.WEB_EXTENSION] = "http://schemas.microsoft.com/office/2011/relationships/webextension";
    }

    private static void initDocxRelationshipTypeTransitional()
    {
        //From package.
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.OFFICE_DOCUMENT] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument";

        //From package.
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.CORE_PROPERTIES] = "http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties";

        //From package.
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.EXTENDED_PROPERTIES] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties";

        //From package.
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.CUSTOM_PROPERTIES] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/custom-properties";

        //From package.
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.WEB_EXTENSION_TASK_PANES] = "http://schemas.microsoft.com/office/2011/relationships/webextensiontaskpanes";

        //From package, from document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.THUMBNAIL] = "http://schemas.openxmlformats.org/package/2006/relationships/metadata/thumbnail";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.FONT_TABLE] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/fontTable";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.STYLES] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.NUMBERING] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/numbering";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.SETTINGS] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/settings";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.WEB_SETTINGS] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/webSettings";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.THEME] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.THEME_OVERRIDE] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/themeOverride";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.HEADER] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/header";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.FOOTER] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/footer";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.COMMENTS] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/comments";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.COMMENTS_EX] = "http://schemas.microsoft.com/office/2011/relationships/commentsExtended";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.COMMENTS_IDS] = 
            "http://schemas.microsoft.com/office/2016/09/relationships/commentsIds";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.COMMENTS_EXTENSIBLE] =
            "http://schemas.microsoft.com/office/2018/08/relationships/commentsExtensible";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.FOOTNOTES] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/footnotes";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.ENDNOTES] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/endnotes";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.GLOSSARY_DOCUMENT] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/glossaryDocument";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.CUSTOM_XML] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/customXml";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.ALTERNATIVE_FORMAT_CHUNK] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/aFChunk";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.CHART] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/chart";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.DIAGRAM_COLORS] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/diagramColors";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.DIAGRAM_DATA] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/diagramData";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.DIAGRAM_LAYOUT] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/diagramLayout";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.DIAGRAM_STYLE] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/diagramQuickStyle";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.PRINTER_SETTINGS] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/printerSettings";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.SUB_DOCUMENT] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/subDocument";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.IMAGE] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/image";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.VIDEO] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/video";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.OLE_OBJECT] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/oleObject";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.CONTROL] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/control";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.PACKAGE] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/package";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.HYPERLINK] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/hyperlink";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.ATTACHED_TEMPLATE] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/attachedTemplate";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.CHART_EX] = "http://schemas.microsoft.com/office/2014/relationships/chartEx";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.VBA] = "http://schemas.microsoft.com/office/2006/relationships/vbaProject";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.CUSTOMIZATIONS] = "http://schemas.microsoft.com/office/2006/relationships/keyMapCustomizations";

        //From document.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.PEOPLE] = "http://schemas.microsoft.com/office/2011/relationships/people";

        //From Chart.
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.CHART_DRAWING] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/chartUserShapes";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.CHART_STYLE] = "http://schemas.microsoft.com/office/2011/relationships/chartStyle";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.CHART_COLOR_STYLE] = "http://schemas.microsoft.com/office/2011/relationships/chartColorStyle";

        //From Diagram Data.
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.DIAGRAM_DRAWING] = "http://schemas.microsoft.com/office/2007/relationships/diagramDrawing";

        //From Custom XML Data Storage Part to its properties.
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.CUSTOM_XML_PROPERTIES] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/customXmlProps";

        //From vbaProject.bin
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.VBA_DATA] = "http://schemas.microsoft.com/office/2006/relationships/wordVbaData";

        //From vbaProject.bin
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.VBA_PROJECT_SIGNATURE] = "http://schemas.microsoft.com/office/2006/relationships/vbaProjectSignature";

        //From customizations.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.ATTACHED_TOOLBARS] = "http://schemas.microsoft.com/office/2006/relationships/attachedToolbars";

        //From settings.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.MAIL_MERGE_DATA_SOURCE] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/mailMergeSource";

        //From settings.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.MAIL_MERGE_HEADER_SOURCE] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/mailMergeHeaderSource";

        //From settings.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.MAIL_MERGE_RECIPIENT_DATA] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/recipientData";

        //From fontTable.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.FONT] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/font";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.FRAMESET] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/frame";
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.CONTROL_BINARY] = "http://schemas.microsoft.com/office/2006/relationships/activeXControlBinary";

        //From settings.xml
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.SAVE_THROUGH_XSLT] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/transform";

        //From taskpanes.xml.
        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL[(int)DocxRelationshipType.WEB_EXTENSION] = "http://schemas.microsoft.com/office/2011/relationships/webextension";
    }

    private static /*final*/ String[] G_DOCX_RELATIONSHIP_TYPES_STRICT;
    private static /*final*/ String[] G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL;

    static/* DocxRelationshipTypes()*/
    {
        int docxRelationshipTypeCount = EnumUtilPal.getEffectiveArrayLength((58));

        G_DOCX_RELATIONSHIP_TYPES_STRICT = new String[docxRelationshipTypeCount];
        initDocxRelationshipTypeStrict();

        G_DOCX_RELATIONSHIP_TYPES_TRANSITIONAL = new String[docxRelationshipTypeCount];
        initDocxRelationshipTypeTransitional();
    }
}

