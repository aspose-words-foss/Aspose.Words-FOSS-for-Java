// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/09/2016 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msUri;
import com.aspose.StringUtil;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.msString;
import com.aspose.SystemPal;
import com.aspose.StringToObjDictionary;
import com.aspose.BitmapPal;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.FileFormat;


/// <summary>
/// Adds a new web video to the document.
/// </summary>
class VideoInserter
{
    VideoInserter(DocumentBuilder documentBuilder)
    {
        mDocumentBuilder = documentBuilder;
    }

    /// <summary>
    /// Inserts online video from the well-known (supported) video resources to the document.
    /// </summary>
    Shape insertFromUrl(
        String videoUrl,
        /*RelativeHorizontalPosition*/int horzPos,
        double left,
        /*RelativeVerticalPosition*/int vertPos,
        double top,
        double width,
        double height,
        /*WrapType*/int wrapType) throws Exception
    {
        // Vimeo and YouTube support discovery of the oEmbed URL (http://oembed.com/). Each page that has a video will have
        // two link tags with types application/json+oembed and application/xml+oembed containing the URLs for
        // JSON and XML oEmbed responses. This response will be used to get embedHtml and thumbnailUrl.
        // Please follow this link to get more information https://developer.vimeo.com/apis/oembed

        String oEmbedApiEndpoint = "";
        String thumbnailUrl = "";
        String embedHtml = "";
        byte[] imgBytes = null;

        // Get the domain name.
        msUri url = new msUri(videoUrl);
        String domainName = url.getHost();

        // Get oEmbed Api endpoint by the domain name.
        if (gSupportedVideoHosts.containsKey(domainName))
            oEmbedApiEndpoint = gSupportedVideoHosts.get(domainName);

        if (StringUtil.hasChars(oEmbedApiEndpoint))
        {
            Stream videoStream = null;
            try
            {
                // Load oEmbed response stream.
                String href = msString.format(oEmbedApiEndpoint + FORMAT_REQUEST, videoUrl, PLAYER_WIDTH, PLAYER_HEIGHT);
                videoStream = SystemPal.openStreamFromHref(href);
            }
            catch (Exception ex)
            {
                WarningUtil.warn(getDocument().getWarningCallback(), WarningType.UNEXPECTED_CONTENT, WarningSource.UNKNOWN, ex.getMessage());
            }

            if (videoStream != null)
            {
                NrxXmlReader xmlReader = new NrxXmlReader(videoStream);

                while (xmlReader.readChild("oembed"))
                {
                    switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                    {
                        case /*"html"*/0:
                            embedHtml = xmlReader.readString();
                            break;
                        case /*"thumbnail_url"*/1:
                            thumbnailUrl = xmlReader.readString();
                            break;
                        default:
                            // Other tags are not required, ignore.
                            xmlReader.ignoreElement();
                            break;
                    }
                }
                videoStream.close();
            }
        }
        else
        {
            WarningUtil.warn(getDocument().getWarningCallback(), WarningType.UNEXPECTED_CONTENT, WarningSource.UNKNOWN,
                msString.format(WARNING_MESSAGE, domainName));
        }

        if (StringUtil.hasChars(thumbnailUrl))
            imgBytes = ImageDataUtil.loadImageBytes(thumbnailUrl, getDocument());

        return insertFromVideoEmbedCode(videoUrl, embedHtml, imgBytes, horzPos, left, vertPos, top, width, height, wrapType);
    }

    Shape insertFromVideoEmbedCode(
       String videoUrl,
       String videoEmbedCode,
       byte[] thumbnailImageBytes,
       /*RelativeHorizontalPosition*/int horzPos,
       double left,
       /*RelativeVerticalPosition*/int vertPos,
       double top,
       double width,
       double height,
       /*WrapType*/int wrapType) throws Exception
    {
        // Document have to be optimized for MS Word 2013 to show video.
        getDocument().getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2013);

        // Mark the document as has drawing extensions because there is wp15:webVideoPr.
        OoxmlComplianceInfo.markAsHasDrawingExtensions(getDocument());

        // Create Dml shape.
        Shape shape = new Shape(getDocument(), ShapeMarkupLanguage.DML);

        shape.setRunPr(mDocumentBuilder.getRunPrCopy());
        shape.setRelativeHorizontalPosition(horzPos);
        shape.setRelativeVerticalPosition(vertPos);
        shape.setLeft(left);
        shape.setTop(top);
        shape.setWrapType(wrapType);

        shape.setShapeType(ShapeType.IMAGE);

        // Set clickable shape hyperlink address.
        shape.setShapeAttrInternal(ShapeAttr.HYPERLINK_ADDRESS, videoUrl);

