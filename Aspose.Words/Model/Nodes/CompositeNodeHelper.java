// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/11/2023 by Evgeny Vasilev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/*static*/ class CompositeNodeHelper
{	/* Simulation of static class by using private constructor */	private CompositeNodeHelper()	{}

    /// <summary>
    /// DocumentBase inherits from CompositeNode, so translated C++ code does not include the DocumentBase header
    /// file in the CompositeNode header file. Because of this, it is not possible to call functions members of an
    /// instance of the DocumentBase class in the template method of the CompositeNode class. The following methods
    /// have been added to solve this problem.
    /// </summary>
    static NodeChangingArgs documentBaseInternalEvent(DocumentBase doc, Node newChild, Node oldParent, Node newParent, /*NodeChangingAction*/int action)
    {
        return doc.internalEvent(newChild, oldParent, newParent, action);
    }

    static void documentBaseBeforeEvent(DocumentBase doc, NodeChangingArgs args)
    {
        if (args != null)
            doc.beforeEvent(args);
    }

    static void documentBaseAfterEvent(DocumentBase doc, NodeChangingArgs args)
    {
        if (args != null)
            doc.afterEvent(args);
    }

    static boolean documentBaseIsTrackRevisionsEnabled(DocumentBase doc)
    {
        return doc.isTrackRevisionsEnabled();
    }

    static void documentBaseCompareException(DocumentBase thisDoc, DocumentBase childDoc)
    {
        if (childDoc != thisDoc)
            throw new IllegalArgumentException("The newChild was created from a different document than the one that created this node.");
    }

}

