// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/12/2012 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.IntToObjDictionary;
import com.aspose.SortedIntegerListGeneric;
import com.aspose.Debug;
import java.util.HashMap;
import com.aspose.DictionaryExtension;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msDictionary;


/// <summary>
/// Used to validate DrawingMLs (checks that there is no duplicated ids to avoid producing non-conformant documents).
/// </summary>
/// <remarks>
/// andrnosk: WORDSNET-7343 If multiple DrawingML objects within the same document 
/// share the same id attribute value, then the document shall be considered non-conformant. (ISO_IEC_29500-1_2011->20.4.2.5)
/// </remarks>
class DrawingMLIdValidator
{
    /*VisitorAction*/int visitDrawingML(ShapeBase drawingML)
    {
        collectNextTxbx(drawingML);
        msArrayList.add(mDrawindMls, drawingML);        
        
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Validate DrawingML ids to avoid duplication and references of the linked shapes.
    /// </summary>
    void validate()
    {          
        IntToObjDictionary<ShapeBase> shapes = new IntToObjDictionary<ShapeBase>();
        // Stores textboxes to recover links after VML to DML conversion.
        IntToObjDictionary<ShapeBase> brokenTxbx = new IntToObjDictionary<ShapeBase>();            

        for (ShapeBase dml : mDrawindMls)
        {
            // WORDSNET-17926 ShapeId might be missing.
            // Assign any value it will be validated below anyway.
            int prevId = dml.getShapePr().contains(ShapeAttr.SHAPE_ID)
                ? (/*int*/Integer)dml.getShapePr().get(ShapeAttr.SHAPE_ID)
                : 0;

            if (dml.isTopLevel())
            {                   
                int newUniqueId = getDrawingMLIdManager().addUniqueId(prevId);

                // andrnosk: WORDSNET-7398 MS Word 2010 crashes when id=0, 
                // this situation is possible just it test mode but we still need to avoid this.
                dml.getShapePr().set(ShapeAttr.SHAPE_ID, (newUniqueId == 0)
                    ? getDrawingMLIdManager().addUniqueId(newUniqueId)
                    : newUniqueId);
            }                

            if ((dml.getDmlNode().getNonVisualPr() != null) && (dml.getDmlNode().getNonVisualPr().getNvDrawingProperties() != null))
            {
                // WORDSNET-13577 wp:docPr->id and wps:cNvPr->id have to be unique within the current document.
                DmlNvDrawingProperties nvPr = dml.getDmlNode().getNonVisualPr().getNvDrawingProperties();
                
                if (dml.isTopLevel())
                {
                    // WORDSNET-19042 Set the image id to get the same results when save the same document.
                    nvPr.setId((/*int*/Integer)dml.getShapePr().get(ShapeAttr.SHAPE_ID));
                }
                else
                {
                    int newUniqueNvPrId = getDrawingMLIdManager().addUniqueId(nvPr.getId());

                    nvPr.setId((newUniqueNvPrId == 0)
                        ? getDrawingMLIdManager().addUniqueId(newUniqueNvPrId)
                        : newUniqueNvPrId);
                }
            }

            // It is necessary to update relations between shapes after changes in shape identifiers.
            updateNextTxbxId(prevId, dml.getId());

            // Collect textboxes with unassigned identifiers to recover chain on textbox validation.
            if (dml.hasTextbox() && dml.getTextboxNextShapeId() > 0 && dml.getTextboxId() == 0 && dml.getLinkedTextboxId() == 0)
                brokenTxbx.set(dml.getId(), dml);

            if (dml.getDmlNode().getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE)
                shapes.set(dml.getId(), dml);
        }

        validateTxbx(shapes, brokenTxbx);
    }

    /// <summary>
    /// Update identifiers for current shape (textbox).
    /// </summary>
    /// <param name="dml">Shape to update identifiers.</param>
    /// <param name="lnkItems">Linked items collection.</param>
    /// <param name="doc">Document which holds specified shape.</param>
    static void updateTxbxId(ShapeBase dml, SortedIntegerListGeneric<ShapeBase> lnkItems, DocumentBase doc)
    {
        Debug.assertTrue((dml != null) && (lnkItems != null) && (doc != null));

        // Update to unique value.     
        if (dml.getTextboxId() > 0)
            dml.setTextboxId(doc.getNextDmlTextBoxId());

        // Assign unique number from document level to avoid intersection of the values.      
        int currentId = doc.mapShapeToRange(doc.getNextShapeId(dml), lnkItems.getCount());
        int nextId = doc.getNextShapeId();

        dml.getShapePr().setAttr(ShapeAttr.SHAPE_ID, currentId);
        dml.getShapePr().setAttr(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID, nextId);

        // Set values in fallback shape.
        if (dml.getFallbackShape() != null)
        {
            dml.getFallbackShape().getShapePr().setAttr(ShapeAttr.SHAPE_ID, currentId);
            dml.getFallbackShape().getShapePr().setAttr(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID, nextId);
        }

        updateLinkedTextboxes(dml, lnkItems, doc);
    }

    /// <summary>
    /// Processes linked textboxes identifiers.
    /// </summary>
    /// <param name="dmlShapes">Collection of the drawingML textboxes."</param>
    /// <param name="dmlLinkedShapes">Dictionary of the linked textboxes stores by textbox identifier.</param>       
    static void processLinkedTxbxId(IntToObjDictionary<ShapeBase> dmlShapes, 
        HashMap<Integer, SortedIntegerListGeneric<ShapeBase>> dmlLinkedShapes)
    {
        Debug.assertTrue((dmlShapes != null) && (dmlLinkedShapes != null));

        for (ShapeBase dml : dmlShapes.getValues())
        {
            SortedIntegerListGeneric<ShapeBase> lnkItems = DictionaryExtension.getValueOrNull(dmlLinkedShapes, dml.getTextboxId());

            // Skip identifiers updating for not linked shapes.            
            if (lnkItems == null)
                continue;

            updateTxbxId(dml, lnkItems, dml.getDocument());
        }
    }

    /// <summary>
    /// Assigns identifiers to items into linked textboxes chain.
    /// </summary>       
    /// <param name="txbx">First textbox in the sequence of the textboxes.</param>
    /// <param name="lnkItems">Collection of linked textboxes of one chain.</param>
    /// <param name="doc">>Document which holds specified textbox.</param>
    private static void updateLinkedTextboxes(ShapeBase txbx, SortedIntegerListGeneric<ShapeBase> lnkItems, DocumentBase doc)
    {
        Debug.assertTrue((txbx != null) && (lnkItems != null) && (doc != null));

        int txbxId = txbx.getTextboxId();
        int nextId = (/*int*/Integer)txbx.getShapePr().get(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID);

        for (int i = 0; i < lnkItems.getCount(); i++)
        {
            Shape nextTextbox = (Shape)lnkItems.getByIndex(i);

            nextTextbox.setLinkedTextboxId(txbxId);
            nextTextbox.getShapePr().setAttr(ShapeAttr.SHAPE_ID, nextId);

            ShapeBase nextFallbackShape = nextTextbox.getFallbackShape();
            if (nextFallbackShape != null)
                nextFallbackShape.getShapePr().setAttr(ShapeAttr.SHAPE_ID, nextId);

            // The last linked textbox in chain does not have TextboxNextShapeId set.
            if (i < (lnkItems.getCount() - 1))
            {
                nextId = doc.getNextShapeId();
                nextTextbox.getShapePr().setAttr(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID, nextId);
                if (nextFallbackShape != null)
                    nextFallbackShape.getShapePr().setAttr(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID, nextId);
            }
        }
    }

    /// <summary>
    /// Collects textboxes which refer to another.
    /// </summary>
    /// <param name="dml">Shape to collect.</param>
    private void collectNextTxbx(ShapeBase dml)
    {
        Debug.assertTrue(dml != null);

        if (dml.getTextboxNextShapeId() == 0)
            return;

        int nextId = dml.getTextboxNextShapeId();

        if (!mNextTxbx.containsKey(nextId))
            mNextTxbx.put(nextId, new ArrayList<ShapeBase>());

        msArrayList.add(mNextTxbx.get(nextId), dml); 
    }

    /// <summary>
    /// Updates relations between textboxes after changes in shape identifiers.
    /// After processing in this method shapes still can hold links to textbox which does not exist.
    /// </summary>
    /// <param name="prevId">Previous identifier of the shape.</param>
    /// <param name="newNextId">New identifier of the shape.</param>
    private void updateNextTxbxId(int prevId, int newNextId)
    {
        if (!mNextTxbx.containsKey(prevId))
            return;

        ArrayList<ShapeBase> dmls = mNextTxbx.get(prevId);

        // Replace reference for first shape in the sequence.
        ShapeBase dml = dmls.get(0);
        dml.setTextboxNextShapeId(newNextId);

        // Another shapes have duplicate relationships.
        // This code for resilience purposes, on this step it is necessary to resolve
        // invalid relations between textboxes.
        for (int i = 1; i < dmls.size(); ++i)
            invalidateNextTxbx(dml);
       
        // Remove items with updated references.           
        msDictionary.remove(mNextTxbx, prevId);
    }   

    /// <summary>
    /// Validates textbox identifiers, linked textbox identifiers and links between them.
    /// </summary>
    /// <param name="shapes">Dictionary which contains textbox and linked textbox shapes.</param>
    /// <param name="brokenTxbx">Dictionary with textboxes which have to be recovered.</param>
    private void validateTxbx(IntToObjDictionary<ShapeBase> shapes, IntToObjDictionary<ShapeBase> brokenTxbx)
    {
        Debug.assertTrue((shapes != null) && (brokenTxbx != null));

        // Invalidate links to next textbox for shape which has wrong references to it.
        // This code for resilience purposes, on this step it is necessary to resolve
        // invalid relations between textboxes.
        for (ArrayList<ShapeBase> txbxes : (Iterable<ArrayList<ShapeBase>>) mNextTxbx.values())
            for (ShapeBase txbx : txbxes)
                invalidateNextTxbx(txbx);

        // Here dictionary does not hold valid references to items, so clear it.
        mNextTxbx.clear();

        // Key is a textbox identifier, value is a sorted collection of the linked textboxes.
        HashMap<Integer, SortedIntegerListGeneric<ShapeBase>> linkedChains = 
            new HashMap<Integer, SortedIntegerListGeneric<ShapeBase>>();            

        // Assign identifiers for textboxes and linked textboxes according to relations between shapes.
        // Textbox identifiers and linked textbox identifiers are not assigning during VML to DML conversion.
        for (ShapeBase txbx : brokenTxbx.getValues())
        {
            int seqNum = 1;
            ShapeBase nextTxbx = getNextTxbx(txbx, shapes);
            int txbxId = (nextTxbx != null) ? txbx.getDocument().getNextDmlTextBoxId() : 0;

            while (nextTxbx != null)
            {
                txbx.setTextboxId(txbxId);
                nextTxbx.setLinkedTextboxId(txbxId);
                nextTxbx.setLinkedTextboxSeq(seqNum++);

                if (!linkedChains.containsKey(txbx.getTextboxId()))
                    linkedChains.put(txbx.getTextboxId(), new SortedIntegerListGeneric<ShapeBase>());

                SortedIntegerListGeneric<ShapeBase> linkedTextboxes = linkedChains.get(txbx.getTextboxId());
                linkedTextboxes.set(nextTxbx.getLinkedTextboxSeq(), nextTxbx);

                nextTxbx = getNextTxbx(nextTxbx, shapes);
            }
        }

        processLinkedTxbxId(brokenTxbx, linkedChains);    
    }

    /// <summary>
    /// Returns next textbox for shape.
    /// </summary>
    /// <param name="dml">Shape with reference to next linked textbox.</param>
    /// <param name="shapes">Dictionary which contains textboxes and linked textboxes.</param>       
    /// <returns>Next textbox or "null" when reference to next textbox does not specified.</returns>
    private static ShapeBase getNextTxbx(ShapeBase dml, IntToObjDictionary<ShapeBase> shapes)
    {
        Debug.assertTrue((dml != null) && (shapes != null));

        return (dml.getTextboxNextShapeId() > 0) ? shapes.get(dml.getTextboxNextShapeId()) : null;
    }

    /// <summary>
    /// Handles all id-related tasks for DrawingMLs.
    /// </summary>
    private UniqueIdManager getDrawingMLIdManager()
    {
        if (mDrawingMLIdManager == null)
            mDrawingMLIdManager = new UniqueIdManager();

        return mDrawingMLIdManager;
    }

    /// <summary>
    /// Removes reference to next textbox and registers warning. 
    /// </summary>
    /// <param name="dml">Shape to remove link.</param>
    private static void invalidateNextTxbx(ShapeBase dml)
    {
        Debug.assertTrue(dml != null);

        dml.removeShapeAttrInternal(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID);
        WarningUtil.warn(dml.getDocument().getWarningCallback(), WarningType.MINOR_FORMATTING_LOSS,
                WarningSource.VALIDATOR, WarningStrings.INVALID_TEXTBOX_LINK, dml.getId());
    }

    /// <summary>
    /// Handles uniqueness of Ids across DrawingMLs in the document descendants. 
    /// This applies both to Document and GlossaryDocument, as they both can host DrawingMLs.
    /// </summary>
    private UniqueIdManager mDrawingMLIdManager;

    /// <summary>
    /// Container for DrawingMLs. Keys
    /// </summary>
    private /*final*/ ArrayList<ShapeBase> mDrawindMls = new ArrayList<ShapeBase>();

    /// <summary>
    /// Stores shapes with textboxes that is references by another shapes.
    /// Elements will be deleted from the dictionary on shape identifiers validation.
    /// </summary>
    private /*final*/ HashMap<Integer, ArrayList<ShapeBase>> mNextTxbx = new HashMap<Integer, ArrayList<ShapeBase>>();
}

