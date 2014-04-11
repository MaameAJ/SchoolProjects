/********
vcutil.c - vCard utility functions
Last updated:  March 10, 2014 

Author: Maame Apenteng #0802637
Contact: mapenten@uoguelph.ca
********/

#include "vcutil.h"
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <stdio.h>
#include <Python.h>



static char * readahead = NULL; //readahead
static int linecount = 0;
/*
 * Helper Function to parse a the name member of a VcProp
 * ARGUMENT VcPname to be parsed
 * RETURNS: Property Name
 */
char * reversePname(VcPname toParse){
  switch(toParse){
    case VCP_BEGIN:
      return "BEGIN";
      break;
    case VCP_END:
      return "END";
      break;
    case VCP_VERSION:
      return "VERSION";
      break;
    case VCP_N:
      return "N";
      break;
    case VCP_FN:
      return "FN";
      break;
    case VCP_NICKNAME:
      return "NICKNAME";
      break;
    case VCP_PHOTO:
      return "PHOTO";
      break;
    case VCP_BDAY:
      return "BDAY";
      break;
    case VCP_ADR:
      return "ADR";
      break;
    case VCP_LABEL:
      return "LABEL";
      break;
    case VCP_TEL:
      return "TEL";
      break;
    case VCP_EMAIL:
      return "EMAIL";
      break;
    case VCP_GEO:
      return "GEO";
      break;
    case VCP_TITLE:
      return "TITLE";
      break;
    case VCP_ORG:
      return "ORG";
      break;
    case VCP_NOTE:
      return "NOTE";
      break;
    case VCP_UID:
      return "UID";
      break;
    case VCP_URL:
      return "URL";
      break;
    case VCP_OTHER :
      return "OTHER";
      break;
  }
  return NULL;
}//end of reversePname

/* Helper function to initialize a VcStatus
 * Arguments: none
 * Returns a VcStatus with lineto and linefrom both initalized to zero
 * NOTE: The error code is NOT initialized
 */
static VcStatus initializeStatus(){
  VcStatus status;
  status.linefrom = linecount;
  status.lineto = linecount;
  return status;
} //end of initializeStatus

/* Helper function to generate an erroneous VcStatus
 * Arguments:
 *	errorno = the error code
 *	linefrom = the line where the error starts (usually the lineto of the calling functions VcStatus)
 *	numlines = the number of lines the error spans (usually the lineto of some other inner function's VcStatus)
 * Returns: a VcStatus
 */
static VcStatus generateStatus (VcError errorno, int linefrom, int lineto){
  VcStatus status;
  status.code = errorno;
  status.linefrom = linefrom;
  status.lineto = lineto;
  return status;
}

/*
 * Helper function to help parse the property name.
 * Arguments: the property name to be parsed
 * Returns: a VcPname
 */
static VcPname parsePname (char * propname){
  
  int i;
  //converts propname to upper case so that search is case insensitive
  for(i = 0; i < strlen(propname); i++){
    propname[i] = toupper(propname[i]);
  }
  
  if(strcmp(propname, "BEGIN") == 0){
    return VCP_BEGIN;
  }
  else if(strcmp(propname, "END") == 0){
    return VCP_END;
  }
  else if(strcmp(propname, "VERSION") == 0){
    return VCP_VERSION;
  }
  else if(strcmp(propname, "N") == 0){
    return VCP_N;
  }
  else if(strcmp(propname, "FN") == 0){
    return VCP_FN;
  }
  else if(strcmp(propname, "NICKNAME") == 0){
    return VCP_NICKNAME;
  }
  else if(strcmp(propname, "PHOTO") == 0){
    return VCP_PHOTO;
  }
  else if(strcmp(propname, "BDAY") == 0){
    return VCP_BDAY;
  }
  else if(strcmp(propname, "ADR") == 0){
    return VCP_ADR;
  }
  else if(strcmp(propname, "LABEL") == 0){
    return VCP_LABEL;
  }
  else if(strcmp(propname, "TEL") == 0){
    return VCP_TEL;
  }
  else if(strcmp(propname, "EMAIL") == 0){
    return VCP_EMAIL;
  }
  else if(strcmp(propname, "GEO") == 0){
    return VCP_GEO;
  }
  else if(strcmp(propname, "TITLE") == 0){
    return VCP_TITLE;
  }
  else if(strcmp(propname, "ORG") == 0){
    return VCP_ORG;
  }
  else if(strcmp(propname, "NOTE") == 0){
    return VCP_NOTE;
  }
  else if(strcmp(propname, "UID") == 0){
    return VCP_UID;
  }
  else if(strcmp(propname, "URL") == 0){
    return VCP_URL;
  }
  else{
    return VCP_OTHER;
  }
}//end of parsePname

