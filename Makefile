build:
	  ./mvnw clean package -DskipTests

test:
	  ./mvnw test

run-dev:
	  docker-compose up --build

clean:
	  ./mvnw clean
