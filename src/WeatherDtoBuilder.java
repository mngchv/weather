import java.util.Date;

public class WeatherDtoBuilder {
    private static WeatherDto weatherDto;

    public WeatherDtoBuilder() {
        weatherDto = new WeatherDto();
    }

    public WeatherDto build() {
        return weatherDto;
    }

    public WeatherDtoBuilder date(Date date) {
        weatherDto.setDate(date);
        return this;
    }

    public WeatherDtoBuilder temperature(Double temperature) {
        weatherDto.setTemperature(temperature);
        return this;
    }

    public WeatherDtoBuilder humidity(Double humidity) {
        weatherDto.setHumidity(humidity);
        return this;
    }

    public WeatherDtoBuilder windSpeed(Double windSpeed) {
        weatherDto.setWindSpeed(windSpeed);
        return this;
    }

    public WeatherDtoBuilder windDirection(Double windDirection) {
        weatherDto.setWindDirection(windDirection);
        return this;
    }
}