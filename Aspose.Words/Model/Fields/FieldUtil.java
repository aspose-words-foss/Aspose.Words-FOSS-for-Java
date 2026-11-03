// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.StringToIntDictionary;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.Debug;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msUri;
import com.aspose.UriUtil;
import com.aspose.SystemPal;
import com.aspose.ms.System.IO.File;
import com.aspose.IntToObjDictionary;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Provides field utility functions.
/// </summary>
/*static*/ class FieldUtil
{
	/* Simulation of static class by using private constructor */
	private FieldUtil()
	{}

    /// <summary>
    /// Returns a value indicating whether a field of the specified type requires its document's layout to be built
    /// before its update or not.
    /// </summary>
    static boolean requiresLayoutDocumentOnUpdate(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_INDEX:
            case FieldType.FIELD_NOTE_REF:
            case FieldType.FIELD_TOA:
                return true;
            default:
                return isComputedByLayout(fieldType);
        }
    }

    /// <summary>
    /// Returns true for field types whose value is provided by layout engine.
    /// Examples: PAGE, NUMPAGES etc.
    /// </summary>
    static boolean isComputedByLayout(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_PAGE:
            case FieldType.FIELD_NUM_PAGES:
            case FieldType.FIELD_PAGE_REF:
            case FieldType.FIELD_SECTION_PAGES:
            case FieldType.FIELD_SECTION:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true for field types that should be updated when layout is updated.
    /// Word updates most of the fields in header/footer implicitly when saving to pdf.
    /// Some fields are not updated however.
    /// To mimic Word, we update these fields with layout.
    /// </summary>
    static boolean isUpdatedWithLayoutInHeaderFooter(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_STYLE_REF:
            case FieldType.FIELD_NOTE_REF:
            case FieldType.FIELD_SEQUENCE:
                return true;
            default:
                return isComputedByLayout(fieldType);
        }
    }

    /// <summary>
    /// Returns a value indicating whether a field of the specified type located in header/footer can be updated
    /// when the update was not initiated by layout.
    /// </summary>
    static boolean isUpdatedWithoutLayoutInHeaderFooter(/*FieldType*/int fieldType)
    {
        return !isUpdatedWithLayoutInHeaderFooter(fieldType);
    }

    /// <summary>
    /// Returns true if this is a "dead" field. We don't really care about live or dead in
    /// the model, but this info is needed when writing to MS Word formats.
    /// </summary>
    static boolean isDead(/*FieldType*/int fieldType)
    {
        // The Macro field can be dead or live. I ignore it here so it will get written as live.

        switch (fieldType)
        {
            case FieldType.FIELD_INDEX_ENTRY:
            case FieldType.FIELD_TOA_ENTRY:
            case FieldType.FIELD_TOC_ENTRY:
            case FieldType.FIELD_PRIVATE:
            case FieldType.FIELD_REF_DOC:
                return true;
            default:
                return false;
        }
    }

    static boolean isFormField(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_FORM_TEXT_INPUT:
            case FieldType.FIELD_FORM_CHECK_BOX:
            case FieldType.FIELD_FORM_DROP_DOWN:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Retrieves a field type from the specified string.
    /// If cannot find the corresponding field type, returns FieldType.FieldNone.
    /// Field code can have spaces in front. Not case-sensitive.
    /// WORDSNET-2727 Field code is not separated by space so we don't use space as separator anymore.
    /// </summary>
    static /*FieldType*/int getFieldType(String fieldCode)
    {
        fieldCode = msString.trim(fieldCode);

        if (!StringUtil.hasChars(fieldCode))
            return FieldType.FIELD_NONE;

        // WORDSNET-940 where a formula field is not recognized.
        if (fieldCode.charAt(0) == '=')
            return FieldType.FIELD_FORMULA;

        fieldCode = getFirstWord(fieldCode);
        int fieldType = G_FIELD_NAMES_TO_TYPES.get(fieldCode);

        return StringToIntDictionary.isNullSubstitute(fieldType) ? FieldType.FIELD_NONE : (/*FieldType*/int)fieldType;
    }

    /// <summary>
    /// Returns FieldType if it is defined inside FieldType enum, if not returns FieldType.FieldNone.
    /// Switch used for java: Enum.IsDefined() is not autoportable since demands relatively slow reflection code.
    /// NOTE: The switch should be updated accordingly after each FieldType enum change.
    /// </summary>
    static /*FieldType*/int getFieldType(int fieldTypeId)
    {
        switch (fieldTypeId)
        {
            case (int)FieldType.FIELD_NONE: return FieldType.FIELD_NONE;
            case (int)FieldType.FIELD_CANNOT_PARSE: return FieldType.FIELD_CANNOT_PARSE;
            case (int)FieldType.FIELD_REF_NO_KEYWORD: return FieldType.FIELD_REF_NO_KEYWORD;
            case (int)FieldType.FIELD_REF: return FieldType.FIELD_REF;
            case (int)FieldType.FIELD_INDEX_ENTRY: return FieldType.FIELD_INDEX_ENTRY;
            case (int)FieldType.FIELD_FOOTNOTE_REF: return FieldType.FIELD_FOOTNOTE_REF;
            case (int)FieldType.FIELD_SET: return FieldType.FIELD_SET;
            case (int)FieldType.FIELD_IF: return FieldType.FIELD_IF;
            case (int)FieldType.FIELD_INDEX: return FieldType.FIELD_INDEX;
            case (int)FieldType.FIELD_TOC_ENTRY: return FieldType.FIELD_TOC_ENTRY;
            case (int)FieldType.FIELD_STYLE_REF: return FieldType.FIELD_STYLE_REF;
            case (int)FieldType.FIELD_REF_DOC: return FieldType.FIELD_REF_DOC;
            case (int)FieldType.FIELD_SEQUENCE: return FieldType.FIELD_SEQUENCE;
            case (int)FieldType.FIELD_TOC: return FieldType.FIELD_TOC;
            case (int)FieldType.FIELD_INFO: return FieldType.FIELD_INFO;
            case (int)FieldType.FIELD_TITLE: return FieldType.FIELD_TITLE;
            case (int)FieldType.FIELD_SUBJECT: return FieldType.FIELD_SUBJECT;
            case (int)FieldType.FIELD_AUTHOR: return FieldType.FIELD_AUTHOR;
            case (int)FieldType.FIELD_KEYWORD: return FieldType.FIELD_KEYWORD;
            case (int)FieldType.FIELD_COMMENTS: return FieldType.FIELD_COMMENTS;
            case (int)FieldType.FIELD_LAST_SAVED_BY: return FieldType.FIELD_LAST_SAVED_BY;
            case (int)FieldType.FIELD_CREATE_DATE: return FieldType.FIELD_CREATE_DATE;
            case (int)FieldType.FIELD_SAVE_DATE: return FieldType.FIELD_SAVE_DATE;
            case (int)FieldType.FIELD_PRINT_DATE: return FieldType.FIELD_PRINT_DATE;
            case (int)FieldType.FIELD_REVISION_NUM: return FieldType.FIELD_REVISION_NUM;
            case (int)FieldType.FIELD_EDIT_TIME: return FieldType.FIELD_EDIT_TIME;
            case (int)FieldType.FIELD_NUM_PAGES: return FieldType.FIELD_NUM_PAGES;
            case (int)FieldType.FIELD_NUM_WORDS: return FieldType.FIELD_NUM_WORDS;
            case (int)FieldType.FIELD_NUM_CHARS: return FieldType.FIELD_NUM_CHARS;
            case (int)FieldType.FIELD_FILE_NAME: return FieldType.FIELD_FILE_NAME;
            case (int)FieldType.FIELD_TEMPLATE: return FieldType.FIELD_TEMPLATE;
            case (int)FieldType.FIELD_DATE: return FieldType.FIELD_DATE;
            case (int)FieldType.FIELD_TIME: return FieldType.FIELD_TIME;
            case (int)FieldType.FIELD_PAGE: return FieldType.FIELD_PAGE;
            case (int)FieldType.FIELD_FORMULA: return FieldType.FIELD_FORMULA;
            case (int)FieldType.FIELD_QUOTE: return FieldType.FIELD_QUOTE;
            case (int)FieldType.FIELD_INCLUDE: return FieldType.FIELD_INCLUDE;
            case (int)FieldType.FIELD_PAGE_REF: return FieldType.FIELD_PAGE_REF;
            case (int)FieldType.FIELD_ASK: return FieldType.FIELD_ASK;
            case (int)FieldType.FIELD_FILL_IN: return FieldType.FIELD_FILL_IN;
            case (int)FieldType.FIELD_DATA: return FieldType.FIELD_DATA;
            case (int)FieldType.FIELD_NEXT: return FieldType.FIELD_NEXT;
            case (int)FieldType.FIELD_NEXT_IF: return FieldType.FIELD_NEXT_IF;
            case (int)FieldType.FIELD_SKIP_IF: return FieldType.FIELD_SKIP_IF;
            case (int)FieldType.FIELD_MERGE_REC: return FieldType.FIELD_MERGE_REC;
            case (int)FieldType.FIELD_DDE: return FieldType.FIELD_DDE;
            case (int)FieldType.FIELD_DDE_AUTO: return FieldType.FIELD_DDE_AUTO;
            case (int)FieldType.FIELD_GLOSSARY: return FieldType.FIELD_GLOSSARY;
            case (int)FieldType.FIELD_PRINT: return FieldType.FIELD_PRINT;
            case (int)FieldType.FIELD_EQUATION: return FieldType.FIELD_EQUATION;
            case (int)FieldType.FIELD_GO_TO_BUTTON: return FieldType.FIELD_GO_TO_BUTTON;
            case (int)FieldType.FIELD_MACRO_BUTTON: return FieldType.FIELD_MACRO_BUTTON;
            case (int)FieldType.FIELD_AUTO_NUM_OUTLINE: return FieldType.FIELD_AUTO_NUM_OUTLINE;
            case (int)FieldType.FIELD_AUTO_NUM_LEGAL: return FieldType.FIELD_AUTO_NUM_LEGAL;
            case (int)FieldType.FIELD_AUTO_NUM: return FieldType.FIELD_AUTO_NUM;
            case (int)FieldType.FIELD_IMPORT: return FieldType.FIELD_IMPORT;
            case (int)FieldType.FIELD_LINK: return FieldType.FIELD_LINK;
            case (int)FieldType.FIELD_SYMBOL: return FieldType.FIELD_SYMBOL;
            case (int)FieldType.FIELD_EMBED: return FieldType.FIELD_EMBED;
            case (int)FieldType.FIELD_MERGE_FIELD: return FieldType.FIELD_MERGE_FIELD;
            case (int)FieldType.FIELD_USER_NAME: return FieldType.FIELD_USER_NAME;
            case (int)FieldType.FIELD_USER_INITIALS: return FieldType.FIELD_USER_INITIALS;
            case (int)FieldType.FIELD_USER_ADDRESS: return FieldType.FIELD_USER_ADDRESS;
            case (int)FieldType.FIELD_BARCODE: return FieldType.FIELD_BARCODE;
            case (int)FieldType.FIELD_DISPLAY_BARCODE: return FieldType.FIELD_DISPLAY_BARCODE;
            case (int)FieldType.FIELD_MERGE_BARCODE: return FieldType.FIELD_MERGE_BARCODE;
            case (int)FieldType.FIELD_DOC_VARIABLE: return FieldType.FIELD_DOC_VARIABLE;
            case (int)FieldType.FIELD_SECTION: return FieldType.FIELD_SECTION;
            case (int)FieldType.FIELD_SECTION_PAGES: return FieldType.FIELD_SECTION_PAGES;
            case (int)FieldType.FIELD_INCLUDE_PICTURE: return FieldType.FIELD_INCLUDE_PICTURE;
            case (int)FieldType.FIELD_INCLUDE_TEXT: return FieldType.FIELD_INCLUDE_TEXT;
            case (int)FieldType.FIELD_FILE_SIZE: return FieldType.FIELD_FILE_SIZE;
            case (int)FieldType.FIELD_FORM_TEXT_INPUT: return FieldType.FIELD_FORM_TEXT_INPUT;
            case (int)FieldType.FIELD_FORM_CHECK_BOX: return FieldType.FIELD_FORM_CHECK_BOX;
            case (int)FieldType.FIELD_NOTE_REF: return FieldType.FIELD_NOTE_REF;
            case (int)FieldType.FIELD_TOA: return FieldType.FIELD_TOA;
            case (int)FieldType.FIELD_TOA_ENTRY: return FieldType.FIELD_TOA_ENTRY;
            case (int)FieldType.FIELD_MERGE_SEQ: return FieldType.FIELD_MERGE_SEQ;
            case (int)FieldType.FIELD_PRIVATE: return FieldType.FIELD_PRIVATE;
            case (int)FieldType.FIELD_DATABASE: return FieldType.FIELD_DATABASE;
            case (int)FieldType.FIELD_AUTO_TEXT: return FieldType.FIELD_AUTO_TEXT;
            case (int)FieldType.FIELD_COMPARE: return FieldType.FIELD_COMPARE;
            case (int)FieldType.FIELD_ADDIN: return FieldType.FIELD_ADDIN;
            case (int)FieldType.FIELD_FORM_DROP_DOWN: return FieldType.FIELD_FORM_DROP_DOWN;
            case (int)FieldType.FIELD_ADVANCE: return FieldType.FIELD_ADVANCE;
            case (int)FieldType.FIELD_DOC_PROPERTY: return FieldType.FIELD_DOC_PROPERTY;
            case (int)FieldType.FIELD_OCX: return FieldType.FIELD_OCX;
            case (int)FieldType.FIELD_HYPERLINK: return FieldType.FIELD_HYPERLINK;
            case (int)FieldType.FIELD_AUTO_TEXT_LIST: return FieldType.FIELD_AUTO_TEXT_LIST;
            case (int)FieldType.FIELD_LIST_NUM: return FieldType.FIELD_LIST_NUM;
            case (int)FieldType.FIELD_HTML_ACTIVE_X: return FieldType.FIELD_HTML_ACTIVE_X;
            case (int)FieldType.FIELD_BIDI_OUTLINE: return FieldType.FIELD_BIDI_OUTLINE;
            case (int)FieldType.FIELD_ADDRESS_BLOCK: return FieldType.FIELD_ADDRESS_BLOCK;
            case (int)FieldType.FIELD_GREETING_LINE: return FieldType.FIELD_GREETING_LINE;
            case (int)FieldType.FIELD_SHAPE: return FieldType.FIELD_SHAPE;
            case (int)FieldType.FIELD_CITATION: return FieldType.FIELD_CITATION;
            case (int)FieldType.FIELD_BIBLIOGRAPHY: return FieldType.FIELD_BIBLIOGRAPHY;

            default:
                return FieldType.FIELD_NONE;
        }
    }

    private static String getFirstWord(String fieldCode)
    {
        Match matchResult = G_FIELD_CODE_REGEX.match(fieldCode);

        if (matchResult.getGroups().getCount() > 0)
            return matchResult.getGroups().get(matchResult.getGroups().getCount() - 1).getValue();

        return "";
    }

    static String fetchFieldCode(/*FieldType*/int fieldType)
    {
        String fieldCode = fetchFieldCodeSafe(fieldType);
        if (fieldCode != null)
            return fieldCode;

        throw new IllegalStateException(msString.format("Field type '{0}' is invalid or not supported.", fieldTypeToString(fieldType)));
    }

    static String fetchFieldCodeSafe(/*FieldType*/int fieldType)
    {
        return G_FIELD_TYPES_TO_NAMES.get((int)fieldType);
    }

    /// <summary>
    /// Majority of field types can have (and do have) a field separator.
    /// However, some field types should not have a separator, otherwise it could create corrupted files.
    /// See comments below.
    /// </summary>
    static /*FieldSeparatorPresence*/int getSeparatorPresence(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_ADVANCE:
            case FieldType.FIELD_AUTO_NUM:
            case FieldType.FIELD_AUTO_NUM_LEGAL:
            case FieldType.FIELD_AUTO_NUM_OUTLINE:
            case FieldType.FIELD_BIDI_OUTLINE:
            case FieldType.FIELD_GO_TO_BUTTON:
            case FieldType.FIELD_FORM_DROP_DOWN:
            case FieldType.FIELD_FORM_CHECK_BOX:
            case FieldType.FIELD_LIST_NUM:
            case FieldType.FIELD_MACRO_BUTTON:
            case FieldType.FIELD_PRINT:
            case FieldType.FIELD_SYMBOL:
            case FieldType.FIELD_DISPLAY_BARCODE:
            case FieldType.FIELD_BARCODE:
                {
                    // These field types usually do not have a separator. If we output a separator for
                    // these fields into the DOC file, the file could come out corrupted.
                    return FieldSeparatorPresence.NEVER;
                }
            case FieldType.FIELD_NONE:
            case FieldType.FIELD_NEXT:
            case FieldType.FIELD_NEXT_IF:
            case FieldType.FIELD_SKIP_IF:
                {
                    // These fields may or may not have a separator. For example, NEXT and NEXTIF do not have separators
                    // when created, but after direct update a separator is inserted to set the result.
                    return FieldSeparatorPresence.SOMETIMES;
                }
            default:
                {
                    if (isDead(fieldType))
                    {
                        // According to MS SPEC, dead fields cannot have a separator.
                        return FieldSeparatorPresence.NEVER;
                    }
                    else
                    {
                        // All other (the majority of) fields can have a separator and most of the time
                        // they do have it.
                        //
                        // Sometimes normal fields do not have a separator and that might cause hickups
                        // in some places in the code, it has to be improved to handle all fields with
                        // and without separators. MS Word seems to regenerate separator and result
                        // if needed, we should be able to do the same eventually.
                        return FieldSeparatorPresence.ALWAYS;
                    }
                }
        }
    }

    /// <summary>
    /// Returns a value indicating whether the field result is locked and should not be updated.
    /// </summary>
    static boolean getEffectiveFieldLocked(Field field)
    {
        if (!field.isLocked())
            return false;

        switch (field.getType())
        {
            // WORDSNET-25232 MS Word ignores fldLock attribute of PAGE and NUMPAGES fields in header/footer.
            case FieldType.FIELD_PAGE:
            case FieldType.FIELD_NUM_PAGES:
                return !field.isInHeaderFooter();
            default:
                return true;
        }
    }

    /// <summary>
    /// Returns a value indicating whether the result of a field of the given type is invisible in MS Word editor.
    /// You should use this overload only when <see cref="HasConstantFieldResultVisibility"/> returns <c>true</c>
    /// for the given field type.
    /// </summary>
    static boolean isFieldResultInvisible(/*FieldType*/int fieldType)
    {
        Debug.assertTrue(hasConstantFieldResultVisibility(fieldType));

        return producesBookmarkAsResult(fieldType);
    }

    /// <summary>
    /// Returns a value indicating whether the result of a field composed of the given field char is invisible
    /// in MS Word editor.
    /// </summary>
    static boolean isFieldResultInvisible(FieldChar fieldChar)
    {
        if (hasConstantFieldResultVisibility(fieldChar.getFieldType()))
            return isFieldResultInvisible(fieldChar.getFieldType());

        FieldBundle bundle = FieldBundle.getFieldBundle(fieldChar);
        return isFieldResultInvisible(bundle.getStart(), bundle.getFieldCodeEnd());
    }

    /// <summary>
    /// Returns a value indicating whether the result of a field which code is strictly contained between
    /// the specified nodes is invisible in MS Word editor.
    /// </summary>
    static boolean isFieldResultInvisible(FieldStart fieldStart, FieldChar fieldCodeEnd)
    {
        if (hasConstantFieldResultVisibility(fieldStart.getFieldType()))
            return isFieldResultInvisible(fieldStart.getFieldType());

        return !FieldUnknown.isBookmarkRef(fieldStart, fieldCodeEnd);
    }

    /// <summary>
    /// Returns a value indicating whether the result of the specified field is invisible in MS Word editor.
    /// </summary>
    /// <dev>
    /// Although this method could belong to <see cref="Field"/> class, it is more convenient to place it here
    /// to keep it in sync with the above overload.
    /// </dev>
    static boolean isFieldResultInvisible(Field field)
    {
        if (hasConstantFieldResultVisibility(field.getType()))
            return isFieldResultInvisible(field.getType());

        return !((FieldUnknown)field).isBookmarkRef();
    }

    /// <summary>
    /// Returns a value indicating whether field result visibility for a field of the given type does not depend on
    /// anything but the field type itself.
    /// </summary>
    static boolean hasConstantFieldResultVisibility(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_NONE:
            case FieldType.FIELD_REF_NO_KEYWORD:
                return false;
            default:
                return true;
        }
    }

    /// <summary>
    /// Returns a value indicating whether a field of the given type produces a bookmark in its result while updating.
    /// </summary>
    static boolean producesBookmarkAsResult(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_SET:
            case FieldType.FIELD_ASK:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true if the field always formats its result according to its locale, regardless of presence of the \@ switch.
    /// </summary>
    static boolean alwaysConsidersLocale(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_DATE:
            case FieldType.FIELD_TIME:
            case FieldType.FIELD_CREATE_DATE:
            case FieldType.FIELD_PRINT_DATE:
            case FieldType.FIELD_SAVE_DATE:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns the index of last argument that should be treated as verbatim, i.e. including all whitespaces and double quotes.
    /// -1 means there is no such argument.
    /// </summary>
    static int getVerbatimArgumentIndex(String fieldCode)
    {
        return getVerbatimArgumentIndex(getFieldType(fieldCode));
    }

    /// <summary>
    /// Returns the index of last argument that should be treated as verbatim, i.e. including all whitespaces and double quotes.
    /// -1 means there is no such argument.
    /// </summary>
    static int getVerbatimArgumentIndex(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_GO_TO_BUTTON:
            case FieldType.FIELD_MACRO_BUTTON:
                return 1;
            case FieldType.FIELD_EQUATION:
                return 0;
            default:
                return -1;
        }
    }

    static boolean ignoreSwitchesInFieldType(/*FieldType*/int fieldType)
    {
        return fieldType != FieldType.FIELD_FORMULA;
    }

    static boolean ignoreSwitchesInFieldType(String fieldCode)
    {
        return ignoreSwitchesInFieldType(getFieldType(fieldCode));
    }

    static boolean ignoreSymbolicQuotes(/*FieldType*/int fieldType, String switchName)
    {
        return fieldType == FieldType.FIELD_TOA_ENTRY && FieldTA.LONG_CITATION_SWITCH.equals(switchName);
    }

    static boolean ignoreSymbolicQuotes(/*FieldType*/int fieldType, int argumentIndex)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_TOC_ENTRY:
                return argumentIndex == FieldTC.TEXT_ARGUMENT_INDEX;
            case FieldType.FIELD_INDEX_ENTRY:
                return argumentIndex == FieldXE.TEXT_ARGUMENT_INDEX;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true if the specified field type should retain original formatting when no field result formatting switch
    /// is specified.
    /// </summary>
    static boolean retainOriginalFormatting(Field field)
    {
        switch (field.getType())
        {
            case FieldType.FIELD_IF:
            case FieldType.FIELD_INCLUDE_TEXT:
            case FieldType.FIELD_INCLUDE:
            case FieldType.FIELD_INCLUDE_PICTURE:
            case FieldType.FIELD_IMPORT:
            case FieldType.FIELD_REF:
            case FieldType.FIELD_REF_NO_KEYWORD:
            case FieldType.FIELD_NONE:
            case FieldType.FIELD_QUOTE:
            case FieldType.FIELD_SHAPE:
            case FieldType.FIELD_AUTO_TEXT:
            case FieldType.FIELD_DATABASE:
                return true;
            case FieldType.FIELD_NOTE_REF:
                FieldNoteRef fieldNoteRef = (FieldNoteRef)field;
                return fieldNoteRef.getInsertReferenceMark();
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns a value indicating whether a spell checking should be disabled for a field result of the specified type.
    /// </summary>
    static boolean disableResultSpellChecking(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_MERGE_FIELD:
            case FieldType.FIELD_FORM_TEXT_INPUT:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true if the specified field type should retain backslashes in result.
    /// </summary>
    static boolean preserveEscapingBackslashesInResult(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_IF:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true if the specified field type should retain double quotes in result.
    /// </summary>
    static boolean preserveDoubleQuotesInResult(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_QUOTE:
            case FieldType.FIELD_SHAPE:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns a <see cref="NodeJoinMode"/> value specifying how ancestor nodes of the specified field's
    /// separator and end should be joined while the field's result removal.
    /// </summary>
    static /*NodeJoinMode*/int getFieldResultRemovalNodeJoinMode(Field field)
    {
        // Normally the following does for all of the fields. Any field's result nodes should be joined
        // to next siblings while removal. This is needed to achieve the same results while the primary field
        // update (when there was no result before the update) and any subsequent update of this field due to
        // MS Word behavior.
        //
        // However, there is WORDSNET-8355 Normally, an INDEX field result should be removed in the same way.
        // But when a column number is provided through its field code, additional sections should be created.
        // MS Word does not create a full-value start section's clones in this case. Instead, it creates them
        // as linked-to-previous ones. AW mimics this as well. That is why we should make an exception in this
        // case and use NodeJoinMode.JoinToPreviousSibling. Note, however, that there is a minor ensuing issue.
        // If there is a text in the same paragraph together with the field's end, then there probably will be
        // a difference between results produced by MS Word and AW. However, an INDEX field's result is
        // multi-paragraph, so there is no sense to append any text after it in the same paragraph. So the case
        // should be extremely rare and hence we can leave this behavior as is until any related client request.
        //
        // Be consistent with FieldCodeIndex.HasNumberOfColumns.
        if (field.getType() == FieldType.FIELD_INDEX)
        {
            FieldIndex fieldIndex = (FieldIndex)field;
            if (fieldIndex.hasNumberOfColumnsSwitch() && (fieldIndex.getNumberOfColumnsAsInt32().getValueOrDefault() > 0))
                return NodeJoinMode.JOIN_TO_PREVIOUS_SIBLING;
        }

        return NodeJoinMode.JOIN_TO_NEXT_SIBLING;
    }

    /// <summary>
    /// Returns a <see cref="NodeJoinMode"/> value specifying how ancestor nodes of the specified field's
    /// separator and end should be joined while the field's old result removal.
    /// </summary>
    static /*NodeJoinMode*/int getFieldOldResultRemovalNodeJoinMode(Field field)
    {
        // WORDSNET-10770 We should join separator and end nodes of INCLUDETEXT field, when they located in different sections.
        // Otherwise, redundant section added during field update.
        return field.hasSeparator()
            && (field.getType() == FieldType.FIELD_INCLUDE_TEXT || field.getType() == FieldType.FIELD_INCLUDE)
            && (field.getSeparator().getAncestor(NodeType.SECTION) != field.getEnd().getAncestor(NodeType.SECTION))
                   ? NodeJoinMode.JOIN_TO_PREVIOUS_SIBLING
                   : NodeJoinMode.DONT_JOIN;
    }

    /// <summary>
    /// Returns a value indicating thet a field of the specified type can contain fields in its result,
    /// which should be updated while updating of the field.
    /// </summary>
    static boolean requiresUpdateFieldsInResult(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_INCLUDE_TEXT:
            case FieldType.FIELD_INCLUDE:
            case FieldType.FIELD_TOC:
            case FieldType.FIELD_HYPERLINK:
            case FieldType.FIELD_INCLUDE_PICTURE:
            case FieldType.FIELD_IMPORT:
            case FieldType.FIELD_AUTO_TEXT:
            case FieldType.FIELD_FORM_TEXT_INPUT:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns a value indicating whether the end position of the specified node range stands at a field end.
    /// </summary>
    static boolean endsWithFieldEnd(NodeRange range)
    {
        return !range.isVoid() && (range.getEnd().getNode().getNodeType() == NodeType.FIELD_END);
    }

    /// <summary>
    /// If the specified field is being updated returns a bookmark from the updater cache.
    /// Otherwise returns a direct document bookmark bypassing any caches.
    /// </summary>
    static Bookmark getCachedBookmark(Field field, String bookmarkName) throws Exception
    {
        return field.isUpdating()
            ? field.getUpdater().getCachedBookmark(bookmarkName)
            : field.getDocument().getRange().getBookmarks().get(bookmarkName);
    }

    /// <summary>
    /// Returns a value indicating whether the specified bookmark start relates to a dynamic bookmark.
    /// Dynamic bookmarks are products of ASK and SET fields' update and are located in their results.
    /// </summary>
    /// <dev>
    /// MS Word processes static and dynamic bookmarks differently while fields' updating. That's why
    /// this method is useful to keep here.
    /// </dev>
    static boolean isDynamicBookmark(BookmarkStart bookmarkStart)
    {
        Node decisiveNode = bookmarkStart.getPreviousNonAnnotationSibling();

        return ((decisiveNode != null) &&
                (decisiveNode.getNodeType() == NodeType.FIELD_SEPARATOR) &&
                producesBookmarkAsResult(((FieldSeparator)decisiveNode).getFieldType()));
    }

    static String fieldTypeToString(/*FieldType*/int value)
    {
        switch (value)
        {
            case FieldType.FIELD_ADDIN:
                return "FieldAddin";
            case FieldType.FIELD_ADDRESS_BLOCK:
                return "FieldAddressBlock";
            case FieldType.FIELD_ADVANCE:
                return "FieldAdvance";
            case FieldType.FIELD_ASK:
                return "FieldAsk";
            case FieldType.FIELD_AUTHOR:
                return "FieldAuthor";
            case FieldType.FIELD_AUTO_NUM:
                return "FieldAutoNum";
            case FieldType.FIELD_AUTO_NUM_LEGAL:
                return "FieldAutoNumLegal";
            case FieldType.FIELD_AUTO_NUM_OUTLINE:
                return "FieldAutoNumOutline";
            case FieldType.FIELD_AUTO_TEXT:
                return "FieldAutoText";
            case FieldType.FIELD_AUTO_TEXT_LIST:
                return "FieldAutoTextList";
            case FieldType.FIELD_BARCODE:
                return "FieldBarcode";
            case FieldType.FIELD_BIBLIOGRAPHY:
                return "FieldBibliography";
            case FieldType.FIELD_BIDI_OUTLINE:
                return "FieldBidiOutline";
            case FieldType.FIELD_CANNOT_PARSE:
                return "FieldCannotParse";
            case FieldType.FIELD_CITATION:
                return "FieldCitation";
            case FieldType.FIELD_COMMENTS:
                return "FieldComments";
            case FieldType.FIELD_COMPARE:
                return "FieldCompare";
            case FieldType.FIELD_CREATE_DATE:
                return "FieldCreateDate";
            case FieldType.FIELD_DATA:
                return "FieldData";
            case FieldType.FIELD_DATABASE:
                return "FieldDatabase";
            case FieldType.FIELD_DATE:
                return "FieldDate";
            case FieldType.FIELD_DDE:
                return "FieldDDE";
            case FieldType.FIELD_DDE_AUTO:
                return "FieldDDEAuto";
            case FieldType.FIELD_DISPLAY_BARCODE:
                return "FieldDisplayBarcode";
            case FieldType.FIELD_MERGE_BARCODE:
                return "FieldMergeBarcode";
            case FieldType.FIELD_DOC_PROPERTY:
                return "FieldDocProperty";
            case FieldType.FIELD_DOC_VARIABLE:
                return "FieldDocVariable";
            case FieldType.FIELD_EDIT_TIME:
                return "FieldEditTime";
            case FieldType.FIELD_EMBED:
                return "FieldEmbed";
            case FieldType.FIELD_EQUATION:
                return "FieldEquation";
            case FieldType.FIELD_FILE_NAME:
                return "FieldFileName";
            case FieldType.FIELD_FILE_SIZE:
                return "FieldFileSize";
            case FieldType.FIELD_FILL_IN:
                return "FieldFillIn";
            case FieldType.FIELD_FOOTNOTE_REF:
                return "FieldFootnoteRef";
            case FieldType.FIELD_FORM_CHECK_BOX:
                return "FieldFormCheckBox";
            case FieldType.FIELD_FORM_DROP_DOWN:
                return "FieldFormDropDown";
            case FieldType.FIELD_FORM_TEXT_INPUT:
                return "FieldFormTextInput";
            case FieldType.FIELD_FORMULA:
                return "FieldFormula";
            case FieldType.FIELD_GLOSSARY:
                return "FieldGlossary";
            case FieldType.FIELD_GO_TO_BUTTON:
                return "FieldGoToButton";
            case FieldType.FIELD_GREETING_LINE:
                return "FieldGreetingLine";
            case FieldType.FIELD_HTML_ACTIVE_X:
                return "FieldHtmlActiveX";
            case FieldType.FIELD_HYPERLINK:
                return "FieldHyperlink";
            case FieldType.FIELD_IF:
                return "FieldIf";
            case FieldType.FIELD_IMPORT:
                return "FieldImport";
            case FieldType.FIELD_INCLUDE:
                return "FieldInclude";
            case FieldType.FIELD_INCLUDE_PICTURE:
                return "FieldIncludePicture";
            case FieldType.FIELD_INCLUDE_TEXT:
                return "FieldIncludeText";
            case FieldType.FIELD_INDEX:
                return "FieldIndex";
            case FieldType.FIELD_INDEX_ENTRY:
                return "FieldIndexEntry";
            case FieldType.FIELD_INFO:
                return "FieldInfo";
            case FieldType.FIELD_KEYWORD:
                return "FieldKeyword";
            case FieldType.FIELD_LAST_SAVED_BY:
                return "FieldLastSavedBy";
            case FieldType.FIELD_LINK:
                return "FieldLink";
            case FieldType.FIELD_LIST_NUM:
                return "FieldListNum";
            case FieldType.FIELD_MACRO_BUTTON:
                return "FieldMacroButton";
            case FieldType.FIELD_MERGE_FIELD:
                return "FieldMergeField";
            case FieldType.FIELD_MERGE_REC:
                return "FieldMergeRec";
            case FieldType.FIELD_MERGE_SEQ:
                return "FieldMergeSeq";
            case FieldType.FIELD_NEXT:
                return "FieldNext";
            case FieldType.FIELD_NEXT_IF:
                return "FieldNextIf";
            case FieldType.FIELD_NONE:
                return "FieldNone";
            case FieldType.FIELD_NOTE_REF:
                return "FieldNoteRef";
            case FieldType.FIELD_NUM_CHARS:
                return "FieldNumChars";
            case FieldType.FIELD_NUM_PAGES:
                return "FieldNumPages";
            case FieldType.FIELD_NUM_WORDS:
                return "FieldNumWords";
            case FieldType.FIELD_OCX:
                return "FieldOcx";
            case FieldType.FIELD_PAGE:
                return "FieldPage";
            case FieldType.FIELD_PAGE_REF:
                return "FieldPageRef";
            case FieldType.FIELD_PRINT:
                return "FieldPrint";
            case FieldType.FIELD_PRINT_DATE:
                return "FieldPrintDate";
            case FieldType.FIELD_PRIVATE:
                return "FieldPrivate";
            case FieldType.FIELD_QUOTE:
                return "FieldQuote";
            case FieldType.FIELD_REF:
                return "FieldRef";
            case FieldType.FIELD_REF_DOC:
                return "FieldRefDoc";
            case FieldType.FIELD_REF_NO_KEYWORD:
                return "FieldRefNoKeyword";
            case FieldType.FIELD_REVISION_NUM:
                return "FieldRevisionNum";
            case FieldType.FIELD_SAVE_DATE:
                return "FieldSaveDate";
            case FieldType.FIELD_SECTION:
                return "FieldSection";
            case FieldType.FIELD_SECTION_PAGES:
                return "FieldSectionPages";
            case FieldType.FIELD_SEQUENCE:
                return "FieldSequence";
            case FieldType.FIELD_SET:
                return "FieldSet";
            case FieldType.FIELD_SHAPE:
                return "FieldShape";
            case FieldType.FIELD_SKIP_IF:
                return "FieldSkipIf";
            case FieldType.FIELD_STYLE_REF:
                return "FieldStyleRef";
            case FieldType.FIELD_SUBJECT:
                return "FieldSubject";
            case FieldType.FIELD_SYMBOL:
                return "FieldSymbol";
            case FieldType.FIELD_TEMPLATE:
                return "FieldTemplate";
            case FieldType.FIELD_TIME:
                return "FieldTime";
            case FieldType.FIELD_TITLE:
                return "FieldTitle";
            case FieldType.FIELD_TOA:
                return "FieldTOA";
            case FieldType.FIELD_TOA_ENTRY:
                return "FieldTOAEntry";
            case FieldType.FIELD_TOC:
                return "FieldTOC";
            case FieldType.FIELD_TOC_ENTRY:
                return "FieldTOCEntry";
            case FieldType.FIELD_USER_ADDRESS:
                return "FieldUserAddress";
            case FieldType.FIELD_USER_INITIALS:
                return "FieldUserInitials";
            case FieldType.FIELD_USER_NAME:
                return "FieldUserName";
            default:
                throw new IllegalStateException("Unknown FieldType: '" + value + "'. Please use TestFieldTypes.GenerateFieldTypeToString()" +
                                                    " to update FieldUtil.FieldTypeToString()");
        }
    }

    /// <summary>
    /// Returns a value indicating whether the result of a field of the given type is image.
    /// </summary>
    static boolean isImageFieldResult(/*FieldType*/int type)
    {
        switch (type)
        {
            case FieldType.FIELD_INCLUDE_PICTURE:
            case FieldType.FIELD_IMPORT:
                return true;
            default:
                return false;
        }
    }

    static NodeRange buildFieldResultNodeRange(Node start, Node end)
    {
        return new NodeRange(
                extractFirstLastNode(start, true),
                extractFirstLastNode(end, false));
    }

    /// <summary>
    /// Indicates that field of given type can have custom private data.
    /// </summary>
    static boolean canHavePrivateData(/*FieldType*/int fieldType)
    {
        return (fieldType == FieldType.FIELD_ADDIN) || (fieldType == FieldType.FIELD_PRIVATE);
    }

    private static Node extractFirstLastNode(Node node, boolean isFirst)
    {
        if (!node.isComposite())
            return node;

        if (node.getNodeLevel() == NodeLevel.INLINE)
            return node;

        if (node.getNodeType() == NodeType.TABLE)
            return node;

        CompositeNode compositeNode = (CompositeNode)node;
        if (!compositeNode.hasChildNodes())
        {
            if (node.getNodeType() != NodeType.PARAGRAPH)
                return node;

            // Appending empty run prevents extra empty para in field result.
            compositeNode.appendChild(new Run(node.getDocument(), ""));
        }

        return extractFirstLastNode(isFirst ? compositeNode.getFirstChild() : compositeNode.getLastChild(), isFirst);
    }

    static Stream openStream(String fileName, IResourceLoadingCallback resourceLoadingCallback) throws Exception
    {
        if (resourceLoadingCallback == null)
            return openStream(fileName);

        ResourceLoadingArgs args = new ResourceLoadingArgs("", fileName, ResourceType.DOCUMENT);
        switch (resourceLoadingCallback.resourceLoading(args))
        {
            case ResourceLoadingAction.DEFAULT:
                return openStream(fileName);
            case ResourceLoadingAction.SKIP:
                return null;
            case ResourceLoadingAction.USER_PROVIDED:
                return new MemoryStream(args.getData());
            default:
                throw new IllegalArgumentException();
        }
    }

    private static Stream openStream(String fileName) throws Exception
    {
        // WORDSNET-4608 We try to create Uri safely. If unsuccessful we check file existence in addition
        // because an invalid uri might be a valid file path.
        msUri uri = UriUtil.createUriSafely(fileName);
        boolean isHttpOrHttpsLink = isUriHttpOrHttps(uri);

        if (isHttpOrHttpsLink)
            return SystemPal.openStreamFromHref(uri.getAbsoluteUri());

        String path = (getLocalPathSafely(uri) != null ? getLocalPathSafely(uri) : fileName);

        if (!File.exists(path))
            return null;

        return File.openRead(path);
    }

    private static String getLocalPathSafely(msUri uri)
    {
        if (uri == null)
            return null;

        try
        {
            // WORDSNET-9570 Handle an exception thrown when the source path is invalid.
            return uri.getLocalPath();
        }
        catch(Exception e)
        {
            return null;
        }
    }

    private static boolean isUriHttpOrHttps(msUri uri)
    {
        if (uri == null || !uri.isAbsoluteUri())
            return false;

        String scheme = uri.getScheme().toLowerCase();
        return "http".equals(scheme) || "https".equals(scheme);
    }

    private static void addMapEntry(String fieldName, /*FieldType*/int fieldType)
    {
        addMapEntry(fieldName, fieldType, true);
    }

    private static void addMapEntry(String fieldName, /*FieldType*/int fieldType, boolean addToFieldTypesToNames)
    {
        G_FIELD_NAMES_TO_TYPES.add(fieldName, (int)fieldType);

        if (addToFieldTypesToNames)
            G_FIELD_TYPES_TO_NAMES.add((int)fieldType, fieldName);
    }

    /// <summary>
    /// Map of field names into field types.
    /// </summary>
    private static /*final*/ StringToIntDictionary G_FIELD_NAMES_TO_TYPES = new StringToIntDictionary(false);
    /// <summary>
    /// Map of field types into field names.
    /// </summary>
    private static /*final*/ IntToObjDictionary<String> G_FIELD_TYPES_TO_NAMES = new IntToObjDictionary<String>();

    // WORDSNET-12237 MS Word accepts special chars in field name.
    private static /*final*/ Regex G_FIELD_CODE_REGEX = new Regex(
        "^\\\"?\\\\?([\\w!#-~]*)\\\"?",
        RegexOptions.COMPILED);

    static/* FieldUtil()*/
    {
        addMapEntry("", FieldType.FIELD_NONE);
        addMapEntry("=", FieldType.FIELD_FORMULA);
        addMapEntry("ADDIN", FieldType.FIELD_ADDIN);
        addMapEntry("ADDRESSBLOCK", FieldType.FIELD_ADDRESS_BLOCK);
        addMapEntry("ADVANCE", FieldType.FIELD_ADVANCE);
        addMapEntry("ASK", FieldType.FIELD_ASK);
        addMapEntry("AUTHOR", FieldType.FIELD_AUTHOR);
        addMapEntry("AUTONUM", FieldType.FIELD_AUTO_NUM);
        addMapEntry("AUTONUMLGL", FieldType.FIELD_AUTO_NUM_LEGAL);
        addMapEntry("AUTONUMOUT", FieldType.FIELD_AUTO_NUM_OUTLINE);
        addMapEntry("AUTOTEXT", FieldType.FIELD_AUTO_TEXT);
        addMapEntry("AUTOTEXTLIST", FieldType.FIELD_AUTO_TEXT_LIST);
        addMapEntry("BARCODE", FieldType.FIELD_BARCODE);
        addMapEntry("BIBLIOGRAPHY", FieldType.FIELD_BIBLIOGRAPHY);
        addMapEntry("BIDIOUTLINE", FieldType.FIELD_BIDI_OUTLINE);
        addMapEntry("CITATION", FieldType.FIELD_CITATION);
        addMapEntry("COMMENTS", FieldType.FIELD_COMMENTS);
        addMapEntry("COMPARE", FieldType.FIELD_COMPARE);
        addMapEntry("CREATEDATE", FieldType.FIELD_CREATE_DATE);
        addMapEntry("DATA", FieldType.FIELD_DATA); // In OOXML?
        addMapEntry("DATABASE", FieldType.FIELD_DATABASE);
        addMapEntry("DATE", FieldType.FIELD_DATE);
        addMapEntry("DDE", FieldType.FIELD_DDE); // In OOXML?
        addMapEntry("DDEAUTO", FieldType.FIELD_DDE_AUTO); // In OOXML?
        addMapEntry("DISPLAYBARCODE", FieldType.FIELD_DISPLAY_BARCODE);
        addMapEntry("MERGEBARCODE", FieldType.FIELD_MERGE_BARCODE);
        addMapEntry("DOCPROPERTY", FieldType.FIELD_DOC_PROPERTY);
        addMapEntry("DOCVARIABLE", FieldType.FIELD_DOC_VARIABLE);
        addMapEntry("EDITTIME", FieldType.FIELD_EDIT_TIME);
        addMapEntry("EMBED", FieldType.FIELD_EMBED); // Not in OOXML.
        addMapEntry("EQ", FieldType.FIELD_EQUATION);
        addMapEntry("FILENAME", FieldType.FIELD_FILE_NAME);
        addMapEntry("FILESIZE", FieldType.FIELD_FILE_SIZE);
        addMapEntry("FILLIN", FieldType.FIELD_FILL_IN);
        addMapEntry("FOOTNOTEREF", FieldType.FIELD_FOOTNOTE_REF); // In OOXML?
        addMapEntry("FORMTEXT", FieldType.FIELD_FORM_TEXT_INPUT);
        addMapEntry("FORMCHECKBOX", FieldType.FIELD_FORM_CHECK_BOX);
        addMapEntry("FORMDROPDOWN", FieldType.FIELD_FORM_DROP_DOWN);
        addMapEntry("GLOSSARY", FieldType.FIELD_GLOSSARY);
        addMapEntry("GOTOBUTTON", FieldType.FIELD_GO_TO_BUTTON);
        addMapEntry("GREETINGLINE", FieldType.FIELD_GREETING_LINE);
        addMapEntry("HYPERLINK", FieldType.FIELD_HYPERLINK);
        addMapEntry("IF", FieldType.FIELD_IF);
        addMapEntry("IMPORT", FieldType.FIELD_IMPORT); // DD: FieldType.FieldImport is not used anywhere,
        // but allows to fix 9345 by keeping separator from
        // being nulled. Look for usuallyHasSeparator
        // var in the code for more.
        addMapEntry("INCLUDE", FieldType.FIELD_INCLUDE);
        addMapEntry("INCLUDEPICTURE", FieldType.FIELD_INCLUDE_PICTURE);
        addMapEntry("INCLUDETEXT", FieldType.FIELD_INCLUDE_TEXT);
        addMapEntry("INDEX", FieldType.FIELD_INDEX);
        addMapEntry("INFO", FieldType.FIELD_INFO);
        addMapEntry("KEYWORDS", FieldType.FIELD_KEYWORD);
        addMapEntry("LASTSAVEDBY", FieldType.FIELD_LAST_SAVED_BY);
        addMapEntry("LINK", FieldType.FIELD_LINK);
        addMapEntry("LISTNUM", FieldType.FIELD_LIST_NUM);
        addMapEntry("MACROBUTTON", FieldType.FIELD_MACRO_BUTTON);
        addMapEntry("MERGEFIELD", FieldType.FIELD_MERGE_FIELD);
        addMapEntry("MERGEREC", FieldType.FIELD_MERGE_REC);
        addMapEntry("MERGESEQ", FieldType.FIELD_MERGE_SEQ);
        addMapEntry("NEXT", FieldType.FIELD_NEXT);
        addMapEntry("NEXTIF", FieldType.FIELD_NEXT_IF);
        addMapEntry("NOTEREF", FieldType.FIELD_NOTE_REF);
        addMapEntry("NUMCHARS", FieldType.FIELD_NUM_CHARS);
        addMapEntry("NUMPAGES", FieldType.FIELD_NUM_PAGES);
        addMapEntry("NUMWORDS", FieldType.FIELD_NUM_WORDS);
        addMapEntry("OCX", FieldType.FIELD_OCX); // Not in OXML.
        addMapEntry("PAGE", FieldType.FIELD_PAGE);
        addMapEntry("PAGEREF", FieldType.FIELD_PAGE_REF);
        addMapEntry("PRINT", FieldType.FIELD_PRINT);
        addMapEntry("PRINTDATE", FieldType.FIELD_PRINT_DATE);
        addMapEntry("PRIVATE", FieldType.FIELD_PRIVATE); //English, German
        addMapEntry("PRIVE", FieldType.FIELD_PRIVATE, false); //French
        addMapEntry("PRIVATESPAN", FieldType.FIELD_PRIVATE, false); //Spanish
        addMapEntry("QUOTE", FieldType.FIELD_QUOTE);
        addMapEntry("RD", FieldType.FIELD_REF_DOC); //All languages
        addMapEntry("REF", FieldType.FIELD_REF);
        addMapEntry("REVNUM", FieldType.FIELD_REVISION_NUM);
        addMapEntry("SAVEDATE", FieldType.FIELD_SAVE_DATE);
        addMapEntry("SECTION", FieldType.FIELD_SECTION);
        addMapEntry("SECTIONPAGES", FieldType.FIELD_SECTION_PAGES);
        addMapEntry("SEQ", FieldType.FIELD_SEQUENCE);
        addMapEntry("SET", FieldType.FIELD_SET);
        addMapEntry("SHAPE", FieldType.FIELD_SHAPE);
        addMapEntry("SKIPIF", FieldType.FIELD_SKIP_IF);
        addMapEntry("STYLEREF", FieldType.FIELD_STYLE_REF);
        addMapEntry("SUBJECT", FieldType.FIELD_SUBJECT);
        addMapEntry("SYMBOL", FieldType.FIELD_SYMBOL);
        addMapEntry("TA", FieldType.FIELD_TOA_ENTRY); //All languages
        addMapEntry("TC", FieldType.FIELD_TOC_ENTRY); //English, Spanish
        addMapEntry("TE", FieldType.FIELD_TOC_ENTRY, false); //French
        addMapEntry("INHALT", FieldType.FIELD_TOC_ENTRY, false); //German
        addMapEntry("TEMPLATE", FieldType.FIELD_TEMPLATE);
        addMapEntry("TIME", FieldType.FIELD_TIME);
        addMapEntry("TITLE", FieldType.FIELD_TITLE);
        addMapEntry("TOA", FieldType.FIELD_TOA);
        addMapEntry("TOC", FieldType.FIELD_TOC);
        addMapEntry("USERADDRESS", FieldType.FIELD_USER_ADDRESS);
        addMapEntry("USERINITIALS", FieldType.FIELD_USER_INITIALS);
        addMapEntry("USERNAME", FieldType.FIELD_USER_NAME);
        addMapEntry("XE", FieldType.FIELD_INDEX_ENTRY); //English, German
        addMapEntry("EX", FieldType.FIELD_INDEX_ENTRY, false); //French
        addMapEntry("E", FieldType.FIELD_INDEX_ENTRY, false); //Spanish
    }
}

