#!/bin/bash

# Build the service registry image
echo "Building Service Registry..."
(cd service-registry && docker-compose up --build -d)

# Start the auth-service stack with build
echo "Starting Auth Service (with build)..."
(cd auth-service && docker-compose up --build -d)

# Start the food-service stack with build
echo "Starting Food Service (with build)..."
(cd food-service && docker-compose up --build -d)

echo "All services started with fresh builds!"
