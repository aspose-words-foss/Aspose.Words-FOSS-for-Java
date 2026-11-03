// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/08/2012 by Dmitry Bormashov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToObjDictionary;
import com.aspose.ms.System.IO.Stream;
import com.aspose.AnyXmlReader;
import com.aspose.ms.System.Convert;
import com.aspose.ms.System.msString;
import com.aspose.ImageSizeCore;
import com.aspose.ImageConstants;
import com.aspose.ImageUtil;
import com.aspose.StreamUtil;


/// <summary>
/// Provides access to page border art information.
/// </summary>
class PageBorderArtRepository
{
    static PageBorderArtInfo getPageBorderArtInfo(/*PageBorderArt*/int pageBorderArt)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	// Only ECMA376 border art graphics information is available.
        	pageBorderArt = getEcma376BorderArt(pageBorderArt);

        	PageBorderArtInfo info = getPageBorderArtInfoCache().get((int)pageBorderArt);
        	if (info != null)
            	return info;

        	throw new IllegalStateException("Unknown border art.");
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Gets the ECMA376 compliant border art corresponding to specified ISO29500 border art.
    /// </summary>
    /// <remarks>
    /// Experiments with MS Word 2016 show that there's a mutual correspondence
    /// between Tribal* border art styles and ISO29500 border art styles,
    /// in range from Triangle1 to Shapes2.
    /// </remarks>
    static /*PageBorderArt*/int getEcma376BorderArt(/*PageBorderArt*/int pageBorderArt)
    {
        switch (pageBorderArt)
        {
            case PageBorderArt.EARTH_3:
                // Experiments show that this value is not accessible via MS Word GUI, 
                // and no border art is rendered for this value if specified directly in document.
                // Let's assign it Earth2 value, according to the legacy code in Iso29500ComplianceEnforcer.
                return PageBorderArt.EARTH_2;
            case PageBorderArt.TRIANGLE_1:
                return PageBorderArt.TRIBAL_1;
            case PageBorderArt.TRIANGLE_2:
                return PageBorderArt.TRIBAL_2;
            case PageBorderArt.TRIANGLE_CIRCLE_1:
                return PageBorderArt.TRIBAL_3;
            case PageBorderArt.TRIANGLE_CIRCLE_2:
                return PageBorderArt.TRIBAL_4;
            case PageBorderArt.SHAPES_1:
                return PageBorderArt.TRIBAL_5;
            case PageBorderArt.SHAPES_2:
                return PageBorderArt.TRIBAL_6;
            case PageBorderArt.CUSTOM:
                // MS-OI29500/[MS-OI29500].pdf / 2.1.528 Part 1 Section 17.18.2, ST_Border (Border Styles):
                // The standard states that custom is a valid enumeration value, however in MS Word, 
                // if the enumeration value of an ST_Border simple type is custom, then the file is considered corrupt.
                break;
            default:
                break;
        }

        return pageBorderArt;
    }

    /// <summary>
    /// Gets the ISO29500 compliant border art corresponding to specified ISO29500 border art.
    /// </summary>
    /// <remarks>
    /// Experiments with MS Word 2016 show that there's a mutual correspondence
    /// between Tribal* border art styles and ISO29500 border art styles,
    /// in range from Triangle1 to Shapes2.
    /// </remarks>
    static /*PageBorderArt*/int getIso29500BorderArt(/*PageBorderArt*/int pageBorderArt)
    {
        // Experiments with MS Word 2016 show that there's a mutual correspondence
        // between Tribal* border art styles and ISO29500 border art styles,
        // in range from Triangle1 to Shapes2.
        switch (pageBorderArt)
        {
            case PageBorderArt.TRIBAL_1:
                return PageBorderArt.TRIANGLE_1;
            case PageBorderArt.TRIBAL_2:
                return PageBorderArt.TRIANGLE_2;                    
            case PageBorderArt.TRIBAL_3:
                return PageBorderArt.TRIANGLE_CIRCLE_1;
            case PageBorderArt.TRIBAL_4:
                return PageBorderArt.TRIANGLE_CIRCLE_2;
            case PageBorderArt.TRIBAL_5:
                return PageBorderArt.SHAPES_1;
            case PageBorderArt.TRIBAL_6:
                return PageBorderArt.SHAPES_2;
            default:
                return pageBorderArt;
        }
    }

