package com.gwm.compiler;

import com.google.auto.service.AutoService;
import com.gwm.annotation.Contact;
import com.gwm.annotation.Layout;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class LayoutProcessor extends BaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        for (Element element : roundEnvironment.getElementsAnnotatedWith(Layout.class)){
            if (element.getKind() == ElementKind.CLASS) {
                Layout annotation = element.getAnnotation(Layout.class);
                int resid = annotation.value();
                //TODO 生成类设置View
                ClassName View = ClassName.get("android.view","View");
                ClassName Context = ClassName.get("android.content","Context");
                MethodSpec main = MethodSpec.methodBuilder("inflate")
                        .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                        .addParameter(Context,"context")
                        .addParameter(int.class,"resId")
                        .returns(View)
                        .addCode("return android.view.LayoutInflater.from(context).inflate(resId, null);\n")
                        .build();

                ClassName Activity = ClassName.get("android.app","Activity");
                MethodSpec bind1 = MethodSpec.methodBuilder("bind")
                        .addParameter(Activity,"activity")
                        .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                        .returns(void.class)
                        .addModifiers()
                        .addCode("activity.setContentView(inflate(activity,"+resid+"));\n")
                        .build();

                ClassName Fragment = ClassName.get("android.support.v4.app","Fragment");
                MethodSpec bind = MethodSpec.methodBuilder("bind")
                        .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                        .addParameter(Fragment,"fragment")
                        .returns(View)
                        .addCode("return inflate(fragment.getActivity().getApplicationContext(),"+resid+");\n")
                        .build();

                //构造类
                TypeSpec clazz = TypeSpec.classBuilder(element.getSimpleName()+"_ViewBind").addModifiers(Modifier.PUBLIC)
                        .addMethod(main)
                        .addMethod(bind1)
                        .addMethod(bind)
                        .build();

                writeClazz(clazz);
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> stringSet = new LinkedHashSet<>();
        stringSet.add(Layout.class.getCanonicalName());
        return stringSet;
    }
}
