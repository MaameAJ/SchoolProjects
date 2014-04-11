# -*- coding: utf-8 -*-

"""
xvcf_backend.py - backend for xvcf
Last updated:  April 06, 2014 

Author: Maame Apenteng #0802637
Contact: mapenten@uoguelph.ca
"""

import Vcf
import subprocess
import webbrowser 
import mysql.connector
import sys
import os

from tkinter import *
from tkinter.messagebox import *
from tkinter.filedialog import *
from GMapData import *
from fileviewpanel import AddDialog
from fileviewpanel import DupDialog
from fileviewpanel import Query

cards = []
mfilename = ""
logname = "log.txt"
tempname = "temp.vcf"
prevstate = "state.vcf"

def db_connect(username, pword, hostname="Dursley.socs.uoguelph.ca"):
  try:
    global cnx
    cnx = mysql.connector.connect(user=username, password=pword, host=hostname, database=username, autocommit=True, get_warnings=True)
    crs = execute("CREATE TABLE IF NOT EXISTS NAME \
      (name_id INT AUTO_INCREMENT PRIMARY KEY, \
      name VARCHAR(60) NOT NULL)")
    
    crs = execute("CREATE TABLE IF NOT EXISTS PROPERTY \
      (name_id INT NOT NULL, pname CHAR(8) NOT NULL, \
      pinst SMALLINT NOT NULL, partype TINYTEXT, \
      parval TINYTEXT, value TEXT, \
      PRIMARY KEY(name_id, pname, pinst), \
      FOREIGN KEY (name_id) REFERENCES NAME(name_id) ON DELETE CASCADE);")
  except:
    showwarning("Database Connection Failed", "xvcf failed to connect to specified database and will now exit. Sorry.")
    sys.exit()

def execute(query):
  global cnx
  cursor = cnx.cursor()
  query.replace("\\", "\\\\")
  query.replace("'", "\\'")
  query.replace('"', '\\"')
  query.replace(";", "\;")
  cursor.execute(query)
  return cursor
  

class Pname(object):
  def __init__(self):
    props = []
    for number, name in enumerate(Vcf.getPnames(props)):
      setattr(self, name.decode("utf-8"), number)
    
  def getProps(self):
    props = []
    rprops = []
    for name in Vcf.getPnames(props):
      rprops.append(name.decode("utf-8"))
    return rprops
    
lprops = Pname()

def fopen(filename, text):
  #read file
  error = Vcf.readFile(filename)
  if error != "OK":
    text.insert(END, error)
    text.insert(END, "\n")
  text.config(state=DISABLED)
  
  ncard = getCards()
  
  while (ncard != 0):
    ncard = getCards()
  #Vcf.freeFile()

"""
MAIN MENU
"""
#file menu
def openfile(root, text, fvp, fbuttons, menu):
  filename = ""
  filename = askopenfilename(filetypes=[("vCard Files","*.vcf"), ('All','*')])
  if(filename):
    root.title("xvcf - %s" % filename)
    #run vcftool
    infile = open(filename, "r")
    outfile = open(tempname, "w")
    logfile = open(logname, "w")
    
    ##update 
    #global cards
    #cards[:] = []
    
    fopen(filename, text)
    
    subprocess.check_call("./vcftool -info", stdin=infile, stdout=outfile, stderr=logfile, shell=True)
    infile.close()
    outfile.close()
    logfile.close()
    #update text
    text.config(state=NORMAL)
    for line in open(tempname, "r"):
      text.insert(END, line) 

    if(not cards):
      showwarning("Empty Card File", "There are no cards in this file.")
      openfile(root, text, fvp, fbuttons)
      
    main = menu[0]
    main.entryconfig(1, state=NORMAL) #enable append
    main.entryconfig(2, state=NORMAL) #enable save
    main.entryconfig(3, state=NORMAL) #enable saveas
    
    #menu[1].config(type=NORMAL)
    
    database = menu[2]
    database.entryconfig(1, state=NORMAL) #enable append database
    database.entryconfig(2, state=NORMAL) #enable store all
    
    updateFVP(fvp)
    fvp.selection_set(0)
    for b in fbuttons:
      b.config(state=NORMAL)
    startWebServer(42637)
    global mfilename
    mfilename = filename
  
def appendfile():
  filename = askopenfilename()
  status = Vcf.readFile(filename)
  ncard = getCards()
  while (ncard != 0):
    ncard = getCards()
  Vcf.freeFile()
  
