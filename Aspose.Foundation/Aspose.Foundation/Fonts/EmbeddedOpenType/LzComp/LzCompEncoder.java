// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Allows to compress data using LZCOMP algorithm.
/// </summary>
/// <remarks>
/// This code is ported and refactored version of C code.
/// Original C code is available at http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#Sample 
/// or http://code.google.com/p/ttf2eot/source/browse/trunk/ (files lzcomp.c and lzcomp.h).
/// All constant values are taken from C code and altering them may lead to incorrect encoding result.
/// </remarks>
/*static*/ class LzCompEncoder
{
	/* Simulation of static class by using private constructor */
	private LzCompEncoder()
	{}

    /// <summary>
    /// Packs data.
    /// </summary>
    static byte[] packData(byte[] data) throws Exception
    {
        // MS Word packs empty data arrays into four zero bytes.
        if (data == null || data.length == 0)
            return new byte[4];

        // LZComp encodes data only with size less than three bytes length.
        if ((data.length & 0xFF000000L) != 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "data");

        byte[] rleEncoded = RleCoder.encode(data);
        // Only use run-length encoding if there is a clear benefit.
        boolean isRleEncoded = (rleEncoded.length < (data.length * 3 / 4));
        byte[] dataSource = (isRleEncoded) ? rleEncoded : data;

        return encode(dataSource, isRleEncoded);
    }

    /// <summary>
    /// Encodes specified data.
    /// </summary>
    private static byte[] encode(byte[] data, boolean isRleEncoded) throws Exception
    {
        MemoryStream stream = new MemoryStream(); // Will be disposed with writer.
        BitBinaryWriter writer = new BitBinaryWriter(stream, true);
        try /*JAVA: was using*/
        {
            writer.writeBit(isRleEncoded);
            writer.writeValue(data.length, 24);

            LzCompEncoderContext context = new LzCompEncoderContext(data, writer);

            int index = context.getPreLoadData().length;
            while (index < context.getMaxIndex())
            {
                int curPos = index;
                Match match = makeCopyDecision(context, index++);
                if (match.getLength() > 0)
                {
                    // The piece of data which already met is found. Write length and distance for it.
                    int distanceRanges = getNumberOfDistanceRanges(match.getDistance());
                    encodeLength(context, match.getLength(), match.getDistance(), distanceRanges);
                    encodeDistance(context, match.getDistance(), distanceRanges);

                    for (int j = 1; j < match.getLength(); j++)
                        context.updateHashTable(index++);
                }
                else
                {
                    byte symbol = context.getSymbol(curPos);
                    if ((curPos >= 2) && (symbol == context.getSymbol(curPos - 2)))
                    {
                        // The symbol met two positions earlier. Write one byte command.
                        context.getSymbolCoder().writeSymbolAndUpdateTree(context.getDup2CommandSymbol());
                    }
                    else
                    {
                        if ((curPos >= 4) && (symbol == context.getSymbol(curPos - 4)))
                        {
                            // The symbol met four positions earlier. Write one byte command.
                            context.getSymbolCoder().writeSymbolAndUpdateTree(context.getDup4CommandSymbol());
                        }
                        else
                        {
                            if ((curPos >= 6) && (symbol == context.getSymbol(curPos - 6)))
                            {
                                // The symbol met six positions earlier. Write one byte command.
                                context.getSymbolCoder().writeSymbolAndUpdateTree(context.getDup6CommandSymbol());
                            }
                            else
                            {
                                // The symbol did not meet yet. Just write it.
                                context.getSymbolCoder().writeSymbolAndUpdateTree((symbol & 0xFF));
                            }
                        }
                    }
                }
            }

            writer.flush();
            return stream.toArray();
        }
        finally { if (writer != null) writer.dispose(); }
    }

    /// <summary>
    /// Encodes the distance.
    /// </summary>
    private static void encodeDistance(LzCompEncoderContext context, int distance, int distanceRanges) throws Exception
    {
        int value = distance - MIN_DISTANCE;
        int distanceRangesCount = (distanceRanges - 1) * WIDTH_OF_DISTANCE;

        for (int i = distanceRangesCount; i >= 0; i -= WIDTH_OF_DISTANCE)
        {
            int distanceSymbol = (value >> i) & BIT_MASK;
            context.getDistanceCoder().writeSymbolAndUpdateTree(distanceSymbol);
        }
    }

    /// <summary>
    /// Encodes the length and the number of distance ranges used for this distance.
    /// </summary>
    private static void encodeLength(LzCompEncoderContext context, int length, int distance, int numDistRanges) throws Exception
    {
        int value = length - ((distance >= MAX_TWO_BYTES_DISTANCE) ? MIN_LENGTH_3 : MIN_LENGTH);

        int bitsUsed = BitUtil.bitsUsed(value);

        int i = BIT_RANGE;
        while (i < bitsUsed)
            i += BIT_RANGE;
        int mask = 1 << (i - 1);

        int symbol = (bitsUsed > BIT_RANGE) ? 2 : 0;

        // Repeat gBitRange times, hard-wire so that we do not have to loop
        /* 1 */
        if ((value & mask) != 0)
            symbol |= 1;
        mask >>= 1;
        /* 2 */
        symbol <<= 1;
        if ((value & mask) != 0)
            symbol |= 1;
        mask >>= 1;


        context.getSymbolCoder().writeSymbolAndUpdateTree(256 + symbol + (numDistRanges - 1) * (1 << WIDTH_OF_LENGTH));

        i = bitsUsed - BIT_RANGE;
        while (i >= 1)
        {
            symbol = (i > BIT_RANGE) ? 2 : 0;

            // Repeat gBitRange times, hard-wire so that we do not have to loop
            /* 1 */
            if ((value & mask) != 0)
                symbol |= 1;
            mask >>= 1;
            /* 2 */
            symbol <<= 1;
            if ((value & mask) != 0)
                symbol |= 1;
            mask >>= 1;

            i -= BIT_RANGE;

            context.getLengthCoder().writeSymbolAndUpdateTree(symbol);
        }
    }

    /// <summary>
    /// Finds matches in already browsed data.
    /// </summary>
    private static Match findMatch(LzCompEncoderContext context, int indexInData)
    {
        final int LEN_MAX_COST_CACHE = 32;
        int[] literalCostCache = new int[LEN_MAX_COST_CACHE + 1];

        Match bestMatch = new Match();

        int hashNodesCount = 0;
        int maxIndexMinusIndex = context.getMaxIndex() - indexInData;
        int maxComputedLength = 0;

        if (maxIndexMinusIndex > 1)
        {
            // Store in hash 2-bytes values.
            int symbol = context.getTwoBytesSymbol(indexInData);

            HashNode prevHashNode = null;
            HashNode curHashNode = context.getHashTable().get(symbol);
            while (curHashNode != null)
            {
                int indexBrowsed = curHashNode.getIndex();
                int distance = indexInData - indexBrowsed;

                hashNodesCount++;
                if ((hashNodesCount > MAX_SYMBOL_NODES_IN_CACHE) || (distance > LzCompEncoderContext.MAX_COPY_DISTANCE))
                {
                    context.clearHash(symbol, prevHashNode, curHashNode);
                    break;
                }

                prevHashNode = curHashNode;
                curHashNode = curHashNode.getNext();

                int maxLength = Math.min(maxIndexMinusIndex, indexInData - indexBrowsed);
                if (maxLength < MIN_LENGTH)
                    continue;

                // Calculate length of the found coinciding piece.
                // Since we found first symbol (2 bytes) in hash, we can skip it.
                int length = context.calcLength(indexBrowsed + 2, indexInData + 2, maxLength - 2) + 2;

                distance = distance - length + 1;
                if (distance > context.getDistanceMax())
                    continue;

                if ((length == 2) && (distance >= MAX_TWO_BYTES_DISTANCE))
                    continue;

                if ((length <= bestMatch.getLength()) && (distance > bestMatch.getDistance()))
                {
                    // Speed optimization.
                    if (length <= bestMatch.getLength() - 2)
                        continue;

                    if (distance > (bestMatch.getDistance() << LzCompDecoderContext.DISTANCE_WIDTH))
                    {
                        // Speed optimization.
                        if (length < bestMatch.getLength())
                            continue;
                        // Speed optimization.
                        if (distance > (bestMatch.getDistance() << (LzCompDecoderContext.DISTANCE_WIDTH + 1)))
                            continue;
                    }
                }

                int literalCost;
                if (length > maxComputedLength)
                {
                    int computedLength = (length > LEN_MAX_COST_CACHE) ? LEN_MAX_COST_CACHE : length;

                    for (int i = maxComputedLength; i < computedLength; i++)
                    {
                        literalCost = context.getSymbolCoder().getSymbolCost((context.getSymbol(indexInData + i) & 0xFF));
                        literalCostCache[i + 1] = literalCostCache[i] + literalCost;
                    }

                    maxComputedLength = computedLength;
                    if (length > LEN_MAX_COST_CACHE)
                    {
                        literalCost = literalCostCache[LEN_MAX_COST_CACHE];
                        // Just approximate.
                        literalCost += literalCost / LEN_MAX_COST_CACHE * (length - LEN_MAX_COST_CACHE);
                    }
                    else
                    {
                        literalCost = literalCostCache[length];
                    }
                }
                else
                {
                    literalCost = literalCostCache[length];
                }

                if (literalCost > bestMatch.getGain())
                {
                    int distRanges = getNumberOfDistanceRanges(distance);
                    int copyCost = getEncodedLengthCost(context, length, distance, distRanges);

                    // Conservatively assumes only one bit per range for distanceBitCount.
                    if (literalCost - copyCost - (distRanges << 16) > bestMatch.getGain())
                    {
                        copyCost += getEncodedDistanceCost(context, distance, distRanges);
                        int gain = literalCost - copyCost;

                        if (gain > bestMatch.getGain())
                        {
                            bestMatch.setGain(gain);
                            bestMatch.setLength(length);
                            bestMatch.setDistance(distance);
                            bestMatch.setCopyCost(copyCost);
                        }
                    }
                }
            }
        }

        return bestMatch;
    }

    /// <summary>
    /// Returns cost of encoded distance.
    /// </summary>
    private static int getEncodedDistanceCost(LzCompEncoderContext context, int distance, int distanceRanges)
    {
        int value = distance - MIN_DISTANCE;
        int distanceRangesCount = (distanceRanges - 1) * WIDTH_OF_DISTANCE;

        int distanceCost = 0;
        for (int i = distanceRangesCount; i >= 0; i -= WIDTH_OF_DISTANCE)
        {
            int distanceSymbol = (value >> i) & BIT_MASK;
            distanceCost += context.getDistanceCoder().getSymbolCost(distanceSymbol);
        }

        return distanceCost;
    }

    /// <summary>
    /// Returns number of distance ranges.
    /// </summary>
    private static int getNumberOfDistanceRanges(int distance)
    {
        int bitsNeeded = BitUtil.bitsUsed(distance - MIN_DISTANCE);
        int distanceRanges = (bitsNeeded + WIDTH_OF_DISTANCE - 1) / WIDTH_OF_DISTANCE;
        return distanceRanges;
    }

    /// <summary>
    /// Returns cost of the encoded length.
    /// </summary>
    private static int getEncodedLengthCost(LzCompEncoderContext context, int length, int distance, int numDistRanges)
    {
        int value = length - ((distance >= MAX_TWO_BYTES_DISTANCE) ? MIN_LENGTH_3 : MIN_LENGTH);

        int bitsUsed = BitUtil.bitsUsed(value);
        int i = BIT_RANGE;
        while (i < bitsUsed)
            i += BIT_RANGE;

        int mask = 1 << (i - 1);
        int symbol = (bitsUsed > BIT_RANGE) ? 2 : 0;

        //Repeat gBitRange times, hard-wire so that we do not have to loop.
        /* 1 */
        if ((value & mask) != 0)
            symbol |= 1;
        mask = mask >> 1;
        /* 2 */
        symbol = symbol << 1;
        if ((value & mask) != 0)
            symbol |= 1;
        mask = mask >> 1;

        int lengthCost = context.getSymbolCoder().getSymbolCost(256 + symbol + (numDistRanges - 1) * (1 << WIDTH_OF_LENGTH));

        i = bitsUsed - BIT_RANGE;
        while (i >= 1)
        {
            symbol = (i > BIT_RANGE) ? 2 : 0;

            //Repeat gBitRange times, hard-wire so that we do not have to loop.
            /* 1 */
            if ((value & mask) != 0)
                symbol |= 1;
            mask >>= 1;
            /* 2 */
            symbol = symbol << 1;
            if ((value & mask) != 0)
                symbol = symbol | 1;
            mask >>= 1;

            i -= BIT_RANGE;

            lengthCost += context.getLengthCoder().getSymbolCost(symbol);
        }

        return lengthCost;
    }

    /// <summary>
    /// Returns match with the optimal length and distance.
    /// </summary>
    /// <remarks>
    /// Makes a decision on whether to use a copy item, and then if it decides
    /// to use a copy item it decides on an optimal length & distance for the copy.
    /// </remarks>
    private static Match makeCopyDecision(LzCompEncoderContext context, int index)
    {
        Match match1 = findMatch(context, index);

        int curPos = index;
        context.updateHashTable(index++);

        if (match1.getGain() > 0)
        {
            Match match2 = findMatch(context, index);
            int symbol = (context.getSymbol(curPos) & 0xFF);
            int symbolCost = context.getSymbolCoder().getSymbolCost(symbol);

            if ((match2.getGain() >= match1.getGain()) &&
                (match1.getCostPerByte() > (match2.getCostPerByte() * match2.getLength() + symbolCost) / (match2.getLength() + 1)))
            {
                match1.setLength(0);
            }
            else if (match1.getLength() > 3)
            {
                // Explore cutting back on match1.Length by one unit.
                match2 = findMatch(context, curPos + match1.getLength());
                if (match2.getLength() >= 2)
                {
                    Match match3 = findMatch(context, curPos + match1.getLength() - 1);
                    if ((match3.getLength() > match2.getLength()) && (match3.getCostPerByte() < match2.getCostPerByte()))
                    {
                        int distRanges = getNumberOfDistanceRanges(match1.getDistance() + 1);

                        int lenBitCount = getEncodedLengthCost(context, match1.getLength() - 1, match1.getDistance() + 1, distRanges);
                        int distBitCount = getEncodedDistanceCost(context, match1.getDistance() + 1, distRanges);

                        int cost1B = lenBitCount + distBitCount;
                        cost1B += match3.getCostPerByte() * match3.getLength();

                        int cost1A = match1.getCostPerByte() * match1.getLength();
                        cost1A += match2.getCostPerByte() * match2.getLength();

                        if ((cost1A / (match1.getLength() + match2.getLength())) > (cost1B / (match1.getLength() - 1 + match3.getLength())))
                        {
                            match1.setLength(match1.getLength() - 1)/*Property--*/;
                            match1.setDistance(match1.getDistance() + 1)/*Property++*/;
                        }
                    }
                }
            }

            if (match1.getLength() == 2)
            {
                int dup2Cost;
                if ((curPos >= 2) && (context.getSymbol(curPos) == context.getSymbol(curPos - 2)))
                {
                    dup2Cost = context.getSymbolCoder().getSymbolCost(context.getDup2CommandSymbol());
                    if ((match1.getCostPerByte() * 2) > (dup2Cost + context.getSymbolCoder().getSymbolCost((context.getSymbol(curPos + 1) & 0xFF))))
                        match1.setLength(0);
                }
                else if ((curPos >= 1) && ((curPos + 1) < context.getMaxIndex()) && (context.getSymbol(curPos + 1) == context.getSymbol(curPos - 1)))
                {
                    dup2Cost = context.getSymbolCoder().getSymbolCost(context.getDup2CommandSymbol());
                    if ((match1.getCostPerByte() * 2) > (symbolCost + dup2Cost))
                        match1.setLength(0);
                }
            }
        }
        return match1;
    }

    /// <summary>
    /// Constants taken from the LZCOMP.
    /// </summary>
    private static final int MAX_TWO_BYTES_DISTANCE = 512;
    private static final int MIN_LENGTH = 2;
    private static final int MIN_LENGTH_3 = 3;
    private static final int WIDTH_OF_LENGTH = 3;
    private static final int BIT_RANGE = WIDTH_OF_LENGTH - 1;
    private static final int MIN_DISTANCE = 1;
    private static final int WIDTH_OF_DISTANCE = 3;
    private static final int BIT_MASK = (1 << WIDTH_OF_DISTANCE) - 1;

    /// <summary>
    /// Limits maximum number of chains in cash for symbol.
    /// </summary>
    /// <remarks>
    /// Note, that original value in algorithm is 256,
    /// but experiments shows that decreasing this value improves 
    /// performance significantly and size of encoded data is not grows.
    /// </remarks>
    private static final int MAX_SYMBOL_NODES_IN_CACHE = 16;

    /// <summary>
    /// Match type that helps to make a decision on whether to use a copy item.
    /// </summary>
    /// <remarks>This type is only for the internal use by <see cref="LzCompEncoder"/>.</remarks>
    private static class Match
    {
        // Match length.
        int getLength() { return mLength; }
        void setLength(int value) { mLength = value; }

        // Match distance.
        int getDistance() { return mDistance; }
        void setDistance(int value) { mDistance = value; }

        // Match gain.
        int getGain() { return mGain; }
        void setGain(int value) { mGain = value; }

        // Match copy cost.
        int getCopyCost() { return mCopyCost; }
        void setCopyCost(int value) { mCopyCost = value; }

        // Match cost per byte.
        int getCostPerByte() { return (getLength() != 0) ? getCopyCost() / getLength() : 0; }

        private int mLength;
        private int mDistance;
        private int mGain;
        private int mCopyCost;
    }
}

