// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/01/2019 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;


/// <summary>
/// Implements OLE HTML radio button control.
/// </summary>
class HtmlOptionOleControl extends HtmlOleControl
{
    /// <summary>
    /// Creates instance of the <see cref="HtmlOptionOleControl"/> class with a specified name.
    /// </summary>
    HtmlOptionOleControl(String name)
    {
    	super(name);
	
    }

    /// <summary>
    /// Reads control from a storage.
    /// </summary>
    /*override*/ void read(MemoryStorage storage) throws Exception
    {
        super.read(storage);

        HtmlDocument htmlDocument = HtmlDocument.load(getHtml());
        HtmlElementNode input = htmlDocument.getRoot().findSingleElementByName("input");
        for (HtmlAttribute attribute : input.getAttributes())
        {
            if ("checked".equals(attribute.getName()))
            {
                setChecked(true);
                break;
            }
        }
    }

    boolean getChecked() { return mChecked; }; private void setChecked(boolean value) { mChecked = value; };

    private boolean mChecked;
}

