// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import com.aspose.HashSetGeneric;
import com.aspose.DictionaryExtension;
import java.util.ArrayList;
import java.util.HashMap;


/// <summary>
/// Generates shape identifiers. Should be used as part of the document validation before save.
/// We do not maintain shape identifiers while in the model, but we have to set them straight before save.
///
/// Updates <see cref="SaveInfo.Shapes"/> and <see cref="SaveInfo.LinkedShapeIds"/> collections.
/// </summary>
class ShapeIdGenerator
{
    ShapeIdGenerator(SaveInfo saveInfo)
    {
        mSaveInfo = saveInfo;

        // Visit picture bullet shapes as they need ids too.
        ListCollection lists = saveInfo.Document.getLists();
        for (int i = 0; i < lists.getPictureBulletCount(); i++)
        {
            Shape shape = lists.getPictureBullet(i);
            visitShape(shape, false);
        }
    }

    void endDocument()
    {
        int shapeId = ShapeBase.MAIN_DRAWING_PATRIARCH_SHAPE_ID + 1;

        if (mSaveInfo.Document.getBackgroundShape() != null)
            mSaveInfo.Document.getBackgroundShape().setId(shapeId++);

        for (ShapeBase shape : mBodyShapes)
            updateShapeIds(shape, shapeId++);

        // Now we know last id used for the shapes in the body, we can generate ids for shapes in the headers/footers.
        // Shape ids are in segments, find the beginning of a next segment.
        int headerDrawingPatriarchShapeId = MathUtil.roundUp(shapeId, ShapeBase.SHAPE_ID_CLUSTER_SIZE);
        shapeId = headerDrawingPatriarchShapeId + 1;

        for (ShapeBase shape : mHeaderShapes)
            updateShapeIds(shape, shapeId++);

        // WORDSNET-5295 Update footnote separator's shape id.
        // AM. I believe this collection should be small so it won't be expensive task.
        for (FootnoteSeparator separator : mSaveInfo.Document.getFootnoteSeparators())
        {
            // Update each shape in footnote separators.
            for (ShapeBase shape : (Iterable<ShapeBase>) separator.getChildNodes(NodeType.SHAPE, true))
                updateShapeIds(shape, shapeId++);
        }

        updateNextShapeIds();
        updateConnectors();
    }

    void visitShape(ShapeBase shape, boolean isInHeaderFooter)
    {
        if (isInHeaderFooter)
            msArrayList.add(mHeaderShapes, shape);
        else
            msArrayList.add(mBodyShapes, shape);

        // Collect all connectors.
        if (shape.getShapePr().get(ShapeAttr.CONNECTOR_RULE) != null)
            msArrayList.add(mConnectors, shape);

        // Verify OleObject Id is unique.
        if (shape.isOle())
            ensureUniqueOleId(shape);
    }

    /// <summary>
    /// Implements checks for VML or DML shapes to cover cases which can cause
    /// conversion errors and can be excluded on initial steps of the shape validation.
    /// </summary>
    /// <param name="shape">Shape which has to be validated.</param>
    static void validateFluently(ShapeBase shape)
    {
        Debug.assertTrue(shape != null);

        // AM. Word sometimes has TextboxNextShapeId attribute referred for shape itself and sometimes not.
        // In order to reduce new golds needs to be accepted and simplify code I set this attribute only if shape is actually linked.
        if (shape.getId() == shape.getTextboxNextShapeId())
            shape.removeShapeAttrInternal(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID);
    }

    /// <summary>
    /// Updates ShapeAttr.TextboxNextShapeId attribute.
    /// </summary>
    /// <remarks>
    /// Shape ids are changed upon saving so ids of chained shape should be updated too.
    /// </remarks>
    private void updateNextShapeIds()
    {
        HashSetGeneric<Integer> linkTarget = new HashSetGeneric<Integer>();
        for (ShapeBase shape : mAllShapes)
        {
            int oldNextShapeId = shape.getTextboxNextShapeId();
            // Remove the old linked shape id value so any broken links will be cleared.
            shape.removeShapeAttrInternal(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID);

            if (oldNextShapeId != 0)
            {
                ShapeBase nextShape = DictionaryExtension.getValueOrNull(mPreviousShapeIds, oldNextShapeId);

                // TestDefect815. TextboxNextShapeId contains Id of missed shape.
                if (nextShape != null)
                {
                    // TestJira9154. Shape in Header is linked to shape in Body.
                    if (linkTarget.contains(nextShape.getId()) || !haveSameDocumentPart(shape, nextShape))
                    {
                        WarningUtil.warnDataLoss(mSaveInfo.Document.getWarningCallback(), WarningSource.VALIDATOR, WarningStrings.INVALID_TEXTBOX_LINK, shape.getId());
                    }
                    else
                    {
                        shape.setTextboxNextShapeId(nextShape.getId());

                        // Store the linked shape ids so this info can be accessible by the writers.
                        mSaveInfo.LinkedShapeIds.add(shape.getId());
                        mSaveInfo.LinkedShapeIds.add(nextShape.getId());

                        linkTarget.add(nextShape.getId());
                    }
                }
            }
        }
    }

