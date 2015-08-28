/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pwr.litmap.textobjects;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.litmap.objectrecognize.GeocodingResultType;

/**
 * 7 głównych grup:
 * • antroponimy – nazwy odnoszące się do osób lub grup osób,
 * • toponimy – nazwy odnoszące się do obiektów geograficznych i geopolitycznych,
 * • kosmonimy – nazwy odnoszące się do obiektów kosmicznych,
 * • organizmy żywe – nazwy odnoszące się do organizmów żywych innych niż ludzie,
 * • chrematonimy – nazw odnoszące się do obiektów stworzonych przez ludzi,
 * • hydronimy – nazwy odnoszące się do naturalnych obiektów wodnych,
 * • urbanonimy – nazwy odnoszące się do elementów przestrzeni miejskiej.
 * 
 * 
 * 
 */

/**
 *
 * @author wgawel
 */
public enum NameClass {
    NATION_NAM,
    PERSON_ADD_NAM,
    PERSON_FIRST_NAM,
    PERSON_LAST_NAM,
    PERSON_NAM,
    ADMIN1_NAM,
    ADMIN2_NAM,
    ADMIN3_NAM,
    CAPE_NAM,
    CITY_NAM,
    CONTINENT_NAM,
    CONURBATION_NAM,
    COUNTRY_NAM,
    COUNTRY_REGION_NAM,
    HISTORICAL_REGION_NAM,
    ISLAND_NAM,
    MOUNTAIN_NAM,
    PENINSULA_NAM,
    REGION_NAM,
    TOPONYM_NAM,
    ADDRESS_STREET_NAM,
    AWARD_NAM,
    EVENT_NAM,
    EVENT_HOLIDAY_NAM,
    EVENT_SPORT_NAM,
    EVENT_CULTURAL_NAM,
    CURRENCY_NAM,
    GROUP_NAM,
    GROUP_TEAM_NAM,
    GROUP_BAND_NAM,
    FACILITY_NAM, /* nazwy budynków: muzea, szkoły, teatry itd. */
    IP_NAM,
    LICENSE_NAM,
    MAIL_NAM,
    MEDIA_NAM,
    MEDIA_TV_NAM,
    MEDIA_RADIO_NAM,
    MEDIA_PERIODIC_NAM,
    MODEL_NAM,
    MODEL_CAR_NAM,
    MODEL_PHONE_NAM,
    MODEL_PLANE_NAM,
    MODEL_SHIP_NAM,
    ORGANIZATION_NAM,
    COMPANY_NAM,
    INSTITUTION_NAM,
    ORGANIZATION_SUB_NAM,
    POLITICAL_PARTY_NAM,
    SOFTWARE_NAM,
    SOFTWARE_OS_NAM,
    SOFTWARE_GAME_NAM,
    SOFTWARE_VERSION_NAM,
    SYSTEM_NAM,
    TECH_NAM,
    TECH_DATA_FORMAT_NAM,
    TITLE_NAM,
    TITLE_ALBUM_NAM,
    TITLE_ARTICLE_NAM,
    TITLE_BOARDGAME_NAM,
    TITLE_BOOK_NAM,
    TITLE_DOCUMENT_NAM,
    TITLE_PAINTING_NAM,
    TITLE_SONG_NAM,
    TITLE_TV_NAM,
    TITLE_RADIO_NAM,
    TITLE_OTHER_NAM,
    TREATY_NAM,
    VEHICLE_NAM,
    WEB_NAM,
    WWW_NAM,
    BRIDGE_NAM,
    CROSSROAD_NAM,
    DISTRICT_NAMPOSTAL_CODE_NUM,
    HOUSE_NUM,
    FLAT_NUM,
    PHONE_NUM,
    PARK_NAM,
    ROAD_NAM,
    SQUARE_NAM,
    SUBDIVISTION_NAM,
    ASTRONOMICAL_NAM,
    ANIMAL_NAM,
    BAY_NAM,
    LAGOON_NAM,
    LAKE_NAM,
    OCEAN_NAM,
    RIVER_NAM,
    SEA_NAM,
    PERSON_ADJ,
    COUNTRY_ADJ,
    CITY_ADJ,
    POSTAL_CODE_NUM,
    BRAND_NAM, /* wystąpiło dla "Siedziba >Orange< mieści się w Warszawie koło Dworca Zachodniego." */
    BAND_NAM, /* wystąpiło dla "Nazywa się <span>Dąb Przewodnik</span> i jest jednym z najstarszych drzew w mieście." oraz "Ten krasnoludek uwielbia spędzać czas w aktywny sposób, wędrując po Wrocławiu z kijkami do <span>Nordic Walking</span>."  */
    PERIODIC_NAM,
    DISTRICT_NAM, /* wystąpiło dla "Konferencja na wysokim szczeblu w sprawie <span>Południowo-Wschodniej</span> Europy powinna wypracować Pakt Stabilizacyjny, obejmujący kompleksową strategię stabilizacji całego regionu poprzez odbudowę gospodarki, promocję demokracji i włączenie w europejskie i euro-atlantyckie struktury integracyjne. " */
    DOCUMENT_NAM,
    SUBDIVISION_NAM;
    
