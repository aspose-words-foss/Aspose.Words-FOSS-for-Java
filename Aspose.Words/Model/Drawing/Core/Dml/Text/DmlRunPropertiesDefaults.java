// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Language;


class DmlRunPropertiesDefaults extends DmlHierarchicalPropertyBag
{
    private DmlRunPropertiesDefaults()
    {
        setProperty((int)DmlRunPropertiesIds.ALTERNATIVE_LANGUAGE, Language.LANGUAGE_NOT_SET);
        setProperty((int)DmlRunPropertiesIds.BOLD, false);
        setProperty((int)DmlRunPropertiesIds.BASELINE, 0.0);
        setProperty((int)DmlRunPropertiesIds.BOOKMARK_LINK_TARGET, "");
        setProperty((int)DmlRunPropertiesIds.CAPITALIZATION, DmlCapitalization.NONE);
        setProperty((int)DmlRunPropertiesIds.IS_DIRTY, false);
        setProperty((int)DmlRunPropertiesIds.HAS_SPELLING_ERROR, false);
        setProperty((int)DmlRunPropertiesIds.ITALICS, false);
        setProperty((int)DmlRunPropertiesIds.KERNING, new DmlTextPoints());
        setProperty((int)DmlRunPropertiesIds.KUMIMOJI, false);
        setProperty((int)DmlRunPropertiesIds.LANGUAGE, Language.LANGUAGE_NOT_SET);
        setProperty((int)DmlRunPropertiesIds.NO_PROOFING, false);
        setProperty((int)DmlRunPropertiesIds.NORMALIZE_HEIGHTS, false);

        // In Office, the smtClean attribute of the rPr, defRpr and endParaRPr elements has a default value of true.
        setProperty((int)DmlRunPropertiesIds.SMART_TAGS_CLEAN, true);
        setProperty((int)DmlRunPropertiesIds.SMART_TAG_ID, Integer.MIN_VALUE);
        setProperty((int)DmlRunPropertiesIds.SPACING, new DmlTextPoints());
        setProperty((int)DmlRunPropertiesIds.STRIKETHROUGH, DmlTextStrike.NO);

        // Office uses a default value of 1800 for this attribute.
        setProperty((int)DmlRunPropertiesIds.FONT_SIZE, new DmlTextPoints(1800));
        setProperty((int)DmlRunPropertiesIds.UNDERLINE, Underline.NONE);
        setProperty((int)DmlRunPropertiesIds.FILL, null);
        setProperty((int)DmlRunPropertiesIds.OUTLINE, new DmlOutline());
        setProperty((int)DmlRunPropertiesIds.HIGHLIGHT_COLOR, null);
        setProperty((int)DmlRunPropertiesIds.LATIN_FONT, null);
        setProperty((int)DmlRunPropertiesIds.EAST_ASIAN_FONT, null);
        setProperty((int)DmlRunPropertiesIds.SYMBOL_FONT, null);
        setProperty((int)DmlRunPropertiesIds.COMPLEX_SCRIPT_FONT, null);
        setProperty((int)DmlRunPropertiesIds.HLINK_CLICK, null);
        setProperty((int)DmlRunPropertiesIds.HLINK_HOVER, null);

        setProperty((int)DmlRunPropertiesIds.UNDERLINE_FILL, null);
        setProperty((int)DmlRunPropertiesIds.UNDERLINE_FILL_TX, false);
        setProperty((int)DmlRunPropertiesIds.UNDERLINE_STROKE, new DmlOutline());
        setProperty((int)DmlRunPropertiesIds.UNDERLINE_STROKE_TX, false);
        setProperty((int)DmlRunPropertiesIds.RIGHT_TO_LEFT_FLOW_DIRECTION, false);

        setProperty((int)DmlRunPropertiesIds.EXTENSIONS, null);
    }

    static DmlRunPropertiesDefaults getInstance() { return G_INSTANCE; }

    private static /*final*/ DmlRunPropertiesDefaults G_INSTANCE = new DmlRunPropertiesDefaults();
}

