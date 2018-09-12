package com.asykurkhamid.submission.Http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hendi on 9/26/17.
 */

public abstract class BaseResponse {
    @SerializedName("page")
    public int page;

    public boolean isSuccessful(){
        return page == 1;
    }
}
