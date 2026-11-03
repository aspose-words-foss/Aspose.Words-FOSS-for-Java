// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2023 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.MathUtil;
import com.aspose.ConvertUtilCore;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;

/// <summary>
/// Applies CSS formatting to document sections.
/// Uses same formatting rules as MS Word.
/// </summary>
class SectionFormatterWordRules extends SectionFormatter
{
    SectionFormatterWordRules(
        Document document,
        CssStyleTracker cssStyleTracker)
    {
    	super(document, cssStyleTracker);
	
        // Empty constructor.
    }

    /*override*/ void updateDefaultSectionProperties(Section section)
    {
        SectPr sectPr = section.getSectPr();
        clearSectionAttributes(sectPr);

        sectPr.setDefaultPageSize();

        if (section.isFirstSection())
        {
            sectPr.set(SectAttr.LINE_PITCH, 360);
        }

        int[] keys = new int[]
        {
            SectAttr.FOOTER_DISTANCE,
            SectAttr.HEADER_DISTANCE,
            SectAttr.GUTTER,
            SectAttr.COLUMNS_SPACING,
            SectAttr.TOP_MARGIN,
            SectAttr.RIGHT_MARGIN,
            SectAttr.BOTTOM_MARGIN,
            SectAttr.LEFT_MARGIN
        };
        for (int key : keys)
        {
            sectPr.set(key, SectPr.fetchDefaultAttr(key));
        }
    }

    protected /*override*/ void applyPageSetupFormatting(
        Section section,
        CssDeclarationCollection pageDeclarations)
    {
        SectPr sectPr = section.getSectPr();

        // The title page value is case-insensitive.
        if (pageDeclarations.containsIdentifier("mso-title-page", "yes"))
        {
            sectPr.setDifferentFirstPageHeaderFooter(true);
        }

        // Max margin value allowed by MS Word. Note that although MS Word's document's model can store greater margin
        // values (up to 22 inches), when loading from MHTML, the max value is 15 inches.
        final double MAX_MARGIN_IN_POINTS = 1080.0;

        double headerMargin = pageDeclarations.getLength("mso-header-margin");
        if (!MathUtil.isMinValue(headerMargin))
        {
            sectPr.setHeaderDistance(ConvertUtilCore.pointToTwip(MathUtil.fitToRange(headerMargin, 0.0, MAX_MARGIN_IN_POINTS)));
        }
        double footerMargin = pageDeclarations.getLength("mso-footer-margin");
        if (!MathUtil.isMinValue(footerMargin))
        {
            sectPr.setFooterDistance(ConvertUtilCore.pointToTwip(MathUtil.fitToRange(footerMargin, 0.0, MAX_MARGIN_IN_POINTS)));
        }

        double gutterMargin = pageDeclarations.getLength("mso-gutter-margin");
        if (!MathUtil.isMinValue(gutterMargin))
        {
            sectPr.setGutter(ConvertUtilCore.pointToTwip(MathUtil.fitToRange(gutterMargin, 0.0, MAX_MARGIN_IN_POINTS)));
        }

        section.getPageSetup().setOddAndEvenPagesHeaderFooter(pageDeclarations.containsIdentifier("mso-facing-pages", "yes"));

        CssDeclaration sizeDeclaration = pageDeclarations.get("size");
        if (sizeDeclaration != null)
        {
            if (sizeDeclaration.getValue().getCount() == 1)
            {
                CssValue value = sizeDeclaration.getValue().getFirstValue();

                int size = parsePageSize(value);
                if (size > 0)
                {
                    sectPr.setPageHeight(size);
                    sectPr.setPageWidth(size);
                }
                // Note that this comparison is case-insensitive.
                else if (value.equals(new CssIdentifierValue("landscape")))
                {
                    sectPr.setOrientation(Orientation.LANDSCAPE);

                    // Swap page width and height.
                    int width = sectPr.getPageWidth();
                    sectPr.setPageWidth(sectPr.getPageHeight());
                    sectPr.setPageHeight(width);
                }
            }
            else if (sizeDeclaration.getValue().getCount() == 2)
            {
                int widthValue = parsePageSize(sizeDeclaration.getValue().get(0));
                int heightValue = parsePageSize(sizeDeclaration.getValue().get(1));
                if ((widthValue > 0) && (heightValue > 0))
                {
                    sectPr.setPageWidth(widthValue);
                    sectPr.setPageHeight(heightValue);
                }
            }
        }

        // Note that this comparison is case-insensitive.
        if (pageDeclarations.containsIdentifier("mso-page-orientation", "landscape"))
        {
            // Note that this declaration affects orientation only and doesn't swap page width and height.
            sectPr.setOrientation(Orientation.LANDSCAPE);
        }

        applyTextColumnsFormatting(section.getPageSetup().getTextColumns(), pageDeclarations);
    }

