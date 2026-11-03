// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class DmlTextBodyPropertiesDefaults extends DmlHierarchicalPropertyBag
{
    private DmlTextBodyPropertiesDefaults()
    {
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.ANCHOR, DmlTextAnchoringType.TOP);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.ANCHOR_CENTER, false);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.ARE_FIRST_AND_LAST_PARAGRAPHS_USE_SPACING, false);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.COLUMN_NUMBER, 1);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.COLUMN_ORDER, DmlTextColumnOrder.LEFT_TO_RIGHT);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.FORCE_ANTI_ALIAS, false);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.FROM_WORD_ART, false);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.IS_TEXT_UPRIGHT, false);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.ROTATION, new DmlAngle());
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.HAS_DEFAULT_ROTATION, false);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.SPACE_BETWEEN_COLUMNS, 0);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.TEXT_HORIZONTAL_OVERFLOW, DmlTextHorizontalOverflowType.OVERFLOW);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.TEXT_VERTICAL_OVERFLOW, DmlTextVerticalOverflowType.OVERFLOW);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.TEXT_ORIENTATION, ShapeTextOrientation.HORIZONTAL);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.TEXT_WRAPPING_TYPE, TextBoxWrapMode.SQUARE);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.USE_COMPATIBLE_LINE_SPACING, false);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.LEFT_INSET, LEFT_INSET);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.TOP_INSET, TOP_INSET);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.RIGHT_INSET, RIGHT_INSET);
        setProperty((int)DmlTextBodyPropertiesDefaultsIds.BOTTOM_INSET, BOTTOM_INSET);
    }

    static DmlTextBodyPropertiesDefaults getInstance() { return G_INSTANCE; }

    static final int LEFT_INSET = 91440;
    static final int TOP_INSET = 45720;
    static final int RIGHT_INSET = 91440;
    static final int BOTTOM_INSET = 45720;
  
    private static /*final*/ DmlTextBodyPropertiesDefaults G_INSTANCE = new DmlTextBodyPropertiesDefaults();
}

