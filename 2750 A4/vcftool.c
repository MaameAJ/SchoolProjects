#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <ctype.h>
#include <string.h>
#include <strings.h>
#include <sys/types.h>
#include <regex.h>
#include <assert.h>
#include "vcftool.h"
#include "iso3166-2_CountryCodes.h"
#include "heuristics_tel.h"

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


static Vcard * copyVcard(Vcard * dest, Vcard * src){
  int i;
  dest = calloc(src->nprops, sizeof(Vcard)+(sizeof(VcProp)));
  dest->nprops = src->nprops;
  for(i = 0; i < src->nprops; i++){
    copyVcProp(&(dest->prop[i]), &(src->prop[i]));
  }
  return dest;
}

/*
 * Compares Vcards by N property
 * Arguments: Vcards to be compared
 * Returns an integer less than, equal to, or greater than zero if card1 is found, respectively, to
 * be less than, to match, or be greater than card2.
 */
static int cmpCard(const void * one, const void * two){
  Vcard * card1 = *(Vcard **) one;
  Vcard * card2 = *(Vcard **) two;
  char * name1, *name2, *given1, *given2, *fam1, *fam2;
  int i, cmp_result;
  VcProp crrtProp;
  for(i = 0; i < card1->nprops; i++){
    crrtProp = card1->prop[i];
    if(crrtProp.name == VCP_N){
      name1 = calloc(strlen(crrtProp.value)+1, sizeof(char));
      assert(name1 != NULL);
      name1 = strcpy(name1, crrtProp.value);
      break;
    }//end of if statement
  }
  
  for(i = 0; i < card2->nprops; i++){
    crrtProp = card2->prop[i];
    if(crrtProp.name == VCP_N){
      name2 = calloc(strlen(crrtProp.value)+1, sizeof(char));
      assert(name2 != NULL);
      name2 = strcpy(name2, crrtProp.value);
      break;
    }//end of if statement
  }
  fam1 = strtok(name1, ";");
  if(fam1 == NULL || strcmp(fam1, "(none)") == 0){
    fam1 = "";
  }
  given1 = strtok(NULL, ";");
  if(given1 == NULL || strcmp(given1, "(none)") == 0){
    given1 = "";
  }
  fam2 = strtok(name2, ";");
  if(fam2 == NULL || strcmp(fam2, "(none)") == 0){
    fam2 = "";
  }
  given2 = strtok(NULL, ";");
  if(given2 == NULL || strcmp(given2, "(none)") == 0){
    given2 = "";
  }
  
  cmp_result = strcasecmp(fam1, fam2);
  if(cmp_result == 0){
    cmp_result = strcasecmp(given1, given2);
  }
  free(name1);
  free(name2);
  return cmp_result;
}

static bool hasCanon(VcProp crrtProp, bool checkCanon){
  int k;
  if(crrtProp.value[0] == '@' && crrtProp.value[5] == '@'){
    for(k = 1; k < 5; k++){
      if(checkCanon){
	if(crrtProp.value[k] == '*' || (crrtProp.value[k] != '-' && crrtProp.value[k] != 'N' && crrtProp.value[k] != 'A' && crrtProp.value[k] != 'T' && crrtProp.value[k] != 'G')){
	  return false;
	}
      }
      else{
	if(crrtProp.value[k] != '*' && crrtProp.value[k] != '-' && crrtProp.value[k] != 'N' && crrtProp.value[k] != 'A' && crrtProp.value[k] != 'T' && crrtProp.value[k] != 'G'){
	    return false;
	}
      }
    }
    return true;
  }
  return false;
}

static char * fixCase(char * canonName){
  int i;
  for(i = 0; i < strlen(canonName); i++){
    if((i == 0 || canonName[i-1] == ';') && isalpha(canonName[i])){
      canonName[i] = toupper(canonName[i]);
    }//end of if statement
  }//end of for loop
  return canonName;
}//end of fixCase function

