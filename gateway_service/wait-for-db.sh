 #!/bin/sh
# Wait-for-db script: waits until the DB host:port is reachable
# Usage: set DB_HOST and DB_PORT env vars or defaults will be used

DB_HOST=${DB_HOST:-db}
DB_PORT=${DB_PORT:-5432}
TIMEOUT=${TIMEOUT:-60}

echo "Waiting for database at ${DB_HOST}:${DB_PORT} (timeout ${TIMEOUT}s)..."

count=0
while ! nc -z "$DB_HOST" "$DB_PORT"; do
  count=$((count + 1))
  if [ "$count" -ge "$TIMEOUT" ]; then
    echo "Timeout waiting for ${DB_HOST}:${DB_PORT}"
    exit 1
  fi
  sleep 1
done

echo "Database is available: ${DB_HOST}:${DB_PORT}"

# Small sleep to ensure DB ready for connections
sleep 1

exit 0