/*
 * Searchs the Vcard for the N and FN and END properties
 * Arguments: Array of VcProps to searchVcard
 */
static int searchVcard( VcProp toSearch[]){
  int i;
  bool name = false, forname = false;
  for(i = 0; i < sizeof(toSearch); i++){
    if(toSearch[i].name == VCP_N){
      name = true;
    }
    else if(toSearch[i].name == VCP_FN){
      forname = true;
    }
  }
  return (name && forname);
} //end of searchVcard

/*
 * Copies a VcProperty
 * Arguments: pointer to the destination property, pointer to the source property
 * Returns nothing
 */

static void copyVcProp(VcProp * dest, VcProp * src){
  dest->name = src->name;
  //copy partype
  if(src->partype != NULL){
    dest->partype = calloc(strlen(src->partype)+1, sizeof(char));
    dest->partype = strcpy(dest->partype, src->partype);
  }
  else{
    dest->partype = NULL;
  }
  //copy parval
  if(src->parval != NULL){
    dest->parval = calloc(strlen(src->parval)+1, sizeof(char));
    dest->parval = strcpy(dest->parval, src->parval);
  }
  else{
    dest->parval = NULL;
  }
  //copy value
  dest->value = calloc(strlen(src->value)+1,sizeof(char));
  dest->value = strcpy(dest->value, src->value);
  //copy hook
}

/*
 * Frees a VcProp
 * NOTE: Does not free the propp pointer
 * Arguments: pointer to the VcProp to be freed
 * Returns: Nothing
 */
static void freeVcProp(VcProp *const propp){
  if(propp != NULL){
    if(propp->partype != NULL){
      free(propp->partype);
      propp->partype = NULL;
    }
    if(propp->parval != NULL){
      free(propp->parval);
      propp->parval = NULL;
    }
    if(propp->value != NULL){
      free(propp->value);
      propp->value = NULL;
    }
    //free(cardp->prop[i]->hook);
  }
}//end of freeVcProp

/*
 * Frees a Vcard structure
 * Arguments: Vcard to be freed
 * Returns nothing
 */
static void freeVcard(Vcard * cardp){
  int i;
  if(cardp != NULL){
    for(i = 0; i < cardp->nprops; i++){
      freeVcProp(&cardp->prop[i]);
    }
    free(cardp);
    cardp = NULL;
  }
}


VcStatus readVcFile( FILE *const vcf, VcFile *const filep ){
  
  //Initialize VcFile
  filep->ncards = 0;
  filep->cardp = NULL;
  //other variable declarations
  VcStatus returnStatus = initializeStatus(); //Initalize VcStatus
  VcStatus crrtStatus;
  
  //loop to read in vcards
  while(!feof(vcf)){ 
    Vcard * crrtCard;
    filep->cardp = realloc(filep->cardp, sizeof(Vcard)*(filep->ncards+1));
    crrtStatus = readVcard(vcf, &crrtCard);
    if(crrtStatus.code != OK){
      freeVcFile(filep);
      return crrtStatus;
    }
    else if(crrtCard == NULL){
      break;
    }
    returnStatus.lineto = linecount;
    filep->cardp[filep->ncards] = crrtCard;
    filep->ncards++;
  }//end of loop
  filep->cardp = realloc(filep->cardp, sizeof(Vcard)*(filep->ncards));
  returnStatus.code = OK;
  return returnStatus;
} //end of readVcFile

