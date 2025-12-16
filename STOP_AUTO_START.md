# Fixed Auto-Start Issue

## What Was Done
Added `restart: no` to all 7 services in `docker-compose.yml` to prevent auto-start when Docker Desktop opens.

## Important: Run This Command Once Docker Desktop Is Open

After opening Docker Desktop, run this command **ONCE** to update any existing containers:

```bash
docker update --restart=no mysql-user mysql-tool mysql-borrow eureka-server user-service tool-service borrow-service
```

This ensures that even existing containers won't auto-start.

## Verification

After running the above command, close and reopen Docker Desktop. The containers should **NOT** start automatically.

You'll need to manually click the play button to start them when you want to use them.
