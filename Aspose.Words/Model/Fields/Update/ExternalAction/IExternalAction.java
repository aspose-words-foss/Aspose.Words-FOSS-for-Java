// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/01/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.lang.Class;


/// <summary>
/// When implemented, represents an external action to perform, such as update of layout or list labels.
/// May be requested by a field that needs it in certain stage of its update.
/// </summary>
interface IExternalAction
{
    /// <summary>
    /// Performs the action.
    /// </summary>
    public void perform() throws Exception;
    /// <summary>
    /// Gets an object uniquely identifying the action. Actions with the same identifiers are performed only once
    /// per field update stage.
    /// </summary>
    public Class getId();
}

