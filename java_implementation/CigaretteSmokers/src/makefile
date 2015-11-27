JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Agent.java \
	Counter.java \
	Item.java \
	Smoker.java \
	SmokeTimeRange.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class