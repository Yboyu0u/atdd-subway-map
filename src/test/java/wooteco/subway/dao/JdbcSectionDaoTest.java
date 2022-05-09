package wooteco.subway.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import wooteco.subway.domain.Section;

@JdbcTest
class JdbcSectionDaoTest {

    private JdbcSectionDao jdbcSectionDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcSectionDao = new JdbcSectionDao(jdbcTemplate);
        Section section = new Section(1L, 2L, 3L, 5);
        jdbcSectionDao.save(section);
    }

    @DisplayName("구간 정보를 등록한다.")
    @Test
    void save() {
        Section section = new Section(1L, 2L, 3L, 5);
        Long id = jdbcSectionDao.save(section);
        assertThat(id).isNotNull();
    }

    @DisplayName("구간 정보를 제거한다.")
    @Test
    void deleteByLineIdAndStationId() {
        boolean isDeleted = jdbcSectionDao.deleteByLineIdAndStationId(1L, 2L);
        assertThat(isDeleted).isTrue();
    }

    @DisplayName("db에 stationId가 존재하지 않는 경우 테스트")
    @Test
    void deleteByLineIdAndStationIdIfStationIdIsNotExist() {
        boolean isDeleted = jdbcSectionDao.deleteByLineIdAndStationId(1L, 4L);
        assertThat(isDeleted).isFalse();
    }
}