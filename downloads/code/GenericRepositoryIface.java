public interface GenericRepositoryIface<T, ID extends Serializable> {
	public void persist(T entity);
	public List<T> findAll();
	public T findOne(ID id);
	public void delete(T t);
}