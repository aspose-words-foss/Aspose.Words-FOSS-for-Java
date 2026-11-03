// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/04/2008 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msChar;
import com.aspose.ConvertUtilCore;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.Map;


/// <summary>
/// Builds lines of text representing a table-like structure.
/// </summary>
class TxtTableBuilder
{
    /// <summary>
    /// Ctor.
    /// </summary>
    TxtTableBuilder(Table table, TxtTableBuilder parentBuilder, TxtSaveOptionsBase saveOptions)
    {
        // WORDSNET-22293 The character width depends on the font size of the text within the table.
        setCharWidth(getApproximateCharWidth(table));

        // WORDSNET-7125 Outputting a table with negative left indent to txt causes exception.
        // For negative left indent to work we need to move all txt output to the right,
        // but let's not bother and output the table from zero position instead.
        mTableLeftIndent = pointsToChars(Math.max(table.getLeftIndent(), 0.0));

        mParentBuilder = parentBuilder;
        mSaveOptions = saveOptions;
        if (parentBuilder != null)
        {
            // We should start a nested table from the next line.
            mRowStartLineIndex = getLines().getCurrentLineIndex();
            mRowEndLineIndex = getLines().getCurrentLineIndex();
        }

        setNestedLineIndex(0);
    }

    void startRow()
    {
        mCell = null;
        mCellPosition = mTableLeftIndent;
    }

    void endRow()
    {
        mRowStartLineIndex = mRowEndLineIndex;

        if (mParentBuilder != null)
            mParentBuilder.setNestedLineIndex(mRowEndLineIndex);
    }

    /// <summary>
    /// Start writing of cell.
    /// </summary>
    /// <param name="cell"></param>
    /// <returns>True if the cell should be processed, or false otherwise.</returns>
    boolean startCell(Cell cell)
    {
        mCell = cell;

        if (shouldSkipCell())
            return false;

        getLines().setCurrentLineIndex(mRowStartLineIndex);
        return true;
    }

    void endCell()
    {
        if (shouldSkipCell())
            return;

        // Shift cell position.
        mCellPosition += getCellWidthInChars();
        // Register the maximal line number to be used by the next row.
        mRowEndLineIndex = Math.max(mRowEndLineIndex, getLines().getCurrentLineIndex());

        // WORDSNET-21385 If this cell contains a nested table, we must consider nested content as well.
        if (getNestedLineIndex() > 0)
            handleNestedContent();
    }

    /// <summary>
    /// Consider nested content to determine the correct <see cref="mRowEndLineIndex"/>
    /// </summary>
    private void handleNestedContent()
    {
        mRowEndLineIndex = Math.max(mRowEndLineIndex, getNestedLineIndex());

        // Reset the value once it was used.
        setNestedLineIndex(0);
    }

    /// <summary>
    /// Starts writing of a paragraph.
    /// </summary>
    void startParagraph(Paragraph paragraph)
    {
        mCurParagraph = paragraph;
        mCurParagraphText.setLength(0);
    }

    /// <summary>
    /// Writes accumulated paragraph text to the content lines.
    /// </summary>
    void endParagraph()
    {
        if (mCurParagraphText.length() <= getCharsLeftInCurrentCell())
        {
            appendTextToCurrentLine(mCurParagraphText.toString());
        }
        else
        {
            appendTextWithWordWrap(mCurParagraphText.toString());
        }

        getLines().newLine();
        mCurParagraphText.setLength(0);
    }

    /// <summary>
    /// Appends a specified text to the current paragraph string builder.
    /// </summary>
    void appendText(String text)
    {
        // Do not add paragraph breaks directly as it will break the layout.
        if (msString.equals(text, mSaveOptions.getParagraphBreak()))
        {
            endParagraph();
        }
        else
        {
            msStringBuilder.append(mCurParagraphText, text);
        }
    }

    /// <summary>
    /// Returns text that is aligned inside a current cell.
    /// </summary>
    private String applyAlignment(String text, /*ParagraphAlignment*/int alignment)
    {
        switch (alignment)
        {
            case ParagraphAlignment.RIGHT:
            {
                return msString.padLeft(text, getCharsLeftInCurrentCell());
            }
            case ParagraphAlignment.CENTER:
            {
                int spaces = getCharsLeftInCurrentCell() - text.length();
                // Do not pad text from the right as we do it when appending text to a current line later. This avoids
                // writing of extra spaces for a very last cells.
                return msString.padLeft(text, (spaces / 2) + text.length());
            }
            default:
                return text;
        }
    }