    private static void applyTextColumnsFormatting(
        TextColumnCollection textColumns,
        CssDeclarationCollection pageDeclarations)
    {
        Debug.assertTrue(pageDeclarations != null);

        CssDeclaration msoColumnsDeclaration = pageDeclarations.get("mso-columns");
        if (msoColumnsDeclaration == null)
        {
            return;
        }

        int columnCount = getColumnCount(msoColumnsDeclaration);
        if (columnCount == Integer.MIN_VALUE)
        {
            return;
        }

        textColumns.setCount(columnCount);

        String evenlySpacedIdentifier = getColumnEvenlySpacedIdentifier(msoColumnsDeclaration);
        if (!StringUtil.hasChars(evenlySpacedIdentifier))
        {
            return;
        }

        evenlySpacedIdentifier = msString.toLowerInvariant(evenlySpacedIdentifier);
        if ((!"even".equals(evenlySpacedIdentifier)) &&
            (!"not-even".equals(evenlySpacedIdentifier)))
        {
            return;
        }

        switch (gStringSwitchMap.of(evenlySpacedIdentifier))
        {
            case /*"even"*/0:
                // MS Word doesn't write 'w:equalWidth="1"' attribute for 'w:cols' element, but we need to set this
                // property here, so AW model correctly handle this case.
                textColumns.setEvenlySpaced(true);
                break;
            case /*"not-even"*/1:
                textColumns.setEvenlySpaced(false);
                break;
            default:
                Debug.fail("Invalid value.");
                return;
        }

        if (textColumns.getEvenlySpaced())
        {
            double spacing = getColumnSpacing(msoColumnsDeclaration, 2);
            if (MathUtil.isMinValue(spacing))
            {
                return;
            }

            textColumns.setSpacing(spacing);
        }
        else
        {
            int valueIndex = 2;
            for (int i = 0; i < textColumns.getCount(); i++)
            {
                TextColumn column = textColumns.get(i);

                double width = getColumnWidth(msoColumnsDeclaration, valueIndex);
                if (MathUtil.isMinValue(width))
                {
                    return;
                }

                column.setWidth(width);
                valueIndex++;

                if (i < (columnCount - 1))
                {
                    double spacing = getColumnSpacing(msoColumnsDeclaration, valueIndex);
                    if (MathUtil.isMinValue(spacing))
                    {
                        return;
                    }

                    column.setSpaceAfter(spacing);
                    valueIndex++;
                }
            }
        }

        // MS Word writes 'w:space="-1"' attribute for 'w:col' element for all columns except last two ones
        // regardless of the actual value.
        for (int i = 0; i < (textColumns.getCount() - 2); i++)
        {
            textColumns.get(i).setRawSpaceAfter(-1);
        }

        CssDeclaration msoColumnSeparatorDeclaration = pageDeclarations.get("mso-column-separator");
        if (msoColumnSeparatorDeclaration == null)
        {
            return;
        }

        String columnSeparatorIdentifier = getColumnSeparatorIdentifier(msoColumnSeparatorDeclaration);
        if (!StringUtil.hasChars(evenlySpacedIdentifier))
        {
            return;
        }

        columnSeparatorIdentifier = msString.toLowerInvariant(columnSeparatorIdentifier);
        if ((!"none".equals(columnSeparatorIdentifier)) &&
            (!"solid".equals(columnSeparatorIdentifier)))
        {
            return;
        }

        switch (gStringSwitchMap.of(columnSeparatorIdentifier))
        {
            case /*"none"*/2:
                // MS Word doesn't write 'w:sep="0"' attribute for 'w:cols' element.
                break;
            case /*"solid"*/3:
                textColumns.setLineBetween(true);
                break;
            default:
                Debug.fail("Invalid value.");
                return;
        }
    }

    protected /*override*/ void applySpecialFormatting(Section section)
    {
        if (getCssStyleTracker().getElementDeclarations().get("page") == null)
        {
            // MS Word doesn't recognize DIV elements as sections if they have no 'page' declarations.
            return;
        }

        if (getCssStyleTracker().getElementDeclarations().containsIdentifier("direction", "rtl"))
        {
            section.getSectPr().setBidi(true);
        }
    }

