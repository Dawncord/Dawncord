# Contributing to Dawncord

Thanks for your interest in contributing! Dawncord is a Java library for building
Discord bots, published to Maven Central. This guide covers how to set up the project,
the conventions we follow, and how to submit changes.

## Prerequisites

- **JDK 25** — the project targets Java 25 (compiler `source`/`target` = 25).
- **Maven 3.9+**

## Building

```bash
# Quick compile check while iterating (preferred)
mvn compile -q -Dgpg.skip

# Compile and package (skip GPG signing)
mvn package -DskipTests -Dgpg.skip

# Generate Javadoc
mvn javadoc:javadoc
```

> GPG signing is only required for publishing to Maven Central. Always pass `-Dgpg.skip`
> for local builds so you don't need a signing key.

There are currently no tests (`src/test/` does not exist yet). The test scope is
**JUnit 5** (`org.junit.jupiter:junit-jupiter`); when tests are added, the
`maven-surefire-plugin` should be wired in so they actually run.

## Conventions

- **Java 25.** Use modern language features where they improve clarity.
- **Logging:** SLF4J + Logback. Never add `System.out` / `System.err` to library code.
- **Javadoc is mandatory** on every public class and method — the artifact ships to
  Maven Central and the Javadoc is consumed by end users. PRs adding public API without
  Javadoc will be asked to add it.
- **JSON:** Jackson only (`JsonNode` for reading, `ObjectNode` for building). Do not
  introduce other JSON libraries. Jackson stays on the `2.x` line for now.
- **HTTP:** the single shared OkHttp client lives in `ApiClient` — do not create OkHttp
  clients elsewhere. Build URLs with the `Routes` factory, never inline URL literals.
- **Entities** follow the `LazyLoader` pattern: no parsing in the constructor; each getter
  memoizes its field lazily.
- **Create/modify operations** follow the Action builder + `ActionExecutor` pattern.
- **Secrets:** never commit bot tokens, GPG keys, `settings.xml`, or `*.token` files.

## Submitting changes

1. **Fork** the repository and create a feature branch off `development`
   (not `master`).
2. Make your change, keeping commits focused. Commit subjects follow
   [Conventional Commits](https://www.conventionalcommits.org/) (e.g.
   `feat: add poll create action`, `fix: handle null guild id`).
3. Ensure the project compiles cleanly: `mvn package -DskipTests -Dgpg.skip`.
4. Add/​update Javadoc for any new or changed public API.
5. Open a Pull Request against `development` describing **what** changed and **why**.

Maintainers cut releases from `development` into `master`; you don't need to bump the
version or edit the changelog in your PR.

## Reporting bugs & requesting features

Use the GitHub [issue tracker](https://github.com/Dawncord/Dawncord/issues). For bugs,
include the Dawncord version, JDK version, and a minimal snippet that reproduces the
problem. Please **never** paste a real bot token into an issue.

## License

By contributing, you agree that your contributions will be licensed under the
[Apache License 2.0](LICENSE), the same license that covers this project.
