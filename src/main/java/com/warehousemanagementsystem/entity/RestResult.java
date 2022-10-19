package com.warehousemanagementsystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RestResult<T> {
    private Integer code;
    private String msg;
    private Object obj;
    private List<T> list;

    public RestResult(Integer code, String msg, Object obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public RestResult(Integer code, String msg, List<T> list) {
        this.code = code;
        this.msg = msg;
        this.list = list;
    }

    public RestResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> RestResult success(Integer code, String msg, List<T> list) {
        return new RestResult(code, msg, list);
    }

    public static <T> RestResult success(Integer code, String msg) {
        return new RestResult(code, msg);
    }

    public static RestResult success(Integer code, String msg, Object obj) {
        return new RestResult(code, msg, obj);
    }

    public static RestResult error(Integer code, String msg) {
        return new RestResult(code, msg);
    }

    public static RestResult error(Integer code, String msg, Object obj) {
        return new RestResult(code, msg, obj);
    }
}
