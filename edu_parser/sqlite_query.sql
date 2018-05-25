INSERT INTO TBL_CRAW_KORDIC('WORD') VALUES('word');
select * from TBL_CRAW_KORDIC;

select * from TBL_CRAW_KORDIC group by word having count(*) > 1;

select * from TBL_CRAW_KORDIC where word = 'bunk';

select * from TBL_CRAW_KORDIC where id between 100 and 200;