// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2005 by Roman Korchagin
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.msString;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Structured storage file system without using Win32.
/// Note this is not a full implementation, but a simplistic and cut down version.
/// Supports only 512 byte sectors and file size will be limited to 2Gb because of casting ints.
/// Keeps all structured storage file in memory.
/// </summary>
public class FileSystem
{
    /// <summary>
    /// A quick check for a signature of a structured storage document.
    /// Preserves the stream position.
    /// </summary>
    public static boolean isStructuredStorage(Stream stream) throws Exception
    {
        long savePos = stream.getPosition();
        byte[] data = new byte[4];
        int bytesRead = stream.read(data, 0, 4);
        stream.setPosition(savePos);
        return isStructuredStorage(data, bytesRead);
    }

    /// <summary>
    /// A quick check for a signature of a structured storage document.
    /// </summary>
    public static boolean isStructuredStorage(byte[] data, int bytesRead)
    {
        return (bytesRead >= G_STRUCTURED_STORAGE_SIGNATURE.length) &&
               (data[0] == G_STRUCTURED_STORAGE_SIGNATURE[0]) &&
               (data[1] == G_STRUCTURED_STORAGE_SIGNATURE[1]) &&
               (data[2] == G_STRUCTURED_STORAGE_SIGNATURE[2]) &&
               (data[3] == G_STRUCTURED_STORAGE_SIGNATURE[3]);
    }

    /// <summary>
    /// Ctor. Creates a new structured storage with the specified root storage.
    /// </summary>
    public FileSystem(MemoryStorage root)
    {
        mRoot = root;
    }

    /// <summary>
    /// Ctor. Creates a new structured storage with an empty Root storage.
    /// </summary>
    public FileSystem(Guid clsid)
    {
    	this(new MemoryStorage(clsid));
	
    }

