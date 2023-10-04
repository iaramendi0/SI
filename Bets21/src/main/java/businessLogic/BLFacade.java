package businessLogic;
//a
import java.util.Vector;
import java.util.Date;





//import domain.Booking;
import domain.Question;
import domain.User;
import domain.Admin;
import domain.Apustua;
import domain.Aukera;
import domain.Erregistratua;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

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
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	
	//KLASEAN EGINDAKOA********************************************************************
	
	
	@WebMethod public User isLogin(String log, String password);
	
	
	@WebMethod public void Register(String log, String password, String NAN, int kontuDirua);
	
	
	@WebMethod public boolean UserExistitzenDa(String user);
	
	
	@WebMethod public Question getQuestion(Event event, String s1, float min);
	
	
	@WebMethod public void setKuota(Question question, String auk, float zenbat);
	

	@WebMethod public Vector<Aukera> getAukerak(Question question);
	
	
	@WebMethod public void createEvent(Integer eventNumber, String description,Date eventDate);
	
	@WebMethod public void addMoney(String log, double diruKop);
	
	@WebMethod public void addBet(Aukera hautatutakoAukera, Apustua apustu);
	
	@WebMethod public Erregistratua erregistratuaItzuli(String log);
	
	@WebMethod public void emaitzaGehitu(Question q, String emaitza);
	
	@WebMethod public User userItzuli(String log);
	
	@WebMethod public Admin adminaItzuli();
	
	@WebMethod public void gehituJarraitzailea(Erregistratua bilatua,Erregistratua unekoa);
	
	@WebMethod public void ezabatuJarraitzailea(Erregistratua bilatua,Erregistratua unekoa);
	
	@WebMethod public void mezuaBidaliAdminari(Date data,Erregistratua bidaltzailea,String mezua);
	
	@WebMethod public void mezuaErantzunErregistratuari(Date data,Admin admin,String bidaltzailea,String mezua);
	
	@WebMethod public Vector<Event> getAllEvents();
	
	@WebMethod public void duplicateEvent(int eventNumBerria, Event event1,Date eventDate);
	
	@WebMethod public void banUser(String erabiltzailea, Date blokeoBukaeraData);

}
