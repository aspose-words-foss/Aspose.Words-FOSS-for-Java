// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/08/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides methods needed for VmlShapeReader to operate.
/// Implemented by DOCX and WordML readers to share same VML reading code.
/// </summary>
interface IVmlShapeReaderContext
{
    /// <summary>
    /// The document we are reading into.
    /// </summary>
    public DocumentBase getDocument();

    /// <summary>
    /// The XML reader we are reading from.
    /// </summary>
    public NrxXmlReader getXmlReader();

    /// <summary>
    /// Specifies whether current nesting level of group shape content.
    /// Zero means we are not in group shape now.
    /// </summary>
    public int getGroupNestingLevel();
    public void setGroupNestingLevel(int value);

    /// <summary>
    /// The implementation should return an embedded object or null if not found.
    /// </summary>
    /// <param name="id">For WML this is OLE object id. For DOCX this is relationship id.</param>
    public IEmbeddedObject getEmbeddedObject(String id) throws Exception;

    /// <summary>
    /// Gets image data.
    /// </summary>
    /// <param name="src">In DOCX this is relationship id. In WordML this is the value of the "src" attribute.</param>
    /// <returns>Image bytes or null if not found.</returns>
    public byte[] getBinData(String src) throws Exception;

    /// <summary>
    /// In DOCX gets the relationship target for the specified relationship.
    /// Returns empty string if cannot fetch the relationship or if the parameter is null or empty string.
    ///
    /// In WML just returns the parameter value because there are no parts in WML.
    /// </summary>
    public String getRelationshipTarget(String relId);

    /// <summary>
    /// Returns true if the specified url is a link to an external image resource; false if to internal.
    /// </summary>
    /// <param name="src">In DOCX this is relationship id. In WordML this is the value of the "src" attribute.</param>
    public boolean isExternalImage(String src) throws Exception;

    /// <summary>
    /// The implementation should add the shape to the temporary "z-order list" so
    /// proper ZOrder can be calculated after all shapes were read.
    /// </summary>
    public void addToZOrderList(ShapeBase shape);

    /// <summary>
    /// The implementation should add the shape to the id-to-shape map so the shape
    /// can later be retrieved using <see cref="GetShapeById"/>.
    /// </summary>
    public void addToShapeMap(String shapeId, ShapeBase shape);

    /// <summary>
    /// The implementation should retrieve the shape read previously using the provided id.
    /// Used when reading OLE objects and ActiveX controls. Because reading is split into two parts?
    /// </summary>
    public ShapeBase getShapeById(String shapeId);

    /// <summary>
    /// WML implementation should read the WordML "binData" element.
    /// In WordML binary data can occur anywhere before it is referenced. The implementation of this
    /// interface needs to read the binary data and cache it until it is requested.
    ///
    /// DOCX implementation should do nothing because binary data does not occur inline in XML.
    /// </summary>
    public void readBinData() throws Exception;

    /// <summary>
    /// The implementation should read context of the textbox and add it to the specified shape.
    /// The XML reader will be positioned on the start of the textbox story.
    /// </summary>
    /// <returns>
    /// Value indicating whether XmlReader's position was already set
    /// to next child during processing of textbox.
    /// </returns>
    public boolean readTextboxContent(ShapeBase shape) throws Exception;

    /// <summary>
    /// The implementation should add shape type to shape types collection.
    /// </summary>
    public void addShapeType(String id, ShapePr shapePr);

    /// <summary>
    /// The implementation should return shape type property collection.
    /// </summary>
    public ShapePr getShapeTypePr(String id);

    /// <summary>
    /// The implementation should marks the shape as a shape with missing source which is needed to be resolved later.
    /// </summary>
    public void markMissingSource(Shape shape, String src);

    /// <summary>
    /// The implementation should return shape source if shape is marked as a shape with missing source.
    /// Otherwise the implementation should return null.
    /// </summary>
    public String getMissingSource(Shape shape);
}

