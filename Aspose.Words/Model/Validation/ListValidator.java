// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/01/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.IntToObjDictionary;
import java.util.Collections;
import com.aspose.HashSetGeneric;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import com.aspose.IntToIntDictionary;


/// <summary>
/// Performs validation of list collection i.e makes lists 1-based sorted array and sorts list definitions. Also updates paragraph ListId if needed.
/// </summary>
/// <remarks>
/// AM. List collection should be validated mostly for binary DOC format because other formats has ListId attributes for list elements and 
/// therefore list collection might be written unsorted (with minor writers update). But for binary doc the following are required:
/// - list elements are written sorted by ListId (they have no ListId and accessed by element position) 
/// - list definitions are written sorted by ListDefId for binary DOC otherwise Word can lost list formatting (Seems Word use binary search).
///
/// I decided to sort lists regardless save format to get uniform behavior.
/// </remarks>
class ListValidator
{
    ListValidator(ListCollection lists, SaveInfo saveInfo, IWarningCallback warningCallback)
    {
        mLists = lists;
        // Check this once and cache result for speed reason.
        mIsTranslationRequired = isTranslationRequired();
        mSaveInfo = saveInfo;
        mWarningCallback = warningCallback;
    }

    /// <summary>
    /// Translates list IDs in document styles.
    /// </summary>
    void visitDocumentStart()
    {
        // WORDSNET-27209 List ID translation in document styles must be performed at document start because
        // TableFormattingExpander may expand list ID attributes from styles to paragraph properties at visiting
        // a table end.
        if (mIsTranslationRequired)
        {
            StyleCollection styles = mLists.getDocument().getStyles();
            for (Style style : styles)
                translateListId(style.getParaPr());
        }
    }

    /// <summary>
    /// Validates list collection.
    /// </summary>
    /// <remarks>
    /// Valid list collection is:
    ///   - lists are sorted by ListId and are 1-based indexed array without gaps.
    ///   - list definitions are sorted by ListDefId.
    /// </remarks>
    void visitDocumentEnd() throws Exception
    {
        if (mIsTranslationRequired)
        {
            // Update lists with 1-based ListId.
            mLists.removeTranslation();
        }

        // Remove duplicated list definitions.
        IntToObjDictionary<ListDef> listDefs = new IntToObjDictionary<ListDef>();
        int index = 0;
        while (index < mLists.getListDefs().size())
        {
            ListDef listDef = mLists.getListDefs().get(index);

            if (listDefs.get(listDef.getListDefId()) != null)
            {
                warn(WarningType.MINOR_FORMATTING_LOSS, WarningStrings.LIST_VALIDATOR_NON_UNIQUE_DEFINITION);
                mLists.getListDefs().remove(index);
            }
            else
            {
                listDefs.add(listDef.getListDefId(), listDef);
                index++;
            }
        }

        validateListDefs();

        // Sort list definitions.
        if (!isListDefsSorted())
            Collections.sort(mLists.getListDefs());

        validatePictureBullets();

        checkDurableIds();
    }

    /// <summary>
    /// Checks <see cref="List.DurableId"/> values for duplicates. Marks the document as having DOCX extensions
    /// if any non-empty <see cref="List.DurableId"/> exists.
    /// </summary>
    private void checkDurableIds()
    {
        if (isUserDefinedCompliance() && (getOoxmlCompliance() == OoxmlComplianceCore.ECMA_376))
            return; // DurableId is not preserved.

        HashSetGeneric<Integer> durableIds = new HashSetGeneric<Integer>();

        // New lists are added to the end of mLists, so a simple mList enumeration preserves DurableId for older lists.
        for (List list : mLists)
        {
            int durableId = list.getDurableId();
            if (durableId != 0)
            {
                OoxmlComplianceInfo.markAsHasDocxExtensionsOf(mSaveInfo.Document, MsWordVersionCore.WORD_2016);

                if (!durableIds.contains(durableId))
                    durableIds.add(durableId);
                else
                    list.setDurableId(0); // Remove duplicate.
            }
        }
    }

    /// <summary>
    /// Updates paragraph ListId if needed.
    /// </summary>
    /// <param name="paragraph"></param>
    void visitParagraphStart(Paragraph paragraph)
    {
        Debug.assertTrue(paragraph.isListItemOriginal() || paragraph.isListItemFinal());

        if (mIsTranslationRequired)
        {
            translateListId(paragraph.getParaPr());

            if (paragraph.getParaPr().hasFormatRevision())
                translateListId((ParaPr)paragraph.getParaPr().getFormatRevision().getRevPr());
        }
    }

    /// <summary>
    /// Updates ListId to new value taken from translation table.
    /// </summary>
    private void translateListId(ParaPr paraPr)
    {
        // Do not translate if it's not a list item.
        if (paraPr.getListId() == 0)
            return;

        paraPr.setListId(mLists.getTranslatedListId(paraPr.getListId()));
    }

