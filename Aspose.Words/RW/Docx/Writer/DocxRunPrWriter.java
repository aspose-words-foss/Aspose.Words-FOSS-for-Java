// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxRunPrWriter extends NrxRunPrWriterBase
{
    private DocxRunPrWriter()
    {
    }

    protected /*override*/ boolean isWriteMathPr(INrxWriterContext writer)
    {
        return writer.isDocx();
    }

    protected /*override*/ void writeMathLineBreak(MathLineBreak mathLineBreak, NrxXmlBuilder builder)
    {
        DocxMathWriter.writeLineBreak(mathLineBreak, builder);
    }

    static DocxRunPrWriter getInstance()
    {
        if (gInstance == null)
            gInstance = new DocxRunPrWriter();
        return gInstance;
    }

    private static DocxRunPrWriter gInstance;
}

