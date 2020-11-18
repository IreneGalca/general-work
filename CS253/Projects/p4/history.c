//Contains the implementation of the history command.

//Each time smash starts it begins with an empty history record.
//and assigns the sequence number 1 to the first command
//
//Smash records the exit status of each command in brackets (except exit status)
//E.G.:
//
//1 [0] cd foo
//2 [127] no-such-command
//3 [1] no-such-directory
//4 [0] history
//
//exit(0) if successful. exit(1) if not. Smash need not record entry for

//NEEDS A STRUCT (CMD)!!!
//exit command

#include "history.h"
#include <string.h>
#include <stdlib.h>
#define MAXLINES 1024

cmdStruct *commands[10];
struct Cmd c;
int index;
int size=0;
void init_history(){	
	index = 0;
	int i = 0;
	for(i = 0; i<10; i++){
		//struct Cmd* cmd = malloc(sizeof(struct Cmd));
		commands[i] = (cmdStruct* ) malloc(sizeof(cmdStruct));
		//cmdStruct * cmd = malloc(sizeof(struct Cmd));
		(*commands[i]).cmd = (char*) malloc(sizeof(char)*CMDLEN);
		//cmd -> cmd = (char*)malloc(sizeof(char)*CMDLEN);
	}
}

void add_history(char *cmd, int exitStatus){
	if(index >=9){
		index=0;
	}
	strncpy(commands[index]->cmd, cmd, CMDLEN);
	commands[index]->exitStatus = exitStatus; 
 	index++;
	if(size < 10){
	size++;
	}
}	

void clear_history(void){
  for(int i = 0; i<=index && i < MAXLINES; i++){
  free (commands[i]);
  }
}

void print_history(int firstSequenceNumber){
  for(int i =0; i<=size && i < 10; i++){
  printf("%d [%d] %s\n", i+1, commands[i]->exitStatus, commands[i]->cmd);

}
}
