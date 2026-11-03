// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/05/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FileSystem;
import com.aspose.Debug;
import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.StreamUtil;
import com.aspose.ms.System.Text.Encoding;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// This class allows to work with structured storage documents organized according to the Data Spaces
/// format as specified in the [MS-OFFCRYPTO]: Office Document Cryptography Structure Specification.
/// </summary>
class DataSpaces
{
    /// <summary>
    /// Creates a data spaces manager from an existing structured storage document.
    /// </summary>
    /// <param name="fs">The structured storage document must be a valid data spaces document.</param>
    /// <exception cref="InvalidOperationException">If some data is unexpected or invalid.</exception>
    DataSpaces(FileSystem fs) throws Exception
    {
        Debug.assertTrue(fs != null);

        mFs = fs;
        mDataSpaces = fs.getRoot().getStorageSafe("\u0006DataSpaces");

        if(mDataSpaces != null)
        {
            verifyDataSpacesVersion();
            readDataSpaceMap();
        }
    }

    boolean isEncryptedEcma376()
    {
        if (mDataSpaces == null)
            return mFs.containsInRootStorage("EncryptedPackage") && mFs.containsInRootStorage("EncryptionInfo");

        // Verify data space map.
        if (mDataSpaceMap.size() != 1)
            return false;

        DataSpaceMapEntry entry = mDataSpaceMap.get(0);
        if (!"StrongEncryptionDataSpace".equals(entry.DataSpaceName))
            return false;

        if (entry.ReferenceComponents.size() != 1)
            return false;

        if (!"EncryptedPackage".equals(entry.ReferenceComponents.get(0)))
            return false;

        // I think this is enough to detect the ECMA-376 encryption.
        return true;
    }

    /// <summary>
    /// Writes "DataSpaces" data.
    /// </summary>
    /// <remarks>
    /// AM. These streams are subset of MS IRMDS and always the same for encryption purpose.
    /// I decided to get them from encrypted DOCX file produced by Word2007.
    /// </remarks>
    private static void initDataSpaces(FileSystem fs) throws Exception
    {
        MemoryStorage dataSpaceInfo = new MemoryStorage();
        dataSpaceInfo.add("StrongEncryptionDataSpace", new MemoryStream(G_STRONG_ENCRYPTION_DATA_SPACE));

        MemoryStorage strongEncryptionTransform = new MemoryStorage();
        strongEncryptionTransform.add("\u0006Primary", new MemoryStream(G0_006_PRIMARY));
        MemoryStorage transformInfo = new MemoryStorage();
        transformInfo.add("StrongEncryptionTransform", strongEncryptionTransform);

        MemoryStorage dataSpaces = new MemoryStorage();

        dataSpaces.add("DataSpaceInfo", dataSpaceInfo);
        dataSpaces.add("DataSpaceMap", new MemoryStream(G_DATA_SPACE_MAP));
        dataSpaces.add("Version", new MemoryStream(G_VERSION));
        dataSpaces.add("TransformInfo", transformInfo);

        fs.getRoot().add("\u0006DataSpaces", dataSpaces);
    }

    /// <summary>
    /// Encrypt stream with given password.
    /// </summary>
    static FileSystem encrypt(Stream stream, String password) throws Exception
    {
        FileSystem fs = new FileSystem(new Guid("00000000-0000-0000-0000-000000000000"));
        initDataSpaces(fs);

        IEcma376Encryption encryption = new Ecma376StandardEncryption();

        MemoryStream encryptionInfo = new MemoryStream();
        MemoryStream encryptedPackage = new MemoryStream();
        encryption.encrypt(encryptionInfo, stream, encryptedPackage, password);

        fs.getRoot().add("EncryptionInfo", encryptionInfo);
        fs.getRoot().add("EncryptedPackage", encryptedPackage);
        return fs;
    }

