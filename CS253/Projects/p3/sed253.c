//-----------------------------------------------------------------------------
//
// NAME
//  sed253 -- Simplified editor
//
// SYNOPSIS
//  sed253
//  sed253 -s regex string
//  sed253 -d line1 line2
//
// DESCRIPTION
//  Simplified editor.  Copies lines read from stdin to stdout.  Options:
//
//  -s Substitute every occurrence of regex with string
//  -d Delete line1 through line2 inclusive
//
// ERRORS
//  Prints usage message and exits abnormally for invalid commands.  Prints an
//  error message and exits abnormally for other issues.
//
// LIMITATIONS
//  Lines of text are limited to a maximum of 1023 chars.
//
// AUTHORS
//  Epoch...................................................................jrc
//
//-----------------------------------------------------------------------------

//Bring in the definitions for our helper functions
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "copy.h"
#include "substitute.h"
#include "delete.h"

//-----------------------------------------------------------------------------
// main -- the main function
//-----------------------------------------------------------------------------
void usage(char* s){
  //print correct usage statement
  fprintf(stderr,"Correct Usage: %s pattern\n", s);
   
  exit(1); //exit status
}
int main(int argc, char **argv) { //checks number of arguments
  char *pattern = "";
  char *replace = "";
  char *fromLine = "";
  char *toLine = "";
  int r = 0;
  
  if(argc > 4 || argc < 1){
  usage(argv[0]);
  }
  else if(argc == 1){ //if using: sed253 <inFile >outFile
      r = doCopy();
  
  }else if(argc == 4){ //if using: sed253 -d <fromLine> <toLine> <inFile >outFile
	if(strcmp(argv[1], "-d") == 0){
	fromLine = argv[2];
	toLine = argv[3];
      r = doDelete(fromLine, toLine);
  }
	else if(strcmp(argv[1], "-s") == 0){
	pattern = argv[2];
	replace = argv[3];
	r = doSubstitute(pattern, replace);
}
}
     else if(strlen(pattern) == 0 || strlen(replace) == 0) usage(argv[0]);
      
  //make sure string is not an empty string
  return r;
 }

