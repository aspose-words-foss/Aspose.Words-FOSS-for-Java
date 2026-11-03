// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.StringUtil;
import java.util.Iterator;
import java.util.ArrayList;



/// <summary>
/// A collection of <see cref="FormField"/> objects that represent all the form fields in a range.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-form-fields/">Working with Form Fields</a> documentation article.</para>
/// </summary>
/// <dev>Facade wrapper for a collection of form field objects.</dev>
public class FormFieldCollection implements Iterable<FormField>
{
    FormFieldCollection(Node parent)
    {
        mItems = parent.isComposite()
            ? ((CompositeNode)parent).getChildNodes(NodeType.FORM_FIELD, true)
            : EmptyNodeCollection.createEmpty();
    }

    FormFieldCollection(NodeRange nodeRange)
    {
        mItems = new NodeCollection(nodeRange.getDocument(), new FormFieldMatcher(nodeRange), true);
    }

    /// <summary>
    /// Returns the number of form fields in the collection.
    /// </summary>
    public int getCount() { return mItems.getCount(); }

    /// <summary>
    /// Returns a form field at the specified index.
    /// </summary>
    /// <include file='..\..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    /// <param name="index">An index into the collection.</param>
    public FormField get(int index) { return (FormField)mItems.get(index); }

    /// <summary>
    /// Returns a form field by bookmark name.
    /// </summary>
    /// <remarks>
    /// Returns <c>null</c> if the form field with the specified bookmark name cannot be found.
    /// </remarks>
    /// <param name="bookmarkName">Case-insensitive bookmark name.</param>
    public FormField get(String bookmarkName)
    {
        ArgumentUtil.checkHasChars(bookmarkName, "bookmarkName");

        for (int i = 0; i < getCount(); i++)
        {
            if (StringUtil.equalsIgnoreCase(this.get(i).getName(), bookmarkName))
                return this.get(i);
        }
        return null;
    }


    /// <summary>
    /// Removes a form field with the specified name.
    /// </summary>
    /// <remarks>
    /// If there is a bookmark associated with the form field, the bookmark is not removed.
    /// </remarks>
    /// <param name="formField">The case-insensitive name of the form field to remove.</param>
    public void remove(String formField) throws Exception
    {
        ArgumentUtil.checkHasChars(formField, "formField");
        removeInternal(this.get(formField));
    }

    /// <summary>
    /// Removes a form field at the specified index.
    /// </summary>
    /// <remarks>
    /// If there is a bookmark associated with the form field, the bookmark is not removed.
    /// </remarks>
    /// <param name="index">The zero-based index of the form field to remove.</param>
    public void removeAt(int index) throws Exception
    {
        removeInternal(this.get(index));
    }

    /// <summary>
    /// Removes all form fields from this collection and from the document.
    /// </summary>
    public void clear() throws Exception
    {
        // SPEED This is a proper way for removing.
        int remaining = getCount();
        while (remaining > 0)
        {
            removeAt(0);
            remaining--;
        }
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<FormField> iterator()
    {
        return mItems.getNodeEnumerator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private static void removeInternal(FormField formField) throws Exception
    {
        if (formField == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "formField");

        //Note I cannot call just Node.Remove because a form field consists of multiple nodes.
        formField.removeField();
    }

    private /*final*/ NodeCollection mItems;

    private static class FormFieldMatcher extends NodeMatcher
    {
        FormFieldMatcher(NodeRange range)
        {
            mNodeRange = range;
            updateFormFieldsCache();
        }

        /// <summary>
        /// Returns true if the specified node matches the appropriate FormField node.
        /// </summary>
        /*override*/ boolean isMatch(Node node)
        {
            if (mInitialChangeCount != getDocument().getTreeChangeCount())
                updateFormFieldsCache();

            if ((node.getNodeType() != NodeType.FORM_FIELD) || mRangeIsEmpty)
                return false;

            return mFormFields.contains(node);
        }

        private void updateFormFieldsCache()
        {
            mInitialChangeCount = getDocument().getTreeChangeCount();

            mRangeIsEmpty = mNodeRange.isEmpty() || mNodeRange.isVoid() ||
                            mNodeRange.getStart().getNode().isRemoved() ||
                            mNodeRange.getEnd().getNode().isRemoved();

            if (mRangeIsEmpty)
                return;

            mFormFields = NodeFinder.findNodes(mNodeRange, NodeType.FORM_FIELD);
        }

        /*override*/ boolean isSkipMarkupNodes() { return true; }

        private Document getDocument() { return mNodeRange.getDocument().fetchDocument(); }

        private boolean mRangeIsEmpty;
        private int mInitialChangeCount;
        private ArrayList<Node> mFormFields;
        private /*final*/ NodeRange mNodeRange;
    }
}


