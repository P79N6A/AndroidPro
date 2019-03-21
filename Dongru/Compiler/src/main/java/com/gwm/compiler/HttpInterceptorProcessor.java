package com.gwm.compiler;

import com.google.auto.service.AutoService;
import com.gwm.annotation.Contact;
import com.gwm.annotation.HTTP;
import com.gwm.annotation.HttpInterceptor;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by Administrator on 2019/1/10.
 */
@AutoService(Processor.class)
public class HttpInterceptorProcessor extends BaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        ClassName OkHttpClient = ClassName.get("okhttp3","OkHttpClient");
        ClassName CookieJarImpl = ClassName.get("com.zhy.http.okhttp.cookie","CookieJarImpl");
        ClassName PersistentCookieStore = ClassName.get("com.zhy.http.okhttp.cookie.store","PersistentCookieStore");
        ClassName Context = ClassName.get("android.content","Context");
        ClassName TimeUnit = ClassName.get("java.util.concurrent","TimeUnit");
        MethodSpec.Builder builder = MethodSpec.methodBuilder("getOkHttpClient")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(OkHttpClient)
                .addParameter(Context,"context")
                .addCode("$T cookieJar = new $T(new $T(context));\n",CookieJarImpl,CookieJarImpl,PersistentCookieStore)
                .addCode("OkHttpClient okHttpClient = new OkHttpClient.Builder()\n")
                .addCode(".cookieJar(cookieJar)\n")
                .addCode(".connectTimeout(30L, $T.SECONDS)\n",TimeUnit)
                .addCode(".retryOnConnectionFailure(true)\n");
        ClassName HttpInterceptor = ClassName.get("com.gwm.http","HttpInterceptor");
        builder.addCode("\t.addInterceptor(new $T())\n",HttpInterceptor);
        for (Element element : roundEnvironment.getElementsAnnotatedWith(HttpInterceptor.class)){
            if (element.getKind() == ElementKind.CLASS) {
                HttpInterceptor interceptor = element.getAnnotation(HttpInterceptor.class);
                ClassName className = ClassName.get(interceptor.value(),element.getSimpleName().toString());
                builder.addCode("\t.addInterceptor(new $T())\n",className);
            }
        }
        builder.addCode("\t.build();\n");
        builder.addCode("return okHttpClient;\n");
        MethodSpec method = builder.build();
        TypeSpec clazz = TypeSpec.classBuilder("HttpClient")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(method)
                .build();
        writeClazz(clazz);
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> stringSet = new LinkedHashSet<>();
        stringSet.add(HttpInterceptor.class.getCanonicalName());
        stringSet.add(HTTP.class.getCanonicalName());
        return stringSet;
    }
}
