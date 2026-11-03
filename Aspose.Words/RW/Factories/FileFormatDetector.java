// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/05/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.EncodingUtil;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.CustomTextReader;
import com.aspose.FileSystem;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ZipReaderPal;
import java.util.zip.ZipException;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.AnyXmlReader;
import com.aspose.FileFormat;
import com.aspose.ImageUtil;
import com.aspose.OpcFlatPackage;
import com.aspose.OpcPackagePart;


/// <summary>
/// Detects the file format.
/// Use this as a normal instance (rather than static methods) to avoid loading
/// of data for some formats twice when you need to perform detect+load.
/// E.g. detecting a DOC file loads some parts of the structured storage directory into memory
/// and you can access the "preloaded" structured storage as a property on this object.
/// </summary>
class FileFormatDetector
{

    /// <summary>
    /// <para>Detects the document load format.</para>
    /// <para>If detected format is HTML then <see cref="Words.FileFormatInfo.Encoding"/>
    /// property will contain the detected encoding.</para>
    /// </summary>
    FileFormatInfo detect(Stream stream) throws Exception
    {
        return detect(stream, null);
    }

    /// <summary>
    /// <para>Detects the document load format. Allows specifying encoding of the detecting stream.</para>
    /// <para>If detected format is HTML then <see cref="Words.FileFormatInfo.Encoding"/>
    /// property will contain the detected encoding.</para>
    /// </summary>
    FileFormatInfo detect(Stream stream, Encoding encoding) throws Exception
    {
        mTextReader = new CustomTextReader(stream, encoding);
        mFileFormatInfo = new FileFormatInfo();

        try
        {
            // Don't change the order unless you are sure this will work.

            // Each Try* method can return false to try other format
            // or throw an exception to show that a format has been detected but the stream is invalid.

            if (tryDoc() || tryOoxmlAndOpenDocument() || tryRtf() || tryEncodingAwareFormats())
            {
                // Detected
            }
        }
        finally
        {
            mTextReader.rewindStream();
        }

        return mFileFormatInfo;
    }

    /// <summary>
    /// Returns true if the file is a valid PDF document.
    /// Rewinds stream to initial position.
    /// </summary>
    static boolean isPdf(Stream stream) throws Exception
    {
        CustomTextReader textReader = new CustomTextReader(stream);

        try
        {
            return tryPdf(textReader, null);
        }
        finally
        {
            textReader.rewindStream();
        }
    }

    /// <summary>
    /// Returns true if the file is a valid PDF document.
    /// </summary>
    private static boolean tryPdf(CustomTextReader textReader, FileFormatInfo fileFormatInfo) throws Exception
    {
        if (textReader.getStream().getLength() < G_PDF_HEADER.length + G_PDF_TRAILER.length)
            return false;

        // WORDSNET-19937 Experiments shows that Acrobat Reader handles files where %PDF-1.x header lies within the first 1024 bytes of the file.
        // CustomTextReader.Contains checks whether prefix of the stream contains given value.
        // Uses a part of the stream which is already read to internal buffer of the reader but
        // not more than 512 characters (it is enough for now but can be extended to 1024 if such document will be find out)
        // WORDSNET-25454 Check also PDF trailers.
        if (textReader.containsBinary(G_PDF_HEADER) && hasPdfTrailer(textReader.getStream()))
        {
            if (fileFormatInfo != null)
                fileFormatInfo.setLoadFormat(LoadFormat.PDF);

            return true;
        }

        return false;
    }

