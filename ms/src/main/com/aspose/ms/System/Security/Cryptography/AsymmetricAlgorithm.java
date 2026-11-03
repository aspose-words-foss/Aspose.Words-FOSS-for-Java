// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//06/12/17 by Konstantin Sidorenko

package com.aspose.ms.System.Security.Cryptography;

import com.aspose.ms.System.IDisposable;

/**
 * Java simplified implementation of
 * https://docs.microsoft.com/en-us/dotnet/api/system.security.cryptography.asymmetricalgorithm
 *
 * Represents the abstract base class from which all implementations of asymmetric algorithms must inherit.
 * Asymmetric cryptographic algorithms also known as public-key algorithms.
 */
public abstract class AsymmetricAlgorithm implements IDisposable
{
    protected AsymmetricAlgorithm() {}

    public  void dispose()
    {
    }

    public void clear()
    {
        this.dispose();
    }

    protected abstract void dispose(boolean disposing);

    public int getKeySize()
    {
        return KeySizeValue;
    }

    public void setKeySize(int value)
    {
        int   i;
        int   j;

        for (i = 0; i < LegalKeySizesValue.length; i++)
        {
            if (LegalKeySizesValue[i].getSkipSize() == 0)
            {
                if (LegalKeySizesValue[i].getMinSize() == value)
                {
                    // assume MinSize = MaxSize
                    KeySizeValue = value;
                    return;
                }
            }
            else
            {
                for (j = LegalKeySizesValue[i].getMinSize();
                     j <= LegalKeySizesValue[i].getMaxSize();
                     j += LegalKeySizesValue[i].getSkipSize())
                {
                    if (j == value)
                    {
                        KeySizeValue = value;
                        return;
                    }
                }
            }
        }

        throw new RuntimeException("Cryptography Exception: InvalidKeySize.");
    }


    public KeySizes[] getLegalKeySizes()
    {
        return LegalKeySizesValue.clone();
    }

    public abstract String getSignatureAlgorithm();

    public abstract String getKeyExchangeAlgorithm();

    static public AsymmetricAlgorithm create()
    {
        // Use the crypto config system to return an instance of
        // the default AsymmetricAlgorithm on this machine
        return create("System.Security.Cryptography.AsymmetricAlgorithm");
    }

    static public AsymmetricAlgorithm create(String algName)
    {
        //return (AsymmetricAlgorithm) CryptoConfig.CreateFromName(algName);
        throw new RuntimeException("Not Implemented Exception.");
    }

    public abstract void FromXmlString(String xmlString);
    public abstract String ToXmlString(boolean includePrivateParameters);

    protected int KeySizeValue;
    protected KeySizes[] LegalKeySizesValue;
}
