// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2019 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToIntDictionary;


/// <summary>
/// Allows normalizing indexes of style definition (Istd) that are stored in the <see cref="Style.Istd"/> properties.
/// </summary>
/// <remarks>
/// After deleting the styles, unused Istds are formed, which, combined with the restriction of the maximum
/// value of Istd (4094), reduces the possible number of styles that can be added to the style collection.
/// So, Istd normalization is needed at this case.
/// </remarks>
class StyleIstdNormalizer extends IstdVisitor
{
    /// <summary>
    /// Cannot create instances of this class from outside.
    /// </summary>
    private StyleIstdNormalizer()
    {
    }

    /// <summary>
    /// Executes normalization of indexes of style definition for the specified document.
    /// </summary>
    static void execute(DocumentBase document) throws Exception
    {
        StyleIstdNormalizer normalizer = new StyleIstdNormalizer();
        normalizer.executeCore(document);
    }

    protected /*override*/ void onRunPr(RunPr runPr)
    {
        if (runPr.contains(FontAttr.ISTD))
            runPr.setIstd(getNewIstd(runPr.getIstd()));
    }

    protected /*override*/ void onParaPr(ParaPr paraPr)
    {
        if (paraPr.contains(ParaAttr.ISTD))
            paraPr.setIstd(getNewIstd(paraPr.getIstd()));
    }

    protected /*override*/ void onRowPr(TablePr tablePr)
    {
        if (tablePr.contains(TableAttr.ISTD))
            tablePr.setIstd(getNewIstd(tablePr.getIstd()));
    }

    protected /*override*/ void onDocPr(DocPr docPr)
    {
        docPr.ClickTypeParaStyleIstd = getNewIstd(docPr.ClickTypeParaStyleIstd);
        docPr.DefaultTableStyleIstd = getNewIstd(docPr.DefaultTableStyleIstd);
    }

    /// <summary>
    /// Performs normalization of Istds for the specified document.
    /// </summary>
    private void executeCore(DocumentBase document) throws Exception
    {
        mDocument = document;

        generateIstdMap();

        // Visit all document content and update style Istd in node properties.
        run(document);

        updateListStyleIstds();
        updateStyles();
    }

    /// <summary>
    /// Generates new Istds and a map from an old Istd to a new Istd.
    /// </summary>
    private void generateIstdMap()
    {
        // Let's keep order of Istds, it is used on writing styles.
        Style[] styles = new Style[mDocument.getStyles().getMaxIstd() + 1];
        for (Style style : mDocument.getStyles())
            styles[style.getIstd()] = style;

        for (Style style : styles)
        {
            if ((style != null) && (style.getIstd() > StyleCollection.MAX_FIXED_ISTD))
            {
                mIstdMap.set(style.getIstd(), mNextIstd);
                mNextIstd++;
            }
        }
    }

    /// <summary>
    /// Updates Istds of styles, also including base, linked and next style Istds.
    /// </summary>
    private void updateStyles()
    {
        for (Style style : mDocument.getStyles())
        {
            // Need to get all depending style Istd before assigning style Istd, since depending style Istds may
            // be reset on the assignment.
            int linkedStyleNewIstd = getNewIstd(style.getLinkedIstd());
            int baseStyleNewIstd = getNewIstd(style.getBasedOnIstd());
            // Next style may be not preserved during document cleanup: need to reset it, otherwise it may become to
            // refer another style after normalization.
            int nextStyleIstd = (mDocument.getStyles().getByIstd(style.getNextIstd(), false) != null)
                ? getNewIstd(style.getNextIstd())
                : StyleIndex.NIL;

            if (style.getIstd() > StyleCollection.MAX_FIXED_ISTD)
                style.setIstd(getNewIstd(style.getIstd()), false);

            style.setLinkedIstd(linkedStyleNewIstd);
            style.setBasedOnIstd(baseStyleNewIstd);
            style.setNextIstd(nextStyleIstd);
        }

        mDocument.getStyles().updateIstdMap();
    }

    /// <summary>
    /// Updates list definition style Istds.
    /// </summary>
    private void updateListStyleIstds()
    {
        for (ListDef listDef : mDocument.getLists().getListDefs())
        {
            listDef.setListStyleIstd(getNewIstd(listDef.getListStyleIstd()));

            for (ListLevel level : listDef.getLevels())
            {
                // If a level is not used, its style is removed by document cleaner: need update it, otherwise it
                // may become referred to another style after normalization.
                level.setParaStyleIstd((mDocument.getStyles().getByIstd(level.getParaStyleIstd(), false) != null)
                    ? getNewIstd(level.getParaStyleIstd())
                    : StyleIndex.NIL);
            }
        }
    }

    /// <summary>
    /// Returns a new Istd for the specified old Istd.
    /// </summary>
    /// <remarks>
    /// The property returns a negative value if the specified old Istd is an Istd of a fixed style.
    /// </remarks>
    int getNewIstd(int oldIstd)
    {
        // Need return a negative value for a fixed style to not set fixed style explicitly when a style property
        // returns a default value.
        if (oldIstd <= StyleCollection.MAX_FIXED_ISTD)
            return oldIstd;

        int newIstd = mIstdMap.get(oldIstd);

        // If it is an Istd that didn't exist, we need to return an Istd that does not exist too.
        if (newIstd < 0)
            newIstd = (oldIstd < mNextIstd) ? mNextIstd : oldIstd;

        return newIstd;
    }

    private DocumentBase mDocument;
    private /*final*/ IntToIntDictionary mIstdMap = new IntToIntDictionary();

    /// <summary>
    /// A new Istd for the next old Istd.
    /// </summary>
    private int mNextIstd = StyleCollection.MAX_FIXED_ISTD + 1;
}

