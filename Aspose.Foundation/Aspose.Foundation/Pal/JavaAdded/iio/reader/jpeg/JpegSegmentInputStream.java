// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//16/01/13 by Vyacheslav Durin
package com.aspose;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

/**
 *  Singe Java's can't read JPEG Metadata properly. This class was introduced.
 *  It extracts segments which contain information about colorspace, ICC_Profile etc.
 *  see http://docs.oracle.com/javase/6/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html
 *  @extends FilterInputStream
 * */
public class JpegSegmentInputStream extends FilterInputStream
{
    /** Start of image */
    public final static int SOI_MARKER = 0xffd8;
    /** End of image */
    public final static int EOI_MARKER = 0xffd9;
    /** Start of scan */
    public final static int SOS_MARKER = 0xffda;
    /** Temporary private use in arithmetic coding */
    public final static int TEM_MARKER = 0xff01;
    /** Reserved for JPEG extensions */
    public final static int JPG0_MARKER = 0xfff0;
    public final static int JPGD_MARKER = 0xfffD;
    /** Illegal marker */
    public final static int JPILLEGAL_MARKER = 0xffff;

    private JpegSegment segment;
    private boolean foundMarker;
    private int offset;
    private final HashSet<Integer> mapSegMarkers = new HashSet<Integer>();
    private boolean isStuffed;
    private int curMarker;

    public JpegSegmentInputStream(InputStream in)
    {
        super(in);
        segment = new JpegSegment();
        offset = 0;
        initMarkerMap();
    }

    /**
     * Return the next segment from the input stream.
     *
     * @return found segment.
     * @throws java.io.IOException
     */
    public JpegSegment getNextSegment() throws IOException
    {
        if (!segment.isEntropyCoded())
        {
            foundMarker = false;
            do
            {
                long skipped = in.skip(segment.length - offset + segment.offset);
                if (skipped == -1 || (skipped == 0 && offset < segment.length + segment.offset))
                {
                    segment = new JpegSegment(0, offset, -1);
                    return null;
                }
                offset += skipped;
            }
            while (offset < segment.length + segment.offset);

            if (segment.marker == SOS_MARKER)
            {
                segment = new JpegSegment(0, offset, -1);
                return segment;
            }
        }

        // Scan the input stream for the next marker.
        while (!foundMarker)
        {
            while (true)
            {
                int b;

                if (isStuffed)
                {
                    b = 255;
                    isStuffed = false;
                }
                else
                {
                    b = readInt();
                }

                if (b == -1)
                    return null;

                if (b == 255)
                {
                    foundMarker = true;
                    break;
                }
            }

            int b = readInt();
            if (b == -1)
                return null;

            if (b == 0)
            {
                foundMarker = false;
            }
            else if (b == 255)
            {
                isStuffed = true;
                foundMarker = false;
            }
            else
            {
                curMarker = 0xff00 | b;
            }

        }
        foundMarker = false;

        if (mapSegMarkers.contains(curMarker))
        {
            segment = new JpegSegment(0xff00 | curMarker, offset, -1);
        }
        else
        {
            int length = (readInt() << 8) | readInt();
            if (length < 2)
                throw new IOException("JpegSegmentInputStream found illegal segment length " + length + " after marker " + Integer.toHexString(curMarker) + " at offset " + offset + ".");

            segment = new JpegSegment(0xff00 | curMarker, offset, length - 2);
        }
        return segment;
    }

    private void initMarkerMap()
    {
        for (int i = JpegSegment.RST0_MARKER; i <= EOI_MARKER; i++)
            mapSegMarkers.add(i); //RST0-RST7,SOI,EOI

        mapSegMarkers.add(TEM_MARKER); // TEM_MARKER

        for (int i = JPG0_MARKER; i <= JPGD_MARKER; i++)
            mapSegMarkers.add(i); //JPG0-JPGD

        mapSegMarkers.add(JPILLEGAL_MARKER);
    }

    /**
     * Reads the next byte of data from the input stream.
     * @return read byte.
     * */
    private int readInt() throws IOException
    {
        int b = in.read();
        if (b != -1)
            offset++;

        return b;
    }

    @Override
    public int read() throws IOException
    {
        if (foundMarker)
            return -1;


        int b;
        if (isStuffed)
        {
            isStuffed = false;
            b = 255;
        }
        else
        {
            b = readInt();
        }

        // if it is ECS0 segment
        if (segment.isEntropyCoded())
        {
            if (b == 255)
            {
                b = readInt();
                if (b == 0)
                {
                    // found a stuffed 0xff byte
                    return 255;
                }
                else if (b == 255)
                {
                    // found an invalid sequence of two 0xff bytes
                    isStuffed = true;
                    return 255;
                }
                foundMarker = true;
                curMarker = 0xff00 | b;
                return -1;
            }
        }
        return b;
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException
    {
        if (foundMarker)
            return -1;


        int count = 0;
        if (segment.isEntropyCoded())
        {
            for (; count < len; count++)
            {
                int data = read();
                if (data == -1)
                {
                    if (count == 0)
                        return -1;
                }

                b[off + count] = (byte) data;
            }
        }
        else
        {
            long available = segment.length - offset + segment.offset;
            if (available <= 0)
                return -1;

            if (available < len)
                len = (int) available;

            count = in.read(b, off, len);
            if (count != -1)
                offset += count;

        }
        return count;
    }

    @Override
    public long skip(long n) throws IOException
    {
        if (foundMarker)
            return -1;

        long count = 0;
        if (segment.isEntropyCoded())
        {
            for (; count < n; count++)
            {
                int data = read();
                if (data == -1)
                    break;
            }
        }
        else
        {
            long available = segment.length - offset + segment.offset;
            if (available < n)
                n = (int) available;

            count = in.skip(n);
            if (count != -1)
                offset += count;
        }
        return count;
    }

    @Override
    public void close() throws IOException
    {
        super.close();
    }
}