    private static void clearSectionAttributes(SectPr sectPr)
    {
        // DocumentBuilder creates a new section by cloning current section, but MS Word creates a new section
        // with default properties. So here we clear all section's attributes, but preserve SectAttr.SectionStart
        // attribute if any.
        Object sectionStartAttribute = sectPr.getDirectSectionAttr(SectAttr.SECTION_START);

        sectPr.clearSectionAttrs();

        if (sectionStartAttribute != null)
        {
            /*SectionStart*/int sectionStart = (/*SectionStart*/Integer)sectionStartAttribute;

            // MS Word doesn't write 'w:val="nextPage"' attribute for 'w:type' element.
            if (sectionStart != SectionStart.NEW_PAGE)
            {
                sectPr.setSectionStart(sectionStart);
            }
        }
    }

    private static int parsePageSize(CssValue value)
    {
        int result = 0;
        if (value.isLength(true))
        {
            double sizeInPoints = CssUtil.lengthToPoint(value);
            if (!MathUtil.isMinValue(sizeInPoints))
            {
                // Note that this conversion may overflow, but this happens in MS Word too.
                int sizeInTwips = ConvertUtilCore.pointToTwip(sizeInPoints);
                // Min page size that MS Word uses is 0.1 inches.
                final int MIN_SIZE_TWIP = 144;
                result = MathUtil.fitToRange(sizeInTwips, MIN_SIZE_TWIP, ConvertUtilCore.MAX_SIZE_TWIP);
            }
        }
        return result;
    }

    private static int getColumnCount(CssDeclaration msoColumnsDeclaration)
    {
        if (msoColumnsDeclaration.getValue().getCount() < 1)
        {
            return -1;
        }

        CssValue value = msoColumnsDeclaration.getValue().get(0);
        if (value.getValueType() != CssValueType.NUMBER)
        {
            return Integer.MIN_VALUE;
        }

        CssNumberValue numberValue = (CssNumberValue)value;
        if (MathUtil.isMinValue(numberValue.getDoubleValue()))
        {
            return Integer.MIN_VALUE;
        }

        return MathUtil.doubleToInt(numberValue.getDoubleValue());
    }

    private static String getColumnEvenlySpacedIdentifier(CssDeclaration msoColumnsDeclaration)
    {
        if (msoColumnsDeclaration.getValue().getCount() < 2)
        {
            return "";
        }

        CssValue value = msoColumnsDeclaration.getValue().get(1);
        if (value.getValueType() != CssValueType.IDENTIFIER)
        {
            return "";
        }

        return ((CssIdentifierValue)value).getValue();
    }

    private static double getColumnWidth(
        CssDeclaration msoColumnsDeclaration,
        int valueIndex)
    {
        if (msoColumnsDeclaration.getValue().getCount() < (valueIndex + 1))
        {
            return -Double.MAX_VALUE;
        }

        CssValue value = msoColumnsDeclaration.getValue().get(valueIndex);
        if (value.getValueType() != CssValueType.LENGTH)
        {
            return -Double.MAX_VALUE;
        }

        CssLengthValue widthValue = (CssLengthValue)value;
        return widthValue.getLength(CssUnit.PT);
    }

    private static double getColumnSpacing(
        CssDeclaration msoColumnsDeclaration,
        int valueIndex)
    {
        if (msoColumnsDeclaration.getValue().getCount() < (valueIndex + 1))
        {
            return -Double.MAX_VALUE;
        }

        CssValue value = msoColumnsDeclaration.getValue().get(valueIndex);
        if (value.getValueType() != CssValueType.LENGTH)
        {
            return -Double.MAX_VALUE;
        }

        CssLengthValue spacingValue = (CssLengthValue)value;
        return spacingValue.getLength(CssUnit.PT);
    }

    private static String getColumnSeparatorIdentifier(CssDeclaration msoColumnSeparatorDeclaration)
    {
        if (!msoColumnSeparatorDeclaration.hasSingleValueOfType(CssValueType.IDENTIFIER))
        {
            return "";
        }

        CssValue value = msoColumnSeparatorDeclaration.getValue().getFirstValue();
        return ((CssIdentifierValue)value).getValue();
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"even",
		"not-even",
		"none",
		"solid"
	);

}

