// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/11/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Forms2 attributes collection for <see cref="FormControl"/>.
/// </summary>
/// <remarks> This Forms 2.0 control has some default values different from the other Forms 2.0 controls.</remarks>
class FormControlPr extends Forms2Pr
{
    protected /*override*/ AttrCollection getDefaults()
    {
        return G_DEFAULTS;
    }

    private static /*final*/ FormControlPr G_DEFAULTS;

    static/* FormControlPr()*/
    {
        G_DEFAULTS = new FormControlPr();
        Forms2Pr baseDefaults = new Forms2Pr();
        baseDefaults.expandTo(G_DEFAULTS, true);

        G_DEFAULTS.set(Forms2Attr.SIZE, new OleSize(4000, 3000));
    }
}

