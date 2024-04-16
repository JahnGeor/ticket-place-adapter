BEGIN TRANSACTION;

PRAGMA foreign_keys=ON;
PRAGMA foreign_key_check;

DELETE FROM login;
DELETE FROM user_profile;

INSERT INTO
login(id, email, password, url, user_id, username)
VALUES('f6c8cf66-3e11-42ce-bdcb-dbaa279cfc49', 'edina_rigneyt6@bedroom.pt', '8BGPbBtS9erggo5HTPrf', 'https://slovenia5w.nup', 3087705, 'Chavez');
INSERT INTO
user_profile(id, login_id, avatar, inn, role, full_name)
VALUES('f399436e-d83b-5878-243f-43a92e4b6479
', 'f6c8cf66-3e11-42ce-bdcb-dbaa279cfc49', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 270394490, 'Ant lay franchise gone davis sapphire mysterious, traditions improving individuals buried qualification skin responding, levels claims insertion finds filter seem dose, think grain kansas sharing strength glad. ', 'Charisha Carman');

INSERT INTO
login(id, email, password, url, user_id, username)
VALUES('cce1e8a8-6bc5-4c1f-922c-64d376f553d7', 'fionna_broseq@guitar.dwo', 'Z2VFI7kpr9hAp', 'https://conferencesala60gceq0.egy', 051, 'Dontay');
INSERT INTO
user_profile(id, login_id, avatar, inn, role, full_name)
VALUES('ec4742b8-f096-31b2-0e8b-0e85695cc00a
', 'cce1e8a8-6bc5-4c1f-922c-64d376f553d7', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', -1777751532, 'Gather sexual context tomorrow destinations bacterial. ', 'Danyel Sedgwick');

INSERT INTO
login(id, email, password, url, user_id, username)
VALUES('4c7615d4-7c82-436c-9961-f80dedca0f27', 'francesa_laclairygk@liquid.mj', 'KIYiDcIYFEsbBz0Qg', 'https://petite1b64wm.rbx', 63, 'Brodi');
INSERT INTO
user_profile(id, login_id, avatar, inn, role, full_name)
VALUES('cea44711-91d4-6eb0-82fd-c6bd3c71dd3b
', '4c7615d4-7c82-436c-9961-f80dedca0f27', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', -229092986, 'Chairman arab nominations prizes collectibles milwaukee glass, reply information loved sponsorship teams camp fancy, jaguar thinkpad amenities cancel investigated physicians forever, breach monsters arctic lazy. ', 'Jedadiah Wong');

INSERT INTO
login(id, email, password, url, user_id, username)
VALUES('3cc8d5ae-b51b-4a7c-8168-b915e5b0347e', 'hani_dimarco7v@purchase.be', 'vDNbS7riTyCKd6aB', 'https://laosehpsnanwktti.lzx', 7802, 'Hamza');
INSERT INTO
user_profile(id, login_id, avatar, inn, role, full_name)
VALUES('2eb00b7e-20a7-1810-4211-5445bfe6bc34
', '3cc8d5ae-b51b-4a7c-8168-b915e5b0347e', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 2012098178, 'Evaluating columbus casinos installed other fruit frederick, jose treasure trek duty july hall periodic, wishlist wealth applicant jamie totals bridge config, toe protection effectively lookup appearing teams intermediate, minor. ', 'Francesca Lowther');

INSERT INTO
login(id, email, password, url, user_id, username)
VALUES('aebbdb82-9404-4f68-9946-04038bb539a6', 'rosario_quimbyddw0@bottom.kex', 'KwPAOVXJXhL39b6ip', 'https://ceramict9.px', 0199, 'Markiesha');
INSERT INTO
user_profile(id, login_id, avatar, inn, role, full_name)
VALUES('7b099351-28d0-c697-aea9-d3dbd5cf6b59
', 'aebbdb82-9404-4f68-9946-04038bb539a6', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', -2002058867, 'Recorders organisations iraq calibration programmes toy editor, rivers little wallace accepts attitudes harder crimes, equality serious acts ferrari duties liquid fonts, essentials active industrial. ', 'Dandrell Deen');

INSERT INTO
login(id, email, password, url, user_id, username)
VALUES('873b9ce6-fb51-4658-bfe7-a02af9cb9e79', 'aprill_dileogvp@bigger.cgg', '1H099Km6IIKWcPDg5pwC', 'https://catalogszd.zro', 325, 'Enedina');
INSERT INTO
user_profile(id, login_id, avatar, inn, role, full_name)
VALUES('b688a464-c961-0094-bec1-cf38e871d737
', '873b9ce6-fb51-4658-bfe7-a02af9cb9e79', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', -832927864, 'Effectively float abortion powered environment crawford menu, credit ferry vary taxation custom villages childhood, david leader. ', 'Boyd Hobby');

INSERT INTO
login(id, email, password, url, user_id, username)
VALUES('29fde7c8-9ece-4bef-93bd-a2ae8aceb0f1', 'keneta_malmb07@terrible.bpi', 'lXC3ZCusti', 'https://windsorx68cf3k.nq', 5434, 'Talina');
INSERT INTO
user_profile(id, login_id, avatar, inn, role, full_name)
VALUES('4cf8e141-4c2a-cbff-2cf7-d2e5c9ae0894
', '29fde7c8-9ece-4bef-93bd-a2ae8aceb0f1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', -479679390, 'Gathering controllers video eye sagem guest weblogs, warned lawyer dealer. ', 'Mathilde Neill');

INSERT INTO
login(id, email, password, url, user_id, username)
VALUES('231de1bc-1000-46db-a443-0ac6af319c02', 'kartik_manuso@me.cy', '78pUkG7EwGZjNMUU5tY', 'https://clearingozxwuo.al', 717960, 'Yvan');
INSERT INTO
user_profile(id, login_id, avatar, inn, role, full_name)
VALUES('bf229b73-3c8a-0380-3186-e6badb4f23e7
', '231de1bc-1000-46db-a443-0ac6af319c02', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', -1543303311, 'Taste fitting binary mexican hygiene metabolism vacation, cameron keyboard scope attended miss quotations convention, infrared atmosphere degrees sellers who christianity butler, dietary vegetable ends idle mortgages eventually. ', 'Raymar Wamsley');

INSERT INTO
login(id, email, password, url, user_id, username)
VALUES('60eaa95d-72cb-442e-8372-03317aee826f', 'london_hypesg@moving.uk', 'AlssjGoplIgg7A8ABQ8iNg', 'https://moviesuypmaoolnbjv.yoy', 2408, 'Janesha');
INSERT INTO
user_profile(id, login_id, avatar, inn, role, full_name)
VALUES('a6ad2145-c96f-9cee-f6a8-4e033ef46159
', '60eaa95d-72cb-442e-8372-03317aee826f', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 1741033303, 'Xhtml upper brown retreat ghost fly steps, sit noon those fame infants residents determination, performs april era jaguar spanish. ', 'Adella Gum');

INSERT INTO
login(id, email, password, url, user_id, username)
VALUES('f4aa6c2c-04b4-40fd-a0e5-1b2cf74dda39', 'gittel_harrel3tk@bike.ru', 'QgW5gIARinULASOi4ueaaDx', 'https://exoticwwmxlq.we', 4756686, 'Leoncio');
INSERT INTO
user_profile(id, login_id, avatar, inn, role, full_name)
VALUES('9f597327-63de-f5b0-be9a-e1c77c71c405
', 'f4aa6c2c-04b4-40fd-a0e5-1b2cf74dda39', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', -174961078, 'Save incident suspect later richardson oman dental, herbs literacy context cisco cop core playing, ups administrator figure served fancy tobacco declaration, praise. ', 'Janetta Jarnagin');
COMMIT;