    /// <summary>
    /// Returns true, if a specified stream has PDF format trailer in last 1024 bytes.
    /// </summary>
    private static boolean hasPdfTrailer(Stream stream) throws Exception
    {
        long initialStreamPosition = stream.getPosition();
        // Acrobat viewers require only that the %%EOF marker appear somewhere within the last 1024 bytes of the file.
        // Adobe Portable Document Format, implementation note 18 in Appendix H.
        long startPosition = stream.getLength() > PDF_TRAILER_OFFSET ? stream.getLength() - PDF_TRAILER_OFFSET : stream.getLength() - G_PDF_HEADER.length;
        stream.setPosition(startPosition);
        byte[] buffer = new byte[(int)(stream.getLength() - startPosition)];
        int readCount = stream.read(buffer, 0, buffer.length);
        int bytesIndex = 0;
        for (int bufferIndex = readCount - G_PDF_TRAILER.length; bufferIndex >= 0; bufferIndex--)
        {
            bytesIndex = 0;
            while ((bytesIndex < G_PDF_TRAILER.length) && (buffer[bufferIndex + bytesIndex] == G_PDF_TRAILER[bytesIndex]))
                bytesIndex++;

            if (bytesIndex == G_PDF_TRAILER.length)
            {
                break;
            }
        }

        stream.setPosition(initialStreamPosition);
        return bytesIndex == G_PDF_TRAILER.length;
    }

    /// <summary>
    /// At the moment both DOC and DOT documents are recognized as <see cref="LoadFormat.Doc"/>.
    /// Rewinds the stream to initial position.
    /// </summary>
    /// <remarks>
    /// Actually this method tries all binary document formats.
    /// </remarks>>
    private boolean tryDoc() throws Exception
    {
        FileSystem fileSystem = getFileSystemFromStream();

        if (fileSystem == null)
        {
            // Detect Word20/WordForMac50 document.
            mTextReader.rewindStream();
            if (mTextReader.hasBinaryPrefix(LegacyDocumentReader.FILE_PREFIX_WORD_20) ||
                mTextReader.hasBinaryPrefix(LegacyDocumentReader.FILE_PREFIX_WORD_FOR_MAC_50))
            {
                mFileFormatInfo.setLoadFormat(LoadFormat.DOC);
                mFileFormatInfo.setIsEncrypted(false);
                return true;
            }

            return false;
        }

        // WORDSNET-4800 XLS or PPT files are recognized as DOC. We have to read a bit into the structured
        // storage to understand whether this is a Word document.
        if (fileSystem.containsInRootStorage("\u0006DataSpaces") ||
            (fileSystem.containsInRootStorage("EncryptedPackage") && fileSystem.containsInRootStorage("EncryptionInfo")))
        {
            // When an OOXML document is encrypted, it is not possible to ascertained whether it is
            // an Excel, Word or PowerPoint document without decrypting it first so we just return DOCX.
            mFileFormatInfo.setLoadFormat(LoadFormat.DOCX);
            mFileFormatInfo.setIsEncrypted(true);
        }
        else if (fileSystem.containsInRootStorage("WordDocument"))
        {

            // SPEED Read just a few bytes from one stream into memory.
            // No warning is needed at this moment.
            Fib fib = new Fib(new BinaryReader(fileSystem.readOneSectorFromStreamInRootStorage("WordDocument")), null, 0);

            // WORDSNET-26415 Try to detect document embedded into WordDocument stream.
            if (!fib.isValid())
            {
                MemoryStream wordDocument = (MemoryStream)fileSystem.getRoot().get("WordDocument");
                FileFormatDetector innerDetector = new FileFormatDetector();

                FileFormatInfo fi = innerDetector.detect(wordDocument);

                if (fi.getLoadFormat() != LoadFormat.UNKNOWN)
                {
                    mFileFormatInfo.EmbeddedInWordDocument = true;
                    mFileFormatInfo.setLoadFormat(fi.getLoadFormat());
                    mFileFormatInfo.setEncoding(fi.getEncodingInternal());
                    return true;
                }
            }

            if (fib.isPreWord60())
                mFileFormatInfo.setLoadFormat(LoadFormat.DOC_PRE_WORD_60);
            else
                mFileFormatInfo.setLoadFormat((fib.fDot) ? LoadFormat.DOT : LoadFormat.DOC);

            mFileFormatInfo.setIsEncrypted(fib.fEncrypted);

            // WORDSNET-17672 Check if document is digitally signed.
            // As far as I understood presence of those streams indicates that document is signed.
            boolean isXmlDsig = fileSystem.containsInRootStorage("_xmlsignatures");
            boolean isCryptoApi = fileSystem.containsInRootStorage("_signatures");

            mFileFormatInfo.setIsDigitalSignaturePresent(isXmlDsig || isCryptoApi);

            if (isXmlDsig)
                mFileFormatInfo.setDigitalSignatureType(DigitalSignatureType.XML_DSIG);
            else if (isCryptoApi)
                mFileFormatInfo.setDigitalSignatureType(DigitalSignatureType.CRYPTO_API);
            else
                mFileFormatInfo.setDigitalSignatureType(DigitalSignatureType.UNKNOWN);

            mFileFormatInfo.hasMacros(fileSystem.getRoot().getStorageSafe("Macros") != null);

            // Default value for DOC format.
            mFileFormatInfo.setApplication(ApplicationType.MS_WORD);
        }
        else if (fileSystem.containsInRootStorage("CONTENTS"))
        {
            BinaryReader reader = new BinaryReader((MemoryStream)fileSystem.getRoot().get("CONTENTS"));
            String signature = Encoding.getASCII().getString(reader.readBytes(8));
        }

        if (fileSystem.containsInRootStorage("WpsCustomData"))
            mFileFormatInfo.setApplication(ApplicationType.WPS_OFFICE);

        boolean result = (mFileFormatInfo.getLoadFormat() != LoadFormat.UNKNOWN);

        mFileFormatInfo.setFileSystem((result) ? fileSystem : null);
        return (mFileFormatInfo.getLoadFormat() != LoadFormat.UNKNOWN);
    }

