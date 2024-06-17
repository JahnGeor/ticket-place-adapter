package ru.atol.drivers10.fptr;

import java.util.Date;



public interface IFptr {
  public static final int LIBFPTR_PARAM_TEXT = 65536;
  
  public static final int LIBFPTR_PARAM_TEXT_WRAP = 65537;
  
  public static final int LIBFPTR_PARAM_ALIGNMENT = 65538;
  
  public static final int LIBFPTR_PARAM_FONT = 65539;
  
  public static final int LIBFPTR_PARAM_FONT_DOUBLE_WIDTH = 65540;
  
  public static final int LIBFPTR_PARAM_FONT_DOUBLE_HEIGHT = 65541;
  
  public static final int LIBFPTR_PARAM_LINESPACING = 65542;
  
  public static final int LIBFPTR_PARAM_BRIGHTNESS = 65543;
  
  public static final int LIBFPTR_PARAM_MODEL = 65544;
  
  public static final int LIBFPTR_PARAM_RECEIPT_TYPE = 65545;
  
  public static final int LIBFPTR_PARAM_REPORT_TYPE = 65546;
  
  public static final int LIBFPTR_PARAM_MODE = 65547;
  
  public static final int LIBFPTR_PARAM_EXTERNAL_DEVICE_TYPE = 65548;
  
  public static final int LIBFPTR_PARAM_EXTERNAL_DEVICE_DATA = 65549;
  
  public static final int LIBFPTR_PARAM_FREQUENCY = 65550;
  
  public static final int LIBFPTR_PARAM_DURATION = 65551;
  
  public static final int LIBFPTR_PARAM_CUT_TYPE = 65552;
  
  public static final int LIBFPTR_PARAM_DRAWER_ON_TIMEOUT = 65553;
  
  public static final int LIBFPTR_PARAM_DRAWER_OFF_TIMEOUT = 65554;
  
  public static final int LIBFPTR_PARAM_DRAWER_ON_QUANTITY = 65555;
  
  public static final int LIBFPTR_PARAM_TIMEOUT_ENQ = 65556;
  
  public static final int LIBFPTR_PARAM_COMMAND_BUFFER = 65557;
  
  public static final int LIBFPTR_PARAM_ANSWER_BUFFER = 65558;
  
  public static final int LIBFPTR_PARAM_SERIAL_NUMBER = 65559;
  
  public static final int LIBFPTR_PARAM_MANUFACTURER_CODE = 65560;
  
  public static final int LIBFPTR_PARAM_NO_NEED_ANSWER = 65561;
  
  public static final int LIBFPTR_PARAM_INFO_DISCOUNT_SUM = 65562;
  
  public static final int LIBFPTR_PARAM_USE_ONLY_TAX_TYPE = 65563;
  
  public static final int LIBFPTR_PARAM_PAYMENT_TYPE = 65564;
  
  public static final int LIBFPTR_PARAM_PAYMENT_SUM = 65565;
  
  public static final int LIBFPTR_PARAM_REMAINDER = 65566;
  
  public static final int LIBFPTR_PARAM_CHANGE = 65567;
  
  public static final int LIBFPTR_PARAM_DEPARTMENT = 65568;
  
  public static final int LIBFPTR_PARAM_TAX_TYPE = 65569;
  
  public static final int LIBFPTR_PARAM_TAX_SUM = 65570;
  
  public static final int LIBFPTR_PARAM_TAX_MODE = 65571;
  
  public static final int LIBFPTR_PARAM_RECEIPT_ELECTRONICALLY = 65572;
  
  public static final int LIBFPTR_PARAM_USER_PASSWORD = 65573;
  
  public static final int LIBFPTR_PARAM_SCALE = 65574;
  
  public static final int LIBFPTR_PARAM_LEFT_MARGIN = 65575;
  
  public static final int LIBFPTR_PARAM_BARCODE = 65576;
  
  public static final int LIBFPTR_PARAM_BARCODE_TYPE = 65577;
  
  public static final int LIBFPTR_PARAM_BARCODE_PRINT_TEXT = 65578;
  
  public static final int LIBFPTR_PARAM_BARCODE_VERSION = 65579;
  
  public static final int LIBFPTR_PARAM_BARCODE_CORRECTION = 65580;
  
  public static final int LIBFPTR_PARAM_BARCODE_COLUMNS = 65581;
  
  public static final int LIBFPTR_PARAM_BARCODE_INVERT = 65582;
  
  public static final int LIBFPTR_PARAM_HEIGHT = 65583;
  
  public static final int LIBFPTR_PARAM_WIDTH = 65584;
  
  public static final int LIBFPTR_PARAM_FILENAME = 65585;
  
  public static final int LIBFPTR_PARAM_PICTURE_NUMBER = 65586;
  
  public static final int LIBFPTR_PARAM_DATA_TYPE = 65587;
  
  public static final int LIBFPTR_PARAM_OPERATOR_ID = 65588;
  
  public static final int LIBFPTR_PARAM_LOGICAL_NUMBER = 65589;
  
  public static final int LIBFPTR_PARAM_DATE_TIME = 65590;
  
  public static final int LIBFPTR_PARAM_FISCAL = 65591;
  
  public static final int LIBFPTR_PARAM_SHIFT_STATE = 65592;
  
  public static final int LIBFPTR_PARAM_CASHDRAWER_OPENED = 65593;
  
  public static final int LIBFPTR_PARAM_RECEIPT_PAPER_PRESENT = 65594;
  
  public static final int LIBFPTR_PARAM_COVER_OPENED = 65595;
  
  public static final int LIBFPTR_PARAM_SUBMODE = 65596;
  
  public static final int LIBFPTR_PARAM_RECEIPT_NUMBER = 65597;
  
  public static final int LIBFPTR_PARAM_DOCUMENT_NUMBER = 65598;
  
  public static final int LIBFPTR_PARAM_SHIFT_NUMBER = 65599;
  
  public static final int LIBFPTR_PARAM_RECEIPT_SUM = 65600;
  
  public static final int LIBFPTR_PARAM_RECEIPT_LINE_LENGTH = 65601;
  
  public static final int LIBFPTR_PARAM_RECEIPT_LINE_LENGTH_PIX = 65602;
  
  public static final int LIBFPTR_PARAM_MODEL_NAME = 65603;
  
  public static final int LIBFPTR_PARAM_UNIT_VERSION = 65604;
  
  public static final int LIBFPTR_PARAM_PRINTER_CONNECTION_LOST = 65605;
  
  public static final int LIBFPTR_PARAM_PRINTER_ERROR = 65606;
  
  public static final int LIBFPTR_PARAM_CUT_ERROR = 65607;
  
  public static final int LIBFPTR_PARAM_PRINTER_OVERHEAT = 65608;
  
  public static final int LIBFPTR_PARAM_UNIT_TYPE = 65609;
  
  public static final int LIBFPTR_PARAM_LICENSE_NUMBER = 65610;
  
  public static final int LIBFPTR_PARAM_LICENSE_ENTERED = 65611;
  
  public static final int LIBFPTR_PARAM_LICENSE = 65612;
  
  public static final int LIBFPTR_PARAM_SUM = 65613;
  
  public static final int LIBFPTR_PARAM_COUNT = 65614;
  
  public static final int LIBFPTR_PARAM_COUNTER_TYPE = 65615;
  
  public static final int LIBFPTR_PARAM_STEP_COUNTER_TYPE = 65616;
  
  public static final int LIBFPTR_PARAM_ERROR_TAG_NUMBER = 65617;
  
  public static final int LIBFPTR_PARAM_TABLE = 65618;
  
  public static final int LIBFPTR_PARAM_ROW = 65619;
  
  public static final int LIBFPTR_PARAM_FIELD = 65620;
  
  public static final int LIBFPTR_PARAM_FIELD_VALUE = 65621;
  
  public static final int LIBFPTR_PARAM_FN_DATA_TYPE = 65622;
  
  public static final int LIBFPTR_PARAM_TAG_NUMBER = 65623;
  
  public static final int LIBFPTR_PARAM_TAG_VALUE = 65624;
  
  public static final int LIBFPTR_PARAM_DOCUMENTS_COUNT = 65625;
  
  public static final int LIBFPTR_PARAM_FISCAL_SIGN = 65626;
  
  public static final int LIBFPTR_PARAM_DEVICE_FFD_VERSION = 65627;
  
  public static final int LIBFPTR_PARAM_FN_FFD_VERSION = 65628;
  
  public static final int LIBFPTR_PARAM_FFD_VERSION = 65629;
  
  public static final int LIBFPTR_PARAM_CHECK_SUM = 65630;
  
  public static final int LIBFPTR_PARAM_COMMODITY_NAME = 65631;
  
  public static final int LIBFPTR_PARAM_PRICE = 65632;
  
  public static final int LIBFPTR_PARAM_QUANTITY = 65633;
  
  public static final int LIBFPTR_PARAM_POSITION_SUM = 65634;
  
  public static final int LIBFPTR_PARAM_FN_TYPE = 65635;
  
  public static final int LIBFPTR_PARAM_FN_VERSION = 65636;
  
  public static final int LIBFPTR_PARAM_REGISTRATIONS_REMAIN = 65637;
  
  public static final int LIBFPTR_PARAM_REGISTRATIONS_COUNT = 65638;
  
  public static final int LIBFPTR_PARAM_NO_ERROR_IF_NOT_SUPPORTED = 65639;
  
  public static final int LIBFPTR_PARAM_OFD_EXCHANGE_STATUS = 65640;
  
  public static final int LIBFPTR_PARAM_FN_ERROR_DATA = 65641;
  
  public static final int LIBFPTR_PARAM_FN_ERROR_CODE = 65642;
  
  public static final int LIBFPTR_PARAM_ENVD_MODE = 65643;
  
  public static final int LIBFPTR_PARAM_DOCUMENT_CLOSED = 65644;
  
  public static final int LIBFPTR_PARAM_JSON_DATA = 65645;
  
  public static final int LIBFPTR_PARAM_COMMAND_SUBSYSTEM = 65646;
  
  public static final int LIBFPTR_PARAM_FN_OPERATION_TYPE = 65647;
  
  public static final int LIBFPTR_PARAM_FN_STATE = 65648;
  
  public static final int LIBFPTR_PARAM_ENVD_MODE_ENABLED = 65649;
  
  public static final int LIBFPTR_PARAM_SETTING_ID = 65650;
  
  public static final int LIBFPTR_PARAM_SETTING_VALUE = 65651;
  
  public static final int LIBFPTR_PARAM_MAPPING_KEY = 65652;
  
  public static final int LIBFPTR_PARAM_MAPPING_VALUE = 65653;
  
  public static final int LIBFPTR_PARAM_COMMODITY_PIECE = 65654;
  
  public static final int LIBFPTR_PARAM_POWER_SOURCE_TYPE = 65655;
  
  public static final int LIBFPTR_PARAM_BATTERY_CHARGE = 65656;
  
  public static final int LIBFPTR_PARAM_VOLTAGE = 65657;
  
  public static final int LIBFPTR_PARAM_USE_BATTERY = 65658;
  
  public static final int LIBFPTR_PARAM_BATTERY_CHARGING = 65659;
  
  public static final int LIBFPTR_PARAM_CAN_PRINT_WHILE_ON_BATTERY = 65660;
  
  public static final int LIBFPTR_PARAM_MAC_ADDRESS = 65661;
  
  public static final int LIBFPTR_PARAM_FN_FISCAL = 65662;
  
  public static final int LIBFPTR_PARAM_NETWORK_ERROR = 65663;
  
  public static final int LIBFPTR_PARAM_OFD_ERROR = 65664;
  
  public static final int LIBFPTR_PARAM_FN_ERROR = 65665;
  
  public static final int LIBFPTR_PARAM_COMMAND_CODE = 65666;
  
  public static final int LIBFPTR_PARAM_PRINTER_TEMPERATURE = 65667;
  
  public static final int LIBFPTR_PARAM_RECORDS_TYPE = 65668;
  
  public static final int LIBFPTR_PARAM_OFD_FISCAL_SIGN = 65669;
  
  public static final int LIBFPTR_PARAM_HAS_OFD_TICKET = 65670;
  
  public static final int LIBFPTR_PARAM_NO_SERIAL_NUMBER = 65671;
  
  public static final int LIBFPTR_PARAM_RTC_FAULT = 65672;
  
  public static final int LIBFPTR_PARAM_SETTINGS_FAULT = 65673;
  
  public static final int LIBFPTR_PARAM_COUNTERS_FAULT = 65674;
  
  public static final int LIBFPTR_PARAM_USER_MEMORY_FAULT = 65675;
  
  public static final int LIBFPTR_PARAM_SERVICE_COUNTERS_FAULT = 65676;
  
  public static final int LIBFPTR_PARAM_ATTRIBUTES_FAULT = 65677;
  
  public static final int LIBFPTR_PARAM_FN_FAULT = 65678;
  
  public static final int LIBFPTR_PARAM_INVALID_FN = 65679;
  
  public static final int LIBFPTR_PARAM_HARD_FAULT = 65680;
  
  public static final int LIBFPTR_PARAM_MEMORY_MANAGER_FAULT = 65681;
  
  public static final int LIBFPTR_PARAM_SCRIPTS_FAULT = 65682;
  
  public static final int LIBFPTR_PARAM_FULL_RESET = 65683;
  
  public static final int LIBFPTR_PARAM_WAIT_FOR_REBOOT = 65684;
  
  public static final int LIBFPTR_PARAM_SCALE_PERCENT = 65685;
  
  public static final int LIBFPTR_PARAM_FN_NEED_REPLACEMENT = 65686;
  
  public static final int LIBFPTR_PARAM_FN_RESOURCE_EXHAUSTED = 65687;
  
  public static final int LIBFPTR_PARAM_FN_MEMORY_OVERFLOW = 65688;
  
  public static final int LIBFPTR_PARAM_FN_OFD_TIMEOUT = 65689;
  
  public static final int LIBFPTR_PARAM_FN_CRITICAL_ERROR = 65690;
  
  public static final int LIBFPTR_PARAM_OFD_MESSAGE_READ = 65691;
  
  public static final int LIBFPTR_PARAM_DEVICE_MIN_FFD_VERSION = 65692;
  
  public static final int LIBFPTR_PARAM_DEVICE_MAX_FFD_VERSION = 65693;
  
  public static final int LIBFPTR_PARAM_DEVICE_UPTIME = 65694;
  
  public static final int LIBFPTR_PARAM_NOMENCLATURE_TYPE = 65695;
  
  public static final int LIBFPTR_PARAM_GTIN = 65696;
  
