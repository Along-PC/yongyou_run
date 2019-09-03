package com.tourye.run.bean;

import java.util.List;

/**
 * Created by longlongren on 2018/11/2.
 * <p>
 * introduce:上传动态时json格式
 */

public class SubmitDynamicBean {

    /**
     * content : xxx
     * images : [1,2,3]
     */

    private String content;
    private String position;
    private String sign_in_id;
    private List<String> images;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSign_in_id() {
        return sign_in_id;
    }

    public void setSign_in_id(String sign_in_id) {
        this.sign_in_id = sign_in_id;
    }
}
