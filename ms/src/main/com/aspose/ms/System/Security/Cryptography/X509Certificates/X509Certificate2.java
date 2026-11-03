// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//07/02/12 by Durin Vyacheslav

package com.aspose.ms.System.Security.Cryptography.X509Certificates;


import com.aspose.ms.System.DateTime;
import com.aspose.ms.java.util.Base64;
import com.aspose.ms.java.logging.Log;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Java implementation of
 * http://msdn.microsoft.com/ru-ru/library/system.security.cryptography.x509certificates.x509certificate2.aspx
 */
public class X509Certificate2
{
    public X509Certificate2(String pathToCert, String password, int flag)
    {
        this(pathToCert, password);
    }

    public X509Certificate2(String pathToCert, String password)
    {
        InputStream input = null;

        try
        {
            input = new FileInputStream(pathToCert);

            ks = KeyStore.getInstance(KEY_STORE_INSTANCE);
            ks.load(input, password.toCharArray());

            List<String> aliases = getCertificateAliases();
            // WORDSANDROID-207. Aliases can be stored at random order.
            // So we are looking for the alias which represents a certificate with some private key
            for (String a : aliases)
            {
                this.alias = a;
                this.privateKey = (PrivateKey) ks.getKey(alias, password.toCharArray());
                if (privateKey != null)
                    break;
            }

            X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
            this.bytes = cert.getEncoded(); // ASN.1 DER format
            certificate = cert;
        }
        catch (Exception e)
        {
            Log.warn(e);
        }
        finally
        {
            if (input != null)
            {
                try
                {
                    input.close();
                }
                catch (Exception ee)
                {
                    Log.warn(ee);
                }
            }
        }
    }

    public X509Certificate2(byte[] cert)
    {
        Import(cert);
    }

    public BigInteger getSerialNumber()
    {
        return certificate.getSerialNumber();
    }

    private void Import(byte[] bytes)
    {
        if (bytes == null || bytes.length < 1)
            throw new IllegalArgumentException("Bytes array cannot be null or empty");

        this.bytes = new byte[bytes.length];
        System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);

