// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.Map;
import java.util.HashMap;



/// <summary>
/// Extracts fields from a given node into a dictionary.
/// </summary>
class FieldExtractorToHashtable extends FieldExtractor
{
    FieldExtractorToHashtable()
    {
    	this(NodeType.FIELD_START);
	
    }

    /// <summary>
    /// Creates dictionary of fields where key is either FieldStart or FieldSeparator or FieldEnd.
    /// </summary>
    /// <remarks>
    /// AM. I need such type of collection during RTF/DOC export to write HFD structures
    /// This structure is written with field separator.
    /// </remarks>
    FieldExtractorToHashtable(/*NodeType*/int type)
    {
        Debug.assertTrue((type == NodeType.FIELD_START) || (type == NodeType.FIELD_SEPARATOR) || (type == NodeType.FIELD_END));

        mType = type;
    }

    /// <summary>
    /// Access to the result of the extraction.
    /// Key is <see cref="FieldStart"/> and value is <see cref="Field"/>.
    /// </summary>
    Map<Node, Field> getFields() { return mFields; }

    protected /*override*/ void onFieldExtracted()
    {
        switch (mType)
        {
            case NodeType.FIELD_START:
                mFields.put(getCurrentField().getStart(), getCurrentField());
                break;
            case NodeType.FIELD_SEPARATOR:
                // Some fields might not have separator. Do not add such field to table.
                if (getCurrentField().getSeparator() != null)
                    mFields.put(getCurrentField().getSeparator(), getCurrentField());
                break;
            case NodeType.FIELD_END:
                mFields.put(getCurrentField().getEnd(), getCurrentField());
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private /*final*/ /*NodeType*/int mType;
    private /*final*/ HashMap<Node, Field> mFields = new HashMap<Node, Field>();
}

