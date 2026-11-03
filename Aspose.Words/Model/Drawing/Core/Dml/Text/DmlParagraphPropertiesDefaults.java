// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class DmlParagraphPropertiesDefaults extends DmlHierarchicalPropertyBag
{
    private DmlParagraphPropertiesDefaults()
    {
        // WORDSNET-8939 Left alignment should be default for paragraphs in drawing parts.
        // Looks like in charts it is ParagraphAlignment.Center by default. In DmlChartTxPr, default
        // value is changed on creation of DML paragraphs.
        setProperty((int)DmlParagraphPropertiesIds.LEVEL, 0);
        setProperty((int)DmlParagraphPropertiesIds.ALIGNMENT, ParagraphAlignment.LEFT);
        setProperty((int)DmlParagraphPropertiesIds.DEFAULT_TAB_SIZE, 914400);
            // Office uses a default value of 914400 EMUs.
        setProperty((int)DmlParagraphPropertiesIds.IS_EAST_ASIAN_LINE_BREAK_ALLOWED, true);
        setProperty((int)DmlParagraphPropertiesIds.FONT_ALIGNMENT, DmlFontAlignment.BASELINE);
        setProperty((int)DmlParagraphPropertiesIds.IS_HANGING_PUNCTUATION_ALLOWED, false);
            // Office uses a default value of 1, or true, for the hangingPunct attribute.
        setProperty((int)DmlParagraphPropertiesIds.TEXT_IDENTATION, 0);
            // Office uses a default value of 0 for the indent attribute.
        setProperty((int)DmlParagraphPropertiesIds.IS_LATIN_LINE_BREAK_ALLOWED, false);
            //  Office uses a default value of 0, or false, for the latinLnBrk attribute.
        setProperty((int)DmlParagraphPropertiesIds.LEFT_MARGIN, 0);
            // Office uses a default value of 0 for the marL attribute.
        setProperty((int)DmlParagraphPropertiesIds.RIGHT_MARGIN, 0);
        setProperty((int)DmlParagraphPropertiesIds.RIGHT_TO_LEFT_FLOW_DIRECTION, false);
        setProperty((int)DmlParagraphPropertiesIds.LINE_SPACING, new DmlPercentageTextSpacing()); //  Default is 100% 
        setProperty((int)DmlParagraphPropertiesIds.SPACE_AFTER, new DmlPointsTextSpacing()); // No after text spacing
        setProperty((int)DmlParagraphPropertiesIds.SPACE_BEFORE, new DmlPointsTextSpacing()); // No before text spacing

        setProperty((int)DmlParagraphPropertiesIds.EXTENSIONS, null);
    }

    static DmlParagraphPropertiesDefaults getInstance() { return M_INSTANCE; }

    private static /*final*/ DmlParagraphPropertiesDefaults M_INSTANCE = new DmlParagraphPropertiesDefaults();
}

