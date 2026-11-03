package com.aspose.ms.System.Security.Cryptography.Xml;

import com.aspose.ms.System.Security.Cryptography.X509Certificates.X509Certificate2;
import com.aspose.ms.System.msString;
import com.aspose.ms.java.xml.XmlUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains handy methods to configure native {@link javax.xml.crypto.dsig.XMLSignature}.
 */
class SignedXmlHelper
{
    /**
     * Creates and configures native {@link XMLObject}s.
     * It skips {@link DataObject}s which represent SignatureProperty elements.
     *
     * @param factory
     * @param objects     the list to add to.
     * @param dataObjects represent parameters for the native {@link XMLObject}
     */
    public static void addDataObjects(XMLSignatureFactory factory, List<XMLObject> objects, List<DataObject> dataObjects)
    {
        // objects
        for (DataObject dob : dataObjects)
        {
            if (dob.getData() != null && dob.getData().getLength() > 0)
            {
                // don't add <SignatureProperty> objects, Java will add it itself, but at the end we will have to replace it with <Object>
                if (isParentSignatureProperty(dob.getData().item(0)))
                    continue;

                List<DOMStructure> content = new ArrayList<DOMStructure>();
                for (int i = 0; i < dob.getData().getLength(); i++)
                    content.add(new DOMStructure(dob.getData().item(i)));

                XMLObject xmlObj = factory.newXMLObject(content, null, null, null);
                objects.add(xmlObj);
            }
        }
    }

    /**
     * Check the node is a child of <SignatureProperty>.
     *
     * @param node to be checked
     * @return true if the node is a child of <SignatureProperty>.
     */
    private static boolean isParentSignatureProperty(Node node)
    {
        return node != null && node.getParentNode() != null && "SignatureProperty".equals(node.getParentNode().getNodeName());
    }

    /***
     * Creates native {@link javax.xml.crypto.dsig.Reference}s with type "http://www.w3.org/2000/09/xmldsig#Object".
     * These references can be resolved within the document by {@link com.aspose.ms.java.xml.security.dereferencer.InDocURIDereferencer}.
     *
     * @param factory
     * @param nativeReferences the list to add to.
     * @param dataObjects represent parameters for the native {@link javax.xml.crypto.dsig.Reference}
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     */
    public static void addInDocReferences(XMLSignatureFactory factory, List<javax.xml.crypto.dsig.Reference> nativeReferences, List<DataObject> dataObjects)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException
    {
        Transform c14NTransform = factory.newTransform(CanonicalizationMethod.INCLUSIVE, (TransformParameterSpec) null);
        DigestMethod digestMethod = factory.newDigestMethod(DigestMethod.SHA1, null);

        for (DataObject dob : dataObjects)
        {
            if (dob.getData() == null)
            {
                javax.xml.crypto.dsig.Reference nativeReference = factory.newReference(msString.format("#{0}", dob.getId()),
                        digestMethod,
                        Arrays.asList(c14NTransform),
                        "http://www.w3.org/2000/09/xmldsig#Object",
                        null);
                nativeReferences.add(nativeReference);
            }
        }
    }

    /**
     * Creates and configure a native {@link javax.xml.crypto.dsig.keyinfo.KeyInfo} based on our own KeyInfo implementation.
     * At the moment only one (first in the chain) {@link X509Certificate} is used. We will improve it when we add appropriate tests.
     *
     * @param keyInfoFactory
     * @param factory
     * @param keyInfo        parameters which can be used to configure the native class.
     * @return configured {@link javax.xml.crypto.dsig.keyinfo.KeyInfo} instance.
     * @throws KeyException
     */
    public static javax.xml.crypto.dsig.keyinfo.KeyInfo createNativeKeyInfo(KeyInfoFactory keyInfoFactory, XMLSignatureFactory factory, KeyInfo keyInfo) throws KeyException
    {
        javax.xml.crypto.dsig.keyinfo.KeyInfo ki = null;
        if (keyInfo != null)
        {
            // There is always at least one certificate exists
            X509Certificate cert = X509Certificate2.toJava(keyInfo.getX509Certificates().get(0));

            KeyInfoFactory kif = factory.getKeyInfoFactory();
            List x509Content = new ArrayList();
            x509Content.add(cert);

            List x509Datas = new ArrayList();
            x509Datas.add(keyInfoFactory.newKeyValue(cert.getPublicKey()));
            x509Datas.add(kif.newX509Data(x509Content));
            x509Datas.addAll(keyInfo.getX509IssuerSerials());
            ki = kif.newKeyInfo(x509Datas);
        }
        return ki;
    }

