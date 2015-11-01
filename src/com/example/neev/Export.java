package com.example.neev;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

public class Export extends Activity {

	FileOutputStream myfile; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_export);
		String mydir=Environment.getExternalStorageDirectory().toString()+"/Myfirstpdf.pdf";
		try {
			 
			  myfile= new FileOutputStream(mydir);
		      Document document = new Document();
		      PdfWriter.getInstance(document, myfile);
		      document.open();
		      Anchor anchor = new Anchor("TableName", new Font(Font.FontFamily.TIMES_ROMAN, 18,
		      	      Font.BOLD));
			    anchor.setName("TableName");
			    Chapter catPart = new Chapter(new Paragraph(anchor), 1);
			    
		    /* Paragraph subPara = new Paragraph("Subcategory 1", new Font(Font.FontFamily.TIMES_ROMAN, 18,
		      	      Font.BOLD));
			    Section subCatPart = catPart.addSection(subPara);
			    subCatPart.add(new Paragraph("Hello"));   
			    
			    //new MyFirstTable().createPdf(RESULT);*/
			   
			    document.add(catPart);
			    document.add(createFirstTable());
			    
		        document.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		
		
		
		
		//********************Mailing pdf file************************************************************
		//String filelocation="sdcard/acm.pdf";    
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		File fileIn = new File(mydir);
        Uri u = Uri.fromFile(fileIn);
		String to[] = {"jigyasamalik95@gmail.com"};
		emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
		//the attachment
		emailIntent .putExtra(Intent.EXTRA_STREAM, u);
		emailIntent.setType("text/plain");
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
		emailIntent.putExtra(Intent.EXTRA_TEXT,"Hello");
		startActivity(Intent.createChooser(emailIntent, "Email"));

		
	}

	private PdfPTable createFirstTable() {
		// TODO Auto-generated method stub
		 PdfPTable table = new PdfPTable(2);
	        // the cell object
		 /*PdfPCell cell;
	        // we add a cell with colspan 3
	        cell = new PdfPCell(new Phrase("Cell with colspan 3"));--
	        cell.setColspan(3);
	        table.addCell(cell);
	        // now we add a cell with rowspan 2
	        //cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
	        //cell.setRowspan(2);
	        table.addCell(cell);
	        // we add the four remaining cells with addCell()*/
	        table.addCell("row 1; cell 1");
	        table.addCell("row 1; cell 2");
	        table.addCell("row 2; cell 1");
	        table.addCell("row 2; cell 2");
	        
	        /*......................Queries needs to be done................................
	         * 
	         * Select iname,quantity, price from material track;
	         * 
	         * 
	         */
	        return table;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.export, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
