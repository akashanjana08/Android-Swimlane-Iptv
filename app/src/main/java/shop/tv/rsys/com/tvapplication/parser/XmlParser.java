package shop.tv.rsys.com.tvapplication.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import shop.tv.rsys.com.tvapplication.Movie;

/**
 * Created by Akash.Sharma on 12/12/2017.
 */

public class XmlParser {

    public static List<Movie>  xmlPullParser(InputStream is)
    {
        List<Movie> movies= new ArrayList<>();
        Movie movie=null;
        String text="";
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("Movie")) {
                            // create a new instance of Movie
                            movie = new Movie();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("Movie")) {
                            // add employee object to list
                            movies.add(movie);
                        }else if (tagname.equalsIgnoreCase("Title")) {
                            movie.setStudio(text);
                        }  else if (tagname.equalsIgnoreCase("longDescription")) {
                            movie.setTitle(text);
                        }else if (tagname.equalsIgnoreCase("JPEG")) {
                            String cardImageUrl = "http://10.67.191.50:8085/posterserver/poster/vod/w-274_h-364/"+text;
                            movie.setCardImageUrl(cardImageUrl);
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        return movies;
    }
}
