/******************************************************************************
 * FILE NAME: iso3166-2_CountryCodes.c
 * PURPOSE:
 * AUTHOR: Przemyslaw Gawron
 * CREATED: Thursday, February 6, 2014
 * CONTACT: pgawron@uoguelph.ca
 * NOTE: Created for Assignment2 of cis2750 at the University of Guelph
 ******************************************************************************/

#define _GNU_SOURCE
#include "iso3166-2_CountryCodes.h"
#include <string.h>

char *countryToCode(char *country){
    char *countryCode = NULL;
    if(strcasecmp("AFGHANISTAN",country) == 0){
        countryCode = strdup("AF");
        return countryCode;
    }else if(strcasecmp("ÅLAND ISLANDS",country) == 0){
        countryCode = strdup("AX");
        return countryCode;
    }else if(strcasecmp("ALBANIA",country) == 0){
        countryCode = strdup("AL");
        return countryCode;
    }else if(strcasecmp("ALGERIA",country) == 0){
        countryCode = strdup("DZ");
        return countryCode;
    }else if(strcasecmp("AMERICAN SAMOA",country) == 0){
        countryCode = strdup("AS");
        return countryCode;
    }else if(strcasecmp("ANDORRA",country) == 0){
        countryCode = strdup("AD");
        return countryCode;
    }else if(strcasecmp("ANGOLA",country) == 0){
        countryCode = strdup("AO");
        return countryCode;
    }else if(strcasecmp("ANGUILLA",country) == 0){
        countryCode = strdup("AI");
        return countryCode;
    }else if(strcasecmp("ANTARCTICA",country) == 0){
        countryCode = strdup("AQ");
        return countryCode;
    }else if(strcasecmp("ANTIGUA AND BARBUDA",country) == 0){
        countryCode = strdup("AG");
        return countryCode;
    }else if(strcasecmp("ARGENTINA",country) == 0){
        countryCode = strdup("AR");
        return countryCode;
    }else if(strcasecmp("ARMENIA",country) == 0){
        countryCode = strdup("AM");
        return countryCode;
    }else if(strcasecmp("ARUBA",country) == 0){
        countryCode = strdup("AW");
        return countryCode;
    }else if(strcasecmp("AUSTRALIA",country) == 0){
        countryCode = strdup("AU");
        return countryCode;
    }else if(strcasecmp("AUSTRIA",country) == 0){
        countryCode = strdup("AT");
        return countryCode;
    }else if(strcasecmp("AZERBAIJAN",country) == 0){
        countryCode = strdup("AZ");
        return countryCode;
    }else if(strcasecmp("BAHAMAS",country) == 0){
        countryCode = strdup("BS");
        return countryCode;
    }else if(strcasecmp("BAHRAIN",country) == 0){
        countryCode = strdup("BH");
        return countryCode;
    }else if(strcasecmp("BANGLADESH",country) == 0){
        countryCode = strdup("BD");
        return countryCode;
    }else if(strcasecmp("BARBADOS",country) == 0){
        countryCode = strdup("BB");
        return countryCode;
    }else if(strcasecmp("BELARUS",country) == 0){
        countryCode = strdup("BY");
        return countryCode;
    }else if(strcasecmp("BELGIUM",country) == 0){
        countryCode = strdup("BE");
        return countryCode;
    }else if(strcasecmp("BELIZE",country) == 0){
        countryCode = strdup("BZ");
        return countryCode;
    }else if(strcasecmp("BENIN",country) == 0){
        countryCode = strdup("BJ");
        return countryCode;
    }else if(strcasecmp("BERMUDA",country) == 0){
        countryCode = strdup("BM");
        return countryCode;
    }else if(strcasecmp("BHUTAN",country) == 0){
        countryCode = strdup("BT");
        return countryCode;
    }else if(strcasecmp("BOLIVIA, PLURINATIONAL STATE OF",country) == 0){
        countryCode = strdup("BO");
        return countryCode;
    }else if(strcasecmp("BONAIRE, SINT EUSTATIUS AND SABA",country) == 0){
        countryCode = strdup("BQ");
        return countryCode;
    }else if(strcasecmp("BOSNIA AND HERZEGOVINA",country) == 0){
        countryCode = strdup("BA");
        return countryCode;
    }else if(strcasecmp("BOTSWANA",country) == 0){
        countryCode = strdup("BW");
        return countryCode;
    }else if(strcasecmp("BOUVET ISLAND",country) == 0){
        countryCode = strdup("BV");
        return countryCode;
    }else if(strcasecmp("BRAZIL",country) == 0){
        countryCode = strdup("BR");
        return countryCode;
    }else if(strcasecmp("BRITISH INDIAN OCEAN TERRITORY",country) == 0){
        countryCode = strdup("IO");
        return countryCode;
    }else if(strcasecmp("BRUNEI DARUSSALAM",country) == 0){
        countryCode = strdup("BN");
        return countryCode;
    }else if(strcasecmp("BULGARIA",country) == 0){
        countryCode = strdup("BG");
        return countryCode;
    }else if(strcasecmp("BURKINA FASO",country) == 0){
        countryCode = strdup("BF");
        return countryCode;
    }else if(strcasecmp("BURUNDI",country) == 0){
        countryCode = strdup("BI");
        return countryCode;
    }else if(strcasecmp("CAMBODIA",country) == 0){
        countryCode = strdup("KH");
        return countryCode;
    }else if(strcasecmp("CAMEROON",country) == 0){
        countryCode = strdup("CM");
        return countryCode;
    }else if(strcasecmp("CANADA",country) == 0){
        countryCode = strdup("CA");
        return countryCode;
    }else if(strcasecmp("CAPE VERDE",country) == 0){
        countryCode = strdup("CV");
        return countryCode;
    }else if(strcasecmp("CAYMAN ISLANDS",country) == 0){
        countryCode = strdup("KY");
        return countryCode;
    }else if(strcasecmp("CENTRAL AFRICAN REPUBLIC",country) == 0){
        countryCode = strdup("CF");
        return countryCode;
    }else if(strcasecmp("CHAD",country) == 0){
        countryCode = strdup("TD");
        return countryCode;
    }else if(strcasecmp("CHILE",country) == 0){
        countryCode = strdup("CL");
        return countryCode;
    }else if(strcasecmp("CHINA",country) == 0){
        countryCode = strdup("CN");
        return countryCode;
    }else if(strcasecmp("CHRISTMAS ISLAND",country) == 0){
        countryCode = strdup("CX");
        return countryCode;
    }else if(strcasecmp("COCOS (KEELING) ISLANDS",country) == 0){
        countryCode = strdup("CC");
        return countryCode;
    }else if(strcasecmp("COLOMBIA",country) == 0){
        countryCode = strdup("CO");
        return countryCode;
    }else if(strcasecmp("COMOROS",country) == 0){
        countryCode = strdup("KM");
        return countryCode;
    }else if(strcasecmp("CONGO",country) == 0){
        countryCode = strdup("CG");
        return countryCode;
    }else if(strcasecmp("CONGO, THE DEMOCRATIC REPUBLIC OF THE",country) == 0){
        countryCode = strdup("CD");
        return countryCode;
    }else if(strcasecmp("COOK ISLANDS",country) == 0){
        countryCode = strdup("CK");
        return countryCode;
    }else if(strcasecmp("COSTA RICA",country) == 0){
        countryCode = strdup("CR");
        return countryCode;
    }else if(strcasecmp("CÔTE D'IVOIRE",country) == 0){
        countryCode = strdup("CI");
        return countryCode;
    }else if(strcasecmp("CROATIA",country) == 0){
        countryCode = strdup("HR");
        return countryCode;
    }else if(strcasecmp("CUBA",country) == 0){
        countryCode = strdup("CU");
        return countryCode;
    }else if(strcasecmp("CURAÇAO",country) == 0){
        countryCode = strdup("CW");
        return countryCode;
    }else if(strcasecmp("CYPRUS",country) == 0){
        countryCode = strdup("CY");
        return countryCode;
    }else if(strcasecmp("CZECH REPUBLIC",country) == 0){
        countryCode = strdup("CZ");
        return countryCode;
    }else if(strcasecmp("DENMARK",country) == 0){
        countryCode = strdup("DK");
        return countryCode;
    }else if(strcasecmp("DJIBOUTI",country) == 0){
        countryCode = strdup("DJ");
        return countryCode;
    }else if(strcasecmp("DOMINICA",country) == 0){
        countryCode = strdup("DM");
        return countryCode;
    }else if(strcasecmp("DOMINICAN REPUBLIC",country) == 0){
        countryCode = strdup("DO");
        return countryCode;
    }else if(strcasecmp("ECUADOR",country) == 0){
        countryCode = strdup("EC");
        return countryCode;
    }else if(strcasecmp("EGYPT",country) == 0){
        countryCode = strdup("EG");
        return countryCode;
    }else if(strcasecmp("EL SALVADOR",country) == 0){
        countryCode = strdup("SV");
        return countryCode;
    }else if(strcasecmp("EQUATORIAL GUINEA",country) == 0){
        countryCode = strdup("GQ");
        return countryCode;
    }else if(strcasecmp("ERITREA",country) == 0){
        countryCode = strdup("ER");
        return countryCode;
    }else if(strcasecmp("ESTONIA",country) == 0){
        countryCode = strdup("EE");
        return countryCode;
    }else if(strcasecmp("ETHIOPIA",country) == 0){
        countryCode = strdup("ET");
        return countryCode;
    }else if(strcasecmp("FALKLAND ISLANDS (MALVINAS)",country) == 0){
        countryCode = strdup("FK");
        return countryCode;
    }else if(strcasecmp("FAROE ISLANDS",country) == 0){
        countryCode = strdup("FO");
        return countryCode;
    }else if(strcasecmp("FIJI",country) == 0){
        countryCode = strdup("FJ");
        return countryCode;
    }else if(strcasecmp("FINLAND",country) == 0){
        countryCode = strdup("FI");
        return countryCode;
    }else if(strcasecmp("FRANCE",country) == 0){
        countryCode = strdup("FR");
        return countryCode;
    }else if(strcasecmp("FRENCH GUIANA",country) == 0){
        countryCode = strdup("GF");
        return countryCode;
    }else if(strcasecmp("FRENCH POLYNESIA",country) == 0){
        countryCode = strdup("PF");
        return countryCode;
    }else if(strcasecmp("FRENCH SOUTHERN TERRITORIES",country) == 0){
        countryCode = strdup("TF");
        return countryCode;
    }else if(strcasecmp("GABON",country) == 0){
        countryCode = strdup("GA");
        return countryCode;
    }else if(strcasecmp("GAMBIA",country) == 0){
        countryCode = strdup("GM");
        return countryCode;
    }else if(strcasecmp("GEORGIA",country) == 0){
        countryCode = strdup("GE");
        return countryCode;
    }else if(strcasecmp("GERMANY",country) == 0){
        countryCode = strdup("DE");
        return countryCode;
    }else if(strcasecmp("GHANA",country) == 0){
        countryCode = strdup("GH");
        return countryCode;
    }else if(strcasecmp("GIBRALTAR",country) == 0){
        countryCode = strdup("GI");
        return countryCode;
    }else if(strcasecmp("GREECE",country) == 0){
        countryCode = strdup("GR");
        return countryCode;
    }else if(strcasecmp("GREENLAND",country) == 0){
        countryCode = strdup("GL");
        return countryCode;
    }else if(strcasecmp("GRENADA",country) == 0){
        countryCode = strdup("GD");
        return countryCode;
    }else if(strcasecmp("GUADELOUPE",country) == 0){
        countryCode = strdup("GP");
        return countryCode;
    }else if(strcasecmp("GUAM",country) == 0){
        countryCode = strdup("GU");
        return countryCode;
    }else if(strcasecmp("GUATEMALA",country) == 0){
        countryCode = strdup("GT");
        return countryCode;
    }else if(strcasecmp("GUERNSEY",country) == 0){
        countryCode = strdup("GG");
        return countryCode;
    }else if(strcasecmp("GUINEA",country) == 0){
        countryCode = strdup("GN");
        return countryCode;
    }else if(strcasecmp("GUINEA-BISSAU",country) == 0){
        countryCode = strdup("GW");
        return countryCode;
    }else if(strcasecmp("GUYANA",country) == 0){
        countryCode = strdup("GY");
        return countryCode;
    }else if(strcasecmp("HAITI",country) == 0){
        countryCode = strdup("HT");
        return countryCode;
    }else if(strcasecmp("HEARD ISLAND AND MCDONALD ISLANDS",country) == 0){
        countryCode = strdup("HM");
        return countryCode;
    }else if(strcasecmp("HOLY SEE (VATICAN CITY STATE)",country) == 0){
        countryCode = strdup("VA");
        return countryCode;
    }else if(strcasecmp("HONDURAS",country) == 0){
        countryCode = strdup("HN");
        return countryCode;
    }else if(strcasecmp("HONG KONG",country) == 0){
        countryCode = strdup("HK");
        return countryCode;
    }else if(strcasecmp("HUNGARY",country) == 0){
        countryCode = strdup("HU");
        return countryCode;
    }else if(strcasecmp("ICELAND",country) == 0){
        countryCode = strdup("IS");
        return countryCode;
    }else if(strcasecmp("INDIA",country) == 0){
        countryCode = strdup("IN");
        return countryCode;
    }else if(strcasecmp("INDONESIA",country) == 0){
        countryCode = strdup("ID");
        return countryCode;
    }else if(strcasecmp("IRAN, ISLAMIC REPUBLIC OF",country) == 0){
        countryCode = strdup("IR");
        return countryCode;
    }else if(strcasecmp("IRAQ",country) == 0){
        countryCode = strdup("IQ");
        return countryCode;
    }else if(strcasecmp("IRELAND",country) == 0){
        countryCode = strdup("IE");
        return countryCode;
    }else if(strcasecmp("ISLE OF MAN",country) == 0){
        countryCode = strdup("IM");
        return countryCode;
    }else if(strcasecmp("ISRAEL",country) == 0){
        countryCode = strdup("IL");
        return countryCode;
    }else if(strcasecmp("ITALY",country) == 0){
        countryCode = strdup("IT");
        return countryCode;
    }else if(strcasecmp("JAMAICA",country) == 0){
        countryCode = strdup("JM");
        return countryCode;
    }else if(strcasecmp("JAPAN",country) == 0){
        countryCode = strdup("JP");
        return countryCode;
    }else if(strcasecmp("JERSEY",country) == 0){
        countryCode = strdup("JE");
        return countryCode;
    }else if(strcasecmp("JORDAN",country) == 0){
        countryCode = strdup("JO");
        return countryCode;
    }else if(strcasecmp("KAZAKHSTAN",country) == 0){
        countryCode = strdup("KZ");
        return countryCode;
    }else if(strcasecmp("KENYA",country) == 0){
        countryCode = strdup("KE");
        return countryCode;
    }else if(strcasecmp("KIRIBATI",country) == 0){
        countryCode = strdup("KI");
        return countryCode;
    }else if(strcasecmp("KOREA, DEMOCRATIC PEOPLE'S REPUBLIC OF",country) == 0){
        countryCode = strdup("KP");
        return countryCode;
    }else if(strcasecmp("KOREA, REPUBLIC OF",country) == 0){
        countryCode = strdup("KR");
        return countryCode;
    }else if(strcasecmp("KUWAIT",country) == 0){
        countryCode = strdup("KW");
        return countryCode;
    }else if(strcasecmp("KYRGYZSTAN",country) == 0){
        countryCode = strdup("KG");
        return countryCode;
    }else if(strcasecmp("LAO PEOPLE'S DEMOCRATIC REPUBLIC",country) == 0){
        countryCode = strdup("LA");
        return countryCode;
    }else if(strcasecmp("LATVIA",country) == 0){
        countryCode = strdup("LV");
        return countryCode;
    }else if(strcasecmp("LEBANON",country) == 0){
        countryCode = strdup("LB");
        return countryCode;
    }else if(strcasecmp("LESOTHO",country) == 0){
        countryCode = strdup("LS");
        return countryCode;
    }else if(strcasecmp("LIBERIA",country) == 0){
        countryCode = strdup("LR");
        return countryCode;
    }else if(strcasecmp("LIBYA",country) == 0){
        countryCode = strdup("LY");
        return countryCode;
    }else if(strcasecmp("LIECHTENSTEIN",country) == 0){
        countryCode = strdup("LI");
        return countryCode;
    }else if(strcasecmp("LITHUANIA",country) == 0){
        countryCode = strdup("LT");
        return countryCode;
    }else if(strcasecmp("LUXEMBOURG",country) == 0){
        countryCode = strdup("LU");
        return countryCode;
    }else if(strcasecmp("MACAO",country) == 0){
        countryCode = strdup("MO");
        return countryCode;
    }else if(strcasecmp("MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF",country) == 0){
        countryCode = strdup("MK");
        return countryCode;
    }else if(strcasecmp("MADAGASCAR",country) == 0){
        countryCode = strdup("MG");
        return countryCode;
    }else if(strcasecmp("MALAWI",country) == 0){
        countryCode = strdup("MW");
        return countryCode;
    }else if(strcasecmp("MALAYSIA",country) == 0){
        countryCode = strdup("MY");
        return countryCode;
    }else if(strcasecmp("MALDIVES",country) == 0){
        countryCode = strdup("MV");
        return countryCode;
    }else if(strcasecmp("MALI",country) == 0){
        countryCode = strdup("ML");
        return countryCode;
    }else if(strcasecmp("MALTA",country) == 0){
        countryCode = strdup("MT");
        return countryCode;
    }else if(strcasecmp("MARSHALL ISLANDS",country) == 0){
        countryCode = strdup("MH");
        return countryCode;
    }else if(strcasecmp("MARTINIQUE",country) == 0){
        countryCode = strdup("MQ");
        return countryCode;
    }else if(strcasecmp("MAURITANIA",country) == 0){
        countryCode = strdup("MR");
        return countryCode;
    }else if(strcasecmp("MAURITIUS",country) == 0){
        countryCode = strdup("MU");
        return countryCode;
    }else if(strcasecmp("MAYOTTE",country) == 0){
        countryCode = strdup("YT");
        return countryCode;
    }else if(strcasecmp("MEXICO",country) == 0){
        countryCode = strdup("MX");
        return countryCode;
    }else if(strcasecmp("MICRONESIA, FEDERATED STATES OF",country) == 0){
        countryCode = strdup("FM");
        return countryCode;
    }else if(strcasecmp("MOLDOVA, REPUBLIC OF",country) == 0){
        countryCode = strdup("MD");
        return countryCode;
    }else if(strcasecmp("MONACO",country) == 0){
        countryCode = strdup("MC");
        return countryCode;
    }else if(strcasecmp("MONGOLIA",country) == 0){
        countryCode = strdup("MN");
        return countryCode;
    }else if(strcasecmp("MONTENEGRO",country) == 0){
        countryCode = strdup("ME");
        return countryCode;
    }else if(strcasecmp("MONTSERRAT",country) == 0){
        countryCode = strdup("MS");
        return countryCode;
    }else if(strcasecmp("MOROCCO",country) == 0){
        countryCode = strdup("MA");
        return countryCode;
    }else if(strcasecmp("MOZAMBIQUE",country) == 0){
        countryCode = strdup("MZ");
        return countryCode;
    }else if(strcasecmp("MYANMAR",country) == 0){
        countryCode = strdup("MM");
        return countryCode;
    }else if(strcasecmp("NAMIBIA",country) == 0){
        countryCode = strdup("NA");
        return countryCode;
    }else if(strcasecmp("NAURU",country) == 0){
        countryCode = strdup("NR");
        return countryCode;
    }else if(strcasecmp("NEPAL",country) == 0){
        countryCode = strdup("NP");
        return countryCode;
    }else if(strcasecmp("NETHERLANDS",country) == 0){
        countryCode = strdup("NL");
        return countryCode;
    }else if(strcasecmp("NEW CALEDONIA",country) == 0){
        countryCode = strdup("NC");
        return countryCode;
    }else if(strcasecmp("NEW ZEALAND",country) == 0){
        countryCode = strdup("NZ");
        return countryCode;
    }else if(strcasecmp("NICARAGUA",country) == 0){
        countryCode = strdup("NI");
        return countryCode;
    }else if(strcasecmp("NIGER",country) == 0){
        countryCode = strdup("NE");
        return countryCode;
    }else if(strcasecmp("NIGERIA",country) == 0){
        countryCode = strdup("NG");
        return countryCode;
    }else if(strcasecmp("NIUE",country) == 0){
        countryCode = strdup("NU");
        return countryCode;
    }else if(strcasecmp("NORFOLK ISLAND",country) == 0){
        countryCode = strdup("NF");
        return countryCode;
    }else if(strcasecmp("NORTHERN MARIANA ISLANDS",country) == 0){
        countryCode = strdup("MP");
        return countryCode;
    }else if(strcasecmp("NORWAY",country) == 0){
        countryCode = strdup("NO");
        return countryCode;
    }else if(strcasecmp("OMAN",country) == 0){
        countryCode = strdup("OM");
        return countryCode;
    }else if(strcasecmp("PAKISTAN",country) == 0){
        countryCode = strdup("PK");
        return countryCode;
    }else if(strcasecmp("PALAU",country) == 0){
        countryCode = strdup("PW");
        return countryCode;
    }else if(strcasecmp("PALESTINE, STATE OF",country) == 0){
        countryCode = strdup("PS");
        return countryCode;
    }else if(strcasecmp("PANAMA",country) == 0){
        countryCode = strdup("PA");
        return countryCode;
    }else if(strcasecmp("PAPUA NEW GUINEA",country) == 0){
        countryCode = strdup("PG");
        return countryCode;
    }else if(strcasecmp("PARAGUAY",country) == 0){
        countryCode = strdup("PY");
        return countryCode;
    }else if(strcasecmp("PERU",country) == 0){
        countryCode = strdup("PE");
        return countryCode;
    }else if(strcasecmp("PHILIPPINES",country) == 0){
        countryCode = strdup("PH");
        return countryCode;
    }else if(strcasecmp("PITCAIRN",country) == 0){
        countryCode = strdup("PN");
        return countryCode;
    }else if(strcasecmp("POLAND",country) == 0){
        countryCode = strdup("PL");
        return countryCode;
    }else if(strcasecmp("PORTUGAL",country) == 0){
        countryCode = strdup("PT");
        return countryCode;
    }else if(strcasecmp("PUERTO RICO",country) == 0){
        countryCode = strdup("PR");
        return countryCode;
    }else if(strcasecmp("QATAR",country) == 0){
        countryCode = strdup("QA");
        return countryCode;
    }else if(strcasecmp("RÉUNION",country) == 0){
        countryCode = strdup("RE");
        return countryCode;
    }else if(strcasecmp("ROMANIA",country) == 0){
        countryCode = strdup("RO");
        return countryCode;
    }else if(strcasecmp("RUSSIAN FEDERATION",country) == 0){
        countryCode = strdup("RU");
        return countryCode;
    }else if(strcasecmp("RWANDA",country) == 0){
        countryCode = strdup("RW");
        return countryCode;
    }else if(strcasecmp("SAINT BARTHÉLEMY",country) == 0){
        countryCode = strdup("BL");
        return countryCode;
    }else if(strcasecmp("SAINT HELENA, ASCENSION AND TRISTAN DA CUNHA",country) == 0){
        countryCode = strdup("SH");
        return countryCode;
    }else if(strcasecmp("SAINT KITTS AND NEVIS",country) == 0){
        countryCode = strdup("KN");
        return countryCode;
    }else if(strcasecmp("SAINT LUCIA",country) == 0){
        countryCode = strdup("LC");
        return countryCode;
    }else if(strcasecmp("SAINT MARTIN (FRENCH PART)",country) == 0){
        countryCode = strdup("MF");
        return countryCode;
    }else if(strcasecmp("SAINT PIERRE AND MIQUELON",country) == 0){
        countryCode = strdup("PM");
        return countryCode;
    }else if(strcasecmp("SAINT VINCENT AND THE GRENADINES",country) == 0){
        countryCode = strdup("VC");
        return countryCode;
    }else if(strcasecmp("SAMOA",country) == 0){
        countryCode = strdup("WS");
        return countryCode;
    }else if(strcasecmp("SAN MARINO",country) == 0){
        countryCode = strdup("SM");
        return countryCode;
    }else if(strcasecmp("SAO TOME AND PRINCIPE",country) == 0){
        countryCode = strdup("ST");
        return countryCode;
    }else if(strcasecmp("SAUDI ARABIA",country) == 0){
        countryCode = strdup("SA");
        return countryCode;
    }else if(strcasecmp("SENEGAL",country) == 0){
        countryCode = strdup("SN");
        return countryCode;
    }else if(strcasecmp("SERBIA",country) == 0){
        countryCode = strdup("RS");
        return countryCode;
    }else if(strcasecmp("SEYCHELLES",country) == 0){
        countryCode = strdup("SC");
        return countryCode;
    }else if(strcasecmp("SIERRA LEONE",country) == 0){
        countryCode = strdup("SL");
        return countryCode;
    }else if(strcasecmp("SINGAPORE",country) == 0){
        countryCode = strdup("SG");
        return countryCode;
    }else if(strcasecmp("SINT MAARTEN (DUTCH PART)",country) == 0){
        countryCode = strdup("SX");
        return countryCode;
    }else if(strcasecmp("SLOVAKIA",country) == 0){
        countryCode = strdup("SK");
        return countryCode;
    }else if(strcasecmp("SLOVENIA",country) == 0){
        countryCode = strdup("SI");
        return countryCode;
    }else if(strcasecmp("SOLOMON ISLANDS",country) == 0){
        countryCode = strdup("SB");
        return countryCode;
    }else if(strcasecmp("SOMALIA",country) == 0){
        countryCode = strdup("SO");
        return countryCode;
    }else if(strcasecmp("SOUTH AFRICA",country) == 0){
        countryCode = strdup("ZA");
        return countryCode;
    }else if(strcasecmp("SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS",country) == 0){
        countryCode = strdup("GS");
        return countryCode;
    }else if(strcasecmp("SOUTH SUDAN",country) == 0){
        countryCode = strdup("SS");
        return countryCode;
    }else if(strcasecmp("SPAIN",country) == 0){
        countryCode = strdup("ES");
        return countryCode;
    }else if(strcasecmp("SRI LANKA",country) == 0){
        countryCode = strdup("LK");
        return countryCode;
    }else if(strcasecmp("SUDAN",country) == 0){
        countryCode = strdup("SD");
        return countryCode;
    }else if(strcasecmp("SURINAME",country) == 0){
        countryCode = strdup("SR");
        return countryCode;
    }else if(strcasecmp("SVALBARD AND JAN MAYEN",country) == 0){
        countryCode = strdup("SJ");
        return countryCode;
    }else if(strcasecmp("SWAZILAND",country) == 0){
        countryCode = strdup("SZ");
        return countryCode;
    }else if(strcasecmp("SWEDEN",country) == 0){
        countryCode = strdup("SE");
        return countryCode;
    }else if(strcasecmp("SWITZERLAND",country) == 0){
        countryCode = strdup("CH");
        return countryCode;
    }else if(strcasecmp("SYRIAN ARAB REPUBLIC",country) == 0){
        countryCode = strdup("SY");
        return countryCode;
    }else if(strcasecmp("TAIWAN, PROVINCE OF CHINA",country) == 0){
        countryCode = strdup("TW");
        return countryCode;
    }else if(strcasecmp("TAJIKISTAN",country) == 0){
        countryCode = strdup("TJ");
        return countryCode;
    }else if(strcasecmp("TANZANIA, UNITED REPUBLIC OF",country) == 0){
        countryCode = strdup("TZ");
        return countryCode;
    }else if(strcasecmp("THAILAND",country) == 0){
        countryCode = strdup("TH");
        return countryCode;
    }else if(strcasecmp("TIMOR-LESTE",country) == 0){
        countryCode = strdup("TL");
        return countryCode;
    }else if(strcasecmp("TOGO",country) == 0){
        countryCode = strdup("TG");
        return countryCode;
    }else if(strcasecmp("TOKELAU",country) == 0){
        countryCode = strdup("TK");
        return countryCode;
    }else if(strcasecmp("TONGA",country) == 0){
        countryCode = strdup("TO");
        return countryCode;
    }else if(strcasecmp("TRINIDAD AND TOBAGO",country) == 0){
        countryCode = strdup("TT");
        return countryCode;
    }else if(strcasecmp("TUNISIA",country) == 0){
        countryCode = strdup("TN");
        return countryCode;
    }else if(strcasecmp("TURKEY",country) == 0){
        countryCode = strdup("TR");
        return countryCode;
    }else if(strcasecmp("TURKMENISTAN",country) == 0){
        countryCode = strdup("TM");
        return countryCode;
    }else if(strcasecmp("TURKS AND CAICOS ISLANDS",country) == 0){
        countryCode = strdup("TC");
        return countryCode;
    }else if(strcasecmp("TUVALU",country) == 0){
        countryCode = strdup("TV");
        return countryCode;
    }else if(strcasecmp("UGANDA",country) == 0){
        countryCode = strdup("UG");
        return countryCode;
    }else if(strcasecmp("UKRAINE",country) == 0){
        countryCode = strdup("UA");
        return countryCode;
    }else if(strcasecmp("UNITED ARAB EMIRATES",country) == 0){
        countryCode = strdup("AE");
        return countryCode;
    }else if(strcasecmp("UNITED KINGDOM",country) == 0){
        countryCode = strdup("GB");
        return countryCode;
    }else if(strcasecmp("UNITED STATES",country) == 0 || strcasecmp("UNITED STATES OF AMERICA",country) == 0){
        countryCode = strdup("US");
        return countryCode;
    }else if(strcasecmp("UNITED STATES MINOR OUTLYING ISLANDS",country) == 0){
        countryCode = strdup("UM");
        return countryCode;
    }else if(strcasecmp("URUGUAY",country) == 0){
        countryCode = strdup("UY");
        return countryCode;
    }else if(strcasecmp("UZBEKISTAN",country) == 0){
        countryCode = strdup("UZ");
        return countryCode;
    }else if(strcasecmp("VANUATU",country) == 0){
        countryCode = strdup("VU");
        return countryCode;
    }else if(strcasecmp("VENEZUELA, BOLIVARIAN REPUBLIC OF",country) == 0){
        countryCode = strdup("VE");
        return countryCode;
    }else if(strcasecmp("VIET NAM",country) == 0){
        countryCode = strdup("VN");
        return countryCode;
    }else if(strcasecmp("VIRGIN ISLANDS, BRITISH",country) == 0){
        countryCode = strdup("VG");
        return countryCode;
    }else if(strcasecmp("VIRGIN ISLANDS, U.S.",country) == 0){
        countryCode = strdup("VI");
        return countryCode;
    }else if(strcasecmp("WALLIS AND FUTUNA",country) == 0){
        countryCode = strdup("WF");
        return countryCode;
    }else if(strcasecmp("WESTERN SAHARA",country) == 0){
        countryCode = strdup("EH");
        return countryCode;
    }else if(strcasecmp("YEMEN",country) == 0){
        countryCode = strdup("YE");
        return countryCode;
    }else if(strcasecmp("ZAMBIA",country) == 0){
        countryCode = strdup("ZM");
        return countryCode;
    }else if(strcasecmp("ZIMBABWE",country) == 0){
        countryCode = strdup("ZW");
        return countryCode;
    }else{
        countryCode = strdup("");
        return countryCode;
    }
}

