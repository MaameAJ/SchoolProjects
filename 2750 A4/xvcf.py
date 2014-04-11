#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

"""
xvcf.py - GUI for the vCard program
Last updated:  April 06, 2014 

Author: Maame Apenteng #0802637
Contact: mapenten@uoguelph.ca
"""

import sys
from tkinter import *
from tkinter.messagebox import *
from multilistbox import MultiListbox
from fileviewpanel import *
from xvcf_backend import *

def notdone():
  showerror('Not implemented', 'Not yet available')
  

  #if ans: root.quit - doesn't do anything
"""
SET UP DATABASE CONNECTION
"""
if(len(sys.argv) == 3):
  db_connect(sys.argv[1], sys.argv[2])
elif(len(sys.argv) == 4):
  db_connect(sys.argv[1], sys.argv[2], sys.argv[3])
else:
  showerror('Not enough arguments!', 'Correct usage: xvcf.py username password [hostname]')
  sys.exit()


"""
MAIN WINDOW
"""
root = Tk()
root.title('xvcf - filename')
root.protocol('WM_DELETE_WINDOW', exit)
"""
MAIN MENU
"""
main = Menu(root)	
root.config(menu=main)		#set menu option
fpset = []
cpset = []
#file menu
mfile = Menu(main, tearoff=False)
mfile.add_command(label='Open...',	command=(lambda:openfile(root, ltext, ftpanel, fpset, (mfile, org, db))),               underline=0)
mfile.add_command(label='Append...',	command=notdone,					        state=DISABLED,         underline=0)
mfile.add_command(label='Save', 	command=notdone,					        state=DISABLED,         underline=0)
mfile.add_command(label='Save As...', 	command=notdone,					        state=DISABLED,         underline=0)
mfile.add_command(label='Exit', 	command=exit,						        underline=0)
main.add_cascade(label='File',		menu=mfile,	 					        underline=0)

#organize menu
org = Menu(main, tearoff=False)
org.add_command(label='Sort',		command=notdone,		                underline=0)
org.add_command(label='Canonicalize',	command=notdone,		                underline=0)
org.add_command(label='Select...',	command=notdone,		                underline=0)
org.add_command(label='Undo',		command=notdone,		                underline=0)
main.add_cascade(label='Organize',	menu=org,	 		state=DISABLED, underline=0)

#database menu
db = Menu(main, tearoff=False)
db.add_command(label='Open from Database',      command=lambda: opendb(root, ltext, ftpanel, fpset, (mfile, org, db)),                  underline=0)
db.add_command(label='Append from Database',    command=lambda: append_db(root, ltext, ftpanel, fpset, mfile),          state=DISABLED, underline=0)
db.add_command(label='Store All',		command=storeAll,		                                        state=DISABLED, underline=0)
db.add_command(label='Store Selected',		command=lambda: storeSelected(ftpanel),                                 state=DISABLED, underline=0)
db.add_command(label='Query',			command=lambda: query(root, db),		                                        underline=0)
main.add_cascade(label='Database',		menu=db,			                                                        underline=0)

#help menu
mhelp = Menu(main, tearoff=False)
mhelp.add_command(label='Card flags and colours...',	command=explain,	underline=0)
mhelp.add_command(label='About xvcf...', 		command=about,		underline=0)
main.add_cascade(label='Help',				menu=mhelp,		underline=0)



    
"""
CARD VIEW PANEL
"""
cvp = Frame(root, bd=2)
cname = LabelFrame(cvp,text='Card View Panel')
cname.pack(side=TOP, fill=BOTH, expand=YES)
#table panel
ctpanel = CardViewPanel(cname, 'SINGLE', (('Property Name', 20), ('Type Parameter', 35), ('Value Parameter', 35), ('Value', 55)), cpset)
chscroll = Scrollbar(ctpanel, orient='horizontal')
#set up table
#chscroll.config(command=ctpanel.xview, relief=SUNKEN)	#move ctpanel when scroll moved
#ctpanel.config(xscrollcommand=chscroll.set)
#pack table panel
#chscroll.pack(side=BOTTOM, fill=X)
ctpanel.pack(side=LEFT, expand=YES, fill=BOTH)
#button panel
cbpanel = Frame(cname)
cbpanel.config(relief=SUNKEN)
#set up buttons
cpset.append(Button(cbpanel, text="Move Up",		command=lambda: moveProp(ctpanel, -1)))
cpset.append(Button(cbpanel, text="Move Down", 		command=lambda: moveProp(ctpanel, 1)))
cpset.append(Button(cbpanel, text="Add Property",	command=lambda: addProp(ctpanel)))
cpset.append(Button(cbpanel, text="Delete Property", 	command=lambda: deleteProp(ctpanel)))
cpset.append(Button(cbpanel, text="Commit Changes", 	command=lambda: commitChanges(ftpanel, ctpanel)))
cpset.append(Button(cbpanel, text="Revert Changes", 	command=lambda: revertChanges(ftpanel, ctpanel)))
for b in cpset:
  b.pack(side=TOP, fill=X)
  #b.config(state=DISABLED)
