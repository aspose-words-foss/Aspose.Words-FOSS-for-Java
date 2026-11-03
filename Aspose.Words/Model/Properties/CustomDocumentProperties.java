// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/04/2004 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;


/// <summary>
/// A collection of custom document properties.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/work-with-document-properties/">Work with Document Properties</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Each <see cref="DocumentProperty"/> object represents a custom property of a container document.</p>
/// </remarks>
/// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentProperties.Common"]/*'/>
/// <seealso cref="Document"/>
/// <seealso cref="Document.BuiltInDocumentProperties"/>
/// <seealso cref="Document.CustomDocumentProperties"/>
public class CustomDocumentProperties extends DocumentPropertyCollection
{
    CustomDocumentProperties(Document document)
    {
        mDocument = document;
    }

    CustomDocumentProperties()
    {
    }

    /// <overloads>Creates a new custom document property.</overloads>
    /// <summary>
    /// Creates a new custom document property of the <see cref="PropertyType.String"/> data type.
    /// </summary>
    /// <param name="name">The name of the property.</param>
    /// <param name="value">The value of the property.</param>
    /// <returns>The newly created property object.</returns>
    public DocumentProperty add(String name,  String value)
    {
        return super.add(name, value);
    }

    /// <summary>
    /// Creates a new custom document property of the <see cref="PropertyType.Number"/> data type.
    /// </summary>
    /// <param name="name">The name of the property.</param>
    /// <param name="value">The value of the property.</param>
    /// <returns>The newly created property object.</returns>
    public DocumentProperty add(String name, int value)
    {
        return super.add(name, value);
    }

    /// <summary>
    /// Creates a new custom document property of the <see cref="PropertyType.DateTime"/> data type.
    /// </summary>
    /// <param name="name">The name of the property.</param>
    /// <param name="value">The value of the property.</param>
    /// <returns>The newly created property object.</returns>
    //JAVA: method is internalized
	DocumentProperty addInternal(String name, DateTime value)
    {
        return super.add(name, value);
    }

	//JAVA-added public wrapper for internalized method
	public DocumentProperty add(String name, java.util.Date value)
	{
		return addInternal(name, DateTime.fromJava(value));
	}

    /// <summary>
    /// Creates a new custom document property of the <see cref="PropertyType.Boolean"/> data type.
    /// </summary>
    /// <param name="name">The name of the property.</param>
    /// <param name="value">The value of the property.</param>
    /// <returns>The newly created property object.</returns>
    public DocumentProperty add(String name, boolean value)
    {
        return super.add(name, value);
    }

    /// <summary>
    /// Creates a new custom document property of the <see cref="PropertyType.Double"/> data type.
    /// </summary>
    /// <param name="name">The name of the property.</param>
    /// <param name="value">The value of the property.</param>
    /// <returns>The newly created property object.</returns>
    public DocumentProperty add(String name, double value)
    {
        return super.add(name, value);
    }

    /// <summary>
    /// Creates a new linked to content custom document property.
    /// </summary>
    /// <param name="name">The name of the property.</param>
    /// <param name="linkSource">The source of the property.</param>
    /// <returns>The newly created property object or <c>null</c> when the <paramref name="linkSource"/> is invalid.</returns>
    public DocumentProperty addLinkToContent(String name, String linkSource) throws Exception
    {
        String value = getLinkedContent(mDocument, linkSource);

        // MSW allows to create a linked content only for valid bookmarks.
        if (value == null)
            return null;

        DocumentProperty linkedProperty = super.add(name, value);
        linkedProperty.setLinkTarget(linkSource);

        return linkedProperty;
    }

    /*override*/ DocumentPropertyCollection create()
    {
        return new CustomDocumentProperties(mDocument);
    }

    void setDocument(Document document)
    {
        mDocument = document;
    }

    /// <summary>
    /// Gets a linked content (a bookmark text) from the document.
    /// </summary>
    private static String getLinkedContent(Document document, String linkTarget) throws Exception
    {
        Bookmark bookmark = document.getRange().getBookmarks().get(linkTarget);

        return (bookmark != null) ? bookmark.getText(true) : null;
    }

    private Document mDocument;
}

