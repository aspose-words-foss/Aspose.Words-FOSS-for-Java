// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/09/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ISetGeneric;
import com.aspose.CaseInsensitiveHashSet;
import com.aspose.words.ref.RefBoolean;


/// <summary>
/// Implements an unknown or unrecognized field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <dev>
/// Such fields are normally treated as references to bookmarks.
/// </dev>
public class FieldUnknown extends Field implements IMergeFieldSurrogate
{
    /*override*/ /*FieldUpdateStage*/int getUpdateStage() throws Exception
    {
        // If the field is not a bookmark reference, then there is no reason to defer the field's update.
        if (!isBookmarkRef())
            return FieldUpdateStage.MAIN_LOOP;

        return FieldRefUtil.getFieldUpdateStage(this, getBookmarkName(), false);
    }

    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        // Do not update the field, if it is not a bookmark reference.
        if (!isBookmarkRef())
            return new FieldUpdateActionDoNothing(this);

        // Forcibly insert field separator here.
        ensureSeparator(true);

        // SPEED Get a bookmark from a cache.
        Bookmark bookmark = FieldUtil.getCachedBookmark(this, getBookmarkName());
        if (bookmark == null)
            return new FieldUpdateActionInsertErrorMessage(this, BOOKMARK_NOT_DEFINED_ERROR_MESSAGE);

        // WORDSNET-22358 Get bookmark node range before old field result is removed because bookmark may be inside.
        NodeRange bookmarkRange = bookmark.getNodeRange();
        if (bookmarkRange.isVoid())
            return new FieldUpdateActionInsertErrorMessage(this, BOOKMARK_NOT_DEFINED_ERROR_MESSAGE);

        boolean hasBookmarkInResult = false;
        RefBoolean referenceToHasBookmarkInResult = new RefBoolean(hasBookmarkInResult);
        Paragraph bookmarkParagraph = FieldRefUtil.beginFieldUpdate(this, bookmark, false, /*out*/ referenceToHasBookmarkInResult);
        hasBookmarkInResult = referenceToHasBookmarkInResult.get();

        return FieldRefUtil.endFieldUpdate(
            this,
            bookmark,
            bookmarkRange,
            bookmarkParagraph,
            hasBookmarkInResult,
            false);
    }

    @ReservedForInternalUse @Deprecated
    
    public String /*IMergeFieldSurrogate.*/getMergeFieldName()
    {
        return getBookmarkName();
    }

    @ReservedForInternalUse @Deprecated
    
    public boolean /*IMergeFieldSurrogate.*/canWorkAsMergeField()
    {
        return !msString.isNullOrEmpty(getBookmarkName());
    }

    @ReservedForInternalUse @Deprecated
    
    public boolean /*IMergeFieldSurrogate.*/isMergeValueRequired()
    {
        return true;
    }

    /// <summary>
    /// Returns a value indicating whether this field is a bookmark reference.
    /// </summary>
    boolean isBookmarkRef()
    {
        return isBookmarkRef(getBookmarkName(), getType());
    }

    /// <summary>
    /// Returns a value indicating whether a field which code is strictly contained between the specified nodes
    /// is a bookmark reference. It should be a field of type <see cref="FieldType.FieldNone"/> or
    /// <see cref="FieldType.FieldRefNoKeyword"/>.
    /// </summary>
    static boolean isBookmarkRef(FieldStart fieldStart, FieldChar fieldCodeEnd)
    {
        return isBookmarkRef(FieldCodeParser.getFieldType(fieldStart, fieldCodeEnd), fieldStart.getFieldType());
    }

    /// <summary>
    /// Returns a value indicating whether a field with the specified field code and field type can be treated
    /// as a bookmark reference.
    /// </summary>
    private static boolean isBookmarkRef(String fieldCode, /*FieldType*/int fieldType)
    {
        Debug.assertTrue((fieldType == FieldType.FIELD_NONE) || (fieldType == FieldType.FIELD_REF_NO_KEYWORD));

        // Get an actual field type from the field code.
        /*FieldType*/int actualFieldType = FieldUtil.getFieldType(fieldCode);

        // Get a surrogate field type from the field start.
        // WORDSNET-8031 FieldType.FieldRefNoKeyword should be treated as FieldType.FieldNone.
        /*FieldType*/int surrogateFieldType = fieldType;
        if (surrogateFieldType == FieldType.FIELD_REF_NO_KEYWORD)
            surrogateFieldType = FieldType.FIELD_NONE;

        // If the field is broken, it is definitely not a bookmark reference.
        if (actualFieldType != surrogateFieldType)
            return false;

        // The field can be treated as a bookmark reference only if a bookmark name is provided through its code.
        // WORDSNET-10968
        // - Bookmark name are limited to 40 characters. All symbols over this limit are ignored.
        // - Bookmark name must contain only alphabetical characters, numbers and underline symbols.
        // - Bookmark name must start with an alphabetical character or an underline symbol.
        // WORDSNET-12492 Allow creation of bookmarks over 40 chars long when the target document has Pdf format.
        // Pdf spec has not any limitation for bookmark length. 40 characters bookmark length is a MSW feature.
        // WORDSNET-25891 MS Word disallows reserved names to be used as bookmark reference (MS-OI29500 2.1.495.g).
        return !G_RESERVED_NAMES.contains(fieldCode) && G_BOOKMARK_NAME_REGEX.isMatch(fieldCode);
    }

    /// <summary>
    /// Gets referenced bookmark name.
    /// </summary>
    private String getBookmarkName() { return getFieldCodeCache().getFieldType(); }

    private static /*final*/ Regex G_BOOKMARK_NAME_REGEX = new Regex("^(?!\\d)\\w+$");
    private static /*final*/ ISetGeneric<String> G_RESERVED_NAMES = new CaseInsensitiveHashSet(
        "ADDIN",
        "CONTROL",
        "DATA",
        "DISPLAYNFC",
        "EMBED",
        "FTNREF",
        "HTMLCONTROL");

    private static final String BOOKMARK_NOT_DEFINED_ERROR_MESSAGE = "Error! Bookmark not defined.";
}

