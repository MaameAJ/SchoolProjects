package library_search;

import error.DuplicateException;
import library_search.Book;
import library_search.Journal;
import java.awt.HeadlessException;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.JList;
import javax.swing.JCheckBox;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;


/**
 * <p>Java application that allows users to add to a library and search it</p>
 * @author Maame Apenteng
 * @version Nov/30/2013
 */
public class LibrarySearchInterface extends JFrame implements ActionListener, WindowListener{
    /* CONSTANTS */
    private static String typeKey = "reference type";
    private static String call_num_key = "call number";
    private static String title_key = "title";
    private static String authorsKey = "authors";
    private static String publishKey = "publisher";
    private static String orgkey = "organization";
    private static String yearKey = "year";
    private static String keywordKey = "keywords";
    private static String minKey = "min year";
    private static String maxKey = "max year";
    
    /* INSTANCE VARIABLES */
    private JPanel message;
    private Color background = Color.lightGray;
    private LibrarySearch library;
    /**
     * Constructor that initializes the look of the program
     * @throws HeadlessException 
     */
    public LibrarySearchInterface() throws HeadlessException {
        super("Library Search");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(500, 500);
        this.addWindowListener(this);
        JMenu commands = new JMenu("Commands");
        JMenuItem add = new JMenuItem("Add");
        add.addActionListener(this);
        JMenuItem edit = new JMenuItem("Edit");
        edit.addActionListener(this);
        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(this);
        JMenuItem search = new JMenuItem("Search");
        search.addActionListener(this);
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(this);
        JMenuItem squit = new JMenuItem("Save and Quit");
        squit.setActionCommand("Squit");
        squit.addActionListener(this);
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(this);
        commands.add(add);
        commands.add(edit);
        commands.add(delete);
        commands.add(search);
        commands.add(save);
        commands.add(squit);
        commands.add(quit);
        JMenuBar main = new JMenuBar();
        main.add(commands);
        setJMenuBar(main);
        message = new JPanel();
        message.setLayout(new BorderLayout());
        WelcomeMessage welcome = new WelcomeMessage();
        message.add(welcome);
        this.add(message);
    }
    
