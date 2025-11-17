package sprint4.product;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A custom VBox containing two radio buttons with a shared toggle group.
 */
public class RadioButtonGroup extends VBox {

    private ToggleGroup toggleGroup = new ToggleGroup();
    private RadioButton rb1;
    private RadioButton rb2;
    
    /**
     * Creates a RadioButtonGroup with two radio buttons labeled as specified. 
     * Default vertical layout.
     * 
     * @param label1 the label for the first radio button
     * @param label2 the label for the second radio button
     */
    public RadioButtonGroup(String label1, String label2) {
    	this(label1, label2, true);
    }
    

    /**
     * Creates a RadioButtonGroup with two radio buttons labeled as specified.
     *
     * @param label1 the label for the first radio button
     * @param label2 the label for the second radio button
     * @param isVertical true for vertical layout, false for horizontal layout
     */
    public RadioButtonGroup(String label1, String label2, boolean isVertical) {
        super(10); // spacing between buttons
        
        rb1 = new RadioButton(label1);
        rb2 = new RadioButton(label2);
        
        rb1.setToggleGroup(toggleGroup);
        rb2.setToggleGroup(toggleGroup);
        
        if (!isVertical) {
            // Create an HBox to hold the buttons horizontally
            HBox horizontalContainer = new HBox(10);
            horizontalContainer.getChildren().addAll(rb1, rb2);
            this.getChildren().add(horizontalContainer);
          } else {
            // Vertical adds buttons directly to this VBox
            this.getChildren().addAll(rb1, rb2);
          }
    }
    
    /**
     * Gets the text of the currently selected radio button.
     * 
     * @return the text of the selected button, or null if nothing is selected
     */
    public String getSelectedButton() {
    	RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle(); //type cast generic Toggle object to RadioButton object
    	return (selected != null) ? selected.getText() : null;
    }
    
    /**
     * Gets the first button in the group.
     * 
     * @return the first button in the group
     */
    public RadioButton getFirstButton() {
    	return rb1;
    }
    
    /**
     * Gets the second button in the group.
     * 
     * @return the second button in the group
     */
    public RadioButton getSecondButton() {
    	return rb2;
    }
    
    /**
     * Sets the first radio button as selected by default.
     */
    public void selectFirst() {
    	rb1.setSelected(true);
    }
}
