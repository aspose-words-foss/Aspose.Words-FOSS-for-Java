// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/07/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Creates instances of field classes.
/// </summary>
/*static*/ class FieldFactory
{
	/* Simulation of static class by using private constructor */
	private FieldFactory()
	{}

    /// <summary>
    /// Responsible for creating concrete instances of fields classes for the specified field type enumeration.
    /// </summary>
    static Field createField(FieldStart start, FieldSeparator separator, FieldEnd end)
    {
        Field result = createField(start.getFieldType());
        result.initialize(start, separator, end);
        return result;
    }

    /// <summary>
    /// Responsible for creating concrete instances of fields classes for the specified field type enumeration.
    /// </summary>
    static Field createField(FieldBundle fieldBundle)
    {
        return createField(fieldBundle.getStart(), fieldBundle.getSeparator(), fieldBundle.getEnd());
    }

    private static Field createField(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_NONE:
                return new FieldUnknown();
            case FieldType.FIELD_CANNOT_PARSE:
                throw new UnsupportedOperationException("Should not reach this line.");
            case FieldType.FIELD_ADDIN:
                return new FieldAddIn();
            case FieldType.FIELD_ADDRESS_BLOCK:
                return new FieldAddressBlock();
            case FieldType.FIELD_ADVANCE:
                return new FieldAdvance();
            case FieldType.FIELD_ASK:
                return new FieldAsk();
            case FieldType.FIELD_AUTHOR:
                return new FieldAuthor();
            case FieldType.FIELD_AUTO_NUM:
                return new FieldAutoNum();
            case FieldType.FIELD_AUTO_NUM_LEGAL:
                return new FieldAutoNumLgl();
            case FieldType.FIELD_AUTO_NUM_OUTLINE:
                return new FieldAutoNumOut();
            case FieldType.FIELD_AUTO_TEXT:
                return new FieldAutoText();
            case FieldType.FIELD_AUTO_TEXT_LIST:
                return new FieldAutoTextList();
            case FieldType.FIELD_BARCODE:
                return new FieldBarcode();
            case FieldType.FIELD_BIBLIOGRAPHY:
                return new FieldBibliography();
            case FieldType.FIELD_BIDI_OUTLINE:
                return new FieldBidiOutline();
            case FieldType.FIELD_CITATION:
                return new FieldCitation();
            case FieldType.FIELD_COMMENTS:
                return new FieldComments();
            case FieldType.FIELD_COMPARE:
                return new FieldCompare();
            case FieldType.FIELD_CREATE_DATE:
                return new FieldCreateDate();
            case FieldType.FIELD_DATA:
                return new FieldData();
            case FieldType.FIELD_DATABASE:
                return new FieldDatabase();
            case FieldType.FIELD_DATE:
                return new FieldDate();
            case FieldType.FIELD_DDE:
                return new FieldDde();
            case FieldType.FIELD_DISPLAY_BARCODE:
                return new FieldDisplayBarcode();
            case FieldType.FIELD_MERGE_BARCODE:
                return new FieldMergeBarcode();
            case FieldType.FIELD_DDE_AUTO:
                return new FieldDdeAuto();
            case FieldType.FIELD_DOC_PROPERTY:
                return new FieldDocProperty();
            case FieldType.FIELD_DOC_VARIABLE:
                return new FieldDocVariable();
            case FieldType.FIELD_EDIT_TIME:
                return new FieldEditTime();
            case FieldType.FIELD_EMBED:
                return new FieldEmbed();
            case FieldType.FIELD_EQUATION:
                return new FieldEQ();
            case FieldType.FIELD_FILE_NAME:
                return new FieldFileName();
            case FieldType.FIELD_FILE_SIZE:
                return new FieldFileSize();
            case FieldType.FIELD_FILL_IN:
                return new FieldFillIn();
            case FieldType.FIELD_FOOTNOTE_REF:
                return new FieldFootnoteRef();
            case FieldType.FIELD_FORM_CHECK_BOX:
                return new FieldFormCheckBox();
            case FieldType.FIELD_FORM_DROP_DOWN:
                return new FieldFormDropDown();
            case FieldType.FIELD_FORM_TEXT_INPUT:
                return new FieldFormText();
            case FieldType.FIELD_FORMULA:
                return new FieldFormula();
            case FieldType.FIELD_GREETING_LINE:
                return new FieldGreetingLine();
            case FieldType.FIELD_GLOSSARY:
                return new FieldGlossary();
            case FieldType.FIELD_GO_TO_BUTTON:
                return new FieldGoToButton();
            case FieldType.FIELD_HTML_ACTIVE_X:
                return new FieldHtmlActiveX();
            case FieldType.FIELD_HYPERLINK:
                return new FieldHyperlink();
            case FieldType.FIELD_IF:
                return new FieldIf();
            case FieldType.FIELD_INCLUDE:
                return new FieldInclude();
            case FieldType.FIELD_INCLUDE_PICTURE:
                return new FieldIncludePicture();
            case FieldType.FIELD_INCLUDE_TEXT:
                return new FieldIncludeText();
            case FieldType.FIELD_INDEX:
                return new FieldIndex();
            case FieldType.FIELD_INDEX_ENTRY:
                return new FieldXE();
            case FieldType.FIELD_INFO:
                return new FieldInfo();
            case FieldType.FIELD_IMPORT:
                return new FieldImport();
            case FieldType.FIELD_KEYWORD:
                return new FieldKeywords();
            case FieldType.FIELD_LAST_SAVED_BY:
                return new FieldLastSavedBy();
            case FieldType.FIELD_LINK:
                return new FieldLink();
            case FieldType.FIELD_LIST_NUM:
                return new FieldListNum();
            case FieldType.FIELD_MACRO_BUTTON:
                return new FieldMacroButton();
            case FieldType.FIELD_MERGE_FIELD:
                return new FieldMergeField();
            case FieldType.FIELD_MERGE_REC:
                return new FieldMergeRec();
            case FieldType.FIELD_MERGE_SEQ:
                return new FieldMergeSeq();
            case FieldType.FIELD_NEXT:
                return new FieldNext();
            case FieldType.FIELD_NEXT_IF:
                return new FieldNextIf();
            case FieldType.FIELD_NOTE_REF:
                return new FieldNoteRef();
            case FieldType.FIELD_NUM_CHARS:
                return new FieldNumChars();
            case FieldType.FIELD_NUM_PAGES:
                return new FieldNumPages();
            case FieldType.FIELD_NUM_WORDS:
                return new FieldNumWords();
            case FieldType.FIELD_OCX:
                return new FieldOcx();
            case FieldType.FIELD_PAGE:
                return new FieldPage();
            case FieldType.FIELD_PAGE_REF:
                return new FieldPageRef();
            case FieldType.FIELD_PRINT:
                return new FieldPrint();
            case FieldType.FIELD_PRINT_DATE:
                return new FieldPrintDate();
            case FieldType.FIELD_PRIVATE:
                return new FieldPrivate();
            case FieldType.FIELD_QUOTE:
                return new FieldQuote();
            case FieldType.FIELD_REF:
                return new FieldRef();
            case FieldType.FIELD_REF_NO_KEYWORD:
                return new FieldUnknown();
            case FieldType.FIELD_REF_DOC:
                return new FieldRD();
            case FieldType.FIELD_REVISION_NUM:
                return new FieldRevNum();
            case FieldType.FIELD_SAVE_DATE:
                return new FieldSaveDate();
            case FieldType.FIELD_SECTION:
                return new FieldSection();
            case FieldType.FIELD_SECTION_PAGES:
                return new FieldSectionPages();
            case FieldType.FIELD_SEQUENCE:
                return new FieldSeq();
            case FieldType.FIELD_SET:
                return new FieldSet();
            case FieldType.FIELD_SHAPE:
                return new FieldShape();
            case FieldType.FIELD_SKIP_IF:
                return new FieldSkipIf();
            case FieldType.FIELD_STYLE_REF:
                return new FieldStyleRef();
            case FieldType.FIELD_SUBJECT:
                return new FieldSubject();
            case FieldType.FIELD_SYMBOL:
                return new FieldSymbol();
            case FieldType.FIELD_TEMPLATE:
                return new FieldTemplate();
            case FieldType.FIELD_TIME:
                return new FieldTime();
            case FieldType.FIELD_TITLE:
                return new FieldTitle();
            case FieldType.FIELD_TOA:
                return new FieldToa();
            case FieldType.FIELD_TOA_ENTRY:
                return new FieldTA();
            case FieldType.FIELD_TOC:
                return new FieldToc();
            case FieldType.FIELD_TOC_ENTRY:
                return new FieldTC();
            case FieldType.FIELD_USER_ADDRESS:
                return new FieldUserAddress();
            case FieldType.FIELD_USER_INITIALS:
                return new FieldUserInitials();
            case FieldType.FIELD_USER_NAME:
                return new FieldUserName();
            default:
                throw new IllegalArgumentException();
        }
    }
}

