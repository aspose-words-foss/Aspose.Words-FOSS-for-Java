// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/10/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.StreamUtil;
import com.aspose.Debug;
import com.aspose.ms.System.Convert;


/// <summary>
/// Reads and parses EncryptionInfo stream.
/// </summary>
class EncryptionInfo extends EncryptionInfoReader
{
    EncryptionInfo(BinaryReader encryptionInfoReader) throws Exception
    {
        // Copy rest of stream.
        MemoryStream xmlStream = new MemoryStream();
        StreamUtil.copyStream(encryptionInfoReader.getBaseStream(), xmlStream);

        NrxXmlReader reader = new NrxXmlReader(xmlStream);

        String tag = reader.getLocalName();
        while(reader.readChild(tag))
        {
            switch(gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"keyData"*/0:
                    readKeyData(reader);
                    break;
                case /*"dataIntegrity"*/1:
                    readDataIntergity(reader);
                    break;
                case /*"keyEncryptors"*/2:
                    // This is container element for keyEncryptor elements. Because we anticipate only one keyEncryptor we can skip this element.
                    break;
                case /*"keyEncryptor"*/3:
                    // This is container element for encryptedKey elements. Actual data is read below.
                    break;
                case /*"encryptedKey"*/4:
                    Debug.assertTrue(mPasswordKeyEncryptor == null);
                    mPasswordKeyEncryptor = new PasswordKeyEncryptor(reader);
                    break;
                default:
                    throw new IllegalStateException("Unknown tag name.");
            }
        }
    }

    /// <summary>
    /// Reads 'keyData' element.
    /// </summary>
    /// <param name="xmlReader"></param>
    private void readKeyData(NrxXmlReader xmlReader)
    {
        while (xmlReader.moveToNextAttribute())
        {
            if (!processTag(xmlReader))
                throw new IllegalStateException("Unknown tag name.");
        }
    }

    /// <summary>
    /// Reads 'dataIntergity' element.
    /// </summary>
    /// <param name="reader"></param>
    private void readDataIntergity(NrxXmlReader reader)
    {
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"encryptedHmacKey"*/5:
                    mEncryptedHmacKey = Convert.fromBase64String(reader.getValue());
                    break;
                case /*"encryptedHmacValue"*/6:
                    mEncryptedHmacValue = Convert.fromBase64String(reader.getValue());
                    break;
                default:
                    throw new IllegalStateException("Unknown tag name.");
            }
        }
    }

    /// <summary>
    /// Specifies an encrypted key utilized in calculating the encryptedHmacValue.
    /// </summary>
    byte[] getEncryptedHmacKey() { return mEncryptedHmacKey; }

    /// <summary>
    /// Specifies an HMAC derived from the encryptedHmacKey and the encrypted data.
    /// </summary>
    byte[] getEncryptedHmacValue() { return mEncryptedHmacValue; }

    /// <summary>
    /// Specifies the parameters used to encrypt an intermediate key, which is used to perform the final encryption of the document.
    /// </summary>
    PasswordKeyEncryptor getPasswordKeyEncryptor() { return mPasswordKeyEncryptor; }

    private byte[] mEncryptedHmacKey;
    private byte[] mEncryptedHmacValue;
    private /*final*/ PasswordKeyEncryptor mPasswordKeyEncryptor;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"keyData",
		"dataIntegrity",
		"keyEncryptors",
		"keyEncryptor",
		"encryptedKey",
		"encryptedHmacKey",
		"encryptedHmacValue"
	);

}

