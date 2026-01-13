#!/bin/sh
DB_HOST=${DB_HOST:-db}
DB_PORT=${DB_PORT:-5432}
TIMEOUT=${TIMEOUT:-60}

count=0
while ! nc -z "$DB_HOST" "$DB_PORT"; do
  count=$((count + 1))
  if [ "$count" -ge "$TIMEOUT" ]; then
    echo "Timeout waiting for ${DB_HOST}:${DB_PORT}"
    exit 1
  fi
  sleep 1
done

sleep 1

exit 0
