# Introduction

BOSCOP is a project producing a set of products aimed at supplying the organizational structure of emergency deployments with more up to date and use full information in a centralised presentation, from decentralised sources.

The key features include:

- Storage of asset and deployment relevant location information.
- Availability of the System in an offline configuration, for use in vehicles.
- Interoperability via common and NATO standard interfaces (WFS).
- Interactive WebGIS.
- Collaboration accross numerous devices on one map.

# Relevant Standards

- NATO ADatP-34 (NATO Interoperability Standards and Profiles, Decides WFS Version)
- NATO AAP-06 (NATO GLOSSARY OF TERMS AND DEFINITIONS)
- OGC 06-121r3 (OWS Common Specification)
- OGC 09-025r2 (WFS 2.0, Decided by ADatP-34)
- OGC 15-005r1 (DGIWG WFS Profile, Military notes on WFS 2.0)
- IETF RFC 4646 (Tags for Identifying Languages, came from )

# Relevant Research

- Řezník, Tomáš & Hynek, Zdeněk. (2009). Data Management in Crisis Situations through WFS-T client.

# Building & Deploying

## The Website

The Maven Site is a fully fledged multi-module site, deployed to GitHub Pages. To be certain that the build fully works run the following commands in order (there are some nuances due to the tests module).

```bash
# compile needed because the tests maven module is not a named module
# site only generates the individual sites in the various modules
# site:stage brings all the sites together
# scm-publish:publish-scm because scm-deploy doesnt work for mutli module at time of writing

mvn clean compile site site:stage
mvn scm-publish:publish-scm
```

## The Package

Sometimes the release:prepare fails on the first try. This seems to be related to the tests project. Just run release:prepare again and it should work.

```bash
mvn clean verify
mvn release:prepare
# Use the tag "taktische-zeichen-..."
mvn release:perform
```

# Detailed Design

## URNs

urn:ns:de:turnertech:boscop

## Security Considerations

- URLs shall not contain any sensitive information. The Path shall be minimised to not include resource IDs where possible. 
    - Why? The URL is transmitted over unencrypted DNS requests, or logged in servers etc.
- A tracker which is not logged in via a User shall only have access to its own resource. It shall not be able to do anything other than update itself. It will not have access to the COP, or other trackers.

