/*----------------------------------------------------------------------------
**
** NAME
**   match -- print lines matching a pattern
**
** SYNOPSIS
**   match pattern
**
** DESCRIPTION
**   The match utility prints lines read from stdin matching a pattern.
**
**   The pattern must be a simple string (no regular expressions).
**
** EXAMPLES
**   match xyz     #Prints lines containing the string xyz read from stdin
**
** EXIT STATUS
**   0 One or more lines matched
**   1 No lines matched
**   2 An error occurred
**
** AUTHORS
**   01/17/19 Epoch.........................................................jrc
**
**---------------------------------------------------------------------------*/

//Import deffinitions required to access functionality implemented elsewhere
#define _GNU_SOURCE
#include <stdio.h>    //Standard I/O functions and definitions
#include <stdlib.h>   //Standard library (includes exit function)
#include <string.h>  //String manipulation functions


//-----------------------------------------------------------------------------
//  usage -- Prints a usage message and exits with status=2
//-----------------------------------------------------------------------------
void usage(char* s) {
    
    //Print the usage message to stderr
    fprintf(stderr,"Usage:  %s pattern\n",s);
    
    //Force an exit.  This is equivalent to return 2 in main()
    exit(2);                //Exit status is 2
    
}


//-----------------------------------------------------------------------------
//  main -- The main function invoked by CRT to start the program
//-----------------------------------------------------------------------------
int main(int argc, char **argv) {
	char *pattern = "";
  //Initialize the normal (non-error) exit status
  int exitStatus = 1;            //No lines have matched (so far)

  //Verify the user remembered to include the pattern on the command line
  if (argc >3 || argc <=1){
	 usage(argv[0]);   //Should have two entires, match & pattern
} 
  //Get a pointer (reference) to the NUL-terminated pattern string
  //char *pattern = argv[1];       //Pattern will get the add
else if (argc == 2){
	pattern = argv[1];
}
else if (argc == 3){
	pattern = argv[2];
}

  //Verify the pattern is not an empty string
  if (strlen(pattern)==0) usage(argv[0]);

    
  //<<<Insert your implementation for match here>>>
char c[1024];

int caseSensitive = 0;
while (fgets(c,sizeof(c),stdin) != NULL){
	if(strcmp(argv[1], "-i") == 0 && caseSensitive == 0){
		if(strcasestr(c,pattern)){		
	puts(c);
		}
	}else{

	if(strstr(c, pattern)){
	puts(c);
	}
}
}
    
  //Report the exit status to the CRT
  return exitStatus;
}


