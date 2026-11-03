// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import com.aspose.Debug;
import java.util.Iterator;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// A collection of <see cref="Field"/> objects that represents the fields in the specified range.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>An instance of this collection iterates fields which start fall within the specified range.</p>
/// <p>The <see cref="FieldCollection"/> collection does not own the fields it contains, rather, is just a selection of fields.</p>
/// <p>The <see cref="FieldCollection"/> collection is "live", i.e. changes to the children of the node object
/// that it was created from are immediately reflected in the fields returned by the <see cref="FieldCollection"/>
/// properties and methods.</p>
/// </remarks>
public class FieldCollection implements Iterable<Field>
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="parent"></param>
    FieldCollection(Node parent)
    {
        mFieldStarts = parent.isComposite()
            ? ((CompositeNode)parent).getChildNodes(NodeType.FIELD_START, true)
            : EmptyNodeCollection.createEmpty();
    }

    FieldCollection(NodeRange nodeRange) throws Exception
    {
        mFieldStarts = new NodeCollection(nodeRange.getDocument(), new FieldMatcher(nodeRange), true);
    }

    /// <summary>
    /// Returns the number of the fields in the collection.
    /// </summary>
    public int getCount() { return mFieldStarts.getCount(); }

    /// <summary>
    /// Returns a field at the specified index.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    /// <param name="index">An index into the collection.</param>
    public Field get(int index)
    {
        FieldStart fieldStart = (FieldStart)mFieldStarts.get(index);
        return (fieldStart != null) ? fieldStart.getField() : null;
    }

    /// <summary>
    /// Removes the specified field from this collection and from the document.
    /// </summary>
    /// <param name="field">A field to remove.</param>
    public void remove(Field field) throws Exception
    {
        if (field == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "field");

        field.remove();
    }

    /// <summary>
    /// Removes a field at the specified index from this collection and from the document.
    /// </summary>
    /// <param name="index">An index into the collection.</param>
    public void removeAt(int index) throws Exception
    {
        FieldStart fieldStart = (FieldStart)mFieldStarts.get(index);
        if (fieldStart == null)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");

        remove(fieldStart);
    }

    /// <summary>
    /// Removes the whole field by the specified field start.
    /// </summary>
    /// <param name="fieldStart"></param>
    private static void remove(FieldStart fieldStart) throws Exception
    {
        Debug.assertTrue(fieldStart != null);

        FieldEnd fieldEnd = FieldBundle.getFieldBundle(fieldStart).getEnd();
        NodeRemover.remove(fieldStart, fieldEnd);
    }

    /// <summary>
    /// Removes all fields of this collection from the document and from this collection itself.
    /// </summary>
    public void clear() throws Exception
    {
        // Fields can be nested to each other so we can not rely on their count like we do for BookmarkCollection.
        while (true) // Break inside.
        {
            FieldStart fieldStart = (FieldStart)mFieldStarts.get(0);
            if (fieldStart == null)
                break;

            remove(fieldStart);
        }
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<Field> iterator()
    {
        return new FieldEnumerator(mFieldStarts);
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private final static class FieldEnumerator implements Iterator<Field>
    {
        FieldEnumerator(NodeCollection fieldStarts)
        {
            mFieldStartsEnumerator = fieldStarts.iterator();
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        
        public Field next()
        {
        	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
        	try
        	{
            	FieldStart fieldStart = (FieldStart)mFieldStartsEnumerator.next();
            	return fieldStart.getField();
        	}
        	catch (Exception e)
        	{
        		throw new RuntimeException(e);
        	}
        }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        public boolean hasNext()
        {
        	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
        	try
        	{
            	return mFieldStartsEnumerator.hasNext();
        	}
        	catch (Exception e)
        	{
        		throw new RuntimeException(e);
        	}
        }

        private /*final*/ Iterator<Node> mFieldStartsEnumerator;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }

    /// <summary>
    /// This is a live collection of field start nodes.
    /// </summary>
    private /*final*/ NodeCollection mFieldStarts;

    private static class FieldMatcher extends NodeMatcher
    {
        FieldMatcher(NodeRange range) throws Exception
        {
            mNodeRange = range;
            updateFieldsCache();
        }

        /// <summary>
        /// Returns true if the specified node matches the appropriate FieldStart node.
        /// </summary>
        /*override*/ boolean isMatch(Node node) throws Exception
        {
            if (mInitialChangeCount != getDocument().getTreeChangeCount())
                updateFieldsCache();

            if (mRangeIsEmpty || (node.getNodeType() != NodeType.FIELD_START))
                return false;

            return mFieldNodes.contains(node);
        }

        private void updateFieldsCache() throws Exception
        {
            mInitialChangeCount = getDocument().getTreeChangeCount();

            mFieldNodes.clear();

            mRangeIsEmpty = mNodeRange.isEmpty() || mNodeRange.isVoid() ||
                            mNodeRange.getStart().getNode().isRemoved() ||
                            mNodeRange.getEnd().getNode().isRemoved();

            if (mRangeIsEmpty)
                return;

            ArrayList<Field> fields = FieldExtractor.extractToCollection(mNodeRange);
            for (Field field : fields)
                msArrayList.add(mFieldNodes, field.getStart());
        }

        /*override*/ boolean isSkipMarkupNodes() { return true; }

        private Document getDocument() { return mNodeRange.getDocument().fetchDocument(); }

        private boolean mRangeIsEmpty;
        private int mInitialChangeCount;
        private /*final*/ ArrayList<Node> mFieldNodes = new ArrayList<Node>();
        private /*final*/ NodeRange mNodeRange;
    }
}

