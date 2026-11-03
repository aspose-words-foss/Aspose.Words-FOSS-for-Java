# Woodstox, modified by Aspose

`ms/lib/woodstox-core-6.5.2-aspose.jar` is [Woodstox](https://github.com/FasterXML/woodstox) built from the tag
`woodstox-core-6.5.1` with the changes in [woodstox-6.5.1-aspose.patch](woodstox-6.5.1-aspose.patch). The version number
6.5.2 and the classifier `aspose` were set by Aspose; FasterXML never released a 6.5.2.

Woodstox is licensed under the Apache License 2.0 (full text in [THIRD-PARTY-NOTICES.md](../../THIRD-PARTY-NOTICES.md)).
As that licence asks, this is the notice that the files listed below were changed by Aspose.

## Changes

1. **Attribute values keep their white space.** .NET `XmlTextReader`, which the Java code emulates, returns tabs and line
   breaks inside attribute values as they are; Woodstox normalises them to spaces, as XML 1.0 (section 3.3.3) prescribes.
   The patch makes that normalisation optional: new flag `CFG_NORMALIZE_ATTR_VALUES` (`cfg/InputConfigFlags.java`),
   `ReaderConfig.doNormalizeAttrValues(boolean)` and `willNormalizeAttrValues()` (`api/ReaderConfig.java`), field
   `mNormalizeAttrValues` set in the constructor of `sr/StreamScanner.java`, and `BasicStreamReader.parseAttrValue` replaces
   white space only when the flag is on. **The flag is off by default**, so this build does not normalise attribute values
   unless asked to.
2. **External entities in attribute values.** When external entities are disabled, `StreamScanner.expandEntity` used to
   reject a reference to an external entity. It now resolves it with the undeclared-entity resolver of the reader
   configuration (`ReaderConfig.setUndeclaredEntityResolver`) if one is set, and fails as before otherwise. Where the library
   turns external entities off (`com.aspose.ms.java.xml.XmlUtils`), it sets a resolver that returns nothing, so such
   references are skipped silently, as in .NET.
3. **Bounds checks.** `StreamScanner.resolveSimpleEntity` and `resolveCharOnlyEntity` check that three characters are
   available before they look for `lt;` / `gt;`, instead of assuming it.
4. `StreamScanner.parsePublicId`: an extra check that skips a space while a space is pending; it has no effect, because
   spaces are consumed earlier in the same loop.
5. `pom.xml`: version 6.5.2, SCM tag `HEAD`, the MSV dependency is no longer `optional`, and an extra jar with the
   classifier `aspose` is built. MSV is still not bundled: the jar contains `com.ctc.wstx` only.

## Rebuilding the jar

```
git clone https://github.com/FasterXML/woodstox.git
cd woodstox
git checkout woodstox-core-6.5.1
git apply <this folder>/woodstox-6.5.1-aspose.patch
mvn clean package -DskipTests
```

The result is `target/woodstox-core-6.5.2-aspose.jar`; it replaces the one in `ms/lib`.
