package com.goproject100.demo.data.dao;

import com.goproject100.demo.model.UserType;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * This mapper handles the SQL requests for this application.
 */
public interface DataPacketDAO {

    @Select("SELECT data_packet FROM demo.data_packets WHERE type = (SELECT id FROM demo.types WHERE type = #{type}) " +
            "ORDER BY id DESC " +
            "LIMIT #{number}")
    List<String> getDataPackets(@Param("type") UserType type, @Param("number") int number);

    @Select("SELECT url FROM demo.listeners WHERE type = (SELECT id FROM demo.types WHERE type = #{type})")
    List<String> getListeners(@Param("type") UserType type);

    @Insert("INSERT INTO demo.listeners (url, type) VALUES (#{url}, SELECT id FROM demo.types WHERE type = #{type})")
    @SelectKey(statement = "call identity()", keyProperty = "id", before = false, resultType = Long.class)
    long registerListener(@Param("url") String url, @Param("type") UserType type);

    @Insert("INSERT INTO demo.data_packets (data_packet, type) VALUES (#{data_packet}, SELECT id FROM demo.types WHERE type = #{type})")
    @SelectKey(statement = "call identity()", keyProperty = "id", before = false, resultType = Long.class)
    long newDataPacket(@Param("data_packet") String dataPacket, @Param("type") UserType type);

}
