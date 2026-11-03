// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.HashMap;
import com.aspose.DictionaryExtension;
import com.aspose.Debug;
import java.util.Map;


/// <summary>
/// Implements <see cref="INodeCopierListener"/> in the way to append fake results of FWR-fields before their starts
/// while copying nodes to the result of a reference field.
/// </summary>
class FieldFakeResultAppender implements INodeCopierListener
{
    FieldFakeResultAppender(Field refField)
    {
    	this(refField, null);
	
    }

    FieldFakeResultAppender(Field refField, IFieldFakeResultNodeModifier nodeModifier)
    {
        mRefField = refField;
        mNodeModifier = nodeModifier;
    }

    public void /*INodeCloningListener.*/notifyNodeCloned(Node source, Node clone)
    {
        // Process field starts only.
        if (source.getNodeType() != NodeType.FIELD_START)
            return;

        // Do not process removed fields.
        FieldStart sourceFieldStart = (FieldStart)source;
        if (sourceFieldStart.isDeleteRevision())
            return;

        switch (sourceFieldStart.getFieldType())
        {
            case FieldType.FIELD_FORM_DROP_DOWN:
            {
                // Do not process FORMDROPDOWN fields inside TOCs' results.
                if (mRefField.getType() != FieldType.FIELD_TOC)
                    collectClone(clone);
                break;
            }
            default:
            {
                if (FieldNumUtil.isFieldNum(sourceFieldStart.getFieldType()))
                    collectCloneAndSource(sourceFieldStart, (FieldStart)clone);
                break;
            }
        }
    }

    /// <summary>
    /// Remembers the given cloned node for a further processing.
    /// </summary>
    private void collectClone(Node clone)
    {
        if (!hasClones())
        {
            // Create on the first demand.
            mClones = new ArrayList<Node>();
        }

        msArrayList.add(mClones, clone);
    }

    /// <summary>
    /// Remembers the given cloned node and its source node for a further processing.
    /// </summary>
    private void collectCloneAndSource(FieldStart source, FieldStart clone)
    {
        FieldStart rootSource = null;
        if (!hasClonesToSourcesMap())
        {
            // Create on the first demand.
            mClonesToSourcesMap = new HashMap<FieldStart, FieldStart>();
        }
        else
        {
            // We may deal with a clone of a tracked clone provided by INodeModifier implementation for example.
            // So let's check if this is the case and use a root source (i.e. a source of the source) if it is.
            rootSource = DictionaryExtension.getValueOrNull(mClonesToSourcesMap, source);
        }

        mClonesToSourcesMap.put(clone, (rootSource != null) ? rootSource : source);
    }

    public void /*INodeCopierListener.*/notifyNodeRangeCopied(NodeRange sourceRange, NodeRange insertedRange) throws Exception
    {
        appendFakeResultsForClones();
        appendFakeResultsForClonesBySources();
    }

    /// <summary>
    /// Appends fake results for collected cloned nodes.
    /// </summary>
    private void appendFakeResultsForClones() throws Exception
    {
        if (!hasClones())
            return;

        for (Node node : mClones)
        {
            FieldStart clone = (FieldStart)node;
            // Ensure that the tracked clone has been added to a document as its addition could be rejected
            // by INodeModifier implementation. Skip the clone in this case.
            if (isNodeDetachedFromDocument(clone))
                continue;

            // At the moment only FORMDROPDOWN fields are processed.
            // Convert the check below to a switch statement if you are about to support other field types here.
            Debug.assertTrue(clone.getFieldType() == FieldType.FIELD_FORM_DROP_DOWN);
            appendFieldFormDropDownFakeResult(clone);
        }
    }

    /// <summary>
    /// Appends the fake result for a single FORMDROPDOWN field with the given field start.
    /// </summary>
    private static void appendFieldFormDropDownFakeResult(FieldStart clone) throws Exception
    {
        // Get the form field. Do nothing, if it is missing.
        FormField formField = clone.getFormField();
        if (formField == null)
            return;

        DocumentBuilder builder = new DocumentBuilder(clone.fetchDocument());
        builder.moveTo(clone);

        // Write the form field's result. It seems like MS Word uses LTR embedding here all the time.
        FieldTextHelper.writeTextBidiAware(builder, formField.getResult(), false);
    }

    /// <summary>
    /// Appends fake results for collected cloned nodes based on their source nodes.
    /// </summary>
    private void appendFakeResultsForClonesBySources() throws Exception
    {
        if (!hasClonesToSourcesMap())
            return;

        for (Map.Entry<FieldStart, FieldStart> entry : mClonesToSourcesMap.entrySet())
        {
            // Ensure that the tracked clone has been added to a document as its addition could be rejected
            // by INodeModifier implementation. Skip the clone in this case.
            FieldStart clone = entry.getKey();
            if (isNodeDetachedFromDocument(clone))
                continue;

            FieldStart source = entry.getValue();

            // At the moment only LISTNUM, AUTONUM, AUTONUMOUT and AUTONUMLGL fields are supported.
            // Convert the check below to a switch statement if you are about to support other field types here.
            Debug.assertTrue(FieldNumUtil.isFieldNum(clone.getFieldType()));
            appendFieldNumFakeResult(source, clone);
        }
    }

    /// <summary>
    /// Appends the fake result for a single LISTNUM, AUTONUM, AUTONUMOUT or AUTONUMLGL field with the given field start.
    /// </summary>
    private void appendFieldNumFakeResult(FieldStart source, FieldStart clone) throws Exception
    {
        // Ensure that the source field start and the cloned one belong to the same document.
        Debug.assertTrue(source.fetchDocument() == clone.fetchDocument());

        ArrayList<Run> runs = FieldNumUtil.getFakeResultList(source, mRefField);
        for (Run run : runs)
        {
            if (mNodeModifier != null)
                mNodeModifier.modify(run);

            clone.insertPrevious(run);
        }
    }

    /// <summary>
    /// Returns a value indicating whether the specified node belongs to a document.
    /// </summary>
    private static boolean isNodeDetachedFromDocument(Node node)
    {
        return node.isRemoved();
    }

    private boolean hasClones() { return (mClones != null); }

    private boolean hasClonesToSourcesMap() { return (mClonesToSourcesMap != null); }

    private /*final*/ Field mRefField;
    private /*final*/ IFieldFakeResultNodeModifier mNodeModifier;
    private HashMap<FieldStart, FieldStart> mClonesToSourcesMap;
    private ArrayList<Node> mClones;
}

