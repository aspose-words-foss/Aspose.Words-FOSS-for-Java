// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/04/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.msChar;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Caches the INDEX field code for reusing. See corresponding <see cref="FieldIndex"/> properties for any details.
/// </summary>
class FieldCodeIndex
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldCodeIndex(FieldIndex field)
    {
        mBookmarkName = field.getBookmarkName();
        mNumberOfColumns = getNumberOfColumns(field);
        mSequenceSeparator = normalizeSeparator(field.getSequenceSeparator(), "-");
        mPageNumberSeparator = normalizeSeparator(field.getPageNumberSeparator(), ", ");
        mEntryType = FieldIndexAndTablesUtil.getIndexEntryType(field.getEntryType());
        mPageRangeSeparator = normalizeSeparator(field.getPageRangeSeparator(), "–");
        mCrossReferenceSeparator = normalizeSeparator(field.getCrossReferenceSeparator(), ". ");
        mPageNumberListSeparator = normalizeSeparator(field.getPageNumberListSeparator(), ", ");
        mLetterRange = normalizeLetterRange(field.getLetterRange());
        mRunSubentriesOnSameLine = field.getRunSubentriesOnSameLine();
        mSequenceName = field.getSequenceName();
        mUseYomi = field.getUseYomi();
        mLanguageId = field.getLanguageId();

        setHeadingData(field.getHeading());
    }

    /// <summary>
    /// Gets number of columns according to MS Word limitation.
    /// </summary>
    private static int getNumberOfColumns(FieldIndex field)
    {
        // TODO MS Word updates a INDEX field with following error if a number of columns switch has not value:
        // Error! Switch argument not specified.

        final int MAX_NUMBER_OF_COLUMNS = 4;
        final int MIN_NUMBER_OF_COLUMNS = 0;

        int numberOfColumns = field.hasNumberOfColumnsSwitch()
            ? field.getNumberOfColumnsAsInt32().getValueOrDefault(MIN_NUMBER_OF_COLUMNS)
            : MIN_NUMBER_OF_COLUMNS;

        return Math.min(MAX_NUMBER_OF_COLUMNS, Math.max(MIN_NUMBER_OF_COLUMNS, numberOfColumns));
    }

    /// <summary>
    /// Normalizes separator length according to MS Word limitation. If the input value is <c>null</c>,
    /// returns the default separator.
    /// </summary>
    private static String normalizeSeparator(String separator, String defaultSeparator)
    {
        if (separator == null)
            return defaultSeparator;

        final int MAX_SEPARATOR_LENGTH = 15;
        return (separator.length() > MAX_SEPARATOR_LENGTH) ? separator.substring((0), (0) + (MAX_SEPARATOR_LENGTH)) : separator;
    }

    /// <summary>
    /// Returns a string consisting of the first and the last upper-cased letter range characters.
    /// If the input value is <c>null</c>, returns <c>null</c>. If the input range is invalid, returns an empty string.
    /// </summary>
    private static String normalizeLetterRange(String letterRange)
    {
        if (letterRange == null)
            return null;

        letterRange = msString.trim(letterRange);
        if (letterRange.length() < 2)
            return "";

        // MS Word checks and uses just the first and the last letters in the argument.
        char firstLetter = msChar.toUpperInvariant(letterRange.charAt(0));
        char lastLetter = msChar.toUpperInvariant(letterRange.charAt(letterRange.length() - 1));
        if (firstLetter > lastLetter)
            return "";

        return new String(new char[] { firstLetter, lastLetter });
    }

    /// <summary>
    /// Stes the data used to generate a heading (i.e. static heading string or dynamic heading format string).
    /// </summary>
    private void setHeadingData(String heading)
    {
        // Empty heading is not written by MS Word.
        if (!StringUtil.hasChars(heading))
            return;

        boolean isALetterMet = false;
        StringBuilder formatBuilder = new StringBuilder();

        for (int i = 0; i < heading.length(); i++)
        {
        	char c = heading.charAt(i);
            switch (c)
            {
                case 'a':
                case 'A':
                {
                    if (isALetterMet)
                    {
                        // Process any subsequent A-letter in a common way.
                        formatBuilder.append(c);
                    }
                    else
                    {
                        // Replace the first A-letter with a letter placeholder.
                        msStringBuilder.append(formatBuilder, "{0}");
                        isALetterMet = true;
                    }
                    break;
                }
                case '{':
                {
                    // Escape special format character.
                    msStringBuilder.append(formatBuilder, "{{");
                    break;
                }
                case '}':
                {
                    // Escape special format character.
                    msStringBuilder.append(formatBuilder, "}}");
                    break;
                }
                default:
                {
                    if (!isALetterMet && Character.isLetter(c))
                    {
                        // MS Word sets the format to a single space character in this case.
                        mHeading = ControlChar.SPACE;
                        return;
                    }

                    formatBuilder.append(c);
                    break;
                }
            }
        }

        // If A-letter is not met at this point then it is considered to be a static heading.
        if (isALetterMet)
        {
            mHeadingFormat = formatBuilder.toString();
        }
        else
        {
            mHeading = heading;
        }
    }

    /// <summary>
    /// Returns a heading for the given start letter.
    /// </summary>
    String getHeading(char startChar)
    {
        if (mHeading != null)
            return mHeading;

        if (mHeadingFormat != null)
            return msString.format(mHeadingFormat, startChar);

        return null;
    }

    /// <summary>
    /// Gets a value indicating whether the field code provides either static or dynamic heading.
    /// </summary>
    boolean hasHeading() { return (mHeading != null) || (mHeadingFormat != null); }

    /// <summary>
    /// Gets the name of the bookmark that marks the portion of the document used to build the index.
    /// </summary>
    String getBookmarkName() { return mBookmarkName; };

    private String mBookmarkName;

    /// <summary>
    /// Gets a value indicating whether the name of the bookmark that marks the portion of the document
    /// used to build the index is specified via the field code.
    /// </summary>
    boolean hasBookmarkName() { return getBookmarkName() != null; }

    /// <summary>
    /// Gets the number of columns per page used when building the index.
    /// </summary>
    int getNumberOfColumns() { return mNumberOfColumns; };

    private int mNumberOfColumns;

    /// <summary>
    /// Gets a value indicating whether the number of columns per page used when building the index
    /// is specified via the field code.
    /// </summary>
    boolean hasNumberOfColumns() { return getNumberOfColumns() > 0; }

    /// <summary>
    /// Gets the character sequence that is used to separate sequence numbers and page numbers.
    /// </summary>
    String getSequenceSeparator() { return mSequenceSeparator; };

    private String mSequenceSeparator;

    /// <summary>
    /// Gets the character sequence that is used to separate an index entry and its page number.
    /// </summary>
    String getPageNumberSeparator() { return mPageNumberSeparator; };

    private String mPageNumberSeparator;

    /// <summary>
    /// Gets an index entry type used to build the index.
    /// </summary>
    int getEntryType() { return mEntryType; };

    private int mEntryType;

    /// <summary>
    /// Gets the character sequence that is used to separate the start and end of a page range.
    /// </summary>
    String getPageRangeSeparator() { return mPageRangeSeparator; };

    private String mPageRangeSeparator;

    /// <summary>
    /// Gets the character sequence that is used to separate cross references and other entries.
    /// </summary>
    String getCrossReferenceSeparator() { return mCrossReferenceSeparator; };

    private String mCrossReferenceSeparator;

    /// <summary>
    /// Gets the character sequence that is used to separate two page numbers in a page number list.
    /// </summary>
    String getPageNumberListSeparator() { return mPageNumberListSeparator; };

    private String mPageNumberListSeparator;

    /// <summary>
    /// Gets a range of letters to which limit the index. The value is trimmed.
    /// </summary>
    String getLetterRange() { return mLetterRange; };

    private String mLetterRange;

    /// <summary>
    /// Gets a value indicating whether any range of letters to which limit the index
    /// is specified via the field code.
    /// </summary>
    boolean hasLetterRange() { return getLetterRange() != null; }

    /// <summary>
    /// Gets a value indicating whether a valid range of letters to which limit the index
    /// is specified via the field code.
    /// </summary>
    boolean isLetterRangeValid() { return StringUtil.hasChars(getLetterRange()); }

    /// <summary>
    /// Gets whether run subentries into the same line as the main entry.
    /// </summary>
    boolean getRunSubentriesOnSameLine() { return mRunSubentriesOnSameLine; };

    private boolean mRunSubentriesOnSameLine;

    /// <summary>
    /// Gets the name of a sequence whose number is included with the page number.
    /// </summary>
    String getSequenceName() { return mSequenceName; };

    private String mSequenceName;

    /// <summary>
    /// Gets a value indicating whether the name of a sequence whose number is included with the page number
    /// is specified via the field code.
    /// </summary>
    boolean hasSequenceName() { return getSequenceName() != null; }

    /// <summary>
    /// Gets whether to enable the use of yomi text for index entries.
    /// </summary>
    boolean getUseYomi() { return mUseYomi; };

    private boolean mUseYomi;

    /// <summary>
    /// Gets the language ID used to generate the index.
    /// </summary>
    String getLanguageId() { return mLanguageId; };

    private String mLanguageId;

    private String mHeading;
    private String mHeadingFormat;
}

