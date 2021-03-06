package com.android.baseproject.core.http;
import com.android.baseproject.core.bean.BaseResponse;
import com.android.baseproject.core.bean.user.UserInfo;
import com.android.baseproject.core.bean.user.UserLoginResponse;
import io.reactivex.Observable;

/**
 * @author yhm
 * @date 2017/11/27
 */

public interface HttpHelper {

    Observable<BaseResponse<UserLoginResponse>> login(UserInfo userInfo);

}
