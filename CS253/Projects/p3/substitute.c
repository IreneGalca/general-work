#define _GNU_SOURCE
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "substitute.h"
 
int doSubstitute(char *pattern, char *replace) {
 
 int INIT = 1024; //sets initial size
 char c[INIT]; //creates array
 c[INIT-2] = '\n';//checks if null
 int numPat = 0; //number of times pattern is found
 int oldString = 0;//string before pattern
 int newString = 0;//string after pattern
 char* ptr = "";//first pointer
 char* newPtr; //new pointer
 
	while(fgets(c, sizeof(c), stdin) != NULL) {
 
    	//Checks if line is bigger than array
    	if(c[INIT-2] != '\n' && c[INIT-2] != '\0') { 
      fprintf(stderr, "Error: Line exceeds array size. Stopping.\n"); 
      exit(1); 
      }
      
    	ptr = c; 	//first pointer
    	int pLength = strlen(pattern); //length of pattern string
    	int rLength = strlen(replace); //length of replacement string
    
    	while((ptr = strstr(ptr, pattern)) != NULL) {
        	numPat++;
        	ptr += pLength;
    	}
 
   	  oldString = pLength*numPat; //combines pattern and everything behind pattern
    	newString = rLength*numPat; //combines pattern and everything beyond pattern
 
    	//check if new combine line is a valid size
    	if((strlen(c) - oldString + newString) > INIT) { 
      fprintf(stderr, "Error: Line exceeds array size. Stopping.\n"); 
      exit(1); 
      }
    	
    	//allocate space for new string
    	char* fini = (char*) malloc(strlen(c) - oldString + newString);
    	ptr = c; //reset first pointer
     
    	while((newPtr = strstr(ptr, pattern)) != NULL) {
        	strncat(fini, ptr, (newPtr-ptr)); 
        	strncat(fini, replace, rLength); 
        	ptr = newPtr + pLength; //move first pointer past pattern string
    	}
    	strncat(fini, ptr, strlen(ptr)); //copy combined strings into c
 
    	printf(fini);
 
    	free(fini);
	}
	return 0;
}