// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//07/02/13 by Durin Vyacheslav
package com.aspose.ms.System.Security.Cryptography.Xml;


import com.aspose.ms.System.Security.Cryptography.X509Certificates.X509Certificate2;
import com.aspose.ms.java.util.Base64;
import com.aspose.ms.java.logging.Log;
import com.aspose.ms.java.xml.security.CertificateKeySelector;
import com.aspose.ms.java.xml.security.RelationshipTransformServiceProvider;
import com.aspose.ms.java.xml.security.X509DataKeySelector;
import com.aspose.ms.java.xml.security.dereferencer.SmartDereferencer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.aspose.ms.System.Security.Cryptography.Xml.SignedXmlHelper.*;

/**
 * Java implementation of
 * http://msdn.microsoft.com/en-us/library/system.security.cryptography.xml.signedxml(v=vs.71).aspx
 */
public class SignedXml
{
    /**
     * Loads XML and checks if it has references to external resources.
     *
     * @param value XML element
     * @throws IllegalArgumentException if value is <code>null</code>
     */
    public void loadXml(Element value)
    {
        if (value == null)
            throw new IllegalArgumentException("Value cannot be null");

        mLoadedXmlDocument = value;
        mSignedInfo = new com.aspose.ms.System.Security.Cryptography.Xml.SignedInfo();
        mHasExternalReferences = hasExternalReferences(mLoadedXmlDocument);
    }

    /**
     * Validates the signature by using X509Data from the <Signature/> element.
     */
    public boolean checkSignature()
    {
        if (mLoadedXmlDocument == null)
            return false;

        DOMValidateContext validateContext = new DOMValidateContext(new X509DataKeySelector(), mLoadedXmlDocument);
        validateContext.setURIDereferencer(new SmartDereferencer(mLoadedXmlDocument));
        return validateSignature(validateContext, !mHasExternalReferences);
    }

    /**
     * Validates the signature and all the references if specified so.
     *
     * @param validateContext    all needed data
     * @param validateReferences <code>true</code> if all the references should be validated
     */
    private boolean validateSignature(DOMValidateContext validateContext, boolean validateReferences)
    {
        boolean valid;
        XMLSignature signature;
        XMLSignatureFactory factory = XMLSignatureFactory.getInstance(MECHANISM_TYPE);

        // create signature from XML document <Signature><SignedInfo>....</Signature>
        try
        {
            signature = factory.unmarshalXMLSignature(new DOMStructure(mLoadedXmlDocument));
        }
        catch (MarshalException ex)
        {
            Log.error(ex);
            return false;
        }

        // validate the signature itself.
        try
        {
            valid = signature.getSignatureValue().validate(validateContext);
        }
        catch (XMLSignatureException ex)
        {
            Log.error(ex);
            return false;
        }

        // there is no need to continue validation
        if (!valid)
            return false;

        if (validateReferences)
        {
            Iterator<javax.xml.crypto.dsig.Reference> referenceIterator = signature.getSignedInfo().getReferences().iterator();
            for (int j = 0; referenceIterator.hasNext(); j++)
            {
                javax.xml.crypto.dsig.Reference reference = referenceIterator.next();
                try
                {
                    valid = reference.validate(validateContext);
                    if (!valid)
                    {
                        System.err.println("ref[" + j + "] validity status: notValid expected digest=" + Base64.encodeToString(reference.getDigestValue(), false) + " actual digest=" + Base64.encodeToString(reference.getCalculatedDigestValue(), false));
                        return false;
                    }
                }
                catch (Exception ex)
                {
                    Log.error(ex);
                    return false;
                }
            }
        }
        return valid; // true
    }

    /**
     * Validates the signature by using the given certificate.
     *
     * @param certificate         the certificate to validate the signature.
     * @param verifySignatureOnly flag which indicates whether references should be verified or not.
     */
    public boolean checkSignature(X509Certificate2 certificate, boolean verifySignatureOnly)
    {
        if (mLoadedXmlDocument == null || certificate == null)
            return false;

        DOMValidateContext validateContext = new DOMValidateContext(new CertificateKeySelector(X509Certificate2.toJava(certificate)), mLoadedXmlDocument);
        return validateSignature(validateContext, verifySignatureOnly && !mHasExternalReferences);
    }

