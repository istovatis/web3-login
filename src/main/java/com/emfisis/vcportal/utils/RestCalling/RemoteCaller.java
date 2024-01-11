package com.emfisis.vcportal.utils.RestCalling;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class RemoteCaller<R> {

    Consumer<Response<R>> onError = this::onError;

    public RemoteCaller<R> setOnError(Consumer<Response<R>> onError) {
        this.onError = onError;
        return this;
    }

    public RemoteCaller<R> doNothingOnError(){
        this.onError = customerDtoResponse -> {};
        return this;
    }

    public R call(Function<String, Response<R>> function) {
        Response<R> r = function.apply(getJwt());
        if(r.serverResponse.getStatus() == 401) {
            Response refreshJwt = updateJwt();
            if(refreshJwt.serverResponse.getStatus() != 200){
                this.onError.accept(r);
            }else{
                r = function.apply(getJwt());
            }
        }
        if (r.serverResponse.getStatus() != 200 && r.serverResponse.getStatus() != 201) {
            this.onError.accept(r);
        }
        return r.entity;
    }

    protected abstract void onError(Response<R> r);
    protected abstract String getJwt();
    protected abstract Response updateJwt();
}
