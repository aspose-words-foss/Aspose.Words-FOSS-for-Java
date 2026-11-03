// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.HashSetGeneric;


/// <summary>
/// Removes unused styles, lists, list definitions from the document.
/// </summary>
class DocumentCleaner extends IstdVisitor
{
    private DocumentCleaner(CleanupOptions options)
    {
        mOptions = options;
    }

    static void execute(DocumentBase doc, CleanupOptions options) throws Exception
    {
        DocumentCleaner cleaner = new DocumentCleaner(options);
        cleaner.executeCore(doc);
    }

    protected /*override*/ void onRunPr(RunPr runPr)
    {
        addStyle(runPr.getIstd());
    }

    protected /*override*/ void onParaPr(ParaPr paraPr)
    {
        addStyle(paraPr.getIstd());

        if (paraPr.getListId() != 0)
            addList(paraPr.getListId());
    }

    protected /*override*/ void onRowPr(TablePr tablePr)
    {
        addStyle(tablePr.getIstd());
    }

    protected /*override*/ void onDocPr(DocPr docPr)
    {
        Style style = mDoc.getStyles().getByIstd(docPr.ClickTypeParaStyleIstd, false);
        if (style != null)
            addStyle(style.getIstd());
        style = mDoc.getStyles().getByIstd(docPr.DefaultTableStyleIstd, false);
        if (style != null)
            addStyle(style.getIstd());
    }

    private void executeCore(DocumentBase doc) throws Exception
    {
        mDoc = doc;

        // Visit all document content and collect used styles/lists.
        run(doc);

        if(!mOptions.getUnusedBuiltinStyles())
            addBuiltInStyles();

        addListsReferredByStyle();

        if (mOptions.getUnusedLists())
        {
            removeUnusedLists();
            removeUnusedListDefs();
        }

        if (mOptions.getUnusedStyles())
            removeUnusedStyles();

        if (mOptions.getDuplicateStyle())
            DuplicateStyleRemover.execute(doc);

        if (mOptions.getUnusedStyles() || mOptions.getDuplicateStyle())
            StyleIstdNormalizer.execute(doc);
    }

    /// <summary>
    /// Marks style and all related items (list, base style, etc) as used.
    /// </summary>
    private void addStyle(int istd)
    {
        if (mUsedStyles.contains(istd))
            return;

        Style style = mDoc.getStyles().getByIstd(istd, false);

        mUsedStyles.add(istd);

        if (style == null)
            return;

        // Add referred list if needed.
        if (style.getParaPr().getListId() != 0)
            addList(style.getParaPr().getListId());

        // Add linked style if needed.
        if (style.getLinkedIstd() != StyleIndex.NIL)
            addStyle(style.getLinkedIstd());

        // Recursively add all based styles.
        if (style.getBasedOnIstd() != StyleIndex.NIL)
            addStyle(style.getBasedOnIstd());
    }

    /// <summary>
    /// Marks list and all related items (list definitions, referred styles, etc) as used.
    /// </summary>
    /// <param name="listId"></param>
    private void addList(int listId)
    {
        mUsedLists.add(listId);

        List list = mDoc.getLists().getListByListId(listId);

        if (list != null)
        {
            ListDef listDef = list.getListDef();
            addStyle(listDef.getListStyleIstd());
            mUsedListDefs.add(listDef.getListDefId());
        }
    }

    /// <summary>
    /// Marks all builtin styles as used.
    /// </summary>
    /// <remarks>
    /// AM. I think that we can be more aggressive and delete not used built-in styles as well. 
    /// </remarks>
    private void addBuiltInStyles()
    {
        for (Style style : mDoc.getStyles())
            if (style.getBuiltIn())
                addStyle(style.getIstd());
    }

    /// <summary>
    /// Marks all list that refers to used styles.
    /// </summary>
    private void addListsReferredByStyle()
    {
        for (List list : mDoc.getLists())
            if ((list.getStyle() != null) && (mUsedStyles.contains(list.getStyle().getIstd())))
                addList(list.getListId());
    }

    /// <summary>
    /// Removes all styles which are not marked as used.
    /// </summary>
    private void removeUnusedStyles()
    {
        int i = 0; 
        while (i < mDoc.getStyles().getCount())
        {
            Style style = mDoc.getStyles().get(i);
            if (!mUsedStyles.contains(style.getIstd()))
            {
                mDoc.getStyles().removeCore(style);
            }
            else
            {
                i++;
            }
        }
    }

    /// <summary>
    /// Removes all lists which are not marked as used.
    /// </summary>
    private void removeUnusedLists()
    {
        int i = 0;
        while (i < mDoc.getLists().getCount())
        {
            List list = mDoc.getLists().get(i);
            if (canRemoveList(list.getListId()))
            {
                mDoc.getLists().removeCore(list.getListId());
            }
            else
            {
                i++;
            }
        }
    }

    /// <summary>
    /// Checks if list is unused and can be safely removed from document.
    /// </summary>
    private boolean canRemoveList(int listId)
    {
        // WORDSNET-18579 If there is only one list and some content has reference to list (even not this one)
        // treat this list as indirectly referred and do not remove from document.
        if (mUsedLists.getCount() > 0 && (mDoc.getLists().getCount() == 1))
            return false;

        return !mUsedLists.contains(listId);
    }

    /// <summary>
    /// Removes all list definitions which are not marked as used.
    /// </summary>
    private void removeUnusedListDefs()
    {
        int i = 0;
        while (i < mDoc.getLists().getListDefs().size())
        {
            ListDef listDef = mDoc.getLists().getListDefs().get(i);
            if (!mUsedListDefs.contains(listDef.getListDefId()))
            {
                mDoc.getLists().getListDefs().remove(i);
            }
            else
            {
                i++;
            }
        }
    }

    private DocumentBase mDoc;

    /// <summary>
    /// Style indexes are marked as used.
    /// </summary>
    private /*final*/ HashSetGeneric<Integer> mUsedStyles = new HashSetGeneric<Integer>();

    /// <summary>
    /// List Ids are marked as used.
    /// </summary>
    private /*final*/ HashSetGeneric<Integer> mUsedLists = new HashSetGeneric<Integer>();

    /// <summary>
    /// List definition Ids are marked as used.
    /// </summary>
    private /*final*/ HashSetGeneric<Integer> mUsedListDefs = new HashSetGeneric<Integer>();

    private /*final*/ CleanupOptions mOptions;
}

