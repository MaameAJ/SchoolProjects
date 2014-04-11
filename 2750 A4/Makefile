CC=gcc
CFLAGS = -Wall -std=c99 -g

all: xvcf.py multilistbox.py GMapData.py vcftool vcf.so

vcf.so: vcfmodule.o
	$(CC) $(CFLAGS) -shared vcfmodule.o -o Vcf.so

vcfmodule.o: vcfmodule.c vcutil.h
	$(CC) $(CFLAGS) -I/usr/local/include/python3.1 -fPIC -c vcfmodule.c -o $@

vcftool:  vcftool.o vcutil.o iso3166-2_CountryCodes.o heuristics_tel.o
	$(CC) $(CFLAGS) $^ -o $@
	
vcftool.o: vcftool.h vcftool.c
	$(CC) $(CFLAGS) -c vcftool.c

heuristics_tel.o: heuristics_tel.c heuristics_tel.h
	$(CC) $(CFLAGS) -c heuristics_tel.c
	
iso3166-2_CountryCodes.o: iso3166-2_CountryCodes.c iso3166-2_CountryCodes.h
	$(CC) $(CFLAGS) -c iso3166-2_CountryCodes.c
	
vcutil.o: vcutil.c vcutil.h  
	$(CC) $(CFLAGS) -c vcutil.c
	
clean:
	rm -f *~ *.o *.out *.so  *.pyc vcftool