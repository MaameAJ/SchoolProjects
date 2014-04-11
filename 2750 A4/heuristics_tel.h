#ifndef PINKCATSY_TEL
#define PINKCATSY_TEL 1

#include <sys/types.h>
#include <regex.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "iso3166-2_CountryCodes.h"

#define MATCH_ERROR -1
#define MATCH_SUCCESS 0
#define MATCH_FAILURE 1
#define CANON 2

/*
 * Checks to see if the phone number matches NA standard
 * (with or without country code, with or without area code)
 * ARGUMENTS: phone number to be checked, canon number
 * RETURNS	MATCH_ERROR if something goes wrong
 * 		MATCH_SUCCESS if phone meets NA standard and canonNumber has been succesfully filled w/ canonized phone number
 * 		MATCH_FAILURE if phone does not meet NA standard
 */
int checkNAStd(const char * phone, char ** canonNumber);

/*
 * Checks to see if the phone number is a valid foreign number
 * (contains country code, area code and subscriber number)
 * ARGUMENTS: phone number to be checked, canon number
 * RETURNS	MATCH_ERROR if something goes wrong
 * 		MATCH_SUCCESS if phone is valid and canon has been succesfully filled w/ canonized phone number
 * 		MATCH_FAILURE if phone is invalid
 */
int checkOther(const char * phone, char ** canon);


/*
 * Performs heuristics for canon address
 * (Converts country to country code and region to region code if applicable)
 * ARGUMENTS: address to be checked, canon addres to save address into
 * RETURNS	MATCH_ERROR if something goes wrong
 * 		MATCH_SUCCESS if address is valid and canon has been succesfully filled w/ canonized address
 * 		MATCH_FAILURE if address is invalid
 */
int checkCountry(const char * adr, char ** canon);

#endif