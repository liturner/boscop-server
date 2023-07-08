module de.turnertech.ows {

    requires transitive java.xml;
    requires transitive java.logging;
    requires transitive org.eclipse.jetty.servlet;
    requires transitive java.desktop;

    exports de.turnertech.ows.common;
    exports de.turnertech.ows.filter;
    exports de.turnertech.ows.gml;
    exports de.turnertech.ows.parameter;
    exports de.turnertech.ows.servlet;
    exports de.turnertech.ows.srs;     
}
