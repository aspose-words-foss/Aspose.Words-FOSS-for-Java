// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/11/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Forms2 attributes collection for <see cref="LabelControl"/>.
/// </summary>
/// <remarks> This Forms 2.0 control has some default values different from the other Forms 2.0 controls.</remarks>
class LabelControlPr extends Forms2Pr
{
    protected /*override*/ AttrCollection getDefaults()
    {
        return G_DEFAULTS;
    }

    private static /*final*/ LabelControlPr G_DEFAULTS;

    static/* LabelControlPr()*/
    {
        G_DEFAULTS = new LabelControlPr();
        Forms2Pr baseDefaults = new Forms2Pr();
        baseDefaults.expandTo(G_DEFAULTS, true);

        G_DEFAULTS.set(Forms2Attr.VARIOUS_PROPERTY_BITS, VariousPropertiesBits.RESERVED_1 |
            VariousPropertiesBits.ENABLED |
            VariousPropertiesBits.BACK_STYLE |
            VariousPropertiesBits.RESERVED_2 |
            VariousPropertiesBits.WORD_WRAP);
    }
}

