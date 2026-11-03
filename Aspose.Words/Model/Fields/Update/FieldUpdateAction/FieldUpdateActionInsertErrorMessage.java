// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/11/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;

/// <summary>
/// Inserts an error message in Word style.
/// </summary>
class FieldUpdateActionInsertErrorMessage extends FieldUpdateAction
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldUpdateActionInsertErrorMessage(Field field, String message)
    {
    	super(field);
	
        mMessage = message;
    }

    /*override*/ void perform() throws Exception
    {
        IDisposable iDisposableResource = getField().getUpdateContext().removeOldResultSafe();
        try /*JAVA: was using*/
        {
            DocumentBuilder builder = new DocumentBuilder(getDocument());
            builder.moveTo(getField().getEnd());
            builder.setBold(true);
            builder.write(mMessage);
        }
        finally { if (iDisposableResource != null) iDisposableResource.dispose(); }
    }

    private /*final*/ String mMessage;
}

