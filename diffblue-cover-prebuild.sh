#!/usr/bin/env bash
# Build manually first (dcover build sometimes struggles without this step on this project)
mvn clean install --no-transfer-progress -T1C -DskipTests