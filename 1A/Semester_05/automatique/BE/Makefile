MAINFILE = BE_Automatique

all:
	pdflatex $(MAINFILE).tex

view:
	pdflatex -shell-escape $(MAINFILE).tex && evince $(MAINFILE).pdf

clean:
	rm -f *.aux
	rm -f *.log
	rm -f *.toc
	rm -f *.lof
	rm -f *.lot
