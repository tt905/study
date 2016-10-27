package com.mo.study.rx;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * 事件总线 必须要先订阅，在发送事件。不然事件会丢失
 * 如果想先发送事件，缓存事件。
 * 用BehaviorSubject或者ReplaySubject来代替SerializedSubject
 * Created by motw on 2016/9/6.
 */
public class RxBus {

    private static volatile RxBus mInstance;
    private SerializedSubject<Object,Object> mSubject; //SerializedSubject是线程安全的
    private HashMap<String, CompositeSubscription> mSubscriptionMap;

    private RxBus(){
        mSubject = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 获取实例
     * @return
     */
    public static RxBus getInstance(){
        if (mInstance == null){
            synchronized (RxBus.class){
                if (mInstance == null){
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送事件
     * @param o
     */
    public void post(Object o){
        mSubject.onNext(o);
    }

    /**
     * 返回指定类型的Obserable
     * @param type 要转换的类
     * @param <T> 类型
     * @return
     */
    public <T>Observable<T> toObservable(final Class<T> type){
        return mSubject.ofType(type);
    }

    /**
     * 是否有观察者订阅
     * @return
     */
    public boolean hasObserable(){
        return mSubject.hasObservers();
    }

    /**
     * 订阅方法
     * @param type class 订阅哪种类型的事件 比如 String
     * @param next action 收到事件后执行的回调
     * @param error error 出错时执行的回调
     * @param <T> 类型
     * @return 返回一个Subscription 可取消订阅
     */
    public <T> Subscription doSubscribe(Class<T> type, Action1<T> next, Action1<Throwable> error){
        return toObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);
    }

    /**
     * 保存订阅后的subscrip
     * @param o
     * @param subscription
     */
    public void addSubdcription(Object o, Subscription subscription){
        if (mSubscriptionMap == null){
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();

        if (mSubscriptionMap.get(key) != null){
            mSubscriptionMap.get(key).add(subscription);
        }else {
            CompositeSubscription compositeSubscription = new CompositeSubscription();
            compositeSubscription.add(subscription);
            mSubscriptionMap.put(key, compositeSubscription);
        }
    }

    /**
     * 取消订阅
     * @param o
     */
    public void unSubscription(Object o){
        if (mSubscriptionMap == null){
            return ;
        }

        String key = o.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)){
            return ;
        }
        if (mSubscriptionMap.get(key) != null){
            mSubscriptionMap.get(key).unsubscribe();
        }
        mSubscriptionMap.remove(key);
    }
}
