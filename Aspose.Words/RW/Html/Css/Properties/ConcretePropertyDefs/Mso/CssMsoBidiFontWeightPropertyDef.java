// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/04/2025 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class CssMsoBidiFontWeightPropertyDef extends CssIndividualSimplePropertyDef
{
    CssMsoBidiFontWeightPropertyDef()
    {
    	super(
            "mso-bidi-font-weight",
            true,
            CssValue.Normal,
            // normal | bold | bolder | lighter | 100 | 200 | 300 | 400 | 500 | 600 | 700 | 800 | 900
            CssValueFilter.values(
                CssValue.Normal,
                CssValue.Bold,
                CssValue.Bolder,
                CssValue.Lighter,
                new CssNumberValue(100.0),
                new CssNumberValue(200.0),
                new CssNumberValue(300.0),
                new CssNumberValue(400.0),
                new CssNumberValue(500.0),
                new CssNumberValue(600.0),
                new CssNumberValue(700.0),
                new CssNumberValue(800.0),
                new CssNumberValue(900.0)));
	
        // Empty constructor.
    }
}

