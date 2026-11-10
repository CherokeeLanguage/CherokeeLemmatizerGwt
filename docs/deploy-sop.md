# CherokeeLemmatizerGwt — build/deploy SoP

This project is a Gradle build producing `CherokeeLemmatizerGwt.war`.
The standing deploy flow is [`publish-live.sh`](../publish-live.sh):
`./gradlew clean build -xtest`, then the WAR is staged to the target
Tomcat's `webapps/` under a `.tmp` name and moved into place so the
container never unpacks a half-transferred artifact.

## Standing container check (two-phase gate)

Defined once, in the devops master page — linked here, never restated:
[Container pre-flight / post-flight checks — vhost-58 (SoP)](https://github.com/CherokeeLanguage/cherokeelanguage-devops/blob/main/docs/runbooks/container-sop-checks.md)
(spec: [cherokeelanguage-devops#56](https://github.com/CherokeeLanguage/cherokeelanguage-devops/issues/56)).

```bash
bash container-sop-checks.sh          # exit 0 required to proceed
```

**When it runs in this repo's deploy flow:**

- **Pre-flight** — before *any* deploy step: run the check before
  `publish-live.sh` starts (before the `gradlew` build and the WAR
  transfer). Exit 0 is a gate: nonzero blocks the deploy.
- **Post-flight** — after the deploy step completes (after the `.tmp` →
  final WAR move): re-run the check. Exit 0 is required for the work to
  be done; nonzero means the work is not done.
- **Capture with the work** — keep both executed outputs (pre- and
  post-flight) in the work's artifacts for the session/issue carrying the
  deploy.

## This repo's inventory on the shared stack

| What | Value |
|---|---|
| Container | `analyzer` service — the analyzer capture-site image this repo builds |
| Deployed at | `/opt/tomcat/webapps/CherokeeAnalyzer` on ced1 |
| Served route | `/CherokeeAnalyzer` on cherokeedictionary.net (Caddy-routed) |
| Expected route family | 200 + non-empty GWT-shell body in the Cherokee-language family (never status-only) |
