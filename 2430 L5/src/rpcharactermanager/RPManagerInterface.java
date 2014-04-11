package rpcharactermanager;
import rpcharacter.*;
import rpinteract.*;
import error.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.TextField;
import java.awt.TextArea;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import utils.DieRoller;


/**
 * An RPCharacter Manager with GUI.
 * @author Maame Apenteng (0802637)
 * @version Nov/25/2013
 */
public class RPManagerInterface extends JFrame implements ActionListener, WindowListener{
    //constants
    private static Dimension dialog = new Dimension(500, 100);
    private static Dimension popup = new Dimension(500, 500);
    
    public RPCharacterManager info;
    private TextField searchKey;
    private TextArea viewChars;
    private TextArea viewWeapons;
    private TextArea viewSkills;
    private TextArea viewSpells;
    
    /**
     * Constructor that sets the up the Main Window
     */
    public RPManagerInterface(){
        info = new RPCharacterManager("data/rpdata.save");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);
        this.setMinimumSize(new Dimension(1000, 700));
        //main menu
        JMenuBar commands = new JMenuBar();
        commands.setOpaque(false);
        JMenu file = new JMenu("File");
        JMenuItem load = new JMenuItem("Load File");
        load.setActionCommand("load");
        load.addActionListener(new FileListener());
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new FileListener());
        JMenuItem squit = new JMenuItem("Save and Quit");
        squit.setActionCommand("Squit");
        squit.addActionListener(new FileListener());
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(this);
        file.add(load);
        file.add(save);
        file.add(squit);
        file.add(quit);
        commands.add(file);
        JMenu charMenu = new JMenu("Character");
        JMenu create = new JMenu("Create");
        JMenuItem character = new JMenuItem("New Character");
        character.setActionCommand("character");
        character.addActionListener(new NewWindowListener());
        JMenuItem skill = new JMenuItem("New Skill");
        skill.setActionCommand("skill");
        skill.addActionListener(new NewWindowListener());
        JMenuItem spell = new JMenuItem("New Spell");
        spell.setActionCommand("spell");
        spell.addActionListener(new NewWindowListener());
        JMenuItem weapon = new JMenuItem("New Weapon");
        weapon.setActionCommand("weapon");
        weapon.addActionListener(new NewWindowListener());
        create.add(character);
        create.add(skill);
        create.add(spell);
        create.add(weapon);
        JMenu delete = new JMenu("Delete");
        JMenuItem delchar = new JMenuItem("Character");
        delchar.setActionCommand("delete");
        delchar.addActionListener(new NewWindowListener());
        delete.add(delchar);
        JMenuItem delskill = new JMenuItem("Skill");
        delskill.setActionCommand("delskill");
        delskill.addActionListener(new NewWindowListener());
        delete.add(delskill);
        JMenuItem delspell = new JMenuItem("Spell");
        delspell.setActionCommand("delspell");
        delspell.addActionListener(new NewWindowListener());
        delete.add(delspell);
        JMenuItem delweapon = new JMenuItem("Weapon");
        delweapon.setActionCommand("delweapon");
        delweapon.addActionListener(new NewWindowListener());
        delete.add(delweapon);
        JMenuItem edit = new JMenuItem("Edit");
        edit.addActionListener(new NewWindowListener());
        JMenuItem sim = new JMenuItem("Simulation");
        sim.setActionCommand("Sim");
        sim.addActionListener(new NewWindowListener());
        charMenu.add(create);
        charMenu.add(edit);
        charMenu.add(delete);
        charMenu.add(sim);
        commands.add(charMenu);
        this.setJMenuBar(commands);
        //search
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        JLabel searchLabel = new JLabel("Enter Search Keys");
        searchPanel.add(searchLabel);
        searchKey = new TextField(20);
        searchPanel.add(searchKey);
        Button search = new Button("Search Characters");
        search.setActionCommand("Search");
        search.addActionListener(this);
        searchPanel.add(search);
        this.add(searchPanel, BorderLayout.NORTH);
        
        //enhancementPanel
        JPanel interact = new JPanel(new GridLayout(3, 1));
        //weaponPanel
        JPanel weapons = new JPanel(new BorderLayout());
        weapons.setBorder(BorderFactory.createEtchedBorder());
        JLabel weapLabel = new JLabel("Armoury");
        weapons.add(weapLabel, BorderLayout.NORTH);
        viewWeapons = new TextArea();
        loadWeapons();
        weapons.add(viewWeapons);
        Button reload = new Button("Refresh");
        reload.setActionCommand("weapons");
        reload.addActionListener(this);
        weapons.add(reload, BorderLayout.SOUTH);
        interact.add(weapons);
        //skillPanel
        JPanel skills = new JPanel(new BorderLayout());
        skills.setBorder(BorderFactory.createEtchedBorder());
        JLabel skillLabel = new JLabel("Skill Storage");
        skills.add(skillLabel, BorderLayout.NORTH);
        viewSkills = new TextArea();
        loadSkills();
        skills.add(viewSkills);
        Button relist = new Button("Refresh");
        relist.setActionCommand("skills");
        relist.addActionListener(this);
        skills.add(relist, BorderLayout.SOUTH);
        interact.add(skills);
        //spellPanel
        JPanel spells = new JPanel(new BorderLayout());
        spells.setBorder(BorderFactory.createEtchedBorder());
        JLabel spellLabel = new JLabel("Spellbook");
        spells.add(spellLabel, BorderLayout.NORTH);
        viewSpells = new TextArea();
        loadSpells();
        spells.add(viewSpells);
        Button renew = new Button("Refresh");
        renew.setActionCommand("spells");
        renew.addActionListener(this);
        renew.addActionListener(this);
        spells.add(renew, BorderLayout.SOUTH);
        interact.add(spells);
        this.add(interact, BorderLayout.EAST);
        //textArea
        JPanel characterPanel = new JPanel(new BorderLayout());
        viewChars = new TextArea();
        viewChars.setEditable(false);
        loadCharacters();
        characterPanel.add(viewChars);
        Button refresh = new Button("Refresh");
        refresh.setActionCommand("characters");
        refresh.addActionListener(this);
        characterPanel.add(refresh, BorderLayout.SOUTH);
        this.add(characterPanel);
        
    }
    
    /**
     * Loads all the characters into the viewCharacters TextArea
     */
    private void loadCharacters(){
        ArrayList<RPCharacter> characters = info.getCharacterList();
        if(characters == null || characters.isEmpty()){
            viewChars.setText("There are no characters to display.");
        }
        else{
            viewChars.setText("");
            for(RPCharacter s : characters){
                viewChars.append(s.toString());
                viewChars.append("\n");
            }
        }
        viewChars.repaint();
    }
    
    /**
     * Loads all the weapons into the viewWeapons TextArea
     */
    private void loadWeapons(){
        ArrayList<RPWeapon> weapons = info.getWeaponSet();
        if(weapons == null || weapons.isEmpty()){
            viewWeapons.setText("There are no weapons to display.");
        }
        else{
            viewWeapons.setText("");
            for(RPWeapon w : weapons){
                viewWeapons.append(w.getName());
            }
        }
        viewWeapons.repaint();
    }
    
    /**
     * Loads all the skills into the viewSkills TextArea
     */
    private void loadSkills(){
        ArrayList<RPSkill> skills = info.getSkillSet();
        if(skills == null || skills.isEmpty()){
            viewSkills.setText("There are no skills to display.");
        }
        else{
            viewSkills.setText("");
            for(RPSkill s : skills){
                viewSkills.append(s.getName());
                viewSkills.append("\n");
            }
        }
        viewSkills.repaint();
    }
    
    /**
     * Loads all the spells into the viewSpells TextArea
     */
    private void loadSpells(){
        ArrayList<RPSpell> spellbook = info.getSpellSet();
        if(spellbook == null || spellbook.isEmpty()){
            viewSpells.setText("There are no spells to display.");
        }
        else{
            viewSpells.setText("");
            for(RPSpell s : spellbook){
                viewSpells.append(s.getName());
            }
        }
        viewSpells.repaint();
    }
    
    /**
     * Launches a window that confirms whether the user wants to quit without saving
     */
    private void confirmWindow(){
        String[] options = {"Yes", "Save and Quit", "Cancel"};
          int response = JOptionPane.showOptionDialog(this, "Are you sure you want to quit without saving?", "Quit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
          if(response == JOptionPane.YES_OPTION){
              System.exit(0);
          }
          else if(response == JOptionPane.NO_OPTION){
              JOptionPane.showMessageDialog(this, info.finish(), "Done", JOptionPane.INFORMATION_MESSAGE);
              System.exit(0);
          }
          else if(response != JOptionPane.CANCEL_OPTION){
              JOptionPane.showMessageDialog(this, "Something went wrong!", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
          }
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equalsIgnoreCase("characters")){
            loadCharacters();
        }
        else if(e.getActionCommand().equalsIgnoreCase("skills")){
            loadSkills();
        }
        else if(e.getActionCommand().equalsIgnoreCase("weapons")){
            loadWeapons();
        }
        else if(e.getActionCommand().equalsIgnoreCase("spells")){
            loadSpells();
        }
        else if(e.getActionCommand().equalsIgnoreCase("Search")){
            viewChars.setText(info.search(searchKey.getText()));
        }
        else if(e.getActionCommand().equalsIgnoreCase("Quit")){
            confirmWindow();
        }
        else{
            JOptionPane.showMessageDialog(this, "Something strange happened!", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
    
    @Override
    public void windowOpened(WindowEvent e){
         info.readAll();
         loadCharacters();
         loadSkills();
         loadSpells();
         loadWeapons();
    }
    
    @Override
    public void windowClosing(WindowEvent e){
          confirmWindow();
    }
    
    @Override
    public void windowClosed(WindowEvent e){
        
    }
    
    @Override
    public void windowIconified(WindowEvent e){
        
    }
    
    @Override
    public void windowDeiconified(WindowEvent e){}
    
    @Override
    public void windowActivated(WindowEvent e){}
    
    @Override
    public void windowDeactivated(WindowEvent e){}
    
    /**
     * Window that allows for the creating and editing of characters
     */
    private class CharacterForm extends JFrame implements ActionListener{
        /**
         * <code>true</code> in edit mode
         * <code>false</code> in create mode
         */
        private boolean mode;
        private Form charInfo = new Form();
        private JComboBox select;
        private JComboBox weapons;
        private JComboBox skills;
        private JComboBox spells;
        private Button view;
        //TextArea viewEnhance;
        private JPanel viewEnhance;
        private TextArea listWeapons;
        private Button unequip;
        private boolean equip;
        private JPanel showSkills;
        private JPanel showSpells;
        private HashMap<JCheckBox, RPSkill> items;
        
        /**
         * Constructor that sets up the form
         * @param mode <code>true</code> in edit mode, <code>false</code> in create mode
         */
        public CharacterForm(boolean mode) {
            items = new HashMap();
            this.mode = mode;
            if(!mode){
                this.setMinimumSize(popup);
            }
            else{
                setMinimumSize(new Dimension(500, 650));
            }
            this.setLayout(new BorderLayout());
            JPanel form = new JPanel();
            //title label
            JLabel title = new JLabel();
            if(mode){
                title.setName("Edit");
            }
            else{
                title.setName("Create");
            }
            title.setLabelFor(form);
            form.add(title, BorderLayout.NORTH);
            //input panel
            JPanel input = new JPanel();
            input.setLayout(new BorderLayout());
            select = new JComboBox();
            if(mode){
                select.addItem("Select a Character");
                ArrayList<RPCharacter> characters = info.getCharacterList();
                if(characters != null && !characters.isEmpty()){
                    for(RPCharacter i : characters){
                        select.addItem(i.getName());
                    }
                }
            }
            else{
                select.addItem("Select a class");
                select.addItem("Bard");
                select.addItem("Wizard");
                select.addItem("Fighter");
            }
            
            select.addItemListener(new updateListener());
            input.add(select, BorderLayout.NORTH);
            JPanel fullForm = new JPanel(new GridLayout(2, 1));
            //input.add(charInfo);
            fullForm.add(charInfo);
            //enhance panel
            JPanel enhance = new JPanel(new BorderLayout());
            JPanel mini = new JPanel(new BorderLayout());
            view = new Button("View Equipped Enhancements");
            view.setActionCommand("View");
            view.addActionListener(this);
            view.setEnabled(false);
            mini.add(view, BorderLayout.NORTH);
            viewEnhance = new JPanel(new BorderLayout());
            //viewEnhance.setVisible(false);
            JPanel viewWeapons = new JPanel(new BorderLayout());
            listWeapons = new TextArea(2, 15);
            listWeapons.setEditable(false);
            unequip = new Button("Unequip");
            unequip.addActionListener(this);
            viewWeapons.add(listWeapons);
            viewWeapons.add(unequip, BorderLayout.EAST);
            viewEnhance.add(viewWeapons, BorderLayout.NORTH);
            JPanel skillPanel = new JPanel(new GridLayout(1,2));
            showSkills = new JPanel();
            showSkills.setLayout(new BoxLayout(showSkills, BoxLayout.PAGE_AXIS));
            showSkills.setBorder(BorderFactory.createEtchedBorder());
            JLabel viewSkill = new JLabel("Skills:");
            showSkills.add(viewSkill);
            skillPanel.add(showSkills);
            showSpells = new JPanel();
            showSpells.setLayout(new BoxLayout(showSpells, BoxLayout.PAGE_AXIS));
            JLabel viewSpell = new JLabel("Spells:");
            showSpells.add(viewSpell);
            showSpells.setBorder(BorderFactory.createEtchedBorder());
            skillPanel.add(showSpells);
            viewEnhance.add(skillPanel);
//            viewEnhance = new TextArea(10, 20);
//            viewEnhance.setEditable(false);
            viewEnhance.setVisible(false);
            mini.add(viewEnhance);
            enhance.add(mini);
            JPanel selection = new JPanel();
            selection.setLayout(new GridLayout(3,2));
            JLabel weapon = new JLabel("Equip Weapon");
            selection.add(weapon);
            weapons = new JComboBox();
            weapons.addItem("Select a weapon"); 
            ArrayList<RPWeapon> weaponSet = info.getWeaponSet();
            if(weaponSet != null && !weaponSet.isEmpty()){
                for(RPWeapon w : weaponSet){
                    weapons.addItem(w.getName());
                }
            }
            selection.add(weapons);
            JLabel skill = new JLabel("Give Skill");
            selection.add(skill);
            skills = new JComboBox();
            skills.addItem("Select a skill");
            ArrayList<RPSkill> skillSet = info.getSkillSet();
            if(skillSet != null && !skillSet.isEmpty()){
                for(RPSkill s : skillSet){
                    skills.addItem(s.getName());
                }
            }
            selection.add(skills);
            JLabel spell = new JLabel("Teach Spell");
            selection.add(spell);
            spells = new JComboBox();
            spells.addItem("Select a spell");
            ArrayList<RPSpell> spellSet = info.getSpellSet();
            if(spellSet != null && !spellSet.isEmpty()){
                for(RPSpell s: spellSet){
                    spells.addItem(s.getName());
                }
            }
            selection.add(spells);
            enhance.add(selection, BorderLayout.SOUTH);
            if(mode){
                //input.add(enhance, BorderLayout.SOUTH);
                fullForm.add(enhance);
            }
            input.add(fullForm);
            this.add(input);
            //button panel
            JPanel buttons = new JPanel();
            buttons.setLayout(new FlowLayout());
            Button go = new Button();
            if(mode){
                go.setLabel("Edit");
                go.setActionCommand("edit");
            }
            else{
                go.setLabel("Create");
                go.setActionCommand("create");
            }
            go.addActionListener(this);
            buttons.add(go);
            Button cancel = new Button("Cancel");
            cancel.addActionListener(this);
            buttons.add(cancel);
            this.add(buttons, BorderLayout.SOUTH);
            
        }
        public void refreshPanels(){
            showSkills.removeAll();
            JLabel viewSkill = new JLabel("Skills:");
            showSkills.add(viewSkill);
            showSpells.removeAll();
            JLabel viewSpell = new JLabel("Spells:");
            showSpells.add(viewSpell);
        }
        @Override
        public void actionPerformed(ActionEvent e){
            //int mode;
            if(e.getActionCommand().equalsIgnoreCase("create")){
                try{ 
                    boolean success;
                    HashMap<String, String> charInfo = this.charInfo.getInfo();
                    if(select.getSelectedItem().toString().equalsIgnoreCase("Bard")){
                        success = info.create(RPBard.mapToCharacter(charInfo));
                    }
                    else if(select.getSelectedItem().toString().equalsIgnoreCase("Fighter")){
                        success = info.create(RPFighter.mapToCharacter(charInfo));
                         
                    }
                    else if(select.getSelectedItem().toString().equalsIgnoreCase("Wizard")){
                        success = info.create(RPWizard.mapToCharacter(charInfo));
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Please select a character class!", "You goofed!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(success){
                        JOptionPane.showMessageDialog(this, "Character was succesfully created!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Something went wrong!", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                    }
                    this.dispose();
                }
                catch(InvalidNameException s){
                    JOptionPane.showMessageDialog(this, s.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                }
                catch(MissingParameterException m){
                    JOptionPane.showMessageDialog(this, m.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                }
                catch(IllegalArgumentException i){
                    JOptionPane.showMessageDialog(this, i.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                }
                catch(DuplicateException d){
                    JOptionPane.showMessageDialog(this, d.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                }
                
            }
            else if(e.getActionCommand().equalsIgnoreCase("edit")){
                HashMap<String, String> newInfo = charInfo.getInfo();
                RPCharacter toEdit = charInfo.getCurrent();
                String success = "Character has been sucessfully edited.";
                try{
                    if(toEdit instanceof RPBard){
                        RPBard edited = RPBard.mapToCharacter(newInfo);
                        success = "\n" + info.edit(toEdit, edited.getLastName(), edited.getFirstName(), edited.getAge(), edited.getLevel(), edited.getSpeed(), edited.getAbilities());

                        if(!skills.getSelectedItem().toString().equalsIgnoreCase("Select a skill") && skills.isEnabled()){
                            success += "\n" + info.giveSkill(toEdit, skills.getSelectedItem().toString());
                        }

                        if(!spells.getSelectedItem().toString().equalsIgnoreCase("Select a spell") && spells.isEnabled()){
                            success += "\n" + info.teachSpell(toEdit, spells.getSelectedItem().toString());
                        }
                        for(JCheckBox j : items.keySet()){ //forgets selected spells
                            if(j.isSelected()){
                                RPSkill s = items.get(j);
                                RPBard forget = (RPBard) toEdit;
                                if(s instanceof RPSpell){
                                    if(forget.forgetSpell((RPSpell)s)){
                                        success += "\n"+toEdit.getName()+" has forgotten "+s.getName();
                                    }
                                    else{
                                        success += "\n"+toEdit.getName()+" refuses to forget "+s.getName();
                                    }
                                    continue;
                                } //end of if its an RPSpell
                                if(forget.loseSkill(s)){
                                    success += "\n"+toEdit.getName()+" no longer has "+s.getName();
                                }
                                else{
                                    success += "\n"+toEdit.getName()+" holds onto "+s.getName();
                                }
                            } //end of if j is selected statement
                        } //end of for-each loop
                    }
                    else if(toEdit instanceof RPFighter){
                        RPFighter edited = RPFighter.mapToCharacter(newInfo);
                        success = "\n" + info.edit(toEdit, edited.getLastName(), edited.getFirstName(), edited.getAge(), edited.getLevel(), edited.getSpeed(), edited.getAbilities());
                        if(!skills.getSelectedItem().toString().equalsIgnoreCase("Select a skill") && skills.isEnabled()){
                            success += "\n" + info.giveSkill(toEdit, skills.getSelectedItem().toString());
                        }
                        if(!weapons.getSelectedItem().toString().equalsIgnoreCase("Select a weapon") && skills.isEnabled()){
                            success += "\n" + info.giveWeapon(toEdit, weapons.getSelectedItem().toString());
                        }
                        if(!equip){
                            RPFighter disarm = (RPFighter) toEdit;
                            disarm.unequipWeapon();
                        }
                        for(JCheckBox j : items.keySet()){
                            if(j.isSelected()){
                                RPSkill s = items.get(j);
                                RPFighter fight = (RPFighter) toEdit;
                                if(fight.loseSkill(s)){
                                    success += "\n"+toEdit.getName()+" no longer has "+s.getName();
                                }
                                else{
                                    success += "\n"+toEdit.getName()+" clings onto "+s.getName();
                                }
                            } //end of if j is selected
                        } //end of for-each select
                    } //end of if toEdit is a fighter
                    else if(toEdit instanceof RPWizard){
                        RPWizard edited = RPWizard.mapToCharacter(newInfo);
                        success = "\n" + info.edit(toEdit, edited.getLastName(), edited.getFirstName(), edited.getAge(), edited.getLevel(), edited.getSpeed(), edited.getAbilities());
                        if(!spells.getSelectedItem().toString().equalsIgnoreCase("Select a spell") && spells.isEnabled()){
                            success += "\n" + info.teachSpell(toEdit, spells.getSelectedItem().toString());
                        }
                        for(JCheckBox j : items.keySet()){ //forgets selected spells
                            if(j.isSelected()){
                                RPSkill s = items.get(j);
                                if(s instanceof RPSpell){
                                    RPWizard forget = (RPWizard) toEdit;
                                    if(forget.forgetSpell((RPSpell)s)){
                                        success += "\n"+toEdit.getName()+" has forgotten "+s.getName();
                                    }
                                    else{
                                        success += "\n"+toEdit.getName()+" refuses to forget "+s.getName();
                                    }
                                }
                            } //end of if j is selected statement
                        } //end of for-each loop
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Something went wrong!", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                        dispose();
                        return;
                    }
                    JOptionPane.showMessageDialog(this, success);
                    loadCharacters();
                    this.dispose();
                }
                catch(ElementNotFoundException n){
                    JOptionPane.showMessageDialog(this, n.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                }
                catch(InvalidNameException i){
                    JOptionPane.showMessageDialog(this, i.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                }
                catch(IllegalArgumentException i){
                    JOptionPane.showMessageDialog(this, i.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                }
                catch(MissingParameterException m){
                    JOptionPane.showMessageDialog(this, m.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("View")){
               view.setActionCommand("hide");
               view.setLabel("Hide Equipped Enhancements");
               viewEnhance.setVisible(true);
               RPCharacter current = charInfo.getCurrent();
               if(current instanceof RPWizard){
                   showSkills.setEnabled(false);
                   listWeapons.setVisible(false);
                   unequip.setVisible(false);
                   RPWizard wiz = (RPWizard) current;
                   if(wiz.getSpellSet().isEmpty()){
                         showSpells.setEnabled(false);
                   }
                   else{
                       for(RPSpell s : wiz.getSpellSet()){
                           JCheckBox item = new JCheckBox(s.getName());
                           items.put(item, s);
                           showSpells.add(item);
                           showSpells.repaint();
                       } //end of for-each loop
                   } //end of else statement
               } //end of if current is a wizard
               else if(current instanceof RPBard){
                   RPBard bard = (RPBard) current;
                   viewEnhance.setVisible(true);
                   listWeapons.setVisible(false);
                   unequip.setVisible(false);
                   if(bard.getSkillSet().isEmpty() && bard.getSpellSet().isEmpty()){
                       showSpells.setEnabled(false);
                   } //end of bard has no skills or spells
                  if(!bard.getSkillSet().isEmpty()){
                       for(RPSkill s : bard.getSkillSet()){
                           JCheckBox item = new JCheckBox(s.getName());
                           items.put(item, s);
                           showSkills.add(item);
                       } //end of for-each loop
                   } //end of if the bard has skills
                   else{
                       showSkills.setEnabled(false);
                   } //end of else statement
                   if(!bard.getSpellSet().isEmpty()){
                       for(RPSpell s : bard.getSpellSet()){
                           JCheckBox item = new JCheckBox(s.getName());
                           items.put(item, s);
                           showSpells.add(item);
                       } //end of for-each loop
                   } //if bard has spells
                   else{
                       showSpells.setEnabled(false);
                   } //end of else statement
               } //end of if current is a bard
               else if(current instanceof RPFighter){
                   RPFighter fight = (RPFighter) current;
                   listWeapons.setText(fight.getWeapon()+"\n");
                   listWeapons.setVisible(true);
                   if(!fight.isArmed()){
                       unequip.setEnabled(false);
                       unequip.setLabel("Nothing to unequip");
                       unequip.setVisible(true);
                   }
                   else{
                       unequip.setEnabled(true);
                       unequip.setLabel("Unequip");
                       unequip.setVisible(true);
                   }
                  if(!fight.getSkillSet().isEmpty()){
                      showSkills.setEnabled(true);
                       for(RPSkill s : fight.getSkillSet()){
                           JCheckBox item = new JCheckBox(s.getName());
                           items.put(item, s);
                           showSkills.add(item);
                       } //end of for each loop
                   } //end of if has skills
                   else{
                      showSkills.setEnabled(false);
                   } //end of else statement
               }//end of if current is a fighter
               this.validate();
            }
            else if(e.getActionCommand().equalsIgnoreCase("Hide")){
                view.setLabel("View Character Enhancements");
                view.setActionCommand("View");
                viewEnhance.setVisible(false);
            }
            else if(e.getActionCommand().equalsIgnoreCase("unequip")){
                equip = false;
                listWeapons.append("This weapon will be unequipped.");
            }
            else if(e.getActionCommand().equalsIgnoreCase("cancel")){
                this.dispose();
            }
        }
        
        /**
         * Inner form that contains fields for character details
         */
        private class Form extends JPanel implements ActionListener{
            private TextField forename;
            private TextField surname;
            private TextField age;
            private TextField level;
            private TextField speed;
            private TextArea printAbilities;
            private RPCharacter current;
            private HashMap<String, String> abilities;
            private Button generate;
            
            /**
             * Constructor that sets up the form
             */
            public Form(){
                this.setLayout(new GridLayout(2,1));
                //topGrid
                JPanel topGrid = new JPanel();
                topGrid.setLayout(new GridLayout(2, 1));
                //name
                JPanel nameInfo = new JPanel();
                nameInfo.setLayout(new FlowLayout());
                JLabel name = new JLabel("Name");
                forename = new TextField(20);
                forename.setName(RPCharacter.forenameKey);
                surname = new TextField(20);
                surname.setName(RPCharacter.surnameKey);
                nameInfo.add(name);
                nameInfo.add(forename);
                nameInfo.add(surname);
                topGrid.add(nameInfo);
                
                //number grid
                JPanel numberGrid = new JPanel();
                numberGrid.setLayout(new GridLayout(1,3));
                //age
                JPanel ageInfo = new JPanel();
                ageInfo.setLayout(new FlowLayout());
                JLabel ageLabel = new JLabel("Age");
                age = new TextField(5);
                age.setName(RPCharacter.ageKey);
                ageInfo.add(ageLabel);
                ageInfo.add(age);
                numberGrid.add(ageInfo);
                //level
                JPanel lvlInfo = new JPanel();
                lvlInfo.setLayout(new FlowLayout());
                JLabel lvlLbl = new JLabel("Level");
                level = new TextField(5);
                level.setName(RPCharacter.levelKey);
                lvlInfo.add(lvlLbl);
                lvlInfo.add(level);
                numberGrid.add(lvlInfo);
                //speed
                JPanel speedInfo = new JPanel();
                speedInfo.setLayout(new FlowLayout());
                JLabel speedLbl = new JLabel("Speed");
                speed = new TextField(5);
                speed.setName(RPCharacter.speedKey);
                speedInfo.add(speedLbl);
                speedInfo.add(speed);
                numberGrid.add(speedInfo);
                topGrid.add(numberGrid);
                //abilites
                JPanel genAbility = new JPanel();
                genAbility.setLayout(new BorderLayout());
                generate = new Button("Generate");
                generate.addActionListener(this);
                generate.setEnabled(false);
                abilities = new HashMap();
                printAbilities = new TextArea();
                printAbilities.setEditable(false);
                genAbility.add(generate, BorderLayout.NORTH);
                genAbility.add(printAbilities);
                
                this.add(topGrid);
                this.add(genAbility);
                current = null;
            }
            
            /**
             * Clears all the textField and textArea values
             */
            public void empty(){
                forename.setText("");
                surname.setText("");
                age.setText("");
                level.setText("");
                speed.setText("");
                abilities.clear();
                printAbilities.setText("");
                this.repaint();
            }
            
            /**
             * Fills in the textField and textArea values with character information
             * @param e the character that the information will be derived from
             */
            public void fill(RPCharacter e){
                current = e;
                forename.setText(e.getFirstName());
                surname.setText(e.getLastName());
                age.setText(Integer.toString(e.getAge()));
                level.setText(Integer.toString(e.getLevel()));
                speed.setText(Float.toString(e.getSpeed()));
                generate.setEnabled(true);
                abilities.putAll(current.getAbilities());
                refreshAbilities();
                this.repaint();
            }
            
            /**
             * Refreshes the character's abilities
             */
            public void refreshAbilities(){
                printAbilities.setText("\nCharisma: " + abilities.get(RPCharacter.charisma));
                printAbilities.append("\nConstitution: " + abilities.get(RPCharacter.constitution));
                printAbilities.append("\nDexterity: " + abilities.get(RPCharacter.dexterity));
                printAbilities.append("\nIntelligence: " + abilities.get(RPCharacter.intelligence));
                printAbilities.append("\nStrength: " + abilities.get(RPCharacter.strength));
                printAbilities.append("\nWisdom: " + abilities.get(RPCharacter.wisdom));
                printAbilities.repaint();
            }
            
            /**
             * 
             * @return the character whose details are being used for the form
             */
            public RPCharacter getCurrent(){
                return current;
            }
            
            /**
             * 
             * @return all the information filled into the form
             */
            public HashMap<String, String> getInfo(){
                HashMap<String, String> formInfo = new HashMap();
                formInfo.put(forename.getName(), forename.getText());
                formInfo.put(surname.getName(), surname.getText());
                formInfo.put(age.getName(), age.getText());
                formInfo.put(level.getName(), level.getText());
                formInfo.put(speed.getName(), speed.getText());
                formInfo.putAll(abilities);
                return formInfo;
            }
            
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getActionCommand().equalsIgnoreCase("Generate")){
                        abilities.put(RPCharacter.wisdom, Integer.toString(DieRoller.generateAbilityScoreStandard()));
                        abilities.put(RPCharacter.strength, Integer.toString(DieRoller.generateAbilityScoreStandard()));
                        abilities.put(RPCharacter.charisma, Integer.toString(DieRoller.generateAbilityScoreStandard()));
                        abilities.put(RPCharacter.dexterity, Integer.toString(DieRoller.generateAbilityScoreStandard()));
                        abilities.put(RPCharacter.intelligence, Integer.toString(DieRoller.generateAbilityScoreStandard()));
                        abilities.put(RPCharacter.constitution, Integer.toString(DieRoller.generateAbilityScoreStandard()));
                        refreshAbilities();
                }//end if generate
                else{
                    JOptionPane.showMessageDialog(this, "Something went wrong!", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                    dispose();
                    return;
                }
            }      
        }
        
        /**
         * Listener used for when a character is selected
         */
        private class updateListener implements ItemListener{
            @Override
            public void itemStateChanged(ItemEvent e){
                if(!mode){
                    return;
                }
                if(e.getStateChange() == ItemEvent.SELECTED){
                    view.setEnabled(true);
                    String name = e.getItem().toString();
                    if(name.equalsIgnoreCase("Select a character")){
                        charInfo.empty();
                    }
                    else{
                    charInfo.fill(info.find(name));
                        RPCharacter current = charInfo.getCurrent();
                        if(!(current instanceof RPFighter)){
                            weapons.addItem("Cannot equip weapon");
                            weapons.setSelectedItem("Cannot equip weapon");
                            weapons.setEnabled(false);
                        }
                        else{
                            weapons.removeItem("Cannot equip weapon");
                            weapons.setEnabled(true);
                            skills.removeItem("Cannot receive skills");
                            skills.setEnabled(true);
                            spells.addItem("Cannot learn spells");
                            spells.setSelectedItem("Cannot learn spells");
                            spells.setEnabled(false);
                        }
                        
                        if(current instanceof RPBard){
                            skills.removeItem("Cannot receive skills");
                            skills.setEnabled(true);
                            spells.removeItem("Cannot learn spells");
                            spells.setEnabled(true);
                        }
                        
                        if(current instanceof RPWizard){
                            skills.addItem("Cannot receive skills");
                            skills.setSelectedItem("Cannot receive skills");
                            skills.setEnabled(false);
                            spells.removeItem("Cannot learn spells");
                            spells.setEnabled(true);
                        }
                        refreshPanels();
                        viewEnhance.setVisible(false);
                        view.setLabel("View Equipped Enhancements");
                        view.setActionCommand("View");
                    }
                }
            }
        } //end of updateListener class
        
    } //end of characterForm class
    
    /**
     * Window that allows for the deleting of characters and enhancements
     */
    private class DeleteForm extends JFrame implements ActionListener{
        private ArrayList<JCheckBox> items;
        private ArrayList elements;
        private JPanel selectPanel;
        
        /**
         * Constructor that initializes the Window
         * @param elements the array list that the deleted items are from
         */
        public DeleteForm(ArrayList elements){
            this.setMinimumSize(popup);
            this.setLayout(new BorderLayout());
            this.elements = elements;
            items = new ArrayList();
            JPanel selectButton = new JPanel(new FlowLayout());
            Button none = new Button("Select None");
            none.setActionCommand("None");
            none.addActionListener(this);
            selectButton.add(none);
            Button all = new Button("Select All");
            all.setActionCommand("All");
            all.addActionListener(this);
            selectButton.add(all);
            this.add(selectButton, BorderLayout.NORTH);
            //bottom panel
            JPanel actionPanel = new JPanel(new FlowLayout());
            Button delete = new Button("Delete");
            delete.addActionListener(this);
            actionPanel.add(delete);
            Button deleteAll = new Button("Delete All Instances");
            if(elements.equals(info.getSkillSet())){
                deleteAll.setActionCommand("skillsDel");
                actionPanel.add(deleteAll);
            }
            else if(elements.equals(info.getSpellSet())){
                deleteAll.setActionCommand("spellsDel");
                actionPanel.add(deleteAll);
            }
            else if(elements.equals(info.getWeaponSet())){
                deleteAll.setActionCommand("weaponsDel");
                actionPanel.add(deleteAll);
            }
            deleteAll.addActionListener(this);
            Button cancel = new Button("Cancel");
            cancel.addActionListener(this);
            actionPanel.add(cancel);
            this.add(actionPanel, BorderLayout.SOUTH);
            //inner selection panel
            selectPanel = new JPanel();
            selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.PAGE_AXIS));
            JScrollPane deletePane = new JScrollPane(selectPanel);
            this.add(deletePane);
        }
        
        /**
         * <p><b>Note:</b> This must be called after the DeleteForm constructor.</p>
         * Fills in the items to be deleted.
         */
        public void run(){
            if(elements == null || elements.isEmpty()){
                JOptionPane.showMessageDialog(this, "There's nothing to delete!", "Sorry", JOptionPane.ERROR_MESSAGE);
                dispose();
                return;
            }
            if(elements.get(0) instanceof RPCharacter){
                ArrayList<RPCharacter> characters = (ArrayList<RPCharacter>) elements;
                for(RPCharacter c : characters){
                    JCheckBox item = new JCheckBox(c.getName());
                    items.add(item);
                    selectPanel.add(item);
                }
            }
            else if(elements.get(0) instanceof RPSpell){
                ArrayList<RPSpell> spells = (ArrayList<RPSpell>) elements;
                for(RPSpell s : spells){
                    JCheckBox item = new JCheckBox(s.getName());
                    items.add(item);
                    selectPanel.add(item);
                }
            }
            else if(elements.get(0) instanceof RPWeapon){
                ArrayList<RPWeapon> weapons = (ArrayList<RPWeapon>) elements;
                for(RPWeapon w: weapons){
                    JCheckBox item = new JCheckBox(w.getName());
                    items.add(item);
                    selectPanel.add(item);
                }
            }
            else if(elements.get(0) instanceof RPSkill){
                ArrayList<RPSkill> skills = (ArrayList<RPSkill>) elements;
                for(RPSkill s : skills){
                    JCheckBox item = new JCheckBox(s.getName());
                    items.add(item);
                    selectPanel.add(item);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Something went wrong!", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                dispose();
                return;
            }
            this.validate();
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            JFrame popup = new JFrame();
            popup.setMinimumSize(dialog);
            JLabel deleting = new JLabel("In the process of deleting items. Please wait.");
            popup.add(deleting);
            if(e.getActionCommand().equalsIgnoreCase("all")){
                for(JCheckBox i : items){
                    i.setSelected(true);
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("none")){
                for(JCheckBox i : items){
                    i.setSelected(false);
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("delete")){
                for(JCheckBox i : items){
                    if(i.isSelected()){
                        String message = "";
                       if(elements.equals(info.getCharacterList())){
                           message = info.deleteCharacter(i.getActionCommand());
                       }
                       else if(elements.equals(info.getSkillSet())){
                           int delete = JOptionPane.showConfirmDialog(this, "This will not delete all instances of this enhancement. This means any character that is equipped with this enhancement will continue to have this enhancement.", "Are you sure?", JOptionPane.OK_CANCEL_OPTION);
                           if(delete == JOptionPane.CANCEL_OPTION){
                               return;
                           }
                           else if(delete == JOptionPane.OK_OPTION){
                                message = info.deleteSkill(i.getActionCommand(), false);
                           }
                           else{
                               return;
                           }
                       }
                       else if(elements.equals(info.getSpellSet())){
                           int delete = JOptionPane.showConfirmDialog(this, "This will not delete all instances of this enhancement. This means any character that is equipped with this enhancement will continue to have this enhancement.", "Are you sure?", JOptionPane.OK_CANCEL_OPTION);
                           if(delete == JOptionPane.CANCEL_OPTION){
                               return;
                           }
                           else if(delete == JOptionPane.OK_OPTION){
                                message = info.deleteSpell(i.getActionCommand(), false);
                           }
                           else{
                               return;
                           }
                       }
                       else if(elements.equals(info.getWeaponSet())){
                           int delete = JOptionPane.showConfirmDialog(this, "This will not delete all instances of this enhancement. This means any character that is equipped with this enhancement will continue to have this enhancement.", "Are you sure?", JOptionPane.OK_CANCEL_OPTION);
                           if(delete == JOptionPane.CANCEL_OPTION){
                               return;
                           }
                           else if(delete == JOptionPane.OK_OPTION){
                                message = info.deleteWeapon(i.getActionCommand(), false);
                           }
                           else{
                               return;
                           }
                       }
                       else{
                           message = "Something went wrong!";
                           JOptionPane.showMessageDialog(this, message, "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                           dispose();
                           return;
                       }
                       JOptionPane.showMessageDialog(this, message, "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                loadCharacters();
                loadSkills();
                loadSpells();
                loadWeapons();
                dispose();
            }
            else if(e.getActionCommand().equalsIgnoreCase("skillsDel")){
                popup.setVisible(true);
                for(JCheckBox i : items){
                    if(i.isSelected()){
                        String message = info.deleteSkill(i.getActionCommand(), true);
                        JOptionPane.showMessageDialog(this, message, "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                popup.dispose();
                JOptionPane.showMessageDialog(this, "All items successfully deleted!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                loadSkills();
                dispose();
            }
            else if(e.getActionCommand().equalsIgnoreCase("spellsDel")){
                popup.setVisible(true);
                for(JCheckBox i : items){
                    if(i.isSelected()){
                        String message = info.deleteSpell(i.getActionCommand(), true);
                        JOptionPane.showMessageDialog(this, message, "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                popup.dispose();
                JOptionPane.showMessageDialog(this, "All items successfully deleted!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                loadSpells();
                dispose();
            }
            else if(e.getActionCommand().equalsIgnoreCase("weaponsDel")){
                popup.setVisible(true);
                for(JCheckBox i : items){
                    if(i.isSelected()){
                        String message = info.deleteWeapon(i.getActionCommand(), true);
                        JOptionPane.showMessageDialog(this, message, "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                popup.dispose();
                JOptionPane.showMessageDialog(this, "All items successfully deleted!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                loadWeapons();
                dispose();
            }
            else if(e.getActionCommand().equalsIgnoreCase("cancel")){
                dispose();
            }
            else{
                JOptionPane.showMessageDialog(this,"Something went wrong!", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } //end of actionPerformed method
        
    }
   
    /**
     * Window that allows for the editing of RPSkills and RPSpells
     */
    private class InteractForm extends JFrame implements ActionListener{
        
        private TextField itemName;
        private TextArea description;
        private TextArea mainAction;
        private TextArea checkFailed;
        private TextArea checkSucceeded;
        private TextArea finalOutcome;
        private JComboBox ability;
        private JComboBox counter;
        
        /**
         * Constructor that initializes the InteractForm GUI
         * @param mode <code>true</code> it's in RPSpell mode, <code>false</code> it's in RPSkill mode
         */
        public InteractForm(boolean mode){
            this.setLayout(new BorderLayout());
            this.setMinimumSize(popup);
            String title = "Create";
            if(mode){
                title += " New Spell";
            }
            else{
                title += " New Skill";
            }            
            JLabel formTitle = new JLabel(title);
            this.add(formTitle, BorderLayout.NORTH);
            //form
            JPanel form = new JPanel(new GridLayout(7, 1));
            //name
            JPanel namePanel = new JPanel(new BorderLayout());
            JLabel name = new JLabel("Name");
            itemName = new TextField(20);
            namePanel.add(name, BorderLayout.NORTH);
            namePanel.add(itemName);
            form.add(namePanel);
            //description
            JPanel descriptPanel = new JPanel(new BorderLayout());
            JLabel descrip = new JLabel("Description");
            description = new TextArea(5, 20);
            descriptPanel.add(descrip, BorderLayout.NORTH);
            descriptPanel.add(description);
            form.add(descriptPanel);
            //main action
            JPanel maPanel = new JPanel(new BorderLayout());
            JLabel action = new JLabel("Action");
            mainAction = new TextArea(5, 20);
            maPanel.add(action, BorderLayout.NORTH);
            maPanel.add(mainAction);
            form.add(maPanel);
            //abilities
            JPanel comboPanel = new JPanel(new GridLayout (2, 1));
            ability = new JComboBox();
            ability.addItem("Select Key Ability");
            ability.addItem("Charisma");
            ability.addItem("Constitution");
            ability.addItem("Dexterity");
            ability.addItem("Intelligence");
            ability.addItem("Strength");
            ability.addItem("Wisdom");
            comboPanel.add(ability);
            //counterabilities
            if(mode){
                counter = new JComboBox();
                counter.addItem("Select Counter Ability");
                for(int i = 1; i < ability.getItemCount(); i++){
                    counter.addItem(ability.getItemAt(i));
                }
                comboPanel.add(counter);
            }
            form.add(comboPanel);
            //checkfailed
            JPanel failurePanel = new JPanel(new BorderLayout());
            JLabel failed = new JLabel("Failure:");
            checkFailed = new TextArea(5, 20);
            failurePanel.add(failed, BorderLayout.NORTH);
            failurePanel.add(checkFailed);
            form.add(failurePanel);
            //checksucceeded
            JPanel successPanel = new JPanel(new BorderLayout());
            JLabel success = new JLabel("Success:");
            checkSucceeded = new TextArea(5, 20);
            successPanel.add(success, BorderLayout.NORTH);
            successPanel.add(checkSucceeded);
            form.add(successPanel);
            //finalOutcome
            JPanel finalPanel = new JPanel(new BorderLayout());
            JLabel outcome = new JLabel("Final Outcome:");
            finalOutcome = new TextArea(5, 20);
            finalPanel.add(outcome, BorderLayout.NORTH);
            finalPanel.add(finalOutcome);
            form.add(finalPanel);
            this.add(form);
            //buttons
            JPanel buttons = new JPanel(new FlowLayout());
            Button create = new Button("Create");
            if(mode){
                create.setActionCommand("Spell");
            }
            else{
                create.setActionCommand("Skill");
            }
            create.addActionListener(this);
            buttons.add(create);
            Button cancel = new Button("Cancel");
            cancel.addActionListener(this);
            buttons.add(cancel);
            this.add(buttons, BorderLayout.SOUTH);
        }
        
        /**
         * Converts the label on the JComboBox to the corresponding constant.
         * @param ability to be converted - label of selected item
         * @return RPCharacter constant that corresponds to the ability
         */
        protected String convertAbility(String ability){
            if(ability.equalsIgnoreCase("Charisma")){
                return RPCharacter.charisma;
            }
            else if(ability.equalsIgnoreCase("Constitution")){
                return RPCharacter.constitution;
            }
            else if(ability.equalsIgnoreCase("Dexterity")){
                return RPCharacter.dexterity;
            }
            else if (ability.equalsIgnoreCase("Intelligence")){
                return RPCharacter.intelligence;
            }
            else if(ability.equalsIgnoreCase("Strength")){
                return RPCharacter.strength;
            }
            else if(ability.equalsIgnoreCase("Wisdom")){
                return RPCharacter.wisdom;
            }
            else{
                throw new IllegalArgumentException("Something went wrong!");
            }
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getActionCommand().equalsIgnoreCase("cancel")){
                this.dispose();
                return;
            }
            try{
                boolean success = true;
                String output = "";
                if(e.getActionCommand().equalsIgnoreCase("spell")){
                    if(ability.getSelectedIndex() == 0 ){
                        JOptionPane.showMessageDialog(this, "Please select a key ability.", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(counter.getSelectedIndex() == 0 ){
                        JOptionPane.showMessageDialog(this, "Please select a counter ability.", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try{
                        success = info.create(convertAbility(counter.getSelectedItem().toString()), itemName.getText(), description.getText(), convertAbility(ability.getSelectedItem().toString()), mainAction.getText(), checkFailed.getText(), checkSucceeded.getText(), finalOutcome.getText()); 
                    }
                    catch(IllegalArgumentException i){
                        JOptionPane.showMessageDialog(this, i.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                }
                else if(e.getActionCommand().equalsIgnoreCase("skill")){
                    if(ability.getSelectedIndex() == 0 ){
                        JOptionPane.showMessageDialog(this, "Please select a key ability.", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    success = info.create(itemName.getText(), description.getText(), convertAbility(ability.getSelectedItem().toString()), mainAction.getText(), checkFailed.getText(), checkSucceeded.getText(), finalOutcome.getText()); 
                }
                if(success){
                    output = itemName.getText()+ " was added to database!";
                    JOptionPane.showMessageDialog(this, output, "Success!", JOptionPane.INFORMATION_MESSAGE);
                    loadSkills();
                    loadSpells();
                    this.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Something went wrong!", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                    this.dispose();
                }
            }
            catch(DuplicateException d){
                JOptionPane.showMessageDialog(this, d.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
            }
            catch(InvalidNameException i){
                JOptionPane.showMessageDialog(this, i.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Window that allows for the creation of RPWeapons
     */
    private class WeaponForm extends JFrame implements ActionListener{
        private TextField itemName;
        private JComboBox ability;
        private JSlider enhanceAmount;
        
        /**
         * Constructor that sets up the appearance of the window
         */
        public WeaponForm(){
            this.setMinimumSize(popup);
            this.setLayout(new BorderLayout());
            JLabel formTitle = new JLabel("Create New Weapon");
            this.add(formTitle, BorderLayout.NORTH);
            JPanel form = new JPanel();
            form.setLayout(new GridLayout(4, 2));
            JLabel name = new JLabel("Name");
            itemName = new TextField(20);
            form.add(name);
            form.add(itemName);
            //abilities
            JLabel keyAbility = new JLabel("Key Ability");
            form.add(keyAbility);
            ability = new JComboBox();
            ability.addItem("Charisma");
            ability.addItem("Constitution");
            ability.addItem("Dexterity");
            ability.addItem("Intelligence");
            ability.addItem("Strength");
            ability.addItem("Wisdom");
            form.add(ability);
            JLabel enhanceName = new JLabel("Enhancement");
            enhanceAmount = new JSlider(1, 7, 1);
            enhanceAmount.setLabelTable(enhanceAmount.createStandardLabels(1));
            enhanceAmount.setPaintLabels(true);
            enhanceAmount.setPaintTicks(true);
            form.add(enhanceName);
            form.add(enhanceAmount);
            this.add(form);
            JPanel buttons = new JPanel();
            buttons.setLayout(new FlowLayout());
            Button create = new Button("Create");
            create.addActionListener(this);
            buttons.add(create);
            Button cancel = new Button("Cancel");
            cancel.addActionListener(this);
            buttons.add(cancel);
            this.add(buttons, BorderLayout.SOUTH);
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getActionCommand().equalsIgnoreCase("cancel")){
                this.dispose();
            }
            else if(e.getActionCommand().equalsIgnoreCase("Create")){
                try{
                    String ability = this.ability.getSelectedItem().toString();
                    if(ability.equalsIgnoreCase("Charisma")){
                        ability = RPCharacter.charisma;
                    }
                    else if(ability.equalsIgnoreCase("Constitution")){
                        ability = RPCharacter.constitution;
                    }
                    else if(ability.equalsIgnoreCase("Dexterity")){
                        ability = RPCharacter.dexterity;
                    }
                    else if(ability.equalsIgnoreCase("Intelligence")){
                        ability = RPCharacter.intelligence;
                    }
                    else if(ability.equalsIgnoreCase("Strength")){
                        ability = RPCharacter.strength;
                    }
                    else if(ability.equalsIgnoreCase("Wisdom")){
                        ability = RPCharacter.wisdom;
                    }
                    if(info.create(itemName.getText(), ability, enhanceAmount.getValue())){
                        JOptionPane.showMessageDialog(this, itemName.getText() + " successfully created!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                        loadWeapons();
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Something went wrong!", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                    }
                    this.dispose();
                }
                catch(InvalidNameException i){
                    JOptionPane.showMessageDialog(this, i.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    /**
     * Window that allows for the running of simulations
     */
    private class SimForm extends JFrame implements ActionListener, WindowListener{
        /**
         * Outputs sim information
         */
        private TextArea output;
        /**
         * Constructor that sets up the Sim window
         */
        public SimForm(){
            setMinimumSize(popup);
            this.setLayout(new BorderLayout());
            output = new TextArea();
            output.setText("Starting sim...\n");
            output.setEditable(false);
            output.append("Selecting characters...\n");
            JPanel buttons = new JPanel(new FlowLayout());
            Button rerun = new Button("Rerun");
            rerun.addActionListener(this);
            buttons.add(rerun);
            Button cancel = new Button("Cancel");
            cancel.addActionListener(this);
            buttons.add(cancel);
            this.add(buttons, BorderLayout.SOUTH);
            this.add(output);
        }
        
        /**
         * Runs the simulation
         */
        protected void runSimulation(){
             try{
                RPCharacter [] pair = info.selectRandom();
                output.append("\n");
                output.append(info.startSim(pair[0], pair[1]));
                //open dialog to say selecting characters - closes once characters have been selected
                /* character 1 */
                int charClass = 0;
                ArrayList<RPSkill> skillSet = new ArrayList();
                ArrayList<RPSpell> spellSet = new ArrayList();
                String skillName = "";
                String spellName = "";
                if(pair[0] instanceof RPBard){
                    charClass = 1;
                    RPBard bard = (RPBard) pair[0];
                    skillSet = bard.getSkillSet();
                    spellSet = bard.getSpellSet();
                } //end of if pair[0] is a bard
                else if(pair[0] instanceof RPFighter){
                    charClass = 2;
                    RPFighter fight = (RPFighter) pair[0];
                    skillSet = fight.getSkillSet();
                }
                else if(pair[0] instanceof RPWizard){
                    charClass = 3;
                    RPWizard wiz = (RPWizard) pair[0];
                    spellSet = wiz.getSpellSet();
                }
                //window prompts user to select skills
                if(!skillSet.isEmpty()){
                    String [] skills = new String [skillSet.size()];
                    for(int i = 0; i < skillSet.size(); i++){
                        skills[i] = skillSet.get(i).getName();
                    }
                    skillName = (String) JOptionPane.showInputDialog(this, "Please select a skill", "Select a skill for simulation", JOptionPane.PLAIN_MESSAGE, null, skills, skills[0]);
                }
                if(!spellSet.isEmpty()){
                    String [] spells = new String [spellSet.size()];
                    for(int i = 0; i < skillSet.size(); i++){
                        spells[i] = spellSet.get(i).getName();
                    }
                    spellName = (String) JOptionPane.showInputDialog(this, "Please select a spell", "Select a spell for simulation", JOptionPane.PLAIN_MESSAGE, null, spells, spells[0]);
                }

                switch(charClass){
                    case 1:
                        output.append(info.simulate((RPBard) pair[0], skillName, spellName, pair[1]));
                        output.append("\n");
                        break;
                    case 2:
                        output.append(info.simulate((RPFighter) pair[0], skillName, pair[1]));
                        output.append("\n");
                        break;
                    case 3:
                        output.append(info.simulate((RPWizard) pair[0], skillName, pair[1]));
                        output.append("\n");
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Something went wrong!", "Uh-oh", JOptionPane.ERROR_MESSAGE);
                        dispose();
                        break;
                } //end of switch statement
                /* character 2 */
                charClass = 0;
                skillSet = new ArrayList();
                spellSet = new ArrayList();
                skillName = "";
                spellName = "";
                if(pair[1] instanceof RPBard){
                    charClass = 1;
                    RPBard bard = (RPBard) pair[1];
                    skillSet = bard.getSkillSet();
                    spellSet = bard.getSpellSet();
                } //end of if pair[0] is a bard
                else if(pair[1] instanceof RPFighter){
                    charClass = 2;
                    RPFighter fight = (RPFighter) pair[1];
                    skillSet = fight.getSkillSet();
                }
                else if(pair[1] instanceof RPWizard){
                    charClass = 3;
                    RPWizard wiz = (RPWizard) pair[1];
                    spellSet = wiz.getSpellSet();
                }
                //window prompts user to select skills
                if(!skillSet.isEmpty()){
                    String [] skills = new String [skillSet.size()];
                    for(int i = 0; i < skillSet.size(); i++){
                        skills[i] = skillSet.get(i).getName();
                    }
                    skillName = (String) JOptionPane.showInputDialog(this, "Please select a skill", "Select a skill for simulation", JOptionPane.PLAIN_MESSAGE, null, skills, skills[0]);
                }
                if(!spellSet.isEmpty()){
                    String [] spells = new String [spellSet.size()];
                    for(int i = 0; i < spellSet.size(); i++){
                        spells[i] = spellSet.get(i).getName();
                    }
                    spellName = (String) JOptionPane.showInputDialog(this, "Please select a spell", "Select a spell for simulation", JOptionPane.PLAIN_MESSAGE, null, spells, spells[0]);
                }
                   
                switch(charClass){
                    case 1:
                        output.append(info.simulate((RPBard) pair[1], skillName, spellName, pair[0]));
                        output.append("\n");
                        break;
                    case 2:
                        output.append(info.simulate((RPFighter) pair[1], skillName, pair[0]));
                        output.append("\n");
                        break;
                    case 3:
                        output.append(info.simulate((RPWizard) pair[1], skillName, pair[0]));
                        output.append("\n");
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Something went wrong!", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                        dispose();
                        break;
                } //end of switch statement
            }
            catch(MissingParameterException e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "Uh-oh!", JOptionPane.ERROR_MESSAGE);
                this.dispose();
            }
        } //end of runSimulation method
        
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getActionCommand().equalsIgnoreCase("Rerun")){
                runSimulation();
            }
            else if(e.getActionCommand().equalsIgnoreCase("Cancel")){
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(this, "Something weird has happened!", "Uh-oh!", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        @Override
        public void windowOpened(WindowEvent e){
            runSimulation();
        }
        
        @Override
        public void windowClosing(WindowEvent e){
        }

        @Override
        public void windowClosed(WindowEvent e){

        }

        @Override
        public void windowIconified(WindowEvent e){

        }

        @Override
        public void windowDeiconified(WindowEvent e){}

        @Override
        public void windowActivated(WindowEvent e){}

        @Override
        public void windowDeactivated(WindowEvent e){}
    } //end of SimForm class
    
    /**
     * Implements file-related actions
     */
    private class FileListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getActionCommand().equalsIgnoreCase("Load")){
                info.readAll();
                loadCharacters();
            }
            else if(e.getActionCommand().equalsIgnoreCase("Save")){
                info.saveAll();
            }
            else if(e.getActionCommand().equalsIgnoreCase("Squit")){
                JOptionPane.showMessageDialog(null, info.finish(), "Done", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    } //end of FileListener class
    
    /**
     * ActionListener that opens up a new window
     */
    private class NewWindowListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getActionCommand().equalsIgnoreCase("character")){
                CharacterForm create = new CharacterForm(false);
                create.setVisible(true);
            }
            else if(e.getActionCommand().equalsIgnoreCase("Edit")){
                CharacterForm edit = new CharacterForm(true);
                edit.setVisible(true);
            }
            else if(e.getActionCommand().equalsIgnoreCase("Weapon")){
                WeaponForm create = new WeaponForm();
                create.setVisible(true);
            }
            else if(e.getActionCommand().equalsIgnoreCase("Skill")){
                InteractForm create = new InteractForm(false);
                create.setVisible(true);
            }
            else if(e.getActionCommand().equalsIgnoreCase("Spell")){
                InteractForm create = new InteractForm(true);
                create.setVisible(true);
            }
            else if(e.getActionCommand().equalsIgnoreCase("Delete")){
                DeleteForm delete = new DeleteForm(info.getCharacterList());
                delete.setVisible(true);
                delete.run();
            }
            else if(e.getActionCommand().equalsIgnoreCase("DelSpell")){
                DeleteForm delete = new DeleteForm(info.getSpellSet());
                System.out.println(info.getSpellSet());
                delete.setVisible(true);
                delete.run();
            }
            else if(e.getActionCommand().equalsIgnoreCase("DelSkill")){
                DeleteForm delete = new DeleteForm(info.getSkillSet());
                delete.setVisible(true);
                delete.run();
            }
            else if(e.getActionCommand().equalsIgnoreCase("DelWeapon")){
                DeleteForm delete = new DeleteForm(info.getWeaponSet());
                delete.setVisible(true);
                delete.run();
            }
            else if(e.getActionCommand().equalsIgnoreCase("Sim")){
                SimForm simulate = new SimForm();
                simulate.setVisible(true);
                simulate.runSimulation();
            }
        } //end of actionPerformed
    } //end of newWindowListener class
   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RPManagerInterface program = new RPManagerInterface();
        program.setVisible(true);
    }
}