    /**
     * Function that causes a confirmation window to pop up.
     */
    private void confirmWindow(){
        String[] options = {"Yes", "Save and Quit", "Cancel"};
          int response = JOptionPane.showOptionDialog(this, "Are you sure you want to quit without saving?", "Quit Without Saving", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
          if(response == JOptionPane.YES_OPTION){
              JOptionPane.showMessageDialog(this, "Have a nice day!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
              System.exit(0);
          }
          else if(response == JOptionPane.NO_OPTION){
              library.save();
              JOptionPane.showMessageDialog(this, "Have a nice day!", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
              System.exit(0);
          }
          else if(response != JOptionPane.CANCEL_OPTION){
              JOptionPane.showMessageDialog(this, "Something went wrong!", "Error!", JOptionPane.ERROR_MESSAGE);
          }
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        Container content = this.getContentPane();
        content.remove(message);
        if(e.getActionCommand().equalsIgnoreCase("add")){
            message = new BasicWindow(0); 
            content.add(message);
        }
        else if(e.getActionCommand().equalsIgnoreCase("edit")){
            message = new BasicWindow(2);
            content.add(message);
        }
        else if(e.getActionCommand().equalsIgnoreCase("search")){
            message = new BasicWindow(1);
            content.add(message);
        }
        else if(e.getActionCommand().equalsIgnoreCase("delete")){
            message = new BasicWindow(3);
            content.add(message);
        }
        else if(e.getActionCommand().equalsIgnoreCase("save")){
            library.save();
            JOptionPane.showMessageDialog(this, "Library has been saved!", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getActionCommand().equalsIgnoreCase("SQuit")){
            library.save();
            JOptionPane.showMessageDialog(this, "Have a nice day!", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        else if(e.getActionCommand().equalsIgnoreCase("quit")){
            confirmWindow();
        }
        //content.add(new BasicWindow());
        this.validate();
        
    }
    
    @Override
    public void windowDeactivated(WindowEvent e){
        
    }
    
    @Override
    public void windowActivated(WindowEvent e){}
    
    @Override
    public void windowDeiconified(WindowEvent e){
        
    }
    
    @Override
    public void windowIconified(WindowEvent e){}
    
    @Override
    public void windowOpened(WindowEvent e){
        JFileChooser open = new JFileChooser();
        File load = null, save = null;
        int result;
        do{
            //get file to load from
            do{
                result = open.showOpenDialog(this);
                if(result == JFileChooser.APPROVE_OPTION){
                    load = open.getSelectedFile();
                }
            }while(result != JFileChooser.APPROVE_OPTION);
            //get file to save to
            do{
                result = open.showSaveDialog(this);
                if(result == JFileChooser.APPROVE_OPTION){
                    save = open.getSelectedFile();
                }
            }while(result != JFileChooser.APPROVE_OPTION);
            //initialize library
            try{
                library = new LibrarySearch(load, save);
                return;
            }
            catch(FileNotFoundException i){
                JOptionPane.showMessageDialog(this, "Sorry: "+i.getMessage()+". Please select another file.", "Error Opening Files", JOptionPane.ERROR_MESSAGE);
            }
            catch(IOException i){
                JOptionPane.showMessageDialog(this, "Sorry: "+i.getMessage()+". Please select another file.", "Error Opening Files", JOptionPane.ERROR_MESSAGE);
            }
            catch(NoSuchElementException n){
                JOptionPane.showMessageDialog(this, "Sorry: Load file incorrectly formatted. Please select another file.", "Error Opening Files", JOptionPane.ERROR_MESSAGE);
            }
        }while(true);
    }
    
    @Override
    public void windowClosing(WindowEvent e){
        confirmWindow();
    }
    
    @Override
    public void windowClosed(WindowEvent e){}
    
    /**
     * The initial message when the program is opened
     */
    private class WelcomeMessage extends JTextArea{
        public WelcomeMessage(){
            super("\nWelcome to Library Search.\n");
            this.append("\nChoose a command from the \"Commands\" menu above for adding a reference, search references or quitting the program.");
            this.setBackground(background);
            this.setLineWrap(true);
            this.setWrapStyleWord(true);
            this.setEnabled(false);
            this.setDisabledTextColor(Color.black);
            
        }
    } //end of WelcomeMessage inner class
    
   
    
    /**
     * Frame for changing the library (adding/searching)
     */
    private class BasicWindow extends JPanel implements ActionListener{
        InputPanel inputPanel;
        JTextArea output;
        /**
         * @param mode determines how the BasicWindow will be loaded - 0 = add frame, 1 = search frame, 2 = edit frame
         */
        public BasicWindow(int mode){
            this.setLayout(new GridLayout(2, 1));
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BorderLayout());
            //set up topPanel
            JLabel textlabel;
            Button action;
            switch(mode){
                case 0:
                    inputPanel = new AddFrame();
                    textlabel = new JLabel("Messages");
                    action = new Button("Add"); 
                    break;
                case 1:
                    inputPanel = new SearchFrame();
                    textlabel = new JLabel("Search Results");
                    action = new Button("Search");
                    break;
                case 2:
                    inputPanel = new EditFrame();
                    textlabel = new JLabel("Messages");
                    action = new Button("Edit");
                    break;
                case 3:
                    inputPanel = new DeleteFrame();
                    textlabel = new JLabel("Messages");
                    action = new Button("Delete");
                    break;
                default:
                    textlabel = new JLabel("Output Panel");
                    action = new Button("Action");
                    break;
            }
            
            inputPanel.setBorder(BorderFactory.createEtchedBorder());
            topPanel.add(inputPanel);
            //set up output panel
            JPanel outputPanel = new JPanel();
            outputPanel.setLayout(new BorderLayout());outputPanel.add(textlabel, BorderLayout.NORTH);
            outputPanel.setBorder(BorderFactory.createEtchedBorder());
            output = new JTextArea();
            output.setEditable(false);
            JScrollPane outputWindow = new JScrollPane(output);
            outputPanel.add(outputWindow);
            //set up button panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(2, 1));
            Button reset = new Button("Reset");
            reset.addActionListener(this);
            buttonPanel.add(reset);
            action.addActionListener(this);
            buttonPanel.add(action);
            topPanel.add(buttonPanel, BorderLayout.EAST);
            //add components
            this.add(topPanel);
            this.add(outputPanel);
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getActionCommand().equalsIgnoreCase("Reset")){
                inputPanel.clear();
                output.setText("");
            }
            else if(e.getActionCommand().equalsIgnoreCase("Add")){
                try{
                    HashMap<String, String> info = inputPanel.getInfo();
                    boolean success = false;
                    if(info.get(typeKey).equalsIgnoreCase("book")){
                        success = library.add(info.get(call_num_key), info.get(title_key), Integer.parseInt(info.get(yearKey)), info.get(authorsKey), info.get(publishKey));
                    }
                    else if(info.get(typeKey).equalsIgnoreCase("journal")){
                        success = library.add(info.get(call_num_key), info.get(title_key), Integer.parseInt(info.get(yearKey)), info.get(orgkey));
                    }
                    if(success){
                        output.setForeground(Color.GREEN);
                        output.append("\nItem successfully created!\n");
                        output.setForeground(Color.BLACK);
                    }
                    else{
                         output.setForeground(Color.RED);
                         output.append("\nI'm sorry, your item could not be created. Some strange error occured.\n");
                         output.setForeground(Color.BLACK);
                    }
                }
                catch(NumberFormatException n){
                    output.setForeground(Color.RED);
                    output.append("\nError in the year field: "+n.getMessage()+"\n");
                    output.setForeground(Color.BLACK);
                }
                catch(IllegalArgumentException i){
                    output.setForeground(Color.RED);
                    output.append("\nI'm sorry, your item could not be created: "+ i.getMessage()+"\n");
                    output.setForeground(Color.BLACK);
                }
                catch(DuplicateException i){
                    output.setForeground(Color.RED);
                    output.append("\nI'm sorry, your item could not be created: "+ i.getMessage()+"\n");
                    output.setForeground(Color.BLACK);
                }
            } //end of if add button is clicked
            else if(e.getActionCommand().equalsIgnoreCase("Search")){
                try{
                    HashMap<String, String> info = inputPanel.getInfo();
                    int minimum = 1000;
                    int maximum = 9999;
                    if(!info.get(minKey).isEmpty()){
                        minimum = Integer.parseInt(info.get(minKey));
                    }
                    if(!info.get(maxKey).isEmpty()){
                        maximum = Integer.parseInt(info.get(maxKey));
                    }
                    output.append(library.search(info.get(call_num_key), minimum, maximum, info.get(keywordKey)));
                }
                catch(NumberFormatException n){
                    output.setForeground(Color.RED);
                    output.append("\n"+n.getMessage()+"\n");
                    output.setForeground(Color.BLACK);
                }
            } //end of if search button is clicked
            else if(e.getActionCommand().equalsIgnoreCase("Edit")){
                if(inputPanel instanceof EditFrame){
                    try{
                        EditFrame input = (EditFrame) inputPanel;
                        Reference toEdit = input.getSelected();
                        Reference edited = null;
                        HashMap<String, String> info = inputPanel.getInfo();
                        if(toEdit instanceof Book){
                            ArrayList<String> listAuthors = new ArrayList();
                            if(!info.get(authorsKey).isEmpty()){
                                StringTokenizer authors = new StringTokenizer(info.get(authorsKey));
                                while(authors.hasMoreTokens()){
                                    listAuthors.add(authors.nextToken(";").trim());
                                }
                            }  
                            edited = new Book(info.get(call_num_key), info.get(title_key), info.get(publishKey), listAuthors, Integer.parseInt(info.get(yearKey)));
                        }
                        else if(toEdit instanceof Journal){
                            edited = new Journal(info.get(call_num_key), info.get(title_key), info.get(orgkey), Integer.parseInt(info.get(yearKey)));
                        }
                        if(!toEdit.equals(edited)){
                            output.setText("\n"+library.edit(toEdit, edited)+"\n");
                            input.update();
                        }
                        else{
                            output.setForeground(Color.RED);
                            output.setText("\nThere are no edits to be made.\n");
                            output.setForeground(Color.BLACK);
                        }
                    }
                    catch(IllegalArgumentException i){
                        output.setForeground(Color.RED);
                        output.append("\nItem could not get edited: "+i.getMessage()+"\n");
                        output.setForeground(Color.BLACK);
                    }
                    catch(NoSuchElementException n){
                        output.setForeground(Color.RED);
                        output.append("\nItem could not get edited: "+n.getMessage()+"\n");
                        output.setForeground(Color.BLACK);
                    }
                    catch(NullPointerException n){
                        output.setForeground(Color.RED);
                        output.append("\nItem could not get edited: "+n.getMessage()+"\n");
                        output.setForeground(Color.BLACK);
                    }
                }//end of if InputPanel is an EditFrame
                else{
                    output.append("\nUnexpected Error.\n");
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("Delete")){
                if(inputPanel instanceof DeleteFrame){
                    try{
                        DeleteFrame input = (DeleteFrame) inputPanel;
                        output.setText(library.delete(input.getDeleted()));
                        input.update();
                    }
                    catch(NullPointerException n){
                        output.setForeground(Color.RED);
                        output.append(n.getMessage());
                        output.setForeground(Color.BLACK);
                    }
                }
                else{
                    output.setText("\nUnexpected Error.\n");
                }
            }//end of if action command equals Delete
        } //end of actionPerformed method for BasicWindow class
    } //end of BasicWindow class
    
    /**
     * Abstract class for the input panel
     */
    private abstract class InputPanel extends JPanel{
        /**
         * Clears all the fields in the InputPanel
         */
        abstract void clear();
        /**
         * Gets all the information contained in the InputPanel
         * @return HashMap containing all the information
         */
        abstract HashMap<String, String> getInfo();
    }
    
    
    /**
     * Input frame for adding references to the library
     */
    private class AddFrame extends InputPanel implements ItemListener{
        private boolean book;
        private JLabel add;
        private JPanel reference;
        private JComboBox type;
        private JPanel callNo;
        private JTextField callnum;
        private JPanel authorInfo;
        private JTextField authors;
        private JPanel titleInfo;
        private JTextField title;
        private JPanel pubInfo;
        private JTextField publisher;
        private JPanel orgInfo;
        private JTextField org;
        private JPanel yearInfo;
        private JTextField year;
        
        public AddFrame(){
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            add = new JLabel("Adding a reference");
            //set up type
            reference = new JPanel();
            reference.setLayout(new BoxLayout(reference, BoxLayout.LINE_AXIS));
            JLabel typeLbl = new JLabel("Type: ");
            type = new JComboBox();
            type.addItem("book");
            type.addItem("journal");
            type.addItemListener(this);
            type.setSelectedIndex(0);
            book = true;
            reference.add(typeLbl);
            reference.add(type);
            //set up call number
            callNo = new JPanel();
            callNo.setLayout(new BoxLayout(callNo, BoxLayout.LINE_AXIS));
            JLabel call_no = new JLabel("Call No:");
            callnum = new JTextField(20);
            callNo.add(call_no);
            callNo.add(callnum);
            //set up authors
            authorInfo = new JPanel();
            authorInfo.setLayout(new BoxLayout(authorInfo, BoxLayout.LINE_AXIS));
            JLabel authorLbl = new JLabel("Authors:");
            authors = new JTextField(20);
            authorInfo.add(authorLbl);
            authorInfo.add(authors);
            //set up title
            titleInfo = new JPanel();
            titleInfo.setLayout(new BoxLayout(titleInfo, BoxLayout.LINE_AXIS));
            JLabel titleLbl = new JLabel("Title:");
            title = new JTextField(20);
            titleInfo.add(titleLbl);
            titleInfo.add(title);
            //set up publisher
            pubInfo = new JPanel();
            pubInfo.setLayout(new BoxLayout(pubInfo, BoxLayout.LINE_AXIS));
            JLabel pubLbl = new JLabel("Publisher:");
            publisher = new JTextField(19);
            pubInfo.add(pubLbl);
            pubInfo.add(publisher);
            //set up organization
            orgInfo = new JPanel();
            orgInfo.setLayout(new BoxLayout(orgInfo, BoxLayout.LINE_AXIS));
            JLabel orgLbl = new JLabel("Organization: ");
            org = new JTextField(15);
            orgInfo.add(orgLbl);
            orgInfo.add(org);
            //set up year
            yearInfo = new JPanel();
            yearInfo.setLayout(new BoxLayout(yearInfo, BoxLayout.LINE_AXIS));
            JLabel yearLbl = new JLabel("Year:");
            year = new JTextField(4);
            yearInfo.add(yearLbl);
            yearInfo.add(year);
            reload();
        } //end of constructor
        
        /**
         * Refreshes the frame
         */
        private void reload(){
            this.removeAll();
            this.add(add);
            this.add(reference);
            this.add(callNo);
            if(book){
                this.add(authorInfo);
            }
            this.add(titleInfo);
            if(book){
                this.add(pubInfo);
            }
            else{
                this.add(orgInfo);
            }
            this.add(yearInfo);
            validate();
        }//end of reload method
        
        /**
         * @see InputPanel
         */
        @Override
        public void clear(){
            callnum.setText("");
            authors.setText("");
            title.setText("");
            publisher.setText("");
            org.setText("");
            year.setText("");
        }
        
        
        /**
         * @see InputPanel
         * @return 
         */
        @Override
        public HashMap<String, String> getInfo(){
            HashMap<String, String> info = new HashMap();
            info.put(call_num_key, callnum.getText());
            info.put(title_key, title.getText());
            info.put(yearKey, year.getText());
            if(book){
                info.put(typeKey, "book");
                info.put(authorsKey, authors.getText());
                info.put(publishKey, publisher.getText());
            }
            else{
                info.put(typeKey, "journal");
                info.put(orgkey, org.getText());
            }
            return info;
        }
        
        /**
         * Refreshes the page depending on what's selected in the JComboBox
         * @param e 
         */
        @Override
        public void itemStateChanged(ItemEvent e){
            if(e.getItem().toString().equalsIgnoreCase("book")){
                book = true;
                reload();
                clear();
            }
            else if(e.getItem().toString().equalsIgnoreCase("journal")){
                book = false;
                reload();
                clear();
            }
            else{
                //error message
            }
        } //end of if itemStateChanged method
    }
    
    /**
     * Input frame for searching the library
     */
    private class SearchFrame extends InputPanel{
        private JTextField callnum;
        private JTextField titleKey;
        private JTextField start;
        private JTextField end;
        
        public SearchFrame(){
            JLabel title = new JLabel("Searching references");
            this.add(title);
            //set up call number panel
            JPanel call_num = new JPanel();
            JLabel callNo = new JLabel("Call No:");
            callnum = new JTextField(15);
            call_num.add(callNo);
            call_num.add(callnum);
            this.add(call_num);
            //set up title keywords panel
            JPanel keywords = new JPanel();
            JLabel searchKeys = new JLabel("Title Keywords:");
            titleKey = new JTextField(20);
            keywords.add(searchKeys);
            keywords.add(titleKey);
            this.add(keywords);
            //set up year panel
            JPanel year = new JPanel(new GridLayout(2,2));
            JLabel startYear = new JLabel("Start Year:");
            start = new JTextField(4);
            JLabel endYear = new JLabel("End Year:");
            end = new JTextField(4);
            year.add(startYear);
            year.add(start);
            year.add(endYear);
            year.add(end);
            this.add(year);
            
        }
        
        /**
         * @see InputPanel
         */
        @Override
        public void clear(){
            callnum.setText("");
            titleKey.setText("");
            start.setText("");
            end.setText("");
        }
        
        /**
         * @see InputPanel
         * @return 
         */
        @Override
        public HashMap<String, String> getInfo(){
            HashMap<String, String> info = new HashMap();
            info.put(call_num_key, callnum.getText());
            info.put(keywordKey, titleKey.getText());
            info.put(minKey, start.getText());
            info.put(maxKey, end.getText());
            return info;
        }
    }
    
    /**
     * Input frame for editing references
     */
    private class EditFrame extends InputPanel implements ItemListener{
        private Reference selected;
        private JLabel edit;
        private JPanel reference;
        private JComboBox refSelect;
        private JPanel callNo;
        private JTextField callnum;
        private JPanel authorInfo;
        private JTextField authors;
        private JPanel titleInfo;
        private JTextField title;
        private JPanel pubInfo;
        private JTextField publisher;
        private JPanel orgInfo;
        private JTextField org;
        private JPanel yearInfo;
        private JTextField year;
        
        /**
         * Initializes all the components of the EditFrame
         */
        public EditFrame(){
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            edit = new JLabel("Editing a reference");
            //set up type
            reference = new JPanel();
            reference.setLayout(new BoxLayout(reference, BoxLayout.LINE_AXIS));
            refSelect = new JComboBox();
            refSelect.setRenderer(new ReferenceRenderer());
            ArrayList<Reference> catalogue = library.getLibrary();
            for(Reference i : catalogue){
                refSelect.addItem(i);
            }
            refSelect.setSelectedIndex(0);
            refSelect.addItemListener(this);
            selected = (Reference) refSelect.getSelectedItem();
            reference.add(refSelect);
            //set up call number
            callNo = new JPanel();
            callNo.setLayout(new BoxLayout(callNo, BoxLayout.LINE_AXIS));
            JLabel call_no = new JLabel("Call No:");
            callnum = new JTextField(20);
            callNo.add(call_no);
            callNo.add(callnum);
            //set up authors
            authorInfo = new JPanel();
            authorInfo.setLayout(new BoxLayout(authorInfo, BoxLayout.LINE_AXIS));
            JLabel authorLbl = new JLabel("Authors:");
            authors = new JTextField(20);
            authorInfo.add(authorLbl);
            authorInfo.add(authors);
            //set up title
            titleInfo = new JPanel();
            titleInfo.setLayout(new BoxLayout(titleInfo, BoxLayout.LINE_AXIS));
            JLabel titleLbl = new JLabel("Title:");
            title = new JTextField(20);
            titleInfo.add(titleLbl);
            titleInfo.add(title);
            //set up publisher
            pubInfo = new JPanel();
            pubInfo.setLayout(new BoxLayout(pubInfo, BoxLayout.LINE_AXIS));
            JLabel pubLbl = new JLabel("Publisher:");
            publisher = new JTextField(19);
            pubInfo.add(pubLbl);
            pubInfo.add(publisher);
            //set up organization
            orgInfo = new JPanel();
            orgInfo.setLayout(new BoxLayout(orgInfo, BoxLayout.LINE_AXIS));
            JLabel orgLbl = new JLabel("Organization: ");
            org = new JTextField(15);
            orgInfo.add(orgLbl);
            orgInfo.add(org);
            //set up year
            yearInfo = new JPanel();
            yearInfo.setLayout(new BoxLayout(yearInfo, BoxLayout.LINE_AXIS));
            JLabel yearLbl = new JLabel("Year:");
            year = new JTextField(4);
            yearInfo.add(yearLbl);
            yearInfo.add(year);
            reload();
        } //end of constructor
        
        /**
         * Sets up the EditFrame
         */
        private void reload(){
            this.removeAll();
            this.add(edit);
            this.add(reference);
            this.add(callNo);
            if(selected instanceof Book){
                this.add(authorInfo);
            }
            this.add(titleInfo);
            if(selected instanceof Book){
                this.add(pubInfo);
            }
            else if(selected instanceof Journal){
                this.add(orgInfo);
            }
            this.add(yearInfo);
            clear();
            validate();
        }
        
        /**
         * Refreshes the EditFrame
         * <p><b>Note:</b> This method currently causes an error with the Renderer.</p>
         */
        public void update(){
            ArrayList<Reference> catalogue = library.getLibrary();
            int index = catalogue.indexOf(selected);
            if(index < 0){
                index = 0;
            }
            refSelect.removeAllItems();
            for(int i = 0; i < catalogue.size(); i++){
                refSelect.addItem(catalogue.get(i));
                if(i == index){
                    refSelect.setSelectedItem(catalogue.get(i));
                }
            }
            
            selected = (Reference) refSelect.getSelectedItem();
            reload();
        }
        
        @Override
        public void itemStateChanged(ItemEvent e){
            if(e.getStateChange() == ItemEvent.SELECTED){
                selected = (Reference) refSelect.getSelectedItem();
                reload();
            }
        } //end of itemStateChanged
        
        @Override
        public void clear(){
            callnum.setText(selected.getCallNum());
            title.setText(selected.getTitle());
            year.setText(Integer.toString(selected.getYear()));
            if(selected instanceof Book){
                Book current = (Book) selected;
                String listAuthors = "";
                
                if(current.hasAuthors()){
                    ArrayList<String> gotAuthors = current.getAuthors();
                    for(int i = 0; i < gotAuthors.size(); i++){
                        listAuthors += gotAuthors.get(i);
                        if(i < (gotAuthors.size() - 1)){
                            listAuthors += ", ";
                        }
                    }//end of for loop
                }//end of current book has authors
                authors.setText(listAuthors);
                if(current.hasPublisher()){
                    publisher.setText(current.getPublish());
                }
                else{
                    publisher.setText("");
                }
            }//end of if selected is a book
            else if(selected instanceof Journal){
                Journal current = (Journal) selected;
                if(current.hasOrg()){
                    org.setText(current.getOrg());
                }
                else{
                    org.setText("");
                }
            }
        }//end of clear method
        
        public Reference getSelected(){
            return selected;
        } //end of getSelected method
        
        @Override
        public HashMap<String, String> getInfo(){
            HashMap<String, String> info = new HashMap();
            info.put(title_key, title.getText());
            info.put(call_num_key, callnum.getText());
            info.put(yearKey, year.getText());
            if(selected instanceof Book){
                info.put(authorsKey, authors.getText());
                info.put(publishKey, publisher.getText());
            }
            else if(selected instanceof Journal){
                info.put(orgkey, org.getText());
            }
            return info;
        }//end of getInfo
        
        private class ReferenceRenderer extends JLabel implements ListCellRenderer{
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                if(value instanceof Reference){
                    Reference selected = (Reference) value;
                    setText(selected.getCallNum()+"; "+selected.getTitle()+"; "+selected.getYear());
                }
                else if(value != null){
                    setText(value.toString());
                }

                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                } 
                else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }

                return this;
            }//end of getListCellRendererComponent method
        }//end of ReferenceRenderer class
    }//end of EditFrame class
    
    /**
     * Input frame for deleting references
     */
    private class DeleteFrame extends InputPanel{
        ArrayList<JCheckBox> options;
        ArrayList<Reference> items;
        JPanel selectPanel;
        
        /**
         * Initializes the Panel.
         * @throws NullPointerException if there are no references that can be deleted
         */
        public DeleteFrame() throws NullPointerException{
            options = new ArrayList();
            items = library.getLibrary();
            setLayout(new BorderLayout());
            selectPanel = new JPanel();
            selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.PAGE_AXIS));
            JScrollPane scroll = new JScrollPane(selectPanel);
            add(scroll);
            setUp();
        }
        
        /**
         * Sets up the panel with references to be deleted
         * @throws NullPointerException if there are no references that can be deleted
         */
        private void setUp() throws NullPointerException{
            if(items == null || items.isEmpty()){
                throw new NullPointerException("There is nothing to delete.");
            }
            for(Reference i : items){
                JCheckBox select = new JCheckBox(i.getCallNum()+"; "+i.getTitle()+"; "+i.getYear());
                options.add(select);
                selectPanel.add(select);
            }
            validate();
        }
        
        /**
         * Deselects all the references
         */
        @Override
        public void clear(){
            for(JCheckBox i : options){
                i.setSelected(false);
            }
        }//end of clear function
        
        /**
         * Used to when library information has changed to update the panel.
         * Calls setUp.
         * @throws NullPointerException if there are no references that can be deleted
         */
        protected void update() throws NullPointerException{
            selectPanel.removeAll();
            options.clear();
            items = library.getLibrary();
            setUp();
        } //end of reset function
        
        /**
         * @return all the references to be deleted
         */
        public ArrayList<Reference> getDeleted(){
            ArrayList<Reference> toDelete = new ArrayList();
            for(int i = 0; i < options.size(); i++){
                if(options.get(i).isSelected()){
                    toDelete.add(items.get(i));
                }//end of if option selected
            }//end of for loop
            return toDelete;
        }//end of getDeleted
        
        /**
         * @return null
         */
        @Override
        public HashMap<String, String> getInfo(){
            return null;
        }//end of getInfo function
        
    }//end of DeleteFrame class
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LibrarySearchInterface program = new LibrarySearchInterface();
        program.setVisible(true);
    }
}
