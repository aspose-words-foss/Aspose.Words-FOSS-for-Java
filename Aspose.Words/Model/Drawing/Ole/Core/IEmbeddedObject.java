// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/10/2021 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Interface to access common properties of embedded objects.
/// </summary>
interface IEmbeddedObject
{
    /// <summary>
    /// Returns name of the embedded object.
    /// </summary>
    public String getName() throws Exception;

    /// <summary>
    /// Returns instance of <see cref="OleObject"/> object.
    /// </summary>
    public OleObject getOleObject() throws Exception;

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    public String getClsidInternal();

    /// <summary>
    /// Returns the extension (with the leading dot).
    /// The extension must be for the inner (e.g. unwrapped) data that can be saved directly to a file.
    /// </summary>
    public String getExtensionForUser(String progId) throws Exception;

    /// <summary>
    /// Returns the file name.
    /// </summary>
    public String getFileNameForUser() throws Exception;

    /// <summary>
    /// Saves the embedded object data in a way that makes it a valid standalone file.
    /// </summary>
    public void saveForUser(Stream stream, IShapeAttrSource attrSource) throws Exception;

    /// <summary>
    /// Gets a boolean value, indicating either the embedded object is Forms2Ole control.
    /// </summary>
    public boolean isForms2OleControlInternal();

    /// <summary>
    /// Gets or sets Id of the embedded object.
    /// </summary>
    public int getId(); public void setId(int value);

    /// <summary>
    /// Gets a boolean value, indicating either the embedded object is empty.
    /// </summary>
    public 
    boolean isEmpty() throws Exception;
}

