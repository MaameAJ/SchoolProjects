# -*- coding: utf-8 -*-

#!/usr/bin/python3

# This program just exercises GMapData in a confidence test

# W Gardner, upgrade to Python 3 (used 2to3 tool)
#  Feb. 11, 2014

# Import GUI classes
from tkinter import *

import string

from GMapData import *

def main():
	
	#1. start CGI/HTTP server
	startWebServer(46875)

	#2. create GMapData object and populate with data
	gmd = GMapData( "maptest", "Univ. of Guelph", [43.530318,-80.223241], 14 )	# use default values
	photo = "http://www.uoguelph.ca/~gardnerw/head.gif" # photo url
	address = ";;50 Stone Road East\, Reynolds 105;\\nGuelph;Ontario;N1G2W1" # address
	gmd.addPoint( [43.530318,-80.223241], photo, address )	# s.b. center of map
	gmd.addOverlay( 0, 1, 3 )	# single point, blue icon

	#3. generate HTML to serve
	gmd.serve( "public_html/index.html" );

	#4. launch browser
	launchBrowser( "http://localhost:42637/" )


	input( "Press enter:" )		# pause
	
	gmd.addPoint( [43.530318,-80.223241] )	# 3-point red line
	gmd.addPoint( [43.535538,-80.223461] )
	gmd.addPoint( [43.520758,-80.220681] )
	gmd.addOverlay( 1, 3, 0 )
	
	gmd.serve( "public_html/index.html" );
	launchBrowser( "http://localhost:46875/" )


	input( "Press enter:" )
	
	gmd.addPoint( [43.530318,-80.223241] )	# 3-point blue line
	gmd.addPoint( [43.515538,-80.220461] )
	gmd.addPoint( [43.520758,-80.225681] )
	gmd.addOverlay( 4, 3, 2 )
	
	gmd.serve( "public_html/index.html" );
	launchBrowser( "http://localhost:46875/" )


	print( "Close the Tk window to proceed with server shutdown..." )
	root = Tk()
	root.mainloop()				# open a Tk window

	#5. kill servers
	killServers()
	
	
if __name__ == "__main__":
    main()
