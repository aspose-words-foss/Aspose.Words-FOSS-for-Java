package com.aspose.ms.java.xml.security;

import org.w3c.dom.Element;

import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.TransformService;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Comparator;

/**
 * Implementation of relationship transformation.
 * See com.aspose.words.RelationshipTransform and ISO29500-2 13.2.4.24 Relationships Transform Algorithm.
 * URI http://schemas.openxmlformats.org/package/2006/RelationshipTransform
 *
 * An example of the implementation can be found here
 * https://github.com/tuanpt/iSign/blob/master/i/src/ooxml/RelationshipTransformService.java
 *
 * WARNING:
 * This class is intentionally empty.
 *
 * The weirdest thing is that in order to sign document <code>signature.sign(signContext)</code> properly
 * we have to create a reference with two types of transformations.
 * <code>refs.add(factory.newReference(r.getUri(), digestMethod, Arrays.asList(c14NTransform, relationTransform), r.getType(), null));</code>
 *
 * If we use just c14NTransform or if we change the order of those transformations then digest won't be calculated correctly.
 *
 * For the testing I used iSign's version of this class
 * Thus while debugging I realized that JDK XmlDsig engine even doesn't call implemented <code>transform</code> method.
 *
 * So I left all the methods empty. It easily could be a bug of JDK XmlDsig engine.
 * All tests pass so we should wait any bug reports from the users.
 */
public class RelationshipTransformService extends TransformService implements Comparator<Element>
{
    @Override
    public int compare(Element o1, Element o2)
    {
        return 0;
    }

    @Override
    public void init(TransformParameterSpec params) throws InvalidAlgorithmParameterException
    {
    }

    @Override
    public void marshalParams(XMLStructure parent, XMLCryptoContext context) throws MarshalException
    {
    }

    @Override
    public void init(XMLStructure parent, XMLCryptoContext context) throws InvalidAlgorithmParameterException
    {
    }

    @Override
    public AlgorithmParameterSpec getParameterSpec()
    {
        return null;
    }

    @Override
    public Data transform(Data data, XMLCryptoContext context) throws TransformException
    {
        return null;
    }

    @Override
    public Data transform(Data data, XMLCryptoContext context, OutputStream os) throws TransformException
    {
        return null;
    }

    @Override
    public boolean isFeatureSupported(String feature)
    {
        return false;
    }

    public static final String TRANSFORM_URI = "http://schemas.openxmlformats.org/package/2006/RelationshipTransform";
}