  public static final int LIBFPTR_PARAM_FN_DOCUMENT_TYPE = 65697;
  
  public static final int LIBFPTR_PARAM_NETWORK_ERROR_TEXT = 65698;
  
  public static final int LIBFPTR_PARAM_FN_ERROR_TEXT = 65699;
  
  public static final int LIBFPTR_PARAM_OFD_ERROR_TEXT = 65700;
  
  public static final int LIBFPTR_PARAM_USER_SCRIPT_ID = 65701;
  
  public static final int LIBFPTR_PARAM_USER_SCRIPT_PARAMETER = 65702;
  
  public static final int LIBFPTR_PARAM_USER_MEMORY_OPERATION = 65703;
  
  public static final int LIBFPTR_PARAM_USER_MEMORY_DATA = 65704;
  
  public static final int LIBFPTR_PARAM_USER_MEMORY_STRING = 65705;
  
  public static final int LIBFPTR_PARAM_USER_MEMORY_ADDRESS = 65706;
  
  public static final int LIBFPTR_PARAM_FN_PRESENT = 65707;
  
  public static final int LIBFPTR_PARAM_BLOCKED = 65708;
  
  public static final int LIBFPTR_PARAM_DOCUMENT_PRINTED = 65709;
  
  public static final int LIBFPTR_PARAM_DISCOUNT_SUM = 65710;
  
  public static final int LIBFPTR_PARAM_SURCHARGE_SUM = 65711;
  
  public static final int LIBFPTR_PARAM_LK_USER_CODE = 65712;
  
  public static final int LIBFPTR_PARAM_LICENSE_COUNT = 65713;
  
  public static final int LIBFPTR_PARAM_DEFER = 65714;
  
  public static final int LIBFPTR_PARAM_CAP_54FZ = 65715;
  
  public static final int LIBFPTR_PARAM_CAP_MANUAL_CLICHE_CONTROL = 65716;
  
  public static final int LIBFPTR_PARAM_CAP_PAYMENTS_COUNT = 65717;
  
  public static final int LIBFPTR_PARAM_FIRMWARE_CHUNK_SIZE = 65718;
  
  public static final int LIBFPTR_PARAM_FIRMWARE_CHUNK_DATA = 65719;
  
  public static final int LIBFPTR_PARAM_FN_FLAGS = 65720;
  
  public static final int LIBFPTR_PARAM_PRINT_FOOTER = 65721;
  
  public static final int LIBFPTR_PARAM_PUBLIC_KEY = 65722;
  
  public static final int LIBFPTR_PARAM_MAGIC_NUMBER = 65723;
  
  public static final int LIBFPTR_PARAM_SIGN = 65724;
  
  public static final int LIBFPTR_PARAM_SOFT_NAME = 65725;
  
  public static final int LIBFPTR_PARAM_SESSION_CODE = 65726;
  
  public static final int LIBFPTR_PARAM_ETHERNET_CONFIG_TIMEOUT = 65727;
  
  public static final int LIBFPTR_PARAM_ETHERNET_DHCP = 65728;
  
  public static final int LIBFPTR_PARAM_ETHERNET_IP = 65729;
  
  public static final int LIBFPTR_PARAM_ETHERNET_MASK = 65730;
  
  public static final int LIBFPTR_PARAM_ETHERNET_GATEWAY = 65731;
  
  public static final int LIBFPTR_PARAM_ETHERNET_PORT = 65732;
  
  public static final int LIBFPTR_PARAM_ETHERNET_DNS_IP = 65733;
  
  public static final int LIBFPTR_PARAM_ETHERNET_DNS_STATIC = 65734;
  
  public static final int LIBFPTR_PARAM_STORE_IN_JOURNAL = 65735;
  
  public static final int LIBFPTR_PARAM_NEW_PLATFORM = 65736;
  
  public static final int LIBFPTR_PARAM_UNIT_RELEASE_VERSION = 65737;
  
  public static final int LIBFPTR_PARAM_USE_VAT18 = 65738;
  
  public static final int LIBFPTR_PARAM_TAG_NAME = 65739;
  
  public static final int LIBFPTR_PARAM_TAG_TYPE = 65740;
  
  public static final int LIBFPTR_PARAM_TAG_IS_COMPLEX = 65741;
  
  public static final int LIBFPTR_PARAM_TAG_IS_REPEATABLE = 65742;
  
  public static final int LIBFPTR_PARAM_SHIFT_AUTO_OPENED = 65743;
  
  public static final int LIBFPTR_PARAM_CONTAINER_FIRMWARE_VERSION = 65744;
  
  public static final int LIBFPTR_PARAM_CONTAINER_CONFIGURATION_VERSION = 65745;
  
  public static final int LIBFPTR_PARAM_CONTAINER_BOOTLOADER_VERSION = 65746;
  
  public static final int LIBFPTR_PARAM_CONTAINER_SCRIPTS_VERSION = 65747;
  
  public static final int LIBFPTR_PARAM_PAPER_NEAR_END = 65748;
  
  public static final int LIBFPTR_PARAM_REPORT_ELECTRONICALLY = 65749;
  
  public static final int LIBFPTR_PARAM_ACTIVATION_METHOD = 65750;
  
  public static final int LIBFPTR_PARAM_KEYS = 65751;
  
  public static final int LIBFPTR_PARAM_UIN = 65752;
  
  public static final int LIBFPTR_PARAM_VERSION = 65753;
  
  public static final int LIBFPTR_PARAM_PUBLIC_KEY_SIGN = 65754;
  
  public static final int LIBFPTR_PARAM_CAP_DISABLE_PRINT_REPORTS = 65755;
  
  public static final int LIBFPTR_PARAM_REGISTRATION_NUMBER = 65756;
  
  public static final int LIBFPTR_PARAM_PIXEL_BUFFER = 65757;
  
  public static final int LIBFPTR_PARAM_REPEAT_NUMBER = 65758;
  
  public static final int LIBFPTR_PARAM_FIELD_TYPE = 65759;
  
  public static final int LIBFPTR_PARAM_MARKING_CODE = 65760;
  
  public static final int LIBFPTR_PARAM_CONTAINER_DIRECT_BOOT_VERSION = 65761;
  
  public static final int LIBFPTR_PARAM_SCRIPT_NAME = 65762;
  
  public static final int LIBFPTR_PARAM_SCRIPT_HASH = 65763;
  
  public static final int LIBFPTR_PARAM_RECORDS_ID = 65764;
  
  public static final int LIBFPTR_PARAM_USER_SCRIPT_RESULT_1 = 65765;
  
  public static final int LIBFPTR_PARAM_USER_SCRIPT_RESULT_2 = 65766;
  
  public static final int LIBFPTR_PARAM_USER_SCRIPT_RESULT_3 = 65767;
  
  public static final int LIBFPTR_PARAM_USER_SCRIPT_RESULT_4 = 65768;
  
  public static final int LIBFPTR_PARAM_USER_SCRIPT_RESULT_5 = 65769;
  
  public static final int LIBFPTR_PARAM_IS_USER_SCRIPT = 65770;
  
  public static final int LIBFPTR_PARAM_DOCUMENT_NUMBER_END = 65771;
  
  public static final int LIBFPTR_PARAM_SHIFT_NUMBER_END = 65772;
  
  public static final int LIBFPTR_PARAM_SCRIPT_CODE = 65773;
  
  public static final int LIBFPTR_PARAM_SCRIPT_RESULT = 65774;
  
  public static final int LIBFPTR_PARAM_SCRIPT_TYPE = 65775;
  
  public static final int LIBFPTR_PARAM_WIFI_CONFIG_TIMEOUT = 65776;
  
  public static final int LIBFPTR_PARAM_WIFI_DHCP = 65777;
  
  public static final int LIBFPTR_PARAM_WIFI_IP = 65778;
  
  public static final int LIBFPTR_PARAM_WIFI_MASK = 65779;
  
  public static final int LIBFPTR_PARAM_WIFI_GATEWAY = 65780;
  
  public static final int LIBFPTR_PARAM_WIFI_PORT = 65781;
  
  public static final int LIBFPTR_PARAM_UC_VERSION = 65782;
  
  public static final int LIBFPTR_PARAM_UC_AVAILABLE_MEMORY = 65783;
  
  public static final int LIBFPTR_PARAM_UC_USED_MEMORY_BY_SUMS = 65784;
  
  public static final int LIBFPTR_PARAM_UC_USED_MEMORY_BY_QUANTITIES = 65785;
  
  public static final int LIBFPTR_PARAM_UC_LAYER_1 = 65786;
  
  public static final int LIBFPTR_PARAM_UC_FLAGS_1 = 65787;
  
  public static final int LIBFPTR_PARAM_UC_MASK_1 = 65788;
  
  public static final int LIBFPTR_PARAM_UC_LAYER_VALUE_1 = 65789;
  
  public static final int LIBFPTR_PARAM_UC_LAYER_2 = 65790;
  
  public static final int LIBFPTR_PARAM_UC_FLAGS_2 = 65791;
  
  public static final int LIBFPTR_PARAM_UC_MASK_2 = 65792;
  
  public static final int LIBFPTR_PARAM_UC_LAYER_VALUE_2 = 65793;
  
  public static final int LIBFPTR_PARAM_UC_LAYER_3 = 65794;
  
  public static final int LIBFPTR_PARAM_UC_FLAGS_3 = 65795;
  
  public static final int LIBFPTR_PARAM_UC_MASK_3 = 65796;
  
  public static final int LIBFPTR_PARAM_UC_LAYER_VALUE_3 = 65797;
  
  public static final int LIBFPTR_PARAM_UC_LAYER_4 = 65798;
  
  public static final int LIBFPTR_PARAM_UC_FLAGS_4 = 65799;
  
  public static final int LIBFPTR_PARAM_UC_MASK_4 = 65800;
  
  public static final int LIBFPTR_PARAM_UC_LAYER_VALUE_4 = 65801;
  
  public static final int LIBFPTR_PARAM_RECEIPTS_COUNT = 65802;
  
  public static final int LIBFPTR_PARAM_PAYMENTS_SUM_CASH = 65803;
  
  public static final int LIBFPTR_PARAM_PAYMENTS_SUM_ELECTRONICALLY = 65804;
  
  public static final int LIBFPTR_PARAM_PAYMENTS_SUM_PREPAID = 65805;
  
  public static final int LIBFPTR_PARAM_PAYMENTS_SUM_CREDIT = 65806;
  
  public static final int LIBFPTR_PARAM_PAYMENTS_SUM_OTHER = 65807;
  
  public static final int LIBFPTR_PARAM_TAXES_SUM_VAT20 = 65808;
  
  public static final int LIBFPTR_PARAM_TAXES_SUM_VAT120 = 65809;
  
  public static final int LIBFPTR_PARAM_TAXES_SUM_VAT10 = 65810;
  
  public static final int LIBFPTR_PARAM_TAXES_SUM_VAT110 = 65811;
  
  public static final int LIBFPTR_PARAM_TAXES_SUM_VAT0 = 65812;
  
  public static final int LIBFPTR_PARAM_TAXES_SUM_NO = 65813;
  
  public static final int LIBFPTR_PARAM_CORRECTIONS_COUNT = 65814;
  
  public static final int LIBFPTR_PARAM_CORRECTIONS_SUM = 65815;
  
  public static final int LIBFPTR_PARAM_FN_COUNTERS_TYPE = 65816;
  
  public static final int LIBFPTR_PARAM_FN_DAYS_REMAIN = 65817;
  
  public static final int LIBFPTR_PARAM_FREE_MEMORY = 65818;
  
  public static final int LIBFPTR_PARAM_FN_MAX_FFD_VERSION = 65819;
  
  public static final int LIBFPTR_PARAM_RECEIPTS_SUM = 65820;
  
  public static final int LIBFPTR_PARAM_LICENSE_NAME = 65821;
  
  public static final int LIBFPTR_PARAM_UNIVERSAL_COUNTERS_FAULT = 65822;
  
  public static final int LIBFPTR_PARAM_USE_LICENSES = 65823;
  
  public static final int LIBFPTR_PARAM_LICENSE_VALID_FROM = 65824;
  
  public static final int LIBFPTR_PARAM_LICENSE_VALID_UNTIL = 65825;
  
  public static final int LIBFPTR_PARAM_MARKING_CODE_TYPE = 65826;
  
  public static final int LIBFPTR_PARAM_SETTING_NAME = 65827;
  
  public static final int LIBFPTR_PARAM_SETTING_TYPE = 65828;
  
  public static final int LIBFPTR_PARAM_FONT_WIDTH = 65829;
  
  public static final int LIBFPTR_PARAM_REMOTE_CALL = 65830;
  
  public static final int LIBFPTR_PARAM_SCRIPT_PARAMS = 65831;
  
  public static final int LIBFPTR_PARAM_IGNORE_EMPTY = 65832;
  
  public static final int LIBFPTR_PARAM_METHOD_DATA = 65833;
  
  public static final int LIBFPTR_PARAM_METHOD_RESULT = 65834;
  
  public static final int LIBFPTR_PARAM_RPC_SERVER_OS = 65835;
  
  public static final int LIBFPTR_PARAM_RPC_SERVER_VERSION = 65836;
  
  public static final int LIBFPTR_PARAM_RPC_DRIVER_VERSION = 65837;
  
  public static final int LIBFPTR_PARAM_LOCKED = 65838;
  
  public static final int LIBFPTR_PARAM_BOUND = 65839;
  
  public static final int LIBFPTR_PARAM_COMMODITIES_TABLE_FAULT = 65840;
  
  public static final int LIBFPTR_PARAM_HAS_ADDITIONAL_DATA = 65841;
  
  public static final int LIBFPTR_PARAM_FISCAL_SIGN_ARCHIVE = 65842;
  
  public static final int LIBFPTR_PARAM_COMMAND_GROUP = 65843;
  
  public static final int LIBFPTR_PARAM_ERROR_CODE = 65844;
  
  public static final int LIBFPTR_PARAM_MARKING_WAIT_FOR_VALIDATION_RESULT = 65845;
  
  public static final int LIBFPTR_PARAM_MARKING_CODE_STATUS = 65846;
  
  public static final int LIBFPTR_PARAM_MARKING_CODE_VALIDATION_RESULT = 65847;
  
  public static final int LIBFPTR_PARAM_MARKING_CODE_OFFLINE_VALIDATION_ERROR = 65848;
  
  public static final int LIBFPTR_PARAM_MARKING_CODE_ONLINE_VALIDATION_ERROR = 65849;
  
  public static final int LIBFPTR_PARAM_MARKING_CODE_VALIDATION_READY = 65850;
  
