// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/04/2021 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Implements conversions of EQ fields to OfficeMath nodes for export to flow-format HTML.
/// </summary>
class EQFieldConverter
{
    EQFieldConverter(DocumentValidator validator, SaveInfo saveInfo)
    {
        mSaveInfo = saveInfo;
        mValidator = validator;
    }

    /// <summary>
    /// Converts EQ fields into form that is appropriate for current save format.
    /// </summary>
    void convert(Field field) throws Exception
    {
        if (field.getStart().getFieldType() != FieldType.FIELD_EQUATION)
            return;

        switch (mSaveInfo.getSaveFormat())
        {
            case SaveFormat.HTML:
            case SaveFormat.MHTML:
            case SaveFormat.EPUB:
            case SaveFormat.AZW_3:
            case SaveFormat.MOBI:
                insertTempNode(getFakeResultNode(field), field.getEnd());
                break;
            default:
                // Do nothing.
                break;
        }
    }

    /// <summary>
    /// Reverts changes performed by this converter.
    /// </summary>
    void revert()
    {
        for (Node node : mTempNodes)
            node.remove();
    }

    private static Node getFakeResultNode(Field field) throws Exception
    {
        // Fake result of an EQ field is NodeRange of a single node (normally, OfficeMath).
        NodeRange fakeResult = field.getFakeResult();
        return fakeResult.getStart().getNode();
    }

    /// <summary>
    /// Inserts the specified node after the reference node. Remembers the inserted node for futher reverting.
    /// </summary>
    private void insertTempNode(Node node, Node refNode) throws Exception
    {
        // WORDSNET-22406 In case an EQ field has no content we just skip it. We don't insert fake empty run, because
        // it will be removed from the model by the document validator and this will lead to an exception later when we'll
        // try to revert changes and remove the fake run.
        Run nodeAsRun = ms.as(node, Run.class);
        if ((nodeAsRun != null) && ("".equals(nodeAsRun.getText())))
        {
            return;
        }

        refNode.insertNext(node);
        msArrayList.add(mTempNodes, node);
        node.accept(mValidator);
    }

    private /*final*/ SaveInfo mSaveInfo;
    private /*final*/ DocumentValidator mValidator;

    /// <summary>
    /// List of fake result nodes which are inserted temporarily and should be removed after the document is saved.
    /// </summary>
    private /*final*/ ArrayList<Node> mTempNodes = new ArrayList<Node>();
}

