package com.gwm.compiler;

import com.gwm.annotation.Contact;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;

/**
 * Created by Administrator on 2019/1/15.
 */

public abstract class BaseProcessor extends AbstractProcessor {
    private Filer filer;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    protected void writeClazz(TypeSpec clazz) {
        JavaFile javaFile = JavaFile.builder(Contact.PACKAGENAME,clazz)
                .skipJavaLangImports(true)
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
