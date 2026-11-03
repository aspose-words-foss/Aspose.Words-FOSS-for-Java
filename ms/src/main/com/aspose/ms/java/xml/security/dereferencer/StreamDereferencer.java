package com.aspose.ms.java.xml.security.dereferencer;

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Security.Cryptography.Xml.Reference;
import com.aspose.ms.java.IO.NetInputStream;

import javax.xml.crypto.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts one of the given references' stream into OctetStreamData which can be used in XMLSignature.
 */
class StreamDereferencer implements IDereferencer
{
    @Override
    public boolean canDereference(URIReference reference, XMLCryptoContext context)
    {
        Reference ref;
        return mReferences != null && !mReferences.isEmpty()
                && reference != null
                && ((ref = mReferences.get(reference.getURI())) != null)
                && ref.getStream() != null;
    }

    @Override
    public Data dereference(URIReference uriReference, XMLCryptoContext context) throws URIReferenceException
    {
        Reference reference = mReferences.get(uriReference.getURI());
        Stream stream = reference.getStream();
        return new OctetStreamData(new NetInputStream(stream));
    }

    /**
     * Creates StreamDereferencer which uses the given references.
     *
     * @param references which have been resolved earlier.
     */
    public StreamDereferencer(List<Reference> references)
    {
        mReferences = new HashMap<String, Reference>(references.size());
        for (Reference ref : references)
            mReferences.put(ref.getUri(), ref);
    }

    private final Map<String, Reference> mReferences;
}
