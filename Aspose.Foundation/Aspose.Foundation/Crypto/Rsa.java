// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.math.BigInteger;

/// <summary>
/// Performs RSA encryption.
/// </summary>
public class Rsa
{
    public Rsa(byte[] modulusBytes, byte[] exponentBytes)
    {
        mModulus = new BigInteger(1, modulusBytes);
        mExponent = new BigInteger(1, exponentBytes);
    }

    public BigInteger getModulus() { return mModulus; }

    public BigInteger getExponent() { return mExponent; }

    BigInteger mModulus;
    BigInteger mExponent;
}


