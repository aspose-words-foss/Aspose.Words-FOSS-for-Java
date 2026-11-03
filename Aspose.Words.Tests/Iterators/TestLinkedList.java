// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2013 by Ivan Pereshein
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestLinkedList
{
    @Test
    public void testInsertFirstNode()
    {
        // Expected list:
        // A(0)
        LinkedList list = new LinkedList();
        msAssert.isTrue(list.isEmpty());

        ILinkedListNode nodeA = new FakeLinkedListNode("A");

        list.insert(nodeA);
        list.moveObject(nodeA);

        msAssert.areEqual(0, nodeA.getIndex());
        msAssert.areEqual(0, nodeA.getSecondaryIndex());
        msAssert.areEqual(list.getGetHead_Test(), nodeA);
        msAssert.areEqual(list.getGetTail_Test(), nodeA);
        msAssert.areEqual(list.getCurrent(), nodeA);
    }

    /// <summary>
    /// Creates list: A[0|0], B[1|0], C[2|0], D[3|0].
    /// </summary>
    private static LinkedList createABCDList()
    {
        LinkedList list = new LinkedList();

        ILinkedListNode nodeA = new FakeLinkedListNode("A");

        list.insert(nodeA);
        list.moveObject(nodeA);

        ILinkedListNode nodeB = new FakeLinkedListNode("B");
        list.append(nodeB);
        list.moveObject(nodeB);

        ILinkedListNode nodeC = new FakeLinkedListNode("C");
        list.append(nodeC);
        list.moveObject(nodeC);

        ILinkedListNode nodeD = new FakeLinkedListNode("D");
        list.append(nodeD);
        list.moveObject(nodeD);

        return list;
    }

    @Test
    public void testCreateSimpleList()
    {
        // A[0|0], B[1|0], C[2|0], D[3|0]
        LinkedList list = createABCDList();

        ILinkedListNode nodeA = list.getGetHead_Test();
        ILinkedListNode nodeB = nodeA.getNextNode();
        ILinkedListNode nodeC = nodeB.getNextNode();
        ILinkedListNode nodeD = nodeC.getNextNode();

        msAssert.areEqual(nodeA.toString(), "A");
        msAssert.areEqual(nodeB.toString(), "B");
        msAssert.areEqual(nodeC.toString(), "C");
        msAssert.areEqual(nodeD.toString(), "D");

        msAssert.areEqual(list.getGetHead_Test().toString(), "A");
        msAssert.areEqual(list.getGetTail_Test().toString(), "D");

        msAssert.isNull(nodeA.getPrevNode());
        msAssert.areEqual(nodeB, nodeA.getNextNode());
        msAssert.areEqual(nodeA, nodeB.getPrevNode());
        msAssert.areEqual(nodeC, nodeB.getNextNode());
        msAssert.areEqual(nodeB, nodeC.getPrevNode());
        msAssert.areEqual(nodeD, nodeC.getNextNode());
        msAssert.areEqual(nodeC, nodeD.getPrevNode());
        msAssert.isNull(nodeD.getNextNode());

        msAssert.areEqual(0, nodeA.getIndex());
        msAssert.areEqual(0, nodeA.getSecondaryIndex());
        msAssert.areEqual(1, nodeB.getIndex());
        msAssert.areEqual(0, nodeB.getSecondaryIndex());
        msAssert.areEqual(2, nodeC.getIndex());
        msAssert.areEqual(0, nodeC.getSecondaryIndex());
        msAssert.areEqual(3, nodeD.getIndex());
        msAssert.areEqual(0, nodeD.getSecondaryIndex());
    }

    @Test
    public void testInsertIntoList()
    {
        // A[0|0], B[1|0], Ba[1|1], Bb[1|2], C[2|0], D[3|0]

        LinkedList list = createABCDList();

        ILinkedListNode nodeB = list.getNode_Test("B");

        // Append new node (Ba) after (B)
        list.moveObject(nodeB);
        list.append(new FakeLinkedListNode("Ba"));

        ILinkedListNode nodeBa = list.getNode_Test("Ba");
        ILinkedListNode nodeC = list.getNode_Test("C");

        msAssert.areEqual(nodeBa, nodeB.getNextNode());
        msAssert.areEqual(nodeB, nodeBa.getPrevNode());
        msAssert.areEqual(nodeC, nodeBa.getNextNode());
        msAssert.areEqual(nodeBa, nodeC.getPrevNode());

        msAssert.areEqual(1, nodeB.getIndex());
        msAssert.areEqual(0, nodeB.getSecondaryIndex());

        msAssert.areEqual(1, nodeBa.getIndex());
        msAssert.areEqual(1000000000, nodeBa.getSecondaryIndex());

        // Insert new node before (C)
        list.moveObject(nodeC);
        
        list.insert(new FakeLinkedListNode("Bb"));

        ILinkedListNode nodeBb = list.getNode_Test("Bb");

        msAssert.areEqual(nodeBb, nodeBa.getNextNode());
        msAssert.areEqual(nodeBa, nodeBb.getPrevNode());
        msAssert.areEqual(nodeC, nodeBb.getNextNode());
        msAssert.areEqual(nodeBb, nodeC.getPrevNode());
        msAssert.areEqual(1, nodeBb.getIndex());
        msAssert.areEqual(2000000000, nodeBb.getSecondaryIndex());
    }

    @Test
    public void testInsertBeforeHead()
    {
        // Aa[-1|0] A[0|0], B[1|0], Ba[1|1], Bb[1|2], C[2|0], D[3|0]
        LinkedList list = createABCDList();
        ILinkedListNode nodeA = list.getNode_Test("A");
        list.moveObject(nodeA);

        ILinkedListNode nodeAa = new FakeLinkedListNode("Aa");
        list.insert(nodeAa);

        msAssert.areEqual(nodeAa, list.getGetHead_Test());
        msAssert.isNull(nodeAa.getPrevNode());
        msAssert.areEqual(nodeA, nodeAa.getNextNode());
        msAssert.areEqual(nodeAa, nodeA.getPrevNode());

        msAssert.areEqual(-1, nodeAa.getIndex());
        msAssert.areEqual(0, nodeAa.getSecondaryIndex());
    }

    @Test
    public void testRemoveHeadAndTail()
    {
        LinkedList list = createABCDList();
        ILinkedListNode nodeA = list.getNode_Test("A");
        list.moveObject(nodeA);
        // Remove head.
        list.removeCurrentNode();
        ILinkedListNode nodeB = list.getNode_Test("B");
        msAssert.areEqual(nodeB, list.getGetHead_Test());
        msAssert.areEqual(nodeB, list.getCurrent());
        msAssert.isNull(nodeB.getPrevNode());

        list.moveObject(list.getNode_Test("D"));
        // Remove tail.
        list.removeCurrentNode();
        ILinkedListNode nodeC = list.getNode_Test("C");
        msAssert.areEqual(nodeC, list.getGetTail_Test());
        msAssert.isNull(nodeC.getNextNode());
        msAssert.isTrue(list.isAfter());

    }

    @Test
    public void testRemoveNode()
    {
        LinkedList list = createABCDList();

        ILinkedListNode nodeA = list.getNode_Test("A");
        ILinkedListNode nodeB = list.getNode_Test("B");
        ILinkedListNode nodeC = list.getNode_Test("C");

        list.moveObject(nodeB);
        list.removeCurrentNode();

        msAssert.areEqual(nodeC, list.getCurrent());
        msAssert.areEqual(nodeC, nodeA.getNextNode());
        msAssert.areEqual(nodeA, nodeC.getPrevNode());
    }

    @Test
    public void testReplaceNode()
    {
        LinkedList list = createABCDList();

        // A[0|0], B[1|0], C[2|0], D[3|0] => A[0|0], C[2|0], D[3|0]
        ILinkedListNode nodeA = list.getNode_Test("A");
        ILinkedListNode nodeB = list.getNode_Test("B");
        ILinkedListNode nodeC = list.getNode_Test("C");

        list.moveObject(nodeB);
        list.removeCurrentNode();

        // A[0|0], C[2|0], D[3|0]=> A[0|0], B_New[1|0], C[2|0], D[3|0]
        list.moveObject(nodeA);

        ILinkedListNode nodeBNew = new FakeLinkedListNode("B_New");
        list.append(nodeBNew);

        msAssert.areEqual(1, nodeBNew.getIndex());
        msAssert.areEqual(0, nodeBNew.getSecondaryIndex());

        // A[0|0], B_New[1|0], C[2|0], D[3|0] => A[0|0], B_New[1|0], D[3|0]
        list.moveObject(nodeC);
        list.removeCurrentNode();

        // A[0|0], B_New[1|0], D[3|0] => A[0|0], B_New[1|0], C_New[2|0], D[3|0]
        ILinkedListNode nodeCNew = new FakeLinkedListNode("C_New");
        list.insert(nodeCNew);
        msAssert.areEqual(2, nodeCNew.getIndex());
        msAssert.areEqual(0, nodeCNew.getSecondaryIndex());

    }

    @Test
    public void testCloneList()
    {
        LinkedList list = createABCDList();

        ILinkedListNode nodeB = list.getNode_Test("B");
        ILinkedListNode nodeC = list.getNode_Test("C");

        list.moveObject(nodeB);

        LinkedList clonedList = list.deepClone();
        
        msAssert.areEqual(list.getCurrent(), clonedList.getCurrent());

        clonedList.removeCurrentNode();

        msAssert.areEqual(nodeC, list.getCurrent());
    }

    @Test (expectedExceptions = IllegalStateException.class)
    public void testPreventDuplicateInsertion()
    {
        LinkedList list = createABCDList();

        ILinkedListNode nodeA = list.getNode_Test("A");
        ILinkedListNode nodeC = list.getNode_Test("C");

        list.moveObject(nodeA);
        list.append(nodeC);
    }
}

