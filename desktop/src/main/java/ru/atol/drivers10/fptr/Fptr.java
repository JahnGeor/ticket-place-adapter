/*     */ package ru.atol.drivers10.fptr;
/*     */ 
/*     */

import java.util.Date;
/*     */ 
/*     */ public class Fptr implements IFptr {
/*     */   private FptrNative fptrNative;
/*   7 */   private long nativePtr = 0L;
/*     */   
/*     */   public Fptr() throws NullPointerException {
/*  10 */     System.setProperty("dto10.library.path", "");
/*  11 */     this.fptrNative = new FptrNative();
/*  12 */     this.nativePtr = this.fptrNative.create();
/*  13 */     if (this.nativePtr == 0L)
/*  14 */       throw new NullPointerException("Не удалось создать дескриптор"); 
/*     */   }
/*     */   
/*     */   public Fptr(String libraryPath) throws NullPointerException {
/*  18 */     System.setProperty("dto10.library.path", (libraryPath == null) ? "" : libraryPath);
/*  19 */     this.fptrNative = new FptrNative();
/*  20 */     this.nativePtr = this.fptrNative.create();
/*  21 */     if (this.nativePtr == 0L)
/*  22 */       throw new NullPointerException("Не удалось создать дескриптор"); 
/*     */   }
/*     */   
/*     */   public Fptr(String id, String libraryPath) throws NullPointerException {
/*  26 */     System.setProperty("dto10.library.path", (libraryPath == null) ? "" : libraryPath);
/*  27 */     this.fptrNative = new FptrNative();
/*  28 */     this.nativePtr = this.fptrNative.createWithID(id);
/*  29 */     if (this.nativePtr == 0L) {
/*  30 */       throw new NullPointerException("Не удалось создать дескриптор");
/*     */     }
/*     */   }
/*     */   
/*     */   public void destroy() {
/*  35 */     if (this.nativePtr != 0L) {
/*     */       
/*  37 */       this.fptrNative.destroy(this.nativePtr);
/*  38 */       this.nativePtr = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  44 */     destroy();
/*  45 */     super.finalize();
/*     */   }
/*     */ 
/*     */   
/*     */   public int logWrite(String tag, int level, String message) {
/*  50 */     return this.fptrNative.logWrite(this.nativePtr, tag, level, message);
/*     */   }
/*     */ 
/*     */   
/*     */   public int changeLabel(String label) {
/*  55 */     return this.fptrNative.changeLabel(this.nativePtr, label);
/*     */   }
/*     */ 
/*     */   
/*     */   public int showProperties(int parentType, long parent) {
/*  60 */     return this.fptrNative.showProperties(this.nativePtr, parentType, parent);
/*     */   }
/*     */ 
/*     */   
/*     */   public String version() {
/*  65 */     return this.fptrNative.version();
/*     */   }
/*     */ 
/*     */   
/*     */   public String wrapperVersion() {
/*  70 */     return "10.10.3.0";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpened() {
/*  75 */     return this.fptrNative.isOpened(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setSettings(String settings) {
/*  80 */     return this.fptrNative.setSettings(this.nativePtr, settings);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSettings() {
/*  85 */     return this.fptrNative.getSettings(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSingleSetting(String key, String setting) {
/*  90 */     this.fptrNative.setSingleSetting(this.nativePtr, key, setting);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSingleSetting(String key) {
/*  95 */     return this.fptrNative.getSingleSetting(this.nativePtr, key);
/*     */   }
/*     */ 
/*     */   
/*     */   public int errorCode() {
/* 100 */     return this.fptrNative.errorCode(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public String errorDescription() {
/* 105 */     return this.fptrNative.errorDescription(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public String errorRecommendation() {
/* 110 */     return this.fptrNative.errorRecommendation(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetError() {
/* 115 */     this.fptrNative.resetError(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParam(int param, boolean value) {
/* 120 */     this.fptrNative.setParamBool(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParam(int param, int value) {
/* 125 */     if (value < 0)
/* 126 */       throw new IllegalArgumentException(String.format("Invalid parameter value %d", new Object[] { Integer.valueOf(value) })); 
/* 127 */     this.fptrNative.setParamIntI(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParam(int param, long value) {
/* 132 */     if (value < 0L || value > 4294967295L)
/* 133 */       throw new IllegalArgumentException(String.format("Invalid parameter value %d", new Object[] { Long.valueOf(value) })); 
/* 134 */     this.fptrNative.setParamInt(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParam(int param, double value) {
/* 139 */     this.fptrNative.setParamDouble(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParam(int param, String value) {
/* 144 */     this.fptrNative.setParamString(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParam(int param, Date value) {
/* 149 */     this.fptrNative.setParamDateTime(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParam(int param, byte[] value) {
/* 154 */     this.fptrNative.setParamByteArray(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNonPrintableParam(int param, boolean value) {
/* 159 */     this.fptrNative.setNonPrintableParamBool(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNonPrintableParam(int param, int value) {
/* 164 */     if (value < 0)
/* 165 */       throw new IllegalArgumentException(String.format("Invalid parameter value %d", new Object[] { Integer.valueOf(value) })); 
/* 166 */     this.fptrNative.setNonPrintableParamIntI(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNonPrintableParam(int param, long value) {
/* 171 */     if (value < 0L || value > 4294967295L)
/* 172 */       throw new IllegalArgumentException(String.format("Invalid parameter value %d", new Object[] { Long.valueOf(value) })); 
/* 173 */     this.fptrNative.setNonPrintableParamInt(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNonPrintableParam(int param, double value) {
/* 178 */     this.fptrNative.setNonPrintableParamDouble(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNonPrintableParam(int param, String value) {
/* 183 */     this.fptrNative.setNonPrintableParamString(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNonPrintableParam(int param, Date value) {
/* 188 */     this.fptrNative.setNonPrintableParamDateTime(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNonPrintableParam(int param, byte[] value) {
/* 193 */     this.fptrNative.setNonPrintableParamByteArray(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUserParam(int param, boolean value) {
/* 198 */     this.fptrNative.setUserParamBool(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUserParam(int param, int value) {
/* 203 */     if (value < 0)
/* 204 */       throw new IllegalArgumentException(String.format("Invalid parameter value %d", new Object[] { Integer.valueOf(value) })); 
/* 205 */     this.fptrNative.setUserParamIntI(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUserParam(int param, long value) {
/* 210 */     if (value < 0L || value > 4294967295L)
/* 211 */       throw new IllegalArgumentException(String.format("Invalid parameter value %d", new Object[] { Long.valueOf(value) })); 
/* 212 */     this.fptrNative.setUserParamInt(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUserParam(int param, double value) {
/* 217 */     this.fptrNative.setUserParamDouble(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUserParam(int param, String value) {
/* 222 */     this.fptrNative.setUserParamString(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUserParam(int param, Date value) {
/* 227 */     this.fptrNative.setUserParamDateTime(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUserParam(int param, byte[] value) {
/* 232 */     this.fptrNative.setUserParamByteArray(this.nativePtr, param, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getParamBool(int param) {
/* 237 */     return this.fptrNative.getParamBool(this.nativePtr, param);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getParamInt(int param) {
/* 242 */     return this.fptrNative.getParamInt(this.nativePtr, param);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getParamDouble(int param) {
/* 247 */     return this.fptrNative.getParamDouble(this.nativePtr, param);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getParamString(int param) {
/* 252 */     return this.fptrNative.getParamString(this.nativePtr, param);
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getParamDateTime(int param) {
/* 257 */     return this.fptrNative.getParamDateTime(this.nativePtr, param);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getParamByteArray(int param) {
/* 262 */     return this.fptrNative.getParamByteArray(this.nativePtr, param);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParamAvailable(int param) {
/* 267 */     return this.fptrNative.isParamAvailable(this.nativePtr, param);
/*     */   }
/*     */ 
/*     */   
/*     */   public int applySingleSettings() {
/* 272 */     return this.fptrNative.applySingleSettings(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int open() {
/* 277 */     return this.fptrNative.open(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int close() {
/* 282 */     return this.fptrNative.close(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int resetParams() {
/* 287 */     return this.fptrNative.resetParams(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int runCommand() {
/* 292 */     return this.fptrNative.runCommand(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int beep() {
/* 297 */     return this.fptrNative.beep(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int openDrawer() {
/* 302 */     return this.fptrNative.openDrawer(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int cut() {
/* 307 */     return this.fptrNative.cut(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int devicePoweroff() {
/* 312 */     return this.fptrNative.devicePoweroff(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int deviceReboot() {
/* 317 */     return this.fptrNative.deviceReboot(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int openShift() {
/* 322 */     return this.fptrNative.openShift(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int resetSummary() {
/* 327 */     return this.fptrNative.resetSummary(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int initDevice() {
/* 332 */     return this.fptrNative.initDevice(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int queryData() {
/* 337 */     return this.fptrNative.queryData(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int cashIncome() {
/* 342 */     return this.fptrNative.cashIncome(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int cashOutcome() {
/* 347 */     return this.fptrNative.cashOutcome(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int openReceipt() {
/* 352 */     return this.fptrNative.openReceipt(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int cancelReceipt() {
/* 357 */     return this.fptrNative.cancelReceipt(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int closeReceipt() {
/* 362 */     return this.fptrNative.closeReceipt(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int checkDocumentClosed() {
/* 367 */     return this.fptrNative.checkDocumentClosed(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int receiptTotal() {
/* 372 */     return this.fptrNative.receiptTotal(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int receiptTax() {
/* 377 */     return this.fptrNative.receiptTax(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int registration() {
/* 382 */     return this.fptrNative.registration(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int payment() {
/* 387 */     return this.fptrNative.payment(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int report() {
/* 392 */     return this.fptrNative.report(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int printText() {
/* 397 */     return this.fptrNative.printText(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int printCliche() {
/* 402 */     return this.fptrNative.printCliche(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int beginNonfiscalDocument() {
/* 407 */     return this.fptrNative.beginNonfiscalDocument(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int endNonfiscalDocument() {
/* 412 */     return this.fptrNative.endNonfiscalDocument(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int printBarcode() {
/* 417 */     return this.fptrNative.printBarcode(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int printPicture() {
/* 422 */     return this.fptrNative.printPicture(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int printPictureByNumber() {
/* 427 */     return this.fptrNative.printPictureByNumber(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int uploadPictureFromFile() {
/* 432 */     return this.fptrNative.uploadPictureFromFile(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int clearPictures() {
/* 437 */     return this.fptrNative.clearPictures(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeDeviceSettingRaw() {
/* 442 */     return this.fptrNative.writeDeviceSettingRaw(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readDeviceSettingRaw() {
/* 447 */     return this.fptrNative.readDeviceSettingRaw(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int commitSettings() {
/* 452 */     return this.fptrNative.commitSettings(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int initSettings() {
/* 457 */     return this.fptrNative.initSettings(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int resetSettings() {
/* 462 */     return this.fptrNative.resetSettings(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeDateTime() {
/* 467 */     return this.fptrNative.writeDateTime(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeLicense() {
/* 472 */     return this.fptrNative.writeLicense(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int fnOperation() {
/* 477 */     return this.fptrNative.fnOperation(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int fnQueryData() {
/* 482 */     return this.fptrNative.fnQueryData(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int fnWriteAttributes() {
/* 487 */     return this.fptrNative.fnWriteAttributes(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int externalDevicePowerOn() {
/* 492 */     return this.fptrNative.externalDevicePowerOn(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int externalDevicePowerOff() {
/* 497 */     return this.fptrNative.externalDevicePowerOff(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int externalDeviceWriteData() {
/* 502 */     return this.fptrNative.externalDeviceWriteData(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int externalDeviceReadData() {
/* 507 */     return this.fptrNative.externalDeviceReadData(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int operatorLogin() {
/* 512 */     return this.fptrNative.operatorLogin(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int processJson() {
/* 517 */     return this.fptrNative.processJson(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readDeviceSetting() {
/* 522 */     return this.fptrNative.readDeviceSetting(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeDeviceSetting() {
/* 527 */     return this.fptrNative.writeDeviceSetting(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int beginReadRecords() {
/* 532 */     return this.fptrNative.beginReadRecords(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readNextRecord() {
/* 537 */     return this.fptrNative.readNextRecord(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int endReadRecords() {
/* 542 */     return this.fptrNative.endReadRecords(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int userMemoryOperation() {
/* 547 */     return this.fptrNative.userMemoryOperation(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int continuePrint() {
/* 552 */     return this.fptrNative.continuePrint(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int initMgm() {
/* 557 */     return this.fptrNative.initMgm(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int utilFormTlv() {
/* 562 */     return this.fptrNative.utilFormTlv(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int utilFormNomenclature() {
/* 567 */     return this.fptrNative.utilFormNomenclature(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int utilMapping() {
/* 572 */     return this.fptrNative.utilMapping(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readModelFlags() {
/* 577 */     return this.fptrNative.readModelFlags(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int lineFeed() {
/* 582 */     return this.fptrNative.lineFeed(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int flashFirmware() {
/* 587 */     return this.fptrNative.flashFirmware(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int softLockInit() {
/* 592 */     return this.fptrNative.softLockInit(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int softLockQuerySessionCode() {
/* 597 */     return this.fptrNative.softLockQuerySessionCode(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int softLockValidate() {
/* 602 */     return this.fptrNative.softLockValidate(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int utilCalcTax() {
/* 607 */     return this.fptrNative.utilCalcTax(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int downloadPicture() {
/* 612 */     return this.fptrNative.downloadPicture(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int bluetoothRemovePairedDevices() {
/* 617 */     return this.fptrNative.bluetoothRemovePairedDevices(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int utilTagInfo() {
/* 622 */     return this.fptrNative.utilTagInfo(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int utilContainerVersions() {
/* 627 */     return this.fptrNative.utilContainerVersions(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int activateLicenses() {
/* 632 */     return this.fptrNative.activateLicenses(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int removeLicenses() {
/* 637 */     return this.fptrNative.removeLicenses(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int enterKeys() {
/* 642 */     return this.fptrNative.enterKeys(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int validateKeys() {
/* 647 */     return this.fptrNative.validateKeys(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int enterSerialNumber() {
/* 652 */     return this.fptrNative.enterSerialNumber(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerialNumberRequest() {
/* 657 */     return this.fptrNative.getSerialNumberRequest(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int uploadPixelBuffer() {
/* 662 */     return this.fptrNative.uploadPixelBuffer(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int downloadPixelBuffer() {
/* 667 */     return this.fptrNative.downloadPixelBuffer(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int printPixelBuffer() {
/* 672 */     return this.fptrNative.printPixelBuffer(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int utilConvertTagValue() {
/* 677 */     return this.fptrNative.utilConvertTagValue(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int parseMarkingCode() {
/* 682 */     return this.fptrNative.parseMarkingCode(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int callScript() {
/* 687 */     return this.fptrNative.callScript(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setHeaderLines() {
/* 692 */     return this.fptrNative.setHeaderLines(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setFooterLines() {
/* 697 */     return this.fptrNative.setFooterLines(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int uploadPictureCliche() {
/* 702 */     return this.fptrNative.uploadPictureCliche(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int uploadPictureMemory() {
/* 707 */     return this.fptrNative.uploadPictureMemory(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int uploadPixelBufferCliche() {
/* 712 */     return this.fptrNative.uploadPixelBufferCliche(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int uploadPixelBufferMemory() {
/* 717 */     return this.fptrNative.uploadPixelBufferMemory(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int execDriverScript() {
/* 722 */     return this.fptrNative.execDriverScript(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int uploadDriverScript() {
/* 727 */     return this.fptrNative.uploadDriverScript(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int execDriverScriptById() {
/* 732 */     return this.fptrNative.execDriverScriptById(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeUniversalCountersSettings() {
/* 737 */     return this.fptrNative.writeUniversalCountersSettings(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUniversalCountersSettings() {
/* 742 */     return this.fptrNative.readUniversalCountersSettings(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int queryUniversalCountersState() {
/* 747 */     return this.fptrNative.queryUniversalCountersState(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int resetUniversalCounters() {
/* 752 */     return this.fptrNative.resetUniversalCounters(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int cacheUniversalCounters() {
/* 757 */     return this.fptrNative.cacheUniversalCounters(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUniversalCounterSum() {
/* 762 */     return this.fptrNative.readUniversalCounterSum(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUniversalCounterQuantity() {
/* 767 */     return this.fptrNative.readUniversalCounterQuantity(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int clearUniversalCountersCache() {
/* 772 */     return this.fptrNative.clearUniversalCountersCache(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int disableOfdChannel() {
/* 777 */     return this.fptrNative.disableOfdChannel(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int enableOfdChannel() {
/* 782 */     return this.fptrNative.enableOfdChannel(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int validateJson() {
/* 787 */     return this.fptrNative.validateJson(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int reflectionCall() {
/* 792 */     return this.fptrNative.reflectionCall(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRemoteServerInfo() {
/* 797 */     return this.fptrNative.getRemoteServerInfo(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int beginMarkingCodeValidation() {
/* 802 */     return this.fptrNative.beginMarkingCodeValidation(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int cancelMarkingCodeValidation() {
/* 807 */     return this.fptrNative.cancelMarkingCodeValidation(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMarkingCodeValidationStatus() {
/* 812 */     return this.fptrNative.getMarkingCodeValidationStatus(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int acceptMarkingCode() {
/* 817 */     return this.fptrNative.acceptMarkingCode(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int declineMarkingCode() {
/* 822 */     return this.fptrNative.declineMarkingCode(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int updateFnmKeys() {
/* 827 */     return this.fptrNative.updateFnmKeys(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeSalesNotice() {
/* 832 */     return this.fptrNative.writeSalesNotice(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int checkMarkingCodeValidationsReady() {
/* 837 */     return this.fptrNative.checkMarkingCodeValidationsReady(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int clearMarkingCodeValidationResult() {
/* 842 */     return this.fptrNative.clearMarkingCodeValidationResult(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int pingMarkingServer() {
/* 847 */     return this.fptrNative.pingMarkingServer(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMarkingServerStatus() {
/* 852 */     return this.fptrNative.getMarkingServerStatus(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int isDriverLocked() {
/* 857 */     return this.fptrNative.isDriverLocked(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLastDocumentJournal() {
/* 862 */     return this.fptrNative.getLastDocumentJournal(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int findDocumentInJournal() {
/* 867 */     return this.fptrNative.findDocumentInJournal(this.nativePtr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int runFnCommand() {
/* 872 */     return this.fptrNative.runFnCommand(this.nativePtr);
/*     */   }
/*     */ }


/* Location:              G:\work.tmp\test_build_javafx\demo\lib\libfptr10.jar!\ru\atol\drivers10\fptr\Fptr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */