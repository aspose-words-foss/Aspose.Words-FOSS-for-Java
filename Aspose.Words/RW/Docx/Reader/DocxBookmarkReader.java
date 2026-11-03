// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/09/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DictionaryExtension;
import com.aspose.ms.System.Collections.msDictionary;


/// <summary>
/// Provides static method for parsing 'w:bookmarkStart' and 'w:bookmarkEnd' nodes.
/// These nodes can be met in all elements that are story containers, and also paragraph, 
/// table and row elements.
/// </summary>
/*static*/ class DocxBookmarkReader
{
	/* Simulation of static class by using private constructor */
	private DocxBookmarkReader()
	{}

    /// <summary>
    /// Reads 'w:bookmarkStart' node.
    /// </summary>
    static void readStart(NrxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        NrxAnnotation annotation = new NrxAnnotation(xmlReader);
        if (!annotation.hasId())
            return;

        // RK Changed this to set instead of add so bookmarks with duplicate IDs do not cause exceptions.
        reader.getIdToBookmarkName().put(annotation.getId(), annotation.getName());
        BookmarkStart bookmarkStart = new BookmarkStart(reader.getDocument(), annotation.getName());
        bookmarkStart.setDisplacedBy(annotation.getDisplacedBy());

        if (annotation.getColFirst() >= 0 && annotation.getColLast() >= 0)
        {
            bookmarkStart.setFirstColumn(annotation.getColFirst());
            bookmarkStart.setLastColumn(annotation.getColLast());
        }

        reader.addCrossStructureAnnotation(bookmarkStart);
    }

    /// <summary>
    /// Reads 'w:bookmarkEnd' node.
    /// </summary>
    static void readEnd(NrxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        NrxAnnotation annotation = new NrxAnnotation(xmlReader);
        if (!annotation.hasId())
            return;

        String name = DictionaryExtension.getValueOrNull(reader.getIdToBookmarkName(), annotation.getId());
        // If 'name is null then BookmarkStart node for this BookmarkEnd node is missing.
        if (name != null)
        {
            BookmarkEnd bookmarkEnd = new BookmarkEnd(reader.getDocument(), name);
            bookmarkEnd.setDisplacedBy(annotation.getDisplacedBy());
            reader.addCrossStructureAnnotation(bookmarkEnd);

            // WORDSNET-4063 RK There are two bookmarks with the same id in this file. 
            // MS Word reads it okay so should we. We now remove the id-to-name mapping once 
            // bookmark end was found, it will allow the second bookmark to be processed okay.
            msDictionary.remove(reader.getIdToBookmarkName(), annotation.getId());
        }
    }
}

