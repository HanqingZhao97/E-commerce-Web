package com.yunniu.lease.model;

import com.yunniu.lease.util.MapTransformUtils;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Data
public class Pages {

    protected int page; // 当前页
    protected int startCount;
    protected int limit;
    private Object object;

    public Pages(Object object, int page, int limit) {
        super();
        this.page = page;
        this.object = object;
        this.startCount = limit * (page - 1);
        this.limit = limit;
    }

    public static Map<String, Object> Pages(Object object, int page, int limit) {
        try {
            Map<String, Object> map = MapTransformUtils.objectToMap(object);
            map.put("startCount", limit * (page - 1));
            map.put("limit", limit);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Map<String, Object> getParams(HttpServletRequest request) {
        Enumeration enu = request.getParameterNames();
        Map<String, Object> map = new HashMap<>();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            map.put(paraName, request.getParameter(paraName));
        }

        String limit1 = (String) map.get("limit");
        int limit = 10, page = 1;
        if (limit1 != null && !"".equals(limit1)) {
            limit = Integer.valueOf(map.get("limit").toString());
            page = Integer.valueOf(map.get("page").toString());
        }
        map.put("limit", limit);
        map.put("startCount", limit * (page - 1));
        return map;
    }

    public static Map<String, Object> getParamsNoPage(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            map.put(paraName, request.getParameter(paraName));
        }
        return map;
    }


}