    public int getTopologicalHierarchyOrderNumber() {
    	int nr = -1;
    	switch (this) {
		case ADDRESS_STREET_NAM:
			nr = 90;
			break;
		case ADMIN1_NAM:
			nr = 400;
			break;
		case ADMIN2_NAM:
			nr = 200;
			break;
		case ADMIN3_NAM:
			nr = 300;
			break;
		case ANIMAL_NAM:
			break;
		case ASTRONOMICAL_NAM:
			break;
		case AWARD_NAM:
			break;
		case BAND_NAM:
			break;
		case BAY_NAM:
			break;
		case BRAND_NAM:
			break;
		case BRIDGE_NAM:
			nr = 90;
			break;
		case CAPE_NAM:
			nr = 600;
			break;
		case CITY_ADJ:
			break;
		case CITY_NAM:
			nr = 150;
			break;
		case COMPANY_NAM:
			break;
		case CONTINENT_NAM:
			nr = 800;
			break;
		case CONURBATION_NAM:
			nr = 350;
			break;
		case COUNTRY_ADJ:
			break;
		case COUNTRY_NAM:
			nr = 600;
			break;
		case COUNTRY_REGION_NAM:
			nr = 500;
			break;
		case CROSSROAD_NAM:
			nr = 80;
			break;
		case CURRENCY_NAM:
			break;
		case DISTRICT_NAMPOSTAL_CODE_NUM:
			break;
		case EVENT_CULTURAL_NAM:
			break;
		case EVENT_HOLIDAY_NAM:
			break;
		case EVENT_NAM:
			break;
		case EVENT_SPORT_NAM:
			break;
		case FACILITY_NAM:
			nr = 60;
			break;
		case FLAT_NUM:
			break;
		case GROUP_BAND_NAM:
			break;
		case GROUP_NAM:
			break;
		case GROUP_TEAM_NAM:
			break;
		case HISTORICAL_REGION_NAM:
			nr = 510;
			break;
		case HOUSE_NUM:
			nr = 50;
			break;
		case INSTITUTION_NAM:
			nr = 55;
			break;
		case IP_NAM:
			break;
		case ISLAND_NAM:
			nr = 700;
			break;
		case LAGOON_NAM:
			break;
		case LAKE_NAM:
			nr = 70;
			break;
		case LICENSE_NAM:
			break;
		case MAIL_NAM:
			break;
		case MEDIA_NAM:
			break;
		case MEDIA_PERIODIC_NAM:
			break;
		case MEDIA_RADIO_NAM:
			break;
		case MEDIA_TV_NAM:
			break;
		case MODEL_CAR_NAM:
			break;
		case MODEL_NAM:
			break;
		case MODEL_PHONE_NAM:
			break;
		case MODEL_PLANE_NAM:
			break;
		case MODEL_SHIP_NAM:
			break;
		case MOUNTAIN_NAM:
			nr = 450;
			break;
		case NATION_NAM:
			break;
		case OCEAN_NAM:
			break;
		case ORGANIZATION_NAM:
			nr = 55;
			break;
		case ORGANIZATION_SUB_NAM:
			nr = 54;
			break;
		case PARK_NAM:
			nr = 95;
			break;
		case PENINSULA_NAM:
			nr = 650;
			break;
		case PERSON_ADD_NAM:
			break;
		case PERSON_ADJ:
			break;
		case PERSON_FIRST_NAM:
			break;
		case PERSON_LAST_NAM:
			break;
		case PERSON_NAM:
			break;
		case PHONE_NUM:
			break;
		case POLITICAL_PARTY_NAM:
			break;
		case POSTAL_CODE_NUM:
			break;
		case REGION_NAM:
			nr = 630;
			break;
		case RIVER_NAM:
			nr = 120;
			break;
		case ROAD_NAM:
			nr = 90;
			break;
		case SEA_NAM:
			nr = 100;
			break;
		case SOFTWARE_GAME_NAM:
			break;
		case SOFTWARE_NAM:
			break;
		case SOFTWARE_OS_NAM:
			break;
		case SOFTWARE_VERSION_NAM:
			break;
		case SQUARE_NAM:
			nr = 70;
			break;
		case SUBDIVISTION_NAM:
			break;
		case SYSTEM_NAM:
			break;
		case TECH_DATA_FORMAT_NAM:
			break;
		case TECH_NAM:
			break;
		case TITLE_ALBUM_NAM:
			break;
		case TITLE_ARTICLE_NAM:
			break;
		case TITLE_BOARDGAME_NAM:
			break;
		case TITLE_BOOK_NAM:
			break;
		case TITLE_DOCUMENT_NAM:
			break;
		case TITLE_NAM:
			break;
		case TITLE_OTHER_NAM:
			break;
		case TITLE_PAINTING_NAM:
			break;
		case TITLE_RADIO_NAM:
			break;
		case TITLE_SONG_NAM:
			break;
		case TITLE_TV_NAM:
			break;
		case TOPONYM_NAM:
			nr = 100;
			break;
		case TREATY_NAM:
			break;
		case VEHICLE_NAM:
			break;
		case WEB_NAM:
			break;
		case WWW_NAM:
			break;
		default:
			break;
    		
    	}
    	return nr;
    }
    
