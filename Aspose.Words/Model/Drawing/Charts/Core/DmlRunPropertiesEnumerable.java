// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/12/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import com.aspose.ms.System.ms;
import java.util.ArrayList;
import java.util.Iterator;


/// <summary>
/// Represents an enumerable over <see cref="DmlRunProperties"/> of all text elements of a paragraph
/// list including <see cref="DmlParagraph.EndParagraphRunProperties"/> (optionally) and
/// <see cref="DmlParagraphProperties.DefaultRunProperties"/>.
/// </summary>
class DmlRunPropertiesEnumerable implements Iterable<DmlRunProperties>
{
    DmlRunPropertiesEnumerable(ArrayList<DmlParagraph> paragraphs, boolean includeParagraphBreakProperties)
    {
        mParagraphs = paragraphs;
        mIncludeParagraphBreakProperties = includeParagraphBreakProperties;
    }

    public Iterator<DmlRunProperties> iterator()
    {
        return new DmlRunPropertiesEnumerator(mParagraphs, mIncludeParagraphBreakProperties);
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private /*final*/ ArrayList<DmlParagraph> mParagraphs;
    private /*final*/ boolean mIncludeParagraphBreakProperties;

    /// <summary>
    /// Represents an enumerator over <see cref="DmlRunProperties"/> of all text elements of a paragraph
    /// list including <see cref="DmlParagraph.EndParagraphRunProperties"/> (optionally) and
    /// <see cref="DmlParagraphProperties.DefaultRunProperties"/>.
    /// </summary>
    private final static class DmlRunPropertiesEnumerator implements Iterator<DmlRunProperties>
    {
        DmlRunPropertiesEnumerator(ArrayList<DmlParagraph> paragraphs, boolean includeParagraphBreakProperties)
        {
            mParagraphs = paragraphs;
            mIncludeParagraphBreakProperties = includeParagraphBreakProperties;
        }

        public boolean hasNext()
        {
            mCurrent = null;
            if (mParagraphs == null)
                return false;

            int paragraphCount = mParagraphs.size();
            if ((mParagraphIndex >= paragraphCount) || (paragraphCount == 0))
                return false;

            if (mParagraphIndex < 0)
            {
                mParagraphIndex = 0;
                mElementIndex = -1;
            }

            DmlParagraph paragraph = mParagraphs.get(mParagraphIndex);
            int elementCount = paragraph.getElements().size();

            if (!mHasFieldParagraphProperties)
                mElementIndex++;

            // Number of DmlRunProperties instances of a paragraph itself to be included in the enumeration.
            int paragraphRunPropertiesCount = mIncludeParagraphBreakProperties ? 2 : 1;
            if (mElementIndex >= elementCount + paragraphRunPropertiesCount)
            {
                mParagraphIndex++;
                mElementIndex = 0;
                if (mParagraphIndex >= paragraphCount)
                    return false;
            }

            // mElementIndex == elementCount is intended for paragraph.Properties.DefaultRunProperties
            if (mElementIndex == elementCount)
            {
                mCurrent = paragraph.getProperties().getDefaultRunProperties();
            }
            // mElementIndex == elementCount + 1 is intended for paragraph.EndParagraphRunProperties
            else if (mElementIndex == elementCount + 1)
            {
                mCurrent = paragraph.getEndParagraphRunProperties();
            }
            // DmlTextField.ParagraphProperties.DefaultRunProperties
            else if (mHasFieldParagraphProperties)
            {
                DmlTextField field = (DmlTextField)paragraph.getElements().get(mElementIndex);
                mCurrent = field.getParagraphProperties().getDefaultRunProperties();
                mHasFieldParagraphProperties = false;
            }
            // DmlParagraphTextElementBase.RunProperties
            else
            {
                DmlParagraphTextElementBase element = paragraph.getElements().get(mElementIndex);
                mCurrent = element.getRunProperties();

                DmlTextField field = ms.as(element, DmlTextField.class);
                mHasFieldParagraphProperties =
                    (field != null) &&
                    (field.getParagraphProperties() != null) &&
                    !field.getParagraphProperties().isEmpty() &&
                    field.getParagraphProperties().hasDefaultRunProperties();
            }

            return true;
        }

        public void reset()
        {
            mParagraphIndex = -1;
            mElementIndex = -1;
            mHasFieldParagraphProperties = false;
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        public DmlRunProperties next() { return mCurrent; }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        private /*final*/ ArrayList<DmlParagraph> mParagraphs;
        private /*final*/ boolean mIncludeParagraphBreakProperties;
        private int mParagraphIndex = -1;
        private int mElementIndex = -1;
        private boolean mHasFieldParagraphProperties;
        private DmlRunProperties mCurrent;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }
}

