// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/11/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Represents a collection of <see cref="IFieldUpdateListener"/> instances.
/// </summary>
class FieldUpdateListenerCollection
{
    /// <summary>
    /// Adds the specified field update listener to the collection.
    /// </summary>
    void add(IFieldUpdateListener listener)
    {
        if (isEmpty())
            mListeners = new ArrayList<IFieldUpdateListener>(); // Create on the first demand.

        msArrayList.add(mListeners, listener);
    }

    /// <summary>
    /// Removes the specified field update listener from the collection.
    /// </summary>
    void remove(IFieldUpdateListener listener)
    {
        if (isEmpty())
            return;

        mListeners.remove(listener);

        if (mListeners.size() == 0)
            mListeners = null;
    }

    /// <summary>
    /// Notifies all of the collected field update listeners about that the specified field
    /// is about to be updated.
    /// </summary>
    FieldUpdateListenerResponse notifyUpdating(Field field, Field parentField) throws Exception
    {
        if (isEmpty())
            return null;

        FieldUpdateListenerResponse listenerResponse = null;

        for (IFieldUpdateListener listener : mListeners)
        {
            listenerResponse = listener.notifyUpdating(field, parentField);

            switch (listenerResponse.getPreferredStageStrategy())
            {
                case FieldUpdatePreferredStageStrategy.MAIN_LOOP:
                    // Continue iterating.
                    break;
                case FieldUpdatePreferredStageStrategy.SKIP:
                    // Return immediately.
                    return listenerResponse;
                default:
                    throw new IllegalStateException();
            }

            field = listenerResponse.getField();
        }

        return listenerResponse;
    }

    /// <summary>
    /// Notifies all of the collected field update listeners about that the specified field
    /// has been updated.
    /// </summary>
    void notifyUpdated(FieldUpdateContext context, /*FieldUpdateStrategy*/int strategy) throws Exception
    {
        if (isEmpty())
            return;

        for (IFieldUpdateListener listener : mListeners)
            listener.notifyUpdated(context, strategy);
    }

    /// <summary>
    /// Notifies all of the collected field update listeners about that the specified field
    /// is updating.
    /// </summary>
    boolean notifyUpdate(FieldUpdateContext context) throws Exception
    {
        if (isEmpty())
            return false;

        for (IFieldUpdateListener listener : mListeners)
        {
            if (listener.update(context))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Gets a value indicating whether this collection does not contain any items.
    /// </summary>
    boolean isEmpty() { return (mListeners == null); }

    private ArrayList<IFieldUpdateListener> mListeners;
}

