package mdiss.umappin.utils;

public class Constants {
	
	
	// URIS
	public static String uMappinUrl = "http://130.206.138.182/";
	//public static String uMappinUrl = "http://10.172.104.17/";
	//public static String uMappinUrl ="http://10.0.2.2:9000/";//localhost from emulator
	public static String pictureUri = uMappinUrl+"photos/";
	public static String followsUri = uMappinUrl+"followsInfo";
	public static String followedUri = uMappinUrl+"followedInfo";
	public static String userUri = uMappinUrl+"api/users"; 


	//END URIS
	public static String prefsName = "uMappinPrefs"; //Name of the SharedPreferences
	public static String logHttp = "HTTP";
	public static String logJSON = "JSON";
	public static String logPrefs = "uMappinPrefs";
	public static String logGeoMethods = "GeoMethods";
	public static String logMap = "Map";
	public static String picturePointName= "The picture is here";
	public static String picturePointDesc= "Current picture";
	
	public static String unauthorizedError = "Unauthorized operation";
	
	
	// IDs to call ProfileHandler
	public static final int profilePicture = 0;
	public static final int profileFollows =1;
	public static final int profileFollowed =2;

}
