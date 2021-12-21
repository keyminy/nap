create table tb_board(
	idx int not null auto_increment,
	title varchar(100) not null,
	contents varchar(4000) not null,
	count int,
	writer varchar(30) not null,
	indate datetime default now() not null,
	primary key(idx)
);
insert into tb_board(title,contents,count,writer)
values('게시판 만들기','게시판 만들기',0,'관리자');
select * from tb_board;