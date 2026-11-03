// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/06/2016 by Alexey Morozov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.SeekOrigin;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;


/// <summary>
/// <see cref="Stream"/> implementation used to read from structured storage file.
/// </summary>
public class CompoundStream extends Stream
{
    CompoundStream(Stream srcStream, SectCollection fat, /*uint*/int sectStart, int length, int sectorSize)
    {
        mFat = fat;
        mLength = length;
        mSectStart = sectStart;

        mSrcStream = srcStream;
        mSectorSize = sectorSize;

        mPosition = 0;
    }

    public /*override*/ void flush()
    {
        // Read-only for a while.
        throw new UnsupportedOperationException();
    }

    public /*override*/ void close()
    {
        throw new UnsupportedOperationException("Close()");
    }

    public /*override*/ long seek(long offset, /*SeekOrigin*/int origin)
    {
        switch (origin)
        {
            case SeekOrigin.BEGIN:
                mPosition = offset;
                break;

            case SeekOrigin.CURRENT:
                mPosition += offset;
                break;

            case SeekOrigin.END:
                mPosition = mLength - offset;
                break;

            default:
                Debug.assertTrue(false);
                break;
        }

        return mPosition;
    }

    public /*override*/ void setLength(long value)
    {
        // Read-only for a while.
        throw new UnsupportedOperationException();
    }

    // Added for Java. .Net realy uses this code from Stream.ReadByte(). 
    // Needed in optimization.
    public /*override*/ int readByte() throws Exception
    {
        
        byte[] oneByteArray = new byte[1];
        int r = read(oneByteArray, 0, 1);
        if (r == 0)
            return -1;
        return (oneByteArray[0] & 0xFF);
    }

    public /*override*/ int read(byte[] buffer, int offset, int count) throws Exception
    {
        int bytesToRead = Math.min(count, (int)(mLength - mPosition));
        bytesToRead = Math.max(bytesToRead, 0);
        long actualRead = 0;

        int dstOffset = offset;
        while (bytesToRead > actualRead)
        {
            byte[] sectBytes = getSectBytes();

            int localPos = (int)mPosition%mSectorSize;
            long localBytesToRead = Math.min(mSectorSize - localPos, (count - actualRead));

            System.arraycopy(sectBytes, (int)(localPos), buffer, (int)(dstOffset), (int)(localBytesToRead));

            mPosition += localBytesToRead;
            actualRead += localBytesToRead;
            dstOffset += (int)localBytesToRead;
        }

        return (int)actualRead;
    }

    public /*override*/ void write(byte[] buffer, int offset, int count)
    {
        // Read-only for a while.
        throw new UnsupportedOperationException();
    }

    public /*override*/ void writeByte(byte value)
    {
        // Read-only for a while.
        throw new UnsupportedOperationException("WriteByte(byte value)");
    }

    /// <summary>
    /// Gets sector data for given position.
    /// </summary>
    // TODO position never used.
    private byte[] getSectBytes() throws Exception
    {
        // Starting sector for this position.
        int sectIndex = (int)mPosition / mSectorSize;
        long sectStart = sectIndex * mSectorSize;

        // Try to get cached bytes.
        byte[] sectBytes = DictionaryExtension.getValueOrNull(mCachedSectBytes, sectStart);
        if (sectBytes != null)
        {
            // Cache hit.
            return sectBytes;
        }

        // Cache miss.
        /*uint*/int sect;
        if ((sectIndex - 1) == mLastSectIndex)
        {
            // We have cached sect from previous search so use it.
            sect = mFat.get(mLastSect);
        }
        else
        {
            // Otherwise perform full FAT search.
            sect = mSectStart;
            for (int i = 0; i < sectIndex; i++)
            {
                if (isEndSect(sect))
                    throw new IllegalStateException("Unable to read beyound stream.");

                sect = mFat.get(sect);
            }
        }

        mLastSectIndex = sectIndex;
        mLastSect = sect;

        sectBytes = cacheSect(sectStart, sect);

        return sectBytes;
    }

    private static boolean isEndSect(/*uint*/int sect)
    {
        return (sect == FatEntryType.END_OF_CHAIN) || (sect == FatEntryType.FREE_SECT);
    }

    private byte[] cacheSect(long sectStart, /*uint*/int sect) throws Exception
    {
        BinaryReader reader = new BinaryReader(mSrcStream);
        mSrcStream.setPosition(Sector.sectorToOffset(sect, mSectorSize));
        byte[] sectBytes = reader.readBytes(mSectorSize);

        // Remove some entry.
        if (mCachedSectBytes.size() > CACHE_SIZE)
        {
            for (long key : (Iterable<Long>) mCachedSectBytes.keySet())
            {
                msDictionary.remove(mCachedSectBytes, key);
                break;
            }
        }

        msDictionary.add(mCachedSectBytes, sectStart, sectBytes);

        return sectBytes;
    }

    public /*override*/ boolean canRead() { return true; }

    public /*override*/ boolean canSeek() { return true; }

    public /*override*/ boolean canWrite() { return false; }

    public /*override*/ long getLength() { return mLength; }

    public /*override*/ long getPosition() { return mPosition; }
    public /*override*/ void setPosition(long value) { mPosition = value; }

    private long mPosition;

    private /*final*/ long mLength;
    private /*final*/ /*uint*/int mSectStart;

    private /*final*/ SectCollection mFat;
    private /*final*/ int mSectorSize;

    private /*final*/ Stream mSrcStream;

    private /*final*/ HashMap<Long, byte[]> mCachedSectBytes = new HashMap<Long, byte[]>(CACHE_SIZE);

    /// <summary>
    /// Sector index from last FAT search. Greatly reduces FAT search count needed.
    /// </summary>
    private int mLastSectIndex = Integer.MIN_VALUE;

    /// <summary>
    ///  Sector number from last FAT search.
    /// </summary>
    private /*uint*/int mLastSect = 0;

    private static final int CACHE_SIZE = 10;
}

