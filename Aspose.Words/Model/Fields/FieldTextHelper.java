// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.UnicodeCharacterDataResolver;
import com.aspose.BidiCharacterType;
import java.util.ArrayList;
import com.aspose.BidiRun;
import com.aspose.BidiAlgorithm;
import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Provides a set of utility BIDI-aware methods to process text within the Field engine.
/// </summary>
class FieldTextHelper
{
    /// <summary>
    /// Ctor.
    /// </summary>
    private FieldTextHelper()
    {
        // Hide from using outside.
    }

    /// <summary>
    /// Writes BIDI text to the given document builder properly basing on the result direction provided by the given field.
    /// </summary>
    static void writeTextBidiAware(Field field, DocumentBuilder builder, String text) throws Exception
    {
        writeTextBidiAware(field, builder, text, false);
    }

    /// <summary>
    /// Writes BIDI text to the given document builder properly using the specified BIDI embedding.
    /// </summary>
    static void writeTextBidiAware(DocumentBuilder builder, String text, boolean isRtlEmbedding) throws Exception
    {
        writeTextBidiAware(null, builder, text, isRtlEmbedding);
    }

    /// <summary>
    /// Writes BIDI text to the given document builder properly basing on the result direction provided by the given field.
    /// </summary>
    static void writeTextBidiAware(Field field, DocumentBuilder builder, FieldFormattingResult result) throws Exception
    {
        Debug.assertTrue(field != null);

        if (!result.getPreserveRichFormatting() || builder.getDocument().getFieldOptions().isBidiTextSupportedOnUpdate())
        {
            writeTextBidiAware(field, builder, result.getText().toSystemString(), false);
            return;
        }

        writeRichString(builder, result.getText());
    }

    /// <summary>
    /// Writes BIDI text to the given document builder properly. If the specified field is null, uses the specified
    /// BIDI embedding. Otherwise, uses the result direction provided by the given field.
    /// </summary>
    private static void writeTextBidiAware(Field field, DocumentBuilder builder, String text, boolean isRtlEmbedding) throws Exception
    {
        if (!builder.getDocument().getFieldOptions().isBidiTextSupportedOnUpdate())
        {
            builder.write(text);
            return;
        }

        writeBidiText(field, builder, text, isRtlEmbedding);
    }

    private static void writeBidiText(Field field, DocumentBuilder builder, String text, boolean isRtlEmbedding) throws Exception
    {
        // Write BIDI-compatible text runs and paragraphs.
        boolean isRtlParagraph = (field != null)
            ? field.isRtlResultParagraph()
            : isRtlEmbedding;

        DocumentBuilderAdapter adapter = createDocumentBuilderAdapter(builder);

        // Every paragraph separator of the text starts a new paragraph. CR followed by LF is one separator.
        int paragraphStart = 0;
        int i = 0;
        while (i < text.length())
        {
            if (!isParagraphSeparator(text.charAt(i)))
            {
                ++i;
                continue;
            }

            appendBidiRuns(text.substring((paragraphStart), (paragraphStart) + (i - paragraphStart)), isRtlParagraph, builder.getDocument(), adapter,
                adapter);
            builder.insertParagraph();

            if ((text.charAt(i) == ControlChar.PARAGRAPH_BREAK_CHAR) && (i + 1 < text.length()) && (text.charAt(i + 1) == ControlChar.LINE_FEED_CHAR))
                ++i;
            ++i;
            paragraphStart = i;
        }

        if (paragraphStart < text.length())
            appendBidiRuns(text.substring(paragraphStart), isRtlParagraph, builder.getDocument(), adapter, adapter);
    }

    /// <summary>
    /// Returns a value indicating whether the specified character separates paragraphs in terms of the Unicode
    /// bidirectional algorithm.
    /// </summary>
    private static boolean isParagraphSeparator(char c)
    {
        return UnicodeCharacterDataResolver.getBidiCharacterType(c) == BidiCharacterType.B;
    }

    /// <summary>
    /// Returns an <see cref="IList{T}"/> instance filled with runs obtained while the specified single-line text
    /// partitioning. Considers BIDI text. Throws, if the specified text is not single-line.
    /// </summary>
    static ArrayList<Run> getSingleLineTextRunListBidiAware(
        String text,
        Document document,
        IFieldRunPrProvider runPrProvider,
        boolean isRtlEmbedding) throws Exception
    {
        RunListBuilder builder = createRunListBuilder();
        appendSingleLineTextRunsBidiAware(text, document, builder, runPrProvider, isRtlEmbedding);
        return builder.getList();
    }

