APPLICATION_CONTAINER_NAME=account-validator

down:
	docker-compose -f ../docker-compose.yml down --v
run:
	@$(MAKE) down
	./gradlew clean build
	docker-compose -f ../docker-compose.yml up -d --build
	docker logs ${APPLICATION_CONTAINER_NAME} --follow