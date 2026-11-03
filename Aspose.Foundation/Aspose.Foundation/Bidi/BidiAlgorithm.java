// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2014 by Victor Chebotok

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msArray;


/// <summary>
/// Implements the Unicode bidirectional algorithm.
/// </summary>
/// <remarks>
/// The Unicode bidirectional algorithm examines implicit and explicit directionality of text characters and splits
/// the text into left-to-right and right-to-left runs.
/// For description of the algorithm see http://www.unicode.org/reports/tr9/
/// Our implementation also reorders the resulting runs in some cases to convert the text from visual to logical order
/// and to arrange runs as required by MS Word.
/// </remarks>
/// JAVA: mData and gFieldMasks arrays type is changed from unsigned long to (signed) long for autoportability to Java.
public class BidiAlgorithm
{
    /// <summary>
    /// Processes text with the Unicode bidirectional algorithm.
    /// </summary>
    /// <param name="text">The text to process.</param>
    /// <param name="isRtlParagraph">
    /// Indicates the direction of the paragraph that the text belongs to.
    /// <c>false</c> - left-to-right (LTR), <c>true</c> - right-to-left (RTL).
    /// </param>
    /// <returns>The text split into an array of runs by bidirectional embedding level.</returns>
    public static BidiRun[] apply(String text, boolean isRtlParagraph)
    {
        return apply(new BidiSourceRun[] { new BidiSourceRun(text) }, isRtlParagraph, false, false);
    }

    /// <summary>
    /// Processes text with the Unicode bidirectional algorithm.
    /// </summary>
    /// <param name="sourceRuns">Runs of text to process.</param>
    /// <param name="isRtlParagraph">
    /// Indicates the direction of the paragraph that the text belongs to.
    /// <c>false</c> - left-to-right (LTR), <c>true</c> - right-to-left (RTL).
    /// </param>
    /// <param name="isVisuallyOrdered"></param>
    /// <param name="convertToMsWordOrder">
    /// Indicates whether the resulting runs must be rearranged in order required by MS Word.
    /// If this flag is <c>false</c>, the algorithm will split the text into runs as described in the specification.
    /// If this flag is <c>true</c>, the resulting runs will be additionally processed to put them into the document model
    /// (embedded RTL fragments will be reversed and level of numeric runs will be decreased).
    /// </param>
    /// <returns>The text split into an array of runs by bidirectional embedding level.</returns>
    public static BidiRun[] apply(BidiSourceRun[] sourceRuns, boolean isRtlParagraph, boolean isVisuallyOrdered,
        boolean convertToMsWordOrder)
    {
        BidiAlgorithm algorithm = new BidiAlgorithm(sourceRuns, isRtlParagraph, isVisuallyOrdered, convertToMsWordOrder);
        algorithm.apply();
        return algorithm.getResult();
    }

    /// <summary>
    /// Processes text of a single paragraph with the Unicode bidirectional algorithm the way MS Word forms a field result.
    /// </summary>
    /// <remarks>
    /// MS Word does not raise a number above the level of its context: every European or Arabic number gets the embedding
    /// level of the character before it, or the paragraph level at the start of the text, although rules I1 and I2
    /// of the algorithm say otherwise.
    /// </remarks>
    /// <param name="text">The text to process. Must not contain paragraph separators.</param>
    /// <param name="isRtlParagraph">
    /// Indicates the direction of the paragraph that the text belongs to.
    /// <c>false</c> - left-to-right (LTR), <c>true</c> - right-to-left (RTL).
    /// </param>
    /// <returns>The text split into an array of runs by embedding level, in logical order.</returns>
    public static BidiRun[] applyToFieldResult(String text, boolean isRtlParagraph)
    {
        BidiAlgorithm algorithm = new BidiAlgorithm(new BidiSourceRun[] { new BidiSourceRun(text) }, isRtlParagraph,
            false, false, true, false);
        algorithm.apply();
        return algorithm.getResult();
    }

    /// <summary>
    /// Processes text of a single paragraph with the Unicode bidirectional algorithm to insert it into the document model.
    /// </summary>
    /// <remarks>
    /// Numeric runs are put into adjacent runs as described for <see cref="Apply(BidiSourceRun[],bool,bool,bool)"/>.
    /// Unlike that method, the runs keep their logical order: they are only reversed as a whole, when the direction
    /// of the text differs from the direction of the paragraph.
    /// </remarks>
    /// <param name="text">The text to process. Must not contain paragraph separators.</param>
    /// <param name="isRtlParagraph">
    /// Indicates the direction of the paragraph that the text belongs to.
    /// <c>false</c> - left-to-right (LTR), <c>true</c> - right-to-left (RTL).
    /// </param>
    /// <returns>The text split into an array of runs.</returns>
    public static BidiRun[] applyToInsertedText(String text, boolean isRtlParagraph)
    {
        BidiAlgorithm algorithm = new BidiAlgorithm(new BidiSourceRun[] { new BidiSourceRun(text) }, isRtlParagraph,
            false, true, false, true);
        algorithm.apply();
        return algorithm.getResult();
    }

