package DataObjects;

import org.junit.Assert;
import org.junit.Test;

public class SaleTest {

    @Test
    public void shouldThrowExceptionIfDateIsNotOfCorrectFormat(){
        // format should be DD-MM-YYYY
        try{
            new Sale("saleid", 12, "NotADate",
                    1, Float.parseFloat("12.22"), "PROCESSED");

        } catch (Exception e){ // should throw exception
            return;
        }
        // did not throw exception
        Assert.fail();
    }
}
