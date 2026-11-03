// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.DrFont;
import com.aspose.ms.System.Drawing.FontStyle;


/// <summary>
/// Implements legacy tab stop calculation.
/// </summary>
/// <remarks>
/// List labels SHOULD be updated to get proper result.
/// </remarks>
/*static*/ class LegacyListFormattingConverter
{
	/* Simulation of static class by using private constructor */
	private LegacyListFormattingConverter()
	{}

    /// <summary>
    /// There is an "incompatibility" between legacy and normal list formatting and we must actually have a paragraph
    /// to be able to convert legacy list formatting into normal list formatting.
    /// </summary>
    static TabStop calculateLegacyTab(Paragraph para)
    {
        if (!para.isListItem())
            return null;

        ListLevel level = para.getListLevel();
        if (!level.getLegacy())
            return null;

        // RK The description of list legacy formatting in OOXML and DOC specifications is incorrect.
        //
        // RK Here is the way legacy list formatting is supposed to work:
        //
        // If legacy formatting is present, it overrides the normal formatting.
        // The sole purpose of legacy formatting is to specify the distance between the list label and the paragraph text.
        // When legacy formatting is used, the list label trailing character looks and acts like a tab in MS Word.
        //
        // There are two fields LegacyIndent and LegacySpace that can specify the distance between the list label and the text.
        // LegacyIndent specifies the distance from the left side of the list label to the left side of the first line of text.
        // LegacySpace specifies the distance from the right side of the list label to the left side of the first line of text.
        // If both LegacyIndent and LegacySpace are specified, then both distances are calculated and the biggest is to be used.
        // Normal paragraph indent and first line indent are taken into account as usual.

        // It is easy to calculate the distance if LegacyIndent is specified - we can add this value to the position
        // of the first line of the paragraph and set a tab stop there.
        //
        // It is more difficult to calculate the distance if LegacySpace is specified. We need to know the length
        // of the label text. To calculate the length of the label of text we need to know its formatting and measure the string.
        // This code already calculates the length of the label text and then selects the bigger
        // between LegacyIndent and LegacySpace + LabelStringWidth.

        // IP 14/02/2014: Added IsLegacyTab to TabStop because legacy tab must be special processed. (CR 9339)
        // 1. Legacy tab is taken into account only for list label tab (i.e legacy tab is used only for first row and must be ignored for other lines in the paragraph).
        // 2. All other custom tabs must be ignored for first row if legacy tab is present.
        // IP: I'm not sure that TabStop should be generated for processed legacySpace and legacyIndent. They probably should be the usual paragraph properties.

        double leftOfListLabel = para.getParagraphFormat().getLeftIndent() + para.getParagraphFormat().getFirstLineIndent();
        double leftOfText;
        double calculatedLegacySpace = 0.0;

        if ((level.getLegacyIndent() != 0) || (level.getLegacySpace() != 0))
        {
            if (level.getLegacySpace() != 0)
                calculatedLegacySpace = getLegacySpace(para);

            leftOfText = leftOfListLabel +
                         Math.max(ConvertUtilCore.twipToPoint(level.getLegacyIndent()), calculatedLegacySpace);
        }
        else
        {
            // This seems to be what MS Word is doing in TestListNasty5.
            leftOfText = para.getParagraphFormat().getLeftIndent();
        }

        // When legacy formatting is used, the list label must act like a tab.
        // WORDSNET-7995 If the left position of the text comes before the left position of the label,
        // the tab stop cannot be used (only tabs that come after the list label can be used).
        // In this case the text should start right next to the label as if it had no trailing character at all.
        /*ListTrailingCharacter*/int trailingCharacter = leftOfText <= leftOfListLabel
                ? ListTrailingCharacter.NOTHING
                : ListTrailingCharacter.TAB;

        return TabStop.createLegacyTabStop(leftOfText, trailingCharacter);
    }

    /// <summary>
    /// Calculate legacy space for paragraph.
    /// </summary>
    /// <param name="para">Paragraph with list item.</param>
    /// <returns>Space size in points.</returns>
    private static double getLegacySpace(Paragraph para)
    {
        ListLabel listLabel = para.getListLabel();
        ListLevel level = para.getListLevel();

        // Get DrFont to measure label string.
        DrFont font = para.getDocument().getFontProvider().fetchDrFont(listLabel.getFont().getName(),
            (float)listLabel.getFont().getSize(), FontStyle.REGULAR);
        double labelStringWidth = font.getTextWidthPoints(listLabel.getLabelStringOriginal());

        return ConvertUtilCore.twipToPoint(level.getLegacySpace()) + labelStringWidth;
    }

}