    /// <summary>
    /// Returns <see cref="FileSystem"/> read from the stream or returns <c>null</c> if the stream is not FileSystem.
    /// May throw an exception if the stream is invalid FileSystem.
    /// </summary>
    private FileSystem getFileSystemFromStream() throws Exception
    {
        mTextReader.resetState();
        return (mTextReader.hasBinaryPrefix(FileSystem.getStructuredStorageSignature()))
            ? new FileSystem(mTextReader.getStream())
            : null;
    }

    /// <summary>
    /// Detects OOXML or OpenDocument document. Both formats are ZIP archives and detection
    /// is combined into one method to avoid unzipping twice during detection.
    /// Returns <see cref="LoadFormat.Unknown"/> if not a recognized document type.
    ///
    /// Rewinds the stream to initial position.
    ///
    /// RK According to the ODT specification, it is possible to detect ODT documents without unzipping
    /// because the mimetype entry is first and not zipped and occurs at a fixed location, but our
    /// early ODT export versions exported mimetype zipped and this type of identification will fail.
    /// Therefore we use the unzip library to find the mimetype entry in any location.
    /// </summary>
    private boolean tryOoxmlAndOpenDocument() throws Exception
    {
        ZipReaderPal zipReader = null;

        try
        {
            // Safe try to get zip reader.
            zipReader = getPalZipReader(mTextReader);
        }
        // WORDSNET-16557 Word generates "file corrupted error" if source file is zip but it has corrupted content.
        catch (ZipException ex)
        {
            throw new FileCorruptedException(ex);
        }
        catch(Exception e)
        {
            // Do nothing.
        }

        if (zipReader == null)
            return false;

        try
        {
            while (zipReader.moveToNextEntry())
            {
                // This code loops through all zip entries, it can be improved if really needed, but leave till later.

                // WORDSNET-8722 Compare ignoring case because seen "mimetype" in upper case when ODT document created by Windows 7 WordPad.

                // OOXML
                if (StringUtil.equalsIgnoreCase(zipReader.getEntryFileName(), "[Content_Types].xml"))
                {
                    mFileFormatInfo.setLoadFormat(DocxReader.detectLoadFormat(zipReader.loadEntryToMemory()));
                }
                else if (StringUtil.equalsIgnoreCase(zipReader.getEntryFileName(), "_rels/.rels"))
                {
                    mFileFormatInfo.setIsDigitalSignaturePresent(
                        DocxReader.isDigitalSignaturePresent(zipReader.loadEntryToMemory()));
                }
                else if (StringUtil.equalsIgnoreCase(zipReader.getEntryFileName(), "word/_rels/document.xml.rels"))
                {
                    mFileFormatInfo.hasMacros(DocxReader.isVbaProjectPresent(zipReader.loadEntryToMemory()));
                }
                // ODT
                else if (StringUtil.equalsIgnoreCase(zipReader.getEntryFileName(), "mimetype"))
                {
                    mFileFormatInfo.setLoadFormat(OdtReader.detectLoadFormat(zipReader.loadEntryToMemory()));
                }
                else if (StringUtil.equalsIgnoreCase(zipReader.getEntryFileName(), "META-INF/manifest.xml"))
                {
                    MemoryStream manifestStream = zipReader.loadEntryToMemory();

                    boolean isEncrypted = OdtReader.isEncrypted(manifestStream);
                    mFileFormatInfo.setIsEncrypted(isEncrypted);

                    if (!isEncrypted)
                        mFileFormatInfo.setLoadFormat(OdtReader.detectLoadFormatByManifest(manifestStream));
                }
                else if (StringUtil.equalsIgnoreCase(zipReader.getEntryFileName(), "META-INF/documentsignatures.xml"))
                {
                    mFileFormatInfo.setIsDigitalSignaturePresent(
                        OdtReader.isDigitalSignaturePresent(zipReader.loadEntryToMemory()));
                }
                else if (StringUtil.equalsIgnoreCase(zipReader.getEntryFileName(), "word/settings.xml"))
                {
                    if (hasWpsCustomDataScheme(zipReader.loadEntryToMemory()))
                        mFileFormatInfo.setApplication(ApplicationType.WPS_OFFICE);
                }
                else if (StringUtil.equalsIgnoreCase(zipReader.getEntryFileName(), "docProps/app.xml"))
                {
                    if (mFileFormatInfo.getApplication() == ApplicationType.UNKNOWN)
                    {
                        mFileFormatInfo.setApplication(getApplicationType(
                            getNameOfApplication(zipReader.loadEntryToMemory())));
                    }
                }
            }
        }
        finally
        {
            zipReader.dispose();
        }

        return (mFileFormatInfo.getLoadFormat() != LoadFormat.UNKNOWN);
    }

