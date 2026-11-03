// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the FORMDROPDOWN field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts a drop-down list style form field.
/// </remarks>
public class FieldFormDropDown extends Field
{
    /*override*/ NodeRange getFakeResult() throws Exception
    {
        // MF There is no way to get layout options here.
        // The one defined at the document level could be overridden by layout (e.g. in tests)
        // So this code assumes that layout will not invoke it if IsDontWrapDropDownFormFields is True.

        // No form field - no result.
        FormField formField = getStart().getFormField();
        if (formField == null)
            return null;

        Document document = fetchDocument();
        IFieldRunPrProvider provider = new FieldFakeResultRunPrProvider(this);
        final boolean IS_RTL_EMBEDDING = false; // Be consistent with FieldFakeResultAppender.

        // FORMDROPDOWN items are always single-line.
        return FieldTextHelper.getSingleLineTextRunRangeBidiAware(formField.getResult(), document, provider, IS_RTL_EMBEDDING);
    }
}

