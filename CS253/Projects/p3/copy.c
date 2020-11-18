//Copy file to be implemented in copy.h

#include <stdio.h>
#include <stdlib.h>
#include "copy.h"

int doCopy(){

  char c[1024];

while(fgets(c,sizeof(c),stdin) != NULL){

  //while((c = getchar()) != EOF){
  //if(strstr(c,pattern){
    fputs(c, stdout);
    }
     return 0;
} 
 
