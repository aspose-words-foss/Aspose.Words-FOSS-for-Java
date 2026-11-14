# Publishing Aspose.Words FOSS for Java to Maven Central

Coordinates: `org.aspose:aspose-words-foss:26.3.0` (the version is stamped only in `pom.xml`).

## Build

```
mvn -B clean verify -Dgpg.skip=true -DskipTests
```

`-Dgpg.skip=true` builds without signing. The pom signs the jars at `verify` with the Aspose FOSS release key, which only
the release workflow holds; without that flag the build stops with `No secret key`.

The build needs a **Git LFS** checkout (`git lfs pull`): two prebuilt jars in `ms/lib` and 26 resource files are LFS
objects. The pom fails the build at `generate-resources` when a bundled jar is LFS pointer text.

## What is inside the jar

The jar contains this repository's compiled classes **plus two prebuilt libraries unpacked into it, unrenamed**:
- `ms/lib/woodstox-core-6.5.2-aspose.jar`
- `ms/lib/bc-fips.jar`

Dependency scanners cannot see classes bundled inside a jar, so the manifest lists them in `Bundled-Dependencies`:
`org.bouncycastle:bc-fips:2.0.0;bcutil-fips:2.0.3;bcpkix-fips:2.0.7, com.fasterxml.woodstox:woodstox-core:6.5.2-aspose`.

The bundled Bouncy Castle FIPS jar is **not the certified FIPS 140 module**. The engine in this package is the upstream
snapshot `ac299db8593da38cd591dece70fcaab425c98de6` and its `BuildVersionInfo.getVersion()` reports the
engine's own version.

The jar also carries this project's `LICENSE` and `THIRD-PARTY-NOTICES.md` under `META-INF/aspose-words-foss/`. The Apache-2.0
`META-INF/LICENSE` at the root of the jar is the bundled Woodstox's own licence text.

The workflows scan the tree for PGP key blocks and stop when they find one. The scan skips `libs-internal-tools/ant/KEYS`, which
only holds public keys; a key block in any other file still stops the job.

## GitHub Release

Each release also attaches the deployed jar, the sources and javadoc jars, their `.asc` signatures
and a `SHA256SUMS` file to the GitHub Release. Maven Central is the source of truth; the attached files are a
convenience copy of the same build.

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
