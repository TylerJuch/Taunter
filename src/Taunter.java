import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Taunter extends JFrame {
	static JFrame mainFrame = new JFrame("Taunter");
	
	static String defaultText = "Enter your text here. One entry per line.";
	
	static JButton startButton = new JButton("Start");
	static JButton stopButton = new JButton("Stop");
	static JButton loadPresetButton  = new JButton("Load Preset");
	static JLabel statusLabel = new JLabel("Inactive");
	static JTextArea textArea = new JTextArea(defaultText, 50,50);
	static JScrollPane textAreaScrollPane = new JScrollPane(textArea);
	
	static String[] quotesList;
	static Timer loopTimer;
    
    public static void main(String[] args) {
    	initMainUI();
    }
    
    private static void initMainUI() {
        mainFrame.setVisible(true);
        mainFrame.setSize(600,600);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new GridBagLayout());
        mainFrame.getContentPane().add(panel, BorderLayout.EAST);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(5,5,5,5);
        
        panel.add(statusLabel, c);
        statusLabel.setForeground(Color.RED);
        statusLabel.setFont(new Font(statusLabel.getFont().getName(), Font.BOLD, statusLabel.getFont().getSize()));
        
        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(startButton, c);
        c.gridy++;
        panel.add(stopButton, c);
        c.gridy++;
        panel.add(loadPresetButton, c);
        
        startButton.addActionListener(new Action());
        stopButton.addActionListener(new Action());
        loadPresetButton.addActionListener(new Action());
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 5;
        textArea.setColumns(40);
        textArea.setRows(30);
        panel.add(textAreaScrollPane, c);
    }
    
    static class Action implements ActionListener {
      	public void actionPerformed(ActionEvent e) {
      		if (e.getSource() == startButton) {
      			clipboardLoop();
      		}
      		else if (e.getSource() == stopButton) {
      			stopLoop();
      		}
      		else if (e.getSource() == loadPresetButton) {
      			initPresetUI();
      		}
      	}
    }
    
    static void initPresetUI() {
    	JFrame loadPresetFrame = new JFrame("Presets");
  		loadPresetFrame.setVisible(true);
  		loadPresetFrame.setSize(200,200);
  		
  		
  		JPanel panel = new JPanel();
  		loadPresetFrame.add(panel);
  		
  		JLabel label = new JLabel("Presets to go here");
  		panel.add(label);
    }
    
	static void clipboardLoop() {
    	statusLabel.setText("Active");
		statusLabel.setForeground(new Color(0,126, 0));
		textArea.setEditable(false);
		
		quotesList = textArea.getText().split("\n");
		
		ActionListener derp = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	int randomIndex = (int)(Math.random() * quotesList.length);
		    	setClipboard(quotesList[randomIndex]);
		    }
		};
		Timer loopTimer = new Timer(5000, derp);
		loopTimer.start();
    }
    
    static void setClipboard(String stringToMakeClipboardText) {
    	StringSelection ss = new StringSelection(stringToMakeClipboardText);
    	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
    }
    
    static void stopLoop() {
    	statusLabel.setText("Inactive");
			statusLabel.setForeground(Color.RED);
			textArea.setEditable(true);
			
			loopTimer.stop();
    }
}