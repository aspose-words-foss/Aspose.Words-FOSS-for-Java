// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class FieldEndFieldBuildingBlock implements IFieldBuildingBlock
{
    FieldEndFieldBuildingBlock(/*FieldType*/int fieldType)
    {
        mFieldType = fieldType;
    }

    FieldSeparator getFieldSeparator() { return mFieldSeparator; }; private void setFieldSeparator(FieldSeparator value) { mFieldSeparator = value; };

    private FieldSeparator mFieldSeparator;

    FieldEnd getFieldEnd() { return mFieldEnd; }; private void setFieldEnd(FieldEnd value) { mFieldEnd = value; };

    private FieldEnd mFieldEnd;

    public void /*IFieldBuildingBlock.*/buildBlock(DocumentBuilder documentBuilder)
    {
        buildSeparator(documentBuilder);
        buildEnd(documentBuilder);
    }

    private void buildSeparator(DocumentBuilder documentBuilder)
    {
        switch (FieldUtil.getSeparatorPresence(mFieldType))
        {
            case FieldSeparatorPresence.NEVER:
            case FieldSeparatorPresence.SOMETIMES:
                setFieldSeparator(null);
                break;
            case FieldSeparatorPresence.ALWAYS:
                setFieldSeparator(new FieldSeparator(documentBuilder.getDocument(), new RunPr(), mFieldType));
                documentBuilder.insertNode(getFieldSeparator());
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void buildEnd(DocumentBuilder documentBuilder)
    {
        setFieldEnd(new FieldEnd(documentBuilder.getDocument(), new RunPr(), mFieldType, getFieldSeparator() != null));
        documentBuilder.insertNode(getFieldEnd());
    }

    private /*final*/ /*FieldType*/int mFieldType;
}
