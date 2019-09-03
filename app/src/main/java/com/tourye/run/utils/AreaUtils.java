package com.tourye.run.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class AreaUtils {

    public static List<AreaBean> getArea(){
        Gson gson = new Gson();
        String temp="[\n" +
                "    { \"text\": \"中国大陆地区\", \"value\": \"86\" },\n" +
                "    { \"text\": \"香港地区\", \"value\": \"852\" },\n" +
                "    { \"text\": \"澳门地区\", \"value\": \"853\" },\n" +
                "    { \"text\": \"台湾地区\", \"value\": \"886\" },\n" +
                "    { \"text\": \"新加坡\", \"value\": \"65\" },\n" +
                "    { \"text\": \"马来西亚\", \"value\": \"60\" },\n" +
                "    { \"text\": \"泰国\", \"value\": \"66\" },\n" +
                "    { \"text\": \"菲律宾\", \"value\": \"63\" },\n" +
                "    { \"text\": \"印度尼西亚\", \"value\": \"62\" },\n" +
                "    { \"text\": \"日本\", \"value\": \"81\" },\n" +
                "    { \"text\": \"韩国\", \"value\": \"82\" },\n" +
                "    { \"text\": \"美国\", \"value\": \"1\" },\n" +
                "    { \"text\": \"加拿大\", \"value\": \"1\" },\n" +
                "    { \"text\": \"澳大利亚\", \"value\": \"61\" }\n" +
                "]";
        List<AreaBean> list = new ArrayList<>();
        try {
            JsonArray arry = new JsonParser().parse(temp).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, AreaBean.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static class AreaBean{

        /**
         * text : 中国大陆地区
         * value : +86
         */

        private String text;
        private String value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "AreaBean{" +
                    "text='" + text + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
