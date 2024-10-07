package com.example.Config;

import com.example.DTO.Result;

import java.util.Objects;

public abstract class BsicUtil {
    public Result prepareResponseObject(String code, String message, Object responseData) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
//        if (responseData) {
//            result.setData(null);
//        } else {
        result.setData(responseData);
//        }
        return result;
    }

    public static boolean isNullOrEmpty(Object value) {
        return (Objects.isNull(value) || value.toString().isBlank()) ? Boolean.TRUE : Boolean.FALSE;
    }

    public abstract Result updateProduct(int id, String productName, String productDescription, String price, String quantity, String file);
}
