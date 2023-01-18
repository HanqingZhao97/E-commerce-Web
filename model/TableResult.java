package com.yunniu.lease.model;

import lombok.Data;

@Data
public class TableResult {
    private Integer code = 0;//状态码10001正常,-10000token出错,-10001参数错误或失败,10000没有数据
    private String msg = "";//消息
    private Object data;//数据对象
    private Integer count;
    private Object total;
//    private Integer monthData;//本月多少条数据

    /**
     * 无参构造器
     */
    public TableResult() {
        super();
    }

    /**
     * 只返回状态,消息
     *
     * @param
     * @param
     * @param msg
     */
    public TableResult(String msg) {
        super();
        this.msg = msg;
    }

    /**
     * 只返回状态,消息
     *
     * @param
     * @param
     * @param
     */
    public TableResult(Integer code) {
        super();
        this.code = code;
        if (code == 10000) {
            this.msg = "没有数据";
        } else if (code == -10000) {
            this.msg = "userId对应的token不匹配!";
        } else if (code == -10001) {
            this.msg = "失败或者参数错误!";
        }
    }

    /**
     * 只返回状态,消息
     *
     * @param
     * @param
     * @param msg
     */
    public TableResult(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public TableResult(int count, Object data) {
        super();
        this.data = data;
        this.count = count;
    }

    public TableResult(int count, Object data, Object total) {
        super();
        this.data = data;
        this.count = count;
        this.total = total;
    }

    /**
     * 返回全部信息状态码，消息，数据对象
     *
     * @param
     * @param code
     * @param msg
     * @param
     */
    public TableResult(Integer code, String msg, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode () {
        return code;
    }

    public void setCode (Integer code) {
        this.code = code;
    }

    public String getMsg () {
        return msg;
    }

    public void setMsg (String msg) {
        this.msg = msg;
    }

    public Object getData () {
        return data;
    }

    public void setData (Object data) {
        this.data = data;
    }

    public Integer getCount () {
        return count;
    }

    public void setCount (Integer count) {
        this.count = count;
    }

    public Object getTotal () {
        return total;
    }

    public void setTotal (Object total) {
        this.total = total;
    }
}
