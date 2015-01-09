public class GenericRepositoryImpl<T, ID extends Serializable>
    implements GenericRepositoryIface<T, ID> {

   		private final EntityManager entityManager;  		

  		public SimpleJpaGenericRepository(EntityManager entityManager) {
   		 this.entityManager = entityManager;    	 
  		}

  		protected EntityManager getEntityManager() {
    		return entityManager;
  		}

  		@Override
  		public void persist(T t) {
    		getEntityManager().persist(t);
  		}

  		@Override
  		public List<T> findAll() {
    		return getEntityManager()
        		.createQuery("select x from " + entityClass.getSimpleName() + " x")
        		.getResultList();
  		}

  		@Override
  		public Optional<T> findOne(ID id) {
    		return Optional.fromNullable(getEntityManager().find(entityClass, id));
  		}


  		@Override
  		public void delete(T t) {
    		getEntityManager().remove(t);
  		}
   }