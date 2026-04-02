# Distributed API Rate Limiter (Production-Ready)

## Overview

A scalable distributed rate limiter using Spring Cloud Gateway, Redis,
and Kafka.

## Problem

Prevents API abuse and ensures system stability by limiting request
rate.

## Tech Stack

Java 17, Spring Boot, Spring Cloud Gateway, Redis, Kafka, Docker

## Architecture

Client -\> Gateway -\> Filter -\> Redis -\> Decision -\> Kafka -\>
Analytics

## Flow

1.  Request hits gateway
2.  Filter checks Redis counter
3.  Allow or Block
4.  Log event to Kafka

## Features

-   Distributed rate limiting
-   Redis-based counters
-   Kafka async logging
-   Scalable architecture

## Run

docker-compose up -d mvn spring-boot:run

## API

GET /api/hello

## Summary

Production-ready backend system demonstrating distributed systems
concepts.
