// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/09/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Performs additional actions necessary for unlinking fields.
/// </summary>
class UnlinkFieldReplaceListener implements IFieldReplaceListener
{
    UnlinkFieldReplaceListener(Field field)
    {
        mField = field;
    }

    public void /*IFieldReplaceListener.*/beforeReplaceWithResult() throws Exception
    {
        mField.beforeUnlink();
    }

    private /*final*/ Field mField;
}