def save(text):
  status = Vcf.writeFile(mfilename, cards)
  print(PyBytes_Check(status))
  
def saveas(text):
  mfilename = asksaveasfilename(filetypes=[("vCard Files","*.vcf"), ('All','*')])
  save(text)
  
def exit():
  ans = askokcancel('Confirm exit', "Are you sure you want to quit?")
  if ans: 
    killServers()
    global cnx
    cnx.close()
    sys.exit()
    os.remove(tempname)
    os.remove(logfile)
    os.remove(prevstate)
    
#organize menu
def sort(text):
  Vcf.writeFile(prevstate, cards)
  infile = open(mfilename, "r")
  outfile = open(tempname, "w")
  logfile = open(logname, "w")
  subprocess.check_call("./vcftool -sort", stdin=infile, stdout=outfile, stderr=logfile, shell=True)
  fopen(tempname, text)
  infile.close()
  outfile.close()
  updateText(text)
  updateFVP(fvp)
  fvp.selection_set(0)
  
#help menu

def explain():
  win = Toplevel()
  win.title('Card flags and colours...')
  text = Text(win, relief=SUNKEN, fg='black', wrap=WORD)#, state=DISABLED)
  text.insert(END, 'Flags\n')
  text.insert(END, '\tC = card as a whole is in canonical form\n')
  text.insert(END, '\t\tNote: a question mark in the first person indicates that\n')
  text.insert(END, '\t\t\tcanonical status is unknown\n')
  text.insert(END, '\tM = card has multiple same mandatory properties: FN or N\n')
  text.insert(END, '\tU, P, G = card has at least one URL, PHOTO, or GEO property,\n')
  text.insert(END, '\t\trespectively\n')
  text.insert(END, '\t- = that any of these above properties are missing\n')
  text.insert(END, '\nColours\n')
  text.insert(END, '\tgreen = card is in canonical form (the C flag is on)\n')
  text.insert(END, '\tred = card needs fixing because\n')
  text.insert(END, '\t\t(a) it has multiple mandatory properties (the M flag is on)\n')
  text.insert(END, "\t\t(b) its FN property has the same value as the preceding card's\n")
  text.insert(END, "\t\t\tFN (signalling a duplication in the file)\n")
  text.insert(END, "\tyellow = means the card is neither canonical nor is in need of repair\n")
  text.config(state=DISABLED)
  text.pack(expand=YES, fill=X)
  Button(win, text='OK', command=win.destroy).pack()
  win.bind('<Escape>', win.destroy)
  win.focus_set()
  win.grab_set()
  win.wait_window()
  
def about():
  showinfo('About xvcf', 'App name: xvcf\nWritten by: Maame Apenteng\nCompatible with vCard version 3.0 only\n')
  
  
def updateText(text):
  text.config(state=NORMAL)
  logfile = open(logname, "r")
  for line in logfile:
    text.insert(END, line)
  text.config(state=DISABLED)
  
def clear(text):
  text.config(state=NORMAL)
  text.delete('1.0', END)
  text.config(state=DISABLED)
  
def getCards():
   global cards
   card = []
   ccard = Vcf.getCard(card)
   if(ccard != 0):
     cards.append(card)
   return ccard

"""
DATABASE
"""
def store(toStore):
  for crrt in toStore:
    for prop in crrt:
      if(prop[0] == lprops.N):
        try:
          fname = prop[3].decode('utf-8')
        except:
          fname = prop[3]
        name = fname.split(";", 1)
        check = execute("SELECT name FROM NAME WHERE name LIKE '%s%%'" % name[0]);
        row = check.fetchall()
        #print(fname)
        if(row):
          nsql = execute("SELECT name_id FROM NAME WHERE name = '{0}'".format(fname))
          nid = nsql.fetchone()
          psql = execute("SELECT pname, partype, parval, value FROM PROPERTY WHERE name_id = '%s'" % nid[0])
          dprops = []
          for k in psql:
            dprops.append(k)
          option = StringVar()
          dd = DupDialog(fname, dprops, option)
          sel = option.get()
          if(sel == "Replace"):
            delete = execute("DELETE FROM PROPERTY WHERE name_id = '%s'" % nid[0])
            insertDB(fname, crrt)
          elif(sel == "Merge"):
            merge(crrt, nid[0])
          elif(sel == "Cancel"):
            print("Cancel storing")
            return "break"
          else:
            pass
        else:
          crs = execute("INSERT INTO NAME (name) VALUES ('%s')" % fname)
          insertDB(fname, crrt)

