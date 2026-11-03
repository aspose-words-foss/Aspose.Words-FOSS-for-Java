// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/12/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Used by fields that update themselves in UpdateCore.
/// </summary>
class FieldUpdateActionDoNothing extends FieldUpdateAction
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldUpdateActionDoNothing(Field field)
    {
    	super(field);
	
    }

    /*override*/ void perform()
    {
        // Do nothing.
    }
}

