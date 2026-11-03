// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/04/2012 by Serkov Igor

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Writes 'o:signatureline' sub-element of v:shape.
/// </summary>
class VmlSignatureLinesWriter
{

    VmlSignatureLinesWriter(NrxXmlBuilder builder)
    {
        mBuilder = builder;
    }

    /// <summary>
    /// Add 'o:signatureline' related attribute.
    /// </summary>
    /// <param name="key">Attribute key.</param>
    /// <param name="value">Attribute value.</param>
    void addAttribute(int key, Object value)
    {
        mAttrCount++;
        switch (key)
        {
            case ShapeAttr.SIG_SETUP_ADDL_XML:
                mAddlXml = (String) value;
                break;
            case ShapeAttr.SIG_SETUP_ALLOW_COMMENTS:
                mAllowComments = (/*boolean*/Boolean) value;
                break;
            case ShapeAttr.SIG_SETUP_ID:
                mId = (String) value;
                break;
            case ShapeAttr.IS_SIGNATURE_LINE:
                mIsSignatureLine = (/*boolean*/Boolean) value;
                break;
            case ShapeAttr.SIG_SETUP_PROV_ID:
                mProvId = (String) value;
                break;
            case ShapeAttr.SIG_SETUP_SHOW_SIGN_DATE:
                mShowDate = (/*boolean*/Boolean) value;
                break;
            case ShapeAttr.SIG_SETUP_SIGN_INST:
                mInstructions = (String) value;
                break;
            case ShapeAttr.SIG_SETUP_PROV_URL:
                mProvUrl = (String) value;
                break;
            case ShapeAttr.SIG_SETUP_SUGG_SIGNER:
                mSuggestedSigner = (String) value;
                break;
            case ShapeAttr.SIG_SETUP_SUGG_SIGNER_2:
                mSuggestedSigner2 = (String) value;
                break;
            case ShapeAttr.SIG_SETUP_SUGG_SIGNER_EMAIL:
                mSuggestedSignereMail = (String) value;
                break;
            case ShapeAttr.SIG_SETUP_SIGN_INST_SET:
                mInstructionsSet = (/*boolean*/Boolean) value;
                break;
            default:
                return;
        }
    }

    /// <summary>
    /// Write 'o:signatureline' element.
    /// </summary>
    void write()
    {
        if(mAttrCount == 0)
            return;
        mBuilder.startElement("o:signatureline");
        mBuilder.writeAttribute("o:addlxml", mAddlXml);
        mBuilder.writeAttribute("allowcomments", mAllowComments);
        mBuilder.writeAttribute("id", mId);
        mBuilder.writeAttribute("issignatureline", mIsSignatureLine);
        mBuilder.writeAttribute("provid", mProvId);
        mBuilder.writeAttribute("showsigndate", mShowDate);
        mBuilder.writeAttribute("o:signinginstructions", mInstructions);
        mBuilder.writeAttribute("signinginstructionsset", mInstructionsSet);
        mBuilder.writeAttribute("o:sigprovurl", mProvUrl);
        mBuilder.writeAttribute("o:suggestedsigner", mSuggestedSigner);
        mBuilder.writeAttribute("o:suggestedsigner2", mSuggestedSigner2);
        mBuilder.writeAttribute("o:suggestedsigneremail", mSuggestedSignereMail);
        mBuilder.endElement();
    }


    private /*final*/ NrxXmlBuilder mBuilder;

    private String mAddlXml;
    private boolean mAllowComments;
    private String mId;
    private String mProvId;
    private boolean mShowDate;
    private String mInstructions;
    private String mProvUrl;
    private String mSuggestedSigner;
    private String mSuggestedSigner2;
    private String mSuggestedSignereMail;
    private boolean mIsSignatureLine;
    private boolean mInstructionsSet;

    private int mAttrCount = 0;
}

