<!-- SPDX-FileCopyrightText: 2026 Michael Conrad -->
<!-- SPDX-License-Identifier: MIT -->

# Spec — same-origin JSONP endpoint: root-relative `/jsonsearch/` URL (the hard-coded `www.cherokeedictionary.net` base removed)

> DRAFT — pending fresh-context validation and the developer approval gate.
> Repository: CherokeeLanguage/CherokeeLemmatizerGwt (public; trunk `master`).
> Ticket: CherokeeLemmatizerGwt#3 (remote-assigned; the local record lives at
> `.issues/3/`). Filed 2026-11-05 from the developer's directives recorded in
> the developer-statement traceability section. Consumer coupling: the
> analyzer's deployment phase (cherokeelanguage-devops#15) consumes this
> fix's rebuilt artifact; the coupling's disposition rides #15's own
> revision, not this ticket.

## Problem

The analyzer client's only server call — its JSONP search API — is hard-coded
to an absolute URL: `src/main/java/com/cherokeelessons/dict/shared/RestApi.java`
line 10 carries the interface-level restygwt annotation
`@Path("http://www.cherokeedictionary.net/jsonsearch/")` (verified at HEAD
`865866e`, 2026-11-05; the deployed bundle carries the same constant —
cherokeelanguage-devops#15's research record, verified 2026-10-31). Three
consequences:

1. The client cannot be exercised against any serving surface other than the
   apex host: a page served at `dev.cherokeedictionary.net` (the migration
   sequence's dev-first verification tier) still sends its search calls to
   `www.cherokeedictionary.net` — pre-cutover that hostname resolves to the
   old host, so a dev-tier deployment exercises the analyzer shell but never
   the stack it would actually depend on post-cutover.
2. The hard-coded `http://` scheme on an `https://` page relies on browsers'
   mixed-content auto-promotion — a behavior verified at smoke time
   (devops#15 SC-3 carries exactly this open verification) rather than a
   property the client guarantees.
3. The client is bound to one hostname for any future host move — precisely
   the coupling class the migration sequence is removing from every other
   application in the org.

The developer directed the fix: the analyzer must not hard-code
`www.cherokeedictionary.net`; the JSONP endpoint must use a proper relative
URL — absolute path, relative host (the statements below).

## Facts (verified, dated)

All repo facts at HEAD `865866e` (2026-11-05, read-only clone inspection):

- `RestApi.java` — a restygwt 2.2.6 `RestService` interface; the
  interface-level `@Path("http://www.cherokeedictionary.net/jsonsearch/")` is
  the request base; three methods — `syll/{query}?regex=true`,
  `chr/{query}?regex=true`, `en/{query}?regex=true` — each
  `@JSONP(callbackParam="callback", failureCallbackParam="failure")`.
  `DictEntryPoint.api = GWT.create(RestApi.class)` is the proxy's single
  construction site. The client source contains exactly one API invocation:
  `DictEntryPoint.api.syll(...)` at
  `src/main/java/com/cherokeelessons/dict/ui/AnalysisView.java:254`; the
  `chr` and `en` methods have zero call sites in `src/main/java` (exhaustive
  grep, 2026-11-05). The fix rides the interface base, so all three methods
  inherit it; the dead `chr`/`en` declarations are untouched (no unrelated
  refactoring).
- No `Defaults.setServiceRoot(...)` call exists anywhere in the client; the
  only `Defaults` use is `Defaults.setRequestTimeout(120 * 1000)` in
  `DictEntryPoint`'s static block.
- **RestyGWT 2.2.6 URL resolution** — source-verified 2026-11-05 against tag
  `restygwt-project-2.2.6` of github.com/resty-gwt/resty-gwt
  (`Defaults.java`, `Resource.java`, `RestServiceClassCreator.java`,
  `JsonpMethod.java`):
  - `Defaults.serviceRoot` defaults to `GWT.getModuleBaseURL()` (here:
    `…/gwt/` — the module descriptor
    `src/main/resources/com/cherokeelessons/dict/DictModule.gwt.xml` carries
    `<module rename-to="gwt">` and the host page loads
    `gwt/gwt.nocache.js`; the deployed context segment rides the
    deploy-time war rename the devops#15 research record documents).
  - The generated proxy builds its base lazily, inside `getResource()`, on
    first use — `new Resource(Defaults.getServiceRoot()).resolve(<interface
    @Path>)` — so a service-root value set during class initialization takes
    effect: the static block runs when the entry-point class initializes,
    before `onModuleLoad` and any callback it schedules, and the first API
    invocation cannot precede that.
  - `Resource.resolve(path)` strips a leading `/` from the resolved path and
    concatenates it onto the base; only a `http:`/`https:`/`file:` prefix
    replaces the base. A root-relative request URL is therefore not
    producible by the annotation value alone — the service root must carry
    the root anchor.
  - `JsonpMethod.send(...)` passes `resource.getUri()` directly into GWT
    core's `JsonpRequestBuilder.requestObject(...)`: the URL string is
    injected as a script `src`, and a host-relative string (no scheme, no
    host) is resolved by the browser against the serving page's own origin.
    Same-origin JSONP needs no CORS machinery.
  - Composition check, derived from the verified semantics:
    `Defaults.setServiceRoot("/")` → `new Resource("/")` normalizes to the
    empty base path → `.resolve("jsonsearch/")` → `/jsonsearch` → the method
    path resolves to the final URI
    `/jsonsearch/{kind}/{query}?regex=true` — root-relative, host-free.
- Sweep (2026-11-05, grep over `src/main/java` `*.java` sources): no other
  runtime `http(s)://` host constant exists in the org's own client code —
  the remaining hits are comment/javadoc text in vendored commons and opencsv
  classes (`commons/lang3`, `commons/codec`, `com/opencsv`) and in the org's
  own `ConsoleLogHandler2.java` comment block. (`*.ui.xml` UiBinder files
  carry `<!DOCTYPE ... SYSTEM "http://dl.google.com/gwt/DTD/xhtml.ent">`
  lines — compile-time DTD system identifiers, no runtime network use,
  outside the runtime-constant surface this sweep reports.) `RestApi` is the
  module's only
  `RestService` interface, so the module-global service root has no other
  consumer.
- The endpoint consumer: the dictionary application serves
  `/jsonsearch/{chr,en,syll}` at the site root (devops#15 research,
  live-verified 2026-10-31), and the serving stack's Caddy catch-all routes
  `/jsonsearch` to the dictionary container on every serving surface of the
  new host — the dev tier today, the apex from the zone flip onward.
- Build: Gradle wrapper 4.10.3; the `war` plugin produces the deployable
  artifact; GWT 2.8.2, restygwt 2.2.6, `sourceCompatibility` 1.8.
- **Build-repair facts (verified 2026-11-05, live network checks) — the trunk
  build is broken before this fix.** `./gradlew war` (wrapper 4.10.3) fails at
  build-script configuration: `gradle.plugin.org.gretty:gretty:2.3.1` requires
  `org.gretty:gretty-core:2.3.1`, which resolves from no live repository —
  `repo.spring.io/plugins-release` returns 401 (portal locked), Maven Central
  has no gretty-core before 3.0.5, jitpack 404s, and jcenter/bintray is
  sunset. Additionally the project repositories reference `jcenter()` (sunset)
  and a sonatype snapshots URL for `gwtquery:2.0.0-SNAPSHOT`, which exists
  nowhere (sonatype snapshots 404, jitpack 404, Central ends at 1.5-beta1) and
  is unused by all client sources (exhaustive grep, 2026-11-05 — zero
  `com.google.gwt.query`/`GQuery` imports). Gretty's published system
  requirements (gretty-3.x branch README, verified 2026-11-05): since 3.0.0
  gretty requires Gradle 5.0+; the 3.1.x line supports JDK8/11; gretty 3.1.9
  and gwt-gradle-plugin up to 1.1.19 are on Maven Central. The daemon crash
  under the system JDK 25 (SIGSEGV, native frame) is daemon-specific and
  avoided by running the pinned JDK 8 with `--no-daemon`.

## Developer-statement traceability

- Statement A (2026-11-05, this session, verbatim): "the analyzer should
  *NOT* be hard-coded to use www.cherokeedictionary.net; it should be able
  to use a proper relative url for the jsonp" — the fix mandate (R-1, R-2).
- Statement B (2026-11-05, this session, verbatim): "use absolute path but
  relative host is acceptable" — the URL-form decision: the request path is
  absolute (`/jsonsearch/…`), the host is relative (taken from the serving
  page's origin at runtime) (R-1).
- Consumer context: cherokeelanguage-devops#15 (phase 6, the analyzer's
  deployment spec) records the hard-coded URL as the analyzer's one runtime
  dependency, and carries the R-8 ordering gate and the SC-3 mixed-content
  verification that dependency forced. This fix removes the dependency's
  host-coupling; what changes on the #15 side is recorded by #15's own
  revision — this ticket executes none of #15's work.

## Requirements

- **R-1 — Host-relative JSONP base.** The implementer changes the client's
  JSONP request base from the hard-coded absolute URL to the root-relative
  form: the request URL the client issues is
  `/jsonsearch/{kind}/{query}?regex=true` — no scheme, no host — resolved by
  the browser against the serving page's own origin. The mechanism (restygwt
  2.2.6; semantics verified in Facts): the `RestApi` interface `@Path`
  becomes the path-only value `jsonsearch/`, and the client initialization
  sets the restygwt service root to the root anchor —
  `Defaults.setServiceRoot("/")` at the same initialization site that
  already carries `Defaults.setRequestTimeout`. The three method paths, the
  `{query}` substitution, the `?regex=true` query string, and the `@JSONP`
  callback contract (`callback`/`failure` params) stay byte-identical. No
  dependency, GWT-module, or build-wiring change rides this fix.
- **R-2 — Zero hard-coded endpoint hosts.** The org's client runtime sources
  carry no hard-coded endpoint host or scheme after the fix: zero
  `cherokeedictionary.net` occurrences under
  `src/main/java/com/cherokeelessons` (javadoc/comment text inside the
  vendored `commons/` tree is not client runtime code and is outside this
  requirement's surface). The rebuilt artifact inherits the property — the
  constant no longer exists to compile into the bundle.
- **R-3 — Same-origin behavior, proven.** The rebuilt client, served from a
  host root, executes a search whose JSONP request resolves against the
  serving page's own origin — proven with executable evidence independent of
  any deployment: the rebuilt war served locally against a stub same-origin
  `/jsonsearch` endpoint; a headless browser executes one search; the
  recorded network log shows the request URL in the root-relative form
  against the page's origin (not `www.cherokeedictionary.net`), the JSONP
  callback fires, and the page renders the decoded results.
- **R-4 — Build integrity (amended 2026-11-05, developer directive: "use the
  correct version of gretty that matches the build requirements").** The
  Gradle build completes exit 0 and produces the deployable war carrying the
  rebuilt permutation bundle. The build repair is in scope because the trunk
  build is broken (Facts): gretty moves from the unresolvable 2.3.1 to 3.1.9
  (the Gradle-5-compatible line's current release on Maven Central), the
  Gradle wrapper moves 4.10.3 → 5.6.4 (gretty ≥3.0.0 requires Gradle 5.0+),
  the dead `jcenter()`/spring plugin-repo references become Maven Central,
  and the unresolvable, unused `gwtquery:2.0.0-SNAPSHOT` dependency is
  dropped. The application dependency pins that matter stay unchanged: GWT
  2.8.2, restygwt 2.2.6, `sourceCompatibility` 1.8, Java 8 runtime.

## Success criteria

| ID | Criterion | Class | Instrument |
|---|---|---|---|
| SC-1 | The client's runtime sources carry no hard-coded endpoint host or scheme: zero `cherokeedictionary.net` occurrences under `src/main/java/com/cherokeelessons`; the `RestApi` interface `@Path` is a path-only value; the service-root setting is the root anchor, placed so it precedes the first API call | structural | source grep over `src/main/java/com/cherokeelessons` (zero hits) + inspection of `RestApi.java` and the service-root initialization site |
| SC-2 | The rebuilt client's JSONP request is host-relative and same-origin: the issued request URL is `/jsonsearch/{kind}/{query}?regex=true` resolved against the serving page's origin; the callback fires and the page renders results | behavioral | headless-browser run against the locally served rebuilt war with a stub same-origin `/jsonsearch` endpoint; the recorded network log shows the request URL and the render observation |
| SC-3 | The build produces the deployable artifact: `./gradlew war` exits 0; the war contains the rebuilt GWT bundle; the change set's application pins are unchanged (GWT 2.8.2, restygwt 2.2.6, sourceCompatibility 1.8) while the build repair's surface (gretty version, Gradle wrapper, repository URLs, dropped gwtquery) is exactly the amended R-4 list | structural | build run recorded (exit 0) + war contents inspection + change-set diff review |

## Scope

In scope: the `RestApi` interface `@Path` value; the client-initialization
service-root setting; the rebuilt war as the verification artifact; the build
repair the amended R-4 enumerates (gretty 3.1.9, Gradle wrapper 5.6.4,
Maven-Central repository references, dropped gwtquery snapshot). Out of
scope: the analyzer's container image and its deployment
(cherokeelanguage-devops#15's flow — including its R-6 image-source election
and R-8 ordering gate, whose recorded rationale this fix supersedes; the
election is #15's revision's to record); the #15 spec revision itself;
GWT/restygwt version changes; any other URL, reference, or comment cleanup in
the repository; the dictionary application's `/jsonsearch` endpoint behavior
(the consumer side).

## Approach

Two edits, one build, one proof. First edit: `RestApi`'s interface `@Path`
from `http://www.cherokeedictionary.net/jsonsearch/` to the path-only
`jsonsearch/` — the method paths and the `@JSONP` annotations untouched.
Second edit: the service root set to the root anchor at client initialization
(`Defaults.setServiceRoot("/")` alongside the existing
`Defaults.setRequestTimeout` in `DictEntryPoint`'s static block). Placement
safety rests on the verified lazy-resolution fact — the generated proxy reads
`Defaults.getServiceRoot()` inside `getResource()` at the first method call,
not at `GWT.create` time — so the static block's position relative to the
`api` field initializer is immaterial: class initialization completes before
`onModuleLoad` runs and before any callback it schedules. Rejected alternative, recorded: computing an
absolute origin URL at runtime (a `Window.Location`-composed
`protocol//host/` string) as the service root — it produces an absolute URL
string whose host is runtime-derived rather than a host-relative URL, adds a
`Window.Location` dependency, and buys nothing over the root-relative form
the developer specified. The proof instrument: serve the exploded rebuilt war
locally with a stub `/jsonsearch` route returning a canned JSONP payload
(derived at implementation from the `SearchResponse` DTO's decode surface),
run a headless browser (the sanctioned vendored browser tooling), execute one
search, and record the network log (request URL + initiating origin) and the
rendered result. The dev-tier end-to-end run against the real endpoint rides
devops#15's revised flow — this ticket's in-repo proof stands on its own, so
the fix's correctness never depends on a deployment window.

## Impact

Consumers: the analyzer client on every serving surface — the dev tier
immediately (this fix is what makes dev-first analyzer testing possible at
all), the apex from the zone flip onward, and any future host (the coupling
class this fix removes). The old-host deployed analyzer is untouched — the
old build keeps its hard-coded URL until devops#15's replacement deploys the
rebuilt artifact. Blast radius: one source file (`RestApi.java`), the
entry-point initialization line, and the rebuilt war's bundle bytes (a
strong-name change is expected — content-hash naming). No dependency, no CI,
no data movement. Risks, each with containment: the service root is
module-global — the sweep fact (a single `RestService` interface) bounds its
effect to `RestApi`; the fix changes GWT dev-mode behavior — the pre-fix
build's absolute `@Path` replaces the module base in dev mode too, so
dev-mode search hits the live `www.cherokeedictionary.net` endpoint today,
while post-fix dev-mode search hits the dev-mode server's nonexistent
`/jsonsearch` route and fails there: a developer-workflow change on a
non-deployment surface, not a runtime behavior change on any serving surface
the stack deploys (the recorded surfaces — host-local, dev tier, apex — all
serve `/jsonsearch` from the stack itself). Failure modes are reversible by
revert; no state, no data, no credentials involved.

## Gap list

1. Stub payload fidelity: the canned JSONP payload must satisfy
   `SearchResponse`'s decode surface — derived from the DTO's field set at
   implementation (the recorded live response shape in devops#15's research
   record is the reference form).

## Traceability mapping

| Source | Requirement | Criteria |
|---|---|---|
| Statement A (no hard-coded host; proper relative URL) | R-1, R-2 | SC-1, SC-2 |
| Statement B (absolute path, relative host) | R-1 | SC-2 |
| Facts — restygwt resolution semantics (the mechanism's basis) and the single-`RestService` sweep (the bound on the global service root) | R-1 (mechanism), R-2 (sweep) | SC-1, SC-2 |
| Consumer context — devops#15's dependency record and its SC-3 mixed-content verification | R-1 (the coupling this fix removes) | SC-2 |
| Build integrity (the deliverable must be producible and deployable-shaped) | R-4 | SC-3 |

🤖 Co-authored with AI: OpenCode (huggingface/zai-org/GLM-5.3-Flash)
