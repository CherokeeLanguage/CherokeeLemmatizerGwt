<!-- SPDX-FileCopyrightText: 2026 Michael Conrad -->
<!-- SPDX-License-Identifier: MIT -->

# Plan — CherokeeLemmatizerGwt#3: same-origin JSONP endpoint (root-relative `/jsonsearch/`)

Source spec: `.issues/3/spec.md` (CherokeeLemmatizerGwt#3). Every item traces to
that spec's SCs; no item exists without one.

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
- **RED:** against current behavior the war's bundle embeds the absolute URL
  constant — the built JS carries `cherokeedictionary.net`.
- **GREEN:** after Item 1, the rebuild bakes only the host-relative base.
- **Instrument (SC-3):** recorded build output (exit 0); inspection that the
  rebuilt bundle's JS no longer contains `cherokeedictionary.net`;
  `git diff --stat`-equivalent review showing the change set touches only
  `RestApi.java` and `DictEntryPoint.java` (no `build.gradle`, no wrapper, no
  dependency-pin file).

## Item 3 — Same-origin JSONP behavior, proven (SC-2, from R-3)

- **Deliverable:** executable proof: the Item-2 war served locally from a host
  root with a stub same-origin `/jsonsearch/syll/{query}?regex=true` endpoint;
  a headless browser (the vendored browser tooling the `playwright-cli` card
  provides) loads the page, executes one search, and the run records the
  network log and the rendered result.
- **RED (baseline run, executed at Item 3's start from a clean trunk
  checkout):** the harness is
  executed once against the pre-fix build — the war built from unmodified
  trunk HEAD (`865866e`) by the same `./gradlew war` — and records the JSONP
  request going to the absolute `www.cherokeedictionary.net` URL (or failing
  on the unresolvable host) — the host-coupled behavior the fix removes. This
  baseline run is scheduled as Item 3's RED execution step, performed at
  Item 3's start from a clean trunk checkout (pre-fix source content,
  independent of the working branch's post-Item-1 state), so the pre-fix
  artifact exists and the RED is failable at its sequence point.
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

Item 1 → Item 2 → Item 3: the build consumes the source edits; the behavioral
proof consumes the built artifact. Each item's instrument runs on the prior
item's verified state — with one carve-out: Item 3's RED (the pre-fix baseline
harness run) runs against pre-fix trunk source content — executed at Item 3's
start from a clean trunk checkout, not against Item 2's verified state.

## Verification notes

- Behavioral SC-2's run is a real browser execution against the locally served
  war — per the `behavioral-testing` card's routing, no ad-hoc substitution.
- The dev-tier end-to-end run against the real endpoint is out of scope
  (spec: it rides cherokeelanguage-devops#15's revised flow).
