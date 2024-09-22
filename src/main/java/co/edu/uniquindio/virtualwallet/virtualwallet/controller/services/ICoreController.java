package co.edu.uniquindio.virtualwallet.virtualwallet.controller.services;

public interface ICoreController<T> {
    boolean add(T object);
    boolean delete(String id);
    boolean update(String id, T object);
}