    /// <summary>
    /// Returns ApplicationType according to NameOfApplication attribute value.
    /// Shall be used as a low priority check since this attribute value is easy to correct manually.
    /// </summary>
    private static /*ApplicationType*/int getApplicationType(String applicationAttr)
    {
        // We cannot check equivalence to "Microsoft Office Word" because Word 2003 with CompatibilityPack2007
        // sets NameOfApplication as "Microsoft Office Outlook".
        if (msString.startsWith(applicationAttr, "Microsoft Office", StringComparison.ORDINAL))
            return ApplicationType.MS_WORD;
        if (msString.startsWith(applicationAttr, "OpenOffice", StringComparison.ORDINAL))
            return ApplicationType.OPEN_OFFICE_WRITER;
        if (msString.startsWith(applicationAttr, "WPS", StringComparison.ORDINAL))
            return ApplicationType.WPS_OFFICE;

        return ApplicationType.UNKNOWN;
    }

    /// <summary>
    /// You need to pass the "word/settings.xml" stream positioned at the beginning.
    /// Does NOT preserve the stream position.
    /// </summary>
    private static boolean hasWpsCustomDataScheme(Stream relsStream) throws Exception
    {
        AnyXmlReader reader = new AnyXmlReader(relsStream);
        if (!"settings".equals(reader.getLocalName()))
            return false;
        String value = reader.readAttribute("wpsCustomData", "");

        return ("http://www.wps.cn/officeDocument/2013/wpsCustomData".equals(value));
    }

    /// <summary>
    /// You need to pass the "word/settings.xml" stream positioned at the beginning.
    /// Does NOT preserve the stream position.
    /// </summary>
    private static String getNameOfApplication(Stream relsStream) throws Exception
    {
        if (relsStream.getLength() > 0)
        {
            AnyXmlReader reader = new AnyXmlReader(relsStream);

            while (reader.readChild("Properties"))
            {
                switch (gStringSwitchMap.of(reader.getLocalName()))
                {
                    case /*"Application"*/0:
                        return reader.readString();
                    default:
                        reader.ignoreElement();
                        break;
                }
            }
        }

        return "";
    }

