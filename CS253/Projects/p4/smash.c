//Contains the [main] function with its loop for reading and 
//executing commands.

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "smash.h"
#include "history.h"
#define MAX 1024

int main()
{
	char start[MAX];
	fputs("$ ", stderr);	
  init_history();
	while (fgets(start, MAX, stdin) != NULL){

	start[strlen(start)-1] = '\0';
	executeCommand(start);
	fputs("$ ", stderr);
}
	return 0;
}
