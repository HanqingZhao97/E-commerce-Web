package com.yunniu.lease.dao;


import com.yunniu.lease.model.MapCoordinate;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MapCoordinateDao {


    @Insert(value = {"<script>",
            "insert into map_coordinate(address,title,phone,lat,lng)",
            "values(#{address},#{title},#{phone},#{lat},#{lng})",
            "</script>"})
    Integer addMapCoordinate(MapCoordinate mapCoordinate);


    @Select(value = {"<script>",
            "select * from map_coordinate",
            "<where>",
            "<when test='address !=null and address !=\"\"'>and address like concat('%',#{address},'%')</when>",
            "</where>",
            "limit ${startCount},${limit}",
            "</script>"})
    List<MapCoordinate> getMapCoordinateList(Map<String, Object> mps);

    @Select(value = {"<script>",
            "select * from map_coordinate",
            "</script>"})
    List<MapCoordinate> getMapCoordinateAll();

    @Select(value = {"<script>",
            "select count(1) from map_coordinate",
            "<where>",
            "<when test='address !=null and address !=\"\"'>and address like concat('%',#{address},'%')</when>",
            "</where>",
            "</script>"})
    Integer getMapCoordinateCount(Map<String, Object> mps);

    @Update(value = {"<script>",
            "update map_coordinate set address = ${address},title = ${title},lat = ${lat},lng = ${lng} where id = ${id}",
            "</script>"})
    Integer updateMapCoordinate(MapCoordinate mapCoordinate);


    @Delete(value = {"<script>",
            "delete from map_coordinate where FIND_IN_SET(id,#{ids})",
            "</script>"})
    Integer delMapCoordinate(String ids);


    @Select(value = {"<script>",
            "select * from map_coordinate where id = #{id}",
            "</script>"})
    MapCoordinate getMapCoordinateInfo(String id);

}
