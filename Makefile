POM := $(shell find . -name '*pom.xml')

.PHONY: run debug clean

default: service

service: $(POM)
	mvn clean install;

run: service
	mvn spring-boot:run

debug: service
	mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8787"

clean:
	rm -rf ./target