    /// <summary>
    /// Returns a <see cref="NodeRange"/> instance consisting of runs obtained while the specified single-line text
    /// partitioning. Considers BIDI text. Throws, if the specified text is not single-line.
    /// </summary>
    static NodeRange getSingleLineTextRunRangeBidiAware(
        String text,
        Document document,
        IFieldRunPrProvider runPrProvider,
        boolean isRtlEmbedding) throws Exception
    {
        RunRangeBuilder builder = createRunRangeBuilder();
        appendSingleLineTextRunsBidiAware(text, document, builder, runPrProvider, isRtlEmbedding);
        return builder.getNodeRange();
    }

    private static void writeRichString(DocumentBuilder builder, RichString text)
    {
        // EV: This method is adapted copy of the DocumentBuilder.WriteCore method.

        //Convert CrLf and Lf into Crs.
        RichString normalizedText = text
            .replaceInternal(ControlChar.CR_LF, ControlChar.CR)
            .replaceInternal(ControlChar.LF, ControlChar.CR);

        //If text had any Crs insert paragraphs as appropriate.
        int paraStart = 0;
        while (paraStart <= normalizedText.getLength())
        {
            int paraEnd = normalizedText.indexOf(Character.toString(ControlChar.PARAGRAPH_BREAK_CHAR), paraStart);
            if (paraEnd != -1)
            {
                int length = paraEnd - paraStart;
                if (length > 0)
                    insertRun(builder, normalizedText.substring(paraStart, length));

                switch (builder.getParagraphBreakCharReplacement())
                {
                    case ParagraphBreakCharReplacement.PARAGRAPH:
                        Paragraph lastParagraph = builder.getCurrentParagraph();
                        Paragraph newParagraph = builder.insertParagraph();

                        newParagraph.setParagraphBreakRunPr(lastParagraph.getParagraphBreakRunPr());
                        lastParagraph.setParagraphBreakRunPr(normalizedText.getInternal(paraEnd).getRunPr());
                        break;
                    case ParagraphBreakCharReplacement.PARAGRAPH_BREAK_CHAR:
                        insertRun(builder, ControlChar.PARAGRAPH_BREAK, normalizedText.getInternal(paraEnd).getRunPr());
                        break;
                    default:
                        throw new IllegalArgumentException();
                }

                paraStart = paraEnd + 1;
            }
            else
            {
                //No more paragraph breaks are found.
                int length = normalizedText.getLength() - paraStart;
                if (length > 0)
                    insertRun(builder, normalizedText.substring(paraStart, length));

                break;
            }
        }
    }

    private static void insertRun(DocumentBuilder builder, RichString text)
    {
        for (RichStringChunk chunk : text.toChunks())
            insertRun(builder, chunk.getValue(), chunk.getRunPr());
    }

    private static void insertRun(DocumentBuilder builder, String text, RunPr runPr)
    {
        runPr = AttrCollectionExtension.deepClone(runPr);
        builder.getRunPrCopy().mirrorTo(runPr, FontAttr.RSID_R, FontAttr.RSID_R_PR);

        Run newRunNode = new Run(builder.getDocument(), text, runPr);

        builder.insertNode(newRunNode);
    }

    /// <summary>
    /// Appends runs obtained while the specified single-line text partitioning to the specified <see cref="IRunAppender"/>.
    /// Considers BIDI text. Throws, if the specified text is not single-line.
    /// </summary>
    private static void appendSingleLineTextRunsBidiAware(
        String text,
        Document document,
        IRunAppender runAppender,
        IFieldRunPrProvider runPrProvider,
        boolean isRtlEmbedding) throws Exception
    {
        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            if (isParagraphSeparator(c))
                throw new IllegalArgumentException("A single-line text was expected." + "\r\nParameter name: " + "text");
        }

