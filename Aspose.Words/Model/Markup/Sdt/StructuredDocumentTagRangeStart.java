// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/08/2019 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.awt.Color;
import java.util.Iterator;
import com.aspose.ms.System.msObject;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.HashSetGeneric;
import java.util.ArrayList;


/// <summary>
/// Represents a start of <b>ranged</b> structured document tag which accepts multi-sections content.
/// See also <see cref="StructuredDocumentTagRangeEnd"/>.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-content-control-sdt/">Structured Document Tags or Content Control</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Can be immediate child of <see cref="Body" /> node <b>only</b>.
/// </remarks>
/// <dev>Implements public facade for encapsulated <see cref="StructuredDocumentTag"/> node.</dev>
public class StructuredDocumentTagRangeStart extends Node implements Iterable<Node>, IStructuredDocumentTag
{
    /// <summary>
    /// Initializes a new instance of the <b>Structured document tag range start</b> class.
    /// </summary>
    /// <remarks>
    /// <para>The following types of SDT can be created:</para>
    /// <list type="bullet">
    /// <item><see cref="Markup.SdtType.Checkbox"/></item>
    /// <item><see cref="Markup.SdtType.DropDownList"/></item>
    /// <item><see cref="Markup.SdtType.ComboBox"/></item>
    /// <item><see cref="Markup.SdtType.Date"/></item>
    /// <item><see cref="Markup.SdtType.BuildingBlockGallery"/></item>
    /// <item><see cref="Markup.SdtType.Group"/></item>
    /// <item><see cref="Markup.SdtType.Picture"/></item>
    /// <item><see cref="Markup.SdtType.RichText"/></item>
    /// <item><see cref="Markup.SdtType.PlainText"/></item>
    /// </list>
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    /// <param name="type">Type of SDT node.</param>
    public StructuredDocumentTagRangeStart(DocumentBase doc, /*SdtType*/int type)
    {
    	this(doc, new StructuredDocumentTag(doc, type, MarkupLevel.BLOCK));
	
    }

    StructuredDocumentTagRangeStart(DocumentBase doc, StructuredDocumentTag sdt)
    {
    	super(doc);
	
        setInternalSdt(sdt);
    }

