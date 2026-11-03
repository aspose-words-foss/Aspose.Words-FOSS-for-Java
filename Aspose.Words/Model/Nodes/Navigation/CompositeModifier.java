// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// An implementation of <see cref="INodeModifier"/> that contains a collection of modifiers and applies
/// all of them one by one.
/// </summary>
class CompositeModifier implements INodeModifier
{
    CompositeModifier(/*params*/ INodeModifier... modifiers)
    {
        mModifiers = new ArrayList<INodeModifier>(modifiers.length);
        for (INodeModifier modifier : modifiers)
            addModifier(modifier);
    }

    void addModifier(INodeModifier modifier)
    {
        if (modifier != null)
            msArrayList.add(mModifiers, modifier);
    }

    public Node /*INodeModifier.*/modify(Node referenceNode, Node nodeToModify, boolean modifyChildren, INodeCloningListener cloningListener) throws Exception
    {
        for (INodeModifier modifier : mModifiers)
        {
            if (nodeToModify != null)
                nodeToModify = modifier.modify(referenceNode, nodeToModify, modifyChildren, cloningListener);
        }

        return nodeToModify;
    }

    private /*final*/ ArrayList<INodeModifier> mModifiers;
}

