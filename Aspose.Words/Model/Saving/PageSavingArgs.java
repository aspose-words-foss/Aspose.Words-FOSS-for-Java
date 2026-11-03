// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/02/2016 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Provides data for the <see cref="IPageSavingCallback.PageSaving"/> event.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/programming-with-documents/">Programming with Documents</a> documentation article.</para>
/// </summary>
public class PageSavingArgs
{
    /// <summary>
    /// Set current page index.
    /// </summary>
    void setPageIndex(int pageIndex)
    {
        mPageIndex = pageIndex;
    }

    /// <summary>
    /// Exists to make calling code autoportable.
    /// </summary>
    UserStreamWrapper createUserStreamWrapper()
    {
        return new UserStreamWrapper(mPageStream, mKeepPageStreamOpen);
    }

    /// <summary>
    /// Allows to specify the stream where the document page will be saved to.
    /// </summary>
    /// <remarks>
    /// <para>This property allows you to save document pages to streams instead of files.</para>
    /// 
    /// <para>The default value is <c>null</c>. When this property is <c>null</c>, the document page 
    /// will be saved to a file specified in the <see cref="PageFileName"/> property.</para>
    /// <para>If both <see cref="PageStream"/> and <see cref="PageFileName"/> are set, then PageStream will be used.</para>
    /// <seealso cref="PageFileName"/>
    /// <seealso cref="KeepPageStreamOpen"/>
    /// </remarks>
    /// <javaName>PageStream(java.io.OutputStream)</javaName>
    //JAVA: property accessor is internalized
	
    Stream getPageStreamInternal() { return mPageStream; }
	//JAVA-added public wrapper for internalized property accessor
	public java.io.OutputStream getPageStream() { return Stream.toJavaOutputStream(getPageStreamInternal()); }
    //JAVA: property accessor is internalized
	
    void setPageStreamInternal(Stream value) { mPageStream = value; }
	//JAVA-added public wrapper for internalized property accessor
	public void setPageStream(java.io.OutputStream value) { setPageStreamInternal(Stream.fromJavaOutputStream(value)); }

    /// <summary>
    /// Specifies whether Aspose.Words should keep the stream open or close it after saving a document page.
    /// </summary>
    /// <remarks>
    /// <para>Default is <c>false</c> and Aspose.Words will close the stream you provided
    /// in the <see cref="PageStream"/> property after writing a document page into it.
    /// Specify <c>true</c> to keep the stream open.</para>
    /// 
    /// <seealso cref="PageStream"/>
    /// </remarks>
    public boolean getKeepPageStreamOpen() { return mKeepPageStreamOpen; }
    public void setKeepPageStreamOpen(boolean value) { mKeepPageStreamOpen = value; }

    /// <summary>
    /// Gets or sets the file name where the document page will be saved to.
    /// </summary>
    /// <remarks>
    /// If not specified then page file name and path will be generated automatically using original file name.
    /// </remarks>
    public String getPageFileName() { return mPageFileName; }
    public void setPageFileName(String value) { mPageFileName = value; }

    /// <summary>
    /// Current page index.
    /// </summary>
    public int getPageIndex() { return mPageIndex; }

    /// <summary>
    /// Exists to make calling code autoportable.
    /// </summary>
    boolean hasUserStream() { return mPageStream != null; }

    private Stream mPageStream;
    private String mPageFileName;
    private int mPageIndex;
    private boolean mKeepPageStreamOpen;
}

