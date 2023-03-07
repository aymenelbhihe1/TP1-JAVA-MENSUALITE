package DAO;

public interface IDao<T,ID> {
    T trouverParID(ID id);
}
