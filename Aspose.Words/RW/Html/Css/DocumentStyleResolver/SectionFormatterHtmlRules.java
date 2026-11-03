// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2023 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.Debug;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Applies CSS formatting to document sections.
/// Uses formatting rules that are different from what MS Word uses but produce better looking results
/// and are more confomant to the CSS and HTML specifications.
/// </summary>
class SectionFormatterHtmlRules extends SectionFormatter
{
    SectionFormatterHtmlRules(
        Document document,
        CssStyleTracker cssStyleTracker)
    {
    	super(document, cssStyleTracker);
	
        // Empty constructor.
    }

    /*override*/ void updateDefaultSectionProperties(Section section)
    {
        // Nothing to do.
    }

    /// <summary>
    /// Applies CSS size style to a model section's page setup.
    /// </summary>
    /// <remarks>
    /// MS Word applies the following rules during setting page setup:
    /// 1. If "size" has a dimension then page orientation is always portrait (example: "size: 300pt 200pt").
    ///    We don't follow MS Word behavior and instead detect page orientation by aspect ratio, because it is more logical.
    /// 2. If "size" is "portrait" then the page orientation is set to portrait (example: "size: portrait").
    /// 3. If "size" is "landscape" then the page orientation is set to landscape (example: "size: landscape").
    /// 4. If "size" contains a page size name (Letter, A4 and etc) then the resulting page size in MS Word depends
    ///    on Office language preferences. For English (US) the default page size is Letter, for other languages it is A4.
    ///    We don't follow MS Word behavior in that case and instead set the page size as specified in CSS.
    /// </remarks>
    protected /*override*/ void applyPageSetupFormatting(
        Section section,
        CssDeclarationCollection pageDeclarations)
    {
        CssDeclaration sizeDeclaration = pageDeclarations.get("size");
        if (sizeDeclaration == null)
            return;

        CssPageSizePropertyValue value = parsePageSizePropertyValue(sizeDeclaration.getValue());
        if (!(sizeDeclaration.getValue() instanceof CssPageSizePropertyValue))
            return;

        double width = CssUtil.lengthToPoint(value.getWidth());
        double height = CssUtil.lengthToPoint(value.getHeight());
        boolean pageSizeIsSpecified = !MathUtil.isMinValue(width) && !MathUtil.isMinValue(height);

        PageSetup pageSetup = section.getPageSetup();

        // Set page orientation first, because the Orientation setter exchanges width and height when page orientation
        // changes.
        switch (value.getOrientation())
        {
            case CssPageOrientation.LANDSCAPE:
            {
                pageSetup.setOrientation(Orientation.LANDSCAPE);
                break;
            }
            case CssPageOrientation.PORTRAIT:
            case CssPageOrientation.NOT_SPECIFIED:
            {
                // WORDSNET-10576 If page size is specified, set page orientation by the longest side of the page.
                // Otherwise, use the portrait orientation.
                pageSetup.setOrientation((pageSizeIsSpecified && (width > height))
                    ? Orientation.LANDSCAPE
                    : Orientation.PORTRAIT);
                break;
            }
            default:
            {
                Debug.assertTrue(false);
                break;
            }
        }

        // Set the page size. Here the width value is always less than or equal to the height value. Otherwise, we would've
        // changed the page orientation.
        if (pageSizeIsSpecified)
        {
            if ((value.getOrientation() == CssPageOrientation.PORTRAIT) ||
                (value.getOrientation() == CssPageOrientation.NOT_SPECIFIED))
            {
                pageSetup.setPageWidth(width);
                pageSetup.setPageHeight(height);
            }
            else
            {
                pageSetup.setPageWidth(height);
                pageSetup.setPageHeight(width);
            }
        }

        // WORDSNET-22945 Preserve page numbering properties. The code below restores these properties from CSS.

        String pageNumberStyle = pageDeclarations.getIdentifier(HtmlConstants.PAGE_SETUP_NUMBER_STYLE);
        // If the page number style CSS property doesn't exist, the default 'arabic' value is used.
        pageSetup.setPageNumberStyle(DocxEnum.docxToNumberStyle(pageNumberStyle));

        String chapterPageSeparator = pageDeclarations.getIdentifier(HtmlConstants.PAGE_SETUP_CHAPTER_SEPARATOR);
        // If the chapter separator CSS property doesn't exist, the default 'hyphen' value is used.
        pageSetup.setChapterPageSeparator(NrxSectEnum.xmlToChapterPageSeparator(chapterPageSeparator));

        double headingLevelForChapter = pageDeclarations.getNumber(HtmlConstants.PAGE_SETUP_HEADING_LEVEL_FOR_CHAPTER);
        pageSetup.setHeadingLevelForChapter((int)MathUtil.fitToRange(headingLevelForChapter, 0.0, 9.0));

        double pageStartingNumber = pageDeclarations.getNumber(HtmlConstants.PAGE_SETUP_STARTING_NUMBER);
        // If the page starting number is omitted, numbering continues from the highest page number in the previous section.
        if (MathUtil.isMinValue(pageStartingNumber))
        {
            pageSetup.setRestartPageNumbering(false);
        }
        else
        {
            pageSetup.setPageStartingNumber((int)MathUtil.fitToRange(pageStartingNumber, 1.0, Integer.MAX_VALUE));
            pageSetup.setRestartPageNumbering(true);
        }

        double headerDistance = pageDeclarations.getLength(HtmlConstants.PAGE_SETUP_HEADER_DISTANCE);
        if (!MathUtil.isMinValue(headerDistance))
        {
            // Limit the header distance value to the range allowed by MS Word.
            pageSetup.setHeaderDistance(MathUtil.fitToRange(headerDistance, 0.0, ConvertUtilCore.MAX_SIZE_POINT));
        }

        double footerDistance = pageDeclarations.getLength(HtmlConstants.PAGE_SETUP_FOOTER_DISTANCE);
        if (!MathUtil.isMinValue(footerDistance))
        {
            // Limit the footer distance value to the range allowed by MS Word.
            pageSetup.setFooterDistance(MathUtil.fitToRange(footerDistance, 0.0, ConvertUtilCore.MAX_SIZE_POINT));
        }
    }

