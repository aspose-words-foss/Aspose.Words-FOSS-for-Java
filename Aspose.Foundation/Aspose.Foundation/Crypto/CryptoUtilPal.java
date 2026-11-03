// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 19/10/2010 by Konstantin Sidorenko

package com.aspose;


import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Security.Cryptography.X509Certificates.X509Certificate2;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Text.UTF8Encoding;
import com.aspose.ms.System.msConsole;
import com.aspose.ms.System.msString;
import com.aspose.ms.java.BouncyCastle.BouncyCastleUtil;
import com.aspose.ms.java.IO.NetInputStream;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;
import com.aspose.words.FipsUnapprovedOperationException;
import org.bouncycastle.asn1.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.asymmetric.*;
import org.bouncycastle.crypto.fips.*;
import org.bouncycastle.crypto.general.RSA;
import org.bouncycastle.crypto.util.BasicEntropySourceProvider;
import org.bouncycastle.operator.AlgorithmNameFinder;
import org.bouncycastle.operator.DefaultAlgorithmNameFinder;
import org.bouncycastle.util.encoders.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.ECPrivateKey;
import java.util.Arrays;
import java.util.Enumeration;


/// <summary>
/// Implement this class in Java manually.
///
/// This class abstracts the Cryptography library.
/// </summary>
//[JavaManual("Platform abstraction for cryptography library. Manual porting by design.")]
public /*static*/ class CryptoUtilPal
{
    /* Simulation of static class by using private constructor */
    private CryptoUtilPal()
    {}

    public static void startFipsMode()
    {
        CryptoServicesRegistrar.setApprovedOnlyMode(true);
        CryptoServicesRegistrar.setSecureRandom(gFipsSecureRandom);
    }

    public static boolean isInFipsMode()
    {
        return CryptoServicesRegistrar.isInApprovedOnlyMode();
    }

    /// <summary>
    /// Converts X509 BouncyCastle certificate to dotNet native.
    /// </summary>
    public static X509Certificate2 toNativeCertificate(X509CertificateHolder certificate)
    {
        try
        {
            return new X509Certificate2(certificate.getEncoded());
        }
        catch (Exception ex)
        {
            Log.warn(ex);
        }
        return null;
    }

    /// <summary>
    /// Creates X509 BouncyCastle certificate from a byte array.
    /// </summary>
    public static X509CertificateHolder createFromByteArray(byte[] bytes)
    {
        byte[] decoded = isBytesBase64(bytes) ? Base64.decode(bytes) : bytes;
        try
        {
            return new X509CertificateHolder(decoded);
        }
        catch (Exception ex)
        {
            Log.warn(ex);
        }
        return null;
    }

    /// <summary>
    /// Creates certificate holder from a byte array.
    /// </summary>
    public static CertificateHolderInternal createHolder(byte[] bytes)
    {
        X509CertificateHolder certificate = createFromByteArray(bytes);

        CertificateHolderInternal holder = CertificateHolderInternal.create();
        holder.setCertificateBc(certificate);
        return holder;
    }

    /// <summary>
    /// Creates PAL wrapped hash algorithm.
    /// </summary>
    public static IDigest createHashAlgorithm(/*DigestAlgorithm*/int digestAlgorithm)
    {
        return new DigestWrapperPal(digestAlgorithm);
    }

    /// <summary>
    /// Creates PAL wrapped hash algorithm.
    /// </summary>
    public static IDigest createHashAlgorithm(String algorithmName)
    {
        /*DigestAlgorithm*/int digestAlgorithm = stringToDigestAlgorithm(algorithmName);
        return createHashAlgorithm(digestAlgorithm);
    }

    /// <summary>
    /// Returns digest algorithm by its name.
    /// </summary>
    public static /*DigestAlgorithm*/int stringToDigestAlgorithm(String algorithmName)
    {
        switch (gStringSwitchMap.of(algorithmName))
        {
            case /*"SHA1"*/ 0:
                return DigestAlgorithm.SHA_1;
            case /*"SHA224"*/ 1:
                return DigestAlgorithm.SHA_224;
            case /*"SHA256"*/ 2:
            case /*"http://www.w3.org/2000/09/xmldsig#sha256"*/ 3:
                return DigestAlgorithm.SHA_256;
            case /*"SHA384"*/ 4:
                return DigestAlgorithm.SHA_384;
            case /*"SHA512"*/ 5:
                return DigestAlgorithm.SHA_512;
            case /*"MD2"*/ 6:
                return DigestAlgorithm.MD_2;
            case /*"MD5"*/ 8:
                return DigestAlgorithm.MD_5;
            default:
                throw new IllegalStateException("Unsupported hash algorithm.");
        }
    }

    /// <summary>
    /// Creates HMAC with the specified hash algorithm.
    /// </summary>
    public static IMac createHMac(/*DigestAlgorithm*/int digestAlgorithm)
    {
        return new MacWrapperPal(digestAlgorithm);
    }

    /// <summary>
    /// Creates HMAC with the specified hash algorithm name.
    /// </summary>
    public static IMac createHMac(String algorithm)
    {
        /*DigestAlgorithm*/int digestAlgorithm = stringToDigestAlgorithm(algorithm);
        return createHMac(digestAlgorithm);
    }

    /// <summary>
    /// Creates HMAC with the specified hash algorithm and key.
    /// </summary>
    public static IMac createHMac(/*DigestAlgorithm*/int digestAlgorithm, byte[] key)
    {
        IMac mac = createHMac(digestAlgorithm);
        mac.init(key);

        return mac;
    }

    /// <summary>
    /// Creates HMAC with the specified hash algorithm and key.
    /// </summary>
    public static IMac createHMac(/*DigestAlgorithm*/int digestAlgorithm, String key)
    {
        UTF8Encoding encoding = new UTF8Encoding();
        byte[] keyBytes = encoding.getBytes(key);

        return createHMac(digestAlgorithm, keyBytes);
    }

    /// <summary>
    /// Creates random generator.
    /// </summary>
    public static IRandomGenerator createRandomGenerator(/*DigestAlgorithm*/int digestAlgorithm)
    {
        if (JavaDebug.ON && RandomUtil.isTestMode())
            return new PredictableRandomGeneratorPal(digestAlgorithm);

        return new RandomGeneratorWrapperPal(digestAlgorithm);
    }

    /// <summary>
    /// Verifies digital signature by certificate.
    /// </summary>
    public static boolean verifySignature(CertificateHolderInternal certificateHolder,
        /*DigestAlgorithm*/int digestAlgorithm, byte[] signedInfoBytes, byte[] signature)
    {
        AsymmetricKey publicKey = getPublicKey(certificateHolder.getCertificateBc());

        /*CryptoAlgorithm*/byte algorithm = getCryptoAlgorithm(publicKey);

        switch (algorithm)
        {
            case CryptoAlgorithm.DSA:
                return verifyDsaSignature((AsymmetricPublicKey) publicKey, digestAlgorithm, signedInfoBytes, signature);
            case CryptoAlgorithm.ECDSA:
                return verifyEcSignature((AsymmetricPublicKey) publicKey, digestAlgorithm, signedInfoBytes, signature);
            case CryptoAlgorithm.RSA:
                return verifyRsaSignature((AsymmetricRSAPublicKey)publicKey, digestAlgorithm,
                    signedInfoBytes, signature);
            default:
                throw new IllegalStateException("Unexpected CryptoAlgorithm value.");
        }
    }

    /// <summary>
    /// Generates digital signature.
    /// </summary>
    public static byte[] generateSignature(CertificateHolderInternal certificateHolder,
        byte[] digest, /*DigestAlgorithm*/int digestAlgorithm) throws Exception
    {
        AsymmetricKey privateKey = certificateHolder.getPrivateKeyBc();
        /*CryptoAlgorithm*/byte algorithm = getCryptoAlgorithm(privateKey);

        switch (algorithm)
        {
            case CryptoAlgorithm.DSA:
                return generateDsaSignature((AsymmetricDSAPrivateKey)privateKey, digest, digestAlgorithm);
            case CryptoAlgorithm.RSA:
                return generateRsaSignature((AsymmetricRSAPrivateKey)privateKey, digest, digestAlgorithm);
             case CryptoAlgorithm.ECDSA:
                return generateEcSignature((AsymmetricECPrivateKey)privateKey, digest, digestAlgorithm);
           default:
                throw new IllegalStateException("Unexpected CryptoAlgorithm value.");
        }
    }

    /// <summary>
    /// Gets public key from X509 BouncyCastle certificate.
    /// </summary>
    public static AsymmetricKey getPublicKey(X509CertificateHolder cert)
    {
        try
        {
            String algName = gAlgorithmFinder.getAlgorithmName(cert.getSignatureAlgorithm());
            if (algName.contains("ECDSA"))
                return new AsymmetricECPublicKey(FipsEC.ALGORITHM, cert.getSubjectPublicKeyInfo());
            if (algName.contains("DSA"))
                return new AsymmetricDSAPublicKey(FipsDSA.ALGORITHM, cert.getSubjectPublicKeyInfo());
            if (algName.contains("RSA"))
                return new AsymmetricRSAPublicKey(FipsRSA.ALGORITHM, cert.getSubjectPublicKeyInfo());
        }
        catch (Exception ex)
        {
            Log.warn(ex);
        }
        return null;
    }

    /// <summary>
    /// Creates Rsa with parameters from X509 public key.
    /// </summary>
    public static Rsa createRsaFromPublicKey(CertificateHolderInternal certificateHolder)
    {
        AsymmetricRSAPublicKey pubKey = (AsymmetricRSAPublicKey)getPublicKey(certificateHolder.getCertificateBc());
        return new Rsa(BouncyCastleUtil.toByteArrayUnsigned(pubKey.getModulus()), BouncyCastleUtil.toByteArrayUnsigned(pubKey.getPublicExponent()));
    }

    /// <summary>
    /// Returns Dsa parameters from X509 public key.
    /// </summary>
    public static DsaParameters getDsaParametersFromPublicKey(CertificateHolderInternal certificateHolder)
    {
        AsymmetricDSAPublicKey pubKey = (AsymmetricDSAPublicKey)getPublicKey(certificateHolder.getCertificateBc());
        DsaParameters parameters = new DsaParameters();
        parameters.setP(BouncyCastleUtil.toByteArrayUnsigned(pubKey.getDomainParameters().getP()));
        parameters.setQ(BouncyCastleUtil.toByteArrayUnsigned(pubKey.getDomainParameters().getQ()));
        parameters.setG(BouncyCastleUtil.toByteArrayUnsigned(pubKey.getDomainParameters().getG()));
        parameters.setY(BouncyCastleUtil.toByteArrayUnsigned(pubKey.getY()));
        return parameters;
    }

    /// <summary>
    /// Gets a X509 certificate serial number.
    /// </summary>
    public static String getSerialNumber(CertificateHolderInternal certificateHolder)
    {
        return certificateHolder.getCertificateBc().getSerialNumber().toString();
    }

    /// <summary>
    /// Gets a X509 certificate Issuer.
    /// </summary>
    public static String getIssuer(CertificateHolderInternal certificateHolder)
    {
        // WORDSNET-27976 There can be scenarios when .Net cert is unavailable.
        // Let's then fallback to BC cert. The signature in this case will be
        // correct while there will be  the difference in the order of written
        // OIDs and sometimes in friendly names.
        return certificateHolder.getCertificateBc().getIssuer().toString();
    }

    /// <summary>
    /// Gets a X509 certificate encoded data.
    /// </summary>
    public static byte[] getEncodedData(CertificateHolderInternal certificateHolder) throws IOException
    {
        return certificateHolder.getCertificateBc().getEncoded();
    }


    //JAVA-Deleted:
    //public static String convertToUnsecureString(SecureString securePassword)

    /// <summary>
    /// Creates certificate holder for PKCS12.
    /// </summary>
    public static CertificateHolderInternal createHolderForPkcs12(Stream storeStream, String password)
    {
        return createHolderForPkcs12(storeStream, password, null);
    }

    /// <summary>
    /// Creates certificate holder for PKCS12.
    /// </summary>
    public static CertificateHolderInternal createHolderForPkcs12(Stream storeSream, String password, String alias)
    {
        resetStream(storeSream);
        InputStream input = null;
        try
        {
            input = new NetInputStream(storeSream);
            try
            {
                return createHolderFromStore(password, input, PKCS_12, alias);
            }
            catch (IOException pkcs12Ex)
            {
                if (JavaDebug.ON)
                    msConsole.writeLine(msString.format(WARNING_STORE_TYPE, pkcs12Ex.getLocalizedMessage()));

                resetStream(storeSream);
                return createHolderFromStore(password, input, JKS, alias);
            }
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
        return null;
    }

    private static CertificateHolderInternal createHolderFromStore(String password, InputStream input, String storeType, String alias)
            throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException
    {
        if (password == null)
            throw new IllegalArgumentException(EXCEPTION_EMPTY);

        // load store
        char[] passwordChars = password.toCharArray();
        KeyStore ks = KeyStore.getInstance(storeType);
        ks.load(input, passwordChars);

        Enumeration<String> en = ks.aliases();

        // if alias is null then find the first alias which can be used to retrieve private key.
        // otherwise use alias which was provided by user.
        String certAlias = alias;
        if (certAlias == null)
        {
            while (en.hasMoreElements())
            {
                String al = en.nextElement();

                if (ks.isKeyEntry(al) && (ks.getKey(al, passwordChars) instanceof PrivateKey))
                {
                    certAlias = al;
                    break;
                }
            }
        }

        if (certAlias == null)
            throw new SecurityException("At least one certificate and its private key must be present.");

        Key key = ks.getKey(certAlias, passwordChars);
        if (key == null)
            throw new SecurityException("Private key was not found by alias '" + certAlias + "'");

        X509Certificate cert = (X509Certificate) ks.getCertificate(certAlias);

        if (key instanceof RSAPrivateKey)
            return createHolderFromRSAKey(passwordChars, ks, certAlias, cert);
        else if (key instanceof DSAPrivateKey)
            return createHolderFromDSAKey(passwordChars, ks, certAlias, cert);
        else if (key instanceof ECPrivateKey)
            return createHolderFromECKey(passwordChars, ks, certAlias, cert);

        throw new IllegalStateException(KEY_IS_NOT_SUPPORTED);
    }

    private static CertificateHolderInternal createHolderFromRSAKey(char[] passwordChars, KeyStore ks, String endAlias, X509Certificate cert)
            throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, IOException, CertificateEncodingException
    {
        RSAPrivateCrtKey pk = (RSAPrivateCrtKey) ks.getKey(endAlias, passwordChars);

        AsymmetricRSAPrivateKey parameters = new AsymmetricRSAPrivateKey(FipsRSA.ALGORITHM, pk.getModulus(), pk.getPublicExponent(),
                pk.getPrivateExponent(), pk.getPrimeP(), pk.getPrimeQ(), pk.getPrimeExponentP(), pk.getPrimeExponentQ(), pk.getCrtCoefficient());

        return createCertHolder(ks, endAlias, cert, pk, parameters);
    }

    private static CertificateHolderInternal createHolderFromDSAKey(char[] passwordChars, KeyStore ks, String endAlias, X509Certificate cert)
            throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, IOException, CertificateEncodingException
    {
        DSAPrivateKey pk = (DSAPrivateKey) ks.getKey(endAlias, passwordChars);
        AsymmetricDSAPrivateKey parameters = new AsymmetricDSAPrivateKey(FipsDSA.ALGORITHM, pk.getEncoded());
        return createCertHolder(ks, endAlias, cert, pk, parameters);
    }

    private static CertificateHolderInternal createHolderFromECKey(char[] passwordChars, KeyStore ks, String endAlias, X509Certificate cert)
            throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, IOException, CertificateEncodingException
    {
        ECPrivateKey pk = (ECPrivateKey) ks.getKey(endAlias, passwordChars);
        AsymmetricECPrivateKey parameters = new AsymmetricECPrivateKey(FipsEC.ALGORITHM, pk.getEncoded());
        return createCertHolder(ks, endAlias, cert, pk, parameters);
    }

    private static CertificateHolderInternal createCertHolder(KeyStore ks, String endAlias, X509Certificate cert,
                                                              PrivateKey pk, AsymmetricPrivateKey parameters)
            throws IOException, CertificateEncodingException, KeyStoreException
    {
        CertificateHolderInternal holder = new CertificateHolderInternal();
        holder.setCertificateBc(new X509CertificateHolder(cert.getEncoded()));
        holder.setPrivateKeyBc(parameters);

        X509Certificate2 x509Certificate2 = toNativeCertificate(holder.getCertificateBc());
        x509Certificate2.setCertificateChain(ks.getCertificateChain(endAlias));
        holder.setCertificateNative(x509Certificate2);
        holder.getCertificateNative().setPrivateKey(pk);
        return holder;
    }

    private static void resetStream(Stream storeSream)
    {
        try
        {
            storeSream.setPosition(0);
        }
        catch (Exception ex)
        {
            Log.warn(ex);
        }
    }

    /// <summary>
    /// Returns true, if a specified byte array contains only Base64 allowed characters.
    /// </summary>
    private static boolean isBytesBase64(byte[] ascii)
    {
        if (ascii == null)
            return true;

        for (int i = 0; i < ascii.length; i++)
        {
            if (ascii[i] == CR || ascii[i] == NL)
                continue;

            boolean isBase64Char = ascii[i] == PAD || ArrayUtil.contains(BASE_64_ALPHABET, ascii[i]);
            if (!isBase64Char)
                return false;
        }
        return true;
    }

    /// <summary>
    /// Returns CryptoAlgorithm enumeration by a specified key.
    /// </summary>
    public static /*CryptoAlgorithm*/byte getCryptoAlgorithm(AsymmetricKey key)
    {
        if (key instanceof AsymmetricECKey)
            return CryptoAlgorithm.ECDSA;

        // Copy of .Net behavior then null returns RSA
        return (key instanceof AsymmetricDSAKey)
                ? CryptoAlgorithm.DSA
                : CryptoAlgorithm.RSA;
    }

    /// <summary>
    /// Returns CryptoAlgorithm enumeration by the public key from the specified certificate.
    /// </summary>
    public static /*CryptoAlgorithm*/byte getCryptoAlgorithmByPublicKey(CertificateHolderInternal certificateHolder)
    {
        return getCryptoAlgorithm(getPublicKey(certificateHolder.getCertificateBc()));
    }

    /// <summary>
    /// Returns CryptoAlgorithm enumeration by the private key from the specified certificate.
    /// </summary>
    public static /*CryptoAlgorithm*/byte getCryptoAlgorithmByPrivateKey(CertificateHolderInternal certificateHolder)
    {
        return getCryptoAlgorithm(certificateHolder.getPrivateKeyBc());
    }

    /// <summary>
    /// Returns DigestAlgorithm enumeration by a specified key. 
    /// </summary>
    public static /*DigestAlgorithm*/int getDigestAlgorithm(AsymmetricKey key)
    {
        if (key instanceof AsymmetricRSAKey)
        {
            switch (((AsymmetricRSAKey) key).getModulus().bitLength())
            {
                case 1792:
                    return DigestAlgorithm.SHA_224;

                case 2048:
                    return DigestAlgorithm.SHA_256;

                case 3072:
                    return DigestAlgorithm.SHA_384;

                case 4096:
                    return DigestAlgorithm.SHA_512;

                default:
                    return DigestAlgorithm.SHA_1;
            }
        }

        return (key instanceof AsymmetricECKey) ? DigestAlgorithm.SHA_256 : DigestAlgorithm.SHA_1;
    }

    /// <summary>
    /// Returns DigestAlgorithm enumeration by the private key from the specified certificate.
    /// </summary>
    public static /*DigestAlgorithm*/int getDigestAlgorithmByPrivateKey(CertificateHolderInternal certificateHolder)
    {
        return getDigestAlgorithm(certificateHolder.getPrivateKeyBc());
    }

    /// <summary>
    /// Verifies RSA signature.
    /// </summary>
    private static boolean verifyRsaSignature(AsymmetricRSAPublicKey publicKey, /*DigestAlgorithm*/int digestAlgorithm, byte[] signedInfoBytes, byte[] signature)
    {
        try
        {
            FipsRSA.SignatureOperatorFactory<FipsRSA.PKCS1v15SignatureParameters> signatureOperatorFactory = new FipsRSA.SignatureOperatorFactory<FipsRSA.PKCS1v15SignatureParameters>();
            FipsDigestAlgorithm digestAlgorithm1 = (FipsDigestAlgorithm) DigestWrapperPal.getDigestAlgorithm(digestAlgorithm);
            FipsRSA.PKCS1v15SignatureParameters parameters = FipsRSA.PKCS1v1_5.withDigestAlgorithm(digestAlgorithm1);
            OutputVerifier<FipsRSA.PKCS1v15SignatureParameters> rsaVer = signatureOperatorFactory.createVerifier(publicKey, parameters);


            OutputStream vOut = rsaVer.getVerifyingStream();
            vOut.write(signedInfoBytes, 0, signedInfoBytes.length);
            vOut.close();
            return rsaVer.isVerified(signature);
        }
        catch (Exception e)
        {
            // Wrap obfuscated BouncyCastle exceptions.
            throw new RuntimeException("CryptoException: ", e);
        }
    }

    /// <summary>
    /// Verifies DSA signature.
    /// </summary>
    private static boolean verifyDsaSignature(AsymmetricPublicKey publicKey, /*DigestAlgorithm*/int digestAlgorithm,
                                              byte[] signedInfoBytes, byte[] signature)
    {
        try
        {
            FipsDSA.OperatorFactory signatureOperatorFactory = new FipsDSA.OperatorFactory();
            FipsDigestAlgorithm digestAlgorithm1 = (FipsDigestAlgorithm) DigestWrapperPal.getDigestAlgorithm(digestAlgorithm);
            FipsDSA.Parameters parameters = FipsDSA.DSA.withDigestAlgorithm(digestAlgorithm1);
            OutputVerifier<FipsDSA.Parameters> dsaVer = signatureOperatorFactory.createVerifier(publicKey, parameters);

            UpdateOutputStream vOut = dsaVer.getVerifyingStream();
            vOut.update(signedInfoBytes);
            vOut.flush();
            vOut.close();

            //DSTU4145Signer

            byte[] derSig = convertIeee1363ToDer(signature);
            return dsaVer.isVerified(derSig);
        }
        catch (Exception e)
        {
            // Wrap obfuscated BouncyCastle exceptions.
            throw new RuntimeException("CryptoException: ", e);
        }
    }

    /// <summary>
    /// Verifies ECDSA signature.
    /// </summary>
    private static boolean verifyEcSignature(AsymmetricPublicKey publicKey, /*DigestAlgorithm*/int digestAlgorithm,
                                              byte[] signedInfoBytes, byte[] signature)
    {
        try
        {
            FipsEC.DSAOperatorFactory signatureOperatorFactory = new FipsEC.DSAOperatorFactory();
            FipsDigestAlgorithm digestAlgorithm1 = (FipsDigestAlgorithm) DigestWrapperPal.getDigestAlgorithm(digestAlgorithm);
            FipsEC.DSAParameters parameters = FipsEC.DSA.withDigestAlgorithm(digestAlgorithm1);
            OutputVerifier<FipsEC.DSAParameters> ecVer = signatureOperatorFactory.createVerifier(publicKey, parameters);

            UpdateOutputStream vOut = ecVer.getVerifyingStream();
            vOut.update(signedInfoBytes);
            vOut.flush();
            vOut.close();

            // WORDSJAVA-2237
            // isVerified() expects objects in the DER format due to call of ASN1Primitive.fromByteArray(encoding) which reads DER objects.
            // DER always has leading length information, while e.g. CER uses end-of-contents (which is 0 tag) octets instead of providing the length of the encoded data.
            // Other tools can generate signature in other formats AW always uses DER format.
            // So if the given signature starts with 0 it needs to be converted to DER format.
            if (signature[0] == 0)
                signature = convertToDerObject(signature);

            return ecVer.isVerified(signature);
        }
        catch (Exception e)
        {
            // Wrap obfuscated BouncyCastle exceptions.
            throw new RuntimeException("CryptoException: ", e);
        }
    }

    /// <summary>
    /// Generates RSA signature. 
    /// </summary>
    private static byte[] generateRsaSignature(AsymmetricRSAPrivateKey privateKey, byte[] digest,
        /*DigestAlgorithm*/int digestAlgorithm)
    {
        try
        {
            org.bouncycastle.crypto.DigestAlgorithm algorithm = DigestWrapperPal.getDigestAlgorithm(digestAlgorithm);
            OutputSigner rsaSig = null;

            if (isInFipsMode())
            {
                int bitLength = privateKey.getModulus().bitLength();
                if (bitLength != 2048 && bitLength != 3072)
                    throw new FipsUnapprovedOperationException(
                            msString.format("You cannot use a key with size {0} for {1} in FIPS mode",
                                    bitLength,
                                    privateKey.getAlgorithm().getName()));
            }

            if (algorithm instanceof FipsDigestAlgorithm)
            {
                FipsRSA.SignatureOperatorFactory<FipsRSA.PKCS1v15SignatureParameters> signatureOperatorFactory = new FipsRSA.SignatureOperatorFactory<FipsRSA.PKCS1v15SignatureParameters>();
                rsaSig = signatureOperatorFactory.createSigner(privateKey, FipsRSA.PKCS1v1_5.withDigestAlgorithm((FipsDigestAlgorithm) algorithm)).withSecureRandom(new SecureRandom());
            }
            else
            {
                org.bouncycastle.crypto.general.RSA.SignatureOperatorFactory genFactory = new RSA.SignatureOperatorFactory<RSA.PKCS1v15SignatureParameters>();
                rsaSig = genFactory.createSigner(privateKey, org.bouncycastle.crypto.general.RSA.PKCS1v1_5.withDigestAlgorithm(algorithm));
            }

            OutputStream sOut = rsaSig.getSigningStream();
            sOut.write(digest, 0, digest.length);
            sOut.close();
            return rsaSig.getSignature();

        }
        catch (Throwable e)
        {
            // Wrap obfuscated BouncyCastle exceptions.
            throw new RuntimeException("CryptoException: ", e);
        }
    }

    /// <summary>
    /// Generates DSA signature.
    /// </summary>
    private static byte[] generateDsaSignature(AsymmetricDSAPrivateKey privateKey, byte[] digest,
        /*DigestAlgorithm*/int digestAlgorithm) throws Exception
    {
        try
        {
            org.bouncycastle.crypto.DigestAlgorithm algorithm = DigestWrapperPal.getDigestAlgorithm(digestAlgorithm);
            FipsDSA.OperatorFactory signatureOperatorFactory = new FipsDSA.OperatorFactory();
            OutputSigner digestSigner = signatureOperatorFactory.createSigner(privateKey, FipsDSA.DSA.withDigestAlgorithm((FipsDigestAlgorithm) algorithm)).withSecureRandom(new SecureRandom());
            OutputStream sOut = digestSigner.getSigningStream();
            sOut.write(digest, 0, digest.length);
            sOut.close();
            //return digestSigner.getSignature();
            return convertDerToIeee1363(digestSigner.getSignature());
        }
        catch (Exception e)
        {
            // Wrap obfuscated BouncyCastle exceptions.
            throw new RuntimeException("CryptoException: ", e);
        }
    }

    /// <summary>
    /// Generates ECDSA signature.
    /// </summary>
    private static byte[] generateEcSignature(AsymmetricECPrivateKey privateKey, byte[] digest,
        /*DigestAlgorithm*/int digestAlgorithm)
    {
        try
        {
            org.bouncycastle.crypto.DigestAlgorithm algorithm = DigestWrapperPal.getDigestAlgorithm(digestAlgorithm);
            FipsEC.DSAOperatorFactory signatureOperatorFactory = new FipsEC.DSAOperatorFactory();
            OutputSigner digestSigner = signatureOperatorFactory.createSigner(privateKey, FipsEC.DSA.withDigestAlgorithm((FipsDigestAlgorithm) algorithm)).withSecureRandom(new SecureRandom());
            OutputStream sOut = digestSigner.getSigningStream();
            sOut.write(digest, 0, digest.length);
            sOut.close();
            return digestSigner.getSignature(); // Generate DER encoded signature
        }
        catch (Exception e)
        {
            // Wrap obfuscated BouncyCastle exceptions.
            throw new RuntimeException("CryptoException: ", e);
        }
    }

    /// <summary>
    /// Converts ANS.1 encoded DER sequence to IEEE1363 sequence used in XmlDsig.
    /// </summary>
    private static byte[] convertDerToIeee1363(byte[] signature) throws Exception
    {
        ASN1InputStream input = new ASN1InputStream(signature);
        DLSequence derSequence = (DLSequence) input.readObject();

        byte[] rBytes = derSequence.getObjectAt(0).toASN1Primitive().getEncoded();
        byte[] sBytes = derSequence.getObjectAt(1).toASN1Primitive().getEncoded();

        //IEEE1363 sequence is 40 bytes length and consists of two numbers sticked together.
        final int LEN = 20;
        byte[] p1363 = new byte[2 * LEN];
        System.arraycopy(rBytes, rBytes.length - LEN, p1363, 0, LEN);
        System.arraycopy(sBytes, sBytes.length - LEN, p1363, LEN, LEN);

        return p1363;
    }

    private static byte[] convertIeee1363ToDer(byte[] signature) throws Exception
    {
        int halfLength = signature.length / 2;
        BigInteger r = new BigInteger(1, Arrays.copyOfRange(signature, 0, halfLength));
        BigInteger s = new BigInteger(1, Arrays.copyOfRange(signature, halfLength, signature.length));

        // DER encoding
        byte[] rb = r.toByteArray();
        byte[] sb = s.toByteArray();
        int off = (2 + 2) + rb.length;
        int tot = off + (2 - 2) + sb.length;
        byte[] der = new byte[tot + 2];
        der[0] = 0x30;
        der[1] = (byte) (tot & 0xff);
        der[2 + 0] = 0x02;
        der[2 + 1] = (byte) (rb.length & 0xff);
        System.arraycopy(rb, 0, der, 2 + 2, rb.length);
        der[off + 0] = 0x02;
        der[off + 1] = (byte) (sb.length & 0xff);
        System.arraycopy(sb, 0, der, off + 2, sb.length);
        return der;
    }

    private static byte[] convertToDerObject(byte[] signature) throws IOException
    {
        if (signature == null || signature.length < 1)
            return signature;

        int len = signature.length / 2;
        byte[] rBytes = new byte[len];
        byte[] sBytes = new byte[len];

        System.arraycopy(signature, 0, rBytes, 0, len);
        System.arraycopy(signature, len, sBytes, 0, len);

        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(new ASN1Integer(new BigInteger(1, rBytes)));
        v.add(new ASN1Integer(new BigInteger(1, sBytes)));

        return new DERSequence(v).getEncoded(ASN1Encoding.DER);
    }

    private static final String PKCS_12 = "PKCS12";
    private static final String JKS = "JKS";

    private static final String KEY_IS_NOT_SUPPORTED = "Key is not supported";
    private static final String EXCEPTION_EMPTY = "Password cannot be empty or null";
    private static final String WARNING_STORE_TYPE = "WARNING: We have just tried to load this store as PKCS12 and failed. Now we are going to try it as JKS one. Exception that occurred: {0}";

    private static final byte CR = (byte) '\r';
    private static final byte NL = (byte) '\n';
    private static final byte PAD = (byte) '=';
    private static /*final*/ byte[] BASE_64_ALPHABET = Encoding.getASCII().getBytes("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwx yz0123456789+/");
    private static final AlgorithmNameFinder gAlgorithmFinder = new DefaultAlgorithmNameFinder();
    public static final FipsSecureRandom gFipsSecureRandom = FipsDRBG.SHA512_HMAC.fromEntropySource(
            new BasicEntropySourceProvider(new SecureRandom(), true)).build(null, true);

    //JAVA-added for string switch emulation
    private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
            (
                    "SHA1",
                    "SHA224",
                    "SHA256",
                    "http://www.w3.org/2000/09/xmldsig#sha256",
                    "SHA384",
                    "SHA512",
                    "MD2",
                    "MD4",
                    "MD5"
            );

    static
    {
        // testEncryptSha256Aes() uses CBCwithX923 padding.
        // Common BC always pads with zeros, but FIPS pads with random bytes (which is ISO 10126)
        // I didn't manage to find a way to change it.
        // So use custom SecureRandom for tests only
        CryptoServicesRegistrar.setSecureRandom((RandomUtil.isTestMode() ? new NextIntZeroSecureRandom() : gFipsSecureRandom));
    }

    private static class NextIntZeroSecureRandom extends SecureRandom
    {
        @Override
        public int nextInt() { return 0; }
    }
}
