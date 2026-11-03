// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/10/2011 by Alexey Morozov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.Convert;


/// <summary>
/// Implements reading of 'keyEncryptor' element.
/// </summary>
class PasswordKeyEncryptor extends EncryptionInfoReader
{
    /// <summary>
    /// Reads PasswordKeyEncryptor data.
    /// </summary>
    /// <param name="reader"></param>
    PasswordKeyEncryptor(NrxXmlReader reader)
    {
        while (reader.moveToNextAttribute())
        {
            if (!processTag(reader))
            {
                switch (gStringSwitchMap.of(reader.getLocalName()))
                {
                    case /*"encryptedVerifierHashInput"*/0:
                        mEncryptedVerifierHashInput = Convert.fromBase64String(reader.getValue());
                        break;
                    case /*"encryptedVerifierHashValue"*/1:
                        mEncryptedVerifierHashValue = Convert.fromBase64String(reader.getValue());
                        break;
                    case /*"encryptedKeyValue"*/2:
                        mEncryptedKeyValue = Convert.fromBase64String(reader.getValue());
                        break;
                    default:
                        throw new IllegalStateException("Unknown tag name: " + reader.getLocalName());
                }
            }
        }
    }

    /// <summary>
    /// Specifies encrypted verifier hash input for a PasswordKeyEncryptor used in password verification.
    /// </summary>
    byte[] getEncryptedVerifierHashInput() { return mEncryptedVerifierHashInput; }

    /// <summary>
    /// Specifies encrypted verifier hash value for a PasswordKeyEncryptor used in password verification.
    /// </summary>
    byte[] getEncryptedVerifierHashValue() { return mEncryptedVerifierHashValue; }

    /// <summary>
    /// Specifies encrypted form of the intermediate key.
    /// </summary>
    byte[] getEncryptedKeyValue() { return mEncryptedKeyValue; }

    private /*final*/ byte[] mEncryptedVerifierHashInput;
    private /*final*/ byte[] mEncryptedVerifierHashValue;
    private /*final*/ byte[] mEncryptedKeyValue;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"encryptedVerifierHashInput",
		"encryptedVerifierHashValue",
		"encryptedKeyValue"
	);

}


