package com.goproject100.demo.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This model class represents the data packet described in the code challenge.
 */
public class DataPacket {

    private final Position position;
    private final UserType type;

    /**
     * Construct an immutable data packet object.
     * @param position the {@link Position} of this data packet
     * @param type the {@link UserType} of this data packet
     */
    @JsonCreator
    public DataPacket(@JsonProperty("position") final Position position, @JsonProperty("type") final UserType type) {
        this.position = position;
        this.type = type;
    }

    /**
     * @return the {@link Position} for this data packet.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @return the {@link UserType} for this data packet.
     */
    public UserType getType() {
        return type;
    }
}
