// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//16/01/13 by Vyacheslav Durin
package com.aspose;

/**
 *  Represent a JPEG segment.
 *  JPEG metadata consists of the data contained in marker segments in a JPEG stream.
 * */
public class JpegSegment
{
    // Start of frame markers
    public final static int SOF0_MARKER = 0xffc0;
    public final static int SOF3_MARKER = 0xffc3;

    public final static int SOF5_MARKER = 0xffc5;
    public final static int SOF7_MARKER = 0xffc7;

    public final static int SOF9_MARKER = 0xffc9;
    public final static int SOFB_MARKER = 0xffcB;

    public final static int SOFD_MARKER = 0xffcD;
    public final static int SOFF_MARKER = 0xffcF;

    // Restart markers
    public final static int RST0_MARKER = 0xffd0;
    public final static int RST7_MARKER = 0xffd7;

    /**
     * APP2_MARKER Reserved for application use
     */
    public final static int APP2_MARKER = 0xffe2;

    /**
     * APP2_MARKER Reserved for application use
     */
    public final static int APP14_MARKER = 0xffee;

    /**
     * The marker code.
     */
    public final int marker;

    /**
     * The offset of the first data byte to the beginning of the stream.
     */
    public final long offset;

    /**
     * If the segment is entropy coded, then the value -1.
     */
    public final int length;

    /**
     * Default constructor.
     * */
    public JpegSegment()
    {
        this(0, 0, -1);
    }

    /**
     * Ctor.
     * */
    public JpegSegment(int marker, long offset, int length)
    {
        this.marker = marker;
        this.offset = offset;
        this.length = length;
    }

    /**
     * @return true if it is ECS0 segment.
     * */
    public boolean isEntropyCoded()
    {
        return length == -1;
    }

    /**
     * @return true if segment marked with "Start Of Frame Header" (SOF*_MARKER)
     * */
    public boolean isStartOfFrameHeader()
    {
        return SOF0_MARKER <= marker && marker <= SOF3_MARKER
                || SOF5_MARKER <= marker && marker <= SOF7_MARKER
                || SOF9_MARKER <= marker && marker <= SOFB_MARKER
                || SOFD_MARKER <= marker && marker <= SOFF_MARKER;
    }

    /**
     * @return true if this is APP2 segment
     * */
    public boolean isApp2()
    {
        return marker == APP2_MARKER;
    }

    /**
     * @return true if this is APP2 segment
     * */
    public boolean isApp14()
    {
        return marker == APP14_MARKER;
    }
}