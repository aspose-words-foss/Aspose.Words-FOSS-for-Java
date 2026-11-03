package com.aspose.ms.System.IO;

/**
 * Created by IntelliJ IDEA.
 * User: romeok
 * Date: Nov 12, 2010
 * Time: 10:35:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileInfo
{
    public FileInfo(String fileName)
    {
        mNativeFile = new java.io.File(fileName);
    }

    public long getLength()
    {
        return mNativeFile.length();
    }

    public boolean exists()
    {
        return mNativeFile.exists();
    }

    public String getFullName()
    {
        return mNativeFile.getAbsolutePath();
    }

    private java.io.File mNativeFile;
}
