package org.openjfx.hellofx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker;

public class App extends Application {

	private Rectangle gamingLights;
	private FillTransition fillTransition;
	private List<Color> colorList = new ArrayList<>();
	private int currentMechanic = 1;
    private int maxMechanics = 9;
    private int currentStreak;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    primaryStage.initStyle(StageStyle.UNDECORATED);
	    primaryStage.setTitle("Rocket League Trainer");
	    
	    StackPane root = new StackPane();
	
	    // Set the window color
	    Color windowColor = Color.rgb(55, 55, 55);
	    BackgroundFill backgroundFill = new BackgroundFill(windowColor, null, null);
	    Background background = new Background(backgroundFill);
	    root.setBackground(background);
	
	    Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	    primaryStage.setScene(scene);
	
	    // Maximize the window
	    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    primaryStage.setX(screenBounds.getMinX());
	    primaryStage.setY(screenBounds.getMinY());
	    primaryStage.setWidth(screenBounds.getWidth());
	    primaryStage.setHeight(screenBounds.getHeight()); 
	
	    // Create application title text
	    Text header = new Text("Rocket League Trainer");
	    header.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    Color redColor = Color.rgb(207, 36, 36);
	    header.setFill(Color.RED);
	    header.setTextAlignment(TextAlignment.LEFT);
	    header.setTranslateX(10);
	    header.setTranslateY(10);
	
	    // Create slogan text
	    Text text = new Text("Road to Supersonic Legend");
	    text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    text.setFill(Color.WHITE);
	    text.setTextAlignment(TextAlignment.LEFT);
	    text.setTranslateX(10);
	    text.setTranslateY(36);
	
	    // Add the text to the top left of the screen
	    StackPane.setAlignment(header, Pos.TOP_LEFT);
	    StackPane.setAlignment(text, Pos.TOP_LEFT);
	    root.getChildren().add(header);
	    root.getChildren().add(text);
	 
	    // Load and display a video clip in the center of the screen
	    WebView webView = new WebView();
        webView.setTranslateY(-70);
        webView.setMaxWidth(650);
        webView.setMaxHeight(380);
        WebEngine webEngine = webView.getEngine();
        root.getChildren().add(webView);
        String videoURL = "https://www.youtube.com/embed/s3U6nRqXjiY";
        webEngine.load(videoURL);
        
	    // Create a ScrollPane for the Lorem Ipsum text
	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	    scrollPane.setTranslateY(180);
	    scrollPane.setMaxWidth(650);
	    scrollPane.setMaxHeight(90);
	    scrollPane.setStyle("-fx-background: #373737;-fx-background-color: transparent;");
	    root.getChildren().add(scrollPane);
	
	    // Create a Label for the Lorem Ipsum text
	    Mechanics mechanics = new Mechanics();
	    String descriptionContent = mechanics.getMechanicInformation("description", 1);
	
	    Label description = new Label(descriptionContent);
	    description.setTextFill(Color.WHITE);
	    description.setFont(Font.font("Arial", FontWeight.BOLD, 13));
	    description.setMaxWidth(650);
	    description.setWrapText(true);
	
	    // Add the Label to the ScrollPane
	    scrollPane.setContent(description);
	    
	    // Create a previous mechanic button
	    Button previousButton = new Button("<");
	    previousButton.getStyleClass().add("red-button");
	    previousButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    previousButton.setTranslateX(-310);
	    previousButton.setTranslateY(250);
	    root.getChildren().add(previousButton);
	    
	    // Create a next mechanic button
	    Button nextButton = new Button(">");
	    nextButton.getStyleClass().add("red-button");
	    nextButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    nextButton.setTranslateX(-125);
	    nextButton.setTranslateY(250);
	    root.getChildren().add(nextButton);
	    
	    // Create mechanic name text
	    Label mechanicName = new Label(mechanics.getMechanicInformation("name", 1));
	    mechanicName.setMaxWidth(155);
	    mechanicName.setAlignment(Pos.CENTER);
	    mechanicName.setTextFill(Color.WHITE);
	    mechanicName.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    mechanicName.setTranslateX(-218);
	    mechanicName.setTranslateY(250);
	    root.getChildren().add(mechanicName);
	    
