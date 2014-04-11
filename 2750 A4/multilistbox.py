# -*- coding: utf-8 -*-
#based heavily on code from http://code.activestate.com/recipes/52266-multilistbox-tkinter-widget/

from tkinter import *

class MultiListbox(Frame):
  def __init__(self, master, selectM, lists):
    Frame.__init__(self, master)
    self.lists = []
    for l,w in lists:
      frame = Frame(self)
      frame.pack(side=LEFT, expand=YES, fill=BOTH)
      Label(frame, text=l, borderwidth=1, relief=RAISED, bg='white', fg='black').pack(fill=X)
      lb = Listbox(frame, width=w, borderwidth=0, bg='white', fg='black',
	relief=FLAT, exportselection=FALSE)
      lb.pack(expand=YES, fill=BOTH)
      self.lists.append(lb)
      lb.bind('<B1-Motion>', lambda e, s=self: s._select(e.y))
      lb.bind('<Button-1>', lambda e, s=self: s._select(e.y))
      lb.bind('<Leave>', lambda e: 'break')
      lb.bind('<B2-Motion>', lambda e, s=self: s._b2motion(e.x, e.y))
      lb.bind('<Button-2>', lambda e, s=self: s._button2(e.x, e.y))
      lb.bind('<MouseWheel>', lambda e, s=self: s._scroll)
      lb.bind('<B5-Motion>', lambda e, s=self: s._scroll)
      if selectM == 'EXTENDED':						
        self.fselect = -1
        lb.bind('<Control-1>', lambda e, s=self: s._multiselect(e.y))
        lb.bind('<Shift-1>', lambda e, s=self: s._conselect(e.y))
     
    frame = Frame(self)
    frame.pack(side=LEFT, fill=Y)
    self.sb = Scrollbar(frame, orient=VERTICAL, command=self._scroll)
    self.sb.pack(expand=YES, fill=Y)
    for l in self.lists:
      l.config(yscrollcommand=self.sb.set)
  
  def _select(self, y):
    row = self.lists[0].nearest(y)
    self.selection_clear(0, END)
    self.selection_set(row)
    return 'break'
    
  def _multiselect(self, y):
    self.cselect = False
    row = self.lists[0].nearest(y)
    self.selection_set(row)
    return 'break'
    
  def _consecselect(self, y):
    self.cselect = True
    self.fselect = self.lists[0].nearest(y)
    self.selection_set(self.fselect)
    
  def _conselect(self, y):
    #first = self.lists[0].curselection().[0]
    row = self.lists[0].nearest(y)
    self.selection_clear(0, END)
    #self.selection_set(first, row)
  
  def _button2(self, x, y):
    for l in self.lists: l.scan_mark(x, y)
    return 'break'
    
  def _b2motion(self, x, y):
    for l in self.lists: l.scan_dragto(x, y)
    return 'break'
    
  def _scroll(self, *args):
    for l in self.lists:
      l.yview(*args)
  
  def curselection(self):
    return self.lists[0].curselection()
    
  def delete(self, first, last=None):
    for l in self.lists:
      l.delete(first, last)
      
  def get(self, first, last=None):
    result = []
    for l in self.lists:
      result.append(l.get(first, last))
    if last: return map(None, *result)
    return result
    
  def index(self, index):
    self.lists[0].index(index)
    
  def insert(self, index, *elements):
    for e in elements:
      i = 0
      for l in self.lists:
        if(e[i] == None):
          l.insert(index, "")
        else:
          l.insert(index,e[i])
        i = i + 1
  
  def size(self):
    return self.lists[0].size()
    
  def see(self, index):
    for l in self.lists:
      l.see(index)
      
  def selection_anchor(self, index):
    for l in self.lists:
      l.selection_anchor(index)
      
  def selection_clear(self, first, last=None):
    for l in self.lists:
      l.selection_clear(first, last)
      
  def selection_includes(self, index):
    return self.lists[0].selection_includes(index)
    
  def selection_set(self, first, last=None):
    for l in self.lists:
      l.selection_set(first, last)
      
  def onselect(handler):
    print("Is this working?")
    handler(curselection)
    
      
if __name__ == '__main__':
  tk = Tk()
  Label(tk, text='Multilistbox').pack()
  mlb = MultiListbox(tk, 'SINGLE', (('Subject', 40), ('Sender', 20), ('Date', 10)))
  for i in range(1000):
    mlb.insert(END, ('Important Message: %d' % i, 'John Doe', '10/10/%04d' % (1900+i)))
  mlb.pack(expand=YES, fill=BOTH)
  tk.mainloop()

    