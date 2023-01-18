package com.yunniu.lease.service;

import com.yunniu.lease.model.MapCoordinate;
import com.yunniu.lease.model.Result;
import com.yunniu.lease.model.TableResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MapCoordinateService {


    TableResult getMapCoordinateList(HttpServletRequest request);

    List<MapCoordinate> getMapCoordinateAll();

    Result addMapCoordinate(MapCoordinate mapCoordinate);

    Result updateMapCoordinate(MapCoordinate mapCoordinate);

    Result delMapCoordinate(String ids);


}