def insertDB(name, card):
  nsql = execute("SELECT name_id FROM NAME WHERE name = '%s'" % name)
  nid = nsql.fetchone()
  for p in card:
    if(p[0] != lprops.N):
      props = lprops.getProps()
      for i in props:
        if(p[0] == getattr(lprops, i)):
          p_name = i
      pcount = execute("SELECT COUNT(*) AS counter FROM PROPERTY WHERE (name_id = {0} AND pname = '{1}')".format(nid[0], p_name))
      row = pcount.fetchone()
      pnum = row[0] + 1
      try:
        execute("INSERT INTO PROPERTY (name_id, pname, pinst, partype, parval, value) \
          VALUES ({n_id}, '{pname}', {pinst}, '{partype}', '{parval}', '{value}')".
          format(n_id=nid[0], pname=p_name, pinst=pnum, partype=p[1].decode('utf-8'), parval=p[2].decode('utf-8'), value=p[3].decode('utf-8')))
      except:
        execute("INSERT INTO PROPERTY (name_id, pname, pinst, partype, parval, value) \
          VALUES ({n_id}, '{pname}', {pinst}, '{partype}', '{parval}', '{value}')".
          format(n_id=nid[0], pname=p_name, pinst=pnum, partype=p[1].decode('utf-8'), parval=p[2].decode('utf-8'), value=p[3].decode('utf-8')))
      
def storeAll():
  store(cards)
  
def storeSelected(fvp):
  selection = fvp.curselection()
  toStore = []
  for i in selection:
    k = int(i)+1
    toStore.append(cards[int(i)-1])
  store(toStore)
  
def opendb(root, text, fvp, fbuttons, menu):
    #check if database is empty
    nsql = execute("SELECT * FROM NAME")
    names = nsql.fetchall()
    if(not names):
      showwarning("Empty Card File", "There are no cards in this file.")
      return ""
      
    root.title("xvcf - Database")
    cards[:] = []
    
    for (name_id, name) in names:
      card = []
      card.append((lprops.N, "", "", name))
      psql = execute("SELECT pname, partype, parval, value FROM PROPERTY WHERE name_id = '%s'" % name_id)
      props = psql.fetchall()
      for(pname, partype, parval, value) in props:
        card.append((getattr(lprops, pname), partype.encode('utf-8'), parval.encode('utf-8'), value.encode('utf-8')))
      cards.append(card)
      
    main = menu[0]
    main.entryconfig(1, state=NORMAL) #enable append
    main.entryconfig(2, state=DISABLED) #disable save
    main.entryconfig(3, state=NORMAL) #enable saveas
    
    #menu[1].config(type=NORMAL)
    
    database = menu[2]
    database.entryconfig(1, state=NORMAL) #enable append database
    database.entryconfig(2, state=NORMAL) #enable store all
      
    updateFVP(fvp)
    fvp.selection_set(0)
    for b in fbuttons:
      b.config(state=NORMAL)
      
def append_db(root, text, fvp, fbuttons, menu):
  nsql = execute("SELECT * FROM NAME")
  names = nsql.fetchall()
  if(not names):
    showwarning("Empty Database", "There are no cards in the database.")
    return "break"
      
  for (name_id, name) in names:
    card = []
    card.append((lprops.N, "", "", name))
    psql = execute("SELECT pname, partype, parval, value FROM PROPERTY WHERE name_id = '%s'" % name_id)
    props = psql.fetchall()
    for(pname, partype, parval, value) in props:
      card.append((getattr(lprops, pname), partype.encode('utf-8'), parval.encode('utf-8'), value.encode('utf-8')))
    cards.append(card)
    
  updateFVP(fvp)
  fvp.selection_set(0)
  menu.entryconfig(2, state=NORMAL) #enable save
      
