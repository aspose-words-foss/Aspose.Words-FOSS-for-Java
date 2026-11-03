// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/01/2024 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IDrFontProvider;
import com.aspose.StackExtension;
import com.aspose.MathUtil;
import com.aspose.ms.System.msString;
import com.aspose.DrFont;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Handles fixed-width spans and generates padding for them.
/// </summary>
class HtmlFixedWidthSpanReader
{
    HtmlFixedWidthSpanReader(
        /*LoadFormat*/int loadFormat,
        DocumentFormatter formatter,
        HtmlBidiTextArranger bidiTextArranger,
        IDrFontProvider fontProvider)
    {
        // For now we preserve span's width only for MHTML files.
        mIsEnabled = loadFormat == LoadFormat.MHTML;
        mFormatter = formatter;
        mBidiTextArranger = bidiTextArranger;
        mFontProvider = fontProvider;
    }

    void handleSpanStart(HtmlElementNode node) throws Exception
    {
        if (!isSupportedFixedWidthSpan())
        {
            return;
        }

        // WORDSNET-17414 IE opens MHTML documents in IE 5 compatibility mode.
        // It looks like in this mode IE processes fixed-width bidi spans separately from surrounding text.
        mBidiTextArranger.rearrangeAndWriteText();

        // Don't write sequence of space characters that pad fixed-width spans if text is not aligned with the start of
        // the span. We only support the case where text is followed by space.
        boolean textIsAlignedWithSpanStart = CssTextAlignStyleConverter.textIsAlignedWithStart(
            mFormatter.getElementDeclarations(),
            mFormatter.isBlockRtl());
        if (!textIsAlignedWithSpanStart)
        {
            return;
        }

        double width = mFormatter.getElementDeclarations().getLength("width");
        if (width <= 0)
        {
            return;
        }

        // We start to process a new fixed-width span.
        mOpenedSpans.push(new FixedWidthSpan(node, width));
    }

    String handleSpanEnd(HtmlElementNode node, Font font) throws Exception
    {
        if (!isSupportedFixedWidthSpan())
        {
            return "";
        }

        // WORDSNET-17414 IE opens MHTML documents in IE 5 compatibility mode.
        // It looks like in this mode IE processes fixed-width bidi spans separately from surrounding text.
        mBidiTextArranger.rearrangeAndWriteText();

        // We don't need to do anything if we're not currently processing any fixed-width span or if the specified node
        // is not the fixed-width span that we're currently processing.
        FixedWidthSpan span = StackExtension.top(mOpenedSpans);
        if ((span == null) || (span.getSpanElement() != node))
        {
            return "";
        }

        // Forget the current span. We have finished processing it.
        mOpenedSpans.pop();

        // Use space characters to pad the trailing empty space (if any) of the span.
        // WORDSNET-27107 If the font size is explicitly set to zero, the calculation result will not be correct.
        if ((span.getTrailingEmptySpaceWidth() > 0) && !MathUtil.isZero(font.getSize()))
        {
            double spaceCharWidth = getTextWidth(ControlChar.SPACE, font);
            int paddingCharCount = MathUtil.doubleToInt(span.getTrailingEmptySpaceWidth() / spaceCharWidth);
            return msString.newString(ControlChar.SPACE_CHAR, paddingCharCount);
        }

        return "";
    }

    void processText(String text, Font font) throws Exception
    {
        if (mOpenedSpans.getCount() > 0)
        {
            float textWidth = getTextWidth(text, font);
            // Add text's width to all fixed-width spans that enclose this text.
            for (FixedWidthSpan span : (Iterable<FixedWidthSpan>) mOpenedSpans)
            {
                span.addTextWidth(textWidth);
            }
        }
    }

    private boolean isSupportedFixedWidthSpan()
    {
        if (!mIsEnabled)
        {
            return false;
        }

        // Normally, "width" has no effect on inline-level elements. However, it can be applied by Internet Explorer when
        // a document is opened in the IE5 compatibility mode, which is used for MHTML documents.
        /*CssDisplayType*/int displayType = mFormatter.getElementDisplayType();
        if ((displayType != CssDisplayType.INLINE) && (displayType != CssDisplayType.INLINE_BLOCK))
        {
            return false;
        }

        return mFormatter.getElementDeclarations().get("width") != null;
    }

    private float getTextWidth(String text, Font font) throws Exception
    {
        DrFont drawingFont = mFontProvider.fetchDrFont(font.getName(), (float)font.getSize(), font.getFontStyle());
        return drawingFont.getTextWidthPoints(text);
    }

    private static class FixedWidthSpan
    {
        FixedWidthSpan(HtmlElementNode span, double width)
        {
            mSpanElement = span;
            setTrailingEmptySpaceWidth(width);
        }

        void addTextWidth(double width)
        {
            setTrailingEmptySpaceWidth(getTrailingEmptySpaceWidth() - width);
        }

        HtmlElementNode getSpanElement() { return mSpanElement; };

        private HtmlElementNode mSpanElement;

        double getTrailingEmptySpaceWidth() { return mTrailingEmptySpaceWidth; }; private void setTrailingEmptySpaceWidth(double value) { mTrailingEmptySpaceWidth = value; };

        private double mTrailingEmptySpaceWidth;
    }

    private /*final*/ boolean mIsEnabled;
    private /*final*/ msStack<HtmlFixedWidthSpanReader.FixedWidthSpan> mOpenedSpans = new msStack<HtmlFixedWidthSpanReader.FixedWidthSpan>();
    private /*final*/ DocumentFormatter mFormatter;
    private /*final*/ HtmlBidiTextArranger mBidiTextArranger;
    private /*final*/ IDrFontProvider mFontProvider;
}

