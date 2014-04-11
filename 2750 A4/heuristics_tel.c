#include "heuristics_tel.h"

/*
 * Checks to see if the phone number matches NA standard
 * (with or without country code, with or without area code)
 * ARGUMENTS: phone number to be checked, canon number
 * RETURNS	MATCH_ERROR if something goes wrong
 * 		MATCH_SUCCESS if phone meets NA standard and canonNumber has been succesfully filled w/ canonized phone number
 * 		MATCH_FAILURE if phone does not meet NA standard
 */
int checkNAStd(const char * phone, char ** canonNumber){
  int nmatch = 9, i, j;
  regex_t cmp_exp;
  regmatch_t * matches = calloc(nmatch, sizeof(regmatch_t));
  if(regcomp(&cmp_exp, "^\\+?1?(\\-| |\\.)*\\(?([0-9]{3})?\\)?(\\-| |\\.)*([0-9]{3})(\\-| |\\.)*([0-9]{4})(\\-| |\\.)*((x\\.*|ext\\.*|\\|).+)?", REG_EXTENDED) != 0){
    fprintf(stderr, "Something went wrong with regex in phone number match.\n");
    regfree(&cmp_exp);
    free(matches);
    return MATCH_ERROR;
  }
  if(regexec(&cmp_exp, phone, nmatch, matches, REG_NOTEOL) == MATCH_FAILURE){
    free(matches);
    regfree(&cmp_exp);
    return MATCH_FAILURE;
  }//end of if no matches
  else{
    regfree(&cmp_exp);
   
    //save the area code
    char * buffer = calloc(strlen(phone)+1, sizeof(char));
    assert(buffer != NULL);
    buffer = strcpy(buffer, phone);
    int extSIZE = 0;
    for(i = 0; i < nmatch; i++){
      if(matches[i].rm_so == -1){
	continue;
      }
      for(j = matches[i].rm_so; j < matches[i].rm_eo; j++){
	if(i == 8){
	  extSIZE++;
	}
      }//end of for loop
    }
    
    char * start;
    char * areacode = calloc(4, sizeof(char));
    assert(areacode != NULL);
    if(matches[2].rm_so == -1){
      for(i = 0; i < 3; i++){
	areacode[i] = '0';
      }
      //printf("Area code = %s\n", areacode);
    }//end of if there is no area code
    else{
      start = buffer + matches[2].rm_so;
      for(i = 0, j = matches[2].rm_so; j < matches[2].rm_eo; j++, i++){
	    areacode[i] = *start++;
      }
      areacode[3] = '\0';
      //printf("Area code = %s\n", areacode);
    }//end if there's an area code
    //subscriber number
    char * subNum = calloc(9, sizeof(char));
    assert(subNum != NULL);
    if(matches[4].rm_so == -1){
      free(buffer);
      free(areacode);
      free(subNum);
      return MATCH_FAILURE;
    }
    start = buffer + matches[4].rm_so;
    for(i = 0, j = matches[4].rm_so; j < matches[4].rm_eo; j++, i++){
	  subNum[i] = *start++;
    }
    subNum[i] = '-';
    if(matches[6].rm_so == -1){
      free(buffer);
      free(areacode);
      free(subNum);
      return MATCH_FAILURE;
    }
    start = buffer + matches[6].rm_so;
    for(i = i+1, j = matches[6].rm_so; j < matches[6].rm_eo; j++, i++){
	  subNum[i] = *start++;
    }
    subNum[8] = '\0';
    //printf("Subscriber Number = %s\n", subNum);
    
    //extension
    char * extent;
    int istart = 0;
    if(matches[8].rm_so != -1){
      start = buffer + matches[8].rm_so;
      char * ext = strstr(start, "ext");
      if(ext == NULL){
	ext = strstr(start, "Ext");
	if(ext == NULL){
	  ext = strchr(start, 'x');
	  if(ext == NULL){
	    ext = strchr(start, 'X');
	    if(ext == NULL){
	      return MATCH_ERROR;
	    }
	    extSIZE--;
	    istart++;
	  }
	  else{
	    extSIZE--;
	    istart++;
	  }
	}//end of middle ext = null
	else{
	  extSIZE-= 3;
	  istart += 3;
	}
      }//end of outer if ext = NULL
      else{
	extSIZE -= 3;
	istart += 3;
      }
      if(start[extSIZE] == '.' || start[extSIZE] == ' '){
	extSIZE--;
	istart++;
      }
      extent = calloc(extSIZE+1, sizeof(char));
      start += istart;
      extent = strcpy(extent, start);
      extent[extSIZE] = '\0';
    }
    else{
      extent = calloc(1, sizeof(char));
      extent[0] = '\0';
      extSIZE = 0;
    }
    
    (*canonNumber) = calloc(strlen("+1 () ")+strlen(areacode)+strlen(subNum)+strlen(extent)+1, sizeof(char));
    assert(*canonNumber != NULL);
    (*canonNumber) = strcpy((*canonNumber), "+1 (");
    (*canonNumber) = strcat((*canonNumber), areacode);
    (*canonNumber) = strcat((*canonNumber), ") ");
    (*canonNumber) = strcat((*canonNumber), subNum);
    if(extSIZE > 0){
      (*canonNumber) = realloc(*canonNumber, sizeof(char)*(strlen(*canonNumber)+strlen(extent)+2));
      (*canonNumber) = strcat(*canonNumber, "|");
    }
    (*canonNumber) = strcat((*canonNumber), extent);
    //printf("This is the canonNumber: %s\n", (*canonNumber));
    free(buffer);
    free(areacode);
    free(subNum);
  }//end of if phone matches regex
  free(matches);
  regfree(&cmp_exp);
  return MATCH_SUCCESS;
}

