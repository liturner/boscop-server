module de.turnertech.ows {

    requires java.xml;
    requires transitive java.logging;
    requires transitive org.eclipse.jetty.servlet;

    exports de.turnertech.ows.common;
    exports de.turnertech.ows.filter;
    exports de.turnertech.ows.gml;
    exports de.turnertech.ows.parameter;
    exports de.turnertech.ows.servlet;
    
}
