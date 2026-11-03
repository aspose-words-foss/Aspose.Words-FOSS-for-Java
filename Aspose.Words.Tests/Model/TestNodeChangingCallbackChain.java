// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2013 by Ivan Lyagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests NodeChangingCallbackChain class functionality and relative DocumentBase class functionality.
/// </summary>
@Test
public class TestNodeChangingCallbackChain
{
    /// <summary>
    /// UC1. A user callback is set, invoked and removed.
    /// </summary>
    @Test
    public void testUC1() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        TestNodeChangingCallback callback = new TestNodeChangingCallback();

        // Set a user callback. Ensure it is fired.
        doc.setNodeChangingCallback(callback);
        msAssert.areEqual(callback, doc.getNodeChangingCallback());

        // Insert a node to the document. This should trigger the callback.
        doc.appendChild(new Section(doc));

        // Remove the user callback when it is not needed anymore. Ensure it is fired.
        doc.setNodeChangingCallback(null);
        msAssert.isNull(doc.getNodeChangingCallback());

        // Check the callback's reaction.
        msAssert.areEqual(1, callback.getInsertedCount());
    }

    /// <summary>
    /// UC2. An internal callback is set, invoked and removed.
    /// </summary>
    @Test
    public void testUC2() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        TestNodeChangingCallback callback = new TestNodeChangingCallback();

        // Set an internal callback.
        doc.addInternalNodeChangingCallback(callback);

        // Insert a node to the document. This should trigger the callback.
        doc.appendChild(new Section(doc));

        // Remove the internal callback when it is not needed anymore.
        doc.removeInternalNodeChangingCallback(callback);

        // Check the callback's reaction.
        msAssert.areEqual(1, callback.getInsertedCount());
    }

    /// <summary>
    /// Tests how DocumentBase.RemoveInternalNodeChangingCallback works.
    /// </summary>
    @Test
    public void testRemoveInternal() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        TestNodeChangingCallback callback = new TestNodeChangingCallback();

        // Nothing has been added. Removal should not fire.
        msAssert.isFalse(doc.removeInternalNodeChangingCallback(callback));

        doc.addInternalNodeChangingCallback(callback);

        // Removal should fire as the callback has been added.
        msAssert.isTrue(doc.removeInternalNodeChangingCallback(callback));

        // Removal should not fire as the callback has been removed.
        msAssert.isFalse(doc.removeInternalNodeChangingCallback(callback));
    }

    /// <summary>
    /// Tests how NodeChangingCallbackChain.IsEmpty works.
    /// </summary>
    @Test
    public void testIsEmpty()
    {
        NodeChangingCallbackChain chain = new NodeChangingCallbackChain();
        msAssert.isTrue(chain.isEmpty());

        INodeChangingCallback callback = new TestNodeChangingCallback();
        chain.setUserCallback(callback);
        msAssert.isFalse(chain.isEmpty());

        chain.setUserCallback(null);
        msAssert.isTrue(chain.isEmpty());

        chain.addInternalCallback(callback);
        msAssert.isFalse(chain.isEmpty());

        chain.removeInternalCallback(callback);
        msAssert.isTrue(chain.isEmpty());
    }

    /// <summary>
    /// Tests a complex use case with multiple internal callbacks and multiple values for a user callback.
    /// Ensures that a chain of invocation is not broken if its item is removed inside the corresponding callback.
    /// Ensures that all of the callbacks are fired properly, i.e. removed callbacks stop firing at the point
    /// of their removal.
    /// </summary>
    @Test
    public void testComplex() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);

        TestNodeChangingCallback callback1 = new TestNodeChangingCallback();
        TestNodeChangingCallback callback2 = new TestNodeChangingCallback(doc);
        TestNodeChangingCallback callback3 = new TestNodeChangingCallback();
        TestNodeChangingCallback callback4 = new TestNodeChangingCallback();
        TestNodeChangingCallback callback5 = new TestNodeChangingCallback();
        TestNodeChangingCallback callback6 = new TestNodeChangingCallback(doc);

        // Add couple of internal callbacks.
        doc.addInternalNodeChangingCallback(callback1);
        doc.addInternalNodeChangingCallback(callback2);

        // Set the user callback. Ensure that it has fired.
        doc.setNodeChangingCallback(callback5);
        msAssert.areEqual(callback5, doc.getNodeChangingCallback());

        // Add more internal callbacks.
        doc.addInternalNodeChangingCallback(callback3);
        doc.addInternalNodeChangingCallback(callback4);

        // Replace the user callback. Ensure that it has fired.
        doc.setNodeChangingCallback(callback6);
        msAssert.areEqual(callback6, doc.getNodeChangingCallback());

        // Remove some internal callbacks (from the beginning and from the end). They should never fire.
        doc.removeInternalNodeChangingCallback(callback1);
        doc.removeInternalNodeChangingCallback(callback4);

        // Insert two nodes and remove one of them.
        doc.appendChild(new Section(doc));
        doc.appendChild(new Section(doc)).remove();

        // A user callback should be removed while reacting.
        msAssert.isNull(doc.getNodeChangingCallback());

        // This callback should not fire as it was removed before any node manipulations.
        msAssert.areEqual(0, callback1.getInsertedCount());
        msAssert.areEqual(0, callback1.getRemovedCount());

        // This callback should fire once and be removed while reacting.
        msAssert.areEqual(1, callback2.getInsertedCount());
        msAssert.areEqual(0, callback2.getRemovedCount());

        // This callback should fire all of the time.
        msAssert.areEqual(2, callback3.getInsertedCount());
        msAssert.areEqual(1, callback3.getRemovedCount());

        // This callback should not fire as it was removed before any node manipulations.
        msAssert.areEqual(0, callback4.getInsertedCount());
        msAssert.areEqual(0, callback4.getRemovedCount());

        // This callback should not fire as it was removed before any node manipulations.
        msAssert.areEqual(0, callback5.getInsertedCount());
        msAssert.areEqual(0, callback5.getRemovedCount());

        // This callback should fire once and be removed while reacting.
        msAssert.areEqual(1, callback6.getInsertedCount());
        msAssert.areEqual(0, callback6.getRemovedCount());
    }


}
