/*     */ package ru.atol.drivers10.fptr;
/*     */ 
/*     */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
/*     */ 
/*     */ public class FptrNative {
/*     */   private static String[] getEnvPaths(String name) {
/*  11 */     String p = System.getenv(name);
/*  12 */     if (p == null)
/*  13 */       return new String[0]; 
/*  14 */     List<String> list = new ArrayList<String>(Arrays.asList(p.split(File.pathSeparator)));
/*  15 */     list.removeAll(Arrays.asList((Object[])new String[] { "", null }));
/*  16 */     return list.<String>toArray(new String[0]);
/*     */   }
/*     */   
/*     */   private static void raiseNotFoundError(String path) {
/*  20 */     if (path.isEmpty()) {
/*  21 */       throw new UnsatisfiedLinkError("Driver library not found in search folders");
/*     */     }
/*     */     
/*  24 */     throw new UnsatisfiedLinkError(String.format("Can`t load driver library from \"%s\"", new Object[] { path }));
/*     */   }
/*     */   
/*     */   private static void loadFromPath(String path) {
/*     */     try {
/*  29 */       File f = new File(path);
/*  30 */       if (System.getProperty("os.name").contains("Windows")) {
/*  31 */         if (f.isDirectory()) {
/*  32 */           path = path + "\\fptr10.dll";
/*     */         }
/*     */         
/*     */         try {
/*  36 */           Runtime.getRuntime().load(path);
/*  37 */         } catch (UnsatisfiedLinkError ignored) {
/*  38 */           Runtime.getRuntime().load(path + (new File(path)).getParent() + "\\msvcp140.dll");
/*  39 */           Runtime.getRuntime().load(path);
/*     */         } 
/*  41 */       } else if (System.getProperty("os.name").contains("OS X")) {
/*     */         try {
/*  43 */           if (f.isDirectory()) {
/*  44 */             path = path + "/fptr10.framework/fptr10";
/*     */           }
/*  46 */           System.load(path);
/*  47 */         } catch (UnsatisfiedLinkError ignored) {
/*  48 */           if (f.isDirectory()) {
/*  49 */             path = path + "/libfptr10.dylib";
/*     */           }
/*  51 */           System.load(path);
/*     */         } 
/*     */       } else {
/*  54 */         if (f.isDirectory()) {
/*  55 */           path = path + "/libfptr10.so";
/*     */         }
/*  57 */         System.load(path);
/*     */       } 
/*  59 */     } catch (UnsatisfiedLinkError ignored) {
/*  60 */       raiseNotFoundError(path);
/*     */     } 
/*     */   }
/*     */   
/*     */   static {
/*  65 */     String libraryPath = System.getProperty("dto10.library.path");
/*     */     
/*  67 */     if (System.getProperty("os.name").contains("Windows")) {
/*  68 */       if (libraryPath.isEmpty()) {
/*     */         try {
/*  70 */           System.loadLibrary("fptr10");
/*  71 */         } catch (UnsatisfiedLinkError ignored) {
/*  72 */           String path = ru.atol.drivers10.fptr.WindowsRegistry.readRegistry("HKLM\\Software\\ATOL\\Drivers\\10.0\\KKT", "INSTALL_DIR");
/*  73 */           if (path.isEmpty()) {
/*  74 */             throw new UnsatisfiedLinkError("Driver not installed");
/*     */           }
/*     */           
/*  77 */           loadFromPath(path + "\\bin\\fptr10.dll");
/*     */         } 
/*     */       } else {
/*  80 */         loadFromPath(libraryPath);
/*     */       } 
/*  82 */     } else if (System.getProperty("os.name").contains("OS X")) {
/*  83 */       if (libraryPath.isEmpty()) {
/*  84 */         UnsatisfiedLinkError ex = null;
/*     */         
/*  86 */         List<String> loadPaths = new ArrayList<String>();
/*  87 */         loadPaths.addAll(Arrays.asList(getEnvPaths("DYLD_FRAMEWORK_PATH")));
/*  88 */         loadPaths.addAll(Arrays.asList(getEnvPaths("DYLD_FALLBACK_FRAMEWORK_PATH")));
/*     */         
/*  90 */         if (loadPaths.isEmpty()) {
/*  91 */           loadPaths = new ArrayList<String>(Arrays.asList(new String[] {
/*  92 */                   System.getenv("HOME") + "/Library/Frameworks", "/Library/Frameworks", "/Network/Library/Frameworks", "/System/Library/Frameworks"
/*     */                 }));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*  98 */         for (String path : loadPaths) {
/*     */           try {
/* 100 */             loadFromPath(path);
/* 101 */             ex = null;
/*     */             break;
/* 103 */           } catch (UnsatisfiedLinkError e1) {
/* 104 */             ex = e1;
/*     */           } 
/*     */         } 
/*     */         
/*     */         try {
/* 109 */           if (ex != null) {
/* 110 */             System.loadLibrary("fptr10");
/*     */           }
/* 112 */         } catch (UnsatisfiedLinkError ignored) {
/* 113 */           raiseNotFoundError(libraryPath);
/*     */         } 
/*     */       } else {
/* 116 */         loadFromPath(libraryPath);
/*     */       }
/*     */     
/* 119 */     } else if (libraryPath.isEmpty()) {
/* 120 */       System.loadLibrary("fptr10");
/*     */     } else {
/* 122 */       loadFromPath(libraryPath);
/*     */     } 
/*     */   }
/*     */   
/*     */   public native String version();
/*     */   
/*     */   public native int logWrite(long paramLong, String paramString1, int paramInt, String paramString2);
/*     */   
/*     */   public native int changeLabel(long paramLong, String paramString);
/*     */   
/*     */   public native int showProperties(long paramLong1, int paramInt, long paramLong2);
/*     */   
/*     */   public native long create();
/*     */   
/*     */   public native long createWithID(String paramString);
/*     */   
/*     */   public native void destroy(long paramLong);
/*     */   
/*     */   public native boolean isOpened(long paramLong);
/*     */   
/*     */   public native int setSettings(long paramLong, String paramString);
/*     */   
/*     */   public native String getSettings(long paramLong);
/*     */   
/*     */   public native void setSingleSetting(long paramLong, String paramString1, String paramString2);
/*     */   
/*     */   public native String getSingleSetting(long paramLong, String paramString);
/*     */   
/*     */   public native int errorCode(long paramLong);
/*     */   
/*     */   public native String errorDescription(long paramLong);
/*     */   
/*     */   public native String errorRecommendation(long paramLong);
/*     */   
/*     */   public native void resetError(long paramLong);
/*     */   
/*     */   public native void setParamBool(long paramLong, int paramInt, boolean paramBoolean);
/*     */   
/*     */   public native void setParamIntI(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   public native void setParamInt(long paramLong1, int paramInt, long paramLong2);
/*     */   
/*     */   public native void setParamDouble(long paramLong, int paramInt, double paramDouble);
/*     */   
/*     */   public native void setParamString(long paramLong, int paramInt, String paramString);
/*     */   
/*     */   public native void setParamDateTime(long paramLong, int paramInt, Date paramDate);
/*     */   
/*     */   public native void setParamByteArray(long paramLong, int paramInt, byte[] paramArrayOfbyte);
/*     */   
/*     */   public native void setNonPrintableParamBool(long paramLong, int paramInt, boolean paramBoolean);
/*     */   
/*     */   public native void setNonPrintableParamInt(long paramLong1, int paramInt, long paramLong2);
/*     */   
/*     */   public native void setNonPrintableParamIntI(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   public native void setNonPrintableParamDouble(long paramLong, int paramInt, double paramDouble);
/*     */   
/*     */   public native void setNonPrintableParamString(long paramLong, int paramInt, String paramString);
/*     */   
/*     */   public native void setNonPrintableParamDateTime(long paramLong, int paramInt, Date paramDate);
/*     */   
/*     */   public native void setNonPrintableParamByteArray(long paramLong, int paramInt, byte[] paramArrayOfbyte);
/*     */   
/*     */   public native void setUserParamBool(long paramLong, int paramInt, boolean paramBoolean);
/*     */   
/*     */   public native void setUserParamInt(long paramLong1, int paramInt, long paramLong2);
/*     */   
/*     */   public native void setUserParamIntI(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   public native void setUserParamDouble(long paramLong, int paramInt, double paramDouble);
/*     */   
/*     */   public native void setUserParamString(long paramLong, int paramInt, String paramString);
/*     */   
/*     */   public native void setUserParamDateTime(long paramLong, int paramInt, Date paramDate);
/*     */   
/*     */   public native void setUserParamByteArray(long paramLong, int paramInt, byte[] paramArrayOfbyte);
/*     */   
/*     */   public native boolean getParamBool(long paramLong, int paramInt);
/*     */   
/*     */   public native long getParamInt(long paramLong, int paramInt);
/*     */   
/*     */   public native double getParamDouble(long paramLong, int paramInt);
/*     */   
/*     */   public native String getParamString(long paramLong, int paramInt);
/*     */   
/*     */   public native Date getParamDateTime(long paramLong, int paramInt);
/*     */   
/*     */   public native byte[] getParamByteArray(long paramLong, int paramInt);
/*     */   
/*     */   public native boolean isParamAvailable(long paramLong, int paramInt);
/*     */   
/*     */   public native int applySingleSettings(long paramLong);
/*     */   
/*     */   public native int open(long paramLong);
/*     */   
/*     */   public native int close(long paramLong);
/*     */   
/*     */   public native int resetParams(long paramLong);
/*     */   
/*     */   public native int runCommand(long paramLong);
/*     */   
/*     */   public native int beep(long paramLong);
/*     */   
/*     */   public native int openDrawer(long paramLong);
/*     */   
/*     */   public native int cut(long paramLong);
/*     */   
/*     */   public native int devicePoweroff(long paramLong);
/*     */   
/*     */   public native int deviceReboot(long paramLong);
/*     */   
/*     */   public native int openShift(long paramLong);
/*     */   
/*     */   public native int resetSummary(long paramLong);
/*     */   
/*     */   public native int initDevice(long paramLong);
/*     */   
/*     */   public native int queryData(long paramLong);
/*     */   
/*     */   public native int cashIncome(long paramLong);
/*     */   
/*     */   public native int cashOutcome(long paramLong);
/*     */   
/*     */   public native int openReceipt(long paramLong);
/*     */   
/*     */   public native int cancelReceipt(long paramLong);
/*     */   
/*     */   public native int closeReceipt(long paramLong);
/*     */   
/*     */   public native int checkDocumentClosed(long paramLong);
/*     */   
/*     */   public native int receiptTotal(long paramLong);
/*     */   
/*     */   public native int receiptTax(long paramLong);
/*     */   
/*     */   public native int registration(long paramLong);
/*     */   
/*     */   public native int payment(long paramLong);
/*     */   
/*     */   public native int report(long paramLong);
/*     */   
/*     */   public native int printText(long paramLong);
/*     */   
/*     */   public native int printCliche(long paramLong);
/*     */   
/*     */   public native int beginNonfiscalDocument(long paramLong);
/*     */   
/*     */   public native int endNonfiscalDocument(long paramLong);
/*     */   
/*     */   public native int printBarcode(long paramLong);
/*     */   
/*     */   public native int printPicture(long paramLong);
/*     */   
/*     */   public native int printPictureByNumber(long paramLong);
/*     */   
/*     */   public native int uploadPictureFromFile(long paramLong);
/*     */   
/*     */   public native int clearPictures(long paramLong);
/*     */   
/*     */   public native int writeDeviceSettingRaw(long paramLong);
/*     */   
/*     */   public native int readDeviceSettingRaw(long paramLong);
/*     */   
/*     */   public native int commitSettings(long paramLong);
/*     */   
/*     */   public native int initSettings(long paramLong);
/*     */   
/*     */   public native int resetSettings(long paramLong);
/*     */   
/*     */   public native int writeDateTime(long paramLong);
/*     */   
/*     */   public native int writeLicense(long paramLong);
/*     */   
/*     */   public native int fnOperation(long paramLong);
/*     */   
/*     */   public native int fnQueryData(long paramLong);
/*     */   
/*     */   public native int fnWriteAttributes(long paramLong);
/*     */   
/*     */   public native int externalDevicePowerOn(long paramLong);
/*     */   
/*     */   public native int externalDevicePowerOff(long paramLong);
/*     */   
/*     */   public native int externalDeviceWriteData(long paramLong);
/*     */   
/*     */   public native int externalDeviceReadData(long paramLong);
/*     */   
/*     */   public native int operatorLogin(long paramLong);
/*     */   
/*     */   public native int processJson(long paramLong);
/*     */   
/*     */   public native int readDeviceSetting(long paramLong);
/*     */   
/*     */   public native int writeDeviceSetting(long paramLong);
/*     */   
/*     */   public native int beginReadRecords(long paramLong);
/*     */   
/*     */   public native int readNextRecord(long paramLong);
/*     */   
/*     */   public native int endReadRecords(long paramLong);
/*     */   
/*     */   public native int userMemoryOperation(long paramLong);
/*     */   
/*     */   public native int continuePrint(long paramLong);
/*     */   
/*     */   public native int initMgm(long paramLong);
/*     */   
/*     */   public native int utilFormTlv(long paramLong);
/*     */   
/*     */   public native int utilFormNomenclature(long paramLong);
/*     */   
/*     */   public native int utilMapping(long paramLong);
/*     */   
/*     */   public native int readModelFlags(long paramLong);
/*     */   
/*     */   public native int lineFeed(long paramLong);
/*     */   
/*     */   public native int flashFirmware(long paramLong);
/*     */   
/*     */   public native int softLockInit(long paramLong);
/*     */   
/*     */   public native int softLockQuerySessionCode(long paramLong);
/*     */   
/*     */   public native int softLockValidate(long paramLong);
/*     */   
/*     */   public native int utilCalcTax(long paramLong);
/*     */   
/*     */   public native int downloadPicture(long paramLong);
/*     */   
/*     */   public native int bluetoothRemovePairedDevices(long paramLong);
/*     */   
/*     */   public native int utilTagInfo(long paramLong);
/*     */   
/*     */   public native int utilContainerVersions(long paramLong);
/*     */   
/*     */   public native int activateLicenses(long paramLong);
/*     */   
/*     */   public native int removeLicenses(long paramLong);
/*     */   
/*     */   public native int enterKeys(long paramLong);
/*     */   
/*     */   public native int validateKeys(long paramLong);
/*     */   
/*     */   public native int enterSerialNumber(long paramLong);
/*     */   
/*     */   public native int getSerialNumberRequest(long paramLong);
/*     */   
/*     */   public native int uploadPixelBuffer(long paramLong);
/*     */   
/*     */   public native int downloadPixelBuffer(long paramLong);
/*     */   
/*     */   public native int printPixelBuffer(long paramLong);
/*     */   
/*     */   public native int utilConvertTagValue(long paramLong);
/*     */   
/*     */   public native int parseMarkingCode(long paramLong);
/*     */   
/*     */   public native int callScript(long paramLong);
/*     */   
/*     */   public native int setHeaderLines(long paramLong);
/*     */   
/*     */   public native int setFooterLines(long paramLong);
/*     */   
/*     */   public native int uploadPictureCliche(long paramLong);
/*     */   
/*     */   public native int uploadPictureMemory(long paramLong);
/*     */   
/*     */   public native int uploadPixelBufferCliche(long paramLong);
/*     */   
/*     */   public native int uploadPixelBufferMemory(long paramLong);
/*     */   
/*     */   public native int execDriverScript(long paramLong);
/*     */   
/*     */   public native int uploadDriverScript(long paramLong);
/*     */   
/*     */   public native int execDriverScriptById(long paramLong);
/*     */   
/*     */   public native int writeUniversalCountersSettings(long paramLong);
/*     */   
/*     */   public native int readUniversalCountersSettings(long paramLong);
/*     */   
/*     */   public native int queryUniversalCountersState(long paramLong);
/*     */   
/*     */   public native int resetUniversalCounters(long paramLong);
/*     */   
/*     */   public native int cacheUniversalCounters(long paramLong);
/*     */   
/*     */   public native int readUniversalCounterSum(long paramLong);
/*     */   
/*     */   public native int readUniversalCounterQuantity(long paramLong);
/*     */   
/*     */   public native int clearUniversalCountersCache(long paramLong);
/*     */   
/*     */   public native int disableOfdChannel(long paramLong);
/*     */   
/*     */   public native int enableOfdChannel(long paramLong);
/*     */   
/*     */   public native int validateJson(long paramLong);
/*     */   
/*     */   public native int reflectionCall(long paramLong);
/*     */   
/*     */   public native int getRemoteServerInfo(long paramLong);
/*     */   
/*     */   public native int beginMarkingCodeValidation(long paramLong);
/*     */   
/*     */   public native int cancelMarkingCodeValidation(long paramLong);
/*     */   
/*     */   public native int getMarkingCodeValidationStatus(long paramLong);
/*     */   
/*     */   public native int acceptMarkingCode(long paramLong);
/*     */   
/*     */   public native int declineMarkingCode(long paramLong);
/*     */   
/*     */   public native int updateFnmKeys(long paramLong);
/*     */   
/*     */   public native int writeSalesNotice(long paramLong);
/*     */   
/*     */   public native int checkMarkingCodeValidationsReady(long paramLong);
/*     */   
/*     */   public native int clearMarkingCodeValidationResult(long paramLong);
/*     */   
/*     */   public native int pingMarkingServer(long paramLong);
/*     */   
/*     */   public native int getMarkingServerStatus(long paramLong);
/*     */   
/*     */   public native int isDriverLocked(long paramLong);
/*     */   
/*     */   public native int getLastDocumentJournal(long paramLong);
/*     */   
/*     */   public native int findDocumentInJournal(long paramLong);
/*     */   
/*     */   public native int runFnCommand(long paramLong);
/*     */ }


/* Location:              G:\work.tmp\test_build_javafx\demo\lib\libfptr10.jar!\ru\atol\drivers10\fptr\FptrNative.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */