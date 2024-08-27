rwildcard=$(foreach d,$(wildcard $(1:=/*)),$(call rwildcard,$d,$2) $(filter $(subst *,%,$2),$d))

CHORAL_SRCS = $(call rwildcard,src/main/java/webshop,*.ch)

.PHONY: all
all: $(patsubst %.ch,%.java,$(CHORAL_SRCS)) 

.PHONY: check
check: $(CHORAL_SRCS)
	choral check -l headers $^

.PHONY: run
run: all
	mvn compile
	java -cp target/classes webshop.Main

.PHONY: clean
clean:
	rm -rf $(patsubst %.ch,%.java,$(CHORAL_SRCS)) $(patsubst %.ch,%_*.java,$(CHORAL_SRCS)) 

%.java: %.ch
	choral epp -s src/main/java -t src/main/java -l headers $(notdir $*)