    private void updateConnectors()
    {
        for (ShapeBase connectorShape : mConnectors)
        {
            ConnectorRule connectorRule = (ConnectorRule)connectorShape.getShapePr().get(ShapeAttr.CONNECTOR_RULE);

            ShapeBase shapeA = DictionaryExtension.getValueOrNull(mPreviousShapeIds, connectorRule.ShapeAId);
            ShapeBase shapeB = DictionaryExtension.getValueOrNull(mPreviousShapeIds, connectorRule.ShapeBId);

            if ((shapeA == null) || (shapeB == null))
            {
                // If there is any problem remove connector rule.
                WarningUtil.warnDataLoss(mSaveInfo.Document.getWarningCallback(),
                    WarningSource.VALIDATOR, WarningStrings.INVALID_CONNECTOR_RULE, connectorShape.getId());
                connectorShape.getShapePr().remove(ShapeAttr.CONNECTOR_RULE);
            }
            else
            {
                // Update shape ids.
                connectorRule.ShapeAId = shapeA.getId();
                connectorRule.ShapeBId = shapeB.getId();

                // Collect connectors for writing.
                Story shapeBody = (Story)connectorShape.getAncestor(NodeType.BODY);

                if (shapeBody != null)
                    mSaveInfo.ConnectorsBody.add(connectorShape);
                else
                    mSaveInfo.ConnectorsHdr.add(connectorShape);
            }
        }
    }

    /// <summary>
    /// Returns true if shapes are both belong to body or both belong to header/footer.
    /// </summary>
    private static boolean haveSameDocumentPart(ShapeBase shape1, ShapeBase shape2)
    {
        boolean isBody1 = (shape1.getAncestor(NodeType.HEADER_FOOTER) == null);
        boolean isBody2 = (shape2.getAncestor(NodeType.HEADER_FOOTER) == null);

        return (isBody1 == isBody2);
    }

    /// <summary>
    /// Set new shape id, while preserving old value in table.
    /// </summary>
    private void updateShapeIds(ShapeBase shape, int id)
    {
        // AM. I read TextboxTxId for unit testing. Remove it before saving.
        shape.removeShapeAttrInternal(ShapeAttr.TEXTBOX_TXID);

        // WORDSNET-10647 Shapes both belongs to headers but NextShapeId equal to 1025.
        // Such value belongs to body shape in general and Word ignores such link.
        if (!DocumentBase.haveSameIdMap(shape.getId(), shape.getTextboxNextShapeId()))
            shape.removeShapeAttrInternal(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID);

        // Before renumbering shapes we can encounter shapes with duplicate id. This can happen for example during
        // shape cloning. In this case only the first shape will be stored so only the first shape will be linked.
        // Further shapes with duplicate ids will simply be assigned new ids and will not be linked.
        // WORDSNET-14369 We can ignore picture bullet shapes.
        if ((!mPreviousShapeIds.containsKey(shape.getId()) ||
                // WORDSNET-27492 Prefer textboxes to be able to resolve TextboxNextShapeId.
                (!mPreviousShapeIds.get(shape.getId()).isTextBox() && shape.isTextBox())) &&
            (shape.getParentNode() != null))
        {
            mPreviousShapeIds.put(shape.getId(), shape);
        }

        msArrayList.add(mAllShapes, shape);

        // Finally set the new shape id.
        shape.setId(id);

        // We store the map of id->shape in SaveInfo so this can be accessible for writers.
        mSaveInfo.Shapes.set(id, shape);
    }

    /// <summary>
    /// Ensures uniqueness of a shape embedded object id.
    /// </summary>
    private void ensureUniqueOleId(ShapeBase shape)
    {
        IEmbeddedObject embeddedObject = shape.getEmbeddedObject();
        if (embeddedObject == null)
            return;

        // AM. I think non unique id will not be occurred too often so algorithm as simple as possible.
        while (mObjectIds.contains(embeddedObject.getId()))
            embeddedObject.setId(embeddedObject.getId() + 1)/*Property++*/;

        mObjectIds.add(embeddedObject.getId());
    }

    /// <summary>
    /// Provides all the input and output data for this class.
    /// </summary>
    private /*final*/ SaveInfo mSaveInfo;
    /// <summary>
    /// Strictly speaking we do not need to cache these shapes as for headers/footers, but to make code consistent we cache them.
    /// This list also includes shapes for bullet pictures.
    /// </summary>
    private /*final*/ ArrayList<ShapeBase> mBodyShapes = new ArrayList<ShapeBase>();
    /// <summary>
    /// We have to cache shapes in the header until the end of the document before we can assign ids to them.
    /// </summary>
    private /*final*/ ArrayList<ShapeBase> mHeaderShapes = new ArrayList<ShapeBase>();
    /// <summary>
    /// Keeps old shape ids to be able to update id of linked shapes.
    /// </summary>
    private /*final*/ HashMap<Integer, ShapeBase> mPreviousShapeIds = new HashMap<Integer, ShapeBase>();
    /// <summary>
    /// All shapes of the document. In comparison with <see cref="mPreviousShapeIds"/>, this list contains all
    /// shapes including ones that have duplicate IDs.
    /// </summary>
    private ArrayList<ShapeBase> mAllShapes = new ArrayList<ShapeBase>();
    /// <summary>
    /// Collects embedded object ids. Used to ensure ids uniqueness.
    /// </summary>
    private /*final*/ HashSetGeneric<Integer> mObjectIds = new HashSetGeneric<Integer>();

    /// <summary>
    /// Collects connector shapes i.e shape which has connector rule attribute.
    /// </summary>
    private /*final*/ ArrayList<ShapeBase> mConnectors = new ArrayList<ShapeBase>();
}