    /// <summary>
    /// Validates list definitions.
    /// </summary>
    /// <remarks>
    /// AM. Currently validates NumberFormat for bulleted lists.
    /// </remarks>
    private void validateListDefs()
    {
        ListDef[] listDefs = msArrayList.toArray(mLists.getListDefs(), new ListDef[0]);

        for (ListDef listDef : listDefs)
        {
            for (ListLevel level : listDef.getLevels())
            {
                if (level.getNumberStyle() == NumberStyle.BULLET && isPlaceHolder(level.getNumberFormat()))
                    level.setNumberFormat("");

                if ((level.getNumberStyle() == NumberStyle.CUSTOM) && !isCustomNumberStyleSupported())
                {
                    warn(WarningType.MINOR_FORMATTING_LOSS,
                        msString.format(WarningStrings.NOT_SUPPORTED_NUMBER_STYLE, level.getNumberStyle(),
                            mSaveInfo.getSaveFormat()));
                    level.setNumberStyle(NumberStyle.ARABIC);
                }
            }

            // WORDSNET-11410 MS Word removes reference to style from the list definition if 
            // list definition of this style hasn't reference to the style.
            // Despite we do this verification in DocxDocumentReaderBase.EnsureStylePointsToExistingList(),
            // the document might be changed later (see for example TestJira7405). So, we do this here again.
            Style style = listDef.getStyle();
            if ((style != null) && (style.getList() != null) && (style.getList().getListDef() != null))
            {
                Style referenceStyleOfStyleListDef = style.getList().getListDef().getStyle();
                if (referenceStyleOfStyleListDef == null)
                    listDef.setListStyleIstd(StyleIndex.NO_LIST);
            }
        }
    }

    /// <summary>
    /// Validates picture bullets.
    /// </summary>
    private void validatePictureBullets() throws Exception
    {
        for (int i = 0; i < mLists.getPictureBulletCount(); i++)
        {
            Shape shape = mLists.getPictureBullet(i);
            shape.fixZeroSize(false);
        }
    }

    /// <summary>
    /// Returns false if the document save format does not support the Custom number style. 
    /// Checks only for MS Word formats: DOCX, DOC, RTF, WML. The other formats can process it in their writers.
    /// </summary>
    private boolean isCustomNumberStyleSupported()
    {
        /*SaveFormat*/int format = mSaveInfo.getSaveFormat();
        return !(
            (mSaveInfo.isOoxmlFormat() && (getOoxmlCompliance() == OoxmlComplianceCore.ECMA_376)) ||
            (format == SaveFormat.DOC) ||
            (format == SaveFormat.DOT) ||
            (format == SaveFormat.WORD_ML) ||
            (format == SaveFormat.RTF)
        );
    }

    /// <summary>
    /// Indicates that given number format is placeholder.
    /// </summary>
    private static boolean isPlaceHolder(String numberFormat)
    {
        return numberFormat.length() > 0 && ((int)numberFormat.charAt(0)) < 10;
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(/*WarningType*/int warningType, String description)
    {
        if (mWarningCallback != null)
            mWarningCallback.warning(new WarningInfo(warningType, WarningSource.VALIDATOR, description));
    }

    /// <summary>
    /// Checks that list definitions are sorted by ListDefId.
    /// </summary>
    private boolean isListDefsSorted()
    {
        if (mLists.getListDefCount() == 0)
            return true;

        int prev = Integer.MIN_VALUE;
        for (int i = 0; i < mLists.getListDefs().size(); i++)
        {
            ListDef listDef = mLists.getListDefs().get(i);

            if (listDef.getListDefId() <= prev)
                return false;

            prev = listDef.getListDefId();
        }

        return true;
    }

    /// <summary>
    /// Indicates that lists are NOT 1-based sorted array without gaps. 
    /// This means that paragraph's ListId should be updated.
    /// </summary>
    private boolean isTranslationRequired()
    {
        // Don't need translation of empty lists.
        if (mLists.getCount() == 0)
            return false;

        // Translation key-values should be the same.
        IntToIntDictionary.Enumerator enumerator = mLists.getListIdTranslationTable().iterator();
        while (enumerator.moveNext())
        {
            if (enumerator.getCurrentKey() != enumerator.getCurrentValue())
                return true;
        }

        // Translation is needed if lists are not 1-based array.
        int firstListId = mLists.getListIdTranslationTable().get(1);
        if (IntToIntDictionary.isNullSubstitute(firstListId))
            throw new IllegalStateException();

        return (firstListId != 1);
    }

    /// <summary>
    /// Specifies the OOXML version for the output document on saving as DOCX formats.
    /// </summary>
    private /*OoxmlComplianceCore*/int getOoxmlCompliance()
    {
        return OoxmlComplianceInfo.getCompliance(mSaveInfo.Document.getComplianceInfo(),
            ms.as(mSaveInfo.SaveOptions, OoxmlSaveOptions.class));
    }

    /// <summary>
    /// Gets a flag indicating that user has set compliance explicitly.
    /// </summary>
    private boolean isUserDefinedCompliance()
    {
        OoxmlSaveOptions ooxmlSaveOptions = ms.as(mSaveInfo.SaveOptions, OoxmlSaveOptions.class);
        return (ooxmlSaveOptions != null) && (ooxmlSaveOptions.getUserSetCompliance());
    }

    private /*final*/ SaveInfo mSaveInfo;
    private /*final*/ IWarningCallback mWarningCallback;

    /// <summary>
    /// ListCollection being validated.
    /// </summary>
    private /*final*/ ListCollection mLists;
    /// <summary>
    /// Holds IsTranslationRequired result.
    /// </summary>
    private /*final*/ boolean mIsTranslationRequired;
}