char *codeToCountry(char *code){
    char *country = NULL;
    
    if(strcasecmp("AF",code) == 0){
        country = strdup("AFGHANISTAN");
        return country;
    }else if(strcasecmp("AX",code) == 0){
        country = strdup("ÅLAND ISLANDS");
        return country;
    }else if(strcasecmp("AL",code) == 0){
        country = strdup("ALBANIA");
        return country;
    }else if(strcasecmp("DZ",code) == 0){
        country = strdup("ALGERIA");
        return country;
    }else if(strcasecmp("AS",code) == 0){
        country = strdup("AMERICAN SAMOA");
        return country;
    }else if(strcasecmp("AD",code) == 0){
        country = strdup("ANDORRA");
        return country;
    }else if(strcasecmp("AO",code) == 0){
        country = strdup("ANGOLA");
        return country;
    }else if(strcasecmp("AI",code) == 0){
        country = strdup("ANGUILLA");
        return country;
    }else if(strcasecmp("AQ",code) == 0){
        country = strdup("ANTARCTICA");
        return country;
    }else if(strcasecmp("AG",code) == 0){
        country = strdup("ANTIGUA AND BARBUDA");
        return country;
    }else if(strcasecmp("AR",code) == 0){
        country = strdup("ARGENTINA");
        return country;
    }else if(strcasecmp("AM",code) == 0){
        country = strdup("ARMENIA");
        return country;
    }else if(strcasecmp("AW",code) == 0){
        country = strdup("ARUBA");
        return country;
    }else if(strcasecmp("AU",code) == 0){
        country = strdup("AUSTRALIA");
        return country;
    }else if(strcasecmp("AT",code) == 0){
        country = strdup("AUSTRIA");
        return country;
    }else if(strcasecmp("AZ",code) == 0){
        country = strdup("AZERBAIJAN");
        return country;
    }else if(strcasecmp("BS",code) == 0){
        country = strdup("BAHAMAS");
        return country;
    }else if(strcasecmp("BH",code) == 0){
        country = strdup("BAHRAIN");
        return country;
    }else if(strcasecmp("BD",code) == 0){
        country = strdup("BANGLADESH");
        return country;
    }else if(strcasecmp("BB",code) == 0){
        country = strdup("BARBADOS");
        return country;
    }else if(strcasecmp("BY",code) == 0){
        country = strdup("BELARUS");
        return country;
    }else if(strcasecmp("BE",code) == 0){
        country = strdup("BELGIUM");
        return country;
    }else if(strcasecmp("BZ",code) == 0){
        country = strdup("BELIZE");
        return country;
    }else if(strcasecmp("BJ",code) == 0){
        country = strdup("BENIN");
        return country;
    }else if(strcasecmp("BM",code) == 0){
        country = strdup("BERMUDA");
        return country;
    }else if(strcasecmp("BT",code) == 0){
        country = strdup("BHUTAN");
        return country;
    }else if(strcasecmp("BO",code) == 0){
        country = strdup("BOLIVIA, PLURINATIONAL STATE OF");
        return country;
    }else if(strcasecmp("BQ",code) == 0){
        country = strdup("BONAIRE, SINT EUSTATIUS AND SABA");
        return country;
    }else if(strcasecmp("BA",code) == 0){
        country = strdup("BOSNIA AND HERZEGOVINA");
        return country;
    }else if(strcasecmp("BW",code) == 0){
        country = strdup("BOTSWANA");
        return country;
    }else if(strcasecmp("BV",code) == 0){
        country = strdup("BOUVET ISLAND");
        return country;
    }else if(strcasecmp("BR",code) == 0){
        country = strdup("BRAZIL");
        return country;
    }else if(strcasecmp("IO",code) == 0){
        country = strdup("BRITISH INDIAN OCEAN TERRITORY");
        return country;
    }else if(strcasecmp("BN",code) == 0){
        country = strdup("BRUNEI DARUSSALAM");
        return country;
    }else if(strcasecmp("BG",code) == 0){
        country = strdup("BULGARIA");
        return country;
    }else if(strcasecmp("BF",code) == 0){
        country = strdup("BURKINA FASO");
        return country;
    }else if(strcasecmp("BI",code) == 0){
        country = strdup("BURUNDI");
        return country;
    }else if(strcasecmp("KH",code) == 0){
        country = strdup("CAMBODIA");
        return country;
    }else if(strcasecmp("CM",code) == 0){
        country = strdup("CAMEROON");
        return country;
    }else if(strcasecmp("CA",code) == 0){
        country = strdup("CANADA");
        return country;
    }else if(strcasecmp("CV",code) == 0){
        country = strdup("CAPE VERDE");
        return country;
    }else if(strcasecmp("KY",code) == 0){
        country = strdup("CAYMAN ISLANDS");
        return country;
    }else if(strcasecmp("CF",code) == 0){
        country = strdup("CENTRAL AFRICAN REPUBLIC");
        return country;
    }else if(strcasecmp("TD",code) == 0){
        country = strdup("CHAD");
        return country;
    }else if(strcasecmp("CL",code) == 0){
        country = strdup("CHILE");
        return country;
    }else if(strcasecmp("CN",code) == 0){
        country = strdup("CHINA");
        return country;
    }else if(strcasecmp("CX",code) == 0){
        country = strdup("CHRISTMAS ISLAND");
        return country;
    }else if(strcasecmp("CC",code) == 0){
        country = strdup("COCOS (KEELING) ISLANDS");
        return country;
    }else if(strcasecmp("CO",code) == 0){
        country = strdup("COLOMBIA");
        return country;
    }else if(strcasecmp("KM",code) == 0){
        country = strdup("COMOROS");
        return country;
    }else if(strcasecmp("CG",code) == 0){
        country = strdup("CONGO");
        return country;
    }else if(strcasecmp("CD",code) == 0){
        country = strdup("CONGO, THE DEMOCRATIC REPUBLIC OF THE");
        return country;
    }else if(strcasecmp("CK",code) == 0){
        country = strdup("COOK ISLANDS");
        return country;
    }else if(strcasecmp("CR",code) == 0){
        country = strdup("COSTA RICA");
        return country;
    }else if(strcasecmp("CI",code) == 0){
        country = strdup("CÔTE D'IVOIRE");
        return country;
    }else if(strcasecmp("HR",code) == 0){
        country = strdup("CROATIA");
        return country;
    }else if(strcasecmp("CU",code) == 0){
        country = strdup("CUBA");
        return country;
    }else if(strcasecmp("CW",code) == 0){
        country = strdup("CURAÇAO");
        return country;
    }else if(strcasecmp("CY",code) == 0){
        country = strdup("CYPRUS");
        return country;
    }else if(strcasecmp("CZ",code) == 0){
        country = strdup("CZECH REPUBLIC");
        return country;
    }else if(strcasecmp("DK",code) == 0){
        country = strdup("DENMARK");
        return country;
    }else if(strcasecmp("DJ",code) == 0){
        country = strdup("DJIBOUTI");
        return country;
    }else if(strcasecmp("DM",code) == 0){
        country = strdup("DOMINICA");
        return country;
    }else if(strcasecmp("DO",code) == 0){
        country = strdup("DOMINICAN REPUBLIC");
        return country;
    }else if(strcasecmp("EC",code) == 0){
        country = strdup("ECUADOR");
        return country;
    }else if(strcasecmp("EG",code) == 0){
        country = strdup("EGYPT");
        return country;
    }else if(strcasecmp("SV",code) == 0){
        country = strdup("EL SALVADOR");
        return country;
    }else if(strcasecmp("GQ",code) == 0){
        country = strdup("EQUATORIAL GUINEA");
        return country;
    }else if(strcasecmp("ER",code) == 0){
        country = strdup("ERITREA");
        return country;
    }else if(strcasecmp("EE",code) == 0){
        country = strdup("ESTONIA");
        return country;
    }else if(strcasecmp("ET",code) == 0){
        country = strdup("ETHIOPIA");
        return country;
    }else if(strcasecmp("FK",code) == 0){
        country = strdup("FALKLAND ISLANDS (MALVINAS)");
        return country;
    }else if(strcasecmp("FO",code) == 0){
        country = strdup("FAROE ISLANDS");
        return country;
    }else if(strcasecmp("FJ",code) == 0){
        country = strdup("FIJI");
        return country;
    }else if(strcasecmp("FI",code) == 0){
        country = strdup("FINLAND");
        return country;
    }else if(strcasecmp("FR",code) == 0){
        country = strdup("FRANCE");
        return country;
    }else if(strcasecmp("GF",code) == 0){
        country = strdup("FRENCH GUIANA");
        return country;
    }else if(strcasecmp("PF",code) == 0){
        country = strdup("FRENCH POLYNESIA");
        return country;
    }else if(strcasecmp("TF",code) == 0){
        country = strdup("FRENCH SOUTHERN TERRITORIES");
        return country;
    }else if(strcasecmp("GA",code) == 0){
        country = strdup("GABON");
        return country;
    }else if(strcasecmp("GM",code) == 0){
        country = strdup("GAMBIA");
        return country;
    }else if(strcasecmp("GE",code) == 0){
        country = strdup("GEORGIA");
        return country;
    }else if(strcasecmp("DE",code) == 0){
        country = strdup("GERMANY");
        return country;
    }else if(strcasecmp("GH",code) == 0){
        country = strdup("GHANA");
        return country;
    }else if(strcasecmp("GI",code) == 0){
        country = strdup("GIBRALTAR");
        return country;
    }else if(strcasecmp("GR",code) == 0){
        country = strdup("GREECE");
        return country;
    }else if(strcasecmp("GL",code) == 0){
        country = strdup("GREENLAND");
        return country;
    }else if(strcasecmp("GD",code) == 0){
        country = strdup("GRENADA");
        return country;
    }else if(strcasecmp("GP",code) == 0){
        country = strdup("GUADELOUPE");
        return country;
    }else if(strcasecmp("GU",code) == 0){
        country = strdup("GUAM");
        return country;
    }else if(strcasecmp("GT",code) == 0){
        country = strdup("GUATEMALA");
        return country;
    }else if(strcasecmp("GG",code) == 0){
        country = strdup("GUERNSEY");
        return country;
    }else if(strcasecmp("GN",code) == 0){
        country = strdup("GUINEA");
        return country;
    }else if(strcasecmp("GW",code) == 0){
        country = strdup("GUINEA-BISSAU");
        return country;
    }else if(strcasecmp("GY",code) == 0){
        country = strdup("GUYANA");
        return country;
    }else if(strcasecmp("HT",code) == 0){
        country = strdup("HAITI");
        return country;
    }else if(strcasecmp("HM",code) == 0){
        country = strdup("HEARD ISLAND AND MCDONALD ISLANDS");
        return country;
    }else if(strcasecmp("VA",code) == 0){
        country = strdup("HOLY SEE (VATICAN CITY STATE)");
        return country;
    }else if(strcasecmp("HN",code) == 0){
        country = strdup("HONDURAS");
        return country;
    }else if(strcasecmp("HK",code) == 0){
        country = strdup("HONG KONG");
        return country;
    }else if(strcasecmp("HU",code) == 0){
        country = strdup("HUNGARY");
        return country;
    }else if(strcasecmp("IS",code) == 0){
        country = strdup("ICELAND");
        return country;
    }else if(strcasecmp("IN",code) == 0){
        country = strdup("INDIA");
        return country;
    }else if(strcasecmp("ID",code) == 0){
        country = strdup("INDONESIA");
        return country;
    }else if(strcasecmp("IR",code) == 0){
        country = strdup("IRAN, ISLAMIC REPUBLIC OF");
        return country;
    }else if(strcasecmp("IQ",code) == 0){
        country = strdup("IRAQ");
        return country;
    }else if(strcasecmp("IE",code) == 0){
        country = strdup("IRELAND");
        return country;
    }else if(strcasecmp("IM",code) == 0){
        country = strdup("ISLE OF MAN");
        return country;
    }else if(strcasecmp("IL",code) == 0){
        country = strdup("ISRAEL");
        return country;
    }else if(strcasecmp("IT",code) == 0){
        country = strdup("ITALY");
        return country;
    }else if(strcasecmp("JM",code) == 0){
        country = strdup("JAMAICA");
        return country;
    }else if(strcasecmp("JP",code) == 0){
        country = strdup("JAPAN");
        return country;
    }else if(strcasecmp("JE",code) == 0){
        country = strdup("JERSEY");
        return country;
    }else if(strcasecmp("JO",code) == 0){
        country = strdup("JORDAN");
        return country;
    }else if(strcasecmp("KZ",code) == 0){
        country = strdup("KAZAKHSTAN");
        return country;
    }else if(strcasecmp("KE",code) == 0){
        country = strdup("KENYA");
        return country;
    }else if(strcasecmp("KI",code) == 0){
        country = strdup("KIRIBATI");
        return country;
    }else if(strcasecmp("KP",code) == 0){
        country = strdup("KOREA, DEMOCRATIC PEOPLE'S REPUBLIC OF");
        return country;
    }else if(strcasecmp("KR",code) == 0){
        country = strdup("KOREA, REPUBLIC OF");
        return country;
    }else if(strcasecmp("KW",code) == 0){
        country = strdup("KUWAIT");
        return country;
    }else if(strcasecmp("KG",code) == 0){
        country = strdup("KYRGYZSTAN");
        return country;
    }else if(strcasecmp("LA",code) == 0){
        country = strdup("LAO PEOPLE'S DEMOCRATIC REPUBLIC");
        return country;
    }else if(strcasecmp("LV",code) == 0){
        country = strdup("LATVIA");
        return country;
    }else if(strcasecmp("LB",code) == 0){
        country = strdup("LEBANON");
        return country;
    }else if(strcasecmp("LS",code) == 0){
        country = strdup("LESOTHO");
        return country;
    }else if(strcasecmp("LR",code) == 0){
        country = strdup("LIBERIA");
        return country;
    }else if(strcasecmp("LY",code) == 0){
        country = strdup("LIBYA");
        return country;
    }else if(strcasecmp("LI",code) == 0){
        country = strdup("LIECHTENSTEIN");
        return country;
    }else if(strcasecmp("LT",code) == 0){
        country = strdup("LITHUANIA");
        return country;
    }else if(strcasecmp("LU",code) == 0){
        country = strdup("LUXEMBOURG");
        return country;
    }else if(strcasecmp("MO",code) == 0){
        country = strdup("MACAO");
        return country;
    }else if(strcasecmp("MK",code) == 0){
        country = strdup("MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF");
        return country;
    }else if(strcasecmp("MG",code) == 0){
        country = strdup("MADAGASCAR");
        return country;
    }else if(strcasecmp("MW",code) == 0){
        country = strdup("MALAWI");
        return country;
    }else if(strcasecmp("MY",code) == 0){
        country = strdup("MALAYSIA");
        return country;
    }else if(strcasecmp("MV",code) == 0){
        country = strdup("MALDIVES");
        return country;
    }else if(strcasecmp("ML",code) == 0){
        country = strdup("MALI");
        return country;
    }else if(strcasecmp("MT",code) == 0){
        country = strdup("MALTA");
        return country;
    }else if(strcasecmp("MH",code) == 0){
        country = strdup("MARSHALL ISLANDS");
        return country;
    }else if(strcasecmp("MQ",code) == 0){
        country = strdup("MARTINIQUE");
        return country;
    }else if(strcasecmp("MR",code) == 0){
        country = strdup("MAURITANIA");
        return country;
    }else if(strcasecmp("MU",code) == 0){
        country = strdup("MAURITIUS");
        return country;
    }else if(strcasecmp("YT",code) == 0){
        country = strdup("MAYOTTE");
        return country;
    }else if(strcasecmp("MX",code) == 0){
        country = strdup("MEXICO");
        return country;
    }else if(strcasecmp("FM",code) == 0){
        country = strdup("MICRONESIA, FEDERATED STATES OF");
        return country;
    }else if(strcasecmp("MD",code) == 0){
        country = strdup("MOLDOVA, REPUBLIC OF");
        return country;
    }else if(strcasecmp("MC",code) == 0){
        country = strdup("MONACO");
        return country;
    }else if(strcasecmp("MN",code) == 0){
        country = strdup("MONGOLIA");
        return country;
    }else if(strcasecmp("ME",code) == 0){
        country = strdup("MONTENEGRO");
        return country;
    }else if(strcasecmp("MS",code) == 0){
        country = strdup("MONTSERRAT");
        return country;
    }else if(strcasecmp("MA",code) == 0){
        country = strdup("MOROCCO");
        return country;
    }else if(strcasecmp("MZ",code) == 0){
        country = strdup("MOZAMBIQUE");
        return country;
    }else if(strcasecmp("MM",code) == 0){
        country = strdup("MYANMAR");
        return country;
    }else if(strcasecmp("NA",code) == 0){
        country = strdup("NAMIBIA");
        return country;
    }else if(strcasecmp("NR",code) == 0){
        country = strdup("NAURU");
        return country;
    }else if(strcasecmp("NP",code) == 0){
        country = strdup("NEPAL");
        return country;
    }else if(strcasecmp("NL",code) == 0){
        country = strdup("NETHERLANDS");
        return country;
    }else if(strcasecmp("NC",code) == 0){
        country = strdup("NEW CALEDONIA");
        return country;
    }else if(strcasecmp("NZ",code) == 0){
        country = strdup("NEW ZEALAND");
        return country;
    }else if(strcasecmp("NI",code) == 0){
        country = strdup("NICARAGUA");
        return country;
    }else if(strcasecmp("NE",code) == 0){
        country = strdup("NIGER");
        return country;
    }else if(strcasecmp("NG",code) == 0){
        country = strdup("NIGERIA");
        return country;
    }else if(strcasecmp("NU",code) == 0){
        country = strdup("NIUE");
        return country;
    }else if(strcasecmp("NF",code) == 0){
        country = strdup("NORFOLK ISLAND");
        return country;
    }else if(strcasecmp("MP",code) == 0){
        country = strdup("NORTHERN MARIANA ISLANDS");
        return country;
    }else if(strcasecmp("NO",code) == 0){
        country = strdup("NORWAY");
        return country;
    }else if(strcasecmp("OM",code) == 0){
        country = strdup("OMAN");
        return country;
    }else if(strcasecmp("PK",code) == 0){
        country = strdup("PAKISTAN");
        return country;
    }else if(strcasecmp("PW",code) == 0){
        country = strdup("PALAU");
        return country;
    }else if(strcasecmp("PS",code) == 0){
        country = strdup("PALESTINE, STATE OF");
        return country;
    }else if(strcasecmp("PA",code) == 0){
        country = strdup("PANAMA");
        return country;
    }else if(strcasecmp("PG",code) == 0){
        country = strdup("PAPUA NEW GUINEA");
        return country;
    }else if(strcasecmp("PY",code) == 0){
        country = strdup("PARAGUAY");
        return country;
    }else if(strcasecmp("PE",code) == 0){
        country = strdup("PERU");
        return country;
    }else if(strcasecmp("PH",code) == 0){
        country = strdup("PHILIPPINES");
        return country;
    }else if(strcasecmp("PN",code) == 0){
        country = strdup("PITCAIRN");
        return country;
    }else if(strcasecmp("PL",code) == 0){
        country = strdup("POLAND");
        return country;
    }else if(strcasecmp("PT",code) == 0){
        country = strdup("PORTUGAL");
        return country;
    }else if(strcasecmp("PR",code) == 0){
        country = strdup("PUERTO RICO");
        return country;
    }else if(strcasecmp("QA",code) == 0){
        country = strdup("QATAR");
        return country;
    }else if(strcasecmp("RE",code) == 0){
        country = strdup("RÉUNION");
        return country;
    }else if(strcasecmp("RO",code) == 0){
        country = strdup("ROMANIA");
        return country;
    }else if(strcasecmp("RU",code) == 0){
        country = strdup("RUSSIAN FEDERATION");
        return country;
    }else if(strcasecmp("RW",code) == 0){
        country = strdup("RWANDA");
        return country;
    }else if(strcasecmp("BL",code) == 0){
        country = strdup("SAINT BARTHÉLEMY");
        return country;
    }else if(strcasecmp("SH",code) == 0){
        country = strdup("SAINT HELENA, ASCENSION AND TRISTAN DA CUNHA");
        return country;
    }else if(strcasecmp("KN",code) == 0){
        country = strdup("SAINT KITTS AND NEVIS");
        return country;
    }else if(strcasecmp("LC",code) == 0){
        country = strdup("SAINT LUCIA");
        return country;
    }else if(strcasecmp("MF",code) == 0){
        country = strdup("SAINT MARTIN (FRENCH PART)");
        return country;
    }else if(strcasecmp("PM",code) == 0){
        country = strdup("SAINT PIERRE AND MIQUELON");
        return country;
    }else if(strcasecmp("VC",code) == 0){
        country = strdup("SAINT VINCENT AND THE GRENADINES");
        return country;
    }else if(strcasecmp("WS",code) == 0){
        country = strdup("SAMOA");
        return country;
    }else if(strcasecmp("SM",code) == 0){
        country = strdup("SAN MARINO");
        return country;
    }else if(strcasecmp("ST",code) == 0){
        country = strdup("SAO TOME AND PRINCIPE");
        return country;
    }else if(strcasecmp("SA",code) == 0){
        country = strdup("SAUDI ARABIA");
        return country;
    }else if(strcasecmp("SN",code) == 0){
        country = strdup("SENEGAL");
        return country;
    }else if(strcasecmp("RS",code) == 0){
        country = strdup("SERBIA");
        return country;
    }else if(strcasecmp("SC",code) == 0){
        country = strdup("SEYCHELLES");
        return country;
    }else if(strcasecmp("SL",code) == 0){
        country = strdup("SIERRA LEONE");
        return country;
    }else if(strcasecmp("SG",code) == 0){
        country = strdup("SINGAPORE");
        return country;
    }else if(strcasecmp("SX",code) == 0){
        country = strdup("SINT MAARTEN (DUTCH PART)");
        return country;
    }else if(strcasecmp("SK",code) == 0){
        country = strdup("SLOVAKIA");
        return country;
    }else if(strcasecmp("SI",code) == 0){
        country = strdup("SLOVENIA");
        return country;
    }else if(strcasecmp("SB",code) == 0){
        country = strdup("SOLOMON ISLANDS");
        return country;
    }else if(strcasecmp("SO",code) == 0){
        country = strdup("SOMALIA");
        return country;
    }else if(strcasecmp("ZA",code) == 0){
        country = strdup("SOUTH AFRICA");
        return country;
    }else if(strcasecmp("GS",code) == 0){
        country = strdup("SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS");
        return country;
    }else if(strcasecmp("SS",code) == 0){
        country = strdup("SOUTH SUDAN");
        return country;
    }else if(strcasecmp("ES",code) == 0){
        country = strdup("SPAIN");
        return country;
    }else if(strcasecmp("LK",code) == 0){
        country = strdup("SRI LANKA");
        return country;
    }else if(strcasecmp("SD",code) == 0){
        country = strdup("SUDAN");
        return country;
    }else if(strcasecmp("SR",code) == 0){
        country = strdup("SURINAME");
        return country;
    }else if(strcasecmp("SJ",code) == 0){
        country = strdup("SVALBARD AND JAN MAYEN");
        return country;
    }else if(strcasecmp("SZ",code) == 0){
        country = strdup("SWAZILAND");
        return country;
    }else if(strcasecmp("SE",code) == 0){
        country = strdup("SWEDEN");
        return country;
    }else if(strcasecmp("CH",code) == 0){
        country = strdup("SWITZERLAND");
        return country;
    }else if(strcasecmp("SY",code) == 0){
        country = strdup("SYRIAN ARAB REPUBLIC");
        return country;
    }else if(strcasecmp("TW",code) == 0){
        country = strdup("TAIWAN, PROVINCE OF CHINA");
        return country;
    }else if(strcasecmp("TJ",code) == 0){
        country = strdup("TAJIKISTAN");
        return country;
    }else if(strcasecmp("TZ",code) == 0){
        country = strdup("TANZANIA, UNITED REPUBLIC OF");
        return country;
    }else if(strcasecmp("TH",code) == 0){
        country = strdup("THAILAND");
        return country;
    }else if(strcasecmp("TL",code) == 0){
        country = strdup("TIMOR-LESTE");
        return country;
    }else if(strcasecmp("TG",code) == 0){
        country = strdup("TOGO");
        return country;
    }else if(strcasecmp("TK",code) == 0){
        country = strdup("TOKELAU");
        return country;
    }else if(strcasecmp("TO",code) == 0){
        country = strdup("TONGA");
        return country;
    }else if(strcasecmp("TT",code) == 0){
        country = strdup("TRINIDAD AND TOBAGO");
        return country;
    }else if(strcasecmp("TN",code) == 0){
        country = strdup("TUNISIA");
        return country;
    }else if(strcasecmp("TR",code) == 0){
        country = strdup("TURKEY");
        return country;
    }else if(strcasecmp("TM",code) == 0){
        country = strdup("TURKMENISTAN");
        return country;
    }else if(strcasecmp("TC",code) == 0){
        country = strdup("TURKS AND CAICOS ISLANDS");
        return country;
    }else if(strcasecmp("TV",code) == 0){
        country = strdup("TUVALU");
        return country;
    }else if(strcasecmp("UG",code) == 0){
        country = strdup("UGANDA");
        return country;
    }else if(strcasecmp("UA",code) == 0){
        country = strdup("UKRAINE");
        return country;
    }else if(strcasecmp("AE",code) == 0){
        country = strdup("UNITED ARAB EMIRATES");
        return country;
    }else if(strcasecmp("GB",code) == 0){
        country = strdup("UNITED KINGDOM");
        return country;
    }else if(strcasecmp("US",code) == 0){
        country = strdup("UNITED STATES");
        return country;
    }else if(strcasecmp("UM",code) == 0){
        country = strdup("UNITED STATES MINOR OUTLYING ISLANDS");
        return country;
    }else if(strcasecmp("UY",code) == 0){
        country = strdup("URUGUAY");
        return country;
    }else if(strcasecmp("UZ",code) == 0){
        country = strdup("UZBEKISTAN");
        return country;
    }else if(strcasecmp("VU",code) == 0){
        country = strdup("VANUATU");
        return country;
    }else if(strcasecmp("VE",code) == 0){
        country = strdup("VENEZUELA, BOLIVARIAN REPUBLIC OF");
        return country;
    }else if(strcasecmp("VN",code) == 0){
        country = strdup("VIET NAM");
        return country;
    }else if(strcasecmp("VG",code) == 0){
        country = strdup("VIRGIN ISLANDS, BRITISH");
        return country;
    }else if(strcasecmp("VI",code) == 0){
        country = strdup("VIRGIN ISLANDS, U.S.");
        return country;
    }else if(strcasecmp("WF",code) == 0){
        country = strdup("WALLIS AND FUTUNA");
        return country;
    }else if(strcasecmp("EH",code) == 0){
        country = strdup("WESTERN SAHARA");
        return country;
    }else if(strcasecmp("YE",code) == 0){
        country = strdup("YEMEN");
        return country;
    }else if(strcasecmp("ZM",code) == 0){
        country = strdup("ZAMBIA");
        return country;
    }else if(strcasecmp("ZW",code) == 0){
        country = strdup("ZIMBABWE");
        return country;
    }else{
        country = strdup("");
        return country;
    }
}


