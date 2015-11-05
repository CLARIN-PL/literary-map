package pl.edu.pwr.services.serel;

/**
 * Created by tnaskret on 04.11.15.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author twalkow
 */
public class SerelServiceRs {

    static private final String LPMN =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><lpmn>\n" +
                    "<source id=\"1\">\n" +
                    " <file name=\"serel.xml\">%fileid%</file> \n" +
                    "</source>\n" +
                    "<activity id=\"2\" name=\"any2txt\" source=\"1\"/>\n" +
                    "<activity id=\"3\" name=\"wcrft2\" source=\"2\"/>\n" +
                    "<activity id=\"4\" name=\"liner2\"  options=\"{'model':'5nam'}\" source=\"3\"/>\n" +
                    "<activity id=\"5\" name=\"serel\" source=\"4\"/>\n" +
                    "<output id=\"result\" source=\"5\">\n" +
                    "</output>\n" +
                    "</lpmn>";

    static private final String nlprestURL = "http://ws.clarin-pl.eu/nlprest/base/";
    static private final String nlpengineURL = "http://ws.clarin-pl.eu/nlpengine/e/";

    public static String nlpTextUpload(String text) throws IOException {
        return ClientBuilder.newClient().target(nlprestURL + "upload").request().
                post(Entity.entity(text, MediaType.APPLICATION_OCTET_STREAM)).
                readEntity(String.class);
    }

    private static String nlpEngineExecute(String lpmn) throws IOException {
        return ClientBuilder.newClient().target(nlpengineURL + "startTask").request().
                post(Entity.entity(lpmn, MediaType.APPLICATION_XML)).
                readEntity(String.class);
    }

    public static String nlpTextDownload(String id) throws IOException {
        URL url = new URL(nlprestURL + "download/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == 200) {
            InputStream in = conn.getInputStream();
            InputStreamReader is = new InputStreamReader(in, StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(is);
            String read = br.readLine();

            while (read != null) {
                sb.append(read);
                read = br.readLine();
            }
            return sb.toString();
        } else throw new IOException("Error downloading file");

    }

    private static String getRes(Response res) throws IOException {
        if (res.getStatus() != 200)
            throw new IOException("Error in nlprest processing");
        return res.readEntity(String.class);
    }


    public static String nlpEngineProcess(String lpmn) throws IOException, InterruptedException {
        Client client = ClientBuilder.newClient();
        String id = nlpEngineExecute(lpmn);
        String status = "";
        JSONObject jsonres = new JSONObject();
        while (!status.equals("DONE")) {
            String res = getRes(client.target(nlpengineURL + "getJSONStatus/" + id).request().get());
            //System.out.println(res);
            jsonres = new JSONObject(res);
            status = jsonres.getString("status");
            if (status.equals("ERROR")) throw new IOException("Error in processing");
            Thread.sleep(500);
        }
        return jsonres.getJSONObject("value").getJSONArray("result").getJSONObject(0).getString("fileID");
    }


    public static String process(String text){

        try {
            String idt = nlpTextUpload(text);
            String lpmn = LPMN.replaceAll("%fileid%", idt);
            String idr = nlpEngineProcess(lpmn);
            String result = nlpTextDownload(idr);
            return result;
        } catch (IOException ex) {
            Logger.getLogger(SerelService.class.getName()).log(Level.SEVERE, "Problems in processing LPMN", ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerelService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}

