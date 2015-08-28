package controllers;

import static play.data.Form.form;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import models.Document;
import pl.edu.pwr.litmap.objectrecognize.ObjectRecognize;
import pl.edu.pwr.litmap.textobjects.FakeTextobject;
import pl.edu.pwr.litmap.textobjects.NameClass;
import pl.edu.pwr.litmap.textobjects.Text;
import pl.edu.pwr.litmap.textobjects.Textobject;
import play.cache.Cache;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import views.html.documentcreate;

public class DocumentManager extends Controller {
	
    /**
     * Defines a form wrapping the Document class.
     */ 
    final static Form<Document> createForm = form(Document.class);
    
    public static Result create() {
        return ok(documentcreate.render(createForm));
    }
    
    public static Result submit() {
        Form<Document> documentForm = createForm.bindFromRequest();
        if (documentForm.hasErrors()) {
            return badRequest(documentcreate.render(documentForm));
        } else {
        	Document document = documentForm.get();
            document.save();
            return redirect(routes.Application.map());
        }
    }
    
    public static Result submitApi() {
        Form<Document> documentForm = createForm.bindFromRequest();
        if (documentForm.hasErrors()) {
            return ok(Json.toJson("ERROR_VALIDATION"));
        } else {
        	Document document = documentForm.get();
            document.save();
            return ok(Json.toJson(document.id));
        }
    }
    
    public static Result getAll() {
    	List<Document> documents = new Model.Finder(Long.class, Document.class).all();
    	return ok(Json.toJson(documents));
    }
    
    public static Result getById(long id) {
    	Document document = Document.find.byId(id);
    	if (document == null) {
    		return Results.notFound("Document with id = "+id+" does not exists.");
    	}
    	return ok(Json.toJson(document));
    }
    
    public static Result getObjectsByDocumentId(long id) {
    	Document document = Document.find.byId(id);
    	
    	if (document == null) {
    		return Results.notFound("Document with id = "+id+" does not exists.");
    	}
    	
    	// brak cache'owania, aby ponownie próbował odpytać usługę lokalizacyjną dla obiektów z OverQueryLimitException
        String textText = document.plainText;
        
        ObjectRecognize or = new ObjectRecognize();
        Text resultText = null;
        try {
        	resultText = or.parseText(textText);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            return Results.internalServerError("Błąd podczas przetwarzania żądania.\n"+ex);
        }
        if (resultText == null) {
        	return Results.internalServerError("Błąd podczas przetwarzania żądania.\nresult is null");
        }
        Result result = ok(Json.toJson(resultText.getJsonWrapperTextobjectAsArray()));

    	return result;
    }
    
    public static Result getObjectsByText() {
    	final Map<String, String[]> values = request().body().asFormUrlEncoded();
    	if (!values.containsKey("text") || values.get("text").length == 0) {
    		return Results.badRequest("Missing argument 'text'.");
    	}
    	String textText = values.get("text")[0];
        
        ObjectRecognize or = new ObjectRecognize();
        Text resultText = null;

        try {
        	resultText = or.parseText(textText);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            return Results.internalServerError("Błąd podczas przetwarzania żądania.\n"+ex);
        }
        if (resultText == null) {
        	return Results.internalServerError("Błąd podczas przetwarzania żądania.\nresult is null");
        }
		long timeStartHeuristicsAndLocationRetrieve = Calendar.getInstance().getTimeInMillis();
        Result result = ok(Json.toJson(resultText.getJsonWrapperTextobjectAsArray()));
        
        long totalTimeHeuristics = Calendar.getInstance().getTimeInMillis() - timeStartHeuristicsAndLocationRetrieve;
        System.out.println("Total time for recognize objects and location retrieving: "+totalTimeHeuristics+" ms.");

    	return result;
    }
    
    public static Result addObjectsByTextProcess() {
    	final Map<String, String[]> values = request().body().asFormUrlEncoded();
    	if (!values.containsKey("text") || values.get("text").length == 0) {
    		return Results.badRequest("Missing argument 'text'.");
    	}
    	String textText = values.get("text")[0];
        
        ObjectRecognize or = new ObjectRecognize();
        Text resultText = null;

        try {
        	resultText = or.parseText(textText);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            return Results.internalServerError("Błąd podczas przetwarzania żądania.\n"+ex);
        }
        if (resultText == null) {
        	return Results.internalServerError("Błąd podczas przetwarzania żądania.\nresult is null");
        }
		long timeStartHeuristicsAndLocationRetrieve = Calendar.getInstance().getTimeInMillis();
        Result result = ok(Json.toJson(resultText.getJsonWrapperTextobjectAsArray()));
        
        long totalTimeHeuristics = Calendar.getInstance().getTimeInMillis() - timeStartHeuristicsAndLocationRetrieve;
        System.out.println("Total time for recognize objects and location retrieving: "+totalTimeHeuristics+" ms.");

    	return result;
    }
    
//    public static Result getParsedDocumentId(long id) {
//    	Document document = Document.find.byId(id);
//    	
//        String textText = document.plainText;
//        
//        ObjectRecognize or = new ObjectRecognize();
//        Text result = null;
//        try {
//            result = or.parseText(textText);
//        } catch (IOException ex) {
//            java.util.logging.Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
//            return Results.internalServerError("Błąd podczas przetwarzania żądania.\n"+ex);
//        }
//        if (result == null) {
//        	return Results.internalServerError("Błąd podczas przetwarzania żądania.\nresult is null");
//        }
//        return ok(Json.toJson(result));
//    }
    