VcStatus readVcard( FILE *const vcf, Vcard **const cardp ){
  
   //Initialize Vcard
   (*cardp) = calloc(10, sizeof(Vcard)+(sizeof(VcProp)));
   
   (*cardp)->nprops = 0;
   //Initialize other variables
   int i = 0, numalloc = 10;
   VcStatus returnStatus = initializeStatus();
   VcStatus crrtstatus;
   VcProp tempProp;
   char * buffer;
   VcError error;
   bool end = false;
   
   do{//start loop to read in vcards
      crrtstatus = getUnfolded(vcf, &buffer);
      if(&buffer == NULL || buffer == NULL){
	freeVcard(*cardp);
	(*cardp) = NULL;
	return generateStatus(OK, returnStatus.lineto, returnStatus.lineto+crrtstatus.lineto);
      }//end getUnfolded returned NULL;
      error = parseVcProp(buffer, &tempProp);
      free(buffer);
      if(error != OK){
	freeVcProp(&tempProp);
	freeVcard(*cardp);
	(*cardp) = NULL;
	return generateStatus(error, returnStatus.lineto, returnStatus.lineto+crrtstatus.lineto);
      }
      //check for BEGIN
      if(tempProp.name == VCP_BEGIN && i == 0){
	freeVcProp(&tempProp);
	returnStatus.lineto = returnStatus.linefrom + crrtstatus.lineto;
	i++;
	continue;
      }
      else if((tempProp.name == VCP_BEGIN && i > 0) || (tempProp.name != VCP_BEGIN && i == 0)){
	freeVcProp(&tempProp);
	freeVcard(*cardp);
	(*cardp) = NULL;
	return generateStatus(BEGEND, returnStatus.lineto, returnStatus.lineto+crrtstatus.lineto);
      }
      
      //check for VERSION 
      if(i == 1 && tempProp.name != VCP_VERSION){
	freeVcProp(&tempProp);
	freeVcard(*cardp);
	(*cardp) = NULL;
	return generateStatus(NOPVER, returnStatus.lineto, returnStatus.lineto+crrtstatus.lineto);
      }
      else if((i > 1 && tempProp.name == VCP_VERSION) || 
	(tempProp.name == VCP_VERSION && strcmp(tempProp.value,VCARD_VER) != 0)){
	freeVcProp(&tempProp);
	freeVcard(*cardp);
	(*cardp) = NULL;
	return generateStatus(BADVER, returnStatus.lineto, returnStatus.lineto+crrtstatus.lineto);
      }
      else if(tempProp.name == VCP_VERSION && strcmp(tempProp.value,VCARD_VER) == 0){
	freeVcProp(&tempProp);
	i++;
	returnStatus.lineto = returnStatus.linefrom + crrtstatus.lineto;
	continue;
      }
      //check for END
      if(tempProp.name == VCP_END){
	if(!searchVcard((*cardp)->prop)){
	  freeVcProp(&tempProp);
	  freeVcard(*cardp);
	  (*cardp) = NULL;
	  return generateStatus(NOPNFN, returnStatus.lineto, returnStatus.lineto+crrtstatus.lineto);
	}
	else{
	  end = true;
	  break;
	}
      }
      /*FOR ALL OTHER PROPERTIES*/
      //allocate memory for one more property
      if((*cardp)->nprops >= (numalloc - 1)){
	numalloc++;
	(*cardp) = (Vcard *)realloc((*cardp),sizeof(int)+(sizeof(VcProp)*numalloc));
      }
      
      copyVcProp(&(*cardp)->prop[(*cardp)->nprops], &tempProp);
      freeVcProp(&tempProp);
      (*cardp)->nprops++;
      i++;
      returnStatus.lineto = linecount; 
   }while(error == OK);
   
   
   if(end == false){
     freeVcard(*cardp);
     (*cardp) = NULL;
     return generateStatus(BEGEND, returnStatus.lineto, returnStatus.lineto+crrtstatus.lineto);
   }
   
   //clean-up
   if(numalloc > (*cardp)->nprops){
     (*cardp) = (Vcard *) realloc((*cardp),sizeof(Vcard)+(sizeof(VcProp)*((*cardp)->nprops)));
   }
   freeVcProp(&tempProp);
   returnStatus.code = OK;
   return returnStatus;
} //end of readVcard

