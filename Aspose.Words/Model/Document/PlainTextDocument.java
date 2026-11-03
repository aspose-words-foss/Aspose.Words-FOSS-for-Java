// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/01/2016 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.SystemPal;


/// <summary>
/// Allows to extract plain-text representation of the document's content.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-text-document/">Working with Text Document</a> documentation article.</para>
/// </summary>
public class PlainTextDocument
{
    /// <summary>
    /// Creates a plain text document from a file. Automatically detects the file format.
    /// </summary>
    /// <param name="fileName">Name of the file to extract the text from.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.LoadFileExceptions"]/*'/>
    public PlainTextDocument(String fileName) throws Exception
    {
        LoadOptions options = new LoadOptions(LoadFormat.AUTO, "", "");
        options.setLoadMode(LoadMode.RAW_TEXT);

        Stream stream = SystemPal.openStreamFromHref(fileName);
        try /*JAVA: was using*/
    	{
            populateData(new Document(stream, options, true));
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Creates a plain text document from a file. Allows to specify additional options such as an encryption password.
    /// </summary>
    /// <remarks>
    /// </remarks>
    /// <param name="fileName">Name of the file to extract the text from.</param>
    /// <param name="loadOptions">Additional options to use when loading a document. Can be <c>null</c>.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.LoadFileExceptions"]/*'/>
    public PlainTextDocument(String fileName, LoadOptions loadOptions) throws Exception
    {
        LoadOptions options = (loadOptions == null)
            ? new LoadOptions()
            : loadOptions.deepClone();
        options.setLoadMode(LoadMode.RAW_TEXT);

        Stream stream = SystemPal.openStreamFromHref(fileName);
        try /*JAVA: was using*/
    	{
            populateData(new Document(stream, options, true));
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Creates a plain text document from a stream. Automatically detects the file format.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.OpenStreamCommon"]/*'/>
    /// <param name="stream">The stream where to extract the text from.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.LoadStreamExceptions"]/*'/>
    /// 
    /// <javaName>PlainTextDocument(java.io.InputStream stream)</javaName>
    // JAVA: the first public api change map will be used: Stream -> java.io.InputStream
    // WORDSJAVA-25686 - Loading from InputStream always load into memory first 
    //JAVA: ctor is internalized
	PlainTextDocument( Stream stream) throws Exception
    {
        LoadOptions options = new LoadOptions(LoadFormat.AUTO, "", "");
        options.setLoadMode(LoadMode.RAW_TEXT);
        populateData(new Document(stream, options, true));
    }

	//JAVA-added public wrapper for internalized ctor
	public PlainTextDocument( java.io.InputStream stream) throws Exception
	{
		this(Stream.fromJavaInputStream(stream));
	}

    /// <summary>
    /// Creates a plain text document from a stream. Allows to specify additional options such as an encryption password.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.OpenStreamCommon"]/*'/>
    /// <param name="stream">The stream where to extract the text from.</param>
    /// <param name="loadOptions">Additional options to use when loading a document. Can be <c>null</c>.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.LoadStreamExceptions"]/*'/>
    /// 
    /// <javaName>PlainTextDocument(java.io.InputStream stream, com.aspose.words.LoadOptions loadOptions)</javaName>
    // JAVA: the first public api change map will be used: Stream -> java.io.InputStream
    // WORDSJAVA-25686 - Loading from InputStream always load into memory first 
    //JAVA: ctor is internalized
	PlainTextDocument( Stream stream, LoadOptions loadOptions) throws Exception
    {
        LoadOptions options = (loadOptions == null)
            ? new LoadOptions()
            : loadOptions.deepClone();
        options.setLoadMode(LoadMode.RAW_TEXT);
        populateData(new Document(stream, options, true));
    }

	//JAVA-added public wrapper for internalized ctor
	public PlainTextDocument( java.io.InputStream stream,LoadOptions loadOptions) throws Exception
	{
		this(Stream.fromJavaInputStream(stream), loadOptions);
	}

    private void populateData(Document container)
    {
        mText = container.getText();
        mBuiltInDocumentProperties = container.getBuiltInDocumentProperties();
        mCustomProperties = container.getCustomDocumentProperties();
    }

    /// <summary>
    /// Gets textual content of the document concatenated as a string.
    /// </summary>
    public String getText() { return mText; }

    /// <summary>
    /// Gets <see cref="BuiltInDocumentProperties"/> of the document.
    /// </summary>
    public BuiltInDocumentProperties getBuiltInDocumentProperties() { return mBuiltInDocumentProperties; }

    /// <summary>
    /// Gets <see cref="CustomDocumentProperties"/> of the document.
    /// </summary>
    public CustomDocumentProperties getCustomDocumentProperties() { return mCustomProperties; }

    private String mText;
    private BuiltInDocumentProperties mBuiltInDocumentProperties;
    private CustomDocumentProperties mCustomProperties;
}

