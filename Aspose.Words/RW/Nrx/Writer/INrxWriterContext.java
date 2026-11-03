// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/07/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



interface INrxWriterContext
{
    public DocumentBase getDocument();

    public String getStyleId(int istd);

    public NrxXmlBuilder getBuilder();

    public boolean isDocx();

    /// <summary>
    /// Based on the value of this property we will write different attributes, elements into the Docx document.
    /// </summary>
    public /*OoxmlComplianceCore*/int getCompliance();

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    public void warn(/*WarningType*/int warningType, String description) throws Exception;

    /// <summary>
    /// Gets a next unique value for writing as w:id of annotation elements in DOCX/WordML 
    /// (comments, bookmarks and revision marks, etc).
    /// </summary>
    public int getNextAnnotationId();

    /// <summary>
    /// Writing of header/footer is too format specific. 
    /// The common section properties writer calls this back so the main writer can write the header/footer itself.
    /// </summary>
    public void writeHeaderFooter(HeaderFooter headerFooter) throws Exception;

    /// <summary>
    /// Writing of footnote properties is too format specific.
    /// The writers that need to invoke writing of footnotes invoke this method, which the main writer should implement.
    /// </summary>
    /// <param name="attrs"></param>
    /// <param name="writeSeparators">True to write footnote separator characters. Use when writing document-wide footnote properties.</param>
    public void writeFootnotePr(AttrCollection attrs, boolean writeSeparators) throws Exception;

    /// <summary>
    /// Writes run attributes specified in 'w14' namespace, if it's needed.
    /// </summary>
    public void writeW14Attributes(INrxWriterContext writer, RunPr runPr) throws Exception;

    /// <summary>
    /// <para>Returns list label string for current paragraph to write as list property.</para>
    /// </summary>
    public String getCurrentParagraphListLabelString();

    /// <summary>
    /// <para>Returns list label font name for current paragraph to write as list property.</para>
    /// </summary>
    public String getCurrentParagraphListLabelFontName();
}

