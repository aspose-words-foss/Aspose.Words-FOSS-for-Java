// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/11/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Forms2 attributes collection for <see cref="MorphDataControl"/>.
/// </summary>
/// <remarks> This Forms 2.0 control has some default values different from the other Forms 2.0 controls.</remarks>
class MorphDataControlPr extends Forms2Pr
{
    protected /*override*/ AttrCollection getDefaults()
    {
        return G_DEFAULTS;
    }

    private static /*final*/ MorphDataControlPr G_DEFAULTS;

    static/* MorphDataControlPr()*/
    {
        G_DEFAULTS = new MorphDataControlPr();
        Forms2Pr baseDefaults = new Forms2Pr();
        baseDefaults.expandTo(G_DEFAULTS, true);

        G_DEFAULTS.set(Forms2Attr.BACKGROUND_COLOR, OleColor.fromRaw((int) 0x80000005L));
        G_DEFAULTS.set(Forms2Attr.FOREGROUND_COLOR, OleColor.fromRaw((int) 0x80000008L));
        G_DEFAULTS.set(Forms2Attr.SPECIAL_EFFECT, SpecialEffect.SUNKEN);
        G_DEFAULTS.set(Forms2Attr.VARIOUS_PROPERTY_BITS, VariousPropertiesBits.RESERVED_1 |
            VariousPropertiesBits.ENABLED |
            VariousPropertiesBits.BACK_STYLE |
            VariousPropertiesBits.RESERVED_2 |
            VariousPropertiesBits.INTEGRAL_HEIGHT |
            VariousPropertiesBits.WORD_WRAP |
            VariousPropertiesBits.SELECTION_MARGIN |
            VariousPropertiesBits.AUTO_WORD_SELECT |
            VariousPropertiesBits.HIDE_SELECTION);
    }
}

