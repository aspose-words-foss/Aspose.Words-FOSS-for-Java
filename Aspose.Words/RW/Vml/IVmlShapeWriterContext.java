// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import java.util.ArrayList;


/// <summary>
/// RK Makes it possible to use common shape writing code for WML and DOCX exports.
/// </summary>
interface IVmlShapeWriterContext
{
    /// <summary>
    /// Returns a value that will be written to idsrc, iddest and idcntry fields
    /// in the diagram relationtable.
    /// </summary>
    /// <param name="key">A model (and DOC, RTF) value (hash of the shape name).</param>
    /// <returns>The value that can be written to WML or DOCX (shape id).</returns>
    public String getDiagramMemberName(int key);

    public String writeImageBinData(byte[] imageBytes) throws Exception;

    public String writeImageLink(String link);

    /// <summary>
    /// Converts the specified <see cref="DrColor"/> to VML color string.
    /// </summary>
    /// <remarks>
    /// Logs a warning to the user-provided warning callback if this is a special color definition (system or scheme
    /// color or palette index) that is currently not supported.
    /// </remarks>
    public String colorToVml(DrColor color);

    public ArrayList<String> getShapeTypesWritten();

    public SaveInfo getSaveInfo();

    /// <summary>
    /// The name of XML attribute to use when writing image reference for image binary data in VML.
    /// </summary>
    public String getImageSrcAttributeName();

    /// <summary>
    /// The name of XML attribute to use when writing external image reference for image binary data in VML.
    /// </summary>
    public String getImageHrefAttributeName();

    public boolean isDocx();

    /// <summary>
    /// Indicates the current position is inside the complex field area.
    /// </summary>
    public boolean isInsideField();
}

