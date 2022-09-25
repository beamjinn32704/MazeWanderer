package com.mindblown.mazewanderer;


import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dialog.ModalityType;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

/**
 *
 * @author beamj
 */
public class Util {
    
    public static int filesOnly = JFileChooser.FILES_ONLY;
    public static int dirsOnly = JFileChooser.DIRECTORIES_ONLY;
    public static int filesAndDirs = JFileChooser.FILES_AND_DIRECTORIES;
    
    public static String currentWriterFileName = "";
    
    public static PrintWriter getWriter(String origName, String fileType){
        String name = origName.substring(0);
        PrintWriter writer = null;
        boolean go = true;
        while(go){
            try {
                writer = new PrintWriter(name + fileType);
                go = false;
            } catch (FileNotFoundException ex) {
                name += "0";
            }
        }
        currentWriterFileName = name + fileType;
        return writer;
    }
    
    public static <T> ArrayList<T> toList(T[] a){
        ArrayList<T> list = new ArrayList<>();
        list.addAll(Arrays.asList(a));
        return list;
    }
    
    public static <T> Object[] toArray(ArrayList<T> list){
        return list.toArray();
    }
    
    public static int[] toIntArray(ArrayList<Integer> list){
        int[] a = new int[list.size()];
        for(int i = 0; i < a.length; i++){
            a[i] = list.get(i);
        }
        return a;
    }
    
    public static void runBatch(File batch) throws Exception {
        Runtime rt = Runtime.getRuntime();
        rt.exec("cmd /c start " + batch);
    }
    
    public static File getFile(int selectionMode, String title) {
        return getFile(selectionMode, title, null);
    }
    
    public static File getFile(int selectionMode, String title, FileFilter filter) {
        File file = null;
        JFileChooser chooser = new JFileChooser();
        if(filter != null){
            chooser.setFileFilter(filter);
        }
        chooser.setFileSelectionMode(selectionMode);
        chooser.setDialogTitle(title);
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
        return file;
    }
    
    public static boolean isImg(File img){
        try {
            Image image = ImageIO.read(img);
            return image != null;
        } catch(Exception e) {
            return false;
        }
    }
    
    public static int objectExists(Object[] o, Object obj){
        for(int i = 0; i < o.length; i++){
            if(o[i] == obj){
                return i;
            }
        }
        return -1;
    }
    