    /**
     * Computes detached signature.
     * The result can get by calling <code>getXml()</code> method.
     *
     * @throws IllegalStateException if SigningKey is null
     */
    public void computeSignature()
    {
        if (mSigningKey == null)
            throw new IllegalStateException("SigningKey cannot be null");

        try
        {
            KeyInfoFactory keyInfoFactory = KeyInfoFactory.getInstance(MECHANISM_TYPE);

            // create Native Factory which create native SignedInfo, KeyInfo etc based on our custom classes
            XMLSignatureFactory factory = XMLSignatureFactory.getInstance(MECHANISM_TYPE);
            DOMSignContext signContext = new DOMSignContext(mSigningKey, mLoadedXmlDocument);

            // set up Smart Dereferencer, it knows how to find objects with ID starting with '#' and external references from ODT document.
            signContext.setURIDereferencer(new SmartDereferencer(mLoadedXmlDocument, getSignedInfo().getReferences()));

            // prepare SignedInfo's references
            List<javax.xml.crypto.dsig.Reference> nativeReferences = new ArrayList<javax.xml.crypto.dsig.Reference>();
            addReferences(factory, nativeReferences, getSignedInfo().getReferences());
            // add objects which are referenced by Id with '#'
            addInDocReferences(factory, nativeReferences, getSignature().getDataObjects());

            // add other objects
            List<XMLObject> nativeDataObjects = new ArrayList<XMLObject>();
            addDataObjects(factory, nativeDataObjects, getSignature().getDataObjects());

            // Create the SignedInfo.
            CanonicalizationMethod signedInfoCanonicalizationMethod = factory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null);
            SignatureMethod newSignatureMethod = factory.newSignatureMethod(SignatureMethod.RSA_SHA1, null);
            javax.xml.crypto.dsig.SignedInfo si = factory.newSignedInfo(signedInfoCanonicalizationMethod, newSignatureMethod, nativeReferences);

            // create <KeyInfo/>
            javax.xml.crypto.dsig.keyinfo.KeyInfo ki = createNativeKeyInfo(keyInfoFactory, factory, mKeyInfo);

            // Create the XMLSignature, but don't sign it yet.
            XMLSignature signature = factory.newXMLSignature(si, ki, nativeDataObjects, getSignature().getId(), null);

            // Marshal, generate, and sign the signature.
            signature.sign(signContext);

            // In order to make it look the same as in .NET
            // SignedInfo, SignatureValue, KeyInfo are copied at the top of the document and the last <Signature> tag is deleted
            moveJavaSignatureInfoAtTheTop(mLoadedXmlDocument);

            // In order to make it look the same as in .NET
            // we replace generated <SignatureProperty/> with <Object/> which has the same content as  <SignatureProperty/> used to have.
            replaceSignaturePropertyWithObject(mLoadedXmlDocument, getSignature().getDataObjects());
        }
        catch (Exception e)
        {
            // any error should stop signing of document.
            // So just show the stacktrace.
            Log.error(e);
        }
    }

    /**
     * Emulates .NETs SignedInfo. The main purpose of it is to keep the list of references.
     * This help us avoid to looking through the document twice.
     *
     * @return SignedInfo with the reference list
     */
    public com.aspose.ms.System.Security.Cryptography.Xml.SignedInfo getSignedInfo() { return mSignedInfo; }

    /**
     * Represents <Signature /> element. Keeps Id and list of Data Objects.
     */
    public Signature getSignature() { return mSignature; }

    /**
     * Sets the key for signing
     *
     * @param signingKey
     */
    public void setSigningKey(PrivateKey signingKey) { this.mSigningKey = signingKey; }

    /**
     * Adds a new reference into SignedInfo instance.
     */
    public void addReference(com.aspose.ms.System.Security.Cryptography.Xml.Reference reference) { mSignedInfo.getReferences().add(reference); }

    /**
     * Keeps X509Certificate and other info.
     */
    public void setKeyInfo(KeyInfo keyInfo) { this.mKeyInfo = keyInfo; }

    /**
     * Returns the signed <Signature/> element. i.e. it contains <SignedInfo />
     */
    public Element getXml() { return mLoadedXmlDocument; }

    public static final String MECHANISM_TYPE = "DOM";

    /**
     * Default ctor which is used in conjunction with loadXml(Element)
     */
    public SignedXml()
    {
    }

    /**
     * Ctor with document usually is used for signing.
     */
    public SignedXml(Document doc) // doc is needed for the autoportability.
    {
        this.mSignature = new Signature();
        this.mSignedInfo = new com.aspose.ms.System.Security.Cryptography.Xml.SignedInfo();
        this.mLoadedXmlDocument = doc.getDocumentElement();
    }

    static
    {
        RelationshipTransformServiceProvider.init();
    }

    private PrivateKey mSigningKey;
    private KeyInfo mKeyInfo;
    private Signature mSignature;
    private Element mLoadedXmlDocument;
    private com.aspose.ms.System.Security.Cryptography.Xml.SignedInfo mSignedInfo;
    private boolean mHasExternalReferences;
}
