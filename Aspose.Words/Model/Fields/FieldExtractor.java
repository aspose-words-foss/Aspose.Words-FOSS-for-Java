// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/01/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import java.util.Map;
import com.aspose.StackExtension;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.ms.System.msArray;
import com.aspose.Debug;


/// <summary>
/// Extracts field nodes from a model into complete field objects.
///
/// When processing nested field and extracting into an array, returns the innermost fields first!
/// This was probably initially a defect, but then we had customers who relied on this behaviour an
/// therefore it is kept for backward compatibility.
///
/// When extracting from a part of a document tree you can get fields that start inside
/// this part or end inside this part, but are not completely contained in it. Such fields
/// are not included in the result.
///
/// </summary>
abstract class FieldExtractor extends DocumentVisitor
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="fieldTypes">Types of fields to extract.</param>
    protected FieldExtractor(/*params*/ /*FieldType*/int... fieldTypes)
    {
        mFieldTypes = fieldTypes;
        mExtractAllFields = (fieldTypes == null) || (fieldTypes.length == 0);
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    protected FieldExtractor()
    {
    	this(null);
	
    }

    /// <summary>
    /// A static method to simplify invocation.
    /// </summary>
    static ArrayList<Field> extractToCollection(Node node)
    {
        return extractToCollection(node, true);
    }

    /// <summary>
    /// A static method to simplify invocation.
    /// </summary>
    static ArrayList<Field> extractToCollection(NodeRange range) throws Exception
    {
        return extractToCollection(range, true, null);
    }

    /// <summary>
    /// A static method to simplify invocation.
    /// </summary>
    static ArrayList<Field> extractToCollection(Node node, boolean isDeep)
    {
        return extractToCollection(node, isDeep, null);
    }

    /// <summary>
    /// A static method to simplify invocation.
    /// </summary>
    static ArrayList<Field> extractToCollection(Node node, boolean isDeep, /*params*/ /*FieldType*/int... fieldTypes)
    {
        FieldExtractorToCollection extractor = new FieldExtractorToCollection(isDeep, fieldTypes);
        extractor.extract(node);
        return extractor.getFields();
    }

    /// <summary>
    /// A static method to simplify invocation.
    /// </summary>
    static ArrayList<Field> extractToCollection(NodeRange range, boolean isDeep, /*params*/ /*FieldType*/int... fieldTypes) throws Exception
    {
        FieldExtractorToCollection extractor = new FieldExtractorToCollection(isDeep, fieldTypes);
        extractor.extract(range);
        return extractor.getFields();
    }

    /// <summary>
    /// A static method to simplify invocation.
    /// </summary>
    static ArrayList<Field> extractToCollection(ArrayList<Node> nodes, boolean isDeep)
    {
        return extractToCollection(nodes, isDeep, null);
    }

    /// <summary>
    /// A static method to simplify invocation.
    /// </summary>
    static ArrayList<Field> extractToCollection(
        ArrayList<Node> nodes,
        boolean isDeep,
        /*params*/ /*FieldType*/int... fieldTypes)
    {
        FieldExtractorToCollection extractor = new FieldExtractorToCollection(isDeep, fieldTypes);
        extractor.extract(nodes);
        return extractor.getFields();
    }

    /// <summary>
    /// A static method to simplify invocation.
    /// Key is <see cref="FieldStart"/> and value is <see cref="Field"/>.
    /// </summary>
    static Map<Node, Field> extractToHashtable(Node node)
    {
        FieldExtractorToHashtable extractor = new FieldExtractorToHashtable();
        extractor.extract(node);
        return extractor.getFields();
    }

    /// <summary>
    /// Extracts fields from the node and all of its children recursively.
    /// </summary>
    void extract(Node node)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	node.accept(this);
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Extracts fields from the listed nodes and all of their children recursively.
    /// </summary>
    /// <param name="nodes"></param>
    void extract(ArrayList<Node> nodes)
    {
        for (Node node : nodes)
            extract(node);
    }

    /// <summary>
    /// Extracts fields from the nodes of the range.
    /// </summary>
    void extract(NodeRange range) throws Exception
    {
        NodeRangeFieldExtractHelper helper = new NodeRangeFieldExtractHelper(range, this);
        try /*JAVA: was using*/
    	{
            helper.extract();
    	}
        finally { if (helper != null) helper.dispose(); }
    }

    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldStart) throws Exception
    {
        if (fieldStart.isVisitorAcceptable(this))
            mFieldCharsStack.push(fieldStart);

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldSeparator(FieldSeparator fieldSeparator) throws Exception
    {
        if (fieldSeparator.isVisitorAcceptable(this))
            mFieldCharsStack.push(fieldSeparator);

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldEnd) throws Exception
    {
        if (!fieldEnd.isVisitorAcceptable(this))
            return VisitorAction.CONTINUE;

        // WORDSNET-23717 FieldSeparator can be removed from the model. Don't throw.
        FieldSeparator fieldSeparator = (FieldSeparator)StackExtension.popIfInstanceOf(mFieldCharsStack, FieldSeparator.class);
        if (fieldSeparator != null && fieldSeparator.isDeleteRevision())
            fieldSeparator = null;

        // WORDSNET-23717 FieldStart can be removed from the model. Don't throw.
        FieldStart fieldStart = (FieldStart)StackExtension.popIfInstanceOf(mFieldCharsStack, FieldStart.class);
        if (fieldStart == null)
        {
            // Ignore the orphan field end.
            return VisitorAction.CONTINUE;
        }

        // Note, that we still push to stack and pop from stack field chars which corresponding fields
        // are not to be extracted, to make IsInField property work properly.
        if (!isExtractNeeded(fieldStart, fieldSeparator, fieldEnd))
            return VisitorAction.CONTINUE;

        // RK This code results in innermost fields being added to the returned array
        // before the outermost fields and therefore further processing of the field array
        // will process the innermost fields first.
        // Do not change this! Customers already rely on this behaviour.
        mCurrentFieldStart = fieldStart;
        mCurrentFieldSeparator = fieldSeparator;
        mCurrentFieldEnd = fieldEnd;

        onFieldExtracted();

        mCurrentFieldStart = null;
        mCurrentFieldSeparator = null;
        mCurrentFieldEnd = null;
        mCurrentField = null;

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitHeaderFooterStart(HeaderFooter headerFooter)
    {
        // WORDSNET-11428 Fields within header/footer should be not extracted as child of body field, when it occupied several sections.
        mLastFieldStart = mCurrentFieldStart;
        mLastFieldSeparator = mCurrentFieldSeparator;
        mLastFieldEnd = mCurrentFieldEnd;
        mLastField = mCurrentField;
        mLastFieldCharsStack = mFieldCharsStack;

        mCurrentFieldStart = null;
        mCurrentFieldSeparator = null;
        mCurrentFieldEnd = null;
        mCurrentField = null;
        mFieldCharsStack = new msStack<FieldChar>();

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitHeaderFooterEnd(HeaderFooter headerFooter)
    {
        mCurrentFieldStart = mLastFieldStart;
        mCurrentFieldSeparator = mLastFieldSeparator;
        mCurrentFieldEnd = mLastFieldEnd;
        mCurrentField = mLastField;
        mFieldCharsStack = mLastFieldCharsStack;

        return VisitorAction.CONTINUE;
    }

    private boolean isExtractNeeded(FieldStart fieldStart, FieldSeparator fieldSeparator, FieldEnd fieldEnd)
    {
        // WORDSNET-12253 Ignore fields which contain deleted chars.
        if (fieldStart.isDeleteRevision() || fieldEnd.isDeleteRevision())
            return false;

        // Ignore fields which occupy multiple stories.
        if (!NodeUtil.areNodesInSameStory(fieldStart, fieldEnd))
            return false;

        if ((fieldSeparator != null) && !NodeUtil.areNodesInSameStory(fieldStart, fieldSeparator))
            return false;

        return (mExtractAllFields || (msArray.indexOf((mFieldTypes), (fieldStart.getFieldType()), 0, (mFieldTypes).length) != -1));
    }

    protected abstract void onFieldExtracted() throws Exception;

    /// <summary>
    /// A lazily initialized <see cref="Field"/> instance representing the current extracted field.
    /// </summary>
    protected Field getCurrentField()
    {
        Debug.assertTrue(mCurrentFieldStart != null);

        // Do not create an instance of a field until it is really needed. In most cases we deal with the topmost
        // fields and hence there is no need to create nested fields' instances. Creating of a field instance can be
        // quite expensive, because some of the fields parse their field codes while creating.
        if (mCurrentField == null)
        {
            mCurrentField = FieldFactory.createField(mCurrentFieldStart, mCurrentFieldSeparator, mCurrentFieldEnd);
            mCurrentField = mCurrentField;
        }
        return mCurrentField;
    }

    /// <summary>
    /// Gets the current extracted field's type without creating of a <see cref="Field"/> instance. This property
    /// should be used to perform checks (if any) before accessing <see cref="CurrentField"/> not to create an unneeded
    /// <see cref="Field"/> instance.
    /// </summary>
    protected /*FieldType*/int getCurrentFieldType()
    {
        Debug.assertTrue(mCurrentFieldStart != null);

        return mCurrentFieldStart.getFieldType();
    }

    protected boolean isInField() { return (mFieldCharsStack.getCount() > 0); }


    /// <summary>
    /// Helps to extract fields from a node range by traversing it.
    /// </summary>
    private static class NodeRangeFieldExtractHelper extends NodeTraverser
    {
        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="range"></param>
        /// <param name="fieldExtractor"></param>
        NodeRangeFieldExtractHelper(NodeRange range, FieldExtractor fieldExtractor)
        {
        	super(range);
	
            mFieldExtractor = fieldExtractor;
        }

        /// <summary>
        /// Extracts fields using the specified field extractor.
        /// </summary>
        void extract() throws Exception
        {
            traverse();
        }

        protected /*override*/ void onNonCompositeNode()
        {
            mFieldExtractor.extract(getCurrentNode());
        }

        protected /*override*/ void onMiddleNodeAncestor()
        {
            mFieldExtractor.extract(getCurrentNode());
        }

        private /*final*/ FieldExtractor mFieldExtractor;
    }

    /// <summary>
    /// Field begin and separator characters are pushed to this stack when processing nested fields.
    /// </summary>
    private msStack<FieldChar> mFieldCharsStack = new msStack<FieldChar>();

    private /*final*/ /*FieldType*/int[] mFieldTypes;
    private /*final*/ boolean mExtractAllFields;

    private FieldStart mCurrentFieldStart;
    private FieldSeparator mCurrentFieldSeparator;
    private FieldEnd mCurrentFieldEnd;
    private Field mCurrentField;

    private msStack<FieldChar> mLastFieldCharsStack = new msStack<FieldChar>();
    private FieldStart mLastFieldStart;
    private FieldSeparator mLastFieldSeparator;
    private FieldEnd mLastFieldEnd;
    private Field mLastField;
}

