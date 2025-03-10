package bamv.training.microposts.dao.impl;

import bamv.training.microposts.dao.TMicropostDao;
import bamv.training.microposts.entity.TMicropost;
import bamv.training.microposts.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TMicropostDaoImpl implements TMicropostDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SequenceService sequenceService;

    @Override
    public int countMicropostNumber(String userId) {
        String query = "SELECT COUNT(*) as count FROM t_micropost WHERE user_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, userId);
    }

    @Override
    public List<TMicropost> searchUserMicropost(String userId, int page) {
        RowMapper<TMicropost> rowMapper = new BeanPropertyRowMapper<>(TMicropost.class);
        int offset = 2 * (page - 1);

        String query = """
                    SELECT
                        tm.*
                    FROM
                        t_micropost tm inner join t_follow tf on tm.user_id = tf.user_id
                    WHERE
                        tm.user_id = ? or tf.following_user_id = ? 
                    ORDER BY
                        tm.posted_datetime desc, tm.micropost_id desc
                    limit ?, 2
                """;
        return jdbcTemplate.query(query, rowMapper, userId, userId,offset);
    }

    @Override
    public List<TMicropost> searchFollowingMicropost(String userId, int page) {
        RowMapper<TMicropost> rowMapper = new BeanPropertyRowMapper<>(TMicropost.class);
        int offset = 2 * (page - 1);

        String query = """
                    SELECT
                        *
                    FROM
                        t_micropost
                    WHERE
                        user_id IN (
                            SELECT
                                followed_user_id
                            FROM
                                t_follow
                            WHERE
                                following_user_id = ?
                        )
                        OR user_id = ?
                    ORDER BY
                        posted_datetime desc, micropost_id desc
                    limit ?, 2
                """;
        return jdbcTemplate.query(query, rowMapper, userId, userId, offset);
    }

    @Override
    public int addNewMicropost(String userId, String content) {
        String newMicropostId = sequenceService.issueSequence("micropost_id");
        String query = "INSERT INTO t_micropost values (?, ?, ?, ?)";
        return jdbcTemplate.update(query,
                newMicropostId,
                content,
                userId,
                LocalDateTime.now());
    }
}
