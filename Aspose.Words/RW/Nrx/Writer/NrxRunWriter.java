// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/08/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.LocaleConverter;


/// <summary>
/// Base class to refactor methods from DocxRunWriter and WmlRunWriter.
/// </summary>
abstract class NrxRunWriter
{
    /// <summary>
    /// Writes <see cref="Ruby"/> objects as part of 'r' element.
    /// </summary>
    protected void writeRuby(INrxWriterContext writer, Inline inline) throws Exception
    {
        Ruby ruby = (Ruby)inline.getRunPr().get(FontAttr.RUBY);
        writeRunStart(inline);

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("w:ruby");

        builder.startElement("w:rubyPr");

        // WORDSNET-18692 Preserve element writing order as described in the ECMA-376 spec.
        builder.writeVal("w:rubyAlign", NrxRunEnum.rubyAlignmentToXml(ruby.Alignment));
        builder.writeVal("w:hps", ruby.TopSize);
        builder.writeVal("w:hpsRaise", ruby.Distance);
        builder.writeVal("w:hpsBaseText", ruby.BaseSize);

        String lidTag = writer.isDocx()
            ? LocaleConverter.localeToDocxTag((int)ruby.Language)
            : LocaleConverter.localeToWmlTag((int)ruby.Language);

        builder.writeVal("w:lid", lidTag);

        builder.endElement("w:rubyPr");

        builder.startElement("w:rt");
        writeRubyChunks(writer, ruby.getTop());
        builder.endElement("w:rt");

        builder.startElement("w:rubyBase");
        writeRubyChunks(writer, ruby.getBase());
        builder.endElement("w:rubyBase");

        builder.endElement("w:ruby");
        writeRunEnd(inline.getRunPr());
    }

    private void writeRubyChunks(INrxWriterContext writer, RubyChunkCollection chunks) throws Exception
    {
        for (RubyChunk rubyChunk : chunks)
        {
            Run topRun = new Run(writer.getDocument(), rubyChunk.Text, rubyChunk.RunPr);
            writeInline(topRun);
        }
    }

    abstract void writeRunStart(Inline inline) throws Exception;
    abstract void writeInline(Inline inline) throws Exception;
    abstract void writeRunEnd(RunPr runPr) throws Exception;
}

