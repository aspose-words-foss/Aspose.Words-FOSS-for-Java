// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/04/2007 by Vladimir Averkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.Comparator;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.zip.ZipException;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msString;


/// <summary>
/// Reads and writes OPC packages.
/// Keeps all parts of the package in memory at all times.
/// </summary>
public class OpcPackage extends OpcPackageBase
{
    /// <summary>
    /// Creates a new empty package.
    /// </summary>
    public OpcPackage() { }

    /// <summary>
    /// Creates a new empty package with a specific comparator and compression level.
    /// </summary>
    public OpcPackage(Comparator<String> comparer, /*ZipCompressionLevel*/int compressionLevel, /*Zip64Option*/int zip64)
    {
    	super(comparer);
	
        mCompressionLevel = compressionLevel;
        mZip64Option = zip64;
    }

    /// <summary>
    /// Opens a package from a file and loads all parts into memory, reads content types and relationships.
    /// </summary>
    /// <param name="filename">Source file path.</param>
    public OpcPackage(String filename) throws Exception
    {
        Stream stream = new FileStream(filename, FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
    	{
            loadCore(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Opens a package from a file and loads all parts into memory, reads content types and relationships.
    /// </summary>
    /// <param name="stream">It is the responsibility of the caller to close the stream.</param>
    public OpcPackage(Stream stream) throws Exception
    {
    	this(stream, false, null);
	
    }

    /// <summary>
    /// Opens a package from a file and loads all parts into memory, reads content types and relationships.
    /// Allows skipping all non-text document parts that can be used for text extraction.
    /// </summary>
    /// <param name="stream">It is the responsibility of the caller to close the stream.</param>
    /// <param name="skipNonTextParts">Value indicating whether non-text parts should not be loaded.</param>
    /// <param name="warningCallback">Callback to register warnings upon loading.</param>
    public OpcPackage(Stream stream, boolean skipNonTextParts, IWarningCallbackCore warningCallback) throws Exception
    {
    	this(stream, skipNonTextParts, warningCallback, null);
	
    }

    /// <summary>
    /// Opens a package from a file and loads all parts into memory or file stream, reads content types
    /// and relationships. Allows skipping all non-text document parts that can be used for text extraction.
    /// </summary>
    public OpcPackage(Stream stream, boolean skipNonTextParts, IWarningCallbackCore warningCallback, TempFiler tempFiler) throws Exception
    {
        mWarnCallback = warningCallback;
        mSkipNonTextParts = skipNonTextParts;
        mTempFiler = tempFiler;
        loadCore(stream);
    }

    /// <summary>
    /// Saves the package into the stream.
    /// </summary>
    /// <param name="stream"></param>
    public /*override*/ void save(Stream stream) throws Exception
    {
        ZipWriterPal zipWriter = new ZipWriterPal(stream, mCompressionLevel, mZip64Option);
        for (OpcPackagePart part : getParts())
        {
            part.getStream().setPosition(0);
            Stream partStream = CompressedData.getStream(part.getStream());

            zipWriter.addEntry(partNameToZipName(part.getName()), StringUtil.trimRightCrLfStream(partStream), part.getZipEntryLastModified());
        }
        zipWriter.finish();
    }

    /// <summary>
    /// Updates content of Relationships and ContextTypes parts.
    /// </summary>
    public void updateRelationshipsAndContentTypes() throws Exception
    {
        ArrayList<OpcPackagePart> partsToRemove = new ArrayList<OpcPackagePart>();

        // We need to rewrite Relationships and ContentTypes, remove them first.
        for (OpcPackagePart part : getParts())
            if ((OpcContentType.RELATIONSHIPS.equals(part.getContentType())) || (CONTENT_TYPES_PART_NAME.equals(part.getName())))
                msArrayList.add(partsToRemove, part);

        for (OpcPackagePart part : partsToRemove)
            getParts().remove(part.getName());

        // Rewrite Relationships and ContentTypes.
        OpcRelsWriter.write(this, false);
        OpcContentTypesWriter.write(this, false);
    }

    /// <summary>
    /// Opens a package from a file and loads all parts into memory, reads content types and relationships.
    /// </summary>
    private void loadCore(Stream stream) throws Exception
    {
        loadParts(stream);

        StringToStringDictionary contentTypes = readContentTypes();
        assignContentTypesToParts(contentTypes);

        loadRelationships();
    }

    /// <summary>
    /// Just loads all parts into the parts collection.
    /// When quits, content types are not yet resolved and relationships are not read.
    /// </summary>
    private void loadParts(Stream stream) throws Exception
    {
        ZipReaderPal zipReader = new ZipReaderPal(stream);
        try /*JAVA: was using*/
        {
            while (zipReader.moveToNextEntry())
            {
                OpcPackagePart part = new OpcPackagePart(zipNameToPartName(zipReader.getEntryFileName()), "");
                part.setZipEntryLastModified(zipReader.getEntryLastModified());
                getParts().add(part);

                if (mSkipNonTextParts && !canContainText(part))
                    continue;

                try
                {
                    part.setStream(getPartStream(zipReader, part));
                }

                catch (ZipException ex)
                {
                    final String MSG = "Unable to extract {0} part.";

                    // WORDSNET-13791 Register information about skipped document part
                    // which cannot be read or throw the exception for debug mode when callback
                    // is not specified.
                    if (mWarnCallback != null)
                        mWarnCallback.warn(WarningTypeCore.DATA_LOSS, WarningSourceCore.UNKNOWN, MSG, part.getName());
                    else
                        Debug.fail(ex.getMessage());
                }
            }
        }
        finally { if (zipReader != null) zipReader.dispose(); }

        stream.setPosition(stream.getLength());
    }

    /// <summary>
    /// Gets data contained in one of MemoryStream, FileStream or ZipStream depending on settings and data content.
    /// </summary>
    /// <remarks>
    /// Concept overview:
    ///   MemoryStream - common way.
    ///   FileStream - temporary files feature.
    ///   ZipStream - large EMF image.
    /// </remarks>
    private Stream getPartStream(ZipReaderPal zipReader, OpcPackagePart part) throws Exception
    {
        /*FileFormat*/int fileFormat = FileFormatCore.fromExt(part.getExtension());
        final long MB = 1 << 20;

        if ((fileFormat == FileFormat.EMF) && (zipReader.getEntryLength() > 1 * MB))
        {
            // Do not decompress data, store it as special "compressed EMF" which is marked by header.
            byte[] compressedData = zipReader.loadCompressedEntryToByteArray();
            return new MemoryStream(compressedData);
        }

        // If temp folder was specified, then we are extracting large (>50Mb) xml package
        // to file. This degrades speed but reduces memory usage.
        if ((mTempFiler != null) && (mTempFiler.isTempFilesEnabled()) &&
            (fileFormat == FileFormat.XML) &&
            (zipReader.getEntryLength() > 50 * MB))
        {
            Stream stream = mTempFiler.getNewStream();
            zipReader.extractEntryToStream(stream);
            stream.setPosition(0);
            return stream;
        }
        return zipReader.loadCompressedEntryToMemory();
    }

    /// <summary>
    /// Returns true if the specified part can contain text to be extracted.
    /// </summary>
    private static boolean canContainText(OpcPackagePart part) throws Exception
    {
        /*FileFormat*/int format = FileFormatCore.fromExt(part.getExtension());
        return !(
                    format == FileFormat.BMP ||
                    format == FileFormat.EMF ||
                    format == FileFormat.GIF ||
                    format == FileFormat.JPEG ||
                    format == FileFormat.MOV ||
                    format == FileFormat.PICT ||
                    format == FileFormat.PNG ||
                    format == FileFormat.SVG ||
                    format == FileFormat.TIFF ||
                    format == FileFormat.WMF ||
                    format == FileFormat.WEB_P ||
                    format == FileFormat.ODTTF
                );
    }

    /// <summary>
    /// Returns a hashtable where the key is either extension or explicit part name and
    /// the value is the content type.
    /// </summary>
    private StringToStringDictionary readContentTypes() throws Exception
    {
        // WORDSNET-15883 and 17236. Content type has to be detected case insensitively.
        StringToStringDictionary contentTypes = new StringToStringDictionary(false);

        OpcPackagePart part = fetchPartByName(CONTENT_TYPES_PART_NAME);
        AnyXmlReader partReader = new AnyXmlReader(part.getStream());
        while (partReader.readChild("Types"))
        {
            switch (gStringSwitchMap.of(partReader.getLocalName()))
            {
                case /*"Default"*/0:
                    readDefaultContentType(partReader, contentTypes);
                    break;
                case /*"Override"*/1:
                    readOverrideContentType(partReader, contentTypes);
                    break;
                default:
                    partReader.ignoreElement();
                    break;
            }
        }

        return contentTypes;
    }

    /// <summary>
    /// Reads a default content type entry and adds it to the hashtable.
    /// </summary>
    private static void readDefaultContentType(AnyXmlReader partReader, StringToStringDictionary contentTypes)
    {
        String contentType = null;
        String extension = null;

        while (partReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(partReader.getLocalName()))
            {
                case /*"Extension"*/2:
                    extension = partReader.getValue();
                    break;
                case /*"ContentType"*/3:
                    contentType = partReader.getValue();
                    break;
                default:
                    // Ignore.
                    break;
            }
        }

        if (StringUtil.hasChars(contentType) && StringUtil.hasChars(extension))
            contentTypes.set(extension, contentType);
    }

    /// <summary>
    /// Reads an override content type entry and adds it to the hashtable.
    /// </summary>
    private static void readOverrideContentType(AnyXmlReader partReader, StringToStringDictionary contentTypes)
    {
        String contentType = null;
        String partName = null;

        while (partReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(partReader.getLocalName()))
            {
                case /*"PartName"*/4:
                    partName = partReader.getValue();
                    break;
                case /*"ContentType"*/3:
                    contentType = partReader.getValue();
                    break;
                default:
                    // Ignore.
                    break;
            }
        }

        if (StringUtil.hasChars(contentType) && StringUtil.hasChars(partName))
            contentTypes.set(partName, contentType);
    }

    private void assignContentTypesToParts(StringToStringDictionary contentTypes) throws Exception
    {
        for (OpcPackagePart part : getParts())
        {
            // First try to get content type for the part name (if it was specified using an override).
            String contentType = contentTypes.get(part.getName());

            // Then try to get content type for the part extension (if it was specified using a default).
            if (!StringUtil.hasChars(contentType))
                contentType = contentTypes.get(part.getExtension());

            part.setContentType(contentType);
        }
    }

    /// <summary>
    /// According to the OPC specification there is a quite complex algorithm to map
    /// from a ZIP entry name to a part name. It is complex because ZIP entry names
    /// are ASCII characters, whereas part names can contain Unicode characters,
    /// some characters need escaping and so on.
    /// These methods map from part name to zip name and back.
    ///
    /// So far there is a very simplistic implementation.
    ///
    /// RK We have to test with image file names containing Unicode characters and finish these methods.
    /// </summary>
    private static String partNameToZipName(String partName)
    {
        return msString.trimStart(partName, '/');
    }

    private static String zipNameToPartName(String zipName)
    {
        // WORDSNET-6582 The problem occurred because paths to parts are separated by back-slash.
        // Fixed by replacing back-slash with slash during loading OPC package.
        return "/" + zipName.replace("\\", "/");
    }

    public static final String CONTENT_TYPES_PART_NAME = "/[Content_Types].xml";
    private /*final*/ boolean mSkipNonTextParts;

    private /*final*/ /*ZipCompressionLevel*/int mCompressionLevel = ZipWriterPal.DEFAULT_COMPRESSION_LEVEL;
    private /*final*/ /*Zip64Option*/int mZip64Option = ZipWriterPal.DEFAULT_ZIP_64_MODE;

    /// <summary>
    /// Callback to notify user about errors and warnings.
    /// </summary>
    private /*final*/ IWarningCallbackCore mWarnCallback;
    private /*final*/ TempFiler mTempFiler;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"Default",
		"Override",
		"Extension",
		"ContentType",
		"PartName"
	);

}

