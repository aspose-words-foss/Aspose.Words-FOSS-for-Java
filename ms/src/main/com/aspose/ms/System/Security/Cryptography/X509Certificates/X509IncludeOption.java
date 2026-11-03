// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//07/02/12 by Durin Vyacheslav
package com.aspose.ms.System.Security.Cryptography.X509Certificates;

/**
 * Java implementation of
 * http://msdn.microsoft.com/en-us/library/system.security.cryptography.x509certificates.x509includeoption.aspx
 * <p>
 * Specifies how much of the X.509 certificate chain should be included in the X.509 data.
 */
public /*enum*/ final class X509IncludeOption
{
    /**
     * No X.509 chain information is included.
     */
    public static final int NONE = 0;

    /**
     * The entire X.509 chain is included except for the root certificate.
     */
    public static final int EXCLUDE_ROOT = 1;

    /**
     * Only the end certificate is included in the X.509 chain information.
     */
    public static final int END_CERT_ONLY = 2;

    /**
     * The entire X.509 chain is included.
     */
    public static final int WHOLE_CHAIN = 3;
}
