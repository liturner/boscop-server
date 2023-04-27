package de.turnertech.thw.cop;

public class Constants {
    
    private Constants() {}

    public static final String REALM = "urn:de:turnertech:cop";

    public class Model {
        private Model() {}
        public static final String NAMESPACE = "urn:ns:de:turnertech:boscop";
    }

    public class Roles {
        private Roles() {}
        public static final String USER = "user";
        public static final String ADMIN = "admin";
        public static final String DEVICE = "device";
    }

    public class Paths {
        private Paths() {}
        public static final String WFS = "/ows";
        public static final String ERROR = "/error";
        public static final String TRACKER_USER = "/tracker";
        public static final String TRACKER_API = "/tracker/update";
    }

    public class HeaderKeys {
        private HeaderKeys() {}
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String X_API_KEY = "X-API-Key";
    }

    public class ContentTypes {
        public static final String XML = "application/xml; charset=utf-8";
    }

}
