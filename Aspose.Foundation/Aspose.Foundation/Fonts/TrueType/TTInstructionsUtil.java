// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Utility functions for handling TrueType instructions.
/// </summary>
/*static*/ class TTInstructionsUtil
{
	/* Simulation of static class by using private constructor */
	private TTInstructionsUtil()
	{}

    /// <summary>
    /// Reads push data from binary reader.
    /// </summary>
    /// <returns>List of push values.</returns>
    /// <remarks>
    /// TrueType instructions in most cases starts with several push instructions.
    /// "Push data" refers to data which is pushed to the stack by these instructions.
    /// Same values may be pushed using different set of push instructions.
    /// </remarks>
    static IntList readPushData(BigEndianBinaryReader reader) throws Exception
    {
        IntList pushValues = IntList.createAllocated();
        while (reader.getBaseStream().getPosition() < reader.getBaseStream().getLength())
        {
            // Read instruction OpCode.
            short opCode = (short)(reader.readByte() & 0xFF);

            // Check OpCode. 
            // If OpCode refers to push instruction - collect value format and count.
            // If not a push instruction - push block is ended.
            boolean isPushWordCommand;
            int pushValuesCount;
            if (opCode == OPCODE_N_PUSH_B)
            {
                isPushWordCommand = false;
                pushValuesCount = (reader.readByte() & 0xFF);
            }
            else if (opCode == OPCODE_N_PUSH_W)
            {
                isPushWordCommand = true;
                pushValuesCount = (reader.readByte() & 0xFF);
            }
            else if (opCode >= OPCODE_PUSH_B_0 && opCode <= OPCODE_PUSH_B_7)
            {
                isPushWordCommand = false;
                pushValuesCount = opCode - OPCODE_PUSH_B_0 + 1;
            }
            else if (opCode >= OPCODE_PUSH_W_0 && opCode <= OPCODE_PUSH_W_7)
            {
                isPushWordCommand = true;
                pushValuesCount = opCode - OPCODE_PUSH_W_0 + 1;
            }
            else
            {
                // Not a push instruction. Push data ends here.
                reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() - 1)/*Property--*/;
                break;
            }

            // Read push values.
            for (int i = 0; i < pushValuesCount; i++)
            {
                pushValues.add(isPushWordCommand ? reader.readInt16() : (reader.readByte() & 0xFF));
            }
        }

        return pushValues;
    }

    /// <summary>
    /// Writes push data to binary writer.
    /// </summary>
    public static void writePushData(BigEndianBinaryWriter writer, short[] pushValues) throws Exception
    {
        // Note: He is simple writing of all push values as words using NPushW command.
        // Output size can be decreased if we utilize other push commands.
        int position = 0;
        while (position < pushValues.length)
        {
            int valuesToWrite = Math.min(255, pushValues.length - position);

            writeNPushWCommand(writer, valuesToWrite);
            for (int i = 0; i < valuesToWrite; i++)
            {
                writer.writeUInt16(pushValues[position++]);
            }
        }
    }

    private static void writeNPushWCommand(BigEndianBinaryWriter writer, int n) throws Exception
    {
        writer.writeByte((byte)OPCODE_N_PUSH_W);
        writer.writeByte((byte)n);
    }

    private static final short OPCODE_N_PUSH_B = 0x40;
    private static final short OPCODE_N_PUSH_W = 0x41;
    private static final short OPCODE_PUSH_B_0 = 0xB0;
    private static final short OPCODE_PUSH_B_7 = 0xB7;
    private static final short OPCODE_PUSH_W_0 = 0xB8;
    private static final short OPCODE_PUSH_W_7 = 0xBF;
}

