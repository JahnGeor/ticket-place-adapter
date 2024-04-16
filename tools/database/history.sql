ROLLBACK;
BEGIN TRANSACTION;
-- Вставляем запись в таблицу Login
SET @LOGIN_ID=(SELECT ID FROM FINAL TABLE(
MERGE INTO LOGIN(EMAIL, PASSWORD, URL)
KEY(EMAIL, URL) VALUES ('jaremy_manlyjeo@findings.vh','JE7qdZErRn0QAAtHb','https://stationstpp8lcj4vc.vjp')));
-- Вставляем запись в таблицу истории
MERGE INTO HISTORY(LOGIN_ID, CREATED_AT, STATUS, ERROR, ORDER_ID, OPERATION_TYPE) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, CURRENT_TIMESTAMP(), 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 796, 6);
-- Вставляем запись в таблицу профиля
MERGE INTO PROFILE(LOGIN_ID, INN, ROLE, AVATAR, FULLNAME, USERNAME, USER_ID) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, 2528994626, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'Tayvon Knott', 'shamecca9', 796);

-- Вставляем запись в таблицу Login
SET @LOGIN_ID=(SELECT ID FROM FINAL TABLE(
MERGE INTO LOGIN(EMAIL, PASSWORD, URL)
KEY(EMAIL, URL) VALUES ('sloane_gillumno1t@listen.vu','GCKL88ofdrhMisiMl','https://highszc1qvq0wusk.ntg')));
-- Вставляем запись в таблицу истории
MERGE INTO HISTORY(LOGIN_ID, CREATED_AT, STATUS, ERROR, ORDER_ID, OPERATION_TYPE) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, CURRENT_TIMESTAMP(), 8, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 353973, 8);
-- Вставляем запись в таблицу профиля
MERGE INTO PROFILE(LOGIN_ID, INN, ROLE, AVATAR, FULLNAME, USERNAME, USER_ID) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, 4866221274, 8, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'Zacheriah Artz', 'lacresiai4', 353973);

-- Вставляем запись в таблицу Login
SET @LOGIN_ID=(SELECT ID FROM FINAL TABLE(
MERGE INTO LOGIN(EMAIL, PASSWORD, URL)
KEY(EMAIL, URL) VALUES ('ta_mckeen5u@occupational.oj','GmTpkwIWIUfrMKCANbk','https://forwardingib9pugf82s.cm')));
-- Вставляем запись в таблицу истории
MERGE INTO HISTORY(LOGIN_ID, CREATED_AT, STATUS, ERROR, ORDER_ID, OPERATION_TYPE) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, CURRENT_TIMESTAMP(), 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 964218, 5);
-- Вставляем запись в таблицу профиля
MERGE INTO PROFILE(LOGIN_ID, INN, ROLE, AVATAR, FULLNAME, USERNAME, USER_ID) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, 7947722468, 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'Ashling Tyrrell', 'lamekaqnx', 964218);

-- Вставляем запись в таблицу Login
SET @LOGIN_ID=(SELECT ID FROM FINAL TABLE(
MERGE INTO LOGIN(EMAIL, PASSWORD, URL)
KEY(EMAIL, URL) VALUES ('deshay_crewo@api.uy','57Ru88sKSJHxFxN1KY7h','https://gazettesi6yw3itnl2.vvh')));
-- Вставляем запись в таблицу истории
MERGE INTO HISTORY(LOGIN_ID, CREATED_AT, STATUS, ERROR, ORDER_ID, OPERATION_TYPE) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, CURRENT_TIMESTAMP(), 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 5123232, 1);
-- Вставляем запись в таблицу профиля
MERGE INTO PROFILE(LOGIN_ID, INN, ROLE, AVATAR, FULLNAME, USERNAME, USER_ID) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, 7586037595, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'Torrence Kinkade', 'giulia56', 5123232);

-- Вставляем запись в таблицу Login
SET @LOGIN_ID=(SELECT ID FROM FINAL TABLE(
MERGE INTO LOGIN(EMAIL, PASSWORD, URL)
KEY(EMAIL, URL) VALUES ('carleen_alday4lr@heavily.pwu','tZD2KqbGs0qlon','https://lockhk.sy')));
-- Вставляем запись в таблицу истории
MERGE INTO HISTORY(LOGIN_ID, CREATED_AT, STATUS, ERROR, ORDER_ID, OPERATION_TYPE) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, CURRENT_TIMESTAMP(), 7, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 1467957, 7);
-- Вставляем запись в таблицу профиля
MERGE INTO PROFILE(LOGIN_ID, INN, ROLE, AVATAR, FULLNAME, USERNAME, USER_ID) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, 8565234999, 7, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'Eleena Grinstead', 'sky6eb3', 1467957);

