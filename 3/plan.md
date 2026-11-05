<!-- SPDX-FileCopyrightText: 2026 Michael Conrad -->
<!-- SPDX-License-Identifier: MIT -->

# Plan — CherokeeLemmatizerGwt#3: same-origin JSONP endpoint (root-relative `/jsonsearch/`)

Source spec: `.issues/3/spec.md` (CherokeeLemmatizerGwt#3). Every item traces to
that spec's SCs; no item exists without one.

## Item 0 — Build repair + Eclipse purge (SC-3 and SC-4, from amended R-4 and R-5)

- **Deliverable:** a buildable, Eclipse-free project —
  1. `build.gradle`'s buildscript and project repositories point at Maven
     Central (`jcenter()`/spring references replaced); the gretty buildscript
     classpath moves from the unresolvable `gradle.plugin.org.gretty:gretty:2.3.1`
     to `org.gretty:gretty:3.1.9`; the Gradle wrapper moves 4.10.3 → 6.9.4
     (gretty 3.1.x needs the Gradle 6.6+ `ExecOperations` API; gwt-gradle-plugin
     1.1.x needs the Gradle 6.4+ `JavaExecSpec.getMainClass` Property API —
     both verified empirically); the unused, unresolvable
     `gwtquery:2.0.0-SNAPSHOT` dependency is dropped.
  2. Eclipse purge (R-5): `eclipse-wtp` and `net.ltgt.apt-eclipse` plugin
     applications removed, the gradle-apt-plugin classpath entry serving them
     removed, the `eclipse { }` DSL block removed, the gwt plugin's
     IDE-only `eclipse { addGwtContainer }` sub-block removed (a
     Gradle-5-empirics casualty of the DSL change), and the untracked Eclipse
     workspace artifacts (`.project`, `.classpath`, `.settings/`, ignored
     `bin/`) deleted. No annotation processing exists in the sources, so no
     apt functionality is lost.
  Application pins untouched: GWT 2.8.2, restygwt 2.2.6,
  `sourceCompatibility` 1.8.
- **RED:** the recorded build failure — `./gradlew war` (wrapper 4.10.3) fails
  at configuration: `Could not resolve org.gretty:gretty-core:2.3.1 …
  Received status code 401` (executed output, 2026-11-05; live repo checks:
  spring 401, Central/jitpack 404 for 2.x gretty-core, jcenter sunset). The
  purge-side RED: `grep -in eclipse build.gradle` hits the plugin
  applications and the `eclipse { }` block today.
- **GREEN:** the build-file edits above.
- **Instrument (SC-3, partial + SC-4):** `./gradlew war` (JDK 8, `--no-daemon`
  — the 4.10.3 daemon SIGSEGVs under the system JDK 25) exits 0; the
  change-set review shows only the enumerated build surfaces touched;
  a grep over `build.gradle` for the R-5 wiring patterns (plugin
  applications `eclipse-wtp`/`net.ltgt.apt-eclipse`, the gradle-apt-plugin
  classpath entry, `eclipse {` DSL lines) returns zero hits — a surviving
  dependency-comment mention of Eclipse is prose outside R-5's surface —
  and `.project`, `.classpath`, `.settings/` are absent from the workspace.

## Item 1 — Host-relative JSONP base (SC-1, from R-1/R-2)

- **Deliverable:** two source edits — `RestApi.java` interface `@Path` becomes
  the path-only value `jsonsearch/`; `DictEntryPoint`'s static block gains
  `Defaults.setServiceRoot("/")` beside the existing
  `Defaults.setRequestTimeout(120 * 1000)`. Method paths, `{query}`
  substitution, `?regex=true`, and the `@JSONP` annotations stay byte-identical.
- **RED:** `grep -rn "cherokeedictionary.net" src/main/java/com/cherokeelessons`
  currently returns exactly one hit (`shared/RestApi.java:10`) — the hard-coded
  host exists.
- **GREEN:** the two edits above; placement safety rests on the spec's verified
  lazy-`getResource()` fact (the static block completes before any callback the
  entry point schedules).
- **Instrument (SC-1):** (a) the grep re-run — zero hits under
  `src/main/java/com/cherokeelessons`; (b) inspection of `RestApi.java`
  (path-only `@Path`, method paths/`@JSONP` untouched) and of the
  `DictEntryPoint` static block (root anchor present).

## Item 2 — Build produces the artifact with pins unchanged (SC-3, from R-4)

- **Deliverable:** `./gradlew war` exits 0 and produces
  `build/libs/CherokeeLemmatizerGwt.war` carrying the rebuilt GWT bundle.
- **RED (executed in the post-Item-0/pre-Item-1 window):** the war Item 0's
  build produces — the build repaired, the source fix not yet applied — is
  inspected: its GWT bundle's JS carries the absolute URL constant
  (`cherokeedictionary.net`). This is the pre-fix observation the GREEN
  removes; it is obtainable only from the Item-0 build, since Item 2 itself
  runs after Item 1.
- **GREEN:** after Item 1, the rebuild bakes only the host-relative base.
- **Instrument (SC-3):** recorded build output (exit 0); inspection that the
  rebuilt bundle's JS no longer contains `cherokeedictionary.net`;
  change-set review showing the change set touches `RestApi.java`,
  `DictEntryPoint.java`, and exactly the build-repair surface Item 0
  enumerates (gretty, wrapper, repository URLs, dropped gwtquery, eclipse
  purge — the amended R-4 list plus R-5; no other build or dependency change).

## Item 3 — Same-origin JSONP behavior, proven (SC-2, from R-3)

- **Deliverable:** executable proof: the Item-2 war served locally from a host
  root with a stub same-origin `/jsonsearch/syll/{query}?regex=true` endpoint;
  a headless browser (the vendored browser tooling the `playwright-cli` card
  provides) loads the page, executes one search, and the run records the
  network log and the rendered result.
- **RED (baseline run, executed at Item 3's start against pre-fix source
  content — trunk `865866e` sources with Item 0's repaired build files
  applied, since trunk's own build cannot run):** the harness is
  executed once against the pre-fix build — the war built from unmodified
  trunk HEAD (`865866e`) by the same `./gradlew war` — and records the JSONP
  request going to the absolute `www.cherokeedictionary.net` URL (or failing
  on the unresolvable host) — the host-coupled behavior the fix removes. This
  baseline run is scheduled as Item 3's RED execution step, performed at
  Item 3's start against pre-fix source content (trunk `865866e` sources with
  Item 0's repaired build files applied — independent of the working branch's
  post-Item-1 source state), so the pre-fix artifact exists and the RED is
  failable at its sequence point.
- **GREEN:** the rebuilt bundle issues `/jsonsearch/syll/{query}?regex=true`
  against the serving page's own origin; the stub's canned payload is decoded
  and the page renders the result rows.
- **Stub payload (Gap-list 1):** a canned JSONP payload satisfying
  `SearchResponse`'s decode surface —
  `{"data":[{…DictEntry fields…}]}` derived from `shared/DictEntry.java`'s
  public field set, wrapped in the `callback(...)` param the `@JSONP` contract
  names. The stub server is a small script serving the exploded war statically
  plus the stub route; it lives outside the repo tree (verification tooling,
  not a repo change).
- **Instrument (SC-2):** the recorded network log (request URL — root-relative,
  same origin, no `cherokeedictionary.net`) and the render observation from the
  headless run.

## Dependency order

Item 0 → Item 1 → Item 2 → Item 3: the build repair comes first (nothing
builds without it); the build consumes the source edits; the behavioral proof
consumes the built artifact. Each item's instrument runs on the prior item's
verified state — with two carve-outs: Item 2's RED (the bundle-constant
observation) is taken from the war Item 0's build produces, in the
post-Item-0/pre-Item-1 window; and Item 3's RED (the pre-fix baseline
harness run) runs against pre-fix trunk source content — executed at Item 3's
start against trunk `865866e` sources with Item 0's repaired build files
applied (trunk's own build cannot run), not against Item 2's verified state.
The baseline war is the repaired build over pre-fix source — the source fix,
not the build repair, is what the baseline demonstrates.)

## Verification notes

- Behavioral SC-2's run is a real browser execution against the locally served
  war — per the `behavioral-testing` card's routing, no ad-hoc substitution.
- The dev-tier end-to-end run against the real endpoint is out of scope
  (spec: it rides cherokeelanguage-devops#15's revised flow).
