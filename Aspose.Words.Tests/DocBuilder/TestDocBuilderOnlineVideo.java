// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/03/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDocBuilderOnlineVideo
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Test first simple overload -  insert online video from Url.
    /// </summary>
    @Test
    public void testInsertOnlineVideoFromUrl() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Pass direct url from youtu.be.
        String url = "https://youtu.be/g1N9ke8Prmk";
        double width = 360.0;
        double height = 270.0;
        Shape shape = builder.insertOnlineVideo(url, width, height);
        checkOnlineVideoPr(shape, url, YOU_TUBE_EMBED_CODE, width, height);

        // Pass "watch" url from www.youtube.com.
        url = "https://www.youtube.com/watch?v=g1N9ke8Prmk";
        width = 320.0;
        height = 230.0;
        shape = builder.insertOnlineVideo(url, width, height);
        checkOnlineVideoPr(shape, url, YOU_TUBE_EMBED_CODE, width, height);

        // Pass "watch" url from youtube.com with extra parameters in request.
        url = "https://youtube.com/watch?v=g1N9ke8Prmk&feature=youtu.be";
        width = 280.0;
        height = 190.0;
        shape = builder.insertOnlineVideo(url, width, height);
        checkOnlineVideoPr(shape, url, YOU_TUBE_EMBED_CODE, width, height);

        // Pass direct url from vimeo.com.
        url = "https://vimeo.com/52477838";
        width = 240.0;
        height = 150.0;
        shape = builder.insertOnlineVideo(url, width, height);
        checkOnlineVideoPr(shape, url, VIMEO_EMBED_CODE, width, height);

        // Pass wrong url - in this case the black image will be inserted to the document.
        url = "https://wrongUrl/Test";
        width = 200.0;
        height = 110.0;
        shape = builder.insertOnlineVideo(url, width, height);
        checkOnlineVideoPr(shape, url, "", width, height);
        msAssert.isTrue(doc.getComplianceInfo().isDrawingExtensions());

        TestUtil.saveOpen(doc, "DocBuilder\\OnlineVideo\\TestInsertOnlineVideoFromUrl", UnifiedScenario.DOCX_2_DOCX);
    }


    private static void checkOnlineVideoPr(Shape shape, String videoUrl, String embebCode, double width, double height) throws Exception
    {
        msAssert.areEqual(width, shape.getWidth());
        msAssert.areEqual(height, shape.getHeight());
        msAssert.areEqual(videoUrl, shape.getHRef());

        msAssert.isTrue(shape.hasImage());

        DmlBlip dmlBlip = ((DmlPicture)shape.getDmlNode()).getBlipFill().getBlip();
        msAssert.areEqual(1, dmlBlip.getExtensions().getCount());
        DmlExtension videoExtension = dmlBlip.getExtensions().get(DmlExtensionUri.VIDEO_PR);

        msAssert.areEqual(DmlExtensionUri.VIDEO_PR, videoExtension.getUri());

        DmlWebVideoProperties webVideoProperties = videoExtension.getWebVideoPr();

        // The width/height of the video player. The default value for YouTube is 640/390.
        msAssert.areEqual(640, webVideoProperties.getFrameWidth());
        msAssert.areEqual(390, webVideoProperties.getFrameHeight());

        // Check EmbedHtml.
        msAssert.areEqual(embebCode, webVideoProperties.getEmbedHtml());
    }

    private static final String VIMEO_EMBED_CODE = "<iframe src=\"https://player.vimeo.com/video/52477838?app_id=122963\" width=\"640\" height=\"360\" " +
    "frameborder=\"0\" allow=\"autoplay; fullscreen; picture-in-picture; clipboard-write; encrypted-media; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" title=\"Aspose\"></iframe>";

    private static final String YOU_TUBE_EMBED_CODE = "<iframe width=\"640\" height=\"360\" " +
    "src=\"https://www.youtube.com/embed/g1N9ke8Prmk?feature=oembed\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen title=\"Aspose.Words - Read DOCX, PDF, MOBI\"></iframe>";
}

