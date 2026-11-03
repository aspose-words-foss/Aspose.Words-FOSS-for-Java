// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/12/2018 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;

public /*static*/ class BookmarkTestUtil
{
	/* Simulation of static class by using private constructor */
	private BookmarkTestUtil()
	{}

    /// <summary>
    /// Creates document with a specified number of bookmarks.
    /// </summary>
    public static Document createDocumentWithBookmarks(int bookmarksCount) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());

        for (int i = 0; i < bookmarksCount; i++)
        {
            String bookmarkName = msString.format("bookmark{0}", i);
            insertBookmark(builder, bookmarkName);
        }

        return builder.getDocument();
    }

    /// <summary>
    /// Appends new bookmark to the document.
    /// </summary>
    public static void appendBookmark(Document doc, String name)
    {
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToDocumentEnd();

        insertBookmark(builder, name);
    }

    /// <summary>
    /// Inserts new bookmark into the document.
    /// </summary>
    private static void insertBookmark(DocumentBuilder builder, String name)
    {
        builder.startBookmark(name);
        builder.write(name);
        builder.endBookmark(name);
        builder.writeln();
    }
}

