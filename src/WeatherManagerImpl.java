import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class WeatherManagerImpl implements WeatherManager{
    private List<WeatherDto> weatherDtos;

    public WeatherManagerImpl(List<WeatherDto> weatherDtos) {
        this.weatherDtos = weatherDtos;
    }

    @Override
    public Double countAverageTemperature() {
        return weatherDtos.stream().mapToDouble((weatherDto) -> weatherDto.getTemperature()).average().getAsDouble();
    }

    @Override
    public Double countAverageHumidity() {
        return weatherDtos.stream().mapToDouble((weatherDto) -> weatherDto.getHumidity()).average().getAsDouble();
    }

    @Override
    public Double countAverageWindSpeed() {
        return weatherDtos.stream().mapToDouble((weatherDto) -> weatherDto.getWindSpeed()).average().getAsDouble();
    }

    @Override
    public WeatherDto findDtoWithMaxTemp() {
        Double maxTemp = weatherDtos.stream().mapToDouble((weatherDto) -> weatherDto.getTemperature()).max().getAsDouble();
        return weatherDtos.stream().filter((weatherDto) -> weatherDto.getTemperature().equals(maxTemp)).findFirst().get();
    }

    @Override
    public WeatherDto findDtoWithMinHumidity() {
        Double minHumidity = weatherDtos.stream().mapToDouble((weatherDto) -> weatherDto.getHumidity()).min().getAsDouble();
        return weatherDtos.stream().filter((weatherDto) -> weatherDto.getHumidity().equals(minHumidity)).findFirst().get();
    }

    @Override
    public WeatherDto findDtoWithMaxWindSpeed() {
        Double maxWindSpeed = weatherDtos.stream().mapToDouble((weatherDto) -> weatherDto.getWindSpeed()).max().getAsDouble();
        return weatherDtos.stream().filter((weatherDto) -> weatherDto.getWindSpeed().equals(maxWindSpeed)).findFirst().get();
    }

    @Override
    public String findCommonWindDirection() {

        List<String> directions = weatherDtos.stream().map((weatherDto) -> {
            double windDirection = weatherDto.getWindDirection();
            if (windDirection >= 315 || windDirection < 45) {
                return "N";
            } else if (windDirection >= 45 && windDirection < 135) {
                return "E";
            } else if (windDirection >= 135 && windDirection < 225) {
                return "S";
            } else {
                return "W";
            }
        }).collect(Collectors.toList());

        TreeMap<String, Long> map = new TreeMap<>();

        map.put("N", directions.stream().filter(direction -> direction.equals("N")).count());
        map.put("E", directions.stream().filter(direction -> direction.equals("E")).count());
        map.put("S", directions.stream().filter(direction -> direction.equals("S")).count());
        map.put("W", directions.stream().filter(direction -> direction.equals("W")).count());


        Collection<Long> values = map.values();
        Long maxValue = values.stream().mapToLong(x -> x).max().getAsLong();


        Set<String> keySet = map.keySet();
        return keySet.stream().filter(key -> map.get(key).equals(maxValue)).findFirst().get();
    }
}