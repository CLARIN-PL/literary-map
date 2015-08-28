package pl.edu.pwr.litmap.ccl;

import java.io.IOException;

/**
 * Abstrakcyjna klasa do strumieniowego wczytywania danych.
 * @author czuk
 *
 */
public abstract class StreamReader {

	protected abstract TokenAttributeIndex getAttributeIndex();
	protected abstract Paragraph readRawParagraph() throws IOException;
	public abstract void close() throws IOException;
	public abstract boolean paragraphReady() throws IOException;
		
	public Paragraph readParagraph() throws Exception {
		if (paragraphReady()){
			Paragraph p = this.readRawParagraph();
			p.setAttributeIndex(this.getAttributeIndex());
			return p;
		}
		else
			return null;
	}
	
	/**
	 * TODO
	 * @return
	 */
	public ParagraphSet readParagraphSet() throws Exception {
		ParagraphSet paragraphSet = new ParagraphSet();
					
		while (paragraphReady())
			paragraphSet.addParagraph(readRawParagraph());
		paragraphSet.setAttributeIndex(this.getAttributeIndex());
		close();
						
		return paragraphSet; 
	}
}
