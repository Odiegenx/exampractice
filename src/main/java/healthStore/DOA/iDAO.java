package healthStore.DOA;

import java.util.List;

public interface iDAO<T,D> {
    public T getById(D id);
    public T create(T t);
    public T update(T t,D id);
    public void delete(D id);
    public List<T> getAll();
    public void isTest(boolean value);
}
