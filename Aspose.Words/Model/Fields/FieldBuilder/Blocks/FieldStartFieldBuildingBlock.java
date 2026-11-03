// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class FieldStartFieldBuildingBlock implements IFieldBuildingBlock
{
    FieldStartFieldBuildingBlock(/*FieldType*/int fieldType)
    {
        mFieldType = fieldType;
    }

    FieldStart getFieldStart() { return mFieldStart; }; private void setFieldStart(FieldStart value) { mFieldStart = value; };

    private FieldStart mFieldStart;

    public void /*IFieldBuildingBlock.*/buildBlock(DocumentBuilder documentBuilder)
    {
        setFieldStart(new FieldStart(documentBuilder.getDocument(), new RunPr(), mFieldType));
        documentBuilder.insertNode(getFieldStart());
    }

    private /*final*/ /*FieldType*/int mFieldType;
}
