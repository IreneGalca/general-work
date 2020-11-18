#Makefile

OBJS = match.c
EXE = match

CFLAGS = -Wall -std=c99
CC = gcc

all: $(EXE)

$(EXE): $(OBJS)
	$(CC) $(CFLAGS) $^ -o $@
	
clean:
	rm -f $(EXE)
