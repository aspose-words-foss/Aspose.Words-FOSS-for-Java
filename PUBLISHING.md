# Publishing Aspose.Words FOSS for Java to Maven Central

Coordinates: `org.aspose:aspose-words-foss:26.3.0` (the version is stamped only in `pom.xml`).

## Build

```
mvn -B clean verify -DskipTests
```

The build needs a **Git LFS** checkout (`git lfs pull`): two prebuilt jars in `ms/lib` and 26 resource files are LFS
objects. The pom fails the build at `generate-resources` when a bundled jar is LFS pointer text.

## What is inside the jar

The jar contains this repository's compiled classes **plus two prebuilt libraries unpacked into it, unrenamed**:
- `ms/lib/woodstox-core-6.5.2-aspose.jar`
- `ms/lib/bc-fips.jar`

`org.bouncycastle:bc-fips:2.0.0;bcutil-fips:2.0.3;bcpkix-fips:2.0.7, com.fasterxml.woodstox:woodstox-core:6.5.2-aspose` is recorded in the manifest (`Bundled-Dependencies`) for scanners,
which cannot see bundled classes in a dependency tree. The bundled Bouncy Castle FIPS jar is **not the certified
FIPS 140 module**. The engine in this package is the upstream snapshot `ac299db8593da38cd591dece70fcaab425c98de6`
and its `BuildVersionInfo.getVersion()` reports the engine's own version.

## Runtime dependencies (ordinary Maven dependencies)

- `org.codehaus.woodstox:stax2-api:4.2.1`
- `com.twelvemonkeys.common:common-lang:3.12.0`
- `com.twelvemonkeys.common:common-io:3.12.0`
- `com.twelvemonkeys.common:common-image:3.12.0`
- `com.twelvemonkeys.imageio:imageio-core:3.12.0`
- `com.twelvemonkeys.imageio:imageio-metadata:3.12.0`
- `com.twelvemonkeys.imageio:imageio-tiff:3.12.0`
- `com.twelvemonkeys.imageio:imageio-webp:3.12.0`
- `org.sejda.imageio:webp-imageio:0.1.6`
- `com.pngencoder:pngencoder:0.15.0`

Third-party licences: see THIRD-PARTY-NOTICES.md. The Aspose-modified Woodstox and bc-fips jars are documented in third-party/README.md.
