import java.util.*;
import java.io.*;
import java.text.*;
import java.lang.*;
import java.util.ArrayList;




public class StockSpan
{
 public static void main( String[] args )
 {


  ArrayList<Double> VALUES = new ArrayList<Double>();
  ArrayList<String> DATES = new ArrayList<String>();
  String str;


  try
  {
    FileInputStream fstream = new FileInputStream("DJIA.csv");
    DataInputStream in = new DataInputStream(fstream);
    BufferedReader b_read = new BufferedReader(new InputStreamReader(in));
    b_read.readLine();


    while ((str = b_read.readLine())!=null)
    {
     StringTokenizer st = new StringTokenizer(str, ",");
     DATES.add(st.nextElement().toString());
     double dle = Double.parseDouble(st.nextElement().toString());
     VALUES.add(dle);
    }
  }
  catch (IOException exc){
    System.out.println("Error:" + exc);
  }
  catch (NumberFormatException e){
    System.out.println("File found Error:" + e);
  }


  int i=0;
  double[] table_values = new double[VALUES.size()];


  for(Double Vrunning : VALUES)
  {
   table_values[i] = Vrunning;
   i=i+1;
  }
  i=0;
  Date[] table_dates = new Date[DATES.size()];


  try {
   for(String Drunning : DATES)
   {
    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
    Date da = dformat.parse(Drunning);
    table_dates[i] = da;
    i=i+1;
   }
  }
  catch (ParseException e){
    e.printStackTrace();
  }


/*Question 1 : Computation of stock span using the simple method. */


 if(args[0].equals("-n"))
  {
    int value_sp1[]=new int[table_dates.length];
    for(i=0; i<table_dates.length; i++)
     {
      int k=1;
      boolean sp_end=false;


      while((i-k>=0) && (!sp_end))
       {
        if(table_values[i-k] <= table_values[i])
          k=k+1;
        else
          sp_end=true;
       }
      value_sp1[i]=k;
     }


  for(i=0; i<table_dates.length; i++)
  {
    SimpleDateFormat cdf = new SimpleDateFormat("yyyy-MM-dd");
    String chronology = cdf.format(table_dates[i]);
    System.out.println( chronology + "," + value_sp1[i]);
  }
 }


/*Question 2 : Computation of stock span using the stack method. */


if(args[0].equals("-s"))
  {
   Stack<Integer> stack = new Stack<Integer>();
   stack.push(0);
   int value_sp2[]=new int[table_dates.length];
   value_sp2[0] = 1;


   for(i=1; i<table_values.length; i++)
    {
      while( (!stack.empty()) && (table_values[stack.peek()])<=(table_values[i]))
       {
        stack.pop();
       }
      if(stack.empty())
       {
        value_sp2[i]=i+1;
       }
      else
       {
        value_sp2[i]=i-stack.peek();
       }
      stack.push(i);
    }
   for(i=0; i<table_dates.length; i++)
    {
     SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
     String chronology = dformat.format(table_dates[i]);
     System.out.println( chronology + "," + value_sp2[i]);
    }
  }


/*Question 3 : Time estimation of the two process before multiplied 100 times. */


if (args[0].equals("-b"))
 {
  long start1, end1, time1, estimate_time1;
  start1 = System.nanoTime();
  for (int j=0;j<100;j++)
    {
     int value_sp1[]=new int[table_dates.length];
     for (i=0; i<table_dates.length; i++)
      {
        int k=1;
        boolean sp_end=false;
        while((i-k>=0) && (!sp_end))
         {
          if(table_values[i-k]<= table_values[i])
           k++;
          else
           sp_end=true;
         }
       value_sp1[i]=k;}
      }
  end1 = System.nanoTime();
  time1 = end1-start1;
  estimate_time1 = time1/1000000;
  System.out.println("naive implemantation took : " + estimate_time1 + " milliseconds");


  long start2, end2, time2, estimate_time2;


  start2 = System.nanoTime();
  for(int a=0;a<100;a++)
   {
     int value_sp2[]=new int[table_dates.length];
     Stack<Integer> stack = new Stack<Integer>();
     stack.push(0);
     value_sp2[0] = 1;


     for(i=1; i<table_values.length; i++)
      {
        while( (stack.empty()==false) && (table_values[stack.peek()])<=(table_values[i]))
         {
           stack.pop();
         }
        if(stack.empty())
          value_sp2[i]=i+1;
        else
          value_sp2[i]=i-stack.peek();
       stack.push(i);
      }
    }


    end2 = System.nanoTime();
    time2 = end2-start2;
    estimate_time2 = time2/1000000;
    System.out.println("stack implemantation took : " + estimate_time2 + " milliseconds");
  }
 }
}