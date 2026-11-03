// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents context of the LZCOMP encoder.
/// </summary>
class LzCompEncoderContext extends LzCompDecoderContext
{

    /// <summary>
    /// Ctor.
    /// </summary>
    LzCompEncoderContext(byte[] data, BitBinaryWriter writer)
    {
    	super(data.length, writer);
	
        // IN. This is speed optimization. Actually, we could use two separate arrays here
        // and return appropriate symbol in GetSymbol() method depending on requested index.
        // But access to GetSymbol() method is very intensive and
        // use of integrated array considerably increases performance.
        mData = new byte[PRE_LOAD_DATA_SIZE + data.length];
        System.arraycopy(getPreLoadData(), 0, mData, 0, getPreLoadData().length);
        System.arraycopy(data, 0, mData, PRE_LOAD_DATA_SIZE, data.length);

        for (int i = 1; i < PRE_LOAD_DATA_SIZE; i++)
            updateHashTable(i);
    }

    /// <summary>
    /// Updates hash table with symbol in the specified index position.
    /// </summary>
    void updateHashTable(int index)
    {
        if (index < 1)
            return;

        byte curByte = mData[index];
        /*ushort*/short prevByte = (/*ushort*/short)(mData[index - 1] & 0xFF);

        int pos = ((prevByte & 0xFFFF) << 8) | (curByte & 0xFF);

        HashNode hNode = new HashNode(index - 1, getHashTable().get(pos));
        getHashTable().set(pos, hNode);
    }

    /// <summary>
    /// Returns symbol from the data array.
    /// </summary>
    /// <remarks>
    /// There is PreLoadData at the beginning of data array and
    /// there are data which should be encoded after that.
    /// See <see cref="LzCompEncoderContext(byte[], BitBinaryWriter)"/> for more details.
    /// </remarks>
    byte getSymbol(int index)
    {
        return mData[index];
    }

    /// <summary>
    /// Returns two-byte symbol.
    /// Gets high byte from the current index and lo byte from the next index.
    /// This is used in <see cref="HashTable"/>.
    /// </summary>
    int getTwoBytesSymbol(int index)
    {
        int symbol = (mData[index] & 0xFF);
        symbol = symbol << 8;
        symbol = symbol | (mData[index + 1] & 0xFF);

        return symbol;
    }

    /// <summary>
    /// Returns number of equal symbols in <see cref="mData"/>
    /// starting from <paramref name="startIndex1"/> and <paramref name="startIndex2"/>.
    /// </summary>
    int calcLength(int startIndex1, int startIndex2, int maxLength)
    {
        int length = 0;
        while (length < maxLength)
        {
            if (mData[startIndex1++] != mData[startIndex2++])
                break;

            length++;
        }

        return length;
    }

    /// <summary>
    /// Clears chain of hash nodes for specified symbol.
    /// </summary>
    void clearHash(int symbol, HashNode prevHashNode, HashNode curHashNode)
    {
        if (getHashTable().get(symbol) == curHashNode)
            getHashTable().remove(symbol);
        else if (prevHashNode != null)
            prevHashNode.setNext(null);

        HashNode curNode = curHashNode;
        while (curNode != null)
        {
            HashNode prevNode = curNode;
            curNode = curNode.getNext();
            prevNode.setNext(null);
        }
    }

    /// <summary>
    /// Symbols hash table.
    /// The key is symbol position.
    /// The value is hash node for this symbol.
    /// </summary>
    IntToObjDictionary<HashNode> getHashTable() { return mHashTable; }

    /// <summary>
    /// Maximum allowed copy distance.
    /// </summary>
    static final int MAX_COPY_DISTANCE = 0x7fffffff;

    private /*final*/ IntToObjDictionary<HashNode> mHashTable = new IntToObjDictionary<HashNode>();

    /// <summary>
    /// The data for encoding.
    /// </summary>
    private /*final*/ byte[] mData;
}

/// <summary>
/// Hash node for symbols and their positions.
/// </summary>
/// <remarks>This type is only for the internal use by <see cref="LzCompEncoderContext"/>.</remarks>
class HashNode
{
    /// <summary>
    /// Ctor.
    /// </summary>
    HashNode(int index, HashNode nextNode)
    {
        mIndex = index;
        mNext = nextNode;
    }

    /// <summary>
    /// Index of symbol, which this HashNode represents.
    /// </summary>
    int getIndex() { return mIndex; }
    void setIndex(int value) { mIndex = value; }

    /// <summary>
    /// Hash node for next symbol.
    /// </summary>
    HashNode getNext() { return mNext; }
    void setNext(HashNode value) { mNext = value; }

    private int mIndex;
    private HashNode mNext;
}


