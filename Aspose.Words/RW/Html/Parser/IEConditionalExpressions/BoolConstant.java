// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class BoolConstant extends ConditionalExpression
{
    BoolConstant(boolean value)
    {
        mValue = value;
    }

    /*override*/ boolean matches(Features features)
    {
        return mValue;
    }

    public /*override*/ String toString()
    {
        return (mValue)
            ? "true"
            : "false";
    }

    protected /*override*/ boolean isSubexpression() { return false; }

    private /*final*/ boolean mValue;
}
