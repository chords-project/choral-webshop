rwildcard=$(foreach d,$(wildcard $(1:=/*)),$(call rwildcard,$d,$2) $(filter $(subst *,%,$2),$d))

CHORAL_SRCS = $(call rwildcard,src/main/java/webshop,*.ch)
CHORAL_HEADERS = headers:src/main/java/webshop/common
JAVA_GEN_FILES = $(patsubst %.ch,%.java,$(CHORAL_SRCS))

.PHONY: all
all: $(JAVA_GEN_FILES)

.PHONY: check
check: $(CHORAL_SRCS)
	choral check -l $(CHORAL_HEADERS) $^

.PHONY: build-reactive
build-reactive: $(patsubst %.ch,%.java,$(call rwildcard,src/main/java/webshop/reactive,*.ch))

.PHONY: run-orchestrated
run-orchestrated: $(JAVA_GEN_FILES)
	mvn compile exec:java -Dexec.mainClass="webshop.orchestrated.Main"

.PHONY: run-choreographic
run-choreographic: $(JAVA_GEN_FILES)
	mvn compile exec:java -Dexec.mainClass="webshop.choreographic.Main"

.PHONY: run-events
run-events: $(JAVA_GEN_FILES)
	mvn compile exec:java -Dexec.mainClass="webshop.events.Main"

.PHONY: run-loopback
run-loopback: $(JAVA_GEN_FILES)
	mvn compile exec:java -Dexec.mainClass="webshop.loopback.Main"

.PHONY: run-reactive
run-reactive: $(JAVA_GEN_FILES)
	mvn compile exec:java -Dexec.mainClass="webshop.reactive.Main"

.PHONY: clean
clean:
	rm -rf $(patsubst %.ch,%.java,$(CHORAL_SRCS)) $(patsubst %.ch,%_*.java,$(CHORAL_SRCS)) 

src/main/java/webshop/choreographic/%.java: src/main/java/webshop/choreographic/%.ch
	choral epp -s src/main/java/webshop/choreographic -t src/main/java -l $(CHORAL_HEADERS) $(notdir $*)

src/main/java/webshop/orchestrated/%.java: src/main/java/webshop/orchestrated/%.ch
	choral epp -s src/main/java/webshop/orchestrated -t src/main/java -l $(CHORAL_HEADERS) $(notdir $*)

src/main/java/webshop/events/%.java: src/main/java/webshop/events/%.ch
	choral epp -s src/main/java/webshop/events -t src/main/java -l $(CHORAL_HEADERS) $(notdir $*)

src/main/java/webshop/loopback/%.java: src/main/java/webshop/loopback/%.ch
	choral epp -s src/main/java/webshop/loopback -t src/main/java -l $(CHORAL_HEADERS):src/main/java/webshop/loopback $(notdir $*)

src/main/java/webshop/reactive/%.java: src/main/java/webshop/reactive/%.ch
	choral epp -s src/main/java/webshop/reactive -t src/main/java -l $(CHORAL_HEADERS) $(notdir $*)

src/main/java/webshop/channel/%.java: src/main/java/webshop/channel/%.ch
	choral epp -s src/main/java/webshop/channel -t src/main/java -l $(CHORAL_HEADERS) $(notdir $*)
