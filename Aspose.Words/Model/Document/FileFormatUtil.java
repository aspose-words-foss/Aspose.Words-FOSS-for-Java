// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import com.aspose.FileFormatCore;
import com.aspose.StringUtil;
import java.io.EOFException;
import java.lang.NumberFormatException;
import com.aspose.FileFormat;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.DictionaryExtension;
import java.util.HashMap;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Provides utility methods for working with file formats, such as detecting file format
/// or converting file extensions to/from file format enums.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/detect-file-format-and-check-format-compatibility/">Detect File Format and Check Format Compatibility</a> documentation article.</para>
/// </summary>
public /*static*/ class FileFormatUtil
{
	/* Simulation of static class by using private constructor */
	private FileFormatUtil()
	{}

    /// <overloads>Detects and returns the information about a format of a document.</overloads>
    /// <summary>
    /// Detects and returns the information about a format of a document stored in a disk file.
    /// </summary>
    /// <remarks>
    /// <para>Even if this method detects the document format, it does not guarantee
    /// that the specified document is valid. This method only detects the document format by
    /// reading data that is sufficient for detection. To fully verify that a document is valid
    /// you need to load the document into a <see cref="Document"/> object.</para>
    /// <para>This method throws <see cref="FileCorruptedException"/> when the format is
    /// recognized, but the detection cannot complete because of corruption.</para>
    /// </remarks>
    ///
    /// <param name="fileName">The file name.</param>
    /// <returns>A <see cref="FileFormatInfo"/> object that contains the detected information.</returns>
    public static FileFormatInfo detectFileFormat(String fileName) throws Exception
    {
        ArgumentUtil.checkHasChars(fileName, "fileName");

        Stream stream = File.openRead(fileName);
        try /*JAVA: was using*/
    	{
            return detectFileFormat(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Detects and returns the information about a format of a document stored in a stream.
    /// </summary>
    /// <remarks>
    /// <para>The stream must be positioned at the beginning of the document.</para>
    ///
    /// <ms><para>When this method returns, the position in the stream is restored to the original position.</para></ms>
    /// <cpp><para>When this method returns, the position in the stream is restored to the original position.</para></cpp>
    ///
    /// <java><para>Detecting a file format might require seeking to various positions in the stream. Because java.io.InputStream
    /// is not seekable, this method loads the whole stream into memory temporarily. When this method returns,
    /// the stream is positioned at the end of the document.</para></java>
    ///
    /// <para>Even if this method detects the document format, it does not guarantee
    /// that the specified document is valid. This method only detects the document format by
    /// reading data that is sufficient for detection. To fully verify that a document is valid
    /// you need to load the document into a <see cref="Document"/> object.</para>
    /// <para>This method throws <see cref="FileCorruptedException"/> when the format is
    /// recognized, but the detection cannot complete because of corruption.</para>
    /// </remarks>
    ///
    /// <param name="stream">The stream.</param>
    /// <returns>A <see cref="FileFormatInfo"/> object that contains the detected information.</returns>
    /// <javaName>com.aspose.words.FileFormatInfo detectFileFormat(java.io.InputStream stream)</javaName>
    // WORDSJAVA-25686 - Loading from InputStream always load into memory first
    //JAVA-added public wrapper for internalized method
    public static FileFormatInfo detectFileFormat(java.io.InputStream stream) throws Exception
    {
        return detectFileFormat(com.aspose.ms.java.IO.JavaOnlyStreamUtil.copyToMemoryStream(stream));
    }
    static FileFormatInfo detectFileFormat( Stream stream) throws Exception
    {
        if (stream == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "stream");

        try
        {
            FileFormatDetector detector = new FileFormatDetector();
            return detector.detect(stream);
        }
        catch (Exception e)
        {
            // Same approach as in the loading of a document.

            throw convertLoadException(e);
        }
    }

    /// <summary>
    /// Converts IANA content type into a load format enumerated value.
    /// </summary>
    /// <exception cref="ArgumentException">Throws when cannot convert.</exception>
    public static /*LoadFormat*/int contentTypeToLoadFormat(String contentType)
    {
        /*LoadFormat*/int loadFormat = toLoadFormat(FileFormatCore.fromContentType(contentType));
        if (loadFormat != LoadFormat.UNKNOWN)
            return loadFormat;
        else
            throw new IllegalArgumentException("Cannot convert this content type to a load format.");
    }

    /// <summary>
    /// Converts IANA content type into a save format enumerated value.
    /// </summary>
    /// <exception cref="ArgumentException">Throws when cannot convert.</exception>
    public static /*SaveFormat*/int contentTypeToSaveFormat(String contentType)
    {
        /*SaveFormat*/int saveFormat = toSaveFormat(FileFormatCore.fromContentType(contentType));
        if (saveFormat != SaveFormat.UNKNOWN)
            return saveFormat;
        else
            throw new IllegalArgumentException("Cannot convert this content type to a save format.");
    }

    /// <summary>
    /// Converts a load format enumerated value into a file extension. The returned extension is a lower-case string with a leading dot.
    /// </summary>
    /// <remarks>
    /// <para>The <see cref="SaveFormat.WordML"/> value is converted to ".wml".</para>
    /// </remarks>
    /// <exception cref="ArgumentException">Throws when cannot convert.</exception>
    public static String loadFormatToExtension(/*LoadFormat*/int loadFormat)
    {
        if (loadFormat == LoadFormat.DOC_PRE_WORD_60)
            loadFormat = LoadFormat.DOC;

        String ext = FileFormatCore.toExt(fromLoadFormat(loadFormat));
        if (StringUtil.hasChars(ext))
            return "." + ext;
        else
            throw new IllegalArgumentException("Cannot convert this load format to a file extension.");
    }

    /// <summary>
    /// Converts a <see cref="SaveFormat"/> value to a <see cref="LoadFormat"/> value if possible.
    /// </summary>
    /// <exception cref="ArgumentException">Throws when cannot convert.</exception>
    public static /*LoadFormat*/int saveFormatToLoadFormat(/*SaveFormat*/int saveFormat)
    {
        /*LoadFormat*/int loadFormat = toLoadFormat(fromSaveFormat(saveFormat));
        if (loadFormat != LoadFormat.UNKNOWN)
            return loadFormat;
        else
            throw new IllegalArgumentException("Cannot convert this save format to a load format.");
    }

    /// <summary>
    /// Converts a <see cref="LoadFormat"/> value to a <see cref="SaveFormat"/> value if possible.
    /// </summary>
    /// <exception cref="ArgumentException">Throws when cannot convert.</exception>
    public static /*SaveFormat*/int loadFormatToSaveFormat(/*LoadFormat*/int loadFormat)
    {
        /*SaveFormat*/int saveFormat = toSaveFormat(fromLoadFormat(loadFormat));
        if (saveFormat != SaveFormat.UNKNOWN)
            return saveFormat;
        else
            throw new IllegalArgumentException("Cannot convert this load format to a save format.");
    }

    /// <summary>
    /// Converts a save format enumerated value into a file extension. The returned extension is a lower-case string with a leading dot.
    /// </summary>
    /// <remarks>
    /// <para>The <see cref="SaveFormat.WordML"/> value is converted to ".wml".</para>
    /// <para>The <see cref="SaveFormat.FlatOpc"/> value is converted to ".fopc".</para>
    /// </remarks>
    /// <exception cref="ArgumentException">Throws when cannot convert.</exception>
    public static String saveFormatToExtension(/*SaveFormat*/int saveFormat)
    {
        String ext = FileFormatCore.toExt(fromSaveFormat(saveFormat));
        if (StringUtil.hasChars(ext))
            return "." + ext;
        else
            throw new IllegalArgumentException("Cannot convert this save format to a file extension.");
    }

    /// <summary>
    /// Converts a file name extension into a <see cref="SaveFormat"/> value.
    /// </summary>
    /// <param name="extension">The file extension. Can be with or without a leading dot. Case-insensitive.</param>
    /// <remarks>
    /// <p>If the extension cannot be recognized, returns <see cref="SaveFormat.Unknown"/>.</p>
    /// </remarks>
    /// <exception cref="ArgumentNullException">Throws if the parameter is <c>null</c>.</exception>
    public static /*SaveFormat*/int extensionToSaveFormat(String extension)
    {
        if (extension == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "extension");

        return toSaveFormat(FileFormatCore.fromExt(extension));
    }

    /// <summary>
    /// Converts an Aspose.Words image type enumerated value into a file extension. The returned extension is a lower-case string with a leading dot.
    /// </summary>
    /// <exception cref="ArgumentException">Throws when cannot convert.</exception>
    public static String imageTypeToExtension(/*ImageType*/int imageType)
    {
        switch (imageType)
        {
            case ImageType.UNKNOWN:
            case ImageType.NO_IMAGE:
                throw new IllegalArgumentException("Cannot convert this image type to a file extension.");
            default:
                return "." + FileFormatCore.toExt(fromImageType(imageType));
        }
    }

    /// <summary>
    /// Below are most likely exceptions that could be thrown by our reader classes or the .NET Framework
    /// when attempting to load corrupted documents. We convert these to a more user friendly exception.
    /// </summary>
    static Exception convertLoadException(Exception e)
    {
        // This could be thrown by our readers when they detect invalid data and cannot recover.
        // But not all invalid data can be easily detected, therefore see all other exceptions below.
        if (e instanceof IllegalStateException)
            return new FileCorruptedException(e);

        // This could be thrown by in binary reader when pointer or length is causing
        // to read beyond end of file.
        if (e instanceof EOFException)
            return new FileCorruptedException(e);

        // This could be thrown by .NET when binary reader parses a document and accesses
        // a buffer or PLCF items beyond valid range.
        if (e instanceof IndexOutOfBoundsException)
            return new FileCorruptedException(e);

        // This could be thrown by .NET or by readers when invalid data is caught at function boundaries.
        if (e instanceof IllegalArgumentException)
            return new FileCorruptedException(e);

        // The file must be so screwed so our reader fails with null reference exception somewhere.
        if (e instanceof NullPointerException)
            return new FileCorruptedException(e);

        // ODT throws when some date formatting pattern is specified incorrectly.
        if (e instanceof NumberFormatException)
            return new FileCorruptedException(e);

        // If zip date is corrupted inside a document, we could have this.
        //Aspose.Zip is fully deleted from java, standard java.util.zip is used instead.
        if (e instanceof java.util.zip.ZipException)
            return new FileCorruptedException(e);

        return e;
    }

    /// <summary>
    /// The same as <see cref="ConvertLoadException(Exception)"/> but throws an exception instead of returning it.
    /// Workaround for WORDSCPP-622. C++ can't properly nest exceptions. If we return exception we are losing the
    /// information about the actual expection type.
    ///
    /// </summary>
    static void convertAndRethrowLoadException( Exception e) throws Exception
    {
        // This could be thrown by our readers when they detect invalid data and cannot recover.
        // But not all invalid data can be easily detected, therefore see all other exceptions below.
        if (e instanceof IllegalStateException)
            throw new FileCorruptedException(e);

        // This could be thrown by in binary reader when pointer or length is causing
        // to read beyond end of file.
        if (e instanceof EOFException)
            throw new FileCorruptedException(e);

        // This could be thrown by .NET when binary reader parses a document and accesses
        // a buffer or PLCF items beyond valid range.
        if (e instanceof IndexOutOfBoundsException)
            throw new FileCorruptedException(e);

        // This could be thrown by .NET or by readers when invalid data is caught at function boundaries.
        if (e instanceof IllegalArgumentException)
            throw new FileCorruptedException(e);

        // The file must be so screwed so our reader fails with null reference exception somewhere.
        if (e instanceof NullPointerException)
            throw new FileCorruptedException(e);

        // ODT throws when some date formatting pattern is specified incorrectly.
        if (e instanceof NumberFormatException)
            throw new FileCorruptedException(e);

        // If zip date is corrupted inside a document, we could have this.
        //Aspose.Zip is fully deleted from java, standard java.util.zip is used instead.
        if (e instanceof java.util.zip.ZipException)
            throw new FileCorruptedException(e);

        throw e;
    }

    //>>>>>>>> #region  Safe Conversions (No Exceptions)

    /// <summary>
    /// Returns true if <see cref="PageSetup"/> is applicable to the specified <see cref="LoadFormat"/>.
    /// </summary>
    /// <remarks>
    /// Used to check the applicability of PaperSize automatic setting by the specified DefaultEditingLanguage.
    /// So far done only for Html. In case of customers' requests other formats may be added.
    /// </remarks>
    static boolean isPageSetupApplicable(/*LoadFormat*/int format)
    {
        return format == LoadFormat.HTML;
    }

    static String saveFormatToString(/*SaveFormat*/int saveFormat)
    {
        return FileFormatCore.toString(fromSaveFormat(saveFormat));
    }

    static /*SaveFormat*/int toSaveFormat(/*FileFormat*/int fileFormat)
    {
        /*SaveFormat*/int saveFormat = 0;
        RefInt referenceToSaveFormat = new RefInt(saveFormat);
        boolean outRefCondition0 = msDictionary.tryGetValue(G_TO_SAVE_FORMAT, fileFormat, /*out*/ referenceToSaveFormat);
        saveFormat = referenceToSaveFormat.get();
        if (outRefCondition0)
            return saveFormat;
        else
            return SaveFormat.UNKNOWN;
    }

    static /*ImageType*/int toImageType(/*FileFormat*/int fileFormat)
    {
        /*ImageType*/int imageType = 0;
        RefInt referenceToImageType = new RefInt(imageType);
        boolean outRefCondition1 = msDictionary.tryGetValue(G_TO_IMAGE_TYPE, fileFormat, /*out*/ referenceToImageType);
        imageType = referenceToImageType.get();
        if (outRefCondition1)
            return imageType;
        else
            return ImageType.UNKNOWN;
    }

    static boolean isXmlBasedFormat(/*LoadFormat*/int format)
    {
        return
             (format == LoadFormat.DOCX) ||
             (format == LoadFormat.DOCM) ||
             (format == LoadFormat.DOTX) ||
             (format == LoadFormat.DOTM) ||
             (format == LoadFormat.FLAT_OPC) ||
             (format == LoadFormat.FLAT_OPC_MACRO_ENABLED) ||
             (format == LoadFormat.FLAT_OPC_TEMPLATE) ||
             (format == LoadFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED) ||
             (format == LoadFormat.WORD_ML);
    }

    /// <summary>
    /// Check if the save format is a fixed format.
    /// </summary>
    /// <param name="saveFormat">The save format to check.</param>
    /// <returns>True if it is a fixed format; otherwise, false.</returns>
    static boolean isFixedFormat(/*SaveFormat*/int saveFormat)
    {
        return saveFormat == SaveFormat.PDF
            || saveFormat == SaveFormat.WEB_P
            || saveFormat == SaveFormat.EMF
            || saveFormat == SaveFormat.TIFF
            || saveFormat == SaveFormat.PNG
            || saveFormat == SaveFormat.BMP
            || saveFormat == SaveFormat.JPEG
            || saveFormat == SaveFormat.SVG
            || saveFormat == SaveFormat.GIF
            || saveFormat == SaveFormat.EPS
            || saveFormat == SaveFormat.XPS
            || saveFormat == SaveFormat.PCL
            || saveFormat == SaveFormat.OPEN_XPS
            || saveFormat == SaveFormat.HTML_FIXED;
    }

    /// <summary>
    /// Check if the save format is an image format.
    /// </summary>
    /// <param name="saveFormat">The save format to check.</param>
    /// <returns>True if it is an image format; otherwise, false.</returns>
    static boolean isImageFormat(/*SaveFormat*/int saveFormat)
    {
        return saveFormat == SaveFormat.EMF
            || saveFormat == SaveFormat.WEB_P
            || saveFormat == SaveFormat.TIFF
            || saveFormat == SaveFormat.PNG
            || saveFormat == SaveFormat.BMP
            || saveFormat == SaveFormat.JPEG
            || saveFormat == SaveFormat.SVG
            || saveFormat == SaveFormat.GIF
            || saveFormat == SaveFormat.EPS;
    }

    static /*FileFormat*/int fromLoadFormat(/*LoadFormat*/int loadFormat)
    {
        return DictionaryExtension.getValueOrDefault(G_FROM_LOAD_FORMAT, loadFormat, FileFormat.UNKNOWN);
    }

    static /*FileFormat*/int fromSaveFormat(/*SaveFormat*/int saveFormat)
    {
        return DictionaryExtension.getValueOrDefault(G_FROM_SAVE_FORMAT, saveFormat, FileFormat.UNKNOWN);
    }

    static /*FileFormat*/int fromImageType(/*ImageType*/int imageType)
    {
        return DictionaryExtension.getValueOrDefault(G_FROM_IMAGE_TYPE, imageType, FileFormat.UNKNOWN);
    }

    private static /*LoadFormat*/int toLoadFormat(/*FileFormat*/int fileFormat)
    {
        return DictionaryExtension.getValueOrDefault(G_TO_LOAD_FORMAT, fileFormat, LoadFormat.UNKNOWN);
    }

    private static void addMap(/*FileFormat*/int fileFormat, /*SaveFormat*/int saveFormat, /*LoadFormat*/int loadFormat, /*ImageType*/int imageType)
    {
        if (saveFormat != SaveFormat.UNKNOWN)
        {
            msDictionary.add(G_TO_SAVE_FORMAT, fileFormat, saveFormat);
            msDictionary.add(G_FROM_SAVE_FORMAT, saveFormat, fileFormat);
        }

        if (loadFormat != LoadFormat.UNKNOWN)
        {
            if (!G_TO_LOAD_FORMAT.containsKey(fileFormat))
                msDictionary.add(G_TO_LOAD_FORMAT, fileFormat, loadFormat);
            msDictionary.add(G_FROM_LOAD_FORMAT, loadFormat, fileFormat);
        }

        if (imageType != ImageType.UNKNOWN)
        {
            msDictionary.add(G_TO_IMAGE_TYPE, fileFormat, imageType);
            msDictionary.add(G_FROM_IMAGE_TYPE, imageType, fileFormat);
        }
    }

    //<<<<<<<< #endregion 

    private static /*final*/ HashMap<Integer, Integer> G_FROM_LOAD_FORMAT = new HashMap<Integer, Integer>();
    private static /*final*/ HashMap<Integer, Integer> G_FROM_SAVE_FORMAT = new HashMap<Integer, Integer>();
    private static /*final*/ HashMap<Integer, Integer> G_FROM_IMAGE_TYPE = new HashMap<Integer, Integer>();

    private static /*final*/ HashMap<Integer, Integer> G_TO_LOAD_FORMAT = new HashMap<Integer, Integer>();
    private static /*final*/ HashMap<Integer, Integer> G_TO_SAVE_FORMAT = new HashMap<Integer, Integer>();
    private static /*final*/ HashMap<Integer, Integer> G_TO_IMAGE_TYPE = new HashMap<Integer, Integer>();

    static/* FileFormatUtil()*/
    {
        addMap(FileFormat.DOC, SaveFormat.DOC, LoadFormat.DOC, ImageType.UNKNOWN);
        addMap(FileFormat.DOC, SaveFormat.UNKNOWN, LoadFormat.AUTO, ImageType.UNKNOWN);
        addMap(FileFormat.DOT, SaveFormat.DOT, LoadFormat.DOT, ImageType.UNKNOWN);
        addMap(FileFormat.DOCX, SaveFormat.DOCX, LoadFormat.DOCX, ImageType.UNKNOWN);
        addMap(FileFormat.DOCX, SaveFormat.UNKNOWN, (/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML, ImageType.UNKNOWN);
        addMap(FileFormat.DOCM, SaveFormat.DOCM, LoadFormat.DOCM, ImageType.UNKNOWN);
        addMap(FileFormat.DOTX, SaveFormat.DOTX, LoadFormat.DOTX, ImageType.UNKNOWN);
        addMap(FileFormat.DOTM, SaveFormat.DOTM, LoadFormat.DOTM, ImageType.UNKNOWN);

        addMap(FileFormat.FLAT_OPC, SaveFormat.FLAT_OPC, LoadFormat.FLAT_OPC, ImageType.UNKNOWN);
        addMap(FileFormat.FLAT_OPC_MACRO_ENABLED, SaveFormat.FLAT_OPC_MACRO_ENABLED, LoadFormat.FLAT_OPC_MACRO_ENABLED,
            ImageType.UNKNOWN);
        addMap(FileFormat.FLAT_OPC_TEMPLATE, SaveFormat.FLAT_OPC_TEMPLATE, LoadFormat.FLAT_OPC_TEMPLATE, ImageType.UNKNOWN);
        addMap(FileFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED, SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED,
            LoadFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED, ImageType.UNKNOWN);

        addMap(FileFormat.RTF, SaveFormat.RTF, LoadFormat.RTF, ImageType.UNKNOWN);
        addMap(FileFormat.WORD_ML, SaveFormat.WORD_ML, LoadFormat.WORD_ML, ImageType.UNKNOWN);

        addMap(FileFormat.HTML, SaveFormat.HTML, LoadFormat.HTML, ImageType.UNKNOWN);
        addMap(FileFormat.HTML_FIXED, SaveFormat.HTML_FIXED, LoadFormat.UNKNOWN, ImageType.UNKNOWN);
        addMap(FileFormat.MHTML, SaveFormat.MHTML, LoadFormat.MHTML, ImageType.UNKNOWN);
        addMap(FileFormat.MOBI, SaveFormat.MOBI, LoadFormat.MOBI, ImageType.UNKNOWN);
        addMap(FileFormat.CHM, SaveFormat.UNKNOWN, LoadFormat.CHM, ImageType.UNKNOWN);
        addMap(FileFormat.AZW_3, SaveFormat.AZW_3, LoadFormat.AZW_3, ImageType.UNKNOWN);

        addMap(FileFormat.ODT, SaveFormat.ODT, LoadFormat.ODT, ImageType.UNKNOWN);
        addMap(FileFormat.OTT, SaveFormat.OTT, LoadFormat.OTT, ImageType.UNKNOWN);

        addMap(FileFormat.PDF, SaveFormat.PDF, LoadFormat.PDF, ImageType.UNKNOWN);
        addMap(FileFormat.PS, SaveFormat.PS, LoadFormat.UNKNOWN, ImageType.UNKNOWN);
        addMap(FileFormat.PCL, SaveFormat.PCL, LoadFormat.UNKNOWN, ImageType.UNKNOWN);
        addMap(FileFormat.XPS, SaveFormat.XPS, LoadFormat.UNKNOWN, ImageType.UNKNOWN);
        addMap(FileFormat.OPEN_XPS, SaveFormat.OPEN_XPS, LoadFormat.UNKNOWN, ImageType.UNKNOWN);
        addMap(FileFormat.XAML_FIXED, SaveFormat.XAML_FIXED, LoadFormat.UNKNOWN, ImageType.UNKNOWN);

        addMap(FileFormat.SVG, SaveFormat.SVG, LoadFormat.UNKNOWN, ImageType.UNKNOWN);
        addMap(FileFormat.EPUB, SaveFormat.EPUB, LoadFormat.EPUB, ImageType.UNKNOWN);
        addMap(FileFormat.TXT, SaveFormat.TEXT, LoadFormat.TEXT, ImageType.UNKNOWN);
        addMap(FileFormat.MARKDOWN, SaveFormat.MARKDOWN, LoadFormat.MARKDOWN, ImageType.UNKNOWN);
        addMap(FileFormat.XAML_FLOW, SaveFormat.XAML_FLOW, LoadFormat.UNKNOWN, ImageType.UNKNOWN);
        addMap(FileFormat.TIFF, SaveFormat.TIFF, LoadFormat.UNKNOWN, ImageType.UNKNOWN);
        addMap(FileFormat.GIF, SaveFormat.GIF, LoadFormat.UNKNOWN, ImageType.GIF);
        addMap(FileFormat.PNG, SaveFormat.PNG, LoadFormat.UNKNOWN, ImageType.PNG);
        addMap(FileFormat.BMP, SaveFormat.BMP, LoadFormat.UNKNOWN, ImageType.BMP);
        addMap(FileFormat.JPEG, SaveFormat.JPEG, LoadFormat.UNKNOWN, ImageType.JPEG);
        addMap(FileFormat.PICT, SaveFormat.UNKNOWN, LoadFormat.UNKNOWN, ImageType.PICT);
        addMap(FileFormat.WMF, SaveFormat.UNKNOWN, LoadFormat.UNKNOWN, ImageType.WMF);
        addMap(FileFormat.XAML_FLOW_PACK, SaveFormat.XAML_FLOW_PACK, LoadFormat.UNKNOWN, ImageType.UNKNOWN);
        addMap(FileFormat.EMF, SaveFormat.EMF, LoadFormat.UNKNOWN, ImageType.EMF);
        addMap(FileFormat.EPS, SaveFormat.EPS, LoadFormat.UNKNOWN, ImageType.EPS);
        addMap(FileFormat.XLSX, SaveFormat.XLSX, LoadFormat.UNKNOWN, ImageType.UNKNOWN);
        addMap(FileFormat.DOCLING, SaveFormat.DOCLING, LoadFormat.UNKNOWN, ImageType.UNKNOWN);
        addMap(FileFormat.WEB_P, SaveFormat.WEB_P, LoadFormat.UNKNOWN, ImageType.WEB_P);
        addMap(FileFormat.MS_WORKS, SaveFormat.UNKNOWN, LoadFormat.MS_WORKS, ImageType.UNKNOWN);
    }
}

