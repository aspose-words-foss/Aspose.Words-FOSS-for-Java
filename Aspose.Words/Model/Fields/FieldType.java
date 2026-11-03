// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/04/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



// NOTE: FieldUtil.GetFieldType() should be updated after each change of this enum.

/// <summary>
/// Specifies Microsoft Word field types.
/// </summary>
public /*enum*/ final class FieldType
{
    private FieldType(){}
    
    /// <summary>
    /// Field type is not specified or unknown.
    /// </summary>
    public static final int FIELD_NONE = 0;
    /// <summary>
    /// Specifies that the field was unable to be parsed.
    /// </summary>
    public static final int FIELD_CANNOT_PARSE = 1;
    /// <summary>
    /// Specifies the ADDIN field.
    /// </summary>
    public static final int FIELD_ADDIN = 81;
    /// <summary>
    /// Specifies the ADDRESSBLOCK field.
    /// </summary>
    public static final int FIELD_ADDRESS_BLOCK = 93;
    /// <summary>
    /// Specifies the ADVANCE field.
    /// </summary>
    public static final int FIELD_ADVANCE = 84;
    /// <summary>
    /// Specifies the ASK field.
    /// </summary>
    public static final int FIELD_ASK = 38;
    /// <summary>
    /// Specifies the AUTHOR field.
    /// </summary>
    public static final int FIELD_AUTHOR = 17;
    /// <summary>
    /// Specifies the AUTONUM field.
    /// </summary>
    public static final int FIELD_AUTO_NUM = 54;
    /// <summary>
    /// Specifies the AUTONUMLGL field.
    /// </summary>
    public static final int FIELD_AUTO_NUM_LEGAL = 53;
    /// <summary>
    /// Specifies the AUTONUMOUT field.
    /// </summary>
    public static final int FIELD_AUTO_NUM_OUTLINE = 52;
    /// <summary>
    /// Specifies the AUTOTEXT field.
    /// </summary>
    public static final int FIELD_AUTO_TEXT = 79;
    /// <summary>
    /// Specifies the AUTOTEXTLIST field.
    /// </summary>
    public static final int FIELD_AUTO_TEXT_LIST = 89;
    /// <summary>
    /// Specifies the BARCODE field.
    /// </summary>
    public static final int FIELD_BARCODE = 63;
    /// <summary>
    /// Specifies the BIBLIOGRAPHY field.
    /// </summary>
    public static final int FIELD_BIBLIOGRAPHY = 100500;
    /// <summary>
    /// Specifies the BIDIOUTLINE field.
    /// </summary>
    public static final int FIELD_BIDI_OUTLINE = 92;
    /// <summary>
    /// Specifies the CITATION field.
    /// </summary>
    public static final int FIELD_CITATION = 1980;
    /// <summary>
    /// Specifies the COMMENTS field.
    /// </summary>
    public static final int FIELD_COMMENTS = 19;
    /// <summary>
    /// Specifies the COMPARE field.
    /// </summary>
    public static final int FIELD_COMPARE = 80;
    /// <summary>
    /// Specifies the CREATEDATE field.
    /// </summary>
    public static final int FIELD_CREATE_DATE = 21;
    /// <summary>
    /// Specifies the DATA field.
    /// </summary>
    public static final int FIELD_DATA = 40;
    /// <summary>
    /// Specifies the DATABASE field.
    /// </summary>
    public static final int FIELD_DATABASE = 78;
    /// <summary>
    /// Specifies the DATE field.
    /// </summary>
    public static final int FIELD_DATE = 31;
    /// <summary>
    /// Specifies the DDE field.
    /// </summary>
    public static final int FIELD_DDE = 45;
    /// <summary>
    /// Specifies the DISPLAYBARCODE field.
    /// </summary>
    public static final int FIELD_DISPLAY_BARCODE = 6301;
    /// <summary>
    /// Specifies the MERGEBARCODE field.
    /// </summary>
    public static final int FIELD_MERGE_BARCODE = 6302;
    /// <summary>
    /// Specifies the DDEAUTO field.
    /// </summary>
    public static final int FIELD_DDE_AUTO = 46;
    /// <summary>
    /// Specifies the DOCPROPERTY field.
    /// </summary>
    public static final int FIELD_DOC_PROPERTY = 85;
    /// <summary>
    /// Specifies the DOCVARIABLE field.
    /// </summary>
    public static final int FIELD_DOC_VARIABLE = 64;
    /// <summary>
    /// Specifies the EDITTIME field.
    /// </summary>
    public static final int FIELD_EDIT_TIME = 25;
    /// <summary>
    /// Specifies the EMBED field.
    /// </summary>
    public static final int FIELD_EMBED = 58;
    /// <summary>
    /// Specifies the EQ field.
    /// </summary>
    public static final int FIELD_EQUATION = 49;
    /// <summary>
    /// Specifies the FILENAME field.
    /// </summary>
    public static final int FIELD_FILE_NAME = 29;
    /// <summary>
    /// Specifies the FILESIZE field.
    /// </summary>
    public static final int FIELD_FILE_SIZE = 69;
    /// <summary>
    /// Specifies the FILLIN field.
    /// </summary>
    public static final int FIELD_FILL_IN = 39;
    /// <summary>
    /// Specifies the FOOTNOTEREF field.
    /// </summary>
    public static final int FIELD_FOOTNOTE_REF = 5;
    /// <summary>
    /// Specifies the FORMCHECKBOX field.
    /// </summary>
    public static final int FIELD_FORM_CHECK_BOX = 71;
    /// <summary>
    /// Specifies the FORMDROPDOWN field.
    /// </summary>
    public static final int FIELD_FORM_DROP_DOWN = 83;
    /// <summary>
    /// Specifies the FORMTEXT field.
    /// </summary>
    public static final int FIELD_FORM_TEXT_INPUT = 70;
    /// <summary>
    /// Specifies the = (formula) field.
    /// </summary>
    public static final int FIELD_FORMULA = 34;
    /// <summary>
    /// Specifies the GREETINGLINE field.
    /// </summary>
    public static final int FIELD_GREETING_LINE = 94;
    /// <summary>
    /// Specifies the GLOSSARY field.
    /// </summary>
    public static final int FIELD_GLOSSARY = 47;
    /// <summary>
    /// Specifies the GOTOBUTTON field.
    /// </summary>
    public static final int FIELD_GO_TO_BUTTON = 50;
    /// <summary>
    /// Specifies the field that represents an HTML control.
    /// </summary>
    public static final int FIELD_HTML_ACTIVE_X = 91;
    /// <summary>
    /// Specifies the HYPERLINK field.
    /// </summary>
    public static final int FIELD_HYPERLINK = 88;
    /// <summary>
    /// Specifies the IF field.
    /// </summary>
    public static final int FIELD_IF = 7;
    /// <summary>
    /// Specifies the INCLUDE field.
    /// </summary>
    public static final int FIELD_INCLUDE = 36;
    /// <summary>
    /// Specifies the INCLUDEPICTURE field.
    /// </summary>
    public static final int FIELD_INCLUDE_PICTURE = 67;
    /// <summary>
    /// Specifies the INCLUDETEXT field.
    /// </summary>
    public static final int FIELD_INCLUDE_TEXT = 68;
    /// <summary>
    /// Specifies the INDEX field.
    /// </summary>
    public static final int FIELD_INDEX = 8;
    /// <summary>
    /// Specifies the XE field.
    /// </summary>
    public static final int FIELD_INDEX_ENTRY = 4;
    /// <summary>
    /// Specifies the INFO field.
    /// </summary>
    public static final int FIELD_INFO = 14;
    /// <summary>
    /// Specifies the IMPORT field.
    /// </summary>
    public static final int FIELD_IMPORT = 55;
    /// <summary>
    /// Specifies the KEYWORDS field.
    /// </summary>
    public static final int FIELD_KEYWORD = 18;
    /// <summary>
    /// Specifies the LASTSAVEDBY field.
    /// </summary>
    public static final int FIELD_LAST_SAVED_BY = 20;
    /// <summary>
    /// Specifies the LINK field.
    /// </summary>
    public static final int FIELD_LINK = 56;
    /// <summary>
    /// Specifies the LISTNUM field.
    /// </summary>
    public static final int FIELD_LIST_NUM = 90;
    // <summary>
    // Undocumented.
    // </summary>
    // FieldMacro = 76,
    /// <summary>
    /// Specifies the MACROBUTTON field.
    /// </summary>
    public static final int FIELD_MACRO_BUTTON = 51;
    /// <summary>
    /// Specifies the MERGEFIELD field.
    /// </summary>
    public static final int FIELD_MERGE_FIELD = 59;
    /// <summary>
    /// Specifies the MERGEREC field.
    /// </summary>
    public static final int FIELD_MERGE_REC = 44;
    /// <summary>
    /// Specifies the MERGESEQ field.
    /// </summary>
    public static final int FIELD_MERGE_SEQ = 75;
    /// <summary>
    /// Specifies the NEXT field.
    /// </summary>
    public static final int FIELD_NEXT = 41;
    /// <summary>
    /// Specifies the NEXTIF field.
    /// </summary>
    public static final int FIELD_NEXT_IF = 42;
    /// <summary>
    /// Specifies the NOTEREF field.
    /// </summary>
    public static final int FIELD_NOTE_REF = 72;
    /// <summary>
    /// Specifies the NUMCHARS field.
    /// </summary>
    public static final int FIELD_NUM_CHARS = 28;
    /// <summary>
    /// Specifies the NUMPAGES field.
    /// </summary>
    public static final int FIELD_NUM_PAGES = 26;
    /// <summary>
    /// Specifies the NUMWORDS field.
    /// </summary>
    public static final int FIELD_NUM_WORDS = 27;
    /// <summary>
    /// <p>Specifies the OCX field.</p>
    /// <p>Normally, Aspose.Words will represent an ActiveX control as a
    /// <see cref="Aspose.Words.Drawing.Shape"/> object, but for some documents, where a control does
    /// not have data and/or seems to be invalid, it will be represented as a field.</p>
    /// </summary>
    public static final int FIELD_OCX = 87;
    /// <summary>
    /// Specifies the PAGE field.
    /// </summary>
    public static final int FIELD_PAGE = 33;
    /// <summary>
    /// Specifies the PAGEREF field.
    /// </summary>
    public static final int FIELD_PAGE_REF = 37;
    /// <summary>
    /// Specifies the PRINT field.
    /// </summary>
    public static final int FIELD_PRINT = 48;
    /// <summary>
    /// Specifies the PRINTDATE field.
    /// </summary>
    public static final int FIELD_PRINT_DATE = 23;
    /// <summary>
    /// Specifies the PRIVATE field.
    /// </summary>
    public static final int FIELD_PRIVATE = 77;
    /// <summary>
    /// Specifies the QUOTE field.
    /// </summary>
    public static final int FIELD_QUOTE = 35;
    /// <summary>
    /// Specifies the REF field.
    /// </summary>
    public static final int FIELD_REF = 3;
    /// <summary>
    /// Specifies that the field represents a REF field where the keyword has been omitted.
    /// </summary>
    public static final int FIELD_REF_NO_KEYWORD = 2;
    /// <summary>
    /// Specifies the RD field.
    /// </summary>
    public static final int FIELD_REF_DOC = 11;
    /// <summary>
    /// Specifies the REVNUM field.
    /// </summary>
    public static final int FIELD_REVISION_NUM = 24;
    /// <summary>
    /// Specifies the SAVEDATE field.
    /// </summary>
    public static final int FIELD_SAVE_DATE = 22;
    /// <summary>
    /// Specifies the SECTION field.
    /// </summary>
    public static final int FIELD_SECTION = 65;
    /// <summary>
    /// Specifies the SECTIONPAGES field.
    /// </summary>
    public static final int FIELD_SECTION_PAGES = 66;
    /// <summary>
    /// Specifies the SEQ field.
    /// </summary>
    public static final int FIELD_SEQUENCE = 12;
    /// <summary>
    /// Specifies the SET field.
    /// </summary>
    public static final int FIELD_SET = 6;
    /// <summary>
    /// Specifies the SHAPE field.
    /// </summary>
    public static final int FIELD_SHAPE = 95;
    /// <summary>
    /// Specifies the SKIPIF field.
    /// </summary>
    public static final int FIELD_SKIP_IF = 43;
    /// <summary>
    /// Specifies the STYLEREF field.
    /// </summary>
    public static final int FIELD_STYLE_REF = 10;
    /// <summary>
    /// Specifies the SUBJECT field.
    /// </summary>
    public static final int FIELD_SUBJECT = 16;
    // <summary>
    // Undocumented.
    // </summary>
    // FieldSubscriber = 82,
    /// <summary>
    /// Specifies the SYMBOL field.
    /// </summary>
    public static final int FIELD_SYMBOL = 57;
    /// <summary>
    /// Specifies the TEMPLATE field.
    /// </summary>
    public static final int FIELD_TEMPLATE = 30;
    /// <summary>
    /// Specifies the TIME field.
    /// </summary>
    public static final int FIELD_TIME = 32;
    /// <summary>
    /// Specifies the TITLE field.
    /// </summary>
    public static final int FIELD_TITLE = 15;
    /// <summary>
    /// Specifies the TOA field.
    /// </summary>
    public static final int FIELD_TOA = 73;
    /// <summary>
    /// Specifies the TA field.
    /// </summary>
    public static final int FIELD_TOA_ENTRY = 74;
    /// <summary>
    /// Specifies the TOC field.
    /// </summary>
    public static final int FIELD_TOC = 13;
    /// <summary>
    /// Specifies the TC field.
    /// </summary>
    public static final int FIELD_TOC_ENTRY = 9;
    /// <summary>
    /// Specifies the USERADDRESS field.
    /// </summary>
    public static final int FIELD_USER_ADDRESS = 62;
    /// <summary>
    /// Specifies the USERINITIALS field.
    /// </summary>
    public static final int FIELD_USER_INITIALS = 61;
    /// <summary>
    /// Specifies the USERNAME field.
    /// </summary>
    public static final int FIELD_USER_NAME = 60;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldType value.
    /// </summary>
    public static String getName(/*FieldType*/int fieldType)
    {
    	switch (fieldType)
    	{
    		case FIELD_NONE: return "FIELD_NONE";
    		case FIELD_CANNOT_PARSE: return "FIELD_CANNOT_PARSE";
    		case FIELD_ADDIN: return "FIELD_ADDIN";
    		case FIELD_ADDRESS_BLOCK: return "FIELD_ADDRESS_BLOCK";
    		case FIELD_ADVANCE: return "FIELD_ADVANCE";
    		case FIELD_ASK: return "FIELD_ASK";
    		case FIELD_AUTHOR: return "FIELD_AUTHOR";
    		case FIELD_AUTO_NUM: return "FIELD_AUTO_NUM";
    		case FIELD_AUTO_NUM_LEGAL: return "FIELD_AUTO_NUM_LEGAL";
    		case FIELD_AUTO_NUM_OUTLINE: return "FIELD_AUTO_NUM_OUTLINE";
    		case FIELD_AUTO_TEXT: return "FIELD_AUTO_TEXT";
    		case FIELD_AUTO_TEXT_LIST: return "FIELD_AUTO_TEXT_LIST";
    		case FIELD_BARCODE: return "FIELD_BARCODE";
    		case FIELD_BIBLIOGRAPHY: return "FIELD_BIBLIOGRAPHY";
    		case FIELD_BIDI_OUTLINE: return "FIELD_BIDI_OUTLINE";
    		case FIELD_CITATION: return "FIELD_CITATION";
    		case FIELD_COMMENTS: return "FIELD_COMMENTS";
    		case FIELD_COMPARE: return "FIELD_COMPARE";
    		case FIELD_CREATE_DATE: return "FIELD_CREATE_DATE";
    		case FIELD_DATA: return "FIELD_DATA";
    		case FIELD_DATABASE: return "FIELD_DATABASE";
    		case FIELD_DATE: return "FIELD_DATE";
    		case FIELD_DDE: return "FIELD_DDE";
    		case FIELD_DISPLAY_BARCODE: return "FIELD_DISPLAY_BARCODE";
    		case FIELD_MERGE_BARCODE: return "FIELD_MERGE_BARCODE";
    		case FIELD_DDE_AUTO: return "FIELD_DDE_AUTO";
    		case FIELD_DOC_PROPERTY: return "FIELD_DOC_PROPERTY";
    		case FIELD_DOC_VARIABLE: return "FIELD_DOC_VARIABLE";
    		case FIELD_EDIT_TIME: return "FIELD_EDIT_TIME";
    		case FIELD_EMBED: return "FIELD_EMBED";
    		case FIELD_EQUATION: return "FIELD_EQUATION";
    		case FIELD_FILE_NAME: return "FIELD_FILE_NAME";
    		case FIELD_FILE_SIZE: return "FIELD_FILE_SIZE";
    		case FIELD_FILL_IN: return "FIELD_FILL_IN";
    		case FIELD_FOOTNOTE_REF: return "FIELD_FOOTNOTE_REF";
    		case FIELD_FORM_CHECK_BOX: return "FIELD_FORM_CHECK_BOX";
    		case FIELD_FORM_DROP_DOWN: return "FIELD_FORM_DROP_DOWN";
    		case FIELD_FORM_TEXT_INPUT: return "FIELD_FORM_TEXT_INPUT";
    		case FIELD_FORMULA: return "FIELD_FORMULA";
    		case FIELD_GREETING_LINE: return "FIELD_GREETING_LINE";
    		case FIELD_GLOSSARY: return "FIELD_GLOSSARY";
    		case FIELD_GO_TO_BUTTON: return "FIELD_GO_TO_BUTTON";
    		case FIELD_HTML_ACTIVE_X: return "FIELD_HTML_ACTIVE_X";
    		case FIELD_HYPERLINK: return "FIELD_HYPERLINK";
    		case FIELD_IF: return "FIELD_IF";
    		case FIELD_INCLUDE: return "FIELD_INCLUDE";
    		case FIELD_INCLUDE_PICTURE: return "FIELD_INCLUDE_PICTURE";
    		case FIELD_INCLUDE_TEXT: return "FIELD_INCLUDE_TEXT";
    		case FIELD_INDEX: return "FIELD_INDEX";
    		case FIELD_INDEX_ENTRY: return "FIELD_INDEX_ENTRY";
    		case FIELD_INFO: return "FIELD_INFO";
    		case FIELD_IMPORT: return "FIELD_IMPORT";
    		case FIELD_KEYWORD: return "FIELD_KEYWORD";
    		case FIELD_LAST_SAVED_BY: return "FIELD_LAST_SAVED_BY";
    		case FIELD_LINK: return "FIELD_LINK";
    		case FIELD_LIST_NUM: return "FIELD_LIST_NUM";
    		case FIELD_MACRO_BUTTON: return "FIELD_MACRO_BUTTON";
    		case FIELD_MERGE_FIELD: return "FIELD_MERGE_FIELD";
    		case FIELD_MERGE_REC: return "FIELD_MERGE_REC";
    		case FIELD_MERGE_SEQ: return "FIELD_MERGE_SEQ";
    		case FIELD_NEXT: return "FIELD_NEXT";
    		case FIELD_NEXT_IF: return "FIELD_NEXT_IF";
    		case FIELD_NOTE_REF: return "FIELD_NOTE_REF";
    		case FIELD_NUM_CHARS: return "FIELD_NUM_CHARS";
    		case FIELD_NUM_PAGES: return "FIELD_NUM_PAGES";
    		case FIELD_NUM_WORDS: return "FIELD_NUM_WORDS";
    		case FIELD_OCX: return "FIELD_OCX";
    		case FIELD_PAGE: return "FIELD_PAGE";
    		case FIELD_PAGE_REF: return "FIELD_PAGE_REF";
    		case FIELD_PRINT: return "FIELD_PRINT";
    		case FIELD_PRINT_DATE: return "FIELD_PRINT_DATE";
    		case FIELD_PRIVATE: return "FIELD_PRIVATE";
    		case FIELD_QUOTE: return "FIELD_QUOTE";
    		case FIELD_REF: return "FIELD_REF";
    		case FIELD_REF_NO_KEYWORD: return "FIELD_REF_NO_KEYWORD";
    		case FIELD_REF_DOC: return "FIELD_REF_DOC";
    		case FIELD_REVISION_NUM: return "FIELD_REVISION_NUM";
    		case FIELD_SAVE_DATE: return "FIELD_SAVE_DATE";
    		case FIELD_SECTION: return "FIELD_SECTION";
    		case FIELD_SECTION_PAGES: return "FIELD_SECTION_PAGES";
    		case FIELD_SEQUENCE: return "FIELD_SEQUENCE";
    		case FIELD_SET: return "FIELD_SET";
    		case FIELD_SHAPE: return "FIELD_SHAPE";
    		case FIELD_SKIP_IF: return "FIELD_SKIP_IF";
    		case FIELD_STYLE_REF: return "FIELD_STYLE_REF";
    		case FIELD_SUBJECT: return "FIELD_SUBJECT";
    		case FIELD_SYMBOL: return "FIELD_SYMBOL";
    		case FIELD_TEMPLATE: return "FIELD_TEMPLATE";
    		case FIELD_TIME: return "FIELD_TIME";
    		case FIELD_TITLE: return "FIELD_TITLE";
    		case FIELD_TOA: return "FIELD_TOA";
    		case FIELD_TOA_ENTRY: return "FIELD_TOA_ENTRY";
    		case FIELD_TOC: return "FIELD_TOC";
    		case FIELD_TOC_ENTRY: return "FIELD_TOC_ENTRY";
    		case FIELD_USER_ADDRESS: return "FIELD_USER_ADDRESS";
    		case FIELD_USER_INITIALS: return "FIELD_USER_INITIALS";
    		case FIELD_USER_NAME: return "FIELD_USER_NAME";
    		default: return "Unknown FieldType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldType value.
    /// </summary>
    public static String toString(/*FieldType*/int fieldType)
    {
    	switch (fieldType)
    	{
    		case FIELD_NONE: return "FieldNone";
    		case FIELD_CANNOT_PARSE: return "FieldCannotParse";
    		case FIELD_ADDIN: return "FieldAddin";
    		case FIELD_ADDRESS_BLOCK: return "FieldAddressBlock";
    		case FIELD_ADVANCE: return "FieldAdvance";
    		case FIELD_ASK: return "FieldAsk";
    		case FIELD_AUTHOR: return "FieldAuthor";
    		case FIELD_AUTO_NUM: return "FieldAutoNum";
    		case FIELD_AUTO_NUM_LEGAL: return "FieldAutoNumLegal";
    		case FIELD_AUTO_NUM_OUTLINE: return "FieldAutoNumOutline";
    		case FIELD_AUTO_TEXT: return "FieldAutoText";
    		case FIELD_AUTO_TEXT_LIST: return "FieldAutoTextList";
    		case FIELD_BARCODE: return "FieldBarcode";
    		case FIELD_BIBLIOGRAPHY: return "FieldBibliography";
    		case FIELD_BIDI_OUTLINE: return "FieldBidiOutline";
    		case FIELD_CITATION: return "FieldCitation";
    		case FIELD_COMMENTS: return "FieldComments";
    		case FIELD_COMPARE: return "FieldCompare";
    		case FIELD_CREATE_DATE: return "FieldCreateDate";
    		case FIELD_DATA: return "FieldData";
    		case FIELD_DATABASE: return "FieldDatabase";
    		case FIELD_DATE: return "FieldDate";
    		case FIELD_DDE: return "FieldDDE";
    		case FIELD_DISPLAY_BARCODE: return "FieldDisplayBarcode";
    		case FIELD_MERGE_BARCODE: return "FieldMergeBarcode";
    		case FIELD_DDE_AUTO: return "FieldDDEAuto";
    		case FIELD_DOC_PROPERTY: return "FieldDocProperty";
    		case FIELD_DOC_VARIABLE: return "FieldDocVariable";
    		case FIELD_EDIT_TIME: return "FieldEditTime";
    		case FIELD_EMBED: return "FieldEmbed";
    		case FIELD_EQUATION: return "FieldEquation";
    		case FIELD_FILE_NAME: return "FieldFileName";
    		case FIELD_FILE_SIZE: return "FieldFileSize";
    		case FIELD_FILL_IN: return "FieldFillIn";
    		case FIELD_FOOTNOTE_REF: return "FieldFootnoteRef";
    		case FIELD_FORM_CHECK_BOX: return "FieldFormCheckBox";
    		case FIELD_FORM_DROP_DOWN: return "FieldFormDropDown";
    		case FIELD_FORM_TEXT_INPUT: return "FieldFormTextInput";
    		case FIELD_FORMULA: return "FieldFormula";
    		case FIELD_GREETING_LINE: return "FieldGreetingLine";
    		case FIELD_GLOSSARY: return "FieldGlossary";
    		case FIELD_GO_TO_BUTTON: return "FieldGoToButton";
    		case FIELD_HTML_ACTIVE_X: return "FieldHtmlActiveX";
    		case FIELD_HYPERLINK: return "FieldHyperlink";
    		case FIELD_IF: return "FieldIf";
    		case FIELD_INCLUDE: return "FieldInclude";
    		case FIELD_INCLUDE_PICTURE: return "FieldIncludePicture";
    		case FIELD_INCLUDE_TEXT: return "FieldIncludeText";
    		case FIELD_INDEX: return "FieldIndex";
    		case FIELD_INDEX_ENTRY: return "FieldIndexEntry";
    		case FIELD_INFO: return "FieldInfo";
    		case FIELD_IMPORT: return "FieldImport";
    		case FIELD_KEYWORD: return "FieldKeyword";
    		case FIELD_LAST_SAVED_BY: return "FieldLastSavedBy";
    		case FIELD_LINK: return "FieldLink";
    		case FIELD_LIST_NUM: return "FieldListNum";
    		case FIELD_MACRO_BUTTON: return "FieldMacroButton";
    		case FIELD_MERGE_FIELD: return "FieldMergeField";
    		case FIELD_MERGE_REC: return "FieldMergeRec";
    		case FIELD_MERGE_SEQ: return "FieldMergeSeq";
    		case FIELD_NEXT: return "FieldNext";
    		case FIELD_NEXT_IF: return "FieldNextIf";
    		case FIELD_NOTE_REF: return "FieldNoteRef";
    		case FIELD_NUM_CHARS: return "FieldNumChars";
    		case FIELD_NUM_PAGES: return "FieldNumPages";
    		case FIELD_NUM_WORDS: return "FieldNumWords";
    		case FIELD_OCX: return "FieldOcx";
    		case FIELD_PAGE: return "FieldPage";
    		case FIELD_PAGE_REF: return "FieldPageRef";
    		case FIELD_PRINT: return "FieldPrint";
    		case FIELD_PRINT_DATE: return "FieldPrintDate";
    		case FIELD_PRIVATE: return "FieldPrivate";
    		case FIELD_QUOTE: return "FieldQuote";
    		case FIELD_REF: return "FieldRef";
    		case FIELD_REF_NO_KEYWORD: return "FieldRefNoKeyword";
    		case FIELD_REF_DOC: return "FieldRefDoc";
    		case FIELD_REVISION_NUM: return "FieldRevisionNum";
    		case FIELD_SAVE_DATE: return "FieldSaveDate";
    		case FIELD_SECTION: return "FieldSection";
    		case FIELD_SECTION_PAGES: return "FieldSectionPages";
    		case FIELD_SEQUENCE: return "FieldSequence";
    		case FIELD_SET: return "FieldSet";
    		case FIELD_SHAPE: return "FieldShape";
    		case FIELD_SKIP_IF: return "FieldSkipIf";
    		case FIELD_STYLE_REF: return "FieldStyleRef";
    		case FIELD_SUBJECT: return "FieldSubject";
    		case FIELD_SYMBOL: return "FieldSymbol";
    		case FIELD_TEMPLATE: return "FieldTemplate";
    		case FIELD_TIME: return "FieldTime";
    		case FIELD_TITLE: return "FieldTitle";
    		case FIELD_TOA: return "FieldTOA";
    		case FIELD_TOA_ENTRY: return "FieldTOAEntry";
    		case FIELD_TOC: return "FieldTOC";
    		case FIELD_TOC_ENTRY: return "FieldTOCEntry";
    		case FIELD_USER_ADDRESS: return "FieldUserAddress";
    		case FIELD_USER_INITIALS: return "FieldUserInitials";
    		case FIELD_USER_NAME: return "FieldUserName";
    		default: return "Unknown FieldType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldType name.
    /// </summary>
    public static /*FieldType*/int fromName(String fieldTypeName)
    {
    	if ("FIELD_NONE".equals(fieldTypeName)) return FIELD_NONE;
    	if ("FIELD_CANNOT_PARSE".equals(fieldTypeName)) return FIELD_CANNOT_PARSE;
    	if ("FIELD_ADDIN".equals(fieldTypeName)) return FIELD_ADDIN;
    	if ("FIELD_ADDRESS_BLOCK".equals(fieldTypeName)) return FIELD_ADDRESS_BLOCK;
    	if ("FIELD_ADVANCE".equals(fieldTypeName)) return FIELD_ADVANCE;
    	if ("FIELD_ASK".equals(fieldTypeName)) return FIELD_ASK;
    	if ("FIELD_AUTHOR".equals(fieldTypeName)) return FIELD_AUTHOR;
    	if ("FIELD_AUTO_NUM".equals(fieldTypeName)) return FIELD_AUTO_NUM;
    	if ("FIELD_AUTO_NUM_LEGAL".equals(fieldTypeName)) return FIELD_AUTO_NUM_LEGAL;
    	if ("FIELD_AUTO_NUM_OUTLINE".equals(fieldTypeName)) return FIELD_AUTO_NUM_OUTLINE;
    	if ("FIELD_AUTO_TEXT".equals(fieldTypeName)) return FIELD_AUTO_TEXT;
    	if ("FIELD_AUTO_TEXT_LIST".equals(fieldTypeName)) return FIELD_AUTO_TEXT_LIST;
    	if ("FIELD_BARCODE".equals(fieldTypeName)) return FIELD_BARCODE;
    	if ("FIELD_BIBLIOGRAPHY".equals(fieldTypeName)) return FIELD_BIBLIOGRAPHY;
    	if ("FIELD_BIDI_OUTLINE".equals(fieldTypeName)) return FIELD_BIDI_OUTLINE;
    	if ("FIELD_CITATION".equals(fieldTypeName)) return FIELD_CITATION;
    	if ("FIELD_COMMENTS".equals(fieldTypeName)) return FIELD_COMMENTS;
    	if ("FIELD_COMPARE".equals(fieldTypeName)) return FIELD_COMPARE;
    	if ("FIELD_CREATE_DATE".equals(fieldTypeName)) return FIELD_CREATE_DATE;
    	if ("FIELD_DATA".equals(fieldTypeName)) return FIELD_DATA;
    	if ("FIELD_DATABASE".equals(fieldTypeName)) return FIELD_DATABASE;
    	if ("FIELD_DATE".equals(fieldTypeName)) return FIELD_DATE;
    	if ("FIELD_DDE".equals(fieldTypeName)) return FIELD_DDE;
    	if ("FIELD_DISPLAY_BARCODE".equals(fieldTypeName)) return FIELD_DISPLAY_BARCODE;
    	if ("FIELD_MERGE_BARCODE".equals(fieldTypeName)) return FIELD_MERGE_BARCODE;
    	if ("FIELD_DDE_AUTO".equals(fieldTypeName)) return FIELD_DDE_AUTO;
    	if ("FIELD_DOC_PROPERTY".equals(fieldTypeName)) return FIELD_DOC_PROPERTY;
    	if ("FIELD_DOC_VARIABLE".equals(fieldTypeName)) return FIELD_DOC_VARIABLE;
    	if ("FIELD_EDIT_TIME".equals(fieldTypeName)) return FIELD_EDIT_TIME;
    	if ("FIELD_EMBED".equals(fieldTypeName)) return FIELD_EMBED;
    	if ("FIELD_EQUATION".equals(fieldTypeName)) return FIELD_EQUATION;
    	if ("FIELD_FILE_NAME".equals(fieldTypeName)) return FIELD_FILE_NAME;
    	if ("FIELD_FILE_SIZE".equals(fieldTypeName)) return FIELD_FILE_SIZE;
    	if ("FIELD_FILL_IN".equals(fieldTypeName)) return FIELD_FILL_IN;
    	if ("FIELD_FOOTNOTE_REF".equals(fieldTypeName)) return FIELD_FOOTNOTE_REF;
    	if ("FIELD_FORM_CHECK_BOX".equals(fieldTypeName)) return FIELD_FORM_CHECK_BOX;
    	if ("FIELD_FORM_DROP_DOWN".equals(fieldTypeName)) return FIELD_FORM_DROP_DOWN;
    	if ("FIELD_FORM_TEXT_INPUT".equals(fieldTypeName)) return FIELD_FORM_TEXT_INPUT;
    	if ("FIELD_FORMULA".equals(fieldTypeName)) return FIELD_FORMULA;
    	if ("FIELD_GREETING_LINE".equals(fieldTypeName)) return FIELD_GREETING_LINE;
    	if ("FIELD_GLOSSARY".equals(fieldTypeName)) return FIELD_GLOSSARY;
    	if ("FIELD_GO_TO_BUTTON".equals(fieldTypeName)) return FIELD_GO_TO_BUTTON;
    	if ("FIELD_HTML_ACTIVE_X".equals(fieldTypeName)) return FIELD_HTML_ACTIVE_X;
    	if ("FIELD_HYPERLINK".equals(fieldTypeName)) return FIELD_HYPERLINK;
    	if ("FIELD_IF".equals(fieldTypeName)) return FIELD_IF;
    	if ("FIELD_INCLUDE".equals(fieldTypeName)) return FIELD_INCLUDE;
    	if ("FIELD_INCLUDE_PICTURE".equals(fieldTypeName)) return FIELD_INCLUDE_PICTURE;
    	if ("FIELD_INCLUDE_TEXT".equals(fieldTypeName)) return FIELD_INCLUDE_TEXT;
    	if ("FIELD_INDEX".equals(fieldTypeName)) return FIELD_INDEX;
    	if ("FIELD_INDEX_ENTRY".equals(fieldTypeName)) return FIELD_INDEX_ENTRY;
    	if ("FIELD_INFO".equals(fieldTypeName)) return FIELD_INFO;
    	if ("FIELD_IMPORT".equals(fieldTypeName)) return FIELD_IMPORT;
    	if ("FIELD_KEYWORD".equals(fieldTypeName)) return FIELD_KEYWORD;
    	if ("FIELD_LAST_SAVED_BY".equals(fieldTypeName)) return FIELD_LAST_SAVED_BY;
    	if ("FIELD_LINK".equals(fieldTypeName)) return FIELD_LINK;
    	if ("FIELD_LIST_NUM".equals(fieldTypeName)) return FIELD_LIST_NUM;
    	if ("FIELD_MACRO_BUTTON".equals(fieldTypeName)) return FIELD_MACRO_BUTTON;
    	if ("FIELD_MERGE_FIELD".equals(fieldTypeName)) return FIELD_MERGE_FIELD;
    	if ("FIELD_MERGE_REC".equals(fieldTypeName)) return FIELD_MERGE_REC;
    	if ("FIELD_MERGE_SEQ".equals(fieldTypeName)) return FIELD_MERGE_SEQ;
    	if ("FIELD_NEXT".equals(fieldTypeName)) return FIELD_NEXT;
    	if ("FIELD_NEXT_IF".equals(fieldTypeName)) return FIELD_NEXT_IF;
    	if ("FIELD_NOTE_REF".equals(fieldTypeName)) return FIELD_NOTE_REF;
    	if ("FIELD_NUM_CHARS".equals(fieldTypeName)) return FIELD_NUM_CHARS;
    	if ("FIELD_NUM_PAGES".equals(fieldTypeName)) return FIELD_NUM_PAGES;
    	if ("FIELD_NUM_WORDS".equals(fieldTypeName)) return FIELD_NUM_WORDS;
    	if ("FIELD_OCX".equals(fieldTypeName)) return FIELD_OCX;
    	if ("FIELD_PAGE".equals(fieldTypeName)) return FIELD_PAGE;
    	if ("FIELD_PAGE_REF".equals(fieldTypeName)) return FIELD_PAGE_REF;
    	if ("FIELD_PRINT".equals(fieldTypeName)) return FIELD_PRINT;
    	if ("FIELD_PRINT_DATE".equals(fieldTypeName)) return FIELD_PRINT_DATE;
    	if ("FIELD_PRIVATE".equals(fieldTypeName)) return FIELD_PRIVATE;
    	if ("FIELD_QUOTE".equals(fieldTypeName)) return FIELD_QUOTE;
    	if ("FIELD_REF".equals(fieldTypeName)) return FIELD_REF;
    	if ("FIELD_REF_NO_KEYWORD".equals(fieldTypeName)) return FIELD_REF_NO_KEYWORD;
    	if ("FIELD_REF_DOC".equals(fieldTypeName)) return FIELD_REF_DOC;
    	if ("FIELD_REVISION_NUM".equals(fieldTypeName)) return FIELD_REVISION_NUM;
    	if ("FIELD_SAVE_DATE".equals(fieldTypeName)) return FIELD_SAVE_DATE;
    	if ("FIELD_SECTION".equals(fieldTypeName)) return FIELD_SECTION;
    	if ("FIELD_SECTION_PAGES".equals(fieldTypeName)) return FIELD_SECTION_PAGES;
    	if ("FIELD_SEQUENCE".equals(fieldTypeName)) return FIELD_SEQUENCE;
    	if ("FIELD_SET".equals(fieldTypeName)) return FIELD_SET;
    	if ("FIELD_SHAPE".equals(fieldTypeName)) return FIELD_SHAPE;
    	if ("FIELD_SKIP_IF".equals(fieldTypeName)) return FIELD_SKIP_IF;
    	if ("FIELD_STYLE_REF".equals(fieldTypeName)) return FIELD_STYLE_REF;
    	if ("FIELD_SUBJECT".equals(fieldTypeName)) return FIELD_SUBJECT;
    	if ("FIELD_SYMBOL".equals(fieldTypeName)) return FIELD_SYMBOL;
    	if ("FIELD_TEMPLATE".equals(fieldTypeName)) return FIELD_TEMPLATE;
    	if ("FIELD_TIME".equals(fieldTypeName)) return FIELD_TIME;
    	if ("FIELD_TITLE".equals(fieldTypeName)) return FIELD_TITLE;
    	if ("FIELD_TOA".equals(fieldTypeName)) return FIELD_TOA;
    	if ("FIELD_TOA_ENTRY".equals(fieldTypeName)) return FIELD_TOA_ENTRY;
    	if ("FIELD_TOC".equals(fieldTypeName)) return FIELD_TOC;
    	if ("FIELD_TOC_ENTRY".equals(fieldTypeName)) return FIELD_TOC_ENTRY;
    	if ("FIELD_USER_ADDRESS".equals(fieldTypeName)) return FIELD_USER_ADDRESS;
    	if ("FIELD_USER_INITIALS".equals(fieldTypeName)) return FIELD_USER_INITIALS;
    	if ("FIELD_USER_NAME".equals(fieldTypeName)) return FIELD_USER_NAME;
    	throw new IllegalArgumentException("Unknown FieldType name.");
    }
    
    /// <summary>
    /// Returns array of FieldType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FIELD_NONE,
    		FIELD_CANNOT_PARSE,
    		FIELD_ADDIN,
    		FIELD_ADDRESS_BLOCK,
    		FIELD_ADVANCE,
    		FIELD_ASK,
    		FIELD_AUTHOR,
    		FIELD_AUTO_NUM,
    		FIELD_AUTO_NUM_LEGAL,
    		FIELD_AUTO_NUM_OUTLINE,
    		FIELD_AUTO_TEXT,
    		FIELD_AUTO_TEXT_LIST,
    		FIELD_BARCODE,
    		FIELD_BIBLIOGRAPHY,
    		FIELD_BIDI_OUTLINE,
    		FIELD_CITATION,
    		FIELD_COMMENTS,
    		FIELD_COMPARE,
    		FIELD_CREATE_DATE,
    		FIELD_DATA,
    		FIELD_DATABASE,
    		FIELD_DATE,
    		FIELD_DDE,
    		FIELD_DISPLAY_BARCODE,
    		FIELD_MERGE_BARCODE,
    		FIELD_DDE_AUTO,
    		FIELD_DOC_PROPERTY,
    		FIELD_DOC_VARIABLE,
    		FIELD_EDIT_TIME,
    		FIELD_EMBED,
    		FIELD_EQUATION,
    		FIELD_FILE_NAME,
    		FIELD_FILE_SIZE,
    		FIELD_FILL_IN,
    		FIELD_FOOTNOTE_REF,
    		FIELD_FORM_CHECK_BOX,
    		FIELD_FORM_DROP_DOWN,
    		FIELD_FORM_TEXT_INPUT,
    		FIELD_FORMULA,
    		FIELD_GREETING_LINE,
    		FIELD_GLOSSARY,
    		FIELD_GO_TO_BUTTON,
    		FIELD_HTML_ACTIVE_X,
    		FIELD_HYPERLINK,
    		FIELD_IF,
    		FIELD_INCLUDE,
    		FIELD_INCLUDE_PICTURE,
    		FIELD_INCLUDE_TEXT,
    		FIELD_INDEX,
    		FIELD_INDEX_ENTRY,
    		FIELD_INFO,
    		FIELD_IMPORT,
    		FIELD_KEYWORD,
    		FIELD_LAST_SAVED_BY,
    		FIELD_LINK,
    		FIELD_LIST_NUM,
    		FIELD_MACRO_BUTTON,
    		FIELD_MERGE_FIELD,
    		FIELD_MERGE_REC,
    		FIELD_MERGE_SEQ,
    		FIELD_NEXT,
    		FIELD_NEXT_IF,
    		FIELD_NOTE_REF,
    		FIELD_NUM_CHARS,
    		FIELD_NUM_PAGES,
    		FIELD_NUM_WORDS,
    		FIELD_OCX,
    		FIELD_PAGE,
    		FIELD_PAGE_REF,
    		FIELD_PRINT,
    		FIELD_PRINT_DATE,
    		FIELD_PRIVATE,
    		FIELD_QUOTE,
    		FIELD_REF,
    		FIELD_REF_NO_KEYWORD,
    		FIELD_REF_DOC,
    		FIELD_REVISION_NUM,
    		FIELD_SAVE_DATE,
    		FIELD_SECTION,
    		FIELD_SECTION_PAGES,
    		FIELD_SEQUENCE,
    		FIELD_SET,
    		FIELD_SHAPE,
    		FIELD_SKIP_IF,
    		FIELD_STYLE_REF,
    		FIELD_SUBJECT,
    		FIELD_SYMBOL,
    		FIELD_TEMPLATE,
    		FIELD_TIME,
    		FIELD_TITLE,
    		FIELD_TOA,
    		FIELD_TOA_ENTRY,
    		FIELD_TOC,
    		FIELD_TOC_ENTRY,
    		FIELD_USER_ADDRESS,
    		FIELD_USER_INITIALS,
    		FIELD_USER_NAME,
    	};
    }

    public static final int length = 97;
}

