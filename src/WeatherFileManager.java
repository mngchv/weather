import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

//Класс, отвечающий за работу с файлом наблюдений
public class WeatherFileManager {
    private File srcFile;

    public WeatherFileManager(File srcFile) {
        this.srcFile = srcFile;
    }

    //Считать все наблюдения из файла
    public List<WeatherDto> readWeatherFromFile() throws Exception {
        List<WeatherDto> weatherDtos = new ArrayList<>();

        Scanner scanner = new Scanner(srcFile);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            if (isWeatherDto(parts)) {
                WeatherDto weatherDto = deserializeWeaterDto(parts);
                weatherDtos.add(weatherDto);
            }
        }

        return weatherDtos;
    }


    public boolean isWeatherDto(String[] parts) {
        String firstPart = parts[0];
        char ch = firstPart.charAt(0);
        return ch >= '0' && ch <= '9';
    }

    private WeatherDto deserializeWeaterDto (String[] parts) throws Exception {
        WeatherDto dto;
        WeatherDtoBuilder builder = new WeatherDtoBuilder();

        if (parts.length < 5) {
            throw new Exception("Parts length is " + parts.length);
        } else {
            String dateStr = parts[0];
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmm");


            Date trueDate = format.parse(dateStr);
            Double temperature = Double.parseDouble(parts[1]);
            Double humidity = Double.parseDouble(parts[2]);
            Double windSpeed = Double.parseDouble(parts[3]);
            Double windDirection = Double.parseDouble(parts[4]);


            dto = builder
                    .date(trueDate)
                    .temperature(temperature)
                    .humidity(humidity)
                    .windSpeed(windSpeed)
                    .windDirection(windDirection)
                    .build();
        }

        return dto;
    }


    public String[] readUnitsFromFile() throws Exception {
        String[] units = {};

        Scanner scanner = new Scanner(srcFile);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            //Первая часть должна быть равна "unit", если считываем единицы измерения
            if (parts[0].equals("unit")) {
                units = parts;
                break;
            }
        }

        return units;
    }
}