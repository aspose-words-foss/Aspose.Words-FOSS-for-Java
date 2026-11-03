// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;
import com.aspose.BitUtil;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.DigestAlgorithm;
import com.aspose.IDigest;
import com.aspose.CryptoUtilPal;
import com.aspose.HashUtil;
import com.aspose.ArrayUtil;
import com.aspose.StringToIntBidirectionalMap;


/// <summary>
/// Contains data and algorithms for calculating an OOXML password hash.
/// </summary>
class PasswordHash implements Cloneable
{

    /// <summary>
    /// Calculates the OOXML hash code for a document protection password.
    /// </summary>
    void calculateDocumentProtectionHash(int legacyHash, PasswordHash passwordHash)
    {
        // andrnosk: WORDSNET-9724 We have to use algorithm, spin count and salt values specified inside document 
        // to calculate correct hash.
        CryptAlgorithmSid = passwordHash.CryptAlgorithmSid > 0 ? passwordHash.CryptAlgorithmSid : DEFAULT_ALGORITHM_SID;
        // WORDSNET-22989 AW should to use value from the document file even it is zero.
        CryptSpinCount = passwordHash.isEmpty() ? DEFAULT_SPIN_COUNT : passwordHash.CryptSpinCount;
        Salt = (passwordHash.Salt != null) ? passwordHash.Salt : DEFAULT_SALT;

        // We are going to have 16 bytes salt and 16 bytes of the legacy hash string.
        byte[] initialBytes = new byte[32];
        System.arraycopy(Salt, 0, initialBytes, 0, Salt.length);

        // Write 16 bytes legacy hash as a Unicode hex string representation of the 4-byte legacy hash.
        String legacyHashString = FormatterPal.intToStrX8(BitUtil.swapInt32(legacyHash));
        byte[] legacyHashBytes = Encoding.getUnicode().getBytes(legacyHashString);
        System.arraycopy(legacyHashBytes, 0, initialBytes, 16, legacyHashBytes.length);

        // WORDSNET-10286 Cryptographic hashing algorithm SHA-512 (w:cryptAlgorithmSid="14") was defined for document
        // and hence should be used to compute a hash value.
        Hash = calculateDocxHash(CryptAlgorithmSid, initialBytes, CryptSpinCount);
    }

    /// <summary>
    /// Calculates the OOXML hash code for a write protection password.
    /// </summary>
    void calculateWriteProtectionHash(String password, PasswordHash passwordHash)
    {
        CryptAlgorithmSid = passwordHash.CryptAlgorithmSid > 0 ? passwordHash.CryptAlgorithmSid : DEFAULT_ALGORITHM_SID;
        CryptSpinCount = passwordHash.CryptSpinCount > 0 ? passwordHash.CryptSpinCount : DEFAULT_SPIN_COUNT;
        Salt = (passwordHash.Salt != null) ? passwordHash.Salt : DEFAULT_SALT;

        // Build 16 bytes salt prepended to the password.
        byte[] passwordBytes = Encoding.getUnicode().getBytes(password);
        byte[] initialBytes = new byte[16 + passwordBytes.length];
        System.arraycopy(Salt, 0, initialBytes, 0, Salt.length);
        System.arraycopy(passwordBytes, 0, initialBytes, 16, passwordBytes.length);

        Hash = calculateDocxHash(CryptAlgorithmSid, initialBytes, CryptSpinCount);
    }

    /// <summary>
    /// Deep clone.
    /// </summary>
    PasswordHash deepClone()
    {
        // RK Memberwise is enough at the moment.
        return (PasswordHash)memberwiseClone();
    }

    /// <summary>
    /// Maps hash algorithm names to int values as expected by old OOXML1.
    /// ISO 29500 quietly introduces new field algorithmName that seems to be equivalent to 
    /// cryptAlgorithmSid as defined in ECMA 376.
    /// </summary>
    static int cryptAlgorithmSidFromAlgorithmName(String name)
    {
        return G_ALGORITHM_SID_NAME_MAP.getValue(name, UNUSED_ALGORITHM_SID);
    }

    static String cryptAlgorithmNameFromSid(int sid)
    {
        return G_ALGORITHM_SID_NAME_MAP.getValue(sid);
    }