    private boolean shouldSkipCell()
    {
        // Do not process merged cells.
        return mCell.getCellPr().isMergedToPrevious();
    }

    /// <summary>
    /// Appends a specified text to the current line.
    /// </summary>
    private void appendTextToCurrentLine(String text)
    {
        if (text.length() == 0)
            return;

        // Append space if needed.
        if (getPositionInCurrentLine() < 0)
            msStringBuilder.append(mLines.getCurrentLine(), ' ', -getPositionInCurrentLine());

        // WORDSNET-19050 Apply paragraph alignment to the text.
        String alignedText = applyAlignment(text, mCurParagraph.getParaPr().getAlignment());

        msStringBuilder.append(getLines().getCurrentLine(), alignedText);
    }

    /// <summary>
    /// Appends a specified text to the current line with word wrapping.
    /// </summary>
    private void appendTextWithWordWrap(String text)
    {
        int start = 0;

        while (start < text.length())
        {
            int length = getMaxSubstringLength(text, start);
            String textToAppend = text.substring((start), (start) + (length));
            appendTextToCurrentLine(textToAppend);

            start += length;
            // Start a new line for each line except of a very last one.
            if (start < text.length())
                getLines().newLine();
        }
    }

    /// <summary>
    /// Returns the maximum length of the substring that may be appended
    /// to the current line in the current cell so that words are wrapped correctly.
    /// </summary>
    private int getMaxSubstringLength(String text, int startPosition)
    {
        Debug.assertTrue(StringUtil.hasChars(text));

        // WORDSNET-24250 If there is no room for new characters in this line of cell, then go to the next line.
        // Note, non-empty cell in our design has minimal length equal to one char (see GetCellWidthInChars()).
        while ((getCharsLeftInCurrentCell() < 1))
            getLines().setCurrentLineIndex(getLines().getCurrentLineIndex() + 1)/*Property++*/;

        int lastBreakPosition = 0;
        int charsLeft = Math.min(text.length() - startPosition, getCharsLeftInCurrentCell());
        boolean isInWord = !msChar.isWhiteSpace(text.charAt(startPosition));

        for (int i = 0; i < charsLeft; i++)
        {
            int position = startPosition + i;
            char c = text.charAt(position);

            if (msChar.isWhiteSpace(c))
            {
                if (isInWord)
                {
                    lastBreakPosition = position;
                    isInWord = false;
                }
            }
            else
            {
                isInWord = true;
            }
        }

        int result;
        if (lastBreakPosition == 0)
            result = charsLeft;   // Handle long words or spaces.
        else
            result = lastBreakPosition - startPosition;

        Debug.assertTrue(result >= 0);
        return result;
    }

    /// <summary>
    /// The index of line, which is the last line of the last row of the nested table.
    /// </summary>
    private int getNestedLineIndex() { return mNestedLineIndex; }; private void setNestedLineIndex(int value) { mNestedLineIndex = value; };

    private int mNestedLineIndex;

    private int getCellWidthInChars()
    {
        if (mCell == null)
            return 0;
        double cellWidthInPoints = ConvertUtilCore.twipToPoint(getCellWidthInTwips());
        return Math.max(pointsToChars(cellWidthInPoints), 1);
    }

    private int getCellWidthInTwips()
    {
        CellPr firstCellPr = mCell.getCellPr();

        if (firstCellPr.getHorizontalMerge() == CellMerge.NONE)
            return firstCellPr.getWidth();

        int width = firstCellPr.getWidth();
        for (Cell cell = mCell.getNextCell(); cell != null; cell = cell.getNextCell())
        {
            if (cell.getCellPr().getHorizontalMerge() != CellMerge.PREVIOUS)
                break;
            width += cell.getCellPr().getWidth();
        }

        return width;
    }

    /// <summary>
    /// Converts a value in points into a value in characters.
    /// </summary>
    private int pointsToChars(double points)
    {
        Debug.assertTrue(points >= 0);
        return (int)(points / getCharWidth());
    }

    /// <summary>
    /// Gets the approximate character width that depends on the font size of the text within the table.
    /// </summary>
    private static double getApproximateCharWidth(Table table)
    {
        return getApproximateCharWidth(getTotalCharsPerFontSize(table));
    }

