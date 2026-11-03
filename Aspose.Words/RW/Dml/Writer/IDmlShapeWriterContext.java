// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/02/2025 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides methods called when writing DML shapes. The interface is implemented to be able to use common shape
/// writing code for DOCX and XLSX exports.
/// </summary>
interface IDmlShapeWriterContext
{
    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    public void warn(/*WarningType*/int warningType, /*WarningSource*/int warningSource, String description) throws Exception;

    /// <summary>
    /// Writes image binary data to the output document.
    /// </summary>
    public String writeImageBinData(byte[] imageBytes) throws Exception;

    /// <summary>
    /// Writes a link to an external image to the output document.
    /// </summary>
    public String writeImageLink(String link);

    /// <summary>
    /// Adds a hyperlink relationship to the current part and returns the id.
    /// </summary>
    public String addHyperlinkRelationship(String dest);

    /// <summary>
    /// Get an XML builder used to write document XML contents.
    /// </summary>
    public NrxXmlBuilder getBuilder();

    /// <summary>
    /// Based on the value of this property we will write different attributes, elements into the Docx document.
    /// </summary>
    public /*OoxmlComplianceCore*/int getCompliance();
}