def merge(card, nid):
  for p in card:
    psql = execute("SELECT pname, partype, parval, value FROM PROPERTY WHERE name_id = '%s'" % nid)
    dprop = psql.fetchall()
    if(p[0] != lprops.N):
      pnames = lprops.getProps()
      for i in pnames:
         if(p[0] == getattr(lprops, i)):
           p_name = i
      par_type = p[1].decode('utf-8')
      par_val = p[2].decode('utf-8')
      pval = p[3].decode('utf-8')
      exists = False
      for(pname, partype, parval, value) in dprop:
        if(p_name == pname and par_type == partype and par_val == parval and value == pval):
          exists = True
      if(not exists):
        msql = execute("SELECT MAX(pinst) FROM PROPERTY WHERE name_id = {0} AND pname = '{1}'".format(nid, p_name))
        query = "SELECT MAX(pinst) FROM PROPERTY WHERE name_id = {0} AND pname = '{1}'".format(nid, p_name)
        row = msql.fetchone()
        if(row[0] != None):
          minst = row[0] + 1
        else:
          minst = 1
        execute("INSERT INTO PROPERTY (name_id, pname, pinst, partype, parval, value) \
          VALUES ({n_id}, '{pname}', {pinst}, '{partype}', '{parval}', '{value}')".
          format(n_id=nid, pname=p_name, pinst=minst, partype=par_type, parval=par_val, value=pval))
           
def query(root, menu):
  menu.entryconfig(4, state=DISABLED)
  Query(execute, menu, root)
            
"""
FILE VIEW PANEL
"""
def updateFVP(fvp):
  fvp.delete(0, END)
  #print(cards)
  i = 0
  for crrtc in cards:
    i += 1
    numname = 0
    numfname = 0
    numadr = 0
    numtel = 0
    haveGeo = False
    haveUrl = False
    havePhoto = False
    haveUID = False
    name = ""
    region = ""
    country = ""
    flag = ""
    for prop in crrtc:
      if(prop[0] == lprops.N): #name prop
        numname+= 1
      elif(prop[0] == lprops.FN): #formatted name prop
        numfname+= 1
        if(numfname == 1):
          name = prop[3]
      elif(prop[0] == lprops.PHOTO): #in the photo url
          havePhoto = True
      elif(prop[0] == lprops.ADR): #address prop
        numadr+= 1
        if(numadr == 1):
          avalue = prop[3].decode('utf-8')
          address = avalue.rsplit(";", 6)
          try:
            region = address[4]
            country = address[6]
          except:
            pass
      elif(prop[0] == lprops.TEL): #telephone prop
          numtel+= 1
      elif(prop[0] == lprops.GEO):
        haveGeo = True
      elif(prop[0] == lprops.UID): #in UID
        uid = prop[3].decode('utf-8')
        haveUID = True
        if(uid.startswith("@")):
          #check if card is canon
          if ("*" not in uid[0:5]):
            flag += "C"
          else:
            flag += "-"
        else:
         haveUID = False
      elif(prop[0] == lprops.URL): #in URL
        haveUrl = True
    #end of for loop
    if(not haveUID):
      flag += "?"
    if(numname > 1 or numfname > 1):
      flag += "M"
    else:
      flag += "-"
    
    if(haveUrl):
      flag += "U"
    else:
      flag += "-"
    
    if(havePhoto):
      flag += "P"
    else:
      flag += "-"
      
    if(haveGeo):
      flag += "G"
    else:
      flag += "-"
	  
    fvp.insert(END, (i, name, region, country, numadr, numtel, flag))
    
createGMP = False
n = 0

def mapSelected(fvp, text):
  global createGMP
  global n
  if(not createGMP):
    global gmd
    gmd = GMapData()
    createGMP = True
    
  selection = fvp.curselection()
  text.config(state=NORMAL)
  j = 0
  for i in selection:
    k = int(i)+1
    c_card = cards[int(i)-1]
    hasGeo = False
    photo = ""
    address = ""
    for prop in c_card:
      if(prop[0] == lprops.PHOTO):
        value = prop[3]
        if value.startswith("http://"):
          photo = value
        else:
          text.insert(END, "Card #%d's PHOTO property is not displayable.\n" % k)
      elif(prop[0] == lprops.ADR):
        address = prop[3]
      elif(prop[0] == lprops.GEO and not hasGeo):
        try:
          geo = prop[3].split(";", 2)
          coord = [float(geo[0]), float(geo[1])]
          hasGeo = True
          j += 1
        except:
          text.insert(END, "Card #%d has an invalid GEO property.\n" % k)
    if(not hasGeo and photo != ""):
      webbrowser.open(photo)
    elif(hasGeo):
      gmd.addPoint(coord, photo, address)
  text.config(state=DISABLED)
  gmd.addOverlay(n, j, 0)
  gmd.serve( "public_html/index.html" );
  launchBrowser( "http://localhost:42637/" )
  n += j
  
