#!/bin/bash

BASE_URL="http://localhost:8080"

echo "GET /airports"
curl "$BASE_URL/airports" | jq '.'
echo ""

echo "GET /flights"
curl "$BASE_URL/flights" | jq '.'
echo ""

echo "POST /flights"
curl -X POST "$BASE_URL/flights" \
-H "Content-Type: application/x-www-form-urlencoded" \
--data "fromAirportId=1&toAirportId=2&date=2023-09-04&time=19:00:00&price=650"
echo ""

echo "GET /airports/1"
curl "$BASE_URL/airports/1"
echo ""

echo "GET flights/11"
curl "$BASE_URL/flights/11"
echo ""

echo "PUT /flights"
curl -X PUT "$BASE_URL/flights" \
-H "Content-Type: application/x-www-form-urlencoded" \
--data "id=11&from=52&to=2&date=2023-11-04&time=22:00:00&price=900"
echo ""

echo "Search from=1&to=2& dateStr=2023-09-04"
curl "$BASE_URL/flights/search?from=1&to=2&dateStr=2023-09-04"
echo ""

echo "Search two way"
curl "$BASE_URL/flights/search?from=1&to=2&dateStr1=2023-09-02&dateStr2=2023-09-04"
echo ""



