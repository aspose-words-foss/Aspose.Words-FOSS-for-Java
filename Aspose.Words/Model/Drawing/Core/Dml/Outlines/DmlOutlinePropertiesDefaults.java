// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/01/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class DmlOutlinePropertiesDefaults extends DmlHierarchicalPropertyBag
{
    private DmlOutlinePropertiesDefaults()
    {
        setProperty((int)DmlOutlinePropertiesIds.LINE_ENDING_CAP_TYPE, EndCap.FLAT);
        setProperty((int)DmlOutlinePropertiesIds.LINE_JOIN_STYLE, JoinStyle.ROUND);
        setProperty((int)DmlOutlinePropertiesIds.WIDTH_IN_EMUS, 0.0);
        setProperty((int)DmlOutlinePropertiesIds.COMPOUND_LINE_TYPE, ShapeLineStyle.SINGLE);
        setProperty((int)DmlOutlinePropertiesIds.STROKE_ALIGNMENT, false);
        setProperty((int)DmlOutlinePropertiesIds.DASH, new DmlPresetDash());
        setProperty((int)DmlOutlinePropertiesIds.LINE_MITER_LIMIT, 0.0);
        setProperty((int)DmlOutlinePropertiesIds.FILL, new DmlNoFill());
        setProperty((int)DmlOutlinePropertiesIds.HEAD_LINE_END_STYLE, new DmlHeadLineEndStyle());
        setProperty((int)DmlOutlinePropertiesIds.TAIL_LINE_END_STYLE, new DmlTailLineEndStyle());
    }

    static DmlOutlinePropertiesDefaults getInstance() { return G_INSTANCE; }

    private static /*final*/ DmlOutlinePropertiesDefaults G_INSTANCE = new DmlOutlinePropertiesDefaults();
}