VcStatus getUnfolded( FILE *const vcf, char **const buff ){
  //initialize VcStatus
    VcStatus returnStatus = initializeStatus();
    returnStatus.code = OK;
    //check if SPECIAL call
    if(vcf == NULL){
      if(readahead != NULL){
	free(readahead);
	readahead = NULL;
      }
      //reset static variables and free allocated memory like linecounts and readahead buffers
      return returnStatus;
    }
   //initialize other variables
    char * firstbuff = calloc(FOLD_LEN+2,sizeof(char));
    char * midbuff;
    char * finalbuff = calloc(2, sizeof(char));
    int whitespace = 1, full_line;
    int i, length; 
    char * nullptr;
   //loop for reading file
    //read in line
    if(readahead == NULL){
      fgets(firstbuff, FOLD_LEN+1, vcf);
    }
    else{
      firstbuff = strcpy(firstbuff, readahead);
      free(readahead);
      readahead = NULL;
    }
    
    do{
      if(feof(vcf)){
	*buff = NULL;
	free(firstbuff);
	free(finalbuff);
	return returnStatus;	
      }
      
      returnStatus.lineto++;
      full_line = 1;
      length = strcspn(firstbuff, "\r\n");
      //if(line = whitespace)
      for(i = 0; i < strlen(firstbuff); i++){
	if(!isspace(firstbuff[i])){
	  whitespace = 0;
	  break;
	}
      } //end of for loop to check if line is all whitespace
      if(whitespace == 1){
	whitespace = 1;
	linecount++;
	continue;
      }
      if(length == strlen(firstbuff)){
	full_line = 0;
      }
      if(full_line != 0){
	linecount++;
      }
      midbuff = calloc(length+1,sizeof(char));
      //remove trailing EOL (/r/n)
      strncpy(midbuff, firstbuff, length);
      free(firstbuff);
      //remove initial whitespace
      if(isspace(midbuff[0])){
	for(i = 0; i < strlen(midbuff)-1; i++){
	  midbuff[i] = midbuff[i+1];
	}
	length--;
      }
      //add null pointer
      nullptr = strchr(midbuff, '\0');
      if(nullptr == NULL || strlen(midbuff) > length){
	midbuff[length] = '\0';
      }
      
      //add line to buffer
      finalbuff = realloc(finalbuff, sizeof(char)*(strlen(finalbuff)+strlen(midbuff)+1));
      strcat(finalbuff, midbuff);
      free(midbuff);
      *buff = finalbuff;
      //read in line
      firstbuff = calloc(FOLD_LEN+2,sizeof(char));
      fgets(firstbuff, FOLD_LEN+1, vcf);
    }while(isspace(firstbuff[0]) || full_line == 0);
      
   //clean-up
   returnStatus.linefrom = returnStatus.lineto - returnStatus.linefrom;
   returnStatus.lineto = returnStatus.linefrom;
   readahead = malloc(sizeof(char)*(strlen(firstbuff)+1));
   readahead = strcpy(readahead, firstbuff);
   free(firstbuff);
   return returnStatus;
} //end of getUnfolded function

VcError parseVcProp( const char *buff, VcProp *const propp ){
  //variable declarations
  int i, namelen, paramlen, paramName;
  char * currentBuff, * ignGroup, * propname, *param, *paramstart, *valstart;
  char * temp;
  //initialize VcProp
  propp->partype = NULL;
  propp->parval = NULL;
  propp->value = NULL;
  //propp->partype = strcpy("\0", propp->value);
  //check format - must match (GROUP.)PROPERTY_NAME(;PARAMETER[S]):PROPERTY_VALUE
  //checks for whitespace in buffer outside of the property value
  for(i = 0; i < strlen(buff); i++){
    if(buff[i] == ':'){
      break;
    }
    else if(isspace(buff[i])){
      freeVcProp(propp);
      return SYNTAX;
    } 
  }
  //check for property value
  valstart = strchr(buff, ':');
  if(valstart == NULL){
    freeVcProp(propp);
    return SYNTAX;
  }
  //ignore (GROUP.)
  ignGroup = strchr(buff, '.');
  
  if(ignGroup != NULL && (strlen(ignGroup) > strlen(valstart))){
    //TEST WHETHER IT DOESN'T INCLUDE THE '.' NOW
    if(strlen(ignGroup) <= 1){
      freeVcProp(propp);
      return SYNTAX;
    }
    ignGroup++;
    currentBuff = malloc(sizeof(char)*(strlen(ignGroup)+1));
    currentBuff = strcpy(currentBuff, ignGroup);
  }
  else{
    currentBuff = malloc(sizeof(char)*(strlen(buff)+1));
    currentBuff = strcpy(currentBuff, buff);
  }
  
  //parse Property name
  namelen = strcspn (currentBuff, ";:");
  //if blank return SYNTAX
  if(namelen == 0){
    free(currentBuff);
    freeVcProp(propp);
    return SYNTAX;
  }
  propname = calloc(namelen+1,sizeof(char));
  strncpy(propname, currentBuff, namelen);
  propp->name = parsePname(propname);
  free(propname);
  if(propp->name == VCP_OTHER){
    propp->value = realloc(propp->value, sizeof(char)*(strlen(buff)+1));
    propp->value = strcpy(propp->value, buff);
    free(currentBuff);
    return OK;
  }//end of if property name is other
  
  //loop for parameters
  
  paramstart = strchr(currentBuff, ';');
  valstart = strchr(currentBuff, ':');
  while(paramstart != NULL && (strlen(paramstart) > strlen(valstart))){
    if(strlen(currentBuff) == 1 || strchr(currentBuff, '=') == NULL){
      free(currentBuff);
      freeVcProp(propp);
      return SYNTAX;
    }
    paramstart++;
    paramlen = strcspn(paramstart, ":;");
    temp = currentBuff;
    currentBuff = calloc(strlen(paramstart)+1, sizeof(char));
    currentBuff = strcpy(currentBuff, paramstart);
    free(temp);
    //if parameter is blank return SYNTAX
    if(paramlen == 0){
      free(currentBuff);
      freeVcProp(propp);
      return SYNTAX;
    }
    
    param = calloc(paramlen+1,sizeof(char));
    param = strncpy(param, currentBuff, paramlen);
    paramstart = strchr(param, '=');
    if(paramstart == NULL){
      free(currentBuff);
      freeVcProp(propp);
      return SYNTAX;
    }
    //convert the parameter name to upper case to allow for case insensitivity
    paramName = strcspn(param, "=");
    for(i = 0; i < paramName; i++){
      param[i] = toupper(param[i]);
    }
    
    if(strncmp(param, "TYPE=", strlen("TYPE=")) == 0){
      if(paramlen == strlen("TYPE=")){
	free(param);
	param = calloc(2, sizeof(char));
      }
      else{
	paramstart++;
	temp = param;
	param = calloc(paramlen, sizeof(char));
	strncpy(param, paramstart, paramlen-1);
	free(temp);
      }
      
      if(propp->partype == NULL){;
	propp->partype = realloc(propp->partype, sizeof(char)*(strlen(param)+1));
	propp->partype = strcpy(propp->partype,param);
      }
      else{
	propp->partype = realloc(propp->partype, sizeof(char)*(strlen(propp->partype)+strlen(param)+2));
	propp->partype = strcat(propp->partype,",");
	propp->partype = strcat(propp->partype,param);
      }
    }
    else if(strncmp(param, "VALUE=", strlen("VALUE=")) == 0){
      if(paramlen == strlen("VALUE=")){
	free(param);
	param = calloc(2,sizeof(char));
      }
      else{
	paramstart++;
	temp = param;
	param = calloc(paramlen, sizeof(char));
	strncpy(param, paramstart, paramlen-1);
	free(temp);
      }
      
      if(propp->parval == NULL){
	propp->parval = realloc(propp->parval, sizeof(char)*(strlen(param)+1));
	propp->parval = strcpy(propp->parval, param);
      }
      else{
	propp->parval = realloc(propp->parval, sizeof(char)*(strlen(propp->parval)+strlen(param)+2));
	propp->parval = strcat(propp->parval,",");
	propp->parval = strcat(propp->parval,param);
      }
    }
    else if(strncmp(param, "CHARSET=", strlen("CHARSET=") != 0) && strncmp(param, "ENCODING=", strlen("ENCODING=") != 0)){
      free(param);
      free(currentBuff);
      freeVcProp(propp);
      return PAROVER;
    }
    paramstart = strchr(currentBuff, ';');
    valstart = strchr(currentBuff, ':');
    free(param);
  }//end of loop for parameters
  
  
  if(strlen(currentBuff) <= 1){
    free(currentBuff);
    freeVcProp(propp);
    return SYNTAX;
  }
  valstart++;
  propp->value = realloc(propp->value, sizeof(char)*(strlen(valstart)+1));
  propp->value = strcpy(propp->value, valstart);
  free(currentBuff);
  return OK;
} //end of parseVcProp

