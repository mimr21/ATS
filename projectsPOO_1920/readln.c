#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define DATA_SIZE 1000

int main(void)
{
FILE* filePointer;
int bufferLength = 255;
char buffer[bufferLength];
char data[DATA_SIZE];
FILE *ptr;
char name[FILENAME_MAX];
filePointer = fopen("output.txt", "r");
int i = 0;
while(fgets(buffer, bufferLength, filePointer)) {

    printf("%s\n", buffer);

     strcat(data,"sonar.projectName=");
     char inte[1000];
     sprintf(inte, "%d", i);
     strcat(data,inte);
     strcat(data,"\nsonar.sources=");
     strcat(data,buffer);
     printf("%s",data);
//printf("ferramenta debug\n");
    //write no ficheri
 snprintf(name, sizeof(name), "%d/%d.txt", i,i);
   ptr=fopen( name, "w");
   //operations to fill data into file i.txt;
   

    fputs(data,ptr); //lmao vai morrer?
    strcpy(data,"");
    fclose(ptr);
    i++;
}

fclose(filePointer);
}