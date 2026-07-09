package bamv.training.microposts.service;

import java.util.List;

public interface FollowService {
    int findFollowNumber(String userId);

    int findFollowerNumber(String userId);
    
    void follow(String userId, String followedUserId);
    
    void unfollow(String userId, String followedUserId);
    
    List<String> getFollowingIds(String userId);
     
 
}