char *provinceToCode(char *province){
    char *provinceCode = NULL;
    
    if(strcasecmp("Alberta",province) == 0){
        provinceCode = strdup("AB");
        return provinceCode;
    }else if(strcasecmp("British Columbia",province) == 0){
        provinceCode = strdup("BC");
        return provinceCode;
    }else if(strcasecmp("Manitoba",province) == 0){
        provinceCode = strdup("MB");
        return provinceCode;
    }else if(strcasecmp("New Brunswick",province) == 0){
        provinceCode = strdup("NB");
        return provinceCode;
    }else if(strcasecmp("Newfoundland and Labrador",province) == 0){
        provinceCode = strdup("NL");
        return provinceCode;
    }else if(strcasecmp("Nova Scotia",province) == 0){
        provinceCode = strdup("NS");
        return provinceCode;
    }else if(strcasecmp("Ontario",province) == 0){
        provinceCode = strdup("ON");
        return provinceCode;
    }else if(strcasecmp("Prince Edward Island",province) == 0){
        provinceCode = strdup("PE");
        return provinceCode;
    }else if(strcasecmp("Quebec",province) == 0){
        provinceCode = strdup("QC");
        return provinceCode;
    }else if(strcasecmp("Saskatchewan",province) == 0){
        provinceCode = strdup("SK");
        return provinceCode;
    }else if(strcasecmp("Northwest Territories",province) == 0){
        provinceCode = strdup("NT");
        return provinceCode;
    }else if(strcasecmp("Nunavut",province) == 0){
        provinceCode = strdup("NU");
        return provinceCode;
    }else if(strcasecmp("Yukon Territory",province) == 0){
        provinceCode = strdup("YT");
        return provinceCode;
    }else{
        provinceCode = strdup("");
        return provinceCode;
    }
}

