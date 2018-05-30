INSERT INTO TBL_CRAW_KORDIC('WORD') VALUES('word');
select * from TBL_CRAW_KORDIC;

select * from TBL_CRAW_KORDIC group by word having count(*) > 1;

select * from TBL_CRAW_KORDIC where word = 'bunk';

select * from TBL_CRAW_KORDIC where id between 114624 and 114724;

select * from TBL_CRAW_KORDIC where CRAW_TEXT is null;

delete from TBL_CRAW_KORDIC;

select count(*) from TBL_CRAW_KORDIC;

select count(*) from TBL_CRAW_KORDIC where WORD like 'a%';

select * from TBL_CRAW_KORDIC where WORD like 'a%';

