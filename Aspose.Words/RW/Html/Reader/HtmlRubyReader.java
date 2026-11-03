// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/11/2015 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.BidiLevelList;


/// <summary>
/// Processes a &lt;ruby&gt; HTML element and writes a corresponding <see cref="Ruby"/> node to the document.
/// </summary>
/// <remarks>
/// Malformed &lt;ruby&gt; elements that have no ruby text associated with them are imported as normal inline text.
/// This class buffers content imported from a ruby element and then decides how the content should be written:
/// as a <see cref="Ruby"/> node or as a sequence of <see cref="Run"/> nodes.
/// </remarks>
class HtmlRubyReader
{
    HtmlRubyReader(
        DocumentBuilder builder,
        CssDeclarationCollection rubyElementDeclarations,
        boolean textIsInVisualOrder)
    {
        Debug.assertTrue(builder != null);

        mBuilder = builder;

        mBidiTextArranger = new HtmlBidiTextArranger(mBuilder, textIsInVisualOrder, false);
        mIsWritingTopText = false;

        mCurrentRuby = new Ruby();
        switch (gStringSwitchMap.of(rubyElementDeclarations.getIdentifier("ruby-align")))
        {
            case /*"left"*/0:
                mCurrentRuby.Alignment = RubyAlignment.LEFT;
                break;
            case /*"auto"*/1:
            case /*"center"*/2:
            case /*"line-edge"*/3:
                mCurrentRuby.Alignment = RubyAlignment.CENTER;
                break;
            case /*"right"*/4:
                mCurrentRuby.Alignment = RubyAlignment.RIGHT;
                break;
            case /*"distribute-letter"*/5:
                mCurrentRuby.Alignment = RubyAlignment.DISTRIBUTE_LETTER;
                break;
            case /*"distribute-space"*/6:
            default:
                mCurrentRuby.Alignment = RubyAlignment.DISTRIBUTE_SPACE;
                break;
        }
    }

    /// <summary>
    /// Ends reading of a ruby element and writes the corresponding ruby node into the document model.
    /// </summary>
    void flush() throws Exception
    {
        if ((mCurrentRuby.getBase().getCount() > 0) && (mCurrentRuby.getTop().getCount() > 0))
        {
            Run run = new Run(mBuilder.getDocument(), "", mBuilder.getRunPrCopy());
            run.getRunPr().setAttr(FontAttr.RUBY, mCurrentRuby);
            mBuilder.insertNode(run);
        }
        else
        {
            mBidiTextArranger.rearrangeAndWriteText();
        }
    }

    void writeText(String text, RunPr runPr, BidiLevelList activeBidiLevels, boolean dontCollapseLastSpace)
    {
        if (mIsWritingTopText)
        {
            RubyChunk topChunk = new RubyChunk();
            topChunk.Text = text;
            topChunk.RunPr = runPr;

            mCurrentRuby.getTop().add(topChunk);
            mCurrentRuby.TopSize = runPr.getSize();
        }
        else
        {
            RubyChunk baseChunk = new RubyChunk();
            baseChunk.Text = text;
            baseChunk.RunPr = runPr;

            mCurrentRuby.getBase().add(baseChunk);
            mCurrentRuby.BaseSize = runPr.getSize();

            mCurrentRuby.Distance = runPr.getSize();
        }

        mBidiTextArranger.append(text, runPr, activeBidiLevels, dontCollapseLastSpace, null);
    }

    boolean isWritingTopText() { return mIsWritingTopText; }
    void isWritingTopText(boolean value) { mIsWritingTopText = value; }

    boolean isEmpty() { return mBidiTextArranger.isEmpty(); }

    boolean collapseWhitespaceAfterText()
    {
        return mBidiTextArranger.collapseWhitespaceAfterText();
    }

    private /*final*/ DocumentBuilder mBuilder;

    private /*final*/ Ruby mCurrentRuby;
    private /*final*/ HtmlBidiTextArranger mBidiTextArranger;
    private boolean mIsWritingTopText;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"left",
		"auto",
		"center",
		"line-edge",
		"right",
		"distribute-letter",
		"distribute-space"
	);

}

