package com.gwm.retrofit;
import com.gwm.http.NetWorkParams;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/24.
 */

public abstract class HttpInvocationHandler implements InvocationHandler {
    protected Map<String,NetWorkParams> paramsMap = new HashMap<>();
    protected List<String> methods = new ArrayList<>();


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //TODO 获取methods跟args上的注解信息 并做好处理
        /**
         * 1.获取方法的注解信息
         * 2.获取参数上的注解信息，并对应好注解信息
         */
        Class clazz = method.getReturnType();
        NetWorkParams params = addNetworkParams(method, args);
        Object obj = clazz.newInstance();
        if(obj instanceof Observable){
            observableMap.put(method.getName(), (Observable) obj);
            methods.add(method.getName());
            paramsMap.put(method.getName(), params);
            return obj;
        }else {
            throw new ClassCastException("The return value type of the method must be com.gwm.Observable or its subclass");
        }
    }

    protected abstract NetWorkParams addNetworkParams(Method method, Object[] args);

    protected Map<String,Observable> observableMap = new HashMap<>();

    public NetWorkParams getHttpParams(String methodName){
        return paramsMap.remove(methodName);
    }

    public Observable getObservable(String methodName){
        return observableMap.remove(methodName);
    }
    public List<String> methods(){
        return methods;
    }
}