        shape.setDmlNode(createDmlPicture(videoEmbedCode, thumbnailImageBytes, getDocument().getNextShapeId()));

        // We have to set size after DmlNode was set.
        shape.setSizeSafe(width, height);

        // We presume we are inserting a top level shape, it has to be inserted into the tree
        // for the shape size validation below to work properly.
        mDocumentBuilder.insertNode(shape);

        return shape;
    }

    /// <summary>
    /// Prepares Dml picture with video extension.
    /// </summary>
    private static DmlPicture createDmlPicture(String videoEmbedCode, byte[] thumbnailImageBytes, int shapeId) throws Exception
    {
        // MSW inserts video as Dml picture with special extension.
        DmlPicture dmlPicture = new DmlPicture();
        dmlPicture.setGeometry(DmlGeometryReader.getPresetGeometry("rect"));

        DmlBlipFill blipFill = new DmlBlipFill();
        blipFill.getBlip().setExtensions(new StringToObjDictionary<DmlExtension>());

            // Create video extension.
        DmlExtension extension = new DmlExtension(DmlExtensionUri.VIDEO_PR, null);
        extension.setWebVideoPr(new DmlWebVideoProperties(videoEmbedCode, PLAYER_WIDTH, PLAYER_HEIGHT));
        blipFill.getBlip().getExtensions().add(DmlExtensionUri.VIDEO_PR, extension);

        if (thumbnailImageBytes == null)
            thumbnailImageBytes = createEmptyThumbnailImage();

        // Set thumbnail image.
        blipFill.getBlip().setEmbedImage(thumbnailImageBytes);

        dmlPicture.setBlipFill(blipFill);

        // Dml shape requires NvDrawingProperties.Id.
        DmlNvPrPicture nvPrPicture = new DmlNvPrPicture();
        nvPrPicture.setNvDrawingProperties(new DmlNvDrawingProperties(shapeId, ""));
        nvPrPicture.setCNvProperties(new DmlCnvPrPicture());
        dmlPicture.setNonVisualPr(nvPrPicture);

        return dmlPicture;
    }

    /// <summary>
    /// Creates an empty thumbnail image if the original thumbnail is not set or not available.
    /// </summary>
    private static byte[] createEmptyThumbnailImage() throws Exception
    {
        byte[] thumbnailImageBytes;

        // Mimic MSW behavior and create black bitmap 10x10.
        BitmapPal noThumbnailImg = new BitmapPal(10, 10);
        try /*JAVA: was using*/
        {
            // F0SS

            MemoryStream ms = new MemoryStream();
            try /*JAVA: was using*/
            {
                noThumbnailImg.save(ms, FileFormat.PNG);
                thumbnailImageBytes = ms.toArray();
            }
            finally { if (ms != null) ms.close(); }
        }
        finally { if (noThumbnailImg != null) noThumbnailImg.dispose(); }

        return thumbnailImageBytes;
    }

    private Document getDocument() { return mDocumentBuilder.getDocument(); }

    // The width of the video player. The default value for YouTube is 640.
    private static final double PLAYER_WIDTH = 640.0;

    // The height of the video player. The default value for YouTube is 390.
    private static final double PLAYER_HEIGHT = 390.0;

    private static final String FORMAT_REQUEST = "?url={0}&format=xml&maxwidth={1}&maxheight={2}";

    private static final String WARNING_MESSAGE = "Video from {0} can not be embedded. " +
          "Please contact us through the Aspose.Words support forum: https://www.aspose.com/community/forums/default.aspx.";

    private /*final*/ DocumentBuilder mDocumentBuilder;

    /// <summary>
    /// Supported video hosts collection.
    /// </summary>
    private static StringToObjDictionary<String> gSupportedVideoHosts  = initSupportedVideoHosts();

    /// <summary>
    /// Init supported video hosts collection.
    /// </summary>
    private static StringToObjDictionary<String> initSupportedVideoHosts()
    {
        // Use "https" to avoid exception "The remote server returned an error: (403) Forbidden." from Youtube
        gSupportedVideoHosts = new StringToObjDictionary<String>();
        gSupportedVideoHosts.add("youtube.com", "https://www.youtube.com/oembed");
        gSupportedVideoHosts.add("www.youtube.com", "https://www.youtube.com/oembed");
        gSupportedVideoHosts.add("youtu.be", "https://www.youtube.com/oembed");
        gSupportedVideoHosts.add("vimeo.com", "https://vimeo.com/api/oembed.xml");

        return gSupportedVideoHosts;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"html",
		"thumbnail_url"
	);

}

