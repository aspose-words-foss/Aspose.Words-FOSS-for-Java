// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/05/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Applies default format to a field result.
/// </summary>
/// <remarks>
/// Used for fields without any general formats.
/// </remarks>
class DefaultFormatApplier implements IFieldResultFormatApplier
{
    DefaultFormatApplier(Inline formattingSource)
    {
    	this(formattingSource.getRunPr(), new int[] { FontAttr.BIDI, FontAttr.HIDDEN });
	
    }

    protected DefaultFormatApplier(RunPr formattingSourceRunPr, int[] ignoredAttributes)
    {
        mFormattingSourceRunPr = formattingSourceRunPr;
        mIgnoredAttributes = ignoredAttributes;
    }

    public void /*IFieldResultFormatApplier.*/applyFormat(NodeRange result)
    {
        applyFormatCore(result);
    }

    protected /*virtual*/ void applyFormatCore(NodeRange result)
    {
        for (Node node : result)
        {
            switch (node.getNodeType())
            {
                case NodeType.RUN:
                case NodeType.SHAPE:
                case NodeType.SPECIAL_CHAR:
                    IInline inline = (IInline)node;
                    inline.setRunPr_IInline(adjustRunPr(inline.getRunPr_IInline(), node));
                    break;
                case NodeType.STRUCTURED_DOCUMENT_TAG:
                    StructuredDocumentTag sdt = (StructuredDocumentTag)node;
                    sdt.setContentsRunPr(adjustRunPr(sdt.getContentsRunPr(), node));
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }
    }

    protected /*virtual*/ RunPr adjustRunPr(RunPr original, Node owner)
    {
        RunPr sourceClone = AttrCollectionExtension.deepClone(mFormattingSourceRunPr);
        original.mirrorTo(sourceClone, mIgnoredAttributes);
        return sourceClone;
    }

    private /*final*/ RunPr mFormattingSourceRunPr;
    private /*final*/ int[] mIgnoredAttributes;
}

