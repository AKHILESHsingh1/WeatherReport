# Weather Report 🌦️

This is a simple Java project that fetches real-time weather data using the **OpenWeatherMap API** and generates a weather report in an HTML file.

## 🔧 Tech Stack
- Java
- OpenWeatherMap API
- HTML
- org.json (for JSON parsing)

## 🚀 How It Works
1. Java fetches live weather data for a given city.
2. Parses the API response (temperature, humidity, condition).
3. Writes the output to `index.html` for browser viewing.

## 📦 How to Run
1. Add the [org.json library](https://github.com/stleary/JSON-java).
2. Replace `your_api_key` in `WeatherReport.java`.
3. Compile and run:
   ```bash
   javac -cp .:json.jar WeatherReport.java
   java -cp .:json.jar WeatherReport
