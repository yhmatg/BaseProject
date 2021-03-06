package com.android.baseproject.core;
import com.android.baseproject.core.bean.BaseResponse;
import com.android.baseproject.core.bean.user.UserInfo;
import com.android.baseproject.core.bean.user.UserLoginResponse;
import com.android.baseproject.core.http.HttpHelper;
import com.android.baseproject.core.http.HttpHelperImpl;
import com.android.baseproject.core.prefs.PreferenceHelper;
import com.android.baseproject.core.prefs.PreferenceHelperImpl;

import io.reactivex.Observable;

/**
 * @author yhm
 * @date 2017/11/27
 */

public class DataManager implements HttpHelper, PreferenceHelper {

    private HttpHelper mHttpHelper;
    private PreferenceHelper mPreferenceHelper;
    private volatile static DataManager INSTANCE = null;

    private DataManager(HttpHelper httpHelper, PreferenceHelper preferencesHelper) {
        mHttpHelper = httpHelper;
        mPreferenceHelper = preferencesHelper;
    }

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DataManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataManager(HttpHelperImpl.getInstance(), PreferenceHelperImpl.getInstance());
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void saveHostUrl(String hostUrl) {
        mPreferenceHelper.saveHostUrl(hostUrl);
    }

    @Override
    public String getHostUrl() {
        return mPreferenceHelper.getHostUrl();
    }

    @Override
    public void setToken(String token) {
        mPreferenceHelper.setToken(token);
    }

    @Override
    public String getToken() {
        return mPreferenceHelper.getToken();
    }

    @Override
    public Observable<BaseResponse<UserLoginResponse>> login(UserInfo userInfo) {
        return mHttpHelper.login(userInfo);
    }



}
