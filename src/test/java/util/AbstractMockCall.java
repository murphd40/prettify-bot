package util;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;

public abstract class AbstractMockCall<T> implements Call<T> {

    @Override
    public void enqueue(Callback<T> callback) {

    }

    @Override
    public boolean isExecuted() {
        return true;
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public Call<T> clone() {
        return null;
    }

    @Override
    public Request request() {
        return null;
    }
}
