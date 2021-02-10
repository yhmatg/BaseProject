package com.android.baseproject.core.http.widget;

import android.text.TextUtils;
import com.android.baseproject.R;
import com.android.baseproject.app.BaseApplication;
import com.android.baseproject.base.view.AbstractView;
import com.android.baseproject.core.http.exception.ServerException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * @author yhm
 * @date 2018/4/2
 */

public abstract class BaseSubscribe <T> extends ResourceSubscriber<T> {

    private AbstractView mView;
    private String mErrorMsg;
    private boolean isShowError = true;

    protected BaseSubscribe(AbstractView view){
        this.mView = view;
    }

    protected BaseSubscribe(AbstractView view, String errorMsg){
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected BaseSubscribe(AbstractView view, boolean isShowError){
        this.mView = view;
        this.isShowError = isShowError;
    }

    protected BaseSubscribe(AbstractView view, String errorMsg, boolean isShowError){
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowError = isShowError;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof ServerException) {
            mView.showErrorMsg(e.toString());
        } else if (e instanceof HttpException) {
            mView.showErrorMsg(BaseApplication.getInstance().getString(R.string.http_error));
        } else {
            mView.showErrorMsg(BaseApplication.getInstance().getString(R.string.unKnown_error));
        }
        if (isShowError) {
            mView.showError();
        }
    }
}
