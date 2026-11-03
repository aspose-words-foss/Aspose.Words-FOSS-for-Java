package com.aspose.ms.java.xml.security.dereferencer;

import com.aspose.ms.System.Security.Cryptography.Xml.Reference;
import org.w3c.dom.Element;

import javax.xml.crypto.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class helps to find XML objects which Basic Java {@link URIDereferencer} cannot find.
 * SmartDereferencer uses:
 * 1. InDocURIDereferencer for objects which have Id starting with '#'.
 * 2. StreamDereferencer for external files. References are resolved earlier in Aspose.Words code.
 * 3. Basic Java {@link URIDereferencer} (e.g. {@link org.jcp.xml.dsig.internal.dom.DOMURIDereferencer}) for other kind of references.
 */
public class SmartDereferencer implements URIDereferencer
{
    @Override
    public Data dereference(URIReference uriReference, XMLCryptoContext context) throws URIReferenceException
    {
        if (uriReference == null || context == null)
            throw new IllegalArgumentException("URIReference and XMLCryptoContext cannot be null");

        // call one-by-one to find which one can handle this reference.
        for (IDereferencer dereferencer : mDereferencers)
            if (dereferencer.canDereference(uriReference, context))
                return dereferencer.dereference(uriReference, context);

        throw new URIReferenceException("None of existing dereferencer can resolve the reference=" + uriReference.getURI() + ", type=" + uriReference.getType());
    }

    /**
     * Configures SmartDereferencer with Document and References' stream (if they exist)
     *
     * @param xmlDocument document to search in.
     * @param references  resolved references. Can be empty.
     */
    public SmartDereferencer(Element xmlDocument, List<Reference> references)
    {
        this(xmlDocument);
        mDereferencers.add(new StreamDereferencer(references));
    }

    /**
     * Configures SmartDereferencer with Document
     *
     * @param xmlDocument document to search in.
     */
    public SmartDereferencer(Element xmlDocument)
    {
        mDereferencers.add(new BaseDereferencer());
        mDereferencers.add(new InDocURIDereferencer(xmlDocument));
    }

    private final List<IDereferencer> mDereferencers = new ArrayList<IDereferencer>(3);
}
