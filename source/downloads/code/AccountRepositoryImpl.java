public class AccountRepositoryImpl extends GenericRepositoryImpl<Account,Long>
        implements AccountRepositoryIface {
    @Inject
    public AccountRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }
    public List<Account> findAccount(Long orderItemId, String accountId) {
           return getEntityManager().createQuery("select ft from Account ft where ft.orderItemId = :orderItemId and ft.accountId = :accountId",
                   Account.class)
                   .setParameter("orderItemId", orderItemId)
                   .setParameter("accountId", accountId)
                   .getResultList();
    }
}