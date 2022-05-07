package wooteco.subway.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import wooteco.subway.dao.JdbcStationDao;
import wooteco.subway.domain.Station;
import wooteco.subway.dto.StationRequest;
import wooteco.subway.dto.StationResponse;

@Service
public class StationService {

    private static final String STATION_DUPLICATION = "이미 등록된 지하철 역입니다.";
    private final JdbcStationDao stationDao;

    public StationService(JdbcStationDao stationDao) {
        this.stationDao = stationDao;
    }

    public StationResponse createStation(StationRequest stationRequest) {
        validateDuplication(stationRequest.getName());

        String name = stationRequest.getName();
        return new StationResponse(stationDao.save(name), name);
    }

    private void validateDuplication(String name) {
        if (stationDao.isExistStation(name)) {
            throw new IllegalArgumentException(STATION_DUPLICATION);
        }
    }

    public List<StationResponse> getStations() {
        List<Station> stations = stationDao.findAll();
        List<StationResponse> stationResponses = stations.stream()
                .map(it -> new StationResponse(it.getId(), it.getName()))
                .collect(Collectors.toList());
        return stationResponses;
    }

    public boolean deleteStation(Long id) {
        return stationDao.deleteById(id);
    }
}
