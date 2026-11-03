// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.DrColor;
import com.aspose.ms.System.msString;


/// <summary>
/// Writes 'v:imagedata' sub-element of 'v:shape' to WordML.
/// </summary>
class VmlImagedataWriter
{
    VmlImagedataWriter(ShapeBase shape, NrxXmlBuilder builder, IVmlShapeWriterContext context)
    {
        Debug.assertTrue(null != context);
        mBuilder = builder;
        mShape = shape;
        mContext = context;
    }

    /// <summary>
    /// Add 'v:imagedata' related attribute.
    /// </summary>
    /// <param name="key">Attribute key.</param>
    /// <param name="value">Attribute value.</param>
    void addAttribute(int key, Object value)
    {
        switch (key)
        {
            case ShapeAttr.IMAGE_ACTIVE:
                {
                    break;
                }
            case ShapeAttr.IMAGE_BI_LEVEL:
                {
                    mImageBiLevel = VmlUtil.boolToVml(value, false);
                    break;
                }
            case ShapeAttr.IMAGE_BRIGHTNESS:
                {
                    mImageBrightness = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.IMAGE_BYTES:
                {
                    mImageBytes = (byte[])value;
                    break;
                }
            case ShapeAttr.IMAGE_CONTRAST:
                {
                    mImageContrast = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.IMAGE_CROP_BOTTOM:
                {
                    mImageCropBottom = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.IMAGE_CROP_LEFT:
                {
                    mImageCropLeft = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.IMAGE_CROP_RIGHT:
                {
                    mImageCropRight = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.IMAGE_CROP_TOP:
                {
                    mImageCropTop = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.IMAGE_DBL_CR_MOD:
                {
                    mImageDblCrMod = mContext.colorToVml((DrColor)value);
                    break;
                }
            case ShapeAttr.IMAGE_FILL_CR_MOD:
                {
                    break;
                }
            case ShapeAttr.IMAGE_GAMMA:
                {
                    mImageGamma = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.IMAGE_GRAY_SCALE:
                {
                    mImageGrayScale = VmlUtil.boolToVml(value, false);
                    break;
                }
            case ShapeAttr.IMAGE_LINE_CR_MOD:
                {
                    break;
                }
            case ShapeAttr.IMAGE_NO_HIT_TEST:
                {
                    break;
                }
            case ShapeAttr.IMAGE_PRESERVE_GRAYS:
                {
                    break;
                }
            case ShapeAttr.IMAGE_RECOLOR:
                {
                    DrColor color = new DrColor((/*int*/Integer)value);
                    mImageRecolor = VmlColor.colorToVml(new DrColor(color.getB(), color.getG(), color.getR()));
                    break;
                }
            case ShapeAttr.IMAGE_RECOLOR_EXT:
                {
                    break;
                }
            case ShapeAttr.IMAGE_RECOLOR_EXT_CR_MOD:
                {
                    break;
                }
            case ShapeAttr.IMAGE_SOURCE_FULL_NAME:
                {
                    mImageSourceFullName = (String)value;
                    break;
                }
            case ShapeAttr.IMAGE_TITLE:
                {
                    mImageTitle = (String)value;
                    break;
                }
            case ShapeAttr.IMAGE_TRANSPARENT:
                {
                    mImageTransparent = mContext.colorToVml((DrColor)value);
                    break;
                }
            default:
                return;
        }
    }

    /// <summary>
    /// Write 'v:imagedata' element.
    /// </summary>
    void write() throws Exception
    {
        // andrnosk: WORDSNET-7968 If ImageRecolor (recolortarget) is specified we should write imagedata.
        if ((mImageBytes == null) && (mImageSourceFullName == null) && (mImageDblCrMod == null) && (mImageRecolor == null))
            return;

        mBuilder.startElement("v:imagedata");

        // If the image is linked, then, if image binary data is defined inside document,
        // the 'src' should be the internal reference to the binary data
        // and the file reference should be put inside 'o:href',
        // otherwise the file reference is put directly in 'src'.
        mBuilder.writeAttribute(mContext.getImageSrcAttributeName(), mImageName);

        if ((mImageBytes != null) && (!msString.equals(mImageSourceFullName, mImageName)))
            mBuilder.writeAttribute(mContext.getImageHrefAttributeName(), mImageSourceFullName);

        // RK This has to be written even if empty string. If not written, then in some cases
        // either MS Word fails to open a document or double click on an inline OLE object does not work
        // or a picture does not show when document is downloaded in Internet Explorer via URL.
        // WORDSNET-13261 If image is not embedded and stored with the path and name of the source file
        // then after modify and re-saving document from MS Word image became embedded. Documentation says: 
        // “If title attribute has a value, then the image is embedded”, so we need skip "title" for linked image
        // when "title" is not set (https://msdn.microsoft.com/en-us/library/bb229579(v=vs.85).aspx).
        if (checkAddTitle(mImageTitle))
            mBuilder.writeAttributeString("o:title", mImageTitle);

        mBuilder.writeAttribute("croptop", mImageCropTop);
        mBuilder.writeAttribute("cropbottom", mImageCropBottom);
        mBuilder.writeAttribute("cropleft", mImageCropLeft);
        mBuilder.writeAttribute("cropright", mImageCropRight);
        mBuilder.writeAttribute("chromakey", mImageTransparent);
        mBuilder.writeAttribute("gain", mImageContrast);
        mBuilder.writeAttribute("blacklevel", mImageBrightness);
        mBuilder.writeAttribute("gamma", mImageGamma);
        mBuilder.writeAttribute("grayscale", mImageGrayScale);
        mBuilder.writeAttribute("bilevel", mImageBiLevel);
        mBuilder.writeAttribute("embosscolor", mImageDblCrMod);
        mBuilder.writeAttribute("recolortarget", mImageRecolor);

        mBuilder.endElement(); //v:imagedata
    }

    /// <summary>
    /// Checks whether it is required to add title attribute.
    /// </summary>
    /// <returns>Is True when "Title" attribute have to be added.</returns>
    private boolean checkAddTitle(String title) throws Exception
    {
        // When current node is inside complex element, it's necessary to write title for properly show image.
        // When title sets implicitly we have to write it.
        if (!(mShape instanceof Shape) || (null != title) || mContext.isInsideField())
            return true;           

        Shape s = (Shape)mShape;                   
        ImageData imgData = s.getImageData();

        // Skip title only for linked image.
        return (imgData == null) || (s.getShapeType() != ShapeType.IMAGE) || !imgData.isLinkOnly();
    }

    byte[] getImageBytes() { return mImageBytes; }

    String getImageName() { return mImageName; }
    void setImageName(String value) { mImageName = value; }

    String getImageSourceFullName() { return mImageSourceFullName; }
    void setImageSourceFullName(String value) { mImageSourceFullName = value; }

    private /*final*/ NrxXmlBuilder mBuilder;
    private /*final*/ ShapeBase mShape;
    private /*final*/ IVmlShapeWriterContext mContext;

    private byte[] mImageBytes;
    private String mImageName = null;
    private String mImageTitle = null;
    private String mImageSourceFullName = null;

    private String mImageCropTop = null;
    private String mImageCropBottom = null;
    private String mImageCropLeft = null;
    private String mImageCropRight = null;
    private String mImageTransparent = null;
    private String mImageContrast = null;
    private String mImageBrightness = null;
    private String mImageGamma = null;
    private String mImageDblCrMod = null;
    private String mImageGrayScale = null;
    private String mImageBiLevel = null;
    private String mImageRecolor = null;

    //id    
    //src    Yes
    //o:title    Yes
    //croptop    Yes
    //cropbottom    Yes
    //cropleft    Yes
    //cropright    Yes
    //chromakey    Yes
    //gain    Yes
    //blacklevel    Yes
    //gamma    Yes
    //grayscale    Yes
    //bilevel    Yes
    //embosscolor    Yes
    //o:href    Yes
    //o:althref    
    //o:oleid    
    //o:detectmouseclick    
    //o:movie    
}

