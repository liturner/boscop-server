package de.turnertech.thw.cop.model.hazard;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.turnertech.thw.cop.gml.Feature;

public class HazardDecoder {
    
    private HazardDecoder() {

    }

    public static List<Feature> getHazards(Element root) {
        NodeList hazardElements = root.getElementsByTagName(HazardModel.NAME);
        List<Feature> returnList = new ArrayList<>(hazardElements.getLength());

        for(int i = 0; i < hazardElements.getLength(); ++i) {
            Element hazardElement = (Element)hazardElements.item(i);
            Hazard hazardOut = new Hazard();

            // Get geometry

            NodeList posListElements = hazardElement.getElementsByTagName("pos");
            String posListString = posListElements.item(0).getTextContent();

            String[] coordValues = posListString.split(" ");
            hazardOut.setLatitude(Double.parseDouble(coordValues[0]));
            hazardOut.setLongitude(Double.parseDouble(coordValues[1]));

            // Get areaType
            NodeList hazardTypeElements = hazardElement.getElementsByTagName("hazardType");
            String hazardTypeString = hazardTypeElements.item(0).getTextContent();
            hazardOut.setHazardType(hazardTypeString);

            returnList.add(hazardOut);
        }

        return returnList;
    }

}
