// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/05/2011 by Alexey Titov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestBitWriter
{
    @Test
    public void writeOneInCurrentBit_1stAndLastBitInByte()
    {
        // Arrange
        byte[] bytes = new byte[1];
        BitWriter writer = new BitWriter(bytes);
        // Act
        writer.writeOneInCurrentBit();
        for (int i = 0; i < 7; i++)
        {
            writer.moveToNextBit();
        }
        writer.writeOneInCurrentBit();
        writer.flush();
        // Assert
        msAssert.areEqual(129, bytes[0]);
    }

    @Test
    public void moveToNextBit_OverByteBoundary()
    {
        // Arrange
        byte[] bytes = new byte[2];
        BitWriter writer = new BitWriter(bytes);
        // Act
        writer.writeOneInCurrentBit();
        for (int i = 0; i < 9; i++)
        {
            writer.moveToNextBit();
        }
        writer.writeOneInCurrentBit();
        writer.flush();
        // Assert
        msAssert.areEqual(128, bytes[0]);
        msAssert.areEqual(64, bytes[1]);
    }

    @Test
    public void moveToNextByte()
    {
        // Arrange
        byte[] bytes = new byte[2];
        BitWriter writer = new BitWriter(bytes);
        // Act
        // Write 1st byte
        writer.writeOneInCurrentBit();
        for (int i = 0; i < 3; i++)
        {
            writer.moveToNextBit();
        }
        // Write 2nd byte
        writer.setByteIndex(1);
        writer.moveToNextBit();
        writer.writeOneInCurrentBit();
        writer.flush();
        // Assert
        msAssert.areEqual(128, bytes[0]);
        msAssert.areEqual(64, bytes[1]);
    }
}