VcStatus writeVcFile(FILE *const vcf, const VcFile *filep){
  /* Pseudocode */
   int i, j;
   int printed = 0; //number of lines outputted
   int charsleft = 0; //characters left to be printed (used when folding), also used to check for errors
   char * temp;
   bool fold;
   for(i = 0; i < filep->ncards; i++){
    charsleft = fprintf(vcf,"BEGIN:VCARD\r\n");
    printed++;
    if(charsleft < 0){ //if an error occured when printing to vcf
      generateStatus(IOERR, printed, printed);
    }
    charsleft = fprintf(vcf, "VERSION:%s\r\n", VCARD_VER);
    printed++;
    if(charsleft < 0){ //if an error occured when printing to vcf
      generateStatus(IOERR, printed, printed);
    }
    for(j = 0; j < filep->cardp[i]->nprops; j++){//for-each property
      VcProp currentProp = filep->cardp[i]->prop[j];
      char * buffer;
      if(currentProp.name == VCP_OTHER){
	buffer = calloc(strlen(currentProp.value)+1, sizeof(char));
	buffer = strcpy(buffer, currentProp.value);
      }
      else{ //buffer = property(;type and value params):value
	char * name = reversePname(currentProp.name);
	char * comma;
	if(name == NULL){
	  return generateStatus(IOERR, printed + 1, printed + 1);
	}
	buffer = calloc(strlen(name)+1, sizeof(char));
	strcpy(buffer, name);
	//parse TYPE param and value param and add to buffer
	if(currentProp.partype != NULL){
	  comma = strchr(currentProp.partype, ',');
	  if(comma == NULL){
	    buffer = realloc(buffer, sizeof(char)*(strlen(buffer)+strlen(";TYPE=")+strlen(currentProp.partype)+1));
	    buffer = strcat(buffer, ";TYPE=");
	    buffer = strcat(buffer, currentProp.partype);
	  }
	  else{
	    temp = calloc(strlen(currentProp.partype)+1, sizeof(char));
	    temp = strcpy(temp, currentProp.partype);
	    comma = strtok(temp, ",");
	    while(comma != NULL){
	      buffer = realloc(buffer, sizeof(char)*(strlen(buffer)+strlen(";TYPE=")+strlen(comma)+1));
	      buffer = strcat(buffer, ";TYPE=");
	      buffer = strcat(buffer, comma);
	      comma = strtok(NULL, ",");
	    }//end of while loop
	    free(temp);
	  }//end of else statement
	}//end of if partype != NULL
	if(currentProp.parval != NULL){
	  comma = strchr(currentProp.parval, ',');
	  if(comma == NULL){
	    buffer = realloc(buffer, sizeof(char)*(strlen(buffer)+strlen(";VALUE=")+strlen(currentProp.parval)+1));
	    buffer = strcat(buffer, ";VALUE=");
	    buffer = strcat(buffer, currentProp.parval);
	  }
	  else{
	    temp = calloc(strlen(currentProp.parval)+1, sizeof(char));
	    temp = strcpy(temp, currentProp.parval);
	    comma = strtok(temp, ",");
	    while(comma != NULL){
	      buffer = realloc(buffer, sizeof(char)*(strlen(buffer)+strlen(";VALUE=")+strlen(comma)+1));
	      buffer = strcat(buffer, ";VALUE=");
	      buffer = strcat(buffer, comma);
	      comma = strtok(NULL, ",");
	    }//end of while loop
	    free(temp);
	  }//end of else statement
	}//end of if parval != NULL
	//append the value to the buffer
	buffer = realloc(buffer, sizeof(char)*(strlen(buffer)+strlen(currentProp.value)+2));
	buffer = strcat(buffer, ":");
	buffer = strcat(buffer, currentProp.value);
      }
      if(strlen(buffer) > FOLD_LEN){
	fold = false;
	char * printBuff = calloc(FOLD_LEN+1, sizeof(char));
	do{
	  fold = false;
	  charsleft = snprintf(printBuff, FOLD_LEN+1, "%s", buffer); //copy FOLD_LEN chars into printBuff
	  if(charsleft < 0){              
	    free(buffer);
	    free(printBuff);
	    return generateStatus(IOERR, printed + 1, printed + 1);
	  }
	  if(fprintf(vcf, "%s\r\n", printBuff) < 0){
	    free(buffer);
	    free(printBuff);
	    return generateStatus(IOERR, printed + 1, printed + 1);
	  }
	  printed++;
	  if(charsleft > FOLD_LEN){
	    if(fprintf(vcf, "\t") < 0){
	      free(buffer);
	      free(printBuff);
	      return generateStatus(IOERR, printed + 1, printed + 1);
	    }//end if error occured
	    fold = true;
	    temp = buffer;
	    buffer = calloc((strlen(buffer) - FOLD_LEN)+1, sizeof(char));
	    buffer = strcpy(buffer, &temp[FOLD_LEN]);
	    free(temp);
	  }
	}while(fold);
	free(printBuff);
      }
      else{
	if(fprintf(vcf, "%s\r\n", buffer) < 0){
	  free(buffer);
	  return generateStatus(IOERR, printed + 1, printed + 1);
	}
	printed++;
      }
      free(buffer);
    }//end of for each property loop
    if(fprintf(vcf, "END:VCARD\r\n") < 0){
      generateStatus(IOERR, printed + 1, printed + 1);
    }
    printed++;
  }//end of loop for vCards in the file
  
  VcStatus returnStatus;
  returnStatus.code = OK;
  returnStatus.lineto = printed;
  returnStatus.linefrom = printed;
  return returnStatus;
}//end of writeVcFile