    /// <summary>
    /// Should be called when the document is ECMA-376 or Agile encrypted.
    /// At the moment supports only the Standard Encryption method.
    /// </summary>
    /// <exception cref="InvalidOperationException">If some data is unexpected or invalid.</exception>
    /// <exception cref="IncorrectPasswordException">If the password is incorrect.</exception>
    MemoryStream decrypt(String password) throws Exception
    {
        if(mDataSpaces != null)
            verifyEcma376EncryptionTransform();

        Stream encryptionInfo = mFs.getRoot().fetchStream("EncryptionInfo");
        Stream encryptedPackage = mFs.getRoot().fetchStream("EncryptedPackage");

        IEcma376Encryption encryption = getEncryption(encryptionInfo);

        MemoryStream decryptedData = new MemoryStream();
        encryption.decrypt(encryptionInfo, encryptedPackage, decryptedData, password);
        return decryptedData;
    }

    /// <summary>
    /// Reads EncryptionVersionInfo and returns appropriate IEcma376Encryption class.
    /// </summary>
    private static IEcma376Encryption getEncryption(Stream encryptionInfo) throws Exception
    {
        BinaryReader reader = new BinaryReader(encryptionInfo);
        /*EncryptionVersionInfo*/int versionInfo = (/*EncryptionVersionInfo*/int)reader.readInt32();

        IEcma376Encryption encryption;
        switch (versionInfo)
        {
            case EncryptionVersionInfo.STANDARD:
            case EncryptionVersionInfo.STANDARD_2010:
                encryption = new Ecma376StandardEncryption();
                break;

            case EncryptionVersionInfo.EXTENSIBLE:
            case EncryptionVersionInfo.EXTENSIBLE_2010:
                throw new UnsupportedFileFormatException(
                    "The document is encrypted using the Extensible Encryption and this is not currently supported.");

            case EncryptionVersionInfo.AGILE:
                encryption = new Ecma376AgileEncryption();
                break;

            default:
                throw new IllegalStateException("Unexpected encryption version.");
        }

        encryptionInfo.setPosition(0);
        return encryption;
    }

    private void verifyEcma376EncryptionTransform() throws Exception
    {
        // Verify data space info.
        String[] transformReferences = readTransformReferences("StrongEncryptionDataSpace");
        if ((transformReferences.length != 1) || (!"StrongEncryptionTransform".equals(transformReferences[0])))
            throw new IllegalStateException("Unexpected transform references.");

        MemoryStorage transformInfo = mDataSpaces.fetchStorage("TransformInfo");
        MemoryStorage transformStorage = transformInfo.fetchStorage(transformReferences[0]);

        MemoryStream primaryStream = transformStorage.getStreamZeroPositioned("\u0006Primary");
        if(primaryStream == null)
            primaryStream = transformStorage.fetchStream("Primary");

        BinaryReader reader = new BinaryReader(primaryStream);

        // Verify IRMDSTransformInfo
        reader.readInt32(); // int transformLength =

        int transformType = reader.readInt32();
        if (transformType != 1)
            throw new IllegalStateException("Unexpected transform type.");

        // WORDSNET-22837 Seems Word does not verify rest (ID, name and reader/updater/writer versions).
        readUnicodeLPP4(reader);    // "{FF9A3F03-56EF-4613-BDD5-5A41C1D07246}"
        readUnicodeLPP4(reader);    // "Microsoft.Container.EncryptionTransform"

        if(StreamUtil.hasEnoughBytesToRead(reader, 4 * 3 /* 3 * Int32 */))
            verifyReaderUpdaterWriter(reader);

        // 2.2.6 IRMDSTransformInfo Structure specification states that the transform info should contain
        // a 32-bit integer length followed by a UTF-8-LP-P4 structure with XML license.
        // But it looks like something is wrong.
        // I can read a string from here such as "AES 128" using readUtf8LPP4, and then a few more bytes,
        // but does not look like what the spec says. So let's ignore completely.

        // EncryptionTransformInfo is stored here, but from the spec it seems to have no authority.
    }

