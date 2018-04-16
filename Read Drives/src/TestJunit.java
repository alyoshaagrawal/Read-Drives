import java.time.LocalDateTime;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestJunit {
	   @Test
	   public void testchgdate() {
	      //message = "New Word";
	      assertEquals(LocalDateTime.now().minusDays(5),DrivesListingExample.chgdate(LocalDateTime.now(), 2));
	   }
   }

