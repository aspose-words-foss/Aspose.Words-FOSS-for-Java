// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/05/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.StreamReader;
import java.util.ArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.HashMap;
import com.aspose.words.ref.Ref;


/// <summary>
/// Reads paragraphs from text file
/// </summary>
class TxtParagraphReader
{
    //>>>>>>>> #region  Constructors

    TxtParagraphReader(StreamReader streamReader, TxtLoadOptions loadOptions)
    {
        if (streamReader == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "streamReader");

        mStreamReader = streamReader;
        mLoadOptions = loadOptions;
    }

    TxtParagraphReader(StreamReader streamReader)
    {
    	this (streamReader, new TxtLoadOptions());
	
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Methods

    /// <summary>
    /// Reads paragraphs.
    /// </summary>
    TxtParagraph[] read() throws Exception
    {
        return read(null);
    }

    /// <summary>
    /// Reads paragraphs.
    /// </summary>
    /// <returns>Array of paragraphs</returns>
    TxtParagraph[] read(LoadingProgressProcessor progressProcessor) throws Exception
    {
        mParagraphs = new ArrayList<TxtParagraph>();
        TxtLine txtLine = readLine(progressProcessor);
        while (txtLine.getLineType() != TxtLineType.EOF)
        {
            switch (txtLine.getLineType())
            {
                case TxtLineType.TEXT_LINE:
                case TxtLineType.DELIMITER_LINE:
                case TxtLineType.EMPTY_LINE:
                case TxtLineType.NUMBERING_LINE:
                    createNewParagraph(txtLine);
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            txtLine = readLine(progressProcessor);
        }

        TxtParagraph[] paragraphs = new TxtParagraph[mParagraphs.size()];
        for (int i = 0; i < mParagraphs.size(); i++)
            paragraphs[i] = mParagraphs.get(i);
        return paragraphs;
    }

    /// <summary>
    /// Reads one text line from the source
    /// </summary>
    private TxtLine readLine(LoadingProgressProcessor progressProcessor) throws Exception
    {
        /*TxtTrailingSpacesOptions*/int trailingSpacesOption = mLoadOptions.getTrailingSpacesOptions();
        /*TxtLeadingSpacesOptions*/int leadingSpacesOption = mLoadOptions.getLeadingSpacesOptions();

        String textLine = mStreamReader.readLine();

        // WORDSNET-22891 Implemented TXT loading progress notification.
        if (progressProcessor != null)
            progressProcessor.execute(mStreamReader.getBaseStream());

        if (textLine == null)
            return new TxtLine(TxtLineType.EOF);

        if (trailingSpacesOption == TxtTrailingSpacesOptions.TRIM)
            textLine = msString.trimEnd(textLine, ' ');

        if (leadingSpacesOption == TxtLeadingSpacesOptions.TRIM)
            textLine = msString.trimStart(textLine, ' ');

        String text = msString.trim(textLine, ' ');
        if ("".equals(text))
        {
            TxtLine newLine = new TxtLine(TxtLineType.EMPTY_LINE);
            processFormFeedCharacter(textLine, newLine);
            return newLine;
        }


        /*TxtLineType*/int lineType = TxtLineType.TEXT_LINE;
        TxtNumbering numbering = null;
        if (isDelimiterLine(text))
            lineType = TxtLineType.DELIMITER_LINE;
        int leftIndent = getLeftIndent(textLine);

        if ((leadingSpacesOption == TxtLeadingSpacesOptions.PRESERVE) && (leftIndent > 0))
        {
            // In this case we are forced to preserve leading spaces, which will not be converted to
            // LeftIndent, and DetectNumbering will not be performed. Just leave this textLine untouched.
            text = textLine;
            leftIndent = 0;
        }
        else
        {
            text = msString.trimStart(textLine, ' ');
            if (lineType != TxtLineType.DELIMITER_LINE)
            {
                numbering = detectNumbering(text, leftIndent);
                if (numbering != null)
                {
                    lineType = TxtLineType.NUMBERING_LINE;
                    // remove numbers from text
                    int textPos = numbering.getText().length();
                    while ((textPos < text.length()) && (text.charAt(textPos) == ' '))
                        textPos++;
                    text = msString.remove(text, 0, textPos);
                    leftIndent += textPos;
                }
            }
        }

        TxtLine txtLine = new TxtLine(text, leftIndent, lineType);
        processFormFeedCharacter(textLine, txtLine);

        txtLine.setNumbering(numbering);
        return txtLine;
    }

    /// <summary>
    /// If form feed is at the end of the line, we'll set new section flag.
    /// </summary>
    private static void processFormFeedCharacter(String textLine, TxtLine txtLine)
    {
        if ((textLine.length() != 0) && (textLine.charAt(textLine.length() - 1) == (char)0xC))
            txtLine.isNewSectionRequested(true);
    }

    /// <summary>
    /// Determines left indent of the line.
    /// </summary>
    /// <returns>Returns left indent in chars</returns>
    private static int getLeftIndent(String line)
    {
        int leftIndent = 0;
        int index = 0;
        boolean whiteChar = true;
        while (whiteChar && (index < line.length()))
        {
            if (line.charAt(index) == ' ')
                leftIndent++;
            else if (line.charAt(index) == '\t')
                leftIndent += TAB_SIZE;
            else
                whiteChar = false;
            index++;
        }
        return leftIndent;
    }

    /// <summary>
    /// Finds a previous paragraph with numbering (a. is previous for b., 1.2. is previous for 1.3.)
    /// </summary>
    private TxtParagraph findPreviousNumberingParagraph(TxtNumbering numbering, TxtParagraph parentParagraph)
    {
        // WORDSNET-28110 Cached numbered paragraphs.
        ArrayList<TxtParagraph> numberedParagraphs = null;
        Ref<ArrayList<TxtParagraph>> referenceToNumberedParagraphs = new Ref<ArrayList<TxtParagraph>>(numberedParagraphs);
        boolean outRefCondition0 = !msDictionary.tryGetValue(mNumberedParagraphs, numbering.getNumberingStyle(), /*out*/ referenceToNumberedParagraphs);
        numberedParagraphs = referenceToNumberedParagraphs.get();
        if (outRefCondition0)
            return null;

        TxtParagraph prevParagraph = null;
        for (int i = numberedParagraphs.size() - 1; i >= 0; i--)
        {
            TxtParagraph p = numberedParagraphs.get(i);

            // WORDSNET-10456 Txt to Pdf/Doc/Docx/Txt convesion issue with number text
            // For nested numbering case: there is no need to move higher than parent paragraph
            if (parentParagraph == p)
                break;

            if ((p.getNumbering() != null) && (p.getNumbering().isPrevSiblingFor(numbering)))
            {
                if (p.getNumbering().getNumberingStyle().isBullet() && (p.getNumbering().getNumberPosition() == numbering.getNumberPosition()))
                    return p;

                if (p.getNumbering().getNumberingStyle().isSet() &&
                    ((prevParagraph == null) ||
                     (Math.abs(p.getNumbering().getNumberPosition() - numbering.getNumberPosition()) <
                      Math.abs(prevParagraph.getNumbering().getNumberPosition() - numbering.getNumberPosition()))))
                    prevParagraph = p;
            }

            // WORDSNET-10456 Txt to Pdf/Doc/Docx/Txt convesion issue with number text
            // For common numbering case: there is no need to move higher than start numbering
            if ((parentParagraph == null) && (p.getNumbering() != null) && (p.getNumbering().getParentNumberingParagraph() == null) &&
                p.getNumbering().isStartNumbering())
                break;
        }

        return prevParagraph;
    }

    /// <summary>
    /// Finds a parent paragraph with numbering (1.2. is parent for 1.2.1.)
    /// </summary>
    private TxtParagraph findParentNumberingParagraph(TxtNumbering numbering)
    {
        // WORDSNET-28110 Cached numbered paragraphs.
        ArrayList<TxtParagraph> numberedParagraphs = null;
        Ref<ArrayList<TxtParagraph>> referenceToNumberedParagraphs = new Ref<ArrayList<TxtParagraph>>(numberedParagraphs);
        boolean outRefCondition1 = !msDictionary.tryGetValue(mNumberedParagraphs, numbering.getNumberingStyle(), /*out*/ referenceToNumberedParagraphs);
        numberedParagraphs = referenceToNumberedParagraphs.get();
        if (outRefCondition1)
            return null;

        if (numberedParagraphs.size() == 0)
            return null;

        TxtParagraph lastNumberedParagraph = numberedParagraphs.get(numberedParagraphs.size() - 1);
        return (lastNumberedParagraph != null) && numbering.isFirstChildFor(lastNumberedParagraph.getNumbering())
            ? lastNumberedParagraph
            : null;
    }

    /// <summary>
    /// Determines whether the line is delimiter line like ******************* or ===================
    /// </summary>
    private static boolean isDelimiterLine(String textLine)
    {
        final int DELIMITER_LINE_MIN_LENGTH = 5;

        if (!((textLine.length() >= DELIMITER_LINE_MIN_LENGTH) &&
              ((textLine.charAt(0) == '*') || (textLine.charAt(0) == '-') || (textLine.charAt(0) == '=') || (textLine.charAt(0) == '~'))))
            return false;

        for (int i = 0; i < textLine.length(); i++)
        {
            char c = textLine.charAt(i);
            if (c != textLine.charAt(0))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Creates and returns the new paragraph
    /// </summary>
    private void createNewParagraph(TxtLine txtLine)
    {
        TxtParagraph paragraph = new TxtParagraph();

        paragraph.setFirstLineIndent(txtLine.getLeftIndent());
        paragraph.appendText(txtLine.getText());
        paragraph.setNumbering(txtLine.getNumbering());
        paragraph.isNewSectionRequested(txtLine.isNewSectionRequested());
        msArrayList.add(mParagraphs, paragraph);

        // WORDSNET-28110 Keep numbered paragraphs in a Dictionary
        // to provide quick access to its properties.
        if (txtLine.getNumbering() != null)
        {
            ArrayList<TxtParagraph> numberedParagraphs = null;
            Ref<ArrayList<TxtParagraph>> referenceToNumberedParagraphs = new Ref<ArrayList<TxtParagraph>>(numberedParagraphs);
            boolean outRefCondition2 = !msDictionary.tryGetValue(mNumberedParagraphs, txtLine.getNumbering().getNumberingStyle(), /*out*/ referenceToNumberedParagraphs);
            numberedParagraphs = referenceToNumberedParagraphs.get();
            if (outRefCondition2)
            {
                numberedParagraphs = new ArrayList<TxtParagraph>();
                msDictionary.add(mNumberedParagraphs, txtLine.getNumbering().getNumberingStyle(), numberedParagraphs);
            }
            msArrayList.add(numberedParagraphs, paragraph);
        }
    }

    /// <summary>
    /// Detects whether the line contains a numbering part.
    /// </summary>
    private TxtNumbering detectNumbering(String text, int leftIndent)
    {
        TxtNumbering numbering = TxtNumberingDetector.detect(text, mLoadOptions);
        if (numbering == null)
            return null;

        numbering.setNumberPosition(leftIndent);
        numbering.setParentNumberingParagraph(findParentNumberingParagraph(numbering));
        numbering.setPrevNumberingParagraph(findPreviousNumberingParagraph(numbering, numbering.getParentNumberingParagraph()));
        // A correct numbering should be sibling, first or child.
        if ((numbering.getPrevNumberingParagraph() == null) && !numbering.isStartNumbering() &&
            (numbering.getParentNumberingParagraph() == null))
            return null;

        return numbering;
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Fields

    private /*final*/ StreamReader mStreamReader;
    private ArrayList<TxtParagraph> mParagraphs;

    /// <summary>
    /// The dictionary to keep numbered paragraphs.
    /// Key: numbering style;
    /// Value: List of paragraphs with numbering style specified in Key.
    /// </summary>
    private /*final*/ HashMap<TxtNumberingStyle, ArrayList<TxtParagraph>> mNumberedParagraphs =
        new HashMap<TxtNumberingStyle, ArrayList<TxtParagraph>>();

    //<<<<<<<< #endregion 

    /// <summary>
    /// Tab size is the amount of space each tab
    /// </summary>
    private static final int TAB_SIZE = 4;

    private /*final*/ TxtLoadOptions mLoadOptions;
}

