
JAVAC = javac
SRCDIR = src
OUTDIR = bin
DOCDIR = docs
WEKAVERSION = 3-9-6
WEKADIR = ./weka/weka-${WEKAVERSION}
WEKA = $(WEKADIR)/weka.jar
SRCS = $(wildcard $(SRCDIR)/*.java)

all: main

main: $(SRCS)
	@mkdir -p $(OUTDIR)
	@$(JAVAC) -cp $(WEKA) $(SRCS) -d $(OUTDIR)

run:
	@java --add-opens java.base/java.lang=ALL-UNNAMED -cp "$(OUTDIR):$(WEKA)" Main

# doc: $(SRCS)
# 	javadoc -d $(DOCDIR) -cp "$(OUTDIR):$(WEKA)" -sourcepath $(SRCDIR) -exclude Main Agent.java Program.java

clean:
	@rm -r $(OUTDIR)
