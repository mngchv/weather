public interface WeatherManager {
    Double countAverageTemperature();
    Double countAverageHumidity();
    Double countAverageWindSpeed();
    WeatherDto findDtoWithMaxTemp();
    WeatherDto findDtoWithMinHumidity();
    WeatherDto findDtoWithMaxWindSpeed();
    String findCommonWindDirection();
}