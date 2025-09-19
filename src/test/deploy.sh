#!/bin/bash
set -e

APP_DIR=~/app
mkdir -p "$APP_DIR"
cd "$APP_DIR"

# Create .env file
cat <<EOF > .env
POSTGRES_DB=$POSTGRES_DB
POSTGRES_USER=$POSTGRES_USER
POSTGRES_PASSWORD=$POSTGRES_PASSWORD
EOF

# Load backend image
docker load -i backend.tar
rm -f backend.tar

# Start services
if docker compose version >/dev/null 2>&1; then
  docker compose up -d --remove-orphans
else
  docker-compose up -d --remove-orphans
fi

docker image prune -f || true