        certificate = toJava(this);
    }


    public void reset()
    {
        this.alias = null;
        this.bytes = null;
        this.certificate = null;
        this.ks = null;
        this.privateKey = null;
    }

    public String getNameInfo(int simpleName, boolean b)
    {
        if (certificate == null)
            return null;

        if (X509NameType.SIMPLE_NAME == simpleName)
            return getSimpleName(CN);

        if (X509NameType.EMAIL_NAME == simpleName)
            return getSimpleName(E);

        return null;
    }

    private String getSimpleName(String key)
    {
        String[] items = getSubject().split(COMMA);
        String cn = null;
        for (int i = 0; i < items.length; i++)
            if (items[i].startsWith(key))
                cn = items[i];

        if (cn == null)
            return EMPTY;

        return cn.split(EQ)[1];
    }

    public String getIssuer()
    {
        if (certificate == null)
            return null;

        return convertToDotNetNames(certificate.getIssuerDN().getName());
    }

    public String getSubject()
    {
        if (certificate == null)
            return null;

        return convertToDotNetNames(certificate.getSubjectDN().getName());
    }

    public byte[] getEncoded()
    {
        return bytes;
    }

    public X509Certificate[] getCertificateChain()
    {
        if (mCertificateChain != null)
            return mCertificateChain;

        try
        {
            List<X509Certificate> list = new ArrayList<X509Certificate>();
            Certificate[] certificates = ks.getCertificateChain(this.alias);
            for (int i = 0, length = certificates == null ? 0 : certificates.length; i < length; i++)
                list.add((X509Certificate) certificates[i]);

            mCertificateChain = list.toArray(new X509Certificate[list.size()]);
            return mCertificateChain;
        }
        catch (Exception e)
        {
            Log.warn(e);
        }
        return null;
    }

    public void setCertificateChain(Certificate[] chain)
    {
        mCertificateChain = new X509Certificate[chain.length];
        System.arraycopy(chain, 0, mCertificateChain, 0, chain.length);
    }

    public static X509Certificate2 fromJava(X509Certificate certificate)
    {
        try
        {
            return new X509Certificate2(certificate.getEncoded());
        }
        catch (Exception ex)
        {
            Log.error(ex);
        }
        return null;
    }

    public static X509Certificate toJava(X509Certificate2 cert)
    {
        try
        {
            return createJavaCertificate(cert.getEncoded());
        }
        catch (Exception e)
        {
            Log.warn(e);
        }
        return null;
    }

    private static X509Certificate createJavaCertificate(byte[] certBytes) throws Exception
    {
        if (certBytes == null || certBytes.length < 1)
            return null;

        ByteArrayInputStream bais = new ByteArrayInputStream(preparePemBytes(certBytes));

        CertificateFactory cf = CertificateFactory.getInstance(X_509);
        X509Certificate x509cert = (X509Certificate) cf.generateCertificate(bais);

        bais.close();
        return x509cert;
    }

    private List<String> getCertificateAliases() throws KeyStoreException
    {
        Enumeration<String> en = ks.aliases();
        List<String> aliases = new ArrayList<String>();
        while (en.hasMoreElements())
            aliases.add(en.nextElement());

        return aliases;
    }

    /**
     * .NET expects to see different keys sometime.
     */
    private String convertToDotNetNames(String name)
    {
        if (name == null || EMPTY.equals(name))
            return EMPTY;

        name = name.replace(EMAILADDRESS, E);
        name = name.replace(ST, S);
        return name;
    }

    private static byte[] preparePemBytes(byte[] certBytes)
    {
        try
        {
            StringBuilder pem = new StringBuilder();

            String cert = new String(certBytes).replaceAll(WHITE_SPACE, EMPTY).replaceAll(CARRIEGE_RETURN, EMPTY).replaceAll(LINE_FEED, EMPTY).replaceAll(TAB, EMPTY);
            boolean isPem = cert.contains(BEGIN_CERT) && cert.contains(END_CERT);

            if (!isPem && !isBytesBase64(cert))
                cert = Base64.encodeToString(certBytes, false);

            if (!isPem)
                pem.append(BEGIN_CERT);

            pem.append(cert);

            if (!isPem)
                pem.append(END_CERT);

            return pem.toString().getBytes();

        }
        catch (Exception e)
        {
            Log.warn(e);
        }

        return null;
    }

    private static boolean isBytesBase64(String certBytesString)
    {
        try
        {
            byte[] ascii = certBytesString.getBytes("US-ASCII");
            int length = ascii.length;
            if (length == 0)
                return true;

            for (int i = 0; i < length; i++)
            {
                if (!isBase64(ascii[i]))
                    return false;

            }
            return true;
        }
        catch (Exception e)
        {
            Log.warn(e);
        }

        return false;
    }

    private static boolean isBase64(byte octect)
    {
        return (octect == PAD || BASE64_ALPHABET[octect] != -1);
    }

    public PrivateKey getPrivateKey()
    {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey pk)
    {
        privateKey = pk;
    }

    public boolean hasPrivateKey()
    {
        return getPrivateKey() != null;
    }

    public DateTime getNotAfter()
    {
        return DateTime.fromJava(certificate.getNotAfter());
    }

    // returns Java's backing certificate
    public X509Certificate getJavaCertificate()
    {
        if (certificate == null)
            certificate = toJava(this);

        return certificate;
    }

    private X509Certificate certificate;
    private byte[] bytes;
    private PrivateKey privateKey;
    private String alias;
    private KeyStore ks;
    private X509Certificate[] mCertificateChain;

    private static final String X_509 = "X.509";
    private static String EQ = "=";
    private static String COMMA = ",";
    private static final String KEY_STORE_INSTANCE = "PKCS12";
    private static final String CN = "CN=";
    private static final String E = "E=";
    private static final String EMAILADDRESS = "EMAILADDRESS=";
    private static final String S = "S=";
    private static final String ST = "ST=";

    private static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----\n";
    private static final String END_CERT = "\n-----END CERTIFICATE-----";
    private static final String WHITE_SPACE = " ";
    private static final String EMPTY = "";

    private static final String TAB = "\t";
    private static final String LINE_FEED = "\n";
    private static final String CARRIEGE_RETURN = "\r";
    private static final int BASELENGTH = 255;
    private static final byte PAD = (byte) '=';
    private static final byte[] BASE64_ALPHABET = new byte[BASELENGTH];

    static
    {

        for (int i = 0; i < BASELENGTH; i++)
            BASE64_ALPHABET[i] = -1;

        for (int i = 'Z'; i >= 'A'; i--)
            BASE64_ALPHABET[i] = (byte) (i - 'A');

        for (int i = 'z'; i >= 'a'; i--)
            BASE64_ALPHABET[i] = (byte) (i - 'a' + 26);

        for (int i = '9'; i >= '0'; i--)
            BASE64_ALPHABET[i] = (byte) (i - '0' + 52);

        BASE64_ALPHABET['+'] = 62;
        BASE64_ALPHABET['/'] = 63;
    }
}
