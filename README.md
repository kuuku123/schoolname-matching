# schoolname-matching
학교이름 비슷한 단어들을 Regex로 찾은후에 https://www.schoolinfo.go.kr/Main.do 에서 검색해보고 적절한 이름인지 확인함

15개의 스레드로 병렬로 쪼개서 처리한후에 result.txt에 가나다순으로 정렬후 작성

추가적으로 "분당고" 라는 단어가 매칭되면 검색후 "분당고등학교" 같이 매칭되는 결과가 있으면 이것으로 바꾸고 카운팅함


