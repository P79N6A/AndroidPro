package com.gwm.compiler;

import com.google.auto.service.AutoService;
import com.gwm.annotation.Contact;
import com.gwm.annotation.HermsMessageService;
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
public class HermsMessageProcessor extends BaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        ClassName List = ClassName.get("java.util","ArrayList");
        ClassName HermsMessageBusService = ClassName.get("com.gwm.messagesendreceive","HermsMessageBusService");
        MethodSpec.Builder builder = MethodSpec.methodBuilder("getHersMessageServices");
        builder.returns(List);
        builder.addModifiers(Modifier.PUBLIC,Modifier.STATIC);
        builder.addCode("ArrayList<Class<? extends $T>> services = new ArrayList<Class<? extends $T>>();\n",HermsMessageBusService,HermsMessageBusService);
        for (Element element : roundEnvironment.getElementsAnnotatedWith(HermsMessageService.class)){
            if (element.getKind() == ElementKind.CLASS) {
                HermsMessageService service = element.getAnnotation(HermsMessageService.class);
                ClassName HermsMessageBusService1 = ClassName.get(service.value(),element.getSimpleName().toString());
                builder.addCode("services.add($T.class);\n",HermsMessageBusService1);
            }
        }
        builder.addCode("return services;\n");
        MethodSpec method = builder.build();
        TypeSpec clazz = TypeSpec.classBuilder("HermsMessageUtil")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(method)
                .build();
        writeClazz(clazz);
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> stringSet = new LinkedHashSet<>();
        stringSet.add(HermsMessageService.class.getCanonicalName());
        return stringSet;
    }
}