int checkOther(const char * phone, char ** canon){
  int nmatch = 8, i, j;
  char * result;
  regex_t cmp_exp;
  regmatch_t * matches = calloc(nmatch, sizeof(regmatch_t));
  if(regcomp(&cmp_exp, "^\\+?([0-9]+)(\\-| |\\.)*\\(?([0-9]+)\\)?(\\-| |\\.)*([0-9]*(\\-| |\\.)[0-9]*)+(\\-| |\\.)*((x\\.*|ext\\.*|\\|).+)?", REG_EXTENDED) != 0){
    fprintf(stderr, "Something went wrong with regex in phone number match.\n");
    regfree(&cmp_exp);
    free(matches);
    return MATCH_ERROR;
  }
  if(regexec(&cmp_exp, phone, nmatch, matches, REG_NOTEOL) == REG_NOMATCH){
    free(matches);
    regfree(&cmp_exp);
    return MATCH_FAILURE;
  }
  else{
    char * buffer = calloc(strlen(phone)+1, sizeof(char));
    int length[nmatch];
    buffer = strcpy(buffer, phone);
    regfree(&cmp_exp);
    for(i = 0; i < nmatch; i++){
      length[i] = 0;
      //fprintf(stderr, "Printing match #%d\n", i);
      if(matches[i].rm_so == -1){
	continue;
      }
      result = buffer + matches[i].rm_so;
      for(j = matches[i].rm_so; j < matches[i].rm_eo; j++){
	length[i]++;
	//fprintf(stderr, "%c", *result++);
      }//end of for loop
      //fprintf(stderr, "\n");
    }
    if(length[1] == 0 || length[3] == 0 || length[5] == 0){ //if there is no country code or area code or subscriber number
      free(matches);
      free(buffer);
      return MATCH_FAILURE;
    }
    else{
      //country code
      char * ccode = calloc(length[1] + 1, sizeof(char));
      assert(ccode != NULL);
      result = buffer + matches[1].rm_so;
      for(i = matches[1].rm_so, j = 0; i < matches[1].rm_eo; i++, j++){
	ccode[j] = *result++;
      }//end of for loop
      ccode[length[1]] = '\0';
      //printf("Country code: %s\n", ccode);
      //area code
      char * acode = calloc(length[3] + 1, sizeof(char));
      assert(acode != NULL);
      result = buffer + matches[3].rm_so;
      for(i = matches[3].rm_so, j = 0; i < matches[3].rm_eo; i++, j++){
	acode[j] = *result++;
      }//end of for loop
      acode[length[3]] = '\0';
      //printf("Area code: %s\n", acode);
      //subscriber number 
      char * scode = calloc(length[5] + 1, sizeof(char));
      assert(scode != NULL);
      result = buffer + matches[5].rm_so;
      for(i = matches[5].rm_so, j = 0; i < matches[5].rm_eo; i++, j++){
	scode[j] = *result++;
      }//end of for loop
      scode[length[5]] = '\0';
      //printf("Subscriber number: %s\n", scode);
      
      
      (*canon) = calloc(length[1]+length[3]+length[5]+strlen("+ () ")+1, sizeof(char));
      (*canon) = strcpy(*canon, "+");
      (*canon) = strcat(*canon, ccode);
      (*canon) = strcat(*canon, " ");
      (*canon) = strcat(*canon, "(");
      (*canon) = strcat(*canon, acode);
      (*canon) = strcat(*canon, ") ");
      (*canon) = strcat(*canon, scode);
      free(ccode);
      free(acode);
      free(scode);
    }
  
    free(matches);
    free(buffer);
  }//end of phone matches
  return MATCH_SUCCESS;
}

