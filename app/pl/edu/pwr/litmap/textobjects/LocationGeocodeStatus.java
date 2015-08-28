package pl.edu.pwr.litmap.textobjects;

public enum LocationGeocodeStatus {
	UNCHECKED,
	OVER_QUERY_LIMIT,
	ZERO_RESULTS,
	EMPTY_INPUT,
	WEBSERVICE_UNKNOWN_ERROR,
	IO_EXCEPTION,
	SUCCESS
}