    public static Result fakeGetObjectsByDocumentId(long id) {
    	if (id == 4) {
	    	Document document = new Document("Trasa koncertowa", "Gdzie Dawid Podsiadło zagra koncerty w 2014 roku? "
	    			+ "Jeden z debiutantów 2013 jest właśnie w trakcie dwumiesięcznej trasy koncertowej po Polsce. "
	    			+ "W lutym laureat X-Factora zagrał w Chełmie, Lublinie i Puławach. "
	    			+ "Przed nami koncerty we Wrocławiu, Warszawie, Łodzi, Poznaniu, Krakowie czy Opolu.");
	    	Text text = new pl.edu.pwr.litmap.textobjects.Text();
	    	Textobject[] tos = {
	    			new FakeTextobject(text, NameClass.PERSON_NAM, "Dawid Podsiadło", "Dawid Podsiadło"),
	    			new FakeTextobject(text, NameClass.COUNTRY_NAM, "Polsce", "Polska"),
	    			new FakeTextobject(text, NameClass.TITLE_TV_NAM, "X-Factora", "X-Factor"),
	    			new FakeTextobject(text, NameClass.CITY_NAM, "Chełmie", "Chełm"),
	    			new FakeTextobject(text, NameClass.CITY_NAM, "Lublinie", "Lublin"),
	    			new FakeTextobject(text, NameClass.CITY_NAM, "Puławach", "Puławy"),
	    			new FakeTextobject(text, NameClass.CITY_NAM, "Wrocławiu", "Wrocław"),
	    			new FakeTextobject(text, NameClass.CITY_NAM, "Warszawie", "Warszawa"),
	    			new FakeTextobject(text, NameClass.CITY_NAM, "Łodzi", "Łódź"),
	    			new FakeTextobject(text, NameClass.CITY_NAM, "Poznaniu", "Poznań"),
	    			new FakeTextobject(text, NameClass.CITY_NAM, "Krakowie", "Kraków"),
	    			new FakeTextobject(text, NameClass.CITY_NAM, "Opolu", "Opole"),
	    	};
	        return ok(Json.toJson(tos));
    	} else {
	    	Document document = new Document("Testowy dokument nr 1", "Pani Ala Kowalska, Sebastian Nowak i Zdzisław mieszkają we Wrocławiu.");
	    	Text text = new pl.edu.pwr.litmap.textobjects.Text();
	    	Textobject[] tos = {
	    			new FakeTextobject(text, NameClass.PERSON_NAM, "Ala Kowalska", "Ala Kowalska"),
	    			new FakeTextobject(text, NameClass.PERSON_NAM, "Sebastian Nowak", "Sebastian Nowak"),
	    			new FakeTextobject(text, NameClass.CITY_NAM, "Wrocławiu", "Wrocław"),
	    			new FakeTextobject(text, NameClass.PERSON_NAM, "Zdzisław", "Zdzisław"),
	    	};
	        return ok(Json.toJson(tos));
    	}
        
    }
    
    public static Result saveDocument() {
    	final Map<String, String[]> values = request().body().asFormUrlEncoded();
    	String text_pl = values.get("txt_pl")[0];
    	String text_en = values.get("txt_en")[0];
    	
    	Calendar calendar = Calendar.getInstance();
    	int hours = calendar.get(Calendar.HOUR_OF_DAY);
    	int minutes = calendar.get(Calendar.MINUTE);
    	int seconds = calendar.get(Calendar.SECOND);
    	
    	String filname_prefix = "/home/wgawel/share/Dropbox/Dokumenty/Moje dokumenty/studia/pwr/praca dyplomowa/teksty do testów/extracted/"
    			+hours+minutes+seconds+"-";
    	
		try {
	    	PrintWriter out_pl;
			out_pl = new PrintWriter(filname_prefix+"pl.txt");
	    	out_pl.println(text_pl);
	    	out_pl.close();

	    	PrintWriter out_en;
			out_en = new PrintWriter(filname_prefix+"en.txt");
	    	out_en.println(text_en);
	    	out_en.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


    	return ok("OK");
    }

}
