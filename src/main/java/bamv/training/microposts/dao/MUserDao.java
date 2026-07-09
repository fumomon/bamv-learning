package bamv.training.microposts.dao;

import bamv.training.microposts.entity.MUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MUserDao {
    MUser findUser(String userId);

    List<MUser> searchUser(String userId);
    
    List<MUser> findAllExceptMe(String userId);

    int addNewUser(String userId, String name, String password);
}
