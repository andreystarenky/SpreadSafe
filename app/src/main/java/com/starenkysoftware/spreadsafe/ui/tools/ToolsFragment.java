package com.starenkysoftware.spreadsafe.ui.tools;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.starenkysoftware.spreadsafe.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ToolsFragment extends Fragment {

    View publicRoot;

    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_model, container, false);
        publicRoot = root;

        handleSSLHandshake();

        getModels();

        Typeface custom_font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins_Light.ttf");
        TextView modelTitle = root.findViewById(R.id.model_title);
        TextView modelDaysText = root.findViewById(R.id.model_days_text);
        modelTitle.setTypeface(custom_font1);
        modelDaysText.setTypeface(custom_font1);

        return root;
    }

    public void getModels(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URL url = new URL("https://yogta.ca/cgi-bin/casemodel.py?start=30&end=34&population=1000");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            String s = con.getResponseMessage();
            Log.d("RFT",content.toString());

            con.disconnect();

            formatInts(content.toString());
        }
        catch (Exception e){
            Log.d("RFT","err-"+e.toString());
        }
    }

    public void formatInts(String x){
        String[] tempStringArr =  x.split("<br/>");
        int[] yVals = new int[tempStringArr.length];
        try{
            for(int i =0; i<tempStringArr.length; i++){
                yVals[i] = Integer.parseInt(tempStringArr[i]);
            }
            addToGraph(yVals);
        }
        catch (Exception e){
            Log.d("RFT",e.toString());
        }
    }

    public void addToGraph(int[] yVals){
        for(int x : yVals){
            Log.d("RFT",Integer.toString(x));
        }

        // GRAPH SETUP
        final GraphView graph = (GraphView) publicRoot.findViewById(R.id.model_graph);
        graph.setVisibility(View.VISIBLE);

        final GraphView graph2 = (GraphView) publicRoot.findViewById(R.id.model_graph2);
        graph2.setVisibility(View.VISIBLE);

        DataPoint[] dataPoints = new DataPoint[yVals.length];
        for(int i = 0; i<yVals.length; i++){
            dataPoints[i] = new DataPoint(i+1,yVals[i]);
            Log.d("RFT","New point at: "+ ((int)(i+1)) +','+ yVals[i]);
        }

        DataPoint[] dataPoints2 = new DataPoint[yVals.length];
        for(int i = 0; i<yVals.length; i++){
            if(i==1 || i==2){
                dataPoints2[i] = new DataPoint(i+1,yVals[i]-100);
            }
            else{
                dataPoints2[i] = new DataPoint(i+1,yVals[i]-120);
            }
            Log.d("RFT","New point at: "+ ((int)(i+1)) +','+ yVals[i]);
        }

        //Log.d("RFT",dataPoints.toString());

        LineGraphSeries<DataPoint> series = new LineGraphSeries< >(dataPoints);
        graph.addSeries(series);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries< >(dataPoints2);
        graph2.addSeries(series2);
    }

    /**
     * Enables https connections
     */
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession arg1) {
                    if (hostname.equalsIgnoreCase("yogta.ca") || hostname.equalsIgnoreCase("api.cloudinary.com")) {    //ONLY ALLOW FROM MY DOMAIN
                        Log.d("RFT","Allowed");
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        } catch (Exception ignored) {
        }
    }
}