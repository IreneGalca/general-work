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
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/types.h>
#include <assert.h>

#define MAXLINES 4096
#define MAXCHARS 1024
 
int input = 0;
int output = 0;
char inArray[3096];
char outArray[3096];
//[implement pipes here]:


int executeExternalCommand(char **argv, char *cpy){
	int pid = fork();
	//int status;
	//char args[20];
	//fprintf(stderr, "first exitStat: %d \n", *exitStat);


	if(pid <0){ //if fork() unsuccessful
		perror("Error: Fork unsuccessful!");
		//exit(exitSt);
		return -1;
		//fprintf(stderr, "Second exitStat: %d \n", *exitStat);

	}

    //clid process
	if(pid == 0){//if fork is successful
    //if input given
        if(input){
            int fileDesc0;
            if((fileDesc0 = open(inArray, O_RDONLY)) < 0){
                perror("Error: Could not open input file");
                exit(1);
            }
            dup2(fileDesc0, 0);
            close(fileDesc0);
        }
		//execvp(argv[0], argv);
		//[implement redirection here]:
		if(execvp(argv[0],argv) < 0){ //if execution unsuccessful
			perror("Error: Execution unsuccessful!");
			exit(1);

			}	
            fclose(stdout);
			exit(0);
	}

	else if(pid > 0){//if parent
    input = 0;
    output = 0;
    int currStat;
    wait(&currStat);
    int exitStat = WEXITSTATUS(currStat);
    add_history(cpy, exitStat);
    return 127;
	//*exitStat = 127;
	//wait(exitStat);
	//fprintf(stderr, "exitStat: %d\n", *exitStat);
	}

    else{
        perror("Error: Execution unsuccessful!");
        return 127;
    }

	//else if(execvp(argv[0],argv) < 0){ //if execution unsuccessful
		//perror("Error: Execution unsuccessful!");
		//return -1;
	//}
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

		executeExternalCommand(argv, cpy);
		//fprintf(stderr, "Error: Invalid command! \n"); [may potentially use]
		add_history(cpy, extStat);
		}
	}
	//add_history(cpy, extStat);
	free(cpy);
	return;		
	
}



