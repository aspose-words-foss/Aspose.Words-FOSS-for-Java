// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// The Frame control is a rectangular box with an optional label.
/// </summary>
class FrameControl extends FormControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FrameControl(String name)
    {
    	super(name);
	
        setSize(mDefaultSize);
    }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.FRAME; }

    /// <summary>
    /// Gets a boolean value indicating either the Forms2 OleControl is composite.
    /// </summary>
    /*override*/ boolean isComposite() { return true; }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*override*/ String getClsidVirtual() { return FRAME_CONTROL_CLSID; }

    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(216.0, 144.0);
}

