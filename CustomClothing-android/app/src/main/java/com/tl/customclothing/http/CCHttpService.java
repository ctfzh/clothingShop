package com.tl.customclothing.http;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by tianlin on 2017/4/6.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public interface CCHttpService
{
    @Headers({
            "Content-Type:application/json;" + "charset=UTF-8"
    })
    @POST("{controller}")
    Observable<String> post(
            @Path("controller") String controller,
            @Body String params);
}
