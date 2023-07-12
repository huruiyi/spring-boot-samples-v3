insert into student(id, name, passport_id) values (20001, 'Ranga', 40001);
insert into student(id, name, passport_id) values (20002, 'Adam', 40002);
insert into student(id, name, passport_id) values (20003, 'Jane', 40003);

insert into passport(id, no) values (40001, 'E123456');
insert into passport(id, no) values (40002, 'N123457');
insert into passport(id, no) values (40003, 'L123890');

insert into course(id, name, created_date, last_updated_date) values (10001, 'JPA in 50 Steps', sysdate, sysdate);
insert into course(id, name, created_date, last_updated_date) values (10002, 'Spring in 50 Steps', sysdate, sysdate);
insert into course(id, name, created_date, last_updated_date) values (10003, 'Spring Boot in 100 Steps', sysdate, sysdate);

insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10001, 50001, 'Great Course', '5');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10001, 50002, 'Wonderful Course', '4');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10003, 50003, 'Awesome Course', '5');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10001, 50004, '4', '4');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10001, 50005, '5', '5');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10001, 50006, '6', '6');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10001, 50007, '7', '7');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10002, 50008, '8', '8');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10003, 50009, '9', '9');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10002, 50010, '10', '10');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10003, 50011, '11', '11');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10002, 50012, '12', '12');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10003, 50013, '13', '13');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10003, 50014, '14', '14');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10002, 50015, '15', '15');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10003, 50016, '16', '16');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10003, 50017, '17', '17');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10002, 50018, '18', '18');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10002, 50019, '19', '19');
insert into REVIEW (COURSE_ID, ID, DESCRIPTION, RATING) values (10003, 50020, '20', '20');

insert into student_course(student_id, course_id) values (20001, 10001);
insert into student_course(student_id, course_id) values (20002, 10001);
insert into student_course(student_id, course_id) values (20003, 10001);
insert into student_course(student_id, course_id) values (20001, 10003);
