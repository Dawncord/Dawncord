# Security Policy

## Supported versions

Dawncord follows a rolling release model on [Maven Central](https://central.sonatype.com/artifact/io.github.dawncord/Dawncord).
Security fixes are applied to the **latest released version** only. Please make sure you
are on the most recent release before reporting an issue.

| Version | Supported          |
|---------|--------------------|
| 1.4.x   | :white_check_mark: |
| < 1.4   | :x:                |

## Reporting a vulnerability

**Please do not report security vulnerabilities through public GitHub issues.**

Instead, report them privately through one of the following channels:

- GitHub's [private vulnerability reporting](https://github.com/Dawncord/Dawncord/security/advisories/new)
  (preferred), or
- email **dawncordteam@gmail.com**

Please include as much of the following as you can:

- A description of the vulnerability and its impact.
- The Dawncord version and JDK version affected.
- Steps to reproduce, or a minimal proof of concept.
- Any suggested mitigation, if you have one.

> **Never include a real Discord bot token** in your report. If a token was exposed as
> part of the issue, rotate it immediately via the Discord Developer Portal.

## What to expect

- We aim to acknowledge your report within **72 hours**.
- We'll keep you informed about the progress toward a fix and may ask for additional
  detail.
- Once a fix is released, we're happy to credit you in the release notes unless you
  prefer to remain anonymous.

## Scope

Dawncord is a client library that talks to the Discord Gateway and REST APIs on behalf
of a bot. Security-relevant areas include, but are not limited to:

- Handling of the bot token and other credentials.
- Parsing of untrusted data received from the Discord Gateway / REST responses.
- Dependency vulnerabilities (OkHttp, Jackson, nv-websocket-client, Logback).

Issues in Discord's own platform should be reported to Discord, not here.
