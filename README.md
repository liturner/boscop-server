

# Relevant Standards

- NATO ADatP-34 (NATO Interoperability Standards and Profiles, Decides WFS Version)
- NATO AAP-06 (NATO GLOSSARY OF TERMS AND DEFINITIONS)
- OGC 06-121r3 (OWS Common Specification)
- OGC 09-025r2 (WFS 2.0, Decided by ADatP-34)
- OGC 15-005r1 (DGIWG WFS Profile, Military notes on WFS 2.0)
- IETF RFC 4646 (Tags for Identifying Languages, came from )

# Detailed Design

## URNs

urn:ns:de:turnertech:boscop

## Security Considerations

- URLs shall not contain any sensitive information. The Path shall be minimised to not include resource IDs where possible. 
    - Why? The URL is transmitted over unencrypted DNS requests, or logged in servers etc.
- A tracker which is not logged in via a User shall only have access to its own resource. It shall not be able to do anything other than update itself. It will not have access to the COP, or other trackers.

