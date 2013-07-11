package com.himz.erc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.himz.erc.util.TrustAllManager;
import com.himz.erc.util.TrustAllSSLSocketFactory;

public class GetMethodEx {


public String getInternetData(String url) throws Exception{

        new TrustAllManager();
        new TrustAllSSLSocketFactory();

        BufferedReader in = null;
        String data = null;


        try
        {
        	
        	HttpParams httpParameters = new BasicHttpParams();
        	// Set the timeout in milliseconds until a connection is established.
        	// The default value is zero, that means the timeout is not used. 
        	//int timeoutConnection = 6000;
        	//HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        	// Set the default socket timeout (SO_TIMEOUT) 
        	// in milliseconds which is the timeout for waiting for data.
        	// **** This is important. Change this to get chat like behaviour
        	int timeoutSocket = 10000;
        	HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        	
            HttpClient client = new DefaultHttpClient(httpParameters);
            URI website = new URI(url);
            HttpGet request = new HttpGet();
            request.setURI(website);
            HttpResponse response = client.execute(request);
            
            response.getStatusLine().getStatusCode();

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String l = "";
            String nl = System.getProperty("line.separator");
            while ((l = in.readLine()) !=null){
                sb.append(l + nl);
            }
            in.close();
            data = sb.toString();
            return data;        
        } finally{
            if (in != null){
                try{
                    in.close();
                    return data;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
}}