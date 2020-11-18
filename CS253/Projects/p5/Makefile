#Makefile for project 4

CFLAGS = -Wall -g -std=c99
CC = gcc
EXE = smash history commands
OBJS = smash.o history.o commands.o

all: rules.d $(EXE)
rules.d: $(wildcard *.c) $(wildcard *.h)
	gcc -MM $(wildcard *.c) > rules.d

-include rules.d

smash:$(OBJS)
	$(CC) $(CFLAGS) $^ -o $@

commands:commands.c commands.o
	$(CC) -c $(CFLAGS) commands.c -o commands.o

history:history.c history.o
	$(CC) -c $(CFLAGS) history.c -o history.o
clean:
	rm -f $(OBJS) $(EXE) rules.d