int vcfInfo(FILE * const outfile, const VcFile * filep){
  char * prevGiven = "", * crrtGiven;
  char * prevFamily = "", *crrtFamily;
  char * name, *prevName = NULL;
  int i, j, k, nphoto = 0, nurl = 0, ngeo = 0, ncanon = 0;
  bool sorted = true, skip, hasUrl, hasGeo, hasPhoto;

  
  for(i = 0; i < filep->ncards; i++){
    Vcard * crrtCard = filep->cardp[i];
    skip = false;
    hasUrl = false;
    hasGeo = false;
    hasPhoto = false;
    for(j = 0; j < crrtCard->nprops; j++){
      VcProp crrtProp = crrtCard->prop[j];
      switch(crrtProp.name){
	case VCP_N:
	  if(!skip){
	    //tokenize the name
	    name = calloc(strlen(crrtProp.value)+1,sizeof(char));
	    assert(name != NULL);
	    name = strcpy(name, crrtProp.value);
	    crrtFamily = strtok(name, ";");
	    if(strcmp(crrtFamily, "(none)") == 0){
	      crrtFamily = "";
	    }
	    crrtGiven = strtok(NULL, ";");
	    if(strcmp(crrtGiven, "(none)") == 0){
	      crrtGiven = "";
	    }
	    //make case insensitive
	    for(k = 0; k < strlen(crrtFamily); k++){
	      crrtFamily[k] = toupper(crrtFamily[k]);
	    }
	    for(k = 0; k < strlen(crrtGiven); k++){
	      crrtGiven[k] = toupper(crrtGiven[k]);
	    }
	    if(strcmp(prevFamily, crrtFamily) > 0){
	      sorted = false;
	    }
	    else if(strcmp(prevFamily, crrtFamily) == 0 && strcmp(prevGiven, crrtGiven) > 0){
		sorted = false;
	    }
	    prevFamily = crrtFamily;
	    prevGiven = crrtGiven;
	    if(prevName != NULL){ //to ensure that the now obselete name pointer is freed
	      free(prevName);
	    }
	    prevName = name; //store the name pointer so that it can be freed when it is no longer useful
	    skip = true;
	  }
	  break;
	case VCP_PHOTO:
	  hasPhoto = true;
	  break;
	case VCP_GEO:
	  hasGeo = true;
	  break;
	case VCP_URL:
	  hasUrl = true;
	  break;
	case VCP_UID:
	  if(hasCanon(crrtProp, true)){
	    ncanon++;
	  }
	  break;
	default:
	  break;
      }//end of switch
    } //end of for each property loop
    if(hasPhoto){
      nphoto++;
    }
    if(hasGeo){
      ngeo++;
    }
    if(hasUrl){
      nurl++;
    }
  }//end of for each card loop
  //output
  fprintf(outfile, "%d cards ", filep->ncards);
  if(sorted){
    fprintf(outfile, "(sorted)\n");
  }
  else{
    fprintf(outfile, "(not sorted)\n");
  }
  fprintf(outfile, "%d with photos\n", nphoto);
  fprintf(outfile, "%d with URLs\n", nurl);
  fprintf(outfile, "%d with geographical coordinates\n", ngeo);
  fprintf(outfile, "%d in canonical form\n", ncanon);
  free(name);
  return 0;
}//end of vcfInfo

int vcfSelect(VcFile * const filep, const char * which){
  bool needPhoto = false, needUrl = false, needGeo = false;
  bool havePhoto, haveUrl, haveGeo;
  int i, j, nselect = 0;
  Vcard ** selp = NULL;
  
  if(strchr(which, 'p') != NULL){
    needPhoto = true;
  }
  if(strchr(which, 'u') != NULL){
    needUrl = true;
  }
  if(strchr(which, 'g') != NULL){
    needGeo = true;
  }
  
  for(i = 0; i < filep->ncards; i++){
    Vcard * crrtCard = filep->cardp[i];
    haveUrl = false;
    havePhoto = false;
    haveGeo = false;
    for(j = 0; j < crrtCard->nprops; j++){
      VcProp crrtProp = crrtCard->prop[j];
      switch(crrtProp.name){
	case VCP_PHOTO:
	  havePhoto = true;
	  break;
	case VCP_URL:
	  haveUrl = true;
	  break;
	case VCP_GEO:
	  haveGeo = true;
	  break;
	default:
	  break;
      }//end of switch case
    }//end of  for each property loop
    if((needPhoto && !havePhoto) || (needUrl && !haveUrl) || (needGeo && !haveGeo)){
      continue;
    }
    else{
      selp = realloc(selp, sizeof(Vcard*)*(nselect+1));
      selp[nselect] = copyVcard(selp[nselect], crrtCard);
      nselect++;
    }
  }//end of for each card loop
  filep->ncards = nselect;
  free(filep->cardp);
  if(nselect == 0){
    fprintf(stderr, "No cards selected.\n");   
  }
  else{
    filep->cardp = selp;
  }
  return 0;
}

