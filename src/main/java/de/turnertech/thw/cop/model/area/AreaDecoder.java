package de.turnertech.thw.cop.model.area;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.turnertech.thw.cop.gml.DirectPosition;
import de.turnertech.thw.cop.gml.DirectPositionList;
import de.turnertech.thw.cop.gml.Feature;
import de.turnertech.thw.cop.gml.LinearRing;
import de.turnertech.thw.cop.gml.Polygon;

public class AreaDecoder {
    
    private AreaDecoder() {

    }

    public static List<Feature> getAreas(Element root) {
        NodeList areaElements = root.getElementsByTagName(AreaModel.NAME);
        List<Feature> returnList = new ArrayList<>(areaElements.getLength());

        for(int i = 0; i < areaElements.getLength(); ++i) {
            Element areaElement = (Element)areaElements.item(i);
            Area areaOut = new Area();

            // Get geometry

            NodeList posListElements = areaElement.getElementsByTagName("posList");
            String posListString = posListElements.item(0).getTextContent();

            String[] coordValues = posListString.split(" ");
            
            DirectPositionList coordsOut = new DirectPositionList(coordValues.length / 2);
            for(int j = 0; j < coordValues.length; j += 2) {
                DirectPosition coord = new DirectPosition(Double.parseDouble(coordValues[j+1]), Double.parseDouble(coordValues[j]));
                coordsOut.add(coord);
            }
            areaOut.setGeometry(new Polygon(new LinearRing(coordsOut)));

            // Get areaType
            NodeList areaTypeElements = areaElement.getElementsByTagName("areaType");
            String areaTypeString = areaTypeElements.item(0).getTextContent();
            areaOut.setAreaType(areaTypeString);

            returnList.add(areaOut);
        }

        return returnList;
    }

}