    /// <summary>
    /// Reads and returns transforms that are specified for the given data space definition.
    /// </summary>
    private String[] readTransformReferences(String dataSpaceName) throws Exception
    {
        MemoryStorage dataSpaceInfo = mDataSpaces.fetchStorage("DataSpaceInfo");
        BinaryReader reader = new BinaryReader(dataSpaceInfo.fetchStream(dataSpaceName));

        int headerLength = reader.readInt32();
        if (headerLength != 8)
            throw new IllegalStateException("Unexpected header length.");

        int transformCount = reader.readInt32();
        String[] result = new String[transformCount];
        for (int i = 0; i < transformCount; i++)
            result[i] = readUnicodeLPP4(reader);

        return result;
    }

    private void verifyDataSpacesVersion() throws Exception
    {
        // WORDSNET-22837 Seems Word does not check Version stream.
        if (mDataSpaces.containsKey(VERSION_STREAM_NAME))
        {
            BinaryReader reader = new BinaryReader(mDataSpaces.fetchStream(VERSION_STREAM_NAME));

            if (!"Microsoft.Container.DataSpaces".equals(readUnicodeLPP4(reader)))
                throw new IllegalStateException("Unexpected FeatureIdentifier.");

            verifyReaderUpdaterWriter(reader);
        }
    }

    private static void verifyReaderUpdaterWriter(BinaryReader reader) throws Exception
    {
        int readerVersion = reader.readInt32();
        if (readerVersion != 0x00000001)
            throw new IllegalStateException("Unexpected reader version.");

        int updatedVersion = reader.readInt32();
        if (updatedVersion != 0x00000001)
            throw new IllegalStateException("Unexpected updated version.");

        int writerVersion = reader.readInt32();
        if (writerVersion != 0x00000001)
            throw new IllegalStateException("Unexpected writer version.");
    }

    private static String readUnicodeLPP4(BinaryReader reader) throws Exception
    {
        int lengthBytes = reader.readInt32();
        byte[] data = reader.readBytes(lengthBytes);
        StreamUtil.seekToNextPage(reader.getBaseStream(), 4);
        return Encoding.getUnicode().getString(data);
    }

    /// <summary>
    /// Reads the stream that associates protected content with the data space definition used to transform it.
    /// </summary>
    private void readDataSpaceMap() throws Exception
    {
        BinaryReader reader = new BinaryReader(mDataSpaces.fetchStream("DataSpaceMap"));

        int headerLength = reader.readInt32();
        if (headerLength != 0x00000008)
            throw new IllegalStateException("Unexpected header length.");

        mDataSpaceMap = new ArrayList<DataSpaceMapEntry>();

        int entryCount = reader.readInt32();
        for (int i = 0; i < entryCount; i++)
        {
            DataSpaceMapEntry entry = readDataSpaceMapEntry(reader);
            msArrayList.add(mDataSpaceMap, entry);
        }
    }

    private static DataSpaceMapEntry readDataSpaceMapEntry(BinaryReader reader) throws Exception
    {
        DataSpaceMapEntry entry = new DataSpaceMapEntry();

        reader.readInt32(); // int entryLength =
        int componentCount = reader.readInt32();
        for (int i = 0; i < componentCount; i++)
        {
            reader.readInt32(); // Specifies whether stream or storage, I don't need this. // int componentType =
            String component = readUnicodeLPP4(reader);
            entry.ReferenceComponents.add(component);
        }

        entry.DataSpaceName = readUnicodeLPP4(reader);

        return entry;
    }

    private /*final*/ FileSystem mFs;
    private /*final*/ MemoryStorage mDataSpaces;
    /// <summary>
    /// Items are <see cref="DataSpaceMapEntry"/>.
    /// </summary>
    private ArrayList<DataSpaceMapEntry> mDataSpaceMap;

    private static final String VERSION_STREAM_NAME = "Version";

