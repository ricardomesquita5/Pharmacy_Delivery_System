# Makefile

#Variaveis

INCLUDES = asm.h
SOURCES = main.c asm1.s asm2.s asm3.s asm4.s final.s bisect.s division.s
OBJFILES = main.o asm1.o asm2.o asm3.o asm4.o final.o bisect.o division.o
EXEC = main

#Suffix rules
.SUFFIXES : .c .o

.c.o:
	gcc -m32 -Wall -g -c $<

.SUFFIXES : .s .o

.s.o:
	gcc -m32 -Wall -g -c $<
	
${EXEC}: ${OBJFILES}
	gcc -m32 -Wall -g -o ${EXEC} ${OBJFILES}
	
${OBJFILES}: ${SOURCES} ${INCLUDES}

run: ${EXEC}
	./${EXEC}
	
clean:
	rm -f ${OBJFILES} ${EXEC}