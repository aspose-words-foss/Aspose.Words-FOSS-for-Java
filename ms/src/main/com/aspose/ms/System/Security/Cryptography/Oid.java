// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//07/02/12 by Durin Vyacheslav
package com.aspose.ms.System.Security.Cryptography;


/**
 * Java implementation of
 * http://msdn.microsoft.com/en-us/library/system.security.cryptography.oid.aspx
 */
public class Oid
{

    public Oid(String oid)
    {
        if (oid == null)
            throw new IllegalArgumentException("Oid cannot be null");

        oidName = oid;
        oidVal = getValue(oid);

    }

    public String getName() { return this.oidName; }
    public String getOidVal() { return this.oidVal; }


    private String getName(String oid)
    {
        //remove suns
        if (oid == null || "".equals(oid))
            return "";

        if (oidRSA.equalsIgnoreCase(oid))
            return nameRSA;

        if (oidPkcs7Data.equalsIgnoreCase(oid))
            return namePkcs7Data;

        if (oidPkcs9ContentType.equalsIgnoreCase(oid))
            return namePkcs9ContentType;

        if (oidPkcs9MessageDigest.equalsIgnoreCase(oid))
            return namePkcs9MessageDigest;

        if (oidPkcs9SigningTime.equalsIgnoreCase(oid))
            return namePkcs9SigningTime;

        if (oidMd5.equalsIgnoreCase(oid))
            return nameMd5;

        if (oid3Des.equalsIgnoreCase(oid))
            return name3Des;

        if (oidSha1.equalsIgnoreCase(oid))
            return nameSha1;

        if (oidSubjectAltName.equalsIgnoreCase(oid))
            return nameSubjectAltName;

        if (oidNetscapeCertType.equalsIgnoreCase(oid))
            return nameNetscapeCertType;

        return oid;
    }

    private String getValue(String name)
    {
        if (name == null || "".equals(name))
            return "";

        if (nameRSA.equalsIgnoreCase(name))
            return oidRSA;

        if (namePkcs7Data.equalsIgnoreCase(name))
            return oidPkcs7Data;

        if (namePkcs9ContentType.equalsIgnoreCase(name))
            return oidPkcs9ContentType;

        if (namePkcs9MessageDigest.equalsIgnoreCase(name))
            return oidPkcs9MessageDigest;

        if (namePkcs9SigningTime.equalsIgnoreCase(name))
            return oidPkcs9SigningTime;

        if (nameMd5.equalsIgnoreCase(name))
            return oidMd5;

        if (name3Des.equalsIgnoreCase(name))
            return oid3Des;

        if (nameSha1.equalsIgnoreCase(name))
            return oidSha1;

        if (nameSubjectAltName.equalsIgnoreCase(name))
            return oidSubjectAltName;

        if (nameNetscapeCertType.equalsIgnoreCase(name))
            return oidNetscapeCertType;

        return name;
    }

    public String toString()
    {
        return oidName ;
    }

    private String oidVal;
    private String oidName;

    // Known OID/Names not defined anywhere else (by OID order)
    public static final String oidRSA = "1.2.840.113549.1.1.1";
    public static final String nameRSA = "RSA";

    public static final String oidPkcs7Data = "1.2.840.113549.1.7.1";
    public static final String namePkcs7Data = "PKCS 7 Data";

    public static final String oidPkcs9ContentType = "1.2.840.113549.1.9.3";
    public static final String namePkcs9ContentType = "Content Type";

    public static final String oidPkcs9MessageDigest = "1.2.840.113549.1.9.4";
    public static final String namePkcs9MessageDigest = "Message Digest";

    public static final String oidPkcs9SigningTime = "1.2.840.113549.1.9.5";
    public static final String namePkcs9SigningTime = "Signing Time";

    public static final String oidMd5 = "1.2.840.113549.2.5";
    public static final String nameMd5 = "md5";

    public static final String oid3Des = "1.2.840.113549.3.7";
    public static final String name3Des = "3des";

    public static final String oidSha1 = "1.3.14.3.2.26";
    public static final String nameSha1 = "sha1";

    public static final String oidSubjectAltName = "2.5.29.17";
    public static final String nameSubjectAltName = "Subject Alternative Name";

    public static final String oidNetscapeCertType = "2.16.840.1.113730.1.1";
    public static final String nameNetscapeCertType = "Netscape Cert Type";

}
