package com.services.parsers;

import com.services.models.ProductoVentaModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Jorge on 9/7/17.
 */
public class ProductoVentaXMlParser extends AbstractXmlParser<ProductoVentaModel> {

    private final String PCOD = "PCod",
                         NOMBRE = "nombre",
                         PRECIOVENTA = "precioVenta",
                         TAGCOCINA = "cocinacodCocina",
                         NOMBRECOCINA = "nombreCocina",
                         TAGSECCION = "seccionnombreSeccion",
                         NOMBRESECCION = "nombreSeccion";




    public ProductoVentaXMlParser() {
       super("productoVenta","productoVentas");
    }

    @Override
    public ProductoVentaModel readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ENTITY);
        String id = null, nombre = null, precio = "0", nombreCocina = null, nombreSeccion = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(PCOD)) {
                id = readString(parser, PCOD);
            } else if (name.equals(NOMBRE)) {
                nombre = readString(parser, NOMBRE);
            } else if (name.equals(PRECIOVENTA)) {
                precio = readString(parser, PRECIOVENTA);
            }else if(name.equals(TAGCOCINA)){
                nombreCocina = readCocina(parser,TAGCOCINA);

            }else if(name.equals(TAGSECCION)){
                nombreSeccion = readSeccion(parser,TAGSECCION);
            }else {
                skip(parser);
            }
        }
        return new ProductoVentaModel(nombreCocina,nombre,id,Float.valueOf(precio),nombreSeccion);
    }

    private String readSeccion(XmlPullParser parser,String TAG) throws IOException, XmlPullParserException {
        String ret = null;
        parser.require(XmlPullParser.START_TAG, ns, TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(NOMBRESECCION)) {
                ret = readString(parser,NOMBRESECCION);
            } else {
                skip(parser);
            }
        }
        return ret;
    }


    private String readCocina(XmlPullParser parser,String TAG) throws IOException, XmlPullParserException {
        String ret = null;
        parser.require(XmlPullParser.START_TAG, ns, TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(NOMBRECOCINA)) {
                ret = readString(parser,NOMBRECOCINA);

            } else {
                skip(parser);
            }
        }
        return ret;
    }
}
