insert into user_details(id,birth_date,name)
values(1001,current_date(),'Shantanu');

insert into user_details(id,birth_date,name)
values(1002,current_date(),'Komal');

insert into user_details(id,birth_date,name)
values(1003,current_date(),'Vimal');


insert into post(id,description,user_id)
values(2001,'I want to Learn AWS',1001);

insert into post(id,description,user_id)
values(2002,'I want to Learn DevOps',1001);

insert into post(id,description,user_id)
values(2003,'I want to get Certified for aws',1002);

insert into post(id,description,user_id)
values(2004,'I want to Learn Azure',1002);