// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/05/2025 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ObjToIntDictionary;
import com.aspose.ms.System.IO.Path;


class HtmlPictureBulletLoader
{
    HtmlPictureBulletLoader(
        Document document,
        HtmlResourceLoader resourceLoader,
        String baseUri)
    {
        Debug.assertTrue(document != null);
        Debug.assertTrue(resourceLoader != null);

        mDocument = document;
        mResourceLoader = resourceLoader;
        mBaseUri = baseUri;
        mPictureBulletIds = new ObjToIntDictionary<String>();
    }

    int getPictureBulletId(String pictureBulletUri) throws Exception
    {
        if (mPictureBulletIds.containsKey(pictureBulletUri))
        {
            return mPictureBulletIds.get(pictureBulletUri);
        }
        else
        {
            byte[] imageBytes = mResourceLoader.loadImage(mBaseUri, pictureBulletUri, false);
            if (imageBytes != null)
            {
                Shape pictureBullet = new Shape(mDocument, ShapeType.IMAGE);
                pictureBullet.getImageData().setImageBytes(imageBytes);
                pictureBullet.setWrapType(WrapType.INLINE);
                pictureBullet.setTitle(Path.getFileNameWithoutExtension(pictureBulletUri));

                int newPictureBulletId = mDocument.getLists().addPictureBullet(pictureBullet);
                mPictureBulletIds.add(pictureBulletUri, newPictureBulletId);

                ImageSize imageSize = pictureBullet.getImageData().getImageSize();
                pictureBullet.setSizeSafe(imageSize.getWidthPoints(), imageSize.getHeightPoints());

                return newPictureBulletId;
            }
        }

        return -1;
    }

    private /*final*/ Document mDocument;
    private /*final*/ HtmlResourceLoader mResourceLoader;
    private /*final*/ String mBaseUri;
    private /*final*/ ObjToIntDictionary<String> mPictureBulletIds;
}

