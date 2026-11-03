// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/03/2022 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.awt.Color;


/// <summary>
/// Interface to define a common data for <see cref="StructuredDocumentTag"/> and <see cref="StructuredDocumentTagRangeStart"/>.
/// </summary>
public interface IStructuredDocumentTag
{
    /// <summary>
    /// Removes just this SDT node itself, but keeps the content of it inside the document tree.
    /// </summary>
    public void removeSelfOnly() throws Exception;

    /// <summary>
    /// Returns a live collection of child nodes that match the specified types.
    /// </summary>
    public NodeCollection getChildNodes(/*NodeType*/int nodeType, boolean isDeep);

    /// <summary>
    /// Returns true if this instance is a ranged (multi-section) structured document tag.
    /// </summary>
    public boolean isMultiSection();

    /// <summary>
    /// Returns Node object that implements this interface.
    /// </summary>
    public Node getNode();

    /// <summary>
    /// <para>Specifies a unique read-only persistent numerical Id for this <b>SDT</b>.</para>
    /// </summary>
    public int getId();

    /// <summary>
    /// Specifies a tag associated with the current SDT node.
    /// Can not be null.
    /// </summary>
    public String getTag(); public void setTag(String value);

    /// <summary>
    /// Specifies the friendly name associated with this <b>SDT</b>.
    /// Can not be null.
    /// </summary>
    public String getTitle(); public void setTitle(String value);

    /// <summary>
    /// Gets the <see cref="BuildingBlock"/> containing placeholder text which should be displayed when this SDT run contents are empty, 
    /// the associated mapped XML element is empty as specified via the <see cref="XmlMapping"/> element
    /// or the <see cref="IsShowingPlaceholderText"/> element is true. 
    /// </summary>
    /// <remarks>Can be null, meaning that the placeholder is not applicable for this Sdt.</remarks>
    public BuildingBlock getPlaceholder();

    /// <summary>
    /// <para>Gets or sets Name of the <see cref="BuildingBlock"/> containing placeholder text.</para>
    /// </summary>
    /// <exception cref="InvalidOperationException">Throw if BuildingBlock with this name <see cref="BuildingBlock.Name"/> is not present in <see cref="Document.GlossaryDocument"/>.</exception>
    public 
    String getPlaceholderName() throws Exception; public void setPlaceholderName(String value) throws Exception;

    /// <summary>
    /// Gets or sets the appearance of the structured document tag.
    /// </summary>
    public /*SdtAppearance*/int getAppearance(); public void setAppearance(/*SdtAppearance*/int value);

    /// <summary>
    /// <para>
    /// Specifies whether the content of this <b>SDT</b> shall be interpreted to contain placeholder text
    /// (as opposed to regular text contents within the SDT). 
    /// </para>
    /// <para>
    /// if set to true, this state shall be resumed (showing placeholder text) upon opening this document.
    /// </para>
    /// </summary>
    public boolean isShowingPlaceholderText(); public void isShowingPlaceholderText(boolean value);

    /// <summary>
    /// Gets the level at which this <b>SDT</b> occurs in the document tree.
    /// </summary>
    public /*MarkupLevel*/int getLevel();

    /// <summary>
    /// Gets type of this <b>Structured document tag</b>.
    /// </summary>
    public /*SdtType*/int getSdtType();

    /// <summary>
    /// When set to true, this property will prohibit a user from deleting this <b>SDT</b>.
    /// </summary>
    public boolean getLockContentControl(); public void setLockContentControl(boolean value);

    /// <summary>
    /// When set to true, this property will prohibit a user from editing the contents of this <b>SDT</b>.
    /// </summary>
    public boolean getLockContents(); public void setLockContents(boolean value);

    /// <summary>
    /// Gets or sets the color of the structured document tag.
    /// </summary>
    public Color getColor(); public void setColor(Color value);

    /// <summary>
    /// Gets an object that represents the mapping of this structured document tag to XML data
    /// in a custom XML part of the current document.
    /// </summary>
    /// <remarks>
    /// You can use the <see cref="Markup.XmlMapping.SetMapping(CustomXmlPart,string,string)"/> method of this object to map 
    /// a structured document tag to XML data.
    /// </remarks>
    /// <dev> 
    /// If this element is present and the parent Sdt is not of a rich text type, then the current
    /// value of the Sdt shall be determined by finding the XML element (if any) which is
    /// determined by the attributes on this element.
    /// See Iso29500, chapter 1, 17.5.2.6 dataBinding (XML Mapping).
    /// If DataBinding information does not result in an XML element, then the
    /// application can use any algorithm desired to find the closest available match. If this information does result in an
    /// XML element, then the contents of that element shall be used to replace the current run content within the
    /// document.
    /// </dev>
    public XmlMapping getXmlMapping();

    /// <summary>
    /// Gets a string that represents the XML contained within the node in the <see cref="SaveFormat.FlatOpc"/> format.
    /// </summary>
    public 
    String getWordOpenXML() throws Exception;
}

