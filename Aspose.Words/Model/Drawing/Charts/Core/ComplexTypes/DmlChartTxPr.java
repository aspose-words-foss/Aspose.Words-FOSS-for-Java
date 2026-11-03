// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Class represents txPr element (5.7.2.217).
/// </summary>
class DmlChartTxPr implements Cloneable
{

    DmlParagraph addParagraph()
    {
        DmlParagraph para = new DmlParagraph();
        para.getProperties().setParentProperties(G_DEFAULT_PARENT_BAG_PROVIDER);
        msArrayList.add(mParagraphs, para);
        return para;
    }

    DmlChartTxPr deepClone()
    {
        DmlChartTxPr lhs = (DmlChartTxPr)memberwiseClone();
        if (mBodyPr != null)
            lhs.mBodyPr = mBodyPr.deepClone();

        lhs.mEmptyBodyPr = mEmptyBodyPr.deepClone();

        if (mLstStyle != null)
            lhs.mLstStyle = mLstStyle.deepClone();

        lhs.mParagraphs = new ArrayList<DmlParagraph>();
        for (DmlParagraph paragraph : mParagraphs)
            msArrayList.add(lhs.mParagraphs, paragraph.deepClone());

        // If mParagraphs is not empty, mFirstParagraph is the first item of it and is already cloned.
        lhs.mFirstParagraph = (mParagraphs.size() == 0) && (mFirstParagraph != null)
            ? mFirstParagraph.deepClone()
            : null;

        return lhs;
    }

    /// <summary>
    /// If <see cref="Paragraphs"/> is empty, adds a paragraph to it.
    /// </summary>
    void ensureParagraphExists()
    {
        if (getParagraphs().size() == 0)
            getParagraphs().add(getFirstParagraph());
    }

    /// <summary>
    /// If <see cref="BodyPr"/> is empty, creates a new one.
    /// </summary>
    void ensureBodyPrExists()
    {
        if (mBodyPr == null)
        {
            mBodyPr = mEmptyBodyPr;
            ensureParagraphExists();
            // Without this, data labels are displayed with wrong font size.
            getFirstParagraph().getProperties().hasDefaultRunProperties(true);
        }
    }

    /// <summary>
    /// Returns text of the runs of the child paragraphs.
    /// </summary>
    String getRunText()
    {
        if (mParagraphs.size() <= 1)
            return getFirstParagraph().getRunText();

        StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;

        for (DmlParagraph paragraph : mParagraphs)
        {
            if (!isFirst)
                msStringBuilder.append(stringBuilder, "\r");

            msStringBuilder.append(stringBuilder, paragraph.getRunText());

            isFirst = false;
        }

        return stringBuilder.toString();
    }

    /// <summary>
    /// Clears the properties so that default formatting is used.
    /// </summary>
    void clear()
    {
        mBodyPr = null;
        mFirstParagraph = (mParagraphs.size() > 0) ? mParagraphs.get(0) : null;

        for (DmlParagraph paragraph : mParagraphs)
        {
            paragraph.getProperties().clear();
            paragraph.setTextBody(null);
            paragraph.setEndParagraphRunProperties(null);
        }
    }

    DmlTextBodyProperties getBodyPr() { return (mBodyPr == null) ? mEmptyBodyPr : mBodyPr; }
    void setBodyPr(DmlTextBodyProperties value) { mBodyPr = value; }

    DmlTextListStyles getLstStyle()
    {
        if (mLstStyle == null)
            mLstStyle = new DmlTextListStyles();

        return mLstStyle;
    }

    DmlRunProperties getRunPr() { return getFirstParagraph().getProperties().getDefaultRunProperties(); }

    /// <summary>
    /// Shortcut to the first paragraph in the collection.
    /// If the collection is empty, generated one paragraph without adding to the collection to not write it to
    /// the document.
    /// </summary>
    DmlParagraph getFirstParagraph()
    {
        // I am not sure why we need paragraphs in Pr element at all, but it seems MS Word uses only properties
        // of the first paragraph.
        // For data label from Waterfall.docx, MS Word takes pPr properties from the first paragraph, but rPr
        // properties from the last one: ignored now since all paragraphs have same formatting (WORDSNET-14824).
        if (mFirstParagraph == null)
        {
            if (mParagraphs.size() > 0)
            {
                mFirstParagraph = mParagraphs.get(0);
            }
            else
            {
                mFirstParagraph = new DmlParagraph();
                mFirstParagraph.getProperties().setParentProperties(G_DEFAULT_PARENT_BAG_PROVIDER);
            }
        }

        return mFirstParagraph;
    }

    /// <summary>
    /// Gets a list of paragraphs in this text properties.
    /// </summary>
    ArrayList<DmlParagraph> getParagraphs() { return mParagraphs; }

    boolean isEmpty()
    {
        if ((mBodyPr != null) || (mParagraphs.size() > 0))
            return false;

        if (mFirstParagraph == null)
            return true;

        return
            mFirstParagraph.getProperties().isEmpty() &&
            ((mFirstParagraph.getTextBody() == null) || mFirstParagraph.getTextBody().getProperties().isEmpty()) &&
            mFirstParagraph.getEndParagraphRunProperties().isEmpty();
    }

    private DmlTextBodyProperties mBodyPr;
    private DmlTextListStyles mLstStyle;
    private DmlTextBodyProperties mEmptyBodyPr = new DmlTextBodyProperties();

    /// <summary>
    /// According to specification txPr must contain at least one paragraph, but can contain unlimited number
    ///  of paragraphs.
    /// I am not sure why we need paragraphs in Pr element at all, but it seems MS Word uses only properties
    /// of the first paragraph.
    /// Anyway lets read all the paragraphs into this list.
    /// </summary>
    private ArrayList<DmlParagraph> mParagraphs = new ArrayList<DmlParagraph>();

    private DmlParagraph mFirstParagraph;

    private static /*final*/ IDmlHierarchicalPropertyBagParentProvider G_DEFAULT_PARENT_BAG_PROVIDER;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
    static/* DmlChartTxPr()*/
    {
        // Looks like chart paragraphs has different defaults than shape ones.
        IDmlHierarchicalPropertyBag defaults = DmlParagraphPropertiesDefaults.getInstance().deepClone();
        defaults.setProperty((int)DmlParagraphPropertiesIds.ALIGNMENT, ParagraphAlignment.CENTER);
        G_DEFAULT_PARENT_BAG_PROVIDER = new DmlHierarchicalPropertyBagParentContainer(defaults);
    }
}

