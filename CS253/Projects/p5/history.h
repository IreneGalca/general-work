//header file for history.c
//Contains the data types, constants and function prototypes for
//the history command.
//
#include <stdio.h>
#define MAXHISTORY 10 //maximum cmds history can store
#define CMDLEN 80 //max length of cmd
typedef struct Cmd{
	char* cmd; //a saved copy of user's cmd
	int exitStatus; //The exitStatus from this cmd
}cmdStruct;

void init_history(void); //builds data structs for recording cmd history.

void add_history(char *cmd, int exitStatus); //adds an entry to history

void clear_history(void); //frees all malloc'd memory in history.

void print_history(int firstSequenceNumber); //Prints history in stdout.