int vcfSort(VcFile *const filep){
  qsort(filep->cardp, filep->ncards, sizeof(Vcard*), cmpCard);
  return 0;
}

int vcfCanProp(VcProp *const propp){
  regex_t cmp_exp;
  switch(propp->name){
    case VCP_N:
      
      if(regcomp(&cmp_exp, "^([A-Z][A-z]*|\\(none\\));([A-Z][a-z]*|\\(none\\));*\\w*;*\\w;*", REG_EXTENDED|REG_NOSUB) != 0){
	fprintf(stderr, "Something went wrong with regex.\n");
	regfree(&cmp_exp);
	return 3;
      }
      
      if(regexec(&cmp_exp, propp->value, 0, NULL, REG_NOTEOL) == REG_NOMATCH){
	regfree(&cmp_exp);
	//apply heuristics
	
	char * canonName, *valuePTR;
	if((valuePTR = strchr(propp->value, ';')) == NULL){
	  return 3;
	}
	//parse the family name
	
	int namelen = strcspn(propp->value, ";");
	if(namelen == 0){
	  canonName = calloc(strlen("(none);")+1, sizeof(char));
	  assert(canonName != NULL);
	  canonName = strcpy(canonName, "(none);");
	}
	else{
	  canonName = calloc(namelen+2, sizeof(char));
	  assert(canonName != NULL);
	  canonName = strncpy(canonName, propp->value, namelen);
	  canonName = strcat(canonName, ";");
	}
	//parse the given name
	if(strlen(valuePTR) == strlen(propp->value)){ //if ; is at the end of the string - how did I do this in A1
	  canonName = realloc(canonName, sizeof(char)*(strlen(canonName)+strlen("(none);;;")+1));
	  assert(canonName != NULL);
	  canonName = strcat(canonName, "(none);;;");
	  canonName = fixCase(canonName);
	  propp->value = calloc(strlen(canonName)+1, sizeof(char));
	  propp->value = strcpy(propp->value, canonName);
	  free(canonName);
	  return 2;
	}
	else{
	  valuePTR++;
	  namelen = strcspn(valuePTR, ";");
	  
	  if(namelen == 0){
	    canonName = realloc(canonName, sizeof(char)*(strlen(canonName)+strlen("(none);;;")+1));
	    assert(canonName != NULL);
	    canonName = strcat(canonName, "(none);;;");
	    canonName = fixCase(canonName);
	    free(propp->value);
	    propp->value = calloc(strlen(canonName)+1, sizeof(char));
	    propp->value = strcpy(propp->value, canonName);
	    free(canonName);
	    return 2;
	  }
	  else if(namelen == strlen(valuePTR)){
	    canonName = realloc(canonName, sizeof(char)*(strlen(canonName)+namelen+4));
	    assert(canonName != NULL);
	    canonName = strncat(canonName, valuePTR, namelen);
	    canonName = strcat(canonName,";;;");
	    canonName = fixCase(canonName);
	    free(propp->value);
	    propp->value = calloc(strlen(canonName)+1, sizeof(char)); 
	    assert(propp->value != NULL);
	    propp->value = strcpy(propp->value, canonName);
	    free(canonName);
	    return 2;
	  }
	  else{
	    do{
	      namelen = strcspn(valuePTR, ";");
	      canonName = realloc(canonName, sizeof(char)*(strlen(canonName)+namelen+4));
	      assert(canonName != NULL);
	      canonName = strncat(canonName, valuePTR, namelen);
	      canonName = strcat(canonName, ";");
	      valuePTR = strchr(valuePTR, ';');
	      valuePTR++;
	    }while(namelen != 0);
	    //check to see if there are enough ';'
	    int semi = 0, i;
	    for(i = 0; i < strlen(canonName); i++){
	      if(canonName[i] == ';'){
		semi++;
	      }
	    }
	    if(semi < 4){
	      canonName = realloc(canonName, sizeof(char)*(strlen(canonName)+(4-semi)));
	      for(i = semi; i < 4; i++){
		canonName = strcat(canonName, ";");
	      }
	    }
	    canonName = fixCase(canonName);
	    free(propp->value);
	    propp->value = calloc(strlen(canonName)+1, sizeof(char)); 
	    assert(propp->value != NULL);
	    propp->value = strcpy(propp->value, canonName);
	    free(canonName);
	    return 2;  
	  }
	}//end of if/else statement for the given name
	
      }
      else{
	regfree(&cmp_exp);
	return 1;
      }
      break;
    case VCP_ADR:
      if(regcomp(&cmp_exp, ";.*;.*;.*;[A-z]{2,};.*;[A-Z]{2}", REG_EXTENDED|REG_NOSUB) != 0){
	fprintf(stderr, "Something went wrong with regex.\n");
	regfree(&cmp_exp);
	return 3;
      }
      if(regexec(&cmp_exp, propp->value, 0, NULL, REG_NOTEOL) == REG_NOMATCH){
	regfree(&cmp_exp);
	//fprintf(stderr, "This is the address: %s\n", propp->value);
	//apply heuristics
	int i, length, canonLen = 0;
	char * semi = propp->value, *region = calloc(2, sizeof(char)), *country = calloc(2, sizeof(char));
	char * canonC, *canonR, *canonADR, *postal = calloc(2, sizeof(char));
	assert(region != NULL);
	assert(country != NULL);
	assert(postal != NULL);
	for(i = 0; i < 6; i++){
	  canonLen += strcspn(semi, ";") + 1;
	  semi = strchr(semi, ';');
	  if(semi == NULL || strlen(semi) <= 1){
	    free(region);
	    free(country);
	    free(postal);
	    return 3;
	  }
	  semi++;
	  if(i == 3){
	    length = strcspn(semi, ";");
	    canonLen -= length;
	    region = realloc(region, sizeof(char)*(length+1));
	    assert(region != NULL);
	    region = strncpy(region, semi, length);
	    region[length] = '\0';
	  }
	  else if(i == 4){
	    length = strcspn(semi, ";");
	    canonLen -= length;
	    postal = realloc(postal, sizeof(char)*(length+1));
	    assert(postal != NULL);
	    postal = strncpy(postal, semi, length);
	    postal[length] = '\0';
	  }
	  else if(i == 5){
	    length = strlen(semi);
	    canonLen -= 2;
	    country = realloc(country, sizeof(char)*(length+1));
	    assert(country != NULL);
	    country = strncpy(country, semi, length);
	    country[length] = '\0';
	  }
	}
	char * ccountry = codeToCountry(country);
	if(strcmp(ccountry, "") == 0){
	  canonC = countryToCode(country);
	  free(country);
	}
	else{
	  canonC = ccountry;
	}
	free(ccountry);
	
	if(strcmp(canonC, "") == 0){
	  free(postal);
	  free(region);
	  free(canonC);
	  return 3;
	}
	else if(strcmp(canonC, "CA") == 0){
	  canonR = provinceToCode(region);
	  if(strcmp(canonR, "") == 0){
	    char * province = codeToProvince(region);
	    if(strcmp(province, "") == 0){
	      free(province);
	      free(postal);
	      free(canonC);
	      free(canonR);
	      return 3;
	    }
	    free(province);
	    free(canonR);
	    canonR = region;
	  }
	  else{
	    free(region);
	  }
	}
	else if(strcmp(canonC, "US") == 0){
	  canonR = stateToCode(region);
	  if(strcmp(canonR, "") == 0){
	    char * state = codeToState(region);
	    if(strcmp(state, "") == 0){
	      free(state);
	      free(postal);
	      free(canonC);
	      free(canonR);
	      return 3;
	    }
	    free(state);
	    free(canonR);
	    canonR = region;
	  }
	  else{
	    free(region);
	  }
	}
	else{
	  canonR = region;
	}
	
	canonADR = calloc(canonLen+strlen(canonC)+strlen(canonR)+strlen(postal)+7, sizeof(char));
	assert(canonADR != NULL);
	canonADR = strncpy(canonADR, propp->value, canonLen);
	canonADR = strcat(canonADR, "");
	if(canonADR[strlen(canonADR)-1] != ';'){
	  canonADR = strcat(canonADR, ";");
	}
	canonADR = strcat(canonADR, canonR);
	canonADR = strcat(canonADR, ";");
	canonADR = strcat(canonADR, postal);
	canonADR = strcat(canonADR, ";");
	canonADR = strcat(canonADR, canonC);
	//fprintf(stderr, "Canon ADR: %s\n", canonADR);
	free(propp->value);
	propp->value = calloc(strlen(canonADR)+1, sizeof(char));
	assert(propp->value != NULL);
	propp->value = strcpy(propp->value, canonADR);
	free(postal);
	free(canonR);
	free(canonC);
	free(canonADR);
	return 2;
      }
      else{
	regfree(&cmp_exp);
	char * canonADR = NULL;
	int result = checkCountry(propp->value, &canonADR);
	if(result == MATCH_FAILURE || result == MATCH_ERROR){
	  return 3;
	}
	else if(result == CANON){
	  return 1;
	}
	free(propp->value);
	propp->value = calloc(strlen(canonADR)+1, sizeof(char));
	propp->value = strcpy(propp->value, canonADR);
	free(canonADR);
	return 2;
      }
      break;
    case VCP_TEL:
      if(regcomp(&cmp_exp, "^\\+[0-9]+ [\\([0-9]*\\) ]*[0-9]+(\\-| |\\.)*[0-9]*(\\|+.*)*$", REG_EXTENDED|REG_NOSUB) != 0){
	fprintf(stderr, "Something went wrong with regex in phone number match.\n");
	regfree(&cmp_exp);
	return 3;
      }
      if(regexec(&cmp_exp, propp->value, 0, NULL, 0) == REG_NOMATCH){
	regfree(&cmp_exp);
	/*apply heuristics*/
	//check to see if only missing the plus
	if(regcomp(&cmp_exp, "^[0-9]+ [\\([0-9]*\\) ]*[0-9]+(\\-| |\\.)*[0-9]*(\\|+.*)*$", REG_EXTENDED|REG_NOSUB) != 0){ 
	  fprintf(stderr, "Something went wrong with regex in phone number match.\n");
	  regfree(&cmp_exp);
	  return 3;
	}
	if(regexec(&cmp_exp, propp->value, 0, NULL, 0) == 0){
	  regfree(&cmp_exp);
	  char * canonNumber = calloc(strlen(propp->value)+2, sizeof(char));
	  assert(canonNumber != NULL);
	  canonNumber = strcpy(canonNumber, "+");
	  canonNumber = strcat(canonNumber, propp->value);
	  //fprintf(stderr, "Canon TEL: %s\n", canonNumber);
	  free(propp->value);
	  propp->value = calloc(strlen(canonNumber)+1, sizeof(char));
	  assert(propp->value != NULL);
	  propp->value = strcpy(propp->value, canonNumber);
	  free(canonNumber);
	  return 2;
	}
	regfree(&cmp_exp);
	char * canonNumber;
	int result = checkNAStd(propp->value, &canonNumber);
	if(result == MATCH_ERROR){
	  return 3;
	}
	else if(result == MATCH_FAILURE){
	  result = checkOther(propp->value, &canonNumber);
	  if(result == MATCH_ERROR || result == MATCH_FAILURE){
	    return 3;
	  }
	  else{
	    //fprintf(stderr, "Canon TEL: %s\n", canonNumber);
	    free(propp->value);
	    propp->value = calloc(strlen(canonNumber)+1, sizeof(char));
	    propp->value = strcpy(propp->value, canonNumber);
	    free(canonNumber);
	    return 2;
	  }
	}
	else{
	  //fprintf(stderr, "Canon TEL: %s\n", canonNumber);
	  free(propp->value);
	  propp->value = calloc(strlen(canonNumber)+1, sizeof(char));
	  propp->value = strcpy(propp->value, canonNumber);
	  free(canonNumber);
	  return 2;
	}
	//#warning heuristics do not check for extension
      }
      else{
	regfree(&cmp_exp);
	return 1;
      }
      break;
    case VCP_GEO:
      if(regcomp(&cmp_exp, "\\-?[0-9]{1,2}\\.[0-9]{6};\\-?[0-1]?[0-8]?[0-9]?\\.[0-9]{6}", REG_EXTENDED|REG_NOSUB) != 0){
	fprintf(stderr, "Something went wrong with regex.\n");
	regfree(&cmp_exp);
	return 3;
      }
      if(regexec(&cmp_exp, propp->value, 0, NULL, REG_NOTEOL) == REG_NOMATCH){
	regfree(&cmp_exp);
	//apply heuristics
	double dblLat, dblLon;
	char * strLat, *strLon, *check;
	char * coord = calloc(strlen(propp->value)+1, sizeof(char));
	assert(coord != NULL);
	coord = strcpy(coord, propp->value);
	strLat = strtok(coord, " ,;");
	strLon = strtok(NULL, " ,;");
	if(strLat == NULL || strLon == NULL){
	  free(coord);
	  return 3;
	}
	dblLat = strtod(strLat, &check);
	if((dblLat == 0 && strLat == check) || dblLat > 90 || dblLat < -90){
	  free(coord);
	  return 3;
	}
	dblLon = strtod(strLon, &check);
	if((dblLon == 0 && strLon == check) || dblLon > 180 || dblLon < -180){
	  free(coord);
	  return 3;
	}
	propp->value = realloc(propp->value, sizeof(char)*(strlen("-90.000000;-180.000000")+1));
	assert(propp->value != NULL);
	sprintf(propp->value, "%.6f;%.6f", dblLat, dblLon);
	//fprintf(stderr, "Canon Geo: %s\n", propp->value);
	free(coord);
	return 2;
      }
      else{
	regfree(&cmp_exp);
	return 1;
      }
      regfree(&cmp_exp);
      break;
    default:
      return 0;
  }//end of switch statement
  return 3;
}