	    // Create a decrease record button
	    Button decreaseButton = new Button("<");
	    decreaseButton.getStyleClass().add("yellow-button");
	    decreaseButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    decreaseButton.setTranslateX(125);
	    decreaseButton.setTranslateY(250);
	    root.getChildren().add(decreaseButton);
	    
	    // Create an increase record button
	    Button increaseButton = new Button(">");
	    increaseButton.getStyleClass().add("yellow-button");
	    increaseButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    increaseButton.setTranslateX(310);
	    increaseButton.setTranslateY(250);
	    root.getChildren().add(increaseButton);
	    
	    // Create mechanic level text
	    currentStreak = Integer.parseInt(mechanics.getMechanicInformation("streak", 1));
	    Label mechanicLevel = new Label("Record of " + currentStreak + "s");
	    mechanicLevel.setMinWidth(155);
	    mechanicLevel.setAlignment(Pos.CENTER);
	    mechanicLevel.setTextFill(Color.WHITE);
	    mechanicLevel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    mechanicLevel.setTranslateX(217);
	    mechanicLevel.setTranslateY(250);
	    root.getChildren().add(mechanicLevel);
	    
	    // Give the buttons their functionalities
	    previousButton.setOnAction(e -> {
	    	if (currentMechanic > 1) {
	    		currentMechanic--;
	    		mechanics.changeMechanic(currentMechanic, mechanicName, description);
	    		currentStreak = Integer.parseInt(mechanics.getMechanicInformation("streak", currentMechanic));
	    		if (currentMechanic > 1) {
	    			mechanicLevel.setText("Streak of " + currentStreak);
	    		}
	    		else {
	    			mechanicLevel.setText("Record of " + currentStreak + "s");
	    		}
	    	}
        });
	    nextButton.setOnAction(e -> {
	    	if (currentMechanic < maxMechanics) {
	    		currentMechanic++;
	    		mechanics.changeMechanic(currentMechanic, mechanicName, description);
	    		currentStreak = Integer.parseInt(mechanics.getMechanicInformation("streak", currentMechanic));
	    		mechanicLevel.setText("Streak of " + currentStreak);
	    	}
        });
	    decreaseButton.setOnAction(e -> {
			if (currentStreak > 1) {
	    		currentStreak--;
	    		mechanics.changeStreak(currentMechanic, currentStreak, mechanicLevel);
	    	}
        });
	    increaseButton.setOnAction(e -> {
	    	currentStreak++;
    		mechanics.changeStreak(currentMechanic, currentStreak, mechanicLevel);
        });
	    
	    // Add gaming lights to the bottom of the screen
	    gamingLights = new Rectangle(screenBounds.getWidth(), 4);
	    gamingLights.setFill(Color.RED);
	    gamingLights.setTranslateY(336);
	    root.getChildren().add(gamingLights);
	    
	    // Set the gaming lights' colors
	    colorList.add(Color.RED);
	    colorList.add(Color.LIGHTGREEN);
	    colorList.add(Color.YELLOW);
	    colorList.add(Color.CYAN);
	    colorList.add(Color.PINK);
	    
	    // Create a FillTransition for color animation
	    fillTransition = new FillTransition(Duration.seconds(5), gamingLights);
        fillTransition.setCycleCount(FillTransition.INDEFINITE);
        
     	// Create a timeline to change the color every 2 seconds
        Timeline colorChangeTimeline = new Timeline(
            new KeyFrame(Duration.seconds(5), e -> {
                updateRandomColor();
            })
        );
        colorChangeTimeline.setCycleCount(Timeline.INDEFINITE);
        colorChangeTimeline.play();
        
	    primaryStage.show();
	}
	
	private void updateRandomColor() {
        Color randomColor = getRandomColor();
        fillTransition.setToValue(randomColor);
        fillTransition.stop();
        fillTransition.play();
    }

	public static void main(String[] args) {
	    launch(args);
	}
	
	// Helper method to get a random color
	private Color getRandomColor() {
        Random random = new Random();
        int randomIndex = random.nextInt(colorList.size());
        return colorList.get(randomIndex);
    }
}
