// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2010 by Viktor Sazhaev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Guid;
import com.aspose.UriUtil;
import com.aspose.ms.System.IO.Directory;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Helps to write subsidiary resources referenced in the document model into non-native formats (primarily HTML and related).
/// </summary>
class ResourceWriterBase
{
    /// <summary>
    /// Call this before building the document.
    /// </summary>
    /// <param name="fileName">Pass the name of the file you want to save to.
    /// Pass null if saving into a stream. Used to extract path and base name for resources.</param>
    /// <param name="subsidiaryContentPartCollector">Can be null. If not null, then we collect all subsidiary
    /// parts to be written separately or externally.</param>
    /// <param name="exportResourceFolder">ExportResourceFolder</param>
    /// <param name="exportResourceFolderAlias">ExportResourceFolderAlias</param>
    /// <param name="messageWhenNoOutputFolder">Message that is put into exception when we cannot write
    /// files because we have no clearly specified output directory.</param>
    /// <param name="doNotUseResourceFolderForResourceUri">
    /// Specifies that <paramref name="exportResourceFolderAlias"/> shouldn't be used to construct resource URI.
    /// This is needed when linked resources (CSS and fonts, for example) are in the same folder.</param>
    /// <param name="isMakeResourceFolderAbsolute">
    /// If exportResourcesFolder is relative, it will be expanded using the document folder as a base.</param>
    ResourceWriterBase(
        String fileName,
        SubsidiaryContentPartCollector subsidiaryContentPartCollector,
        String exportResourceFolder,
        String exportResourceFolderAlias,
        String messageWhenNoOutputFolder,
        boolean doNotUseResourceFolderForResourceUri,
        boolean isMakeResourceFolderAbsolute) throws Exception
    {
        mSubsidiaryContentPartCollector = subsidiaryContentPartCollector;
        mMessageWhenNoOutputFolder = messageWhenNoOutputFolder;

        if (mSubsidiaryContentPartCollector != null)
        {
            // In this mode we just collect subsidiary parts not writing them anywhere. File paths are empty.
            mResourceFolder = "";
            mResourceFolderAlias = "";
            mResourceBaseName = "";
        }
        else
        {
            // Whether to write document to stream (fileNameSpecified==false) or to file
            boolean fileNameSpecified = StringUtil.hasChars(fileName);
            boolean exportFolderSpecified = StringUtil.hasChars(exportResourceFolder);
            boolean exportAliasSpecified = StringUtil.hasChars(exportResourceFolderAlias);

            // If exportResourceFolder is specified then always use it, it has priority over current document file name.
            // Next, when file name is available, resources will be saved in the same folder as the document.
            // If nothing is given don't choose temp directory: in this case either streams via resource saving
            // event (maybe in the future) must be provided or exception will be thrown.
            if (exportFolderSpecified)
            {
                mResourceFolder = exportResourceFolder;
                if (!Path.isPathRooted(mResourceFolder) && isMakeResourceFolderAbsolute)
                {
                    String saveFileDirectoryName = (fileNameSpecified)
                        ? Path.getDirectoryName(Path.getFullPath(fileName))
                        : "";
                    if (StringUtil.hasChars(saveFileDirectoryName))
                    {
                        mResourceFolder = Path.combine(saveFileDirectoryName, mResourceFolder);
                    }
                }
            }
            else if (fileNameSpecified)
            {
                mResourceFolder = Path.getDirectoryName(Path.getFullPath(fileName));
            }
            else
            {
                mResourceFolder = "";
            }

            // If alias is specified then use it. Alias can consist of one dot ('.') or start with dot+slash. Remove this.
            // Next, when exportResourceFolder is specified explicitly write the same.
            // Defaults to nothing if directory comes from output file name or not available: if noting but output file
            // name is specified then client code expects resources in the same directory and short names in output.
            if (!doNotUseResourceFolderForResourceUri && exportAliasSpecified)
            {
                mResourceFolderAlias = G_MATCH_DOT_SLASHES.replace(exportResourceFolderAlias, "");
            }
            else if (!doNotUseResourceFolderForResourceUri && exportFolderSpecified)
            {
                mResourceFolderAlias = exportResourceFolder;
            }
            else
            {
                mResourceFolderAlias = "";
            }

            // The resources will be named after the document file name if available.
            // Otherwise we will save them into files called like "Aspose.Words.xxxxxxxxxx.nnn.ext".
            // So in the most cases you can save resources from different documents to the same directory.
            // Now using guid to generate file name instead of ticks to make file names unique.
            mResourceBaseName = (fileNameSpecified)
                                    ? Path.getFileNameWithoutExtension(fileName)
                                    : msString.format("Aspose.Words.{0}", Guid.newGuid());
        }
    }

    /// <summary>
    /// Returns full filename including the folder where the resources are saved.
    /// </summary>
    String getFullFileName(String fileName) throws Exception
    {
        return Path.combine(mResourceFolder, fileName);
    }

    /// <summary>
    /// Maps a physical file name into a URI that can be written into HTML file.
    /// </summary>
    String mapResourceFileName(String fileName) throws Exception
    {
        if (UriUtil.isHrefWithScheme(fileName))
            return fileName;

        // RK Do not prepend file://. I don't think it is necessary, besides,
        // it screws up Aspose.Network so images in email appear as attachments.
        return UriUtil.combineHref(mResourceFolderAlias, Path.getFileName(fileName));
    }

    /// <summary>
    /// Checks whether we can write subsidiaries to disk.
    /// </summary>
    void createFolderIfNeeded() throws Exception
    {
        if (!StringUtil.hasChars(mResourceFolder))
            throw new IllegalStateException(mMessageWhenNoOutputFolder);

        // WORDSNET-14590 Create folder if it doesn't exist.
        if (!Directory.exists(mResourceFolder))
            Directory.createDirectory(mResourceFolder);
    }

    /// <summary>
    /// Handler responsible for storing subsidiary parts when writing container formats.
    /// </summary>
    protected SubsidiaryContentPartCollector getSubsidiaryContentPartCollector() { return mSubsidiaryContentPartCollector; }

    /// <summary>
    /// The base (common) part of all resource names exported from this file.
    /// </summary>
    protected String getResourceBaseName() { return mResourceBaseName; }

    /// <summary>
    /// Handler responsible for storing subsidiary parts when writing container formats.
    /// </summary>
    private /*final*/ SubsidiaryContentPartCollector mSubsidiaryContentPartCollector;
    /// <summary>
    /// Message to throw in an exception when unable to write to disk.
    /// Should be dependent on whether we write HTML or PDF and kind of resource.
    /// </summary>
    private /*final*/ String mMessageWhenNoOutputFolder;
    /// <summary>
    /// Folder where the resources are saved.
    /// </summary>
    private /*final*/ String mResourceFolder;
    /// <summary>
    /// Name of the folder used when constructing the resources URI that is written into the file.
    /// </summary>
    private /*final*/ String mResourceFolderAlias;
    /// <summary>
    /// The base (common) part of all resource names exported from this file.
    /// Maybe used differently for some resources. Basic idea is providing distinguishability.
    /// </summary>
    private /*final*/ String mResourceBaseName;

    /// <summary>
    /// Matches the lonely '.', either './' or '.\' at the beginning. Removing this from the alias.
    /// </summary>
    private static /*final*/ Regex G_MATCH_DOT_SLASHES = new Regex("^\\.($|[/\\\\])", RegexOptions.COMPILED);
}