int vcfCanon(VcFile *const filep ){
  int i, j, isCanon;
  Vcard * crrtCard;
  VcProp * crrtProp;
  bool skipName = false, skipADR = false, skipTEL = false, skipGEO = false;
  char canon[8] = "@----@;\0";
  for(i = 0; i < filep->ncards; i++){
    crrtCard = filep->cardp[i];
    int UID = -1;
    for(j = 0; j < crrtCard->nprops; j++){
      crrtProp = &crrtCard->prop[j];
      switch(crrtProp->name){
	case VCP_N:
	  isCanon = vcfCanProp(crrtProp);
	  if(skipName){
	    break;
	  }
	  if(isCanon > 0 && isCanon <= 2){
	    canon[1] = 'N';
	  }
	  else if(isCanon == 3){
	    canon[1] = '*'; 
	    skipName = true;
	  }
	  break;
	case VCP_ADR:
	  isCanon = vcfCanProp(crrtProp);
	  if(skipADR){
	    break;
	  }
	  if(isCanon > 0 && isCanon <= 2){
	    canon[2] = 'A';
	  }
	  else if(isCanon == 3){
	    canon[2] = '*';
	    skipADR = true;
	  }
	  break;
	case VCP_TEL:
	  isCanon = vcfCanProp(crrtProp);
	  if(skipTEL){
	    break;
	  }
	  if(isCanon > 0 && isCanon <= 2){
	    canon[3] = 'T';
	  }
	  else if(isCanon == 3){
	    canon[3] = '*';
	    skipTEL = true;
	  }
	  break;
	case VCP_GEO:
	  isCanon = vcfCanProp(crrtProp);
	  if(skipGEO){
	    break;
	  }
	  if(isCanon > 0 && isCanon <= 2){
	    canon[4] = 'G';
	  }
	  else if(isCanon == 3){
	    canon[4] = '*';
	    skipGEO = true;
	  }
	  break;
	case VCP_UID:
	  UID = j;
	default:
	  break;
      }//end of switch statement
    }//end of for each property loop
    if(UID < 0){
	filep->cardp[i] = realloc(filep->cardp[i], sizeof(Vcard)+(sizeof(VcProp)*(filep->cardp[i]->nprops+1)));
	assert(filep->cardp[i] != NULL);
	crrtCard = filep->cardp[i];
	crrtCard->prop[crrtCard->nprops].name = VCP_UID;
	crrtCard->prop[crrtCard->nprops].parval = NULL;
	crrtCard->prop[crrtCard->nprops].partype = NULL;
	crrtCard->prop[crrtCard->nprops].value = calloc(strlen(canon)+1, sizeof(char));
	assert(crrtCard->prop[crrtCard->nprops].value != NULL);
	crrtCard->prop[crrtCard->nprops].value = strcpy(crrtCard->prop[crrtCard->nprops].value, canon);
	crrtCard->prop[crrtCard->nprops].hook = NULL;
	filep->cardp[i]->nprops++;
    }
    else{
      if(hasCanon(crrtCard->prop[UID], false)){
	for(j = 0; j < strlen(canon); j++){
	  crrtCard->prop[UID].value[i] = canon[i];
	}
      }//end of if card already has a canonicalization string
      else{
	char * oldValue = calloc(strlen(crrtCard->prop[UID].value)+1, sizeof(char));
	assert(oldValue != NULL);
	oldValue = strcpy(oldValue, crrtCard->prop[UID].value);
	free(crrtCard->prop[UID].value);
	crrtCard->prop[UID].value = calloc(strlen(canon)+strlen(oldValue)+1, sizeof(char));
	assert(crrtCard->prop[UID].value != NULL);
	crrtCard->prop[UID].value = strcpy(crrtCard->prop[UID].value, canon);
	crrtCard->prop[UID].value = strcat(crrtCard->prop[UID].value, oldValue);
	free(oldValue);
      }
    }//end of if Vcard already has a UID property
  }//end of for each Vcard
  return 0;
}

