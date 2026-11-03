# Modified third-party libraries

Two of the libraries this project depends on are not the builds published by their authors: Aspose changed them. The jars
are kept in [`ms/lib`](../ms/lib); this folder documents what was changed, has the changes as patches against the original
sources, and says how to rebuild each jar. Every other dependency is an unmodified artefact from Maven Central, downloaded
by the build (`ant fetch-libs`). Licences of all of them: [THIRD-PARTY-NOTICES.md](../THIRD-PARTY-NOTICES.md).

| Jar in `ms/lib` | Based on | Licence | Details |
|---|---|---|---|
| `woodstox-core-6.5.2-aspose.jar` | Woodstox 6.5.1 | Apache License 2.0 | [woodstox/](woodstox/README.md) |
| `bc-fips.jar` | Bouncy Castle FIPS Java API: bc-fips 2.0.0, bcutil-fips 2.0.3, bcpkix-fips 2.0.7 | Bouncy Castle Licence (MIT) | [bc-fips/](bc-fips/README.md) |
