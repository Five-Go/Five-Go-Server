package com.fievgo.server.dto;

import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherConditionDto {
    private Boolean strongWind; // 강풍
    private Boolean turbulence; // 난기류
    private Boolean thunderstorm; // 뇌우
    private Boolean snow; // 눈
    private Boolean rain; // 비
    private Boolean lowTemperature; // 저기온
    private Boolean lowVisibility; // 저시정
    private Boolean altocumulus; // 적락운
    private String totalWeather;
    private String baseTime;

    public static WeatherConditionDto of(HashMap<String, String> weather) {
        return WeatherConditionDto.builder()
                .strongWind(checkStrongWind(weather.get("UUU")))
                .snow(checkSnow(weather.get("PTY")))
                .rain(checkRain(weather.get("PTY")))
                .lowTemperature(checkLowTemperature(weather.get("T1H")))
                .lowVisibility(checkLowVisibility(weather.get("VVV")))
                .baseTime(formatTime(weather.get("baseTime")))
                .build();

    }

    private static Boolean checkStrongWind(String uuu) {
        boolean isStrongWind = false;
        if (Integer.parseInt(uuu) > 1) {
            isStrongWind = true;
        }
        return isStrongWind;
    }

    private static Boolean checkSnow(String pty) {
        boolean isSnow = false;
        List<String> list = List.of("2", "3", "7");
        if (list.contains(pty)) {
            isSnow = true;
        }
        return isSnow;
    }

    private static Boolean checkRain(String pty) {
        boolean isRain = false;
        List<String> list = List.of("1", "3", "4", "6", "7");
        if (list.contains(pty)) {
            isRain = true;
        }
        return isRain;
    }

    private static Boolean checkLowVisibility(String vvv) {
        boolean isLowVisibility = false;
        if (!vvv.equals("0")) {
            isLowVisibility = true;
        }
        return isLowVisibility;
    }

    private static Boolean checkLowTemperature(String t1h) {
        boolean isLowTemperature = false;
        if (Integer.parseInt(t1h) < 0) {
            isLowTemperature = true;
        }
        return isLowTemperature;
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
        if (strongWind != null && strongWind) {
            if (totalWeather.equals("")) {
                totalWeather += "강풍";
            } else {
                totalWeather += ", 강풍";
            }
        }

        if (turbulence != null && turbulence) {
            if (totalWeather.equals("")) {
                totalWeather += "난기류";
            } else {
                totalWeather += ", 난기류";
            }
        }

        if (thunderstorm != null && thunderstorm) {
            if (totalWeather.equals("")) {
                totalWeather += "뇌우";
            } else {
                totalWeather += ", 뇌우";
            }
        }

        if (snow != null && snow) {
            if (totalWeather.equals("")) {
                totalWeather += "눈";
            } else {
                totalWeather += ", 눈";
            }
        }

        if (rain != null && rain) {
            if (totalWeather.equals("")) {
                totalWeather += "비";
            } else {
                totalWeather += ", 비";
            }
        }

        if (lowTemperature != null && lowTemperature) {
            if (totalWeather.equals("")) {
                totalWeather += "저기온";
            } else {
                totalWeather += ", 저기온";
            }
        }

        if (lowVisibility != null && lowVisibility) {
            if (totalWeather.equals("")) {
                totalWeather += "저시정";
            } else {
                totalWeather += ", 저시정";
            }
        }

        if (altocumulus != null && altocumulus) {
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
}
