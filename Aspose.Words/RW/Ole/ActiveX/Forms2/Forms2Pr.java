// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Forms2 attributes collection.
/// </summary>
class Forms2Pr extends AttrCollection
{
    protected /*override*/ AttrCollection getDefaults()
    {
        return G_DEFAULTS;
    }

    private static /*final*/ Forms2Pr G_DEFAULTS;

    static/* Forms2Pr()*/
    {
        G_DEFAULTS = new Forms2Pr();

        G_DEFAULTS.add(Forms2Attr.BOOLEAN_PROPERTIES, FormFlags.ENABLED);
        G_DEFAULTS.add(Forms2Attr.CLSID_CACHE_INDEX, 0x7FFF);
        G_DEFAULTS.add(Forms2Attr.CYCLE, Cycle.ALL_FORMS);
        G_DEFAULTS.add(Forms2Attr.OBJECT_STREAM_SIZE, (/*uint*/int)0);
        G_DEFAULTS.add(Forms2Attr.MAX_LENGTH, 0);

        G_DEFAULTS.add(Forms2Attr.ID, 0);
        G_DEFAULTS.add(Forms2Attr.HELP_CONTEXT_ID, 0);
        G_DEFAULTS.add(Forms2Attr.TAB_INDEX, -1);
        G_DEFAULTS.add(Forms2Attr.GROUP_ID, (/*uint*/int)0x00);
        G_DEFAULTS.add(Forms2Attr.NEXT_AVAILABLE_ID, (/*uint*/int)0x00);
        G_DEFAULTS.add(Forms2Attr.MULTI_ROW, false);
        G_DEFAULTS.add(Forms2Attr.BIT_FLAGS_SITE,
            SiteFlag.TAB_STOP |
            SiteFlag.VISIBLE |
            SiteFlag.STREAMED |
            SiteFlag.AUTO_SIZE);

        G_DEFAULTS.add(Forms2Attr.LIST_INDEX, -1);

        G_DEFAULTS.add(Forms2Attr.TAB_ORIENATION, TabOrientation.TOP);
        G_DEFAULTS.add(Forms2Attr.TAB_STYLE, TabStyle.TABS);
        G_DEFAULTS.add(Forms2Attr.TAB_FIXED_WIDTH, (/*uint*/int)0);
        G_DEFAULTS.add(Forms2Attr.TABS_ALLOCATED, (/*uint*/int)0);
        G_DEFAULTS.add(Forms2Attr.TAB_DATA, (/*uint*/int)0);

        G_DEFAULTS.add(Forms2Attr.PAGE_COUNT, -1);
        G_DEFAULTS.add(Forms2Attr.FLAGS, true);
        G_DEFAULTS.add(Forms2Attr.TRANSITION_EFFECT, TransitionEffect.NONE);
        G_DEFAULTS.add(Forms2Attr.TRANSITION_PERIOD, (/*uint*/int)0);
        G_DEFAULTS.add(Forms2Attr.GRID_X, 0);
        G_DEFAULTS.add(Forms2Attr.GRID_Y, 0);
        G_DEFAULTS.add(Forms2Attr.CLICK_CONTROL_MODE, ClickControlMode.INSERTION_POINT);
        G_DEFAULTS.add(Forms2Attr.DBL_CLICK_CONTROL_MODE, DblClickControlMode.SELECT_TEXT);
        G_DEFAULTS.add(Forms2Attr.BIT_FLAGS_DX,
            DesignExtenderFlag.INHERIT_DESIGN |
            DesignExtenderFlag.INHERIT_SHOW_TOOLBOX |
            DesignExtenderFlag.INHERIT_SHOW_GRID |
            DesignExtenderFlag.INHERIT_SNAP_TO_GRID |
            DesignExtenderFlag.INHERIT_GRID_X |
            DesignExtenderFlag.INHERIT_GRID_Y |
            DesignExtenderFlag.INHERIT_CLICK_CONTROL |
            DesignExtenderFlag.INHERIT_DBL_CLICK_CONTROL |
            DesignExtenderFlag.INHERIT_SHOW_INVISIBLE |
            DesignExtenderFlag.INHERIT_SHOW_TOOLTIPS |
            DesignExtenderFlag.INHERIT_LAYOUT_IMMEDIATE);

        G_DEFAULTS.add(Forms2Attr.MIN, 0);
        G_DEFAULTS.add(Forms2Attr.MAX, 100);
        G_DEFAULTS.add(Forms2Attr.POSITION, 0);

        G_DEFAULTS.add(Forms2Attr.PREV_ENABLED, 0x01);
        G_DEFAULTS.add(Forms2Attr.NEXT_ENABLED, 0x01);
        G_DEFAULTS.add(Forms2Attr.SMALL_CHANGE, 0x01);
        G_DEFAULTS.add(Forms2Attr.LARGE_CHANGE, 0x01);
        G_DEFAULTS.add(Forms2Attr.PROPORTIONAL_THUMB, ProportionalThumb.PROPORTIONAL);

        G_DEFAULTS.add(Forms2Attr.ORIENTATION, FormOrientation.AUTO);
        G_DEFAULTS.add(Forms2Attr.MATCH_ENTRY, MatchEntry.NONE);
        G_DEFAULTS.add(Forms2Attr.SHOW_DROP_BUTTON_WHEN, ShowDropButtonWhen.NEVER);
        G_DEFAULTS.add(Forms2Attr.DROP_BUTTON_STYLE, DropButtonStyle.ARROW);
        G_DEFAULTS.add(Forms2Attr.SCROLL_BARS, ScrollBars.NONE);

        G_DEFAULTS.add(Forms2Attr.LIST_WIDTH, (/*uint*/int)0x00);
        G_DEFAULTS.add(Forms2Attr.LIST_STYLE, ListStyle.PLAIN);
        G_DEFAULTS.add(Forms2Attr.MULTI_SELECT, MultiSelect.SINGLE);
        G_DEFAULTS.add(Forms2Attr.BOUND_COLUMN, (/*uint*/int)0x01);
        G_DEFAULTS.add(Forms2Attr.TEXT_COLUMN, (/*uint*/int)0xFFFF);
        G_DEFAULTS.add(Forms2Attr.COLUMN_COUNT, (/*uint*/int)0x01);
        G_DEFAULTS.add(Forms2Attr.LIST_ROWS, (/*uint*/int)0x08);
        G_DEFAULTS.add(Forms2Attr.COLUMN_INFO, (/*uint*/int)0x00);

        G_DEFAULTS.add(Forms2Attr.PASSWORD_CHAR, (/*uint*/int)0x00);

        G_DEFAULTS.add(Forms2Attr.DELAY, 0x00000032);

        G_DEFAULTS.add(Forms2Attr.MOUSE_POINTER, MousePointer.DEFAULT);
        G_DEFAULTS.add(Forms2Attr.MOUSE_ICON, null);

        G_DEFAULTS.add(Forms2Attr.CAPTION, "");
        G_DEFAULTS.add(Forms2Attr.VALUE, "");
        G_DEFAULTS.add(Forms2Attr.GROUP_NAME, "");
        G_DEFAULTS.add(Forms2Attr.NAME, "");
        G_DEFAULTS.add(Forms2Attr.TAG, "");
        G_DEFAULTS.add(Forms2Attr.TOOLTIPS, "");
        G_DEFAULTS.add(Forms2Attr.RUNTIME_LIC_KEY, "");
        G_DEFAULTS.add(Forms2Attr.CONTROL_SOURCE, "");
        G_DEFAULTS.add(Forms2Attr.ROW_SOURCE, "");

        G_DEFAULTS.add(Forms2Attr.FONT, null);

        G_DEFAULTS.add(Forms2Attr.PICTURE, null);
        G_DEFAULTS.add(Forms2Attr.PICTURE_SIZE_MODE, PictureSizeMode.CLIP);
        G_DEFAULTS.add(Forms2Attr.PICTURE_ALIGNMENT, PictureAlignment.CENTER);
        G_DEFAULTS.add(Forms2Attr.PICTURE_POSITION, PicturePosition.ABOVE_CENTER);
        G_DEFAULTS.add(Forms2Attr.PICTURE_TILING, false);
        G_DEFAULTS.add(Forms2Attr.AUTO_SIZE, false);

        G_DEFAULTS.add(Forms2Attr.ZOOM, 100);
        G_DEFAULTS.add(Forms2Attr.SHAPE_COOKIE, 0x00);
        G_DEFAULTS.add(Forms2Attr.DRAW_BUFFER, (/*uint*/int)0x00);

        G_DEFAULTS.add(Forms2Attr.DISPLAY_STYLE, 0x01);

        G_DEFAULTS.add(Forms2Attr.LOGICAL_SIZE, new OleSize(4000, 3000));
        G_DEFAULTS.add(Forms2Attr.SCROLL_POSITION, new OlePosition(0, 0));
        G_DEFAULTS.add(Forms2Attr.SITE_POSITION, new OlePosition(0, 0));

        G_DEFAULTS.add(Forms2Attr.FONT_NAME, "MS Sans Serif");
        G_DEFAULTS.add(Forms2Attr.FONT_HEIGHT, (/*uint*/int)160);
        G_DEFAULTS.add(Forms2Attr.FONT_WEIGHT, 400);
        G_DEFAULTS.add(Forms2Attr.FONT_CHAR_SET, 0x01);
        G_DEFAULTS.add(Forms2Attr.FONT_EFFECTS, FontEffects.NONE);
        G_DEFAULTS.add(Forms2Attr.FONT_PITCH_AND_FAMILY, 0x00);
        G_DEFAULTS.add(Forms2Attr.PARAGRAPH_ALIGN, ParagraphAlign.LEFT);

        G_DEFAULTS.add(Forms2Attr.TAKE_FOCUS_ON_CLICK, true);

        G_DEFAULTS.add(Forms2Attr.ACCELERATOR, (char)0);

        // The following defaults are different for various controls, so they can be overridden in concrete control.
        G_DEFAULTS.add(Forms2Attr.BACKGROUND_COLOR, OleColor.fromRaw((int) 0x8000000FL));
        G_DEFAULTS.add(Forms2Attr.FOREGROUND_COLOR, OleColor.fromRaw((int) 0x80000012L));
        G_DEFAULTS.add(Forms2Attr.VARIOUS_PROPERTY_BITS,
            VariousPropertiesBits.RESERVED_1 |
            VariousPropertiesBits.ENABLED |
            VariousPropertiesBits.BACK_STYLE |
            VariousPropertiesBits.RESERVED_2);
        G_DEFAULTS.add(Forms2Attr.BORDER_COLOR, OleColor.fromRaw((int) 0x80000006L));
        G_DEFAULTS.add(Forms2Attr.BORDER_STYLE, BorderStyle.NONE);
        G_DEFAULTS.add(Forms2Attr.SPECIAL_EFFECT, SpecialEffect.FLAT);
        G_DEFAULTS.add(Forms2Attr.SIZE, new OleSize(0, 0));
    }
}

