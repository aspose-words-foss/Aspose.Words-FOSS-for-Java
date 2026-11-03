// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/06/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents list item with a bullet. Bullet is specified by 'list-style-image' CSS property.
/// </summary>
class HtmlPictureBulletListItem extends HtmlListItemBase
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="listLevelId">List level identifier.</param>
    /// <param name="pictureBulletId">Picture bullet's id.</param>
    /// <param name="pictureBulletSize">Picture bullet's size.</param>
    HtmlPictureBulletListItem(
        HtmlListLevelId listLevelId,
        int pictureBulletId,
        int pictureBulletSize)
    {
    	super(listLevelId, HtmlMarkerType.HTML, "");
	
        Debug.assertTrue(pictureBulletId >= 0);
        Debug.assertTrue(pictureBulletSize >= 0);

        setListLevelType(HtmlModelListLevelType.PICTURE_BULLET);
        setPictureBulletId(pictureBulletId);

        mPictureBulletSize = pictureBulletSize;
    }

    /*override*/ void postModifyList(HtmlModelList modelList)
    {
        HtmlModelListLevel modelListLevel = modelList.getListLevel(getListLevelId().getListLevelNumber());
        modelListLevel.getListLevel().getRunPr().setSize(mPictureBulletSize);
    }

    private /*final*/ int mPictureBulletSize;
}

