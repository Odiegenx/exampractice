package healthStore.DOA;


import healthStore.persistance.User;

public interface iSecurityDAO {
    public User verifyUser(String username, String password);
}
