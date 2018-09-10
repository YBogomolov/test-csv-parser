# Plans:

1. Streaming import from HTTP source
    1. [ ] Find example CSV with prices
1. Streaming parsing & transformation from CSV to POJO
1. POJO (case classes) -> Redis (using circe as JSON)
1. HTTP server which serves JSON by price ID
    1. [ ] Serving (searching) for prices by ID
    1. [ ] Triggering prices refresh
1. Dockerfile
1. Tests (TDD?)
1. README