int main(int argc, char ** argv){
  VcFile filep;
  VcStatus status;
  int i, rtn;
  //check to ensure that there are enough arguments
  if(argc < 2){
    fprintf(stderr, "Error: not enough arguments.\n");
    return 1;
  }
  
  status = readVcFile(stdin, &filep);
  if(status.code == OK){
    //figure out which command to execute
    for(i = 1; i < argc; i++){
      if(strcmp(argv[i], "-info") == 0){
	vcfInfo(stdout, &filep);
	if(i+1 != argc){
	  fprintf(stderr, "Too many commands, sorry.\n");
	  return EXIT_FAILURE;
	}
      }
      else if(strcmp(argv[i], "-select")== 0){
	if(i+1 >= argc){
	  fprintf(stderr, "Please specify which properties to select cards based on.\n");
	  return 1;
	}
	vcfSelect(&filep, argv[++i]);
	if(filep.ncards != 0){
	  writeVcFile(stdout, &filep);
	}
	if(i+1 != argc){
	  fprintf(stderr, "Too many commands, sorry.\n");
	  return EXIT_FAILURE;
	}
      }
      else if(strcmp(argv[i], "-canon") == 0){
	vcfCanon(&filep);
	writeVcFile(stdout, &filep);
	if(i+1 != argc){
	  fprintf(stderr, "Too many commands, sorry.\n");
	  return EXIT_FAILURE;
	}
      }
      else if(strcmp(argv[i], "-sort") == 0){
	vcfSort(&filep);
	writeVcFile(stdout, &filep);
	if(i+1 != argc){
	  fprintf(stderr, "Too many commands, sorry.\n");
	  return EXIT_FAILURE;
	}
      }
      else{
	fprintf(stderr, "Invalid command.\n");
	return EXIT_FAILURE;
      }
    }
    rtn = EXIT_SUCCESS;
  }
  else{
    rtn = EXIT_FAILURE;
    switch(status.code){
	case BEGEND:
	  fprintf(stderr, "BEGEND error occured on lines %d - %d", status.linefrom, status.lineto );
	  break;
	case BADVER:
	  fprintf(stderr, "BADVER error occured on lines %d - %d", status.linefrom, status.lineto );
	  break;
	case NOPVER:
	  fprintf(stderr, "NOPVER error occured on lines %d - %d", status.linefrom, status.lineto );
	  break;
	case NOPNFN:
	  fprintf(stderr, "NOPNFN error occured on lines %d - %d", status.linefrom, status.lineto );
	  break;
	case SYNTAX:
	  fprintf(stderr, "SYNTAX error occured on lines %d - %d", status.linefrom, status.lineto );
	  break;
	case PAROVER:
	  fprintf(stderr, "PAROVER error occured on lines %d - %d", status.linefrom, status.lineto );
	  break;
	default:
	  fprintf(stderr, "Unknown error occured on lines %d - %d", status.linefrom, status.lineto );
	  break;
      }
  }
  /* clean-up */
  getUnfolded(NULL, NULL);
  freeVcFile(&filep);
  return rtn;
}