import sun.awt.AWTAccessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by nathan on 2/22/2016.
 */
public class pb_mine {
    private static char[][] map= new char[22][88];
    private static String[]text= new String[100];
    private static String[] map_points = new String[200];
    private static String[] move_jumps ={"1_0","1_11","8_11","8_4","19_4","19_0","8_29","19_29","19_45","0_45","19_61","8_61","8_88",};
    private static int pos=0;

    public static void main(String[] args) {
        getText();
        dlMap();
        run();


    }

    private static void getMap(){
        int i=0;
        int j=0;
        try {
            Scanner in = new Scanner(new File("map.txt"));
            while(in.hasNext()){
                map[j]=(in.next()).toCharArray();
               // System.out.println(map[j]);
                j++;
            }
            //System.out.println(map[1][0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    private static void printMap(){
        for(char[] a: map){
            for(char b:a){
                System.out.print(b);
            }
            System.out.println();
        }
    }
    private static void getText(){
        int ind =0;
        try {
            Scanner in =new Scanner(new File("text.txt"));
            while(in.hasNext()){
                String inp=in.nextLine();
                if(inp.contains("_ref")) {
                    String index = inp.split("_")[0];
                    index= index.split("<")[1];
                    //System.out.print(index);
                    ind = Integer.parseInt(index);
                }else{
                    if(!(text[ind]==null)) {
                        text[ind]=text[ind]+"\n"+inp;
                    }else{
                        text[ind]=inp;
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    private static void test(){
        map[1][0]='@';
        map[1][11]='@';
        map[8][11]='@';
        map[8][4]='@';
        map[8][29]='@';
        map[19][29]='@';
        map[19][4]='@';
        map[19][0]='@';
        map[19][45]='@';
        map[0][45]='@';
        map[19][61]='@';
        map[8][61]='@';
        map[8][88]='@';
    }
    private static void move(String in){
        int mpos[]= new int[2];
        mpos[0]= Integer.parseInt(move_jumps[pos].split("_")[0]);
        mpos[1]= Integer.parseInt(move_jumps[pos].split("_")[1]);
        map[mpos[0]][mpos[1]]='*';
        switch (pos){
            case 0: pos=(in.equalsIgnoreCase("w"))?1:0;
                break;
            case 1: pos=(in.equalsIgnoreCase("d")?2:1);
                break;
            case 2: pos=(in.equalsIgnoreCase("a")?6:in.equalsIgnoreCase("d")?3:2);
                break;
            case 3: pos=(in.equalsIgnoreCase("a")?4:3);
                break;
            case 4: pos=(in.equalsIgnoreCase("d")?5:4);
                break;
            case 5: System.out.print("you got out");
                break;
            case 6: pos=(in.equalsIgnoreCase("d")?7:6);
                break;
            case 7: pos=(in.equalsIgnoreCase("a")?8:7);
                break;
            case 8: pos=(in.equalsIgnoreCase("a")?9:in.equalsIgnoreCase("w")?10:8);
                break;
            case 9: System.out.print("you got out");
                break;
            case 10: pos=(in.equalsIgnoreCase("a")?11:10);
                break;
            case 11: pos=(in.equalsIgnoreCase("d")?12:11);
                break;
            case 12: System.out.print("you got out");

            }
            mpos[0]= Integer.parseInt(move_jumps[pos].split("_")[0]);
            mpos[1]= Integer.parseInt(move_jumps[pos].split("_")[1]);
        map[mpos[0]][mpos[1]]='@';
        }
    private static void run(){
        while(pos!=600){
            Scanner in = new Scanner(System.in);
            System.out.println(text[pos]);
            String inp =in.nextLine();
            if(inp.equalsIgnoreCase("map")){
                printMap();
            }else {
                dlmove(inp);
            }
            /*if(pos==5||pos==9||pos==12)
                pos=600;*/
        }
    }
    private static void dlMap(){
        int k=1;
        int j=0;
        try {
            Scanner in = new Scanner(new File("mapt.txt"));
            while(in.hasNext()){
                String inp=(in.next());
                int i=0;
                for(char a: inp.toCharArray()){
                    if(a=='@'){
                        map_points[0]=j+"_"+i;
                    }
                    if(a=='p'){
                        map_points[k]=j+"_"+i;
                        k++;
                    }
                    i++;
                }
                inp=inp.replaceAll("p","*");
                map[j]=(inp.toCharArray());
                j++;
            }
            System.out.println(map_points[3]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    private static void dlmove(String in){
        if(in.equalsIgnoreCase("w")){
            int mpos[]= new int[2];
            int or = mpos[1]= Integer.parseInt(map_points[pos].split("_")[1]);
            mpos[0]= Integer.parseInt(map_points[pos].split("_")[0]);
            mpos[1]= Integer.parseInt(map_points[pos].split("_")[1]);
            map[mpos[0]][mpos[1]]='*';
            boolean done = false;
            int h=(mpos[0]=Integer.parseInt(map_points[pos].split("_")[0]))-1;
            mpos[1]= Integer.parseInt(map_points[pos].split("_")[1]);
            System.out.println(map[mpos[0]-1][mpos[1]]=='*');
            if(map[mpos[0]-1][mpos[1]]=='*'){
                for(; h<23 ;h--){
                    if(done){
                        break;
                    }
                    for(int k=0;(k<map_points.length)&&(map_points[k]!=null);k++){
                        mpos[0]= Integer.parseInt(map_points[k].split("_")[0]);
                        mpos[1]= Integer.parseInt(map_points[k].split("_")[1]);
                        if(h==mpos[0]&&(or==mpos[1])){
                            mpos[0]= Integer.parseInt(map_points[k].split("_")[0]);
                            mpos[1]= Integer.parseInt(map_points[k].split("_")[1]);
                            map[mpos[0]][mpos[1]]='@';
                            pos= k;
                            printMap();
                            done=true;
                            break;
                        }
                    }
                }

            }
        }if(in.equalsIgnoreCase("s")){
            int mpos[]= new int[2];
            int or =mpos[1]= Integer.parseInt(map_points[pos].split("_")[1]);
            mpos[0]= Integer.parseInt(map_points[pos].split("_")[0]);
            mpos[1]= Integer.parseInt(map_points[pos].split("_")[1]);
            map[mpos[0]][mpos[1]]='*';
            boolean done = false;
            int h=(mpos[0]=Integer.parseInt(map_points[pos].split("_")[0]))+1;
            mpos[1]= Integer.parseInt(map_points[pos].split("_")[1]);
            System.out.println(map[mpos[0]-1][mpos[1]]=='*');
            if(map[mpos[0]+1][mpos[1]]=='*'){
                for(; h<23 ;h++){
                    if(done){
                        break;
                    }
                    for(int k=0;(k<map_points.length)&&(map_points[k]!=null);k++){
                        mpos[0]= Integer.parseInt(map_points[k].split("_")[0]);
                        mpos[1]= Integer.parseInt(map_points[k].split("_")[1]);
                        if(h==mpos[0]&&(or==mpos[1])){
                            mpos[0]= Integer.parseInt(map_points[k].split("_")[0]);
                            mpos[1]= Integer.parseInt(map_points[k].split("_")[1]);
                            map[mpos[0]][mpos[1]]='@';
                            pos= k;
                            printMap();
                            done=true;
                            break;
                        }
                    }
                }

            }
        }
        if(in.equalsIgnoreCase("a")){
            int mpos[]= new int[2];
            int or =mpos[0]= Integer.parseInt(map_points[pos].split("_")[0]);
            mpos[0]= Integer.parseInt(map_points[pos].split("_")[0]);
            mpos[1]= Integer.parseInt(map_points[pos].split("_")[1]);
            map[mpos[0]][mpos[1]]='*';
            boolean done = false;
            int h=(mpos[1]=Integer.parseInt(map_points[pos].split("_")[1]))-1;
            mpos[1]= Integer.parseInt(map_points[pos].split("_")[1]);
            System.out.println(map[mpos[0]-1][mpos[1]]=='*');
            if(map[mpos[0]][mpos[1]-1]=='*'){
                for(; h<88 ;h--){
                    if(done){
                        break;
                    }
                    for(int k=0;(k<map_points.length)&&(map_points[k]!=null);k++){
                        mpos[1]= Integer.parseInt(map_points[k].split("_")[1]);
                        mpos[0]= Integer.parseInt(map_points[k].split("_")[0]);
                        if(h==mpos[1]&&(or==mpos[0])){
                            mpos[0]= Integer.parseInt(map_points[k].split("_")[0]);
                            mpos[1]= Integer.parseInt(map_points[k].split("_")[1]);
                            map[mpos[0]][mpos[1]]='@';
                            pos= k;
                            printMap();
                            done=true;
                            break;
                        }
                    }
                }

            }
        }
        if(in.equalsIgnoreCase("d")){
            int mpos[]= new int[2];
            int or =mpos[0]= Integer.parseInt(map_points[pos].split("_")[0]);
            mpos[0]= Integer.parseInt(map_points[pos].split("_")[0]);
            mpos[1]= Integer.parseInt(map_points[pos].split("_")[1]);
            map[mpos[0]][mpos[1]]='*';

            boolean done = false;
            int h=(mpos[1]=Integer.parseInt(map_points[pos].split("_")[1]))+1;
            mpos[1]= Integer.parseInt(map_points[pos].split("_")[1]);
            System.out.println(map[mpos[0]-1][mpos[1]]=='*');
            if(map[mpos[0]][mpos[1]+1]=='*'){
                for(; h<88 ;h++){
                    if(done){
                        break;
                    }
                    for(int k=0;(k<map_points.length)&&(map_points[k]!=null);k++){
                        mpos[1]= Integer.parseInt(map_points[k].split("_")[1]);
                        mpos[0]= Integer.parseInt(map_points[k].split("_")[0]);
                        if(h==mpos[1]&&(or==mpos[0])){
                            mpos[0]= Integer.parseInt(map_points[k].split("_")[0]);
                            mpos[1]= Integer.parseInt(map_points[k].split("_")[1]);
                            map[mpos[0]][mpos[1]]='@';
                            pos= k;
                            printMap();
                            done=true;
                            break;
                        }
                    }
                }

            }
        }

    }
    }