  public static final int LIBFPTR_PARAM_MEASUREMENT_UNIT = 65851;
  
  public static final int LIBFPTR_PARAM_MARKING_PROCESSING_MODE = 65852;
  
  public static final int LIBFPTR_PARAM_MARKING_FRACTIONAL_QUANTITY = 65853;
  
  public static final int LIBFPTR_PARAM_PRODUCT_CODE = 65854;
  
  public static final int LIBFPTR_PARAM_TRADE_MARKED_PRODUCTS = 65855;
  
  public static final int LIBFPTR_PARAM_INSURANCE_ACTIVITY = 65856;
  
  public static final int LIBFPTR_PARAM_PAWN_SHOP_ACTIVITY = 65857;
  
  public static final int LIBFPTR_PARAM_TLV_LIST = 65858;
  
  public static final int LIBFPTR_PARAM_CHECK_MARKING_SERVER_READY = 65859;
  
  public static final int LIBFPTR_PARAM_MARKING_SERVER_RESPONSE_TIME = 65860;
  
  public static final int LIBFPTR_PARAM_MARKING_SERVER_ERROR_CODE = 65861;
  
  public static final int LIBFPTR_PARAM_MARKING_SERVER_ERROR_DESCRIPTION = 65862;
  
  public static final int LIBFPTR_PARAM_ISM_ERROR = 65863;
  
  public static final int LIBFPTR_PARAM_ISM_ERROR_TEXT = 65864;
  
  public static final int LIBFPTR_PARAM_MARKING_MODE_CHECKING_STATUS = 65865;
  
  public static final int LIBFPTR_PARAM_MARK_CHECKING_COUNT = 65866;
  
  public static final int LIBFPTR_PARAM_MARK_SOLD_COUNT = 65867;
  
  public static final int LIBFPTR_PARAM_NOTICE_IS_BEGIN = 65868;
  
  public static final int LIBFPTR_PARAM_NOTICE_FREE_MEMORY = 65869;
  
  public static final int LIBFPTR_PARAM_NOTICE_COUNT = 65870;
  
  public static final int LIBFPTR_PARAM_MARKING_NOT_SEND_TO_SERVER = 65871;
  
  public static final int LIBFPTR_PARAM_DOCUMENT_TYPE = 65872;
  
  public static final int LIBFPTR_PARAM_PRINT_REPORT = 65873;
  
  public static final int LIBFPTR_PARAM_FN_EXECUTION = 65874;
  
  public static final int LIBFPTR_PARAM_MCU_SN = 65875;
  
  public static final int LIBFPTR_PARAM_MCU_PART_ID = 65876;
  
  public static final int LIBFPTR_PARAM_MCU_PART_NAME = 65877;
  
  public static final int LIBFPTR_PARAM_IS_REQUEST_SENT = 65878;
  
  public static final int LIBFPTR_PARAM_FN_CHECK_MARK_TIME = 65879;
  
  public static final int LIBFPTR_PARAM_SENDING_MARK_TIME = 65880;
  
  public static final int LIBFPTR_PARAM_MARKING_SERVER_EXCHANGE_TIME = 65881;
  
  public static final int LIBFPTR_PARAM_FULL_SENDING_MARK_TIME = 65882;
  
  public static final int LIBFPTR_PARAM_MARK_CHECKING_STATUS_IN_CASH = 65883;
  
  public static final int LIBFPTR_PARAM_MARK_CHECKING_TYPE_IN_CASH = 65884;
  
  public static final int LIBFPTR_PARAM_MARK_CHECKING_STAGE_IN_CASH = 65885;
  
  public static final int LIBFPTR_PARAM_MARKING_CODE_ONLINE_VALIDATION_RESULT = 65886;
  
  public static final int LIBFPTR_PARAM_MARKING_CODE_ONLINE_VALIDATION_ERROR_DESCRIPTION = 65887;
  
  public static final int LIBFPTR_PARAM_FN_CONTAINS_KEYS_UPDATER_SERVER_URI = 65888;
  
  public static final int LIBFPTR_PARAM_CLEAR_MARKING_TABLE = 65889;
  
  public static final int LIBFPTR_PARAM_MODULE_ADDRESS = 65890;
  
  public static final int LIBFPTR_PARAM_SEGMENT_ADDRESS = 65891;
  
  public static final int LIBFPTR_PARAM_LAST_SUCCESSFUL_OKP = 65892;
  
  public static final int LIBFPTR_PARAM_FN_SERIAL_NUMBER = 65893;
  
  public static final int LIBFPTR_PARAM_ECR_REGISTRATION_NUMBER = 65894;
  
  public static final int LIBFPTR_PARAM_OFD_VATIN = 65895;
  
  public static final int LIBFPTR_PARAM_FNS_URL = 65896;
  
  public static final int LIBFPTR_PARAM_MACHINE_NUMBER = 65897;
  
  public static final int LIBFPTR_PARAM_MARKING_PRODUCT_ID = 65898;
  
  public static final int LIBFPTR_PARAM_TIMEOUT = 65899;
  
  public static final int LIBFPTR_PARAM_PRINT_UPDATE_FNM_KEYS_REPORT = 65900;
  
  public static final int LIBFPTR_PARAM_FN_KEYS_UPDATER_SERVER_URI = 65901;
  
  public static final int LIBFPTR_PARAM_DOCUMENT_ELECTRONICALLY = 65902;
  
  public static final int LIBFPTR_PARAM_FORMAT_TEXT = 65903;
  
  public static final int LIBFPTR_PARAM_RECEIPT_SIZE = 65904;
  
  public static final int LIBFPTR_PARAM_MARK_SIZE = 65905;
  
  public static final int LIBFPTR_PARAM_MCU_TEMPERATURE = 65906;
  
  public static final int LIBFPTR_PARAM_DATA_FOR_SEND_IS_EMPTY = 65907;
  
  public static final int LIBFPTR_PARAM_AVAILABLE_CLOSING = 65908;
  
  public static final int LIBFPTR_PARAM_AVAILABLE_CANCELLATION = 65909;
  
  public static final int LIBFPTR_PARAM_AVAILABLE_POSITION_ADDING = 65910;
  
  public static final int LIBFPTR_PARAM_AVAILABLE_PAYMENT = 65911;
  
  public static final int LIBFPTR_PARAM_AVAILABLE_TOTAL = 65912;
  
  public static final int LIBFPTR_PARAM_AVAILABLE_ATTRIBUTES_ADDING = 65913;
  
  public static final int LIBFPTR_PARAM_OPERATOR_REGISTERED = 65914;
  
  public static final int LIBFPTR_PARAM_DEVICE_PLATFORM_VERSION = 65915;
  
  public static final int LIBFPTR_PARAM_GUID = 65916;
  
  public static final int LIBFPTR_PARAM_PATTERN_REGISTERS = 65917;
  
  public static final int LIBFPTR_PARAM_PATTERN_TAGS = 65918;
  
  public static final int LIBFPTR_PARAM_PATTERN_SETTINGS = 65919;
  
  public static final int LIBFPTR_PARAM_VENDING = 65920;
  
  public static final int LIBFPTR_PARAM_CATERING = 65921;
  
  public static final int LIBFPTR_PARAM_WHOLESALE = 65922;
  
  public static final int LIBFPTR_PARAM_REGISTRATION_POSITION_FORM = 65923;
  
  public static final int LIBFPTR_PARAM_MERGE_POSITIONS = 65924;
  
  public static final int LIBFPTR_PARAM_DATAFLASH_JEDEC_ID = 65925;
  
  public static final int LIBFPTR_PARAM_DATAFLASH_NAME = 65926;
  
  public static final int LIBFPTR_PARAM_DATAFLASH_SIZE = 65927;
  
  public static final int LIBFPTR_PARAM_FRAM_EEPROM_NAME = 65928;
  
  public static final int LIBFPTR_PARAM_FRAM_EEPROM_SIZE = 65929;
  
  public static final int LIBFPTR_PARAM_MARKING_NOT_FORM_REQUEST = 65930;
  
  public static final int LIBFPTR_PARAM_LAST_SUCCESS_FNM_UPDATE_KEYS_DATE_TIME = 65931;
  
  public static final int LIBFPTR_PARAM_LAST_ATTEMPTION_FNM_UPDATE_KEYS_DATE_TIME = 65932;
  
  public static final int LIBFPTR_PARAM_COUNT_ATTEMPTION_FNM_UPDATE_KEYS = 65933;
  
  public static final int LIBFPTR_OK = 0;
  
  public static final int LIBFPTR_ERROR_CONNECTION_DISABLED = 1;
  
  public static final int LIBFPTR_ERROR_NO_CONNECTION = 2;
  
  public static final int LIBFPTR_ERROR_PORT_BUSY = 3;
  
  public static final int LIBFPTR_ERROR_PORT_NOT_AVAILABLE = 4;
  
  public static final int LIBFPTR_ERROR_INCORRECT_DATA = 5;
  
  public static final int LIBFPTR_ERROR_INTERNAL = 6;
  
  public static final int LIBFPTR_ERROR_UNSUPPORTED_CAST = 7;
  
  public static final int LIBFPTR_ERROR_NO_REQUIRED_PARAM = 8;
  
  public static final int LIBFPTR_ERROR_INVALID_SETTINGS = 9;
  
  public static final int LIBFPTR_ERROR_NOT_CONFIGURED = 10;
  
  public static final int LIBFPTR_ERROR_NOT_SUPPORTED = 11;
  
  public static final int LIBFPTR_ERROR_INVALID_MODE = 12;
  
  public static final int LIBFPTR_ERROR_INVALID_PARAM = 13;
  
  public static final int LIBFPTR_ERROR_NOT_LOADED = 14;
  
  public static final int LIBFPTR_ERROR_UNKNOWN = 15;
  
  public static final int LIBFPTR_ERROR_INVALID_SUM = 16;
  
  public static final int LIBFPTR_ERROR_INVALID_QUANTITY = 17;
  
  public static final int LIBFPTR_ERROR_CASH_COUNTER_OVERFLOW = 18;
  
  public static final int LIBFPTR_ERROR_LAST_OPERATION_STORNO_DENIED = 19;
  
  public static final int LIBFPTR_ERROR_STORNO_BY_CODE_DENIED = 20;
  
  public static final int LIBFPTR_ERROR_LAST_OPERATION_NOT_REPEATABLE = 21;
  
  public static final int LIBFPTR_ERROR_DISCOUNT_NOT_REPEATABLE = 22;
  
  public static final int LIBFPTR_ERROR_DISCOUNT_DENIED = 23;
  
  public static final int LIBFPTR_ERROR_INVALID_COMMODITY_CODE = 24;
  
  public static final int LIBFPTR_ERROR_INVALID_COMMODITY_BARCODE = 25;
  
  public static final int LIBFPTR_ERROR_INVALID_COMMAND_FORMAT = 26;
  
  public static final int LIBFPTR_ERROR_INVALID_COMMAND_LENGTH = 27;
  
  public static final int LIBFPTR_ERROR_BLOCKED_IN_DATE_INPUT_MODE = 28;
  
  public static final int LIBFPTR_ERROR_NEED_DATE_ACCEPT = 29;
  
  public static final int LIBFPTR_ERROR_NO_MORE_DATA = 30;
  
  public static final int LIBFPTR_ERROR_NO_ACCEPT_OR_CANCEL = 31;
  
  public static final int LIBFPTR_ERROR_BLOCKED_BY_REPORT_INTERRUPTION = 32;
  
  public static final int LIBFPTR_ERROR_DISABLE_CASH_CONTROL_DENIED = 33;
  
  public static final int LIBFPTR_ERROR_MODE_BLOCKED = 34;
  
  public static final int LIBFPTR_ERROR_CHECK_DATE_TIME = 35;
  
  public static final int LIBFPTR_ERROR_DATE_TIME_LESS_THAN_FS = 36;
  
  public static final int LIBFPTR_ERROR_CLOSE_ARCHIVE_DENIED = 37;
  
  public static final int LIBFPTR_ERROR_COMMODITY_NOT_FOUND = 38;
  
  public static final int LIBFPTR_ERROR_WEIGHT_BARCODE_WITH_INVALID_QUANTITY = 39;
  
  public static final int LIBFPTR_ERROR_RECEIPT_BUFFER_OVERFLOW = 40;
  
  public static final int LIBFPTR_ERROR_QUANTITY_TOO_FEW = 41;
  
  public static final int LIBFPTR_ERROR_STORNO_TOO_MUCH = 42;
  
  public static final int LIBFPTR_ERROR_BLOCKED_COMMODITY_NOT_FOUND = 43;
  
  public static final int LIBFPTR_ERROR_NO_PAPER = 44;
  
  public static final int LIBFPTR_ERROR_COVER_OPENED = 45;
  
  public static final int LIBFPTR_ERROR_PRINTER_FAULT = 46;
  
  public static final int LIBFPTR_ERROR_MECHANICAL_FAULT = 47;
  
  public static final int LIBFPTR_ERROR_INVALID_RECEIPT_TYPE = 48;
  
  public static final int LIBFPTR_ERROR_INVALID_UNIT_TYPE = 49;
  
  public static final int LIBFPTR_ERROR_NO_MEMORY = 50;
  
  public static final int LIBFPTR_ERROR_PICTURE_NOT_FOUND = 51;
  
  public static final int LIBFPTR_ERROR_NONCACH_PAYMENTS_TOO_MUCH = 52;
  
  public static final int LIBFPTR_ERROR_RETURN_DENIED = 53;
  
  public static final int LIBFPTR_ERROR_PAYMENTS_OVERFLOW = 54;
  
  public static final int LIBFPTR_ERROR_BUSY = 55;
  
  public static final int LIBFPTR_ERROR_GSM = 56;
  
  public static final int LIBFPTR_ERROR_INVALID_DISCOUNT = 57;
  
  public static final int LIBFPTR_ERROR_OPERATION_AFTER_DISCOUNT_DENIED = 58;
  
  public static final int LIBFPTR_ERROR_INVALID_DEPARTMENT = 59;
  
  public static final int LIBFPTR_ERROR_INVALID_PAYMENT_TYPE = 60;
  
  public static final int LIBFPTR_ERROR_MULTIPLICATION_OVERFLOW = 61;
  
  public static final int LIBFPTR_ERROR_DENIED_BY_SETTINGS = 62;
  
  public static final int LIBFPTR_ERROR_TOTAL_OVERFLOW = 63;
  
  public static final int LIBFPTR_ERROR_DENIED_IN_ANNULATION_RECEIPT = 64;
  
