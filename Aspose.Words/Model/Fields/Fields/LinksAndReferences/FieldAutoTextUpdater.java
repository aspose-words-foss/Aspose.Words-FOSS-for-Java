// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/05/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;
import com.aspose.StringUtil;


/// <summary>
/// Updates the <see cref="FieldAutoText"/> and <see cref="FieldGlossary"/> fields.
/// </summary>
class FieldAutoTextUpdater
{
    private FieldAutoTextUpdater(Field field, IFieldAutoTextCode fieldCode)
    {
        mField = field;
        mFieldCode = fieldCode;
    }

    static FieldUpdateAction update(Field field) throws Exception
    {
        FieldAutoTextUpdater updater = new FieldAutoTextUpdater(field, (IFieldAutoTextCode)field);
        return updater.update();
    }

    private FieldUpdateAction update() throws Exception
    {
        if (!StringUtil.hasChars(mFieldCode.getEntryName()))
            return new FieldUpdateActionInsertErrorMessage(mField, "Error! No AutoText entry specified.");

        BuildingBlock buildingBlock = mField.getUpdater().getAutoTextEntryExtractor().extract(mFieldCode.getEntryName());
        if (buildingBlock == null)
            return null;

        IDisposable iDisposableResource = mField.getUpdateContext().removeOldResultSafe();
        try /*JAVA: was using*/
        {
            NodeRange result = FieldUtil.buildFieldResultNodeRange(buildingBlock.getFirstSection(), buildingBlock.getLastSection());
            ExternalDocumentModifier modifier = new ExternalDocumentModifier(result.getDocument(), mField.fetchDocument(), ImportFormatMode.USE_DESTINATION_STYLES);
            NodeCopier.copy(result, mField.getEnd(), modifier, null,
                NodeCopierOptions.USE_SOURCE_START_ANCESTOR_PR | NodeCopierOptions.CLONE_NODE);
            copyFirstParagraphAttrs(result, modifier);
        }
        finally { if (iDisposableResource != null) iDisposableResource.dispose(); }

        return new FieldUpdateActionFormatResult(mField);
    }

    private void copyFirstParagraphAttrs(NodeRange result, INodeModifier modifier) throws Exception
    {
        if (result.isInSameAncestor(NodeType.PARAGRAPH))
            return;

        Paragraph sourcePara = (Paragraph)result.getStart().getNode().getAncestor(NodeType.PARAGRAPH);
        if (sourcePara != null)
        {
            sourcePara = (Paragraph)modifier.modify(sourcePara, sourcePara, false, null);

            Paragraph resultPara = mField.getStart().getParentParagraph();
            resultPara.setParaPr(AttrCollectionExtension.deepClone(sourcePara.getParaPr()));
            resultPara.setParagraphBreakRunPr(AttrCollectionExtension.deepClone(sourcePara.getParagraphBreakRunPr()));
        }
    }

    private /*final*/ Field mField;
    private /*final*/ IFieldAutoTextCode mFieldCode;
}

