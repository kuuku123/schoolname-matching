# schoolname-matching

https://github.com/kuuku123/schoolname-matching

Pattern-Matching , Validation 으로 2개의 처리 과정을 거쳐서 결과를 도출함 

학교이름 비슷한 단어들을 Regex로 (~초, ~중,~고 , ~초등학교 ,~중학교, ~고등학교) 후보군들을 찾은후에
N(13)개의 스레드로 병렬로 후보군들을 쪼개서 https://www.schoolinfo.go.kr/Main.do 에서 검색해보고 적절한 이름인지 확인함A(need to adjust Parameter(delay,threads) log 가 발생하면 Main.java 에서 thread수를 줄이거나 delay를 늘려야함)

위 사이트(shcoolinfo)에서 검증한후에 추합한 다음에 result.txt에 가나다순으로 정렬후 작성

추가적으로 "분당고 , 분당여중 ,분당체고" 같은 단어들이 매칭되면 validation에서 
"분당고등학교 , 분당여자중학교 , 분당체육고등학교" 처럼 변경하고나서 매칭되는 결과가 schoolInfo 에 있으면 이것으로 바꾸고 카운팅함