    /// <summary>
    /// Returns a live collection of child nodes that match the specified types.
    /// </summary>
    public NodeCollection getChildNodes(/*NodeType*/int nodeType, boolean isDeep)
    {
        return new NodeCollection(getDocument(), new StructuredDocumentTagRangeMatcher(nodeType, this, isDeep), true);
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        StructuredDocumentTagRangeStart lhs = (StructuredDocumentTagRangeStart)super.deepClone(isCloneChildren, cloningListener);
        lhs.setInternalSdt((StructuredDocumentTag)getInternalSdt().deepClone(isCloneChildren, cloningListener));

        // AM. When document is cloned we should try to preserve original Ids.
        // Otherwise range link between range start/end will be lost.
        // I don't like this, maybe it's better to handle link translation in Document.Clone()? Postpone for a while.
        lhs.getInternalSdt().setIdExplicitly(getInternalSdt().getId());

        return lhs;
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IStructuredDocumentTag.*/isMultiSection() { return true; }

    @ReservedForInternalUse @Deprecated
    public Node /*IStructuredDocumentTag.*/getNode() { return this; }

    /// <summary>
    /// Returns <see cref="NodeType.StructuredDocumentTagRangeStart"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START; }

    /// <summary>
    /// Gets all nodes between this range start node and the range end node.
    /// </summary>
    NodeCollection getChildNodes() { return getChildNodes(NodeType.ANY, false); }

    /// <summary>
    /// Gets the last child in the stdContent range.
    /// </summary>
    /// <remarks>
    /// If there is no last child node, a <c>null</c> is returned.
    /// </remarks>
    public Node getLastChild()
    {
        if (getRangeEnd() != null)
            return getRangeEnd().getPreviousSibling();

        return null;
    }

    /// <summary>
    /// Gets the level at which this structured document tag range start occurs in the document tree.
    /// </summary>
    public /*MarkupLevel*/int getLevel() { return MarkupLevel.BLOCK; }

    /// <summary>
    /// Gets type of this structured document tag.
    /// </summary>
    public /*SdtType*/int getSdtType() { return getInternalSdt().getControlProperties().getType(); }

    /// <summary>
    /// Gets or sets the color of the structured document tag.
    /// </summary>
    public Color getColor() { return getInternalSdt().getColor(); }
    public void setColor(Color value) { getInternalSdt().setColor(value); }

    /// <summary>
    /// <para>Specifies a unique read-only persistent numerical Id for this structured document tag.</para>
    /// </summary>
    /// <remarks>
    /// <para> Id attribute shall follow these rules:
    /// <list type="bullet">
    /// <item>The document shall retain structured document tag ids only if the whole document
    /// is cloned <see cref="Document.Clone()"/>.</item>
    /// <item>During <see cref="DocumentBase.ImportNode(Aspose.Words.Node,bool)"/>
    /// Id shall be retained if import does not cause conflicts with other structured document tag Ids in
    /// the target document.</item>
    /// <item>
    /// If multiple structured document tag nodes specify the same decimal number value for the Id attribute,
    /// then the first structured document tag in the document shall maintain this original Id,
    /// and all subsequent structured document tag nodes shall have new identifiers assigned to them when the document is loaded.
    /// </item>
    /// <item>During standalone structured document tag <see cref="StructuredDocumentTag.Clone"/> operation new unique ID will be
    /// generated for the cloned structured document tag node.</item>
    /// <item>
    /// If Id is not specified in the source document, then the structured document tag node shall have a new unique identifier assigned
    /// to it when the document is loaded.
    /// </item>
    /// </list>
    /// </para>
    /// </remarks>
    public int getId() { return getInternalSdt().getId(); }

    /// <summary>
    /// When set to <c>true</c>, this property will prohibit a user from deleting this structured document tag.
    /// </summary>
    public boolean getLockContentControl() { return getInternalSdt().getLockContentControl(); }
    public void setLockContentControl(boolean value) { getInternalSdt().setLockContentControl(value); }

    /// <summary>
    /// When set to <c>true</c>, this property will prohibit a user from editing the contents of this structured document tag.
    /// </summary>
    public boolean getLockContents() { return getInternalSdt().getLockContents(); }
    public void setLockContents(boolean value) { getInternalSdt().setLockContents(value); }

    /// <summary>
    /// <para>
    /// Specifies whether the content of this structured document tag shall be interpreted to contain
    /// placeholder text (as opposed to regular text contents within the structured document tag).
    /// </para>
    /// <para>
    /// if set to <c>true</c>, this state shall be resumed (showing placeholder text) upon opening this document.
    /// </para>
    /// </summary>
    public boolean isShowingPlaceholderText() { return getInternalSdt().isShowingPlaceholderText(); }
    public void isShowingPlaceholderText(boolean value) { getInternalSdt().isShowingPlaceholderText(value); }

    /// <summary>
    /// Gets the <see cref="BuildingBlock"/> containing placeholder text which should be displayed when
    /// this structured document tag run contents are empty, the associated mapped XML element is empty as specified
    /// via the <see cref="XmlMapping"/> element or the <see cref="IsShowingPlaceholderText"/> element is <c>true</c>.
    /// </summary>
    /// <remarks>Can be <c>null</c>, meaning that the placeholder is not applicable for this structured document tag.</remarks>
    public BuildingBlock getPlaceholder() { return getInternalSdt().getPlaceholder(); }

    /// <summary>
    /// <para>Gets or sets Name of the <see cref="BuildingBlock"/> containing placeholder text.</para>
    /// </summary>
    /// <exception cref="InvalidOperationException">Throw if BuildingBlock with this name <see cref="BuildingBlock.Name"/> is not present in <see cref="Document.GlossaryDocument"/>.</exception>
    public String getPlaceholderName() { return getInternalSdt().getPlaceholderName(); }
    public void setPlaceholderName(String value) { getInternalSdt().setPlaceholderName(value); }

    /// <summary>
    /// Specifies a tag associated with the current structured document tag node.
    /// Can not be <c>null</c>.
    /// </summary>
    /// <remarks>
    /// A tag is an arbitrary string which applications can associate with structured document
    /// tag in order to identify it without providing a visible friendly name.
    /// </remarks>
    public String getTag() { return getInternalSdt().getTag(); }
    public void setTag(String value) { getInternalSdt().setTag(value); }

    /// <summary>
    /// Specifies the friendly name associated with this structured document tag.
    /// Can not be <c>null</c>.
    /// </summary>
    public String getTitle() { return getInternalSdt().getTitle(); }
    public void setTitle(String value) { getInternalSdt().setTitle(value); }

    /// <summary>
    /// Gets a string that represents the XML contained within the node in the <see cref="SaveFormat.FlatOpc"/> format.
    /// </summary>
    public String getWordOpenXML() throws Exception
    {
        OpcDocumentFragmentWriter writer = new OpcDocumentFragmentWriter();
        return writer.saveToString(this);
    }

    /// <summary>
    /// Gets a string that represents the XML contained within the node in the <see cref="SaveFormat.FlatOpc"/> format.
    ///
    /// Unlike the <see cref="WordOpenXML"/> property, this method generates a stripped-down document that excludes any non-content-related parts.
    /// </summary>
    /// <dev>
    /// This is experimental API to understand uncertain needs of important customer.
    /// Later we could try rework it using Node.ToString() pattern.
    /// </dev>
    public String getWordOpenXMLMinimal() throws Exception
    {
        OpcDocumentFragmentWriter writer = new OpcDocumentFragmentWriter(true);
        return writer.saveToString(this);
    }

    /// <summary>
    /// Gets or sets the appearance of the structured document tag.
    /// </summary>
    public /*SdtAppearance*/int getAppearance() { return getInternalSdt().getAppearance(); }
    public void setAppearance(/*SdtAppearance*/int value)
    {
        getInternalSdt().setAppearance(value);

        SdtContentUpdater.updateNonBoundDataContent(getInternalSdt());
    }

    /// <summary>
    /// Gets an object that represents the mapping of this structured document tag range to XML data
    /// in a custom XML part of the current document.
    /// </summary>
    /// <remarks>
    /// You can use the <see cref="Markup.XmlMapping.SetMapping(CustomXmlPart,string,string)"/> method of this
    /// object to map a structured document tag range to XML data.
    /// </remarks>
    public XmlMapping getXmlMapping() { return getInternalSdt().getXmlMapping(); }

    /// <include file='..\..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return visitorActionToBool(visitor.visitStructuredDocumentTagRangeStart(this));
    }