void freeVcFile( VcFile * filep ){
  int i;
  if(filep != NULL){
    for(i = 0; i < filep->ncards; i++){
      freeVcard(filep->cardp[i]);
    }
    free(filep->cardp);
  }
} //end of freeVcFile

/**************************************************************
 * WRAPPER FUNCTIONS
 **************************************************************/

static PyObject * generateError(VcStatus result){
  char msg[100];
  
  switch(result.code){
    case SYNTAX:
      sprintf(msg, "Syntax error on lines %d-%d", result.linefrom, result.lineto);
      break;
    case PAROVER:
      sprintf(msg, "Parameter overflow on lines %d-%d", result.linefrom, result.lineto);
      break;
    case BEGEND:
      sprintf(msg, "Invalid BEGIN or END on lines %d-%d", result.linefrom, result.lineto);
      break;
    case BADVER:
      sprintf(msg, "Bad version on lines %d-%d", result.linefrom, result.lineto);
      break;
    case NOPNFN:
      sprintf(msg, "Missing formatted or name on lines %d-%d", result.linefrom, result.lineto);
      break;
    case NOPVER:
      sprintf(msg, "Missing version on lines %d-%d", result.linefrom, result.lineto);
      break;
    case OK:
      sprintf(msg, "OK");
      break;
    case IOERR:
      sprintf(msg, "IO error on lines %d-%d", result.linefrom, result.lineto);
      break;
  }//end of switch 
  return PyBytes_FromString(msg);
}//en d of generateError

