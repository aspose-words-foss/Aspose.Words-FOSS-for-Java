// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/10/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.Convert;


/// <summary>
/// Abstract class used to read and hold encryption parameters.
/// </summary>
/// <remarks>AM. I made separate class because this parameter block is read twice: first for EncryptionInfo and second for PasswordKeyEncryptors.</remarks>
abstract class EncryptionInfoReader
{
    /// <summary>
    /// Parses tags which relate to encryption parameters. 
    /// </summary>
    /// <param name="xmlReader"></param>
    /// <returns>Returns true if tag is processed and consumed otherwise returns false.</returns>
    protected boolean processTag(NrxXmlReader xmlReader)
    {
        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"saltSize"*/0:
                mSaltSize = xmlReader.getValueAsInt();
                break;
            case /*"blockSize"*/1:
                mBlockSize = xmlReader.getValueAsInt();
                break;
            case /*"keyBits"*/2:
                mKeyBits = xmlReader.getValueAsInt();
                break;
            case /*"hashSize"*/3:
                mHashSize = xmlReader.getValueAsInt();
                break;
            case /*"cipherAlgorithm"*/4:
                mCipherAlgorithm = xmlReader.getValue();
                break;
            case /*"cipherChaining"*/5:
                mCipherChaining = xmlReader.getValue();
                break;
            case /*"hashAlgorithm"*/6:
                mHashAlgorithm = xmlReader.getValue();
                break;
            case /*"saltValue"*/7:
                mSaltValue = Convert.fromBase64String(xmlReader.getValue());
                break;
            case /*"spinCount"*/8:
                mSpinCount = xmlReader.getValueAsInt();
                break;
            default:
                return false;
        }

        return true;
    }

    /// <summary>
    /// Specifies the size of the salt.
    /// </summary>
    int getSaltSize() { return mSaltSize; }

    /// <summary>
    /// Specifies the block size.
    /// </summary>
    int getBlockSize() { return mBlockSize; }

    /// <summary>
    /// Specifies the number of bits.
    /// </summary>
    int getKeyBits() { return mKeyBits; }

    /// <summary>
    /// Specifies the number of bytes utilized by a hash value.
    /// </summary>
    int getHashSize() { return mHashSize; }

    /// <summary>
    /// Specifies the number of times to iterate on a hash of a password.
    /// </summary>
    int getSpinCount() { return mSpinCount; }

    /// <summary>
    /// Specifies the cipher algorithm.
    /// </summary>
    String getCipherAlgorithm() { return mCipherAlgorithm; }

    /// <summary>
    /// Specifies the chaining mode used by the CipherAlgorithm.
    /// </summary>
    String getCipherChaining() { return mCipherChaining; }

    /// <summary>
    /// Specifies a hashing algorithm.
    /// </summary>
    String getHashAlgorithm() { return mHashAlgorithm; }

    /// <summary>
    /// 
    /// </summary>
    byte[] getSaltValue() { return mSaltValue; }

    private int mSaltSize;
    private int mBlockSize;
    private int mKeyBits;
    private int mHashSize;
    private int mSpinCount;
    private String mCipherAlgorithm;
    private String mCipherChaining;
    private String mHashAlgorithm;
    private byte[] mSaltValue;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"saltSize",
		"blockSize",
		"keyBits",
		"hashSize",
		"cipherAlgorithm",
		"cipherChaining",
		"hashAlgorithm",
		"saltValue",
		"spinCount"
	);

}

