// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/08/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Warning messages.
/// </summary>
/*static*/ class WarningStrings
{
	/* Simulation of static class by using private constructor */
	private WarningStrings()
	{}


    static final String ABSOLUTE_POSITION_TAB_NOT_SUPPORTED = "Absolute position tabs are not supported in {0}, using regular tab character.";

    static final String RESERVED_STYLE_INDEX_USED = "Style index reserved for built-in style '{0}' is used in style '{1}'.";
    static final String CANNOT_COPY_STYLE = "Cannot copy style {0} because it matches a style of a different type.";

    static final String CUSTOM_XML_ERROR = "Error is occurred while parsing Custom XML data.";

    static final String INVALID_H_LINKS = "Invalid _PID_HLINKS property, ignored.";

    static final String INVALID_DIGITAL_SIGNATURE = "Digital signature is invalid, document content was tampered.";

    static final String EMPTY_TEMPLATE_PATH = "Attached template path is empty, styles were not updated.";

    static final String FAILED_TEMPLATE_LOAD = "Attached template could not be loaded at {0}.";

    //>>>>>>>> #region  Validator strings

    static final String LIST_VALIDATOR_NON_UNIQUE_DEFINITION = "Non-unique list definition removed.";

    static final String FIELD_VALIDATOR_FIELD_NONE = "Fields with FieldType.FieldNone are not supported by Aspose.Words.";

    static final String FIELD_VALIDATOR_NO_START_END = "FieldSeparator without corresponding FieldStart and FieldEnd was removed.";

    static final String FIELD_VALIDATOR_NO_START = "FieldSeparator without corresponding FieldStart was removed.";

    static final String FIELD_VALIDATOR_REMOVED_INVALID_FORM_FIELD = "Invalid form field was removed.";

    static final String FIELD_VALIDATOR_DIFFERENT_STORIES_FIELD = "Invalid field is located in different document stories, removed.";

    static final String DOCUMENT_VALIDATOR_DML = "DrawingML is not supported in {0} format and will be converted to shape.";

    static final String DOCUMENT_VALIDATOR_VML_IN_ISO_STRICT =
        "VML shape is not supported in Strict Open XML Document format and will be converted to DrawingML.";

    static final String DOCUMENT_VALIDATOR_OFFICE_MATH = "Office math is not supported in {0} format and will be converted to shape.";

    static final String DOCUMENT_VALIDATOR_SDT_XML_MAPPING = "Data binding failed for StructuredDocumentTag.";

    static final String DOCUMENT_VALIDATOR_SDT_PLACEHOLDER_UNUSED = "One or more unused StructuredDocumentTag placeholder blocks were removed from Document Glossary.";

    static final String DOCUMENT_VALIDATOR_WRONG_STORY = "{0} is not in the main text story and shall be removed.";

    static final String DOCUMENT_VALIDATOR_ALLOCATED_COMMAND_INVALID = "Invalid allocated command changed to ApplyStyle Normal.";

    static final String DOCUMENT_VALIDATOR_REPLACED_DML = "DrawingML shape is replaced with fallback Shape, some formatting might be lost.";

    static final String DOCUMENT_VALIDATOR_ODT_CHART_TO_DML = "Open Document Format chart will be converted to DrawingML.";

    static final String ISO_29500_COMPLIANCE_PAGE_BORDER_ART_CHANGED = "Replaced PageBorderArt value with ISO29500-compliant.";

    static final String DOCUMENT_IS_DIGITALLY_SIGNED = "Document is digitally signed, export of digital signatures is not supported by Aspose.Words.";

    static final String COMMENT_VALIDATOR_NO_START = "CommentEnd without corresponding CommentStart was removed.";

    static final String COMMENT_VALIDATOR_NO_END = "CommentStart without corresponding CommentEnd was removed.";

    static final String COMMENT_VALIDATOR_EMPTY_RANGE = "Empty comment range was removed.";

    static final String COMMENT_ABOVE_COMMENT_RANGE = "Comment range was removed because located below its Comment node.";

    static final String EDITABLE_RANGE_VALIDATOR_NO_START = "EditableRangeEnd without corresponding EditableRangeStart was removed.";

    static final String EDITABLE_RANGE_VALIDATOR_NO_END = "EditableRangeStart without corresponding EditableRangeEnd was removed.";

    static final String COMMENT_VALIDATOR_NO_CONTENT = "CommentRange without corresponding Comment was removed.";

    static final String BOOKMARK_VALIDATOR_TOO_LONG_NAME = "Bookmark name exceeded maximum allowed length was truncated.";

    static final String BOOKMARK_VALIDATOR_DUPLICATE_NAME = "Bookmark with duplicated name '{0}' was removed.";

    static final String BOOKMARK_VALIDATOR_NO_START = "Bookmark '{0}' without corresponding BookmarkStart was removed.";

    static final String BOOKMARK_VALIDATOR_NO_END = "Bookmark '{0}' without corresponding BookmarkEnd was removed.";

    static final String TABLE_VALIDATOR_EMPTY_TABLE = "Empty table was removed.";

    static final String TABLE_VALIDATOR_TABLE_RESIZED = "Table width exceed maximum allowed, table was resized.";

    static final String TABLE_VALIDATOR_EMPTY_ROW = "Empty table row was removed.";

    static final String TABLE_VALIDATOR_INVALID_CELL_WIDTH = "Invalid cell width, table layout updated.";

    static final String TABLE_VALIDATOR_WORD_CELL_LIMIT_EXCEED = "Cell count per row must not exceed 63, document can have problem while opened in MS Word.";

    static final String INVALID_OFFICE_MATH_XML = "Aspose.Words failed to load OfficeMath XML for equation.";

    static final String INVALID_SHAPE_FIELD = "Invalid SHAPE field could not be parsed.";

    static final String INVALID_TEXTBOX_LINK = "Invalid textbox link for shape {0}, removed.";

    static final String INVALID_CONNECTOR_RULE = "Invalid connector rule for shape {0}, removed.";

    static final String ROW_HEIGHT_EXCEED_LIMIT = "Table row height exceed maximum allowed, corrected.";

    static final String LIST_COUNT_EXCEED_LIMIT = "List count exceed maximum allowed, document can have problem while opened in MS Word.";

    static final String HINT_TO_REMOVE_COMPATIBILITY_MODE = "The OoxmlSaveOptions.Compliance property should be " +
        "set to OoxmlCompliance.Iso29500_2008_Transitional or higher to remove the Compatibility Mode ribbon.";

    static final String NON_UNIQUE_SIGNATURE_LINE_ID = "The value {0} of SignatureLine.Id is not unique and was replaced with {1}.";

    static final String INVALID_COMPATIBILITY_FOR_LAYOUT_IN_CELL =
        "Value of the IsLayoutInCell property has no effect for DML shapes if a document is optimized for the version 2013 or higher.";
    static final String TASK_PANE_ADDINS_WILL_BE_SKIPPED_ON_SAVING =
        "Taskpane add-ins can only be saved to the Office Open XML file formats and will be skipped when saving to other formats.";
    static final String TASK_PANE_ADDIN_HAS_UNEXPECTED_REFERENCE_TYPE =
        "Reference to web extension can not be external.";

    static final String TEXT_PLAIN_TEXT_DECORATION_IN_VML =
        "Text underline property for VML WordArt shapes is not supported by MS Word.";

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Binary DOC strings

    static final String INVALID_FONT_TABLE_FC = "Invalid font table reference, ignored.";

    static final String UNSUPPORTED_TABLE_SPRM = "Table formatting modifier 0x{0:x4} is not supported for DOC format by Aspose.Words.";

    static final String INVALID_TABLE_SPRM = "Invalid table formatting modifier 0x{0:x4}, ignored.";

    static final String UNSUPPORTED_PARA_SPRM = "Paragraph formatting modifier 0x{0:x4} is not supported for DOC format by Aspose.Words.";

    static final String UNSUPPORTED_RUN_SPRM = "Text formatting modifier 0x{0:x4} is not supported for DOC format by Aspose.Words.";

    static final String UNSUPPORTED_SECT_SPRM = "Section formatting modifier 0x{0:x4} is not supported for DOC format by Aspose.Words.";

    /// <summary>
    /// Specifies warning message for unknown/undocumented SPRMs.
    /// </summary>
    static final String UNKNOWN_SPRM = "Unknown formatting modifier 0x{0:x4} occurred while reading DOC file.";

    /// <summary>
    /// TCellShdStyle outside table style definition resilience.
    /// </summary>
    static final String UNEXPECTED_CELL_SHD_STYLE = "Unexpected shading information occurred outside table style definition while reading DOC file.";

    static final String INVALID_ASSOCIATED_STRING = "Invalid associated string requested for 0x{0:x4}, replaced with empty string.";

    static final String CELL_PROPERTIES_MISSED = "Cell properties is missing.";

    static final String INVALID_TABLE_PADDING = "Invalid table padding spacing read, replaced with defaults.";

    static final String FIELD_TYPE_REPLACED = "Invalid field type specified while reading DOC file.";

    static final String INVALID_CELL = "EndStory occurred in a cell, row will be deleted.";

    static final String UNKNOWN_OFFICE_ART_PROPERTY = "Unknown OfficeArt property found 0x{0:x4}, ignored.";

    static final String META_STREAM_MISSED = "OLE 1.0 object stream 'META' is missing, possible image size problem.";

    static final String SECTION_BREAK_NOT_IN_MAIN_DOCUMENT = "Section breaks allowed only inside main document.";

    static final String SECTION_BREAK_IN_TABLE = "Section breaks are not allowed inside tables by Aspose.Words.";

    static final String FILE_CORRUPTED_AT = "File is corrupted, data loss at FC: 0x{0:x4}.";

    static final String OLE_OBJECT_DATA_MISSED = "Embedded OLE object data is missed for ObjectID: 0x{0:x4}.";

    static final String INVALID_PICF_AT = "Invalid PICF structure at 0x{0:x4}, shape is ignored.";

    static final String INVALID_PICF = "Invalid PICF structure, shape is ignored.";

    static final String SHAPE_FSPA_MISSED = "Couldn't get FSPA structure for shape, shape is ignored.";

    static final String SHAPE_OFFICE_ART_MISSED = "Couldn't get OfficeArt record for shape, shape is ignored.";

    static final String INVALID_ANNOTATION_AT = "Invalid annotation reference at local CP: 0x{0:x4}.";

    static final String EMPTY_STYLE_SHEET = "Document contains empty stylesheet.";

    static final String INVALID_STYLE_SHEET = "Invalid fixed styles count, ignore whole stylesheet.";

    static final String INVALID_STYLE = "Style definition for Istd 0x{0:x2} is corrupt, ignored.";

    static final String INVALID_STYLE_IGNORE_REST = "Style definition for Istd 0x{0:x2} is corrupt, rest of stylesheet is ignored.";

    static final String NORMAL_BASED_ON = "Normal style is based on other style which is not allowed, corrected.";

    static final String INVALID_FONT_NAME = "Invalid font name, replaced with '{0}'.";

    static final String INVALID_INDENT = "Invalid indent value {0} read, ignore.";

    static final String INVALID_UNDERLINE = "Invalid underline type read, used defaults.";

    static final String INVALID_TAB_STOP_ALIGNMENT = "Invalid tab stop alignment type read, used defaults.";

    static final String OBJECT_POOL_MISSED = "Document 'ObjectPool' stream is missed, OLE objects will be lost.";

    static final String LOAD_OVERRIDE = "LoadOverride flag is not supported in DOC format by Aspose.Words.";

    static final String FAR_EAST_INSTALLATION_LID = "Installation language is FarEast language.";

    static final String WORD_60_DOCUMENT = "Document is a Word 6.0/95 document.";

    static final String PRINTER_DRIVER_DATA = "Printer driver information is not supported by Aspose.Words.";

    static final String LAST_SELECTION_DATA = "Last selection information is not supported by Aspose.Words.";

    static final String SPELL_CHECKER = "Spell checker information is not supported by Aspose.Words.";

    static final String GRAMMAR_CHECKER = "Grammar checker information is not supported by Aspose.Words.";

    static final String ROUTE_SLIP = "RouteSlip information is DOC format only feature and lost if saved to another format.";

    static final String SAVE_HISTORY = "Word97/2000 save history is not supported by Aspose.Words.";

    static final String DOCUMENT_VERSIONING = "Document versioning is not supported by Aspose.Words.";

    static final String DEPRECATED_OCX_DATA = "Pre-Word2003 additional information for ActiveX is not supported by Aspose.Words.";

    static final String THREADING_DATA = "Threading information is not supported by Aspose.Words.";

    static final String HYBRID_LISTS_FORMATS = "Hybrid multilevel lists numbering formats is not supported by Aspose.Words.";

    static final String MSO_ENVELOPE = "MSO Envelope information is not supported by Aspose.Words.";

    static final String LANGUAGE_AUTO_DETECT_DATA = "Language auto-detect data is not supported by Aspose.Words.";

    static final String GRAMMAR_CHECKER_OPTIONS =  "Grammar checker options is not supported is not supported by Aspose.Words.";

    static final String GRAMMAR_CHECKER_COOKIES = "Grammar checker cookies is not supported is not supported by Aspose.Words.";

    static final String TEXT_FRAMEWORK_DATA = "Microsoft Text Framework data is not supported by Aspose.Words.";

    static final String SMART_TAG_RECOGNIZER_DATA = "Smart tag recognizer data is not supported by Aspose.Words.";

    static final String REPAIR_BOOKMARKS = "Repair bookmarks are not supported by Aspose.Words.";

    static final String COLOR_MRU = "ColorMru OfficeArt record is not supported, ignored.";

    static final String INVALID_NUMBER_REVISION = "Invalid number revision found, removed.";

    static final String INVALID_CNFC = "Unknown conditional formatting read 0x{0:x4}.";

    static final String INVALID_PARAGRAPH_STYLE_ISTD = "Paragraph style '{0}' has incorrect Istd set.";

    static final String INVALID_STYLE_OUTLINE_LEVEL = "Style '{0}' has incorrect OutlineLevel, removed.";

    static final String INVALID_OFFICE_ART_LENGTH = "Invalid OfficeArt record length, corrected.";

    static final String UNKNOWN_OFFICE_ART_TYPE = "Unknown OfficeArt record 0x{0:x4} found, ignored.";

    static final String SECT_PROPERTIES_MISSED = "Section properties is missed, used defaults.";

    static final String COLUMN_WIDTH_MISSED = "Column width is missed, used default value 17.09 cm.";

    static final String EXTRA_COLUMN_DEFINITIONS = "Extra column definitions found, removed.";

    static final String SMART_TAG_END_MISSED = "Smart tag '{0}' ends after paragraph mark.";

    static final String EMPTY_PROPERTY_NAME = "Document property without name found, ignored.";

    static final String EMBEDDED_OLE_WITHOUT_DATA = "Embedded OLE object without data found, converted to image.";

    static final String INVALID_BOOL_EX = "Invalid BoolEx value occurred, ignored.";

    static final String FIELD_START_MISSED = "Invalid field separator without field start occurred.";

    static final String INVALID_LIST_NUMBER_FORMAT = "Invalid number format definition, skipped.";

    static final String INVALID_LIST_NUMBER_STYLE = "Invalid number style definition, skipped.";

    static final String INVALID_LIST_LEVEL_ALIGNMENT = "Invalid level alignment definition, skipped.";

    static final String INVALID_LIST_RESTART_AFTER_LEVEL = "Invalid level restart definition, skipped.";

    static final String INVALID_LIST_TRAILING_CHARACTER = "Invalid trailing character definition, skipped.";

    static final String INVALID_LIST_LEGACY_SPACE = "Invalid legacy space definition, skipped.";

    static final String LIST_NUMBER_FORMAT_TOO_LONG = "Number format definition is too long, truncated.";

    static final String INVALID_LIST_LEVEL = "List level in list definition 0x{0:x} is corrupted.";

    static final String AUTO_NUM_LINKED_LIST = "Lists linked to AutoNum fields are not supported by Aspose.Words.";

    static final String OFFICE_ART_READ_FAIL = "Read wrong number of bytes from OfficeArt record, ignored.";

    static final String STYLE_INVALID_REFERENCE = "Style '{0}' refers to non-existent style, removed invalid reference.";

    static final String BAD_NESTING_LEVEL = "Table end is at an incorrect nesting level.";

    static final String UNEXPECTED_END_OF_TABLE = "Unexpected end of table, ignored.";

    static final String UNEXPECTED_END_OF_ROW = "Unexpected end of row, ignored.";

    static final String TOO_LONG_FOLLOWINGS = "Too long following chars count, used defaults.";

    static final String TOO_LONG_LEADINGS = "Too long leading chars count, used defaults.";

    static final String INVALID_REVISION_NUMBER = "Invalid revision number.";

    static final String INVALID_DATE_TIME_PROPERTY = "Invalid DateTime property, ignored.";

    static final String UNSUPPORTED_DOCUMENT_PROPERTY = "Unsupported document property 0x{0:x4}, ignored.";

    static final String UNSUPPORTED_POWER_POINT_PROPERTY = "Unsupported PowerPoint property 0x{0:x4}, ignored.";

    static final String UNACCEPTABLE_MARKUP = "Node is unable to accept markup, markup ignored.";

    static final String INVALID_TAB_STOP_POSITION = "Invalid tab stop position {0}, ignored.";

    static final String INVALID_LINE_STYLE = "Invalid line style value is read for border while reading DOC file.";

    static final String SDT_NOT_SUPPORTED = "Structured Document Tags feature is not supported in DOC.";

    static final String REPEATING_SECTION_X_PATH_LAST_INDEXER_REMOVED = "An indexer of last node selector in RepeatingSection SDT XPath is removed.";

    static final String GLOSSARY_DOCUMENT_NOT_SUPPORTED = "Glossary Document feature is not supported in DOC.";

    static final String TABLE_STYLE_EXPANDED = "Table style 0x{0:x2} can not be preserved and will be converted to direct formatting.";

    static final String OOXML_CONTROL_NOT_SUPPORTED = "OOXML control is not supported in DOC and will be converted to image.";

    static final String AUTO_VERSION_OFF = "Word2003 versioning feature is not supported, AutoVersion option turned off";

    static final String UNKNOWN_PROG_ID = "Unknown ProgId value '{0}'. This might cause inaccessible OLE embedding.";

    static final String INVALID_STYLE_TYPE = "Invalid style type value 0x{0:x2}, style is ignored.";

    static final String INVALID_SECTION_BREAK = "Invalid section break character at CP: {0}.";

    static final String INVALID_EMBEDDED_FONT_INDEX = "Invalid embedded font index {0}.";

    static final String INVALID_NUMBER_REVISION_LENGTH = "Invalid number revision length.";

    static final String TOO_MANY_TEXTBOXES = "Too many textboxes, document can have problem while opened in MS Word.";

    static final String INVALID_LIST_DEF_REFERENCE = "List {0} refers to invalid list definition, removed.";

    static final String INVALID_LIST_DEF = "Invalid list definition at index {0}, removed.";

    static final String INVALID_STYLE_EXTRA_BYTES = "Invalid extra data in style '{0}', ignored.";

    static final String THUMBNAIL_PROPERTY_NOT_SUPPORTED = "Thumbnail property is not supported in DOC format by Aspose.Words.";

    static final String SMART_TAG_PROPERTY_INDEX_OUT_OF_RANGE = "Invalid smart tag at index {0}, removed.";

    static final String INVALID_TAB_STOP_SPRM = "Invalid tab stop data, ignored.";

    static final String TOO_MANY_BOOKMARKS = "Maximum bookmarks count supported by binary DOC file format is reached.";

    static final String ILLEGAL_BOOKMARK_NAME_PICTURE_BULLETS = "Bookmark name _PictureBullets is reserved in DOC format, bookmark removed.";

    static final String DUPLICATED_OFFICE_ART_PROPERTY = "OfficeArt property with duplicated Id {0} was read, ignored.";

    static final String INVALID_PLCF_BTE_PAPX = "Invalid PlcfBtePapx structure, paragraph properties are lost.";

    static final String INVALID_PLCF_BTE_CHPX = "Invalid PlcfBteChpx structure, character properties are lost.";

    static final String INVALID_PARENT_COMMENT = "Unable to write parent reference for comment id: {0}.";

    static final String TEXTBOX_VERTICAL_ANCHOR_2007_ONLY = "Textbox vertical alignment is compatible only with Word 2007.";

    static final String TEXTBOX_VERTICAL_ANCHOR_NOT_SUPPORTED = "Textbox vertical alignment is not supported in save format.";

    //<<<<<<<< #endregion 

    static final String UNEXPECTED_ELEMENT = "Unexpected element '{0}' is occurred while reading document, ignored.";
    static final String UNEXPECTED_ATTRIBUTE = "Unexpected attribute '{0}' is occurred while reading document, ignored.";
    static final String INVALID_ON_OFF_VALUE = "Unknown value '{0}' for '{1}' element.";
    static final String INVALID_VALUE = "Invalid value '{0}' for '{1}' element.";
    static final String UNEXPECTED_TAG_OR_ATTRIBUTE = "Tag or attribute with name '{0}' is unexpected.";
    static final String NOT_SUPPORTED_TAG = "Tag with name '{0}' is not supported.";
    static final String NOT_SUPPORTED_ATTRIBUTE = "Attribute with name '{0}' is not supported.";

    static final String UNEXPECTED_SECTION_BREAK = "Unexpected section break, ignored.";

    static final String JPEG_CONVERTED_TO_PNG = "JPEG images with transparent areas are not supported. " +
        "The image is converted to PNG.";

    static final String SHAPE_CANNOT_BE_RENDERED = "Shape has invalid size or other parameters and cannot be rendered.";

    static final String RTF_EMPTY_THEME_DATA = "Empty themedata, ignored.";

    static final String FOOTNOTE_WITHOUT_REFERENCE_MARK = "Custom footnote mark '{0}' without reference mark, ignored.";
    static final String FOOTNOTE_WITH_EMPTY_REFERENCE_MARK = "Custom footnote mark '{0}' with empty reference mark, ignored.";
    static final String FOOTNOTE_WITH_SEVERAL_REFERENCES = "There are several references to the footnote with ID {0}.";

    static final String CORRUPTED_GFX_DATA = "GFX data is corrupted, ignored.";

    static final String IMAGE_REPLACED_WITH_PLACEHOLDER = "Image has been replaced with a placeholder.";

    // Below warnings are not tested (there is no appropriate files).

    static final String INVALID_BLIP_ID = "Invalid OfficeArt blip reference, ignored.";
    static final String DELETED_PSPL = "DeletedPspl OfficeArt record is not supported, ignored.";
    static final String REVISIONS_AUTHOR_SELECTION = "Saved revision author selection is not supported, ignored.";
    static final String UNSUPPORTED_OFFICE_ART_PROPERTY = "Unsupported OfficeArt property 0x{0:x4}, ignored.";
    static final String INVALID_LINK_TO_FILE = "Invalid link to file, moved to image description.";
    static final String INVALID_OFFICE_ART = "Invalid OfficeArt record found, ignored.";
    static final String AUTO_CAPTIONS = "Captions/AutoCaptions are not supported, ignored.";
    static final String AUTO_SUMMARY_PRIORITIES = "Priority of a text ranges for AutoSummary is not supported, ignored.";
    static final String CONSISTENCY_CHECKER_BOOKMARKS = "Consistency checker bookmarks are not supported, ignored.";
    static final String END_STORY_IN_CELL = "EndStory occurred in row, row will be deleted.";
    static final String INVALID_FIELD_CHAR = "Field char is invalid at FC: 0x{0:x4}.";
    static final String UNABLE_TO_GET_PARENT = "Unable to get common parent range, markup ignored.";
    static final String ZERO_SHAPE_SIZE = "Image has zero dimensions, used defaults.";
    static final String UNRECOGNIZED_BINARY_DATA = "Unrecognized binary data.";
    static final String VIOLATION_I_4_I = "Violation of i4i's patent #5,787,449. Custom xml will no longer be supported.";

    static final String REMOVED_STYLE_LIST_REFERENCE = "Removed numbering reference for style '{0}'.";

    static final String NOT_SUPPORTED_ALGORITHM = "Unsupported AlgorithmId value '{0}'.";

    static final String NOT_SUPPORTED_BY_ISO_STRICT =
        "The element '{0}' is not supported by Strict Open XML Document format, discarded.";
    static final String NOT_SUPPORTED_NUMBER_STYLE = "The number style {0} is not supported by the {1} file format.";

    static final String NON_GUID_XML_PART_ID = "The Id property of a custom XML part has a non-GUID value: '{0}'. " +
        "The latest versions of Microsoft Word may fail on opening this document.";
    static final String CHANGED_XML_PART_ID = "The Id property of a custom XML part has been changed from '{0}' " +
        "to '{1}' to comply with OOXML specification requirements.";

    static final String FONTS_NOT_EMBEDDED_DUE_TO_EMBEDDING_DISABLED =
        "Fonts are not embedded, despite either 'EmbedSystemFonts' or 'SaveSubsetFonts' is set to 'true' due to 'EmbedTrueTypeFonts' option is set to 'false'.";
    static final String FONTS_EMBEDDING_NOT_SUPPORTED_FOR_FILE_FORMAT = "Fonts are not embedded, as it is not supported for {0} file format.";
    static final String FONT_NOT_EMBEDDED_DUE_TO_SIZE_LIMIT = "Font '{0}' is not embedded due to exceeding the maximum allowed size of {1:N0} bytes.";
    static final String FONT_SUBSET_EMBEDDED_DUE_TO_SIZE_LIMIT = "Font subset embedded for font '{0}' due to exceeding the maximum allowed size of {1:N0} bytes.";
    static final String INVALID_EMBEDDED_FONT_STYLE_NAME = "Invalid embedded font style name: {0}.";

    static final String SHAPE_IGNORED = "Shape is ignored due to: {0}";

    static final String NOT_SUPPORTED_ENTITY_PICKER =
        "Entity Picker element of Structured Document Tag is not supported by the {0} file format.";
    static final String NOT_SUPPORTED_WEB_EXTENSIONS =
        "Web Extensions are not supported by the {0} file format.";
    static final String NOT_SUPPORTED_SDT_APPEARANCE =
        "Appearance element of Structured Document Tag is not supported by the {0} file format.";
    static final String NOT_SUPPORTED_COLLAPSED =
        "'Collapsed as default' property is not supported by the {0} file format.";
    static final String NOT_SUPPORTED_FOOTNOTE_COLUMNS =
        "Setting footnote columns is not supported by the {0} file format.";
    static final String NOT_SUPPORTED_PEOPLE_PART =
        "Contact information for authors of comments and revisions is not supported in the {0} file format.";
    static final String NOT_SUPPORTED_SETTING =
        "The '{0}' document setting is not supported by the {0} file format.";
    static final String NOT_SUPPORTED_SETTING_BY_AW =
        "Aspose.Words does not support the '{0}' document setting for the {0} file format.";
    static final String NOT_SUPPORTED_TABLE_TITLE =
        "Table title is not supported by the {0} file format.";
    static final String NOT_SUPPORTED_TABLE_DESCRIPTION =
        "Table description is not supported by the {0} file format.";

    static final String UNEXPECTED_BREAK = "Unexpected break, ignored.";
    static final String UNEXPECTED_BASE_64 = "Unexpected base-64 string used for protection document, ignored.";

    static final String CELL_REVISION_LOST = "Table cell edit revisions are lost.";

    static final String UNSUPPORTED_CHART_EX_RENDERING =
        "New chart types of MS Word 2016 are not supported: the chart will be rendered with using its fallback shape.";

    static final String UNSUPPORTED_CONTENT_PART_RENDERING = "Content part is not supported and will be replaced with its fallback shape.";

    static final String UNSUPPORTED_SHAPE_MESSAGE = "Some of DrawingML shape features are not supported upon rendering." +
                                    "DrawingML shape will be replaced with its fallback VML shape.";

    static final String CANNOT_DEFINE_NUMBERING_FORMATTING =
        "Cannot preserve list number/symbol formatting on saving to ODT file format.";

    static final String ECMA_376_NOT_SUPPORTED_XML_MAPPING =
        "The defined XML mapping is not supported by the ECMA-376 OOXML format.";

    static final String FOOTNOTE_PART_MISSING = "Footnote/endnote document part is missing.";

    static final String THEME_PART_MISSING = "Theme part '{0}' is missing.";

    static final String MOVE_START_MISSED = "The start element of moved content is missed. End element will be ignored.";

    static final String UNSUPPORTED_DML_TEXT_ALIGNMENT =
        "The alignment {0} is not supported by drawing paragraphs. It has been replaced with the {1} value.";

    static final String UNEXPECTED_STORY_FOR_SHAPE = "Floating shape in unexpected story";

    static final String UNSUPPORTED_VML_COLOR = "Unsupported color type encountered during {0} export: {1}.";

    static final String INVALID_STYLE_ISTD = "Style '{0}' has style index unsupported by target format.";

    static final String LOADING_PROGRESS_UNSUPPORTED = "Loading progress is not supported for the '{0}' format.";

    static final String SAVING_PROGRESS_UNSUPPORTED = "Saving progress is not supported for the '{0}' format.";

    static final String PICTURE_PROPERTIES_IGNORE = "Picture properties in an unexpected place, ignored";

    static final String CANNOT_SET_Y_VALUE = "Please make sure you are using correct chart type. " +
        "There is no way to set Y value for this chart type.";

    static final String CANNOT_SET_BUBBLE_SIZE = "Please make sure you are using correct chart type. " +
        "There is no way to set bubble size for non-bubble chart.";

    static final String CHART_DATA_REGENERATION =
        "The embedded Excel workbook is regenerated after updating chart data. " +
        "Any additional data not used for the chart and formatting contained in the workbook will be lost.";

    static final String CELL_LIMIT_EXCESS =
        "The number of cells in a row exceeds 63. " +
        "The cell contents are merged with the last cell in the row.";

    static final String OPTIMIZED_FOR_WORD_2013 = "The document has been optimized for MS Word 2013.";

    static final String DOC_VERSION_INCREASED_TO_2013 =
        "The document version has been increased to 2013 to support this feature.";

    static final String HEADER_FOOTER_PART_MISSING = "Header/Footer relationship part is missing.";

    //>>>>>>>> #region   Html5 strings
    static final String HTML_5_EMPTY_TITLE = "Document title is empty that doesn't conform to HTML5 standard (element title must not be empty).";
    static final String HTML_5_IMG_USEMAP_INSIDE_ANCHOR = "The element img with the attribute usemap appears as a descendant of the a element that doesn't conform to HTML5 standard.";
    static final String HTML_5_I_FRAME_NORESIZE_IGNORED = "Frame.NoResize property doesn't have a counterpart in iframe HTML element and has been ignored.";
    static final String HTML_5_I_FRAME_SCROLL_TYPE_IGNORED = "Frame.ScrollType property doesn't have a counterpart in iframe HTML5 element and has been ignored.";
    //<<<<<<<< #endregion 

    //>>>>>>>> #region   ODT strings
    static final String ODT_NOT_SUPPORTED_NUMBER_FORMAT_LENGHT = "Aspose.Word model only support 30 symbols or less in NumberFormat. Excessive symbols will be truncated.";

    static final String ODT_NOT_SUPPORTED_META_EMPTY_NAME = "Empty user-defined property name, ignored.";
    //<<<<<<<< #endregion 

    //>>>>>>>> #region   linked textbox strings
    static final String LINK_TEXTBOX_INVALID_TARGET = "Invalid target textbox.";
    static final String LINK_TEXTBOX_SAME_DOCUMENT = "Parent document of the linked textboxes must be the same.";
    static final String LINK_TEXTBOX_BOTH_SHAPES_TEXT = "Please, make sure both shapes are able to store a text.";
    static final String LINK_TEXTBOX_EMPTY_TARGET = "Target textbox must be empty.";
    static final String LINK_TEXTBOX_SAME_MARKUP = "Source and target textboxes must use the same markup language (DML/VML).";
    static final String LINK_TEXTBOX_TARGET_IS_LINKED = "Target textbox must not be a part of another linked chain.";
    static final String LINK_TEXTBOX_HAS_LINK = "This textbox already has a link.";
    static final String LINK_TEXTBOX_DIFFERENT_STORY_TYPES = "Different story types of textboxes.";
    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Markdown strings
    static final String MARKDOWN_FORMATTING_LOST = "The ({0}) cannot be properly written into Markdown.";
    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Xlsx strings
    static final String UNSUPPORTED_COMMENTS = "Comments are not supported when saving as {0}.";

    static final String UNSUPPORTED_FOOTNOTES = "Footnotes are not supported when saving as {0}.";

    static final String UNSUPPORTED_SPECIAL_CHARS = "Special chars are not supported when saving as {0}.";

    static final String UNSUPPORTED_SMART_TAGS = "Smart tags are not supported when saving as {0}.";

    static final String UNSUPPORTED_SDTS = "Structured document tags are not supported when saving as {0}.";

    static final String XLSX_UNSUPPORTED_FORM_FIELDS = "Form fields are not supported when saving as XLSX.";

    static final String XLSX_LOST_SHAPE_FORMAT = "Shape format may lost when saving as XLSX.";

    static final String XLSX_UNSUPPORTED_TABLE_SHAPES = "Shapes in tables are not supported when saving as XLSX.";
    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Docling JSON strings
    static final String DOCLING_OMITTED_HEADERS_FOOTERS = "Headers and footers are omitted when saving as Docling JSON.";

    static final String DOCLING_UNSUPPORTED_SHAPE_TYPE = "This shape type is not supported when saving as Docling JSON.";
    //<<<<<<<< #endregion 
}

