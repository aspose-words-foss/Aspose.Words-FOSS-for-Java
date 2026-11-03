// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// An implementation of <see cref="INodeCopierListener"/> that contains a collection of listeners and applies all of them one by one.
/// </summary>
class CompositeNodeCopierListener implements INodeCopierListener
{
    CompositeNodeCopierListener()
    {
        mListeners = new ArrayList<INodeCopierListener>();
    }

    void add(INodeCopierListener listener)
    {
        msArrayList.add(mListeners, listener);
    }

    void remove(INodeCopierListener listener)
    {
        mListeners.remove(listener);
    }

    public void /*INodeCloningListener.*/notifyNodeCloned(Node source, Node clone)
    {
        for (INodeCopierListener listener : mListeners)
            listener.notifyNodeCloned(source, clone);
    }

    public void /*INodeCopierListener.*/notifyNodeRangeCopied(NodeRange sourceRange, NodeRange insertedRange) throws Exception
    {
        for (INodeCopierListener listener : mListeners)
            listener.notifyNodeRangeCopied(sourceRange, insertedRange);
    }

    private /*final*/ ArrayList<INodeCopierListener> mListeners;
}