#pack button panel
cbpanel.pack(side=RIGHT, fill=Y)
   
"""
FILE VIEW PANEL
"""
fvp = Frame(root, bd=2)
#fname = Frame(fvp)
#Label(fname,text='File View Panel').pack(side=LEFT)
#fname.pack(side=TOP, fill=X)
fname = LabelFrame(fvp, text='File View Panel', padx=0, pady=0, width=100, height=100)
fname.pack(side=TOP, fill=BOTH, expand=YES)
fvp.pack(side=TOP, expand=YES, fill=BOTH)
#table panel
ftpanel = FileViewPanel(fname, 'EXTENDED', (('Card', 5), ('Name', 30), ('Region', 10), ('Country', 10), ('#ADR', 5), ('#TEL', 5), ('Flags', 10)), ctpanel, updateCVP, db)
#ftpanel = MultiListbox(fname, 'EXTENDED', (('Card', 5), ('Name', 30), ('Region', 10), ('Country', 10), ('#ADR', 5), ('#TEL', 5), ('Flags', 10)), updateCVP)
ftpanel.pack(expand=YES, fill=BOTH)
#set up table
#pack table panel
ftpanel.pack(side=LEFT, expand=YES, fill=BOTH)
#button panel
fbpanel = Frame(fname)
fbpanel.config(relief=SUNKEN)
#set up buttons
fpset.append(Button(fbpanel, text="Map Selected",	command=(lambda: mapSelected(ftpanel, ltext))))
fpset.append(Button(fbpanel, text="Reset Map", 		command=clearMap))
fpset.append(Button(fbpanel, text="Browse Selected",	command=(lambda: browseSelected(ftpanel, ltext))))
fpset.append(Button(fbpanel, text="Delete Selected", 	command=(lambda: deleteSelected(ftpanel))))
fpset.append(Button(fbpanel, text="Add Card", 		command=(lambda: addCard(ftpanel))))

for b in fpset:
  b.config(state=DISABLED)
  b.pack(side=TOP, fill=X)
#pack button panel
fbpanel.pack(side=RIGHT, fill=Y)
   
   
#pack CARD VIEW PANEL after FILE VIEW PANEL
cvp.pack(side=TOP, expand=YES, fill=BOTH)
"""
LOG DIALOGUE
"""
logdis = Frame(root, bd=4)
logdis.pack(expand=YES, fill=BOTH)
lpanel = Frame(logdis)
Label(lpanel,	text='Log Display').pack(side=LEFT)
lpanel.pack(side=TOP, expand=YES, fill=X)
logvscroll = Scrollbar(logdis)
loghscroll = Scrollbar(logdis, orient='horizontal')
ltext = Text(logdis, relief=SUNKEN, bg='white', fg='black', state=DISABLED)
loghscroll.config(command=ltext.xview)			#xlink scrollbar and text
logvscroll.config(command=ltext.yview)			#xlink scrollbar and text
ltext.config(xscrollcommand=loghscroll.set)		#move one moves other (horizontal)
ltext.config(yscrollcommand=logvscroll.set)		#move one moves other (vertical)
Button(lpanel,	text='Clear', command= (lambda: clear(ltext))).pack(side=RIGHT)
logvscroll.pack(side=RIGHT, fill=Y)
loghscroll.pack(side=BOTTOM, fill=X)
ltext.pack(side=LEFT, expand=YES, fill=BOTH)

"""
LOAD WINDOW
"""
root.mainloop()