package businessLogic;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.User;
import domain.Admin;
import domain.Apustua;
import domain.Aukera;
import domain.Erregistratua;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
		    dbManager.initializeDB();
		    } else
		     dbManager=new DataAccess();
		dbManager.close();

		
	}
	
public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager=da;		
	}
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   @WebMethod
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	 //The minimum bed must be greater than 0
	 		dbManager.open(false);
	 		Question qry=null;
	 		
	 	    
	 		if(new Date().compareTo(event.getEventDate())>0)
	 			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
	 				
	 		
	 		 qry=dbManager.createQuestion(event,question,betMinimum);		

	 		dbManager.close();
	 		
	 		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    @WebMethod	
	public Vector<Event> getEvents(Date date)  {
    	dbManager.open(false);
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}
    
    
    //KLASEAN EGINDAKOA*********************************************************************
    
    
    
    @WebMethod public User isLogin(String log, String password) {
    	dbManager.open(false);
		User user = dbManager.isLogin(log,password);
		dbManager.close();
		return user;
    	
    }
    
    
    @WebMethod public void Register(String log, String password, String NAN, int kontuDirua) {
    	dbManager.open(false);
		dbManager.Register(log,password,NAN,kontuDirua);
		dbManager.close();
	
    }
    
    
    @WebMethod public boolean UserExistitzenDa(String user) {
    	dbManager.open(false);
		boolean emaitza = dbManager.UserExistitzenDa(user);
		dbManager.close();
		return emaitza;
    }
    
    
    @WebMethod
	public Question getQuestion(Event event, String s1, float min) {
    	dbManager.open(false);
		Question question = dbManager.findQuestion(event,s1);
		dbManager.close();
		return question;
	}
     
    
    @WebMethod
	public void setKuota(Question q, String opt, float zenbat) {
		Aukera auk = new Aukera(opt,q,zenbat);
		dbManager.open(false);
		dbManager.newKuota(q,auk);
		dbManager.close();
	}
    
    @WebMethod
    public Vector<Aukera> getAukerak(Question q) {
    	dbManager.open(false);
		Vector<Aukera> aukerak = dbManager.getAukerak(q);
		dbManager.close();
		return aukerak;
	}

    @WebMethod
	public void createEvent(Integer eventNumber, String description, Date eventDate) {
		dbManager.open(false);
		dbManager.createEvent(eventNumber, description, eventDate);
		dbManager.close();
	}
	
    @WebMethod
	public void addMoney(String log, double diruKop) {
		dbManager.open(false);
		dbManager.addMoney(log, diruKop);
		dbManager.close();
	}
	
    @WebMethod
	public void addBet(Aukera hautatutakoAukera, Apustua apustu) {
		dbManager.open(false);
		dbManager.addBet(hautatutakoAukera,apustu);
		dbManager.close();
	}
	
    @WebMethod
	public Erregistratua erregistratuaItzuli(String log) {
		dbManager.open(false);
		Erregistratua erreg=dbManager.erregistratuaItzuli(log);
		dbManager.close();
		return erreg;
	}
	
    @WebMethod
	public void emaitzaGehitu(Question q, String emaitza) {
		dbManager.open(false);
		dbManager.emaitzaGehitu(q, emaitza);
		dbManager.close();
	}
	
    @WebMethod
	public User userItzuli(String log) {
		dbManager.open(false);
		User u=dbManager.userItzuli(log);
		dbManager.close();
		return u;
	}
    
    @WebMethod
    public Admin adminaItzuli() {
    	dbManager.open(false);
    	Admin a=dbManager.adminaItzuli();
    	dbManager.close();
    	return a;
    }
    
    @WebMethod
    public void gehituJarraitzailea(Erregistratua bilatua,Erregistratua unekoa) {
    	dbManager.open(false);
    	dbManager.gehituJarraitzailea(bilatua, unekoa);
    	dbManager.close();
    }
    
    @WebMethod
    public void ezabatuJarraitzailea(Erregistratua bilatua,Erregistratua unekoa) {
    	dbManager.open(false);
    	dbManager.ezabatuJarraitzailea(bilatua, unekoa);
    	dbManager.close();
    }
    
    @WebMethod
    public void mezuaBidaliAdminari(Date data,Erregistratua bidaltzailea,String mezua) {
    	dbManager.open(false);
    	dbManager.mezuaBidaliAdminari(data,bidaltzailea,mezua);
    	dbManager.close();
    }
    
    @WebMethod
    public void mezuaErantzunErregistratuari(Date data, Admin admin, String bidaltzailea,String mezua) {
    	dbManager.open(false);
    	dbManager.mezuaErantzunErregistratuari(data, admin, bidaltzailea, mezua);
    	dbManager.close();
    }
    
    @WebMethod
    public Vector<Event> getAllEvents(){
    	dbManager.open(false);
    	Vector<Event> ema=dbManager.getAllEvents();
    	dbManager.close();
    	return ema;
    }
    
    @WebMethod
    public void duplicateEvent(int eventNumBerria, Event event1,Date eventDate) {
    	dbManager.open(false);
    	dbManager.duplicateEvent(eventNumBerria,event1,eventDate);
    	dbManager.close();
    }
    
    @WebMethod
    public void banUser(String erabiltzailea, Date blokeoBukaeraData) {
    	dbManager.open(false);
    	dbManager.banUser(erabiltzailea,blokeoBukaeraData);
    	dbManager.close();
    }


}

