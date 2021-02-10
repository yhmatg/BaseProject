package com.android.baseproject.core.http.api;

import com.android.baseproject.core.bean.BaseResponse;
import com.android.baseproject.core.bean.user.UserInfo;
import com.android.baseproject.core.bean.user.UserLoginResponse;
import java.util.List;
import java.util.Set;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author yhm
 * @date 2018/2/12
 */

public interface GeeksApis {

    //登录
    @POST("user-server/userauth/loginwithinfo")
    Observable<BaseResponse<UserLoginResponse>> login(@Body UserInfo userInfo);
}
