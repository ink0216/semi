DROP TABLE "CONTINENT";

CREATE TABLE "CONTINENT" (
	"CONTI_CODE"	VARCHAR2(2)		NOT NULL,
	"CONTI_NAME"	VARCHAR(10)		NOT NULL
);


COMMENT ON COLUMN "CONTINENT"."CONTI_CODE" IS '대륙코드';

COMMENT ON COLUMN "CONTINENT"."CONTI_NAME" IS '대륙이름';

DROP TABLE "COUNTRY";

CREATE TABLE "COUNTRY" (
	"COUNTRY_CODE"	VARCHAR2(2)		NOT NULL,
	"COUNTRY_NAME"	VARCHAR2(20)		NOT NULL,
	"CONTI_CODE"	VARCHAR2(2)		NOT NULL
);

COMMENT ON COLUMN "COUNTRY"."COUNTRY_CODE" IS '국가코드';

COMMENT ON COLUMN "COUNTRY"."COUNTRY_NAME" IS '국가명';

COMMENT ON COLUMN "COUNTRY"."CONTI_CODE" IS '대륙코드';

DROP TABLE "BOARD";

CREATE TABLE "BOARD" (
	"BOARD_NO"	NUMBER		NOT NULL,
	"BOARD_TITLE"	NVARCHAR2(100)		NOT NULL,
	"BOARD_CONTENT"	VARCHAR2(4000)		NOT NULL,
	"WRITE_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"READ_COUNT"	NUMBER	DEFAULT 0	NOT NULL,
	"BOARD_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"COUNTRY_CODE"	VARCHAR2(2)		NOT NULL
);

COMMENT ON COLUMN "BOARD"."BOARD_NO" IS '게시글번호';

COMMENT ON COLUMN "BOARD"."BOARD_TITLE" IS '게시글제목';

COMMENT ON COLUMN "BOARD"."BOARD_CONTENT" IS '게시글내용';

COMMENT ON COLUMN "BOARD"."WRITE_DATE" IS '작성일';

COMMENT ON COLUMN "BOARD"."READ_COUNT" IS '조회수';

COMMENT ON COLUMN "BOARD"."BOARD_DEL_FL" IS '게시글 삭제 여부';

COMMENT ON COLUMN "BOARD"."MEMBER_NO" IS '작성한 회원번호';

COMMENT ON COLUMN "BOARD"."COUNTRY_CODE" IS '국가코드';

DROP TABLE "MEMBER";

CREATE TABLE "MEMBER" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"MEMBER_NICKNAME"	NVARCHAR2(10)		NOT NULL,
	"ENROLL_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"MEMBER_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"MEMBER_EMAIL"	NVARCHAR2(50)		NOT NULL,
	"MEMBER_PW"	NVARCHAR2(100)		NOT NULL,
	"MEMBER_TEL"	CHAR(11)		NOT NULL,
	"MEMBER_ADDRESS"	NVARCHAR2(300)		NULL,
	"PROFILE_IMG"	VARCHAR2(300)		NULL,
	"AUTHORITY"	NUMBER	DEFAULT 0	NOT NULL
);

COMMENT ON COLUMN "MEMBER"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "MEMBER"."MEMBER_NICKNAME" IS '회원닉네임';

COMMENT ON COLUMN "MEMBER"."ENROLL_DATE" IS '회원가입일';

COMMENT ON COLUMN "MEMBER"."MEMBER_DEL_FL" IS '회원탈퇴여부';

COMMENT ON COLUMN "MEMBER"."MEMBER_EMAIL" IS '회원이메일';

COMMENT ON COLUMN "MEMBER"."MEMBER_PW" IS '회원비밀번호';

COMMENT ON COLUMN "MEMBER"."MEMBER_TEL" IS '전화번호';

COMMENT ON COLUMN "MEMBER"."MEMBER_ADDRESS" IS '주소';

COMMENT ON COLUMN "MEMBER"."PROFILE_IMG" IS '프로필이미지';

COMMENT ON COLUMN "MEMBER"."AUTHORITY" IS '권한(0 : 일반 // 1: 관리자)';

DROP TABLE "BOARD_IMG";

CREATE TABLE "BOARD_IMG" (
	"IMG_NO"	NUMBER		NOT NULL,
	"IMG_ORDER"	NUMBER		NOT NULL,
	"IMG_RENAME"	NVARCHAR2(50)		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL,
	"IMG_PATH"	VARCHAR2(200)		NOT NULL
);

