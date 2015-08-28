package pl.edu.pwr.litmap.webservices.php;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.databind.JsonNode;

import pl.edu.pwr.litmap.objectrecognize.ObjectRecognize;
import pl.edu.pwr.litmap.webservices.WebserviceClient;
import play.libs.Json;

public class PhpSerelWebserviceClient implements WebserviceClient {
 
    public static final String USER_AGENT = "Mozilla/5.0";
//    private static final String URL_SEND_TEXT = "http://www.clarin-pl.eu/synatold/ws/ner/send.php";
//    private static final String URL_CHECK_STATUS = "http://www.clarin-pl.eu/synatold/ws/ner/check.php";
//    private static final String URL_GET_RESULTS = "http://www.clarin-pl.eu/synat/ws/ner/results.php";
    private static final String URL_SEND_TEXT = "http://w8.clarin-pl.eu/synatold/ws/serel/send.php";
    private static final String URL_CHECK_STATUS = "http://w8.clarin-pl.eu/synatold/ws/serel/check.php";
    private static final String URL_GET_RESULTS = "http://w8.clarin-pl.eu/synat/ws/serel/results.php";
    private static final String STATUS_READY = "READY";
    private static final String STATUS_PROCESSING = "PROCESSING";
    private static final String STATUS_QUEUED = "QUEUED";
    private static final int SERVICE_STATUS_CHECK_INTERVAL = 700; // in milliseconds
    private static final int SERVICE_WAIT_FOR_RESULTS_MAX_TIME = 60000; // in milliseconds
    private String token;
//    private String lastStatus;

	public String process(String content) throws IOException {
		
//		System.out.println("PhpSerel service start");
		
        String result = null;
        try {
            sendText(content);
            long timeStart = Calendar.getInstance().getTimeInMillis();
            boolean resultsReady;
            while (!(resultsReady = isResultsReady()) && (Calendar.getInstance().getTimeInMillis() - timeStart < SERVICE_WAIT_FOR_RESULTS_MAX_TIME)) {
                Thread.sleep(SERVICE_STATUS_CHECK_INTERVAL);
            }
            if (resultsReady) {
                result = getResults();
            } else {
    			throw new IOException("Too long time waiting for results. Maximum time is now "+SERVICE_WAIT_FOR_RESULTS_MAX_TIME+" ms.");
            }
//            System.out.println("\n PhpSerel service stop. Execution time: "+(Calendar.getInstance().getTimeInMillis() - timeStart)+ " ms.");
        } catch (InterruptedException ex) {
//            System.out.println("\n PhpSerel service stop with exception: "+ex);
            Logger.getLogger(ObjectRecognize.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return result;
	}

    public void sendText(String text) throws IOException {
        String url = URL_SEND_TEXT;

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("content", text));
        urlParameters.add(new BasicNameValuePair("guesser", "false"));
        urlParameters.add(new BasicNameValuePair("input", "text"));
        urlParameters.add(new BasicNameValuePair("output", "ccl"));

        JsonNode jn = getJSON(url, urlParameters);
        this.token = jn.get("token").asText();
        
//        System.out.println("Token: "+token);
    }

    public void sendCcl(String ccl) throws IOException {
        String url = URL_SEND_TEXT;

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("content", ccl));
        urlParameters.add(new BasicNameValuePair("guesser", "false"));
        urlParameters.add(new BasicNameValuePair("input", "ccl"));
        urlParameters.add(new BasicNameValuePair("output", "ccl"));

        JsonNode jn = getJSON(url, urlParameters);
        this.token = jn.get("token").asText();
        
//        System.out.println("Token: "+token);
    }
    
    private boolean isResultsReady() throws IOException {
        if (this.token == null) {
            throw new RuntimeException("Token is null.");
        }
        String url = URL_CHECK_STATUS;

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("token", this.token));

        JsonNode jn = getJSON(url, urlParameters);
        String resultText = jn.get("status").asText();
		
//        if (lastStatus.equals(resultText)) {
//        	System.out.print('.');
//        } else {
//        	System.out.println("PhpSerel service status = "+resultText);
//        	this.lastStatus = resultText;
//        }
        
        return resultText.equals(STATUS_READY);
    }
    
    public static String getResultStatus(String token) throws IOException {
        if (token == null) {
            throw new RuntimeException("Token is null.");
        }
        String url = URL_CHECK_STATUS;

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("token", token));

        JsonNode jn = getJSON(url, urlParameters);
        String resultText = jn.get("status").asText();
        
        return resultText;
    }
    
    public String getToken() {
    	return token;
    }
    
    private String getResults() throws IOException {
        return getResult(this.token);
    }
    
    public static String getResult(String token) throws IOException {
        if (token == null) {
            throw new RuntimeException("Token is null.");
        }
        String url = URL_GET_RESULTS;

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("output", "ccl"));
        urlParameters.add(new BasicNameValuePair("token", token));

        JsonNode jn = getJSON(url, urlParameters);
        String resultXml = jn.get("xml").asText();
        
        return resultXml;
    }

	public static JsonNode getJSON(String url, List<NameValuePair> urlParameters) throws IOException {
	        
	        HttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost(url);
	
	        // add header
	        post.setHeader("User-Agent", USER_AGENT);
	
	        post.setEntity(new UrlEncodedFormEntity(urlParameters,"UTF-8"));
	
	        HttpResponse response = client.execute(post);
	
	        BufferedReader rd = new BufferedReader(
	                new InputStreamReader(response.getEntity().getContent()));
	
	        StringBuilder result = new StringBuilder();
	        String line = "";
	        while ((line = rd.readLine()) != null) {
	                result.append(line);
	        }
	        
	        
	        if (response.getStatusLine().getStatusCode() != 200) {
	            throw new IOException("Problem with webservice at url: "+url+"\n"
	                    + "Response code = "+response.getStatusLine().getStatusCode()+" (expected: 200)\n"
	                    + "Content:\n"+result);
	        }
	        
	        JsonNode jn = Json.parse(result.toString());
	
	        return jn;
	    }
	
}
