// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.FormatterPal;
import com.aspose.NumberFormattingOptions;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements the FORMTEXT field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts a text box style form field.
/// </remarks>
public class FieldFormText extends Field
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        FormField formField = getStart().getFormField();

        // WORDSNET-8618 we assume that document is not correctly formed
        if (formField == null)
            return null;

        switch (formField.getTextInputType())
        {
            case TextFormFieldType.CURRENT_DATE:
            case TextFormFieldType.CURRENT_TIME:
            {
                // Current date and time format is stored in the inner field code and the result is formatted
                // when the inner field is updated so we only need to copy the value into the outer form field.
                if (getUpdateContext().getFirstChild() == null)
                    return buildDefaultTextFieldUpdateAction(formField);

                String result = getUpdateContext().getFirstChild().getField().getResult();
                result = applyMaxLength(result, formField.getMaxLength());
                return new FieldUpdateActionApplyResult(this, result, false);
            }
            case TextFormFieldType.CALCULATED:
            {
                FieldFormula fieldFormula = getUpdateContext().getFirstChild() != null
                    ? ms.as(getUpdateContext().getFirstChild().getField(), FieldFormula.class)
                    : null;

                String stringResult = getFieldCodeCache().getArgumentAsString(INNER_FIELD_ARGUMENT_INDEX, true, true);
                double doubleResult = Double.NaN;

                // WORDSNET-12301 we should accept FORMULA field result directly as double.
                if (fieldFormula != null)
                {
                    Constant formulaResult = fieldFormula.evaluate();

                    // WORDSNET-14703 MS Word does not pass errors from formula field.
                    if (formulaResult instanceof ErrorConstant)
                        return buildDefaultTextFieldUpdateAction(formField);

                    if (formulaResult instanceof DoubleConstant)
                    {
                        doubleResult = formulaResult.getValueDouble();
                    }
                    else if (formulaResult instanceof StringConstant)
                    {
                        stringResult = formulaResult.getValueString();
                    }
                }

                if (Double.isNaN(doubleResult))
                    doubleResult = FormatterPal.tryParseDoubleInvariant(stringResult);

                if (!Double.isNaN(doubleResult))
                {
                    FieldOptions fieldOptions = fetchDocument().getFieldOptions();
                    /*NumberFormattingOptions*/int formattingOptions =
                        fieldOptions.getNumberFormattingOptions() |
                        NumberFormattingOptions.IGNORE_UNMATCHED_DIGIT_PLACEHOLDER |
                        NumberFormattingOptions.IS_MULTIPLY_PERCENT;
                    stringResult = FormatterPal.numberToStrMSWord(
                        doubleResult,
                        formField.getTextInputFormat(),
                        formattingOptions);
                }

                return new FieldUpdateActionApplyResult(this, stringResult, false);
            }
            case TextFormFieldType.REGULAR:
            case TextFormFieldType.NUMBER:
            {
                String result = formField.getTextInputDefault();
                if (!msString.isNullOrEmpty(result))
                {
                    result = applyMaxLength(result, formField.getMaxLength());

                    return new FieldUpdateActionApplyResult(this, result, false);
                }

                return buildDefaultTextFieldUpdateAction(formField);
            }
            default:
            {
                // All other text input types are not calculated.
                return null;
            }
        }
    }

    private NodeRange buildDefaultTextInputResultRange(int maxLength)
    {
        String result = applyMaxLength(FormField.DEFAULT_TEXT_INPUT_VALUE, maxLength);

        Paragraph para = new Paragraph(getDocument());

        for (int i = 0; i < result.length(); i++)
        {
            char c = result.charAt(i);
            para.appendChild(new SpecialChar(getDocument(), c, new RunPr()));
        }

        return new NodeRange(para.getFirstChild(), para.getLastChild());
    }

    private FieldUpdateAction buildDefaultTextFieldUpdateAction(FormField formField)
    {
        NodeRange resultRange = buildDefaultTextInputResultRange(formField.getMaxLength());
        return new FieldUpdateActionApplyResult(this, new NodeRangeFieldResult(resultRange));
    }

    private static String applyMaxLength(String result, int maxLength)
    {
        if (msString.isNullOrEmpty(result))
            return result;

        if (maxLength <= 0)
            return result;

        if (maxLength >= result.length())
            return result;

        return result.substring((0), (0) + (maxLength));
    }

    /*override*/ void beforeUnlink()
    {
        FormField formField = getStart().getFormField();
        if (formField == null)
            return;

        BookmarkStart bookmarkStart = formField.getBookmarkStart();
        if (bookmarkStart == null)
            return;

        getStart().insertPrevious(bookmarkStart);
    }

    /*override*/ FieldResultApplier buildResultApplier(String result)
    {
        if (!msString.equals(result, FormField.DEFAULT_TEXT_INPUT_VALUE))
            return super.buildResultApplier(result);

        NodeRange resultRange = buildDefaultTextInputResultRange(0);
        return new NodeRangeResultApplier(this, resultRange);
    }

    private static final int INNER_FIELD_ARGUMENT_INDEX = 0;
}

