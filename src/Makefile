JNINATIVE= -Djava.library.path=/home/michael/liu/JOGL/putil/lib/
SYSTEMCLASSPATH= /home/michael/liu/JOGL/putil/lib/jar/jogl.all.jar:/home/michael/liu/JOGL/putil/lib/jar/gluegen-rt.jar:/home/michael/liu/JOGL/putil/lib/jar/nativewindow.all.jar:/home/michael/liu/JOGL/putil/putil.jar:/usr/share/java/jama.jar:/home/michael/liu/JOGL/putil/lib/jar/jcommon.jar:/home/michael/liu/JOGL/putil/lib/jar/jfreechart.jar:/home/michael/liu/JOGL/putil/lib/jar/jfreechart-swt.jar
JAVACFLAGS=  -encoding euc-jp -d bin -cp $(SYSTEMCLASSPATH):. 
JAVAC= javac $(JAVACFLAGS)
JAVAFLAGS= $(JNINATIVE) -cp $(SYSTEMCLASSPATH):bin 
JAVA = java $(JAVAFLAGS)
RM = rm -rf

.SUFFIXES: .class .java

.java.class:
	mkdir -p bin
	$(JAVAC) $<

.class:
	$(JAVA) $(<:.class=)



.PHONY: clean
clean:
	$(RM) *.class *~ *.o bin
