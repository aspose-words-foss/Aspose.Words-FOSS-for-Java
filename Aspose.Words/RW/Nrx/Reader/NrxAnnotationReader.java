// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// This class provides common code for DOCX and WML annotation readers.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class NrxAnnotationReader
{
    NrxAnnotationReader(NrxRunPrReaderBase runPrReader,
        NrxParaPrReaderBase paraPrReader)
    {
        Debug.assertTrue(runPrReader != null);
        Debug.assertTrue(paraPrReader != null);
        mRunPrReader = runPrReader;
        mParaPrReader = paraPrReader;
    }

    /// <summary>
    /// In DOCX and WML we have AfterChanges and BeforeChanges properties, but in the model
    /// we need to have BeforeChanges and PositiveDifference. 
    /// This method reads previous formatting properties (BeforeChanges) and converts to the model way. 
    /// The AfterChanges must be already read and the reader must have the paragraph as the current container.
    /// </summary>
    void readParaPr(NrxDocumentReaderBase reader, ParaPr paraPr, RunPr paragraphBreakRunPr, NrxAnnotation annotation) throws Exception
    {
        // This is now AfterChanges.
        ParaPr revPr = AttrCollectionExtension.deepClone(paraPr);

        // Read the BeforeChanges into the original runPr because we do not return a new collection.
        paraPr.clear();
        mParaPrReader.read(reader, paraPr, paragraphBreakRunPr);

        revPr.moveTo(paraPr, RevisionAttr.NUMBER_REVISION);
        revPr.moveTo(paraPr, ParaAttr.RSID_P);

        paraPr.setFormatRevision(new FormatRevision(revPr, annotation.getAuthor(), annotation.getDate()));
    }

    void readRunPr(NrxDocumentReaderBase reader, RunPr runPr, NrxAnnotation annotation) throws Exception
    {
        // This is now AfterChanges.
        RunPr revPr = AttrCollectionExtension.deepClone(runPr);

        // Read the BeforeChanges into the original runPr because we do not return a new collection.
        runPr.clear();
        mRunPrReader.read(reader, runPr);

        // WORDSNET-23778 Ignore a nested insertion revision.
        runPr.remove(RevisionAttr.INSERT_REVISION);

        revPr.moveTo(runPr, RevisionAttr.INSERT_REVISION);
        revPr.moveTo(runPr, RevisionAttr.DELETE_REVISION);
        revPr.moveTo(runPr, RevisionAttr.MOVE_FROM_REVISION);
        revPr.moveTo(runPr, RevisionAttr.MOVE_TO_REVISION);
        revPr.moveTo(runPr, FontAttr.RSID_R);
        revPr.moveTo(runPr, FontAttr.RSID_R_PR);

        runPr.setFormatRevision(new FormatRevision(revPr, annotation.getAuthor(), annotation.getDate()));
    }

    /// <summary>
    /// Sets numbering related properties of NumberRevision according to w:original attribute value.
    /// Described in ECMA TC 45: 2.13.5.30 numberingChange (Previous Paragraph Numbering Properties).
    /// Example: "%1:3:0:-%2:1:0:%3:1:4:.".
    /// </summary>
    static void readOriginal(ParagraphNumberRevision numberRevision, String original)
    {
        // Position in w:original string.
        int pos = 0;

        // Index into NumberRevision array properties (NumberLocations, NumberStyles, NumberValues).
        int index = 0;

        StringBuilder numFormatBuilder = new StringBuilder();

        while (pos < original.length())
        {
            // I have seen w:original to be "-" sometimes. I don't know how to parse such cases,
            // so I simply skip them for now.
            if (original.charAt(pos) != '%')
                break;

            pos++;

            numFormatBuilder.append((char)(original.charAt(pos) - '1'));
            // WORDSNET-5583 By storing number location after appending we solve the problem.
            // RK It is strange, but does it look like indexes in NumberLocations are 1-based?
            numberRevision.getNumberLocations()[index] = (byte)numFormatBuilder.length();

            pos += 2; // Skip ':'.
            numberRevision.getNumberValues()[index] = original.charAt(pos) - '0';
            pos += 2; // Skip ':'.
            numberRevision.getNumberStyles()[index] = (/*NumberStyle*/int)(original.charAt(pos) - '0');
            pos += 2; // Skip ':'.

            if ((pos < original.length()) && original.charAt(pos) != '%')
            {
                numFormatBuilder.append(original.charAt(pos));
                pos++;
            }

            index++;
        }

        numberRevision.setNumberFormat(numFormatBuilder.toString());
    }

    private /*final*/ NrxRunPrReaderBase mRunPrReader;
    private /*final*/ NrxParaPrReaderBase mParaPrReader;
}

