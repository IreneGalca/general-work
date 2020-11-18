//Contains the [executeCommand] function with implementations
//of most internal commands except history.
#define _GNU_SOURCE
#define POSIX_C_SOURCE_200809L
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dirent.h>
#include "smash.h"
#include <unistd.h>
#include <sys/wait.h>
#include "history.h"
#define MAXLINES 4096
#define MAXCHARS 1024

//[implement pipes here]:
	//int pipeFD[2];

	/*if(pipe(pipeFD) < 0){
		perror("Error: creating pipe");
		exit -1;
	}*/

int executeExternalCommand(char* argv[], int *exitStat){
	*exitStat = 0;
	int pid = fork();
	//int status;
	//char args[20];
	fprintf(stderr, "first exitStat: %d \n", *exitStat);


	if(pid <0){ //if fork() unsuccessful
		perror("Error: Fork unsuccessful!");
		//exit(exitSt);
		return -1;
		fprintf(stderr, "Second exitStat: %d \n", *exitStat);

	}

	

	if(pid == 0){//if successful
		//execvp(argv[0], argv);
		//[implement redirection here]:
		if(execvp(argv[0],argv)== -1){ //if execution unsuccessful
			perror("Error: Execution unsuccessful!");
			exit(-1);

			}	
			exit(0);

	}

	else if(pid > 0){//if parent
	//exitStat = 127;
	wait(exitStat);
	fprintf(stderr, "exitStat: %d\n", *exitStat);
	}

	//else if(execvp(argv[0],argv) < 0){ //if execution unsuccessful
		//perror("Error: Execution unsuccessful!");
		//return -1;
	//}
	return 0;
}

void executeCommand(char *str){
	int count = 0;
	char* cpy = strndup(str, strlen(str));
	char* argv[20];
	char* ptr = strtok(str, " ");

	while(ptr != NULL){
		argv[count]=ptr;
		ptr = strtok(NULL, " ");
		count ++;
	}
	argv[count] = NULL;
	int extStat= 0;

	if(count != 0){		

		if (strcmp(argv[0], "cd") == 0){

			if ((extStat = chdir(argv[1])) ==0){
			fputs(argv[1], stderr);
		
			}
			else {
			perror(argv[1]);
			}
			add_history(cpy, extStat);
		}
		
		else if (strcmp(argv[0], "history") == 0){
		
      			print_history(0);
			add_history(cpy, extStat);	

		}
	
		else if(strcmp(argv[0], "exit") == 0){
			clear_history();
			free(cpy);
			exit(0); 
		}

		else{

		executeExternalCommand(argv, &extStat);
		//fprintf(stderr, "Error: Invalid command! \n"); [may potentially use]
		add_history(cpy, extStat);
		}
	}
	//add_history(cpy, extStat);
	free(cpy);
	return;		
	
}



