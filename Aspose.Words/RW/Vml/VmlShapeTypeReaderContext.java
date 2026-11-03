// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;



/// <summary>
/// Used to parse VML of standard shape type definitions from an embedded resource.
/// Most of the implemented methods do nothing because shape type definitions do not
/// contain image data and so on.
/// </summary>
class VmlShapeTypeReaderContext implements IVmlShapeReaderContext
{
    private /*final*/ NrxXmlReader mXmlReader;

    public int /*IVmlShapeReaderContext.*/getGroupNestingLevel() { return mGroupNestingLevel; }; public void /*IVmlShapeReaderContext.*/setGroupNestingLevel(int value) { mGroupNestingLevel = value; };

    private int /*IVmlShapeReaderContext.*/ mGroupNestingLevel;

    VmlShapeTypeReaderContext(String shapeTypeXml)
    {
        mXmlReader = new NrxXmlReader(shapeTypeXml, G_SHAPE_TYPE_NAMESPACES);
    }

    public DocumentBase /*IVmlShapeReaderContext.*/getDocument() { return null; }

    public NrxXmlReader /*IVmlShapeReaderContext.*/getXmlReader() { return mXmlReader; }

    public IEmbeddedObject /*IVmlShapeReaderContext.*/getEmbeddedObject(String id)
    {
        return null;
    }

    public byte[] /*IVmlShapeReaderContext.*/getBinData(String src)
    {
        return null;
    }

    public String /*IVmlShapeReaderContext.*/getRelationshipTarget(String relId)
    {
        return null;
    }

    public boolean /*IVmlShapeReaderContext.*/isExternalImage(String src)
    {
        return false;
    }

    public void /*IVmlShapeReaderContext.*/addToZOrderList(ShapeBase shape)
    {
        // Do nothing.
    }

    public void /*IVmlShapeReaderContext.*/addToShapeMap(String shapeId, ShapeBase shape)
    {
        // Do nothing.
    }

    public ShapeBase /*IVmlShapeReaderContext.*/getShapeById(String shapeId)
    {
        return null;
    }

    public void /*IVmlShapeReaderContext.*/readBinData()
    {
        // Do nothing.
    }

    public boolean /*IVmlShapeReaderContext.*/readTextboxContent(ShapeBase shape)
    {
        return false;
    }

    public void /*IVmlShapeReaderContext.*/addShapeType(String id, ShapePr shapePr)
    {
        // Do nothing.
    }

    public ShapePr /*IVmlShapeReaderContext.*/getShapeTypePr(String id)
    {
        return null;
    }

    public void /*IVmlShapeReaderContext.*/markMissingSource(Shape shape, String src)
    {
        throw new UnsupportedOperationException("This is intended for NrxDocumentReaderBase only so far.");
    }

    public String /*IVmlShapeReaderContext.*/getMissingSource(Shape shape)
    {
        throw new UnsupportedOperationException("This is intended for NrxDocumentReaderBase only so far.");
    }

    private static /*final*/ HashMap<String, String> G_SHAPE_TYPE_NAMESPACES;

    static/* VmlShapeTypeReaderContext()*/
    {
        G_SHAPE_TYPE_NAMESPACES = new HashMap<String, String>();
        msDictionary.add(G_SHAPE_TYPE_NAMESPACES, "v", NrxNamespaces.VML);
        msDictionary.add(G_SHAPE_TYPE_NAMESPACES, "o", NrxNamespaces.OFFICE);
    }
}