    /// <summary>
    /// Ctor. Reads structured storage file into memory completely. Closes the file on return.
    /// </summary>
    public FileSystem(String fileName) throws Exception
    {
        Stream stream = File.openRead(fileName);
        try /*JAVA: was using*/
        {
            load(stream);
            readData();
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Ctor. Reads structured storage header, FAT and directory into memory, but does not read the data.
    /// Data is loaded automatically when you first access the <see cref="Root"/> property.
    /// Does not close the stream.
    /// </summary>
    public FileSystem(Stream stream) throws Exception
    {
        load(stream);
    }

    /// <summary>
    /// Loads structured storage header, FAT and directory from a stream.
    /// Note this always reads from the stream start.
    /// </summary>
    private void load(Stream stream) throws Exception
    {
        Debug.assertTrue(stream != null);
        mFileSystemStream = stream;
        mFileSystemStream.setPosition(0);

        mHeader = new Header(new BinaryReader(mFileSystemStream, Encoding.getUnicode()));
        mFat = new Fat(mFileSystemStream, mHeader);
        mFat.read();
        checkZeroFat();

        mMiniFat = new SectCollection(readStream(mHeader.SectMiniFatStart, mHeader.getMiniFatLength(), mHeader.getMiniFatLength(), true));
        readDirectory();
    }

    /// <summary>
    /// WORDSNET-9530 Resiliency against FAT that consists entirely of zeroes.
    /// </summary>
    private void checkZeroFat()
    {
        final int ENTRIES_TO_CHECK = 10;
        int maxI = Math.min(ENTRIES_TO_CHECK, mFat.getCount());
        for (/*uint*/int i = 0; (i & 0xFFFFFFFFL) < maxI; i++)
        {
            if (mFat.get(i) != 0)
                return;
        }
        throw new IllegalStateException("The FAT in the structured storage document seems to be corrupted.");
    }

    /// <summary>
    /// Saves structured storage to a file.
    /// </summary>
    public void save(String fileName) throws Exception
    {
        Stream stream = File.create(fileName);
        try /*JAVA: was using*/
    	{
            save(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Saves structured storage to a stream. Note this always writes at the stream start.
    /// </summary>
    public void save(Stream stream) throws Exception
    {
        if ((stream != null) && (!stream.canSeek()))
            saveToNonSeekStream(stream);
        else
            saveCore(stream);

    }

    /// <summary>
    /// Saves the document to a temporary stream and then copies it to a non-seekable stream.
    /// </summary>
    private void saveToNonSeekStream(Stream stream) throws Exception
    {
        MemoryStream memStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            // WORDSNET-21179 Use a temporary stream to be able to write to the NonSeekStream.
            saveCore(memStream);
            memStream.setPosition(0);
            StreamUtil.copyStream(memStream, stream);
        }
        finally { if (memStream != null) memStream.close(); }
    }

    /// <summary>
    /// Saves structured storage to a stream. Note this always writes at the stream start.
    /// </summary>
    private void saveCore(Stream stream) throws Exception
    {
        Debug.assertTrue(stream != null);
        mFileSystemStream = stream;
        //Reserve space for the header
        mFileSystemStream.setPosition(Sector.SECTOR_SIZE);

        //Create control structures.
        mHeader = new Header();
        mFat = new Fat(mFileSystemStream, mHeader);
        mMiniFat = new SectCollection();
        mMiniStream = new MemoryStream();

        //Build flat dirEntries from the storage tree and at the same time write streams and update fats.
        mDirEntries = new DirEntryCollection();
        writeStorage(mRoot, null);

        //Write mini stream and minifat. Its start is recorded in the root directory entry.
        if (mMiniStream.getLength() > 0)
        {
            mDirEntries.get(0).SectStart = writeStream(mMiniStream, true);
            mDirEntries.get(0).Size = mMiniStream.getLength();

            // RK This has to be in a local variable to allow autoporting to Java.
            int csectMiniFat = 0;
            RefInt referenceToCsectMiniFat = new RefInt(csectMiniFat);
            mHeader.SectMiniFatStart = writeStream(mMiniFat.toMemoryStream(), true, /*out*/ referenceToCsectMiniFat);
            csectMiniFat = referenceToCsectMiniFat.get();
            mHeader.CsectMiniFat = csectMiniFat;
        }
        else
            mHeader.SectMiniFatStart = FatEntryType.END_OF_CHAIN;

        //Write directory.
        mHeader.SectDirStart = writeStream(mDirEntries.toMemoryStream(), true);

        mFat.write();

        //Finally write the header
        mFileSystemStream.setPosition(0);
        mHeader.write(new BinaryWriter(mFileSystemStream, Encoding.getUnicode()));

        mFileSystemStream.setPosition(mFileSystemStream.getLength());
    }

    /// <summary>
    /// Recursively writes storage and its children storages and streams into the structured storage.
    /// Creates directory entries and updates fat.
    /// </summary>
    /// <param name="storage">Storage to write.</param>
    /// <param name="storageEntry">Directory entry that corresponds to this storage.
    /// Pass null to indicate root storage.</param>
    private void writeStorage(MemoryStorage storage, DirEntry storageEntry) throws Exception
    {
        //Create root storage when given a null directory entry.
        if (storageEntry == null)
        {
            storageEntry = new DirEntry("Root Entry", true, storage.getClsid());
            mDirEntries.add(storageEntry);
        }

        //Set the pointer to the first child if there is at least one child.
        if (storage.getCount() > 0)
            storageEntry.Child = (/*uint*/int)mDirEntries.getCount();

        //Write the children.
        for (int i = 0; i < storage.getCount(); i++)
        {
            DirEntry childEntry;
            String itemName = storage.getKey(i);
            Object item = storage.getByIndex(i);
            MemoryStorage itemMemoryStorage = ms.as(item, MemoryStorage.class);
            Stream itemStream = ms.as(item, Stream.class);

            if (itemMemoryStorage != null)
            {
                //Create directory entry and write a child storage.
                childEntry = new DirEntry(itemName, false, itemMemoryStorage.getClsid());
                mDirEntries.add(childEntry);
                writeStorage(itemMemoryStorage, childEntry);
            }
            else if (itemStream != null)
            {
                //Create a directory entry and write a child stream.
                childEntry = new DirEntry(itemName, DirEntryType.STREAM, itemStream.getLength());
                mDirEntries.add(childEntry);
                childEntry.SectStart = writeStream(itemStream, false);
            }
            else
                throw new IllegalStateException("Unknown object in memory storage.");

            //If this is not the last child, set its right sibling to next item.
            if (i < storage.getCount() - 1)
                childEntry.RightSib = (/*uint*/int)mDirEntries.getCount();
        }
    }

    /// <summary>
    /// Writes a stream into the structured storage and updates fat or mini fat structures.
    /// Returns the start sector of the written data.
    /// WORDSNET-15430 Allow FileStream to be written.
    /// </summary>
    private /*uint*/int writeStream(Stream srcStream, boolean isForceFat, /*out*/RefInt sectorCount) throws Exception
    {
        sectorCount.set(0);
        if (srcStream.getLength() == 0)
            return FatEntryType.END_OF_CHAIN;

        //Select fat or mini fat structures to use.
        boolean isFat = (mHeader.isBigEnoughForFat(srcStream.getLength()) || isForceFat);
        SectCollection fat = (isFat) ? mFat : mMiniFat;
        Stream dstStream = (isFat) ? mFileSystemStream : mMiniStream;
        int sectorSize = (isFat) ? Sector.SECTOR_SIZE : Sector.MINI_SECTOR_SIZE;

        //Remember start sector and write the stream.
        /*uint*/int sectStart = Sector.offsetToSector(dstStream.getPosition(), isFat);
        srcStream.setPosition(0);
        StreamUtil.copyStream(srcStream, dstStream);

        //Pad to sector size.
        StreamUtil.seekToNextPage(dstStream, sectorSize);

        //Update fat to include all sectors used by the stream.
        sectorCount.set(MathUtil.divUp(srcStream.getLength(), sectorSize));
        //Start from 1 because first entry in the chain is known from the directory entry.
        for (/*uint*/int i = 1; (i & 0xFFFFFFFFL) < sectorCount.get(); i++)
            fat.add((/*uint*/int)((sectStart & 0xFFFFFFFFL) + (i & 0xFFFFFFFFL)));
        fat.add(FatEntryType.END_OF_CHAIN);

        return sectStart;
    }

    /// <summary>
    /// Same as above, just does not return number of sectors written.
    /// WORDSNET-15430 Allow FileStream to be written.
    /// </summary>
    private /*uint*/int writeStream(Stream srcStream, boolean isForceFat) throws Exception
    {
        int dummy = 0;
        RefInt referenceToDummy = new RefInt(dummy);
        /*uint*/int startSector = writeStream(srcStream, isForceFat, /*out*/ referenceToDummy);
        dummy = referenceToDummy.get();
        // RK Split the return statement so autoporting can work.
        return startSector;
    }

    /// <summary>
    /// Reads all directory entries into a flat list.
    /// </summary>
    /// <remarks>
    /// This is stored as a stream and it would be nice to read it using ReadStream,
    /// but I cannot since the length of this stream is not known.
    /// </remarks>
    private void readDirectory() throws Exception
    {
        Debug.assertTrue(mHeader != null, "Header must be read before.");
        Debug.assertTrue(mFat != null, "Fat must be read before.");

        mDirEntries = new DirEntryCollection();
        BinaryReader reader = new BinaryReader(mFileSystemStream, Encoding.getUnicode());
        final int DIR_ENTRIES_PER_SECTOR = Sector.SECTOR_SIZE / DirEntry.STRUCTURE_SIZE;

        //Walk the FAT to go through all directory entries.
        /*uint*/int sect = mHeader.SectDirStart;

        HashSetGeneric<Integer> sectRead = new HashSetGeneric<Integer>();
        while (sect != FatEntryType.END_OF_CHAIN)
        {
            mFileSystemStream.setPosition(Sector.sectorToOffset(sect, true));

            for (int i = 0; i < DIR_ENTRIES_PER_SECTOR; i++)
                mDirEntries.add(new DirEntry(reader));

            sectRead.add(sect);

            //Get the next sector of the directory entry.
            sect = mFat.get(sect);

            // Verify FAT has no cycles.
            if(sectRead.contains(sect))
            {
                // WARN.
                throw new IllegalStateException("The structured storage file seems to be corrupt. FAT contains cycles.");
            }
        }
    }

    /// <summary>
    /// Reads the specified number of bytes from the beginning of the specified stream.
    /// </summary>
    /// <param name="sectStart"></param>
    /// <param name="streamLength">The full length of the stream as specified in the dir entry.
    /// Needed to decide whether to read from FAT or MiniFAT.</param>
    /// <param name="lengthToRead">The length of the stream to read.</param>
    /// <param name="isForceFat">True to force reading from FAT. False to let the
    /// code decide whether to read from FAT or MiniFAT.</param>
    private MemoryStream readStream(
        /*uint*/int sectStart,
        int streamLength,
        int lengthToRead,
        boolean isForceFat) throws Exception
    {
        lengthToRead = Math.min(streamLength, lengthToRead);

        // SPEED Specify length to reduce the number of memory allocations.
        MemoryStream dstStream = new MemoryStream(lengthToRead);
        dstStream.setLength(lengthToRead);

        // Select main or mini fat structures.
        boolean isFat = (isForceFat || mHeader.isBigEnoughForFat(streamLength));

        if (!isFat)
        {
            // Read mini stream if present and not loaded.
            DirEntry rootEntry = mDirEntries.get(0);
            if ((rootEntry.SectStart != DirEntry.NO_STREAM) && (mMiniStream == null))
                mMiniStream = readStream(rootEntry.SectStart, (int)rootEntry.Size, (int)rootEntry.Size, true);

            // RK Resiliency. Reading from mini stream is requested, but mini fat or mini stream is
            // missing, therefore return without reading.
            if ((mHeader.getMiniFatLength() == 0) || (mMiniStream == null))
            {
                dstStream.setLength(0);
                return dstStream;
            }
        }

        SectCollection fat = (isFat) ? mFat : mMiniFat;
        Stream srcStream = (isFat) ? mFileSystemStream : mMiniStream;
        int sectorSize = (isFat) ? Sector.SECTOR_SIZE : Sector.MINI_SECTOR_SIZE;

        //Maintain dstPos to know where to write in the destination memory stream.
        int dstPos = 0;

        //Maintain srcPos to avoid unnecessary seeks when sectors are continuous to speed up reading.
        long srcPos = srcStream.getPosition();

        //Walk the fat and read all sectors of the stream.
        /*uint*/int sect = sectStart;

        // WORDSNET-5326 Missed EndOfChain marker in FAT (FreeSect occurred instead) but it seems OK for Word so we should do the same.
        while ((sect != FatEntryType.END_OF_CHAIN) && (sect != FatEntryType.FREE_SECT))
        {
            long newSrcPos = Sector.sectorToOffset(sect, isFat);
            if (srcPos != newSrcPos)
            {
                // WORDSNET-20574 Offset beyond the stream.
                if (newSrcPos > srcStream.getLength())
                    break;

                srcStream.setPosition(newSrcPos);
                srcPos = newSrcPos;
            }

            int bytesRemaining = lengthToRead - dstPos;

            //Normally this is not needed because loop terminated at the end of chain marker.
            //However I've seen an invalid document that has no end marker.
            if (bytesRemaining == 0)
                break;

            int bytesToRead = Math.min(sectorSize, bytesRemaining);
            int bytesRead = srcStream.read(dstStream.getBuffer(), dstPos, bytesToRead);

            // This should not happen, but this does happen.
            // For example in defect 1049 there are 511 bytes instead of 512 at the end of the file.
            if (bytesRead != bytesToRead)
                Debug.writeLine("The structured file is probably corrupt. Cannot read all data that was expected, but okay to continue.");

            // WORDSNET-1049 penning document in Aspose.Words fails with 'Object reference not set to an instance of an object.' exception.
            // Used to add number of bytes read, but should add number of bytes that we intended to read.
            // The file was shorter by 1 byte than expected.
            dstPos += bytesToRead;
            srcPos += bytesToRead;

            // WORDSNET-16071 attached document with corrupted stream throws exception in AW.
            // Word reads file well so we should stop reading failed stream
            // and fail (or not - in case the stream is not used) somewhere else
            if ((sect & 0xFFFFFFFFL) >= fat.getCount())
                break;

            sect = fat.get(sect);
        }

        return dstStream;
    }

    /// <summary>
    /// Reads just the first sector of the first stream that matches this name.
    /// Use this for things like file format detection without loading all storage into memory.
    /// </summary>
    public MemoryStream readOneSectorFromStreamInRootStorage(String streamName) throws Exception
    {
        DirEntry dirEntry = mDirEntries.getEntryInRoot(streamName);
        if (dirEntry == null)
            throw new IllegalStateException(msString.format("Cannot find directory entry {0}.", streamName));

        return readStream(dirEntry.SectStart, (int)dirEntry.Size, Sector.SECTOR_SIZE, false);
    }

    /// <summary>
    /// Loads content of all streams and storages into memory.
    /// </summary>
    private void readData() throws Exception
    {
        readData(mDirEntries.get(0), null);
    }

    /// <summary>
    /// Recursively reads content of all streams and storages into memory starting from the specified
    /// directory entry.
    /// </summary>
    private void readData(DirEntry dirEntry, MemoryStorage parent) throws Exception
    {
        // WORDSNET-1045 Document hangs up Aspose.Words when opened.
        // Lets allow reading structured storage files with circular references in the directory
        // because this is what MS Word allows to do.
        if (dirEntry.IsRead)
        {
            Debug.writeLine("The structured storage file seems to be corrupt. This directory entry was already read, ignoring.");
            return;
        }
        else
        {
            dirEntry.IsRead = true;
        }

        switch (dirEntry.EntryType)
        {
            case DirEntryType.ROOT:
            case DirEntryType.STORAGE:
            {
                MemoryStorage storage = new MemoryStorage(dirEntry.Clsid);
                // WORDSNET-11915 Prevent reading Root Entry more than once while reading corrupted files.
                if ((dirEntry.EntryType == DirEntryType.ROOT) && (mRoot == null))
                    mRoot = storage;
                else
                    parent.add(dirEntry.Name, storage);

                // Recursion. Read all children if they are present.
                DirEntry child = mDirEntries.getSafe(dirEntry, dirEntry.Child);
                if (child != null)
                    readData(child, storage);

                break;
            }
            case DirEntryType.STREAM:
            {
                // WORDSNET-22534 Resilience for invalid entry.
                if((int)dirEntry.Size >= 0)
                    parent.add(dirEntry.Name, readStream(dirEntry.SectStart, (int)dirEntry.Size, (int)dirEntry.Size, false));
                break;
            }
            case DirEntryType.INVALID:
                // This seems to be all zeroes, skip such entries.
                return;
            default:
                throw new IllegalStateException("Unknown type of directory entry in the structured storage.");
        }

        // Read all siblings from left and right completely and add them to the current parent storage.
        DirEntry left = mDirEntries.getSafe(dirEntry, dirEntry.LeftSib);
        if (left != null)
            readData(left, parent);

        DirEntry right = mDirEntries.getSafe(dirEntry, dirEntry.RightSib);
        if (right != null)
            readData(right, parent);
    }

    /// <summary>
    /// Returns true if the structured storage contains the specified storage or stream. Case sensitive compare.
    /// </summary>
    public boolean containsInRootStorage(String name)
    {
        return (mDirEntries.getEntryInRoot(name) != null);
    }

    /// <summary>
    /// Access to the in-memory version of the structured storage.
    /// </summary>
    public MemoryStorage getRoot() throws Exception
    {
        // RK This is delay reading introduced for detecting DOC file format without loading all data.
        if (mRoot == null)
            readData();

        return mRoot;
    }

    public static byte[] getStructuredStorageSignature() { return G_STRUCTURED_STORAGE_SIGNATURE; }

    /// <summary>
    /// Exposed for unit testing only.
    /// </summary>
    Fat getFat() { return mFat; }

    /// <summary>
    /// The stream that contains the file in the structured storage format.
    /// </summary>
    private Stream mFileSystemStream;
    private Header mHeader;
    private Fat mFat;
    private SectCollection mMiniFat;
    private DirEntryCollection mDirEntries;
    private MemoryStream mMiniStream;
    private MemoryStorage mRoot;
    private static /*final*/ byte[] G_STRUCTURED_STORAGE_SIGNATURE = new byte[] {(byte) 0xd0, (byte) 0xcf, (byte) 0x11, (byte) 0xe0};
}

