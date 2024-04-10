package healthStore.DOA;

import healthStore.persistance.Role;
import healthStore.persistance.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;


public class UserDAO extends DAO<User,String> implements iSecurityDAO{

    private static UserDAO instance;
    public UserDAO(boolean isTesting) {
        super(User.class,isTesting);
    }
    public static UserDAO getInstance(Boolean isTesting){
        if(instance == null){
            instance = new UserDAO(isTesting);
        }
        return instance;
    }

    public User verifyUser(String username,String password){
        EntityManager em = emf.createEntityManager();
        User userToFind = em.find(User.class,username);
        if(userToFind == null) throw new EntityNotFoundException("No user with username: " + username +" was found");
        if(!userToFind.verifyUser(password)){
            throw new EntityNotFoundException("Wrong password");
        }
        return userToFind;
    }
    @Override
    public User create(User user) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Role role = em.find(Role.class,"user");
            if(role == null){
                role = new Role("user");
                em.persist(role);
            }
            user.addRole(role);
            em.persist(user);
            em.getTransaction().commit();
            return user;
        }
    }
}
