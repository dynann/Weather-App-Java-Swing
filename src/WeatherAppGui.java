import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui{

    private JSONObject weatherData;
    private JFrame weather;

    //contructor
    public WeatherAppGui(){
        weather = new JFrame();
        weather.setTitle("Weather App");
        weather.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        weather.setSize(450, 650);
        weather.setResizable(false);
        weather.setLocationRelativeTo(null);

    }

    //adding components
    private void components(){

        //search text box
        JTextField searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(300, 40));
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));


        //load image
        JLabel weatherConditionImage = new JLabel();
        int width = 130;
        int height = 130;
        ImageIcon weatherConditionImg = new ImageIcon("assets/clear.png");
        weatherConditionImg.setImage(weatherConditionImg.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        weatherConditionImage.setIcon(weatherConditionImg);




        //temperature text
        JLabel tempText = new JLabel("35 C");
        tempText.setFont(new Font("Dialog", Font.PLAIN, 43));
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //condition description
        JLabel weatherDes = new JLabel("Cloudy");
        weatherDes.setFont(new Font("Dialog", Font.PLAIN, 32));
        JPanel des_panel = new JPanel(new GridLayout(2, 1));
        des_panel.add(tempText);
        des_panel.add(weatherDes);


        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        searchPanel.setPreferredSize(new Dimension(450, 60));
        searchPanel.add(searchTextField);



        //adding weather icon






        //condtion panel
        JPanel conditionPanel = new JPanel(new BorderLayout());
        conditionPanel.add(weatherConditionImage, BorderLayout.CENTER);
        conditionPanel.add(des_panel, BorderLayout.SOUTH);
        //conditionPanel.add(weatherDes, BorderLayout.SOUTH);

        //center panel
        centerPanel.add(conditionPanel);


        // Humidity image and label
        JLabel humidityLabel = new JLabel();
        ImageIcon humidImg = new ImageIcon("assets/humidity.png");
        humidImg.setImage(humidImg.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        humidityLabel.setIcon(humidImg);
        JLabel humidityText = new JLabel("Humidity 100%");
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 18));

        // Windspeed image and label
        JLabel windSpeedLabel = new JLabel();
        ImageIcon windImg = new ImageIcon("assets/wind.png");
        windImg.setImage(windImg.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        windSpeedLabel.setIcon(windImg);
        JLabel windSpeedText = new JLabel("Windspeed 15km/h");
        windSpeedText.setFont(new Font("Dialog", Font.PLAIN, 18));

        // Bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(2, 2, 20, 10));
        bottomPanel.add(humidityLabel);

        bottomPanel.add(windSpeedLabel);
        bottomPanel.add(humidityText);
        bottomPanel.add(windSpeedText);




        //add button to next text field
        ImageIcon buttonI = new ImageIcon("assets/search.png");
        Image scaledImage = buttonI.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        buttonI = new ImageIcon(scaledImage);
       JButton searchButton = new JButton();
       searchButton.setIcon(buttonI);
       searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
       searchButton.setPreferredSize(new Dimension(100, 40));
       searchButton.setFocusable(false);
       searchButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

               //get location from the user
               String userInput = searchTextField.getText();

               //validate input remove whitespace
               if(userInput.replaceAll("\\s", "").length() <= 0){
                   return;
               }

               //retrieve weather data
               weatherData = Weather.getWeatherData(userInput);

               //update GUI
               //update weather IMG

               String weatherCondition = (String) weatherData.get("weather_condition");

               switch (weatherCondition){
                   case "Clear":
                       ImageIcon weatherConditionImg = new ImageIcon("assets/clear.png");
                       weatherConditionImg.setImage(weatherConditionImg.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                       weatherConditionImage.setIcon(weatherConditionImg);
                       break;
                   case "Cloudy":
                       weatherConditionImg = new ImageIcon("assets/cloudy.png");
                       weatherConditionImg.setImage(weatherConditionImg.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                       weatherConditionImage.setIcon(weatherConditionImg);
                       break;
                   case "Rain":
                       weatherConditionImg = new ImageIcon("assets/raining.png");
                       weatherConditionImg.setImage(weatherConditionImg.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                       weatherConditionImage.setIcon(weatherConditionImg);
                       break;
                   case "Snow":
                       weatherConditionImg = new ImageIcon("assets/snow.png");
                       weatherConditionImg.setImage(weatherConditionImg.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                       weatherConditionImage.setIcon(weatherConditionImg);
                       break;
               }

               double temperature = (double) weatherData.get("temperature");
               tempText.setText(temperature + " C");

               // update weather condition
               weatherDes.setText(weatherCondition);

               //update humidity
               long humidity = (long) weatherData.get("humidity");
               humidityText.setText("<html><b>Humidity<\b> " + humidity + "%</html>");

               //update windspeed text
               double windspeed = (double) weatherData.get("windspeed");
               windSpeedText.setText("<html><b>Windspeed<\b> " + windspeed + "km/h</html>");

           }
       });




        searchPanel.add(searchButton);

       //adding search panel to frame
       weather.getContentPane().setLayout(new BorderLayout());
       weather.getContentPane().add(searchPanel, BorderLayout.NORTH);
       weather.getContentPane().add(centerPanel, BorderLayout.CENTER);
       weather.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

    }

    public void display(){
        weather.setVisible(true);
        components();
    }



}