int checkCountry(const char * adr, char ** canon){
  char * country, * region, * ccountry, * cregion, *ccode, *rcode;
  int i, semi = 0, cstart, rstart, clen, rlen, ilen;
  
  for(i = 0; i < strlen(adr); i++){
    if(adr[i] == ';'){
      semi++;
      if(semi == 4){
	i++;
	region = calloc(strlen(&adr[i]), sizeof(char));
        region = strcpy(region, &adr[i]);
	rstart = i;
      }
      else if(semi == 6){
	i++;
        country = calloc(strlen(&adr[i]), sizeof(char));
	country = strcpy(country, &adr[i]);
	cstart = i;
	break;
      }
    }//end of if statement
  }//end of for loop
  
  clen = strcspn (country,";");
  ccountry = calloc(clen+1, sizeof(char));
  assert(ccountry != NULL);
  ccountry = strncpy(ccountry, country, clen);
  ccountry = strcat(ccountry, "");
  
  //apply heuristics to country name
  ccode = countryToCode(ccountry);
  if(strcmp(ccode, "") == 0){
    ccode = codeToCountry(ccountry);
    if(strcmp(ccode, "") == 0){
      return MATCH_FAILURE;
    }
    else{
      ccode = ccountry;
    }
  }
  else{
    free(ccountry);
  }
  
  //apply heuristics to region
  rlen = strcspn(region, ";");
  cregion = calloc(rlen+1, sizeof(char));
  assert(cregion != NULL);
  cregion = strncpy(cregion, region, rlen);
  cregion = strcat(cregion, "");
  if(strcmp(ccode, "CA") == 0){
    rcode = provinceToCode(cregion);
    if(strcmp(rcode, "") == 0){
      rcode = codeToProvince(cregion);
      if(strcmp(rcode, "") == 0){
	free(cregion);
	return MATCH_FAILURE;
      }
      else{
	rcode = cregion;
      }
    }//end of rcode failes to match
    else{
      free(cregion);
    }
  }
  else if(strcmp(ccode, "US") == 0){ //we're in the States
    rcode = stateToCode(cregion);
    if(strcmp(rcode, "") == 0){
      rcode = codeToState(cregion);
      if(strcmp(rcode, "") == 0){
	return MATCH_FAILURE;
      }
      else{
	rcode = cregion;
      }
    }//end of rcode failes to match
    else{
      free(cregion);
    }
  }
  else{
    rcode = cregion;
  }
  
  (*canon) = calloc(cstart+strlen(ccode)+strlen(rcode)+2, sizeof(char));
  (*canon) = strncpy((*canon), adr, rstart);
  (*canon) = strcat((*canon), ";");
  (*canon) = strcat((*canon), rcode);
  region += rlen;
  ilen = cstart - (rstart + rlen);
  (*canon) = realloc((*canon), sizeof(char)*strlen((*canon)+ilen+2));
  (*canon) = strncat((*canon), region, ilen);
  (*canon) = strcat((*canon), ccode);
  
  return MATCH_SUCCESS;
  
}//end of checkCountry

// int main(){
//   int result;
//   char * canon = NULL;
//   char * adr = ";;;Toronto;Ontario;L4M 4Z9;CA";
//   result = checkCountry(adr, &canon);
//   if(result == MATCH_SUCCESS){
//     printf("Canon = %s\n", canon);
//     free(canon);
//   }
//   else{
//    printf("Something went wrong trololol\n"); 
//   }
//   char * phone = "1-416-807-7714";
//   printf("%p\n", phone);
//   result = checkNAStd(phone, &canon);
//   printf("Test #1: 1-416-807-7714 -> %s\n", canon);
//   free(canon);
//   
//   result = checkNAStd("(416) 8077714", &canon);
//   printf("Test #2: (416) 8077714 -> %s\n", canon);
//   free(canon);
//   
//   result = checkNAStd("416 807 7714", &canon);
//   printf("Test #3: 416 807 7714 -> %s\n", canon);
//   free(canon);
//   
//   result = checkNAStd("+1 (416) 8077714", &canon);
//   printf("Test #4: +1 (416) 8077714 -> %s\n", canon);
//   free(canon);
//   
//   result = checkNAStd("(519) 824-4120 Ext.52696", &canon);
//   printf("Test #4: (519) 824-4120 Ext.52696 -> %s\n", canon);
//   free(canon);
//   
//   result = checkNAStd("(519) 824-4120 x52696", &canon);
//   printf("Test #4: (519) 824-4120 x52696 -> %s\n", canon);
//   free(canon);
//   
//   //testing checkOther
//   result = checkOther("82 2 2255 0114", &canon);
//   if(result == MATCH_ERROR){
//     fprintf(stderr, "Something went wrong when parsing 82 2 2255 0114\n");
//   }
//   else if(result == MATCH_FAILURE){
//     fprintf(stderr, "82 2 2255 0114 failed to match\n");
//   }
//   else{
//     printf("Test #B1: 82 2 2255 0114 -> %s\n", canon);
//     free(canon);
//   }
//   
//   result = checkOther("82 2 2255 0114x344", &canon);
//   if(result == MATCH_ERROR){
//     fprintf(stderr, "Something went wrong when parsing 82 2 2255 0114x344\n");
//   }
//   else if(result == MATCH_FAILURE){
//     fprintf(stderr, "82 2 2255 0114x344 failed to match\n");
//   }
//   else{
//     printf("Test #B1: 82 2 2255 0114x344 -> %s\n", canon);
//     free(canon);
//   }
//   return MATCH_SUCCESS;
// }