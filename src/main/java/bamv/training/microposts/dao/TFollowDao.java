package bamv.training.microposts.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface TFollowDao {
    int countFollowingNumber(String userId);

    int countFollowerNumber(String userId);
    
    int insertFollow(String followId, String userId, String followedUserId);
    
    void deleteFollow(String userId, String followedUserId);
    
    List<String> findFollowingIds(String userId);
    
}
