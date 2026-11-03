// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/05/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides static method for parsing 'w:permStart' and 'w:permEnd' nodes.
/// These nodes can be met in all elements that are story containers, and also paragraph, 
/// table and row elements.
/// </summary>
/*static*/ class NrxEditableRangesReader
{
	/* Simulation of static class by using private constructor */
	private NrxEditableRangesReader()
	{}

    /// <summary>
    /// Reads 'w:permStart' node.
    /// </summary>
    static void readEditableRangeStart(NrxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        NrxAnnotation annotation = new NrxAnnotation(xmlReader);
        if (!annotation.hasId())
            return;

        EditableRangeStart editableRangeStart = new EditableRangeStart(
            reader.getDocument(), annotation.getId(), annotation.getSingleUser(), annotation.getEditorGroup());
        editableRangeStart.setDisplacedBy(annotation.getDisplacedBy());

        if (annotation.getColFirst() >= 0 && annotation.getColLast() >= 0)
        {
            editableRangeStart.setFirstColumn(annotation.getColFirst());
            editableRangeStart.setLastColumn(annotation.getColLast());
        }

        reader.addCrossStructureAnnotation(editableRangeStart);
    }

    /// <summary>
    /// Reads 'w:permEnd' node.
    /// </summary>
    static void readEditableRangeEnd(NrxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        NrxAnnotation annotation = new NrxAnnotation(xmlReader);
        if (!annotation.hasId())
            return;

        EditableRangeEnd editableRangeEnd = new EditableRangeEnd(reader.getDocument(), annotation.getId());
        editableRangeEnd.setDisplacedBy(annotation.getDisplacedBy());

        reader.addCrossStructureAnnotation(editableRangeEnd);
    }
}

