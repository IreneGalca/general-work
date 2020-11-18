//Contains the [executeCommand] function with implementations
//of most internal commands except history.
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dirent.h>
#include "smash.h"
#include <unistd.h>
#include "history.h"
#define MAXLINES 4096
#define MAXCHARS 1024

void executeCommand(char *str){
char name[MAXCHARS];
strncpy(name, str, MAXCHARS);
int extStat= 0;
//char dir[MAXCHARS];//directory name
char *tok = strtok(str, " "); //strtok delimeters
char *pointer = " ";
	//while(tok != NULL){
 		//dir[index] = tok;
		//tok = strtok(NULL, " ");
		//index++;
	if(tok != NULL){		

		if (strcmp(tok, "cd") == 0){
			//pointer = tok +3;
			pointer = strtok(NULL, " ");
			if ((extStat = chdir(pointer)) ==0){
			fputs(pointer, stderr);
		
			}
			else {
			perror(pointer);
			}
		}

		else if (strcmp(tok, "history") == 0){
      print_history(0);
		}else if(strcmp(tok, "exit") == 0){
			exit(0); 
		}
		else{
			extStat = 127;
			int i=0;
			while(tok != NULL){
			printf("[%d]%s\n", i, tok);
			i++;
			tok = strtok(NULL, " "); 
			}
			//return;
		}
			//else{
			//fprintf(stderr, "Error: No such Command.\n");
			//fprintf(stderr, "Please try: cd <directoryName> \n");
			//fprintf(stderr, "or: history.\n");
			//fprintf(stderr, "or: exit.\n");
		}

	add_history(name, extStat);
return;		
	//}	
}