COMMENT ON COLUMN "BOARD_IMG"."IMG_NO" IS '파일 번호';

COMMENT ON COLUMN "BOARD_IMG"."IMG_ORDER" IS '파일 순서';

COMMENT ON COLUMN "BOARD_IMG"."IMG_RENAME" IS '이미지 변경명';

COMMENT ON COLUMN "BOARD_IMG"."BOARD_NO" IS '게시글번호';

COMMENT ON COLUMN "BOARD_IMG"."IMG_PATH" IS '이미지 요청 경로';

DROP TABLE "COMMENT";

CREATE TABLE "COMMENT" (
	"COMMENT_NO"	NUMBER		NOT NULL,
	"COMMENT_CONTENT"	VARCHAR2(4000)		NOT NULL,
	"COMMENT_WRITE_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"COMMENT_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL,
	"PARENT_COMMENT_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "COMMENT"."COMMENT_NO" IS '댓글 번호(PK)';

COMMENT ON COLUMN "COMMENT"."COMMENT_CONTENT" IS '댓글 내용';

COMMENT ON COLUMN "COMMENT"."COMMENT_WRITE_DATE" IS '댓글 작성일';

COMMENT ON COLUMN "COMMENT"."COMMENT_DEL_FL" IS '댓글 삭제 여부';

COMMENT ON COLUMN "COMMENT"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "COMMENT"."BOARD_NO" IS '게시글번호';

COMMENT ON COLUMN "COMMENT"."PARENT_COMMENT_NO" IS '댓글 번호(PK)';

DROP TABLE "LIKE";

CREATE TABLE "LIKE" (
	"BOARD_NO"	NUMBER		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "LIKE"."BOARD_NO" IS '게시글번호';

COMMENT ON COLUMN "LIKE"."MEMBER_NO" IS '회원번호';

ALTER TABLE "CONTINENT" ADD CONSTRAINT "PK_CONTINENT" PRIMARY KEY (
	"CONTI_CODE"
);

ALTER TABLE "COUNTRY" ADD CONSTRAINT "PK_COUNTRY" PRIMARY KEY (
	"COUNTRY_CODE"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "PK_BOARD" PRIMARY KEY (
	"BOARD_NO"
);

ALTER TABLE "MEMBER" ADD CONSTRAINT "PK_MEMBER" PRIMARY KEY (
	"MEMBER_NO"
);

ALTER TABLE "BOARD_IMG" ADD CONSTRAINT "PK_BOARD_IMG" PRIMARY KEY (
	"IMG_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "PK_COMMENT" PRIMARY KEY (
	"COMMENT_NO"
);

ALTER TABLE "LIKE" ADD CONSTRAINT "PK_LIKE" PRIMARY KEY (
	"BOARD_NO",
	"MEMBER_NO"
);

ALTER TABLE "COUNTRY" ADD CONSTRAINT "FK_CONTINENT_TO_COUNTRY_1" FOREIGN KEY (
	"CONTI_CODE"
)
REFERENCES "CONTINENT" (
	"CONTI_CODE"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "FK_MEMBER_TO_BOARD_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "FK_COUNTRY_TO_BOARD_1" FOREIGN KEY (
	"COUNTRY_CODE"
)
REFERENCES "COUNTRY" (
	"COUNTRY_CODE"
);

ALTER TABLE "BOARD_IMG" ADD CONSTRAINT "FK_BOARD_TO_BOARD_IMG_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_MEMBER_TO_COMMENT_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_BOARD_TO_COMMENT_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_COMMENT_TO_COMMENT_1" FOREIGN KEY (
	"PARENT_COMMENT_NO"
)
REFERENCES "COMMENT" (
	"COMMENT_NO"
);

ALTER TABLE "LIKE" ADD CONSTRAINT "FK_BOARD_TO_LIKE_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "LIKE" ADD CONSTRAINT "FK_MEMBER_TO_LIKE_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);
COMMIT;

--테이블 컬럼 자료형 바꾸는 ALTER구문!!
ALTER TABLE CONTINENT 
MODIFY CONTI_NAME VARCHAR2(100);

INSERT INTO "CONTINENT" VALUES(
'AF', '아프리카'
); --완료
INSERT INTO "CONTINENT" VALUES(
'SA', '남아메리카'
);
INSERT INTO "CONTINENT" VALUES(
'AS', '아시아'
);
INSERT INTO "CONTINENT" VALUES(
'OC', '오세아니아'
);
INSERT INTO "CONTINENT" VALUES(
'EU', '유럽'
);
INSERT INTO "CONTINENT" VALUES(
'NA', '북아메리카'
);
SELECT * FROM BOARD;

--댓글 테이블 부모 댓글 번호 NULL 허용으로 바꾸기!!(함!!!)
ALTER TABLE "COMMENT" 
MODIFY PARENT_COMMENT_NO NUMBER NULL;

--qwer
-- $2a$10$NXVVwXw30DFL86sT2868kOdWrF0UORlvvCHiHGm25kHfZ9YCeRMT2
--샘플 회원 데이터 삽입
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL,
			'샘플샘플1',
			DEFAULT,
			DEFAULT,
			'qwer',
			'$2a$10$NXVVwXw30DFL86sT2868kOdWrF0UORlvvCHiHGm25kHfZ9YCeRMT2',
			'01011112222',
			NULL,
			NULL,
			DEFAULT
			);
--국가 샘플 데이터 삽입
INSERT INTO "COUNTRY"
VALUES('KO','대한민국','AS');
--게시글 번호 시퀀스 생성
CREATE SEQUENCE SEQ_BOARD_NO NOCACHE;
--게시글 샘플 데이터 삽입
INSERT INTO "BOARD"
VALUES(SEQ_BOARD_NO.NEXTVAL,
			'중국 1번 게시글입니다.',
			'중국 1번 게시글 내용입니다.',
			DEFAULT,
			0,
			DEFAULT,
			3,
			'CN'
);


--BOARD_IMG 테이블용 시퀀스 생성
CREATE SEQUENCE SEQ_IMG_NO NOCACHE;
--BOARD_IMG 테이블에 샘플 데이터 삽입
INSERT INTO BOARD_IMG 
VALUES (SEQ_IMG_NO.NEXTVAL,
				1,
				'스폰지밥2.gif',
				6,
				'/images/board/'
);

COMMIT;
--게시글 상세 조회하는 SQL
SELECT BOARD_NO, BOARD_TITLE , BOARD_CONTENT ,CONTI_CODE,
			READ_COUNT , MEMBER_NO , MEMBER_NICKNAME,PROFILE_IMG,
			TO_CHAR(WRITE_DATE, 'YYYY"년" MM"월" DD"일" HH24:MI:SS') WRITE_DATE,
			(SELECT COUNT(*) FROM "LIKE" 
				WHERE BOARD_NO=6 ) LIKE_COUNT,
				(SELECT IMG_PATH||IMG_RENAME 
					FROM BOARD_IMG 
					WHERE BOARD_NO=6
					AND IMG_ORDER=0) THUMBNAIL
FROM "BOARD"
JOIN "COUNTRY" USING (COUNTRY_CODE)
JOIN "CONTINENT" USING (CONTI_CODE)
JOIN "MEMBER" USING (MEMBER_NO)
WHERE BOARD_DEL_FL ='N'
AND CONTI_CODE = 'AS'
AND BOARD_NO = 6;
--회원 가입 시 프로필 이미지 지정 안할 경우 디폴트 값 지정->안해도 됨!
-- /images/profile/
--ALTER TABLE "MEMBER" MODIFY 
--(PROFILE_IMG DEFAULT '/images/profile.jpg')
--이렇게 하면 회원 수 대로 컬럼에 불필요한 값이 insert되게돼서 
--선생님은 messages.properties에 값 넣어놓고 꺼내왔다 (myPage-profile.html에서 타임리프로!)
------------------------
--댓글 테이블 댓글 번호 시퀀스 생성
CREATE SEQUENCE SEQ_COMMENT_NO NOCACHE;
--댓글 샘플 데이터 삽입
INSERT INTO "COMMENT" 
VALUES (SEQ_COMMENT_NO.NEXTVAL, 
				'6번 게시글의 2번 부모의 1번 댓글의 1번 자식 댓글',
				DEFAULT,
				DEFAULT,
				3,
				6,
				6
				);
			UPDATE "COMMENT"
			SET COMMENT_CONTENT  = '6번 게시글의 1번 부모의 1번 댓글'
			WHERE COMMENT_NO =4;
			SELECT * FROM "COMMENT";
COMMIT;
--상세 조회되는 게시글의 모든 이미지 조회하기
SELECT * 
FROM "BOARD_IMG" 
WHERE BOARD_NO =6
ORDER BY IMG_ORDER ;

/*상세조회되는 게시글의 모든 댓글 조회*/
/*계층형 쿼리
 * START WITH
 * 계층형에서 NULL인 애들이 1레벨이야
 * CONNECT BY PRIOR 
 * COMMENT_NO랑 PARENT_COMMENT_NO가 같은 것끼리 연결해줄거야
 * 정렬은 같은 레벨끼리 정렬하는데 COMMENT_NO 오름차순으로 정렬할거야
 * */

SELECT LEVEL, C.* 
FROM
		(SELECT COMMENT_NO, COMMENT_CONTENT,
		  TO_CHAR(COMMENT_WRITE_DATE, 'YYYY"년" MM"월" DD"일" HH24"시" MI"분" SS"초"') COMMENT_WRITE_DATE,
		    BOARD_NO, MEMBER_NO, MEMBER_NICKNAME, PROFILE_IMG, PARENT_COMMENT_NO, COMMENT_DEL_FL
		FROM "COMMENT"
		JOIN MEMBER USING(MEMBER_NO)
		WHERE BOARD_NO = 6) C --서브쿼리의 결과가 테이블 됨
WHERE COMMENT_DEL_FL = 'N'
	OR 0 != (SELECT COUNT(*) FROM "COMMENT" SUB
					WHERE SUB.PARENT_COMMENT_NO = C.COMMENT_NO
					AND COMMENT_DEL_FL = 'N')
	START WITH PARENT_COMMENT_NO IS NULL
	CONNECT BY PRIOR COMMENT_NO = PARENT_COMMENT_NO
	ORDER SIBLINGS BY COMMENT_NO;
--이 결과를 저장할 DTO 만들기

--상세조회하면서 SELECT 3회 할거다
--DTO없는 이미지랑, 댓글에 대한 DTO 만들고 결과 다 가져와서 화면에 꾸미면 된다!!

--해당 게시판에 존재하는 게시글의 나라 이름 종류 조회
SELECT DISTINCT COUNTRY_NAME
FROM "BOARD"
JOIN "COUNTRY" USING (COUNTRY_CODE)
JOIN "CONTINENT" USING (CONTI_CODE)
WHERE CONTI_CODE = 'AS';


CREATE OR REPLACE FUNCTION NEXT_IMG_NO

--반환형 숫자가 반환될거다
RETURN NUMBER 

--사용할 변수를 미리 선언
IS IMG_NO NUMBER;

BEGIN
	SELECT SEQ_IMG_NO.NEXTVAL --10번 생성 시
	INTO IMG_NO --생성된 10번을 여기다 넣겠다
	FROM DUAL;

	RETURN IMG_NO; --이 값을 리턴하겠다
END;
;
SELECT NEXT_IMG_NO() FROM DUAL;
--대륙별 국가 샘플 데이터 삽입
INSERT INTO "COUNTRY" VALUES
('US', '미국', 'NA');
COMMIT;

UPDATE "COUNTRY" SET 
COUNTRY_NAME ='남아프리카공화국'
WHERE COUNTRY_NAME ='남아공';
--국가명 자료형 바꾸는 구문
ALTER TABLE COUNTRY 
MODIFY COUNTRY_NAME VARCHAR2(100);

--좋아요 테이블 LIKE에 샘플 데이터 삽입
INSERT INTO "LIKE" VALUES (19,3);
COMMIT;
SELECT * FROM "LIKE";
SELECT * FROM "MEMBER";




-- 내가쓴글 조회 구문
SELECT BOARD_NO,COUNTRY_NAME, BOARD_TITLE,WRITE_DATE ,READ_COUNT,
		(SELECT COUNT(*)
		FROM "LIKE" L 
		WHERE L.BOARD_NO  = B.BOARD_NO) LIKE_COUNT
FROM BOARD B 
JOIN COUNTRY USING (COUNTRY_CODE)
WHERE MEMBER_NO = 3
ORDER BY BOARD_NO DESC ;

SELECT CONTI_CODE, COUNT(*) FROM "COUNTRY" GROUP BY CONTI_CODE ;
INSERT INTO "COUNTRY" VALUES
('PE', '페루', 'SA');
UPDATE "BOARD" SET BOARD_DEL_FL ='Y' WHERE BOARD_NO =17;
COMMIT;
UPDATE "MEMBER" SET MEMBER_EMAIL ='ink0215@naver.com'
WHERE MEMBER_EMAIL ='ink0216@naver.com';

UPDATE "MEMBER" SET PROFILE_IMG  ='/myPage/profile/20240503105748_00003.webp'
WHERE MEMBER_NICKNAME  ='Sponge';

COMMIT;