    /// <summary>
    /// Specifies end of range if the <see cref="StructuredDocumentTag"/> is a ranged structured document tag.
    /// Otherwise returns <c>null</c>.
    /// </summary>
    public StructuredDocumentTagRangeEnd getRangeEnd()
    {
        if (getParentNode() == null)
            return null;

        // Resilience check. If the node is in the air, we take the last child of the parent.
        Node endRefNode = (isRemoved())
            ? getParentNode().getLastChild()
            : getDocument().getLastChild();

        // Range end node must be located after range start node.
        // If there is no range start node return null;
        return RangeNodeFinder.findRangeEnd(new NodeRange(this, false, endRefNode, true), getId());
    }

    /// <summary>
    /// Provides support for the for each style iteration over the child nodes of this node.
    /// </summary>
    public Iterator<Node> iterator()
    {
        return new StructuredDocumentTagRangeEnumerator(this);
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds the specified node to the end of the stdContent range.
    /// </summary>
    /// <param name="newChild">The node to add.</param>
    /// <returns>The node added.</returns>
    public Node appendChild(Node newChild)
    {
        Node refNode = (getLastChild() != null) ? getLastChild() : this;
        return refNode.insertNext(newChild);
    }

    /// <summary>
    /// Removes all the nodes between this range start node and the range end node.
    /// </summary>
    public void removeAllChildren()
    {
        // Do not start the removal process without making sure that we can totally complete it.
        Node rangeEnd = getRangeEnd();
        if (rangeEnd == null)
            return;

        Section startSection = (getParentNode() != null) ? ((Body)getParentNode()).getParentSection() : null;
        Section endSection = (rangeEnd.getParentNode() != null) ? ((Body)rangeEnd.getParentNode()).getParentSection() : null;

        // Do not throw here to better follow CompositeNode pattern (composite node does not need parent to remove its child nodes).
        if ((startSection == null) || (endSection == null))
            return;

        if (msObject.referenceEquals(startSection, endSection))
        {
            // Just clear content between range nodes if any exists.
            if (getNextSibling() != rangeEnd)
                removeChildRange(getNextSibling(), rangeEnd.getPreviousSibling());
        }
        else
        {
            // Clear first section content after range end.
            if (getNextSibling() != null)
                removeChildRange(getNextSibling(), startSection.getBody().getLastChild());

            // Clear end section content before range end.
            if (rangeEnd.getPreviousSibling() != null)
                removeChildRange(endSection.getBody().getFirstChild(), rangeEnd.getPreviousSibling());

            // Move remaining content from first section to end section.
            while (startSection.getBody().hasChildNodes())
                endSection.getBody().insertBefore(startSection.getBody().getFirstChild(), rangeEnd);

            // Remove sections between range nodes including starting section.
            removeChildRange(startSection, endSection.getPreviousSibling());
        }
    }

    /// <summary>
    /// Removes this range start and appropriate range end nodes of the structured document tag,
    /// but keeps its content inside the document tree.
    /// </summary>
    public void removeSelfOnly()
    {
        if (getRangeEnd() != null)
            getRangeEnd().remove();
        remove();
    }

    void setId(int id)
    {
        getInternalSdt().setId(id);
    }

    /// <summary>
    /// Accepts StructuredDocumentTagRangeStart and all its pseudo child nodes.
    /// </summary>
    boolean acceptAsCompositeNode(DocumentVisitor visitor) throws Exception
    {
        if (!acceptPseudoChildren(visitor))
            return false;

        return visitorActionToBool(visitor.visitStructuredDocumentTagRangeStart(this));
    }

    /// <summary>
    /// Encapsulated StructuredDocumentTag.
    /// </summary>
    /// <remarks>
    /// Customer not going to access this. Instead they should work with structured document tag facade provided by this class.
    /// </remarks>
    StructuredDocumentTag getInternalSdt()
    {
        // Ensure that encapsulated node belongs to the same document as we can get another document
        // after some manipulation, for example, node cloned and appended to another document.
        // We need to consider making full implementation later instead of SDT encapsulation.
        if(mInternalSdt != null)
            mInternalSdt.setDocument(getDocument());

        return mInternalSdt;
    }

    void setInternalSdt(StructuredDocumentTag value)
    {
        mInternalSdt = value;
    }

    /// <summary>
    /// Removes range between nodes including reference nodes itself.
    /// </summary>
    private static void removeChildRange(Node first, Node last)
    {
        Debug.assertTrue(msObject.referenceEquals(first.getParentNode(), last.getParentNode()));

        CompositeNode parent = first.getParentNode();

        int startIndex = parent.indexOf(first);
        int count = parent.indexOf(last) - startIndex + 1;

        for(int i = 0; i < count; i++)
            parent.getChildNodes(NodeType.ANY, false).removeAt(startIndex);
    }

    /// <summary>
    /// Accepts pseudo child nodes for StructuredDocumentTagRangeStart.
    /// </summary>
    private boolean acceptPseudoChildren(DocumentVisitor visitor) throws Exception
    {
        for (Node node : this)
        {
            // Skip the nested StructuredDocumentTag ranges to avoid double iteration of their children.
            if ((node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START) ||
                (node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END))
                continue;

            if (!node.accept(visitor))
                return false;
        }

        return true;
    }

    private StructuredDocumentTag mInternalSdt;

    /// <summary>
    /// Implements node matching for ranged SDT range.
    /// </summary>
    /// <dev>
    /// The implementation is straight and not very effective. Will try to improve if customer complained.
    /// </dev>
    private static class StructuredDocumentTagRangeMatcher extends NodeMatcher
    {
        StructuredDocumentTagRangeMatcher(/*NodeType*/int nodeType, StructuredDocumentTagRangeStart start, boolean isDeep)
        {
            mRangeStart = start;
            mNodeType = nodeType;
            mIsDeep = isDeep;
            mDoc = start.getDocument();

            invalidate();
        }

        /*override*/ boolean isMatch(Node node)
        {
            if ((mNodeType != NodeType.ANY) && (node.getNodeType() != mNodeType))
                return false;

            checkInvalidate();

            return mRangeNodes.contains(node);
        }

        /*override*/ boolean isSkipMarkupNodes() { return false; }

        private boolean getRangeNodeListLocked() { return mOpenedRangedBlocks.getCount() > 0; }

        private void checkInvalidate()
        {
            if (mInitialChangeCount != mDoc.getTreeChangeCount())
                invalidate();
        }

        private void openRangedBlock(Node rangedBlock)
        {
            mOpenedRangedBlocks.add(((StructuredDocumentTagRangeStart)rangedBlock).getId());
        }

        private void closeRangedBlock(Node rangedBlock)
        {
            mOpenedRangedBlocks.remove(((StructuredDocumentTagRangeEnd)rangedBlock).getId());
        }

        private void invalidate()
        {
            mInitialChangeCount = mDoc.getTreeChangeCount();

            mRangeNodes.clear();
            mOpenedRangedBlocks.clear();

            // Collect range nodes.
            Node rangeEnd = mRangeStart.getRangeEnd();
            Node curNode = mRangeStart;
            while (true)
            {
                curNode = curNode.nextPreOrder(mDoc);

                // Nothing to enumerate or reached end of range node.
                if ((curNode == null) || (curNode == rangeEnd))
                    break;

                // Do not enumerate Body or Section nodes.
                if ((curNode.getNodeType() == NodeType.BODY) || (curNode.getNodeType() == NodeType.SECTION))
                    continue;

                if (!mIsDeep && curNode.getParentNode().getNodeType() != NodeType.BODY)
                    continue;

                if (!getRangeNodeListLocked())
                    msArrayList.add(mRangeNodes, curNode);

                if (mIsDeep)
                    continue;

                // We count the nested ranged blocks here. Their content shall not be taken into account by the
                // matcher if the isDeep flag is false.
                if (curNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START)
                    openRangedBlock(curNode);
                else if (curNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END)
                    closeRangedBlock(curNode);
            }
        }

        private /*final*/ HashSetGeneric<Integer> mOpenedRangedBlocks = new HashSetGeneric<Integer>();
        private /*final*/ ArrayList<Node> mRangeNodes = new ArrayList<Node>();
        private /*final*/ StructuredDocumentTagRangeStart mRangeStart;
        private /*final*/ DocumentBase mDoc;
        private /*final*/ /*NodeType*/int mNodeType;
        private /*final*/ boolean mIsDeep;
        private int mInitialChangeCount;
    }

    /// <summary>
    /// Helper class to locate range start/end nodes for ranged structured document tags.
    /// </summary>
    private static class RangeNodeFinder extends NodeFinder
    {
        private RangeNodeFinder(NodeRange range, int id)
        {
        	super(range, NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END);
	
            mId = id;
        }

        static StructuredDocumentTagRangeEnd findRangeEnd(NodeRange range, int id)
        {
            ArrayList<Node> nodes = new RangeNodeFinder(range, id).find();

            // Do strict check: only ONE range end can be present.
            return (nodes.size() == 1) ? (StructuredDocumentTagRangeEnd)nodes.get(0) : null;
        }

        protected /*override*/ boolean onNodeFinding()
        {
            return ((StructuredDocumentTagRangeEnd)getCurrentNode()).getId() == mId;
        }

        private /*final*/ int mId;
    }

    /// <summary>
    /// Provides for-each iteration over StructuredDocumentTag range nodes.
    /// </summary>
    private final static class StructuredDocumentTagRangeEnumerator implements Iterator<Node>
    {
        StructuredDocumentTagRangeEnumerator(StructuredDocumentTagRangeStart start)
        {
            mRangeStart = start;
            mDoc = start.getDocument();

            reset();
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        public Node next() { return mCurNode; }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        public boolean hasNext()
        {
            if (mRangeStart.getRangeEnd() == null)
                return false;

            if (mCurNode == null)
                mCurNode = mRangeStart;

            while (true)
            {
                mCurNode = mCurNode.nextPreOrder(mDoc);

                if (mCurNode == null)
                    break;

                if ((mCurNode.getNodeType() != NodeType.BODY) &&
                    (mCurNode.getNodeType() != NodeType.SECTION) &&
                    (mCurNode.getParentNode().getNodeType() == NodeType.BODY))
                    break;
            }
            if (mCurNode == mRangeStart.getRangeEnd())
                mCurNode = null;

            return (mCurNode != null);
        }

        public void reset()
        {
            mCurNode = null;
        }

        private Node mCurNode;
        private /*final*/ StructuredDocumentTagRangeStart mRangeStart;
        private /*final*/ DocumentBase mDoc;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }
}

