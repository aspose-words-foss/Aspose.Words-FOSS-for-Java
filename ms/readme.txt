This folder contains the "ms" library, which is our Java implementation of most frequently used .NET classes.
There are two types of class hierarchies inside the library:
1. Direct analogs of .Net classes located in 'com\aspose\ms\ + <.Net hierarchy>' - for instance:
   com\aspose\ms\System\IO\Stream.java.
2. Java-only helper classes located under com\aspose\ms\java\* hierarchy.

Note MS.iml - IntelliJ IDEA module file. At the moment MS module is a part of Aspose.Words.Java project since only
Aspose.Words.Java uses it. The module is configured so the compiled output is placed directly to Aspose.Words.Java
project hierarchy.

Sometimes we will do autoporting of number of projects. In this time we have to brake this schema and made the module
standalone so already compiled ms.jar can be plugged to number of different autoporting projects:)