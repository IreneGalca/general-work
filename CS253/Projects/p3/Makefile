#Makefile

CFLAGS = -Wall -std=c99
CC = gcc
EXE = sed253 substitute delete copy
OBJS = sed253.o copy.o substitute.o delete.o

 all: rules.d $(EXE)
 rules.d: $(wildcard *.c) $(wildcard *.h)
	gcc -MM $(wildcard *.c) >rules.d

-include rules.d
	
 sed253:$(OBJS)
	$(CC) $(CFLAGS) $^ -o $@
 
 copy:copy.c copy.h copy.o
	$(CC) -c $(CFLAGS) copy.c -o copy.o
	#$(CC) $(CFLAGS) copy.o -o copy
  
 substitute:substitute.h substitute.c substitute.o
	$(CC) -c $(CFLAGS) substitute.c -o substitute.o
	#$(CC) $(CFLAGS) substitute.o -o substitute
  
 delete:delete.h delete.c delete.o
	$(CC) -c $(CFLAGS) delete.c -o delete.o
	#$(CC) $(CFLAGS) delete.o -o delete

 	
 clean:
	rm -f $(OBJS) $(EXE) rules.d
