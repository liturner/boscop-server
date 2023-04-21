

# Relevant Standards


# Detailed Design

## Security Considerations

- URLs shall not contain any sensitive information. The Path shall be minimised to not include resource IDs where possible. 
    - Why? The URL is transmitted over unencrypted DNS requests, or logged in servers etc.
- A tracker which is not logged in via a User shall only have access to its own resource. It shall not be able to do anything other than update itself. It will not have access to the COP, or other trackers.

