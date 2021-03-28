import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Loader {
    private static final String PATH_TO_FILE = "src/weather_table.csv";
    private static final String PATH_TO_OUTPUT_FILE = "src/output.txt";

    public static void main(String[] args) throws Exception {
        processWeatherInfoFromFile(PATH_TO_FILE, PATH_TO_OUTPUT_FILE);
    }

    public static void processWeatherInfoFromFile(String srcFilePath, String targetFilePath) throws Exception {
        WeatherFileManager fileManager = new WeatherFileManager(
                new File(Paths.get(srcFilePath).toUri())
        );
        List<WeatherDto> weatherDtoList = fileManager.readWeatherFromFile();
        WeatherManager weatherManager = new WeatherManagerImpl(weatherDtoList);

        File file = new File(targetFilePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        PrintWriter writer = new PrintWriter(file);

        String[] units = fileManager.readUnitsFromFile();

        writer.println("Средняя температура воздуха: " + weatherManager.countAverageTemperature() + " " + units[1]);
        writer.println("Средняя влажность: " + weatherManager.countAverageHumidity() + " " + units[2]);
        writer.println("Средняя скорость ветра: " + weatherManager.countAverageWindSpeed() + " " + units[3]);

        SimpleDateFormat format = new SimpleDateFormat("dd.MM, HH:mm");

        WeatherDto maxTempDto = weatherManager.findDtoWithMaxTemp();
        Date maxTempDtoDate = maxTempDto.getDate();
        writer.println("Самая высокая температура (" + maxTempDto.getTemperature() + " " + units[1] + ") была зафиксирована в " + format.format(maxTempDtoDate));

        WeatherDto minHumidity = weatherManager.findDtoWithMinHumidity();
        Date minHumidityDate = maxTempDto.getDate();
        writer.println("Самая низкая влажность (" + minHumidity.getHumidity() + " " + units[2] + ") была зафиксирована в " + format.format(minHumidityDate));

        WeatherDto maxWindSpeedDto = weatherManager.findDtoWithMaxWindSpeed();
        Date maxWindSpeedDtoDate = maxTempDto.getDate();
        writer.println("Самый сильный ветер (" + maxWindSpeedDto.getWindSpeed() + " " + units[3] + ") был зафиксирован в " + format.format(maxWindSpeedDtoDate));

        writer.println("Самое популярное направление ветра: " + weatherManager.findCommonWindDirection());

        writer.flush();
        writer.close();
    }
}