    /// <summary>
    /// Determines implicit paragraph level of text.
    /// </summary>
    /// <remarks>
    /// Corresponds to the P2 rule of the Unicode bidirectional algorithm (see https://unicode.org/reports/tr9/#P2).
    /// </remarks>
    public static boolean isRtlParagraph(String text)
    {
        // Search for a strong character.
        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            /*BidiCharacterType*/int bidiType = UnicodeCharacterDataResolver.getBidiCharacterType(c);
            if ((bidiType == BidiCharacterType.R) || (bidiType == BidiCharacterType.AL))
            {
                // Found an RTL strong character.
                return true;
            }
            else if (bidiType == BidiCharacterType.L)
            {
                // Found an LTR strong character.
                return false;
            }
        }
        // No strong characters found.
        return false;
    }

    /// <summary>
    /// Reorders the runs so that they come in visual order and don't have nested LTR/RTL ranges.
    /// </summary>
    /// <remarks>
    /// Uses the reordering part of the Unicode bidirectional algorithm, described here:
    /// http://www.unicode.org/reports/tr9/#L2
    /// </remarks>
    public static void arrangeInVisualOrder(BidiRun[] runs)
    {
        // We use zero paragraph embedding level that corresponds to LTR paragraph direction, because our renderers
        // move from left to right when they populate the APS model with glyphs.
        flatternEmbeddingLevels(runs, 0);
    }

    /// <summary>
    /// Processes text with the Unicode bidirectional algorithm.
    /// </summary>
    /// <param name="sourceRuns">Runs of text to process.</param>
    /// <param name="isRtlParagraph">
    /// Indicates the direction of the paragraph that the text belongs to.
    /// <c>false</c> - left-to-right (LTR), <c>true</c> - right-to-left (RTL).
    /// </param>
    /// <param name="isVisuallyOrdered"></param>
    /// <param name="convertToMsWordOrder">
    /// Indicates whether the resulting runs must be rearranged in order required by MS Word.
    /// If this flag is <c>false</c>, the algorithm will split the text into runs as described in the specification.
    /// If this flag is <c>true</c>, the resulting runs will be additionaly processed to put them into the document model
    /// (embedded RTL fragments will be reversed and level of numeric runs will be decreased).
    /// </param>
    /// <returns>The text split into an array of runs by bidirectional embedding level.</returns>
    private BidiAlgorithm(BidiSourceRun[] sourceRuns, boolean isRtlParagraph, boolean isVisuallyOrdered, boolean convertToMsWordOrder)
    {
    	this(sourceRuns, isRtlParagraph, isVisuallyOrdered, convertToMsWordOrder, false, false);
	
        // Empty constructor.
    }

    /// <param name="numbersTakePrecedingLevel">
    /// Indicates whether numbers get the embedding level of the preceding text, see <see cref="ApplyToFieldResult"/>.
    /// </param>
    /// <param name="keepLogicalOrder">
    /// Indicates whether runs in MS Word order keep their logical order, see <see cref="ApplyToInsertedText"/>.
    /// </param>
    private BidiAlgorithm(BidiSourceRun[] sourceRuns, boolean isRtlParagraph, boolean isVisuallyOrdered, boolean convertToMsWordOrder,
        boolean numbersTakePrecedingLevel, boolean keepLogicalOrder)
    {
        mConvertVisualToLogicalOrder = isVisuallyOrdered;
        mConvertToMsWordOrder = convertToMsWordOrder;
        mNumbersTakePrecedingLevel = numbersTakePrecedingLevel;
        mKeepLogicalOrder = keepLogicalOrder;
        mIsRtlParagraph = isRtlParagraph;

        // Set the paragraph embedding level.
        // WORDSNET-7347 Visually ordered text is an RTL text which includes some LTR fragments,
        // so it should be processed as an RTL text by the bidirectional algorithm.
        mParagraphEmbeddingLevel = (isRtlParagraph || mConvertVisualToLogicalOrder) ? 1 : 0;

        // If text is in visual order, its runs are also in visual order.
        // To convert visually ordered text of an RTL paragraph to logical order, we need to reverse the order of the runs.
        boolean reverseRunOrder = (mConvertVisualToLogicalOrder && isRtlParagraph);

        initialize(sourceRuns, reverseRunOrder);
    }

    /// <summary>
    /// Fills data structures used by the Unicode bidirectional algorithm.
    /// </summary>
    private void initialize(BidiSourceRun[] sourceRuns, boolean reverseRunOrder)
    {
        Debug.assertTrue(!mAlgorithmIsApplied);

        // Calculate the text length.
        BidiLevelList activeLevels = new BidiLevelList();
        int textLength = 0;
        for (int i = 0; i < sourceRuns.length; i++)
        {
            BidiSourceRun run = sourceRuns[i];
            textLength += run.getText().length();
            textLength += getTransitionControlCharacters(activeLevels, run.getLevels()).length;
            activeLevels = run.getLevels();
        }
        textLength += getTrailingControlCharacters(activeLevels).length;

        // Allocate space for data structures.
        mData = new long[textLength];

        // Fill the data required by the algorithm.
        activeLevels = new BidiLevelList();
        int charIndex = 0;
        for (int i = 0; i < sourceRuns.length; i++)
        {
            int runIndex = (reverseRunOrder)
                ? sourceRuns.length - i - 1
                : i;
            BidiSourceRun run = sourceRuns[runIndex];

            // Write transition bidi control characters.
            for (char controlChar : getTransitionControlCharacters(activeLevels, run.getLevels()))
            {
                initializeChar(charIndex, controlChar, runIndex);
                ++charIndex;
            }
            activeLevels = run.getLevels();

            // Write the run text.
            for (int j = 0; j < run.getText().length(); j++)
            {
                initializeChar(charIndex, run.getText().charAt(j), runIndex, run.getUseHebrewNumberSeparators(), run.getIgnoreBidiTypeET());
                ++charIndex;
            }
        }
        // Write trailing bidi control characters.
        for (char controlChar : getTrailingControlCharacters(activeLevels))
        {
            initializeChar(charIndex, controlChar, sourceRuns.length - 1);
            ++charIndex;
        }
    }

    /// <summary>
    /// Gets Unicode control characters needed to change from one stack of bidi levels to another.
    /// </summary>
    private static char[] getTransitionControlCharacters(BidiLevelList fromLevels, BidiLevelList toLevels)
    {
        Debug.assertTrue(fromLevels != null);
        Debug.assertTrue(toLevels != null);

        // Count the number of matched levels.
        int matchCount = 0;
        while ((matchCount < fromLevels.getLength()) &&
            (matchCount < toLevels.getLength()) &&
            (fromLevels.get(matchCount) == toLevels.get(matchCount)))
        {
            ++matchCount;
        }

        int resultLength = (fromLevels.getLength() - matchCount) + (toLevels.getLength() - matchCount);
        char[] result = new char[resultLength];
        int resultIndex = 0;

        // Pop all previous unmatched levels.
        for (int j = matchCount; j < fromLevels.getLength(); j++)
        {
            result[resultIndex] = BidiChars.PDF;
            ++resultIndex;
        }

        // Open new unmatched levels.
        for (int j = matchCount; j < toLevels.getLength(); j++)
        {
            result[resultIndex] = getOpeningControlChar(toLevels.get(j));
            ++resultIndex;
        }

        return result;
    }

    /// <summary>
    /// Gets a Unicode bidi control character that opens the specified bidi level.
    /// </summary>
    private static char getOpeningControlChar(/*BidiLevel*/int level)
    {
        switch (level)
        {
            case BidiLevel.EMBED_LTR:
                return BidiChars.LRE;
            case BidiLevel.EMBED_RTL:
                return BidiChars.RLE;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "level");
        }
    }

    /// <summary>
    /// Gets Unicode control characters needed to close all of the specified bidi levels.
    /// </summary>
    private static char[] getTrailingControlCharacters(BidiLevelList levels)
    {
        Debug.assertTrue(levels != null);
        return getTransitionControlCharacters(levels, new BidiLevelList());
    }

    private void initializeChar(int index, char c, int sourceRunIndex)
    {
        initializeChar(index, c, sourceRunIndex, true, false);
    }

    private void initializeChar(int index, char c, int sourceRunIndex, boolean useHebrewSeparators, boolean ignoreBidiTypeET)
    {
        setChar(index, c);

        /*BidiCharacterType*/int bidiType = UnicodeCharacterDataResolver.getBidiCharacterType(c);

        if (mIsRtlParagraph && bidiType == BidiCharacterType.ET && ignoreBidiTypeET)
        {
            bidiType = BidiCharacterType.ON;
        }

        setBidiType(index, bidiType);
        setEmbeddingLevel(index, 0);
        setSourceRunIndex(index, sourceRunIndex);
        setUseHebrewSeparators(index, useHebrewSeparators);
    }

    private char getChar(int index)
    {
        return (char)getFieldValue(index, CHAR_FIELD_INDEX);
    }

    private void setChar(int index, char c)
    {
        setFieldValue(index, CHAR_FIELD_INDEX, c);
    }

    private /*BidiCharacterType*/int getBidiType(int index)
    {
        return (/*BidiCharacterType*/int)getFieldValue(index, BIDI_TYPE_FIELD_INDEX);
    }

    private void setBidiType(int index, /*BidiCharacterType*/int bidiCharacterType)
    {
        setFieldValue(index, BIDI_TYPE_FIELD_INDEX, (long)bidiCharacterType);
    }

    private int getEmbeddingLevel(int index)
    {
        return (int)getFieldValue(index, EMBEDDING_LEVEL_FIELD_INDEX);
    }

    private void setEmbeddingLevel(int index, int embeddingLevel)
    {
        setFieldValue(index, EMBEDDING_LEVEL_FIELD_INDEX, (long)embeddingLevel);
    }

    private boolean getRequiresHebrewLocale(int index)
    {
        return getFieldValue(index, REQUIRES_HEBREW_LOCALE_FIELD_INDEX) != 0;
    }

    private void setRequiresHebrewLocale(int index, boolean value)
    {
        setFieldValue(index, REQUIRES_HEBREW_LOCALE_FIELD_INDEX, (value) ? 1L : 0L);
    }

    private boolean getUseHebrewSeparators(int index)
    {
        return getFieldValue(index, USE_HEBREW_SEPARATORS_FIELD_INDEX) != 0;
    }

    private void setUseHebrewSeparators(int index, boolean value)
    {
        setFieldValue(index, USE_HEBREW_SEPARATORS_FIELD_INDEX, (value) ? 1L : 0L);
    }

    private int getSourceRunIndex(int index)
    {
        return (int)getFieldValue(index, SOURCE_RUN_INDEX_FIELD_INDEX);
    }

    private void setSourceRunIndex(int index, int sourceRunIndex)
    {
        setFieldValue(index, SOURCE_RUN_INDEX_FIELD_INDEX, (long)sourceRunIndex);
    }

    private long getFieldValue(int index, int fieldIndex)
    {
        //JAVA-changed:
        return (mData[index] & G_FIELD_MASKS[fieldIndex]) >>> G_FIELD_SHIFTS[fieldIndex];
    }

    private void setFieldValue(int index, int fieldIndex, long value)
    {
        // We are packing a value into a limited-length field. Let's make sure the value actually fits the field and we
        // don't clear any significant value bits by applying the mask. All value bits outside the mask should be zero.
        // If the value don't fit the target field, the field should be resized.
        Debug.assertTrue((value & (~(G_FIELD_MASKS[fieldIndex] >> G_FIELD_SHIFTS[fieldIndex]))) == 0);

        long oldValue = mData[index];
        long newFieldValue = (value << G_FIELD_SHIFTS[fieldIndex]) & G_FIELD_MASKS[fieldIndex];
        // Re-pack data. Merge the masked value with other fields. The code is taken from here:
        // https://graphics.stanford.edu/~seander/bithacks.html#MaskedMerge
        mData[index] = oldValue ^ ((oldValue ^ newFieldValue) & G_FIELD_MASKS[fieldIndex]);
    }

    /// <summary>
    /// Applies the Unicode bidirectional algorithm to the text.
    /// </summary>
    private void apply()
    {
        // The bidirectional algorithm must be applied to the text only once.
        Debug.assertTrue(!mAlgorithmIsApplied);
        if (mAlgorithmIsApplied)
        {
            return;
        }

        resolveExplicitLevels();

        // X10. The remaining rules are applied to each sequence of characters at the same level.
        ArrayList<BidiAlgorithm.Sequence> sequences = new ArrayList<BidiAlgorithm.Sequence>();
        int start = 0;
        while (start < mData.length)
        {
            int level = getEmbeddingLevel(start);

            int limit = start + 1;
            while ((limit < mData.length) && (getEmbeddingLevel(limit) == level))
            {
                ++limit;
            }

            int prevLevel = mParagraphEmbeddingLevel;
            for (int i = start - 1; i >= 0; i--)
            {
                if (getBidiType(i) != BidiCharacterType.BN)
                {
                    prevLevel = getEmbeddingLevel(i);
                    break;
                }
            }

            int nextLevel = mParagraphEmbeddingLevel;
            for (int i = limit; i < mData.length; i++)
            {
                if (getBidiType(i) != BidiCharacterType.BN)
                {
                    nextLevel = getEmbeddingLevel(i);
                    break;
                }
            }

            /*BidiCharacterType*/int sor = getDirectionOfLevel(Math.max(prevLevel, level));
            /*BidiCharacterType*/int eor = getDirectionOfLevel(Math.max(nextLevel, level));

            msArrayList.add(sequences, new Sequence(start, limit, sor, eor, level));
            start = limit;
        }
        for (Sequence sequence : (Iterable<Sequence>) sequences)
        {
            resolveWeakTypes(sequence);
            resolveNeutralTypes(sequence);
        }

        resolveImplicitLevels();

        if (mNumbersTakePrecedingLevel)
        {
            putNumbersOnPrecedingLevel();
        }

        resolveSeparators();

        if (mConvertVisualToLogicalOrder)
        {
            convertVisualToLogicalOrder();
        }

        if (mConvertToMsWordOrder)
        {
            adjustLevelOfNumericRuns();
        }

        // Set the flag to prevent repeated application of bidirectional algorithm.
        mAlgorithmIsApplied = true;
    }

    /// <summary>
    /// Resolves the explicit embedding levels of the characters (rules X1-X9).
    /// </summary>
    /// <remarks>
    /// Embedding, override and PDF characters and characters of the BN class stay in the text: they get the level
    /// of the last character that got a level of its own and become BN, as section 5.2 of the algorithm
    /// ("Retaining BNs and Explicit Formatting Characters") describes.
    /// </remarks>
    private void resolveExplicitLevels()
    {
        // The current embedding: its level and its directional override (L or R, or ON when there is none).
        int currentLevel = mParagraphEmbeddingLevel;
        /*BidiCharacterType*/int currentOverride = BidiCharacterType.ON;

        // The embeddings enclosing the current one, the innermost last. Every item packs a level and an override.
        IntList enclosingEmbeddings = new IntList();

        // The level for retained formatting characters.
        int lastOwnLevel = mParagraphEmbeddingLevel;

        for (int i = 0; i < mData.length; i++)
        {
            /*BidiCharacterType*/int bidiType = getBidiType(i);

            if (bidiType == BidiCharacterType.B)
            {
                // X8. A paragraph separator gets the paragraph level and ends all embeddings and overrides.
                setEmbeddingLevel(i, mParagraphEmbeddingLevel);
                currentLevel = mParagraphEmbeddingLevel;
                currentOverride = BidiCharacterType.ON;
                enclosingEmbeddings.clear();
                continue;
            }

            if (isEmbeddingStart(bidiType))
            {
                // X2-X5. Start an embedding one or two levels higher: at an odd level for RLE and RLO, at an even
                // level for LRE and LRO. An embedding that would go too deep is ignored.
                boolean isRtl = (bidiType == BidiCharacterType.RLE) || (bidiType == BidiCharacterType.RLO);
                int maxEnclosingLevel = isRtl
                    ? MAX_LEVEL_BELOW_RTL_EMBEDDING
                    : MAX_LEVEL_BELOW_LTR_EMBEDDING;
                if (currentLevel <= maxEnclosingLevel)
                {
                    enclosingEmbeddings.add((currentLevel << 8) | (int)currentOverride);

                    currentLevel = isRtl
                        ? (currentLevel + 1) | 1
                        : (currentLevel + 2) & ~1;

                    if (bidiType == BidiCharacterType.RLO)
                        currentOverride = BidiCharacterType.R;
                    else if (bidiType == BidiCharacterType.LRO)
                        currentOverride = BidiCharacterType.L;
                    else
                        currentOverride = BidiCharacterType.ON;
                }
            }
            else if ((bidiType == BidiCharacterType.PDF) && (enclosingEmbeddings.getCount() > 0))
            {
                // X7. PDF returns to the enclosing embedding, if there is one.
                int enclosing = enclosingEmbeddings.get(enclosingEmbeddings.getCount() - 1);
                enclosingEmbeddings.removeAt(enclosingEmbeddings.getCount() - 1);
                currentLevel = enclosing >> 8;
                currentOverride = (/*BidiCharacterType*/int)(enclosing & 0xFF);
            }

            if (isEmbeddingStart(bidiType) || (bidiType == BidiCharacterType.PDF) || (bidiType == BidiCharacterType.BN))
            {
                // Retained formatting character (X9 removes it in the plain algorithm).
                setEmbeddingLevel(i, lastOwnLevel);
                setBidiType(i, BidiCharacterType.BN);
            }
            else
            {
                // X6. Any other character gets the current level and, under an override, the override direction.
                setEmbeddingLevel(i, currentLevel);
                lastOwnLevel = currentLevel;
                if (currentOverride != BidiCharacterType.ON)
                    setBidiType(i, currentOverride);
            }
        }
    }

    /// <summary>
    /// Resolves the weak types of a level run (rules W1-W7).
    /// </summary>
    /// <remarks>
    /// Retained BN characters are skipped when the rules look for adjacent characters, and they join the numbers,
    /// terminators and separators next to them.
    /// </remarks>
    private void resolveWeakTypes(Sequence sequence)
    {
        resolveNonspacingMarks(sequence);
        resolveEuropeanNumbersAfterArabicLetters(sequence);
        replaceTypes(sequence, BidiCharacterType.AL, BidiCharacterType.R); // W3
        resolveSeparatorsBetweenNumbers(sequence);
        resolveTerminatorsNextToEuropeanNumbers(sequence);
        resolveRemainingSeparatorsAndTerminators(sequence);
        resolveEuropeanNumbersAfterLeftToRightText(sequence);
    }

    /// <summary>
    /// W1. A nonspacing mark takes the type of the character before it, or the start-of-sequence type.
    /// </summary>
    private void resolveNonspacingMarks(Sequence sequence)
    {
        /*BidiCharacterType*/int typeBefore = sequence.getStartOfSequence();
        for (int i = sequence.getStart(); i < sequence.getLimit(); i++)
        {
            /*BidiCharacterType*/int bidiType = getBidiType(i);
            if (bidiType == BidiCharacterType.NSM)
            {
                setBidiType(i, typeBefore);
            }
            else if (bidiType != BidiCharacterType.BN)
            {
                typeBefore = bidiType;
            }
        }
    }

    /// <summary>
    /// W2. A European number preceded by an Arabic letter (the closest strong character before it) is an Arabic number.
    /// </summary>
    private void resolveEuropeanNumbersAfterArabicLetters(Sequence sequence)
    {
        /*BidiCharacterType*/int lastStrongType = sequence.getStartOfSequence();
        for (int i = sequence.getStart(); i < sequence.getLimit(); i++)
        {
            /*BidiCharacterType*/int bidiType = getBidiType(i);
            if ((bidiType == BidiCharacterType.L) || (bidiType == BidiCharacterType.R) || (bidiType == BidiCharacterType.AL))
            {
                lastStrongType = bidiType;
            }
            else if ((bidiType == BidiCharacterType.EN) && (lastStrongType == BidiCharacterType.AL))
            {
                setBidiType(i, BidiCharacterType.AN);
            }
        }
    }

    /// <summary>
    /// W4. A single European separator between two European numbers, or a single common separator between two numbers
    /// of the same type, becomes part of the numbers.
    /// </summary>
    /// <remarks>
    /// In a right-to-left paragraph '+', '-' and '/' join numbers only if all the characters involved allow it
    /// (see <see cref="BidiSourceRun.UseHebrewNumberSeparators"/>).
    /// </remarks>
    private void resolveSeparatorsBetweenNumbers(Sequence sequence)
    {
        for (int i = sequence.getStart() + 1; i < sequence.getLimit() - 1; i++)
        {
            /*BidiCharacterType*/int separatorType = getBidiType(i);
            if ((separatorType != BidiCharacterType.ES) && (separatorType != BidiCharacterType.CS))
                continue;

            int numberBefore = findNonBoundaryNeutral(i - 1, -1, sequence);
            int numberAfter = findNonBoundaryNeutral(i + 1, 1, sequence);
            if ((numberBefore < 0) || (numberAfter < 0))
                continue;

            /*BidiCharacterType*/int numberType = getBidiType(numberBefore);
            boolean isSeparatorOfNumberType = (numberType == BidiCharacterType.EN) ||
                ((numberType == BidiCharacterType.AN) && (separatorType == BidiCharacterType.CS));
            if (!isSeparatorOfNumberType || (getBidiType(numberAfter) != numberType))
                continue;

            if (mIsRtlParagraph && isHebrewNumberSeparator(getChar(i)) && !useHebrewSeparators(numberBefore, numberAfter))
                continue;

            setBidiTypes(numberBefore + 1, numberAfter, numberType);
        }
    }

    /// <summary>
    /// Returns <c>true</c>, if all characters in the specified inclusive range may use Hebrew number separators.
    /// </summary>
    private boolean useHebrewSeparators(int first, int last)
    {
        for (int i = first; i <= last; i++)
        {
            if (!getUseHebrewSeparators(i))
                return false;
        }
        return true;
    }

    /// <summary>
    /// W5. A sequence of European terminators next to a European number becomes part of the number.
    /// </summary>
    private void resolveTerminatorsNextToEuropeanNumbers(Sequence sequence)
    {
        int i = sequence.getStart();
        while (i < sequence.getLimit())
        {
            /*BidiCharacterType*/int bidiType = getBidiType(i);
            if ((bidiType != BidiCharacterType.ET) && (bidiType != BidiCharacterType.BN))
            {
                ++i;
                continue;
            }

            int end = i;
            boolean hasTerminator = false;
            while ((end < sequence.getLimit()) &&
                ((getBidiType(end) == BidiCharacterType.ET) || (getBidiType(end) == BidiCharacterType.BN)))
            {
                hasTerminator |= getBidiType(end) == BidiCharacterType.ET;
                ++end;
            }

            // The start-of-sequence and end-of-sequence types are never European numbers.
            boolean isNextToNumber = ((i > sequence.getStart()) && (getBidiType(i - 1) == BidiCharacterType.EN)) ||
                ((end < sequence.getLimit()) && (getBidiType(end) == BidiCharacterType.EN));
            if (hasTerminator && isNextToNumber)
                setBidiTypes(i, end, BidiCharacterType.EN);

            i = end;
        }
    }

    /// <summary>
    /// W6. Separators and terminators that are left become other neutrals.
    /// </summary>
    private void resolveRemainingSeparatorsAndTerminators(Sequence sequence)
    {
        int i = sequence.getStart();
        while (i < sequence.getLimit())
        {
            if (!isSeparatorTerminatorOrBoundaryNeutral(getBidiType(i)))
            {
                ++i;
                continue;
            }

            // Retained BN characters are resolved together with adjacent separators and terminators, if there are any.
            int end = i;
            boolean hasSeparatorOrTerminator = false;
            while ((end < sequence.getLimit()) && isSeparatorTerminatorOrBoundaryNeutral(getBidiType(end)))
            {
                hasSeparatorOrTerminator |= getBidiType(end) != BidiCharacterType.BN;
                ++end;
            }

            if (hasSeparatorOrTerminator)
                setBidiTypes(i, end, BidiCharacterType.ON);

            i = end;
        }
    }

    /// <summary>
    /// W7. A European number preceded by left-to-right text (the closest strong character before it) becomes L.
    /// </summary>
    private void resolveEuropeanNumbersAfterLeftToRightText(Sequence sequence)
    {
        /*BidiCharacterType*/int lastStrongType = sequence.getStartOfSequence();
        for (int i = sequence.getStart(); i < sequence.getLimit(); i++)
        {
            /*BidiCharacterType*/int bidiType = getBidiType(i);
            if ((bidiType == BidiCharacterType.L) || (bidiType == BidiCharacterType.R))
            {
                lastStrongType = bidiType;
            }
            else if ((bidiType == BidiCharacterType.EN) && (lastStrongType == BidiCharacterType.L))
            {
                setBidiType(i, BidiCharacterType.L);
            }
        }
    }

    /// <summary>
    /// Resolves the neutral types of a level run (rules N1 and N2).
    /// </summary>
    /// <remarks>
    /// N1. Neutrals between two characters of the same direction take that direction; numbers count as right-to-left.
    /// N2. Other neutrals take the direction of the level run.
    /// </remarks>
    private void resolveNeutralTypes(Sequence sequence)
    {
        int i = sequence.getStart();
        while (i < sequence.getLimit())
        {
            if (!isNeutralOrBoundaryNeutral(getBidiType(i)))
            {
                ++i;
                continue;
            }

            int end = i;
            while ((end < sequence.getLimit()) && isNeutralOrBoundaryNeutral(getBidiType(end)))
            {
                ++end;
            }

            /*BidiCharacterType*/int directionBefore = (i == sequence.getStart())
                ? sequence.getStartOfSequence()
                : getDirectionForNeutrals(getBidiType(i - 1));
            /*BidiCharacterType*/int directionAfter = (end == sequence.getLimit())
                ? sequence.getEndOfSequence()
                : getDirectionForNeutrals(getBidiType(end));

            setBidiTypes(i, end, (directionBefore == directionAfter)
                ? directionBefore
                : getDirectionOfLevel(sequence.getEmbeddingLevel()));

            i = end;
        }
    }

    /// <summary>
    /// Returns the direction that a character of a resolved strong or number type gives to adjacent neutrals.
    /// </summary>
    private static /*BidiCharacterType*/int getDirectionForNeutrals(/*BidiCharacterType*/int bidiType)
    {
        return ((bidiType == BidiCharacterType.EN) || (bidiType == BidiCharacterType.AN))
            ? BidiCharacterType.R
            : bidiType;
    }

    /// <summary>
    /// Resolves the implicit embedding levels of the characters (rules I1 and I2).
    /// </summary>
    /// <remarks>
    /// I1. On an even level, right-to-left characters go one level up and numbers go two levels up.
    /// I2. On an odd level, left-to-right characters and numbers go one level up.
    /// </remarks>
    private void resolveImplicitLevels()
    {
        for (int i = 0; i < mData.length; i++)
        {
            /*BidiCharacterType*/int bidiType = getBidiType(i);
            boolean isNumber = (bidiType == BidiCharacterType.EN) || (bidiType == BidiCharacterType.AN);
            int level = getEmbeddingLevel(i);

            int increase;
            if ((level % 2) == 0)
            {
                if (bidiType == BidiCharacterType.R)
                    increase = 1;
                else if (isNumber)
                    increase = 2;
                else
                    increase = 0;
            }
            else
            {
                increase = ((bidiType == BidiCharacterType.L) || isNumber)
                    ? 1
                    : 0;
            }

            if (increase > 0)
                setEmbeddingLevel(i, level + increase);
        }
    }

    /// <summary>
    /// Replaces the specified type with another one within the level run.
    /// </summary>
    private void replaceTypes(Sequence sequence, /*BidiCharacterType*/int oldType, /*BidiCharacterType*/int newType)
    {
        for (int i = sequence.getStart(); i < sequence.getLimit(); i++)
        {
            if (getBidiType(i) == oldType)
                setBidiType(i, newType);
        }
    }

    /// <summary>
    /// Sets the type of the characters from start (inclusive) to end (exclusive).
    /// </summary>
    private void setBidiTypes(int start, int end, /*BidiCharacterType*/int bidiType)
    {
        for (int i = start; i < end; i++)
        {
            setBidiType(i, bidiType);
        }
    }

    /// <summary>
    /// Starting from the specified index and moving in the specified direction (1 or -1), finds the first character
    /// of the level run that is not a retained BN character. Returns -1, if there is none.
    /// </summary>
    private int findNonBoundaryNeutral(int index, int step, Sequence sequence)
    {
        for (int i = index; (i >= sequence.getStart()) && (i < sequence.getLimit()); i += step)
        {
            if (getBidiType(i) != BidiCharacterType.BN)
                return i;
        }
        return -1;
    }

    /// <summary>
    /// Indicates whether the embedding levels of separators and of whitespace before them and at the end of the text are
    /// reset to the paragraph level (rule L1).
    /// </summary>
    /// <remarks>
    /// Rule L1 prepares a line for display. Text that is inserted into the document model as runs
    /// (<see cref="ApplyToInsertedText"/>, <see cref="ApplyToFieldResult"/>) keeps the levels of these characters.
    /// </remarks>
    private boolean getAppliesRuleL1() { return !mKeepLogicalOrder && !mNumbersTakePrecedingLevel; }

    /// <summary>
    /// Gives every character resolved as a European or Arabic number the embedding level of the character before it,
    /// or the paragraph embedding level if there is none.
    /// </summary>
    private void putNumbersOnPrecedingLevel()
    {
        int precedingLevel = mParagraphEmbeddingLevel;
        for (int i = 0; i < mData.length; i++)
        {
            /*BidiCharacterType*/int bidiType = getBidiType(i);
            if ((bidiType == BidiCharacterType.EN) || (bidiType == BidiCharacterType.AN))
            {
                setEmbeddingLevel(i, precedingLevel);
            }
            else
            {
                precedingLevel = getEmbeddingLevel(i);
            }
        }
    }

    /// <summary>
    /// 3.4 Reset the embedding level of segment and paragraph separator characters (rule L1).
    /// </summary>
    /// <remarks>
    /// See  http://unicode.org/reports/tr9/#L1
    /// </remarks>
    private void resolveSeparators()
    {
        // Reset the embedding level of any sequence of whitespace characters or control characters
        // preceding a segment separator or paragraph separator to the paragraph embedding level.
        int lastNonWhitespaceLevel = mParagraphEmbeddingLevel;
        for (int i = 0; i < mData.length; i++)
        {
            /*BidiCharacterType*/int originalBidiType = UnicodeCharacterDataResolver.getBidiCharacterType(getChar(i));
            if (getAppliesRuleL1() &&
                ((originalBidiType == BidiCharacterType.B) || (originalBidiType == BidiCharacterType.S)))
            {
                // Reset the embedding level of the separator character.
                setEmbeddingLevel(i, mParagraphEmbeddingLevel);

                // Reset the embedding levels of preceding whitespace and control characters.
                for (int j = i - 1; j >= 0; j--)
                {
                    /*BidiCharacterType*/int t = UnicodeCharacterDataResolver.getBidiCharacterType(getChar(j));
                    if ((t == BidiCharacterType.WS) ||
                        (t == BidiCharacterType.BN) ||
                        isControlChar(getChar(j)))
                    {
                        setEmbeddingLevel(j, mParagraphEmbeddingLevel);
                    }
                    else
                    {
                        break;
                    }
                }
            }

            // Resolve bidi control characters and BN to the level of the preceding character if there is one,
            // and otherwise to the base level. This step is needed because our implementation does not remove
            // bidi control characters after explicit level resolution.
            if (isControlChar(getChar(i)) || (originalBidiType == BidiCharacterType.BN))
            {
                setEmbeddingLevel(i, lastNonWhitespaceLevel);
            }
            else
            {
                lastNonWhitespaceLevel = getEmbeddingLevel(i);
            }
        }

        if (!getAppliesRuleL1())
        {
            return;
        }

        // Reset the embedding level of any sequence of whitespace characters or control characters at the end of the line
        // to the paragraph embedding level.
        for (int j = mData.length - 1; j >= 0; j--)
        {
            /*BidiCharacterType*/int t = UnicodeCharacterDataResolver.getBidiCharacterType(getChar(j));
            if ((t == BidiCharacterType.WS) ||
                (t == BidiCharacterType.BN) ||
                isControlChar(getChar(j)))
            {
                setEmbeddingLevel(j, mParagraphEmbeddingLevel);
            }
            else
            {
                break;
            }
        }
    }

    /// <summary>
    /// Converts visually ordered text into logical order.
    /// </summary>
    /// <remarks>
    /// To convert the text we split it into LTR and RTL fragments and then reverse the order of the fragments
    /// and reverse text of RTL fragmens. We use the Unicode bidirectional algorithm (UBA) to split the text.
    /// </remarks>
    private void convertVisualToLogicalOrder()
    {
        // A temporary array used to store reversed text.
        long[] convertedData = new long[mData.length];

        // The embedding levels of the text has already been calculated. We process runs of same embedding level.
        int runStartIndex = 0;
        int runLength = 0;
        int runEmbeddingLevel = -1;
        // An additional imaginary end-of-array character is used as a boundary of the last run.
        for (int i = 0; i < mData.length + 1; i++)
        {
            int currentEmbeddingLevel = (i < mData.length)
                ? getEmbeddingLevel(i)
                : -1; // Embedding level of the last end-of-array imaginary character.

            if (currentEmbeddingLevel != runEmbeddingLevel)
            {
                if (runLength > 0)
                {
                    // If the current run has RTL direction, reverse its text. We will throw the original data array away,
                    // so we can reverse and mirror characters in place.
                    boolean isRtlRun = ((runEmbeddingLevel % 2) == 1);
                    if (isRtlRun)
                    {
                        msArray.reverse(mData, runStartIndex, runLength);

                        // Reversing text also requires some of characters to be replaced with their mirrored counterparts
                        // (for example, brackets).
                        for (int j = runStartIndex; j < (runStartIndex + runLength); j++)
                        {
                            char originalChar = getChar(j);
                            char mirroredChar = BidiCharacterMirrorResolver.getBidiCharacterMirror(originalChar);
                            if (mirroredChar != originalChar)
                            {
                                setChar(j, mirroredChar);
                            }
                        }
                    }

                    // To reverse the order of all runs we write them from the end to the beginning of the temporary arrays.
                    int destinationIndex = mData.length - i;
                    msArray.copy(mData, runStartIndex, convertedData, destinationIndex, runLength);
                }
                runEmbeddingLevel = currentEmbeddingLevel;
                runStartIndex = i;
                runLength = 0;
            }
            ++runLength;
        }

        // Replace the text with its reordered variant.
        mData = convertedData;
    }

    /// <summary>
    /// Adjust the embedding level of runs containing numbers as required by MS Word.
    /// </summary>
    /// <remarks>
    /// The Unicode bidirectional algorithm increases the embedding level of numeric runs in RTL context to make sure
    /// the numbers are rendered in left-to-right direction. However, this offset is not needed when we write RTL text
    /// with numbers to MS Word, because Word correctly processes numbers in RTL context. In this method, we put numeric
    /// runs into one of adjacent runs by decreasing the number's embedding level. Of two adjacent runs we choose the one
    /// with higher embedding level (however, the run's embedding level must not be higher than that
    /// of the numeric run itself).
    /// </remarks>
    private void adjustLevelOfNumericRuns()
    {
        // Split the text into runs of same embedding level and examine each run to find numbers.
        int rangeStart = 0;
        int rangeLength = 0;
        int previousRunEmbeddingLevel = -1;
        int runEmbeddingLevel = -1;
        boolean runContainsDigits = false;
        boolean runContainsLetters = false;
        boolean runContainsHebrewNumberSeparators = false;
        // Here we scan one character past the text. An imaginary end-of-array character bounds the last run and allows
        // to process it inside the main loop thus reducing the code duplication.
        for (int i = 0; i < mData.length + 1; i++)
        {
            char currentChar;
            int currentEmbeddingLevel;
            if (i < mData.length)
            {
                currentChar = getChar(i);
                currentEmbeddingLevel = getEmbeddingLevel(i);
            }
            else
            {
                // Properties of the imaginary end-of-array character.
                currentChar = BidiChars.NOT_A_CHAR;
                currentEmbeddingLevel = -1;
            }

            // Control characters are ignored.
            if (isControlChar(currentChar))
            {
                continue;
            }

            // The end of current run is reached. Let's process the run.
            if (currentEmbeddingLevel != runEmbeddingLevel)
            {
                // If the current run contains a number, we need to correct its embedding level.
                if (runContainsDigits && (!runContainsLetters))
                {
                    // Calculate adjacent run levels.
                    int leadingLevel = Math.min(runEmbeddingLevel, previousRunEmbeddingLevel);
                    int trailingLevel = Math.min(runEmbeddingLevel, currentEmbeddingLevel);
                    int maxAdjacentLevel = Math.max(leadingLevel, trailingLevel);

                    // Calculate the new level of the numeric run.
                    // If there are no adjacent runs, keep the number's embedding level unchanged.
                    int adjustedEmbeddingLevel = (maxAdjacentLevel >= 0)
                        ? Math.min(maxAdjacentLevel, runEmbeddingLevel)
                        : runEmbeddingLevel;

                    // Correct the embedding level of the current run if it has changed.
                    if (adjustedEmbeddingLevel != runEmbeddingLevel)
                    {
                        for (int j = 0; j < rangeLength; j++)
                        {
                            setEmbeddingLevel(rangeStart + j, adjustedEmbeddingLevel);
                        }
                    }

                    // If needed, mark run as containing Hebrew number separators.
                    boolean isRtlRun = (adjustedEmbeddingLevel % 2) == 1;
                    if (isRtlRun && runContainsHebrewNumberSeparators)
                    {
                        for (int j = 0; j < rangeLength; j++)
                        {
                            setRequiresHebrewLocale(rangeStart + j, true);
                        }
                    }
                }

                // Start a new run.
                previousRunEmbeddingLevel = runEmbeddingLevel;
                runEmbeddingLevel = currentEmbeddingLevel;
                rangeStart = i;
                rangeLength = 0;
                runContainsDigits = false;
                runContainsLetters = false;
                runContainsHebrewNumberSeparators = false;
            }

            // Append the current character to the current run.
            ++rangeLength;

            // Numeric runs always have left-to-right direction, so we can skip checks for right-to-left runs.
            boolean isLtrRun = ((runEmbeddingLevel % 2) == 0);
            if (isLtrRun)
            {
                // The BiDi type of the character might have been changed by the Bidirectional Algorithm,
                // so we get the original type of the character.
                /*BidiCharacterType*/int originalBidiType = UnicodeCharacterDataResolver.getBidiCharacterType(currentChar);
                if ((originalBidiType == BidiCharacterType.EN) || (originalBidiType == BidiCharacterType.AN))
                {
                    runContainsDigits = true;
                }
                // When common number separators are inside a run with a number, they are treated as a part of that number,
                // not as separate letters.
                //
                // The following characters are number separators from the Unicode point of view. MS Word, however, treat
                // them as such only if run's bidi locale is set to 'he-IL' (see ISO 29500, Annex I, Section I.2, 
                // 'lang' element description).
                else if (isHebrewNumberSeparator(currentChar))
                {
                    runContainsHebrewNumberSeparators = true;
                }
                // Althought Unicode defines more number separator characters (CS and ES characters types),
                // some of them are always treated as neutral direction characters (ON) in MS Word.
                else if ((currentChar != ',') && (currentChar != '.') && (currentChar != ':') && (currentChar != '\u060C'))
                {
                    // Here letters are all characters except digits and number separators (all characters that cannot be
                    // a part of a number).
                    runContainsLetters = true;
                }
            }
        }
    }

    /// <summary>
    /// Gets runs reordered by the Unicode bidirectional algorithm.
    /// </summary>
    private BidiRun[] getResult()
    {
        // Before getting the result, the text must be processed by the bidirectional algorithm.
        Debug.assertTrue(mAlgorithmIsApplied);
        if (!mAlgorithmIsApplied)
        {
            return new BidiRun[0];
        }

        ArrayList<BidiRun> resultRuns = new ArrayList<BidiRun>();

        // Create a mask that filters all character properties relevant to run splitting. By using this mask we can get
        // all the properties at once and store them together in order to simplify their comparison and copying.
        long propertiesMask = G_FIELD_MASKS[EMBEDDING_LEVEL_FIELD_INDEX] |
            G_FIELD_MASKS[SOURCE_RUN_INDEX_FIELD_INDEX] |
            G_FIELD_MASKS[REQUIRES_HEBREW_LOCALE_FIELD_INDEX];

        long rangeCharProperties = 0;
        int rangeStartIndex = 0;
        StringBuilder rangeText = new StringBuilder();
        // Here we scan one character past the end of text. The extra end-of-text character closes the last run and allows
        // to process it inside the loop thus reducing the code duplication.
        for (int i = 0; i <= mData.length; i++)
        {
            char currentChar = (i < mData.length)
                ? getChar(i)
                : BidiChars.NOT_A_CHAR; // An arbitrary non-control character. It won't be written to the result anyway.

            // Skip normal control characters.
            // The end-of-text character is guaranteed to be non-control and it is never skipped.
            if (isControlChar(currentChar))
            {
                continue;
            }

            // Get all relevant character properties.
            // Make sure properties of the end-of-text character differ from properties of the preceding character
            // in order to close the last run.
            long currentCharProperties = (i >= mData.length)
                ? ~rangeCharProperties
                : mData[i] & propertiesMask;

            if (currentCharProperties != rangeCharProperties)
            {
                if (rangeText.length() > 0)
                {
                    msArrayList.add(resultRuns, new BidiRun(rangeText.toString(), getEmbeddingLevel(rangeStartIndex),
                        getSourceRunIndex(rangeStartIndex), getRequiresHebrewLocale(rangeStartIndex)));
                }
                rangeCharProperties = currentCharProperties;
                rangeStartIndex = i;
                rangeText.setLength(0);
            }
            rangeText.append(currentChar);
        }

        BidiRun[] result = msArrayList.toArray(resultRuns, new BidiRun[0]);
        if (mConvertToMsWordOrder)
        {
            if (mKeepLogicalOrder)
            {
                reverseIfDirectionDiffers(result, mParagraphEmbeddingLevel);
            }
            else
            {
                flatternEmbeddingLevels(result, mParagraphEmbeddingLevel);
            }
        }
        return result;
    }

    /// <summary>
    /// Rearranges runs in order to flattern embedding levels as required by Microsoft Word.
    /// </summary>
    /// <remarks>
    /// In MS Word, the text should be a sequence of left-to-right and right-to-left runs, without bidirectional embeddings
    /// (one run cannot be nested inside another). To flattern bidirectional embeddings, we use the reordering part
    /// of the Unicode bidirectional algorithm, described here: http://www.unicode.org/reports/tr9/#L2
    /// </remarks>
    private static void flatternEmbeddingLevels(BidiRun[] runs, int paragraphEmbeddingLevel)
    {
        Debug.assertTrue(paragraphEmbeddingLevel >= 0);

        // Text that consists of bidi control characters only gives no runs.
        if (runs.length == 0)
        {
            return;
        }

        int minEmbeddingLevel = Integer.MAX_VALUE;
        int maxEmbeddingLevel = Integer.MIN_VALUE;
        for (BidiRun run : runs)
        {
            minEmbeddingLevel = Math.min(minEmbeddingLevel, run.getLevel());
            maxEmbeddingLevel = Math.max(maxEmbeddingLevel, run.getLevel());
        }

        Debug.assertTrue(minEmbeddingLevel >= 0);
        Debug.assertTrue(maxEmbeddingLevel >= minEmbeddingLevel);

        // Flattern nested embeddings one level at a time.
        while (maxEmbeddingLevel > minEmbeddingLevel)
        {
            int rangeStart = 0;
            int rangeLength = 0;
            // Find ranges of runs whose order should be reversed at this step.
            // An additional imaginary end-of-array run bounds the last range. This allows to process the last range
            // inside the loop and avoid code duplication.
            for (int i = 0; i <= runs.length; i++)
            {
                // At this step we reorder runs whose level is higher or equal the maximum allowed level.
                boolean reorderCurrentRun = (i < runs.length) && (runs[i].getLevel() >= maxEmbeddingLevel);

                if (reorderCurrentRun)
                {
                    // This run in inside a range that should be reordered.
                    if (rangeLength == 0)
                    {
                        rangeStart = i;
                    }
                    ++rangeLength;
                }
                else
                {
                    // This run should not be reordered. If there is a preceding range of reorderable runs,
                    // we need to reorder them now.
                    reverseRuns(runs, rangeStart, rangeLength);
                    rangeStart = i;
                    rangeLength = 0;
                }
            }

            // One more level has been flatterned.
            --maxEmbeddingLevel;
        }

        // Reverse the order of the runs if implicit text direction does not match the paragraph direction.
        int textDirection = minEmbeddingLevel % 2;
        int paragraphDirection = paragraphEmbeddingLevel % 2;
        if (textDirection != paragraphDirection)
        {
            reverseRuns(runs, 0, runs.length);
        }
    }

    /// <summary>
    /// Reverses the order of the runs if the direction of the text, given by the lowest embedding level of the runs,
    /// differs from the paragraph direction.
    /// </summary>
    private static void reverseIfDirectionDiffers(BidiRun[] runs, int paragraphEmbeddingLevel)
    {
        if (runs.length <= 1)
        {
            return;
        }

        int minEmbeddingLevel = runs[0].getLevel();
        for (BidiRun run : runs)
        {
            minEmbeddingLevel = Math.min(minEmbeddingLevel, run.getLevel());
        }

        if ((minEmbeddingLevel % 2) != (paragraphEmbeddingLevel % 2))
        {
            reverseRuns(runs, 0, runs.length);
        }
    }

    /// <summary>
    /// Reverses the specified subrange of an array of runs.
    /// </summary>
    /// <remarks>
    /// Run direction is taken into account during reordering: order of subsequent runs having same direction is not changed.
    /// For example, the array L0-R1-L2-R3 will be reordered as follows: R3-L2-R1-L0 (the letter stands for run direction
    /// and the number stands for original run index).
    /// However, the array L0-R1-R2-L3 will become L3-R1-R2-L0, because the second (R1) and the third (R2) runs have the
    /// same direction and will be processed as one.
    /// </remarks>
    private static void reverseRuns(BidiRun[] runs, int start, int length)
    {
        Debug.assertTrue(start >= 0);
        Debug.assertTrue(start < runs.length);

        if (length <= 1)
        {
            // Nothing to reverse.
            return;
        }

        // Reorder runs into a temporary array. We cannot just call Array.Reverse(),
        // because we have process adjacent runs having same direction in a special way.
        BidiRun[] reorderedRuns = new BidiRun[length];
        int rangeStart = start;
        int rangeLength = 0;
        int rangeDirection = -1; // Meaning of values: 0 - LTR; 1 - RTL; -1 (and any other) - none (not initialized).
        // Here we process an imaginary out-of-array element that bounds the last range.
        // This allows to process the last range inside the cycle and prevent code duplication.
        for (int i = 0; i <= length; i++)
        {
            boolean sameDirection = (i < length) && ((runs[start + i].getLevel() % 2) == rangeDirection);
            if (sameDirection)
            {
                ++rangeLength;
            }
            else
            {
                int destinationIndex = reorderedRuns.length - (rangeStart - start) - rangeLength;
                if (rangeLength == 1)
                {
                    reorderedRuns[destinationIndex] = runs[rangeStart];
                }
                else if (rangeLength > 1)
                {
                    msArray.copy(runs, rangeStart, reorderedRuns, destinationIndex, rangeLength);
                }
                rangeStart = start + i;
                rangeLength = 1;
                rangeDirection = (rangeStart < runs.length)
                    ? runs[rangeStart].getLevel() % 2
                    : -1;
            }
        }

        // Copy the reordered runs back to the main array.
        msArray.copy(reorderedRuns, 0, runs, start, length);
    }

    private static boolean isEmbeddingStart(/*BidiCharacterType*/int bidiType)
    {
        return (bidiType == BidiCharacterType.LRE) ||
            (bidiType == BidiCharacterType.RLE) ||
            (bidiType == BidiCharacterType.LRO) ||
            (bidiType == BidiCharacterType.RLO);
    }

    private static boolean isSeparatorTerminatorOrBoundaryNeutral(/*BidiCharacterType*/int bidiType)
    {
        return (bidiType == BidiCharacterType.ES) ||
            (bidiType == BidiCharacterType.ET) ||
            (bidiType == BidiCharacterType.CS) ||
            (bidiType == BidiCharacterType.BN);
    }

    private static boolean isNeutralOrBoundaryNeutral(/*BidiCharacterType*/int bidiType)
    {
        return (bidiType == BidiCharacterType.WS) ||
            (bidiType == BidiCharacterType.ON) ||
            (bidiType == BidiCharacterType.B) ||
            (bidiType == BidiCharacterType.S) ||
            (bidiType == BidiCharacterType.BN);
    }

    public static boolean isControlChar(char c)
    {
        return (c == BidiChars.LRE) ||
            (c == BidiChars.RLE) ||
            (c == BidiChars.LRO) ||
            (c == BidiChars.RLO) ||
            (c == BidiChars.PDF);
    }

    private static boolean isHebrewNumberSeparator(char c)
    {
        return (c == '+') || (c == '-') || (c == '/');
    }

    /// <summary>
    /// Returns the direction of the specified embedding level: L for even levels, R for odd ones.
    /// </summary>
    private static /*BidiCharacterType*/int getDirectionOfLevel(int level)
    {
        return ((level % 2) == 0)
            ? BidiCharacterType.L
            : BidiCharacterType.R;
    }

    /// <summary>
    /// Represents a sequence of characters having same embedding level.
    /// </summary>
    private static class Sequence
    {
        Sequence(int start, int limit, /*BidiCharacterType*/int startOfSequence, /*BidiCharacterType*/int endOfSequence,
            int embeddingLevel)
        {
            Debug.assertTrue(start >= 0);
            Debug.assertTrue(limit > start);
            Debug.assertTrue((startOfSequence == BidiCharacterType.L) || (startOfSequence == BidiCharacterType.R));
            Debug.assertTrue((endOfSequence == BidiCharacterType.L) || (endOfSequence == BidiCharacterType.R));
            Debug.assertTrue(embeddingLevel >= 0);

            mStart = start;
            mLimit = limit;
            mStartOfSequence = startOfSequence;
            mEndOfSequence = endOfSequence;
            mEmbeddingLevel = embeddingLevel;
        }

        int getStart() { return mStart; }

        int getLimit() { return mLimit; }

        /*BidiCharacterType*/int getStartOfSequence() { return mStartOfSequence; }

        /*BidiCharacterType*/int getEndOfSequence() { return mEndOfSequence; }

        int getEmbeddingLevel() { return mEmbeddingLevel; }

        private /*final*/ int mStart;
        private /*final*/ int mLimit;
        private /*final*/ /*BidiCharacterType*/int mStartOfSequence;
        private /*final*/ /*BidiCharacterType*/int mEndOfSequence;
        private /*final*/ int mEmbeddingLevel;
    }

    // The following fields contain information used by getter and setter methods to access individual fields
    // of character-related data.
    /// JAVA-changed from unsigned long to (signed) long for autoportability to Java.
    private static /*final*/ long[] G_FIELD_MASKS;
    private static /*final*/ int[] G_FIELD_SHIFTS;
    /// <summary>
    /// The highest level at which RLE and RLO still start an embedding.
    /// </summary>
    /// <remarks>
    /// Together with <see cref="MaxLevelBelowLtrEmbedding"/> keeps explicit levels within 61, the maximum depth
    /// of the algorithm. Both limits are one level stricter than the algorithm requires.
    /// </remarks>
    private static final int MAX_LEVEL_BELOW_RTL_EMBEDDING = 59;

    /// <summary>
    /// The highest level at which LRE and LRO still start an embedding.
    /// </summary>
    private static final int MAX_LEVEL_BELOW_LTR_EMBEDDING = 58;

    private static final int CHAR_FIELD_INDEX = 0;
    private static final int REQUIRES_HEBREW_LOCALE_FIELD_INDEX = 1;
    private static final int BIDI_TYPE_FIELD_INDEX = 2;
    private static final int EMBEDDING_LEVEL_FIELD_INDEX = 3;
    private static final int USE_HEBREW_SEPARATORS_FIELD_INDEX = 4;
    private static final int SOURCE_RUN_INDEX_FIELD_INDEX = 5;


    /// <summary>
    /// Characters and related information used by the algorithm. Use the corresponding getter and setter methods to access
    /// individual data fields.
    /// </summary>
    /// <remarks>
    /// Items of this array are in fact packed structures containing characters and related information such as their levels
    /// and bidi types. See the static constructor of this class for information on internal representation of data.
    /// It would be better to use an array of structures instead, but structures cannot be ported to Java,
    /// and classes are too heavy to create one per character.
    /// </remarks>
    /// JAVA-changed from unsigned long to (signed) long for autoportability to Java.
    private long[] mData;

    private /*final*/ int mParagraphEmbeddingLevel;
    private /*final*/ boolean mConvertVisualToLogicalOrder;
    private /*final*/ boolean mConvertToMsWordOrder;
    private /*final*/ boolean mNumbersTakePrecedingLevel;
    private /*final*/ boolean mKeepLogicalOrder;

    private /*final*/ boolean mIsRtlParagraph;

    /// <summary>
    /// Indicates whether the Unicode bidirectional algorithm has been applied to the text.
    /// </summary>
    private boolean mAlgorithmIsApplied;

    static/* BidiAlgorithm()*/
    {
        final int FIELD_COUNT = 6;

        int[][] fieldOffsetsAndLengths = new int[][]
        {
            {0, 16},
            {16, 1}, // 1-bit flag 'is a character of an RTL number with Hebrew separators'.
            {17, 7}, // Bidi character types. Our implementation supports 19 types and the latest spec declares 23.
            {24, 8}, // Embedding levels. We support up to 64 levels and the latest spec raises this limit to 128.
            {32, 1}, // UseHebrewSeparator
            {33, 31}
        };

        G_FIELD_MASKS = new long[FIELD_COUNT];
        G_FIELD_SHIFTS = new int[FIELD_COUNT];

        int freeBlockOffset = 0;
        for (int fieldIndex = 0; fieldIndex < FIELD_COUNT; fieldIndex++)
        {
            int fieldOffset = fieldOffsetsAndLengths[fieldIndex][0];
            int fieldLength = fieldOffsetsAndLengths[fieldIndex][1];

            Debug.assertTrue(fieldOffset >= 0);
            Debug.assertTrue(fieldLength > 0);
            Debug.assertTrue(fieldOffset >= freeBlockOffset);

            int shift = 64/*sizeof(long) * 8*/ - (fieldOffset + fieldLength);
            Debug.assertTrue(shift >= 0);
            long mask = (((long)1 << fieldLength) - 1) << shift;

            G_FIELD_MASKS[fieldIndex] = mask;
            G_FIELD_SHIFTS[fieldIndex] = shift;

            freeBlockOffset = fieldOffset + fieldLength;
        }
    }
}

