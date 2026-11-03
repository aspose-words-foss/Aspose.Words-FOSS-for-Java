// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msObject;
import com.aspose.StringUtil;
import com.aspose.FileFormat;
import com.aspose.ImageUtil;
import com.aspose.CompressedData;
import com.aspose.ArrayUtil;


/// <summary>
/// 20.1.8.13 blip (Blip)
/// This element specifies the existence of an image (binary large image or picture) 
/// and contains a reference to the image data.
/// </summary>
class DmlBlip extends DmlExtensionListSource implements Cloneable
{
    public DmlBlip deepClone()
    {
        DmlBlip lhs = (DmlBlip)memberwiseClone();
        if (mEffects != null)
        {
            lhs.mEffects = new ArrayList<DmlEffect>();
            for (DmlEffect effect : mEffects)
                lhs.mEffects.add(effect.deepClone());
        }
        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlBlip value = (DmlBlip)obj;

        return (value.getCompressionState() == getCompressionState()) &&
               msObject.equals(value.getImageLink(), getImageLink()) &&
               msObject.equals(value.getEmbedImage(), getEmbedImage()) &&
               hasSameEffects(value);
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        hash ^= /*enum.hashCode()=(int)value*/getCompressionState();
        if (StringUtil.hasChars(getImageLink()))
            hash ^= getImageLink().hashCode();
        if (hasEmbedImage())
            hash ^= getEmbedImage().hashCode();

        for (DmlEffect effect : getEffects())
            hash ^= effect.hashCode();

        return hash;
    }

    private boolean hasSameEffects(DmlBlip value)
    {
        if (value.getEffects().size() != getEffects().size())
            return false;

        boolean hasSameEffects = true;
        for (int i = 0; i < getEffects().size(); i++)
        {
            DmlEffect e1 = getEffects().get(i);
            DmlEffect e2 = value.getEffects().get(i);

            hasSameEffects &= msObject.equals(e1, e2);
            if(!hasSameEffects)
                break;
        }

        return hasSameEffects;
    }

    /*FileFormat*/int getImageType() throws Exception
    {
        if (hasEmbedImage())
            return ImageUtil.getImageType(mEmbedImage);

        return ImageUtil.getImageType(getImageBytes());
    }

    byte[] getImageBytes() throws Exception
    {            
        if (mEmbedImage != null)
            return CompressedData.getData(mEmbedImage);

        if ((mCachedImage == null) && StringUtil.hasChars(mImageLink))
            mCachedImage = ImageDataUtil.loadImageBytes(getImageLink(), mDocument);

        return mCachedImage;
    }

    boolean hasEmbedImage() { return ArrayUtil.hasData(mEmbedImage); }

    byte[] getEmbedImage() { return CompressedData.getData(mEmbedImage); }
    void setEmbedImage(byte[] value) { mEmbedImage = value; }

    String getImageLink() { return mImageLink; }
    void setImageLink(String value) { mImageLink = value; }

    /// <summary>
    /// Specifies the compression state with which the picture is stored. 
    /// This allows the application to specify the amount of compression 
    /// that has been applied to a picture.
    /// </summary>
    /*DmlCompressionState*/int getCompressionState() { return mCompressionState; }
    void setCompressionState(/*DmlCompressionState*/int value) { mCompressionState = value; }

    ArrayList<DmlEffect> getEffects()
    {
        if (mEffects == null)
            mEffects = new ArrayList<DmlEffect>();
        return mEffects;
    }
    void setEffects(ArrayList<DmlEffect> value) { mEffects = value; }

    DocumentBase getDocument() { return mDocument; }
    void setDocument(DocumentBase value) { mDocument = value; }

    private /*DmlCompressionState*/int mCompressionState;
    private ArrayList<DmlEffect> mEffects;
    private byte[] mCachedImage;
    private String mImageLink;
    private byte[] mEmbedImage;
    private DocumentBase mDocument;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

