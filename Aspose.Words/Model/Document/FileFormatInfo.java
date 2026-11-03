// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/07/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.Encoding;
import com.aspose.FileSystem;


/// <summary>
/// Contains data returned by <see cref="FileFormatUtil"/> document format detection methods.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/detect-file-format-and-check-format-compatibility/">Detect File Format and Check Format Compatibility</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>You do not create instances of this class directly. Objects of this class are returned by
/// <see cref="FileFormatUtil.DetectFileFormat(System.IO.Stream)"/> methods.</para>
/// </remarks>
public class FileFormatInfo
{
    /// <summary>
    /// No public ctor.
    /// </summary>
    FileFormatInfo()
    {
    }

    /// <summary>
    /// Gets the detected document format.
    /// </summary>
    /// <remarks>
    /// <para>When an OOXML document is encrypted, it is not possible to ascertain whether it is
    /// an Excel, Word or PowerPoint document without decrypting it first so for an encrypted OOXML
    /// document this property will always return <see cref="Aspose.Words.LoadFormat.Docx"/>.</para>
    ///
    /// <seealso cref="IsEncrypted"/>
    /// </remarks>
    public /*LoadFormat*/int getLoadFormat() { return mLoadFormat; }

    /// <summary>
    /// Returns <c>true</c> if the document is encrypted and requires a password to open.
    /// </summary>
    /// <remarks>
    /// <para>This property exists to help you sort documents that are encrypted from those that are not.
    /// If you attempt to load an encrypted document using Aspose.Words without supplying a password an
    /// exception will be thrown. You can use this property to detect whether a document requires a password
    /// and take some action before loading a document, for example, prompt the user for a password. </para>
    ///
    /// <seealso cref="LoadFormat"/>
    /// </remarks>
    public boolean isEncrypted() { return mIsEncrypted; }

    /// <summary>
    /// Returns <c>true</c> if this document contains a digital signature.
    /// This property merely informs that a digital signature is present on a document,
    /// but it does not  specify whether the signature is valid or not.
    /// </summary>
    /// <remarks>
    /// <para>This property exists to help you sort documents that are digitally signed from those that are not.
    /// If you use Aspose.Words to modify and save a document that is digitally signed, then the digital signature will
    /// be lost. This is by design because a digital signature exists to guard the authenticity of a document.
    /// Using this property you can detect digitally signed documents before processing them in the same way as normal
    /// documents and take some action to avoid losing the digital signature, for example notify the user.
    /// </para>
    /// </remarks>
    public boolean hasDigitalSignature() { return mHasDigitalSignature; }

    /// <summary>
    /// Returns <c>true</c> if this document contains a VBA macros.
    /// </summary>
    public boolean hasMacros() { return mHasMacros; }; void hasMacros(boolean value) { mHasMacros = value; };

    private boolean mHasMacros;

    /// <summary>
    /// Gets the detected encoding if applicable to the current document format.
    /// At the moment detects encoding only for HTML documents.
    /// </summary>
    //JAVA: property accessor is internalized
	Encoding getEncodingInternal() { return mEncoding; }
	//JAVA-added public wrapper for internalized property accessor
	public java.nio.charset.Charset getEncoding() { return Encoding.toJava(getEncodingInternal()); }

    /// <summary>
    /// Signing algorithm used in this file. Valid only if <see cref="HasDigitalSignature" /> is <c>true</c>.
    /// </summary>
    /*DigitalSignatureType*/int getDigitalSignatureType() { return mDigitalSignatureType; }
    void setDigitalSignatureType(/*DigitalSignatureType*/int value) { mDigitalSignatureType = value; }

    /// <summary>
    /// Indicates that the file is a MS Word template document.
    /// </summary>
    boolean isDocumentTemplate()
    {
        switch (mLoadFormat)
        {
            case LoadFormat.DOTX:
            case LoadFormat.DOT:
            case LoadFormat.DOTM:
            case LoadFormat.FLAT_OPC_TEMPLATE:
            case LoadFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
                return true;

            default:
                return false;
        }
    }

    void setLoadFormat(/*LoadFormat*/int loadFormat)
    {
        mLoadFormat = loadFormat;
    }

    void setIsEncrypted(boolean isEncrypted)
    {
        mIsEncrypted = isEncrypted;
    }

    void setIsDigitalSignaturePresent(boolean isDigitalSignaturePresent)
    {
        mHasDigitalSignature = isDigitalSignaturePresent;
    }

    void setEncoding(Encoding encoding)
    {
        mEncoding = encoding;
    }

    /// <summary>
    /// Gets the structured storage file system if the document is structured storage; <c>null</c> otherwise.
    ///
    /// RK This does not look like a very good idea to pass the Structured Storage File System here in the FileFormatInfo class.
    /// Was it done for speed optimization or for what purpose? I think it creates some bad dependencies. Should be investigated.
    /// </summary>
    FileSystem getFileSystem() { return mFileSystem; }
    void setFileSystem(FileSystem value) { mFileSystem = value; }

    /// <summary>
    /// Gets or sets a boolean value indicating whether file contains characters belonging to RTL scripts.
    /// </summary>
    boolean hasRtlScript() { return mHasRtlScript; }
    void hasRtlScript(boolean value) { mHasRtlScript = value; }

    /// <summary>
    /// Returns guessed <see cref="ApplicationType"/> where the document was created.
    /// </summary>
    /*ApplicationType*/int getApplication() { return mApplication; }
    void setApplication(/*ApplicationType*/int value) { mApplication = value; }

    /// <summary>
    /// Indicates that document is embedded into WordDocument stream.
    /// </summary>
    boolean EmbeddedInWordDocument = false;

    private /*LoadFormat*/int mLoadFormat = LoadFormat.UNKNOWN;
    private boolean mIsEncrypted;
    private boolean mHasDigitalSignature;
    private Encoding mEncoding;
    private boolean mHasRtlScript;
    private /*DigitalSignatureType*/int mDigitalSignatureType = DigitalSignatureType.UNKNOWN;
    private FileSystem mFileSystem;
    private /*ApplicationType*/int mApplication = ApplicationType.UNKNOWN;
}

