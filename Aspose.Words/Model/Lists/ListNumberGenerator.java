// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/11/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToObjDictionary;


/// <summary>
/// <para>
/// Represents a repository for <see cref="ListNumberState"/> instances used to generate sequential list numbers
/// for different document lists.
/// </para>
/// <para>
/// <see cref="ListNumberGenerator"/> must not be used on its own, so use <see cref="ListLabelUpdater"/>
/// to update list labels for paragraphs in a document.
/// </para>
/// </summary>
class ListNumberGenerator
{
    /// <summary>
    /// <para>
    /// Gets the current list number state for the given list.
    /// </para>
    /// <para>
    /// Creates and initializes the numbers of a list if paragraphs with this list id were not visited before.
    /// (Use <see cref="ListLabelUpdater"/> to visit paragraphs in correct order).
    /// </para>
    /// </summary>
    ListNumberState getCurrentListNumberState(List list)
    {
        ListNumberState state = mListNumberStates.get(list.getListDefId());
        if (state == null)
        {
            // A new list has started, create and initialize the numbers of the list.
            state = new ListNumberState(list);
            mListNumberStates.set(list.getListDefId(), state);
        }

        return state;
    }

    /// <summary>
    /// <para>
    /// Gets the next list number state for the given list and the given level.
    /// </para>
    /// <para>
    /// Creates and initializes the numbers of a list if paragraphs with this list id were not visited before.
    /// (Use <see cref="ListLabelUpdater"/> to visit paragraphs in correct order).
    /// </para>
    /// <para>
    /// Increments number on appropriate level if the numbering for this list is already under way.
    /// </para>
    /// </summary>
    ListNumberState getNextListNumberState(List list, int listLevel)
    {
        ListNumberState state = getCurrentListNumberState(list);

        // The numbering for this list is already under way, just select the next item.
        state.nextItem(list, listLevel);
        return state;
    }

    /// <summary>
    /// Restarts numbering from a specific list level.
    /// </summary>
    /// <remarks>
    /// AM. This is support for ListDef.RestartAtEachSection poorly documented flag.
    /// </remarks>
    void restart(int listId, int level)
    {
        for (ListNumberState state : mListNumberStates.getValues())
            if(state.getListId() == listId)
                if(state.getListDef().isRestartAtEachSection())
                    state.init(level);
    }

    /// <summary>
    /// Key is a list definition id. Value is <see cref="ListNumberState"/>.
    /// </summary>
    private /*final*/ IntToObjDictionary<ListNumberState> mListNumberStates = new IntToObjDictionary<ListNumberState>();
}

