// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2023 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DoublePal;
import com.aspose.MathUtil;
import com.aspose.ConvertUtilCore;

/// <summary>
/// Applies CSS formatting to document sections.
/// </summary>
abstract class SectionFormatter
{
    protected SectionFormatter(
        Document document,
        CssStyleTracker cssStyleTracker)
    {
        mDocument = document;
        mCssStyleTracker = cssStyleTracker;
    }

    abstract void updateDefaultSectionProperties(Section section);

    void format(Section section)
    {
        if (getCssStyleTracker().isEmpty())
        {
            return;
        }

        if (getCssStyleTracker().getElementDeclarations().get("page") != null)
        {
            CssDeclarationCollection pageDeclarations = getCssStyleTracker().getAllPageDeclarations();
            applyPageSetupFormatting(section, pageDeclarations);
            // Note that we must apply page size first, because page margin values may be relative.
            applyPageMargins(section, pageDeclarations);
        }

        applySpecialFormatting(section);
    }

    protected abstract void applyPageSetupFormatting(
        Section section,
        CssDeclarationCollection pageDeclarations);

    protected abstract void applySpecialFormatting(Section section);

    private static void applyPageMargins(
        Section section,
        CssDeclarationCollection pageDeclarations)
    {
        SectPr sectPr = section.getSectPr();
        int pageWidth = sectPr.getPageWidth();
        int pageHeight = sectPr.getPageHeight();

        int margin = getPageMargin(pageDeclarations, "margin-top", pageHeight);
        if (margin >= 0)
            sectPr.setTopMargin(margin);

        margin = getPageMargin(pageDeclarations, "margin-right", pageWidth);
        if (margin >= 0)
            sectPr.setRightMargin(margin);

        margin = getPageMargin(pageDeclarations, "margin-bottom", pageHeight);
        if (margin >= 0)
            sectPr.setBottomMargin(margin);

        margin = getPageMargin(pageDeclarations, "margin-left", pageWidth);
        if (margin >= 0)
            sectPr.setLeftMargin(margin);
    }

    private static int getPageMargin(
        CssDeclarationCollection declarations,
        String propertyName,
        int pageSize)
    {
        int margin = Integer.MIN_VALUE;

        CssDeclaration declaration = declarations.get(propertyName);
        if ((declaration == null) || (declaration.getValue().getCount() > 1))
        {
            return margin;
        }

        CssValue cssValue = declaration.getValue().getFirstValue();

        switch (cssValue.getValueType())
        {
            case CssValueType.PERCENTAGE:
            {
                double ratio = ((CssPercentageValue)cssValue).getDoubleValue() / 100.0;
                margin = DoublePal.roundToIntUp(ratio * pageSize);
                break;
            }
            default:
            {
                double length = CssUtil.lengthToPoint(cssValue);
                if (!MathUtil.areEqual(length, -Double.MAX_VALUE))
                {
                    margin = ConvertUtilCore.pointToTwip(length);
                }
                break;
            }
        }

        return margin;
    }

    protected Document getDocument() { return mDocument; };

    private Document mDocument;

    protected CssStyleTracker getCssStyleTracker() { return mCssStyleTracker; };

    private CssStyleTracker mCssStyleTracker;
}

