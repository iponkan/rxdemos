package com.iponkan.rxdemos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * RxLifeCycle写法
 * 1.这种写法更好，可以使用subscribe不用使用subscribeWith，不用手动管理DisposeObserver
 * 2.在activity退出后会回调Observer的onComplete方法
 */
public class SecondActivity extends RxAppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        test();
    }

    @SuppressLint("CheckResult")
    private void test() {
        Observable.interval(0, 500, TimeUnit.MILLISECONDS)
                .take(100)
                // map操作的书写位置要注意，如果是在线程里执行需要写在observeOn前面
                .map(new Function<Long, Object>() {
                    @Override
                    public Object apply(Long aLong) throws Exception {
                        Log.d(TAG, "Thread;" + Thread.currentThread().getName() + " " + Thread.currentThread().getId());
                        return aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(new DisposableObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        Log.d(TAG, "数据:" + o);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.toString(), e);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
