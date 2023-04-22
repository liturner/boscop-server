

# Relevant Standards

- NATO ADatP-34 (NATO Interoperability Standards and Profiles, Decides WFS Version)
- NATO AAP-06 (NATO GLOSSARY OF TERMS AND DEFINITIONS)
- OGC 09-025r2 (WFS 2.0, Decided by ADatP-34)
- OGC 15-005r1 (DGIWG WFS Profile, Military notes on WFS 2.0)

# WFS Conformace Test
- OGC 09-025r2
- OGC 09-026r2, A.1 (FES Conformance Tests)
- ISO 19136:2007, A.1.1, A.1.4, A.1.5, A.1.7, B.3, B.5, B.2.3 (GML Conformance Tests)

# Detailed Design

## URNs

urn:ns:de:turnertech:boscop

## Security Considerations

- URLs shall not contain any sensitive information. The Path shall be minimised to not include resource IDs where possible. 
    - Why? The URL is transmitted over unencrypted DNS requests, or logged in servers etc.
- A tracker which is not logged in via a User shall only have access to its own resource. It shall not be able to do anything other than update itself. It will not have access to the COP, or other trackers.

