package com.aspose.ms.System.Xml;

public class XmlDateTimeSerializationMode
{
    //
    // Summary:
    //     Treat as local time. If the System.DateTime object represents a Coordinated Universal
    //     Time (UTC), it is converted to the local time.
    public static final int LOCAL = 0;
    //
    // Summary:
    //     Treat as a UTC. If the System.DateTime object represents a local time, it is
    //     converted to a UTC.
    public static final int UTC = 1;
    //
    // Summary:
    //     Treat as a local time if a System.DateTime is being converted to a string.
    public static final int UNSPECIFIED = 2;
    //
    // Summary:
    //     Time zone information should be preserved when converting.
    public static final int ROUNDTRIP_KIND = 3;

}
