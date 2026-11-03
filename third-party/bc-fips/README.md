# Bouncy Castle FIPS Java API, rebuilt by Aspose

`ms/lib/bc-fips.jar` is one jar built from the sources of three modules of the
[Bouncy Castle FIPS Java API](https://www.bouncycastle.org/fips-java/): bc-fips 2.0.0, bcutil-fips 2.0.3 and
bcpkix-fips 2.0.7 (the `-sources.jar` artefacts on Maven Central, group `org.bouncycastle`), with the changes in
[bc-fips-2.0.0-aspose.patch](bc-fips-2.0.0-aspose.patch). The Bouncy Castle Licence (an MIT licence) is quoted in
[THIRD-PARTY-NOTICES.md](../../THIRD-PARTY-NOTICES.md).

**This jar is not the NIST-certified FIPS 140 module and must not be represented as one.** The certified module is defined
by the exact jars Bouncy Castle ships, and its power-on self-test checks their integrity; this build changes the code and
switches that check off. Applications that need the certified module should use the original jars from Bouncy Castle.

## Changes

The patch is against the bc-fips 2.0.0 sources with the bcutil-fips 2.0.3 sources unpacked over them (the layout the jar is
built from). The bcpkix-fips 2.0.7 sources are used unchanged.

1. `org/bouncycastle/crypto/fips/FipsStatus.java`: the body of `checksumValidate()` is commented out. It computes an HMAC
   over the classes of the jar it was loaded from and compares it with the one recorded at release time; a merged,
   recompiled jar can never pass it.
2. `org/bouncycastle/crypto/fips/FipsKDF.java` (three places) and `org/bouncycastle/jcajce/provider/BouncyCastleFipsProvider.java`
   (one): a local variable used by an anonymous class is declared `final`, so the sources compile with older `javac`
   language levels.
3. `org/bouncycastle/asn1/ConstructedOctetStream.java` is an older revision of the class, from an earlier Bouncy Castle
   release: it casts the next object of a constructed OCTET STRING to `ASN1OctetStringParser` directly, where 2.0.0 reports
   an object of another type with an `IOException`.
4. Six files differ only in the line break at the end of the file.

## Rebuilding the jar

[maven/](maven) holds the Maven project used for it: a parent `pom.xml` with three modules — `provider` (bc-fips plus
bcutil-fips), `bckpix` (bcpkix-fips) and `dist`, which merges both into `bc-fips.jar` with the Maven Shade plugin
(without `META-INF`).

1. Unpack `bc-fips-2.0.0-sources.jar`, then `bcutil-fips-2.0.3-sources.jar`, into `maven/provider/src/main/java`
   (later files replace earlier ones), and `bcpkix-fips-2.0.7-sources.jar` into `maven/bckpix/src/main/java`; leave out
   `META-INF` of each.
2. In `maven/provider/src/main/java`: `git apply <this folder>/bc-fips-2.0.0-aspose.patch`.
3. In `maven`: `mvn clean package -DskipTests`. The result is `dist/target/bc-fips.jar`; it replaces the one in `ms/lib`.
