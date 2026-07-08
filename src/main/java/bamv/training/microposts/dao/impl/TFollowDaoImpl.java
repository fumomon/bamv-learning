package bamv.training.microposts.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import bamv.training.microposts.dao.TFollowDao;

@Repository
public class TFollowDaoImpl implements TFollowDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int countFollowingNumber(String userId) {
		String query = "SELECT COUNT(*) as count FROM t_follow WHERE following_user_id = ?";
		return jdbcTemplate.queryForObject(query, Integer.class, userId);
	}

	@Override
	public int countFollowerNumber(String userId) {
		String query = "SELECT COUNT(*) as count FROM t_follow WHERE followed_user_id = ?";
		return jdbcTemplate.queryForObject(query, Integer.class, userId);
	}

	@Override
	public int insertFollow(String followId, String userId, String followedUserId) {
		String query = "INSERT INTO t_follow (follow_id, following_user_id, followed_user_id) VALUES (?, ?, ?)";
		return jdbcTemplate.update(query, followId, userId, followedUserId);
	}

	@Override
	public void deleteFollow(String userId, String followedUserId) {
		String sql = """
				DELETE FROM
				    t_follow
				WHERE
				    following_user_id = ?
				    AND followed_user_id = ?
				""";
		jdbcTemplate.update(sql, userId, followedUserId);
	}

	@Override
	public List<String> findFollowingIds(String userId) {
		String query = "SELECT followed_user_id FROM t_follow WHERE following_user_id = ?";
		return jdbcTemplate.queryForList(query, String.class, userId);
	}
}
