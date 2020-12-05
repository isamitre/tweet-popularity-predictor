import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OutputFrame extends JFrame implements ActionListener {

    JButton retry = new JButton(); // this is the button that reads "Try Again!"

    OutputFrame(String tweet, String type) { // tweet is the tweet string that the user input to the text box, type is either "ordered" or "unordered" depending on the selection
        String score = "3.5";
        String influentialWordsString = "Dog, Cat, etc";
        String timeTakenString = "XXX Seconds";

        /*
        * Kian you can compute the score here because the "tweet" string has the tweet that the user inputs
        * Call your methods here and do whatever computations before the rest of the code in this constructor
        * Then update score, influentialWordsString, timeTakenString to their respective values that the algorithms calculate
        * the string "type" will either be "ordered" or "unordered"
        * Also delete this when you're done
        * */

        if (type == "ordered") {
            // calculate for ordered
        }
        if (type == "unordered") {
            // calculate for unordered
        }

        ImageIcon twitter = new ImageIcon("twitter.png"); // this is the image for the twitter logo used on the screen
        ImageIcon scaleImage = new ImageIcon("betterScale.png"); // this is the image for the scale of faces that can be seen on the screen
        // the next two images are scaled versions of the 2 images above to fit the screen
        ImageIcon smallTwitter = new ImageIcon(twitter.getImage().getScaledInstance(twitter.getIconWidth() / 5, twitter.getIconHeight() / 5, Image.SCALE_SMOOTH));
        ImageIcon smallScale = new ImageIcon(scaleImage.getImage().getScaledInstance(scaleImage.getIconWidth() / 5, scaleImage.getIconHeight() / 5, Image.SCALE_SMOOTH));
        JLabel jScale = new JLabel(); // this is the label that contains the image of the scale
        JLabel leftImage = new JLabel(); // this is the label that contains the upper left twitter logo and the title "Results"
        JLabel rightImage = new JLabel(); // this is the label that contains the upper right twitter logo
        JPanel panel = new JPanel(); // this is the panel that contains the upper 2 images
        // the rest of the labels are all different texts used in the frame
        JLabel text = new JLabel();
        JLabel text2 = new JLabel();
        JLabel text3 = new JLabel();
        JLabel timeTaken = new JLabel();
        JLabel influentialWords = new JLabel();
        JLabel tweetRating = new JLabel();
        JLabel scaleText = new JLabel();
        JLabel ratingNums = new JLabel();

        jScale.setIcon(smallScale); // sets the scale's image to the image of the scale
        jScale.setBounds(305, 300, 181, 50); // sets its position and size

        retry.setBounds(283, 375, 225, 50); // sets the retry button's position and size
        retry.addActionListener(this); // allows the button to perform a function when pressed
        retry.setFocusable(false); // this makes the text inside the button to not be surrounded by a text box
        retry.setText("Try Again!"); // sets the button's text

        /*
        * Lines 62 - 96 sets the text, font, size, and position of all of the different text labels in the frame
        */
        ratingNums.setText("0         2         4");
        ratingNums.setBounds(324, 275, 181, 25);
        ratingNums.setFont(new Font("SansSerif Bold", Font.BOLD, 20));

        scaleText.setText("Rating Scale");
        scaleText.setBounds(336, 225, 118, 50);
        scaleText.setFont(new Font("SansSerif Bold", Font.BOLD, 20));

        text.setText("Time Taken to Complete:");
        text.setFont(new Font("SansSerif Bold", Font.BOLD, 20));
        text.setBounds(20, 75, 260, 50);

        timeTaken.setText(timeTakenString);
        timeTaken.setFont(new Font("SansSerif Bold", Font.PLAIN, 20));
        timeTaken.setBounds(265, 75, 250, 50);

        text2.setText("Your Tweet Rating:");
        text2.setFont(new Font("SansSerif Bold", Font.BOLD, 20));
        text2.setBounds(20, 125, 190, 50);

        tweetRating.setText(score);
        tweetRating.setFont(new Font("SansSerif Bold", Font.PLAIN, 20));
        tweetRating.setBounds(210, 125, 200, 50);

        text3.setText("Most Influential Words:");
        text3.setFont(new Font("SansSerif Bold", Font.BOLD, 20));
        text3.setBounds(20, 175, 230, 50);

        influentialWords.setText(influentialWordsString);
        influentialWords.setFont(new Font("SansSerif Bold", Font.PLAIN, 20));
        influentialWords.setBounds(250, 175, 540, 50);

        leftImage.setIcon(smallTwitter);
        leftImage.setText("Results");
        leftImage.setFont(new Font("SansSerif Bold", Font.PLAIN, 50));

        rightImage.setIcon(smallTwitter); // this sets the top right image of the frame to the twitter logo

        panel.add(leftImage); // this adds the top left image as well as the title to the top of the page
        panel.add(rightImage); // this adds the top right image to the top of the page
        panel.setBackground(new java.awt.Color(137, 207, 240)); // this sets the page's background color to light blue

        this.setTitle("Tweet Popularity Predictor"); // sets the title of the frame
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // allows the frame to be closed upon pressing top right "x"
        this.setResizable(false); // prohibits the frame from being resized
        this.setSize(800, 500); // sets the size of the frame
        this.setVisible(true); // makes the frame visible
        // the rest of the code (lines 110 - 120) adds the components to the frame
        this.add(retry);
        this.add(text);
        this.add(text2);
        this.add(text3);
        this.add(timeTaken);
        this.add(influentialWords);
        this.add(tweetRating);
        this.add(jScale);
        this.add(scaleText);
        this.add(ratingNums);
        this.add(panel);
    }
    /*
    * This method is called when the "Try Again!" button is pressed
    * The method will create a new InputFrame object to allow the user to easily use the program again
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == retry) {
            InputFrame frame = new InputFrame();
        }
    }
}