    /// <summary>
    /// Checks for the @"{\rtf" signature.
    /// RTF is a text format. Can it potentially be in different encodings?
    /// </summary>
    private boolean tryRtf()
    {
        boolean result = mTextReader.hasBinaryPrefix("{\\rtf");

        if (result)
            mFileFormatInfo.setLoadFormat(LoadFormat.RTF);

        return result;
    }

    /// <summary>
    /// Returns true if any encoding-aware format is detected. The document in these formats can have
    /// various encodings and they will be recognized. Remembers the detected encoding for HTML only.
    ///
    /// Preserves the stream position, but positions the stream to skip UTF-7 BOM
    /// if one is found because .NET cannot read it.
    /// </summary>
    private boolean tryEncodingAwareFormats() throws Exception
    {
        if (tryPdf(mTextReader, mFileFormatInfo))
            return true;

        // BOM is found on CustomTextReader creation and will be ignored during detection MHTML.
        // Although MHTML must be in ASCII we ignore erroneous BOM at the beginning if any.

        if (tryMhtml())
            return true;

        mTextReader.setEncodingByBom();

        if (tryEncodingAwareFormatsOneEncoding())
            return true;

        FileFormatInfo fileFormatInfo = detectMarkdown();
        if (fileFormatInfo != null)
        {
            mFileFormatInfo = fileFormatInfo;
            return true;
        }

        // WORDSNET-23492 Check known image formats explicitly to filter out.
        /*FileFormat*/int fileFormat = ImageUtil.getImageType(mTextReader.getStream());
        if (fileFormat != FileFormat.UNKNOWN)
            return false;

        // If all format detection attempts, except plain text, are failed, use plain text detection result
        fileFormatInfo = detectPlainText();
        if (fileFormatInfo != null)
        {
            mFileFormatInfo = fileFormatInfo;
            return true;
        }

        return false;
    }

    /// <summary>
    /// Tries detecting encoding-aware formats on one encoding.
    /// </summary>
    private boolean tryEncodingAwareFormatsOneEncoding() throws Exception
    {
        return tryWml() || tryFlatOpc() || tryHtml();
    }

    /// <summary>
    /// Returns true and updates FileFormatInfo accordingly if the file is a valid MHTML document.
    /// </summary>
    private boolean tryMhtml() throws Exception
    {
        FileFormatInfo fileFormatInfo = MhtmlFormatDetector.detect(mTextReader);

        if (fileFormatInfo != null)
        {
            mFileFormatInfo = fileFormatInfo;
            return true;
        }

        return false;
    }

    /// <summary>
    /// Returns true and updates FileFormatInfo accordingly if the file is a valid HTML document.
    /// </summary>
    private boolean tryHtml() throws Exception
    {
        mTextReader.setEncodingByBom();

        FileFormatInfo fileFormatInfo = HtmlFormatDetector.detect(mTextReader);

        if (fileFormatInfo.getLoadFormat() == LoadFormat.HTML)
        {
            mFileFormatInfo = fileFormatInfo;
            return true;
        }

        return false;
    }

    /// <summary>
    /// If the file is a valid plain text document, records its load format and encoding in returned FileFormatInfo object.
    /// </summary>
    private FileFormatInfo detectPlainText() throws Exception
    {
        mTextReader.stepFirst();
        return TxtFormatDetector.detect(mTextReader);
    }

    /// <summary>
    /// Returns FileFormatInfo object if the file is a markdown document, or <c>null</c> otherwise.
    /// </summary>
    private FileFormatInfo detectMarkdown() throws Exception
    {
        mTextReader.stepFirst();
        return MarkdownFormatDetector.detect(mTextReader);
    }

    /// <summary>
    /// Returns true if this is a WordML document. We do not return the encoding because our importer
    /// reads it using XmlReader that automatically detects encoding of an XML document.
    /// </summary>
    private boolean tryWml()
    {
        boolean isDetected = mTextReader.contains("<w:wordDocument");

        if (isDetected)
            mFileFormatInfo.setLoadFormat(LoadFormat.WORD_ML);

        return isDetected;
    }

