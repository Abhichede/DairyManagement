package main;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

public class APIService {
    String username = "abhichede", pass = "7777";
    static DatabaseConnection dbConnection = new DatabaseConnection();
    public String sendSMS(String mobileNumber, String message, int cust_id) throws IOException, ClientProtocolException, URISyntaxException {
        HttpClient client = new DefaultHttpClient();
        String messageStatus = "";
        String nummsg = "";
        String msgID = "";
        String msgData[] = new String[4];


        if (netIsAvailable()) {
            QueryString qs = new QueryString("username", username);
            qs.add("pass", pass);
            qs.add("route", "trans1");
            qs.add("senderid", "DANCEE");
            qs.add("numbers", mobileNumber);
            qs.add("message", message);
            System.out.println("http://173.45.76.227/send.aspx?" + qs);
            HttpGet request = new HttpGet("http://173.45.76.227/send.aspx?" + qs);
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            System.out.println("Status: ");
            String line = rd.readLine();
            line = line.replace('|', '-');

            if (line != null) {
                System.out.println(line);
                String[] arrOfStr = line.split("-", 3);

                messageStatus = arrOfStr[0];
                if (messageStatus.equals("1")) {
                    msgID = arrOfStr[2];
                }

                msgData[0] = mobileNumber;
                msgData[1] = message;
                msgData[2] = messageStatus;
                msgData[3] = msgID;
            }
        }else{

            messageStatus = "8";
            msgData[0] = mobileNumber;
            msgData[1] = message;
            msgData[2] = messageStatus;
            msgData[3] = msgID;
        }
        dbConnection.addMessageStatus(cust_id, msgData);

        return messageStatus;
    }

    public String getAvailableBalance() throws IOException, ClientProtocolException, URISyntaxException {
        HttpClient client = new DefaultHttpClient();

        QueryString qs = new QueryString("username", username);
        qs.add("pass", pass);
        System.out.println("http://173.45.76.227/balance.aspx?"+ qs);
        HttpGet request = new HttpGet("http://173.45.76.227/balance.aspx?"+ qs);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        System.out.println("Status: ");
        String line = rd.readLine();
        line = line.replace('|', '-');

        String avlBalance = "";

        if (line != null) {
            String[] arrOfStr = line.split("-", 30);

            String msgStatus = arrOfStr[0];
            if(msgStatus.equals("1")) {
                for(int i = 1; i < arrOfStr.length; i++){
                    String balance[] = arrOfStr[i].split(":");
                    if(balance[0].equals("trans1")){
                        avlBalance = balance[1];
                    }
                }
            }
        }

        return avlBalance;
    }

    class QueryString {

        private String query = "";

        public QueryString(String name, String value) {
            encode(name, value);
        }

        public void add(String name, String value) {
            query += "&";
            encode(name, value);
        }

        private void encode(String name, String value) {
            try {
                query += URLEncoder.encode(name, "UTF-8");
                query += "=";
                query += URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException("Broken VM does not support UTF-8");
            }
        }

        public String getQuery() {
            return query;
        }

        public String toString() {
            return getQuery();
        }

    }

    public static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
}
