package bamv.training.microposts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bamv.training.microposts.dao.MUserDao;
import bamv.training.microposts.dto.UserDto;
import bamv.training.microposts.entity.MUser;
import bamv.training.microposts.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private MUserDao mUserDao;

    @Override
    public UserDto findUser(String userId) {
        MUser mUser = mUserDao.findUser(userId);
        return new UserDto(
                mUser.getUserId(),
                mUser.getName()
        );
    }

    @Override
    @Transactional
    public int createNewUser(String userId, String name, String password) {
        return mUserDao.addNewUser(userId, name, password);
    }
    
    @Override
    public List<MUser> getUsersExceptMe(String userId,int page) {
    	RowMapper<MUser> rowMapper = new BeanPropertyRowMapper<>(MUser.class);
    	int offset = 2 * (page - 1);

        String query = """
                SELECT
                    *
                FROM
                    m_user
                WHERE
                    user_id != ?
                ORDER BY
                    user_id
                limit ?, 2  
            """;
    	
        return jdbcTemplate.query(query, rowMapper, userId, offset);
    }
}
