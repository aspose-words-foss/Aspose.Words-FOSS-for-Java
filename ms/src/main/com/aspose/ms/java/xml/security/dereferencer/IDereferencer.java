package com.aspose.ms.java.xml.security.dereferencer;

import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.URIReference;
import javax.xml.crypto.XMLCryptoContext;

/**
 * Common interface which is used in {@link SmartDereferencer} to dereference references by their types.
 */
interface IDereferencer extends URIDereferencer
{
    /**
     * Check if the current URIDereferencer implementation can dereference this reference.
     *
     * @param reference the give reference/
     * @param context   crypto context.
     * @return true if the current URIDereferencer implementation can dereference the given reference.
     */
    boolean canDereference(URIReference reference, XMLCryptoContext context);
}
