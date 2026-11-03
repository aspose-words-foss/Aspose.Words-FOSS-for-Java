// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/12/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.words.ref.Ref;


/// <summary>
/// Applies a field result, whether by updating a real result or replacing the whole field with updated result.
/// </summary>
class FieldUpdateActionApplyResult extends FieldUpdateAction
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldUpdateActionApplyResult(Field field, String result)
    {
    	this(field, result, true);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    FieldUpdateActionApplyResult(Field field, String result, boolean formatResult)
    {
    	this(
            field,
            (result != null ? new FieldResult(new StringConstant(result)) : null),
            formatResult);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    FieldUpdateActionApplyResult(Field field, Constant result)
    {
    	this(field, result, true);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    FieldUpdateActionApplyResult(Field field, Constant result, boolean formatResult)
    {
    	this(
            field,
            (result != null ? new FieldResult(result) : null),
            formatResult);
	
    }


    /// <summary>
    /// Ctor.
    /// </summary>
    FieldUpdateActionApplyResult(Field field, IFieldResult result)
    {
    	this(field, result, true);
	
    }

    FieldUpdateActionApplyResult(Field field, String result, /*ParagraphBreakCharReplacement*/int paragraphBreakCharReplacement)
    {
    	this(field, result);
	
        mParagraphBreakCharReplacement = paragraphBreakCharReplacement;
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    FieldUpdateActionApplyResult(Field field, IFieldResult result, boolean formatResult)
    {
    	super(field);
	
        mResult = result;
        mFormatResult = formatResult;
    }

    /*override*/ void perform() throws Exception
    {
        FieldResultApplier resultApplier = getResultApplier();
        resultApplier.applyResult();

        getField().getUpdateContext().joinOldResultNodes();
    }

    private FieldResultApplier getResultApplier() throws Exception
    {
        if (mResult != null)
        {
            Constant result = mResult.getFieldResultValue();
            String resultString = result.getValueString();

            if (resultString == null)
                return new TextResultApplier(getField(), "");

            FieldFormattingResult formattingResult = new FieldFormattingResult(resultString);
            Ref<FieldFormattingResult> referenceToFormattingResult = new Ref<FieldFormattingResult>(formattingResult);
            boolean isFormatted = mFormatResult && getField().getFormat().formatResult(result, /*out*/ referenceToFormattingResult);
            formattingResult = referenceToFormattingResult.get();

            NodeRange resultRange = mResult.getFieldResultRange();
            if ((resultRange == null) || isFormatted)
            {
                // Source formatting is unavailable or overridden by a field switch, use a text representation
                // of the calculated result.
                return new TextResultApplier(getField(), formattingResult, mParagraphBreakCharReplacement);
            }
            else
            {
                // Copy formatted content.
                return new NodeRangeResultApplier(getField(), resultRange);
            }
        }
        else
        {
            // Set empty result.
            return new TextResultApplier(getField(), "");
        }
    }

    private /*final*/ IFieldResult mResult;
    private /*final*/ boolean mFormatResult;
    private /*final*/ /*ParagraphBreakCharReplacement*/int mParagraphBreakCharReplacement;
}

