// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import com.aspose.ms.System.IO.FileShare;
import com.aspose.FileSystem;
import com.aspose.OpcPackage;
import com.aspose.OpcPackagePart;
import com.aspose.OpcRelationshipType;
import com.aspose.OpcRelationship;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.DigestAlgorithm;
import com.aspose.CertificateHolderInternal;


/// <summary>
/// Provides methods for signing document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-digital-signatures/">Work with Digital Signatures</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>Since digital signature works with file content rather than Document Object Model these methods are put into a separate class.</para>
/// <para>Supported formats are:
/// <see cref= "LoadFormat.Doc"/>,
/// <see cref= "LoadFormat.Dot"/>,
/// <see cref= "LoadFormat.Docx"/>,
/// <see cref= "LoadFormat.Dotx"/>,
/// <see cref= "LoadFormat.Docm"/>,
/// <see cref= "LoadFormat.Dotm"/>,
/// <see cref= "LoadFormat.Odt"/>,
/// <see cref= "LoadFormat.Ott"/>.</para>
/// </remarks>
public /*static*/ class DigitalSignatureUtil
{
	/* Simulation of static class by using private constructor */
	private DigitalSignatureUtil()
	{}

    /// <summary>
    /// Signs source document using given <see cref="CertificateHolder"/> and <see cref="SignOptions"/>
    /// with digital signature and writes signed document to destination stream.
    /// <p>Supported formats are:
    /// <see cref= "LoadFormat.Doc"/>,
    /// <see cref= "LoadFormat.Dot"/>,
    /// <see cref= "LoadFormat.Docx"/>,
    /// <see cref= "LoadFormat.Dotx"/>,
    /// <see cref= "LoadFormat.Docm"/>,
    /// <see cref= "LoadFormat.Dotm"/>,
    /// <see cref= "LoadFormat.Odt"/>,
    /// <see cref= "LoadFormat.Ott"/>.</p>
    /// <p><b>Output will be written to the start of stream and stream size will be updated with content length.</b></p>
    /// </summary>
    /// <param name="srcStream">The stream which contains the document to sign.</param>
    /// <param name="dstStream">The stream that signed document will be written to.</param>
    /// <param name="certHolder"><see cref="CertificateHolder"/> object with certificate that used to sign file.
    /// <ms>The certificate in holder MUST contain private keys and have the X509KeyStorageFlags.Exportable flag set.</ms>
    /// <cpp>The certificate in holder MUST contain private keys.</cpp></param>
    /// <param name="signOptions"><see cref="SignOptions"/> object with various signing options.</param>
    /// <javaName>void sign(java.io.InputStream srcStream,java.io.OutputStream dstStream,com.aspose.words.CertificateHolder certHolder,com.aspose.words.SignOptions signOptions)</javaName>
    //JAVA: method is internalized
	
    static void signInternal( Stream srcStream,  Stream dstStream, CertificateHolder certHolder, SignOptions signOptions) throws Exception
    {
        ArgumentUtil.checkNotNull(srcStream, "srcStream");
        ArgumentUtil.checkNotNull(dstStream, "dstStream");
        ArgumentUtil.checkNotNull(certHolder, "certHolder");

        signStreams(srcStream, dstStream, certHolder, signOptions);

        // WORDSJAVA-2079 internal MemoryStream should be flushed to public java.io.OutputStream.
        dstStream.flush();
    }

	//JAVA-added public wrapper for internalized method
	public static  void sign( java.io.InputStream srcStream,  java.io.OutputStream dstStream,CertificateHolder certHolder,SignOptions signOptions) throws Exception
	{
		signInternal(Stream.fromJavaInputStream(srcStream), Stream.fromJavaOutputStream(dstStream), certHolder, signOptions);
	}

    private static void signStreams(Stream srcStream, Stream dstStream, CertificateHolder certHolder, SignOptions signOptions) throws Exception
    {
        DigitalSignature signature = new DigitalSignature(certHolder);

        if (signOptions != null)
            signOptions.applyTo(signature);

        // FOSS

        FileFormatDetector detector = new FileFormatDetector();
        FileFormatInfo formatInfo = detector.detect(srcStream);

        // Choose appropriate signer.
        switch (formatInfo.getLoadFormat())
        {
            case LoadFormat.DOC:
            case LoadFormat.DOT:
                throw new UnsupportedOperationException("FOSS");

            case LoadFormat.DOCX:
            case LoadFormat.DOTX:
            case LoadFormat.DOCM:
            case LoadFormat.DOTM:
                throw new UnsupportedOperationException("FOSS");

            // WORDSNET-25914 Added case for OTT format.
            case LoadFormat.OTT:
            case LoadFormat.ODT:
                throw new UnsupportedOperationException("FOSS");
            default:
                throw new IllegalStateException(NOT_SUPPORTED_BY_FILE_FORMAT);
        }
    }

    /// <summary>
    /// Signs source document using given <see cref="CertificateHolder"/> and <see cref="SignOptions"/>
    /// with digital signature and writes signed document to destination file.
    /// <p>Supported formats are:
    /// <see cref= "LoadFormat.Doc"/>,
    /// <see cref= "LoadFormat.Dot"/>,
    /// <see cref= "LoadFormat.Docx"/>,
    /// <see cref= "LoadFormat.Dotx"/>,
    /// <see cref= "LoadFormat.Docm"/>,
    /// <see cref= "LoadFormat.Dotm"/>,
    /// <see cref= "LoadFormat.Odt"/>,
    /// <see cref= "LoadFormat.Ott"/>.</p>
    /// </summary>
    /// <param name="srcFileName">The file name of the document to sign.</param>
    /// <param name="dstFileName">The file name of the signed document output.</param>
    /// <param name="certHolder"><see cref="CertificateHolder"/> object with certificate that used to sign file.
    /// <ms>The certificate in holder MUST contain private keys and have the X509KeyStorageFlags.Exportable flag set.</ms>
    /// <cpp>The certificate in holder MUST contain private keys.</cpp></param>
    /// <param name="signOptions"><see cref="SignOptions"/> object with various signing options.</param>
    public static void sign(String srcFileName, String dstFileName, CertificateHolder certHolder, SignOptions signOptions) throws Exception
    {
        ArgumentUtil.checkHasChars(srcFileName, "srcFileName");
        ArgumentUtil.checkHasChars(dstFileName, "dstFileName");

        // Open source stream with read/write sharing because it can be the same file passed as source and destination.
        FileStream srcStream = File.open(srcFileName, FileMode.OPEN, FileAccess.READ, FileShare.READ_WRITE);
        try /*JAVA: was using*/
        {
            // The same reason we OpenOrCreate output file.
            FileStream dstStream = File.open(dstFileName, FileMode.OPEN_OR_CREATE, FileAccess.WRITE, FileShare.READ);
            try /*JAVA: was using*/
        	{
                signInternal(srcStream, dstStream, certHolder, signOptions);
        	}
            finally { if (dstStream != null) dstStream.close(); }
        }
        finally { if (srcStream != null) srcStream.close(); }
    }

    /// <summary>
    /// Signs source document using given <see cref="CertificateHolder"/> with digital signature
    /// and writes signed document to destination stream.
    /// <p>Supported formats are:
    /// <see cref= "LoadFormat.Doc"/>,
    /// <see cref= "LoadFormat.Dot"/>,
    /// <see cref= "LoadFormat.Docx"/>,
    /// <see cref= "LoadFormat.Dotx"/>,
    /// <see cref= "LoadFormat.Docm"/>,
    /// <see cref= "LoadFormat.Dotm"/>,
    /// <see cref= "LoadFormat.Odt"/>,
    /// <see cref= "LoadFormat.Ott"/>.</p>
    /// <p><b>Output will be written to the start of stream and stream size will be updated with content length.</b></p>
    /// </summary>
    /// <param name="srcStream">The stream which contains the document to sign.</param>
    /// <param name="dstStream">The stream that signed document will be written to.</param>
    /// <param name="certHolder"><see cref="CertificateHolder"/> object with certificate that used to sign file.
    /// <ms>The certificate in holder MUST contain private keys and have the X509KeyStorageFlags.Exportable flag set.</ms>
    /// <cpp>The certificate in holder MUST contain private keys.</cpp></param>
    /// <javaName>void sign(java.io.InputStream srcStream,java.io.OutputStream dstStream,com.aspose.words.CertificateHolder certHolder)</javaName>
    //JAVA: method is internalized
	
    static void signInternal( Stream srcStream,  Stream dstStream, CertificateHolder certHolder) throws Exception
    {
        signInternal(srcStream, dstStream, certHolder, null);
    }

	//JAVA-added public wrapper for internalized method
	public static  void sign( java.io.InputStream srcStream,  java.io.OutputStream dstStream,CertificateHolder certHolder) throws Exception
	{
		signInternal(Stream.fromJavaInputStream(srcStream), Stream.fromJavaOutputStream(dstStream), certHolder);
	}

    /// <summary>
    /// Signs source document using given <see cref="CertificateHolder"/> with digital signature
    /// and writes signed document to destination file.
    /// <p>Supported formats are:
    /// <see cref= "LoadFormat.Doc"/>,
    /// <see cref= "LoadFormat.Dot"/>,
    /// <see cref= "LoadFormat.Docx"/>,
    /// <see cref= "LoadFormat.Dotx"/>,
    /// <see cref= "LoadFormat.Docm"/>,
    /// <see cref= "LoadFormat.Dotm"/>,
    /// <see cref= "LoadFormat.Odt"/>,
    /// <see cref= "LoadFormat.Ott"/>.</p>
    /// </summary>
    /// <param name="srcFileName">The file name of the document to sign.</param>
    /// <param name="dstFileName">The file name of the signed document output.</param>
    /// <param name="certHolder"><see cref="CertificateHolder"/> object with certificate that used to sign file.
    /// <ms>The certificate in holder MUST contain private keys and have the X509KeyStorageFlags.Exportable flag set.</ms>
    /// <cpp>The certificate in holder MUST contain private keys.</cpp></param>
    public static void sign(String srcFileName, String dstFileName, CertificateHolder certHolder) throws Exception
    {
        sign(srcFileName, dstFileName, certHolder, null);
    }

    /// <summary>
    /// Removes all digital signatures from source file and writes unsigned file to destination file.
    /// <p>The following formats are compatible for digital signature removal:
    /// <see cref= "LoadFormat.Doc"/>,
    /// <see cref= "LoadFormat.Dot"/>,
    /// <see cref= "LoadFormat.Docx"/>,
    /// <see cref= "LoadFormat.Dotx"/>,
    /// <see cref= "LoadFormat.Docm"/>,
    /// <see cref= "LoadFormat.Dotm"/>,
    /// <see cref= "LoadFormat.Odt"/>,
    /// <see cref= "LoadFormat.Ott"/>.</p>
    /// </summary>
    public static void removeAllSignatures(String srcFileName, String dstFileName) throws Exception
    {
        // Open source stream with read/write sharing because it can be the same file passed as source and destination.
        FileStream srcStream = File.open(srcFileName, FileMode.OPEN, FileAccess.READ, FileShare.READ_WRITE);
        try /*JAVA: was using*/
        {
            // The same reason we OpenOrCreate output file.
            FileStream dstStream = File.open(dstFileName, FileMode.OPEN_OR_CREATE, FileAccess.WRITE, FileShare.READ);
            try /*JAVA: was using*/
        	{
                removeAllSignaturesInternal(srcStream, dstStream);
        	}
            finally { if (dstStream != null) dstStream.close(); }
        }
        finally { if (srcStream != null) srcStream.close(); }
    }

    /// <summary>
    /// Removes all digital signatures from document in source stream and writes unsigned document to destination stream.
    /// <p><b>Output will be written to the start of stream and stream size will be updated with content length.</b></p>
    /// <p>The following formats are compatible for digital signature removal:
    /// <see cref= "LoadFormat.Doc"/>,
    /// <see cref= "LoadFormat.Dot"/>,
    /// <see cref= "LoadFormat.Docx"/>,
    /// <see cref= "LoadFormat.Dotx"/>,
    /// <see cref= "LoadFormat.Docm"/>,
    /// <see cref= "LoadFormat.Dotm"/>,
    /// <see cref= "LoadFormat.Odt"/>,
    /// <see cref= "LoadFormat.Ott"/>.</p>
    /// </summary>
    /// <javaName>void removeAllSignatures(java.io.InputStream srcStream,java.io.OutputStream dstStream)</javaName>
    //JAVA: method is internalized
	
    static void removeAllSignaturesInternal( Stream srcStream,  Stream dstStream) throws Exception
    {
        FileFormatDetector detector = new FileFormatDetector();
        FileFormatInfo formatInfo = detector.detect(srcStream);

        switch (formatInfo.getLoadFormat())
        {
            case LoadFormat.DOC:
            case LoadFormat.DOT:
            {
                FileSystem fs = new FileSystem(srcStream);

                fs.getRoot().remove(OfficeCryptoNames.XML_DSIG_SIGNATURE_STORAGE_NAME);
                fs.getRoot().remove(OfficeCryptoNames.CRYPTO_API_SIGNATURE_STREAM_NAME);

                dstStream.setPosition(0);

                // Save file system to destination stream.
                fs.save(dstStream);

                // Update stream length.
                dstStream.setLength(dstStream.getPosition());
                break;
            }

            case LoadFormat.DOCX:
            case LoadFormat.DOTX:
            case LoadFormat.DOCM:
            case LoadFormat.DOTM:
            {
                OpcPackage _package = new OpcPackage(srcStream);
                // Look for existing signatures.
                OpcPackagePart originPart = _package.getPartByRelationshipType(null, OpcRelationshipType.DIGITAL_SIGNATURE_ORIGIN);

                if (originPart != null)
                {
                    for (OpcRelationship rel : originPart.getRels())
                    {
                        String signaturePartName = originPart.getRelatedPartName(rel);
                        _package.getParts().remove(signaturePartName);
                    }

                    _package.getParts().remove(originPart.getName());
                    OpcRelationship originPartRel = _package.getRels().getFirstByType(OpcRelationshipType.DIGITAL_SIGNATURE_ORIGIN);
                    _package.getRels().remove(originPartRel.getId());
                    _package.updateRelationshipsAndContentTypes();
                }

                dstStream.setPosition(0);

                // Save package to destination stream.
                _package.save(dstStream);

                dstStream.setLength(dstStream.getPosition());
                break;
            }

            // WORDSNET-25043 Implemented signature removing in ODT.
            // WORDSNET-25411 Added case for OTT.
            case LoadFormat.ODT:
            case LoadFormat.OTT:
                throw new UnsupportedOperationException("FOSS");
            default:
                throw new IllegalStateException(NOT_SUPPORTED_BY_FILE_FORMAT);
        }
    }

	//JAVA-added public wrapper for internalized method
	public static  void removeAllSignatures( java.io.InputStream srcStream,  java.io.OutputStream dstStream) throws Exception
	{
		removeAllSignaturesInternal(Stream.fromJavaInputStream(srcStream), Stream.fromJavaOutputStream(dstStream));
	}

    /// <summary>
    /// Loads digital signatures from document.
    /// </summary>
    /// <param name="fileName">Path to the document.</param>
    /// <returns>Collection of digital signatures. Returns empty collection if file is not signed.</returns>
    public static DigitalSignatureCollection loadSignatures(String fileName) throws Exception
    {
        FileStream stream = File.open(fileName, FileMode.OPEN, FileAccess.READ, FileShare.READ);
        try /*JAVA: was using*/
    	{
            return loadSignaturesInternal(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Loads digital signatures from document using stream.
    /// </summary>
    /// <param name="stream">Stream with the document.</param>
    /// <returns>Collection of digital signatures. Returns empty collection if file is not signed.</returns>
    /// <javaName>com.aspose.words.DigitalSignatureCollection loadSignatures(java.io.InputStream stream)</javaName>
    //JAVA: method is internalized
	static DigitalSignatureCollection loadSignaturesInternal( Stream stream) throws Exception
    {
        FileFormatDetector detector = new FileFormatDetector();
        FileFormatInfo formatInfo = detector.detect(stream);

        switch (formatInfo.getLoadFormat())
        {
            case LoadFormat.DOC:
            case LoadFormat.DOT:
            case LoadFormat.DOCX:
            case LoadFormat.DOTX:
            case LoadFormat.DOTM:
            case LoadFormat.DOCM:
            case LoadFormat.ODT:
            case LoadFormat.OTT:
                // Simplify the task for a while and use existing code.
                Document doc = new Document(stream, null, false);
                return doc.getDigitalSignatures();

            default:
                throw new IllegalStateException(NOT_SUPPORTED_BY_FILE_FORMAT);
        }
    }

	//JAVA-added public wrapper for internalized method
	public static DigitalSignatureCollection loadSignatures( java.io.InputStream stream) throws Exception
	{
		return loadSignaturesInternal(Stream.fromJavaInputStream(stream));
	}

    /// <summary>
    /// Decrypts <see cref="FileSystem"/> using specified password.
    /// </summary>
    static MemoryStream decryptFileSystem(FileSystem fileSystem, String password) throws Exception
    {
        DataSpaces dataSpaces = new DataSpaces(fileSystem);
        if (dataSpaces.isEncryptedEcma376())
        {
            MemoryStream decryptedStream = dataSpaces.decrypt(password);

            // SPEED After we decrypted we don't need these and we null them to remove
            // all references to the structured storage so it can go to garbage.
            // Although resharper grays this code out, I think it helps with memory.
            dataSpaces = null;
            fileSystem = null;

            return decryptedStream;
        }
        else
            throw new UnsupportedFileFormatException("Unknown file format.");
    }

    /// <summary>
    /// Returns a string with a digest method.
    /// </summary>
    static String getDigestMethod(/*DigestAlgorithm*/int digestAlgorithm)
    {
        switch (digestAlgorithm)
        {
            case DigestAlgorithm.SHA_256:
                return "http://www.w3.org/2001/04/xmlenc#sha256";
            default:
                return "http://www.w3.org/2000/09/xmldsig#sha1";
        }
    }

    /// <summary>
    /// Loads digital signatures from XPS document.
    /// </summary>
    private static DigitalSignatureCollection loadFromXps(Stream stream) throws Exception
    {
        stream.setPosition(0);
        OpcPackage _package = new OpcPackage(stream);

        DigitalSignatureCollection signatures = new DigitalSignatureCollection();

        OpcPackagePart signatureOriginPart = _package.getPartByRelationshipType(null, OpcRelationshipType.DIGITAL_SIGNATURE_ORIGIN);
        if (signatureOriginPart != null)
            for (OpcRelationship rel : signatureOriginPart.getRels())
            {
                String signaturePartName = signatureOriginPart.getRelatedPartName(rel);
                OpcPackagePart signaturePart = _package.fetchPartByName(signaturePartName);

                OpcPackagePart certificatePart =
                    _package.getPartByRelationshipType(signaturePart, OpcRelationshipType.DIGITAL_SIGNATURE_CERTIFICATE);


                CertificateHolderInternal cert = com.aspose.CryptoUtilPal.createHolder(
                    certificatePart.getAsMemoryStream().toArray());

                XmlDsigReader.read(signaturePart.getStream(), cert, new OpcReferenceResolver(_package), signatures, null);
            }

        return signatures;
    }

    private static final String NOT_SUPPORTED_BY_FILE_FORMAT = "Signing feature is not supported by this file format.";
}

