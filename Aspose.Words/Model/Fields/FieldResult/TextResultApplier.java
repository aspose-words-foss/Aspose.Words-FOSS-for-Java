// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;

/// <summary>
/// Applies a field result represented by a string.
/// </summary>
class TextResultApplier extends FieldResultApplier
{
    TextResultApplier(Field field, String result)
    {
    	this(field, result, true);
	
    }

    TextResultApplier(Field field, String result, boolean applyFormat)
    {
    	this(field, new FieldFormattingResult(RichString.createFromString(result), false), applyFormat);
	

    }

    TextResultApplier(Field field, FieldFormattingResult result)
    {
    	this(field, result, true);
	
    }

    TextResultApplier(Field field, FieldFormattingResult result, /*ParagraphBreakCharReplacement*/int paragraphBreakCharReplacement)
    {
    	this(field, result, true);
	
        mParagraphBreakCharReplacement = paragraphBreakCharReplacement;
    }

    private TextResultApplier(Field field, FieldFormattingResult result, boolean applyFormat)
    {
    	super(field, applyFormat);
	
        Debug.assertTrue(result != null);
        mResult = result;
    }

    protected /*override*/ void applyResultCore() throws Exception
    {
        Document document = getField().fetchDocument();
        // Use the builder because it inserts multi-paragraph text properly.
        DocumentBuilder builder = new DocumentBuilder(document);
        builder.setParagraphBreakCharReplacement(mParagraphBreakCharReplacement);

        // WORDSNET-4921 Set font attributes from the previous node.
        // WORDSNET-13345 Set font attributes from field code node.
        moveAndSetFontAttributesFromPreviousNode(builder, getField().getEnd(), getField().getStart());

        FieldTextHelper.writeTextBidiAware(getField(), builder, mResult);
    }

    /// <summary>
    /// Moves the builder to refNode, but resets the direct font attributes set by builder.MoveTo()
    /// with attributes from the following non whitespace inline node, if it is found.
    /// </summary>
    private void moveAndSetFontAttributesFromPreviousNode(DocumentBuilder builder, Node refNode, FieldStart formattingStartpointNode)
    {
        builder.moveTo(refNode);

        boolean isStartHidden = isHidden(formattingStartpointNode);

        // Originally, the inserted text should get the attributes from the first non whitespace node.
        // Later they may or may not be revised when applying the field format.
        // DocumentBuilder.MoveTo() sets the attributes from the next node.
        // So look for a following inline node to get the formatting attributes from.
        Node formattingSource = formattingStartpointNode;
        Inline inlineFormattingSource;
        do
        {
            formattingSource = formattingSource.getNextSibling();
            inlineFormattingSource = ms.as(formattingSource, Inline.class);
            if (inlineFormattingSource != null &&
                NodeUtil.isNonWhitespace(inlineFormattingSource) &&
                (isHidden(inlineFormattingSource) == isStartHidden))
                break;
        } while (formattingSource != null);

        if (inlineFormattingSource != null)
        {
            // Use the attributes from the previous node, if they are present.
            builder.setFont(inlineFormattingSource.getRunPr(), true);
        }
        // Do not change the attributes if a valid previous node not found.
        // They are still set from the refNode by DocumentBuilder.MoveTo().
    }

    private boolean isHidden(Inline inline)
    {
        return getField().isUpdating()
            ? getField().getUpdater().getHiddenAttributeCache().getHiddenAttribute(inline)
            : inline.isHiddenOrDeleted();
    }

    private /*final*/ FieldFormattingResult mResult;
    private /*final*/ /*ParagraphBreakCharReplacement*/int mParagraphBreakCharReplacement;
}