    //>>>>>>>> #region  "DataSpaces" content.
    /// <summary>
    /// See [MS-OFFCRYPTO] 2.3.4 ECMA-376 Document Encryption.
    /// </summary>
    private static /*final*/ byte[] G_VERSION = new byte[]
    {
        (byte) 0x3C, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x4D, (byte) 0x00, (byte) 0x69, (byte) 0x00, (byte) 0x63, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x6F, (byte) 0x00, (byte) 0x73, (byte) 0x00, (byte) 0x6F, (byte) 0x00,
        (byte) 0x66, (byte) 0x00, (byte) 0x74, (byte) 0x00, (byte) 0x2E, (byte) 0x00, (byte) 0x43, (byte) 0x00, (byte) 0x6F, (byte) 0x00, (byte) 0x6E, (byte) 0x00, (byte) 0x74, (byte) 0x00, (byte) 0x61, (byte) 0x00, (byte) 0x69, (byte) 0x00,
        (byte) 0x6E, (byte) 0x00, (byte) 0x65, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x2E, (byte) 0x00, (byte) 0x44, (byte) 0x00, (byte) 0x61, (byte) 0x00, (byte) 0x74, (byte) 0x00, (byte) 0x61, (byte) 0x00, (byte) 0x53, (byte) 0x00,
        (byte) 0x70, (byte) 0x00, (byte) 0x61, (byte) 0x00, (byte) 0x63, (byte) 0x00, (byte) 0x65, (byte) 0x00, (byte) 0x73, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00
    };

