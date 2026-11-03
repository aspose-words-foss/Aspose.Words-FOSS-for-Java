// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/12/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.HashMap;
import java.util.Map;


/// <summary>
/// Converts OfficeMath objects to Shape objects for storing into formats that don't support OMML.
/// </summary>
class OfficeMathToShapeConverter
{
    /// <summary>
    /// Ctor.
    /// </summary>
    OfficeMathToShapeConverter(Document document, /*SaveFormat*/int saveFormat)
    {
        Debug.assertTrue(document != null);
        mDocument = document;
        mSaveFormat = saveFormat;

        mConvertedOfficeMathToShape = new HashMap<OfficeMath, Shape>();
        mShapeToConvertedOfficeMath = new HashMap<ShapeBase, OfficeMath>();
    }

    /*virtual*/ /*VisitorAction*/int visitOfficeMathStart(OfficeMath officeMath, DocumentValidator validator)
    {
        // FOSS
        return VisitorAction.CONTINUE;
    }

    /*virtual*/ /*VisitorAction*/int visitOfficeMathEnd(OfficeMath officeMath, DocumentValidator validator) throws Exception
    {
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Revert conversion of OfficeMath to Shape to keep model in original state.
    /// </summary>
    void revert()
    {
        // Office Math is converted to shape with special attribute when document is saved
        // to DOC or WML or RTF formats. Reverts these changes back to get original Model.
        for (Map.Entry<OfficeMath, Shape> entry : mConvertedOfficeMathToShape.entrySet())
        {
            Shape mathShape = entry.getValue();
            OfficeMath officeMath = entry.getKey();
            insertShapeAtCompatibleTreeLevel(officeMath, mathShape);
            mathShape.remove();
        }
    }

    protected /*virtual*/ void insertShapeAtCompatibleTreeLevel(OfficeMath officeMath, Shape mathShape)
    {
        officeMath.setDocument(mDocument);
        NodeUtil.insertShapeAtCompatibleTreeLevel(officeMath, mathShape);
    }

    protected /*virtual*/ void applySpecialPreserveAttribute(OfficeMath officeMath, Shape mathShape) throws Exception
    {
        // Do nothing.
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(/*WarningType*/int type, /*WarningSource*/int source, String description)
    {
        IWarningCallback warningCallback = mDocument.getWarningCallback();
        if (warningCallback != null)
            warningCallback.warning(new WarningInfo(type, source, description));
    }

    Map<OfficeMath, Shape> getConvertedOfficeMathToShape() { return mConvertedOfficeMathToShape; }

    Map<ShapeBase, OfficeMath> getShapeToConvertedOfficeMath() { return mShapeToConvertedOfficeMath; }

    private /*final*/ Document mDocument;
    private /*final*/ /*SaveFormat*/int mSaveFormat;

    /// <summary>
    /// Maps converted OfficeMath objects to original ShapeBase objects
    /// when document is saved to format which doesn't support OMML.
    /// Keys are instances of <see cref="OfficeMath"/>.
    /// Values are instances of <see cref="Shape"/>.
    /// </summary>
    private /*final*/ HashMap<OfficeMath, Shape> mConvertedOfficeMathToShape;

    /// <summary>
    /// Maps original ShapeBase objects to converted OfficeMath objects
    /// when document is saved to format which doesn't support OMML.
    /// Keys are instances of <see cref="ShapeBase"/>.
    /// Values are instances of <see cref="OfficeMath"/>.
    /// </summary>
    private /*final*/ HashMap<ShapeBase, OfficeMath> mShapeToConvertedOfficeMath;
}

