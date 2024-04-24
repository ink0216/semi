CREATE TABLE "CONTINENT" (
	"CONTI_CODE"	VARCHAR2(2)		NOT NULL,
	"CONTI_NAME"	VARCHAR(10)		NOT NULL
);

COMMENT ON COLUMN "CONTINENT"."CONTI_CODE" IS '대륙코드';

COMMENT ON COLUMN "CONTINENT"."CONTI_NAME" IS '대륙이름';

CREATE TABLE "COUNTRY" (
	"COUNTRY_CODE"	VARCHAR2(2)		NOT NULL,
	"COUNTRY_NAME"	VARCHAR2(20)		NOT NULL,
	"CONTI_CODE"	VARCHAR2(2)		NOT NULL
);

COMMENT ON COLUMN "COUNTRY"."COUNTRY_CODE" IS '국가코드';

COMMENT ON COLUMN "COUNTRY"."COUNTRY_NAME" IS '국가명';

COMMENT ON COLUMN "COUNTRY"."CONTI_CODE" IS '대륙코드';

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
INSERT INTO CONTINENT VALUES(
	
);

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
COMMIT;
------------------------------------------------
--회원 번호 시퀀스 생성
CREATE SEQUENCE SEQ_MEMBER_NO NOCACHE; --이것만 만듦!
--게시글 번호 시퀀스 생성
CREATE SEQUENCE SEQ_BOARD_NO NOCACHE; --이것만 만듦!
--게시글 샘플 데이터 삽입
INSERT INTO BOARD VALUES
(SEQ_BOARD_NO.NEXTVAL, 
''
);
--댓글 번호 시퀀스 생성
CREATE SEQUENCE SEQ_COMMENT_NO NOCACHE; --이것만 만듦!
--댓글 샘플 데이터 삽입


--댓글 테이블 부모 댓글 번호 NULL 허용으로 바꾸기!!(아직 안함)
ALTER TABLE "COMMENT" 
MODIFY PARENT_COMMENT_NO NUMBER NULL;

--특정 게시판에 삭제되지 않은 게시글 목록 조회
--단, 최신글이 제일 위에 존재
--몇 초/분/시간 전 또는 YYYY-MM-DD 형식으로 작성일 조회
-- + 댓글 개수
-- + 좋아요 개수

-- 번호 / 제목[댓글개수] / 작성자닉네임 / 작성일  / 조회수 / 좋아요 수
--상관 서브쿼리
--1)메인 쿼리 1행 조회
--2) 1행 조회 결과를 이용해서 서브쿼리 수행
--	(매안 쿼리 모두 조회할 떄까지 반복한다) 
SELECT BOARD_NO, BOARD_TITLE, MEMBER_NICKNAME, READ_COUNT,
	(SELECT COUNT(*) FROM "COMMENT" C
	WHERE C.BOARD_NO = B.BOARD_NO) COMMENT_COUNT,
	(SELECT COUNT(*)
	FROM "LIKE" L
	WHERE L.BOARD_NO = B.BOARD_NO) LIKE_COUNT,
	CASE 
		WHEN SYSDATE-WRITE_DATE <1/24/60
		THEN FLOOR((SYSDATE-WRITE_DATE)*24*60*60)||'초 전'
		
		WHEN SYSDATE-WRITE_DATE <1/24
		THEN FLOOR((SYSDATE-WRITE_DATE)*24*60)||'분 전'
		
		WHEN SYSDATE-WRITE_DATE <1
		THEN FLOOR((SYSDATE-WRITE_DATE)*24)||'시간 전'
		--더 오래 지나면 ELSE로 날짜로 나온다
		ELSE TO_CHAR(WRITE_DATE, 'YYYY-MM-DD')
	END WRITE_DATE 
	
	FROM "BOARD" B
	JOIN "MEMBER" USING (MEMBER_NO)
	WHERE BOARD_DEL_FL = 'N'
	ORDER BY BOARD_NO DESC;

--이 조회 결과 전체 한 행의 정보를 담을 BOARD DTO 만들기!

--$2a$10$EvNgaFSGQUygb2DdiiiPS.8JGdJJBx.mcZ3txZ8Hvgo9d2JmFkfTe
--샘플 회원 데이터 삽입
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL,
			'샘플샘플1',
			DEFAULT,
			DEFAULT,
			'qwer',
			'$2a$10$EvNgaFSGQUygb2DdiiiPS.8JGdJJBx.mcZ3txZ8Hvgo9d2JmFkfTe',
			'01012345678',
			NULL,
			NULL,
			DEFAULT
			);
SELECT * FROM "MEMBER";
COMMIT;

