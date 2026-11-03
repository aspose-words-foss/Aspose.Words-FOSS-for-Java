// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.StringUtil;
import com.aspose.TextualNumber;
import com.aspose.CharCase;
import com.aspose.ms.System.msString;
import com.aspose.words.ref.Ref;
import com.aspose.words.ref.RefBoolean;
import com.aspose.words.ref.RefDouble;


/// <summary>
/// Provides typed access to field's numeric, date and time, and general formatting.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
public class FieldFormat
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldFormat(Field field)
    {
        mField = field;
    }

    /// <summary>
    /// Formats the calculated result based on the field format switches. Returns true if any formatting was applied.
    /// </summary>
    boolean formatResult(Constant value, /*out*/Ref<FieldFormattingResult> formattingResult) throws Exception
    {
        RichString result = null;
        boolean preserveRichFormatting = false;
        Ref<RichString> referenceToResult = new Ref<RichString>(result);
        RefBoolean referenceToPreserveRichFormatting = new RefBoolean(preserveRichFormatting);
        boolean isFormattedAsNumber = tryConvertNumber(value, /*out*/ referenceToResult) ||
                                   tryFormatNumber(value, /*out*/ referenceToResult, /*out*/ referenceToPreserveRichFormatting) ||
                                   tryFormatPageNumber(value, /*out*/ referenceToResult);
        result = referenceToResult.get();
        preserveRichFormatting = referenceToPreserveRichFormatting.get();

        boolean isFormattedAsDateTime = false;
        if (!isFormattedAsNumber)
        {
            referenceToResult.set(result);
            isFormattedAsDateTime = tryFormatDateTime(value, /*out*/ referenceToResult);
            result = referenceToResult.get();

            // WORDSNET-7807 Try to applay numeric format to DateTime value.
            if (isFormattedAsDateTime)
                result = tryApplyNumericFormatToDateTime(result);
        }

        referenceToResult.set(result);
        boolean isFormattedAsString = tryFormatString(result, /*out*/ referenceToResult);
        result = referenceToResult.get();

        formattingResult.set(result != null
            ? new FieldFormattingResult(result, preserveRichFormatting)
            : null);
        return isFormattedAsNumber || isFormattedAsDateTime || isFormattedAsString;
    }

    /// <summary>
    /// Attempts to apply date/time formatting to the specified constant based on the current update context.
    /// Returns true if the formatting was applied.
    /// </summary>
    /// <param name="value"></param>
    /// <param name="result"></param>
    /// <returns></returns>
    private boolean tryFormatDateTime(Constant value, /*out*/Ref<RichString> result)
    {
        result.set(RichString.createFromString(value.getValueString()));

        String format = getDateTimeFormat();
        boolean isDateTimeFormatSpecified = format != null;

        if (!isDateTimeFormatSpecified)
            format = mField.getDefaultDateTimeFormat();

        if (format == null)
            return false;

        IFieldResultFormatter fieldResultFormatter = isDateTimeFormatSpecified ? mField.fetchDocument().getFieldOptions().getResultFormatter() : null;
        String formattedResult = value.tryFormatDateTime(format, mField.getFieldCodeCache().getLanguageIdFarEast(), fieldResultFormatter);
        if (formattedResult != null)
        {
            result.set(RichString.createFromString(formattedResult));
            return true;
        }

        return false;
    }

    /// <summary>
    /// Attempts to apply numeric formatting to the DateTime value.
    /// Returns formatted string or original string if Numeric format wasn't applied.
    /// </summary>
    /// <param name="value">Converted DateTime value.</param>
    /// <returns></returns>
    private RichString tryApplyNumericFormatToDateTime(RichString value)
    {
        if (RichStringBehaviour.isNullOrEmptyInternal(value))
            return value;

        String numericFormat = getGeneralFormats().getNumericFormatString();
        if (StringUtil.hasChars(numericFormat))
        {
            RichString numericFormattedResult = null;
            Ref<RichString> referenceToNumericFormattedResult = new Ref<RichString>(numericFormattedResult);
            boolean isConverted = tryConvertNumber(new StringConstant(value.toSystemString()), /*out*/ referenceToNumericFormattedResult);
            numericFormattedResult = referenceToNumericFormattedResult.get();

            if (isConverted)
                return numericFormattedResult;

        }

        return value;
    }

    /// <summary>
    /// Attempts to apply numeric formatting to the specified string based on the current update context.
    /// Returns true if the formatting was applied.
    /// </summary>
    private boolean tryFormatNumber(Constant value, /*out*/Ref<RichString> result, /*out*/RefBoolean preserveRichFormatting) throws Exception
    {
        result.set(RichString.createFromString(value.getValueString()));
        preserveRichFormatting.set(false);

        RichString format = getRichNumericFormat();

        if (RichStringBehaviour.isNullOrEmptyInternal(format))
            return false;

        format = format.trimFormat(FontAttr.RSID_R, FontAttr.RSID_R_PR);

        FieldFormattingResult formattedResult = value.tryFormatNumber(format, mField);
        if (formattedResult != null)
        {
            result.set(formattedResult.getText());
            preserveRichFormatting.set(formattedResult.getPreserveRichFormatting());
            return true;
        }

        return false;
    }

    private boolean tryConvertNumber(Constant value, /*out*/Ref<RichString> result)
    {
        result.set(null);

        double doubleValue = 0.0;
        RefDouble referenceToDoubleValue = new RefDouble(doubleValue);
        boolean outRefCondition0 = !value.tryConvertToDouble(/*out*/ referenceToDoubleValue);
        doubleValue = referenceToDoubleValue.get();
        if (outRefCondition0)
            return false;

        long intValue = (long)doubleValue;

        // Simply ignore formatting switch if the number is invalid. Word displays
        // 'Error! Number cannot be represented in specified format.' message in this case.
        // WORDSNET-21047 MS Word does not allow numbers over 999999, but we DO.
        if (intValue < 0)
            return false;

        /*GeneralFormat*/int format = getGeneralFormats().getNumericFormat();
        if (format != GeneralFormat.NONE)
        {
            IFieldResultFormatter resultFormatter = mField.fetchDocument().getFieldOptions().getResultFormatter();
            if (resultFormatter != null)
            {
                String formatted = resultFormatter.format(doubleValue, format);
                result.set(RichString.createFromString(formatted));
                if (result.get() != null)
                    return true;
            }

            switch (format)
            {
                case GeneralFormat.DB_CHAR:
                case GeneralFormat.SB_CHAR:
                    return false;
                case GeneralFormat.DOLLAR_TEXT:
                    result.set(RichString.createFromString(TextualNumber.toDollarText(doubleValue)));
                    break;
                default:
                    result.set(RichString.createFromString(NumberConverter.numberToLocalizedString(intValue, GeneralFormatUtil.generalFormatToNumberStyle(format), false)));
                    break;
            }

            return true;
        }

        return false;
    }

    /// <summary>
    /// Attempts to format a number using a page number format taken from a corresponding section.
    /// </summary>
    /// <param name="value"></param>
    /// <param name="result"></param>
    /// <returns></returns>
    private boolean tryFormatPageNumber(Constant value, /*out*/Ref<RichString> result) throws Exception
    {
        result.set(null);

        double doubleValue = 0.0;
        RefDouble referenceToDoubleValue = new RefDouble(doubleValue);
        boolean outRefCondition1 = !value.tryConvertToDouble(/*out*/ referenceToDoubleValue);
        doubleValue = referenceToDoubleValue.get();
        if (outRefCondition1)
            return false;

        Section pageNumberFormatSection = mField.getPageNumberFormatSection();
        if (pageNumberFormatSection != null)
        {
            result.set(RichString.createFromString(NumberConverter.numberToString(
                (int)doubleValue,
                pageNumberFormatSection.getSectPr().getPageNumberStyleFinal(),
                false)));

            // Get a chapter number and include it to result, if it is required. Note, that MS Word does not include
            // an empty chapter number (even if the corresponding chapter title paragraph is found).
            String chapterNumber = getChapterNumber(pageNumberFormatSection);
            if (StringUtil.hasChars(chapterNumber))
            {
                char separator = getChapterPageSeparatorChar(pageNumberFormatSection.getSectPr().getChapterPageSeparatorFinal());

                result.set(new RichStringBuilder(chapterNumber.length() + 1 + result.get().getLength())
                    .append(chapterNumber, new RunPr())
                    .append(separator, new RunPr())
                    .appendInternal(result.get())
                    .toRichString());
            }

            return true;
        }

        return false;
    }

    /// <summary>
    /// Returns a chapter number string for the given field in the context of the specified format section.
    /// </summary>
    private String getChapterNumber(Section formatSection) throws Exception
    {
        // If a chapter number should not be added to a page number, simply return null.
        if (formatSection.getSectPr().getHeadingLevelForChapterFinal() == 0)
            return null;

        // Find the corresponding chapter title paragraph and if it is found, use it to generate a chapter number to return.
        Paragraph chapterTitleParagraph = ChapterTitleParagraphFinder.findChapterTitleParagraph(mField, formatSection);

        return (chapterTitleParagraph != null)
            ? ListLabelUtil.buildListLabel(chapterTitleParagraph.getListLabel().getNumberStateFinal(), new FieldFormatListLabelBuildBehaviour())
            : null;
    }

    /// <summary>
    /// Returns a character corresponding to the specified <see cref="ChapterPageSeparator"/> value.
    /// </summary>
    private static char getChapterPageSeparatorChar(/*ChapterPageSeparator*/int separator)
    {
        switch (separator)
        {
            case ChapterPageSeparator.HYPHEN:
                return '-';
            case ChapterPageSeparator.PERIOD:
                return '.';
            case ChapterPageSeparator.COLON:
                return ':';
            case ChapterPageSeparator.EM_DASH:
                return '—';
            case ChapterPageSeparator.EN_DASH:
                return '–';
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "separator");
        }
    }

    /// <summary>
    /// Attempts to apply general formatting to the specified string based on the current update context.
    /// Returns true if the formatting was applied.
    /// </summary>
    /// <param name="value"></param>
    /// <param name="result"></param>
    /// <returns></returns>
    private boolean tryFormatString(RichString value, /*out*/Ref<RichString> result)
    {
        result.set(value);
        boolean isFormatted = false;

        IFieldResultFormatter resultFormatter = mField.fetchDocument().getFieldOptions().getResultFormatter();
        for (/*GeneralFormat*/int format : (Iterable</*GeneralFormat*/Integer>) getGeneralFormats())
        {
            /*CharCase*/int charCase = GeneralFormatUtil.generalFormatToCharCase(format);
            if (charCase != CharCase.DEFAULT)
            {
                result.set(formatString(result.get(), format, charCase, resultFormatter));
                isFormatted = true;
            }
        }

        return isFormatted;
    }

    private static RichString formatString(RichString value, /*GeneralFormat*/int format, /*CharCase*/int charCase, IFieldResultFormatter resultFormatter)
    {
        if (resultFormatter != null)
        {
            String result = resultFormatter.format(value.toSystemString(), format);
            if (result != null)
                return RichString.createFromString(result);
        }

        return RichStringUtils.formatCharCase(value, charCase);
    }

    /// <summary>
    /// Returns an implementation of a field result format provider.
    /// </summary>
    /// <returns></returns>
    IFieldResultFormatProvider getFieldResultFormatProvider()
    {
        CompositeFieldResultFormatProvider provider = new CompositeFieldResultFormatProvider();

        // WORDSNET-14698 MS Word applies result formats in order they are specified in the field code.
        // Applying particular format twice is redundant. That is why we iterate formats in backward direction
        // and use only latest instance of particular format.
        for (int i = getGeneralFormats().getCount() - 1; i >= 0; i--)
        {
            switch (getGeneralFormats().get(i))
            {
                case GeneralFormat.CHAR_FORMAT:
                    // CHARFORMAT - take formatting from the first meaningful run of field code.
                    provider.insertProvider(new CharFormatProvider(mField));
                    break;
                case GeneralFormat.MERGE_FORMAT:
                    // MERGEFORMAT - at the moment we simply take formatting from the first meaningful run of
                    // field result, but it seems like there is a more sophisticated algorithm. To be investigated.
                    provider.insertProvider(new MergeFormatProvider(mField));
                    break;
                default:
                    break;
            }
        }

        if (provider.isEmpty())
        {
            IFieldResultFormatProvider fieldProvider = ms.as(mField, IFieldResultFormatProvider.class);
            if (fieldProvider != null)
            {
                provider.appendProvider(fieldProvider);
            }
            else if (!FieldUtil.retainOriginalFormatting(mField) && msString.isNullOrEmpty(getNumericFormat()))
            {
                provider.appendProvider(new CharFormatProvider(mField));
            }
        }

        // WORDSNET-1586 Apply noproof attribute to mergefield field result.
        if (FieldUtil.disableResultSpellChecking(mField.getType()))
            provider.appendProvider(NoProofingFormatProvider.INSTANCE);

        return provider;
    }

    private FieldCode getFieldCode() { return mField.getFieldCodeCache(); }

    /// <summary>
    /// Gets or sets a formatting that is applied to a numeric field result. Corresponds to the \# switch.
    /// </summary>
    public String getNumericFormat() { return getFieldCode().getSwitchArgumentAsString(NUMERIC_FORMAT_SWITCH); }
    public void setNumericFormat(String value) throws Exception { getFieldCode().setSwitch(NUMERIC_FORMAT_SWITCH, value); }

    RichString getRichNumericFormat() { return getFieldCode().getSwitchArgumentAsRichString(NUMERIC_FORMAT_SWITCH); }

    /// <summary>
    /// Gets or sets a formatting that is applied to a date and time field result. Corresponds to the \@ switch.
    /// </summary>
    public String getDateTimeFormat() { return getFieldCode().getSwitchArgumentAsString(DATE_TIME_FORMAT_SWITCH); }
    public void setDateTimeFormat(String value) throws Exception { getFieldCode().setSwitch(DATE_TIME_FORMAT_SWITCH, value); }

    /// <summary>
    /// Gets a collection of general formats that are applied to a numeric, text or any field result.
    /// Corresponds to the \* switches.
    /// </summary>
    public GeneralFormatCollection getGeneralFormats() { return (mGeneralFormats != null ? mGeneralFormats : (mGeneralFormats = new GeneralFormatCollection(getFieldCode()))); }

    private /*final*/ Field mField;
    private GeneralFormatCollection mGeneralFormats;

    static final String NUMERIC_FORMAT_SWITCH = "\\#";
    static final String DATE_TIME_FORMAT_SWITCH = "\\@";
    static final String GENERAL_FORMAT_SWITCH = "\\*";

    private static class FieldFormatListLabelBuildBehaviour implements IListLabelBuildBehaviour
    {
        public void notifyListNumberAppended(int listLabelLength)
        {
            mListLabelLengthAtLastListNumber = listLabelLength;
        }

        public boolean shouldAppendNotListNumberChar(char c)
        {
            return mListLabelLengthAtLastListNumber != 0;
        }

        public int finalizeListLabelLength(int listLabelLength)
        {
            return mListLabelLengthAtLastListNumber;
        }

        public /*NumberStyle*/int normalizeNumberStyle(/*NumberStyle*/int numberStyle)
        {
            switch (numberStyle)
            {
                case NumberStyle.NUMBER:
                case NumberStyle.ORDINAL:
                case NumberStyle.ORDINAL_TEXT:
                    return NumberStyle.ARABIC;
                default:
                    return numberStyle;
            }
        }

        private int mListLabelLengthAtLastListNumber;
    }
}

