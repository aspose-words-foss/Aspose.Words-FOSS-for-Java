package com.aspose.ms.java.IO;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/// <summary>
/// java.io.FileInputStream wrapped into ms Stream.
/// Most Java's FileInputStreams are two-way streams so we can wrap them into ms Stream.
/// </summary>
public class JavaFileInputStream extends JavaInputStream
{
    public JavaFileInputStream(FileInputStream inputStream) throws IOException
    {
        super(inputStream);
        mFileChannel = inputStream.getChannel();
    }

    @Override
    public long getPosition() throws IOException
    {
        return mFileChannel.position();
    }

    @Override
    public void setPosition(long position) throws IOException
    {
        mFileChannel.position(position);
    }

    private FileChannel mFileChannel;
}
