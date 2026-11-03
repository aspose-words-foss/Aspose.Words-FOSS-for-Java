// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.msString;


// AM. There is one possible threat now. Word provides rendered images for both valid and invalid signature line states.
// It seems to be OK to use them as is but consider following scenario:
// Hacker injected valid image instead of invalid image into digital signature.
// Digital signature is violated of course and it will be shown in both MS Word and Aspose.Words.
// But what if user just renders such document to PDF (or any format which not support digital signatures)?
// Render engine gets SignatureLine.ImageBytesInvalid (but it is changed by hacker!),
// prints out this image and output PDF looks like valid document.
//
// The only way to avoid this situation is to render valid/invalid images by render engine and do not use Word prepared images.
// We need to get Shape.ImageData.ImageBytes as template (placeholder) and render either
// user selected signature DigitalSiagnture.ImageBytes in case signature line is valid or
// some picture that indicates that signature line is invalid.

/// <summary>
/// Provides access to signature line properties.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-digital-signatures/">Work with Digital Signatures</a> documentation article.</para>
/// </summary>
public class SignatureLine
{
    SignatureLine(Shape parent)
    {
        mParent = parent;

        String sigSetupId = (String)mParent.getDirectShapeAttrInternal(ShapeAttr.SIG_SETUP_ID);
        if (!StringUtil.hasChars(sigSetupId))
            mParent.setShapeAttrInternal(ShapeAttr.SIG_SETUP_ID, Guid.newGuid().toString("B").toUpperCase());

        // The value {00000000-0000-0000-0000-000000000000} is Word reserved for default signature provider:
        // https://msdn.microsoft.com/en-us/library/ff531802(v=office.12).aspx
        String providerId = (String)mParent.getDirectShapeAttrInternal(ShapeAttr.SIG_SETUP_PROV_ID);
        if (!StringUtil.hasChars(providerId))
            mParent.setShapeAttrInternal(ShapeAttr.SIG_SETUP_PROV_ID, "{00000000-0000-0000-0000-000000000000}");
    }

     /// <summary>
    /// Generates new signature line image and places it into the shape.
    /// </summary>
    void updateShapeImage()
    {
    }

