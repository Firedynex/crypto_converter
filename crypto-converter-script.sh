#!/usr/bin/bash -ex

mvn -q clean
mvn -q compile
mvn -q exec:exec -Dexec.mainClass=src\main\java\striker\crypto_converter\CryptoConverterDriver.java.