    /// <summary>
    /// Returns true if this is a FlatOpc (Word 2007 XML) document. We do not return the encoding because our importer
    /// reads it using XmlReader that automatically detects encoding of an XML document.
    /// </summary>
    private boolean tryFlatOpc() throws Exception
    {
        // AN: The first condition detects if it is Flat Opc package.
        // The second detects if it is Word document. This is needed because any office documents can be saved as Flat Opc.
        // I think it is enough to detect Word 2007 XML documents.
        // AM. FlatOpc alt chunk does not contain mso-application element.
        boolean isDetected = mTextReader.contains("<pkg:package");

        if (isDetected)
        {
            mTextReader.resetState();
            OpcFlatPackage _package = new OpcFlatPackage(mTextReader.getStream());

            // MS Word does not allow saving Word XML as strict, that is why try to get the part only by transitional namespace (isStrict=false).
            OpcPackagePart documentPart = _package.getPartByRelationshipType(null, DocxRelationshipTypes.getType(DocxRelationshipType.OFFICE_DOCUMENT, false));
            if (documentPart == null)
                // Try to get a workbookPart by the strict DocxRelationshipType.
                documentPart = _package.getPartByRelationshipType(null, DocxRelationshipTypes.getType(DocxRelationshipType.OFFICE_DOCUMENT, true));

            switch (gStringSwitchMap.of(documentPart.getContentType()))
            {
                case/*DocxContentType.DOCUMENT*/1:
                    mFileFormatInfo.setLoadFormat(LoadFormat.FLAT_OPC);
                    break;
                case/*DocxContentType.DOCUMENT_MACRO_ENABLED*/2:
                    mFileFormatInfo.setLoadFormat(LoadFormat.FLAT_OPC_MACRO_ENABLED);
                    break;
                case/*DocxContentType.TEMPLATE*/3:
                    mFileFormatInfo.setLoadFormat(LoadFormat.FLAT_OPC_TEMPLATE);
                    break;
                case/*DocxContentType.TEMPLATE_MACRO_ENABLED*/4:
                    mFileFormatInfo.setLoadFormat(LoadFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED);
                    break;
                default:
                    // Unknown content type.
                    isDetected = false;
                    break;
            }
        }

        return isDetected;
    }

    /// <summary>
    /// Returns <see cref="ZipReaderPal"/> built over the stream or returns <c>null</c> if the stream is not Zip package.
    /// May throw an exception if the stream is invalid Zip package.
    /// </summary>
    private static ZipReaderPal getPalZipReader(CustomTextReader textReader) throws Exception
    {
        textReader.resetState();
        return (textReader.hasBinaryPrefix(ZipReaderPal.getFileHeaderSignature()))
            ? new ZipReaderPal(textReader.getStream())
            : null;
    }

    /// <summary>
    /// Gets detected <see cref="System.Text.Encoding"/>.
    /// </summary>
    Encoding getEncoding() { return mTextReader.getEncoding(); }

    private static /*final*/ byte[] G_PDF_HEADER = new byte[] { (byte) 0x25, (byte) 0x50, (byte) 0x44, (byte) 0x46, (byte) 0x2D }; // %PDF-
    private static /*final*/ byte[] G_PDF_TRAILER = new byte[] { (byte) 0x25, (byte) 0x25, (byte) 0x45, (byte) 0x4F, (byte) 0x46 }; // %%EOF
    private static final int PDF_TRAILER_OFFSET = 1024;

    private CustomTextReader mTextReader;
    private FileFormatInfo mFileFormatInfo;
    /// <summary>
    /// Static ctor.
    /// </summary>
    static/* FileFormatDetector()*/
    {
        // Register encodings supported in the desktop .NET Framework but not in .NET Standard.
        EncodingUtil.registerEncodings();
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"Application",
		"application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml",
		"application/vnd.ms-word.document.macroEnabled.main+xml",
		"application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml",
		"application/vnd.ms-word.template.macroEnabledTemplate.main+xml"
	);

}