    public static int objectExists(Object[] o, Object obj, boolean rel){
        for(int i = 0; i < o.length; i++){
            if(rel){
                if(o[i].equals(obj)){
                    return i;
                }
            } else {
                if(o[i] == obj){
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static String nextAlpha(String text){
        return nextAlpha(text, 0);
    }
    
    public static String nextAlpha(String text, int start){
        for(int i = start ; i < text.length(); i++){
            char c = text.charAt(i);
            if(Character.isAlphabetic(c)){
                return c + "";
            }
        }
        return null;
    }
    
    public static String getText(File file){
        if(!file.isFile()){
            return "";
        }
        String text = "";
        try(Scanner in = new Scanner(file)){
            while(in.hasNextLine()){
                text += in.nextLine();
                text += "\n";
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
        return text;
    }
    
    public static String removeLines(String s){
        String string = Util.strip(s);
        String text = "";
        for(int i = 0; i < string.length(); i++){
            String str = string.substring(i, i+1);
            if(!(Util.isBlank(str) && !str.equals(" "))){
                text += str;
            }
        }
        return text;
    }
    
    public static <T> void print(ArrayList<T> list){
        for(T o : list){
            System.out.println(o);
        }
    }
    
    public static <T extends Comparable> int indexOf(ArrayList<? extends Comparable<? super T>> list, T key){
        return Collections.binarySearch(list, key);
    }
    
    public static <T extends Comparable> boolean has(ArrayList<? extends Comparable<? super T>> list, T key){
        return indexOf(list, key) > -1;
    }
    
    public static <T extends Comparable<? super T>> boolean add(ArrayList<T> list, T key){
        int ind = Util.indexOf(list, key);
        if(ind >= 0){
            return false;
        } else {
            ind = -1 * (ind + 1);
            list.add(ind, key);
            return true;
        }
    }
    
    public static <T extends Comparable<? super T>> boolean addAll(ArrayList<T> list, ArrayList<T> key){
        boolean ret = true;
        for(T t : key){
            int ind = Util.indexOf(list, t);
            if(ind >= 0){
                ret = false;
            } else {
                ind = -1 * (ind + 1);
                list.add(ind, t);
            }
        }
        return ret;
    }
    
    public static <T extends Comparable<? super T>> boolean remove(ArrayList<T> list, T key){
        int ind = Util.indexOf(list, key);
        if(ind >= 0){
            list.remove(ind);
            return true;
        } else {
            return false;
        }
    }
    
    public static int indexOf(Object[] list, Object key){
        return Arrays.binarySearch(list, key);
    }
    
    public static boolean has(Object[] list, Object key){
        return indexOf(list, key) > -1;
    }
    
    public static boolean put(Object[] list, Object key){
        int ind = Util.indexOf(list, key);
        if(ind >= 0){
            return false;
        } else {
            ind = -1 * (ind + 1);
            if(ind >= list.length){
                return false;
            } else {
                list[ind] = key;
                return true;
            }
        }
    }
    
    public static boolean isBlank(String str){
        return strip(str).isEmpty();
    }
    
    public static String strip(String s){
        return s.trim();
    }
    
    public static JDialog showNonBlocking(Object obj, String title, boolean resizable){
        return show(obj, title, JDialog.DISPOSE_ON_CLOSE, JDialog.ModalityType.MODELESS, resizable);
    }
    
    public static JDialog showBlocking(Object obj, String title, boolean resizable){
        return show(obj, title, JDialog.DISPOSE_ON_CLOSE, JDialog.DEFAULT_MODALITY_TYPE, resizable);
    }
    
    public static JDialog showNorm(Object obj, String title){
        return show(obj, title, false, true, true);
    }
    
    public static JDialog show(Object obj, String title, boolean resizable, boolean block, boolean disposeOnClose){
        int close;
        if(disposeOnClose){
            close = JDialog.DISPOSE_ON_CLOSE;
        } else {
            close = JDialog.DO_NOTHING_ON_CLOSE;
        }
        ModalityType modal;
        if(block){
            modal = JDialog.DEFAULT_MODALITY_TYPE;
        } else {
            modal = JDialog.ModalityType.MODELESS;
        }
        return show(obj, title, close, modal, resizable);
    }
    
    public static JDialog show(Object obj, String title, int close, ModalityType modalityType, boolean resizable){
        JOptionPane pane = new JOptionPane(obj, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        JDialog dial = new JDialog();
        dial.setTitle(title);
        dial.setContentPane(pane);
        dial.setResizable(resizable);
        dial.setModalityType(modalityType);
        dial.setDefaultCloseOperation(close);
        dial.pack();
        return dial;
    }
    
    public static String input(String message, String title){
        return input(null, message, title);
    }
    
    public static String input(Component parent, String message, String title){
        return JOptionPane.showInputDialog(parent, message, title, JOptionPane.PLAIN_MESSAGE);
    }
    
    public static boolean open(File file){
        try {
            Desktop.getDesktop().open(file);
        } catch (Exception e){
            try {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", file.toString()});
            } catch(Exception ex){
                return false;
            }
        }
        return true;
    }
    
    public static boolean openDir(File file){
        try {
            String open;
            if(file.isDirectory()){
                open = file.toString();
            } else {
                open = file.getParent();
            }
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", open});
        } catch(Exception ex){
            return false;
        }
        return true;
    }
    
    public static boolean open(String url){
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e){
            try {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
            } catch(Exception ex){
                return false;
            }
        }
        return true;
    }
    
    public static void message(Component comp, Object obj, String title){
        JOptionPane.showMessageDialog(comp, obj, title, JOptionPane.PLAIN_MESSAGE);
    }
}