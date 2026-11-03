// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/12/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

class HtmlPictureBulletIdPropertyValue implements IHtmlModelListLevelPropertyValue
{
    HtmlPictureBulletIdPropertyValue(int pictureBulletId)
    {
        Debug.assertTrue(pictureBulletId >= 0);

        mPictureBulletId = pictureBulletId;
    }

    public boolean /*IHtmlModelListLevelPropertyValue.*/canModifyListLevel(HtmlModelListLevel modelListLevel)
    {
        return !modelListLevel.isPictureBulletIdSet() || (mPictureBulletId == modelListLevel.getPictureBulletId());
    }

    public void /*IHtmlModelListLevelPropertyValue.*/modifyListLevel(HtmlModelListLevel modelListLevel)
    {
        if (!modelListLevel.isPictureBulletIdSet())
        {
            modelListLevel.setPictureBulletId(mPictureBulletId);
        }
    }

    private /*final*/ int mPictureBulletId;
}