    /// <summary>
    /// Gets the dictionary with total characters per font size of the text within the table.
    /// </summary>
    private static HashMap<Double, Long> getTotalCharsPerFontSize(Table table)
    {
        Debug.assertTrue(table != null);

        HashMap<Double, Long> totalCharsPerFontSize = new HashMap<Double, Long>();

        for (Run run : (Iterable<Run>) table.getChildNodes(NodeType.RUN, true))
        {
            if (totalCharsPerFontSize.containsKey((double)run.getRunPr().getSize()))
                totalCharsPerFontSize.put((double)run.getRunPr().getSize(), (long)totalCharsPerFontSize.get((double)run.getRunPr().getSize()) + run.getText().length());
            else
                msDictionary.add(totalCharsPerFontSize, (double)run.getRunPr().getSize(), (long)run.getText().length());
        }
        return totalCharsPerFontSize;
    }

    /// <summary>
    /// Gets the approximate character width that depends on the specified dictionary with
    /// total characters per font size of the text within the table.
    /// </summary>
    private static double getApproximateCharWidth(HashMap<Double, Long> totalCharsPerFontSize)
    {
        final double WIDTH_DIVIDED_TO_HEIGHT = 0.6;
        final double CALIBRATION_COEFFICIENT = 3.5;

        Debug.assertTrue(totalCharsPerFontSize != null);

        if (totalCharsPerFontSize.size() == 0)
            return DEFAULT_CHAR_WIDTH;

        double numerator = 0.0;
        long totalCharsQuantity = 0;
        for (Map.Entry<Double, Long> sizeAndQuantity : totalCharsPerFontSize.entrySet())
        {
            totalCharsQuantity += sizeAndQuantity.getValue();
            numerator += (sizeAndQuantity.getValue() * sizeAndQuantity.getKey());
        }
        return (((numerator * WIDTH_DIVIDED_TO_HEIGHT) / totalCharsQuantity) / CALIBRATION_COEFFICIENT);
    }

    TxtContentLines getLines()
    {
        // We share line collection between nested table builders so only create it for the first builder.
        if (mLines == null)
            mLines = (mParentBuilder != null) ? mParentBuilder.getLines() : new TxtContentLines();
        return mLines;
    }

    /// <summary>
    /// Returns the number of characters we exceeded beyond the current cell position. May be negative.
    /// </summary>
    private int getPositionInCurrentLine() { return (getLines().getCurrentLine().length() - getAbsoluteCellPosition()); }

    /// <summary>
    /// Returns the number of characters we exceeded beyond the current cell position.
    /// </summary>
    private int getPositionInCurrentCell() { return Math.max(getPositionInCurrentLine(), 0); }

    /// <summary>
    /// Returns the number of characters left until the end of the current cell.
    /// </summary>
    private int getCharsLeftInCurrentCell() { return (getCellWidthInChars() - getPositionInCurrentCell()); }

    /// <summary>
    /// Returns the X position of the current cell relatively to the beginning of the current line.
    /// </summary>
    private int getAbsoluteCellPosition() { return (getParentCellPosition() + mCellPosition); }

    /// <summary>
    /// Returns the X position of the parent cell relatively to the beginning of the current line.
    /// </summary>
    private int getParentCellPosition() { return (mParentBuilder != null) ? mParentBuilder.getAbsoluteCellPosition() : 0; }

    /// <summary>
    /// A width of a character in points used to compute how to break cell contents into lines
    /// obtained from the first run of the cell.
    /// </summary>
    private double getCharWidth() { return mCharWidth; }; private void setCharWidth(double value) { mCharWidth = value; };

    private double mCharWidth;

    /// <summary>
    /// Measures in "chars".
    /// </summary>
    private /*final*/ int mTableLeftIndent;
    /// <summary>
    /// The previous table builder.
    /// </summary>
    private /*final*/ TxtTableBuilder mParentBuilder;

    private /*final*/ TxtSaveOptionsBase mSaveOptions;
    /// <summary>
    /// The lines of the table.
    /// </summary>
    private TxtContentLines mLines;
    /// <summary>
    /// The current cell.
    /// </summary>
    private Cell mCell;
    /// <summary>
    /// The current horizontal offset of the cell in chars.
    /// </summary>
    private int mCellPosition;
    /// <summary>
    /// The index of the current row's first line.
    /// </summary>
    private int mRowStartLineIndex;
    /// <summary>
    /// The index of the current row's last line.
    /// </summary>
    private int mRowEndLineIndex;
    /// <summary>
    /// A string builder to accumulate a text of the current paragraph.
    /// </summary>
    private /*final*/ StringBuilder mCurParagraphText = new StringBuilder();
    /// <summary>
    /// The current paragraph.
    /// </summary>
    private Paragraph mCurParagraph;
    /// <summary>
    /// The default width of a character in points.
    /// </summary>
    private static final double DEFAULT_CHAR_WIDTH = 8.0;
}