    /**
     * Creates and configures a list of native {@link javax.xml.crypto.dsig.Reference}s based on our own {@link Reference}s
     *
     * @param factory
     * @param nativeReferences the list to add newly created references to.
     * @param references       our own references which are used to configure native references.
     * @throws Exception
     */
    public static void addReferences(XMLSignatureFactory factory, List<javax.xml.crypto.dsig.Reference> nativeReferences, List<Reference> references) throws Exception
    {
        // At the moment only following Transforms and digestMethod are being used
        Transform c14NTransform = factory.newTransform(CanonicalizationMethod.INCLUSIVE, (TransformParameterSpec) null);
        List<Transform> transformList = Arrays.asList(c14NTransform);
        DigestMethod digestMethod = factory.newDigestMethod(DigestMethod.SHA1, null);

        for (Reference ref : references)
        {
            List<Transform> transforms = ref.getUri().endsWith(".xml") && ref.getStream().getLength() > 0 ? transformList : null;
            javax.xml.crypto.dsig.Reference nativeReference = factory.newReference(ref.getUri(), digestMethod, transforms, null, null);
            nativeReferences.add(nativeReference);
        }
    }

    /**
     * Move the <Signature> tag at the top of the document to make it look the same as in .NET.
     *
     * @param doc which contains the <Signature> tag.
     */
    public static void moveJavaSignatureInfoAtTheTop(Element doc)
    {
        Node firstObject = doc.getFirstChild();
        Node firstObjectParent = firstObject.getParentNode();

        Node javaNewSignatureElement = doc.getLastChild();
        NodeList javaNewSignatureChildren = javaNewSignatureElement.getChildNodes();

        for (int i = 0; i < javaNewSignatureChildren.getLength(); i++)
        {
            Element item = (Element) javaNewSignatureChildren.item(i);
            Node signatureItem = item.cloneNode(true);
            firstObjectParent.insertBefore(signatureItem, firstObject);
        }
        firstObjectParent.removeChild(javaNewSignatureElement);
    }

    /**
     * Replaces <SignatureProperty> tag with <Object>.
     * <p>
     * Since XmlDsigSignerOdt.finalize() expects to see
     * <Object>
     * <dc:date>...</dc:date>
     * </Object>
     * <p>
     * But Java creates
     * <SignatureProperty>
     * <dc:date>...</dc:date>
     * </SignatureProperty>
     * <p>
     * We have to move all <SignatureProperty> children under <Object> and remove <SignatureProperty> tag.
     *
     * @param document    signature document.
     * @param dataObjects the list to search for "SignatureProperty" object in.
     */
    public static void replaceSignaturePropertyWithObject(Element document, List<DataObject> dataObjects)
    {
        // check if <SignatureProperty> had been created by Aspose.Words.
        // If it was created then we have to remove the one generated by Java and move its children inside <Object>
        NodeList nonJavaSignaturePropertyChildren = getNonJavaSignaturePropertyChildren(dataObjects);
        if (nonJavaSignaturePropertyChildren != null && nonJavaSignaturePropertyChildren.getLength() > 0)
        {
            document.removeChild(document.getElementsByTagName("SignatureProperty").item(0));
            Element obj = document.getOwnerDocument().createElement("Object");
            document.appendChild(obj);
            for (int i = 0; i < nonJavaSignaturePropertyChildren.getLength(); i++)
                obj.appendChild(nonJavaSignaturePropertyChildren.item(i));
        }
    }

    /**
     * Returns the children of the first found <SignatureProperty> element.
     *
     * @param dataObjects the list to search in.
     * @return the children of the first found <SignatureProperty> element.
     */
    private static NodeList getNonJavaSignaturePropertyChildren(List<DataObject> dataObjects)
    {
        for (DataObject dob : dataObjects)
        {
            if (dob.getData() != null && dob.getData().getLength() > 0)
            {
                if (isParentSignatureProperty(dob.getData().item(0)))
                    return dob.getData();
            }
        }
        return null;
    }

    /**
     * Determine whether this xml has external resources or not.
     *
     * @return true if ther are any external references.
     */
    public static boolean hasExternalReferences(Element value)
    {
        if (value == null)
            return false;

        NodeList nl = value.getElementsByTagName("Reference");
        for (int i = 0; i < nl.getLength(); i++)
        {
            Node n = nl.item(i);
            String uri = XmlUtils.getAttribute(n, "URI");

            // it means that this XML has external References either in zip storage or in ObjectPool storage.
            // We should keep it to prevent verifying references  with xmlsecutity libs itlself.
            // so we can verify them ourselves in XmlDsig later

            // If there are references to ZIP storage or to ObjectPool we return true.
            if (uri.contains(".") && !uri.contains(":") && !uri.startsWith("/") || uri.contains("ObjectPool"))
                return true;
        }

        return false;
    }
}
