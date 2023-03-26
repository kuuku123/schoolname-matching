# schoolname-matching

Pattern-Matching , Validation 으로 2개의 처리 과정을 거쳐서 결과를 도출함 

학교이름 비슷한 단어들을 Regex로 (~초, ~중,~고 , ~초등학교 ,~중학교, ~고등학교) 후보군들을 찾은후에
N(15)개의 스레드로 병렬로 쪼개서 https://www.schoolinfo.go.kr/Main.do 에서 검색해보고 적절한 이름인지 확인함

shcoolinfo에서 검증한후에 추합한 다음에 result.txt에 가나다순으로 정렬후 작성

추가적으로 "분당고" 라는 단어가 매칭되면 검색후 "분당고등학교" 같이 매칭되는 결과가 있으면 이것으로 바꾸고 카운팅함