  public static final int LIBFPTR_ERROR_JOURNAL_OVERFLOW = 65;
  
  public static final int LIBFPTR_ERROR_NOT_FULLY_PAID = 66;
  
  public static final int LIBFPTR_ERROR_DENIED_IN_RETURN_RECEIPT = 67;
  
  public static final int LIBFPTR_ERROR_SHIFT_EXPIRED = 68;
  
  public static final int LIBFPTR_ERROR_DENIED_IN_SELL_RECEIPT = 69;
  
  public static final int LIBFPTR_ERROR_FISCAL_MEMORY_OVERFLOW = 70;
  
  public static final int LIBFPTR_ERROR_INVALID_PASSWORD = 71;
  
  public static final int LIBFPTR_ERROR_JOURNAL_BUSY = 72;
  
  public static final int LIBFPTR_ERROR_DENIED_IN_CLOSED_SHIFT = 73;
  
  public static final int LIBFPTR_ERROR_INVALID_TABLE_NUMBER = 74;
  
  public static final int LIBFPTR_ERROR_INVALID_ROW_NUMBER = 75;
  
  public static final int LIBFPTR_ERROR_INVALID_FIELD_NUMBER = 76;
  
  public static final int LIBFPTR_ERROR_INVALID_DATE_TIME = 77;
  
  public static final int LIBFPTR_ERROR_INVALID_STORNO_SUM = 78;
  
  public static final int LIBFPTR_ERROR_CHANGE_CALCULATION = 79;
  
  public static final int LIBFPTR_ERROR_NO_CASH = 80;
  
  public static final int LIBFPTR_ERROR_DENIED_IN_CLOSED_RECEIPT = 81;
  
  public static final int LIBFPTR_ERROR_DENIED_IN_OPENED_RECEIPT = 82;
  
  public static final int LIBFPTR_ERROR_DENIED_IN_OPENED_SHIFT = 83;
  
  public static final int LIBFPTR_ERROR_SERIAL_NUMBER_ALREADY_ENTERED = 84;
  
  public static final int LIBFPTR_ERROR_TOO_MUCH_REREGISTRATIONS = 85;
  
  public static final int LIBFPTR_ERROR_INVALID_SHIFT_NUMBER = 86;
  
  public static final int LIBFPTR_ERROR_INVALID_SERIAL_NUMBER = 87;
  
  public static final int LIBFPTR_ERROR_INVALID_RNM_VATIN = 88;
  
  public static final int LIBFPTR_ERROR_FISCAL_PRINTER_NOT_ACTIVATED = 89;
  
  public static final int LIBFPTR_ERROR_SERIAL_NUMBER_NOT_ENTERED = 90;
  
  public static final int LIBFPTR_ERROR_NO_MORE_REPORTS = 91;
  
  public static final int LIBFPTR_ERROR_MODE_NOT_ACTIVATED = 92;
  
  public static final int LIBFPTR_ERROR_RECORD_NOT_FOUND_IN_JOURNAL = 93;
  
  public static final int LIBFPTR_ERROR_INVALID_LICENSE = 94;
  
  public static final int LIBFPTR_ERROR_NEED_FULL_RESET = 95;
  
  public static final int LIBFPTR_ERROR_DENIED_BY_LICENSE = 96;
  
  public static final int LIBFPTR_ERROR_DISCOUNT_CANCELLATION_DENIED = 97;
  
  public static final int LIBFPTR_ERROR_CLOSE_RECEIPT_DENIED = 98;
  
  public static final int LIBFPTR_ERROR_INVALID_ROUTE_NUMBER = 99;
  
  public static final int LIBFPTR_ERROR_INVALID_START_ZONE_NUMBER = 100;
  
  public static final int LIBFPTR_ERROR_INVALID_END_ZONE_NUMBER = 101;
  
  public static final int LIBFPTR_ERROR_INVALID_RATE_TYPE = 102;
  
  public static final int LIBFPTR_ERROR_INVALID_RATE = 103;
  
  public static final int LIBFPTR_ERROR_FISCAL_MODULE_EXCHANGE = 104;
  
  public static final int LIBFPTR_ERROR_NEED_TECHNICAL_SUPPORT = 105;
  
  public static final int LIBFPTR_ERROR_SHIFT_NUMBERS_DID_NOT_MATCH = 106;
  
  public static final int LIBFPTR_ERROR_DEVICE_NOT_FOUND = 107;
  
  public static final int LIBFPTR_ERROR_EXTERNAL_DEVICE_CONNECTION = 108;
  
  public static final int LIBFPTR_ERROR_DISPENSER_INVALID_STATE = 109;
  
  public static final int LIBFPTR_ERROR_INVALID_POSITIONS_COUNT = 110;
  
  public static final int LIBFPTR_ERROR_DISPENSER_INVALID_NUMBER = 111;
  
  public static final int LIBFPTR_ERROR_INVALID_DIVIDER = 112;
  
  public static final int LIBFPTR_ERROR_FN_ACTIVATION_DENIED = 113;
  
  public static final int LIBFPTR_ERROR_PRINTER_OVERHEAT = 114;
  
  public static final int LIBFPTR_ERROR_FN_EXCHANGE = 115;
  
  public static final int LIBFPTR_ERROR_FN_INVALID_FORMAT = 116;
  
  public static final int LIBFPTR_ERROR_FN_INVALID_STATE = 117;
  
  public static final int LIBFPTR_ERROR_FN_FAULT = 118;
  
  public static final int LIBFPTR_ERROR_FN_CRYPTO_FAULT = 119;
  
  public static final int LIBFPTR_ERROR_FN_EXPIRED = 120;
  
  public static final int LIBFPTR_ERROR_FN_OVERFLOW = 121;
  
  public static final int LIBFPTR_ERROR_FN_INVALID_DATE_TIME = 122;
  
  public static final int LIBFPTR_ERROR_FN_NO_MORE_DATA = 123;
  
  public static final int LIBFPTR_ERROR_FN_TOTAL_OVERFLOW = 124;
  
  public static final int LIBFPTR_ERROR_BUFFER_OVERFLOW = 125;
  
  public static final int LIBFPTR_ERROR_PRINT_SECOND_COPY_DENIED = 126;
  
  public static final int LIBFPTR_ERROR_NEED_RESET_JOURNAL = 127;
  
  public static final int LIBFPTR_ERROR_TAX_SUM_TOO_MUCH = 128;
  
  public static final int LIBFPTR_ERROR_TAX_ON_LAST_OPERATION_DENIED = 129;
  
  public static final int LIBFPTR_ERROR_INVALID_FN_NUMBER = 130;
  
  public static final int LIBFPTR_ERROR_TAX_CANCEL_DENIED = 131;
  
  public static final int LIBFPTR_ERROR_LOW_BATTERY = 132;
  
  public static final int LIBFPTR_ERROR_FN_INVALID_COMMAND = 133;
  
  public static final int LIBFPTR_ERROR_FN_COMMAND_OVERFLOW = 134;
  
  public static final int LIBFPTR_ERROR_FN_NO_TRANSPORT_CONNECTION = 135;
  
  public static final int LIBFPTR_ERROR_FN_CRYPTO_HAS_EXPIRED = 136;
  
  public static final int LIBFPTR_ERROR_FN_RESOURCE_HAS_EXPIRED = 137;
  
  public static final int LIBFPTR_ERROR_INVALID_MESSAGE_FROM_OFD = 138;
  
  public static final int LIBFPTR_ERROR_FN_HAS_NOT_SEND_DOCUMENTS = 139;
  
  public static final int LIBFPTR_ERROR_FN_TIMEOUT = 140;
  
  public static final int LIBFPTR_ERROR_FN_SHIFT_EXPIRED = 141;
  
  public static final int LIBFPTR_ERROR_FN_INVALID_TIME_DIFFERENCE = 142;
  
  public static final int LIBFPTR_ERROR_INVALID_TAXATION_TYPE = 143;
  
  public static final int LIBFPTR_ERROR_INVALID_TAX_TYPE = 144;
  
  public static final int LIBFPTR_ERROR_INVALID_COMMODITY_PAYMENT_TYPE = 145;
  
  public static final int LIBFPTR_ERROR_INVALID_COMMODITY_CODE_TYPE = 146;
  
  public static final int LIBFPTR_ERROR_EXCISABLE_COMMODITY_DENIED = 147;
  
  public static final int LIBFPTR_ERROR_FISCAL_PROPERTY_WRITE = 148;
  
  public static final int LIBFPTR_ERROR_INVALID_COUNTER_TYPE = 149;
  
  public static final int LIBFPTR_ERROR_CUTTER_FAULT = 150;
  
  public static final int LIBFPTR_ERROR_REPORT_INTERRUPTED = 151;
  
  public static final int LIBFPTR_ERROR_INVALID_LEFT_MARGIN = 152;
  
  public static final int LIBFPTR_ERROR_INVALID_ALIGNMENT = 153;
  
  public static final int LIBFPTR_ERROR_INVALID_TAX_MODE = 154;
  
  public static final int LIBFPTR_ERROR_FILE_NOT_FOUND = 155;
  
  public static final int LIBFPTR_ERROR_PICTURE_TOO_BIG = 156;
  
  public static final int LIBFPTR_ERROR_INVALID_BARCODE_PARAMS = 157;
  
  public static final int LIBFPTR_ERROR_FISCAL_PROPERTY_DENIED = 158;
  
  public static final int LIBFPTR_ERROR_FN_INTERFACE = 159;
  
  public static final int LIBFPTR_ERROR_DATA_DUPLICATE = 160;
  
  public static final int LIBFPTR_ERROR_NO_REQUIRED_FISCAL_PROPERTY = 161;
  
  public static final int LIBFPTR_ERROR_FN_READ_DOCUMENT = 162;
  
  public static final int LIBFPTR_ERROR_FLOAT_OVERFLOW = 163;
  
  public static final int LIBFPTR_ERROR_INVALID_SETTING_VALUE = 164;
  
  public static final int LIBFPTR_ERROR_HARD_FAULT = 165;
  
  public static final int LIBFPTR_ERROR_FN_NOT_FOUND = 166;
  
  public static final int LIBFPTR_ERROR_INVALID_AGENT_FISCAL_PROPERTY = 167;
  
  public static final int LIBFPTR_ERROR_INVALID_FISCAL_PROPERTY_VALUE_1002_1056 = 168;
  
  public static final int LIBFPTR_ERROR_INVALID_FISCAL_PROPERTY_VALUE_1002_1017 = 169;
  
  public static final int LIBFPTR_ERROR_SCRIPT = 170;
  
  public static final int LIBFPTR_ERROR_INVALID_USER_MEMORY_INDEX = 171;
  
  public static final int LIBFPTR_ERROR_NO_ACTIVE_OPERATOR = 172;
  
  public static final int LIBFPTR_ERROR_REGISTRATION_REPORT_INTERRUPTED = 173;
  
  public static final int LIBFPTR_ERROR_CLOSE_FN_REPORT_INTERRUPTED = 174;
  
  public static final int LIBFPTR_ERROR_OPEN_SHIFT_REPORT_INTERRUPTED = 175;
  
  public static final int LIBFPTR_ERROR_OFD_EXCHANGE_REPORT_INTERRUPTED = 176;
  
  public static final int LIBFPTR_ERROR_CLOSE_RECEIPT_INTERRUPTED = 177;
  
  public static final int LIBFPTR_ERROR_FN_QUERY_INTERRUPTED = 178;
  
  public static final int LIBFPTR_ERROR_RTC_FAULT = 179;
  
  public static final int LIBFPTR_ERROR_MEMORY_FAULT = 180;
  
  public static final int LIBFPTR_ERROR_CHIP_FAULT = 181;
  
  public static final int LIBFPTR_ERROR_TEMPLATES_CORRUPTED = 182;
  
  public static final int LIBFPTR_ERROR_INVALID_MAC_ADDRESS = 183;
  
  public static final int LIBFPTR_ERROR_INVALID_SCRIPT_NUMBER = 184;
  
  public static final int LIBFPTR_ERROR_SCRIPTS_FAULT = 185;
  
  public static final int LIBFPTR_ERROR_INVALID_SCRIPTS_VERSION = 186;
  
  public static final int LIBFPTR_ERROR_INVALID_CLICHE_FORMAT = 187;
  
  public static final int LIBFPTR_ERROR_WAIT_FOR_REBOOT = 188;
  
  public static final int LIBFPTR_ERROR_NO_LICENSE = 189;
  
  public static final int LIBFPTR_ERROR_INVALID_FFD_VERSION = 190;
  
  public static final int LIBFPTR_ERROR_CHANGE_SETTING_DENIED = 191;
  
  public static final int LIBFPTR_ERROR_INVALID_NOMENCLATURE_TYPE = 192;
  
  public static final int LIBFPTR_ERROR_INVALID_GTIN = 193;
  
  public static final int LIBFPTR_ERROR_NEGATIVE_MATH_RESULT = 194;
  
  public static final int LIBFPTR_ERROR_FISCAL_PROPERTIES_COMBINATION = 195;
  
  public static final int LIBFPTR_ERROR_OPERATOR_LOGIN = 196;
  
  public static final int LIBFPTR_ERROR_INVALID_INTERNET_CHANNEL = 197;
  
  public static final int LIBFPTR_ERROR_DATETIME_NOT_SYNCRONIZED = 198;
  
  public static final int LIBFPTR_ERROR_JOURNAL = 199;
  
  public static final int LIBFPTR_ERROR_DENIED_IN_OPENED_DOC = 200;
  
  public static final int LIBFPTR_ERROR_DENIED_IN_CLOSED_DOC = 201;
  
  public static final int LIBFPTR_ERROR_LICENSE_MEMORY_OVERFLOW = 202;
  
  public static final int LIBFPTR_ERROR_NEED_CANCEL_DOCUMENT = 203;
  
  public static final int LIBFPTR_ERROR_REGISTERS_NOT_INITIALIZED = 204;
  
  public static final int LIBFPTR_ERROR_TOTAL_REQUIRED = 205;
  
  public static final int LIBFPTR_ERROR_SETTINGS_FAULT = 206;
  
  public static final int LIBFPTR_ERROR_COUNTERS_FAULT = 207;
  
  public static final int LIBFPTR_ERROR_USER_MEMORY_FAULT = 208;
  
  public static final int LIBFPTR_ERROR_SERVICE_COUNTERS_FAULT = 209;
  
  public static final int LIBFPTR_ERROR_ATTRIBUTES_FAULT = 210;
  
  public static final int LIBFPTR_ERROR_ALREADY_IN_UPDATE_MODE = 211;
  
