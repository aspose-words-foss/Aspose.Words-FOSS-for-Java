// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/04/2016 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This is modelled after Org.BouncyCastle.Crypte.IDigest.
/// We need it to make code auto portable to C++.
/// </summary>
public interface IDigest
{
    public void blockUpdate(byte[] input, int inOff, int length);
    public int doFinal(byte[] output, int outOff);
    public int getDigestSize();
    public void reset();
}

