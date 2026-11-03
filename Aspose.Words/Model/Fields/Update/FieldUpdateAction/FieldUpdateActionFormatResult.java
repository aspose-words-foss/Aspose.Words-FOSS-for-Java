// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/06/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.words.ref.Ref;


/// <summary>
/// This class is used to format field result only. It is used in fields whose result is formed directly in
/// the UpdateCore method.
/// </summary>
class FieldUpdateActionFormatResult extends FieldUpdateAction
{
    FieldUpdateActionFormatResult(Field field) throws Exception
    {
    	super(field);
	
        mFormatApplier = getField().getFormat().getFieldResultFormatProvider().getFormatApplier();
    }

    /*override*/ void perform() throws Exception
    {
        // First try to format result as a number, date etc.
        // If unsuccessful, only apply general formatting.
        FieldFormattingResult formattingResult = null;
        StringConstant result = new StringConstant(getField().getResult());

        Ref<FieldFormattingResult> referenceToFormattingResult = new Ref<FieldFormattingResult>(formattingResult);
        boolean outRefCondition0 = getField().getFormat().formatResult(result, /*out*/ referenceToFormattingResult);
        formattingResult = referenceToFormattingResult.get();
        if (outRefCondition0)
        {
            TextResultApplier resultApplier = new TextResultApplier(getField(), formattingResult);
            resultApplier.applyResult();
        }
        else
        {
            // Apply result format.
            if (mFormatApplier != null)
                mFormatApplier.applyFormat(getField().getFieldResultRange());
        }

        getField().getUpdateContext().joinOldResultNodes();
    }

    private /*final*/ IFieldResultFormatApplier mFormatApplier;
}

