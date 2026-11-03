// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Extracts fields from a given node or collection of nodes into a <see cref="List{Field}"/>.
/// </summary>
class FieldExtractorToCollection extends FieldExtractor
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldExtractorToCollection()
    {
    	this(false);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="isDeep">True to extract nested fields, false to extract top level fields only.</param>
    FieldExtractorToCollection(boolean isDeep)
    {
    	this(isDeep, null);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="isDeep">True to extract nested fields, false to extract top level fields only.</param>
    /// <param name="fieldTypes">Types of fields to extract.</param>
    FieldExtractorToCollection(boolean isDeep, /*params*/ /*FieldType*/int... fieldTypes)
    {
    	super(fieldTypes);
	
        mFields = new ArrayList<Field>();

        mIsDeep = isDeep;
    }

    /// <summary>
    /// Access to the result of the extraction.
    /// Note that the outer fields will appear in the collection after the nested fields.
    /// </summary>
    ArrayList<Field> getFields() { return mFields; };

    private ArrayList<Field> mFields;

    protected /*override*/ void onFieldExtracted()
    {
        if (mIsDeep || !isInField())
            msArrayList.add(getFields(), getCurrentField());
    }

    private /*final*/ boolean mIsDeep;
}

