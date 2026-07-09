package bamv.training.microposts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bamv.training.microposts.dao.MSequenceDao;
import bamv.training.microposts.dao.TFollowDao;
import bamv.training.microposts.entity.MSequence;
import bamv.training.microposts.service.FollowService;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private TFollowDao tFollowDao;
    
    @Autowired
    private MSequenceDao mSequenceDao;


    @Override
    public int findFollowNumber(String userId) {
        return tFollowDao.countFollowingNumber(userId);
    }

    @Override
    public int findFollowerNumber(String userId) {
        return tFollowDao.countFollowerNumber(userId);
    } 
    
    @Transactional 
    @Override
    public void follow(String userId, String followedUserId) {

        MSequence seq = mSequenceDao.findSequence("follow_id");
        int nextNumber = seq.getCurrentNumber() + 1;
        String prefix = seq.getPrefix() != null ? seq.getPrefix().trim() : "FL";
        String newFollowId = prefix + String.format("%08d", nextNumber);
        tFollowDao.insertFollow(newFollowId, userId, followedUserId);
        mSequenceDao.incrementSequenceCurrentNumber(seq);
    }
    
    @Transactional
    @Override
    public void unfollow(String userId, String followedUserId) {
        // DAOを呼び出して、該当のフォロー関係のレコードを削除する
        tFollowDao.deleteFollow(userId, followedUserId);
    }


    @Override
    public List<String> getFollowingIds(String loginUserId) {
        return tFollowDao.findFollowingIds(loginUserId);
    }
}