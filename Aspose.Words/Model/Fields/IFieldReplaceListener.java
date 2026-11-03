// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/09/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Performs additional actions during field replacing with result.
/// </summary>
interface IFieldReplaceListener
{
    public void beforeReplaceWithResult() throws Exception;
}

/// <summary>
/// Represents listener without any side effects.
/// </summary>
class EmptyFieldReplaceListener implements IFieldReplaceListener
{
    private EmptyFieldReplaceListener()
    {
    }

    static /*final*/ EmptyFieldReplaceListener INSTANCE = new EmptyFieldReplaceListener();

    public void /*IFieldReplaceListener.*/beforeReplaceWithResult()
    {
        // Do nothing.
    }
}
