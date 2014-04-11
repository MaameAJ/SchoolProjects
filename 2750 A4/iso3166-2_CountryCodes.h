/******************************************************************************
 * FILE NAME: iso3166-2_CountryCodes.h
 * PURPOSE: Interface for iso3166-2_CountryCodes.c
 * AUTHOR: Przemyslaw Gawron
 * CREATED: Thursday, February 6, 2014
 * CONTACT: pgawron@uoguelph.ca
 * NOTE: Created for Assignment2 of cis2750 at the University of Guelph
 ******************************************************************************/

#ifndef _iso3166_2_CountryCodes_h
#define _iso3166_2_CountryCodes_h

/***********************************************************************
 * Function Name: countryToCode
 * Purpose: Converts a country to its ISO 2-character code.
 * Parameters: - The address of the country variable to be converted
 * Returns: - An empty string ("") if the country doesn't have a
 *            ISO 2-character code. Otherwise it will return a char* to
 *            a 2-character code.
* NOTE: Caller is responsible for freeing. Including the empty string("").
 ************************************************************************/
char *countryToCode(char *country);

/***********************************************************************
 * Function Name: codeToCountry
 * Purpose: Converts an  ISO 2-character code to its Country.
 * Parameters: - The address of the code variable to be converted
 * Returns: - An empty string ("") if the code doesn't have a country 
              associated with it. Otherwise it will return a char* to
 *            a country.
 * NOTE: Caller is responsible for freeing. Including the empty string("").
 ************************************************************************/
char *codeToCountry(char *code);

/***********************************************************************
 * Function Name: provinceToCode
 * Purpose: Converts a province to an ISO 2-character.
 * Parameters: - The address of the province variable to be converted
 * Returns: - An empty string ("") if the province doesn't have an ISO-2
 *             character code. Otherwise it will return a char* to
 *            a 2-character code.
 * NOTE: Caller is responsible for freeing. Including the empty string("").
 ************************************************************************/
char *provinceToCode(char *province);

/***********************************************************************
 * Function Name: codeToProvince
 * Purpose: Converts an ISO 2-character to its province.
 * Parameters: - The address of the code variable to be converted
 * Returns: - An empty string ("") if the code doesn't have a
 *            province associated with it. Otherwise it will return a char* 
 *            to a province.
 * NOTE: Caller is responsible for freeing. Including the empty string("").
 ************************************************************************/
char *codeToProvince(char *code);

/***********************************************************************
 * Function Name: sateToCode
 * Purpose: Converts a state to an ISO 2-character.
 * Parameters: - The address of the state variable to be converted
 * Returns: - An empty string ("") if the state doesn't have an ISO-2
 *             character code. Otherwise it will return a char* to
 *            a 2-character code.
 * NOTE: Caller is responsible for freeing. Including the empty string("").
 ************************************************************************/
char *stateToCode(char *state);

/***********************************************************************
 * Function Name: codeToState
 * Purpose: Converts an ISO 2-character to its state.
 * Parameters: - The address of the code variable to be converted
 * Returns: - An empty string ("") if the code doesn't have a
 *            state associated with it. Otherwise it will return a char* to
 *            a state.
 * NOTE: Caller is responsible for freeing. Including the empty string("").
 ************************************************************************/
char *codeToState(char *code);

#endif