    /// <summary>
    /// See [MS-OFFCRYPTO] 2.3.4.1 \0x06DataSpaces\DataSpaceMap Stream.
    /// </summary>
    private static /*final*/ byte[] G_DATA_SPACE_MAP = new byte[]
    {
        (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x68, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x20, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x6E, (byte) 0x00, (byte) 0x63, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x79, (byte) 0x00, (byte) 0x70, (byte) 0x00,
        (byte) 0x74, (byte) 0x00, (byte) 0x65, (byte) 0x00, (byte) 0x64, (byte) 0x00, (byte) 0x50, (byte) 0x00, (byte) 0x61, (byte) 0x00, (byte) 0x63, (byte) 0x00, (byte) 0x6B, (byte) 0x00, (byte) 0x61, (byte) 0x00, (byte) 0x67, (byte) 0x00,
        (byte) 0x65, (byte) 0x00, (byte) 0x32, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x53, (byte) 0x00, (byte) 0x74, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x6F, (byte) 0x00, (byte) 0x6E, (byte) 0x00, (byte) 0x67, (byte) 0x00,
        (byte) 0x45, (byte) 0x00, (byte) 0x6E, (byte) 0x00, (byte) 0x63, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x79, (byte) 0x00, (byte) 0x70, (byte) 0x00, (byte) 0x74, (byte) 0x00, (byte) 0x69, (byte) 0x00, (byte) 0x6F, (byte) 0x00,
        (byte) 0x6E, (byte) 0x00, (byte) 0x44, (byte) 0x00, (byte) 0x61, (byte) 0x00, (byte) 0x74, (byte) 0x00, (byte) 0x61, (byte) 0x00, (byte) 0x53, (byte) 0x00, (byte) 0x70, (byte) 0x00, (byte) 0x61, (byte) 0x00, (byte) 0x63, (byte) 0x00,
        (byte) 0x65, (byte) 0x00,
        (byte) 0x00, (byte) 0x00
    };

    /// <summary>
    /// See [MS-OFFCRYPTO] 2.3.4.2 \0x06DataSpaces\DataSpaceInfo Storage.
    /// </summary>
    private static /*final*/ byte[] G_STRONG_ENCRYPTION_DATA_SPACE = new byte[]
    {
        (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x32, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x53, (byte) 0x00, (byte) 0x74, (byte) 0x00, (byte) 0x72, (byte) 0x00,
        (byte) 0x6F, (byte) 0x00, (byte) 0x6E, (byte) 0x00, (byte) 0x67, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x6E, (byte) 0x00, (byte) 0x63, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x79, (byte) 0x00, (byte) 0x70, (byte) 0x00,
        (byte) 0x74, (byte) 0x00, (byte) 0x69, (byte) 0x00, (byte) 0x6F, (byte) 0x00, (byte) 0x6E, (byte) 0x00, (byte) 0x54, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x61, (byte) 0x00, (byte) 0x6E, (byte) 0x00, (byte) 0x73, (byte) 0x00,
        (byte) 0x66, (byte) 0x00, (byte) 0x6F, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x6D, (byte) 0x00, (byte) 0x00, (byte) 0x00
    };

    /// <summary>
    /// See [MS-OFFCRYPTO] 2.3.4.3 \0x06DataSpaces\TransformInfo Storage.
    /// </summary>
    private static /*final*/ byte[] G0_006_PRIMARY = new byte[]
    {
        (byte) 0x58, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x4C, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x7B, (byte) 0x00, (byte) 0x46, (byte) 0x00, (byte) 0x46, (byte) 0x00,
        (byte) 0x39, (byte) 0x00, (byte) 0x41, (byte) 0x00, (byte) 0x33, (byte) 0x00, (byte) 0x46, (byte) 0x00, (byte) 0x30, (byte) 0x00, (byte) 0x33, (byte) 0x00, (byte) 0x2D, (byte) 0x00, (byte) 0x35, (byte) 0x00, (byte) 0x36, (byte) 0x00,
        (byte) 0x45, (byte) 0x00, (byte) 0x46, (byte) 0x00, (byte) 0x2D, (byte) 0x00, (byte) 0x34, (byte) 0x00, (byte) 0x36, (byte) 0x00, (byte) 0x31, (byte) 0x00, (byte) 0x33, (byte) 0x00, (byte) 0x2D, (byte) 0x00, (byte) 0x42, (byte) 0x00,
        (byte) 0x44, (byte) 0x00, (byte) 0x44, (byte) 0x00, (byte) 0x35, (byte) 0x00, (byte) 0x2D, (byte) 0x00, (byte) 0x35, (byte) 0x00, (byte) 0x41, (byte) 0x00, (byte) 0x34, (byte) 0x00, (byte) 0x31, (byte) 0x00, (byte) 0x43, (byte) 0x00,
        (byte) 0x31, (byte) 0x00, (byte) 0x44, (byte) 0x00, (byte) 0x30, (byte) 0x00, (byte) 0x37, (byte) 0x00, (byte) 0x32, (byte) 0x00, (byte) 0x34, (byte) 0x00, (byte) 0x36, (byte) 0x00, (byte) 0x7D, (byte) 0x00, (byte) 0x4E, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x4D, (byte) 0x00, (byte) 0x69, (byte) 0x00, (byte) 0x63, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x6F, (byte) 0x00, (byte) 0x73, (byte) 0x00, (byte) 0x6F, (byte) 0x00, (byte) 0x66, (byte) 0x00,
        (byte) 0x74, (byte) 0x00, (byte) 0x2E, (byte) 0x00, (byte) 0x43, (byte) 0x00, (byte) 0x6F, (byte) 0x00, (byte) 0x6E, (byte) 0x00, (byte) 0x74, (byte) 0x00, (byte) 0x61, (byte) 0x00, (byte) 0x69, (byte) 0x00, (byte) 0x6E, (byte) 0x00,
        (byte) 0x65, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x2E, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x6E, (byte) 0x00, (byte) 0x63, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x79, (byte) 0x00, (byte) 0x70, (byte) 0x00,
        (byte) 0x74, (byte) 0x00, (byte) 0x69, (byte) 0x00, (byte) 0x6F, (byte) 0x00, (byte) 0x6E, (byte) 0x00, (byte) 0x54, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x61, (byte) 0x00, (byte) 0x6E, (byte) 0x00, (byte) 0x73, (byte) 0x00,
        (byte) 0x66, (byte) 0x00, (byte) 0x6F, (byte) 0x00, (byte) 0x72, (byte) 0x00, (byte) 0x6D, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00,
        (byte) 0x00, (byte) 0x00
    };
    //<<<<<<<< #endregion 
}

