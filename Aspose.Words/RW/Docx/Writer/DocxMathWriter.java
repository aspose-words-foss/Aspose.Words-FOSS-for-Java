// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/03/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Able to write m:oMath and m:oMathPara root elements and all their Office Math children.
/// </summary>
/*static*/ class DocxMathWriter
{
	/* Simulation of static class by using private constructor */
	private DocxMathWriter()
	{}

    static void writeStart(MathObject mathObject, IMathRunPr runPr, IMathWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startMath();
        switch (mathObject.getMathObjectType())
        {
            case MathObjectType.O_MATH:
                builder.startElement("m:oMath");
                break;
            case MathObjectType.O_MATH_PARA:
                writeOMathPara(mathObject, writer);
                break;
            case MathObjectType.ACCENT:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:acc");
                break;
            case MathObjectType.BAR:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:bar");
                break;
            case MathObjectType.BORDER_BOX:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:borderBox");
                break;
            case MathObjectType.BOX:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:box");
                break;
            case MathObjectType.DELIMITER:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:d");
                break;
            case MathObjectType.ARRAY:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:eqArr");
                break;
            case MathObjectType.GROUP_CHARACTER:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:groupChr");
                break;
            case MathObjectType.PHANTOM:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:phant");
                break;
            case MathObjectType.DEGREE:
                writeMathObjectWithArgPr(mathObject, runPr, writer, "m:deg");
                break;
            case MathObjectType.ARGUMENT:
                writeMathObjectWithArgPr(mathObject, runPr, writer, "m:e");
                break;
            case MathObjectType.FRACTION:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:f");
                break;
            case MathObjectType.DENOMINATOR:
                writeMathObjectWithArgPr(mathObject, runPr, writer, "m:den");
                break;
            case MathObjectType.NUMERATOR:
                writeMathObjectWithArgPr(mathObject, runPr, writer, "m:num");
                break;
            case MathObjectType.FUNCTION:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:func");
                break;
            case MathObjectType.FUNCTION_NAME:
                writeMathObjectWithArgPr(mathObject, runPr, writer, "m:fName");
                break;
            case MathObjectType.LIMIT:
                writeMathObjectWithArgPr(mathObject, runPr, writer, "m:lim");
                break;
            case MathObjectType.LOWER_LIMIT:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:limLow");
                break;
            case MathObjectType.UPPER_LIMIT:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:limUpp");
                break;
            case MathObjectType.MATRIX:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:m");
                break;
            case MathObjectType.MATRIX_ROW:
                builder.startElement("m:mr");
                break;
            case MathObjectType.N_ARY:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:nary");
                break;
            case MathObjectType.RADICAL:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:rad");
                break;
            case MathObjectType.SUBSCRIPT_PART:
                writeMathObjectWithArgPr(mathObject, runPr, writer, "m:sub");
                break;
            case MathObjectType.SUPERSCRIPT_PART:
                writeMathObjectWithArgPr(mathObject, runPr, writer, "m:sup");
                break;
            case MathObjectType.PRE_SUB_SUPERSCRIPT:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:sPre");
                break;
            case MathObjectType.SUBSCRIPT:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:sSub");
                break;
            case MathObjectType.SUB_SUPERSCRIPT:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:sSubSup");
                break;
            case MathObjectType.SUPERSCRIPT:
                writeMathObjectWithObjPr(mathObject, runPr, writer, "m:sSup");
                break;
            default:
                throw new IllegalStateException("Please report exception.");
        }
    }

    /// <summary>
    /// This function could have been merged with <see cref="WriteMathObjectWithObjPr"/> but
    /// it is a special case separated from it to simplify writing.
    /// </summary>
    private static void writeOMathPara(MathObject mathObject, IMathWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("m:oMathPara");
        if (mathObject.getCount() > 0)
        {
            builder.startElement("m:oMathParaPr");
            writeMathAttrs(mathObject, writer);
            builder.endElement();
        }
    }

    private static void writeMathObjectWithObjPr(MathObject mathObject, IMathRunPr runPr, IMathWriterContext writer,
        String tagName) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(tagName);

        if ((mathObject.getCount() > 0) || (needToWriteCtrlPr(runPr)) || isNonEmptyMatrix(mathObject))
        {
            builder.startElement(tagName+"Pr");
            writeMathAttrs(mathObject, writer);

            if (isNonEmptyMatrix(mathObject))
                writeMcs(((MathObjectMatrix)mathObject).getColumnPrCollection(), builder);

            writeControlPr(runPr, writer);

            builder.endElement();
        }
    }

    private static boolean isNonEmptyMatrix(MathObject m)
    {
        if (m.getMathObjectType() == MathObjectType.MATRIX)
            return ((MathObjectMatrix)m).getColumnPrCollection().getCount() != 0;

        return false;
    }

    /// <summary>
    /// Write column-specific properties. If two or more columns have equal properties, then only one columnPr is written,
    /// but counter inside of it denotes number of columns this pr applies to.
    /// </summary>
    /// <param name="columns"></param>
    /// <param name="builder"></param>
    private static void writeMcs(MathMatrixColumnPrCollection columns, NrxXmlBuilder builder)
    {
        builder.startElement("m:mcs");

        int counter = 1;
        for (int i = 0; i < columns.getCount(); i++)
        {
            MathMatrixColumnPr cur = columns.get(i);
            MathMatrixColumnPr next = (i + 1 < columns.getCount()) ? columns.get(i + 1) : null;
            if ((next == null) || (cur.getHorizontalAlignment() != next.getHorizontalAlignment()) || (counter > MAX_COUNT_ALLOWED_BY_WORD))
            {

                // cur is the last el or alignments mismatch, write cur alignment.
                writeColumnPr(cur, counter, builder);
                counter = 1;
            }
            else if (cur.getHorizontalAlignment() == next.getHorizontalAlignment())
            {
                counter++;
            }
        }

        builder.endElement();
    }

    private static void writeColumnPr(MathMatrixColumnPr columnPr, int count, NrxXmlBuilder builder)
    {
        builder.startElement("m:mc");
        builder.startElement("m:mcPr");

        // WORDSNET-26355 OOXML Spec says  count == 1 is default and we shouldn't write it
        // MS Word displays visual difference in matrices if we omit writing count == 1
        builder.writeVal("m:count", count);

        builder.writeVal("m:mcJc", StyleConvertUtil.horizontalAlignmentToXml(columnPr.getHorizontalAlignment()));

        builder.endElement();
        builder.endElement();
    }

    private static void writeMathAttrs(AttrCollection attrs, IMathWriterContext writer)
    {
        for (int iAttr = 0; iAttr < attrs.getCount(); iAttr++)
            processAttr(attrs.getKey(iAttr), attrs.getByIndex(iAttr), writer);
    }

    private static void processAttr(int key, Object value, IMathWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        switch (key)
        {
            case MathAttr.JUSTIFICATION:
                builder.writeVal("m:jc", DocxEnum.mathJustificationToDocx((/*OfficeMathJustification*/Integer)value));
                break;
            case MathAttr.GROUP_CHAR:
            case MathAttr.ACCENT_CHARACTER:
            case MathAttr.NARY_CHAR:
                writeChar(builder, "m:chr", (/*char*/Character)value);
                break;
            case MathAttr.POSITION:
            case MathAttr.GROUP_CHAR_POSITION:
                builder.writeVal("m:pos", DocxEnum.mathPositionTypeToDocx((/*MathPosition*/Integer)value));
                break;
            case MathAttr.HIDE_BOTTOM:
                builder.writeVal("m:hideBot", (/*boolean*/Boolean)value);
                break;
            case MathAttr.HIDE_LEFT:
                builder.writeVal("m:hideLeft", (/*boolean*/Boolean)value);
                break;
            case MathAttr.HIDE_RIGHT:
                builder.writeVal("m:hideRight", (/*boolean*/Boolean)value);
                break;
            case MathAttr.HIDE_TOP:
                builder.writeVal("m:hideTop", (/*boolean*/Boolean)value);
                break;
            case MathAttr.STRIKE_BLTR:
                builder.writeVal("m:strikeBLTR", (/*boolean*/Boolean)value);
                break;
            case MathAttr.STRIKE_H:
                builder.writeVal("m:strikeH", (/*boolean*/Boolean)value);
                break;
            case MathAttr.STRIKE_TLBR:
                builder.writeVal("m:strikeTLBR", (/*boolean*/Boolean)value);
                break;
            case MathAttr.STRIKE_V:
                builder.writeVal("m:strikeV", (/*boolean*/Boolean)value);
                break;
            case MathAttr.IS_ALIGNMENT_POINT:
                builder.writeVal("m:aln", (/*boolean*/Boolean)value);
                break;
            case MathAttr.LINE_BREAK:
                writeLineBreak((MathLineBreak)value, builder);
                break;
            case MathAttr.IS_DIFFERENTIAL:
                builder.writeVal("m:diff", (/*boolean*/Boolean)value);
                break;
            case MathAttr.NO_BREAKS:
                builder.writeVal("m:noBreak", (/*boolean*/Boolean)value);
                break;
            case MathAttr.IS_OP_EMU:
                builder.writeVal("m:opEmu", (/*boolean*/Boolean)value);
                break;
            case MathAttr.BEGIN_CHAR:
                writeChar(builder, "m:begChr", (/*char*/Character)value);
                break;
            case MathAttr.END_CHAR:
                writeChar(builder, "m:endChr", (/*char*/Character)value);
                break;
            case MathAttr.SEPARATOR_CHAR:
                writeChar(builder, "m:sepChr", (/*char*/Character)value);
                break;
            case MathAttr.GROW_OPERAND:
                builder.writeVal("m:grow", (/*boolean*/Boolean)value);
                break;
            case MathAttr.DELIMITER_SHAPE:
                builder.writeVal("m:shp", DocxEnum.mathDelimiterShapeToDocx((/*MathDelimiterShape*/Integer)value));
                break;
            case MathAttr.ROW_SPACING_RULE:
                builder.writeVal("m:rSpRule", (/*int*/Integer)value);
                break;
            case MathAttr.ROW_SPACING:
                builder.writeVal("m:rSp", (/*int*/Integer)value);
                break;
            case MathAttr.BASE_JUSTIFICATION:
            {
                boolean ooxmlCompliance = !writer.isDocx() || (writer.getCompliance() != OoxmlComplianceCore.ECMA_376);

                builder.writeVal("m:baseJc",
                                 DocxEnum.mathBaseJustificationToDocx((/*MathBaseJustification*/Integer) value, ooxmlCompliance));
                break;
            }
            case MathAttr.MAX_DIST:
                builder.writeVal("m:maxDist", (/*boolean*/Boolean)value);
                break;
            case MathAttr.OBJ_DIST:
                builder.writeVal("m:objDist", (/*boolean*/Boolean)value);
                break;
            case MathAttr.VERTICAL_JUSTIFICATION:
                {
                    /*MathVerticalJustification*/int vj = (/*MathVerticalJustification*/Integer)value;
                    if (vj != MathVerticalJustification.BOTTOM) // WORDSNET-26354
                        builder.writeVal("m:vertJc", DocxEnum.mathVerticalJustificationTypeToDocx(vj));
                    else
                        builder.writeEmptyElement("m:vertJc");
                    break;
                }
            case MathAttr.IS_SHOWN:
                builder.writeVal("m:show", (/*boolean*/Boolean)value);
                break;
            case MathAttr.IS_TRANSPARENT:
                builder.writeVal("m:transp", (/*boolean*/Boolean)value);
                break;
            case MathAttr.IS_ZERO_ASCENT:
                builder.writeVal("m:zeroAsc", (/*boolean*/Boolean)value);
                break;
            case MathAttr.IS_ZERO_DESCENT:
                builder.writeVal("m:zeroDesc", (/*boolean*/Boolean)value);
                break;
            case MathAttr.IS_ZERO_WIDTH:
                builder.writeVal("m:zeroWid", (/*boolean*/Boolean)value);
                break;
            case MathAttr.FRACTION_TYPE:
                builder.writeVal("m:type", DocxEnum.mathFractionTypeToDocx((/*MathFractionType*/Integer)value));
                break;
            case MathAttr.COLUMN_GAP_RULE:
                builder.writeVal("m:cGpRule", (/*int*/Integer)value);
                break;
            case MathAttr.COLUMN_GAP:
                builder.writeVal("m:cGp", (/*int*/Integer)value);
                break;
            case MathAttr.IS_HIDE_PLACEHOLDERS:
                builder.writeVal("m:plcHide",(/*boolean*/Boolean)value);
                break;
            case MathAttr.MIN_COLUMN_WIDTH:
                builder.writeVal("m:cSp", (/*int*/Integer)value);
                break;
            case MathAttr.LIMIT_LOCATION:
                builder.writeVal("m:limLoc", DocxEnum.mathLimitLocationToDocx((/*MathLimitLocation*/Integer)value));
                break;
            case MathAttr.IS_HIDE_SUBSCRIPT:
                builder.writeVal("m:subHide", (/*boolean*/Boolean)value);
                break;
            case MathAttr.IS_HIDE_SUPERSCRIPT:
                builder.writeVal("m:supHide", (/*boolean*/Boolean)value);
                break;
            case MathAttr.DEGREE_HIDE:
                builder.writeVal("m:degHide", (/*boolean*/Boolean)value);
                break;
            case MathAttr.IS_ALIGN_SCRIPTS:
                builder.writeVal("m:alnScr", (/*boolean*/Boolean)value);
                break;
            default:
                // Ignore this unknown attribute.
                break;
        }
    }

    static void writeLineBreak(MathLineBreak lineBreak, NrxXmlBuilder builder)
    {
        if (lineBreak.isDefaultAlignment())
            builder.writeEmptyElement("m:brk");
        else
            builder.writeElementWithAttributes("m:brk", new Object[] { "m:alnAt", Integer.toString(lineBreak.getAlignment()) });
    }

    private static void writeMathObjectWithArgPr(MathObject mathObject, IMathRunPr runPr, IMathWriterContext writer,
        String tagName) throws Exception
    {

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(tagName);

        writeControlPr(runPr, writer);

        MathObjectArgumentBase arg = (MathObjectArgumentBase)mathObject;
        int argSize = arg.getArgumentSize();
        if (argSize != MathObjectArgumentBase.DEFAULT_ARGUMENT_SIZE)
        {
            builder.startElement("m:argPr");
            builder.writeVal("m:argSz", argSize);
            builder.endElement();
        }
    }

    private static boolean needToWriteCtrlPr(IMathRunPr rPr)
    {
        return (rPr.getCount() != 0);// has non-empty rPr
    }

    private static void writeControlPr(IMathRunPr rPr, IMathWriterContext writer) throws Exception
    {
        if (!needToWriteCtrlPr(rPr))
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("m:ctrlPr");

        if (rPr.isDml())
        {
            DmlTextShapeWriter.writeDmlRunProperties("a:rPr", (DmlRunProperties)rPr, (IDmlShapeWriterContext)writer);
        }
        else
        {
            RunPr runPr = (RunPr)rPr;
            // Normal case is when only one revision is present. But if we receive two, need to write them.
            if (runPr.hasInsertRevision() || runPr.hasDeleteRevision())
            {
                if (runPr.hasInsertRevision())
                {
                    builder.writeRevisionStart(runPr.getInsertRevision(), writer.getNextAnnotationId());
                    writer.writeRunPr(runPr);
                    builder.writeRevisionEnd();

                }

                if (runPr.hasDeleteRevision())
                {
                    builder.writeRevisionStart(runPr.getDeleteRevision(), writer.getNextAnnotationId());
                    writer.writeRunPr(runPr);
                    builder.writeRevisionEnd();
                }
            }
            else // write plain rPr only when no revisions exist
            {
                writer.writeRunPr(runPr);
            }
        }

        builder.endElement("m:ctrlPr");
}

    static void writeEnd(NrxXmlBuilder builder)
    {
        builder.endElement();
        builder.endMath();
    }

    private static void writeChar(NrxXmlBuilder builder, String elementName, char elementValue)
    {
        String value = (elementValue != MathObject.EMPTY_CHARACTER) ? Character.toString(elementValue) : "";
        builder.startElement(elementName);
        builder.writeAttributeString("m:val", value);
        builder.endElement(elementName);
    }

    /// <summary>
    /// The standard states that the values of are defined by ST_Integer255 simple type.
    /// MS Office restricts the value of this attribute to be at most 64.
    /// </summary>
    private static final int MAX_COUNT_ALLOWED_BY_WORD = 64;
}

