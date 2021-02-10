package com.android.baseproject.core.http.widget;

import android.text.TextUtils;
import android.util.Log;

import com.android.baseproject.R;
import com.android.baseproject.app.BaseApplication;
import com.android.baseproject.base.view.AbstractView;
import com.android.baseproject.core.http.exception.ResultIsNullException;
import com.android.baseproject.core.http.exception.ServerException;
import com.android.baseproject.core.http.exception.TokenException;
import com.android.baseproject.utils.ToastUtils;

import java.net.SocketTimeoutException;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

/**
 * @param <T>
 * @author yhm
 * @date 2017/11/27
 */

public abstract class BaseObserver<T> extends ResourceObserver<T> {

    private AbstractView mView;
    private String mErrorMsg;
    private boolean isShowError = true;

    protected BaseObserver(AbstractView view) {
        this.mView = view;
    }

    protected BaseObserver(AbstractView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected BaseObserver(AbstractView view, boolean isShowError) {
        this.mView = view;
        this.isShowError = isShowError;
    }

    protected BaseObserver(AbstractView view, String errorMsg, boolean isShowError) {
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
        mView.dismissDialog();
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof ServerException) {
            mView.showErrorMsg(e.toString());
        } else if (e instanceof HttpException) {
            ToastUtils.showShort(R.string.http_error);
        } else if (e instanceof TokenException) {
            ToastUtils.showShort(R.string.token_error);
            mView.startLoginActivity();
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.showShort(R.string.socket_time_out_error);
        } else if (e instanceof ResultIsNullException) {
            ToastUtils.showShort(R.string.not_result_error);
        } else {
            mView.showErrorMsg(BaseApplication.getInstance().getString(R.string.unKnown_error));
        }
        if (isShowError) {
            mView.showError();
        }
        Log.e("BaseObserver", "Throwable=====" + e);
    }
}
