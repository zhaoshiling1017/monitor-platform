package com.ducetech.api.model.vo;

import java.io.Serializable;

/**
 * Created by lenzhao on 17-2-2.
 */
public class AdvertiserVO implements Serializable {

    private String advertiserName;

    private String description;

    public String getAdvertiserName() {
        return advertiserName;
    }

    public void setAdvertiserName(String advertiserName) {
        this.advertiserName = advertiserName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
