// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// Code sample from PNG Specification.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// A utility class to compute CRC32.
/// </summary>
public /*static*/ class Crc32
{
	/* Simulation of static class by using private constructor */
	private Crc32()
	{}

    /// <summary>
    /// Table of CRCs of all 8-bit messages.
    /// </summary>
    private static /*final*/ /*uint*/int[] CRC_TABLE =
        {
            0x00000000, 0x77073096, (int) 0xee0e612cL, (int) 0x990951baL, 0x076dc419, 0x706af48f,
            (int) 0xe963a535L, (int) 0x9e6495a3L, 0x0edb8832, 0x79dcb8a4, (int) 0xe0d5e91eL, (int) 0x97d2d988L,
            0x09b64c2b, 0x7eb17cbd, (int) 0xe7b82d07L, (int) 0x90bf1d91L, 0x1db71064, 0x6ab020f2,
            (int) 0xf3b97148L, (int) 0x84be41deL, 0x1adad47d, 0x6ddde4eb, (int) 0xf4d4b551L, (int) 0x83d385c7L,
            0x136c9856, 0x646ba8c0, (int) 0xfd62f97aL, (int) 0x8a65c9ecL, 0x14015c4f, 0x63066cd9,
            (int) 0xfa0f3d63L, (int) 0x8d080df5L, 0x3b6e20c8, 0x4c69105e, (int) 0xd56041e4L, (int) 0xa2677172L,
            0x3c03e4d1, 0x4b04d447, (int) 0xd20d85fdL, (int) 0xa50ab56bL, 0x35b5a8fa, 0x42b2986c,
            (int) 0xdbbbc9d6L, (int) 0xacbcf940L, 0x32d86ce3, 0x45df5c75, (int) 0xdcd60dcfL, (int) 0xabd13d59L,
            0x26d930ac, 0x51de003a, (int) 0xc8d75180L, (int) 0xbfd06116L, 0x21b4f4b5, 0x56b3c423,
            (int) 0xcfba9599L, (int) 0xb8bda50fL, 0x2802b89e, 0x5f058808, (int) 0xc60cd9b2L, (int) 0xb10be924L,
            0x2f6f7c87, 0x58684c11, (int) 0xc1611dabL, (int) 0xb6662d3dL, 0x76dc4190, 0x01db7106,
            (int) 0x98d220bcL, (int) 0xefd5102aL, 0x71b18589, 0x06b6b51f, (int) 0x9fbfe4a5L, (int) 0xe8b8d433L,
            0x7807c9a2, 0x0f00f934, (int) 0x9609a88eL, (int) 0xe10e9818L, 0x7f6a0dbb, 0x086d3d2d,
            (int) 0x91646c97L, (int) 0xe6635c01L, 0x6b6b51f4, 0x1c6c6162, (int) 0x856530d8L, (int) 0xf262004eL,
            0x6c0695ed, 0x1b01a57b, (int) 0x8208f4c1L, (int) 0xf50fc457L, 0x65b0d9c6, 0x12b7e950,
            (int) 0x8bbeb8eaL, (int) 0xfcb9887cL, 0x62dd1ddf, 0x15da2d49, (int) 0x8cd37cf3L, (int) 0xfbd44c65L,
            0x4db26158, 0x3ab551ce, (int) 0xa3bc0074L, (int) 0xd4bb30e2L, 0x4adfa541, 0x3dd895d7,
            (int) 0xa4d1c46dL, (int) 0xd3d6f4fbL, 0x4369e96a, 0x346ed9fc, (int) 0xad678846L, (int) 0xda60b8d0L,
            0x44042d73, 0x33031de5, (int) 0xaa0a4c5fL, (int) 0xdd0d7cc9L, 0x5005713c, 0x270241aa,
            (int) 0xbe0b1010L, (int) 0xc90c2086L, 0x5768b525, 0x206f85b3, (int) 0xb966d409L, (int) 0xce61e49fL,
            0x5edef90e, 0x29d9c998, (int) 0xb0d09822L, (int) 0xc7d7a8b4L, 0x59b33d17, 0x2eb40d81,
            (int) 0xb7bd5c3bL, (int) 0xc0ba6cadL, (int) 0xedb88320L, (int) 0x9abfb3b6L, 0x03b6e20c, 0x74b1d29a,
            (int) 0xead54739L, (int) 0x9dd277afL, 0x04db2615, 0x73dc1683, (int) 0xe3630b12L, (int) 0x94643b84L,
            0x0d6d6a3e, 0x7a6a5aa8, (int) 0xe40ecf0bL, (int) 0x9309ff9dL, 0x0a00ae27, 0x7d079eb1,
            (int) 0xf00f9344L, (int) 0x8708a3d2L, 0x1e01f268, 0x6906c2fe, (int) 0xf762575dL, (int) 0x806567cbL,
            0x196c3671, 0x6e6b06e7, (int) 0xfed41b76L, (int) 0x89d32be0L, 0x10da7a5a, 0x67dd4acc,
            (int) 0xf9b9df6fL, (int) 0x8ebeeff9L, 0x17b7be43, 0x60b08ed5, (int) 0xd6d6a3e8L, (int) 0xa1d1937eL,
            0x38d8c2c4, 0x4fdff252, (int) 0xd1bb67f1L, (int) 0xa6bc5767L, 0x3fb506dd, 0x48b2364b,
            (int) 0xd80d2bdaL, (int) 0xaf0a1b4cL, 0x36034af6, 0x41047a60, (int) 0xdf60efc3L, (int) 0xa867df55L,
            0x316e8eef, 0x4669be79, (int) 0xcb61b38cL, (int) 0xbc66831aL, 0x256fd2a0, 0x5268e236,
            (int) 0xcc0c7795L, (int) 0xbb0b4703L, 0x220216b9, 0x5505262f, (int) 0xc5ba3bbeL, (int) 0xb2bd0b28L,
            0x2bb45a92, 0x5cb36a04, (int) 0xc2d7ffa7L, (int) 0xb5d0cf31L, 0x2cd99e8b, 0x5bdeae1d,
            (int) 0x9b64c2b0L, (int) 0xec63f226L, 0x756aa39c, 0x026d930a, (int) 0x9c0906a9L, (int) 0xeb0e363fL,
            0x72076785, 0x05005713, (int) 0x95bf4a82L, (int) 0xe2b87a14L, 0x7bb12bae, 0x0cb61b38,
            (int) 0x92d28e9bL, (int) 0xe5d5be0dL, 0x7cdcefb7, 0x0bdbdf21, (int) 0x86d3d2d4L, (int) 0xf1d4e242L,
            0x68ddb3f8, 0x1fda836e, (int) 0x81be16cdL, (int) 0xf6b9265bL, 0x6fb077e1, 0x18b74777,
            (int) 0x88085ae6L, (int) 0xff0f6a70L, 0x66063bca, 0x11010b5c, (int) 0x8f659effL, (int) 0xf862ae69L,
            0x616bffd3, 0x166ccf45, (int) 0xa00ae278L, (int) 0xd70dd2eeL, 0x4e048354, 0x3903b3c2,
            (int) 0xa7672661L, (int) 0xd06016f7L, 0x4969474d, 0x3e6e77db, (int) 0xaed16a4aL, (int) 0xd9d65adcL,
            0x40df0b66, 0x37d83bf0, (int) 0xa9bcae53L, (int) 0xdebb9ec5L, 0x47b2cf7f, 0x30b5ffe9,
            (int) 0xbdbdf21cL, (int) 0xcabac28aL, 0x53b39330, 0x24b4a3a6, (int) 0xbad03605L, (int) 0xcdd70693L,
            0x54de5729, 0x23d967bf, (int) 0xb3667a2eL, (int) 0xc4614ab8L, 0x5d681b02, 0x2a6f2b94,
            (int) 0xb40bbe37L, (int) 0xc30c8ea1L, 0x5a05df1b, 0x2d02ef8d
        };

    /*
        // Make the table for a fast CRC.
        private static void MakeCRCTable()
        {
            uint c;
            for (int n = 0; n < 256; n++)
            {
                c = (uint)n;
                for (int k = 0; k < 8; k++)
                    c = (c & 1) != 0 ? 0xedb88320U ^ (c >> 1) : c >> 1;
                crcTable[n] = c;
            }
        }
        */

    /// <summary>
    /// Update a running CRC with the bytes buf[index..count-1]-the CRC should be initialized to all 1's,
    /// and the transmitted value is the 1's complement of the final running CRC.
    /// </summary>
    private static /*uint*/int updateCRC(/*uint*/int crc, byte[] buf, int index, int count)
    {
        /*uint*/int c = crc;

        for (int n = index; n < index + count; n++)
        {
            c = (/*uint*/int)((CRC_TABLE[(/*uint*/int)(((c & 0xFFFFFFFFL) ^ (buf[n] & 0xFF)) & 0xff)] & 0xFFFFFFFFL) ^ (c >>> 8));
        }
        return c;
    }

    /// <summary>
    /// Return the CRC of the bytes array.
    /// </summary>
    public static /*uint*/int computeCRC(byte[] buf)
    {
        return computeCRC(buf, 0, buf.length);
    }

    /// <summary>
    /// Return the CRC of the bytes array for the specified range.
    /// </summary>
    public static /*uint*/int computeCRC(byte[] buf, int index, int count)
    {
        return (/*uint*/int)((updateCRC((int) 0xffffffffL, buf, index, count) & 0xFFFFFFFFL) ^ (int) 0xffffffffL);
    }
}

