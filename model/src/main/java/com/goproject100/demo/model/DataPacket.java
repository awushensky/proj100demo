package com.goproject100.demo.model;

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
    public DataPacket(final Position position, final UserType type) {
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