char *codeToProvince(char *code){
    char *province = NULL;
    
    if(strcasecmp("AB",code) == 0){
        province = strdup("Alberta");
        return province;
    }else if(strcasecmp("BC",code) == 0){
        province = strdup("British Columbia");
        return province;
    }else if(strcasecmp("MB",code) == 0){
        province = strdup("Manitoba");
        return province;
    }else if(strcasecmp("NB",code) == 0){
        province = strdup("New Brunswick");
        return province;
    }else if(strcasecmp("NL",code) == 0){
        province = strdup("Newfoundland and Labrador");
        return province;
    }else if(strcasecmp("NS",code) == 0){
        province = strdup("Nova Scotia");
        return province;
    }else if(strcasecmp("ON",code) == 0){
        province = strdup("Ontario");
        return province;
    }else if(strcasecmp("PE",code) == 0){
        province = strdup("Prince Edward Island	");
        return province;
    }else if(strcasecmp("QC",code) == 0){
        province = strdup("Quebec");
        return province;
    }else if(strcasecmp("SK",code) == 0){
        province = strdup("Saskatchewan");
        return province;
    }else if(strcasecmp("NT",code) == 0){
        province = strdup("Northwest Territories");
        return province;
    }else if(strcasecmp("NU",code) == 0){
        province = strdup("Nunavut");
        return province;
    }else if(strcasecmp("YT",code) == 0){
        province = strdup("Yukon Territory");
        return province;
    }else{
        province = strdup("");
        return province;
    }
}