VcFile gfile;

int n = 0; //card index

static PyObject *Vcf_readFile( PyObject *self, PyObject *args ){
  char *filename;
  VcStatus result;
  FILE * fptr;
//   VcFile nfile;
  
  if(!PyArg_ParseTuple( args, "s", &filename )){
    return NULL;
  }
  
  fptr = fopen(filename, "r");
  if(fptr == NULL){
    char * errmsg = strerror(errno);
    char msg[25+strlen(errmsg)];
    sprintf(msg, "Cannot open file due to %s", errmsg);
    return PyBytes_FromString(msg);
  }
  
  result = readVcFile(fptr, &gfile);
  return generateError(result);
  
}//end of readVcFile wrapper function

PyObject *Vcf_getCard( PyObject *self, PyObject *args ){
  PyObject * card;
  PyObject * nextprop;
  int result, i;
  
  if(!PyArg_ParseTuple(args,"O",&card)){
    return NULL;
  }
  
  if(n == gfile.ncards){
    return PyLong_FromLong(0);
  }
  else{
    Vcard * crrtC = gfile.cardp[n];
    for(i = 0; i < crrtC->nprops; i++){
      VcProp crrtP = crrtC->prop[i];
      printf("%d", crrtP.name);
      nextprop = PyTuple_New(4);
      PyTuple_SetItem(nextprop, 0, PyLong_FromLong(crrtP.name));
      if(crrtP.partype != NULL){
	PyTuple_SetItem(nextprop, 1, PyBytes_FromString(crrtP.partype));
      }
      else{
	PyTuple_SetItem(nextprop, 1, PyBytes_FromString(""));
      }
      if(crrtP.parval != NULL){
	PyTuple_SetItem(nextprop, 2, PyBytes_FromString(crrtP.parval));
      }
      else{
	PyTuple_SetItem(nextprop, 2, PyBytes_FromString(""));
      }
      if(crrtP.value != NULL){
	PyTuple_SetItem(nextprop, 3, PyBytes_FromString(crrtP.value));
      }
      else{
	PyTuple_SetItem(nextprop, 3, PyBytes_FromString(""));
      }
      result = PyList_Append(card, nextprop);
      if(result == -1){
	fprintf(stderr, "An error occured when attempted to retrieve card #%d\n", n+1);
	return PyLong_FromLong(0);
      }
    }
    n++;
  }//end of else 
  return  PyLong_FromLong(1);
}//end of vcf_getCard

PyObject *Vcf_freeFile( PyObject *self, PyObject *args ){
  freeVcFile(&gfile);
  //gfile = NULL;
  return PyLong_FromLong(0);
}//end of wrapper function for freeVcFile