        if (document.getFieldOptions().isBidiTextSupportedOnUpdate())
        {
            // Build and append BIDI-compatible text runs.
            appendBidiRuns(text, isRtlEmbedding, document, runAppender, runPrProvider);
        }
        else
        {
            // Build and append a single text run regardless of whether the text contains any RTL characters.
            runAppender.append(new Run(document, text, runPrProvider.getRunPr()));
        }
    }

    /// <summary>
    /// Splits the specified single-paragraph text into BIDI runs and appends them to the given <see cref="IRunAppender"/>.
    /// </summary>
    private static void appendBidiRuns(
        String text,
        boolean isRtlParagraph,
        Document document,
        IRunAppender runAppender,
        IFieldRunPrProvider runPrProvider) throws Exception
    {
        for (BidiRun bidiRun : BidiAlgorithm.applyToFieldResult(text, isRtlParagraph))
        {
            if (!StringUtil.hasChars(bidiRun.getText()))
                continue;

            RunPr runPr = runPrProvider.getRunPr();
            if (bidiRun.getRtl())
            {
                runPr.setBidi(AttrBoolEx.TRUE);
            }
            else
            {
                runPr.remove(FontAttr.BIDI);
            }

            Run run = new Run(document, bidiRun.getText(), runPr);
            runAppender.append(run);
        }
    }

    /// <summary>
    /// A factory method to create a <see cref="RunListBuilder"/> instance.
    /// </summary>
    /// <remarks>
    /// This method should be an instance member the code to be autoportable to Java since
    /// instances of nested classes should be created within instance members of an outer class.
    /// </remarks>
    private static RunListBuilder createRunListBuilder()
    {
        return new RunListBuilder();
    }

    /// <summary>
    /// A factory method to create a <see cref="RunRangeBuilder"/> instance.
    /// </summary>
    /// <remarks>
    /// This method should be an instance member the code to be autoportable to Java since
    /// instances of nested classes should be created within instance members of an outer class.
    /// </remarks>
    private static RunRangeBuilder createRunRangeBuilder()
    {
        return new RunRangeBuilder();
    }

    /// <summary>
    /// A factory method to create a <see cref="DocumentBuilderAdapter"/> instance.
    /// </summary>
    /// <remarks>
    /// This method should be an instance member the code to be autoportable to Java since
    /// instances of nested classes should be created within instance members of an outer class.
    /// </remarks>
    private static DocumentBuilderAdapter createDocumentBuilderAdapter(DocumentBuilder builder)
    {
        return new DocumentBuilderAdapter(builder);
    }

    /// <summary>
    /// Represents an entity which can collect runs.
    /// </summary>
    private interface IRunAppender
    {
        public void append(Run run) throws Exception;
    }

    /// <summary>
    /// Implements <see cref="IRunAppender"/> in the way to build an <see cref="List{T}"/> of appended runs.
    /// </summary>
    private static class RunListBuilder implements IRunAppender
    {
        ArrayList<Run> getList()
        {
            return mList;
        }

        public void /*IRunAppender.*/append(Run run)
        {
            msArrayList.add(mList, run);
        }

        private /*final*/ ArrayList<Run> mList = new ArrayList<Run>();
    }

    /// <summary>
    /// Implements <see cref="IRunAppender"/> in the way to build a <see cref="NodeRange"/> consisting of appended runs.
    /// </summary>
    private static class RunRangeBuilder implements IRunAppender
    {
        NodeRange getNodeRange()
        {
            return ((mStartRun != null) && (mEndRun != null)) ? new NodeRange(mStartRun, mEndRun) : null;
        }

        public void /*IRunAppender.*/append(Run run)
        {
            if (mStartRun == null)
                mStartRun = run;

            if (mEndRun == null)
            {
                mEndRun = run;
            }
            else
            {
                CompositeNode parent = mEndRun.getParentNode();
                if (parent == null)
                {
                    // We should stick multiple runs together by a parent node,
                    // NodeEnumerator to be able to move between them.
                    parent = new Paragraph(mEndRun.getDocument());
                    parent.insertAfter(mEndRun, null);
                }

                parent.insertAfter(run, mEndRun);
                mEndRun = run;
            }
        }

        private Run mStartRun;
        private Run mEndRun;
    }

    /// <summary>
    /// Adapts a <see cref="DocumentBuilder"/> instance to <see cref="IRunAppender"/> and <see cref="IFieldRunPrProvider"/>
    /// interfaces.
    /// </summary>
    private static class DocumentBuilderAdapter implements IRunAppender, IFieldRunPrProvider
    {
        DocumentBuilderAdapter(DocumentBuilder builder)
        {
            mBuilder = builder;
        }

        public void /*IRunAppender.*/append(Run run)
        {
            mBuilder.insertNode(run);
        }

        public RunPr /*IFieldRunPrProvider.*/getRunPr()
        {
            return mBuilder.getRunPrCopy();
        }

        private /*final*/ DocumentBuilder mBuilder;
    }
}