char *stateToCode(char *state){
    char *stateCode = NULL;
    
    if(strcasecmp("Alaska",state) == 0){
        stateCode = strdup("AK");
        return stateCode;
    }else if(strcasecmp("Alabama",state) == 0){
        stateCode = strdup("AL");
        return stateCode;
    }else if(strcasecmp("Arkansas",state) == 0){
        stateCode = strdup("AR");
        return stateCode;
    }else if(strcasecmp("American Samoa",state) == 0){
        stateCode = strdup("AS");
        return stateCode;
    }else if(strcasecmp("Arizona",state) == 0){
        stateCode = strdup("AZ");
        return stateCode;
    }else if(strcasecmp("California",state) == 0){
        stateCode = strdup("CA");
        return stateCode;
    }else if(strcasecmp("Colorado",state) == 0){
        stateCode = strdup("CO");
        return stateCode;
    }else if(strcasecmp("Connecticut",state) == 0){
        stateCode = strdup("CT");
        return stateCode;
    }else if(strcasecmp("District of Columbia",state) == 0){
        stateCode = strdup("DC");
        return stateCode;
    }else if(strcasecmp("Delaware",state) == 0){
        stateCode = strdup("DE");
        return stateCode;
    }else if(strcasecmp("Florida",state) == 0){
        stateCode = strdup("FL");
        return stateCode;
    }else if(strcasecmp("Georgia",state) == 0){
        stateCode = strdup("GA");
        return stateCode;
    }else if(strcasecmp("Guam",state) == 0){
        stateCode = strdup("GU");
        return stateCode;
    }else if(strcasecmp("Hawaii",state) == 0){
        stateCode = strdup("HI");
        return stateCode;
    }else if(strcasecmp("Iowa",state) == 0){
        stateCode = strdup("IA");
        return stateCode;
    }else if(strcasecmp("Idaho",state) == 0){
        stateCode = strdup("ID");
        return stateCode;
    }else if(strcasecmp("Illinois",state) == 0){
        stateCode = strdup("IL");
        return stateCode;
    }else if(strcasecmp("Indiana",state) == 0){
        stateCode = strdup("IN");
        return stateCode;
    }else if(strcasecmp("Kansas",state) == 0){
        stateCode = strdup("KS");
        return stateCode;
    }else if(strcasecmp("Kentucky",state) == 0){
        stateCode = strdup("KY");
        return stateCode;
    }else if(strcasecmp("Louisiana",state) == 0){
        stateCode = strdup("LA");
        return stateCode;
    }else if(strcasecmp("Massachusetts",state) == 0){
        stateCode = strdup("MA");
        return stateCode;
    }else if(strcasecmp("Maryland",state) == 0){
        stateCode = strdup("MD");
        return stateCode;
    }else if(strcasecmp("Maine",state) == 0){
        stateCode = strdup("ME");
        return stateCode;
    }else if(strcasecmp("Michigan",state) == 0){
        stateCode = strdup("MI");
        return stateCode;
    }else if(strcasecmp("Minnesota",state) == 0){
        stateCode = strdup("MN");
        return stateCode;
    }else if(strcasecmp("Missouri",state) == 0){
        stateCode = strdup("MO");
        return stateCode;
    }else if(strcasecmp("Northern Mariana Islands",state) == 0){
        stateCode = strdup("MP");
        return stateCode;
    }else if(strcasecmp("Mississippi",state) == 0){
        stateCode = strdup("MS");
        return stateCode;
    }else if(strcasecmp("Montana",state) == 0){
        stateCode = strdup("MT");
        return stateCode;
    }else if(strcasecmp("North Carolina",state) == 0){
        stateCode = strdup("NC");
        return stateCode;
    }else if(strcasecmp("North Dakota",state) == 0){
        stateCode = strdup("ND");
        return stateCode;
    }else if(strcasecmp("Nebraska",state) == 0){
        stateCode = strdup("NE");
        return stateCode;
    }else if(strcasecmp("New Hampshire",state) == 0){
        stateCode = strdup("NH");
        return stateCode;
    }else if(strcasecmp("New Jersey",state) == 0){
        stateCode = strdup("NJ");
        return stateCode;
    }else if(strcasecmp("New Mexico",state) == 0){
        stateCode = strdup("NM");
        return stateCode;
    }else if(strcasecmp("Nevada",state) == 0){
        stateCode = strdup("NV");
        return stateCode;
    }else if(strcasecmp("New York",state) == 0){
        stateCode = strdup("NY");
        return stateCode;
    }else if(strcasecmp("Ohio",state) == 0){
        stateCode = strdup("OH");
        return stateCode;
    }else if(strcasecmp("Oklahoma",state) == 0){
        stateCode = strdup("OK");
        return stateCode;
    }else if(strcasecmp("Oregon",state) == 0){
        stateCode = strdup("OR");
        return stateCode;
    }else if(strcasecmp("Pennsylvania",state) == 0){
        stateCode = strdup("PA");
        return stateCode;
    }else if(strcasecmp("Puerto Rico",state) == 0){
        stateCode = strdup("PR");
        return stateCode;
    }else if(strcasecmp("Rhode Island",state) == 0){
        stateCode = strdup("RI");
        return stateCode;
    }else if(strcasecmp("South Carolina",state) == 0){
        stateCode = strdup("SC");
        return stateCode;
    }else if(strcasecmp("South Dakota",state) == 0){
        stateCode = strdup("SD");
        return stateCode;
    }else if(strcasecmp("Tennessee",state) == 0){
        stateCode = strdup("TN");
        return stateCode;
    }else if(strcasecmp("Texas",state) == 0){
        stateCode = strdup("TX");
        return stateCode;
    }else if(strcasecmp("United States Minor Outlying Islands",state) == 0){
        stateCode = strdup("UM");
        return stateCode;
    }else if(strcasecmp("Utah",state) == 0){
        stateCode = strdup("UT");
        return stateCode;
    }else if(strcasecmp("Virginia",state) == 0){
        stateCode = strdup("VA");
        return stateCode;
    }else if(strcasecmp("Virgin Islands, U.S.",state) == 0){
        stateCode = strdup("VI");
        return stateCode;
    }else if(strcasecmp("Vermont",state) == 0){
        stateCode = strdup("VT");
        return stateCode;
    }else if(strcasecmp("Washington",state) == 0){
        stateCode = strdup("WA");
        return stateCode;
    }else if(strcasecmp("Wisconsin",state) == 0){
        stateCode = strdup("WI");
        return stateCode;
    }else if(strcasecmp("West Virginia",state) == 0){
        stateCode = strdup("WV");
        return stateCode;
    }else if(strcasecmp("Wyoming",state) == 0){
        stateCode = strdup("WY");
        return stateCode;
    }else{
        stateCode = strdup("");
        return stateCode;
    }
    
}

