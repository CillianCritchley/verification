package cm;

import com.sun.istack.internal.localization.NullLocalizable;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class OCriothaileCillianTestTask2 {

    private  BigDecimal normalRate;
    private  BigDecimal reducedRate;
    private ArrayList<Period> normalPeriod;
    private ArrayList<Period> reducedPeriod;

    @Before
    public void setUp() throws Exception {
      normalRate = new BigDecimal(10);
        reducedRate = new BigDecimal(6);
       normalPeriod = new ArrayList<Period>(Arrays.asList(new Period(1,2)));
      reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(4,5)));
    }

    @Test
    public void test_1_all_normal(){


        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
    }


    @Test
    public void test_2_normalrate_0(){

        BigDecimal normalRate = new BigDecimal(0);

        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
    }

    @Test
    public void test_3_reducedrate_0(){

        BigDecimal reducedRate = new BigDecimal(0);

        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
    }

    @Test
    public void test_4_reducedperiod_start_0(){
        ArrayList<Period>  reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(0,3)));

        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
    }

    @Test
    public void test_5_normalperiod_start_0(){
         normalPeriod = new ArrayList<Period>(Arrays.asList(new Period(0,5)));
         reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(6,7)));


        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_5_reducedrate_less_than_0(){
        BigDecimal reducedRate = new BigDecimal(-1);

        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_6_normalrate_less_than_0(){
        BigDecimal normalRate = new BigDecimal(-1);

        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_7_reducedperiod_start_time_less_than_0(){
        reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(-1,5)));
        normalPeriod = new ArrayList<Period>(Arrays.asList(new Period(6,8)));

        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_8_normalperiod_start_time_less_than_0(){
          reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(-1,5)));
         normalPeriod = new ArrayList<Period>(Arrays.asList(new Period(6,8)));

        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_9_normal_and_reduced_periods_overlap(){
        reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(1,5), new Period(9,10)));
        normalPeriod = new ArrayList<Period>(Arrays.asList(new Period(4,8)));

        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_10_reduced_periods_overlap(){
      reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(1,3), new Period(2,4)));
         normalPeriod = new ArrayList<Period>(Arrays.asList(new Period(5,8)));

        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_11_normal_periods_overlap(){
         reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(1,5)));
          normalPeriod = new ArrayList<Period>(Arrays.asList(new Period(6,8), new Period(7,9), new Period(11,12)));

        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
    }


    @Test
    public void test_12_calculate_correct(){
         reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(1,5)));
         normalPeriod = new ArrayList<Period>(Arrays.asList( new Period(7,9)));

        Rate theRate = new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
        assertEquals(theRate.calculate(new Period(4,8)),16);
    }

    @Test
    public void test_13_calculate_free(){
         reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(1,5)));
         normalPeriod = new ArrayList<Period>(Arrays.asList( new Period(7,9)));

        Rate theRate = new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
        assertEquals(theRate.calculate(new Period(11,18)),0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_14_null_normalRate(){

        new Rate(CarParkKind.VISITOR, null, reducedRate, reducedPeriod, normalPeriod);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_15_null_reducedRate(){

        new Rate(CarParkKind.VISITOR, normalRate, null, reducedPeriod, normalPeriod);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_16_null_normalPeriod(){

        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_17_null_reducedPeriod(){

        new Rate(CarParkKind.VISITOR, normalRate, reducedRate, null, normalPeriod);
    }


    @Test
    public void test_18_calculate_visitor_free_up_to_8_euro(){
        reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(1,5)));
        normalPeriod = new ArrayList<Period>(Arrays.asList( new Period(7,9)));
        normalRate = new BigDecimal(4);
        Rate theRate = new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
        assertEquals(theRate.calculate(new Period(7,9)),0);
    }


    @Test
    public void test_19_calculate_visitor_discount_after_8_euro_normal_rate(){
        reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(1,5)));
        normalPeriod = new ArrayList<Period>(Arrays.asList( new Period(7,11)));
        normalRate = new BigDecimal(4);
        Rate theRate = new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
        assertEquals(theRate.calculate(new Period(7,11)),4);
    }


    @Test
    public void test_20_calculate_visitor_discount_after_8_euro_reduced_rate(){
        reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(1,5)));
        normalPeriod = new ArrayList<Period>(Arrays.asList( new Period(7,11)));
        reducedRate = new BigDecimal(4);
        Rate theRate = new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
        assertEquals(theRate.calculate(new Period(1,5)),4);
    }

    @Test
    public void test_21_calculate_visitor_discount_after_8_euro_both_rates(){
        reducedPeriod = new ArrayList<Period>(Arrays.asList(new Period(1,7)));
        normalPeriod = new ArrayList<Period>(Arrays.asList( new Period(7,11)));
        reducedRate = new BigDecimal(4);
        normalRate = new BigDecimal(8);
        Rate theRate = new Rate(CarParkKind.VISITOR, normalRate, reducedRate, reducedPeriod, normalPeriod);
        assertEquals(theRate.calculate(new Period(5,9)),8);
    }

}