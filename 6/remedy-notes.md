Discovered while pre-flighting CherokeeLemmatizerGwt#5 (deploy-SoP spec, docs-only change): `./gradlew build -xtest` fails in `:compileGwt` on trunk (7445709, post-#4). Doc change excluded — the failure is implementation-class, pre-existing.

## Evidence

```
Caused by: java.lang.NoClassDefFoundError: javax/xml/bind/annotation/XmlTransient
  at org.fusesource.restygwt.rebind.JsonEncoderDecoderClassCreator.isXmlTransient(JsonEncoderDecoderClassCreator.java:856)
  ...
Caused by: java.lang.ClassNotFoundException: javax.xml.bind.annotation.XmlTransient
[ERROR] at DictEntryPoint.java(18): static RestApi api = (RestApi) GWT.create(RestApi.class)
```

JAXB was removed from the JDK at 11; the gradle JVM here is JDK 25 (`/usr/lib/jvm/java-25-openjdk-amd64`). The restygwt 2.2.6 rebind class-loads `javax.xml.bind.*` annotation classes, and nothing on the rebind classpath provides them. `./gradlew test` fails at the same task (it pulls in `compileGwt`), so the full-suite gate is red on trunk, not only the WAR package.

## Candidate remedy (needs its own authorization)

Add `javax.xml.bind:jaxb-api:2.3.1` to the `gwt` configuration (and/or the compile classpath) so the restygwt rebind JVM class-loads the annotation; optionally pin a JDK toolchain the GWT 2.8.2 toolchain supports.

Blocks the PR pre-create test-green gate for CherokeeLemmatizerGwt#5 — dispositioned by filing this issue per the PR card.