    /// <summary>
    /// Gets or sets suggested signer of the signature line.
    /// Default value for this property is <b>empty string</b><ms> (<see cref="string.Empty"/>)</ms>.
    /// </summary>
    public String getSigner()
    {
        String result = (String)mParent.getDirectShapeAttrInternal(ShapeAttr.SIG_SETUP_SUGG_SIGNER);
        return (result != null) ? result : "";
    }
    public void setSigner(String value)
    {
        if (!msString.equals(getSigner(), value))
        {
            if (StringUtil.hasChars(value))
                mParent.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SUGG_SIGNER, value);
            else
                mParent.removeShapeAttrInternal(ShapeAttr.SIG_SETUP_SUGG_SIGNER);
            updateShapeImage();
        }
    }

    /// <summary>
    /// Gets or sets suggested signer's title (for example, Manager).
    /// Default value for this property is <b>empty string</b><ms> (<see cref="string.Empty"/>)</ms>.
    /// </summary>
    public String getSignerTitle()
    {
        String result = (String)mParent.getDirectShapeAttrInternal(ShapeAttr.SIG_SETUP_SUGG_SIGNER_2);
        return (result != null) ? result : "";
    }
    public void setSignerTitle(String value)
    {
        if (!msString.equals(getSignerTitle(), value))
        {
            if (StringUtil.hasChars(value))
                mParent.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SUGG_SIGNER_2, value);
            else
                mParent.removeShapeAttrInternal(ShapeAttr.SIG_SETUP_SUGG_SIGNER_2);
            updateShapeImage();
        }
    }

    /// <summary>
    /// Gets or sets suggested signer's e-mail address.
    /// Default value for this property is <b>empty string</b><ms> (<see cref="string.Empty"/>)</ms>.
    /// </summary>
    public String getEmail()
    {
        String result = (String)mParent.getDirectShapeAttrInternal(ShapeAttr.SIG_SETUP_SUGG_SIGNER_EMAIL);
        return (result != null) ? result : "";
    }
    public void setEmail(String value)
    {
        if (StringUtil.hasChars(value))
            mParent.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SUGG_SIGNER_EMAIL, value);
        else
            mParent.removeShapeAttrInternal(ShapeAttr.SIG_SETUP_SUGG_SIGNER_EMAIL);
    }

    /// <summary>
    /// Gets or sets a value indicating that default instructions is shown in the Sign dialog.
    /// Default value for this property is <c>true</c>.
    /// </summary>
    public boolean getDefaultInstructions() { return !(/*boolean*/Boolean)mParent.fetchShapeAttrInternal(ShapeAttr.SIG_SETUP_SIGN_INST_SET); }
    public void setDefaultInstructions(boolean value)
    {
        mParent.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SIGN_INST_SET, !value);
        if (value)
            mParent.removeShapeAttrInternal(ShapeAttr.SIG_SETUP_SIGN_INST);
    }

    /// <summary>
    /// Gets or sets instructions to the signer that are displayed on signing the signature line.
    /// This property is ignored if <see cref="DefaultInstructions"/> is set.
    /// Default value for this property is <b>empty string</b><ms> (<see cref="string.Empty"/>)</ms>.
    /// </summary>
    public String getInstructions()
    {
        String result = (String)mParent.getDirectShapeAttrInternal(ShapeAttr.SIG_SETUP_SIGN_INST);
        return (result != null) ? result : "";
    }
    public void setInstructions(String value)
    {
        if (StringUtil.hasChars(value))
            mParent.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SIGN_INST, value);
        else
            mParent.removeShapeAttrInternal(ShapeAttr.SIG_SETUP_SIGN_INST);
    }

    /// <summary>
    /// Gets or sets a value indicating that the signer can add comments in the Sign dialog.
    /// Default value for this property is <c>false</c>.
    /// </summary>
    public boolean getAllowComments() { return (/*boolean*/Boolean)mParent.fetchShapeAttrInternal(ShapeAttr.SIG_SETUP_ALLOW_COMMENTS); }
    public void setAllowComments(boolean value) { mParent.setShapeAttrInternal(ShapeAttr.SIG_SETUP_ALLOW_COMMENTS, value); }

    /// <summary>
    /// Gets or sets a value indicating that sign date is shown in the signature line.
    /// Default value for this property is <c>true</c>.
    /// </summary>
    public boolean getShowDate() { return (/*boolean*/Boolean)mParent.fetchShapeAttrInternal(ShapeAttr.SIG_SETUP_SHOW_SIGN_DATE); }
    public void setShowDate(boolean value) { mParent.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SHOW_SIGN_DATE, value); }

    /// <summary>
    /// Gets or sets identifier for this signature line.
    /// <p>This identifier can be associated with a digital signature, when signing document using <see cref="DigitalSignatureUtil"/>.
    /// This value must be unique and by default it is randomly generated new Guid
    /// <ms> (<see cref="Guid.NewGuid"/>)</ms>
    /// <cpp> (<see cref="Guid.NewGuid"/>)</cpp>.</p>
    /// </summary>
    //JAVA: property accessor is internalized
	Guid getIdInternal()
    {
        String sigSetupId = (String)mParent.getDirectShapeAttrInternal(ShapeAttr.SIG_SETUP_ID);
        return new Guid(sigSetupId);
    }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.UUID getId() { return Guid.toJava(getIdInternal()); }
    //JAVA: property accessor is internalized
	void setIdInternal(Guid value)
    {
        mParent.setShapeAttrInternal(ShapeAttr.SIG_SETUP_ID, value.toString("B").toUpperCase());
    }
	//JAVA-added public wrapper for internalized property accessor
	public void setId(java.util.UUID value) { setIdInternal(Guid.fromJava(value)); }

    /// <summary>
    /// Gets or sets signature provider identifier for this signature line.
    /// Default value is "{00000000-0000-0000-0000-000000000000}".
    /// </summary>
    /// <remarks>
    /// <para>The cryptographic service provider (CSP) is an independent software module that actually performs
    /// cryptography algorithms for authentication, encoding, and encryption. MS Office reserves the value
    /// of {00000000-0000-0000-0000-000000000000} for its default signature provider.</para>
    /// <para>The GUID of the additionally installed provider should be obtained from the documentation shipped with the provider.</para>
    /// <para>In addition, all the installed cryptographic providers are enumerated in windows registry.
    /// It can be found in the following path: HKLM\SOFTWARE\Microsoft\Cryptography\Defaults\Provider.
    /// There is a key name "CP Service UUID" which corresponds to a GUID of signature provider.</para>
    /// </remarks>
    //JAVA: property accessor is internalized
	Guid getProviderIdInternal()
    {
        String sigSetupProvId = (String)mParent.getDirectShapeAttrInternal(ShapeAttr.SIG_SETUP_PROV_ID);
        return new Guid(sigSetupProvId);
    }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.UUID getProviderId() { return Guid.toJava(getProviderIdInternal()); }
    //JAVA: property accessor is internalized
	void setProviderIdInternal(Guid value)
    {
        mParent.setShapeAttrInternal(ShapeAttr.SIG_SETUP_PROV_ID, value.toString("B").toUpperCase());
    }
	//JAVA-added public wrapper for internalized property accessor
	public void setProviderId(java.util.UUID value) { setProviderIdInternal(Guid.fromJava(value)); }

    /// <summary>
    /// Digital signature used to sign this signature line or <c>null</c> if signature line is not signed.
    /// </summary>
    /// <remarks>
    /// Don't be confused here. DigitalSignature and SignatureLine are different things.
    /// SignatureLine is just placeholder that can be signed and till it's not signed this property is null.
    /// DigitalSignature is real digital signature that signs document content and linked to this SignatureLine.
    ///
    /// AM.  I think this won't be accessed often, I didn't implement caching.
    /// </remarks>
    DigitalSignature getDigitalSignature()
    {
        Document doc = ((Document)mParent.getDocument());
        String setupId = (String)mParent.getDirectShapeAttrInternal(ShapeAttr.SIG_SETUP_ID);

        return doc.getDigitalSignatures().getBySetupId(setupId);
    }

    /// <summary>
    /// Indicates that signature line is signed by digital signature.
    /// </summary>
    public boolean isSigned() { return getDigitalSignature() != null; }

    /// <summary>
    /// Indicates that signature line is signed by digital signature and this digital signature is valid.
    /// </summary>
    public boolean isValid() { return isSigned() && getDigitalSignature().isValid(); }

    /// <summary>
    /// Returns image bytes depending on signature line state.
    /// </summary>
    byte[] getImageBytes() throws Exception
    {
        if (isValid())
        {
            // Signed and valid signature line.
            byte[] image = getDigitalSignature().getImageBytesValid();
            return image;
        }
        else if (isSigned())
        {
            throw new UnsupportedOperationException("FOSS");
        }
        else
        {
            // Not signed signature line. Return parent's image bytes.
            return mParent.getImageData().getImageBytes();
        }
    }

    /// <summary>
    /// Returns parent shape of this signature line.
    /// </summary>
    Shape getParent() { return mParent; }

    private /*final*/ Shape mParent;
}


