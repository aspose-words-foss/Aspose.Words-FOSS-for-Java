// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/08/2011 by Kamimura Andrey

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ImageUtil;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ArrayUtil;
import com.aspose.Debug;
import com.aspose.ImageSizeCore;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.StringUtil;
import com.aspose.UriUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Stream;
import com.aspose.SystemPal;
import com.aspose.StreamUtil;
import java.util.HashMap;
import com.aspose.words.ref.Ref;


/// <summary>
/// <para>Loads external and local resources and caches them. Provides universal access to a resource by its Uri
/// in spite of whether it's already loaded or not yet.</para>
/// <para>If specified, uses a callback implementing <see cref="IResourceLoadingCallback"/> interface to resolve
/// Uri of a resource to byte array or string with its data.</para>
/// </summary>
class HtmlResourceLoader
{
    /// <summary>
    /// Ctor.
    /// </summary>
    HtmlResourceLoader()
    {
        // Empty constructor. Nothing to set up.
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="resourceLoadingCallback">Callback used to resolve Uri of a resource
    /// to byte array or string with its data. Can be <c>null</c>.</param>
    /// <param name="warningCallback">Warning callback processor.</param>
    HtmlResourceLoader(IResourceLoadingCallback resourceLoadingCallback, IWarningCallback warningCallback)
    {
        mResourceLoadingCallback = resourceLoadingCallback;
        mWarningCallback = warningCallback;
    }

    /// <summary>
    /// <para>Returns byte array with image data.</para>
    /// <para>If downloading callback was not specified or was specified and has returned <see cref=
    /// "ResourceLoadingAction.Default"/> for an image but the image cannot be downloaded,
    /// returns standard 'no image' picture.</para>
    /// <para>If downloading callback was specified and it has returned <see cref=
    /// "ResourceLoadingAction.Skip"/> for an image, returns <c>null</c>.</para>
    /// </summary>
    byte[] loadImage(String baseUri, String originalUri) throws Exception
    {
        return loadImage(baseUri, originalUri, true);
    }

    /// <summary>
    /// <para>Returns byte array with image data.</para>
    /// <para>If downloading callback was not specified or was specified and has returned <see cref=
    /// "ResourceLoadingAction.Default"/> for an image but the image cannot be downloaded,
    /// returns either the standard 'no image' picture or <c>null</c> depending on
    /// the <paramref name="returnNoImageIfMissing"/> parameter value.</para>
    /// <para>If downloading callback was specified and it has returned <see cref=
    /// "ResourceLoadingAction.Skip"/> for an image, returns <c>null</c>.</para>
    /// </summary>
    byte[] loadImage(String baseUri, String originalUri, boolean returnNoImageIfMissing) throws Exception
    {
        ResourceLoadResult result = load(baseUri, originalUri, ResourceType.IMAGE);

        // Skipped images will be interpreted as external links.
        if (result.getLoadingAction() == ResourceLoadingAction.SKIP)
            return null;

        if (HtmlImageUtil.isInsertableImage(result.getResourceBytes()))
            return result.getResourceBytes();

        // WORDSNET-2579 Try to load using the direct link for corrupted images.
        if (result.getLoadingAction() == ResourceLoadingAction.DEFAULT)
        {
            byte[] reloadedImageBytes = reloadEmbeddedImage(baseUri, originalUri);
            if (HtmlImageUtil.isInsertableImage(reloadedImageBytes))
            {
                return reloadedImageBytes;
            }
        }

        return returnNoImageIfMissing
            ? ImageUtil.getNoImageBytes()
            : null;
    }

    /// <summary>
    /// <para>Returns byte array with font binary data.</para>
    /// <para>If downloading callback was not specified or was specified and has returned <see cref=
    /// "ResourceLoadingAction.Default"/> or <see cref="ResourceLoadingAction.Skip"/> for a font
    /// but the font cannot be downloaded, returns <c>null</c>.</para>
    /// </summary>
    byte[] loadFont(String baseUri, String originalUri) throws Exception
    {
        ResourceLoadResult result = load(baseUri, originalUri, ResourceType.FONT);
        return result.getResourceBytes();
    }

    /// <summary>
    /// <para>Returns byte array with HTML document data.</para>
    /// <para>If downloading callback was not specified or was specified and has returned <see cref=
    /// "ResourceLoadingAction.Default"/> or <see cref="ResourceLoadingAction.Skip"/> for an HTML document
    /// but the HTML document cannot be downloaded, returns <c>null</c>.</para>
    /// </summary>
    byte[] loadHtmlDocument(String baseUri, String originalUri) throws Exception
    {
        ResourceLoadResult result = load(baseUri, originalUri, ResourceType.DOCUMENT);
        return result.getResourceBytes();
    }

    /// <summary>
    /// <para>Returns string with CSS style sheet. Returns <c>null</c> if CSS cannot be downloaded.</para>
    /// <para>Also returns <c>null</c> if downloading callback was specified and it has returned
    /// <see cref="ResourceLoadingAction.Skip"/> for the CSS resource.</para>
    /// </summary>
    String loadCss(String baseUri, String originalUri) throws Exception
    {
        ResourceLoadResult result = load(baseUri, originalUri, ResourceType.CSS_STYLE_SHEET);
        return (result.getResourceBytes() != null)
            ? Encoding.getUTF8().getString(result.getResourceBytes())
            : null;
    }

    /// <summary>
    /// Adds bytes of an embedded resource to the cache. Used by MHTML import.
    /// </summary>
    void cacheEmbeddedResource(String originalUri, byte[] data)
    {
        mEmbeddedResourceCache.put(originalUri, data);
    }

    /// <summary>
    /// Updates a specified shape depending on provided data in corresponding ResourceLoadingCallback of the document.
    /// </summary>
    void updateShape(Shape shape) throws Exception
    {
        if (!shape.getImageData().isLinkOnly())
            return;

        String baseUri = "";
        String originalUri = shape.getImageData().getSourceFullName();

        ResourceLoadResult result = load(baseUri, originalUri, ResourceType.IMAGE);
        if (!ArrayUtil.hasData(result.getResourceBytes()))
            return;

        switch (result.getLoadingAction())
        {
            case ResourceLoadingAction.USER_PROVIDED:
            {
                // The customer has provided an image or the image is embedded as a data URL,
                // so lets insert it into the shape.
                shape.getImageData().setImageBytes(result.getResourceBytes());
                break;
            }
            case ResourceLoadingAction.DEFAULT:
            {
                // By default we leave shape image as external link and just update its size from the actual image data.
                break;
            }
            case ResourceLoadingAction.SKIP:
            {
                // The customer wants to skip load image, so do nothing.
                return;
            }
            default:
                Debug.fail("Never invoked!");
                return;
        }

        ImageSizeCore size = ImageUtil.getImageSize(result.getResourceBytes());
        // WORDSNET-22219 The ImageSizeCore.Width/Height are in pixels, but in shape they should be in points.
        double widthInPoints = ConvertUtil.pixelToPoint(size.getWidth(), size.getHorizontalResolution());
        double heightInPoints = ConvertUtil.pixelToPoint(size.getHeight(), size.getVerticalResolution());
        shape.setSizeSafe(widthInPoints, heightInPoints);
    }

    /// <summary>
    /// The number of milliseconds to wait before the web request times out. The default value is 100000 milliseconds (100 seconds).
    /// </summary>
    int getWebRequestTimeout() { return mWebRequestTimeout; }
    void setWebRequestTimeout(int value) { mWebRequestTimeout = value; }

    /// <summary>
    /// <para>Removes embedded image (if any) from the cache and loads image by Uri provided.
    /// Embedded images are those which are embedded into imported MHTML document and
    /// are added to the cache by <see cref="CacheEmbeddedResource"/>.</para>
    /// <para>Used if embedded image is broken to try download it from external source.</para>
    /// <para>If an image is not embedded, it isn't removed from the cache and the method returns <c>null</c>.</para>
    /// </summary>
    /// <returns>Bytes of the image in case it has been reloaded by the URI. Otherwise, <c>null</c>.</returns>
    private byte[] reloadEmbeddedImage(String baseUri, String originalUri) throws Exception
    {
        if (msDictionary.remove(mEmbeddedResourceCache, originalUri))
        {
            ResourceLoadResult loadResult = load(baseUri, originalUri, ResourceType.IMAGE);
            return loadResult.getResourceBytes();
        }
        return null;
    }

    /// <summary>
    /// Loads resource with using user defined callback interface.
    /// </summary>
    private ResourceLoadResult load(String baseUri, String originalUri, /*ResourceType*/int resourceType) throws Exception
    {
        Debug.assertTrue(originalUri != null);

        // WORDSNET-23677 Empty URIs cannot reference anything and we shouldn't process them.
        if (!StringUtil.hasChars(originalUri))
        {
            return new ResourceLoadResult(null, ResourceLoadingAction.DEFAULT);
        }

        // First, check if the URI is a data URL. Resources embedded as data URLs are not considered external
        // and they should be processed internally, without invoking the resource loading callback.
        ResourceLoadResult dataUrlLoadResult = parseDataUrl(originalUri, resourceType);
        if (dataUrlLoadResult != null)
        {
            return dataUrlLoadResult;
        }

        String absoluteUri = UriUtil.constructUnescapedAbsoluteUri(baseUri, originalUri);

        byte[] resourceBytes = null;
        /*ResourceLoadingAction*/int action = ResourceLoadingAction.DEFAULT;

        // Invoke the resource loading callback, if provided by the user.
        if (mResourceLoadingCallback != null)
        {
            ResourceLoadingArgs args = new ResourceLoadingArgs(absoluteUri, originalUri, resourceType);
            action = mResourceLoadingCallback.resourceLoading(args);

            // The user could change args.Uri in his callback.
            if (!msString.equals(args.getUri(), absoluteUri))
            {
                absoluteUri = args.getUri();

                // And the new URI privided by the user may be a data URL.
                // We support this scenario and load data from such URLs.
                if (action == ResourceLoadingAction.DEFAULT)
                {
                    dataUrlLoadResult = parseDataUrl(args.getUri(), resourceType);
                    if (dataUrlLoadResult != null)
                    {
                        return dataUrlLoadResult;
                    }
                }
            }

            if (action == ResourceLoadingAction.USER_PROVIDED)
            {
                resourceBytes = args.getData();
            }
        }

        if (action == ResourceLoadingAction.DEFAULT)
        {
            resourceBytes = loadFromCacheOrUri(originalUri, absoluteUri, resourceType);
        }

        // If loading of the resource has been skipped, the resource data array must be null.
        Debug.assertTrue(!((action == ResourceLoadingAction.SKIP) && (resourceBytes != null)));

        // WORDSNET-15038 Try to unzip an image blob, as it can come in a zip-archived format(.wmz or .emz).
        if (resourceType == ResourceType.IMAGE)
            resourceBytes = VmlUtil.unpackImage(resourceBytes);

        return new ResourceLoadResult(resourceBytes, action);
    }

    private ResourceLoadResult parseDataUrl(
        String uri,
        /*ResourceType*/int resourceType)
    {
        // From the calling code's point of view, data embedded as data URLs are similar to data provided by user code
        // in the way that they are not loaded from external resources.
        final /*ResourceLoadingAction*/int DATA_URL_ACTION = ResourceLoadingAction.USER_PROVIDED;

        DataUrl dataUrl = DataUrl.parse(uri);

        // An instance with no data is returned as an indication that the parser has recognized the URI
        // as a data URL but couldn't parse it further. This is different from the case where a URI is
        // not recognized as a data URL and should be processed somewhere else.
        if (dataUrl != null)
        {
            if (dataUrl.getData().length == 0)
            {
                warnAboutResourceLoadingError(uri);
                return new ResourceLoadResult(null, DATA_URL_ACTION);
            }

            byte[] dataBytes;

            // Image resources are normally binary data that should ignore charset information, except for SVG images,
            // that are textual data. All other resource types are textual data as well.
            // WORDSNET-19654 Added support for SVG embedded in data URLs.
            byte[] dataInUtf8 = dataUrl.getDataInUtf8();
            dataBytes = ((resourceType == ResourceType.IMAGE) && !ImageUtil.isSvg(dataInUtf8))
                ? dataUrl.getData()
                : dataInUtf8;

            return new ResourceLoadResult(dataBytes, DATA_URL_ACTION);
        }

        return null;
    }

    /// <summary>
    /// <para>Takes a resource from the cache. If not found, downloads it by Uri provided.</para>
    /// <para>If the resource cannot be downloaded never tries to download it again on subsequent requests.</para>
    /// </summary>
    /// <param name="originalUri">First name to search the resource in the cache.
    /// Used for storing resource embedded to MHTML.</param>
    /// <param name="absoluteUri">Second name to search the resource in the cache.</param>
    /// <param name="resourceType">Loading rules are different for resources of different types.</param>
    private byte[] loadFromCacheOrUri(String originalUri, String absoluteUri, /*ResourceType*/int resourceType) throws Exception
    {
        byte[] data = null;
        Ref<byte[]> referenceToData = new Ref<byte[]>(data);
        boolean outRefCondition0 = msDictionary.tryGetValue(mEmbeddedResourceCache, originalUri, /*out*/ referenceToData);
        data = referenceToData.get();
        if (outRefCondition0)
        {
            return data;
        }

        referenceToData.set(data);
        boolean outRefCondition1 = msDictionary.tryGetValue(mExternalResourceCache, absoluteUri, /*out*/ referenceToData);
        data = referenceToData.get();
        if (outRefCondition1)
        {
            return data;
        }

        data = loadResourceByUri(absoluteUri, resourceType);
        mExternalResourceCache.put(absoluteUri, data);
        return data;
    }

    /// <summary>
    /// Loads array of bytes from specified uri. Make specific handling of various resource types.
    /// </summary>
    private byte[] loadResourceByUri(String uri, /*ResourceType*/int resourceType) throws Exception
    {
        boolean canLoad;
        switch (resourceType)
        {
            case ResourceType.CSS_STYLE_SHEET:
            case ResourceType.DOCUMENT:
            case ResourceType.FONT:
            {
                canLoad = true;
                break;
            }
            case ResourceType.IMAGE:
            {
                canLoad = !UriUtil.isCid(uri);
                break;
            }
            default:
            {
                Debug.fail("Never invoked!");
                canLoad = false;
                break;
            }
        }
        return canLoad
            ? loadBytesByUri(uri)
            : null;
    }

    /// <summary>
    /// Loads array of bytes from specified uri.
    /// </summary>
    private byte[] loadBytesByUri(String uri) throws Exception
    {
        byte[] loadedData = null;
        try
        {
            Stream stream = SystemPal.openStreamFromHref(uri, mWebRequestTimeout);
            try /*JAVA: was using*/
            {
                if (stream.getLength() > 0)
                {
                    loadedData = StreamUtil.copyStreamToByteArray(stream);
                }
            }
            finally { if (stream != null) stream.close(); }
        }
        catch(Exception e)
        {
            // Suppress data loading exceptions.
        }

        // WORDSNET-14515 Issue a WarningType.DataLoss warning in case a resource is not loaded.
        if (loadedData == null)
        {
            warnAboutResourceLoadingError(uri);
        }

        return loadedData;
    }

    private void warnAboutResourceLoadingError(String uri)
    {
        if (mWarningCallback != null)
        {
            // URIs may be very long (especially if they are data URLs) so we need to make sure warning messages remain
            // relatively short. The limit has been chosen arbitrarily.
            final int URI_LENGTH_LIMIT = 256;
            if (uri.length() > URI_LENGTH_LIMIT)
            {
                uri = uri.substring((0), (0) + (URI_LENGTH_LIMIT)) + "...";
            }

            mWarningCallback.warning(new WarningInfo(
                WarningType.DATA_LOSS,
                WarningSource.HTML,
                "Couldn't load a resource from '" + uri + "'."));
        }
    }

    private /*final*/ IWarningCallback mWarningCallback;

    private /*final*/ IResourceLoadingCallback mResourceLoadingCallback;

    /// <summary>
    /// <para>Contains data of all resources previously requested. Key is the absolute URI of the resource (a string).
    /// Value is a byte array.</para>
    /// <para>Value is <c>null</c> or standard 'no image' picture for resources which cannot be downloaded.</para>
    /// </summary>
    private /*final*/ HashMap<String, byte[]> mExternalResourceCache = new HashMap<String, byte[]>();

    /// <summary>
    /// <para>Contains data for all resources embedded to MHTML. Key is the original URI of the resource (a string,
    /// as specified in the source document). Value is a byte array.</para>
    /// </summary>
    private /*final*/ HashMap<String, byte[]> mEmbeddedResourceCache = new HashMap<String, byte[]>();

    private int mWebRequestTimeout = 100000;
}

