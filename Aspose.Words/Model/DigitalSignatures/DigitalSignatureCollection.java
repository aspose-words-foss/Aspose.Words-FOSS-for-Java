// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/08/2010 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Guid;
import com.aspose.StringUtil;
import java.util.Iterator;
import java.util.ArrayList;
import com.aspose.GuidToObjDictionary;


/// <summary>
/// Provides a read-only collection of digital signatures attached to a document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-digital-signatures/">Work with Digital Signatures</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <see cref="Document.DigitalSignatures"/>
/// </remarks>
public class DigitalSignatureCollection implements Iterable<DigitalSignature>
{
    /// <summary>
    /// Returns <c>true</c> if all digital signatures in this collection are valid and the document has not been tampered with
    /// Also returns <c>true</c> if there are no digital signatures.
    /// Returns <c>false</c> if at least one digital signature is invalid.
    /// </summary>
    public boolean isValid()
    {
        for (DigitalSignature signature : mItems)
        {
            if (!signature.isValid())
                return false;
        }

        return true;
    }

    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mItems.size(); }

    /// <summary>
    /// Gets a document signature at the specified index.
    /// </summary>
    /// <param name="index">Zero-based index of the signature.</param>
    public DigitalSignature get(int index) { return mItems.get(index); }

    /// <summary>
    /// This method is used only internally. From user side this collection can be only read.
    /// </summary>
    void add(DigitalSignature signature)
    {
        msArrayList.add(mItems, signature);

        // Collect visible signatures SetupIDs.
        if(signature.getVisible())
        {
            // AM. I think only visible signatures have SetupID.
            mSignatureBySetupId.add(signature.getSetupId(), signature);
        }
        else
        {
            // AM. Non-visible signatures must not have SetupID.
            Debug.assertTrue(signature.getSetupId().equals(Guid.Empty));
        }
    }

    /// <summary>
    /// Returns DigitalSignature object by given SetupID or null if not found.
    /// </summary>
    DigitalSignature getBySetupId(String setupId)
    {
        if (!StringUtil.hasChars(setupId))
            return null;

        return mSignatureBySetupId.get(new Guid(setupId));
    }

    /// <summary>
    /// Returns a dictionary <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    public Iterator<DigitalSignature> iterator()
    {
        return mItems.iterator();
    }
    
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private /*final*/ ArrayList<DigitalSignature> mItems = new ArrayList<DigitalSignature>();

    /// <summary>
    /// Provides quick access to visible signatures by SetupId.
    /// </summary>
    private /*final*/ GuidToObjDictionary<DigitalSignature> mSignatureBySetupId = 
        new GuidToObjDictionary<DigitalSignature>();
}

