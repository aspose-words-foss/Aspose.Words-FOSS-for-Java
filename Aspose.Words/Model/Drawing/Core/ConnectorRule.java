// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// TODO 3 Maintain shape connections. At the moment shapes are disconnected in the model.
/// 
/// Describes what shapes does the connector connect.
/// In Escher and in WordML connector rules are stored in a rule container separately from shapes.
/// RTF does not support connectors at all.
/// </summary>
class ConnectorRule
{
    ConnectorRule()
    {
        this.ShapeAId = 0;
        this.ShapeBId = 0;
    }

    ConnectorRule(int shapeAId, int shapeBId, int shapeASite, int shapeBSite)
    {
        this.ShapeAId = shapeAId;
        this.ShapeBId = shapeBId;
        this.ShapeASite = shapeASite;
        this.ShapeBSite = shapeBSite;
    }

    /// <summary>
    /// The id of the start shape. 0 if not attached.
    /// </summary>
    int ShapeAId;

    /// <summary>
    /// The id of the end shape. 0 if not attached.
    /// </summary>
    int ShapeBId;

    /// <summary>
    /// The id of the site on the start shape where the connector is attached. 
    /// </summary>
    int ShapeASite;

    /// <summary>
    /// The id of the site on the end shape where the connector is attached. 
    /// </summary>
    int ShapeBSite;

    /// <summary>
    /// Contains either ShapeName or ShapeId read for A shape.
    /// </summary>
    /// <remarks>
    /// Unfortunately we can not translate it immediately because connector rules are read before document.
    /// So it is translated to ShapeId after document import complete.
    /// </remarks>
    String ShapeAIdRaw;

    /// <summary>
    /// Contains either ShapeName or ShapeId read for B shape.
    /// </summary>
    String ShapeBIdRaw;
}

