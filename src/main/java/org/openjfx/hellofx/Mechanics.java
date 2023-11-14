package org.openjfx.hellofx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;

public class Mechanics {
    public String getMechanicInformation(String property, int mechanicNumber) {
        try {
            // Load the JSON file
        	ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("res/mechanics.json");
            JsonNode rootNode = objectMapper.readTree(jsonFile);

            // Navigate to mechanic number x and extract the x property
            JsonNode thirdMechanic = rootNode.get("mechanics").get(mechanicNumber-1);
            String information = thirdMechanic.get(property).asText();
            return information;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
    }

	@SuppressWarnings("exports")
	public void changeMechanic(int mechanicNumber, Label mechanicName, Label description) {
		mechanicName.setText(getMechanicInformation("name", mechanicNumber));
		description.setText(getMechanicInformation("description", mechanicNumber));
	}
	
	@SuppressWarnings("exports")
	public void changeStreak(int mechanicNumber, int newStreakNumber, Label mechanicLevel) {
		File jsonFile = new File("res/mechanics.json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty-printing

        try {
            // Read the JSON file
            JsonNode root = mapper.readTree(jsonFile);

            // Update the streak of the third mechanic to 20
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            ((ObjectNode) root.get("mechanics").get(mechanicNumber-1)).put("streak", newStreakNumber);

            // Write the updated JSON back to the file
            writer.writeValue(jsonFile, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Update the streak text GUI
        if (mechanicNumber > 1) {
        	mechanicLevel.setText("Streak of " + newStreakNumber);
        }
        else {
        	mechanicLevel.setText("Record of " + newStreakNumber + "s");
        }
	}
}
