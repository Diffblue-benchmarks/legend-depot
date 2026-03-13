#!/bin/bash
export JAVA_HOME=/opt/jdk11
mvn test -Dtest=org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryConfigurationTest -pl legend-depot-artifacts-repository-api
