//Include definitions for C Runtime Library functions used in this program
#include <stdio.h>				//The standard I/O functions

//-------------------------------------------------------------------------------
//This is the main function, invoked by the C Runtime (CRT) to start the program
//-------------------------------------------------------------------------------
int main(void) {
	char c;
	int capCon = 0;
	int lowCon = 0;
	int upperVow = 0;
	int lowerVow = 0;
	int digits = 0;
	int other = 0;

	//gets a char from stdin (the given file in this case)
	while((c = getchar()) != EOF){
	switch(c){ //capital consonants
	 case 'B': case 'C': case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': case 'L': case 'M': case 'N': case 'P': case 'Q': case 'R': case 'S': case 'T': case 'V': case 'W': case 'X': case 'Z':
	capCon++;
	break;

	 // lower case consonants
	 case 'b': case 'c': case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': case 'l': case 'm': case 'n': case 'p': case 'q': case 'r': case 's': case 't': case 'v': case 'w': case 'x': case 'z':
	lowCon++;
	break;

	 // upper vowels
	case 'A': case 'E':  case 'I': case 'O': case 'U': case 'Y':
	upperVow++;
	break;

	// lower vowels	
	case 'a': case 'e': case 'i': case 'o': case 'u': case 'y':
	lowerVow++;
	break;
	
	// digits
	case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': case '0':
	digits++;
	break;

	//others
	case '?': case '\n':
	other++;
	break;	
}


}

	printf("Upper-case: %d \n", capCon + upperVow);//adds all upper cases together
	printf("Lower-case: %d \n", lowCon + lowerVow);//adds all lower cases together
	printf("Vowels: %d \n", upperVow + lowerVow); //adds only vowels 
	printf("Consonants: %d \n", capCon + lowCon); //adds only consonants
	printf("Digits: %d \n", digits);
	printf("Total Characters: %d \n", capCon + upperVow + lowCon + lowerVow + digits + other);	
  return 0;
}