    /// <summary>
    /// Returns hash, calculated using the given algorithm.
    /// </summary>
    /// <param name="cryptAlgorithmSid">Hash algorithm ID.</param>
    /// <param name="initialBytes">Initial message for applying hash algorithm.</param>
    /// <param name="spinCount">How many times hashing algorithm will be applied.</param>
    private static byte[] calculateDocxHash(int cryptAlgorithmSid, byte[] initialBytes, int spinCount)
    {
        /*DigestAlgorithm*/int alg = (cryptAlgorithmSid == cryptAlgorithmSidFromAlgorithmName("SHA-512")) ? DigestAlgorithm.SHA_512 : DigestAlgorithm.SHA_1;
        IDigest digest = CryptoUtilPal.createHashAlgorithm(alg);

        int digestSize = digest.getDigestSize();

        byte[] hash = HashUtil.computeHash(digest, initialBytes);

        // Hash length, followed by 4 bytes containing the number of the iteration
        byte[] iterationBytes = new byte[digestSize + BLOCK_SIZE];
        for (int iterationNumber = 0; iterationNumber < spinCount; iterationNumber++)
        {
            System.arraycopy(hash, 0, iterationBytes, 0, hash.length);
            ArrayUtil.writeUInt32ToByteArray(iterationNumber, iterationBytes, digestSize);
            hash = HashUtil.computeHash(digest, iterationBytes);
        }

        return hash;
    }


    /// <summary>
    /// Returns true if there is no password.
    /// </summary>
    boolean isEmpty() { return (Hash == null); }

    /// <summary>
    /// The value of OOXML "secure" hash.
    /// Null if there is no hash.
    /// </summary>
    byte[] Hash;

    /// <summary>
    /// Salt added before calculating the OOXML hash.
    /// </summary>
    byte[] Salt;

    /// <summary>
    /// The type of the hash algorithm used for the OOXML hash.
    /// </summary>
    int CryptAlgorithmSid;

    /// <summary>
    /// The number of times OOXML hash is calculated.
    /// </summary>
    int CryptSpinCount;

    /// <summary>
    /// We use fixed salt when hashing so unit tests and gold files can pass.
    /// </summary>
    static /*final*/ byte[] DEFAULT_SALT = new byte[] { (byte) 132, (byte) 138, (byte) 142, (byte) 151, (byte) 223, (byte) 100, (byte) 34, (byte) 191, (byte) 254, (byte) 55, (byte) 72, (byte) 114, (byte) 162, (byte) 241, (byte) 42, (byte) 237 };
    /// <summary>
    /// This is what MS Word does.
    /// </summary>
    static final int DEFAULT_SPIN_COUNT = 50000;

    /// <summary>
    ///  SHA-1. This is what MS Word does.
    /// </summary>
    static final int DEFAULT_ALGORITHM_SID = 4;

    /// <summary>
    /// Per OOXML spec any value above 14 is undefined value, so this one is.
    /// </summary>
    static final int UNUSED_ALGORITHM_SID = 15;

    private static final int BLOCK_SIZE = 4;

    private static /*final*/ StringToIntBidirectionalMap G_ALGORITHM_SID_NAME_MAP = new StringToIntBidirectionalMap();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
    static/* PasswordHash()*/
    {
        G_ALGORITHM_SID_NAME_MAP.addEntry("MD2", 1);
        G_ALGORITHM_SID_NAME_MAP.addEntry("MD4", 2);
        G_ALGORITHM_SID_NAME_MAP.addEntry("MD5", 3);
        G_ALGORITHM_SID_NAME_MAP.addEntry("RIPEMD-128", 6);
        G_ALGORITHM_SID_NAME_MAP.addEntry("RIPEMD-160", 7);
        G_ALGORITHM_SID_NAME_MAP.addEntry("SHA-1", 4);
        G_ALGORITHM_SID_NAME_MAP.addEntry("SHA-256", 12);
        G_ALGORITHM_SID_NAME_MAP.addEntry("SHA-384", 13);
        G_ALGORITHM_SID_NAME_MAP.addEntry("SHA-512", 14);
        G_ALGORITHM_SID_NAME_MAP.addEntry("WHIRLPOOL", UNUSED_ALGORITHM_SID); // does not have corresponding ECMA-376 int, so map to unused now.
    }
}

