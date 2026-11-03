// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/04/2016 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This is modelled after Org.BouncyCastle.Crypto.IRandomGenerator.
/// We need it to make code auto portable to C++.
/// 
/// Note we require implementation of the methods that we really need in AW.
/// </summary>
public interface IRandomGenerator
{
    /// <summary>
    /// Fills byte array with random values.
    /// </summary>
    public void nextBytes(byte[] bytes);

    /// <summary>
    /// Adds seed material to the generator.
    /// </summary>
    public void addSeedMaterial(byte[] seed);
}

