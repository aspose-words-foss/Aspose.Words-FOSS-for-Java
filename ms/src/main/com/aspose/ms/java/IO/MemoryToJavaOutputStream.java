// Copyright (c) 2001-2018 Aspose Pty Ltd. All Rights Reserved.

package com.aspose.ms.java.IO;

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.java.logging.Log;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Helper class that wraps java.io.OutputStream by ms MemoryStream.
 *
 * flush() does the trick by copying MemoryStream to OutputStream.
 */
public class MemoryToJavaOutputStream extends MemoryStream
{
    public MemoryToJavaOutputStream(OutputStream outputStream)
    {
        if (outputStream == null)
            throw new NullPointerException("OutputStream cannot be null");

        this.mJavaOutputStream = outputStream;
    }

    @Override
    public void flush() throws IOException
    {
        super.flush();
        try
        {
            long currentPosition = getPosition();
            setPosition(mLastSyncedPosition);
            JavaOnlyStreamUtil.copyStream(this, mJavaOutputStream);
            mJavaOutputStream.flush();
            mLastSyncedPosition = currentPosition;
        }
        catch (Exception e)
        {
            Log.error(e);
        }
    }

    @Override
    public void close()
    {
        try
        {
            flush();
        }
        catch (Exception e)
        {
            Log.error(e);
        }
        super.close();
    }

    private final OutputStream mJavaOutputStream;
    private long mLastSyncedPosition;
}
