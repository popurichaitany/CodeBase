/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplelinearregression;
/**
 *
 * @author anand
 */
import MatrixManip.Matrix;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCount {
    static String[] arg;
  
    public static class MlrMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable wrt =  new IntWritable(1);
        private Text txt = new Text();
        @Override
        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException{
                String ln = value.toString();
                StringTokenizer strTokenizer = new StringTokenizer(ln);
                while(strTokenizer.hasMoreTokens()){
                    txt.set(strTokenizer.nextToken());
                    output.collect(txt, wrt);
                }
        }
    }
    public static class MlrReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>{
        @Override
        public void reduce(Text key, Iterator<IntWritable> values,
            OutputCollector<Text, IntWritable> output, Reporter reporter)
            throws IOException{
                int summation = 0;
                while (values.hasNext()){
                    summation += values.next().get();
                }
                output.collect(key, new IntWritable(summation));
        }
    }
    public static void main(String[]args) throws IOException{
       Matrix X = new Matrix(new double[][]{{2.5,0,25},{2.55,1,25},{6,1,25.25},{10,0,25.75},{10,0,25.75},{0.3,0,19},{0.5,0,21},{2.5,0,25},{2.55,1,25},{6,0,25.25},{10,0,25.75},{10,0,25.75}});
       Matrix Y;
       double predict[][]=new double[12][3];
        
        try{
                    File counterOpDir=new File("//home//anand//Desktop/Output//");
                    if(counterOpDir.exists()){
                    File[] flist = counterOpDir.listFiles();       
                    for (int n = 0; n < flist.length; n++) {
                        if (flist[n].isFile()) {
                        System.gc();
                        Thread.sleep(1800);
                        flist[n].delete();
                        }
                    }   
                    System.gc();
                    Thread.sleep(2000);
                    counterOpDir.delete();
                    File prevDelt=new File("//home//anand//Desktop//mlrOutput.txt");
                    if(prevDelt.exists())
                    {
                        System.gc();
                        Thread.sleep(1800);
                        prevDelt.delete();
                    }
                    }
        }catch(InterruptedException ie){
                
        }
        GenericOptionsParser parser = new GenericOptionsParser(args);
        arg = parser.getRemainingArgs();
        JobConf jConf = new JobConf(WordCount.class);
        jConf.setJobName("monthlycount");
        jConf.setOutputKeyClass(Text.class);
        jConf.setOutputValueClass(IntWritable.class);
        jConf.setMapperClass(MlrMapper.class);
        jConf.setReducerClass(MlrReducer.class);
        jConf.setInputFormat(TextInputFormat.class);
        jConf.setOutputFormat(TextOutputFormat.class);
        FileInputFormat.setInputPaths(jConf, new Path(arg[0]));
        FileOutputFormat.setOutputPath(jConf, new Path(arg[1]));
                try{
                    JobClient.runJob(jConf);
                    File outputCount=new File("//home//anand//Desktop//Output//part-00000");
                    try (BufferedReader br = new BufferedReader(new FileReader(outputCount))) {
                        String line;
                        String v[]=new String[4];
                        while ((line = br.readLine()) != null) {
                            if(line.contains("January")){
                                line=line.replaceAll("\\s+", " ");
                                v=line.split(" ");
                                predict[0][0]=Double.parseDouble(v[1]);
                                predict[0][0]=predict[0][0]/2;
                            }
                            else if(line.contains("February")){
                                line=line.replaceAll("\\s+", " ");
                                v=line.split(" ");
                                predict[1][0]=Double.parseDouble(v[1]);
                                predict[1][0]=predict[1][0]/2;
                            }
                            else if(line.contains("March")){
                                line=line.replaceAll("\\s+", " ");
                                v=line.split(" ");
                                predict[2][0]=Double.parseDouble(v[1]);
                                predict[2][0]=predict[2][0]/2;
                            }
                            else if(line.contains("April")){
                                line=line.replaceAll("\\s+", " ");
                                v=line.split(" ");
                                predict[3][0]=Double.parseDouble(v[1]);
                                predict[3][0]=predict[3][0]/2;
                            }
                            else if(line.contains("May")){
                                line=line.replaceAll("\\s+", " ");
                                v=line.split(" ");
                                predict[4][0]=Double.parseDouble(v[1]);
                                predict[4][0]=predict[4][0]/2;
                            }
                            else if(line.contains("June")){
                                line=line.replaceAll("\\s+", " ");
                                v=line.split(" ");
                                predict[5][0]=Double.parseDouble(v[1]);
                                predict[5][0]=predict[5][0]/2;
                            }
                            else if(line.contains("July")){
                                line=line.replaceAll("\\s+", " ");
                                v=line.split(" ");
                                predict[6][0]=Double.parseDouble(v[1]);
                                predict[6][0]=predict[6][0]/2;
                            }
                            else if(line.contains("August")){
                                line=line.replaceAll("\\s+", " ");
                                v=line.split(" ");
                                predict[7][0]=Double.parseDouble(v[1]);
                                predict[7][0]=predict[7][0]/2;
                            }
                            else if(line.contains("September")){
                                line=line.replaceAll("\\s+", " ");
                                v=line.split(" ");
                                predict[8][0]=Double.parseDouble(v[1]);
                                predict[8][0]=predict[8][0]/2;
                            }
                            else if(line.contains("October")){
                                line=line.replaceAll("\\s+", " ");
                                v=line.split(" ");
                                predict[9][0]=Double.parseDouble(v[1]);
                                predict[9][0]=predict[9][0]/2;
                            }
                            else if(line.contains("November")){
                                line=line.replaceAll("\\s+", " ");
                                v=line.split(" ");
                                predict[10][0]=Double.parseDouble(v[1]);
                                predict[10][0]=predict[10][0]/2;
                            }
                            else if(line.contains("December")){
                                line=line.replaceAll("\\s+", " ");
                                v=line.split(" ");
                                predict[11][0]=Double.parseDouble(v[1]);
                                predict[11][0]=predict[11][0]/2;
                            }
                        }
                        double beta0=0,beta1=0,beta2=0,beta3=0;
                        double mlrPrediction[][]=new double[12][1];
                        String month[]={"Jan-16","Feb-16","Mar-16","Apr-16","May-16","Jun-16","Jul-16","Aug-16","Sep-16","Oct-16","Nov-16","Dec-16"};
                        double p;
                        Y=new Matrix(predict);
                        MLR ml = new MLR(X, Y);
                        Matrix result = ml.calculate();
                        double oput[][]=result.getValues();
                        for(int j=0;j<result.getRowCount();j++){
                            for(int k=0;k<result.getColCount();k++){
                                if(k==0 && j==0)
                                    beta0=oput[j][k];
                                if(k==0 && j==1)
                                    beta1=oput[j][k];
                                if(k==0 && j==2)
                                    beta2=oput[j][k];
                                if(k==0 && j==3)
                                    beta3=oput[j][k];
                                    }
                        }
                        PrintWriter pw = new PrintWriter("//home//anand//Desktop//mlrOutput.txt", "UTF-8");
                        pw.println("Three factors affecting library study rooms' booking are:");
                        pw.println("1. Previous year M-o-M usage index (0 to 10)");
                        pw.println("2. Particular month in 2016 starts with weekend or not (0/1)");
                        pw.println("3. Average age of students eligible for studyroom usage (17-35)");
                        pw.println("\n Corresponding 12 triads observed to be:");
                        pw.println("{2.5,0,25},{2.55,1,25},{6,1,25.25},{10,0,25.75},{10,0,25.75},{0.3,0,19},{0.5,0,21},{2.5,0,25},{2.55,1,25},{6,0,25.25},{10,0,25.75},{10,0,25.75}");
                        pw.println("\nMultiple linear Regression coefficient are:\nBeta0:"+beta0+"\nBeta1:"+beta1+"\nBeta2:"+beta2+"\nBeta3:"+beta3);
                        pw.println("Multiple linear regression based output:");
                        pw.println("Month\t\tBookings\tMLR prediction");
                        for(int k=0;k<X.getRowCount();k++){
                            mlrPrediction[k][0]=beta0+(beta1*X.getValueAt(k,0))+(beta2*X.getValueAt(k, 1))+(beta3*X.getValueAt(k,2));
                            pw.println(month[k]+"\t\t"+Y.getValueAt(k, 0)+"\t\t"+Math.floor(mlrPrediction[k][0]));
                        }
                        pw.close();
                    } catch(Exception e){
                        System.out.println("In catch:"+e.getMessage());
                    }
                }catch(IOException e){
                    System.err.println(e.getMessage());
                } finally{

                }  
        
    }
}
