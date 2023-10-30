package com.oblitus.serviceApp.Abstracts;

public abstract class BaseResponseMapper<TBuilder extends IBuilder> {
    protected TBuilder builder;
    protected TBuilder useBuilder(TBuilder builder){
        this.builder = builder;
        this.builder.setEntity();
        return this.builder;
    }

    protected void chceckBuilderSetted(){
        if(builder == null){
            throw new RuntimeException("Builder not setted!");
        }
    }
}