  public static final int LIBFPTR_ERROR_INVALID_FIRMWARE = 212;
  
  public static final int LIBFPTR_ERROR_INVALID_CHANNEL = 213;
  
  public static final int LIBFPTR_ERROR_INTERFACE_DOWN = 214;
  
  public static final int LIBFPTR_ERROR_INVALID_FISCAL_PROPERTY_VALUE_1212_1030 = 215;
  
  public static final int LIBFPTR_ERROR_INVALID_FISCAL_PROPERTY_VALUE_1214 = 216;
  
  public static final int LIBFPTR_ERROR_INVALID_FISCAL_PROPERTY_VALUE_1212 = 217;
  
  public static final int LIBFPTR_ERROR_SYNC_TIME = 218;
  
  public static final int LIBFPTR_ERROR_VAT18_VAT20_IN_RECEIPT = 219;
  
  public static final int LIBFPTR_ERROR_PICTURE_NOT_CLOSED = 220;
  
  public static final int LIBFPTR_ERROR_INTERFACE_BUSY = 221;
  
  public static final int LIBFPTR_ERROR_INVALID_PICTURE_NUMBER = 222;
  
  public static final int LIBFPTR_ERROR_INVALID_CONTAINER = 223;
  
  public static final int LIBFPTR_ERROR_ARCHIVE_CLOSED = 224;
  
  public static final int LIBFPTR_ERROR_NEED_REGISTRATION = 225;
  
  public static final int LIBFPTR_ERROR_DENIED_DURING_UPDATE = 226;
  
  public static final int LIBFPTR_ERROR_INVALID_TOTAL = 227;
  
  public static final int LIBFPTR_ERROR_MARKING_CODE_CONFLICT = 228;
  
  public static final int LIBFPTR_ERROR_INVALID_RECORDS_ID = 229;
  
  public static final int LIBFPTR_ERROR_INVALID_SIGNATURE = 230;
  
  public static final int LIBFPTR_ERROR_INVALID_EXCISE_SUM = 231;
  
  public static final int LIBFPTR_ERROR_NO_DOCUMENTS_FOUND_IN_JOURNAL = 232;
  
  public static final int LIBFPTR_ERROR_INVALID_SCRIPT_TYPE = 233;
  
  public static final int LIBFPTR_ERROR_INVALID_SCRIPT_NAME = 234;
  
  public static final int LIBFPTR_ERROR_INVALID_POSITIONS_COUNT_WITH_1162 = 235;
  
  public static final int LIBFPTR_ERROR_INVALID_UC_COUNTER = 236;
  
  public static final int LIBFPTR_ERROR_INVALID_UC_TAG = 237;
  
  public static final int LIBFPTR_ERROR_INVALID_UC_IDX = 238;
  
  public static final int LIBFPTR_ERROR_INVALID_UC_SIZE = 239;
  
  public static final int LIBFPTR_ERROR_INVALID_UC_CONFIG = 240;
  
  public static final int LIBFPTR_ERROR_CONNECTION_LOST = 241;
  
  public static final int LIBFPTR_ERROR_UNIVERSAL_COUNTERS_FAULT = 242;
  
  public static final int LIBFPTR_ERROR_INVALID_TAX_SUM = 243;
  
  public static final int LIBFPTR_ERROR_INVALID_MARKING_CODE_TYPE = 244;
  
  public static final int LIBFPTR_ERROR_LICENSE_HARD_FAULT = 245;
  
  public static final int LIBFPTR_ERROR_LICENSE_INVALID_SIGN = 246;
  
  public static final int LIBFPTR_ERROR_LICENSE_INVALID_SERIAL = 247;
  
  public static final int LIBFPTR_ERROR_LICENSE_INVALID_TIME = 248;
  
  public static final int LIBFPTR_ERROR_DOCUMENT_CANCELED = 249;
  
  public static final int LIBFPTR_ERROR_INVALID_SCRIPT_PARAMS = 250;
  
  public static final int LIBFPTR_ERROR_CLICHE_TOO_LONG = 251;
  
  public static final int LIBFPTR_ERROR_COMMODITIES_TABLE_FAULT = 252;
  
  public static final int LIBFPTR_ERROR_COMMODITIES_TABLE = 253;
  
  public static final int LIBFPTR_ERROR_COMMODITIES_TABLE_INVALID_TAG = 254;
  
  public static final int LIBFPTR_ERROR_COMMODITIES_TABLE_INVALID_TAG_SIZE = 255;
  
  public static final int LIBFPTR_ERROR_COMMODITIES_TABLE_NO_TAG_DATA = 256;
  
  public static final int LIBFPTR_ERROR_COMMODITIES_TABLE_NO_FREE_MEMORY = 257;
  
  public static final int LIBFPTR_ERROR_INVALID_CACHE = 258;
  
  public static final int LIBFPTR_ERROR_SCHEDULER_NOT_READY = 259;
  
  public static final int LIBFPTR_ERROR_SCHEDULER_INVALID_TASK = 260;
  
  public static final int LIBFPTR_ERROR_MINIPOS_NO_POSITION_PAYMENT = 261;
  
  public static final int LIBFPTR_ERROR_MINIPOS_COMMAND_TIME_OUT = 262;
  
  public static final int LIBFPTR_ERROR_MINIPOS_MODE_FR_DISABLED = 263;
  
  public static final int LIBFPTR_ERROR_ENTRY_NOT_FOUND_IN_OTP = 264;
  
  public static final int LIBFPTR_ERROR_EXCISABLE_COMMODITY_WITHOUT_EXCISE = 265;
  
  public static final int LIBFPTR_ERROR_BARCODE_TYPE_NOT_SUPPORTED = 266;
  
  public static final int LIBFPTR_ERROR_OVERLAY_DATA_OVERFLOW = 267;
  
  public static final int LIBFPTR_ERROR_INVALID_MODULE_ADDRESS = 268;
  
  public static final int LIBFPTR_ERROR_ECR_MODEL_NOT_SUPPORTED = 269;
  
  public static final int LIBFPTR_ERROR_PAID_NOT_REQUIRED = 270;
  
  public static final int LIBFPTR_ERROR_NON_PRINTABLE_CHAR = 271;
  
  public static final int LIBFPTR_ERROR_INVALID_USER_TAG = 272;
  
  public static final int LIBFPTR_ERROR_COMMODITIES_TABLE_ITERATION_STOPPED = 273;
  
  public static final int LIBFPTR_ERROR_COMMODITIES_TABLE_INVALID_CSV_FORMAT = 274;
  
  public static final int LIBFPTR_ERROR_MINIPOS_NO_FILE_ON_USB_STORE = 275;
  
  public static final int LIBFPTR_ERROR_MINIPOS_NO_AGENT_FISCAL_PROPERTY = 276;
  
  public static final int LIBFPTR_ERROR_NO_CONNECTION_WITH_PRINT_SERVICE = 277;
  
  public static final int LIBFPTR_ERROR_BASE_MARKING = 400;
  
  public static final int LIBFPTR_ERROR_MARKING_CODE_VALIDATION_IN_PROGRESS = 401;
  
  public static final int LIBFPTR_ERROR_NO_CONNECTION_WITH_SERVER = 402;
  
  public static final int LIBFPTR_ERROR_MARKING_CODE_VALIDATION_CANCELED = 403;
  
  public static final int LIBFPTR_ERROR_INVALID_MARKING_CODE_STATUS = 404;
  
  public static final int LIBFPTR_ERROR_INVALID_GS1 = 405;
  
  public static final int LIBFPTR_ERROR_MARKING_WORK_DENIED = 406;
  
  public static final int LIBFPTR_ERROR_MARKING_WORK_TEMPORARY_BLOCKED = 407;
  
  public static final int LIBFPTR_ERROR_MARKS_OVERFLOW = 408;
  
  public static final int LIBFPTR_ERROR_INVALID_MARKING_CODE = 409;
  
  public static final int LIBFPTR_ERROR_INVALID_STATE = 410;
  
  public static final int LIBFPTR_ERROR_OFD_EXCHANGE = 411;
  
  public static final int LIBFPTR_ERROR_INVALID_MEASUREMENT_UNIT = 412;
  
  public static final int LIBFPTR_ERROR_OPERATION_DENIED_IN_CURRENT_FFD = 413;
  
  public static final int LIBFPTR_ERROR_MARKING_OPERATION_DENIED = 414;
  
  public static final int LIBFPTR_ERROR_NO_DATA_TO_SEND = 415;
  
  public static final int LIBFPTR_ERROR_NO_MARKED_POSITION = 416;
  
  public static final int LIBFPTR_ERROR_HAS_NOT_SEND_NOTICES = 417;
  
  public static final int LIBFPTR_ERROR_UPDATE_KEYS_REQUIRED = 418;
  
  public static final int LIBFPTR_ERROR_UPDATE_KEYS_SERVICE = 419;
  
  public static final int LIBFPTR_ERROR_MARK_NOT_CHECKED = 420;
  
  public static final int LIBFPTR_ERROR_MARK_CHECK_TIMEOUT_EXPIRED = 421;
  
  public static final int LIBFPTR_ERROR_NO_MARKING_CODE_IN_TABLE = 422;
  
  public static final int LIBFPTR_ERROR_CHEKING_MARK_IN_PROGRESS = 423;
  
  public static final int LIBFPTR_ERROR_INVALID_SERVER_ADDRESS = 424;
  
  public static final int LIBFPTR_ERROR_UPDATE_KEYS_TIMEOUT = 425;
  
  public static final int LIBFPTR_ERROR_PROPERTY_FOR_MARKING_POSITION_ONLY = 426;
  
  public static final int LIBFPTR_ERROR_BASE_WEB = 500;
  
  public static final int LIBFPTR_ERROR_RECEIPT_PARSE_ERROR = 501;
  
  public static final int LIBFPTR_ERROR_INTERRUPTED_BY_PREVIOUS_ERRORS = 502;
  
  public static final int LIBFPTR_ERROR_DRIVER_SCRIPT_ERROR = 503;
  
  public static final int LIBFPTR_ERROR_VALIDATE_FUNC_NOT_FOUND = 504;
  
  public static final int LIBFPTR_ERROR_WEB_FAIL = 505;
  
  public static final int LIBFPTR_PORT_COM = 0;
  
  public static final int LIBFPTR_PORT_USB = 1;
  
  public static final int LIBFPTR_PORT_TCPIP = 2;
  
  public static final int LIBFPTR_PORT_BLUETOOTH = 3;
  
  public static final int LIBFPTR_PORT_BITS_7 = 7;
  
  public static final int LIBFPTR_PORT_BITS_8 = 8;
  
  public static final int LIBFPTR_PORT_PARITY_NO = 0;
  
  public static final int LIBFPTR_PORT_PARITY_ODD = 1;
  
  public static final int LIBFPTR_PORT_PARITY_EVEN = 2;
  
  public static final int LIBFPTR_PORT_PARITY_MARK = 3;
  
  public static final int LIBFPTR_PORT_PARITY_SPACE = 4;
  
  public static final int LIBFPTR_PORT_SB_1 = 0;
  
  public static final int LIBFPTR_PORT_SB_1_5 = 1;
  
  public static final int LIBFPTR_PORT_SB_2 = 2;
  
  public static final int LIBFPTR_BT_EAN_8 = 0;
  
  public static final int LIBFPTR_BT_EAN_13 = 1;
  
  public static final int LIBFPTR_BT_UPC_A = 2;
  
  public static final int LIBFPTR_BT_UPC_E = 3;
  
  public static final int LIBFPTR_BT_CODE_39 = 4;
  
  public static final int LIBFPTR_BT_CODE_93 = 5;
  
  public static final int LIBFPTR_BT_CODE_128 = 6;
  
  public static final int LIBFPTR_BT_CODABAR = 7;
  
  public static final int LIBFPTR_BT_ITF = 8;
  
  public static final int LIBFPTR_BT_ITF_14 = 9;
  
  public static final int LIBFPTR_BT_GS1_128 = 10;
  
  public static final int LIBFPTR_BT_QR = 11;
  
  public static final int LIBFPTR_BT_PDF417 = 12;
  
  public static final int LIBFPTR_BT_AZTEC = 13;
  
  public static final int LIBFPTR_BT_CODE_39_EXTENDED = 14;
  
  public static final int LIBFPTR_BC_DEFAULT = 0;
  
  public static final int LIBFPTR_BC_0 = 1;
  
  public static final int LIBFPTR_BC_1 = 2;
  
  public static final int LIBFPTR_BC_2 = 3;
  
  public static final int LIBFPTR_BC_3 = 4;
  
  public static final int LIBFPTR_BC_4 = 5;
  
  public static final int LIBFPTR_BC_5 = 6;
  
  public static final int LIBFPTR_BC_6 = 7;
  
  public static final int LIBFPTR_BC_7 = 8;
  
  public static final int LIBFPTR_BC_8 = 9;
  
  public static final int LIBFPTR_TM_POSITION = 0;
  
  public static final int LIBFPTR_TM_UNIT = 1;
  
  public static final int LIBFPTR_SCT_OVERALL = 0;
  
  public static final int LIBFPTR_SCT_FORWARD = 1;
  
  public static final int LIBFPTR_CT_ROLLUP = 0;
  
  public static final int LIBFPTR_CT_RESETTABLE = 1;
  
  public static final int LIBFPTR_SS_CLOSED = 0;
  
  public static final int LIBFPTR_SS_OPENED = 1;
  
  public static final int LIBFPTR_SS_EXPIRED = 2;
  
  public static final int LIBFPTR_CT_FULL = 0;
  
  public static final int LIBFPTR_CT_PART = 1;
  
  public static final int LIBFPTR_ALIGNMENT_LEFT = 0;
  
  public static final int LIBFPTR_ALIGNMENT_CENTER = 1;
  
  public static final int LIBFPTR_ALIGNMENT_RIGHT = 2;
  
  public static final int LIBFPTR_TW_NONE = 0;
  
  public static final int LIBFPTR_TW_WORDS = 1;
  
  public static final int LIBFPTR_TW_CHARS = 2;
  
  public static final int LIBFPTR_FNT_DEBUG = 0;
  
  public static final int LIBFPTR_FNT_RELEASE = 1;
  
  public static final int LIBFPTR_FNT_UNKNOWN = 2;
  
  public static final int LIBFPTR_RT_CLOSE_SHIFT = 0;
  
  public static final int LIBFPTR_RT_X = 1;
  
  public static final int LIBFPTR_RT_LAST_DOCUMENT = 2;
  
  public static final int LIBFPTR_RT_OFD_EXCHANGE_STATUS = 3;
  
  public static final int LIBFPTR_RT_KKT_DEMO = 4;
  
