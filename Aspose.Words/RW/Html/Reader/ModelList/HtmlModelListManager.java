// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/06/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.HashMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.words.ref.Ref;


/// <summary>
/// Helps to work with created lists.
/// </summary>
class HtmlModelListManager
{
    HtmlModelListManager(Document document)
    {
        Debug.assertTrue(document != null);

        mDocument = document;
        mModelLists = new HashMap<Integer, ArrayList<HtmlModelList>>();
    }

    /// <summary>
    /// Adds a new list item to a model list. The model list is chosen automatically. If no list fits, a new one is created.
    /// </summary>
    /// <returns>The chosen model list.</returns>
    HtmlModelList addListItem(/*ListTemplate*/int listTemplate, HtmlListItemBase listItem) throws Exception
    {
        HtmlModelList modelList = getList(listTemplate, listItem);
        listItem.addToList(modelList);

        return modelList;
    }

    void deleteAllLists()
    {
        mModelLists.clear();
    }

    private HtmlModelList getList(/*ListTemplate*/int listTemplate, HtmlListItemBase listItem)
    {
        ArrayList<HtmlModelList> modelLists = null;
        Ref<ArrayList<HtmlModelList>> referenceToModelLists = new Ref<ArrayList<HtmlModelList>>(modelLists);
        boolean outRefCondition0 = !msDictionary.tryGetValue(mModelLists, listItem.getListLevelId().getListNestedLevel(), /*out*/ referenceToModelLists);
        modelLists = referenceToModelLists.get();
        if (outRefCondition0)
        {
            modelLists = new ArrayList<HtmlModelList>();
            msDictionary.add(mModelLists, listItem.getListLevelId().getListNestedLevel(), modelLists);
        }

        // Currently we aren't concerned about preserving list structure, when importing lists.
        // But using latest created list first should provide better result, so using reverse enumeration here.
        for (int i = modelLists.size() - 1; i >= 0; i--)
        {
            HtmlModelList modelList = modelLists.get(i);
            if ((listItem.getListLevelId().getListLevelNumber() >= modelList.getStartedAtListLevelNumber()) &&
                listItem.canAddToList(modelList))
            {
                // WORDSNET-27415 - Raise priority of the last used list.
                modelLists.remove(modelList);
                msArrayList.add(modelLists, modelList);

                return modelList;
            }
        }

        List newList = mDocument.getLists().add(listTemplate);
        HtmlModelList newModelList = new HtmlModelList(newList, listItem.getListLevelId().getListLevelNumber());
        msArrayList.add(modelLists, newModelList);

        return newModelList;
    }

    private /*final*/ Document mDocument;

    /// <summary>
    /// Maps list nested level <see cref="HtmlListLevelId.ListNestedLevel"/> to list of model lists.
    /// </summary>
    private /*final*/ HashMap<Integer, ArrayList<HtmlModelList>> mModelLists;
}

