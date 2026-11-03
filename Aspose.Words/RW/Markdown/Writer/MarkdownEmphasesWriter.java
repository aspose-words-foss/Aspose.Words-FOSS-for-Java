// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/04/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.msString;


/// <summary>
/// The class responsible for writing markdown emphases.
/// </summary>
class MarkdownEmphasesWriter
{
    /// <summary>
    /// Initializes a new instance of the MarkdownEmphasesWriter class.
    /// </summary>
    MarkdownEmphasesWriter(TxtContentLines contentLines, MarkdownWriter writer, IWarningCallback warningCallback)
    {
    	this (contentLines, (IInline)null, writer);
	
        mWarningCallback = warningCallback;
    }

    /// <summary>
    /// Initializes a new instance of the MarkdownEmphasesWriter class.
    /// </summary>
    MarkdownEmphasesWriter(TxtContentLines contentLines, IInline inline, MarkdownWriter writer)
    {
        if (inline != null)
            mWarningCallback = inline.getDocument_IInline().getWarningCallback();

        mWriter = writer;

        mEmphasesWriters = new ArrayList<MarkdownEmphasisWriterBase>();
        // Create emphases writers.
        msArrayList.add(mEmphasesWriters, new MarkdownBoldEmphasisWriter(contentLines, inline));
        msArrayList.add(mEmphasesWriters, new MarkdownItalicEmphasisWriter(contentLines, inline));
        msArrayList.add(mEmphasesWriters, new MarkdownStrikeThroughWriter(contentLines, inline));
        msArrayList.add(mEmphasesWriters, new MarkdownSubscriptEmphasisWriter(contentLines, inline));
        msArrayList.add(mEmphasesWriters, new MarkdownSuperscriptEmphasisWriter(contentLines, inline));
        // WORDSNET-26248 Implemented export of underline formatting.
        if (mWriter.getSaveOptions().getExportUnderlineFormatting())
            msArrayList.add(mEmphasesWriters, new MarkdownUnderlineWriter(contentLines, inline));

        // Create InlineCode writer.
        mInlineCodeWriter = new MarkdownInlineCodeWriter(contentLines, inline);
    }

    /// <summary>
    /// Flushes collection of pending opening emphases delimiters.
    /// </summary>
    void flushPendingOpeningEmphases()
    {
        for (MarkdownEmphasisWriterBase writer : mEmphasesWriters)
        {
            if (writer.isPendingForOpening())
                writer.open();
        }
    }

    /// <summary>
    /// Appends emphases for the specified IInline object.
    /// </summary>
    void appendEmphases(IInline src)
    {
        updateWritersState(src);
        writeClosingDelimiters();

        // The InlineCode delimiter can be written immediately, because it is not affected by whitespace
        // characters. Also, as InlineCode delimiter is not a whitespace character itself, any other delimiters
        // can be written before InlineCode. So, flush emphases just now in this case.
        if (mInlineCodeWriter.getState() == MarkdownEmphasisWriterState.OPENING)
        {
            flushPendingOpeningEmphases();

            // Note, InlineCode delimiter should be written at the very end.
            // Otherwise, all other delimiters become just literals inside the InlineCode block.
            mInlineCodeWriter.open();
        }
    }

    /// <summary>
    /// Updates states of the all writers for a specified inline.
    /// </summary>
    private void updateWritersState(IInline inline)
    {
        for (MarkdownEmphasisWriterBase writer : mEmphasesWriters)
        {
            writer.UseHtmlSyntax = mWriter.getUseHtmlSyntax();
            writer.updateState(inline);
        }

        mInlineCodeWriter.updateState(inline);
    }

    /// <summary>
    /// Writes closing delimiters for all writers.
    /// </summary>
    private void writeClosingDelimiters()
    {
        if (mInlineCodeWriter.getState() == MarkdownEmphasisWriterState.CLOSING)
            mInlineCodeWriter.close();

        // Tries to close opened emphases using different strategies.
        tryCloseProbeAllClosings();
        tryCloseSwappingOpenings();
        tryCloseWithCloseOpen();
    }

    /// <summary>
    /// Tries to close emphases using 'ProbeAllClosings' strategy.
    /// </summary>
    /// <remarks>
    /// As we cannot close emphasis for which there is any still opened emphasis inside it, we are iterating through
    /// the all emphases being closed and try to close each of them. If something was closed successfully, we repeat
    /// the operation and check again if we can close something else.
    /// </remarks>
    private void tryCloseProbeAllClosings()
    {
        boolean isSuccessClosed;
        do
        {
            isSuccessClosed = false;
            for (MarkdownEmphasisWriterBase writer : mEmphasesWriters)
            {
                if (writer.getState() != MarkdownEmphasisWriterState.CLOSING)
                    continue;

                // There should not be inner opened emphases. Otherwise, they never will be closed.
                if (getNextOpened(writer) == null)
                {
                    writer.close();
                    isSuccessClosed = true;
                }
            }
        }
        while (isSuccessClosed);
    }

    /// <summary>
    /// Tries to close emphases using 'SwapOpenings' strategy.
    /// </summary>
    /// <remarks>
    /// As we cannot close emphasis for which there is any still opened emphasis inside it, we try to swap
    /// positions of neighbored opening delimiters. The order of openings in this case is not important, but the
    /// problematic opening delimiter will be moved outside of closing range and this will allow to complete
    /// closing operation successfully.
    /// </remarks>
    private void tryCloseSwappingOpenings()
    {
        for (MarkdownEmphasisWriterBase writer : mEmphasesWriters)
        {
            if (writer.getState() != MarkdownEmphasisWriterState.CLOSING)
                continue;

            MarkdownEmphasisWriterBase nextOpened = getNextOpened(writer);
            while (writer.swap(nextOpened))
                nextOpened = getNextOpened(writer);

            if (nextOpened == null)
                writer.close();
        }
    }

