package de.turnertech.thw.cop.ows.model.area;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.turnertech.thw.cop.util.Coordinate;
import de.turnertech.thw.cop.util.DataObject;

public class AreaDecoder {
    
    private AreaDecoder() {

    }

    public static List<DataObject> getAreas(Element root) {
        NodeList areaElements = root.getElementsByTagName(AreaModel.NAME);
        List<DataObject> returnList = new ArrayList<>(areaElements.getLength());

        for(int i = 0; i < areaElements.getLength(); ++i) {
            Element areaElement = (Element)areaElements.item(i);
            Area areaOut = new Area();

            // Get geometry

            NodeList posListElements = areaElement.getElementsByTagName("posList");
            String posListString = posListElements.item(0).getTextContent();

            String[] coordValues = posListString.split(" ");
            List<Coordinate> coordsOut = new ArrayList<>(coordValues.length / 2);
            for(int j = 0; j < coordValues.length; j += 2) {
                Coordinate coord = new Coordinate(Double.parseDouble(coordValues[j]), Double.parseDouble(coordValues[j+1]));
                coordsOut.add(coord);
            }
            areaOut.setGeometry(coordsOut);

            // Get areaType
            NodeList areaTypeElements = areaElement.getElementsByTagName("areaType");
            String areaTypeString = areaTypeElements.item(0).getTextContent();
            areaOut.setAreaType(areaTypeString);

            returnList.add(areaOut);
        }

        return returnList;
    }

}
