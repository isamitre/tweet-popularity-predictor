import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class InputFrame extends JFrame implements ActionListener{
	JButton unorderedButton = new JButton(); // this represents the button for the unordered map calculation
    JButton orderedButton = new JButton(); // this represents the button for the ordered map calculation
    JTextField textField = new JTextField(); // this represents the text box that the user types their tweet into

    private String inputTweet;

    public void setInputTweet(String tweet) {
        this.inputTweet = tweet;
    }
    public String getInputTweet() {
        return this.inputTweet;
    }

    InputFrame() { // constructor for the tweet input frame
        JLabel text = new JLabel(); // this is the text stating "choose your calculation method"
        JLabel warning = new JLabel(); // warns the user about the time the process will take
        ImageIcon logo = new ImageIcon("twitter.png"); // this is the container for the twitter logo image used
        JLabel leftTwitterLogo = new JLabel(); // this label is for the top left twitter logo image and the title
        JLabel rightTwitterLogo = new JLabel(); // this label is for the top right twitter logo image
        //the smallLogo represents a much smaller version of the "logo" image that is scaled to fit the screen at the desired size
        ImageIcon smallLogo = new ImageIcon(logo.getImage().getScaledInstance(logo.getIconWidth() / 5, logo.getIconHeight() / 5, Image.SCALE_SMOOTH));
        JPanel panel = new JPanel(); // this is a container for the elements at the top of the page to make it easier to display them

        leftTwitterLogo.setIcon(smallLogo); // this sets the top left twitter logo's image to the scaled twitter logo image
        leftTwitterLogo.setHorizontalAlignment(SwingConstants.LEFT); // this sets the label's horizontal position
        leftTwitterLogo.setVerticalAlignment(SwingConstants.TOP); // sets the label's vertical position
        leftTwitterLogo.setText("Tweet Popularity Predictor"); // this sets the label's text to the title seen at the top of the interface
        leftTwitterLogo.setFont(new Font("SansSerif Bold", Font.PLAIN, 50)); // this sets the title's font and its text size

        rightTwitterLogo.setIcon(smallLogo); // this sets the top right twitter logo's image to the scaled twitter logo image
        rightTwitterLogo.setHorizontalAlignment(SwingConstants.RIGHT); // sets the label's horizontal position
        rightTwitterLogo.setVerticalAlignment(SwingConstants.TOP); // sets the label's vertical position

        panel.add(leftTwitterLogo); // adds the left twitter logo image to the panel that is displayed on the UI
        panel.add(rightTwitterLogo); // adds the right twitter logo image to the panel
        panel.setBackground(new java.awt.Color(137, 207, 240)); // sets the panel's background color to light blue through RGB values

        text.setText("Choose Your Calculation Method");
        text.setBounds(300, 200, 200, 30); // this sets the text's position on the screen - (x position, y position, width, height)

        warning.setText("Calculation Process Could Take up to 3 Minutes");
        warning.setBounds(260, 325, 300, 30);

        orderedButton.setBounds(450, 250, 225, 50); // sets the ordered map calculation button's position
        orderedButton.addActionListener(this); // enables the button to be perform an operation
        orderedButton.setText("Ordered Map / Red-Black Tree"); // sets the text on the button
        unorderedButton.setFocusable(false); // sets the text on the button to not be enclosed in a text box
        unorderedButton.setBounds(125, 250, 225, 50); // sets button's position
        unorderedButton.addActionListener(this);
        unorderedButton.setText("Unordered Map / Hash Table");
        unorderedButton.setFocusable(false);
        textField.setBounds(100, 100, 600, 100); // sets the position and size of the text box the user can use to input their tweet

        this.setTitle("Tweet Popularity Predictor"); // sets the title of the actual frame
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // allows the "x" in top right corner of the frame to close the program if pressed
        this.setResizable(false); // makes the frame unable to be resized
        this.setSize(800, 400); // sets the size of the frame
        // add method adds the components to the frame
        this.add(unorderedButton);
        this.add(orderedButton);
        this.add(textField);
        this.add(text);
        this.add(warning);
        this.setIconImage(logo.getImage()); // sets the frame's icon image to the twitter logo
        this.add(panel);
        this.setVisible(true);
    }

    /*
    * The following actionPerformed method is called whenever a button is pressed, and checks which button has been pressed.
    * It will then create a new output frame that shows the user's tweet's results and gives the user a score.
    * The method also sets the tweet input into the text box as the "input tweet" that is then passed into the constructor of the output frame
    */

    @Override
    public void actionPerformed(ActionEvent e) { // this method is called whenever a button is pressed
        if (e.getSource() == unorderedButton) {
            setInputTweet(textField.getText());
            try {
				OutputFrame unordered = new OutputFrame(getInputTweet(), "unordered");
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        if (e.getSource() == orderedButton) {
            setInputTweet(textField.getText());
            try {
				OutputFrame ordered = new OutputFrame(getInputTweet(), "ordered");
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }
}
