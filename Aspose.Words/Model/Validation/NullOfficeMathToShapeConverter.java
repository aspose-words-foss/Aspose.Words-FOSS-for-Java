// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/06/2016 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents OfficeMathToShapeConverter, which doesn't make any conversions from OfficeMath to Shape.
/// </summary>
class NullOfficeMathToShapeConverter extends OfficeMathToShapeConverter
{
    NullOfficeMathToShapeConverter(Document document)
    {
    	super(document, SaveFormat.UNKNOWN);
	
        // Do nothing.
    }

    protected /*override*/ void insertShapeAtCompatibleTreeLevel(OfficeMath officeMath, Shape mathShape)
    {
        // Do nothing.
    }

}

