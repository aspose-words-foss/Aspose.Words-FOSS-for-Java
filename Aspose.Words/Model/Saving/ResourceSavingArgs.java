// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/03/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Path;


/// <summary>
/// Provides data for the <see cref="IResourceSavingCallback.ResourceSaving"/> event.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/save-a-document/">Save a Document</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>By default, when Aspose.Words saves a document to fixed page HTML, SVG or Markdown, it saves each resource into
/// a separate file. Aspose.Words uses the document file name and a unique number to generate unique file name
/// for each resource found in the document.</para>
///
/// <para><see cref="ResourceSavingArgs"/> allows to redefine how resource file names are generated or to
/// completely circumvent saving of resources into files by providing your own stream objects.</para>
///
/// <para>To apply your own logic for generating resource file names use the
/// <see cref="ResourceFileName"/> property.</para>
///
/// <para>To save resources into streams instead of files, use the <see cref="ResourceStream"/> property.</para>
/// </remarks>
public class ResourceSavingArgs
{
    /// <summary>
    /// Ctor. Should be internal. Client code won't create this object.
    /// </summary>
    ResourceSavingArgs(Document doc, String resourceFileName, String resourceFileUri)
    {
        Debug.assertTrue(StringUtil.hasChars(resourceFileName));
        Debug.assertTrue(resourceFileUri != null);

        mDoc = doc;
        mResourceFileName = resourceFileName;
        mResourceFileUri = resourceFileUri;
    }

    /// <summary>
    /// Gets the document object that is currently being saved.
    /// </summary>
    public Document getDocument() { return mDoc; }

    /// <summary>
    /// Gets or sets the file name (without path) where the resource will be saved to.
    /// </summary>
    /// <remarks>
    /// <para>This property allows you to redefine how the resource file names are generated
    /// during export to fixed page HTML, SVG or Markdown.</para>
    ///
    /// <para>When the event is fired, this property contains the file name that was generated
    /// by Aspose.Words. You can change the value of this property to save the resource into a
    /// different file. Note that file names must be unique.</para>
    ///
    /// <p>Aspose.Words automatically generates a unique file name for every resource when
    /// exporting to fixed page HTML, SVG or Markdown format. How the resource file name is generated
    /// depends on whether you save the document to a file or to a stream.</p>
    ///
    /// <p>When saving a document to a file, the generated resource file name looks like
    /// <i>&lt;document base file name&gt;.&lt;image number&gt;.&lt;extension&gt;</i>.</p>
    ///
    /// <p>When saving a document to a stream, the generated resource file name looks like
    /// <i>Aspose.Words.&lt;document guid&gt;.&lt;image number&gt;.&lt;extension&gt;</i>.</p>
    ///
    /// </remarks>
    public String getResourceFileName() { return mResourceFileName; }
    public void setResourceFileName(String value) throws Exception
    {
        ArgumentUtil.checkHasChars(value, "ResourceFileName");
        if (!msString.equals(Path.getFileName(value), value))
            throw new IllegalArgumentException("ResourceFileName must be a file name without path.");
        mResourceFileName = value;
    }

    /// <summary>
    /// Gets or sets the uniform resource identifier (URI) used to reference the resource file from the document.
    /// </summary>
    /// <remarks>
    /// <para>This property allows you to change URIs of resource files exported to fixed page HTML,
    /// SVG or Markdown documents.</para>
    ///
    /// <para>Aspose.Words automatically generates an URI for every resource file during export to fixed page HTML,
    /// SVG or Markdown format. The generated URIs reference resource files saved by Aspose.Words. However, the URIs
    /// can be incorrect if resource files are to be moved to other location or if resource files are saved to streams.
    /// This property allows to correct URIs in these cases.</para>
    ///
    /// <para>When the event is fired, this property contains the URI that was generated
    /// by Aspose.Words. You can change the value of this property to provide a custom URI for the resource file.</para>
    ///
    /// <see cref="MarkdownSaveOptions.ImagesFolder"/>
    /// <see cref="MarkdownSaveOptions.ImagesFolderAlias"/>
    /// </remarks>
    public String getResourceFileUri() { return mResourceFileUri; }
    public void setResourceFileUri(String value)
    {
        ArgumentUtil.checkHasChars(value, "ResourceFileUri");
        mResourceFileUri = value;
        mHasUserResourceFileUri = true;
    }

    /// <summary>
    /// Indicates whether the user has provided a custom URI for the resource file.
    /// </summary>
    boolean hasUserResourceFileUri() { return mHasUserResourceFileUri; }

    /// <summary>
    /// Specifies whether Aspose.Words should keep the stream open or close it after saving a resource.
    /// </summary>
    /// <remarks>
    /// <para>Default is <c>false</c> and Aspose.Words will close the stream you provided
    /// in the <see cref="ResourceStream"/> property after writing a resource into it.
    /// Specify <c>true</c> to keep the stream open.</para>
    ///
    /// <seealso cref="ResourceStream"/>
    /// </remarks>
    public boolean getKeepResourceStreamOpen() { return mKeepResourceStreamOpen; }
    public void setKeepResourceStreamOpen(boolean value) { mKeepResourceStreamOpen = value; }

    /// <summary>
    /// Allows to specify the stream where the resource will be saved to.
    /// </summary>
    /// <remarks>
    /// <para>This property allows you to save resources to streams instead of files.</para>
    ///
    /// <para>The default value is <c>null</c>. When this property is <c>null</c>, the resource
    /// will be saved to a file specified in the <see cref="ResourceFileName"/> property.</para>
    ///
    /// <para>Using <see cref="IResourceSavingCallback" /> you cannot substitute one resource with
    /// another. It is intended only for control over location where to save resources.</para>
    ///
    /// <seealso cref="ResourceFileName"/>
    /// <seealso cref="KeepResourceStreamOpen"/>
    /// </remarks>
    /// <javaName>ResourceStream(java.io.OutputStream)</javaName>
    // Replaces the Stream with java.io.OutputStream in Java public API.
    public java.io.OutputStream getResourceStream() { return mResourceStream; }
    public void setResourceStream(java.io.OutputStream value) { mResourceStream = value; }
    private java.io.OutputStream mResourceStream;

    /// <summary>
    /// Exists to make calling code autoportable.
    /// </summary>
    boolean hasUserStream() { return mResourceStream != null; }

    /// <summary>
    /// Exists to make calling code autoportable.
    /// </summary>
    UserStreamWrapper createUserStreamWrapper()
    {
        return new UserStreamWrapper(mResourceStream, mKeepResourceStreamOpen);
    }

    private String mResourceFileName;
    private String mResourceFileUri;
    private boolean mHasUserResourceFileUri;
    private boolean mKeepResourceStreamOpen;
    private /*final*/ Document mDoc;
}

