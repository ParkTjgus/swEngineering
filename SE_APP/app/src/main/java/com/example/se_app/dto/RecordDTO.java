package com.example.se_app.dto;

public class RecordDTO {

    /* 당일 기록 조회 */
    public static class TodayRecord {
        private int recordTime; //당일 기록시간

        //setter
        public int setRecordTimeToday() {
            this.recordTime = recordTime;
            return 0;
        }
    }

    /* 기록하기, 출석하기 */
    public static class StartRecord {
        private Double userLatitude; //경도
        private Double userLongitude; //위도
        private String message; //매세지

        //생성자
        public StartRecord(Double userLatitude, Double userLongitude) {
            this.userLatitude = userLatitude;
            this.userLongitude = userLongitude;
        }

        //setter
        public void setUserLongitude(Double userLongitude) { this.userLatitude = userLongitude; }
        public void setUserLatitude(Double userLatitude) { this.userLatitude = userLatitude; }

        //getter
        public Double getUserLongitude() { return userLongitude; }
        public Double getUserLatitude() { return userLatitude; }
        public String getMessage() { return message; }

    }

    /* 기록 중단 */
    public static class StopRecord {
        private int recordTime;
        private Double userLatitude;
        private Double userLongitude;
        private String message;
        public StopRecord(int recordTime, Double userLatitude, Double userLongitude) {
            this.recordTime = recordTime;
            this.userLatitude = userLatitude;
            this.userLongitude = userLongitude;
        }

        //setter
        public void setRecordTime(int recordTime) { this.recordTime = recordTime; }
        public void setUserLatitude(Double userLatitude) { this.userLatitude = userLatitude; }

        public void setUserLongitude(Double userLongitude) { this.userLongitude = userLongitude; }

        //getter
        public int getRecordTime() { return recordTime; }
        public Double getUserLatitude() { return userLatitude; }
        public Double getUserLongitude() { return userLongitude; }
        public String getMessage() { return message; }
    }


    /* 위치 보내기 */
    public static class Location {
        private int recordTime;
        private Double memberLatitude;
        private Double memberLongitude;
        private String message;
        public Location(int recordTime, Double userLatitude, Double userLongitude) {
            this.recordTime = recordTime;
            this.memberLatitude = userLatitude;
            this.memberLongitude = userLongitude;
        }

        //setter
        public void setRecordTime(int recordTime) { this.recordTime = recordTime; }
        public void setMemberLatitude(Double userLatitude) { this.memberLatitude = userLatitude; }

        public void setMemberLongitude(Double userLongitude) { this.memberLongitude = userLongitude; }

        //getter
        public int getRecordTime() { return recordTime; }
        public Double getMemberLatitude() { return memberLatitude; }

        public Double getMemberLongitude() { return memberLongitude; }
        public String getMessage() { return message; }

    }


}