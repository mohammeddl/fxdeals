# Makefile (place in fxdeals/)
# -----------------------------------------
# Usage:
#   make build     → builds the jar via Maven
#   make test      → runs unit tests
#   make run-dev   → starts Docker Compose locally
#   make clean     → mvn clean
# -----------------------------------------
build:
	  ./mvnw clean package -DskipTests

test:
	  ./mvnw test

run-dev:
	  docker-compose up --build

clean:
	  ./mvnw clean
