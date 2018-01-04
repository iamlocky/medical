package presenter;

public interface IPresenterListener<T> {
    void done(T data);
    void done(String data);
    void showMessage(String msg);
}
