//delete file made to use delete.h
#define _GNU_SOURCE
#include "delete.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int doDelete(char *line1, char *line2){

//array
char c[1024];

//lines to delete
   int fromLine = atoi(line1);
   int toLine = atoi(line2);

   //checks if fromLine or toLine are valid
   if(fromLine == 0 || toLine == 0){
	fprintf(stderr, "Error: Line not detected.\n");
	fprintf(stderr, "Please type in a valid line.\n");
	fprintf(stderr, "Usage: sed253 -d line1 line2.\n");
	exit(2);
}
   //checks if line1 is greater than line2
   if(fromLine > toLine){
	fprintf(stderr, "Error: line1 is greater than line2.\n"); 
	exit(2);
}

int index = 1; //line index

//finds each line in the txt file
while(fgets(c,sizeof(c),stdin) != NULL){

   //check if line is greater than array size
   if(c[1024] != '\n' && c[1024] != '\0'){
	fprintf(stderr, "Error: Line exceeds array size. Aray can only hold 1023 characters.\n");    
   }

   //check if line is outside of dedletion boundaries
   if(index < fromLine || index > toLine){
	fputs(c, stdout);
}
	index++;
}
return 0;
}