    /// <summary>
    /// Tries to close emphases using 'CloseOpen' strategy.
    /// </summary>
    /// <remarks>
    /// As we cannot close emphasis for which there is any still opened emphasis inside it, we try to close
    /// still opened problematic emphasis and then send it for opening again.
    /// </remarks>
    private void tryCloseWithCloseOpen()
    {
        for (MarkdownEmphasisWriterBase writer : mEmphasesWriters)
        {
            if (writer.getState() == MarkdownEmphasisWriterState.CLOSING)
                tryCloseWithCloseOpen(writer);
        }
    }

    /// <summary>
    /// Tries to close a specified emphasis using 'CloseOpen' strategy.
    /// </summary>
    private boolean tryCloseWithCloseOpen(MarkdownEmphasisWriterBase writer)
    {
        if (!writer.isOpened())
            return true;

        // Fist try to close still opened inner emphasis.
        MarkdownEmphasisWriterBase nextOpened = getNextOpened(writer);
        if (nextOpened != null)
        {
            if (!tryCloseWithCloseOpen(nextOpened))
                return false;
        }

        // Check either the emphasis should be just closed (without opening).
        if (writer.getState() == MarkdownEmphasisWriterState.CLOSING)
            return close(writer);

        // Close and then open the emphasis.
        writer.close();
        writer.isPendingForOpening(true);

        return true;
    }

    /// <summary>
    /// Closes a specified emphasis writer.
    /// </summary>
    private boolean close(MarkdownEmphasisWriterBase closingWriter)
    {
        Debug.assertTrue(closingWriter.isOpened());
        Debug.assertTrue(getNextOpened(closingWriter) == null);

        // The emphasis can be closed successfully, if it has delimiter char different from the opening
        // delimiter of the next following emphasis. Note, that if we here, then there cannot be already
        // opened inner emphases, but there is pending ones in opening queue.
        for (MarkdownEmphasisWriterBase openingWriter : mEmphasesWriters)
        {
            if (!openingWriter.isPendingForOpening())
                continue;

            // Try to change delimiter to make it to be different with adjacent opening emphasis.
            if (closingWriter.isSameDelimiterChar(openingWriter) && !closingWriter.switchToUnderscore())
            {
                // We encounter a pending for opening emphasis that cannot be properly opened.
                // But we still can close the target emphasis.
                closingWriter.close();

                // This can be improved by checking each pending emphasis independently,
                // but for a moment just clear the whole opening queue.
                clearOpeningQueue();
                return false;
            }
        }

        // The emphasis can be successfully closed.
        closingWriter.close();
        return true;
    }

    /// <summary>
    /// Clears queue of emphases writers pending for opening.
    /// </summary>
    private void clearOpeningQueue()
    {
        for (MarkdownEmphasisWriterBase writer : mEmphasesWriters)
        {
            if (writer.isPendingForOpening())
            {
                writer.isPendingForOpening(false);
                warnLostFormatting(writer);
            }
        }
    }

    /// <summary>
    /// Returns next opened emphasis writer.
    /// </summary>
    private MarkdownEmphasisWriterBase getNextOpened(MarkdownEmphasisWriterBase writer)
    {
        Debug.assertTrue(writer != null);

        MarkdownEmphasisWriterBase nextEmphasis = null;
        for (MarkdownEmphasisWriterBase curWriter : mEmphasesWriters)
        {
            if (curWriter.isOpened() && (curWriter.compareTo(writer) > 0))
            {
                if ((nextEmphasis == null) || (curWriter.compareTo(nextEmphasis) < 0))
                    nextEmphasis = curWriter;
            }
        }

        return nextEmphasis;
    }

    /// <summary>
    /// Issues lost formatting warning for a specified emphasis.
    /// </summary>
    private void warnLostFormatting(MarkdownEmphasisWriterBase emphasis)
    {
        if (mWarningCallback != null)
        {
            String description = msString.format(WarningStrings.MARKDOWN_FORMATTING_LOST, emphasis.getString());
            mWarningCallback.warning(new WarningInfo(WarningType.MAJOR_FORMATTING_LOSS, WarningSource.MARKDOWN, description));
        }
    }

    /// <summary>
    /// Gets a boolean value indicating either InlineCode emphasis is in opening state.
    /// </summary>
    boolean isInlineCodeOpening() { return (mInlineCodeWriter.getState() == MarkdownEmphasisWriterState.OPENING); }

    /// <summary>
    /// Gets a boolean value indicating either InlineCode emphasis is opened.
    /// </summary>
    boolean isInInlineCode() { return (mInlineCodeWriter.isOpened()); }

    /// <summary>
    /// The underlying writer.
    /// </summary>
    private /*final*/ MarkdownWriter mWriter;

    /// <summary>
    /// The warning callback to log warnings.
    /// </summary>
    private /*final*/ IWarningCallback mWarningCallback;

    /// <summary>
    /// Keeps all possible emphases writers.
    /// </summary>
    private /*final*/ ArrayList<MarkdownEmphasisWriterBase> mEmphasesWriters;

    /// <summary>
    /// The InlineCode writer.
    /// </summary>
    private /*final*/ MarkdownInlineCodeWriter mInlineCodeWriter;
}

