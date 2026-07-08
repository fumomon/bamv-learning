package bamv.training.microposts.service;

import java.util.List;

import bamv.training.microposts.dto.UserDto;
import bamv.training.microposts.entity.MUser;

public interface UserService {
    UserDto findUser(String userId);

    int createNewUser(String userId, String name, String password);
    
    List<MUser> getUsersExceptMe(String userId,int page);
    
}
