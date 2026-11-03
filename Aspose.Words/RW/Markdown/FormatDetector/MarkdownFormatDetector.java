// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.CustomTextReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.StringUtil;
import com.aspose.ms.System.IO.Path;
import com.aspose.FileFormatCore;
import com.aspose.FileFormat;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// The class to detect markdown file format.
/// </summary>
/// <remarks>
/// It helps <see cref="FileFormatDetector"/> to confirm or reject an assumption that a provided stream is Markdown.
/// </remarks>
class MarkdownFormatDetector
{
    /// <summary>
    /// Ctor.
    /// </summary>
    private MarkdownFormatDetector(CustomTextReader textReader)
    {
        Debug.assertTrue(textReader != null);
        mContext = new MarkdownDetectorContext(textReader);
    }

    /// <summary>
    /// Returns <see cref="FileFormatInfo"/> if a specified reader represents Markdown file, or <c>null</c> otherwise.
    /// </summary>
    static FileFormatInfo detect(CustomTextReader textReader) throws Exception
    {
        FileStream fileStream = ms.as(textReader.getStream(), FileStream.class);
        if ((fileStream != null) && StringUtil.hasChars(fileStream.getName()))
        {
            String extension = Path.getExtension(fileStream.getName());
            if (FileFormatCore.fromExt(extension) == FileFormat.MARKDOWN)
                return getMarkdownFileFormat(textReader.getEncoding());
        }

        return new MarkdownFormatDetector(textReader).detect();
    }

    /// <summary>
    /// Returns <see cref="FileFormatInfo"/> object if Markdown format is detected, or <c>null</c> otherwise.
    /// </summary>
    /// <remarks>
    /// Should be invoked only if a file extension was not found in <see cref="FileFormatCore"/>.
    /// </remarks>
    private FileFormatInfo detect() throws Exception
    {
        int totalWeight = 0;
        int totalCount = 0;

        while (mContext.readLine())
        {
            // The Markdown format is a text file, so let's limit a number of allowed non-printable characters in it.
            if (((double)mContext.getNonPrintableCharsCount() / mContext.getLength()) > NON_PRINTABLE_CHARS_RATIO)
                return null;

             // WORDSNET-28614 Consider lengths of sentences.
            if (((double)mContext.getLongSentencesTotalLength() / mContext.getLength()) > LONG_SENTENCES_RATIO)
                return null;

            for (MarkdownDetectorBase detector : mMarkdownDetectors)
            {
                if (detector.detectAndUpdateContext(mContext))
                {
                    totalCount += detector.getCount();
                    totalWeight += detector.getWeight();

                    if ((totalWeight >= TOTAL_FEATURES_WEIGHT) && (totalCount >= TOTAL_FEATURES_COUNT))
                        return getMarkdownFileFormat(mContext.getEncoding());
                }
            }
        }

        return null;
    }

    /// <summary>
    /// Returns <see cref="FileFormatInfo"/> object valid for markdown file format.
    /// </summary>
    private static FileFormatInfo getMarkdownFileFormat(Encoding encoding)
    {
        FileFormatInfo fileFormatInfo = new FileFormatInfo();
        fileFormatInfo.setLoadFormat(LoadFormat.MARKDOWN);
        fileFormatInfo.setEncoding(encoding);

        return fileFormatInfo;
    }

    /// <summary>
    /// A context that contains a various data to detect markdown features.
    /// </summary>
    private /*final*/ MarkdownDetectorContext mContext;

    /// <summary>
    /// An array of detectors to try recognize markdown features.
    /// </summary>
    private /*final*/ MarkdownDetectorBase[] mMarkdownDetectors =
    {
        // The order of the detectors is important.
        new QuoteDetector(),
        new HeadingDetector(),
        new HorizontalRuleDetector(),
        new ListDetector(),
        new FencedCodeDetector(),
        new LinkDetector(),
        new EmphasisDetector()
    };

    /// <summary>
    /// A total allowed ratio of non-printable characters in a file.
    /// </summary>
    /// <remarks>It is chosen on the basis of common sense. Feel free to adjust it if needed.</remarks>
    private static final double NON_PRINTABLE_CHARS_RATIO = 0.01;

    /// <summary>
    /// A total allowed ratio of too long sentences in a file.
    /// </summary>
    /// <remarks>It is chosen on the basis of common sense. Feel free to adjust it if needed.</remarks>
    private static final double LONG_SENTENCES_RATIO = 0.9;

    /// <summary>
    /// A total weight of the detected features that allows to treat them as the markdown format.
    /// </summary>
    /// <remarks>It is chosen on the basis of common sense. Feel free to adjust it if needed.</remarks>
    private static final int TOTAL_FEATURES_WEIGHT = 12;

    /// <summary>
    /// A total number of the detected features that allows to treat them as the markdown format.
    /// </summary>
    /// <remarks>It is chosen on the basis of common sense. Feel free to adjust it if needed.</remarks>
    private static final int TOTAL_FEATURES_COUNT = 2;
}

