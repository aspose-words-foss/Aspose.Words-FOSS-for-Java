// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/05/2017 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements methods to resolve attributes of the paragraph properties
/// while paragraph with list is importing.
/// </summary>
class ImportParaPrSaver implements IExpandedAttrSaver
{
    /// <summary>
    /// Ctr.
    /// </summary>
    ImportParaPrSaver(ParaPr srcPr, ParaPr dstPr)
    {
        mSrcPr = srcPr;
        mDstPr = dstPr;
    }

    /// <summary>
    /// Saves attribute with specified key to destination collection.
    /// </summary>
    public void save(AttrCollection dstAttrs, int key, Object value)
    {
        if (dstAttrs.containsKey(key))
            return;

        // Mimic MSW and use left indent equal to default tab stop when source
        // properties does not contain appropriate attribute and value 
        // cannot be inherited from destination definition properties. 
        // It is operation with boxing, however it does not happened often.
        if ((key == ParaAttr.LEFT_INDENT) && !mSrcPr.containsKey(key))
        {
            if ((/*int*/Integer)mDstPr.fetchAttr(ParaAttr.LEFT_INDENT) == DocPr.DEFAULT_TAB_STOP_DEFAULT)
            {
                dstAttrs.remove(key);
                return;
            }

            value = DocPr.DEFAULT_TAB_STOP_DEFAULT;
        }

        dstAttrs.set(key, value);
    }

    /// <summary>
    /// Properties of the source paragraph while importing.
    /// </summary>
    private /*final*/ ParaPr mSrcPr;

    /// <summary>
    /// Properties of the destination paragraph while importing.
    /// </summary>
    private /*final*/ ParaPr mDstPr;
}