char *codeToState(char *code){
    char *state = NULL;
    
    if(strcasecmp("AK",code) == 0){
        state = strdup("Alaska");
        return state;
    }else if(strcasecmp("AL",code) == 0){
        state = strdup("Alabama");
        return state;
    }else if(strcasecmp("AR",code) == 0){
        state = strdup("Arkansas");
        return state;
    }else if(strcasecmp("AS",code) == 0){
        state = strdup("American Samoa");
        return state;
    }else if(strcasecmp("AZ",code) == 0){
        state = strdup("Arizona");
        return state;
    }else if(strcasecmp("CA",code) == 0){
        state = strdup("California");
        return state;
    }else if(strcasecmp("CO",code) == 0){
        state = strdup("Colorado");
        return state;
    }else if(strcasecmp("CT",code) == 0){
        state = strdup("Connecticut");
        return state;
    }else if(strcasecmp("DC",code) == 0){
        state = strdup("District of Columbia");
        return state;
    }else if(strcasecmp("DE",code) == 0){
        state = strdup("Delaware");
        return state;
    }else if(strcasecmp("FL",code) == 0){
        state = strdup("Florida");
        return state;
    }else if(strcasecmp("GA",code) == 0){
        state = strdup("Georgia");
        return state;
    }else if(strcasecmp("GU",code) == 0){
        state = strdup("Guam");
        return state;
    }else if(strcasecmp("HI",code) == 0){
        state = strdup("Hawaii");
        return state;
    }else if(strcasecmp("IA",code) == 0){
        state = strdup("Iowa");
        return state;
    }else if(strcasecmp("ID",code) == 0){
        state = strdup("Idaho");
        return state;
    }else if(strcasecmp("IL",code) == 0){
        state = strdup("Illinois");
        return state;
    }else if(strcasecmp("IN",code) == 0){
        state = strdup("Indiana");
        return state;
    }else if(strcasecmp("KS",code) == 0){
        state = strdup("Kansas");
        return state;
    }else if(strcasecmp("KY",code) == 0){
        state = strdup("Kentucky");
        return state;
    }else if(strcasecmp("LA",code) == 0){
        state = strdup("Louisiana");
        return state;
    }else if(strcasecmp("MA",code) == 0){
        state = strdup("Massachusetts");
        return state;
    }else if(strcasecmp("MD",code) == 0){
        state = strdup("Maryland");
        return state;
    }else if(strcasecmp("ME",code) == 0){
        state = strdup("Maine");
        return state;
    }else if(strcasecmp("MI",code) == 0){
        state = strdup("Michigan");
        return state;
    }else if(strcasecmp("MN",code) == 0){
        state = strdup("Minnesota");
        return state;
    }else if(strcasecmp("MO",code) == 0){
        state = strdup("Missouri");
        return state;
    }else if(strcasecmp("MP",code) == 0){
        state = strdup("Northern Mariana Islands");
        return state;
    }else if(strcasecmp("MS",code) == 0){
        state = strdup("Mississippi");
        return state;
    }else if(strcasecmp("MT",code) == 0){
        state = strdup("Montana");
        return state;
    }else if(strcasecmp("NC",code) == 0){
        state = strdup("North Carolina");
        return state;
    }else if(strcasecmp("ND",code) == 0){
        state = strdup("North Dakota");
        return state;
    }else if(strcasecmp("NE",code) == 0){
        state = strdup("Nebraska");
        return state;
    }else if(strcasecmp("NH",code) == 0){
        state = strdup("New Hampshire");
        return state;
    }else if(strcasecmp("NJ",code) == 0){
        state = strdup("New Jersey");
        return state;
    }else if(strcasecmp("NM",code) == 0){
        state = strdup("New Mexico");
        return state;
    }else if(strcasecmp("NV",code) == 0){
        state = strdup("Nevada");
        return state;
    }else if(strcasecmp("NY",code) == 0){
        state = strdup("New York");
        return state;
    }else if(strcasecmp("OH",code) == 0){
        state = strdup("Ohio");
        return state;
    }else if(strcasecmp("OK",code) == 0){
        state = strdup("Oklahoma");
        return state;
    }else if(strcasecmp("OR",code) == 0){
        state = strdup("Oregon");
        return state;
    }else if(strcasecmp("PA",code) == 0){
        state = strdup("Pennsylvania");
        return state;
    }else if(strcasecmp("PR",code) == 0){
        state = strdup("Puerto Rico");
        return state;
    }else if(strcasecmp("RI",code) == 0){
        state = strdup("Rhode Island");
        return state;
    }else if(strcasecmp("SC",code) == 0){
        state = strdup("South Carolina");
        return state;
    }else if(strcasecmp("SD",code) == 0){
        state = strdup("South Dakota");
        return state;
    }else if(strcasecmp("TN",code) == 0){
        state = strdup("Tennessee");
        return state;
    }else if(strcasecmp("TX",code) == 0){
        state = strdup("Texas");
        return state;
    }else if(strcasecmp("UM",code) == 0){
        state = strdup("United States Minor Outlying Islands");
        return state;
    }else if(strcasecmp("UT",code) == 0){
        state = strdup("Utah");
        return state;
    }else if(strcasecmp("VA",code) == 0){
        state = strdup("Virginia");
        return state;
    }else if(strcasecmp("VI",code) == 0){
        state = strdup("Virgin Islands, U.S.");
        return state;
    }else if(strcasecmp("VT",code) == 0){
        state = strdup("Vermont");
        return state;
    }else if(strcasecmp("WA",code) == 0){
        state = strdup("Washington");
        return state;
    }else if(strcasecmp("WI",code) == 0){
        state = strdup("Wisconsin");
        return state;
    }else if(strcasecmp("WV",code) == 0){
        state = strdup("West Virginia");
        return state;
    }else if(strcasecmp("WY",code) == 0){
        state = strdup("Wyoming");
        return state;
    }else{
        state = strdup("");
        return state;
    }
}