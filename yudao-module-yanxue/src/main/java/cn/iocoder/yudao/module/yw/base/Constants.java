package cn.iocoder.yudao.module.yw.base;


public class Constants {


    //风险等级
    public static enum AuthCommentType{
        CONDI("CONDI"),DETAIL("DETAIL");
        private String value;
        AuthCommentType(String value){
            this.value=value;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }

    public static enum AuthStatus{
        Init("0"),FIX("1"),SUBMITED("4"),COMMENT("5"),FAIL("9"),SUCC("10");
        private String value;
        AuthStatus(String value){
            this.value=value;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }
    //风险等级的处理状态
    public static enum ArticleType{
        NEWS("NEWS"),ACTIVITY("ACTIVITY"),ZYZRZ("ZYZRZ"),ZPRZ("ZPRZ"),ZP("ZP");
        private String value;
        ArticleType(String value){
            this.value=value;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }
    public static enum ArticleClassfication{
        ZONE("ZONE"),TOPIC("TOPIC"),GROUP("GROUP"),MAJOR("MAJOR");
        private String value;
        ArticleClassfication(String value){
            this.value=value;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }
}
