REPONAME = compose
DOCKERIMAGENAME = benchflow/$(REPONAME)
VERSION = dev
JAVA_VERSION_FOR_COMPILATION = java-8-oracle
JAVA_HOME := `update-java-alternatives -l | cut -d' ' -f3 | grep $(JAVA_VERSION_FOR_COMPILATION)`"/jre"

.PHONY: all build_release 

all: build_release

clean:
	mvn clean

build:
	JAVA_HOME=$(JAVA_HOME) mvn package

build_release:
	JAVA_HOME=$(JAVA_HOME) mvn install

install:
	JAVA_HOME=$(JAVA_HOME) mvn install

test:
	JAVA_HOME=$(JAVA_HOME) mvn test

build_container_local:
	JAVA_HOME=$(JAVA_HOME) mvn package
	docker build -t $(DOCKERIMAGENAME):$(VERSION) -f Dockerfile.test .
	rm target/benchflow-$(REPONAME).jar

test_container_local:
	docker run -ti --rm -e "BENCHFLOW_SWARM_ENDPOINT=10.40.1.128:2376" -e "BENCHFLOW_SWARM_TLSVERIFY=0" -e "ENVCONSUL_CONSUL=195.176.181.55:8500" \
	-p 8080:8080 --net="host" --name $(REPONAME) $(DOCKERIMAGENAME):$(VERSION)

rm_container_local:
	docker rm -f -v $(REPONAME)