def clearMap():
  global createGMP
  global n
  createGMP = False
  n = 0
  
def browseSelected(fvp, text):
  selection = fvp.curselection()
  text.config(state=NORMAL)
  for i in selection:
    cardC = cards[int(i)-1]
    hasUrl = False
    for prop in cardC:
      if(prop[0] == lprops.URL and not hasUrl):
        value = prop[3]
        if value.startswith("http://"):
          webbrowser.open(value)
        else:
          text.insert(END, "Card #%s has an invalid URL property.\n" % i)
        hasUrl = True
    if(not hasUrl):
      text.insert(END, "Card #%s has no URL property.\n" % i)
  text.config(state=DISABLED)
  
def deleteSelected(fvp):
  selection = fvp.curselection()
  if(len(selection) != fvp.size()):
    ans = askokcancel('Confirm Delete', "Are you sure you want to delete %d card(s)?" % len(selection))
    if(ans):
      select = int(selection[0])
      for i in selection:
        del cards[int(i)-1]
      updateFVP(fvp)
      fvp.selection_set(select)
  else:
    showerror('Deletion Error!', 'Cannot have an empty file.')
  
def addCard(fvp):
  win = Toplevel()
  form = Frame(win)
  form.pack(side=TOP)
  Label(form, text='Name:').grid(row=0, column=0, sticky='NSW') # add a few widgets
  name = Entry(form)
  name.insert(0, 'FamilyName;GivenName;;')
  name.grid(row=0, column=1, sticky='NSE')
  name.focus()
  Label(form, text="Formatted Name:").grid(row=1, column=0, sticky='NSW')
  fname = Entry(form)
  fname.insert(0, 'GivenName FamilyName')
  fname.grid(row=1, column=1, sticky='NSE')
  Button(win, text='Add Card', command=(lambda: getCard(win, name, fname, fvp))).pack()
  win.bind('<Escape>', win.destroy)
  win.focus_set()
  win.grab_set()
  win.wait_window()

def getCard(win, name, fname, fvp):
  vcpname = name.get()
  vcpfname = fname.get()
  if(vcpname == "FamilyName;GivenName;;" or vcpname == "" or vcpfname == "" or  vcpfname == "GivenName FamilyName"):
    showerror("Missing important values", "Please fill all fields and enter (none) for any missing field.")
    if(vcpname == ""):
      name.config(highlightcolor="red")
      name.focus()
    if(vcpname == ""):
      fname.config(highlightcolor="red")
      fname.focus()
  else:
    cards.append([(lprops.N, None, None, vcpname), (lprops.FN, None, None, vcpfname)])
    updateFVP(fvp)
    fvp.selection_set(END)
    win.destroy()
  
def updateCVP(cvp, selection):
  clearCVP(cvp)
  if(len(selection) == 1):
    i = int(selection[0])
    card = cards[i]
    props = lprops.getProps()
    for prop in card:
      name = ""
      for p in props:
        if(prop[0] == getattr(lprops, p)):
          name = p
      cvp.insert(END, (name, prop[1], prop[2], prop[3]))
  cvp.updateStates()
    
def clearCVP(cvp):
  cvp.delete(0, END)
  
def moveProp(cvp, direction):
  selection = cvp.curselection()
  if(len(selection)):
    i = int(selection[0])
    toInsert = cvp.get(i)
    cvp.delete(i)
    cvp.insert(i+direction, toInsert)
    cvp.selection_set(i+direction)
    
def deleteProp(cvp):
  selection = cvp.curselection()
  ans = askokcancel('Confirm Delete', "Are you sure you want to delete this property?")
  if(ans):
    i = int(selection[0])
    cvp.delete(i)
  
def addProp(cvp):
  win = AddDialog(lprops.getProps(), cvp)

def commitChanges(fvp, cvp):
  selection = fvp.curselection()
  i = int(selection[0])
  card = cards[i]
  card[:] = []
  for i in range(cvp.size()):
    pval = cvp.get(i)
    name = (getattr(lprops, pval[0]),)
    prop = name+tuple(pval[1:])
    card.append(prop)
  updateFVP(fvp)
  
def revertChanges(fvp, cvp):
  ans = askokcancel('Confirm Revert', "This will remove all uncommitted changes to the current card.")
  if(ans):
    updateCVP(cvp, fvp.curselection())