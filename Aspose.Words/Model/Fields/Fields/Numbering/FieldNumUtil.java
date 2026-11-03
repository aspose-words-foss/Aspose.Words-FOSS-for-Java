// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/01/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import java.util.ArrayList;
import com.aspose.Debug;


/// <summary>
/// An utility class that provides methods to work with LISTNUM, AUTONUM, AUTONUMOUT and AUTONUMLGL fields.
/// </summary>
/*static*/ class FieldNumUtil
{
	/* Simulation of static class by using private constructor */
	private FieldNumUtil()
	{}

    /// <summary>
    /// Returns a value indicating whether the specified field type relates to LISTNUM, AUTONUM, AUTONUMOUT
    /// or AUTONUMLGL field.
    /// </summary>
    static boolean isFieldNum(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_LIST_NUM:
            case FieldType.FIELD_AUTO_NUM:
            case FieldType.FIELD_AUTO_NUM_OUTLINE:
            case FieldType.FIELD_AUTO_NUM_LEGAL:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Gets a separator character by the specified separator string.
    /// </summary>
    static char getSeparatorCharacterCore(String separatorCharacter)
    {
        return StringUtil.hasChars(separatorCharacter) ? separatorCharacter.charAt(0) : DEFAULT_SEPARATOR;
    }

    /// <summary>
    /// Gets a list of fake result runs for LISTNUM, AUTONUM, AUTONUMOUT or AUTONUMLGL field basing on
    /// the corresponding field start and a containing field.
    /// </summary>
    static ArrayList<Run> getFakeResultList(Field refField) throws Exception
    {
        return getFakeResultList(refField.getStart(), refField);
    }

    /// <summary>
    /// Gets a list of fake result runs for LISTNUM, AUTONUM, AUTONUMOUT or AUTONUMLGL field basing on
    /// the corresponding field start and a containing field.
    /// </summary>
    public static ArrayList<Run> getFakeResultList(FieldStart fieldNumStart, Field refField) throws Exception
    {
        FakeResultInfoProvider infoProvider = getFakeResultInfoProvider(fieldNumStart, refField);
        if (infoProvider == null)
            return new ArrayList<Run>();

        return FieldTextHelper.getSingleLineTextRunListBidiAware(
            infoProvider.getListLabel().getText(),
            infoProvider.getDocument(),
            infoProvider,
            infoProvider.isRtlEmbedding());
    }

    /// <summary>
    /// Gets a range of fake result runs for LISTNUM, AUTONUM, AUTONUMOUT or AUTONUMLGL field basing on
    /// the corresponding field start and a containing field.
    /// </summary>
    static NodeRange getFakeResultNodeRange(Field refField) throws Exception
    {
        FakeResultInfoProvider infoProvider = getFakeResultInfoProvider(refField.getStart(), refField);
        if (infoProvider == null)
            return null;

        return FieldTextHelper.getSingleLineTextRunRangeBidiAware(
            infoProvider.getListLabel().getText(),
            infoProvider.getDocument(),
            infoProvider,
            infoProvider.isRtlEmbedding());
    }

    /// <summary>
    /// Gets an <see cref="ListNumberState"/> object associated with LISTNUM, AUTONUM, AUTONUMOUT or AUTONUMLGL field.
    /// </summary>
    static ListNumberState getFieldListNumberState(Field refField)
    {
        FakeResultInfoProvider infoProvider = getFakeResultInfoProvider(refField.getStart(), refField);
        if (infoProvider == null)
            return null;

        return infoProvider.getListLabel().getState();
    }

    /// <summary>
    /// A factory method to create a <see cref="FakeResultInfoProvider"/> instance.
    /// </summary>
    private static FakeResultInfoProvider getFakeResultInfoProvider(FieldStart fieldNumStart, Field refField)
    {
        Debug.assertTrue(isFieldNum(refField.getType()) == (fieldNumStart == refField.getStart()));

        Document document = refField.fetchDocument();
        Debug.assertTrue(document == fieldNumStart.fetchDocument());

        FieldNumListLabel listLabel = document.getFieldNumListLabel(fieldNumStart);
        return ((listLabel != null) && listLabel.hasText())
            ? new FakeResultInfoProvider(listLabel, fieldNumStart, refField, document)
            : null;
    }

    /// <summary>
    /// Provides information about LISTNUM, AUTONUM, AUTONUMOUT or AUTONUMLGL fake result. Implements
    /// <see cref="IFieldRunPrProvider"/> in the way to get MS-Word-like text look.
    /// </summary>
    private static class FakeResultInfoProvider implements IFieldRunPrProvider
    {
        /// <summary>
        /// Ctor.
        /// </summary>
        FakeResultInfoProvider(FieldNumListLabel listLabel, FieldStart fieldNumStart, Field refField, Document document)
        {
            mListLabel = listLabel;
            mFieldNumStart = fieldNumStart;
            mRefField = refField;
            mDocument = document;
        }

        /// <summary>
        /// Returns a value indicating whether a BIDI-text for the list label should be treated as if it was
        /// contained within RTL paragraph or not.
        /// </summary>
        boolean isRtlEmbedding()
        {
            if (!getDocument().getFieldOptions().isBidiTextSupportedOnUpdate())
                return false;

            switch (mRefField.getType())
            {
                case FieldType.FIELD_TOC:
                    // It seems like MS Word always uses LTR here. To be investigated.
                    return false;
                case FieldType.FIELD_REF:
                case FieldType.FIELD_REF_NO_KEYWORD:
                case FieldType.FIELD_NONE:
                    // See Field.IsRtlResultParagraph() for details.
                    return (/*boolean*/Boolean)mFieldNumStart.getRunPr().getBidi().resolveFetchInheritedRunAttrWithNull(mFieldNumStart, FontAttr.BIDI);
                default:
                    if (isFieldNum(mRefField.getType()))
                        return mRefField.getStart().getParentParagraph().getParagraphFormat().getBidi();

                    throw new IllegalStateException("Invalid reference field type for the operation.");
            }
        }

        public RunPr /*IFieldRunPrProvider.*/getRunPr()
        {
            RunPr runPr;
            if (isFieldNum(mRefField.getType()))
            {
                // LISTNUM, AUTONUM, AUTONUMOUT and AUTONUMLGL fields themselves are FWR fields and hence their results
                // are not a part of a document content so we need fully expand font properties for these results' runs.
                runPr = mFieldNumStart.getExpandedRunPr(RunPrExpandFlags.LAYOUT);
            }
            else
            {
                // Clone font properties of the containing field's start.
                runPr = AttrCollectionExtension.deepClone(mRefField.getStart().getRunPr());

                // Add LISTNUM, AUTONUM, AUTONUMOUT or AUTONUMLGL field start's font properties' overrides.
                // This step should be skipped if the containing field is TOC.
                if (mRefField.getType() != FieldType.FIELD_TOC)
                    mFieldNumStart.getRunPr().expandTo(runPr);
            }

            // Add list label font properties' overrides if any.
            if (getListLabel().hasRunPrOverrides())
                getListLabel().getRunPrOverrides().expandTo(runPr);

            return runPr;
        }

        /// <summary>
        /// Gets a <see cref="FieldNumListLabel"/> instance associated with this instance.
        /// </summary>
        FieldNumListLabel getListLabel() { return mListLabel; };

        private FieldNumListLabel mListLabel;

        /// <summary>
        /// Gets a document associated with this instance.
        /// </summary>
        Document getDocument() { return mDocument; };

        private Document mDocument;

        private /*final*/ FieldStart mFieldNumStart;
        private /*final*/ Field mRefField;
    }

    /// <summary>
    /// The default separator character used to build AUTONUM fields' fake results.
    /// </summary>
    static final char DEFAULT_SEPARATOR = '.';
}

