#-------Define names of all the object files in this project
OBJS = smash.o commands.o history.o

#-------Define the name of the resulting released product
EXE = smash

#-------Define options passed by make to the compiler
CFLAGS = -Wall -std=c99

smashLib = smashLib.a

#-------Define target "all" for building the executable(s)
all: rules.d $(EXE) $(smashLib)


$(smashLib): $(OBJS)
	ar r $@ $?

rules.d: Makefile $(wildcard *.c) $(wildcard *.h)
	gcc -MM $(wildcard *.c) >rules.d

#-------Include the rules for rebuilding each *.o file
-include rules.d

#-------Rule for linking the executable product
$(EXE): $(OBJS) $(smashLib)
	$(CC) $(CFLAGS) $^ -o $@

#-------Rule for linking the executable product


#-------Rule for cleaning build and emacs artifacts
clean:
	rm -f $(OBJS) $(EXE) *.d  *~ *.a
