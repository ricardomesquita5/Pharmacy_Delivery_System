#include <stdio.h>
#include "asm.h"
#include <time.h>
#include <glob.h>
#include <string.h>
#include <dirent.h> // used to file management
#include <stdlib.h> // to use the file related functions
#include <unistd.h> // to use the sleep function

char **readFlagFiles();

void make_calc(char* batteryChar, char* batteryCapacityChar, char* parkPowerChar, char* chargingVehicleChar, char* fileName, char* id,char* designation);

int main() {
int loop = 0;
while (loop == 0){

FILE *file;

  char batteryChar [10]= {0};
  char batteryCapacityChar[10]= {0};
  char parkPowerChar[10]= {0};
  char chargingVehicleChar[10]= {0};
  char id [10] = {0};
  char designation [10] = {0};

  char ** fileNames = readFlagFiles();
  char empty_line[35];
  unsigned int i;
  
  for( i = 0; fileNames[i] != NULL; i++){
			char string[255];
			strcpy(string,fileNames[i]);
			char *token = strtok(string,".");
			fileNames[i] = NULL;
			char aux[255];
			strcpy(aux,token);
			strcat(aux,".txt");
			fileNames[i] = aux;
		}

    for(i = 0; fileNames[i] != NULL; i++){
			file = fopen(fileNames[i],"r");
			if(file == NULL){
				printf("Error opening source file\n");
			}else{
            char name[255];
			      strcpy(name,fileNames[i]);

			fgets(empty_line,35,file);
            fgets(id,35,file);
			fgets(empty_line,35,file);
            fgets(designation,35,file);
            fgets(empty_line,35,file);
            fgets(batteryChar,35,file);
            fgets(empty_line,35,file);
            fgets(batteryCapacityChar,35,file);
            fgets(empty_line,35,file);
            fgets(parkPowerChar,35,file);
            fgets(empty_line,35,file);
            fgets(chargingVehicleChar,35,file);

            

            make_calc(batteryChar, batteryCapacityChar,parkPowerChar,chargingVehicleChar,name,id,designation);
            
				}
				fclose(file);
			}

}
return 0;

}

char **readFlagFiles(){
	glob_t information;
	char **fileNames = NULL;
	unsigned int i;
	
	switch(glob("lock_*.flag",0,NULL,&information)){
		case 0:
			break;
		case GLOB_NOSPACE:
			printf("Program runned out of memory.");
			break;
		case GLOB_ABORTED:
			printf("Error found.");
			break;
		case GLOB_NOMATCH: 
			break;
	}
	
	//gl_pathc+1 for future if clause to find last position in the "array"
	fileNames = malloc(sizeof(char*)*(information.gl_pathc+1));
	
	for(i = 0; i < information.gl_pathc; i++){
		fileNames[i] = strdup(information.gl_pathv[i]);
	}
	//last position with NULL assignment
	fileNames[i] = NULL;
	globfree(&information);
	return fileNames;
}

void make_calc(char* batteryChar, char* batteryCapacityChar, char* parkPowerChar, char* chargingVehicleChar, char* fileName, char* id, char* designation){
  FILE *fpointer;
  char * title = malloc(30);

    int battery = atoi(batteryChar);
    int batteryCapacity= atoi(batteryCapacityChar);
    int parkPower= atoi(parkPowerChar);
    int chargingVehicles= atoi(chargingVehicleChar);
    
    int result_hour;
    int result_min;
    int result_sec;
    int final_mili;

    int spotPower = cal_spot(parkPower,chargingVehicles);

      result_hour = cal_hour(battery,batteryCapacity,spotPower);
      result_min = cal_min(battery,batteryCapacity,spotPower);
      result_sec = cal_sec(battery,batteryCapacity,spotPower);
      final_mili = cal_finalMili(result_hour,result_min,result_sec);


time_t t = time(NULL);
struct tm tm = *localtime(&t);

int new_sec= tm.tm_sec;
int new_min = tm.tm_min;
int new_hour= tm.tm_hour;
int new_day= tm.tm_mday;
int new_month = tm.tm_mon + 1;
int new_year= tm.tm_year + 1900;

new_sec = final(new_sec,result_sec);

if (new_sec > 59) {
new_sec = new_sec - 60;
new_min = new_min + 1;
}

new_min = final(new_min,result_min);

if (new_min > 59) {
new_min = new_min - 60;
new_hour = new_hour + 1;
}

new_hour = final(new_hour,result_hour);

if (new_hour > 23) {
new_hour = new_hour - 24;
new_day = new_day + 1;
}

if (new_day > 31 && (new_month == 1 ||  new_month == 3 || new_month == 5 || new_month == 7 ||  new_month == 8 ||  new_month == 10 || new_month == 12 )) {
new_day = new_day - 31;
new_month = new_month + 1;
}

if (new_day > 30 && (new_month == 4 ||  new_month == 6 || new_month == 9 || new_month == 11 )) {
new_day = new_day - 30;
new_month = new_month + 1;
}

if (new_month == 2){

  if(check_bisect(new_year) == 0 && new_day > 29){
    new_day = new_day - 29;
    new_month = new_month +1;
  }else if(check_bisect(new_year) != 0 && new_day > 28){
    new_day = new_day - 28;
    new_month = new_month +1;
  }

}

if (new_month > 12) {
new_month = new_month - 12;
new_year = new_year + 1;
}

sprintf(title, "estimate_%d_%02d_%02d_%02d_%02d_%02d.data",tm.tm_year + 1900, tm.tm_mon + 1, tm.tm_mday, tm.tm_hour, tm.tm_min, tm.tm_sec);

fpointer = fopen(title, "w");

char * year = malloc(5);
sprintf(year,"%d\n", new_year);
char * month = malloc(5);
sprintf(month,"%d\n", new_month);
char * day = malloc(5);
sprintf(day,"%d\n", new_day);
char * hour = malloc(5);
sprintf(hour,"%d\n", new_hour);
char * min = malloc(5);
sprintf(min,"%d\n", new_min);
char * sec = malloc(5);
sprintf(sec,"%d\n", new_sec);
char * mili = malloc(20);
sprintf(mili,"%d\n", final_mili);

fputs(id, fpointer);
fputs(designation, fpointer);
fputs(year, fpointer);
fputs(month, fpointer);
fputs(day, fpointer);
fputs(hour, fpointer);
fputs(min, fpointer);
fputs(sec, fpointer);
fputs(mili,fpointer);


fclose(fpointer);

sprintf(title, "estimate_%d_%02d_%02d_%02d_%02d_%02d.data.flag",tm.tm_year + 1900, tm.tm_mon + 1, tm.tm_mday, tm.tm_hour, tm.tm_min, tm.tm_sec);
fpointer = fopen(title, "w");
fclose(fpointer);

remove(fileName);

char string[255];
			strcpy(string,fileName);
			char *token = strtok(string,".");
			char aux[255];
			strcpy(aux,token);
			strcat(aux,".flag");

remove(aux);
}
