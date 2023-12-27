#!/bin/sh
# Runs docker container on host network, port 4555
docker run -d --rm --name java-quiz-server --net host java-quiz-server
