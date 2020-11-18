//Contains the implementation of the history commannd.

#include "history.h"
#include <string.h>
#include <stdlib.h>
#define MAXLINES 1024
#include <stdbool.h>

struct Cmd *commands[10];
struct Cmd c;
int index;
int overflowCount;
//int seq = -1;
int size= -1;
int total = 0;

void init_history(){
	overflowCount = 0;
	int i = 0;
	for(i = 0; i<10; i++){

		commands[i] = (cmdStruct* ) malloc(sizeof(cmdStruct));
		(*commands[i]).cmd = (char*) malloc(sizeof(char)*CMDLEN);
	}
}

void add_history(char *comm, int exitStat){
	
	if(size <= 9){
	size++;
	}

	
	if(size > 9){
		free (commands[0]->cmd);
		free (commands[0]); 

		for(int i=0; i<size; i++){
		commands[i] = commands[i+1];
		//printf("%d .. %s \n", i, commands[i]->cmd);
	
		}
		size--;
		overflowCount++;	
	}
	//int seq;
	commands[size] = (struct Cmd*) malloc(sizeof(struct Cmd));
	commands[size]->cmd = (char*) malloc(sizeof(char)*CMDLEN);
	commands[size]->exitStatus=exitStat; 
 	strncpy(commands[size]->cmd, comm, CMDLEN);
	total++;
	//seq++;
	//size++;	
}	

void clear_history(void){
	for(int i = 0; i<size; i++){
		free (commands[i]->cmd);commands[i]->exitStatus = 0;
		commands[i]->exitStatus = 0;
  		free (commands[i]);
  	}

}

void print_history(int firstSequenceNumber){
	int n = 0;
	int start = 1;


	if(size >9){
		n = size - 1;
		start = total -9;
	}	
	else {
		n = size;
	}


	for(int i =0; i<= n; i++){
		printf("%d. [%d] %s\n", start + overflowCount, commands[i]->exitStatus, commands[i]->cmd);
		start++;

  	}

}
