/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package r1;

import java.io.*;
import java.net.URL;
import java.net.URLConnection; 
import java.util.Scanner;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.json.JSONArray;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import java.util.Properties;
/**
 *
 * @author akhil
 */
public class WeatherReport extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String reciept = request.getParameter("email");
      String city = request.getParameter("city");
      String access_key = "api_key";
    
      
      String apiUrl = "http://api.weatherstack.com/current?access_key=" + access_key+ "&query=" +city;
      
      URL url = new URL(apiUrl);
      URLConnection connection = url.openConnection();
      Scanner scanner = new Scanner(connection.getInputStream());
      String weatherData = "";
      while (scanner.hasNextLine()) {
          weatherData += scanner.nextLine();
      }
      scanner.close();
      
      out.println("<style>body {background-image: url('image.jpg');background-size: 100% 100%;} @media print{button, .noprint {display: none;}}</style>");

      
      
        final String username = "your_email@gmail.com";
        final String password = "your_password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "*");


        Session session = Session.getInstance(props,
          new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {
            
       String jsonstring = weatherData;  
        JSONObject json = new JSONObject(jsonstring);
        JSONObject drequest = json.getJSONObject("request");
        JSONObject location = json.getJSONObject("location");
        JSONObject current = json.getJSONObject("current");
        JSONArray icons = current.getJSONArray("weather_icons");

        
    out.println("<html><body><style>body background-size: 100% 100%;}table, th, td {border: 1px solid black;border-collapse: collapse;}th, td {background-color: #e5f1f1;border-radius: 3px;} table {position: relative; left: 50%;"
            + " transform: translate(-50%);width: 50vw; text-align: left;}</style>");
    out.println("<h1 style='text-align: center; widht: 100%;'>Weather Report for " + location.getString("name") + " <img src='"+icons.getString(0)+"' width='30' height='30'/></h1>");
    out.println("<table class='table'>");
    out.println("<tr><th>Type</th><td>" + drequest.getString("type") + "</td></tr>");
    out.println("<tr><th>Query</th><td>" + drequest.getString("query") + "</td></tr>");
    out.println("<tr><th>Language</th><td>" + drequest.getString("language") + "</td></tr>");
    out.println("<tr><th>Unit</th><td>" + drequest.getString("unit") + "</td></tr>");
    out.println("<tr><th>Name</th><td>" + location.getString("name") + "</td></tr>");
    out.println("<tr><th>Country</th><td>" + location.getString("country") + "</td></tr>");
    out.println("<tr><th>Region</th><td>" + location.getString("region") + "</td></tr>");
    out.println("<tr><th>Latitude</th><td>" + location.getString("lat") + "</td></tr>");
    out.println("<tr><th>Longitude</th><td>" + location.getString("lon") + "</td></tr>");
    out.println("<tr><th>Time</th><td>" + location.getString("localtime") + "</td></tr>");
    out.println("<tr><th>Observation time</th><td>" + current.getString("observation_time") + "</td></tr>");
    out.println("<tr><th>Temperature</th><td>" + current.getString("temperature") + "&deg C</td></tr>");
    out.println("<tr><th>Description</th><td>" + current.getString("weather_descriptions").replace("[\"", "").replace("\"]", "")+ "</td></tr>");
    out.println("<tr><th>Wind Speed</th><td>" + current.getString("wind_speed") + "</td></tr>");
    out.println("<tr><th>wind_degree</th><td>" + current.getString("wind_degree") + "</td></tr>");
    out.println("<tr><th>wind direction</th><td>" + current.getString("wind_dir") + "</td></tr>");
    out.println("<tr><th>Pressure</th><td>" + current.getString("pressure") + "</td></tr>");
    out.println("<tr><th>Precip</th><td>" + current.getString("precip") + "</td></tr>");
    out.println("<tr><th>Humidity</th><td>" + current.getString("humidity") + "</td></tr>");
    out.println("<tr><th>Cloudcover</th><td>" + current.getString("cloudcover") + "</td></tr>");
    out.println("<tr><th>feelslike</th><td>" + current.getString("feelslike") + "</td></tr>");
    out.println("<tr><th>UV Index</th><td>" + current.getString("uv_index") + "</td></tr>");
    out.println("<tr><th>Visibility</th><td>" + current.getString("visibility") + "</td></tr>");
    out.println("<tr><th>Is Day</th><td>" + current.getString("is_day") + "</td></tr>");
    out.println("</table>");
    
    out.println("<button style='color: #fff; border: none; background: #000;"
            + " font-size: 1.25rem; position: relative; left: 50%; transform: translate(-50%);"
            + "margin-top: 5px; padding: 5px 10px; border-radius: 5px;"
            + "'onclick='window.print()'>Print</button>");
    
    
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("facein.shak@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(reciept));
            message.setSubject("Weather Report of "+city);
            message.setText("-------Weather Report of "+city+"--------\n"
                    +" Name: " + location.getString("name")+"\n"+ " Country: " +location.getString("country")+"\n"
                    +" Region: " + location.getString("region")+"\n"+" Latitude: " + location.getString("lat")+"\n"+" Longitude: "+ location.getString("lon")+"\n"
                    +" Time: " + location.getString("localtime")+"\n"+" Temperature: " + current.getString("temperature")+"\n"+" Description: " + current.getString("weather_descriptions")+"\n"
                    +" Wind Speed: " + current.getString("wind_speed")+"\n"+" wind_degree: " + current.getString("wind_degree")+"\n"
                    +" wind direction: " + current.getString("wind_dir")+"\n"+" Pressure: " + current.getString("pressure")+"\n"
                    +" Precip: " + current.getString("precip")+"\n"+" Humidity: " + current.getString("humidity")+"\n"+" Cloudcover: " + current.getString("cloudcover")+"\n"
                    +" feelslike: " + current.getString("feelslike")+"\n"+" UV Index: " + current.getString("uv_index")+"\n" + " Visibility: " + current.getString("visibility")+"\n"
                    +" Is Day: " + current.getString("is_day"));

            Transport.send(message);

            out.println("<p class='noprint'>Sent mail successfully</p>");
    }
    catch (Exception e){
        e.printStackTrace();
        out.println(e.getMessage());
    }
  }
  }
      
  