    protected /*override*/ void applySpecialFormatting(Section section)
    {
        /*TextFlow*/int textFlow = CssUtil.cssBlockFlowDirectionToTextFlow(getCssStyleTracker().getBlockFlowDirection());
        if (section.getSectPr().getTextFlow() != textFlow)
        {
            section.getSectPr().setTextFlow(textFlow);
        }

        if (getCssStyleTracker().getElementDeclarations().containsIdentifier("direction", "rtl"))
        {
            section.getSectPr().setBidi(true);
        }
    }

    private static CssPageSizePropertyValue parsePageSizePropertyValue(CssPropertyValue propertyValue)
    {
        // Value:    <length>{1,2} | auto | [ <page-size> || [ portrait | landscape] ]
        switch (propertyValue.getCount())
        {
            case 1:
            {
                CssValue value = propertyValue.get(0);
                if (value.equals(CssValue.Auto))
                    return CssPageSizePropertyValue.createAuto();
                if (value.getValueType() == CssValueType.IDENTIFIER)
                    return CssPageSizePropertyValue.createPageSize((CssIdentifierValue)value);

                CssLengthValue valueAsLength = value.toLength(true);
                if (valueAsLength != null)
                {
                    return CssPageSizePropertyValue.createLength(valueAsLength);
                }
                break;
            }
            case 2:
            {
                CssValue left = propertyValue.get(0);
                CssValue right = propertyValue.get(1);
                if (left.getValueType() == right.getValueType())
                {
                    if (left.getValueType() == CssValueType.LENGTH)
                        return CssPageSizePropertyValue.createLength((CssLengthValue)left, (CssLengthValue)right);
                    if (left.getValueType() == CssValueType.IDENTIFIER)
                        return CssPageSizePropertyValue.createPageSize((CssIdentifierValue)left, (CssIdentifierValue)right);
                }
                break;
            }
            default:
                break;
        }
        return null;
    }
}

