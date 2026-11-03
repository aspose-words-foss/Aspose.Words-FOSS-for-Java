// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2013 by Denis Shvydkiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;

/// <summary>
/// Adapter for <see cref="StructuredDocumentTag"/> class to <see cref="IInline"/> interface.
/// This class is used for extracting run properties from StructuredDocumentTag.
/// The class is not intended and not tested for other purposes.
/// </summary>
class StructuredDocumentTagToInlineAdapter implements IInline
{
    StructuredDocumentTagToInlineAdapter(StructuredDocumentTag sdt)
	{
		this(sdt, true);
	}
    StructuredDocumentTagToInlineAdapter(StructuredDocumentTag sdt, boolean isStart)
    {
        Debug.assertTrue(sdt != null);
        mSdt = sdt;
        mIsStart = isStart;
    }

    public Object /*IRunAttrSource.*/getDirectRunAttr(int key)
    {
        return ((IRunAttrSource)this).getDirectRunAttr(key, RevisionsView.ORIGINAL);
    }

    public Object /*IRunAttrSource.*/getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return getRunPr().getDirectAttr(key, revisionsView);
    }

    public Object /*IRunAttrSource.*/fetchInheritedRunAttr(int fontAttr)
    {
        return InlineHelper.fetchInheritedAttr(this, fontAttr);
    }

    public void /*IRunAttrSource.*/setRunAttr(int fontAttr, Object value)
    {
        getRunPr().setAttr(fontAttr, value);
    }

    public void /*IRunAttrSource.*/removeRunAttr(int key)
    {
        getRunPr().remove(key);
    }

    public void /*IRunAttrSource.*/clearRunAttrs()
    {
        getRunPr().clear();
    }

    public Paragraph /*IInline.*/getParentParagraph_IInline() { return ms.as(mSdt.getParentNode(), Paragraph.class); }

    public DocumentBase /*IInline.*/getDocument_IInline() { return mSdt.getDocument(); }

    public RunPr /*IInline.*/getExpandedRunPr_IInline(/*RunPrExpandFlags*/int flags)
    {
        return InlineHelper.getExpandedRunPr(this, flags);
    }

    public RunPr /*IInline.*/getRunPr_IInline() { return getRunPr(); }
    public void /*IInline.*/setRunPr_IInline(RunPr value)
    {
        if (mIsStart)
            mSdt.setContentsRunPr(value);
        else
            mSdt.setEndCharacterRunPr(value);
    }

    private RunPr getRunPr() { return mIsStart ? mSdt.getContentsRunPr() : mSdt.getEndCharacterRunPr(); }

    private /*final*/ boolean mIsStart;
    private /*final*/ StructuredDocumentTag mSdt;
}

