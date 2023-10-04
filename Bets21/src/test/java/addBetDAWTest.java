import static org.junit.Assert.*;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Event;
import domain.Erregistratua;
import domain.Aukera;
import domain.Apustua;
import test.dataAccess.TestDataAccess;

public class addBetDAWTest {

	//sut:system under test
		 static DataAccess sut=new DataAccess();
		 
		 //additional operations needed to execute the test 
		 static TestDataAccess testDA=new TestDataAccess();

		private Event ev;
		
		
		@Test
		//DB-n apustua gehitzen da. Jarraitzailerik ez du.
		public void test() {
			Erregistratua erab = new Erregistratua("aa", "bb", "111111111A", 2); 
			Aukera auk = new Aukera(1,"Atletico",2);
			try {
				testDA.open();
				testDA.persist(erab);
				testDA.persist(auk);
				testDA.close();
				Apustua apustua = new Apustua("erab", 1);
				sut.addBet(auk, apustua);
				assertTrue(true);
			}catch(Exception a) {
				fail("Not yet implemented");
			}finally {
				testDA.open();
				testDA.removeErreg(erab);
				testDA.removeAuk(auk);
				testDA.close();
			}
		}
		
		@Test
		//DB-n apustua gehitzen da. Jarraitzaile bat du (banneatuta edo dirurik gabe)
		public void test2() {
			Erregistratua erab = new Erregistratua("aa", "bb", "111111111A", 2); 
			Erregistratua erab2 = new Erregistratua("bb", "nn", "222222222A", 0.5);
			Aukera auk = new Aukera(1,"Atletico",2);
			try {
				testDA.open();
				testDA.persist(erab2);
				testDA.persist(erab);
				testDA.persist(auk);
				testDA.close();
				erab.addJarraitzailea(erab2.getUser());
				Apustua apustua = new Apustua("erab", 1);
				sut.addBet(auk, apustua);
				if(erab2.getApustuak().size()==0) {
					assertTrue(true);
				}else {
					fail("Ez du apusturik izan behar");
				}
			}catch(Exception a) {
				fail("Errorea");
			}finally {
				testDA.open();
				testDA.removeErreg(erab);
				testDA.removeAuk(auk);
				testDA.close();
			}
		}

}
