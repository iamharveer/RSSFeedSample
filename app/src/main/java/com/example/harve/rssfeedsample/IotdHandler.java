package com.example.harve.rssfeedsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.*;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;

import java.net.HttpURLConnection;
import java.net.URLConnection;


public class IotdHandler extends DefaultHandler {


    public class URLConnectionReader {
        String input = "";
        public URLConnectionReader() throws Exception {
            URL yahoo = new URL("https://www.nasa.gov/rss/dyn/image_of_the_day.rss");
            URLConnection yc = yahoo.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null)
                input += inputLine;

            in.close();
        }
    }

    private String url = null; //"https://www.nasa.gov/rss/dyn/image_of_the_day.rss";
    private boolean inUrl = false;
    private boolean inTitle = false;
    private boolean inDescription = false;
    private boolean inItem = false;
    private boolean inDate = false;
    private Bitmap image = null;
    private String title = null;
    private StringBuffer description = new StringBuffer();
    private String date = null;



    public void processFeed() {
        Object response = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URLConnectionReader obj = new URLConnectionReader();

            InputSource inputSource = new InputSource( new StringReader( obj.input ) );
            // the SAX way:
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser newSAXParser = saxParserFactory.newSAXParser();
            XMLReader myReader = newSAXParser.getXMLReader();;
            myReader.setContentHandler(this);
            myReader.parse(inputSource);
        }
        catch (Exception e) {
            Log.d("Exception: ", e.getMessage());
        }
    }
    public Bitmap getBitmap(String imageURL) {
        try {
           this.url =  "";
            Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(new URL("https://www.nasa.gov/sites/default/files/thumbnails/image/32260740536_2866cf4065_o.jpg").openStream()));
            return bitmap;
        } catch (Exception ioe) {
            Log.d("Exception bitmap:", ioe.getMessage());
            return null;
        }
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        Log.d("xml Tag:", localName);
        if (localName.toLowerCase().equals("enclosure")) {
            inUrl = true; }
        else { inUrl = false; }
        if (localName.startsWith("item")) { inItem = true; }
        else if (inItem) {
            if (localName.equals("title")) { inTitle = true; }
            else { inTitle = false; }
            if (localName.equals("description")) { inDescription = true; }
            else { inDescription = false; }
            if (localName.equals("pubDate")) { inDate = true; }
            else { inDate = false; }
        }
    }
    public void characters(char ch[], int start, int length) {
        String chars = new String(ch).substring(start, start + length);
        if (inUrl && url == null) { image = getBitmap(chars); }
        if (inTitle && title == null) { title = chars; }
        if (inDescription) { description.append(chars); }
        if (inDate && date == null) { date = chars; }
    }


    public Bitmap getImage() { return image; }
    public String getTitle() { return title; }
    public StringBuffer getDescription() { return description; }
    public String getDate() { return date; }
}