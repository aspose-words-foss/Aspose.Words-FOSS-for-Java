// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/02/2020 by Alexander Dudin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// DSA algorithm parameters.
/// </summary>
public /*struct*/ final class DsaParameters
{
	public DsaParameters(){}
	
    /// <summary>
    /// P parameter of the DSA algorithm.
    /// </summary>
    public byte[] getP() { return mP; }; public void setP(byte[] value) { mP = value; };

    private byte[] mP;

    /// <summary>
    /// Q parameter of the DSA algorithm.
    /// </summary>
    public byte[] getQ() { return mQ; }; public void setQ(byte[] value) { mQ = value; };

    private byte[] mQ;

    /// <summary>
    /// G parameter of the DSA algorithm.
    /// </summary>
    public byte[] getG() { return mG; }; public void setG(byte[] value) { mG = value; };

    private byte[] mG;

    /// <summary>
    /// Y parameter of the DSA algorithm (public key).
    /// </summary>
    public byte[] getY() { return mY; }; public void setY(byte[] value) { mY = value; };

    private byte[] mY;

    /// <summary>
    /// X parameter of the DSA algorithm (private key).
    /// </summary>
    public byte[] getX() { return mX; }; public void setX(byte[] value) { mX = value; };

    private byte[] mX;
}

