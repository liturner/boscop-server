package de.turnertech.ows.srs;

import java.util.Objects;

public class SpatialReferenceSystemRepresentation {

    private final SpatialReferenceSystem srs;

    private final SpatialReferenceSystemFormat format;

    public SpatialReferenceSystemRepresentation(final SpatialReferenceSystem srs, final SpatialReferenceSystemFormat format) {
        Objects.requireNonNull(srs);
        Objects.requireNonNull(format);
        this.format = format;
        this.srs = srs;
    }

    public SpatialReferenceSystem getSrs() {
        return srs;
    }

    public SpatialReferenceSystemFormat getFormat() {
        return format;
    }

    public static SpatialReferenceSystemRepresentation from(String value) {
        SpatialReferenceSystem srs = SpatialReferenceSystem.from(value);
        if(srs == null) {
            return null;
        }

        if(srs.getCode().equalsIgnoreCase(value)) {
            return new SpatialReferenceSystemRepresentation(srs, SpatialReferenceSystemFormat.CODE);
        } else if(srs.getUri().equalsIgnoreCase(value)) {
            return new SpatialReferenceSystemRepresentation(srs, SpatialReferenceSystemFormat.URI);
        } else {
            return new SpatialReferenceSystemRepresentation(srs, SpatialReferenceSystemFormat.URN);
        }
    }

    @Override
    public String toString() {
        switch(format) {
            case CODE:
                return srs.getCode();
            case URI:
                return srs.getUri();
            case URN:
                return srs.getCode();
            default:
                return "Error, unsupported SpatialReferenceSystemFormat";
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((srs == null) ? 0 : srs.hashCode());
        result = prime * result + ((format == null) ? 0 : format.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SpatialReferenceSystemRepresentation other = (SpatialReferenceSystemRepresentation) obj;
        if (srs != other.srs)
            return false;
        if (format != other.format)
            return false;
        return true;
    }

}
