.PHONY: all build release

REGISTRY := r.k8s.kakuom.com
REPOSITORY_NAME := shop
PREFIX := staging-
COMMIT_HASH := $(shell git rev-parse --short HEAD)
DOCKER_IMAGE := $(REGISTRY)/$(REPOSITORY_NAME):$(PREFIX)$(COMMIT_HASH)
ECR_REGION := us-east-1

all: release

build:
	docker build --rm --force-rm -t $(DOCKER_IMAGE) .

push:
	docker push $(DOCKER_IMAGE)

release: build push
