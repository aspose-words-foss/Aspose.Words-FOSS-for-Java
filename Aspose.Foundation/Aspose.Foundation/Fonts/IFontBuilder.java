// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/02/2013 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


interface IFontBuilder
{
    public int getSfntVersion(); public void setSfntVersion(int value);

    public void addTable(String tag, byte[] tableData);

    public byte[] writeFileToByteArray() throws Exception;

    public void writeFileToStream(Stream stream) throws Exception;
}