    private static IntToObjDictionary<PageBorderArtInfo> loadPageBorderArtXml() throws Exception
    {
        final String RESOURCE_NAME = "Aspose.Words.Resources.PageBorderArt.Definitions.xml";

        IntToObjDictionary<PageBorderArtInfo> result = new IntToObjDictionary<PageBorderArtInfo>();

        Stream stream = ResourceUtil.fetchResourceStream(RESOURCE_NAME);
        try /*JAVA: was using*/
        {
            AnyXmlReader xr = new AnyXmlReader(stream);
            
            while (xr.readChild("BorderArtDefinitions"))
            {
                PageBorderArtInfo pageBorderArtInfo = new PageBorderArtInfo(
                    Convert.toInt32(xr.readAttribute("id","-1")),
                    Convert.toInt32(xr.readAttribute("contraction", "0")),
                    Convert.toInt32(xr.readAttribute("hexpansion", "0")),
                    Convert.toInt32(xr.readAttribute("vexpansion", "0")));


                pageBorderArtInfo.setElement(BorderType.TOP, PageBorderArtElementPosition.FIRST,
                                             getElementBytes(xr.readAttribute("tl", "")));
                pageBorderArtInfo.setElement(BorderType.TOP, PageBorderArtElementPosition.MIDDLE,
                                             getElementBytes(xr.readAttribute("t", "")));
                pageBorderArtInfo.setElement(BorderType.TOP, PageBorderArtElementPosition.LAST,
                                             getElementBytes(xr.readAttribute("tr", "")));

                pageBorderArtInfo.setElement(BorderType.LEFT, PageBorderArtElementPosition.MIDDLE,
                                             getElementBytes(xr.readAttribute("l", "")));
                pageBorderArtInfo.setElement(BorderType.RIGHT, PageBorderArtElementPosition.MIDDLE,
                                             getElementBytes(xr.readAttribute("r", "")));

                pageBorderArtInfo.setElement(BorderType.BOTTOM, PageBorderArtElementPosition.FIRST,
                                             getElementBytes(xr.readAttribute("bl", "")));
                pageBorderArtInfo.setElement(BorderType.BOTTOM, PageBorderArtElementPosition.MIDDLE,
                                             getElementBytes(xr.readAttribute("b", "")));
                pageBorderArtInfo.setElement(BorderType.BOTTOM, PageBorderArtElementPosition.LAST,
                                             getElementBytes(xr.readAttribute("br", "")));
               
                result.add(pageBorderArtInfo.getId(), pageBorderArtInfo);
            }
        }
        finally { if (stream != null) stream.close(); }
        return result;
    }

    private static IntToObjDictionary<PageBorderArtInfo> getPageBorderArtInfoCache() throws Exception
    {
        // double-checked locking pattern.
        if (gPageBorderArtInfoCache == null)
        {
            synchronized (G_CACHE_SYNC_ROOT)
            {
                if (gPageBorderArtInfoCache == null)
                    gPageBorderArtInfoCache = loadPageBorderArtXml();
            }
        }

        return gPageBorderArtInfoCache;
    }

    private static byte[] getElementBytes(String elementName)
    {
        try
        {
            Stream stream =
                ResourceUtil.fetchResourceStream(msString.format("Aspose.Words.Resources.PageBorderArt.{0}", elementName));
            try /*JAVA: was using*/
            {
                // Some images for border art don't appear correctly if they don't have
                // placeable header. To solve this problem we add the code below.
                // In this case, width and height in imageSizeCore can have any values,
                // but they should be greater than zero.
                ImageSizeCore imageSizeCore = ImageSizeCore.createWithResolution(
                    1, 1, ImageConstants.STANDARD_RESOLUTION);

                return ImageUtil.prependWmfPlaceableHeader(StreamUtil.copyStreamToByteArray(stream), imageSizeCore);
            }
            finally { if (stream != null) stream.close(); }
        }
        catch(Exception e)
        {
            return new byte[0];
        }
    }

    private static /*final*/ Object G_CACHE_SYNC_ROOT = new Object();
    //JAVA: volatile modifier is added to static field with purpose to double-check pattern work in java.
    private static volatile IntToObjDictionary<PageBorderArtInfo> gPageBorderArtInfoCache;
}

