package com.main.parser;

import android.os.AsyncTask;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 7/7/17.
 */
public abstract class AbstractXmlParser<T> {

    //// TODO: 9/7/17 esto hay que optimizarlo;

    protected String ENTITY,ENTITIES,ns=null;
    protected List<T> objects = new ArrayList<T>();

    public AbstractXmlParser(String entity, String entities) {
        ENTITY = entity;
        ENTITIES = entities;
    }




    public List<T> parse(InputStream in) throws XmlPullParserException, IOException {

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }


    protected List<T> readFeed (XmlPullParser parser) throws XmlPullParserException,IOException{
        List<T> ret = new ArrayList<T>();

        parser.require(XmlPullParser.START_TAG, ns, ENTITIES);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(ENTITY)) {
                ret.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return ret;
    }

    public abstract T readEntry(XmlPullParser parser) throws XmlPullParserException, IOException;

    //protected abstract T read(String value);


    // For the tags codMesa and estado, extracts their text values.
    protected String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }


    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    protected void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    protected String readString(XmlPullParser parser,String TAG) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns,TAG);
        String x = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG);
        return x;
    }

    public List<T> fetch (String url){
        fetchData f = new fetchData();
        f.execute(url);//TODO: aqui falta implementar algo para tratar el tiempo de espera
        try {
            return f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected class fetchData extends AsyncTask<String , Integer ,List<T> >{


        @Override
        protected List <T> doInBackground(String... url) {
            try {
                InputStream in =  downloadUrl(url[0]);
                objects =  parse(in);
                return objects;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                return null;
            }


        }


        private InputStream downloadUrl(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            // Starts the query
            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK) {
                InputStream ret = conn.getInputStream();
                //conn.disconnect(); TODO: ver porque esto esta comentariado;
                return ret;
            }
            else{
                return null;}
        }
    }

}