-- Вставляем запись в таблицу Login
SET @LOGIN_ID=(SELECT ID FROM FINAL TABLE(
MERGE INTO LOGIN(EMAIL, PASSWORD, URL)
KEY(EMAIL, URL) VALUES ('jera_nogaj@tries.cb','igMEhriDlcbCjWv1S','https://costs905uwtaehtbr.mf')));
-- Вставляем запись в таблицу истории
MERGE INTO HISTORY(LOGIN_ID, CREATED_AT, STATUS, ERROR, ORDER_ID, OPERATION_TYPE) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, CURRENT_TIMESTAMP(), 2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 38, 2);
-- Вставляем запись в таблицу профиля
MERGE INTO PROFILE(LOGIN_ID, INN, ROLE, AVATAR, FULLNAME, USERNAME, USER_ID) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, 8496329861, 2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'Rodrigus Simpkins', 'osamahl', 38);

-- Вставляем запись в таблицу Login
SET @LOGIN_ID=(SELECT ID FROM FINAL TABLE(
MERGE INTO LOGIN(EMAIL, PASSWORD, URL)
KEY(EMAIL, URL) VALUES ('willis_morellygp@frontpage.hz','yCUBquLAbbh4o5u7vC','https://definitelypga1dsns0bt.vzk')));
-- Вставляем запись в таблицу истории
MERGE INTO HISTORY(LOGIN_ID, CREATED_AT, STATUS, ERROR, ORDER_ID, OPERATION_TYPE) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, CURRENT_TIMESTAMP(), 4, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 1106, 4);
-- Вставляем запись в таблицу профиля
MERGE INTO PROFILE(LOGIN_ID, INN, ROLE, AVATAR, FULLNAME, USERNAME, USER_ID) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, 1524038064, 4, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'Trinika Hern', 'jeralynnecnh', 1106);

-- Вставляем запись в таблицу Login
SET @LOGIN_ID=(SELECT ID FROM FINAL TABLE(
MERGE INTO LOGIN(EMAIL, PASSWORD, URL)
KEY(EMAIL, URL) VALUES ('jessicaann_tollesonrpf@studios.qg','ZkvYIeHdVpqHU8buF4yGU','https://archive6xlwagdcb.xux')));
-- Вставляем запись в таблицу истории
MERGE INTO HISTORY(LOGIN_ID, CREATED_AT, STATUS, ERROR, ORDER_ID, OPERATION_TYPE) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, CURRENT_TIMESTAMP(), 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 4982, 5);
-- Вставляем запись в таблицу профиля
MERGE INTO PROFILE(LOGIN_ID, INN, ROLE, AVATAR, FULLNAME, USERNAME, USER_ID) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, 5372058874, 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'Corban Easterwood', 'ainaaj', 4982);

-- Вставляем запись в таблицу Login
SET @LOGIN_ID=(SELECT ID FROM FINAL TABLE(
MERGE INTO LOGIN(EMAIL, PASSWORD, URL)
KEY(EMAIL, URL) VALUES ('chakira_eliasqb@political.nxp','oWn6DAtMEZj','https://plazan7ulj4d.zo')));
-- Вставляем запись в таблицу истории
MERGE INTO HISTORY(LOGIN_ID, CREATED_AT, STATUS, ERROR, ORDER_ID, OPERATION_TYPE) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, CURRENT_TIMESTAMP(), 8, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 2251260, 8);
-- Вставляем запись в таблицу профиля
MERGE INTO PROFILE(LOGIN_ID, INN, ROLE, AVATAR, FULLNAME, USERNAME, USER_ID) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, 0093985270, 8, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'Jacquelin Stonecipher', 'bridgettelwe', 2251260);

-- Вставляем запись в таблицу Login
SET @LOGIN_ID=(SELECT ID FROM FINAL TABLE(
MERGE INTO LOGIN(EMAIL, PASSWORD, URL)
KEY(EMAIL, URL) VALUES ('cornel_boyettdr@neck.ic','duP87txlZBjg','https://predictxia.pvf')));
-- Вставляем запись в таблицу истории
MERGE INTO HISTORY(LOGIN_ID, CREATED_AT, STATUS, ERROR, ORDER_ID, OPERATION_TYPE) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, CURRENT_TIMESTAMP(), 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 4, 5);
-- Вставляем запись в таблицу профиля
MERGE INTO PROFILE(LOGIN_ID, INN, ROLE, AVATAR, FULLNAME, USERNAME, USER_ID) KEY(LOGIN_ID)
VALUES (@LOGIN_ID, 5746032320, 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'Derryck Contreras', 'trintonz', 4);
COMMIT;
