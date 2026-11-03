// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class TextFieldBuildingBlock implements IFieldBuildingBlock
{
    TextFieldBuildingBlock(String text)
    {
        mText = text;
    }

    public void /*IFieldBuildingBlock.*/buildBlock(DocumentBuilder documentBuilder)
    {
        documentBuilder.write(mText);
    }

    private /*final*/ String mText;
}
