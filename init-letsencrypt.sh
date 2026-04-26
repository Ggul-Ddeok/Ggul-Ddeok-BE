#!/bin/bash
set -e

DOMAIN="43-202-181-73.nip.io"
EMAIL="minsua0503@gmail.com"

if [ -d "./certbot/conf/live/$DOMAIN" ]; then
    echo "Certificate already exists for $DOMAIN, skipping."
    exit 0
fi

echo "Creating directories..."
mkdir -p ./certbot/conf/live/$DOMAIN
mkdir -p ./certbot/www

echo "Creating temporary self-signed certificate..."
openssl req -x509 -nodes -newkey rsa:2048 -days 1 \
    -keyout "./certbot/conf/live/$DOMAIN/privkey.pem" \
    -out "./certbot/conf/live/$DOMAIN/fullchain.pem" \
    -subj "/CN=$DOMAIN"

echo "Starting nginx with temporary certificate..."
docker compose up -d nginx

echo "Waiting for nginx to be ready..."
sleep 3

echo "Requesting Let's Encrypt certificate..."
docker compose run --rm certbot certonly \
    --webroot \
    -w /var/www/certbot \
    -d "$DOMAIN" \
    --email "$EMAIL" \
    --agree-tos \
    --no-eff-email \
    --force-renewal

echo "Reloading nginx with real certificate..."
docker compose exec nginx nginx -s reload

echo "Done! HTTPS is now enabled for https://$DOMAIN"
