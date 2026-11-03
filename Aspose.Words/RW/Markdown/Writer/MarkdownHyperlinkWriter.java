// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/04/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.Debug;


/// <summary>
/// The class responsible for writing Hyperlinks into markdown.
/// </summary>
class MarkdownHyperlinkWriter extends MarkdownFieldWriter
{
    /// <summary>
    /// Initializes a new instance of the <see cref="MarkdownHyperlinkWriter" /> class.
    /// </summary>
    MarkdownHyperlinkWriter(FieldStart fieldStart, MarkdownLinkDefinitionWriter linkDefinitionWriter,
        MarkdownWriter writer)
    {
    	super(fieldStart, writer);
	
        mLinkDefinitionWriter = linkDefinitionWriter;
        mLinkExportMode = writer.getSaveOptions().getLinkExportMode();
        mFieldType = fieldStart.getFieldType();
    }

    /*override*/ void onFieldSeparator(FieldSeparator fieldSeparator) throws Exception
    {
        String text = getFieldResult().toString();
        if (!StringUtil.hasChars(text))
            return;

        // NOTE: FieldCodeHyperlink.Parse() cannot understand double quotes and backslashes
        // in the screen tip, if they are not escaped. Backslashes are thrown out.
        // The screen tip is cut at the first unescaped double quote.
        mFieldCodeHyperlink = FieldCodeHyperlink.parse(text);

        // WORDSNET-25816 The trick with replacing FieldCodeHyperlink SubAddress by FieldRef Address
        // allows us to implement FieldRef support without introducing a new separate field writer.
        // WORDSNET-28104 Added the same trick for FieldPageRef.
        if ((mFieldType == FieldType.FIELD_REF) || (mFieldType == FieldType.FIELD_PAGE_REF))
        {
            mFieldCodeHyperlink.setSubAddress(mFieldCodeHyperlink.getAddress());
            mFieldCodeHyperlink.setAddress("");
        }

        mLinkDefinitionWriter.onHyperlink(mFieldCodeHyperlink);

        super.onFieldSeparator(fieldSeparator);
    }

    /// <summary>
    /// Gets a string that is ready for writing into markdown.
    /// </summary>
    protected /*override*/ String getText()
    {
        // WORDSNET-26087 There is only a hidden text in FieldCode. We need a resilience for this case.
        if (mFieldCodeHyperlink == null)
            return "";

        if (isValidAutolink())
            return msString.format("{0}{1}{2}",
                AutolinkInlineBlock.OPENING_DELIMITER, getFieldResult(), AutolinkInlineBlock.CLOSING_DELIMITER);

        String displayText = MarkdownUtil.escapeSquareBrackets(getFieldResult().toString());
        String referenceString = MarkdownLinkDefinitionWriter.getReferenceString(mFieldCodeHyperlink);
        String target = MarkdownUtil.escapeMarkupSymbols(mFieldCodeHyperlink.getHRef().toString(false));
        String title = (mFieldCodeHyperlink.getScreenTip().length() > 0)
            ? msString.format(" \"{0}\"", MarkdownUtil.escapeDoubleQuotes(msString.replace(mFieldCodeHyperlink.getScreenTip(), ControlChar.LINE_BREAK_CHAR, MarkdownUtil.HARD_LINE_BREAK_SLASH_CHAR)))
            : "";

        if (StringUtil.hasChars(target) && msString.startsWith(target, "#", StringComparison.INVARIANT_CULTURE))
            // WORDSNET-24954 ToLowerInvariant() is a workaround for html import of mixed case links.
            // See TestExportMixedCaseLinks() for more details.
            target = msString.toLowerInvariant(target);

        if (!mLinkDefinitionWriter.hasFullReferenceLinkDefinition(referenceString, mFieldCodeHyperlink.getHRef().toString(false),
            mFieldCodeHyperlink.getScreenTip()))
        {
            String newReference = msString.format("{0}{1}{2}", LinkTextBlock.OPENING_DELIMITER,
                displayText, LinkTextBlock.CLOSING_DELIMITER);
            mLinkDefinitionWriter.setReference(newReference, mFieldCodeHyperlink.getHRef().toString(false),
                mFieldCodeHyperlink.getScreenTip());
        }

        // Exported as Inline if the corresponding option is set or if the reference link could not have been resolved.
        boolean isExportAsInline = (mLinkExportMode == MarkdownLinkExportMode.INLINE) ||
                                (!mLinkDefinitionWriter.hasCollapsedLinkDefinition(mFieldCodeHyperlink.getHRef().toString(false),
                                    mFieldCodeHyperlink.getScreenTip()));
        // Exported as a shortcut if referenceLabel is empty or matches the display result text of the current field.
        boolean isExportAsShortcut = (msString.equals(referenceString, "")) ||
                                  (msString.equals(referenceString.substring((1), (1) + (referenceString.length() - 2)), displayText));
        return isExportAsInline
            ? msString.format("{0}{1}{2}{3}{4}{5}{6}",
                LinkTextBlock.OPENING_DELIMITER, displayText, LinkTextBlock.CLOSING_DELIMITER,
                LinkDestinationBlock.OPENING_DELIMITER, target, title, LinkDestinationBlock.CLOSING_DELIMITER)
            : isExportAsShortcut
                ? msString.format("{0}{1}{2}", LinkTextBlock.OPENING_DELIMITER, displayText, LinkTextBlock.CLOSING_DELIMITER)
                : msString.format("{0}{1}{2}{3}", LinkTextBlock.OPENING_DELIMITER, displayText,
                    LinkTextBlock.CLOSING_DELIMITER, referenceString);
    }

    /// <summary>
    /// Gets a boolean value indicating either this hyperlink is a valid Autolink.
    /// </summary>
    private boolean isValidAutolink()
    {
        Debug.assertTrue(mFieldCodeHyperlink != null);

        String displayText = getFieldResult().toString();
        String target = mFieldCodeHyperlink.getHRef().toString(false);

        // To be a valid Autolink, the target must be the same as display text.
        if (msString.equals(target, displayText, StringComparison.ORDINAL) ||
            (
                // The MailTo prefix can be omitted at the beginning of the target (see TestAutolinkP()).
                msString.startsWith(target, MAIL_TO_PREFIX, StringComparison.INVARIANT_CULTURE_IGNORE_CASE) &&
                msString.equals(target.substring(MAIL_TO_PREFIX.length()), displayText, StringComparison.ORDINAL)
            ))
        {
            return AutolinkInlineBlock.isValid(target, 0, target.length() - 1);
        }

        return false;
    }

    /// <summary>
    /// FieldCode hyperlink.
    /// </summary>
    private FieldCodeHyperlink mFieldCodeHyperlink;

    private /*final*/ /*FieldType*/int mFieldType;

    private /*final*/ MarkdownLinkDefinitionWriter mLinkDefinitionWriter;

    private /*final*/ /*MarkdownLinkExportMode*/int mLinkExportMode;

    private static final String MAIL_TO_PREFIX = "mailto:";
}