    public boolean isHigherInTopologicalHierachyThan(NameClass nc) {
    	return this.getTopologicalHierarchyOrderNumber() > nc.getTopologicalHierarchyOrderNumber();
    }
    
    /**
     * Rzutowanie typów nazw własnych Liner2 na typy z Google Maps API
     * TODO: Uzupełnić i sprawdzić.
     * @return
     */
    public List<GeocodingResultType> getGeocodingResultTypes() {
    	List<GeocodingResultType> result = new ArrayList<>();
    	switch (this) {
		case ADDRESS_STREET_NAM:
			result.add(GeocodingResultType.STREET_ADDRESS);
			break;
		case ADMIN1_NAM:
			result.add(GeocodingResultType.ADMINISTRATIVE_AREA_LEVEL_1);
			break;
		case ADMIN2_NAM:
			result.add(GeocodingResultType.ADMINISTRATIVE_AREA_LEVEL_2);
			break;
		case ADMIN3_NAM:
			result.add(GeocodingResultType.ADMINISTRATIVE_AREA_LEVEL_3);
			result.add(GeocodingResultType.ADMINISTRATIVE_AREA_LEVEL_4);
			break;
		case ANIMAL_NAM:
			break;
		case ASTRONOMICAL_NAM:
			break;
		case AWARD_NAM:
			break;
		case BAND_NAM:
			break;
		case BAY_NAM:
			break;
		case BRAND_NAM:
			break;
		case BRIDGE_NAM:
			result.add(GeocodingResultType.ROUTE); // sprawdzone
			break;
		case CAPE_NAM:
			break;
		case CITY_ADJ:
			break;
		case CITY_NAM:
			result.add(GeocodingResultType.LOCALITY); // sprawdzone
			result.add(GeocodingResultType.POLITICAL); // sprawdzone
			break;
		case COMPANY_NAM:
			break;
		case CONTINENT_NAM:
			result.add(GeocodingResultType.NATURAL_FEATURE); // sprawdzona dla "Europa", "Azja"
			break;
		case CONURBATION_NAM:
			break;
		case COUNTRY_ADJ:
			break;
		case COUNTRY_NAM:
			result.add(GeocodingResultType.COUNTRY); // teoretyczne
			break;
		case COUNTRY_REGION_NAM:
			break;
		case CROSSROAD_NAM:
			break;
		case CURRENCY_NAM:
			break;
		case DISTRICT_NAMPOSTAL_CODE_NUM:
			break;
		case EVENT_CULTURAL_NAM:
			break;
		case EVENT_HOLIDAY_NAM:
			break;
		case EVENT_NAM:
			break;
		case EVENT_SPORT_NAM:
			break;
		case FACILITY_NAM:
			break;
		case FLAT_NUM:
			result.add(GeocodingResultType.FLOOR);
			break;
		case GROUP_BAND_NAM:
			break;
		case GROUP_NAM:
			break;
		case GROUP_TEAM_NAM:
			break;
		case HISTORICAL_REGION_NAM:
			break;
		case HOUSE_NUM:
			result.add(GeocodingResultType.STREET_NUMBER);
			break;
		case INSTITUTION_NAM:
			break;
		case IP_NAM:
			break;
		case ISLAND_NAM:
			break;
		case LAGOON_NAM:
			break;
		case LAKE_NAM:
			result.add(GeocodingResultType.NATURAL_FEATURE);
			break;
		case LICENSE_NAM:
			break;
		case MAIL_NAM:
			break;
		case MEDIA_NAM:
			break;
		case MEDIA_PERIODIC_NAM:
			break;
		case MEDIA_RADIO_NAM:
			break;
		case MEDIA_TV_NAM:
			break;
		case MODEL_CAR_NAM:
			break;
		case MODEL_NAM:
			break;
		case MODEL_PHONE_NAM:
			break;
		case MODEL_PLANE_NAM:
			break;
		case MODEL_SHIP_NAM:
			break;
		case MOUNTAIN_NAM:
			break;
		case NATION_NAM:
			break;
		case OCEAN_NAM:
			break;
		case ORGANIZATION_NAM:
			break;
		case ORGANIZATION_SUB_NAM:
			break;
		case PARK_NAM:
			result.add(GeocodingResultType.PARK);
			break;
		case PENINSULA_NAM:
			break;
		case PERSON_ADD_NAM:
			break;
		case PERSON_ADJ:
			break;
		case PERSON_FIRST_NAM:
			break;
		case PERSON_LAST_NAM:
			break;
		case PERSON_NAM:
			break;
		case PHONE_NUM:
			break;
		case POLITICAL_PARTY_NAM:
			break;
		case POSTAL_CODE_NUM:
			break;
		case REGION_NAM:
			result.add(GeocodingResultType.LOCALITY);
			break;
		case RIVER_NAM:
			result.add(GeocodingResultType.NATURAL_FEATURE);
			break;
		case ROAD_NAM:
			result.add(GeocodingResultType.ROUTE); // sprawdzone
			break;
		case SEA_NAM:
			result.add(GeocodingResultType.NATURAL_FEATURE);
			break;
		case SOFTWARE_GAME_NAM:
			break;
		case SOFTWARE_NAM:
			break;
		case SOFTWARE_OS_NAM:
			break;
		case SOFTWARE_VERSION_NAM:
			break;
		case SQUARE_NAM:
			result.add(GeocodingResultType.ROUTE);
			result.add(GeocodingResultType.PARK);
			break;
		case SUBDIVISTION_NAM:
			break;
		case SYSTEM_NAM:
			break;
		case TECH_DATA_FORMAT_NAM:
			break;
		case TECH_NAM:
			break;
		case TITLE_ALBUM_NAM:
			break;
		case TITLE_ARTICLE_NAM:
			break;
		case TITLE_BOARDGAME_NAM:
			break;
		case TITLE_BOOK_NAM:
			break;
		case TITLE_DOCUMENT_NAM:
			break;
		case TITLE_NAM:
			break;
		case TITLE_OTHER_NAM:
			break;
		case TITLE_PAINTING_NAM:
			break;
		case TITLE_RADIO_NAM:
			break;
		case TITLE_SONG_NAM:
			break;
		case TITLE_TV_NAM:
			break;
		case TOPONYM_NAM:
			break;
		case TREATY_NAM:
			break;
		case VEHICLE_NAM:
			break;
		case WEB_NAM:
			break;
		case WWW_NAM:
			break;
		default:
			break;
    		
    	}
    	return result;
    }

}