  public static final int LIBFPTR_RT_KKT_INFO = 5;
  
  public static final int LIBFPTR_RT_OFD_TEST = 6;
  
  public static final int LIBFPTR_RT_FN_DOC_BY_NUMBER = 7;
  
  public static final int LIBFPTR_RT_QUANTITY = 8;
  
  public static final int LIBFPTR_RT_DEPARTMENTS = 9;
  
  public static final int LIBFPTR_RT_OPERATORS = 10;
  
  public static final int LIBFPTR_RT_HOURS = 11;
  
  public static final int LIBFPTR_RT_FN_REGISTRATIONS = 12;
  
  public static final int LIBFPTR_RT_FN_SHIFT_TOTAL_COUNTERS = 13;
  
  public static final int LIBFPTR_RT_FN_TOTAL_COUNTERS = 14;
  
  public static final int LIBFPTR_RT_FN_NOT_SENT_DOCUMENTS_COUNTERS = 15;
  
  public static final int LIBFPTR_RT_COMMODITIES_BY_TAXATION_TYPES = 16;
  
  public static final int LIBFPTR_RT_COMMODITIES_BY_DEPARTMENTS = 17;
  
  public static final int LIBFPTR_RT_COMMODITIES_BY_SUMS = 18;
  
  public static final int LIBFPTR_RT_START_SERVICE = 19;
  
  public static final int LIBFPTR_RT_DISCOUNTS = 20;
  
  public static final int LIBFPTR_RT_JOURNAL_DOCUMENT_BY_NUMBERS = 21;
  
  public static final int LIBFPTR_RT_JOURNAL_DOCUMENT_BY_SHIFTS = 22;
  
  public static final int LIBFPTR_RT_CLOSE_SHIFT_REPORTS = 23;
  
  public static final int LIBFPTR_PT_CASH = 0;
  
  public static final int LIBFPTR_PT_ELECTRONICALLY = 1;
  
  public static final int LIBFPTR_PT_PREPAID = 2;
  
  public static final int LIBFPTR_PT_CREDIT = 3;
  
  public static final int LIBFPTR_PT_OTHER = 4;
  
  public static final int LIBFPTR_PT_6 = 5;
  
  public static final int LIBFPTR_PT_7 = 6;
  
  public static final int LIBFPTR_PT_8 = 7;
  
  public static final int LIBFPTR_PT_9 = 8;
  
  public static final int LIBFPTR_PT_10 = 9;
  
  public static final int LIBFPTR_TAX_DEPARTMENT = 0;
  
  public static final int LIBFPTR_TAX_VAT18 = 1;
  
  public static final int LIBFPTR_TAX_VAT10 = 2;
  
  public static final int LIBFPTR_TAX_VAT118 = 3;
  
  public static final int LIBFPTR_TAX_VAT110 = 4;
  
  public static final int LIBFPTR_TAX_VAT0 = 5;
  
  public static final int LIBFPTR_TAX_NO = 6;
  
  public static final int LIBFPTR_TAX_VAT20 = 7;
  
  public static final int LIBFPTR_TAX_VAT120 = 8;
  
  public static final int LIBFPTR_TAX_INVALID = 9;
  
  public static final int LIBFPTR_EXTERNAL_DEVICE_DISPLAY = 0;
  
  public static final int LIBFPTR_EXTERNAL_DEVICE_PINPAD = 1;
  
  public static final int LIBFPTR_EXTERNAL_DEVICE_MODEM = 2;
  
  public static final int LIBFPTR_EXTERNAL_DEVICE_BARCODE_SCANNER = 3;
  
  public static final int LIBFPTR_DT_STATUS = 0;
  
  public static final int LIBFPTR_DT_CASH_SUM = 1;
  
  public static final int LIBFPTR_DT_UNIT_VERSION = 2;
  
  public static final int LIBFPTR_DT_PICTURE_INFO = 3;
  
  public static final int LIBFPTR_DT_LICENSE_ACTIVATED = 4;
  
  public static final int LIBFPTR_DT_REGISTRATIONS_SUM = 5;
  
  public static final int LIBFPTR_DT_REGISTRATIONS_COUNT = 6;
  
  public static final int LIBFPTR_DT_PAYMENT_SUM = 7;
  
  public static final int LIBFPTR_DT_CASHIN_SUM = 8;
  
  public static final int LIBFPTR_DT_CASHIN_COUNT = 9;
  
  public static final int LIBFPTR_DT_CASHOUT_SUM = 10;
  
  public static final int LIBFPTR_DT_CASHOUT_COUNT = 11;
  
  public static final int LIBFPTR_DT_REVENUE = 12;
  
  public static final int LIBFPTR_DT_DATE_TIME = 13;
  
  public static final int LIBFPTR_DT_SHIFT_STATE = 14;
  
  public static final int LIBFPTR_DT_RECEIPT_STATE = 15;
  
  public static final int LIBFPTR_DT_SERIAL_NUMBER = 16;
  
  public static final int LIBFPTR_DT_MODEL_INFO = 17;
  
  public static final int LIBFPTR_DT_RECEIPT_LINE_LENGTH = 18;
  
  public static final int LIBFPTR_DT_CUTTER_RESOURCE = 19;
  
  public static final int LIBFPTR_DT_STEP_RESOURCE = 20;
  
  public static final int LIBFPTR_DT_TERMAL_RESOURCE = 21;
  
  public static final int LIBFPTR_DT_ENVD_MODE = 22;
  
  public static final int LIBFPTR_DT_SHIFT_TAX_SUM = 23;
  
  public static final int LIBFPTR_DT_RECEIPT_TAX_SUM = 24;
  
  public static final int LIBFPTR_DT_NON_NULLABLE_SUM = 25;
  
  public static final int LIBFPTR_DT_RECEIPT_COUNT = 26;
  
  public static final int LIBFPTR_DT_CANCELLATION_COUNT_ALL = 27;
  
  public static final int LIBFPTR_DT_CANCELLATION_SUM = 28;
  
  public static final int LIBFPTR_DT_CANCELLATION_SUM_ALL = 29;
  
  public static final int LIBFPTR_DT_POWER_SOURCE_STATE = 30;
  
  public static final int LIBFPTR_DT_CANCELLATION_COUNT = 31;
  
  public static final int LIBFPTR_DT_NON_NULLABLE_SUM_BY_PAYMENTS = 32;
  
  public static final int LIBFPTR_DT_PRINTER_TEMPERATURE = 33;
  
  public static final int LIBFPTR_DT_FATAL_STATUS = 34;
  
  public static final int LIBFPTR_DT_MAC_ADDRESS = 35;
  
  public static final int LIBFPTR_DT_DEVICE_UPTIME = 36;
  
  public static final int LIBFPTR_DT_RECEIPT_BYTE_COUNT = 37;
  
  public static final int LIBFPTR_DT_DISCOUNT_AND_SURCHARGE_SUM = 38;
  
  public static final int LIBFPTR_DT_LK_USER_CODE = 39;
  
  public static final int LIBFPTR_DT_LAST_SENT_OFD_DOCUMENT_DATE_TIME = 40;
  
  public static final int LIBFPTR_DT_SHORT_STATUS = 41;
  
  public static final int LIBFPTR_DT_PICTURES_ARRAY_INFO = 42;
  
  public static final int LIBFPTR_DT_ETHERNET_INFO = 43;
  
  public static final int LIBFPTR_DT_SCRIPTS_INFO = 44;
  
  public static final int LIBFPTR_DT_SHIFT_TOTALS = 45;
  
  public static final int LIBFPTR_DT_WIFI_INFO = 46;
  
  public static final int LIBFPTR_DT_FONT_INFO = 47;
  
  public static final int LIBFPTR_DT_SOFTLOCK_STATUS = 48;
  
  public static final int LIBFPTR_DT_LAST_SENT_ISM_NOTICE_DATE_TIME = 49;
  
  public static final int LIBFPTR_DT_MCU_INFO = 50;
  
  public static final int LIBFPTR_DT_MODULE_ADDRESS = 51;
  
  public static final int LIBFPTR_DT_CACHE_REQUISITES = 52;
  
  public static final int LIBFPTR_DT_DEPARTMENT_SUM = 53;
  
  public static final int LIBFPTR_DT_MCU_TEMPERATURE = 54;
  
  public static final int LIBFPTR_DT_AVAILABLE_OPERATIONS = 55;
  
  public static final int LIBFPTR_DT_PATTERN_PARAMETERS = 56;
  
  public static final int LIBFPTR_FNDT_TAG_VALUE = 0;
  
  public static final int LIBFPTR_FNDT_OFD_EXCHANGE_STATUS = 1;
  
  public static final int LIBFPTR_FNDT_FN_INFO = 2;
  
  public static final int LIBFPTR_FNDT_LAST_REGISTRATION = 3;
  
  public static final int LIBFPTR_FNDT_LAST_RECEIPT = 4;
  
  public static final int LIBFPTR_FNDT_LAST_DOCUMENT = 5;
  
  public static final int LIBFPTR_FNDT_SHIFT = 6;
  
  public static final int LIBFPTR_FNDT_FFD_VERSIONS = 7;
  
  public static final int LIBFPTR_FNDT_VALIDITY = 8;
  
  public static final int LIBFPTR_FNDT_REG_INFO = 9;
  
  public static final int LIBFPTR_FNDT_DOCUMENTS_COUNT_IN_SHIFT = 10;
  
  public static final int LIBFPTR_FNDT_ERRORS = 11;
  
  public static final int LIBFPTR_FNDT_TICKET_BY_DOC_NUMBER = 12;
  
  public static final int LIBFPTR_FNDT_DOCUMENT_BY_NUMBER = 13;
  
  public static final int LIBFPTR_FNDT_REGISTRATION_TLV = 14;
  
  public static final int LIBFPTR_FNDT_ERROR_DETAIL = 15;
  
  public static final int LIBFPTR_FNDT_VALIDITY_DAYS = 16;
  
  public static final int LIBFPTR_FNDT_FREE_MEMORY = 17;
  
  public static final int LIBFPTR_FNDT_TOTALS = 18;
  
  public static final int LIBFPTR_FNDT_ISM_ERRORS = 19;
  
  public static final int LIBFPTR_FNDT_ISM_EXCHANGE_STATUS = 20;
  
  public static final int LIBFPTR_FNDT_MARKING_MODE_STATUS = 21;
  
  public static final int LIBFPTR_FNDT_CHECK_MARK_TIME = 22;
  
  public static final int LIBFPTR_FNDT_RECEIPT_SIZE = 23;
  
  public static final int LIBFPTR_FNDT_FNM_KEYS_UPDATE_DATE_TIME = 24;
  
  public static final int LIBFPTR_UT_FIRMWARE = 0;
  
  public static final int LIBFPTR_UT_CONFIGURATION = 1;
  
  public static final int LIBFPTR_UT_TEMPLATES = 2;
  
  public static final int LIBFPTR_UT_CONTROL_UNIT = 3;
  
  public static final int LIBFPTR_UT_BOOT = 4;
  
  public static final int LIBFPTR_FNOP_REGISTRATION = 0;
  
  public static final int LIBFPTR_FNOP_CHANGE_FN = 1;
  
  public static final int LIBFPTR_FNOP_CHANGE_PARAMETERS = 2;
  
  public static final int LIBFPTR_FNOP_CLOSE_ARCHIVE = 3;
  
  public static final int LIBFPTR_OFD_CHANNEL_NONE = 0;
  
  public static final int LIBFPTR_OFD_CHANNEL_USB = 1;
  
  public static final int LIBFPTR_OFD_CHANNEL_PROTO = 2;
  
  public static final int LIBFPTR_PST_POWER_SUPPLY = 0;
  
  public static final int LIBFPTR_PST_RTC_BATTERY = 1;
  
  public static final int LIBFPTR_PST_BATTERY = 2;
  
  public static final int LIBFPTR_RT_LAST_DOCUMENT_LINES = 0;
  
  public static final int LIBFPTR_RT_FN_DOCUMENT_TLVS = 1;
  
  public static final int LIBFPTR_RT_EXEC_USER_SCRIPT = 2;
  
  public static final int LIBFPTR_RT_FIRMWARE = 3;
  
  public static final int LIBFPTR_RT_LICENSES = 4;
  
  public static final int LIBFPTR_RT_FN_REGISTRATION_TLVS = 5;
  
  public static final int LIBFPTR_RT_PARSE_COMPLEX_ATTR = 6;
  
  public static final int LIBFPTR_RT_FN_SUM_COUNTERS = 7;
  
  public static final int LIBFPTR_RT_FN_QUANTITY_COUNTERS = 8;
  
  public static final int LIBFPTR_RT_FN_UNSENT_DOCS_COUNTERS = 9;
  
  public static final int LIBFPTR_RT_SETTINGS = 10;
  
  public static final int LIBFPTR_RT_RUN_COMMAND = 11;
  
  public static final int LIBFPTR_LOG_ERROR = 0;
  
  public static final int LIBFPTR_LOG_WARN = 1;
  
  public static final int LIBFPTR_LOG_INFO = 2;
  
  public static final int LIBFPTR_LOG_DEBUG = 3;
  
  public static final int LIBFPTR_NT_FURS = 0;
  
  public static final int LIBFPTR_NT_MEDICINES = 1;
  
  public static final int LIBFPTR_NT_TOBACCO = 2;
  
  public static final int LIBFPTR_NT_SHOES = 3;
  
  public static final int LIBFPTR_UMO_GET_SIZE = 0;
  
  public static final int LIBFPTR_UMO_READ_DATA = 1;
  
  public static final int LIBFPTR_UMO_WRITE_DATA = 2;
  
  public static final int LIBFPTR_UMO_READ_STRING = 3;
  
  public static final int LIBFPTR_UMO_WRITE_STRING = 4;
  
  public static final int LIBFPTR_UMO_COMMIT = 5;
  
  public static final int LIBFPTR_GUI_PARENT_NATIVE = 0;
  
  public static final int LIBFPTR_GUI_PARENT_QT = 1;
  
  public static final int LIBFPTR_DEFER_NONE = 0;
  
  public static final int LIBFPTR_DEFER_PRE = 1;
  
  public static final int LIBFPTR_DEFER_POST = 2;
  
  public static final int LIBFPTR_DEFER_OVERLAY = 3;
  
  public static final int LIBFPTR_TAG_TYPE_STLV = 0;
  
  public static final int LIBFPTR_TAG_TYPE_STRING = 1;
  
  public static final int LIBFPTR_TAG_TYPE_ARRAY = 2;
  
