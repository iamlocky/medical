package presenter;

public interface IPresenterListener<T> {
    void done(T data);
    void showMessage(String msg);
}
