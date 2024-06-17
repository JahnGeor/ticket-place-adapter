/*    */ package ru.atol.drivers10.fptr;
/*    */ 
/*    */

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
/*    */ 
/*    */ class WindowsRegistry {
/*    */   public static String readRegistry(String location, String key) {
/*    */     try {
/* 11 */       String jvmArch = System.getProperty("os.arch").equals("x86") ? "32" : "64";
/*    */       
/* 13 */       String archEnv = System.getenv("PROCESSOR_ARCHITECTURE");
/* 14 */       String wow64ArchEnv = System.getenv("PROCESSOR_ARCHITEW6432");
/*    */       
/* 16 */       String realArch = (archEnv.endsWith("64") || (wow64ArchEnv != null && wow64ArchEnv.endsWith("64"))) ? "64" : "32";
/*    */ 
/*    */       
/* 19 */       String reg = "C:\\Windows\\System32\\reg.exe";
/* 20 */       if (jvmArch.equals("32") && realArch.equals("64")) {
/* 21 */         reg = "C:\\Windows\\SysWOW64\\reg.exe";
/*    */       }
/* 23 */       Process process = Runtime.getRuntime().exec(reg + " query " + '"' + location + "\" /v " + key);
/*    */ 
/*    */       
/* 26 */       StreamReader reader = new StreamReader(process.getInputStream());
/* 27 */       reader.start();
/* 28 */       process.waitFor();
/* 29 */       reader.join();
/*    */       
/* 31 */       String[] parsed = reader.getResult().split("\\s+");
/* 32 */       StringBuilder result = new StringBuilder();
/* 33 */       for (int i = Arrays.<String>asList(parsed).indexOf(key) + 2; i < parsed.length; i++) {
/* 34 */         result.append(parsed[i]).append(" ");
/*    */       }
/* 36 */       return result.toString().trim();
/* 37 */     } catch (Exception exception) {
/*    */ 
/*    */       
/* 40 */       return "";
/*    */     } 
/*    */   }
/*    */   
/*    */   static class StreamReader extends Thread {
/* 45 */     private StringWriter sw = new StringWriter(); private InputStream is;
/*    */     
/*    */     StreamReader(InputStream is) {
/* 48 */       this.is = is;
/*    */     }
/*    */     
/*    */     public void run() {
/*    */       try {
/*    */         int c;
/* 54 */         while ((c = this.is.read()) != -1)
/* 55 */           this.sw.write(c); 
/* 56 */       } catch (IOException iOException) {}
/*    */     }
/*    */ 
/*    */     
/*    */     String getResult() {
/* 61 */       return this.sw.toString();
/*    */     }
/*    */   }
/*    */ }


/* Location:              G:\work.tmp\test_build_javafx\demo\lib\libfptr10.jar!\ru\atol\drivers10\fptr\WindowsRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */