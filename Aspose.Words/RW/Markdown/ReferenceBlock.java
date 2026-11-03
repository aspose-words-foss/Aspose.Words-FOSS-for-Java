// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2023 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.words.ref.Ref;


/// <summary>
/// Represents a markdown ReferenceBlock block.
/// </summary>
abstract class ReferenceBlock extends ContainerBlock
{
    /// <summary>
    /// Resolve destination link.
    /// </summary>
    boolean resolve(HashMap<String, LinkDefinitionBlock> linkDefinitions, msStack<ReferenceBlock> unresolvedLinks)
    {
        if (resolveCore(getLabel(), linkDefinitions))
        {
            if (isFullReferenceText() && (unresolvedLinks.getCount() > 0))
            {
                ReferenceBlock linkLabelBlock = unresolvedLinks.pop();
                linkLabelBlock.resolveCore(getLabel(), linkDefinitions);
                linkLabelBlock.setRawText(mRawText);
                return true;
            }
        }
        else if (isCollapsedReferenceText())
            return true;
        else
        {
            unresolvedLinks.push(this);
            mRawText = "";
        }

        return false;
    }

    /// <summary>
    /// Returns true, if appropriate LinkDestinationBlock is not null.
    /// </summary>
    boolean hasLinkDestination()
    {
        return (getLinkDestinationBlock() != null);
    }

    /// <summary>
    /// Raw text containing all special characters.
    /// </summary>
    /// <remarks>
    /// Used only for mapping LinkText and LinkDefinition.
    /// </remarks>
    void setRawText(String text)
    {
        mRawText = text;
    }

    /// <summary>
    /// Expands the text content of the child block that was lost when the current block was parsed.
    /// </summary>
    void expandInlineLinkText()
    {
        if (getCount() == 0)
            add(new InlineBlock(""));

        InlineBlock inline = ms.as(this.get(0), InlineBlock.class);
        if (inline != null)
            inline.tryParse(msString.format("{0}{1}{2}", getOpeningStringDelimiter(), inline.getContentLine(),
                LinkTextBlock.CLOSING_DELIMITER), 0);
    }

    /// <summary>
    /// Gets a string value representing a label of the LinkDefinition block.
    /// </summary>
    String getDefinitionLabel()
    {
        return (StringUtil.hasChars(mRawText))
            ? msString.format("{0}{1}{2}", LinkTextOpeningDelimiter.CHARACTER, mRawText, LinkTextClosingDelimiter.CHARACTER)
            : "";
    }

    /// <summary>
    /// Resolve destination link.
    /// </summary>
    private boolean resolveCore(String label, HashMap<String, LinkDefinitionBlock> linkDefinitions)
    {
        Ref<LinkDefinitionBlock> referenceToMLinkDefinition = new Ref<LinkDefinitionBlock>(mLinkDefinition);
        boolean retValue = (getInlineLinkDestinationBlock() != null) ||
                        msDictionary.tryGetValue(linkDefinitions, label, /*out*/ referenceToMLinkDefinition);
        mLinkDefinition = referenceToMLinkDefinition.get();

        return retValue;
    }

    /// <summary>
    /// True if the current reference block is the link text part of the full reference link.
    /// </summary>
    private boolean isFullReferenceText()
    {
        return (isReferenceLink() && StringUtil.hasChars(getText()));
    }

    /// <summary>
    /// True if the current reference block is the link text part of the collapsed reference link.
    /// </summary>
    private boolean isCollapsedReferenceText()
    {
        return (isReferenceLink() && !StringUtil.hasChars(getText()));
    }

    /// <summary>
    /// True if the current reference block is the reference link.
    /// </summary>
    private boolean isReferenceLink()
    {
        if (getInlineLinkDestinationBlock() != null)
            return false;

        LinkTextBlock prevLinkDesc = ms.as(getPreviousSibling(), LinkTextBlock.class);
        ImageDescriptionBlock prevImageDesc = ms.as(getPreviousSibling(), ImageDescriptionBlock.class);
        LinkTextBlock next = ms.as(getNextSibling(), LinkTextBlock.class);
        if ((((prevLinkDesc != null) || (prevImageDesc != null)) && (next != null)) ||
            ((prevLinkDesc == null) && (prevImageDesc == null) && (next == null)))
            return false;

        return ((prevLinkDesc != null) || (prevImageDesc != null));
    }

    /// <summary>
    /// Gets a corresponding LinkDestination block, or <c>null</c> if not exist.
    /// </summary>
    LinkDestinationBlock getLinkDestinationBlock()
    {
        return (getInlineLinkDestinationBlock() != null)
            ? getInlineLinkDestinationBlock()
            : (mLinkDefinition != null) ? mLinkDefinition.getLinkDestination() : null;
    }

    /// <summary>
    /// Gets a corresponding inline LinkDestination block, or <c>null</c> if not exist.
    /// </summary>
    private LinkDestinationBlock getInlineLinkDestinationBlock() { return ms.as(getNextSibling(), LinkDestinationBlock.class); }

    /// <summary>
    /// Gets a string value representing a label of the current reference block.
    /// </summary>
    private String getLabel()
    {
        return (StringUtil.hasChars(mRawText))
            ? msString.toUpper(mRawText, msCultureInfo.getInvariantCulture())
            : msString.toUpper(getText(), msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Opening string delimiter.
    /// </summary>
    protected abstract String getOpeningStringDelimiter();

    private String mRawText;

    private LinkDefinitionBlock mLinkDefinition;
}


