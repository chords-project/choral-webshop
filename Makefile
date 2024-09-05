rwildcard=$(foreach d,$(wildcard $(1:=/*)),$(call rwildcard,$d,$2) $(filter $(subst *,%,$2),$d))

CHORAL_SRCS = $(call rwildcard,src/main/java/webshop,*.ch)
CHORAL_HEADERS = "headers:src/main/java"
JAVA_GEN_FILES = $(patsubst %.ch,%.java,$(CHORAL_SRCS))

.PHONY: all
all: $(JAVA_GEN_FILES)

.PHONY: check
check: $(CHORAL_SRCS)
	choral check -l $(CHORAL_HEADERS) $^

.PHONY: run-orchestrated
run-orchestrated: $(JAVA_GEN_FILES)
	mvn compile exec:java -Dexec.mainClass="webshop.orchestrated.Main"

.PHONY: run-choreographic
run-choreographic: $(JAVA_GEN_FILES)
	mvn compile exec:java -Dexec.mainClass="webshop.choreographic.Main"

.PHONY: run-events
run-events: $(JAVA_GEN_FILES)
	mvn compile exec:java -Dexec.mainClass="webshop.events.Main"

.PHONY: clean
clean:
	rm -rf $(patsubst %.ch,%.java,$(CHORAL_SRCS)) $(patsubst %.ch,%_*.java,$(CHORAL_SRCS)) 

src/main/java/webshop/choreographic/%.java: src/main/java/webshop/choreographic/%.ch
	choral epp -s src/main/java/webshop/choreographic -t src/main/java -l $(CHORAL_HEADERS) $(notdir $*)

src/main/java/webshop/orchestrated/%.java: src/main/java/webshop/orchestrated/%.ch
	choral epp -s src/main/java/webshop/orchestrated -t src/main/java -l $(CHORAL_HEADERS) $(notdir $*)

src/main/java/webshop/events/%.java: src/main/java/webshop/events/%.ch
	choral epp -s src/main/java/webshop/events -t src/main/java -l $(CHORAL_HEADERS) $(notdir $*)

src/main/java/webshop/channel/%.java: src/main/java/webshop/channel/%.ch
	choral epp -s src/main/java/webshop/channel -t src/main/java -l $(CHORAL_HEADERS) $(notdir $*)