  public static final int LIBFPTR_TAG_TYPE_FVLN = 3;
  
  public static final int LIBFPTR_TAG_TYPE_BITS = 4;
  
  public static final int LIBFPTR_TAG_TYPE_BYTE = 5;
  
  public static final int LIBFPTR_TAG_TYPE_VLN = 6;
  
  public static final int LIBFPTR_TAG_TYPE_UINT_16 = 7;
  
  public static final int LIBFPTR_TAG_TYPE_UINT_32 = 8;
  
  public static final int LIBFPTR_TAG_TYPE_UNIX_TIME = 9;
  
  public static final int LIBFPTR_TAG_TYPE_BOOL = 10;
  
  public static final int LIBFPTR_FT_BYTE_ARRAY = 0;
  
  public static final int LIBFPTR_FT_BIN = 1;
  
  public static final int LIBFPTR_FT_BCD = 2;
  
  public static final int LIBFPTR_FT_STRING = 3;
  
  public static final int LIBFPTR_FT_STRING_NULL_TERM = 4;
  
  public static final int LIBFPTR_ST_NUMBER = 0;
  
  public static final int LIBFPTR_ST_STRING = 1;
  
  public static final int LIBFPTR_ST_BOOL = 2;
  
  public static final int LIBFPTR_SCRIPT_EXECUTABLE = 0;
  
  public static final int LIBFPTR_SCRIPT_JSON = 1;
  
  public static final int LIBFPTR_SCRIPT_SETTINGS = 2;
  
  public static final int LIBFPTR_SCRIPT_LIBRARY = 3;
  
  public static final int LIBFPTR_UCL_UNUSED = 0;
  
  public static final int LIBFPTR_UCL_RECEIPT_TYPE = 1;
  
  public static final int LIBFPTR_UCL_TAXATION_TYPE = 2;
  
  public static final int LIBFPTR_UCL_TAX_TYPE = 3;
  
  public static final int LIBFPTR_UCL_PRODUCT_TYPE = 4;
  
  public static final int LIBFPTR_UCL_PAYMENT_METHOD = 5;
  
  public static final int LIBFPTR_UCL_USER_3 = 6;
  
  public static final int LIBFPTR_UCL_USER_4 = 7;
  
  public static final int LIBFPTR_UCL_USER_5 = 8;
  
  public static final int LIBFPTR_UCL_USER_6 = 9;
  
  public static final int LIBFPTR_FNCT_SHIFT = 0;
  
  public static final int LIBFPTR_FNCT_NON_NULLABLE = 1;
  
  public static final int LIBFPTR_MCT_OTHER = 0;
  
  public static final int LIBFPTR_MCT_EGAIS_20 = 1;
  
  public static final int LIBFPTR_MCT_EGAIS_30 = 2;
  
  public static final int LIBFPTR_MCT12_UNKNOWN = 0;
  
  public static final int LIBFPTR_MCT12_SHORT = 1;
  
  public static final int LIBFPTR_MCT12_88_CHECK = 2;
  
  public static final int LIBFPTR_MCT12_44_NO_CHECK = 3;
  
  public static final int LIBFPTR_MCT12_44_CHECK = 4;
  
  public static final int LIBFPTR_MCT12_4_NO_CHECK = 5;
  
  public static final int LIBFPTR_MES_PIECE_SOLD = 1;
  
  public static final int LIBFPTR_MES_DRY_FOR_SALE = 2;
  
  public static final int LIBFPTR_MES_PIECE_RETURN = 3;
  
  public static final int LIBFPTR_MES_DRY_RETURN = 4;
  
  public static final int LIBFPTR_MCS_BLOCK = 0;
  
  public static final int LIBFPTR_MCS_NO_MARK_FOR_CHECK = 1;
  
  public static final int LIBFPTR_MCS_MARK_RECEIVE_B1 = 2;
  
  public static final int LIBFPTR_MCS_MARK_STATE_QUERY_B5 = 3;
  
  public static final int LIBFPTR_MCS_MARK_STATE_ANSWER_B6 = 4;
  
  public static final int LIBFPTR_NFM_LESS_50_PERCENT = 0;
  
  public static final int LIBFPTR_NFM_FROM_50_TO_80_PERCENT = 1;
  
  public static final int LIBFPTR_NFM_FROM_80_TO_90_PERCENT = 2;
  
  public static final int LIBFPTR_NFM_MORE_90_PERCENT = 3;
  
  public static final int LIBFPTR_NFM_OUT_OF_MEMORY = 4;
  
  public static final int LIBFPTR_OIS_ESTIMATED_STATUS_CORRECT = 1;
  
  public static final int LIBFPTR_OIS_ESTIMATED_STATUS_INCORRECT = 2;
  
  public static final int LIBFPTR_OIS_SALE_STOPPED = 3;
  
  public static final int LIBFPTR_ORR_CORRECT = 0;
  
  public static final int LIBFPTR_ORR_INCORRECT = 1;
  
  public static final int LIBFPTR_ORR_UNRECOGNIZED = 2;
  
  public static final int LIBFPTR_CER_CHECKED = 0;
  
  public static final int LIBFPTR_CER_TYPE_INCORRECT = 1;
  
  public static final int LIBFPTR_CER_NO_KEYS = 2;
  
  public static final int LIBFPTR_CER_NO_GS1 = 3;
  
  public static final int LIBFPTR_CER_OTHER = 4;
  
  public static final int LIBFPTR_MCS_NOT_EXECUTED = 0;
  
  public static final int LIBFPTR_MCS_EXECUTED = 1;
  
  public static final int LIBFPTR_MCS_IS_OVER = 2;
  
  public static final int LIBFPTR_MCS_RESULT_IS_RECIEVED = 3;
  
  public static final int LIBFPTR_MCT_AUTONOMOUS = 0;
  
  public static final int LIBFPTR_MCT_WAIT_FOR_RESULT = 1;
  
  public static final int LIBFPTR_MCT_RESULT_NOT_WAIT = 2;
  
  public static final int LIBFPTR_MCT_QUERY_NOT_SEND = 3;
  
  public static final int LIBFPTR_MCT_QUERY_NOT_FORM = 4;
  
  public static final int LIBFPTR_MCST_WAITING_FOR_TASK = 0;
  
  public static final int LIBFPTR_MCST_OPENING_CONNECTION = 1;
  
  public static final int LIBFPTR_MCST_SENDING = 2;
  
  public static final int LIBFPTR_MCST_WAITING_FOR_RESULT = 3;
  
  public static final int LIBFPTR_MCST_GETTING_RESULT = 4;
  
  public static final int LIBFPTR_MCST_DECODE_RESULT = 5;
  
  public static final int LIBFPTR_MCST_TASK_IS_OVER = 6;
  
  public static final int LIBFPTR_MCST_WAITING_FOR_REPEAT = 7;
  
  public static final int LIBFPTR_SILENT_REBOOT_NO = 0;
  
  public static final int LIBFPTR_SILENT_REBOOT_AFTER_SESSION_CLOSE = 1;
  
  public static final int LIBFPTR_SILENT_REBOOT_BEFORE_SESSION_OPEN = 2;
  
  public static final int LIBFPTR_MERGE_RECEIPT_NO = 0;
  
  public static final int LIBFPTR_MERGE_RECEIPT_ALL = 1;
  
  public static final int LIBFPTR_MERGE_RECEIPT_MARK_ONLY = 2;
  
  public static final int LIBFPTR_RPF_ELECTRONIC_AND_PRINT = 0;
  
  public static final int LIBFPTR_RPF_ONLY_ELECTRONIC = 1;
  
  public static final int LIBFPTR_RPF_ONLY_PRINT = 2;
  
  public static final int LIBFPTR_ERROR_BASE_RPC = 600;
  
  public static final int LIBFPTR_ERROR_RCP_SERVER_BUSY = 601;
  
  public static final int LIBFPTR_ERROR_RCP_SERVER_VERSION = 602;
  
  public static final int LIBFPTR_ERROR_RCP_SERVER_EXCHANGE = 603;
  
  public static final int LIBFPTR_ERROR_MARKING_END = 499;
  
  public static final int LIBFPTR_ERROR_WEB_END = 599;
  
  public static final int LIBFPTR_OFD_CHANNEL_AUTO = 2;
  
  public static final String LIBFPTR_SETTING_LIBRARY_PATH = "LibraryPath";
  
  public static final String LIBFPTR_SETTING_MODEL = "Model";
  
  public static final String LIBFPTR_SETTING_PORT = "Port";
  
  public static final String LIBFPTR_SETTING_BAUDRATE = "BaudRate";
  
  public static final String LIBFPTR_SETTING_BITS = "Bits";
  
  public static final String LIBFPTR_SETTING_PARITY = "Parity";
  
  public static final String LIBFPTR_SETTING_STOPBITS = "StopBits";
  
  public static final String LIBFPTR_SETTING_IPADDRESS = "IPAddress";
  
  public static final String LIBFPTR_SETTING_IPPORT = "IPPort";
  
  public static final String LIBFPTR_SETTING_MACADDRESS = "MACAddress";
  
  public static final String LIBFPTR_SETTING_COM_FILE = "ComFile";
  
  public static final String LIBFPTR_SETTING_USB_DEVICE_PATH = "UsbDevicePath";
  
  public static final String LIBFPTR_SETTING_BT_AUTOENABLE = "AutoEnableBluetooth";
  
  public static final String LIBFPTR_SETTING_BT_AUTODISABLE = "AutoDisableBluetooth";
  
  public static final String LIBFPTR_SETTING_ACCESS_PASSWORD = "AccessPassword";
  
  public static final String LIBFPTR_SETTING_USER_PASSWORD = "UserPassword";
  
  public static final String LIBFPTR_SETTING_OFD_CHANNEL = "OfdChannel";
  
  public static final String LIBFPTR_SETTING_EXISTED_COM_FILES = "ExistedComFiles";
  
  public static final String LIBFPTR_SETTING_SCRIPTS_PATH = "ScriptsPath";
  
  public static final String LIBFPTR_SETTING_DOCUMENTS_JOURNAL_PATH = "DocumentsJournalPath";
  
  public static final String LIBFPTR_SETTING_USE_DOCUMENTS_JOURNAL = "UseDocumentsJournal";
  
  public static final String LIBFPTR_SETTING_AUTO_RECONNECT = "AutoReconnect";
  
  public static final String LIBFPTR_SETTING_INVERT_CASH_DRAWER_STATUS = "InvertCashDrawerStatus";
  
  public static final String LIBFPTR_SETTING_REMOTE_SERVER_ADDR = "RemoteServerAddr";
  
  public static final String LIBFPTR_SETTING_REMOTE_SERVER_CONNECTION_TIMEOUT = "RemoteServerConnectionTimeout";
  
  public static final String LIBFPTR_SETTING_VALIDATE_MARK_WITH_FNM_ONLY = "ValidateMarksWithFnmOnly";
  
  public static final String LIBFPTR_SETTING_AUTO_MEASUREMENT_UNIT = "AutoMeasurementUnit";
  
  public static final String LIBFPTR_SETTING_SILENT_REBOOT = "SilentReboot";
  
  public static final String LIBFPTR_SETTING_LOG_PATHS = "LogPaths";
  
  public static final String LIBFPTR_SETTING_AUTO_TIME_SYNC = "AutoTimeSync";
  
  public static final String LIBFPTR_SETTING_AUTO_TIME_SYNC_TIME = "AutoTimeSyncTime";
  
  public static final String LIBFPTR_SETTING_MERGE_RECEIPT_ITEMS = "MergeReceiptItems";
  
  public static final int LIBFPTR_MODEL_UNKNOWN = 0;
  
  public static final int LIBFPTR_MODEL_ATOL_25F = 57;
  
  public static final int LIBFPTR_MODEL_ATOL_30F = 61;
  
  public static final int LIBFPTR_MODEL_ATOL_55F = 62;
  
  public static final int LIBFPTR_MODEL_ATOL_22F = 63;
  
  public static final int LIBFPTR_MODEL_ATOL_52F = 64;
  
  public static final int LIBFPTR_MODEL_ATOL_11F = 67;
  
  public static final int LIBFPTR_MODEL_ATOL_77F = 69;
  
  public static final int LIBFPTR_MODEL_ATOL_90F = 72;
  
  public static final int LIBFPTR_MODEL_ATOL_60F = 75;
  
  public static final int LIBFPTR_MODEL_ATOL_42FS = 77;
  
  public static final int LIBFPTR_MODEL_ATOL_15F = 78;
  
  public static final int LIBFPTR_MODEL_ATOL_50F = 80;
  
  public static final int LIBFPTR_MODEL_ATOL_20F = 81;
  
  public static final int LIBFPTR_MODEL_ATOL_91F = 82;
  
  public static final int LIBFPTR_MODEL_ATOL_92F = 84;
  
  public static final int LIBFPTR_MODEL_ATOL_SIGMA_10 = 86;
  
  public static final int LIBFPTR_MODEL_ATOL_27F = 87;
  
  public static final int LIBFPTR_MODEL_ATOL_SIGMA_7F = 90;
  
  public static final int LIBFPTR_MODEL_ATOL_SIGMA_8F = 91;
  
  public static final int LIBFPTR_MODEL_ATOL_1F = 93;
  
  public static final int LIBFPTR_MODEL_KAZNACHEY_FA = 76;
  
  public static final int LIBFPTR_MODEL_ATOL_22V2F = 95;
  
  public static final int LIBFPTR_MODEL_ATOL_AUTO = 500;
  
  public static final int LIBFPTR_MODEL_ATOL_47FA = 48;
  
  public static final int LIBFPTR_MODEL_ATOL_PT_5F = 89;
  
  public static final int LIBFPTR_MODEL_ATOL_27_FP7_F = 99;
  
  public static final int LIBFPTR_MODEL_ATOL_42FA = 70;
  
  public static final int LIBFPTR_MODEL_ALLIANCE_20F = 50;
  
  public static final int LIBFPTR_MODEL_ATOL_55V2F = 66;
  
  public static final int LIBFPTR_MODEL_ATOL_STB_6 = 92;
  
  public static final int LIBFPTR_MODEL_ATOL_35F = 97;
  
  public static final int LIBFPTR_PORT_BR_1200 = 1200;
  
  public static final int LIBFPTR_PORT_BR_2400 = 2400;
  
  public static final int LIBFPTR_PORT_BR_4800 = 4800;
  
  public static final int LIBFPTR_PORT_BR_9600 = 9600;
  
  public static final int LIBFPTR_PORT_BR_19200 = 19200;
  
  public static final int LIBFPTR_PORT_BR_38400 = 38400;
  
