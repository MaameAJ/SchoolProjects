# -*- coding: utf-8 -*-

"""
fileviewpanel.py - collection of subclasses for the GUI
Last updated:  April 06, 2014 

Author: Maame Apenteng #0802637
Contact: mapenten@uoguelph.ca
"""

from tkinter import *
from multilistbox import MultiListbox
from tkinter.messagebox import *
import mysql.connector

class FileViewPanel(MultiListbox):
  def __init__(self, master, selectM, lists, cvp, updateCVP, menu):
    MultiListbox.__init__(self, master, selectM, lists)
    self.updateCVP = updateCVP
    self.cvp = cvp
    self.menu = menu
    
  def insert(self, index, *elements):
    for e in elements:
      i = 0
      for l in self.lists:
        l.insert(index,e[i])
        if(i == 6):
          if("M" in e[i]):
            l.itemconfig(END,{'bg':'red'})
          elif("C" in e[i]):
            l.itemconfig(END,{'bg':'green'})
          else:
            l.itemconfig(END,{'bg':'yellow'})
        i = i + 1
        
  def selection_set(self, first, last=None):
    MultiListbox.selection_set(self, first, last)
    self.updateCVP(self.cvp, self.curselection())
    self.menu.entryconfig(3, state=NORMAL)
    
  def selection_clear(self, first, last=None):
    MultiListbox.selection_clear(self, first, last)
    self.updateCVP(self.cvp, self.curselection())
    self.menu.entryconfig(3, state=DISABLED)
  
class CardViewPanel(MultiListbox):
  def __init__(self, master, selectM, lists, cbset):
    MultiListbox.__init__(self, master, selectM, lists)
    for l in self.lists:
      l.bind('<Double-1>', self.editProp)
    self.cbset = cbset
    for b in self.cbset:
      b.config(state=DISABLED)
      
  def updateStates(self):
    if(self.size() == 0):
      for b in self.cbset:
        b.config(state=DISABLED)
    else:
      for b in self.cbset:
        b.config(state=NORMAL)
        
  def editProp(self, event):
    selection = self.curselection()
    i = int(selection[0])
    prop = self.get(i)
    if(prop[0] != "OTHER"):
      self.win = Toplevel()
      self.win.title = ("Edit Property")
      Label(self.win, text="Property Type: ").grid(row=0, column=0, sticky='NSW')
      Label(self.win, text = prop[0]).grid(row=0, column=1, sticky='NSW')
      Label(self.win, text="Parameter Type: ").grid(row=1, column=0, sticky='NSW')
      partype = Entry(self.win)
      partype.insert(0, prop[1])
      partype.grid(row=1, column=1, sticky='NSE')
      Label(self.win, text="Parameter Value: ").grid(row=2, column=0, sticky='NSW')
      parval = Entry(self.win)
      parval.insert(0, prop[2])
      parval.grid(row=2, column=1, sticky='NSE')
      Label(self.win, text="Property Value: ").grid(row=3, column=0, sticky='NSW')
      value = Entry(self.win)
      value.insert(0, prop[3])
      value.grid(row=3, column=1, sticky='NSE')
      Button(self.win, text="Edit", command=(lambda i=i:self.makeEdit(i, prop[0], partype, parval, value))).grid(row=4, column=0, columnspan=2)
      #win.bind('<Escape>', win.quit)
      #win.focus_set()
      #win.grab_set()
      #win.wait_window()
    else:
      showerror("Edit OTHER", "Cannot edit an OTHER property. Sorry.")
    
  def makeEdit(self, index, name, parval, partype, value):
    self.delete(index)
    self.insert(index, (name, parval.get(), partype.get(), value.get()))
    self.selection_set(index)
  
class AddDialog():
  def __init__(self, props, cvp, parent=None):
    self.name = ""
    self.win = Toplevel()
    self.win.title=('Add Property')
    form = Frame(self.win)
    form.pack(side=TOP)
    Label(form, text='Property Name:').grid(row=0, column=0, sticky='NSW') # add a few widgets
    self.bname = Menubutton(form, text='Select a Property')
    mname = Menu(self.bname, tearoff=False)
    self.bname.config(menu=mname)
    for p in props:
      if(p != "N" and p != "FN" and p != "BEGIN" and p != "END" and p != "VERSION"):
        mname.add_command(label=p, command=lambda p=p: self.setName(p))
    self.bname.grid(row=0, column=1)
    self.bname.focus()
    Label(form, text="Parameter Type:").grid(row=1, column=0, sticky='NSW')
    self.partype = Entry(form)
    self.partype.grid(row=1, column=1, sticky='NSE')
    Label(form, text="Parameter Value:").grid(row=2, column=0, sticky='NSW')
    self.parval = Entry(form)
    self.parval.grid(row=2, column=1, sticky='NSE')
    Label(form, text="Property Value:").grid(row=3, column=0, sticky='NSW')
    self.value = Entry(form)
    self.value.grid(row=3, column=1, sticky='NSE')
    Button(self.win, text='Add Card', command=lambda: self.get(cvp)).pack()
    #self.win.bind('<Escape>', self.quit)
    self.win.focus_set()
    self.win.grab_set()
    self.win.wait_window()
  
  def setName(self, name):
    if(name == "OTHER"):
      self.parval.config(state=DISABLED)
      self.partype.config(state=DISABLED)
    else:
      self.parval.config(state=NORMAL)
      self.partype.config(state=NORMAL)
    self.name = name
    self.bname.config(text=name)
    
  def get(self, cvp):
    cvp.insert(END, (self.name, self.partype.get(), self.parval.get(), self.value.get()))
    self.win.quit()
    
  def quit(self):
    win = self.win.quit()
    win.quit()
    
class DupDialog():
  def __init__(self, name, prop, option, parent=None):
    self.win = Toplevel()
    self.win.title=('Duplicate Card Exists')
    Label(self.win, text=name).pack(side=TOP)
    ppanel = MultiListbox(self.win, 'SINGLE', (('Property Name', 20), ('Type Parameter', 35), ('Value Parameter', 35), ('Value', 55)))
    for p in prop:
      ppanel.insert(END, (p[0], p[1], p[2], p[3]))
    ppanel.pack(side=TOP, fill=BOTH)
    bpanel = Frame(self.win)
    Label(bpanel, text='What would you like to do?').pack(side=TOP, fill=X)
    self.var = StringVar()
    self.option = option
    Radiobutton(bpanel, text="Don't store this card", variable=self.var, value="Skip").pack(anchor=NW)
    Radiobutton(bpanel, text="Replace this card", variable=self.var, value="Replace").pack(anchor=NW)
    Radiobutton(bpanel, text="Merge with this card",variable=self.var, value="Merge").pack(anchor=NW)
    Radiobutton(bpanel, text="Cancel storing",variable=self.var, value="Cancel").pack(anchor=NW)
    Button(bpanel, text="OK", command= lambda: self.get()).pack(side=RIGHT)
    bpanel.pack(side=LEFT)
    self.var.set("Skip")
    self.win.focus_set()
    self.win.grab_set()
    self.win.wait_window()

  def get(self):
    self.option.set(self.var.get())
    self.win.destroy()
    
class Query():
  def __init__(self, execute, menu, parent=None):
    self.win = Toplevel(parent)
    self.win.title('Query')
    self.win.protocol('WM_DELETE_WINDOW', self.exit)
    self.execute = execute
    self.menu = menu
    
    qpanel = LabelFrame(self.win, text='Select a Transaction:', padx=0, pady=0, width=100, height=50)
    qpanel.pack(side=TOP, fill=X)
    self.var = StringVar()
    Radiobutton(qpanel, text="Display the properties of all cards with the name ", variable=self.var, value="Display", command=lambda: self.onSelect(self.allname)).grid(row=0, column=0, sticky='NW')
    self.allname = Entry(qpanel)
    self.allname.bind('<FocusIn>', (lambda event: self.onFocus("Display")))
    self.allname.insert(0, "(formatted name format)")
    self.allname.grid(row=0, column=1)
    Label(qpanel, text=".").grid(row=0, column=2, sticky='NE')
    Radiobutton(qpanel, text="How many cards are in ", variable=self.var, value="How", command=lambda: self.onSelect(self.country)).grid(row=1, column=0, sticky='NW')
    self.country = Entry(qpanel)
    self.country.bind('<FocusIn>', (lambda event: self.onFocus("How")))
    self.country.insert(0, "(country)")
    self.country.grid(row=1, column=1)
    Label(qpanel, text="?").grid(row=1, column=2, sticky='NE')
    Radiobutton(qpanel, text="How can I contact ", variable=self.var, value="Contact", command=lambda: self.onSelect(self.cinfo)).grid(row=2, column=0, sticky='NW')
    self.cinfo = Entry(qpanel)
    self.cinfo.bind('<FocusIn>', (lambda event: self.onFocus("Contact")))
    self.cinfo.insert(0, "(name - formatted name format)")
    self.cinfo.grid(row=2, column=1)
    Label(qpanel, text="?").grid(row=2, column=2, sticky='NE')
    Radiobutton(qpanel, text="Show me all my contacts.", variable=self.var, value="Index").grid(row=3, columnspan=3, sticky='NW')
    cpanel = Frame(qpanel)
    tpanel = Frame(cpanel)
    Radiobutton(tpanel, text='Custom SQL Command', variable=self.var, command=lambda: self.activate(), value="Custom").pack(side=LEFT, anchor=NW)
    Button(tpanel, text='Help', command= lambda: self.help()).pack(side=RIGHT, anchor=NE)
    tpanel.pack(side=TOP, expand=YES, fill=BOTH)
    self.ctext = Text(cpanel, relief=SUNKEN, bg='white', fg='black')
    self.ctext.insert(END, "SELECT")
    self.ctext.config(state=DISABLED)
    self.ctext.bind('<FocusIn>', (lambda event: self.onFocus("Custom")))
    self.ctext.pack(side=BOTTOM, expand=YES)
    cpanel.grid(row=4, columnspan=3, sticky='NW')
    bpanel = Frame(qpanel)
    self.sbutton = Button(bpanel, text="Submit", command=lambda: self.submit(), state=DISABLED)
    self.sbutton.pack(side=RIGHT)
    bpanel.grid(row=5, columnspan=3, sticky='NE')
    
    rpanel = Frame(self.win)
    rlabel = Frame(rpanel)
    Label(rlabel, text="Results").pack(fill=X, side=LEFT)
    rlabel.pack(side=TOP, fill=BOTH)
    self.rtext = Text(rpanel, relief=SUNKEN, bg='white', fg='black', state=DISABLED)
    self.rtext.pack(expand=YES, fill=BOTH)
    vscroll = Scrollbar(self.rtext)
    hscroll = Scrollbar(self.rtext, orient='horizontal')
    hscroll.config(command=self.rtext.xview)                      #xlink scrollbar and text
    vscroll.config(command=self.rtext.yview)                  #xlink scrollbar and text
    self.rtext.config(xscrollcommand=hscroll.set)             #move one moves other (horizontal)
    self.rtext.config(yscrollcommand=vscroll.set)             #move one moves other (vertical)
    Button(rlabel,text='Clear', command= lambda: self.clear()).pack(side=RIGHT, anchor=NE)
    vscroll.pack(side=RIGHT, fill=Y)
    hscroll.pack(side=BOTTOM, fill=X)
    rpanel.pack(side=TOP, expand=YES, fill=BOTH)
    
    self.win.bind('<Return>', (lambda event: self.submit()))
    
  def help(self):
    hwin = Toplevel(self.win)
    hwin.title('Help')
    npanel = LabelFrame(hwin, text='NAME table', padx=0, pady=0, width=100, height=100)
    nlist = MultiListbox(npanel, 'SINGLE', (('FIELD', 20), ('Type', 15), ('Null', 5), ('Key', 5), ('Default', 10), ('Extra', 20)))
    dname = self.execute("DESCRIBE NAME")
    rname = dname.fetchall()
    for r in rname:
      nlist.insert(END, r)
    nlist.pack(fill=Y)
    npanel.pack()
    
    ppanel = npanel = LabelFrame(hwin, text='PROPERTY table', padx=0, pady=0, width=100, height=100)
    plist = MultiListbox(ppanel, 'SINGLE', (('FIELD', 20), ('Type', 15), ('Null', 5), ('Key', 5), ('Default', 10), ('Extra', 20)))
    dprop = self.execute("DESCRIBE PROPERTY")
    rprop = dprop.fetchall()
    for r in rprop:
      plist.insert(END, r)
    plist.pack(fill=Y)
    ppanel.pack()
    
  def clear(self):
    self.rtext.config(state=NORMAL)
    self.rtext.delete('1.0', END)
    self.rtext.config(state=DISABLED)
    
  def onFocus(self, select):
    self.sbutton.config(state=NORMAL)
    self.var.set(select)
    
  def onSelect(self, entry):
    self.sbutton.config(state=NORMAL)
    entry.focus()
    
  def activate(self):
    self.ctext.config(state=NORMAL)
    self.ctext.focus()
    
  def submit(self):
    result = self.var.get()
    query = ""
    if(result == "Display"):
      name = self.allname.get()
      preface = "These are all the properties with the name %s\n" % name
      if '%' in name:
        #query = "SELECT pname, partype, parval, value FROM PROPERTY WHERE name_id = (SELECT name_id FROM PROPERTY WHERE pname = 'FN' AND value LIKE '%s')" % name 
        query = "SELECT pname, partype, parval, value FROM PROPERTY JOIN NAME WHERE PROPERTY.name_id = NAME.name_id AND PROPERTY.name_id IN (SELECT name_id FROM PROPERTY WHERE pname = 'FN' AND value LIKE '%s') ORDER BY NAME.name, PROPERTY.pname" % name
      else:
        #query = "SELECT pname, partype, parval, value FROM PROPERTY WHERE name_id = (SELECT name_id FROM PROPERTY WHERE pname = 'FN' AND value = '%s')" % name 
        query = "SELECT pname, partype, parval, value FROM PROPERTY JOIN NAME WHERE PROPERTY.name_id = NAME.name_id AND PROPERTY.name_id IN (SELECT name_id FROM PROPERTY WHERE pname = 'FN' AND value = '%s') ORDER BY NAME.name, PROPERTY.pname"
      pass
    elif(result == "How"):
      country = self.country.get()
      preface = "The number of cards in %s is " % country
      query = "SELECT COUNT(DISTINCT name_id) AS counter FROM PROPERTY WHERE pname = 'ADR' AND value LIKE '%%;%%;%%;%%;%%;%%;%s'" % country
    elif(result == "Contact"):
      name = self.cinfo.get()
      preface = "You can contact %s in the following ways:\n" % name
      query = "SELECT pname, partype, value FROM PROPERTY WHERE name_id = (SELECT name_id FROM PROPERTY WHERE pname = 'FN' AND value LIKE '%s') AND (pname = 'ADR' OR pname = 'TEL' OR pname = 'EMAIL')" % name
    elif(result == 'Index'):
      preface = "You have the following contacts:\n"
      query = "SELECT pname, value FROM PROPERTY JOIN NAME WHERE PROPERTY.name_id = NAME.name_id AND (PROPERTY.pname = 'FN' OR PROPERTY.pname = 'TITLE' OR PROPERTY.pname = 'ORG')";
    elif(result == 'Custom'):
      query = self.ctext.get('1.0',  END+'-1c')
      preface = ""
      self.rtext.config(state=NORMAL)
      self.rtext.insert(END, "%s returned:\n" % query)
    try:
      cursor = self.execute(query)
      self.rtext.config(state=NORMAL)
      rprint = cursor.fetchall()
      if(not rprint):
        self.rtext.insert(END, "Query produced no results\n")
      else:
        self.rtext.insert(END, preface)
        for r in rprint:
          self.rtext.insert(END, r)
          self.rtext.insert(END, "\n")
      for i in range(1, 10):
        self.rtext.insert(END, '-')
      self.rtext.insert(END, "\n")
      self.rtext.config(state=DISABLED)
    except mysql.connector.Error as err:
      self.rtext.config(state=NORMAL)
      self.rtext.insert(END, "The following errors occured when trying to execute %s:\n" % query)
      self.rtext.insert(END, "{}\n".format(err))
      for i in range(1, 50):
        self.rtext.insert(END, '-')
      self.rtext.config(state=DISABLED)
    
  def exit(self):
    self.win.destroy()
    self.menu.entryconfig(4, state=NORMAL)