// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/02/2025 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



interface IMathWriterContext
{
    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    public void warn(/*WarningType*/int warningType, String description) throws Exception;

    /// <summary>
    /// Gets a next unique value for writing as w:id of annotation elements in DOCX/WordML
    /// (comments, bookmarks and revision marks, etc).
    /// </summary>
    public int getNextAnnotationId();

    public void writeRunPr(RunPr runPr) throws Exception;

    public NrxXmlBuilder getBuilder();

    public boolean isDocx();

    /// <summary>
    /// Based on the value of this property we will write different attributes, elements into the Docx document.
    /// </summary>
    public /*OoxmlComplianceCore*/int getCompliance();
}