  public static final int LIBFPTR_PORT_BR_57600 = 57600;
  
  public static final int LIBFPTR_PORT_BR_115200 = 115200;
  
  public static final int LIBFPTR_PORT_BR_230400 = 230400;
  
  public static final int LIBFPTR_PORT_BR_460800 = 460800;
  
  public static final int LIBFPTR_PORT_BR_921600 = 921600;
  
  public static final int LIBFPTR_FNS_INITIAL = 0;
  
  public static final int LIBFPTR_FNS_CONFIGURED = 1;
  
  public static final int LIBFPTR_FNS_FISCAL_MODE = 3;
  
  public static final int LIBFPTR_FNS_POSTFISCAL_MODE = 7;
  
  public static final int LIBFPTR_FNS_ACCESS_ARCHIVE = 15;
  
  public static final int LIBFPTR_RT_CLOSED = 0;
  
  public static final int LIBFPTR_RT_SELL = 1;
  
  public static final int LIBFPTR_RT_SELL_RETURN = 2;
  
  public static final int LIBFPTR_RT_SELL_CORRECTION = 7;
  
  public static final int LIBFPTR_RT_SELL_RETURN_CORRECTION = 8;
  
  public static final int LIBFPTR_RT_BUY = 4;
  
  public static final int LIBFPTR_RT_BUY_RETURN = 5;
  
  public static final int LIBFPTR_RT_BUY_CORRECTION = 9;
  
  public static final int LIBFPTR_RT_BUY_RETURN_CORRECTION = 10;
  
  public static final int LIBFPTR_FFD_UNKNOWN = 0;
  
  public static final int LIBFPTR_FFD_1_0 = 100;
  
  public static final int LIBFPTR_FFD_1_0_5 = 105;
  
  public static final int LIBFPTR_FFD_1_1 = 110;
  
  public static final int LIBFPTR_FFD_1_2 = 120;
  
  public static final int LIBFPTR_TT_DEFAULT = 0;
  
  public static final int LIBFPTR_TT_OSN = 1;
  
  public static final int LIBFPTR_TT_USN_INCOME = 2;
  
  public static final int LIBFPTR_TT_USN_INCOME_OUTCOME = 4;
  
  public static final int LIBFPTR_TT_ENVD = 8;
  
  public static final int LIBFPTR_TT_ESN = 16;
  
  public static final int LIBFPTR_TT_PATENT = 32;
  
  public static final int LIBFPTR_AT_NONE = 0;
  
  public static final int LIBFPTR_AT_BANK_PAYING_AGENT = 1;
  
  public static final int LIBFPTR_AT_BANK_PAYING_SUBAGENT = 2;
  
  public static final int LIBFPTR_AT_PAYING_AGENT = 4;
  
  public static final int LIBFPTR_AT_PAYING_SUBAGENT = 8;
  
  public static final int LIBFPTR_AT_ATTORNEY = 16;
  
  public static final int LIBFPTR_AT_COMMISSION_AGENT = 32;
  
  public static final int LIBFPTR_AT_ANOTHER = 64;
  
  public static final int LIBFPTR_DT_CLOSED = 0;
  
  public static final int LIBFPTR_DT_RECEIPT_SELL = 1;
  
  public static final int LIBFPTR_DT_RECEIPT_SELL_RETURN = 2;
  
  public static final int LIBFPTR_DT_RECEIPT_BUY = 3;
  
  public static final int LIBFPTR_DT_RECEIPT_BUY_RETURN = 4;
  
  public static final int LIBFPTR_DT_OPEN_SHIFT = 5;
  
  public static final int LIBFPTR_DT_CLOSE_SHIFT = 6;
  
  public static final int LIBFPTR_DT_REGISTRATION = 7;
  
  public static final int LIBFPTR_DT_CLOSE_ARCHIVE = 8;
  
  public static final int LIBFPTR_DT_OFD_EXCHANGE_STATUS = 11;
  
  public static final int LIBFPTR_DT_RECEIPT_SELL_CORRECTION = 12;
  
  public static final int LIBFPTR_DT_RECEIPT_SELL_RETURN_CORRECTION = 13;
  
  public static final int LIBFPTR_DT_RECEIPT_BUY_CORRECTION = 14;
  
  public static final int LIBFPTR_DT_RECEIPT_BUY_RETURN_CORRECTION = 15;
  
  public static final int LIBFPTR_DT_DOCUMENT_SERVICE = 20;
  
  public static final int LIBFPTR_DT_DOCUMENT_COPY = 21;
  
  public static final int LIBFPTR_FN_DOC_REGISTRATION = 1;
  
  public static final int LIBFPTR_FN_DOC_OPEN_SHIFT = 2;
  
  public static final int LIBFPTR_FN_DOC_RECEIPT = 3;
  
  public static final int LIBFPTR_FN_DOC_BSO = 4;
  
  public static final int LIBFPTR_FN_DOC_CLOSE_SHIFT = 5;
  
  public static final int LIBFPTR_FN_DOC_CLOSE_FN = 6;
  
  public static final int LIBFPTR_FN_DOC_OPERATOR_CONFIRMATION = 7;
  
  public static final int LIBFPTR_FN_DOC_REREGISTRATION = 11;
  
  public static final int LIBFPTR_FN_DOC_EXCHANGE_STATUS = 21;
  
  public static final int LIBFPTR_FN_DOC_CORRECTION = 31;
  
  public static final int LIBFPTR_FN_DOC_BSO_CORRECTION = 41;
  
  public static final int LIBFPTR_FWT_FIRMWARE = 0;
  
  public static final int LIBFPTR_FWT_SCRIPTS = 2;
  
  public static final int LIBFPTR_UCF_CALC_SUMS = 1;
  
  public static final int LIBFPTR_UCF_CALC_QUANTITIES = 2;
  
  public static final int LIBFPTR_UCF_CALC_SUMS_OTHERS = 4;
  
  public static final int LIBFPTR_UCF_CALC_QUANTITIES_OTHERS = 8;
  
  public static final long LIBFPTR_UC_OTHERS = 4294967295L;
  
  public static final int LIBFPTR_MCT12_AUTO = 256;
  
  public static final int LIBFPTR_MES_UNCHANGED = 255;
  
  public static final int LIBFPTR_IU_PIECE = 0;
  
  public static final int LIBFPTR_IU_GRAM = 10;
  
  public static final int LIBFPTR_IU_KILOGRAM = 11;
  
  public static final int LIBFPTR_IU_TON = 12;
  
  public static final int LIBFPTR_IU_CENTIMETER = 20;
  
  public static final int LIBFPTR_IU_DECIMETER = 21;
  
  public static final int LIBFPTR_IU_METER = 22;
  
  public static final int LIBFPTR_IU_SQUARE_CENTIMETER = 30;
  
  public static final int LIBFPTR_IU_SQUARE_DECIMETER = 31;
  
  public static final int LIBFPTR_IU_SQUARE_METER = 32;
  
  public static final int LIBFPTR_IU_MILLILITER = 40;
  
  public static final int LIBFPTR_IU_LITER = 41;
  
  public static final int LIBFPTR_IU_CUBIC_METER = 42;
  
  public static final int LIBFPTR_IU_KILOWATT_HOUR = 50;
  
  public static final int LIBFPTR_IU_GKAL = 51;
  
  public static final int LIBFPTR_IU_DAY = 70;
  
  public static final int LIBFPTR_IU_HOUR = 71;
  
  public static final int LIBFPTR_IU_MINUTE = 72;
  
  public static final int LIBFPTR_IU_SECOND = 73;
  
  public static final int LIBFPTR_IU_KILOBYTE = 80;
  
  public static final int LIBFPTR_IU_MEGABYTE = 81;
  
  public static final int LIBFPTR_IU_GIGABYTE = 82;
  
  public static final int LIBFPTR_IU_TERABYTE = 83;
  
  public static final int LIBFPTR_IU_OTHER = 255;
  
  public static final int LIBFPTR_ERROR_USERS_SCRIPTS_BASE = 1000;
  
  public static final int LIBFPTR_PLATFORM_UNKNOWN = 0;
  
  public static final int LIBFPTR_PLATFORM_25 = 25;
  
  public static final int LIBFPTR_PLATFORM_50 = 50;
  
  public static final int LIBFPTR_ERROR_USERS_SCRIPTS_END = 1999;
  
  public static final int LIBFPTR_ERROR_RPC_END = 699;
  
  void destroy();
  
  int logWrite(String paramString1, int paramInt, String paramString2);
  
  int changeLabel(String paramString);
  
  int showProperties(int paramInt, long paramLong);
  
  String version();
  
  String wrapperVersion();
  
  boolean isOpened();
  
  int setSettings(String paramString);
  
  String getSettings();
  
  void setSingleSetting(String paramString1, String paramString2);
  
  String getSingleSetting(String paramString);
  
  int errorCode();
  
  String errorDescription();
  
  String errorRecommendation();
  
  void resetError();
  
  void setParam(int paramInt, boolean paramBoolean);
  
  void setParam(int paramInt1, int paramInt2);
  
  void setParam(int paramInt, long paramLong);
  
  void setParam(int paramInt, double paramDouble);
  
  void setParam(int paramInt, String paramString);
  
  void setParam(int paramInt, Date paramDate);
  
  void setParam(int paramInt, byte[] paramArrayOfbyte);
  
  void setNonPrintableParam(int paramInt, boolean paramBoolean);
  
  void setNonPrintableParam(int paramInt, long paramLong);
  
  void setNonPrintableParam(int paramInt1, int paramInt2);
  
  void setNonPrintableParam(int paramInt, double paramDouble);
  
  void setNonPrintableParam(int paramInt, String paramString);
  
  void setNonPrintableParam(int paramInt, Date paramDate);
  
  void setNonPrintableParam(int paramInt, byte[] paramArrayOfbyte);
  
  void setUserParam(int paramInt, boolean paramBoolean);
  
  void setUserParam(int paramInt, long paramLong);
  
  void setUserParam(int paramInt1, int paramInt2);
  
  void setUserParam(int paramInt, double paramDouble);
  
  void setUserParam(int paramInt, String paramString);
  
  void setUserParam(int paramInt, Date paramDate);
  
  void setUserParam(int paramInt, byte[] paramArrayOfbyte);
  
  boolean getParamBool(int paramInt);
  
  long getParamInt(int paramInt);
  
  double getParamDouble(int paramInt);
  
  String getParamString(int paramInt);
  
  Date getParamDateTime(int paramInt);
  
  byte[] getParamByteArray(int paramInt);
  
  boolean isParamAvailable(int paramInt);
  
  int applySingleSettings();
  
  int open();
  
  int close();
  
  int resetParams();
  
  int runCommand();
  
  int beep();
  
  int openDrawer();
  
  int cut();
  
  int devicePoweroff();
  
  int deviceReboot();
  
  int openShift();
  
  int resetSummary();
  
  int initDevice();
  
  int queryData();
  
  int cashIncome();
  
  int cashOutcome();
  
  int openReceipt();
  
  int cancelReceipt();
  
  int closeReceipt();
  
  int checkDocumentClosed();
  
  int receiptTotal();
  
  int receiptTax();
  
  int registration();
  
  int payment();
  
  int report();
  
  int printText();
  
  int printCliche();
  
  int beginNonfiscalDocument();
  
  int endNonfiscalDocument();
  
  int printBarcode();
  
  int printPicture();
  
  int printPictureByNumber();
  
  int uploadPictureFromFile();
  
  int clearPictures();
  
  int writeDeviceSettingRaw();
  
  int readDeviceSettingRaw();
  
  int commitSettings();
  
  int initSettings();
  
  int resetSettings();
  
  int writeDateTime();
  
  int writeLicense();
  
  int fnOperation();
  
  int fnQueryData();
  
  int fnWriteAttributes();
  
  int externalDevicePowerOn();
  
  int externalDevicePowerOff();
  
  int externalDeviceWriteData();
  
  int externalDeviceReadData();
  
  int operatorLogin();
  
  int processJson();
  
  int readDeviceSetting();
  
  int writeDeviceSetting();
  
  int beginReadRecords();
  
  int readNextRecord();
  
  int endReadRecords();
  
  int userMemoryOperation();
  
  int continuePrint();
  
  int initMgm();
  
  int utilFormTlv();
  
  int utilFormNomenclature();
  
  int utilMapping();
  
  int readModelFlags();
  
  int lineFeed();
  
  int flashFirmware();
  
  int softLockInit();
  
  int softLockQuerySessionCode();
  
  int softLockValidate();
  
  int utilCalcTax();
  
  int downloadPicture();
  
  int bluetoothRemovePairedDevices();
  
  int utilTagInfo();
  
  int utilContainerVersions();
  
  int activateLicenses();
  
  int removeLicenses();
  
  int enterKeys();
  
  int validateKeys();
  
  int enterSerialNumber();
  
  int getSerialNumberRequest();
  
  int uploadPixelBuffer();
  
  int downloadPixelBuffer();
  
  int printPixelBuffer();
  
  int utilConvertTagValue();
  
  int parseMarkingCode();
  
  int callScript();
  
  int setHeaderLines();
  
  int setFooterLines();
  
  int uploadPictureCliche();
  
  int uploadPictureMemory();
  
  int uploadPixelBufferCliche();
  
  int uploadPixelBufferMemory();
  
  int execDriverScript();
  
  int uploadDriverScript();
  
  int execDriverScriptById();
  
  int writeUniversalCountersSettings();
  
  int readUniversalCountersSettings();
  
  int queryUniversalCountersState();
  
  int resetUniversalCounters();
  
  int cacheUniversalCounters();
  
  int readUniversalCounterSum();
  
  int readUniversalCounterQuantity();
  
  int clearUniversalCountersCache();
  
  int disableOfdChannel();
  
  int enableOfdChannel();
  
  int validateJson();
  
  int reflectionCall();
  
  int getRemoteServerInfo();
  
  int beginMarkingCodeValidation();
  
  int cancelMarkingCodeValidation();
  
  int getMarkingCodeValidationStatus();
  
  int acceptMarkingCode();
  
  int declineMarkingCode();
  
  int updateFnmKeys();
  
  int writeSalesNotice();
  
  int checkMarkingCodeValidationsReady();
  
  int clearMarkingCodeValidationResult();
  
  int pingMarkingServer();
  
  int getMarkingServerStatus();
  
  int isDriverLocked();
  
  int getLastDocumentJournal();
  
  int findDocumentInJournal();
  
  int runFnCommand();
}


/* Location:              G:\work.tmp\test_build_javafx\demo\lib\libfptr10.jar!\ru\atol\drivers10\fptr\IFptr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */