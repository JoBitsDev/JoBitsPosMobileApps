package com.services.parsers;

import com.services.models.SeccionModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Jorge on 7/7/17.
 */
public class SeccionXMlParser extends AbstractXmlParser<SeccionModel> {

    private static final String NOMBRE_SECCION = "nombreSeccion";

    public SeccionXMlParser() {
        super("seccion","seccions");
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them
    // off
    // to their respective &quot;read&quot; methods for processing. Otherwise, skips the tag.
    public SeccionModel readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ENTITY);
        String nombreSeccion = "";
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
        if (name.equals(NOMBRE_SECCION)) {
            nombreSeccion = readString(parser,NOMBRE_SECCION);
        } else {
            skip(parser);
        }
    }

    return new SeccionModel(nombreSeccion);
}



    // Processes CodMesa tags in the feed.





}