PyObject *Vcf_writeFile( PyObject *self, PyObject *args ){
  char *filename;
  PyObject *cards;
  FILE * fptr;
  Py_ssize_t i, j, max, nprops;
  PyObject * ccard/*, * cprop, *name, *parval, *partype, *value*/;
  VcFile * wfile;
  Vcard * crrtC;
//   VcStatus status;
//   int m, n;
  
  if(!PyArg_ParseTuple( args, "sO", &filename, &cards)){
    return NULL;
  }
  
  fptr = fopen(filename, "w");
  if(fptr == NULL){
    char * errmsg = strerror(errno);
    char msg[25+strlen(errmsg)];
    sprintf(msg, "Cannot open file due to %s", errmsg);
    return PyBytes_FromString(msg);
  }
  
  wfile = calloc(1, sizeof(VcFile));
  
  max = PyList_Size(cards);
  wfile->ncards = max;
  wfile->cardp = calloc(max, sizeof(Vcard*));
  
  for(i = 0; i < max; i++){
    ccard = PyList_GetItem(cards, i);
    nprops = PyList_Size(ccard);
    crrtC = calloc(nprops, sizeof(Vcard)+sizeof(VcProp));
    crrtC->nprops = nprops;
//     printf("This is what crrtC = %p", crrtC);
    for(j = 0; j < nprops; j++){
      //grab python object
      PyArg_ParseTuple(PyList_GetItem(ccard, j), "isss", &(crrtC->prop[j].name), &(crrtC->prop[j].partype), &(crrtC->prop[j].parval), &(crrtC->prop[j].value));
    }//end of j loop
    wfile->cardp[i] = crrtC;
  }//end of i loop
  
//   printf("This is the number of cards: %d\n", wfile->ncards);
//   for(m = 0; m < wfile->ncards; m++){
//     printf("Is this even fucking working?\n");
//     crrtC = wfile->cardp[m];
//     printf("This is card #%d\n", m+1);
//     for(n = 0; n < crrtC->nprops; n++){
//       VcProp crrtP = crrtC->prop[n];
//       printf("Prop #%d: VcPName= %d Partype = %s, Parval = %s, Value = %s\n", n, crrtP.name, crrtP.partype, crrtP.parval, crrtP.value);
//     }
//   }
  //status = writeVcFile(fptr, wfile);
  
  //freeVcFile(wfile);
  
  return PyLong_FromLong(1);
  //generateError(status);
  
}//end of wrapper function for writeVcFile


PyObject *Vcf_getVcPName( PyObject *self, PyObject *args){
  VcPname i;
  PyObject * pname;
  
  if(!PyArg_ParseTuple(args,"O",&pname)){
    return NULL;
  }
  for(i = VCP_BEGIN; i <= VCP_OTHER; i++){
    switch(i){
      case VCP_BEGIN:
	PyList_Append(pname, PyBytes_FromString("BEGIN"));
	break;
      case VCP_END:
	PyList_Append(pname, PyBytes_FromString("END"));
	break;
      case VCP_VERSION:
	PyList_Append(pname, PyBytes_FromString("VERSION"));
	break;
      case VCP_N:
	PyList_Append(pname, PyBytes_FromString("N"));
	break;
      case VCP_FN:
	PyList_Append(pname, PyBytes_FromString("FN"));
	break;
      case VCP_NICKNAME:
	PyList_Append(pname, PyBytes_FromString("NICKNAME"));
	break;
      case VCP_PHOTO:
	PyList_Append(pname, PyBytes_FromString("PHOTO"));
	break;
      case VCP_BDAY:
	PyList_Append(pname, PyBytes_FromString("BDAY"));
	break;
      case VCP_ADR:
	PyList_Append(pname, PyBytes_FromString("ADR"));
	break;
      case VCP_LABEL:
	PyList_Append(pname, PyBytes_FromString("LABEL"));
	break;
      case VCP_TEL:
	PyList_Append(pname, PyBytes_FromString("TEL"));
	break;
      case VCP_EMAIL:
	PyList_Append(pname, PyBytes_FromString("EMAIL"));
	break;
      case VCP_GEO:
	PyList_Append(pname, PyBytes_FromString("GEO"));
	break;
      case VCP_TITLE:
	PyList_Append(pname, PyBytes_FromString("TITLE"));
	break;
      case VCP_ORG:
	PyList_Append(pname, PyBytes_FromString("ORG"));
	break;
      case VCP_NOTE:
	PyList_Append(pname, PyBytes_FromString("NOTE"));
	break;
      case VCP_UID:
	PyList_Append(pname, PyBytes_FromString("UID"));
	break;
      case VCP_URL:
	PyList_Append(pname, PyBytes_FromString("URL"));
	break;
      case VCP_OTHER:
	PyList_Append(pname, PyBytes_FromString("OTHER"));
	break;
    }//end of switch loop
  }//for loop
  return pname;
}//end of vcf_getVcPName

//Module Method Definition
static PyMethodDef vcfMethods[] = {
  {"readFile", Vcf_readFile, METH_VARARGS},
  {"getCard", Vcf_getCard, METH_VARARGS},
  {"freeFile", Vcf_freeFile, METH_NOARGS},
  {"writeFile", Vcf_writeFile, METH_VARARGS},
  {"getPnames", Vcf_getVcPName, METH_VARARGS},
  {NULL, NULL} 
};

//Module Header Definition
static struct PyModuleDef vcfModuleDef = {
  PyModuleDef_HEAD_INIT,
  "Vcf", //enable "import Vcf"
  NULL, //omit module documentation
  -1, //module keeps state in global variables
  vcfMethods //link module name "Vcf" to methods table 
};

PyMODINIT_FUNC 

PyInit_Vcf(void) { //creates the module
  return PyModule_Create(&vcfModuleDef); 
}



