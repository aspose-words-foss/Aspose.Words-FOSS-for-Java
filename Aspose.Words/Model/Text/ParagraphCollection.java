// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Provides typed access to a collection of <see cref="Paragraph"/> nodes.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-paragraphs/">Working with Paragraphs</a> documentation article.</para>
/// </summary>
public class ParagraphCollection extends NodeCollection<Paragraph>
{
    ParagraphCollection(CompositeNode parent)
    {
    	super(parent, NodeType.PARAGRAPH, false);
	
    }

    /// <summary>
    /// Retrieves a <see cref="Paragraph"/> at the given index.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    /// <param name="index">An index into the collection.</param>
    public /*new*/ Paragraph get(int index) { return (Paragraph)super.get(index); }

    /// <summary>
    /// Copies all paragraphs from the collection to a new array of paragraphs.
    /// </summary>
    /// <returns>An array of paragraphs.</returns>
    public /*new*/ Paragraph[] toArray()
    {
        return msArrayList.toArray(this.<Paragraph>toList(), new Paragraph[0]);
    }
}

