package healthStore.DOA;

import healthStore.exceptions.ApiException;

import java.util.List;

public interface iDAOMock<T,D> {
    public T getById(D id) throws ApiException;
    public T create(T t) throws ApiException;
    public T update(T t,D id) throws ApiException;
    public T delete(D id) throws ApiException;
    public List<T> getAll() throws ApiException;
}
