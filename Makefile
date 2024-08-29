rwildcard=$(foreach d,$(wildcard $(1:=/*)),$(call rwildcard,$d,$2) $(filter $(subst *,%,$2),$d))

CHORAL_SRCS = $(call rwildcard,src/main/java/webshop,*.ch)
JAVA_GEN_FILES = $(patsubst %.ch,%.java,$(CHORAL_SRCS))

.PHONY: all
all: $(JAVA_GEN_FILES)

.PHONY: check
check: $(CHORAL_SRCS)
	choral check -l headers $^

.PHONY: run-orchestrated
run-orchestrated: $(JAVA_GEN_FILES)
	mvn compile
	java -cp target/classes webshop.orchestrated.Main

.PHONY: run-choreographic
run-choreographic: $(JAVA_GEN_FILES)
	mvn compile
	java -cp target/classes webshop.choreographic.Main

.PHONY: clean
clean:
	rm -rf $(patsubst %.ch,%.java,$(CHORAL_SRCS)) $(patsubst %.ch,%_*.java,$(CHORAL_SRCS)) 

%.java: %.ch
	choral epp -s src/main/java -t src/main/java -l headers $(notdir $*)