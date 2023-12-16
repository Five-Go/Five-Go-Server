package com.fievgo.server.dto;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherConditionDto {
    private Integer strongWind; // 강풍
    private Integer turbulence; // 난기류
    private Integer thunderstorm; // 뇌우
    private Integer snow; // 눈
    private Integer rain; // 비
    private Integer lowTemperature; // 저기온
    private Integer lowVisibility; // 저시정
    private Integer altocumulus; // 적락운
    private String totalWeather;
    private String baseTime;

    public static WeatherConditionDto of(HashMap<String, String> weather) {
        return WeatherConditionDto.builder()
                .strongWind(Integer.parseInt(weather.get("UUU")))
                .snow(Integer.parseInt(weather.get("PTY")))
                .rain(Integer.parseInt(weather.get("PTY")))
                .lowTemperature(Integer.parseInt(weather.get("T1H")))
                .lowVisibility(Integer.parseInt(weather.get("VVV")))
                .baseTime(formatTime(weather.get("baseTime")))
                .build();
    }

    @Override
    public String toString() {
        return "WeatherConditionDto{" +
                "strongWind=" + strongWind +
                ", turbulence=" + turbulence +
                ", thunderstorm=" + thunderstorm +
                ", snow=" + snow +
                ", rain=" + rain +
                ", lowTemperature=" + lowTemperature +
                ", lowVisibility=" + lowVisibility +
                ", altocumulus=" + altocumulus +
                '}';
    }

    public void setTotalWeather() {
        String totalWeather = "";
        if (strongWind != null && strongWind > 0) {
            if (totalWeather.equals("")) {
                totalWeather += "강풍";
            } else {
                totalWeather += ", 강풍";
            }
        }

        if (turbulence != null && turbulence > 0) {
            if (totalWeather.equals("")) {
                totalWeather += "난기류";
            } else {
                totalWeather += ", 난기류";
            }
        }

        if (thunderstorm != null && thunderstorm > 0) {
            if (totalWeather.equals("")) {
                totalWeather += "뇌우";
            } else {
                totalWeather += ", 뇌우";
            }
        }

        if (snow != null && snow > 0) {
            if (totalWeather.equals("")) {
                totalWeather += "눈";
            } else {
                totalWeather += ", 눈";
            }
        }

        if (rain != null && rain > 0) {
            if (totalWeather.equals("")) {
                totalWeather += "비";
            } else {
                totalWeather += ", 비";
            }
        }

        if (lowTemperature != null && lowTemperature > 0) {
            if (totalWeather.equals("")) {
                totalWeather += "저기온";
            } else {
                totalWeather += ", 저기온";
            }
        }

        if (lowVisibility != null && lowVisibility > 0) {
            if (totalWeather.equals("")) {
                totalWeather += "저시정";
            } else {
                totalWeather += ", 저시정";
            }
        }

        if (altocumulus != null && altocumulus > 0) {
            if (totalWeather.equals("")) {
                totalWeather += "적락운";
            } else {
                totalWeather += ", 적락운";
            }
        }

        if (totalWeather.equals("")) {
            totalWeather = "맑음";
        }
        System.out.println("total" + totalWeather);
        this.totalWeather = totalWeather;
    }

    private static String formatTime(String inputTime) {
        if (inputTime.length() != 4) {
            System.out.println("유효하지 않은 입력 형식입니다.");
            return null;
        }

        String hours = inputTime.substring(0, 2);
        String minutes = inputTime.substring(2);

        return hours + ":" + minutes;
    }

    public Integer getTotalCondition() {
        int totalCondition = 0;
        int count = 0;
        if (strongWind != null && strongWind > 0) {
            totalCondition += strongWind;
            count++;
        }

        if (turbulence != null && turbulence > 0) {
            totalCondition += turbulence;
            count++;
        }

        if (thunderstorm != null && thunderstorm > 0) {
            totalCondition += thunderstorm;
            count++;
        }

        if (snow != null && snow > 0) {
            totalCondition += snow;
            count++;
        }

        if (rain != null && rain > 0) {
            totalCondition += rain;
            count++;
        }

        if (lowTemperature != null) {
            if (lowTemperature < 0) {
                lowTemperature *= -1;
            }
            totalCondition += lowTemperature / 5;
            count++;
        }

        if (lowVisibility != null) {
            if (lowVisibility < 0) {
                lowVisibility *= -1;
            }
            totalCondition += lowVisibility / 5;
            count++;
        }

        if (altocumulus != null) {
            totalCondition += altocumulus;
            count++;
        }
        return totalCondition / count;
    }
}
