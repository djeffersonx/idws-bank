APPLICATION_CONTAINER_NAME=account-validator

down:
	docker-compose down --v
run:
	@$(MAKE) down
	./gradlew clean build
	docker-compose up -d --build
	docker logs ${APPLICATION_CONTAINER_NAME} --follow