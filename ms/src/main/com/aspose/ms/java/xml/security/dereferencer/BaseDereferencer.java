package com.aspose.ms.java.xml.security.dereferencer;

import javax.xml.crypto.*;
import javax.xml.crypto.dsig.XMLSignatureFactory;

/**
 * Class uses basic XMLSignature URIDereferencer (e.g. DOMURIDereferencer in Oracle Java 6+).
 */
public class BaseDereferencer implements IDereferencer
{
    @Override
    public Data dereference(URIReference uriReference, XMLCryptoContext context) throws URIReferenceException
    {
        // Out workflow assumes canDereference() had been called for this reference, if it hadn't then call it again.
        if (mCurrentValue == null)
        {
            if (!canDereference(uriReference, context))
                throw new IllegalStateException(getClass().getName() + " cannot dereference this kind of references. " + uriReference);
        }

        Data ret = mCurrentValue;
        mCurrentValue = null; // reset it for the next canDereference() call.
        return ret;
    }

    @Override
    public boolean canDereference(URIReference reference, XMLCryptoContext context)
    {
        try
        {
            mCurrentValue = mBaseUriDereferencer.dereference(reference, context);
        }
        catch (URIReferenceException e)
        {
            // ignore this exception
            return false;
        }
        return true;
    }

    public BaseDereferencer()
    {
        mBaseUriDereferencer = XMLSignatureFactory.getInstance().getURIDereferencer();
    }

    private final URIDereferencer mBaseUriDereferencer;
    private Data mCurrentValue;
}
