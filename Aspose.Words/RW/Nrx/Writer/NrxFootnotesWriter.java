// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/02/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;


/// <summary>
/// Base class of WML and DOCX footnote writers.
/// </summary>
abstract class NrxFootnotesWriter
{
    protected NrxFootnotesWriter(/*FootnoteType*/int footnoteType, boolean hasFootnotes, INrxWriterContext writer)
    {
        mFootnoteType = footnoteType;
        mHasFootnotes = hasFootnotes;
        mWriter = writer;
    }

    /// <summary>
    /// Checks if footnote/endnote options should be written and writes them as footnotePr/endnotePr element.
    /// </summary>
    /// <param name="attrs">The properties to write.</param>
    /// <param name="writeSeparators">
    /// Set to true to write separator references in the "Settings" builder.
    /// and separator definitions in "Footnote"/"Endnote" part.
    /// </param>
    void writeFootnotePr(AttrCollection attrs, boolean writeSeparators) throws Exception
    {
        prepareOptionKeys();
        prepareSeparators(writeSeparators);

        if (hasOptionsToWrite(attrs))
            writeFootnotePrElement(attrs);
    }

    /// <summary>
    /// Writes the "w:footnotePr" or "w:endnotePr" element to the current builder.
    /// </summary>
    private void writeFootnotePrElement(AttrCollection attrs) throws Exception
    {
        // Generate start element
        NrxXmlBuilder builder = mWriter.getBuilder();
        builder.startElement(isEndnote() ? "w:endnotePr" : "w:footnotePr");

        // Write options if needed
        if (!attrs.isDefaultValue(mPosKey))
        {
            builder.writeVal("w:pos", getPositionValueForXml(attrs));
        }
        if (!attrs.isDefaultValue(mNumFmtKey))
        {
            builder.writeVal("w:numFmt", numberStyleToXml((/*NumberStyle*/Integer)attrs.getDirectAttr(mNumFmtKey)));
        }
        if (!attrs.isDefaultValue(mNumStartKey))
        {
            builder.writeVal("w:numStart", (/*int*/Integer)attrs.getDirectAttr(mNumStartKey));
        }
        if (!attrs.isDefaultValue(mNumRestartKey))
        {
            builder.writeVal("w:numRestart", NrxSectEnum.noteNumberingRuleToXml(
                (/*FootnoteNumberingRule*/Integer)attrs.getDirectAttr(mNumRestartKey), mWriter.isDocx()));
        }

        // Write separators
        writeSeparator(mSeparator);
        writeSeparator(mContinuation);
        writeSeparator(mContinuationNotice);

        // Write end element
        builder.endElement();
    }

    /// <summary>
    /// Gets string value of footnote/endnote position to write to XML.
    /// </summary>
    private String getPositionValueForXml(AttrCollection attrs)
    {
        return isEndnote()
            ? NrxSectEnum.endnotePositionToXml((/*EndnotePosition*/Integer)attrs.getDirectAttr(mPosKey), mWriter.isDocx())
            : NrxSectEnum.footnotePositionToXml((/*FootnotePosition*/Integer)attrs.getDirectAttr(mPosKey), mWriter.isDocx());
    }

    /// <summary>
    /// Returns <c>true</c> if there exist options to write.
    /// </summary>
    private boolean hasOptionsToWrite(AttrCollection attrs)
    {
        return (mSeparator != null) ||
               (mContinuation != null) ||
               (mContinuationNotice != null) ||
               (!attrs.isDefaultValue(mPosKey) && StringUtil.hasChars(getPositionValueForXml(attrs))) ||
               !attrs.isDefaultValue(mNumFmtKey) ||
               !attrs.isDefaultValue(mNumStartKey) ||
               !attrs.isDefaultValue(mNumRestartKey);
    }

    /// <summary>
    /// Initializes values for option key fields depending on footnote type.
    /// </summary>
    private void prepareOptionKeys()
    {
        int keyDelta = isEndnote() ? SectAttr.ENDNOTE_KEY_DELTA : 0;
        mPosKey = SectAttr.FOOTNOTE_LOCATION + keyDelta;
        mNumFmtKey = SectAttr.FOOTNOTE_NUMBER_STYLE + keyDelta;
        mNumStartKey = SectAttr.FOOTNOTE_START_NUMBER + keyDelta;
        mNumRestartKey = SectAttr.FOOTNOTE_NUMBERING_RULE + keyDelta;
    }

    /// <summary>
    /// Initializes values for the separator fields depending on current document and footnote type.
    /// </summary>
    private void prepareSeparators(boolean writeSeparators)
    {
        mSeparator = null;
        mContinuation = null;
        mContinuationNotice = null;
        if (writeSeparators && mHasFootnotes)
        {
            FootnoteSeparatorCollection separators = mWriter.getDocument().getFootnoteSeparators();

            mSeparator = separators.getByFootnoteSeparatorType(isEndnote() ? FootnoteSeparatorType.ENDNOTE_SEPARATOR 
                : FootnoteSeparatorType.FOOTNOTE_SEPARATOR);
            mContinuation = separators.getByFootnoteSeparatorType(isEndnote() ? FootnoteSeparatorType.ENDNOTE_CONTINUATION_SEPARATOR
                : FootnoteSeparatorType.FOOTNOTE_CONTINUATION_SEPARATOR);
            mContinuationNotice = separators.getByFootnoteSeparatorType(isEndnote() ? FootnoteSeparatorType.ENDNOTE_CONTINUATION_NOTICE
                : FootnoteSeparatorType.FOOTNOTE_CONTINUATION_NOTICE);
        }
    }

    /// <summary>
    /// Writes definition of a footnote separator.
    /// </summary>
    protected abstract void writeSeparator(FootnoteSeparator separator) throws Exception;

    /// <summary>
    /// Converts the specified number style to a string value to write it to document XML.
    /// </summary>
    protected abstract String numberStyleToXml(/*NumberStyle*/int value);

    /// <summary>
    /// Returns <c>true</c> if this object is writing endnotes.
    /// </summary>
    protected boolean isEndnote() { return mFootnoteType == FootnoteType.ENDNOTE; }

    /// <summary>
    /// Returns <c>true</c> if the document contains footnotes of <see cref="mFootnoteType"/> type.
    /// </summary>
    protected boolean hasFootnotes() { return mHasFootnotes; }

    // Fields that are temporarily used by the WriteFootnotePr method.
    private int mPosKey;
    private int mNumFmtKey;
    private int mNumStartKey;
    private int mNumRestartKey;
    private FootnoteSeparator mSeparator;
    private FootnoteSeparator mContinuation;
    private FootnoteSeparator mContinuationNotice;

    private /*final*/ /*FootnoteType*/int mFootnoteType;
    private /*final*/ boolean mHasFootnotes;
    private /*final*/ INrxWriterContext mWriter;
}

